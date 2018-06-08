package cn.canton.miaosha.controller;


import cn.canton.miaosha.domain.MiaoshaOrder;
import cn.canton.miaosha.domain.MiaoshaUser;
import cn.canton.miaosha.domain.OrderInfo;
import cn.canton.miaosha.redis.RedisService;
import cn.canton.miaosha.result.CodeMsg;
import cn.canton.miaosha.service.GoodsService;
import cn.canton.miaosha.service.MiaoshaService;
import cn.canton.miaosha.service.MiaoshaUserService;
import cn.canton.miaosha.service.OrderService;
import cn.canton.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;

    /*

     */
    @RequestMapping("/do_miaosha")
    public String list(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",user);
        if (user == null){
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock < 0){
            model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAO_SHA.getMsg());
            return "miaosha_fail";
        }
        //减小库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }

}
