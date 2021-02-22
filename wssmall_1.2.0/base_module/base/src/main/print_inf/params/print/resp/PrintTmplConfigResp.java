package params.print.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.Printtmplconfig;

public class PrintTmplConfigResp extends ZteResponse{
	
   private  List<Printtmplconfig>  printTmplConfigList;
   private  Printtmplconfig printTmplconfig;
   
   

public List<Printtmplconfig> getPrintTmplConfigList() {
	return printTmplConfigList;
}

public void setPrintTmplConfigList(List<Printtmplconfig> printTmplConfigList) {
	this.printTmplConfigList = printTmplConfigList;
}

public Printtmplconfig getPrintTmplconfig() {
	return printTmplconfig;
}

public void setPrintTmplconfig(Printtmplconfig printTmplconfig) {
	this.printTmplconfig = printTmplconfig;
}
   
   
}
