package com.ztesoft.net.app.base.plugin.fileUpload.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.Consts;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class BatchAcceptExcelServlet extends HttpServlet {

    String errMsg = "";

    //"状态","status"

//	String[] orderCols = new String[]{"商品ID","goods_id","商户ID","shop_id","收货人姓名","ship_name", 
//			"收货人地址","ship_addr","收货人联系电话","ship_mobile","商品名称","goods_name","商品数量",
//			"goods_num","订单金额","order_amount","下单时间","create_time","客户名称","cust_name",
//			"证件号码（身份证）","certi_number","套餐名称","offer_name","业务号码","acc_nbr",
//			"终端名称","terminal_name","终端串码","terminal_code","订单类型(1订购、2退费、3换货、4退货)",
//			"order_type","本地网","lan_id","外系统订单编号","sec_order_id","外系统旧订单编号",
//			"old_sec_order_id","备注","comments","","",
//			"这里是注释注释注释","explain"};

    /*******************************************************************************/
    //商品配件导入
    String [] adjuncts = new String[]{"商品名称","name","商品编码(不能重复)","sn","商品图片路径","image_default","图片名","imageName",
            "市场价","mktprice","销售价","price","税率","taxes_ratio","单位","unit",
            "电信员工价","lv_id1","经销商价","lv_id2","产品介绍","specs"};
    
  //批开钱包导入
    String [] openWallet = new String[]{"手机号码","phone_num","客户姓名","cust_name","证件类型","cert_type","证件号码","cert_number"};
    
    //批充钱包导入
    String [] chargeWallet = new String[]{"充值号码","phone_num","充值金额","charge_money"};
    
    //批充话费导入
    String [] chargeBill = new String[]{"充值号码","phone_num","充值金额","charge_money"};

    //模板字段说明
//	String [] adjunctColDesc = new String[]{"","","请勿重复，否则无法导入配件","","本机配件临时图片存放目录，导入时需要上传此目录图片","","与配件相匹配的图片名称，存放在临时目录","",
//			"","","","","","","默认导入单位数量为1，如果未选择单位则默认单位为个","","","","","","",""};
    // 对应模板
    String [] adjunctContents = new String[]{"模板数据商品名称","","jk_08363088","","C:\\imageData\\images","","xx1.jpg","",
            "99","","98","","-1","","1","","80","","85","","产品描述信息",""};
    /*******************************************************************************/

    // 批开钱包对应模板
    String [] openWalletContents = new String[]{"17034523454(样板数据)","","小芳(样板数据)","","1(样板数据)","","430231231231312313(样板数据)",""};

    // 批充钱包对应模板
    String [] chargeWalletContents = new String[]{"17034523454(样板数据)","","100.00(样板数据)",""};
    
    // 批充话费对应模板
    String [] chargeBillContents = new String[]{"17034523454(样板数据)","","100.00(样板数据)",""};
    /*******************************************************************************/
    //批量资料返档列名
    String[] custCols = new String[]{"客户名称","cust_name","证件类型","certi_type","证件号码","certi_number",
            "业务号码","acc_nbr","本地网","lan_id","联系地址","ship_addr","","",
            "注释：证件类型输入如：本地身份证 A、军人证 B、户口簿 C、单位公章证明 D、" +
                    "工商执照 E、教师证 F、外地身份证 G、香港身份证 H、澳门身份证 I、其他 P","explain"};
    //对应模板
    String[] custContents = new String[]{"(样板数据)张三一","","P","","430721198805140024","",
            "18907492564","","731","","湖南省长沙市芙蓉区荷花园大厦01","","","","",""};
    /*******************************************************************************/

    /*******************************************************************************/
    //合约机导出列名 (1订购、2退费、3换货、4退货)
    String[] contractOrderCols = new String[]{"商品ID","goods_id","商户ID","shop_id","收货人姓名",
            "ship_name", "收货人地址","ship_addr","收货人联系电话","ship_mobile","订单金额",
            "order_amount","客户名称","cust_name","证件号码（身份证）","certi_number","套餐名称",
            "offer_name","业务号码","acc_nbr","终端名称","terminal_name","终端串码","terminal_code",
            "外系统订单编号","sec_order_id","备注","comments","","",
            "注释：商品ID填写分销系统的goods_id；商户id对应分销商的店铺编号；终端串码填写终端机子上面的编码","explain"};
    //对应模板
    String[] contractOrderContents = new String[]{"(样板数据)201305194579000548","","WHJ","","张三一","",
            "湖南省长沙市芙蓉区荷花园大厦01","","18984535342","","100","","李四一","",
            "430721198805140000","","galaxy4s合约套餐（乐享3G上网版-129元档）","","13348615550","",
            "小米2S","","mi235253","","13345745457","","无","","","","",""};
    /*******************************************************************************/

    /*******************************************************************************/
    //云卡 资料返单导出列名称
    String[] cloudOrderCols = new String[]{"商品ID","goods_id","商户ID","shop_id","订单金额",
            "order_amount","客户名称","cust_name","证件号码（身份证）","certi_number",
            "套餐名称","offer_name","业务号码","acc_nbr","收货人姓名","ship_name","收货人地址",
            "ship_addr","收货人联系电话","ship_mobile","备注","comments","","",
            "注释：商品ID填写分销系统的goods_id；商户id对应分销商的店铺编号","explain"};
    //对应模板
    String[] cloudOrderContents = new String[]{"(样板数据)201305177889000495","","36R36","","123.00","","张三一","",
            "43012219631120001X","","乐享49云卡_ocs","","18904573563","","李四一","","湖南省长沙市芙蓉区荷花园大厦01",
            "","18984535342","","无","","","","",""};
    /*******************************************************************************/

    /*******************************************************************************/
    //云卡 资料返单 (带终端)导出列名称
    String[] cloudwithLanOrderCols = new String[]{"商品ID","goods_id","商户ID","shop_id","订单金额",
            "order_amount","客户名称","cust_name","证件号码（身份证）","certi_number",
            "套餐名称","offer_name","业务号码","acc_nbr","终端名称","terminal_name",
            "终端串码","terminal_code","收货人姓名","ship_name","收货人地址",
            "ship_addr","收货人联系电话","ship_mobile","备注","comments","","",
            "注释：商品ID填写分销系统的goods_id；商户id对应分销商的店铺编号；终端串码填写终端机子上面的编码","explain"};
    //对应模板
    String[] cloudwithLanOrderContents = new String[]{"(样板数据)201305102723000446","","BXW","","210.00 ","","张三一","",
            "43012419790627957X","","乐享3G上网版-129元档","","13345745457","","小米2S","","mi235253","","李四一","",
            "湖南省长沙市芙蓉区荷花园大厦01","","18984535342","","无","","","","",""};
    /*******************************************************************************/

    /*******************************************************************************/
    //云卡导入
    String[] cloudCols = new String[]{"销售品名称","offer_name","销售品id","offer_id","批开金额","pay_money",
            "业务号码","phone_num","UIM卡号","uim","本地网id","lan_id","","",
            "注释：销售品ID和销售品名称填写CRM系统的套餐id和名称；支付金额填写批开云卡的预存款费用；本地网id参考长沙：731，湘潭：732，株洲：733等","explain"};
    //对应模板
    String[] cloudContents = new String[]{"(样板数据)乐享49云卡_ocs","","1151417","",
            "50","","15373105271","","8986092436363423","","731","","","","",""};
    /*******************************************************************************/

    /*******************************************************************************/
    //充值卡和流量卡
    String[] cardCols = new String[]{"卡编码","card_code","卡密码","card_password","卡金额",
            "card_price","","",
            "这里是注释注释注释","explain"};
    String[] cardContents = new String[]{};
    /*******************************************************************************/

    /*******************************************************************************/
    //号码导入
    String[] phoneNumCols = new String[]{"号码","num_code","本地网编码","area_code"
            ,"最低消费","min_consume","最低消费月数","min_month","用途","use_type",
            "号码等级","acc_level","预存金","pre_cash","","",
            "注释：本地网编码输入如：长沙 731 湘潭 732 株洲 733等；用途：00A 网厅 00B分销商","explain"};
    //对应模板
    String[] phoneNumContents = new String[]{"(样板数据)18007310001","","731","",
            "0","","0","","00B(00A 网厅 00B分销商)","","1","","500","","","","",""};
    /*******************************************************************************/

    /*******************************************************************************/
    //商品批量导入-合约机
    String[] cmCols = new String[]{"活动编码","活动名称","活动描述","活动期限","活动类型编码值","优惠购机方案","预存金额","首月返还","按月返还金额",
    		"备注","产品编码","产品名称","产品描述","产品品牌描述","付费类型","网别","合约费用","机型名称","机型编码","品牌","品牌编码","型号","型号编码","颜色","颜色编码","资源类别名称"};
    
    String[] cmContents = new String[]{"20140704101539040119","30个月智能终端预存话费送手机","本活动适用于：WCDMA(3G)-186元基本套餐B。","30","4","00","2810000","281000","84000","20140707新增",
    		"99003580","B类3G基本套餐186元档","套餐月费186元，套餐包含国内语音拨打分钟数1180分钟，国内流量150MB，国内接听免费（含可视电话）。套餐超出后，国内语音拨打0.15元/分钟，国内流量0.0003元/KB，国内可视电话拨打0.60元/分钟。套餐赠送多媒体内容20个M、文本内容40个T、国内可视电话拨打分钟数20分钟，以及来电显示和手机邮箱，其他执行标准资费。",
    		"3G手机","后付费","3G","4699000","HTCM8w月光银","7400208530","HTC","HT","M8w","M8w","月光银","9809120800036795","手机"};
    
    String goodsExplaination = "导入商品的类型以“活动类型编码值”列的值区分，3为号卡，4为存费送机，5为购机送费；预存金额、按月返还金额和合约费用的单位是厘。";
    
    /*******************************************************************************/
    
    /*******************************************************************************/
    //小区批量导入
    String[] comCols = new String[]{"地市ID","地市","行政区域ID","行政区域","县分公司ID","县分公司","小区编号","小区名称","标准地址","网格"};
    
    String[] comContents = new String[]{"A","杭州","16","富阳市","AFY","富阳县分公司","4574104","龙舍里","浙江省杭州地区富阳市巨利村龙舍里","城北网格（富阳）"};
    
    
    /*******************************************************************************/
    
    /*******************************************************************************/
    //活动批量导入
    String[] actCols = new String[]{"产品类型","资费类别","活动类别","政策名称","终端来源","商品名称","终端品牌","终端型号","政策有效期","原价","促销价/结算价",
    		"号码优惠","礼品赠送政策","电渠专属优惠","适用套餐","适用渠道","适用地市","其他优惠","备注"};
    
    String[] actContents = new String[]{"合约机","3G后付费","存费送机","2230061201","定制机","ZTE中兴V5S白","中兴","V5S","2014/08/25-2014/08/26","1599","1299",
    		"减免五类靓号费","嘉荣购物卡面值100元","3G-20元预付费套餐","96","vip商城、沃货架","全省","",""};
    
    /*******************************************************************************/
    
    //联通商品系统活动批量导入
    String[] actColsEcs = new String[]{"活动ID","活动名称","生效标识","用户类型","活动类型","金额","货品包名称","套餐分类","最低套餐档次","最高套餐档次","活动地市","活动修改生效时间","活动商城",
    		"活动有效期","活动内容","直降转兑包","赠送业务","礼品","靓号减免类型"};
    
    String[] actContentsEcs = new String[]{"123","iphone6合约机","失效","新用户","直降","100","test号卡0724001","3GA|3GB","1","9999","全省","2014/10/18","vip商城",
    		"2014/10/15 14:02:15-2014/10/20 04:57:08","iphone6合约机","692014032725593","692014032725593","692014032725593","一类|二类"};
  
    String[] actExpsEcs = new String[]{"填写活动对应的ID，新增活动为空（非必填）","活动的中文名称(必填)","生效或失效（必填）","新用户或老用户(非必填)","可选值包括直降、溢价","折扣(8折填0.8)；直降填减免掉的金额，价格单位为元(必填)","必填，可以有多个货品包名称，以“|”分隔",
    		"可选值包括3GA、3GB、3GC，可选择多个，以“|”分隔","活动适用的最低套餐档次，填数字","活动适用的最高套餐档次，填数字","活动适用的地市名称，可选全省、单个地市或者多个地市(必填,多个地市以“|”分隔)","本次活动修改的生效时间，日期格式为YYYY/MM/DD(必填),格式要求为文本",
    		"活动适用的商城名称，可填多个，以“|”分隔(必填)",
    		"活动的起始日期和结束日期，格式为YYYY/MM/DD hh:mm:ss—YYYY/MM/DD hh:mm:ss(必填)，格式要求为文本","活动内容介绍(必填)","直降转兑包的货品sku，可有多个，以“|”分隔","赠送的附加产品的货品sku，可有多个，以“|”分隔","赠送的有价卡、实物、虚拟卡券等的货品sku，可有多个，以“|”分隔",
    		"活动减免哪些类型的靓号费，可选值包括一类、二类、三类、四类、五类、+1类、+2类、+3类、+4类、+5类，可有多个，以“|”分隔"};
    
    /*******************************************************************************/
    //号码批量导入
    String[] numCols = new String[]{"序号","手机号码","网别","预存款","合约期","最低消费额","付费方式","减免金额"};
    
    String[] numContents = new String[]{"1","18622220006","3G","1798","24","89","预付费","0"};
    
    //联通商品号码批量导入
    String[] numColsEcs = new String[]{"序号","手机号码","网别","预存款","合约期","最低消费额","付费方式","减免金额","号码分组编码"};
    
    String[] numContentsEcs = new String[]{"1","18622220006","3G","1798","24","89","预付费","0","TEST"};
    
    //广东联通转兑包批量导入
    String[] zdbColsEcs = new String[]{"名称","转兑包类型","BSS编码","转兑包网别","调价额度（元）"};
    
    String[] zdbContentEcs  = new String[]{"iphone5s直降500","直降转兑包","81017516","3G","500"};
    
    String[] zdbExpsEcs = new String[]{"必填","必填，可选值包括直降转兑包与流量转兑包","必填，可选值包括4G,3G,2G","BSS的优惠活动编码，3G转兑包必填","单位为元,直降转兑包必填"};
    /*******************************************************************************/
    
    //终端批量导入
    String[] termLqyCols = new String[]{"MAC地址","终端名称","终端品牌","终端型号"};
    
    String[] termLqyContents = new String[]{"AC:22:0B:4B:3C:ED","小米盒子","xiaomi","xm001"};
    
    /*******************************************************************************/
    
    /*******************************************************************************/
    //手机货品导入
    String[] termCols = new String[]{"品牌","型号","颜色","是否是4G","手机终端标识","手机终端编码","手机终端品牌","手机终端序列","手机终端型号",
    		"手机终端颜色","手机终端外形","系统类型","手机终端系统","网络制式",
    		"数据业务","浏览器","机身内存","可扩展内存","屏幕尺寸","是否支持宽屏","屏幕类型","分辨率","触摸屏","屏幕色彩","音乐播放","视频播放","是否支持","电子书","录音",
    		"彩信功能","是否Office功能","摄像头","副摄像头","录像","传感器类型","变焦模式","是否支持GPRS","是否支持蓝牙","电池配置","电池容量","理论通话时间","理论待机时间","超长待机","大图","小图",
    		"机身重量","特性","特色功能","增值业务","机身尺寸","描述","说明","电池类型","备注","RESERVED1","RESERVED2","协议价格","商城产品CODE","商城型号CODE","商城颜色CODE",
    		"手机操作系统版本","CPU信息","RAM信息","运行时内存","是否双卡双待","终端显示类型","是否大屏"};
    
    String[] termContents = new String[]{"小米","小米3","蓝","","4014010900063905","7400208269","XM","","MI3","9809120800036807","00","01","安卓",
    		"GSM 850/900/1800/1900MHz、UMTS 850/900/1900/2100MHz","上行峰值：5.76Mbps 下行峰值：42Mbps","支持","16GB","16","5.0英寸","00","13","1920*1080",
    		"01","1600万色","支持MP3/AAC/AMR/OGG/MP4等格式","支持MP4/3GP/3GP2/WAV/MP4等格式","00","01","01","01","01","1300万像素AF ","200万像素","01","-",
    		"-","01","01","","3050mAh","3G：25小时 2G：5小时","3G：待机：500小时  2G：待机：500小时","-","","","145g","","","","144*73.6*8.1mm","","单电单充",
    		"-","","","","","XM","MI3","9809120800036807","4.3","高通骁龙800 8274AB 四核2.3GHz","-","2GB","DOUBLE_CARD_N","","IS_LARGE_SCN_Y"};
    
    /*******************************************************************************/
    
    // 物流信息批量导入  GL
    String[] logiTerm = new String[] {"订单编号","物流公司编码","物流单号","备注"};
    
    String[] logiContents= new String[] {"D90000000002332600","EMS0001","3102457922705","备注信息"};
    
    String[] logiTermCompany = new String[] {"物流编码","物流公司"};
    
    String[] logiTermCompanyCode = new String[] {"EMS0001","SF-FYZQYF","WBSW","WZTW","WYTW","WYDW","WTTW"};
    String[] logiTermCompanyName = new String[] {"全球邮政特快专递","顺丰速递","百世汇通","中通快递","圆通快递","韵达快递","天天快递"};
    
    /*******************************************************************************/
    //不能重复的字段
    String[] custCampare = new String[]{"acc_nbr"};
    String[] cloudCampare = new String[]{"phone_num","uim"};
    String[] cardCampare = new String[]{"card_code"};
    String[] phoneNumCampare = new String[]{"num_code"};
    String[] orderCompare = new String[]{"acc_nbr"};
    /*******************************************************************************/

    /**************add by zheng.dong--zj*****************************************************************/
	String[] uploadPhotoInfo = new String[] { "客户手机号码", "phone_num",
			"身份证正面图片名称", "img_ddr1", "身份证反面图片名称", "img_ddr2", "证件合影图片名称",
			"img_ddr3" };

	String[] uploadPhotoInfoContents = new String[] { "17011111111(样板数据)", "",
			"17011111111_1.jpg(样板数据)", "", "17011111111_2.jpg(样板数据)", "",
			"17011111111_3.jpg(样板数据)", "" };
	/*******************************************************************************/    
    
	/**************add by zheng.dong--zj*****************************************************************/
	String[] smsBatchSend = new String[] { "手机号码", "phone_num",
			"短信内容", "sms_content"};

	String[] smsBatchSendContents = new String[] { "17011111111(样板数据)", "",
			"短信发送内容TEST(样板数据)", ""};
	/*******************************************************************************/   
	
	/**************add by zengxianlian*****************************************************************/
	String[] terminalCode = new String[] { "机型编码", "串号"};

	String[] terminalCodeContents = new String[] { "17011111111(样板数据)","17011111111(样板数据)"};
	/*******************************************************************************/   

    private IDaoSupport daoSupport =  (IDaoSupport)SpringContextHolder.getBean("daoSupport");
    public BatchAcceptExcelServlet() {
        super();
    }

    @Override
	public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lx = request.getParameter("lx") == null ? "" : request.getParameter("lx");
        if (lx.equals("mb")) {
            exportExcel(response, request);
        } else {
            doGet(request, response);
        }

    }

    // 导出模板单
    public boolean exportExcel(HttpServletResponse response, HttpServletRequest request) {
        OutputStream os =null;
        WritableWorkbook wbook = null;
        String service = request.getParameter("service") == null ?"":request.getParameter("service");
        String order_type = request.getParameter("order_type") == null ?"":request.getParameter("order_type");

        String fileTitle = "";// 标题

        try {
            String title = "";
            String content = "";
            String explaination = "";
            String explainationLogi="";
//			String content2="";
            if(service.equals(Consts.BATCH_CUST)){
                for(int i = 0; i < custCols.length; i+=2 ){
                    title += custCols[i] + ",";
                    content += custContents[i] + ",";
                }
                fileTitle = "批量资料返档模板单(请参考注释填写)";
            }else if(service.equals(Consts.BATCH_ADJUNCT)){
                for(int i = 0; i < adjuncts.length; i+=2 ){
                    title += adjuncts[i] + ",";
                    content += adjunctContents[i] + ",";
//					content2 += adjunctColDesc[i] + ",";
                }
                fileTitle = "商品配件导入模板(请参考注释填写)";
            }else if(service.equals(Consts.BATCH_OPEN_WALLET)){
                for(int i = 0; i < openWallet.length; i+=2 ){
                    title += openWallet[i] + ",";
                    content += openWalletContents[i] + ",";
//					content2 += adjunctColDesc[i] + ",";
                }
                fileTitle = "批开钱包导入模板(请参考注释填写)";
            }else if(service.equals(Consts.BATCH_CHARGE_WALLET)){
                for(int i = 0; i < chargeWallet.length; i+=2 ){
                    title += chargeWallet[i] + ",";
                    content += chargeWalletContents[i] + ",";
//					content2 += adjunctColDesc[i] + ",";
                }
                fileTitle = "批充钱包导入模板(请参考注释填写)";
            } else if(service.equals(Consts.BATCH_ORDER)){
                if(Consts.BATCH_ORDER_CONTRACT.equals(order_type)){
                    for(int i = 0; i < contractOrderCols.length; i+=2 ){
                        title += contractOrderCols[i] + ",";
                        content += contractOrderContents[i] + ",";
                    }
                    fileTitle = "合约卡导入模板单(请参考注释填写)";
                }else if(Consts.BATCH_ORDER_CUSTRETURNRD.equals(order_type)){
                    for(int i = 0; i < cloudOrderCols.length; i+=2 ){
                        title += cloudOrderCols[i] + ",";
                        content += cloudOrderContents[i] + ",";
                    }
                    fileTitle = "云卡导入模板单(请参考注释填写)";
                }else if(Consts.BATCH_ORDER_CUSTRETURNRD_LAN.equals(order_type)){
                    for(int i = 0; i < cloudwithLanOrderCols.length; i+=2 ){
                        title += cloudwithLanOrderCols[i] + ",";
                        content += cloudwithLanOrderContents[i] + ",";
                    }
                    fileTitle = "云卡带终端导入模板单(请参考注释填写)";
                }
            }else if(Consts.BATCH_CLOUD.equals(service)){
                for(int i = 0; i < cloudCols.length; i+=2 ){
                    title += cloudCols[i] + ",";
                    content += cloudContents[i] + ",";
                }
                fileTitle = "云卡导入模板单(请参考注释填写)";
            }else if(Consts.BATCH_CARD.equals(service)){
                for(int i = 0; i < cardCols.length; i+=2 ){
                    title += cardCols[i] + ",";
                    content += cardContents[i] + ",";
                }
                fileTitle = "充值卡流量卡导入模板单(请参考注释填写)";
            }else if(Consts.BATCH_PHONENUM.equals(service)){
                for(int i = 0; i < phoneNumCols.length; i+=2 ){
                    title += phoneNumCols[i] + ",";
                    content += phoneNumContents[i] + ",";
                }
                fileTitle = "号码导入模板单(请参考注释填写)";
			} else if ("upload_photo_info".equals(service)) {
				for (int i = 0; i < uploadPhotoInfo.length; i += 2) {
					title += uploadPhotoInfo[i] + ",";
					content += uploadPhotoInfoContents[i] + ",";
				}
				fileTitle = "补传身份证照片信息导入模板单(请参考注释填写)";
			} else if("cm".equals(service)){
				for(int i=0;i<cmCols.length;i++){
					title += cmCols[i]+",";
					content += cmContents[i]+",";
				}
				fileTitle = "商品批量导入模板";
				explaination = goodsExplaination;
			}else if("community".equals(service)){
				for(int i=0;i<comCols.length;i++){
					title += comCols[i]+",";
					content += comContents[i]+",";
				}
				fileTitle = "小区批量导入模板";
			}else if("activity".equals(service)){
				for(int i=0;i<actCols.length;i++){
					title += actCols[i]+",";
					content += actContents[i]+",";
				}
				fileTitle = "活动批量导入模板";
			}else  if("activityEcs".equals(service)){
				//联通商品系统活动导入专用模板
				for(int i=0;i<actColsEcs.length;i++){
					title += actColsEcs[i]+",";
					content += actContentsEcs[i]+",";
				}
				fileTitle = "活动批量导入模板";
			} 			
			else if("numero".equals(service)){
				for(int i=0;i<numCols.length;i++){
					title += numCols[i]+",";
					content += numContents[i]+",";
				}
				fileTitle = "号码批量导入模板";
			} else if("numeroEcs".equals(service)){
				//联通商品号码批量导入专用模板
				for(int i=0;i<numColsEcs.length;i++){
					title += numColsEcs[i]+",";
					content += numContentsEcs[i]+",";
				}
				fileTitle = "号码批量导入模板";
			}else if("zdbsEcs".equals(service)){
				//联通商品转兑包批量导入模版
				for(int i=0;i<zdbColsEcs.length;i++){
					title +=zdbColsEcs[i]+",";
					content +=zdbContentEcs[i]+",";
				}
				fileTitle = "转兑包批量导入模板";
			}
			else if("terminalLqy".equals(service)){
				for(int i=0;i<termLqyCols.length;i++){
					title += termLqyCols[i]+",";
					content += termLqyContents[i]+",";
				}
				fileTitle = "终端导入模板";
			}else if("terminal".equals(service)){
				for(int i=0;i<termCols.length;i++){
					title += termCols[i]+",";
					content += termContents[i]+",";
				}
				fileTitle = "手机货品导入模板";
			}else if ("sms_batch_send".equals(service)) {
				for (int i = 0; i < smsBatchSend.length; i += 2) {
					title += smsBatchSend[i] + ",";
					content += smsBatchSendContents[i] + ",";
				}
				fileTitle = "短信批量发送导入模板单(请参考注释填写)";
			}else if ("terminalCodeImport".equals(service)) {
				for (int i = 0; i < terminalCode.length; i++) {
					title += terminalCode[i] + ",";
					content += terminalCodeContents[i] + ",";
				}
				fileTitle = "虚拟串号导入模板";
			}else if(service.equals(Consts.BATCH_CHARGE_BILL)){
                for(int i = 0; i < chargeBill.length; i+=2 ){
                    title += chargeBill[i] + ",";
                    content += chargeBillContents[i] + ",";
//					content2 += adjunctColDesc[i] + ",";
                }
                fileTitle = "批充话费导入模板(请参考注释填写)";
            }else if(service.equals(Consts.BATCH_LOGI)) {
            	  for(int i = 0; i < logiTerm.length; i++){
                      title += logiTerm[i] + ",";
                      content += logiContents[i] + ",";
                  }
                  fileTitle = "宁波号卡物流信息批量导入更新_模板(请参考注释填写)";
                  explainationLogi="物流公司编码说明";
            }

            title = title.substring(0, title.length() -1);
            title = new String(title.getBytes("gb2312"), "gbk");

            content = content.substring(0, content.length() -1);
            content = new String(content.getBytes("gb2312"), "gbk");

            String titles[] = title.split(",");
            String contents[] = content.split(",");

            String fileName = new String(fileTitle.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
            os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型

            wbook = Workbook.createWorkbook(os); // 建立excel文件
            WritableSheet wsheet = wbook.createSheet(fileTitle, 0); // sheet名称
           
            
            // 设置excel标题
            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(jxl.format.Colour.AQUA);
            wcfFC = new WritableCellFormat(wfont);
            
            //添加模板填写说明
            if(!StringUtils.isEmpty(explaination)){
            	 WritableSheet wsheet2 = wbook.createSheet("说明", 1); // sheet名称
            	 wsheet2.addCell(new Label(0, 0, "说明", wcfFC));
                 wsheet2.addCell(new Label(1, 0, explaination, wcfFC));
            }
            
            // 开始生成主体内容
            for (int i = 0; i < titles.length; i++) {
                wsheet.addCell(new Label(i, 0, titles[i], wcfFC));
                wsheet.setColumnView(i, 23);// 设置宽度为23
                wsheet.setRowView(0, 340);//设置标题行高
            }

            // 设置excel标题
            WritableFont cfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
            WritableCellFormat ccfFC = new WritableCellFormat(cfont);
            ccfFC.setBackground(jxl.format.Colour.AQUA);
            ccfFC = new WritableCellFormat(cfont);

//			outSheet.mergeCells(1, 1, 1, 5); //合并单元格，参数格式（开始列，开始行，结束列，结束行） 
//			if(content2!=""){
//				content2 = content2.substring(0, content.length() -1);			
//				content2 = new String(content2.getBytes("gb2312"), "gbk");
//				String contents2[] = content2.split(",");
//				
//				WritableFont dfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
//						UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
//				WritableCellFormat ccfFC2 = new WritableCellFormat(cfont);
//				ccfFC.setBackground(Colour.AQUA);
//				ccfFC = new WritableCellFormat(cfont);
//				
//				for (int i = 0; i < contents2.length; i++) {
//					wsheet.addCell(new Label(i, 1, contents2[i], ccfFC2));
//					wsheet.setColumnView(i, 23);// 设置宽度为23
//				}
//			}

            for (int i = 0; i < contents.length; i++) {
                wsheet.addCell(new Label(i, 1, contents[i], ccfFC));
                wsheet.setColumnView(i, 23);// 设置宽度为23
            }
            
            if("activityEcs".equals(service)){
            	  WritableSheet wsheet2 = wbook.createSheet("活动填写说明", 1); 
            	  for (int i = 0; i < titles.length; i++) {
                      wsheet2.addCell(new Label(i, 0, titles[i], wcfFC));
                      wsheet2.setColumnView(i, 45);// 设置宽度
                      wsheet2.setRowView(0, 340);
                  }
            	  for (int i = 0; i < actExpsEcs.length; i++) {
                      wsheet2.addCell(new Label(i, 1, actExpsEcs[i], ccfFC));
                      wsheet2.setColumnView(i, 23);
                  }
              }else if("zdbsEcs".equals(service)){
            	  WritableSheet wsheet2 = wbook.createSheet("转兑包填写说明", 1); 
            	  for (int i = 0; i < titles.length; i++) {
                      wsheet2.addCell(new Label(i, 0, titles[i], wcfFC));
                      wsheet2.setColumnView(i, 45);// 设置宽度
                      wsheet2.setRowView(0, 340);
                  }
            	  for (int i = 0; i < zdbExpsEcs.length; i++) {
                      wsheet2.addCell(new Label(i, 1, zdbExpsEcs[i], ccfFC));
                      wsheet2.setColumnView(i, 23);
                  }
              }else if(service.equals(Consts.BATCH_LOGI)) {
            	  WritableSheet wsheet2 = wbook.createSheet(explainationLogi, 1); 
            	  for (int i = 0; i < logiTermCompany.length; i++) {
                      wsheet2.addCell(new Label(i, 0, logiTermCompany[i], wcfFC));
                      wsheet2.setColumnView(i, 23);// 设置宽度
                      wsheet2.setRowView(0, 340);
                  }
            	  for (int i = 0; i < logiTermCompanyCode.length; i++) {
            		 wsheet2.addCell(new Label(0, i+1,logiTermCompanyCode[i] , ccfFC));
            		 wsheet2.setColumnView(i, 23);
                  }
            	  for (int i = 0; i < logiTermCompanyName.length; i++) {
             		 wsheet2.addCell(new Label(1, i+1,logiTermCompanyName[i] , ccfFC));
             		 wsheet2.setColumnView(i, 23);
                   }
              }

            // 主体内容生成结束
            wbook.write(); // 写入文件
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }finally {
            if(wbook != null) {
                try {
                    wbook.close();
                } catch (WriteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if(os != null) {
                try {
                    os.close(); // 关闭流
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        FileUpload fupload = null;
//        InputStream is = null;
//        String url = "";
//        String msg = "";
//        String flag = "1";
//        fupload = new FileUpload();
//        url = (String) request.getParameter("url");
//        BatchResult batchResult = null;
//        List inList = null;
//        String service = request.getParameter("service") == null ?"":request.getParameter("service");
//        String fileType = request.getParameter("fileType") == null ?"":request.getParameter("fileType");
//        String order_type = request.getParameter("order_type") == null ?"":request.getParameter("order_type");
//        String goods_info = request.getParameter("goods_info") == null ?"":request.getParameter("goods_info");
//        String goods_id = request.getParameter("goods_id") == null ?"":request.getParameter("goods_id");
//        // 上传失败时，直接退出,不进行数据的插入
//        try {
//            fupload.load((HttpServletRequest) request);
//            is = fupload.getIS("uploadFile");
//
//            Map inMap = new HashMap();
//
//            if("xls".equals(fileType) || "".equals(fileType)){
//                //解析excel文件
//                inMap = iStreamToArrayListEx(is,service);
//            }else if("txt".equals(fileType)){
//                //解析txt文件
//                inMap = iStreamToArrayListTxt(is,goods_info);
//            }
//
//
//
//            inList = (List) inMap.get("list");
//            String inMsg = (String) inMap.get("msg");
//
//            if (inList == null && StringUtils.isNotEmpty(errMsg)) {
//                msg = "文件上传失败：" + errMsg;
//                flag = "-1";
//                forwardJsp(request, response, url, msg, flag);
//                try {
//
//                } catch (Exception e) {
//                    fupload.fileClose();
//                }
//                return;
//            }
//            if ((inList != null && inList.size() ==0) && StringUtils.isEmpty(inMsg)) {
//
//                if(StringUtils.isEmpty(errMsg))
//                    errMsg="导入模板数据无效，请检查！";
//                msg = "文件上传失败：" + errMsg;
//                flag = "-1";
//                forwardJsp(request, response, url, msg, flag);
//                try {
//
//                } catch (Exception e) {
//                    fupload.fileClose();
//                }
//                return;
//            }
//
//            if(!StringUtils.isEmpty(inMsg)){
//                if(Consts.MSG_NULL.equals(inMsg)){
//                    errMsg = "导入模板中存在空数据，请检查！";
//                }else if(Consts.MSG_DUPICATE.equals(inMsg)){
//                    errMsg = "导入模板中存在重复数据，请检查！";
//                }else if(Consts.MSG_WRONG_TYPE.equals(inMsg)){
//                    errMsg = "拆分txt数据出错！";
//                }else if(Consts.MSG_WRONG_EXCEL.equals(inMsg)){
//                    errMsg = "导入模板有误，请下载模板后重新检查！";
//                }else if(Consts.MSG_WRONG_GOOD.equals(inMsg)){
//                    errMsg = "导入商品与所选商品面值不匹配，请重新选择！";
//                }
//
//                msg = "文件上传失败：" + errMsg;
//                flag = "-1";
//                forwardJsp(request, response, url, msg, flag);
//                try {
//
//                } catch (Exception e) {
//                    fupload.fileClose();
//                }
//                return;
//            }
//
//            //验证导入数据中是否存在数据库已存在记录
//            int count = 0;
//
//            if(Consts.BATCH_CLOUD.equals(service)){
//                ICloudManager cloudManager = (ICloudManager) SpringContextHolder.getBean("cloudManager");
//                count = cloudManager.isExistsCloud(inList);
//            }else if(Consts.BATCH_CARD.equals(service)){
//                CardInf cardManager = (CardInf) SpringContextHolder.getBean("cardServ");
//                //ICardManager cardManager = (ICardManager) SpringContextHolder.getBean("cardManager");
//                String cardType = (String) request.getSession().getAttribute("type_code");
//                if("xls".equals(fileType)){
//                    count = cardManager.isExistsExCard(inList, cardType);
//                }else{
//                    inMap.put("cardType", cardType);
//                    count = cardManager.isExistsTxtCard(inMap);
//                }
//            }else if(Consts.BATCH_PHONENUM.equals(service)){
//                IAccNbrManager accNbrManager = (IAccNbrManager) SpringContextHolder.getBean("accNbrManager");
//                count = accNbrManager.isExistsPhoneNo(inList);
//            }else if(Consts.BATCH_ORDER.equals(service)){
//                IOrderOuterManager orderOuterManager = (IOrderOuterManager)SpringContextHolder.getBean("orderOuterManager");
//                count = orderOuterManager.isExistsOrder(inList);
//            }else if(Consts.BATCH_ADJUNCT.equals(service)){//商品配件导入
//                GoodsAdjunctInf goodsAdjunctServ=(GoodsAdjunctInf)SpringContextHolder.getBean("goodsAdjunctServ");
//                count = goodsAdjunctServ.isExistsAjdunct(inList);
//            }
//
//            if(count > 0){
//                errMsg = "已存在" + count + "条记录，请检查后重新导入！";
//
//                msg = "文件上传失败：" + errMsg;
//                flag = "-1";
//                forwardJsp(request, response, url, msg, flag);
//                try {
//
//                } catch (Exception e) {
//                    fupload.fileClose();
//                }
//                return;
//            }
//
//            int oneBatch = 1000;
//            if(!inList.isEmpty() && inList.size() > oneBatch){
//
//                msg = "单次导入超出 配置的最大限度"+oneBatch+"条，请减少上传数量,上传失败。";
//                flag = "-1";
//                forwardJsp(request, response, url, msg, flag);
//                try {
//
//                } catch (Exception e) {
//                    fupload.fileClose();
//                }
//                return;
//            }
//            if(service.equals(Consts.BATCH_CUST)){
//                ICustReturnedManager custReturnedManager = (ICustReturnedManager)SpringContextHolder.getBean("custReturnedManager");
//                batchResult = custReturnedManager.batchReturned(inList);		//批量资料反档
//            }else if(service.equals(Consts.BATCH_ORDER)){
//                IOrderOuterManager orderOuterManager = (IOrderOuterManager)SpringContextHolder.getBean("orderOuterManager");
//                batchResult = orderOuterManager.importOrd(inList,(String) inMap.get("order_type"));
//            }else if (Consts.BATCH_CLOUD.equals(service)){
//                ICloudManager cloudManager = (ICloudManager) SpringContextHolder.getBean("cloudManager");
//                batchResult = cloudManager.importCloud(inList);
//            }else if(Consts.BATCH_CARD.equals(service)){
//                CardInf cardManager = (CardInf) SpringContextHolder.getBean("cardServ");
//                //	ICardManager cardManager = (ICardManager) SpringContextHolder.getBean("cardManager");
//                String cardType = (String) request.getSession().getAttribute("type_code");
//                if("xls".equals(fileType)){
//                    batchResult = cardManager.importCard(inList, cardType);
//                }else{
//                    inMap.put("cardType", cardType);
//                    batchResult = cardManager.importCardByTxt(inMap);
//                }
//            }else if(Consts.BATCH_PHONENUM.equals(service)){
//                IAccNbrManager accNbrManager = (IAccNbrManager) SpringContextHolder.getBean("accNbrManager");
//                batchResult = accNbrManager.importPhoneNum(inList);
//            }else if(Consts.BATCH_ADJUNCT.equals(service)){
//			/*	IGoodsImportManager goodsImportManager = (IGoodsImportManager) SpringContextHolder.getBean("goodsImportManager");
//				batchResult = goodsImportManager.importAdjunct(inList,goods_id);*/
//                GoodsAdjunctInf goodsAdjunctServ=(GoodsAdjunctInf)SpringContextHolder.getBean("goodsAdjunctServ");
//                batchResult = goodsAdjunctServ.importAdjunct(inList,goods_id);
//            }else {
//
//                throw new RuntimeException("service:" + service + "未定义");
//            }
//
//            request.setAttribute("batchId", batchResult.getBatchId());
//            request.setAttribute("sucNum", batchResult.getSucNum());
//            request.setAttribute("failNum", batchResult.getFailNum());
//            request.setAttribute("errMsg", batchResult.getErr_msg());
//
//            boolean isHavingMsg = true;
//            if(!StringUtils.isEmpty(batchResult.getErr_msg())){
//                isHavingMsg = false;
//            }
//
//            if(Consts.BATCH_CUST.equals(service)){
//                if(isHavingMsg){
//                    msg = "本次成功返档：" + batchResult.getSucNum() + "条，失败：" + batchResult.getFailNum() + "条";
//                }else{
//                    msg = "返档失败：" + batchResult.getErr_msg();
//                }
//            }else if(Consts.BATCH_ORDER.equals(service)){
//                if(isHavingMsg){
//                    msg = "本次成功导入订单数据：" + batchResult.getSucNum() + "条，失败：" + batchResult.getFailNum() + "条";
//                }else{
//                    msg = "导入订单失败：" + batchResult.getErr_msg();
//                }
//            }else if(Consts.BATCH_CLOUD.equals(service)){
//                if(isHavingMsg){
//                    msg = "本次成功导入云卡数据：" + batchResult.getSucNum() + "条，失败：" + batchResult.getFailNum() + "条";
//                }else{
//                    msg = "导入云卡失败：" + batchResult.getErr_msg();
//                }
//            }else if(Consts.BATCH_CARD.equals(service)){
//                String cardType = (String) request.getSession().getAttribute("type_code");
//                if("CARD".equals(cardType)){
//                    if(isHavingMsg){
//                        msg = "本次成功导入充值卡数据：" + batchResult.getSucNum() + "条，失败：" + batchResult.getFailNum() + "条";
//                    }else{
//                        msg = "导入充值卡失败：" + batchResult.getFailNum() + "条，原因：" + batchResult.getErr_msg();
//                    }
//                }else{
//                    if(isHavingMsg){
//                        msg = "本次成功导入流量卡数据：" + batchResult.getSucNum() + "条，失败：" + batchResult.getFailNum() + "条";
//                    }else{
//                        msg = "导入流量卡失败：" + batchResult.getFailNum() + "条，原因：" + batchResult.getErr_msg();
//                    }
//                }
//            }else if(Consts.BATCH_PHONENUM.equals(service)){
//                if(isHavingMsg){
//                    msg = "本次成功导入号码数据：" + batchResult.getSucNum() + "条，失败：" + batchResult.getFailNum() + "条";
//                }else{
//                    msg = "导入号码失败：" + batchResult.getErr_msg();
//                }
//            }
//
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            msg = "文件上传失败：" + ex.getCause() + ex.getMessage();
//            flag = "-1";
//            forwardJsp(request, response, url, msg, flag);
//            try {
//
//            } catch (Exception e) {
//                fupload.fileClose();
//                e.printStackTrace();
//            }
//            return;
//
//        }finally {
//            if(is != null) {
//                try {
//                    is.close();
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            forwardJsp(request, response, url, msg, flag);
//        }
    }

    /**
     * 跳转到导入界面
     *
     * @param request
     * @param response
     * @param url
     * @param msg
     * @param flag
     * @throws ServletException
     * @throws IOException
     */
    private void forwardJsp(HttpServletRequest request, HttpServletResponse response, String url, String msg,
                            String flag) throws ServletException, IOException {
        // msg编码
        request.setAttribute("msg", msg);
        request.setAttribute("flag", flag);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }

    /**
     * 将导入数据转为List数据
     * @param is  输入流
     * @return
     * @throws Exception
     */
    private Map iStreamToArrayListEx(InputStream is, String service) throws Exception {

        Map map = new HashMap();
        List aList = new ArrayList();
        jxl.Workbook workBook = null;
        errMsg = "";
        List compareList = new ArrayList();

        try {
            workBook = jxl.Workbook.getWorkbook(is);
        } catch (BiffException e) {
            errMsg = "当前导入Excel版本过高，请先将Excel文件另存为2003版本。";
            return null;
        }

        Sheet sheet = workBook.getSheet(0);
        int rowsNum = sheet.getRows();
        int colsNum = sheet.getColumns();
        String colName = "";
        String col = "";
        String value = "";
        String msg = "";
        String order_type = "";


        //验证模板是否正确
        if(service.equals(Consts.BATCH_CUST)){
            msg = isRightExcel(sheet, custCols, colsNum);
        }else if(service.equals(Consts.BATCH_ORDER)){
            if(colsNum == contractOrderCols.length/2){
                msg = isRightExcel(sheet, contractOrderCols, colsNum);
            }
            if(colsNum == cloudOrderCols.length/2){
                msg = isRightExcel(sheet, cloudOrderCols, colsNum);
            }
            if(colsNum == cloudwithLanOrderCols.length/2){
                msg = isRightExcel(sheet, cloudwithLanOrderCols, colsNum);
            }
        }else if(Consts.BATCH_CLOUD.equals(service)){
            msg = isRightExcel(sheet, cloudCols, colsNum);
        }else if(Consts.BATCH_CARD.equals(service)){
            msg = isRightExcel(sheet, cardCols, colsNum);
        }else if(Consts.BATCH_PHONENUM.equals(service)){
            msg = isRightExcel(sheet, phoneNumCols, colsNum);
        }else if(Consts.BATCH_ADJUNCT.equals(service)){
            msg = isRightExcel(sheet, adjuncts, colsNum);
        }else {
            throw new RuntimeException("service:" + service + "未定义");
        }

        //如果模板正确，进行下一步验证
        if("".equals(msg)){
            for (int i = 1; i < rowsNum; i++) { 	// 第一行存放标题
                Map mapCol = new HashMap<String, String>();
                for (int j = 0; j < colsNum; j++) { // 列,获取excel每行数据并放入map对象中
                    colName = sheet.getCell(j, 0).getContents().trim();

                    value = sheet.getCell(j, i).getContents().trim();

                    if(service.equals(Consts.BATCH_CUST)){
                        col = transeCol(colName,custCols);
                    }else if(service.equals(Consts.BATCH_ORDER)){
                        if(colsNum == contractOrderCols.length/2){
                            col = transeCol(colName,contractOrderCols);
                            order_type = Consts.BATCH_ORDER_CONTRACT;
                            mapCol.put("order_type", order_type);
                        }
                        if(colsNum == cloudOrderCols.length/2){
                            col = transeCol(colName,cloudOrderCols);
                            order_type = Consts.BATCH_ORDER_CUSTRETURNRD;
                            mapCol.put("order_type", order_type);
                        }
                        if(colsNum == cloudwithLanOrderCols.length/2){
                            col = transeCol(colName,cloudwithLanOrderCols);
                            order_type = Consts.BATCH_ORDER_CUSTRETURNRD_LAN;
                            mapCol.put("order_type", order_type);
                        }
                    }else if(Consts.BATCH_CLOUD.equals(service)){
                        col = transeCol(colName,cloudCols);
                    }else if(Consts.BATCH_CARD.equals(service)){
                        col = transeCol(colName,cardCols);
                    }else if(Consts.BATCH_PHONENUM.equals(service)){
                        col = transeCol(colName,phoneNumCols);
                    }else if(Consts.BATCH_ADJUNCT.equals(service)){
                        col = transeCol(colName,adjuncts);
                    }else {
                        throw new RuntimeException("service:" + service + "未定义");
                    }

                    //如果是注释列跟空出列，不进行空验证
                    if(!"".equals(col) && !"explain".equals(col)){
                        if(StringUtils.isEmpty(value)){
                            msg = Consts.MSG_NULL;
                        }
                    }
                    mapCol.put(col, value);
                }

                //如果某一行全部为空，取消空验证，并且不增加此行
                //如果此行为模板数据行，不插入数据
                boolean isMould = false;

                Collection c = mapCol.values();
                Iterator it = c.iterator();
                int j = 0;
                while (it.hasNext()) {
                    String com = (String) it.next();
                    if("".equals(com)){
                        j++;
                    }
                    if(com.indexOf("(样板数据)") > -1){
                        isMould = true;
                    }
                }
                if(mapCol.size() == j){
                    msg = "";
                }else{
                    if(!isMould){
                        aList.add(mapCol);
                    }
                }

            }

            //判断是否有重复字段
            if(StringUtils.isEmpty(msg)){
                if(Consts.BATCH_CLOUD.equals(service)){
                    //云卡
                    msg = isDupicate(aList, cloudCampare);
                }else if(Consts.BATCH_CARD.equals(service)){
                    //充值卡流量卡
                    msg = isDupicate(aList, cardCampare);
                }else if(Consts.BATCH_CUST.equals(service)){
                    //资料返档
                    /**
                     * 资料返档直接在数据库判断
                     */
                    //msg = isDupicate(aList, custCampare);
                }else if(Consts.BATCH_PHONENUM.equals(service)){
                    //号码导入
                    msg = isDupicate(aList, phoneNumCampare);
                }else if(Consts.BATCH_ORDER.equals(service)){
                    //合约机云卡订单
                    msg = isDupicate(aList, orderCompare);
                }
            }
        }

        map.put("list", aList);
        map.put("msg", msg);
        map.put("order_type", order_type);

        return map;
    }


    /**
     * 验证模板是否正确
     * @param sheet
     * @param cols
     * @param colsNum
     * @return
     */
    private String isRightExcel(Sheet sheet,String[] cols,int colsNum){
        String msg = "";
        if(cols.length/2 == colsNum){
            for (int i = 0; i < colsNum; i++) {
                String colName_1 = sheet.getCell(i, 0).getContents().trim();
                String colName_2 = cols[i*2].trim();
                if(!colName_1.equals(colName_2)){
                    msg = Consts.MSG_WRONG_EXCEL;
                }
            }
        }else{
            msg = Consts.MSG_WRONG_EXCEL;
        }
        return msg;
    }


    /**
     * 将导入数据转为List数据
     * @param is  输入流
     * @param goods_info 关联商品信息
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Map iStreamToArrayListTxt(InputStream is,String goods_info) throws Exception {
//        String msg = "";
//        String[] goodsInfo = goods_info.split("_");
//        String goodsId = goodsInfo[0];
//        String goodsPrice = goodsInfo[1];
//
//        //暂不进行验证
//        Map map = new HashMap();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));    	//缓冲指定文件的输入
//
//        CardLog cardLog = new CardLog();
//        List<Map> list = new ArrayList<Map>();
//
//
//        String myreadline = "";
//        int i=0;
//
//        while(br.ready()){
//            myreadline = br.readLine();			//读取一行
////            logger.info(myreadline);		//在屏幕上输出
//            if (i==0){
//                //根据文件格式，第一行为[HEAD]，不作操作
//            }
//            if(i==1){
//                //Batch:=100207
//                cardLog.setBatch_id(getTxtVal(myreadline));
//            }
//            if(i==2){
//                //CardType:=100
//                cardLog.setCardtype(getTxtVal(myreadline));
//            }
//            if(i==3){
//                //CardTheme:=中国电信充值付费卡
//                cardLog.setCardtheme(getTxtVal(myreadline));
//            }
//            if(i==4){
//                //CardNumber:=25
//                cardLog.setCardnumber(Long.valueOf(getTxtVal(myreadline)));
//            }
//            if(i==5){
//                //StartCardNo:=7311001002070000000
//                cardLog.setStartcardno(getTxtVal(myreadline));
//            }
//            if(i==6){
//                //EndCardNo:=7311001002070000024
//                cardLog.setEndcardno(getTxtVal(myreadline));
//            }
//            if(i==7){
//                //StartDate:=20100225
////            	String date = getTxtVal(myreadline);
////            	date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);
//                cardLog.setStartdate(getTxtVal(myreadline));
//            }
//            if(i==8){
//                //StopDate:=20101231
////            	String date = getTxtVal(myreadline);
////            	date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);
//                cardLog.setStopdate(getTxtVal(myreadline));
//            }
//            if(i==9){
//                //Reservestopdate:=20101231
//                cardLog.setReservestopdate(getTxtVal(myreadline));
//            }
//            if(i==10){
//                //Facevalue:=100
//                String card_price = String.valueOf(StringUtil.transMoneyToYuan(
//                        Integer.parseInt(getTxtVal(myreadline))));
//                if(!card_price.equals(goodsPrice)){
//                    msg = Consts.MSG_WRONG_GOOD;
//                }
//
//                cardLog.setFacevalue(card_price);
//            }
//            if(i==11){
//                //[BEGIN]
//            }
//            if(i>11){
//                if(myreadline.indexOf("[")>-1){
//                    //[END]
//                }else{
//                    //卡号					密码
//                    //7311001002070000000	730061465346755465
//                    //中间有可能是空格或者tab
//                    Map cardMap = new HashMap();
//                    String cardNum = "";
//                    String password = "";
//
//                    if(myreadline.indexOf("\t") > -1){
//                        String[] cardMes = myreadline.split("\t");
//                        cardNum = cardMes[0].trim();
//                        password = cardMes[1].trim();
//                    }else if(myreadline.indexOf(" ") > -1){
//                        cardNum = myreadline.substring(0, myreadline.indexOf(" ") + 1).trim();
//                        password = myreadline.substring(myreadline.indexOf(" ") + 1,myreadline.length()).trim();
//                    }else{
//                        msg = Consts.MSG_WRONG_TYPE;
//                    }
//                    cardMap.put("cardNum", cardNum);
//                    cardMap.put("password", password);
//                    list.add(cardMap);
//                }
//            }
//            i++;
//        }
//
//        cardLog.setLog_id(ManagerUtils.getUserId());
//        cardLog.setGoods_id(goodsId);
//
//
//        map.put("cardLog", cardLog);
//        map.put("list", list);
//        map.put("msg", msg);

        return null;
    }


    /**
     * 将InputStream转换成某种字符编码的String 
     * (未使用)
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    private String InputStreamTOString(InputStream in) throws Exception{

        int BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(),"iso-8859-1");
    }


    private String getTxtVal(String myreadline){

        return myreadline.substring(myreadline.indexOf("=")+1, myreadline.length()).trim();
    }


    //判断是否有重复的字段
    private String isDupicate(List list,String[] compare){
        String msg = "";
        for (int i = 0; i < compare.length; i++) {
            List conList = new ArrayList();
            String compareName = compare[i];
            for (int j = 0; j < list.size(); j++) {
                Map aMap = (Map) list.get(j);
                String conVal = (String) aMap.get(compareName);
                conList.add(conVal);
            }
            Set set = new HashSet(conList);
            if(set.size() != conList.size()){
                msg = Consts.MSG_DUPICATE;
                return msg;
            }
        }
        return msg;
    }

    /**
     * 列名称转换
     * @param colName
     * @return
     */
    private String transeCol(String colName, String[] cols){
        String col = "";
        for(int i = 0; i < cols.length; i+=2){
            if(cols[i].equals(colName)){
                col = cols[i + 1];
            }
        }
        return col;
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException
     *             if an error occure
     */
    @Override
	public void init() throws ServletException {
        // Put your code here
    }
    

}