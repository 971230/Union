package com.ztesoft.services;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ztesoft.api.ZteClient;
import com.ztesoft.parameters.accept.AcceptRequest;
import com.ztesoft.parameters.accept.bean.MainOrdBean;
import com.ztesoft.parameters.accept.bean.OrdItemBean;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
public class OrderService extends IROPService {

	protected final Logger logger = Logger.getLogger(getClass());
	private ZteClient client=null;

    private MainOrdBean getOrdBean(AcceptRequest request) {
        MainOrdBean ordBean = request.getMainOrd();
        if (ordBean == null) {//http协议
            ordBean = new MainOrdBean();
            ordBean.setAcc_nbr(request.getAcc_nbr());
            ordBean.setArea_code(request.getArea_code());
            ordBean.setOrd_desc(request.getOrd_desc());

            ArrayList items = new ArrayList();
            OrdItemBean itemBean = new OrdItemBean();
            itemBean.setOrd_id(request.getOrd_id());
            itemBean.setItem_desc(request.getOrd_desc());
            itemBean.setItem_fee(request.getItem_fee());
            itemBean.setItem_type(request.getItem_type());
            itemBean.setPlan_id(request.getPlan_id());
            itemBean.setPlan_num(request.getPlan_num());
            itemBean.setRel_ord_code(request.getRel_ord_code());
            items.add(itemBean);
            ordBean.setItems(items);
            request.setMainOrd(ordBean);
        }
        return ordBean;
    }
}
