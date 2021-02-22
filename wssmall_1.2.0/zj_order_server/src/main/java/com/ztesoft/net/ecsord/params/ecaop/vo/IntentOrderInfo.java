package com.ztesoft.net.ecsord.params.ecaop.vo;
public class IntentOrderInfo {

	private String intent_order_id;
	private String intent_order_time;
	private String status;
	private String remark;
	private String order_id;
	private String ship_name;
	private String ship_tel;
	private String ship_addr;
	private String referee_num; 
	private String referee_name;
	private String deal_user;
	private String deal_user_name;
	private String deal_user_phone;
	private OrderInfo order_info;
	private String intent_finish_time;
	private PhoneInfo phone_info;
	private GoodsInfo goods_info;
	private CustInfo cust_info ;
	private DeveloperInfo developer_info;
	private String seed_user_id ;
	private String market_user_id;
	private String share_svc_num;
	private String is_no_modify;
	private String is_online_order;//add by cqq 20181226 是否线上线下订单
	private String top_share_userid;
	private String top_share_num;
	private String offer_eff_type;
	private String pay_tag;
	
	
	
	public IntentOrderInfo() {
		
	}
	
	public IntentOrderInfo(Builder builder) {
		this.intent_order_id = builder.intent_order_id;
		this.intent_order_time = builder.intent_order_time;
		this.status = builder.status;
		this.remark = builder.remark;
		this.order_id = builder.order_id;
		this.ship_name = builder.ship_name;
		this.ship_tel = builder.ship_tel;
		this.ship_addr = builder.ship_addr;
		this.referee_num = builder.referee_num;
		this.referee_name = builder.referee_name;
		this.deal_user = builder.deal_user;
		this.deal_user_name = builder.deal_user_name;
		this.deal_user_phone = builder.deal_user_phone;
		this.order_info = builder.order_info;
		this.intent_finish_time = builder.intent_finish_time;
		this.phone_info = builder.phone_info;
		this.goods_info = builder.goods_info;
		this.cust_info = builder.cust_info;
		this.seed_user_id = builder.seed_user_id;
		this.market_user_id = builder.market_user_id;
		this.share_svc_num = builder.share_svc_num;
		this.is_no_modify = builder.is_no_modify;
		this.developer_info = builder.developer_info;
		this.is_online_order = builder.is_online_order;//add by cqq 20181226
		this.top_share_userid = builder.top_share_userid;
		this.top_share_num = builder.top_share_num;
		this.offer_eff_type = builder.offer_eff_type;
		this.pay_tag = builder.pay_tag;
	}
	
	public static class Builder{
		private String intent_order_id;
		private String intent_order_time;
		private String status;
		private String remark;
		private String order_id;
		private String ship_name;
		private String ship_tel;
		private String ship_addr;
		private String referee_num; 
		private String referee_name;
		private String deal_user;
		private String deal_user_name;
		private String deal_user_phone;
		private OrderInfo order_info;
		private String intent_finish_time;
		private PhoneInfo phone_info;
		private GoodsInfo goods_info;
		private CustInfo cust_info ;
		private DeveloperInfo developer_info ;
		private String seed_user_id ;
		private String market_user_id;
		private String share_svc_num;
		private String is_no_modify;
		private String is_online_order;//add by cqq 20181226 是否线上线下订单
		private String top_share_userid;
		private String top_share_num;
		private String offer_eff_type;
		private String pay_tag;
		
		public Builder seedUserId(String seed_user_id) {
			this.seed_user_id = seed_user_id;
			return this ;
		}
		
		public Builder marketUserId(String market_user_id) {
			this.market_user_id = market_user_id;
			return this ;
		}
		
		public Builder shareSvcNum(String share_svc_num) {
			this.share_svc_num = share_svc_num;
			return this ;
		}
		
		public Builder isNoModify(String is_no_modify) {
			this.is_no_modify = is_no_modify;
			return this ;
		}
		
		public Builder custInfo(CustInfo CustInfo) {
			this.cust_info = CustInfo;
			return this ;
		}
		public Builder developerInfo(DeveloperInfo developerInfo) {
            this.developer_info = developerInfo;
            return this ;
        }
        public Builder goodsInfo(GoodsInfo goods_info) {
			this.goods_info = goods_info;
			return this ;
		}
		
		public Builder phoneInfo(PhoneInfo phone_info) {
			this.phone_info = phone_info;
			return this ;
		}
		
		public Builder intentFinishTime(String intent_finish_time) {
			this.intent_finish_time = intent_finish_time;
			return this ;
		}
		
		public Builder orderInfo(OrderInfo order_info) {
			this.order_info = order_info;
			return this ;
		}
		
		public Builder dealUserPhone(String deal_user_phone) {
			this.deal_user_phone = deal_user_phone;
			return this ;
		}
		
		public Builder dealUserName(String deal_user_name) {
			this.deal_user_name = deal_user_name;
			return this ;
		}
		
		public Builder shipTel(String ship_tel) {
			this.ship_tel = ship_tel;
			return this ;
		}
		
		public Builder shipAddr(String ship_addr) {
			this.ship_addr = ship_addr;
			return this ;
		}
		
		public Builder refereeNum(String referee_num) {
			this.referee_num = referee_num;
			return this ;
		}

		public Builder refereeName(String referee_name) {
			this.referee_name = referee_name;
			return this ;
		}
		
