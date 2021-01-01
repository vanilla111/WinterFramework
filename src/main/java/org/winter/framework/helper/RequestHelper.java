package org.winter.framework.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.winter.framework.bean.FormParam;
import org.winter.framework.bean.Param;
import org.winter.framework.utils.ArrayUtil;
import org.winter.framework.utils.CodecUtil;
import org.winter.framework.utils.StreamUtil;
import org.winter.framework.utils.StringUtil;

/**
 * @author wang
 * @Description
 * @Date 2021/1/1
 */
public final class RequestHelper {

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if (ArrayUtil.isNotEmpty(fieldValues)) {
                Object fieldValue;
                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < fieldValues.length; i++){
                        sb.append(fieldValues[i]);
                        if (i != fieldValues.length - 1){
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(fieldName, fieldValue));
            }

        }
        return formParamList;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest req) throws IOException{
        List<FormParam> formParamList = new ArrayList<>();
        // 从 请求流中 获取请求参数，例如POST、PUT等
        String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
        if (StringUtil.isNotEmpty(body)) {
            String[] params = StringUtil.splitString(body, "&");
            if (ArrayUtil.isNotEmpty(params)) {
                for (String param : params) {
                    String[] array = StringUtil.splitString(param, "=");
                    if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                        String paramName = array[0];
                        String paramValue = array[1];
                        formParamList.add(new FormParam(paramName, paramValue));
                    }
                }
            }
        }
        return formParamList;
    }
}
