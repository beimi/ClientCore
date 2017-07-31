package info.xiaomo.tool.config;

/**
 * 数据配置
 *
 * @author 小莫
 * 2017-1-12 上午10:41:13
 */
public class DataConfig {

    /**
     * 数据类型
     */
    private DataType type;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 是否使用优化写入
     * 该字段只有在type等于int和long时有效 可选值：true，false
     */
    private boolean optimized = true;

    /**
     * 是否使用正数优化
     * 该字段只有在 type等于int和long并且optimized等于true时有效，可选值有：true，false
     */
    private boolean positive = false;

    /**
     * bean的名称，如果该数据类型是bean的话
     */
    private String beanName;

    /**
     * 是否是数组
     */
    private boolean list;

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isOptimized() {
        return optimized;
    }

    public void setOptimized(boolean optimized) {
        this.optimized = optimized;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }


}
