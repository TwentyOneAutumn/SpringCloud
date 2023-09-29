//package com.security.config;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.util.StrUtil;
//import com.core.doMain.AjaxResult;
//import com.core.doMain.basic.SysMenu;
//import com.core.doMain.basic.SysModule;
//import com.core.enums.FeignRequestHeater;
//import com.core.utils.RequestUtils;
//import com.core.utils.StreamUtils;
//import com.security.enums.PermitUrl;
//import com.security.utils.SecurityUtils;
//import org.springframework.stereotype.Component;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Set;
//import java.util.regex.Pattern;
//
///**
// * 身份权限校验过滤器
// */
//@Component
//public class AuthRoleFilter implements Filter {
//
//    /**
//     * 过滤方法
//     * @param servletRequest 请求对象
//     * @param servletResponse 响应对象
//     * @param filterChain 过滤器链对象
//     * @throws IOException IO异常
//     * @throws ServletException Servlet异常
//     */
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest)servletRequest;
//        HttpServletResponse response = (HttpServletResponse)servletResponse;
//        String requestUrl = RequestUtils.getRequestUri(request);
//        if(isFeignRequest(request) || isPermit(requestUrl) || isAuthRole(requestUrl)){
//            filterChain.doFilter(servletRequest,servletResponse);
//        }else {
//            isError(response);
//        }
//    }
//
//    /**
//     * 判断是否为内部服务调用
//     * @param request 请求对象
//     * @return true:是 false:否
//     */
//    public boolean isFeignRequest(HttpServletRequest request){
//        String header = RequestUtils.getHeader(request,FeignRequestHeater.HeaterName);
//        return StrUtil.isNotEmpty(header);
//    }
//
//    /**
//     * 判断是否放行
//     * @param requestUrl 请求路径
//     * @return true:是 false:否
//     */
//    public boolean isPermit(String requestUrl){
//        return StreamUtils.match(Arrays.asList(PermitUrl.UrlArr),url -> Pattern.compile(url.replaceAll("/\\*\\*","/*")).matcher(requestUrl).find());
//    }
//
//    /**
//     * 判断是否有权限访问目标接口
//     * @param requestUrl 请求路径
//     * @return true:是 false:否
//     */
//    public boolean isAuthRole(String requestUrl){
//        Set<SysModule> moduleSet = SecurityUtils.getUser().getModuleSet();
//        if(CollUtil.isNotEmpty(moduleSet)){
//            return StreamUtils.match(StreamUtils.mapToSet(moduleSet, SysModule::getModuleUrl),url -> Pattern.compile(url).matcher(requestUrl).find());
//        }else {
//            return false;
//        }
//    }
//
//    /**
//     * 返回错误信息
//     */
//    public void isError(HttpServletResponse response) throws IOException {
//        RequestUtils.writer(response,AjaxResult.error("当前用户权限不足，拒绝访问"));
//    }
//}
