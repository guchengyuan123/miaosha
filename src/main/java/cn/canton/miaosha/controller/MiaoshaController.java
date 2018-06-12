package cn.canton.miaosha.controller;


import cn.canton.miaosha.domain.MiaoshaOrder;
import cn.canton.miaosha.domain.MiaoshaUser;
import cn.canton.miaosha.domain.OrderInfo;
import cn.canton.miaosha.redis.RedisService;
import cn.canton.miaosha.result.CodeMsg;
import cn.canton.miaosha.result.Result;
import cn.canton.miaosha.service.GoodsService;
import cn.canton.miaosha.service.MiaoshaService;
import cn.canton.miaosha.service.MiaoshaUserService;
import cn.canton.miaosha.service.OrderService;
import cn.canton.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        1354.1/sec  linux
     */
    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",user);
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock < 0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null){
            return Result.error(CodeMsg.REPEATE_MIAO_SHA);
        }
        //减小库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        return Result.success(orderInfo);
    }

}
