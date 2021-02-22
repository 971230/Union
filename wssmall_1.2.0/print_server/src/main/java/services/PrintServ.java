package services;

import java.util.List;

import params.print.req.PrintReq;
import params.print.req.PrintTmplConfigListReq;
import params.print.resp.PrintResp;
import params.print.resp.PrintTmplConfigResp;
import params.print.resp.PrintTmplResp;
import service.PrintInf;

import com.ztesoft.net.mall.core.model.PrintConfigName;
import com.ztesoft.net.mall.core.model.PrintTmpl;
import com.ztesoft.net.mall.core.model.Printtmplconfig;
import com.ztesoft.net.mall.core.service.IPrintTmplManager;
import com.ztesoft.net.mall.core.service.IPrinttmplconfigManager;

public class PrintServ  extends ServiceBase implements PrintInf{

	private IPrinttmplconfigManager printtmplconfigManager;
	private IPrintTmplManager printTmplManager;
	@Override
	public PrintResp getConfigName(PrintReq printReq){
		PrintResp printResp = new PrintResp();
		List<PrintConfigName> configItemList = printtmplconfigManager.getConfigName(printReq.getConfig_id());
		printResp.setConfigItemList(configItemList);
		addReturn(printReq, printResp);
		return printResp;
	}
	
	@Override
	public PrintTmplConfigResp printTpmlConfigList(
			PrintTmplConfigListReq printTmplConfigListReq) {
		// TODO Auto-generated method stub
		PrintTmplConfigResp  PrintTmplConfigListResp  = new PrintTmplConfigResp();
		List<Printtmplconfig> configList = this.printtmplconfigManager.listPrinttmplconfig();
		PrintTmplConfigListResp.setPrintTmplConfigList(configList);
		addReturn(printTmplConfigListReq, PrintTmplConfigListResp);
		return PrintTmplConfigListResp;
	}
	
	public IPrinttmplconfigManager getPrinttmplconfigManager() {
		return printtmplconfigManager;
	}

	public void setPrinttmplconfigManager(
			IPrinttmplconfigManager printtmplconfigManager) {
		this.printtmplconfigManager = printtmplconfigManager;
	}

	public IPrintTmplManager getPrintTmplManager() {
		return printTmplManager;
	}

	public void setPrintTmplManager(IPrintTmplManager printTmplManager) {
		this.printTmplManager = printTmplManager;
	}

	@Override
	public PrintTmplResp getPrintTmplList(PrintReq printReq) {
		// TODO Auto-generated method stub
		PrintTmplResp printTmplResp = new PrintTmplResp();
		List<PrintTmpl> list  = this.printTmplManager.listCanUse();
		printTmplResp.setPrintTmplList(list);
		addReturn(printReq,printTmplResp);
		return printTmplResp;
	}

	@Override
	public PrintTmplConfigResp getPrintTmplConfig(PrintReq printReq) {
		// TODO Auto-generated method stub
		PrintTmplConfigResp printTmplConfigResp = new PrintTmplConfigResp();
		printTmplConfigResp.setPrintTmplconfig(this.printtmplconfigManager.get(printReq.getConfig_id()));
		addReturn(printReq,printTmplConfigResp);
		return printTmplConfigResp;
	}

	@Override
	public PrintTmplResp getPrintTmpl(PrintReq printReq) {
		// TODO Auto-generated method stub
		PrintTmplResp  printTmplResp = new PrintTmplResp();
		String prt_tmpl_id = printReq.getPrt_tmpl_id();
		printTmplResp.setPrintTmpl(this.printTmplManager.get(prt_tmpl_id));
		addReturn(printReq,printTmplResp);
		return printTmplResp;
	}

	

}
