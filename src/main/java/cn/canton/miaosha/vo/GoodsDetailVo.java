package cn.canton.miaosha.vo;

import cn.canton.miaosha.domain.MiaoshaUser;
import lombok.Data;

@Data
public class GoodsDetailVo {
    private int miaoshaStatus;
    private int remainSeconds;
    private GoodsVo goods;
    MiaoshaUser user;

}
