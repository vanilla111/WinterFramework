package org.winter.framework.bean;

import java.util.Map;

import org.winter.framework.utils.CastUtil;
import org.winter.framework.utils.CollectionUtil;

/**
 * @author wang
 * @Description 参数
 * @Date 2020/12/31
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }

    public long getLong(String paramName) {
        return CastUtil.castLong(paramMap.get(paramName));
    }

    public boolean isEmpty() {
        return CollectionUtil.isEmpty(this.paramMap);
    }
}
