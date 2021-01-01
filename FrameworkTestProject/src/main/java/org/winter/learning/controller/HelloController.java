package org.winter.learning.controller;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.winter.framework.annotation.Action;
import org.winter.framework.annotation.Controller;
import org.winter.framework.bean.Data;
import org.winter.framework.bean.Param;
import org.winter.framework.bean.View;

/**
 * @author wang
 * @Description
 * @Date 2020/12/31
 */
@Controller("/hello")
public class HelloController {

    @Action("/say")
    public Data sayHello() {
        return new Data("Hello");
    }

    @Action("/")
    public View index() {
        View view = new View("hello.jsp");
        view.addModel("currentTime", new Date().toString());
        return view;
    }

    @Action("/param")
    public Data param(Param param) {
        Map<String, Object> map = param.getMap();
        for (Entry<String, Object> en : map.entrySet()) {
            System.out.println(en.getKey() + ": " + en.getValue());
        }
        return new Data(map);
    }
}
