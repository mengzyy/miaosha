package com.mzy.miaosha.exception;

import com.mzy.miaosha.result.CodeMsg;

/**
 * 全局的异常处理器
 *
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-25 11:55
 **/
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }


}
