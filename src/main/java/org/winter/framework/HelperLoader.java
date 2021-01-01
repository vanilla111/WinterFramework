package org.winter.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.winter.framework.helper.AopHelper;
import org.winter.framework.helper.BeanHelper;
import org.winter.framework.helper.ClassHelper;
import org.winter.framework.helper.ControllerHelper;
import org.winter.framework.helper.DatabaseHelper;
import org.winter.framework.helper.IocHelper;
import org.winter.framework.utils.ClassUtil;

/**
 * @author wang
 * @Description 框架初始化类
 * @Date 2020/12/31
 */
public final class HelperLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperLoader.class);

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class,
                DatabaseHelper.class
        };
        for (Class<?> clz : classList) {
            LOGGER.info("Load helper class: " + clz.getName());
            ClassUtil.loadClass(clz.getName(), true);
        }
    }

    public static void main(String[] args) {
        init();
    }
}
