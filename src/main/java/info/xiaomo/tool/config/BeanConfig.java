package info.xiaomo.tool.config;

import java.util.ArrayList;
import java.util.List;

public class BeanConfig {

    /**
     * 生成的java文件名
     */
    private String className;

    /**
     * 描述
     */
    private String desc;

    /**
     * 单个属性列表
     */
    private List<DataConfig> fieldList = new ArrayList<>();

    /**
     * 数组属性列表（对应到java是list数据类型）
     */

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<DataConfig> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<DataConfig> fieldList) {
        this.fieldList = fieldList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
