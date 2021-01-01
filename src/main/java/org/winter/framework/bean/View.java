package org.winter.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wang
 * @Description 视图
 * @Date 2020/12/31
 */
public class View {

    private String path;

    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
    }

    public View addModel(String key, Object value) {
        if (model == null) model = new HashMap<>();
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
