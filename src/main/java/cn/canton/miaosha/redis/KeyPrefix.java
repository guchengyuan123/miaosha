package cn.canton.miaosha.redis;

public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
