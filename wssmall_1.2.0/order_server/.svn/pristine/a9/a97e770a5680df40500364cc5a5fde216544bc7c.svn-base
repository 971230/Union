package zte.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import params.order.req.DlyTypeReq;
import params.order.resp.DlyTypeResp;
import services.OrderInf;
import services.ServiceBase;
import zte.net.iservice.IDlyTypeAddressService;
import zte.params.order.req.DlyTypeAddressListReq;
import zte.params.order.req.DlyTypeListReq;
import zte.params.order.req.DlyTypePriceListReq;
import zte.params.order.resp.DlyTypeAddressListResp;
import zte.params.order.resp.DlyTypeListResp;
import zte.params.order.resp.DlyTypePriceListResp;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.mall.core.model.DlyType;
import com.ztesoft.net.mall.core.service.IDlyTypeAddressManager;
import com.ztesoft.net.mall.core.service.IDlyTypeManager;
import com.ztesoft.net.model.DlyTypeAddress;

public class DlyTypeAddressService extends ServiceBase implements IDlyTypeAddressService {
	
	@Resource
	private IDlyTypeAddressManager dlyTypeAddressManager;
	@Resource
	private OrderInf orderServ;
	@Resource
	private IDlyTypeManager dlyTypeManager;

	@Override
	public DlyTypeAddressListResp listTypeAddress(DlyTypeAddressListReq req) {
		DlyTypeAddressListResp resp = new DlyTypeAddressListResp();
		List<DlyTypeAddress> addList = dlyTypeAddressManager.qryDlyTypeAddressByRegionId(req.getRegion_id());
		resp.setDlyTypeAddressList(addList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询配送方式", summary = "查询配送方式")
	public DlyTypeListResp listDlyType(DlyTypeListReq req) {
		DlyTypeReq oreq = new DlyTypeReq();
		DlyTypeListResp resp = new DlyTypeListResp();
		try{
			if(StringUtils.isEmpty(req.getRegion_id())){
				List<DlyType> dlytypeList  = dlyTypeManager.list();  
				resp.setDlyTypeList(dlytypeList);
			}else{
				BeanUtils.copyProperties(oreq, req);
				DlyTypeResp oresp = orderServ.showDlyType(oreq);
				BeanUtils.copyProperties(resp, oresp);
				resp.setDlyTypeList(oresp.getDlyTypeList());
			}
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception ex){
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询配送方式金额", summary = "查询配送方式金额")
	public DlyTypePriceListResp listDlyTypePrice(DlyTypePriceListReq req) {
		List<DlyType> dlytypeList  = dlyTypeManager.list();
		List priceList  = new ArrayList();
		for(DlyType type  : dlytypeList){
			String typeid  = type.getType_id();
			Double goodsprice  = req.getGoodsprice();
			double weight  = req.getWeight();
			Double[] price= dlyTypeManager.countPrice(typeid, weight, goodsprice, ""+req.getRegionid(), false);
			Map map  = new HashMap(2);
			map.put("name", type.getName());
			map.put("price", price[0]);
			priceList.add(map);
		}
		DlyTypePriceListResp resp = new DlyTypePriceListResp();
		resp.setDlyTypePriceList(priceList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

}
