package com.ztesoft.net.mall.cmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.web.util.FTPUtil;

import com.ztesoft.net.mall.core.consts.Consts;

/**
 * 百信网，暂时先废弃，代码可供参考
 * @author wui
 *
 */

@Deprecated
public class CompCharge implements ICompCharge {
	
	public Logger imlog = Logger.getLogger(CompCharge.class);
	
	@Override
	public void compCharge(Map map,CompBillResult compBillResult){
		
		imlog.debug("百信网对账数据"+map.toString());		
		
		String strFileName = (String)map.get("bxw_shop_no") + (String)map.get("bxw_file_tail");
		strFileName = (String)map.get("ACCOUNTS_ID") + strFileName ;
		map.put("strFileName", strFileName);				//对账文件

		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			String remoteFile = (String)map.get("bxw_file_remote_path") + strFileName;
			String localFile = (String)map.get("bxw_file_local_path") + strFileName;
			
			imlog.debug("远程文件" + remoteFile);
			
			//下载对账文件
			Map ftpConfig = new HashMap();
			ftpConfig.put("ip", map.get("up_ftp_addr"));
			ftpConfig.put("port", map.get("up_ftp_port"));
			ftpConfig.put("userName", map.get("up_ftp_user"));
			ftpConfig.put("passWord", map.get("up_ftp_passwd"));
			
			FTPUtil.download(remoteFile, localFile, ftpConfig);
			
            fr = new FileReader(localFile);		//创建FileReader对象，用来读取字符流
            br = new BufferedReader(fr);    	//缓冲指定文件的输入
            
            int i = 0;
            List list =  new ArrayList();
            String myreadline = "";
            String transDate = "";		//对账日期
            while (br.ready()) {
            	            	
            	myreadline = br.readLine();			//读取一行
                imlog.info(myreadline);		//在屏幕上输出
                if (i==0){
                	
                    StringTokenizer strToken = new StringTokenizer(myreadline,"|");
                    String shopNo = strToken.nextToken();							//商户号，校验文件的是否正确
                    
                    if(!shopNo.equals(map.get("bxw_shop_no").toString())){
                    	
                    	throw new RuntimeException("对账文件不正确，商户号对不上号，请检查商户号是否正确");
                    }
                    transDate = strToken.nextToken();
                    String transNum = strToken.nextToken();
                    String transMoney = strToken.nextToken();
                    map.put("transDate", transDate);					//交易日期                    
                    map.put("transNum", transNum);						//交易数量
                    map.put("transMoney", transMoney);					//交易金额
                    
                }else{
                	
                    StringTokenizer strToken = new StringTokenizer(myreadline,"|");
                    
                    String requestId =strToken.nextToken(); 			//百信网请求流水
                    String bizCode =  strToken.nextToken(); 			//业务编码
                    String accNbr =  strToken.nextToken();  			//业务号码
                    String amount =   strToken.nextToken(); 			//充值金额
                    Map temp = new HashMap();
                    temp.put("requestId", requestId);
                    temp.put("bizCode", bizCode);
                    temp.put("accNbr", accNbr);
                    temp.put("amount", amount);
                    list.add(temp);
                }
                i++;
            }
            map.put("chargeList", list);
            map.put("icount", new Integer(i-1));
            br.close();
            fr.close();
            
        } catch (Exception e) {
        	compBillResult.setErr_type(Consts.COMP_BILL_ERROR_00C);
        	compBillResult.setErr_message("处理翼支付对帐文件失败");
            e.printStackTrace();
            throw new RuntimeException("处理翼支付对帐文件失败:" + e.getMessage());
        }finally{
        	
        	if (br!=null){
        		try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	if (fr!=null){
        		try {
        			fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}        	        	        	
        }
	}
}
