package info.xiaomo.tool.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class TemplateUtil {
    private static final String base = "F:\\ChessGame\\MessageGenerator\\src\\main\\resources\\";


    /**
     * bean模板
     *
     * @return String
     * @throws IOException IOException
     */
    static String beanTemplate() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\bean.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }


    /**
     * 消息模板
     *
     * @return String
     * @throws IOException IOException
     */
    static String messageTemplate() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\message.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }

    /**
     * filed模板
     *
     * @return String
     * @throws IOException IOException
     */
    static String fieldTemplate() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\field.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }

    /**
     * get/set模板
     *
     * @return String
     * @throws IOException IOException
     */
    static String getterAndSetterTemplate() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\getterAndSetter.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }


    /**
     * 列表模板
     *
     * @return String
     * @throws IOException IOException
     */
    static String listFieldTemplate() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\listField.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }

    /**
     * 列表的get/set
     *
     * @return String
     * @throws IOException IOException
     */
    static String listGetterAndSetter() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\listGetterAndSetter.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }

    /**
     * 读list模板
     *
     * @return String
     * @throws IOException IOException
     */
    static String readList() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\readList.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }

    /**
     * 写list模板
     *
     * @return String
     * @throws IOException IOException
     */
    static String writeList() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(base + "template\\writeList.template")))) {
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            return builder.toString();
        }
    }


}
