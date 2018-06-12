package cn.canton.miaosha.vo;


import cn.canton.miaosha.domain.OrderInfo;
import lombok.Data;

@Data
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
