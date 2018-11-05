package com.corner.user.server.controller;

import com.corner.user.server.constant.CookieConstant;
import com.corner.user.server.dataobject.UserInfo;
import com.corner.user.server.enums.ResultEnum;
import com.corner.user.server.enums.RoleEnum;
import com.corner.user.server.service.UserService;
import com.corner.user.server.utils.CookieUtil;
import com.corner.user.server.utils.ResultVOUtil;
import com.corner.user.server.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {
        //1. openid和数据库里的数据匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2. 判断角色
        if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3. cookie里设置openid=abc
        CookieUtil.set(response, CookieConstant.OPENID,openid,CookieConstant.EXPIRE);
        return ResultVOUtil.success();
    }

    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid, HttpServletResponse response) {
        return null;
    }
}
