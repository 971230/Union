package com.ztesoft.net.framework.model;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public class GoodsPicDirectiveModel implements TemplateDirectiveModel {

	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String pic  = params.get("pic").toString();
		if(StringUtil.isEmpty(pic)){
			if(EopSetting.IMG_SERVER_DOMAIN.startsWith("http://"))
				pic = EopSetting.IMG_SERVER_DOMAIN+"/images/no_picture.jpg";
			else
				pic ="http://"+EopSetting.IMG_SERVER_DOMAIN+"/images/no_picture.jpg";
		}
		if(pic.startsWith("fs:")){
			pic  = UploadUtil.replacePath(pic);
		}
		String postfix=   params.get("postfix").toString() ;
		if("_normal".equals(postfix))
			env.getOut().write(pic);
		else
			env.getOut().write(UploadUtil.getThumbPath(pic, postfix));
	}

}
