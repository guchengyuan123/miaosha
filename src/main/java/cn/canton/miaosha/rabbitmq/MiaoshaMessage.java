package cn.canton.miaosha.rabbitmq;

import cn.canton.miaosha.domain.MiaoshaUser;
import lombok.Data;

@Data
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;

}
