package org.winter.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.winter.framework.utils.ReflectionUtil;

/**
 * @author wang
 * @Description Bean 辅助类
 * @Date 2020/12/31
 */
public final class BeanHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    // Bean 映射 类 => 实例
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        // 所有的 Controller 和 Service
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            LOGGER.info("initial bean [" + beanClass.getSimpleName() + "]");
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取 Bean 映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     */
    public static <T> T getBean(Class<T> clz) {
        if (!BEAN_MAP.containsKey(clz)) {
            throw new RuntimeException("can not get bean by class: "  + clz);
        }
        return (T) BEAN_MAP.get(clz);
    }

    /**
     * 设置Bean实例
     */
    public static void setBean(Class<?> clz, Object object) {
        BEAN_MAP.put(clz, object);
    }
}
