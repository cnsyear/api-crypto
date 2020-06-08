package com.cnsyear.apicrypto.filter;

import com.cnsyear.apicrypto.util.DesedeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 数据加解密过滤器
 * @Author jie.zhao
 * @Date 2020/6/8 15:54
 */
@Slf4j
@Component
public class EncryptionFilter implements Filter {
    
    /**
     * AES加密Key
     */
    private String key="kCXQjT6gM6O2LZtFJdiloTHz";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        log.debug("RequestURI: {}", uri);
        EncryptionResponseWrapper responseWrapper = new EncryptionResponseWrapper(resp);
        EncryptionReqestWrapper requestWrapper = new EncryptionReqestWrapper(req);
        //请求解密
        processDecryption(requestWrapper, req);
        //执行后续操作
        chain.doFilter(request, response);
        //响应加密
        String responseData = responseWrapper.getResponseData();
        writeEncryptContent(responseData, response);
    }


    @Override
    public void destroy() {

    }


    /**
     * 请求解密处理
     *
     * @param requestWrapper
     * @param req
     */
    private void processDecryption(EncryptionReqestWrapper requestWrapper, HttpServletRequest req) {
        String requestData = requestWrapper.getRequestData();
        log.debug("RequestData: {}", requestData);
        try {
            if (!StringUtils.endsWithIgnoreCase(req.getMethod(), RequestMethod.GET.name())) {
                String decryptRequestData = DesedeUtil.decryptStr(key, requestData);
                log.debug("DecryptRequestData: {}", decryptRequestData);
                requestWrapper.setRequestData(decryptRequestData);
            }

            // url参数解密
            Map<String, String> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                String decryptParamValue = DesedeUtil.decryptStr(key, paramValue);
                paramMap.put(paramName, decryptParamValue);
            }
            requestWrapper.setParamMap(paramMap);
        } catch (Exception e) {
            log.error("请求数据解密失败： {}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 输出加密内容
     *
     * @param responseData
     * @param response
     * @throws IOException
     */
    private void writeEncryptContent(String responseData, ServletResponse response) throws IOException {
        log.debug("ResponseData: {}", responseData);
        ServletOutputStream out = null;
        try {
            responseData = "";//EncryptionAesUtil.encryptHex(key, responseData);;
            log.debug("EncryptResponseData: {}", responseData);
            response.setContentLength(responseData.length());
            response.setCharacterEncoding("UTF-8");
            out = response.getOutputStream();
            out.write(responseData.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("响应数据加密失败：{}", e);
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}