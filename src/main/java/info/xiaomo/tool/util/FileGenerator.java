package info.xiaomo.tool.util;


import info.xiaomo.tool.config.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileGenerator {


    /**
     * 生成文件
     *
     * @param fileConfig fileConfig
     * @param output     fileConfig
     * @param type       type
     * @throws IOException IOException
     */
    public static void generator(FileConfig fileConfig, String output, int type) throws IOException {

        if (!checkId(fileConfig)) {
            return;
        }

        // 生成bean
        for (BeanConfig beanConfig : fileConfig.getBeanList()) {
            String template = TemplateUtil.beanTemplate();
            template = template.replaceAll("\\{package}", generatorPackage(fileConfig, beanConfig));
            template = template.replaceAll("\\{import}", generatorImport(fileConfig, beanConfig));
            template = template.replaceAll("\\{className}", beanConfig.getClassName());
            template = template.replaceAll("\\{doc}", beanConfig.getDesc() == null ? "" : beanConfig.getDesc());
            template = template.replaceAll("\\{field}", generateField(beanConfig));
            template = template.replaceAll("\\{getterAndSetter}", generatorGetterAndSetter(beanConfig));
            template = template.replaceAll("\\{write}", generatorWrite(beanConfig));
            template = template.replaceAll("\\{read}", generatorRead(beanConfig));
            write(fileConfig.getPackagePath() + ".msg.bean", beanConfig.getClassName(), template, output);
        }

        // 生成message
        for (MessageConfig messageConfig : fileConfig.getMessageList()) {
            String template = TemplateUtil.messageTemplate();
            template = template.replaceAll("\\{package}", generatorPackage(fileConfig, messageConfig));
            template = template.replaceAll("\\{import}", generatorImport(fileConfig, messageConfig));
            template = template.replaceAll("\\{className}", messageConfig.getClassName());
            template = template.replaceAll("\\{doc}", messageConfig.getDesc() == null ? "" : messageConfig.getDesc());
            template = template.replaceAll("\\{field}", generateField(messageConfig));
            template = template.replaceAll("\\{getterAndSetter}", generatorGetterAndSetter(messageConfig));
            template = template.replaceAll("\\{write}", generatorWrite(messageConfig));
            template = template.replaceAll("\\{read}", generatorRead(messageConfig));
            int id = fileConfig.getGroup() * 1000 + messageConfig.getId();
            template = template.replaceAll("\\{id}", String.valueOf(id));
            int queueId = messageConfig.getQueueId();
            if (queueId <= 0) {
                queueId = fileConfig.getQueueId();
            }
            template = template.replaceAll("\\{queueId}", String.valueOf(queueId));

            write(fileConfig.getPackagePath() + ".msg", messageConfig.getClassName(), template, output);
        }

    }


    /**
     * 检查id是否重复
     *
     * @param fileConfig fileConfig
     * @return boolean
     */
    private static boolean checkId(FileConfig fileConfig) {
        List<String> existList = new ArrayList<>();
        for (MessageConfig config : fileConfig.getMessageList()) {
            if (existList.contains(config.getId() + "_" + config.getDir().name())) {
                System.out.println("消息" + config.getClassName() + "重复：id->" + config.getId() + ",group->" + fileConfig.getGroup());
                return false;
            }
            existList.add(config.getId() + "_" + config.getDir().name());
        }
        return true;
    }


    /**
     * 生成注释
     *
     * @param beanConfig beanConfig
     * @return String
     * @throws IOException IOException
     */
    private static String generateField(BeanConfig beanConfig) throws IOException {
        StringBuilder ret = new StringBuilder();
        for (DataConfig config : beanConfig.getFieldList()) {
            String template;

            if (config.isList()) {
                template = TemplateUtil.listFieldTemplate();
            } else {
                template = TemplateUtil.fieldTemplate();
            }
            template = template.replaceAll("\\{type}", generatorFieldType(config, config.isList()));
            template = template.replaceAll("\\{fieldName}", config.getName());
            template = template.replaceAll("\\{doc}", config.getDesc());
            ret.append(template);
        }
        return ret.toString();
    }

    /**
     * 生成get/set方法
     *
     * @param beanConfig beanConfig
     * @return String
     * @throws IOException IOException
     */
    private static String generatorGetterAndSetter(BeanConfig beanConfig) throws IOException {
        StringBuilder ret = new StringBuilder();
        for (DataConfig config : beanConfig.getFieldList()) {
            String template;
            if (config.isList()) {
                template = TemplateUtil.listGetterAndSetter();
            } else {
                template = TemplateUtil.getterAndSetterTemplate();
            }
            template = template.replaceAll("\\{type}", generatorFieldType(config, config.isList()));
            template = template.replaceAll("\\{fieldName}", config.getName());
            template = template.replaceAll("\\{methodName}", toFirstWorldUpper(config.getName()));
            ret.append(template);
        }
        return ret.toString();
    }

    /**
     * 生成import 头
     *
     * @param fileConfig fileConfig
     * @param beanConfig String
     * @return String
     */
    private static String generatorImport(FileConfig fileConfig, BeanConfig beanConfig) {
        StringBuilder ret = new StringBuilder();
        boolean needImportList = false;
        Set<String> alreadyExisted = new HashSet<>();
        for (DataConfig config : beanConfig.getFieldList()) {
            if (config.getType() == DataType.BEAN) {
                String importStr = getBeanImport(fileConfig, config);
                if (!alreadyExisted.contains(importStr)) {
                    ret.append(importStr);
                    alreadyExisted.add(importStr);
                }
            }
            if (config.isList()) {
                needImportList = true;
            }
        }

        if (needImportList) {
            ret.append("import java.util.List;");
            ret.append("\n");
            ret.append("import java.util.ArrayList;");
        }
        return ret.toString();
    }

    /**
     * 生成包名
     *
     * @param fileConfig fileConfig
     * @param beanConfig beanConfig
     * @return String
     */
    private static String generatorPackage(FileConfig fileConfig, BeanConfig beanConfig) {
        if (beanConfig instanceof MessageConfig) {
            return "package " + fileConfig.getPackagePath() + ".msg";
        } else {
            return "package " + fileConfig.getPackagePath() + ".msg.bean";
        }
    }

    /**
     * 生成write方法
     *
     * @param beanConfig beanConfig
     * @return String
     * @throws IOException IOException
     */
    private static String generatorWrite(BeanConfig beanConfig) throws IOException {
        StringBuilder ret = new StringBuilder();
        for (DataConfig config : beanConfig.getFieldList()) {
            if (!config.isList()) {
                ret.append(config.getType().write(config, false));
                ret.append("\n");
            } else {
                String readMethod = config.getType().write(config, true);
                String template = TemplateUtil.writeList();
                template = template.replaceAll("\\{fieldName}", config.getName());
                template = template.replaceAll("\\{writeMethod}", readMethod);
                ret.append(template);
                ret.append("\n");
            }
        }
        return ret.toString();
    }


    /**
     * 生成read方法
     *
     * @param beanConfig beanConfig
     * @return String
     * @throws IOException IOException
     */
    private static String generatorRead(BeanConfig beanConfig) throws IOException {
        StringBuilder ret = new StringBuilder();
        for (DataConfig config : beanConfig.getFieldList()) {
            if (!config.isList()) {
                ret.append(config.getType().read(config, false));
                ret.append("\n");
            } else {
                String readMethod = config.getType().read(config, true);
                String template = TemplateUtil.readList();
                template = template.replaceAll("\\{fieldName}", config.getName());
                template = template.replaceAll("\\{readMethod}", readMethod);
                ret.append(template);
                ret.append("\n");
            }
        }
        return ret.toString();
    }

    /**
     * 写入文件
     *
     * @param packagePath packagePath
     * @param fileName    fileName
     * @param content     content
     * @param output      output
     * @throws IOException IOException
     */
    private static void write(String packagePath, String fileName, String content, String output) throws IOException {
        String path = packagePath.replaceAll("\\.", "/");
        path = output + "/" + path;
        File dir = new File(path);
        if (!dir.exists()) {
            boolean res = dir.mkdirs();
            if (res) {
                System.out.println("创建文件夹" + dir);
            }
        }
        path += "/" + fileName + ".java";
        File file = new File(path);
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(content);
            out.flush();
        }
    }

    /**
     * 第一个单词首字母大写
     *
     * @param str str
     * @return static
     */
    private static String toFirstWorldUpper(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        String first = str.substring(0, 1).toUpperCase();
        if (str.length() > 1) {
            first += str.substring(1, str.length());
        }
        return first;
    }

    /**
     * 第一个单词首字母小写
     *
     * @param str str
     * @return String
     */
    public static String toFirstWorldLower(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        String first = str.substring(0, 1).toLowerCase();
        if (str.length() > 1) {
            first += str.substring(1, str.length());
        }
        return first;
    }

    /**
     * 生成消息类型
     *
     * @param config config
     * @param isList isList
     * @return String
     */
    private static String generatorFieldType(DataConfig config, boolean isList) {
        if (config.getType() != DataType.BEAN) {
            if (isList) {
                return config.getType().getReferenceType();
            } else {
                return config.getType().getPrimitiveType();
            }
        }
        return config.getBeanName();
    }

    /**
     * 生成bean的import
     *
     * @param fileConfig fileConfig
     * @param config     config
     * @return String
     */
    private static String getBeanImport(FileConfig fileConfig, DataConfig config) {
        boolean isInCurrentPackage = false;
        for (BeanConfig other : fileConfig.getBeanList()) {
            if (other.getClassName().equals(config.getBeanName())) {
                isInCurrentPackage = true;
            }
        }
        if (isInCurrentPackage) {
            return "import " + fileConfig.getPackagePath() + ".msg.bean." + config.getBeanName() + ";\n";
        }
        return "import " + config.getBeanName() + ";\n";
    }

}
