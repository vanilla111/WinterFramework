package org.winter.learning.controller;

import java.util.HashMap;
import java.util.Map;

import org.winter.framework.annotation.Action;
import org.winter.framework.annotation.Controller;
import org.winter.framework.annotation.Inject;
import org.winter.framework.bean.Data;
import org.winter.framework.bean.Param;
import org.winter.framework.utils.CastUtil;
import org.winter.learning.entity.Student;
import org.winter.learning.service.StudentService;

/**
 * @author wang
 * @Description
 * @Date 2021/1/1
 */
@Controller("/student")
public class StudentController {

    @Inject
    private StudentService service;

    @Action(method = "POST")
    public Data save(Param param) {
        Map<String, Object> map = param.getFieldMap();
//        String stuName = CastUtil.castString(map.get("name"));
//        String stuId = CastUtil.castString(map.get("stuId"));
//        Student student = new Student(stuName, stuId);

        service.insertOneStudent(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "200");
        resultMap.put("msg", "success");
        return new Data(resultMap);
    }

    @Action
    public Data findOneStudent(Param param) {
        int id = CastUtil.castInt(param.getFieldMap().get("id"));
        Student student = service.findStuById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "200");
        resultMap.put("msg", "success");
        resultMap.put("data", student);
        return new Data(resultMap);
    }
}
