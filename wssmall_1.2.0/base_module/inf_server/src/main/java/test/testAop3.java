package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import zte.net.ecsord.common.CommonDataFactory;

/**
 * aop接口连通测试
 * @author chenjl
 *
 */
@SuppressWarnings("all")
public class testAop3{
	
	public static String post(String url,String reqEncoding,String respEncoding,List<NameValuePair> param) {
		HttpClient httpclient=new DefaultHttpClient();
        String resStr = "";
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        httppost.setHeader("Accept-Language", "us");
        httppost.setHeader("CONN_type", "SSL");
        // 创建参数队列
        List<NameValuePair> formparams = param;
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, reqEncoding);
            httppost.setEntity(uefEntity);
            HttpResponse response;
            //System.out.println("什么什么头"+httppost.getEntity().toString());
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resStr = EntityUtils.toString(entity,respEncoding);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
           // httpclient.getConnectionManager().shutdown();
        }
        return resStr;
    }
	
	public static String request(String url,String appkey,String method,String apptx,String phoneBssOccupyRequestBean){
		List<NameValuePair> list =new ArrayList<NameValuePair>();
	    list.add(new BasicNameValuePair("appkey", appkey));//
	    list.add(new BasicNameValuePair("method", method));//
	    list.add(new BasicNameValuePair("apptx", apptx));
	    
       	DateFormat tempDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        list.add(new BasicNameValuePair("timestamp", tempDF.format(new Date())));
        
//        System.out.println("请求报文："+phoneBssOccupyRequestBean);
        list.add(new BasicNameValuePair("msg",phoneBssOccupyRequestBean));
        System.out.println("完整请求："+list.toString());
		String content = post(url,"UTF-8", "UTF-8",list);
        System.out.println("返回报文："+content);
        //JSONObject json = JSONObject.fromObject(content);
        //System.out.println("返回报文："+json.getString("acceptanceForm").toString());        
        return content;
	}
	
	public static String makeJsonStr(int num, String url, String operatorId,String province, String city, String district, String channelId,			
			String channelType, String resourcesCode, String developerId,String developerName, String channelIdDev, String proKey,			
			String custName, String certType, String certNum,String contactNum, String occupiedTime, 
			String productId,String resourcesCodeMobile,String bipType,String iccid,			
			String orderId,String provOrderId,String imsi,String capacityTypeCode,String resKindCode,
			String actPlanId,String occupiedFlag,String procId,String cardData)
			throws Exception {
		//员工信息查询ecaop.trades.check.staff.check
		String jsonStr0 = 
				"{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				//"\"channelId\":\""+channelId+"\"," +
				//"\"channelType\":\""+channelType+"\"," +
				"\"checkMode\":\"2\"}";
	
		//客户资料校验ecaop.trades.query.comm.cust.mcheck
		String jsonStr1 =
				"{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"checkType\":\"0\"," +
				"\"opeSysType\":2," +
				"\"serviceClassCode\":\"0000\"," +
				"\"areaCode\":\"\"," +
//				"\"serialNumber\":\""+resourcesCode+"\"," +
				"\"certType\":\""+certType+"\"," +
				"\"certNum\":\""+certNum+"\"," +
				"\"serType\":\"2\"}";
		//发展人查询ecaop.trades.query.comm.devr.qry
		String jsonStr2 = "{" +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"chnlName\":\"\"," +
				//"\"channelId\":\""+channelIdDev+"\"," +
				"\"developerId\":\""+developerId+"\"," +
				//"\"developerName\":\""+developerName+"\"," +
				"\"developerNumber\":\"\"}";
		//号码查询 ecaop.trades.query.comm.snres.qry
		String jsonStr3 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"serType\":\"1\"," +
				"\"busType\":\"1\"," +
				"\"groupFlag\":\"1\"," +
				"\"is3G\":\"\"," +
				"\"queryParas\":[{\"queryType\":\"02\",\"queryPara\":\"185\"}]," +
				"\"para\":[{\"paraId\":\"\",\"paraValue\":\"\"}]}";
		//号码变更
		String jsonStr4 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"serType\":\"1\"," +
				"\"resourcesInfo\":[{" +
				"\"keyChangeTag\":\"0\"," +
				"\"oldKey\":\"\"," +
				"\"proKeyMode\":\"0	\"," +
				"\"proKey\":\""+proKey+"\"," +
				"\"groupKey\":\"\"," +
				"\"resourcesType\":\"01\"," +
				"\"packageTag\":\"0\"," +
				"\"resourcesCode\":\""+resourcesCode+"\"," +
				"\"oldResourcesCode\":\"\"," +
				"\"occupiedFlag\":\""+occupiedFlag+"\"," +
				"\"snChangeTag\":\"0\"," +
				"\"delayOccupiedFlag\":\"\"," +
				"\"occupiedTime\":\""+occupiedTime+"\"," +
				"\"custName\":\""+custName+"\"," +
				"\"certType\":\""+certType+"\"," +
				"\"certNum\":\""+certNum+"\"," +
				"\"contactNum\":\""+contactNum+"\"," +
				"\"preOrderTag\":\"0\"," +
				"\"productId\":\""+productId+"\"," +
				"\"acceptChannelTag\":\"\"," +
				"\"developPersonTag\":\"0\"," +
				"\"recomPersonId\":\"\"," +
				"\"recomDeparId\":\"\"," +
				"\"remark\":\"\"}]," +
				"\"para\":[{\"paraId\":\"\",\"paraValue\":\"\"}]}";
		
		//终端状态查询变更
		String jsonStr5 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"resourcesInfo\":[{" +
				"\"resourcesType\":\"01\"," +
				"\"resCodeType\":\"01\"," +
				"\"resourcesCode\":\""+resourcesCodeMobile+"\"," +
				"\"occupiedFlag\":\"0\"," +
				"\"occupiedTime\":\""+occupiedTime+"\"," +
				"\"custName\":\""+custName+"\"," +
				"\"certType\":\""+certType+"\"," +
				"\"certNum\":\""+certNum+"\"}]}";
		//开户处理申请
		String jsonStr6 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"ordersId\":\""+orderId+"\"," +//外围系统订单ID
				"\"opeSysType\":\"2\"," +//办理业务系统：1：ESS2：CBSS
				//"\"deductionTag\":\"1\"," +//扣款标示 0：不扣款1：扣款该字段没传的时候默认扣款
				"\"numId\":[{" +
					"\"serialNumber\":\""+resourcesCode+"\" ," +//号码
					"\"proKey\":\""+proKey+"\"" +//资源预占关键字
//					",\"niceInfo\":{" +//靓号信息
//						"\"advancePay\":\"168000\"," +//预存话费金额
//						"\"advanceCom\":\"\"," +//普通预存
//						"\"advanceSpe\":\"\"," +//专项预存
//						"\"numThawTim\":\"\"," +//返还时长
//						"\"numThawPro\":\"7000\"," +//分月返还金额
//						"\"classId\":\"9\" ," +//号码等级：1：一级靓号2：二级靓号3：三级靓号4：四级靓号5：五级靓号6：六级靓号9：普通号码
//						"\"lowCostChe\":\"\"," +//考核期最低消费
//						"\"timeDurChe\":\"\"," +//考核时长
//						"\"changeTagChe\":\"0\"," +//考核期是否过户 0:允许1：不允许
//						"\"cancelTagChe\":\"0\"," +//考核期是否销户 0:允许1：不允许
//						"\"bremonChe\":\"\"," +//考核期违约金月份
//						"\"lowCostPro\":\"\"," +
//						"\"timeDurPro\":\"00000\"," +//协议时长 当值为00000时表示无期限
//						"\"changeTagPro\":\"0\"," +//协议期是否过户 0:允许1：不允许
//						"\"cancelTagPro\":\"0\"," +//协议期是否销户 0:允许1：不允许
//						"\"broMonPro\":\"\" "+
//						"}" + //协议期违约金月份
				"}]," +						
				//
//				"\"simCardNo\":[{" +
//					"\"cardDataProcId\":\"\"," +//获取写卡数据业务流水
//					"\"simId\":\"1212323\"," +//卡号
//					"\"imsi\":\""+imsi+"\"," +//新IMSI号
//					"\"cardType\":\"\"," +//白卡类型 参考附录白卡类型说明
//					"\"cardData\":\"\"}]," +//白卡数据	
				"\"customerInfo\":[{" +
					"\"authTag\":\"0\"," +//鉴权标识 0：未鉴权1：已鉴权
					"\"realNameType\":\"0\"," +//客户实名标识 0：实名1：匿名
					"\"custType\":\"0\"," +//新老客户标识 0：新增客户1：老客户
//					"\"custId\":\"\"," +//老客户ID
					"\"newCustomerInfo\":[{" +
						"\"certType\":\""+certType+"\"," +//证件类型 参考附录证件类型说明
						"\"certNum\":\""+certNum+"\"," +//证件号码
						"\"certAdress\":\"广西岑溪市波塘镇南垌村长二组11号\"," +//证件地址
						"\"customerName\":\""+custName+"\"," +//客户名称（不能全数字）
						"\"certExpireDate\":\"20501231\"," +//格式：yyyymmdd 证件失效日期(考虑默认)
						"\"contactPerson\":\"周杰棍\"," +//联系人（不能全数字）
						"\"contactPhone\":\"18666694758\"," +//联系人电话>6位
						"\"contactAddress\":\"外太空18号\"," +//通讯地址
						"\"custType\":\"01\"" +//客户类型：01：个人客户02：集团客户
//						",\"customerRemark\":[{" +
//							"\"customerRemarkId\":\"01\"," +//客户备注ID
//							"\"customerRemarkValue\":\"呵呵呵\"}]" +//客户备注信息
					"}]" +
				"}]," +
				"\"acctInfo\":[{" +
					"\"createOrExtendsAcct\":\"0\"," +//是否继承账户标示 0：新建账户1：继承老账户
					"\"accountPayType\":\"10\"" +//账户付费方式，默认：10 参考附录证件类型说明
//					",\"debutySn\":\"\"" +//合帐号码
				"}]," +	
				"\"userInfo\":[{" +//userInfo开始	
					"\"userType\":\"1\"," +//用户类型 1：新用户2：老用户
					"\"bipType\":\""+bipType+"\"," +//业务类型：1：号卡类业务2：合约类业务3：上网卡4：上网本5：其它配件类6：自备机合约类业务
					"\"is3G\":\"2\"," +//0-2G 1-3G 2-4G
					"\"serType\":\"1\"," +//受理类型 1：后付费  2：预付费 3：准预付费
					"\"packageTag\":\"0\"," +//套包销售标记 0：非套包销售1：套包销售
//							"\"firstMonBillMode\":\"01\"," +//首月付费模式 01：标准资费（免首月月租）02：全月套餐03：套餐减半
//							"\"creditVale\":\"\"," +//信用等级，（3G后付费业务取值范围A、B、C）
//							"\"checkCreditVale\":\"\"," +//用户选择信用等级（3G后付费业务取值范围A、B、C）
//							"\"userPwd\":\"\"," +//用户密码
					"\"product\":[{" +
						"\"productId\":\""+productId+"\"," +//产品标识
						"\"productMode\":\"1\"" +//产品模式 1：主产品2：附加产品
						",\"packageElement\":[{" +
							"\"packageId\":\"51093077\"," +//包编号
							"\"elementId\":\"5997401\"," +//元素编号
							"\"elementType\":\"D\"}]" +//元素类型
//						",\"productCode\":\"\"" +
					"}]," +//套包编码，北六无线上网卡必传
//					"\"activityInfo\":[{" +
//						"\"actPlanId\":\""+actPlanId+"\" " +//活动ID（总部活动ID）
//						",\"packageElement\":[{" +
//							"\"packageId\":\"51000386\"," +//包编号
//							"\"elementId\":\"78880000\"," +//元素编号
//							"\"elementType\":\"A\"}]" +//元素类型
//							
//						",\"resourcesType\":\"\"," +//资源类型 03：移动电话04：上网卡05：上网本
//						"\"resProcId\":\"\"," +//预占流水，保持前后多次资源操作的内容一致，保证多次操作是同一订单的前后关联
//						"\"resourcesCode\":\""+resourcesCodeMobile+"\"" +//终端资源编码，一般是IMEI码
//						",\"isTest\":\"0\"," +//是否测试终端 0：测试1：正式
//						"\"resourcesFee\":\"\"," +//资源费用
//						"\"activityFee\":\"\" ," +//活动预存费用
//						"\"actPara\":[{" +
//							"\"actParaId\":\"\"," +//活动扩展字段ID
//							"\"actParaValue\":\"\"}]" +//活动扩展字段值\
//					"}]," +
					 
					"\"groupFlag\":\"0\" ," +//集团标识 0：非集团用户1：集团用户
//					"\"groupId\":\"5151000000000704873 \"," +//集团ID
//					"\"appType\":\"\"," +//应用类别，当加入具体集团时为必填 0：行业应用1：非行业应用
//					"\"subAppType\":\"\"," +//应用子类别 参考附录应用子类别说明
//					"\"guarantorType\":\"\"," +//担保方式 参见附录
//					"\"guaratorID\":\"\"," +//担保信息参数
//					"\"guCertType\":\"\"," +//被担保人证件类型
//					"\"guCertNum\":\"\"," +//被担保人证件号码

					"\"payInfo\":{" +
						"\"payType\":\"15\"," +//支付方式 参考附录支付方式说明
						"\"payOrg\":\"工商\"," +//支付机构名称
						"\"payNum\":\"62229878765738\"}" +//支付账号
				"}]," +
						
//				"\"recomDis\":\"\","+//客户业务发展区域
			    "\"recomPersonId\":\""+developerId+"\","+//发展员工
				"\"recomPersonName\":\""+developerName+"\""+//发展人名称
//				",\"recomDepartId\":\"\""+//发展人渠道
//						
//				",\"resourcesInfo\":[{" +
//					"\"salePrice\":\"6880000\"," +//销售价格（单位：厘）
//					"\"cost\":\"6500000\"," +//成本价格（单位：厘）
//					"\"cardPrice\":\"\"," +//卡费（单位：厘）
//					"\"reservaPrice\":\"\"," +//预存话费金额（单位：厘）
//					"\"productActivityInfo\":[{" +
//						"\"productId\":\"99999823\"," +//资源可选产品ID
//						"\"resourcesActivityCode\":\"89000561\"," +//已打包终端套餐对应活动编码。
//						"\"resourcesActivityPer\":\"24\"}]," +//活动协议期限，单位：月，正整数
//					"\"resourcesBrandCode\":\"AP\"," +//品牌
//					"\"orgDeviceBrandCode\":\"AP\"," +//3GESS维护品牌，当iphone时品牌与上面的一致
//					"\"resourcesBrandName\":\"Apple\"," +//终端品牌名称
//					"\"resourcesModelCode\":\"iP6128G\"," +//型号
//					"\"resourcesModelName\":\"iPhone6 128G\"," +//终端型号名称
//					"\"resourcesSrcCode\":\"1\"," +//终端来源编码
//					"\"resourcesSupplyCorp\":\"\"," +//终端供货商名称
//					"\"resourcesServiceCorp\":\"\"," +//终端服务商名称
//					"\"resourcesColor\":\"金\"," +//终端颜色
//					"\"machineTypeCode\":\"7400209171\"," +//终端机型编码
//					"\"machineTypeName\":\"Apple iPhone6 128G 金\"," +//终端机型名称
//					"\"distributionTag\":\"0\"," +//铺货标志
//					"\"resRele\":\"01\"" +//资源归属
//					",\"terminalType\":\"01\"" +//终端类别编码
//					",\"terminalTSubType\":\"\"" +//终端子类别编码
//					",\"serviceNumber\":\"\"" + //终端绑定的服务号码
//				"}]" +
			"}";
		//开户处理提交
		String jsonStr7 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"provOrderId\":\""+provOrderId+"\"," +//ESS订单交易流水为正式提交时使用
				"\"ordersId\":\""+orderId+"\"," +//外围系统订单ID
				"\"opeSysType\":\"2\"," +//办理业务系统：1：ESS2：CBSS
				
//						"\"simCardNo\":[{\"cardDataProcId\":\"\"," +//获取写卡数据业务流水
//							"\"simId\":\""+iccid+"\"," +//如果是成卡是USIM卡号，如果是白卡是ICCID
//							"\"imsi\":\""+imsi+"\"," +//新IMSI号
//							"\"cardType\":\"1\"," +//白卡类型参考附录白卡类型说明
//							"\"cardData\":\"1\"," +//白卡数据
//							"\"cardFee\":\"1\"}]," +//卡费，单位：厘
//							
				"\"feeInfo\":[{\"feeId\":\"100005\"," +//收费项编码，以省分现有编码为准
					"\"feeCategory\":\"2\"," +//收费项科目
					"\"feeDes\":\"[预存]营业厅收入(营业缴费)_普通预存款(不可清退))\"," +//收费项描述
					"\"origFee\":\"120000\"," +//应收费用正整数，单位：厘
					"\"reliefFee\":\"0\"," +//减免金额，单位：厘
					",\"reliefResult\":\"[预存]\"," +//减免原因
					",\"realFee\":\"120000\"" +//实收金额，单位：厘					 	
				 "}]," +
//				"\"invoiceNo\":\"3\" }]," +  //支付账号+ //发票号码
				"\"origTotalFee\":\"0\"," +//总费用正整数，单位：厘	
			 	"\"acceptanceReqTag\":\"1\"," + 
			 	"\"payInfo\":{\"payType\":\"15\"," +//支付方式参考附录支付方式说明现金，不填写支付机构和支付账号
				"\"payOrg\":\"工商银行\"," +//支付机构名称
				"\"payNum\":\"62229878765738\"}" +
			"}";
		//写卡数据查询
		String jsonStr8 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"cardUseType\":\"0\"," +//写卡目的：0：新开户；1：补换卡
				"\"reDoTag\":\"0\"," +//重复查询标记 0：首次读卡 1：重复读卡 2: 重复写卡
				"\"opeSysType\":\"2\"," +//办理业务系统：1：ESS2：CBSS
				//"\"procId\":\"\"," +//读卡序列（首次为空）
				//"\"activeId\":\"\"," +//交易流水（ESS生成返回,首次为空）
				"\"iccid\":\""+iccid+"\"," +//大卡卡号
				//"\"oldIccId\":\"\"," +//原白卡卡号（重复读卡必传）
				"\"numId\":\""+resourcesCode+"\"," +//手机号码
				"\"busiType\":\"32\"," +//业务类型
				"\"cardType\":\"06\"," +//白卡业务类型
				"\"userType\":\"0\""+ //用户类型
				"}";
		//写卡结果通知
		String jsonStr9 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"cardUseType\":\"0\"," +
				"\"opeSysType\":\"2\"," +//办理业务系统：1：ESS2：CBSS
				"\"errorIccid\":\"\"," +//坏卡卡号(重复写卡必传)
				"\"iccid\":\""+iccid+"\"," +//大卡卡号
				"\"imsi\":\""+imsi+"\"," +//写入的IMSI号
				"\"procId\":\""+procId+"\"," +//读卡序列
				"\"activeId\":\""+procId+"\"," +//交易流水（ESS生成返回）
				"\"reasonId\":\"0\"," +//0：写卡成功 非0则由读卡器返回的错误代码
				"\"resKindCode\":\""+resKindCode+"\"," +
				"\"capacityTypeCode\":\""+capacityTypeCode+"\"," +//卡容量
				"\"errorComments\":\"\"" +//错误说明 由写卡器返回的错误说明，如果reasonId为非0，则必填
				"}";
		//开户卡数据同步
		String jsonStr10 = "{\"operatorId\":\""+operatorId+"\"," +
				"\"province\":\""+province+"\"," +
				"\"city\":\""+city+"\"," +
				"\"district\":\""+district+"\"," +
				"\"channelId\":\""+channelId+"\"," +
				"\"channelType\":\""+channelType+"\"," +
				"\"ordersId\":\""+orderId+"\"," +//外围系统订单ID。
				"\"provOrderId\":\""+provOrderId+"\"," +//ESS订单交易流水
				"\"numId\":\""+resourcesCode+"\"," +//服务号码
				"\"opeSysType\":\"2\"," +//办理业务系统：1：ESS2：CBSS
				
				"\"simCardNo\":[{" +
					"\"cardDataProcId\":\"2015050803201183\"," +//卡号
					"\"simId\":\""+iccid+"\"," +//卡号
					"\"imsi\":\""+imsi+"\"," +//新IMSI号
					"\"cardType\":\"06\"," +//白卡类型参考附录白卡类型说明
					"\"cardData\":\""+cardData+"\"," +//白卡数据
					//"\"cardFee\":\"\"," +//卡费（实收费用）
					"\"capacityTypeCode\":\""+capacityTypeCode+"\"," +//卡容量
					"\"resKindCode\":\""+resKindCode+"\"" +//卡种类
				"}]," +//发票打印标识：0 打印发票 1 重打发票 2 补打发票 3 不打发票 注：如果需打印发票，则发票号码不能为空
				
//				"\"feeInfo\":[{" +
//					"\"feeId\":\"1\"," +//收费项编码，以省分现有编码为准
//					"\"feeCategory\":\"1\"," +//收费项科目
//					"\"feeDes\":\"1\"," +//收费项描述
//					"\"origFee\":\"1\"," +//应收费用正整数，单位：厘）
//					"\"reliefFee\":\"1\"," +//减免金额，单位：厘
//					"\"reliefResult\":\"1\"," +//减免原因
//					"\"realFee\":\"1\"" +//实收金额，单位：厘
//				"}]," +
				"\"taxBatchNo\":\"\"," +//发票批次号码
				"\"taxNo\":\"\"," +//发票号码
				"\"taxType\":\"3\""+//发票打印标识：0 打印发票 1 重打发票 2 补打发票 3 不打发票 注：如果需打印发票，则发票号码不能为空	
			"}";
		String jsonStr = "";	
		switch (num) {
			case 0:
				jsonStr = jsonStr0; 
				break;
			case 1:
				jsonStr = jsonStr1; 
				break;
			case 2:
				jsonStr = jsonStr2; 
				break;
			case 3:
				jsonStr = jsonStr3; 
				break;
			case 4:
				jsonStr = jsonStr4; 
				break;
			case 5:
				jsonStr = jsonStr5; 
				break;
			case 6:
				jsonStr = jsonStr6; 
				break;
			case 7:
				jsonStr = jsonStr7; 
				break;
			case 8:
				jsonStr = jsonStr8; 
				break;
			case 9:
				jsonStr = jsonStr9; 
				break;
			case 10:
				jsonStr = jsonStr10; 
				break;
			default:
				jsonStr = jsonStr1; 
				break;
		}
		return jsonStr;
	}
	
	
	public static void main(String[] args) throws Exception {
		
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, 270);
		String str = sdf.format(c.getTime());

		//员工信息查询
		String method0 = "ecaop.trades.check.staff.check";		
		//客户资料校验
		String method1 = "ecaop.trades.query.comm.cust.mcheck";		
		//发展人查询
		String method2 = "ecaop.trades.query.comm.devr.qry";		
		//号码查询 
		String method3 = "ecaop.trades.query.comm.simpsnres.qry";		
		//号码变更
		String method4 = "ecaop.trades.query.comm.snres.chg";			
		//终端状态查询变更		
		String method5 = "ecaop.trades.query.resi.term.chg";		
		//开户处理申请
		String method6 = "ecaop.trades.sell.mob.newu.open.app";		
		//开户处理提交
		String method7 = "ecaop.trades.sell.mob.newu.open.sub";		
		//写卡数据查询
		String method8 = "ecaop.trades.sell.mob.comm.carddate.qry";		
		//写卡结果通知
		String method9 = "ecaop.trades.sell.mob.comm.cardres.notify";		
		//开户卡数据同步
		String method10 = "ecaop.trades.sell.mob.newu.opencarddate.syn";

		String url = "http://127.0.0.1:10080/aop/test";
		String orderId = "GZLT0051111111111111";
		String operatorId = "DG000181";//GZLT0051
		String province = "51";
		String city = "580";//广州510	
		String district = "0";
		String channelId = "51a1625";//51a1625
		String channelType = "1020200";//1020200
		String developerId= "5102942675";//5102942675
		String developerName= "W增城中小企源浚公司";
		String channelIdDev = "51b1jbq";
		String custName = "测试-2015052610";
		String certType = "13";
		String certNum = "5119870325100002";
		String contactNum = "18666694758";
		String occupiedTime = "20150625235900";
		String productId = "89106072";
		String actPlanId="89000100";//总部活动编码
		String resourcesCodeMobile = "51iP6128G150306000";
		String bipType = "1";//业务类型：1：号卡类业务2：合约类业务3：上网卡4：上网本5：其它配件类6：自备机合约类业务
		String iccid ="8986011485106347000J";
		String provOrderId = "5115052262810837";
		String imsi="460010042517134";
		String procId="2015051903201331";
		String capacityTypeCode="1000101";
		String resKindCode="08";
		String cardData = "MIIBlwYJKoZIhvcNAQcDoIIBiDCCAYQCAQAxggEQMIIBDAIBADB3MHAxCzAJBgNV\nBAYTAkNOMQ4wDAYDVQQIEwVqaWxpbjESMBAGA1UEBxMJY2hhbmdjaHVuMQwwCgYD\nVQQKEwNDTkMxDjAMBgNVBAsTBUNOQ0NBMR8wHQYDVQQDExZDSElOQSBORVRDT00g\nQ0xBU1MzIENBAgM2w1cwCwYJKoZIhvcNAQEBBIGAMq0jGFnTD2ysQQjQU7wT/U/5\nXBuzvhwQ/CFkxTAh9fmY24b/mfD3IUMkIAwseEkl68hLeLgXKE4weroWY/hSGaLH\nIn8lPDLd+WcWtbiZr0VMKUm1d7rDI289hQSHLwNLS+d7ftIu4M1gLr6gEf8YIooN\n78Ic33Nej/Q+fCdy4wYwawYJKoZIhvcNAQcBMBQGCCqGSIb3DQMHBAivg6gHscmX\nXYBIbm2KM/0fzFxBVk5xVRzIyUUhaFhJWOLjHJZa0E5fD8V+3b3+YQ+vz/T0fslF\nUMwXInyJ1+RgFG6mBVi0miumWFjUzux2vp7W";
	
		String proKeyTmp = CommonDataFactory.getInstance().getActiveNo(false);
		System.out.println("proKeyTmp："+proKeyTmp);
		
		String apptx = CommonDataFactory.getInstance().getActiveNo(true);
		System.out.println("apptx："+apptx);
		request(url,
				"cmall.sub",
				method0,
				apptx,
				makeJsonStr(0, url, operatorId, province, city,district, channelId, channelType, "18566550000",						
						developerId, developerName, channelIdDev, "P512015591133434361",custName, certType, certNum, contactNum, occupiedTime,						
						productId,resourcesCodeMobile,bipType,iccid,orderId,provOrderId,imsi,capacityTypeCode,resKindCode,actPlanId,"1",
						procId,cardData));
	}
}
