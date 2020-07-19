package com.mzy.miaosha.redis;

/**
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-19 19:28
 **/
public abstract class BasePrefix implements KeyPrefix {
    int expireSeconds;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this.prefix = prefix;
    }

    String prefix;


    @Override
    public int expireSeconds() {
        return 0;
    }

    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();

        return simpleName + ":" + this.prefix;
    }
}
