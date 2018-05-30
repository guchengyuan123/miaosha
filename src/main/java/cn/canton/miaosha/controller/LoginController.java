package cn.canton.miaosha.controller;


import cn.canton.miaosha.redis.RedisService;
import cn.canton.miaosha.result.Result;
import cn.canton.miaosha.service.MiaoshaUserService;
import cn.canton.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo){
        log.info(loginVo.toString());
        //登陆
        miaoshaUserService.login(loginVo);
        return Result.success(true);
    }
}
