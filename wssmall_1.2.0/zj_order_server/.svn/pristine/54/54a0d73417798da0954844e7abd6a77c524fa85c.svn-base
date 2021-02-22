/**
 * @author Rapon
 * @date 2015-09-30
 * @description 新商城工号同步处理工具
 */
package util;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.params.wyg.req.MallOpIDSynInfoReq;
import zte.net.ecsord.params.wyg.vo.CBSSInfo;
import zte.net.ecsord.params.wyg.vo.StaffInfo;

import com.ztesoft.ibss.common.util.DateUtil;


/**
 * @author Rapon
 * @version 1.0
 */
public final class WYGOperatorIDSynUtil
{

	/**
	 * 检验必填参数及格式
	 * @param req
	 * @return
	 */
	public static String checkReq(MallOpIDSynInfoReq req){
		StringBuffer strBuf = new StringBuffer();
		if(StringUtils.isEmpty(req.getSerial_no())){
			strBuf.append("缺少必填字段serial_no.");
		}
		if(StringUtils.isEmpty(req.getTime())){
			strBuf.append("缺少必填字段time.");
		}else{
			strBuf.append(checkTimeFormat("time",req.getTime(),DateUtil.DATE_FORMAT_5));
		}
		if(StringUtils.isEmpty(req.getSource_system())){
			strBuf.append("缺少必填字段source_system.");
		}
		if(StringUtils.isEmpty(req.getReceive_system())){
			strBuf.append("缺少必填字段receive_system.");
		}
		if(null==req.getStaffInfo()||req.getStaffInfo().size()<1){
			strBuf.append("缺少必填信息StaffInfo.");
		}else{
			for(StaffInfo info:req.getStaffInfo()){				
				strBuf.append(checkStaffInfo(req.getSource_system(),info));
			}
		}
		return strBuf.toString();

	}

	/**
	 * 检验StaffInfo信息
	 * @param info
	 * @return
	 */
	private static String checkStaffInfo(String source_system,StaffInfo info){
		StringBuffer strBuf = new StringBuffer();
		if(StringUtils.isEmpty(info.getStaffCode())){
			strBuf.append("缺少必填信息StaffCode.");
		}else{
			strBuf.append(checkStaffCode("StaffCode",info.getStaffCode()));
		}

		if(StringUtils.isEmpty(info.getCodeState())){
			strBuf.append("缺少必填信息CodeState.");
		}else{
			strBuf.append(checkCodeState(info.getCodeState()));
		}
		if("100312".equals(source_system)){
			if(null==info.getCBSSInfo()||info.getCBSSInfo().size()<1){
				strBuf.append("缺少必填信息CBSSInfo.");
			}
		}
		if(null!=info.getCBSSInfo()&&info.getCBSSInfo().size()>0){
			for(CBSSInfo cbssInfo:info.getCBSSInfo()){
				strBuf.append(checkCBSSInfo(cbssInfo));
			}
		}
		if(!StringUtils.isEmpty(info.getUpdateTime())){
			strBuf.append(checkTimeFormat("工号:"+info.getStaffCode()+"字段UpdateTime",info.getUpdateTime(),DateUtil.DATE_FORMAT_5));
		}
		return strBuf.toString();
	}

	/**
	 * 检验CBSS信息
	 * @param info
	 * @return
	 */
	private static String checkCBSSInfo(CBSSInfo info){
		StringBuffer strBuf = new StringBuffer();

		if(StringUtils.isEmpty(info.getCBSSStaff())){
			strBuf.append("缺少必填字段CBSSStaff.");
		}
		if(StringUtils.isEmpty(info.getProvince())){
			strBuf.append("缺少必填字段Province.");
		}
		if(StringUtils.isEmpty(info.getCity())){
			strBuf.append("缺少必填字段City.");
		}
		return strBuf.toString();
	}

	/**
	 * 检验字符串只能是字母或者数字
	 * @return
	 */
	private static String checkStaffCode(String key,String value){
		StringBuffer strBuf = new StringBuffer();
		if(!value.matches("[0-9A-Za-z_]*")){
			strBuf.append(key+":"+value+"只能是字母或者数字.");
		}
		return strBuf.toString();
	}

	/**
	 * 检验CodeState编码只能是	10：正常;20：注销
	 * @param CodeState
	 * @return
	 */
	private static String checkCodeState(String CodeState){
		StringBuffer strBuf = new StringBuffer();
		if(!("10".equals(CodeState)||"20".equals(CodeState))){
			strBuf.append("CodeState编码只能是10：正常;20：注销.");
		}
		return strBuf.toString();
	}

	private static String checkTimeFormat(String key,String value,String format){
		StringBuffer strBuf = new StringBuffer();
		if(!DateUtil.isDateFormat(value,format)){
			strBuf.append(key+":"+value+"不符合yyyymmddhhmiss格式.");
		}
		return strBuf.toString();
	}

	/**
	 * 注册/注销工号
	 * @param info
	 * @return
	 */
	public static String createStaffInfo(StaffInfo info){
		String username = info.getStaffCode();
		return null;
	}
}
