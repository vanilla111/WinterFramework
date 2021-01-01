package org.winter.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.winter.framework.bean.Data;
import org.winter.framework.bean.Handler;
import org.winter.framework.bean.Param;
import org.winter.framework.bean.View;
import org.winter.framework.helper.BeanHelper;
import org.winter.framework.helper.ConfigHelper;
import org.winter.framework.helper.ControllerHelper;
import org.winter.framework.helper.RequestHelper;
import org.winter.framework.helper.ServletHelper;
import org.winter.framework.helper.UploadHelper;
import org.winter.framework.utils.CollectionUtil;
import org.winter.framework.utils.JsonUtil;
import org.winter.framework.utils.ReflectionUtil;
import org.winter.framework.utils.StringUtil;

/**
 * @author wang
 * @Description
 * @Date 2020/12/31
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (requestPath.equals("/favicon.ico")) {
            return ;
        }

        ServletHelper.init(req, resp);
        try {
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }

                // 执行
                Object result;
                Method actionMethod = handler.getActionMethod();
                Class<?>[] parameterTypes = actionMethod.getParameterTypes();
                if (parameterTypes.length == 0) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    if (parameterTypes.length > 1 || !parameterTypes[0].equals(Param.class)) {
                        throw new ServletException("Unsupported controller method: "
                                + controllerClass.getName() + "#" + actionMethod.getName());
                    }
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }

                if (result instanceof View) {
                    handleViewResult((View) result, req, resp);
                } else if (result instanceof Data) {
                    handleDataResult((Data) result, resp);
                }
            }
        } finally {
            ServletHelper.destroy();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = config.getServletContext();
        // 注册处理JSP文件的 Servlet
        ServletRegistration jspRegistration = servletContext.getServletRegistration("jsp");
        jspRegistration.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源默认的 Servlet
        ServletRegistration defaultRegistration = servletContext.getServletRegistration("default");
        defaultRegistration.addMapping(ConfigHelper.getAppAssetPath() + "*");

        UploadHelper.init(servletContext);
    }

    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                // 重定向页面
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                if (CollectionUtil.isNotEmpty(model)) {
                    for (Entry<String, Object> entry : model.entrySet()) {
                        // 设置模版所需要的数据
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
                // forward 请求转发，由下一个servlet完成响应体
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletResponse resp) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
