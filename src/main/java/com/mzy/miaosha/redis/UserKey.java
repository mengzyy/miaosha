package com.mzy.miaosha.redis;

/**
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-19 19:31
 **/
public class UserKey extends BasePrefix {
    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey(30, "id");
    public static UserKey getByName = new UserKey(30, "name");


}
