package com.mzy.miaosha.controller;


import com.mzy.miaosha.result.Result;
import com.mzy.miaosha.service.MiaoshaUserService;
import com.mzy.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 控制用户登录的Controller层
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MiaoshaUserService userService;

    /* slf4j常量*/
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * @return 返回初始化登录页面
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录逻辑主体
     *
     * @param loginVo 用户登录表单信息
     * @return 返回页面信息
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response,@Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        //登录
        userService.login(response, loginVo);
        return Result.success("success");
    }


}