		public Builder dealUser(String deal_user) {
			this.deal_user = deal_user;
			return this ;
		}
		
		public Builder intentOrderId(String intent_order_id) {
			this.intent_order_id = intent_order_id;
			return this ;
		}
		
		public Builder intentOrderTime(String intent_order_time) {
			this.intent_order_time = intent_order_time;
			return this ;
		}
		
		public Builder orderId(String order_id) {
			this.order_id = order_id;
			return this ;
		} 
		
		public Builder shipName(String ship_name) {
			this.ship_name = ship_name;
			return this ;
		} 
		
		public Builder remark(String remark) {
			this.remark = remark;
			return this ;
		} 
		
		public Builder status(String status) {
			this.status = status;
			return this ;
		} 
		
		//add by cqq 20181226
		public Builder isOnlineOrder(String isOnlineOrder) {
			this.is_online_order = isOnlineOrder;
			return this ;
		}
		
		public Builder top_share_userid(String top_share_userid) {
			this.top_share_userid = top_share_userid;
			return this ;
		} 
		
		public Builder top_share_num(String top_share_num) {
			this.top_share_num = top_share_num;
			return this ;
		} 
		
		public Builder offer_eff_type(String offer_eff_type) {
			this.offer_eff_type = offer_eff_type;
			return this ;
		} 
		
		public Builder pay_tag(String pay_tag) {
			this.pay_tag = pay_tag;
			return this ;
		} 
		
		public IntentOrderInfo build() {
			return new IntentOrderInfo(this);
		}
		
		
	}
	
	public PhoneInfo getPhone_info() {
		return phone_info;
	}
	public void setPhone_info(PhoneInfo phone_info) {
		this.phone_info = phone_info;
	}
	
	public GoodsInfo getGoods_info() {
		return goods_info;
	}
	public void setGoods_info(GoodsInfo goods_info) {
		this.goods_info = goods_info;
	}
	
	public CustInfo getCust_info() {
		return cust_info;
	}
	public void setCust_info(CustInfo cust_info) {
		this.cust_info = cust_info;
	}
	
	public DeveloperInfo getDeveloper_info() {
        return developer_info;
    }

    public void setDeveloper_info(DeveloperInfo developer_info) {
        this.developer_info = developer_info;
    }

    public String getIntent_order_id() {
		return intent_order_id;
	}
	public void setIntent_order_id(String intent_order_id) {
		this.intent_order_id = intent_order_id;
	}
	public String getIntent_order_time() {
		return intent_order_time;
	}
	public void setIntent_order_time(String intent_order_time) {
		this.intent_order_time = intent_order_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_tel() {
		return ship_tel;
	}
	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}
	public String getShip_addr() {
		return ship_addr;
	}
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	public String getReferee_num() {
		return referee_num;
	}
	public void setReferee_num(String referee_num) {
		this.referee_num = referee_num;
	}
	public String getReferee_name() {
		return referee_name;
	}
	public void setReferee_name(String referee_name) {
		this.referee_name = referee_name;
	}
	public String getDeal_user() {
		return deal_user;
	}
	public void setDeal_user(String deal_user) {
		this.deal_user = deal_user;
	}
	public String getDeal_user_name() {
		return deal_user_name;
	}
	public void setDeal_user_name(String deal_user_name) {
		this.deal_user_name = deal_user_name;
	}
	public String getDeal_user_phone() {
		return deal_user_phone;
	}
	public void setDeal_user_phone(String deal_user_phone) {
		this.deal_user_phone = deal_user_phone;
	}
	public OrderInfo getOrder_info() {
		return order_info;
	}
	public void setOrder_info(OrderInfo order_info) {
		this.order_info = order_info;
	}
	public String getIntent_finish_time() {
		return intent_finish_time;
	}
	public void setIntent_finish_time(String intent_finish_time) {
		this.intent_finish_time = intent_finish_time;
	}

	public String getSeed_user_id() {
		return seed_user_id;
	}

	public void setSeed_user_id(String seed_user_id) {
		this.seed_user_id = seed_user_id;
	}

	public String getMarket_user_id() {
		return market_user_id;
	}

	public void setMarket_user_id(String market_user_id) {
		this.market_user_id = market_user_id;
	}

	public String getShare_svc_num() {
		return share_svc_num;
	}

	public void setShare_svc_num(String share_svc_num) {
		this.share_svc_num = share_svc_num;
	}

	public String getIs_no_modify() {
		return is_no_modify;
	}

	public void setIs_no_modify(String is_no_modify) {
		this.is_no_modify = is_no_modify;
	}

	public String getIs_online_order() {
		return is_online_order;
	}

	public void setIs_online_order(String is_online_order) {
		this.is_online_order = is_online_order;
	}

	public String getTop_share_userid() {
		return top_share_userid;
	}

	public void setTop_share_userid(String top_share_userid) {
		this.top_share_userid = top_share_userid;
	}

	public String getTop_share_num() {
		return top_share_num;
	}

	public void setTop_share_num(String top_share_num) {
		this.top_share_num = top_share_num;
	}

	public String getOffer_eff_type() {
		return offer_eff_type;
	}

	public void setOffer_eff_type(String offer_eff_type) {
		this.offer_eff_type = offer_eff_type;
	}

	public String getPay_tag() {
		return pay_tag;
	}

	public void setPay_tag(String pay_tag) {
		this.pay_tag = pay_tag;
	}
	
	
	
}
