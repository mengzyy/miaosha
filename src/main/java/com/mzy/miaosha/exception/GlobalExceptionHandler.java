package com.mzy.miaosha.exception;

import com.mzy.miaosha.result.CodeMsg;
import com.mzy.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-25 11:56
 **/

/*
 *@ControllerAdvice :该注解可以处理全局的异常信息 注意只有在Controller层中失生效
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /*该行代表该类处理全部的Exception信息*/
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCm());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /*该行代表该类处预加载属性*/
    @ModelAttribute(name = "md")
    public Map<String, Object> mydata() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "mengzy");
        return map;
    }
}
