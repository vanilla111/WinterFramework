package org.winter.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.winter.framework.annotation.Action;
import org.winter.framework.annotation.Controller;
import org.winter.framework.bean.Handler;
import org.winter.framework.bean.Request;
import org.winter.framework.utils.ArrayUtil;
import org.winter.framework.utils.CollectionUtil;

/**
 * @author wang
 * @Description
 * @Date 2020/12/31
 */
public final class ControllerHelper {

    // 请求 => 处理器
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerSet)) {
            for (Class<?> controllerClass : controllerSet) {
                // todo 添加功能：请求路径 = 基础路径（由Controller注解设置） + 额外路径（由Action注解设置）
                String baseRequestPath = controllerClass.getAnnotation(Controller.class).value();
                Method[] methods = controllerClass.getMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String requestMethod = action.method().toLowerCase();
                            String requestPath = baseRequestPath + action.value();
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);
                            ACTION_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod.toLowerCase(), requestPath);
        return ACTION_MAP.get(request);
    }
}
