package com.zlz.blog.oauth.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/10/24 18:04
 */
@Controller
@SessionAttributes({ "authorizationRequest" })
public class BaseMainController {

    @Value("${gateway.server.url}")
    private String gatewayServerUrl;

    @GetMapping("/auth/login")
    public String loginPage(Model model, HttpServletRequest request){
        if(request.getQueryString() == null){
            model.addAttribute("error", "null");
        }else{
            model.addAttribute("error", "error");
        }
        String formAction = gatewayServerUrl.concat("oauth/form");
        model.addAttribute("formAction",formAction);
        return "login";
    }

    @RequestMapping({ "/oauth/approvale/confirm" })
    public String getAccessConfirmation(Map<String, Object> map,
                                        HttpServletRequest request, Model model) throws Exception {
        String scope = (String) (map.containsKey("scope") ?
                map.get("scope") : request.getAttribute("scope"));
        String client = (String) (map.containsKey("client_id") ?
                map.get("client_id") : request.getAttribute("client_id"));
        model.addAttribute("scope",scope).addAttribute("client", client);
        System.out.println("授权进行中，client："+client);
        return "authorization";
    }
    @RequestMapping({ "/oauth/approvale/error" })
    public String handleError(Map<String, Object> model, HttpServletRequest request) {
        Object error = request.getAttribute("error");
        String errorSummary;
        if (error instanceof OAuth2Exception) {
            OAuth2Exception oauthError = (OAuth2Exception) error;
            errorSummary = HtmlUtils.htmlEscape(oauthError.getSummary());
        } else {
            errorSummary = "Unknown error";
        }
        model.put("errorSummary", errorSummary);
        return "oauth_error";
    }

    /**
     * 退出登录清除session
     * @param request
     * @return
     */
    @RequestMapping("/token/logout")
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //清空session;
        request.getSession().invalidate();
        response.sendRedirect("http://localhost:4200");
    }
}
