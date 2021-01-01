package org.winter.learning.aspect;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.winter.framework.Proxy.AspectProxy;
import org.winter.framework.annotation.Aspect;
import org.winter.framework.annotation.Controller;

/**
 * @author wang
 * @Description
 * @Date 2020/12/31
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    // 测试注解工作情况
    // 改切面用来统计一个请求的处理时间
    private long begin;

    @Override
    public void before(Class<?> clz, Method method, Object[] params) throws Throwable {
        LOGGER.debug("--------begin---------");
        LOGGER.debug("class: " + clz.getName());
        LOGGER.debug("method: " + method.getName());
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> clz, Method method, Object[] params) throws Throwable {
        LOGGER.debug("use time: " + (System.currentTimeMillis() - begin) + " ms");
        LOGGER.debug("---------end----------");
    }

}
