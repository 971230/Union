package rule.impl;

import java.util.List;

import javax.annotation.Resource;

import params.adminuser.req.MblWorkLogVO;
import params.adminuser.req.WordLogAddReq;
import params.order.req.ItemAcceptReq;
import params.order.resp.AcceptResp;
import rule.AbstractRuleHander;
import rule.IAcceptRule;
import rule.params.accept.req.AcceptRuleReq;
import rule.params.accept.resp.AcceptRuleResp;
import services.AcceptInf;
import services.GoodsInf;
import services.WorkLogInf;
import vo.AcceptVo;
import zte.net.iservice.IOrderServices;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.AttrUserInfo;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import commons.CommonTools;

import consts.ConstsCore;

//import org.apache.log4j.Logger;

/**
 * 缺省受理规则处理器
 *
 * @author wu.i
 */
public abstract class AcceptBaseRule extends AbstractRuleHander implements IAcceptRule {
    //protected Logger logger = Logger.getLogger(AcceptBaseRule.class);

    private AcceptInf acceptServ = null;
    private WorkLogInf workLogServ;
    @Resource
    private GoodsInf goodsServ;
    @Resource
    private IOrderServices orderServices;
    


    //受理数据保存公共入口
    public AcceptRuleResp validateAcceptInstPerform(AcceptRuleReq acceptRuleReq) {
        AcceptRuleResp acceptRuleResp =  this.validateAcceptInst(acceptRuleReq);
//        acceptRuleResp.setError_code(ConstsCore.ERROR_SUCC);
//        return acceptRuleResp;
        return acceptRuleResp;
    }

//    private static int afSaveAcceptInstPerform_before_saveTplInst_index =0;
	
    //受理数据保存公共入口     
    public AcceptRuleResp afSaveAcceptInstPerform(AcceptRuleReq acceptRuleReq) throws ApiBusiException {
    	try{
	    	AcceptRuleResp acceptRuleResp = new AcceptRuleResp();
	    	//保存受理单数据
	    	AcceptResp acceptResp = new AcceptResp();
//	    	afSaveAcceptInstPerform_before_saveTplInst_index++;
//	    	logger.info(afSaveAcceptInstPerform_before_saveTplInst_index+"========================================>afSaveAcceptInstPerform_before_saveTplInst_index");
	    	//ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    	//acceptRuleResp = client.execute(acceptRuleReq, AcceptRuleResp.class);
	    	acceptResp = orderServices.saveTplInst(acceptRuleReq); //TODO AcceptRuleReq设值到acceptRuleResp
	        OrderItem item = acceptRuleReq.getOrderItem();
	        try {
	        	acceptRuleReq.setAcceptVos(acceptResp.getAcceptVos());
	        	
	        	acceptRuleResp = this.afSaveAcceptInst(acceptRuleReq);
	        } catch (ApiBusiException e1) { //业务异常，事物不回滚
	            e1.printStackTrace();
	            saveErrorLog(acceptRuleReq, acceptRuleResp, item);
	            throw new ApiBusiException(e1.getMessage());
	        }
	
	        if (acceptServ == null)
	            acceptServ = SpringContextHolder.getBean("acceptServ");
	        //根据受理成功状态更新主订单的标识
	        if (ConstsCore.RULE_SUCC.equals(acceptRuleResp.getFlag())) {
	            saveSuccLog(acceptRuleReq, acceptRuleResp, item);
	        } else if (ConstsCore.RULE_FAIL.equals(acceptRuleResp.getFlag())) {
	            {
	                saveErrorLog(acceptRuleReq, acceptRuleResp, item);
	            }
	        }
	        return acceptRuleResp;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new ApiBusiException("受理失败");
    	}
    }

	
    private void saveSuccLog(AcceptRuleReq acceptRuleReq, AcceptRuleResp acceptRuleResp, OrderItem item) throws ApiBusiException {
        ItemAcceptReq req = new ItemAcceptReq();
        req.setAcceptRuleReq(acceptRuleReq);
        req.setAcceptRuleResp(acceptRuleResp);
        req.setAccept_num(item.getNum());
        req.setAccept_status(OrderStatus.ACCEPT_STATUS_3);
        req.setItem_id(item.getItem_id());
        req.setOrder_id(item.getOrder_id());
        req.setUserSessionId(acceptRuleReq.getUserSessionId());
        req.setDescription(acceptRuleResp.getError_msg());
        acceptServ.itemAccept(req);
    }

