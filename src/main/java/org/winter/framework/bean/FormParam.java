package org.winter.framework.bean;

/**
 * @author wang
 * @Description
 * @Date 2021/1/1
 */
public class FormParam {

    private String fieldName;

    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
