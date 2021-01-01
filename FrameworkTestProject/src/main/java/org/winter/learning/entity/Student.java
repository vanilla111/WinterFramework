package org.winter.learning.entity;

import org.winter.framework.annotation.Table;

/**
 * @author wang
 * @Description
 * @Date 2021/1/1
 */
@Table("student")
public class Student {

    private int id;

    private String name;

    private String stuId;

    public Student() {
    }

    public Student(String name, String stuId) {
        this.name = name;
        this.stuId = stuId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
