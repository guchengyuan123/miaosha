package cn.canton.miaosha.redis;

public class MiaoshaKey extends BasePrefix{

	private MiaoshaKey(String prefix) {
		super(prefix);
	}
	public static MiaoshaKey isGoodsOver = new MiaoshaKey("goodsOver");
}
