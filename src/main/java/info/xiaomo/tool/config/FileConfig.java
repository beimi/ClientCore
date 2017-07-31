package info.xiaomo.tool.config;

import java.util.ArrayList;
import java.util.List;

public class FileConfig {

    /**
     * 组
     */
    private int group;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 队列id
     */
    private int queueId;


    private List<BeanConfig> beanList = new ArrayList<BeanConfig>();
    private List<MessageConfig> messageList = new ArrayList<MessageConfig>();


    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public List<BeanConfig> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<BeanConfig> beanList) {
        this.beanList = beanList;
    }

    public List<MessageConfig> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageConfig> messageList) {
        this.messageList = messageList;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }


}
