package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import params.print.req.PrintReq;
import service.PrintInf;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.PrintConfigName;
import com.ztesoft.net.mall.core.model.PrintTmpl;

/**
 * 数据解析基类
 * @作者 MoChunrun
 * @创建日期 2013-11-7 
 * @版本 V 1.0
 */
public abstract class AbsPrintTmplData extends BaseSupport implements IPrintTmplData {

	private PrintInf printServ;
	public void parseTmplData(PrintTmpl printTmpl,Map<String,String> data){
		Map<String,String> map = perform(data);
		PrintReq printReq = new PrintReq();
		printReq.setConfig_id(printTmpl.getConfig_id());
		List<PrintConfigName> configItemList = printServ.getConfigName(printReq).getConfigItemList();
		String tmp_data = printTmpl.getPrt_tmpl_data();
		for(PrintConfigName name:configItemList){
			//logger.info(name.getE_name());
			//logger.info(map.get(name.getE_name()));
			String value = map.get(name.getE_name())==null?"":map.get(name.getE_name());
			tmp_data = tmp_data.replaceAll(name.getC_name(), value);
		}
		printTmpl.setPrt_tmpl_data(tmp_data);
	}
	public PrintInf getPrintServ() {
		return printServ;
	}
	public void setPrintServ(PrintInf printServ) {
		this.printServ = printServ;
	}

	

}
