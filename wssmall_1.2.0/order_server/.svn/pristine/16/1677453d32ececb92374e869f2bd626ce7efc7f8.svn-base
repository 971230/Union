package services;

import java.util.List;

import util.MallThreadLocalHolder;

import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.ITplInstManager;
import com.ztesoft.net.model.AttrInst;

/**
 * 订单线程变量接口
* @作者 MoChunrun 
* @创建日期 2013-10-12 
* @版本 V 1.0
 */
public class OrderThreadLocalServ extends ServiceBase implements OrderThreadLocalInf {
	
	private IOrderNFlowManager orderNFlowManager;
	private ITplInstManager tplInstManager; 



	@Override
	public List<AttrInst> getOuterAttrInst(String outer_order_id) {
		if(MallThreadLocalHolder.getInstance().isOuterOrder()){
			return tplInstManager.getOuterAttrInst(outer_order_id);
		}else{
			return MallThreadLocalHolder.getInstance().getOuterAttrInstList();
		}
	}
	
	@Override
	public void saveOrderOuter() {
		if(MallThreadLocalHolder.getInstance().isOuterOrder()){
			this.baseDaoSupport.insert("ORDER_OUTER", MallThreadLocalHolder.getInstance().getCurrOrderOuter());
		}
	}

	@Override
	public void saveOuterAttrInst() {
		if(MallThreadLocalHolder.getInstance().isOuterOrder()){
			for(AttrInst a:MallThreadLocalHolder.getInstance().getOuterAttrInstList()){
				this.baseDaoSupport.insert("es_outer_attr_inst", a);
			}
		}
	}

	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}

	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}

	public ITplInstManager getTplInstManager() {
		return tplInstManager;
	}

	public void setTplInstManager(ITplInstManager tplInstManager) {
		this.tplInstManager = tplInstManager;
	}


	
}
