package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * 
 * zj省份选号号码返回参数
 * @author wayne
 *
 */
public class NumInfoZjBss implements Serializable {

	@ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="number：号码")
	private String number;
	
	@ZteSoftCommentAnnotationParam(name="是否靓号",type="String",isNecessary="Y",desc="is_spenum：00：普通号码;01：靓号")
	private String is_spenum;
	
	@ZteSoftCommentAnnotationParam(name="靓号等级",type="String",isNecessary="N",desc="num_lvl：靓号等级")
	private String num_lvl;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIs_spenum() {
		return is_spenum;
	}

	public void setIs_spenum(String is_spenum) {
		this.is_spenum = is_spenum;
	}

	public String getNum_lvl() {
		if(num_lvl.length()>0&&num_lvl!=""){
		if("9".equals(num_lvl)){
			return "普通号码";
		}else if("1".equals(num_lvl)){
			return "一级靓号";
		}else if("2".equals(num_lvl)){
			return "二级靓号";
		}else if("3".equals(num_lvl)){
			return "三级靓号";
		}else if("4".equals(num_lvl)){
			return "四级靓号";
		}else if("5".equals(num_lvl)){
			return "五级靓号";
		}else if("6".equals(num_lvl)){
			return "六级靓号";
		}else{
			return num_lvl;
		}
	}
		return "普通号码";
	}

	public void setNum_lvl(String num_lvl) {
		this.num_lvl = num_lvl;
	}
}
