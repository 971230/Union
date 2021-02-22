package com.zte.cbss.autoprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.PsptInfo;
import com.zte.cbss.autoprocess.model.data.BSSCheckData;
import com.zte.cbss.autoprocess.model.data.CreateCustomPageData;
import com.zte.cbss.autoprocess.model.data.PsptCheckData;
import com.zte.cbss.autoprocess.request.BSSCheckRequest;
import com.zte.cbss.autoprocess.request.CreateCustomPage1Request;
import com.zte.cbss.autoprocess.request.CreateCustomPage2Request;
import com.zte.cbss.autoprocess.request.CreateCustomRequest;
import com.zte.cbss.autoprocess.request.PsptCheckPage1Request;
import com.zte.cbss.autoprocess.request.PsptCheckPage2Request;
import com.zte.cbss.autoprocess.request.PsptCheckRequest;
import com.ztesoft.common.util.JsonUtil;

/**
 * 创建客户信息过程
 * @author 张浩
 * @version 1.0.0(2014-5-20)
 */
public class CreateCustomHandler {
	
	static final Logger log = LoggerFactory.getLogger(CreateCustomHandler.class);

	/**
	 * 模拟创建客户信息，返回客户信息，如果未空则创建客户失败
	 * @param bill	客户账单
	 * @return
	 */
	public static CustomInfo emulate(CustomBill bill,HttpLoginClient client){
		client.getParam().setBill(bill); //赋值
		CustomInfo result = null;
		log.debug("-----客户信息-----"+JsonUtil.toJson(bill));
		//进行身份证检查
		PsptCheckData data = new PsptCheckData();
		data.setPsptId(bill.getPsptId());
		data.setCustomName(bill.getCustomName());
		PsptCheckPage1Request psptCheckPage1Request = new PsptCheckPage1Request(client);
		psptCheckPage1Request.send(data);
		PsptCheckPage2Request psptCheckPage2Request = new PsptCheckPage2Request(client);
		psptCheckPage2Request.send(data);
		PsptInfo psptInfo = null;
		if("1".equals(bill.getIdTypeCode())){//身份证用户订单才检测身份证信息
			PsptCheckRequest psptCheckRequest = new PsptCheckRequest(client);
			psptInfo = psptCheckRequest.send(data); //证件信息
		}
		//测试创建客户使用
		/*if(psptInfo == null){
			psptInfo = new PsptInfo();
			psptInfo.setAddressInfo("不知道不知道不知道");
			psptInfo.setBirthday("1983-01-01");
			client.getParam().setPsptInfo(psptInfo);
		}*/
		if(psptInfo != null || !"1".equals(bill.getIdTypeCode())){
			//检查客户是否存在，对应页面BSS校验
			client.getParam().setPsptInfo(psptInfo);
			BSSCheckData bssCheckData = new BSSCheckData();
			bssCheckData.setID_TYPE_CODE(bill.getIdTypeCode());
			bssCheckData.setEPARCHY_CODE(client.getParam().getEparchyCode());
			//bssCheckData.setPSPT_ID(bill.getPsptId());
			bssCheckData.setSERIAL_NUMBER(client.getParam().getBill().getSerialNumber());
			result = new BSSCheckRequest(client).send(bssCheckData);
			if(result == null){
				if(psptInfo == null){
					PsptInfo newPsptInfo = new PsptInfo();
					newPsptInfo.setAddressInfo(bill.getPostAddress());
					newPsptInfo.setBirthday("1990-01-01 00:00:00");
					newPsptInfo.setIdentityName(bill.getCustomName());
					newPsptInfo.setIdentityNo(bill.getPsptId());
					client.getParam().setPsptInfo(newPsptInfo);
				}
				//创建客户
				CreateCustomPage1Request createCustomPage1Request = new CreateCustomPage1Request(client);
				createCustomPage1Request.send(client.getParam());
				CreateCustomPage2Request createCustomPage2Request = new CreateCustomPage2Request(client);
				CreateCustomPageData createCustomPageData = new CreateCustomPageData(client.getParam());
				createCustomPage2Request.send(createCustomPageData);
				CreateCustomRequest createCustomRequest = new CreateCustomRequest(client);
				createCustomRequest.send(createCustomPageData);
				//重新BSS校验，偷懒的写法
				result = new BSSCheckRequest(client).send(bssCheckData);
				client.getParam().setCustomInfo(result);
			}else{
				log.info("客户已存在，请进行开卡操作");
				client.getParam().setCustomInfo(result);
				log.debug("{}客户已存在，请进行开卡操作。",bill.getCustomName());
			}
			return result;
		}else{
			log.info("证件验证失败，证件编号：{}，客户名称：{}",bill.getPsptId(),bill.getCustomName());
			return null;
		}
//		return result;
	}
}
