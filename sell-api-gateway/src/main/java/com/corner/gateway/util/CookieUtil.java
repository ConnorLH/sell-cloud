package com.corner.gateway.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request,String token){
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if(StringUtils.equals(cookies[i].getName(),token)){
                return cookies[i];
            }
        }
        return null;
    }
}