    public void saveErrorLog(AcceptRuleReq acceptRuleReq, AcceptRuleResp acceptRuleResp, OrderItem item) throws ApiBusiException {
        ItemAcceptReq req = new ItemAcceptReq();
        req.setAccept_num(item.getNum());
        req.setAcceptRuleReq(acceptRuleReq);
        req.setAcceptRuleResp(acceptRuleResp);
        req.setAccept_status(OrderStatus.ACCEPT_STATUS_4);
        req.setItem_id(item.getItem_id());
        req.setOrder_id(item.getOrder_id());
        req.setUserSessionId(acceptRuleReq.getUserSessionId());
        req.setDescription(acceptRuleResp.getError_msg());
        acceptServ.itemAccept(req);
    }

    /**
     * 插入工作日志
     * @param acceptRuleReq
     * @param acceptRuleResp
     * @param attrUserInfo
     * @param service_code
     * @param service_offer_name
     */
    public void insertWorkLog(AcceptRuleReq acceptRuleReq, AcceptRuleResp acceptRuleResp,
                              AttrUserInfo attrUserInfo, String service_code, String service_offer_name) {
        MblWorkLogVO mblWorkLogVO = new MblWorkLogVO();

        if (ConstsCore.RULE_SUCC.equals(acceptRuleResp.getFlag())) {
            mblWorkLogVO.setBusiness_status(Consts.WORK_LOG_STATE_SUCC);
        } else {
            mblWorkLogVO.setBusiness_status(Consts.WORK_LOG_STATE_FAIL);
        }
        AdminUser adminUser = ManagerUtils.getAdminUser();
        String accept_table = "";
        List<AcceptVo> list = acceptRuleReq.getAcceptVos();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                AcceptVo acceptVo = list.get(i);
                accept_table = acceptVo.getTable_name();
            }
        }
        String seq = this.baseDaoSupport.getSequences("SEQ_WORK_FLOW");
        mblWorkLogVO.setAccept_time(Consts.SYSDATE);
        mblWorkLogVO.setAccept_table(accept_table);
        mblWorkLogVO.setBrand(attrUserInfo.getBrand());
        mblWorkLogVO.setPhoneno(attrUserInfo.getPhoneno());
        mblWorkLogVO.setBusiness_code(service_code);
        mblWorkLogVO.setBusiness_name(service_offer_name);
        mblWorkLogVO.setCity(adminUser.getCity());
        mblWorkLogVO.setCity_code(adminUser.getCity_id());
        mblWorkLogVO.setOp_id(adminUser.getUserid());
        mblWorkLogVO.setOp_name(adminUser.getUsername());
        mblWorkLogVO.setParnter_phone(adminUser.getPhone_num());
        mblWorkLogVO.setPrice(attrUserInfo.getPrice());
        mblWorkLogVO.setBusiness_status(Consts.LOG_BUS_SUCC);
        mblWorkLogVO.setLan_name(adminUser.getLan_name());
        mblWorkLogVO.setLan_code(adminUser.getLan_id());
        mblWorkLogVO.setSeq(seq);
        mblWorkLogVO.setUserid(attrUserInfo.getCust_id());
        mblWorkLogVO.setUsername(attrUserInfo.getCust_name());
        mblWorkLogVO.setSource_from(CommonTools.getSourceForm());
        mblWorkLogVO.setProduct_id(acceptRuleReq.getOrderItem().getProduct_id());
        mblWorkLogVO.setProduct_name(goodsServ.getGoods(acceptRuleReq.getOrderItem().getGoods_id()).getName());
        mblWorkLogVO.setPhone_lan_name(attrUserInfo.getPhone_lan_name());
        mblWorkLogVO.setPhone_lan_id(attrUserInfo.getPhone_lan_id());
        mblWorkLogVO.setIp_ads(attrUserInfo.getIp_ads());

        WordLogAddReq wordLogAddReq = new WordLogAddReq();
        wordLogAddReq.setMblWorkLogVO(mblWorkLogVO);
        this.workLogServ.addWork(wordLogAddReq);
    }

    /*public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }*/

    public AcceptInf getAcceptServ() {
        return acceptServ;
    }

    public void setAcceptServ(AcceptInf acceptServ) {
        this.acceptServ = acceptServ;
    }


    public WorkLogInf getWorkLogServ() {
        return workLogServ;
    }

    public void setWorkLogServ(WorkLogInf workLogServ) {
        this.workLogServ = workLogServ;
    }

	public GoodsInf getGoodsServ() {
		return goodsServ;
	}

	public void setGoodsServ(GoodsInf goodsServ) {
		this.goodsServ = goodsServ;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

}
