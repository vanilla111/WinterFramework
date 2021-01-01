package org.winter.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.winter.framework.annotation.Inject;
import org.winter.framework.utils.ArrayUtil;
import org.winter.framework.utils.CollectionUtil;
import org.winter.framework.utils.ReflectionUtil;

/**
 * @author wang
 * @Description 依赖注入辅助类
 * @Date 2020/12/31
 */
public final class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                LOGGER.debug("scan " + beanClass.getSimpleName() + "'s fields[length=" + beanFields.length + "]");
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        LOGGER.debug("beanFields: " + beanField.getType().getSimpleName());
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                LOGGER.debug("inject [" + beanFieldClass.getSimpleName() + "] for [" + beanClass.getSimpleName() +"] instance");
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
