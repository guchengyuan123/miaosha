package cn.canton.miaosha.redis;

public class OrderKey extends BasePrefix{

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
