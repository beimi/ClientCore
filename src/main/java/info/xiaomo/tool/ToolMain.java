package info.xiaomo.tool;

import info.xiaomo.tool.config.ConfigReader;
import info.xiaomo.tool.config.FileConfig;
import info.xiaomo.tool.util.FileGenerator;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * いま 最高の表現 として 明日最新の始発．．～
 * Today the best performance  as tomorrow newest starter!
 * Created by IntelliJ IDEA.
 * <p>
 * author: xiaomo
 * github: https://github.com/xiaomoinfo
 * email : xiaomo@xiaomo.info
 * QQ    : 83387856
 * Date  : 2017/7/11 13:17
 * desc  : 消息生成工具
 * Copyright(©) 2017 by xiaomo.
 */
public class ToolMain {
    public static void main(String[] args) throws IOException, DocumentException {
        InputStream in = null;
        try {
            in = new FileInputStream(args[0]);
            Properties properties = new Properties();
            properties.load(in);
            String input = (String) properties.get("input");
            String output = (String) properties.get("output");
            int type = Integer.parseInt(properties.getProperty("type"));


            File file = new File(input);
            if (file.isDirectory()) {
                String[] fileList = file.list((dir, name) -> name.endsWith(".xml"));
                if (fileList == null) {
                    return;
                }
                for (String fileName : fileList) {
                    System.out.println("处理：" + fileName + "...");
                    FileConfig ret = ConfigReader.read(file.getAbsolutePath() + "/" + fileName);
                    FileGenerator.generator(ret, output, type);
                }
            } else {
                FileConfig ret = ConfigReader.read(input);
                FileGenerator.generator(ret, output, type);
            }
            System.out.println("生成完毕.");
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}

