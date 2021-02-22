package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-04 19:04
 * To change this template use File | Settings | File Templates.
 */
public enum GoodsServiceTypeEnum implements GoodsServiceType,Serializable {
	CONTRACT_OFFER,//合约关联套餐
	TERMINAL_PLAN,//终端关联购买计划
	TERMINAL,// 终端服务类型值
    OFFER,// 套餐服务类型值
    CONTRACT, //合约机服务类型值
    PAYWAY,//支付方式
    BUYWAY,//购买方式
    PROPERTY,//业务购买类型
	MODULE;//模板
    

}
