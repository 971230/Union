/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 靓号信息
 * 
 */
public class NiceInfoReqVo implements Serializable {

	private String advancePay; // Y 预存话费金额
	private String advanceCom; // N 普通预存
	private String advanceSpe; // N 专项预存
	private String numThawTim; // N 返还时长
	private String numThawPro; // N 分月返还金额
	private String classId; // Y "号码等级：1：一级靓号 2：二级靓号 3：三级靓号 4：四级靓号 5：五级靓号 6：六级靓号 9：普通号码"
	private String lowCostChe; // N 考核期最低消费
	private String timeDurChe; // N 考核时长
	private String changeTagChe; // N "考核期是否过户 0:允许 1：不允许"
	private String cancelTagChe; // N "考核期是否销户 0:允许 1：不允许"
	private String bremonChe; // N 考核期违约金月份
	private String lowCostPro; // N 协议期最低消费
	private String timeDurPro; // N "协议时长 当值为00000时表示无期限"
	private String changeTagPro; // N "协议期是否过户 0:允许 1：不允许"
	private String cancelTagPro; // N "协议期是否销户 0:允许 1：不允许"
	private String broMonPro; // N 协议期违约金月份
	
	public String getAdvancePay() {
		return advancePay;
	}
	public void setAdvancePay(String advancePay) {
		this.advancePay = advancePay;
	}
	public String getAdvanceCom() {
		return advanceCom;
	}
	public void setAdvanceCom(String advanceCom) {
		this.advanceCom = advanceCom;
	}
	public String getAdvanceSpe() {
		return advanceSpe;
	}
	public void setAdvanceSpe(String advanceSpe) {
		this.advanceSpe = advanceSpe;
	}
	public String getNumThawTim() {
		return numThawTim;
	}
	public void setNumThawTim(String numThawTim) {
		this.numThawTim = numThawTim;
	}
	public String getNumThawPro() {
		return numThawPro;
	}
	public void setNumThawPro(String numThawPro) {
		this.numThawPro = numThawPro;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getLowCostChe() {
		return lowCostChe;
	}
	public void setLowCostChe(String lowCostChe) {
		this.lowCostChe = lowCostChe;
	}
	public String getTimeDurChe() {
		return timeDurChe;
	}
	public void setTimeDurChe(String timeDurChe) {
		this.timeDurChe = timeDurChe;
	}
	public String getChangeTagChe() {
		return changeTagChe;
	}
	public void setChangeTagChe(String changeTagChe) {
		this.changeTagChe = changeTagChe;
	}
	public String getCancelTagChe() {
		return cancelTagChe;
	}
	public void setCancelTagChe(String cancelTagChe) {
		this.cancelTagChe = cancelTagChe;
	}
	public String getBremonChe() {
		return bremonChe;
	}
	public void setBremonChe(String bremonChe) {
		this.bremonChe = bremonChe;
	}
	public String getLowCostPro() {
		return lowCostPro;
	}
	public void setLowCostPro(String lowCostPro) {
		this.lowCostPro = lowCostPro;
	}
	public String getTimeDurPro() {
		return timeDurPro;
	}
	public void setTimeDurPro(String timeDurPro) {
		this.timeDurPro = timeDurPro;
	}
	public String getChangeTagPro() {
		return changeTagPro;
	}
	public void setChangeTagPro(String changeTagPro) {
		this.changeTagPro = changeTagPro;
	}
	public String getCancelTagPro() {
		return cancelTagPro;
	}
	public void setCancelTagPro(String cancelTagPro) {
		this.cancelTagPro = cancelTagPro;
	}
	public String getBroMonPro() {
		return broMonPro;
	}
	public void setBroMonPro(String broMonPro) {
		this.broMonPro = broMonPro;
	}
	
}
