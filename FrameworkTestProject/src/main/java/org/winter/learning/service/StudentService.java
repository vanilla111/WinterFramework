package org.winter.learning.service;

import java.util.Map;

import org.winter.framework.annotation.Service;
import org.winter.framework.annotation.Transaction;
import org.winter.framework.helper.DatabaseHelper;
import org.winter.learning.entity.Student;

/**
 * @author wang
 * @Description
 * @Date 2021/1/1
 */
@Service
public class StudentService {

    @Transaction
    public void insertOneStudent(Map<String, Object> params) {
        DatabaseHelper.insertEntity(Student.class, params);
    }

    public Student findStuById(int id) {
        Student student = DatabaseHelper.queryEntity(Student.class, "select * from student where id=?", id);
        return student;
    }
}
