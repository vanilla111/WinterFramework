package org.winter.framework;

/**
 * @author wang
 * @Date 2020/12/31
 */
public interface ConfigConstant {
    String CONFIG_FILE = "winter.properties";

    String JDBC_DRIVER = "winter.framework.jdbc.driver";
    String JDBC_URL = "winter.framework.jdbc.url";
    String JDBC_USERNAME = "winter.framework.jdbc.username";
    String JDBC_PASSWORD= "winter.framework.jdbc.password";

    String APP_BASE_PACKAGE = "winter.framework.app.base_package";
    String APP_JSP_PATH = "winter.framework.app.jsp_path";
    String APP_ASSET_PATH = "winter.framework.app.asset_path";

    String APP_UPLOAD_LIMIT = "winter.framework.app.upload_limit";
}
