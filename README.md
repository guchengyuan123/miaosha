# miaosha
Java秒杀系统方案优化 高性能高并发实战

框架：SpringBoot

持久化：Redis, mysql

中间件：RabbitMQ

如何设计一个秒杀系统

1、制作好登陆页面，商品列表页面以及倒计时秒杀页面。

2、采用Redis做分布式session，将sessionId存入redis。

3、页面静态化存入redis，前后端分离。

4、并发对服务器的压力，其实是针对mysql的压力，将商品信息缓存到redis中

5、将秒杀到到User存入rabbitmq。

6、如何防止超卖现象

    有两种情况可能会导致卖超：

    （1）一个用户同时发出了多个请求，如果库存足够，没加限制，用户就可以下多个订单。

    （2）减库存的sql上没有加库存数量的判断，并发的时候也会导致把库存减成负数。

    我们的解决办法：

    对于（1）：前端加验证码，防止用户同时发出多个请求，在后端的miaosha_order表中，对user_id和goods_id加唯一索引，
    
    确保一个用户对一个商品绝对不会生成两个订单。

    对于（2）：我们的减库存的sql上应该加上库存数量的判断：
    
    update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0
