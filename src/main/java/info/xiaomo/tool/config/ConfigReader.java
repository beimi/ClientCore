package info.xiaomo.tool.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

public class ConfigReader {

    private static final String MESSAGE = "message";
    private static final String LIST = "list";
    private static final String CLAZZ = "class";
    private static final String TYPE = "type";
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String QUEUE_ID = "queueId";
    private static final String PACKAGE = "package";
    private static final String OPTIMIZED = "optimized";
    private static final String POSITIVE = "positive";
    private static final String DESC = "desc";
    private static final String BEAN = "bean";


    /**
     * 读配置
     *
     * @param fileName fileName
     * @return FileConfig
     * @throws FileNotFoundException FileNotFoundException
     * @throws DocumentException     DocumentException
     */
    @SuppressWarnings("unchecked")
    public static FileConfig read(String fileName) throws FileNotFoundException, DocumentException {
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = new FileInputStream(fileName);
        Document document = saxReader.read(inputStream);
        Element root = document.getRootElement();

        final int group = Integer.parseInt(root.attributeValue(ID));
        final String packagePath = root.attributeValue(PACKAGE);
        String queueIdStr = root.attributeValue(QUEUE_ID);
        int queueId = 0;
        if (queueIdStr != null && !queueIdStr.isEmpty()) {
            queueId = Integer.parseInt(queueIdStr);
        }
        FileConfig ret = new FileConfig();
        ret.setGroup(group);
        ret.setQueueId(queueId);
        ret.setPackagePath(packagePath);

        Iterator<Element> messageIt = root.elementIterator(MESSAGE);
        while (messageIt.hasNext()) {
            Element element = messageIt.next();
            MessageConfig message = readMessage(element);
            ret.getMessageList().add(message);
        }

        Iterator<Element> beanIt = root.elementIterator(BEAN);
        while (beanIt.hasNext()) {
            Element element = beanIt.next();
            BeanConfig bean = readBean(element);
            ret.getBeanList().add(bean);
        }
        return ret;
    }


    /**
     * 读一个消息
     *
     * @param element element
     * @return MessageConfig
     */
    @SuppressWarnings("unchecked")
    private static MessageConfig readMessage(Element element) {
        MessageConfig config = new MessageConfig();
        config.setId(Integer.parseInt(element.attributeValue(ID)));
        String queueIdStr = element.attributeValue(QUEUE_ID);
        int queueId = 0;
        if (queueIdStr != null && !queueIdStr.isEmpty()) {
            queueId = Integer.parseInt(queueIdStr);
        }
        config.setQueueId(queueId);
        config.setClassName(element.attributeValue(CLAZZ));
        config.setDir(MessageDirection.parse(element.attributeValue(TYPE)));
        config.setDesc(element.attributeValue(DESC));
        Iterator<Element> it = element.elementIterator();
        while (it.hasNext()) {
            Element fieldElement = it.next();
            DataConfig dataConfig = readData(fieldElement);
            config.getFieldList().add(dataConfig);
        }
        return config;

    }


    /**
     * 读一个bean
     *
     * @param element element
     * @return BeanConfig
     */
    @SuppressWarnings("unchecked")
    private static BeanConfig readBean(Element element) {
        BeanConfig config = new BeanConfig();
        config.setClassName(element.attributeValue(CLAZZ));
        config.setDesc(element.attributeValue(DESC));
        Iterator<Element> it = element.elementIterator();
        while (it.hasNext()) {
            Element fieldElement = it.next();
            DataConfig dataConfig = readData(fieldElement);
            config.getFieldList().add(dataConfig);
        }
        return config;

    }


    /**
     * 读取一个element
     *
     * @param element element
     * @return DataConfig
     */
    private static DataConfig readData(Element element) {

        DataConfig config = new DataConfig();

        boolean isList = element.getName().equals(LIST);
        config.setList(isList);
        config.setType(DataType.parse(element.attributeValue(TYPE)));

        if (config.getType() == DataType.BEAN) {
            config.setBeanName(element.attributeValue(TYPE));
        }

        config.setDesc(element.attributeValue(DESC));
        config.setName(element.attributeValue(NAME));

        String optimized = element.attributeValue(OPTIMIZED);
        if (optimized != null && optimized.trim().length() > 0) {
            config.setOptimized(Boolean.parseBoolean(optimized));
        } else {
            if (config.getType() == DataType.LONG) {
                config.setOptimized(false);
            } else {
                config.setOptimized(true);
            }
        }


        if (config.isOptimized()) {
            String positive = element.attributeValue(POSITIVE);
            if (positive != null && positive.trim().length() > 0) {
                config.setPositive(Boolean.parseBoolean(positive));
            }
        }
        return config;
    }
}
