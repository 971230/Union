/**
 * 
 */
package com.zte.cbss.autoprocess;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.convert.ExtGeneration;
import com.zte.cbss.convert.ExtGenerationFree;

/**
 * 类名称：TODO 当前类和类的业务处理名
 * 内容摘要：TODO 当前类的作用
 * @author yangziran
 * @version 1.0 2014年5月17日
 */
public class ChooseProductHandler {

    private static final String URL = "https://gd.cbss.10010.com/custserv";
    private static final Logger log = Logger.getLogger(Login.class);
    private static List<NameValuePair> headParams = new ArrayList<NameValuePair>();

    static {
        headParams.add(new BasicNameValuePair("Referer", "https://gd.cbss.10010.com/essframe"));
        log.info("Referer：" + headParams);
    }

    /**
     * 访问开户页面
     * @param hcb
     * @return
     * @author yangziran
     * @throws Exception
     */
    private static String visitOpenAccountPage(HCBrowser hcb, ParameterData data) throws Exception {
        log.info("访问开户页面！");
        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "page/personalserv.createuser.CreateUser"));
        postDict.add(new BasicNameValuePair("listener", "initMobTrade"));
        postDict.add(new BasicNameValuePair("RIGHT_CODE", "csCreateUserTrade"));
        // 登录随机编码
        postDict.add(new BasicNameValuePair("LOGIN_RANDOM_CODE", data.getLOGIN_RANDOM_CODE()));
        // 登录检查编码（根据登录账户设置值）
        postDict.add(new BasicNameValuePair("LOGIN_CHECK_CODE", data.getLOGIN_CHECK_CODE()));
        // 省份，51：广东
        postDict.add(new BasicNameValuePair("LOGIN_PROVINCE_CODE", data.getProvinceId()));
        postDict.add(new BasicNameValuePair("IPASS_LOGIN", ""));
        // 员工号（根据登录账户设置值）
        postDict.add(new BasicNameValuePair("staffId", data.getStaffId()));
        // 所属部门 （根据登录账户设置值）
        postDict.add(new BasicNameValuePair("departId", data.getDepartId()));
        postDict.add(new BasicNameValuePair("subSysCode", "BSS"));
        // 地市，0757：佛山（根据登录账户设置值）
        postDict.add(new BasicNameValuePair("eparchyCode", data.getEparchyCode()));
        log.info("请求参数：" + postDict);
        // 发送请求 获取到返回Html
        String respHtml = hcb.getUrlRespHtml(URL, headParams, postDict);
        // 解析获取到的Html 获取需要的值
        analyzeParam(respHtml, data);

        return respHtml;
    }

    /**
     * 初始化选号
     * @param hcb
     * @param data
     * @return
     * @author yangziran
     */
    private static String initCheckSerailNumber(HCBrowser hcb, ParameterData data) {

        log.info("初始化选号！");

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/common.product.ProductHelper/guideBrandByNetcode/1"));
        // 移动业务选号，电信类型
        postDict.add(new BasicNameValuePair("netTypeCode", data.getBill().getNetTypeCode()));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.createuser.CreateUser"));

        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    /**
     * 检验号码是否被占用
     * @param hcb
     * @return
     * @author yangziran
     */
    private static String checkNewSerailNumber(HCBrowser hcb, ParameterData data) {

        log.info("校验号码是否被占用！");

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service",
                "swallow/personalserv.createuser.CreateUser/checkNewSerailNumber/1"));
        // 被校验号码
        postDict.add(new BasicNameValuePair("serialNumber", data.getBill().getSerialNumber()));
        postDict.add(new BasicNameValuePair("productId", ""));
        // 证件号码
        postDict.add(new BasicNameValuePair("psptId", data.getPsptId()));
        // 证件类型
        postDict.add(new BasicNameValuePair("psptType", data.getPsptType()));
        // 移动业务选号，电信类型
        postDict.add(new BasicNameValuePair("netTypeCode", data.getBill().getNetTypeCode()));
        postDict.add(new BasicNameValuePair("isReleaseRes", "1"));
        postDict.add(new BasicNameValuePair("isYwyhPRadio", "1"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.createuser.CreateUser"));

        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    /**
     * 产品别名查询
     * @param hcb
     * @return
     * @author yangziran
     */
    private static String selectProductName(HCBrowser hcb, ParameterData data) {

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "direct/1/personalserv.createuser.CreateUser/$DirectLink$0"));
        // 产品别名
        postDict.add(new BasicNameValuePair("productName", data.getBill().getProductName()));
        postDict.add(new BasicNameValuePair("kind", "0"));
        // 移动业务选号，电信类型
        postDict.add(new BasicNameValuePair("netTypeCode", data.getBill().getNetTypeCode()));
        postDict.add(new BasicNameValuePair("mode", "2"));
        postDict.add(new BasicNameValuePair("PROD_LOW_COST", "0"));
        // 被查询号码
        postDict.add(new BasicNameValuePair("SERIAL_NUMBER", data.getBill().getSerialNumber()));

        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    /**
     * 活动选择跳转到产品定制
     * @param hcb
     * @return
     * @author yangziran
     */
    private static String selectProductInfo(HCBrowser hcb, ParameterData data) {

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/common.product.ProductHelper/getProductInfoByType/1"));
        postDict.add(new BasicNameValuePair("productItem", "{}"));
        // postDict.add(new BasicNameValuePair("productTypeCode", "4G000001"));
        postDict.add(new BasicNameValuePair("productTypeCode", data.getProductTypeCode()));
        // postDict.add(new BasicNameValuePair("brandCode", "4G00"));
        postDict.add(new BasicNameValuePair("brandCode", data.getBrandCode()));
        postDict.add(new BasicNameValuePair("root", "1000"));
        // 产品编码
        postDict.add(new BasicNameValuePair("productId", data.getBill().getSelectedProductId()));
        // 归属地市
        postDict.add(new BasicNameValuePair("userEparchyCode", data.getEparchyCode()));
        // 客户归属业务区
        postDict.add(new BasicNameValuePair("userCityCode", data.getBill().getUserCityCode()));
        postDict.add(new BasicNameValuePair("userCallingArea", ""));
        postDict.add(new BasicNameValuePair("tradeTypeCode", "10"));
        postDict.add(new BasicNameValuePair("groupId", "F"));

        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    /**
     * 查询产品包
     * @param hcb
     * @param data
     * @return
     * @author yangziran
     */
    private static String selectPackage(HCBrowser hcb, ParameterData data) {

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/common.product.ProductHelper/getPackageByPId/1"));
        // 产品编码
        postDict.add(new BasicNameValuePair("productId", data.getBill().getSelectedProductId()));
        postDict.add(new BasicNameValuePair("modifyTag", "0"));
        postDict.add(new BasicNameValuePair("userId", ""));
        postDict.add(new BasicNameValuePair("productMode", "00"));
        postDict.add(new BasicNameValuePair("curProductId", ""));
        postDict.add(new BasicNameValuePair("onlyUserInfos", "0"));
        postDict.add(new BasicNameValuePair("productInvalid", ""));
        postDict.add(new BasicNameValuePair("tradeTypeCode", "10"));

        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    /**
     * 查询活动
     * @param hcb
     * @return
     * @author yangziran
     */
    private static String selectActivities(HCBrowser hcb, ParameterData data) {

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/common.product.ProductHelper/getProductInfoByType/1"));
        // 产品类型编码
        postDict.add(new BasicNameValuePair("productTypeCode", data.getBill().getActivityType()));
        postDict.add(new BasicNameValuePair("assureType", ""));
        postDict.add(new BasicNameValuePair("deviceType", ""));
        postDict.add(new BasicNameValuePair("deviceBrand", ""));
        postDict.add(new BasicNameValuePair("deviceNumber", ""));
        // 产品编码
        postDict.add(new BasicNameValuePair("baseSaleProductId", data.getBill().getSelectedProductId()));
        postDict.add(new BasicNameValuePair("root", "SALE"));
        postDict.add(new BasicNameValuePair("isCheckRight", "0"));
        postDict.add(new BasicNameValuePair("isOtherExchange", "0"));
        postDict.add(new BasicNameValuePair("groupIdEss", ""));
        postDict.add(new BasicNameValuePair("orgDeviceBrandCode", ""));
        // 归属地市
        postDict.add(new BasicNameValuePair("userEparchyCode", data.getEparchyCode()));
        // 客户归属区域
        postDict.add(new BasicNameValuePair("userCityCode", data.getBill().getUserCityCode()));
        postDict.add(new BasicNameValuePair("userCallingArea", ""));
        postDict.add(new BasicNameValuePair("tradeTypeCode", "10"));

        //modify by zhangtao 20140902
        postDict.add(new BasicNameValuePair("groupUserId", ""));
        postDict.add(new BasicNameValuePair("serialNumber", data.getBill().getSerialNumber()));
        postDict.add(new BasicNameValuePair("psptId", data.getBill().getPsptId()));
        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    /**
     * 下一步
     * @param hcb
     * @param data
     * @return
     * @author yangziran
     */
    private static String next(HCBrowser hcb, ParameterData data) {

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.createuser.CreateUser/checkProduct/1"));
        postDict.add(new BasicNameValuePair("Base", data.getTradeBase()));
        postDict.add(new BasicNameValuePair("Ext", new ExtGeneration().getExt(data)));
        // 产品编码
        postDict.add(new BasicNameValuePair("ProductId", data.getBill().getSelectedProductId()));
        // 号码
        postDict.add(new BasicNameValuePair("serialNumber", data.getBill().getSerialNumber()));
        postDict.add(new BasicNameValuePair("SnBindProduct", ""));
        postDict.add(new BasicNameValuePair("custId", data.getCustomInfo().getCustId()));
        postDict.add(new BasicNameValuePair(
                "TradeAttr",
                "[{\"elementId\": \"50300\", \"elementTypeCode\": \"S\", \"elementName\": \"4G/3G流量提醒\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50019\", \"elementTypeCode\": \"S\", \"elementName\": \"无条件呼叫转移\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50020\", \"elementTypeCode\": \"S\", \"elementName\": \"遇忙前转\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50022\", \"elementTypeCode\": \"S\", \"elementName\": \"不可及前转\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50021\", \"elementTypeCode\": \"S\", \"elementName\": \"无应答前转\", \"itemObjNew\": \"N\"}]"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.createuser.CreateUser"));

        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    /**
     * 自由组合下一步
     * @param hcb
     * @param data
     * @return
     */
    private static String nextFree(HCBrowser hcb, ParameterData data) {

        // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.createuser.CreateUser/checkProduct/1"));
        postDict.add(new BasicNameValuePair("Base", data.getTradeBase()));
        postDict.add(new BasicNameValuePair("Ext", new ExtGenerationFree().getExt(data)));
        // 产品编码
        postDict.add(new BasicNameValuePair("ProductId", data.getBill().getSelectedProductId()));
        // 开户号码
        postDict.add(new BasicNameValuePair("serialNumber", data.getBill().getSerialNumber()));
        postDict.add(new BasicNameValuePair("SnBindProduct", ""));
        postDict.add(new BasicNameValuePair("custId", data.getCustomInfo().getCustId()));
        postDict.add(new BasicNameValuePair(
                "TradeAttr",
                "[{\"elementId\": \"50300\", \"elementTypeCode\": \"S\", \"elementName\": \"4G/3G流量提醒\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50019\", \"elementTypeCode\": \"S\", \"elementName\": \"无条件呼叫转移\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50020\", \"elementTypeCode\": \"S\", \"elementName\": \"遇忙前转\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50022\", \"elementTypeCode\": \"S\", \"elementName\": \"不可及前转\", \"itemObjNew\": \"N\"}, {\"elementId\": \"50021\", \"elementTypeCode\": \"S\", \"elementName\": \"无应答前转\", \"itemObjNew\": \"N\"}]"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.createuser.CreateUser"));

        log.info("请求参数：" + postDict);

        return hcb.getUrlRespHtml(URL, headParams, postDict);
    }

    public static ParameterData chooseProduct(HCBrowser hcb, ParameterData data) throws Exception {

        String productInfoResult = preparedBeforeChooseProduct(hcb, data);

        // 点击产品选择列表
        String[] url5 = {
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=59999792&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=59999768&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=59999767&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=59999793&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=59999764&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=" };
        for (String string : url5) {
            String html = hcb.getUrlRespHtml(string, headParams, null);
            log.info(html);
        }

        // 活动信息选择活动类型
        String[] url6 = {
                "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement_first/1&contextCode=ASSURE_CHANGE_PTYPE_%7CassureArea&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getIphoneFormula/1&selBaseProductId="
                        + data.getBill().getSelectedProductId() + "&globalPageName=personalserv.createuser.CreateUser" };
        for (String string : url6) {
            hcb.getUrlRespHtml(string, headParams, null);
        }

        // 查询活动
        String activitiesResult = selectActivities(hcb, data);
        if (StringUtils.isNotEmpty(activitiesResult) && activitiesResult.indexOf("prepayTag=\"1\"") != -1) {
            log.info(productInfoResult);
        }
        else {
            log.info("出现错误！");
            log.warn("查询活动出现错误:\n"+activitiesResult);
            throw new Exception("查询活动出现错误！");
        }

        // 选择存回送费测试合约
        String[] url10 = {
                "https://gd.cbss.10010.com/custserv?service=swallow/popupdialog.PopMobileInfo/init/1&score=&rewardLimit=&userId=&context=&paramvalue=&myTradeInfo=&baseProductId="
                        + data.getBill().getSelectedProductId()
                        + "&purchaseProductId=89001146&selectTypeStr=3&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getPackageByPId/1&productId=89001146&modifyTag=0&userId=&productMode=50&curProductId=&onlyUserInfos=0&productInvalid=&tradeTypeCode=10",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=51001393&packageTrans=&productId=89001146&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=" };
        for (String string : url10) {
            hcb.getUrlRespHtml(string, headParams, null);
        }

        // 下一步 产品定制到信息录入 (第一条链接是产品定制信息保存链接)
        // param的值
        // {"psptType": "1", "prodNetTypeCode": "50", "prodBrandCode": "4G00",
        // "productTypeCode": "4G000001",
        // "productId": "99999830", "psptId": "430124198804279178"}
        String url11 = "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/checkRightOpen/1&param=%7B%22psptType%22%3A%20%22"
                + data.getBill().getIdTypeCode()
                + "%22%2C%20%22prodNetTypeCode%22%3A%20%22"
                + data.getBill().getNetTypeCode()
                + "%22%2C%20%22prodBrandCode%22%3A%20%22"
                + data.getBrandCode()
                + "%22%2C%20%22productTypeCode%22%3A%20%22"
                + data.getProductTypeCode()
                + "%22%2C%20%22productId%22%3A%20%22"
                + data.getBill().getSelectedProductId()
                + "%22%2C%20%22psptId%22%3A%20%22"
                + data.getPsptId()
                + "%22%7D&globalPageName=personalserv.createuser.CreateUser";
        hcb.getUrlRespHtml(url11, headParams, null);

        String url12 = "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getSequence/1&seqName=seq_item_id&registerName=seq_item_id&globalPageName=personalserv.createuser.CreateUser";
        String result12 = hcb.getUrlRespHtml(url12, headParams, null);
        data = InformationInput.readSeqIdForXml(result12, data);

        String nextResult = next(hcb, data);
        if (StringUtils.isNotEmpty(nextResult) && nextResult.indexOf("isok=\"ok\"") != -1) {
            log.info(nextResult);
        }
        else {
            log.info("出现错误！");
            log.warn("点击下一步，跳转到产品定制错误:\n"+nextResult);
            throw new Exception("点击下一步，跳转到产品定制错误！");
        }

        String[] url13 = {
                "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/showMofficeEps/1&netTypeCode="
                        + data.getBill().getNetTypeCode() + "&brandCode=" + data.getBrandCode() + "&prodId="
                        + data.getBill().getSelectedProductId() + "&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement_first/1&contextCode=CREATE_USER_"
                        + data.getBill().getSelectedProductId()
                        + "%7CCREATE_USER%2CCREATE_USER_"
                        + data.getBill().getNetTypeCode()
                        + "_"
                        + data.getBrandCode()
                        + "%7CCREATE_USER%2CCREATE_USER_"
                        + data.getBill().getNetTypeCode()
                        + "%7CCREATE_USER%2CCREATE_USER&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement_first/1&contextCode=TRADE_ITEM_10_"
                        + data.getBill().getNetTypeCode()
                        + "_"
                        + data.getBrandCode()
                        + "%7CShowTradeItem%2CTRADE_ITEM_10_"
                        + data.getBill().getNetTypeCode()
                        + "%7CShowTradeItem%2CTRADE_ITEM_10%7CShowTradeItem%2CTRADE_ITEM_10_"
                        + data.getBill().getSelectedProductId()
                        + "%7CShowTradeItem%2CTRADE_ITEM_"
                        + data.getBill().getNetTypeCode()
                        + "_"
                        + data.getBrandCode()
                        + "%7CShowTradeItem%2CTRADE_ITEM_"
                        + data.getBill().getSelectedProductId()
                        + "%7CShowTradeItem%2CTRADE_ITEM%7CShowTradeItem&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/ifLHSerialNumber/1&serialNumber="
                        + data.getBill().getSerialNumber()
                        + "&ProductId="
                        + data.getBill().getSelectedProductId()
                        + "&globalPageName=personalserv.createuser.CreateUser" };
        for (String string : url13) {
            hcb.getUrlRespHtml(string, headParams, null);
        }

        return data;
    }

    /**
     * 选择自由组合产品
     * @param hcb
     * @param data
     * @return
     * @throws DocumentException
     * @throws ParserException
     */

    public static ParameterData chooseFreeProduct(HCBrowser hcb, ParameterData data) throws Exception {

        String productInfoResult = preparedBeforeChooseProduct(hcb, data);
        // TODO 获取产品包
        // Element element = docRoot.element("pkgByPId").element("data");
        // // 获取到产品类型编码设置到ParameterData
        // String productTypeCode =
        // element.attribute("productTypeCode").getValue();
        // log.info("productTypeCode：" + productTypeCode);
        // data.setProductTypeCode(productTypeCode);

        // 点击产品选择列表
        String[] url8 = {
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=51002514&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=51002515&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=51002516&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=51002517&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=59999793&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=59999764&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=51002540&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem=",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getElementByPkgId/1&packageId=51002531&packageTrans=&productId="
                        + data.getBill().getSelectedProductId()
                        + "&userId=&prodModifyTag=0&packModifyTag=0&curProductId=&onlyUserInfos=0&packageInvalid=&userEparchyCode="
                        + data.getEparchyCode()
                        + "&userCityCode="
                        + data.getBill().getUserCityCode()
                        + "&userCallingArea=&CallingAreaInfo=%7B%7D&discntItem="

        };
        for (String string : url8) {
            String html = hcb.getUrlRespHtml(string, headParams, null);
            log.info(html);
        }

        // 下一步 产品定制到信息录入 (第一条链接是产品定制信息保存链接)
        // param的值
        // {"psptType": "1", "prodNetTypeCode": "50", "prodBrandCode": "4G00",
        // "productTypeCode": "4G000001",
        // "productId": "99999830", "psptId": "430124198804279178"}
        String url11 = "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/checkRightOpen/1&param=%7B%22psptType%22%3A%20%22"
                + data.getBill().getIdTypeCode()
                + "%22%2C%20%22prodNetTypeCode%22%3A%20%22"
                + data.getBill().getNetTypeCode()
                + "%22%2C%20%22prodBrandCode%22%3A%20%22"
                + data.getBrandCode()
                + "%22%2C%20%22productTypeCode%22%3A%20%22"
                + data.getProductTypeCode()
                + "%22%2C%20%22productId%22%3A%20%22"
                + data.getBill().getSelectedProductId()
                + "%22%2C%20%22psptId%22%3A%20%22"
                + data.getPsptId()
                + "%22%7D&globalPageName=personalserv.createuser.CreateUser";
        hcb.getUrlRespHtml(url11, headParams, null);

        /**
         * 自由组合没有这个请求
         */
        /*
         * String url12 =
         * "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getSequence/1&seqName=seq_item_id&registerName=seq_item_id&globalPageName=personalserv.createuser.CreateUser"
         * ;
         * String result12 = hcb.getUrlRespHtml(url12, headParams, null);
         * data = InformationInput.readSeqIdForXml(result12, data);
         */

        String nextResult = nextFree(hcb, data);
        if (StringUtils.isNotEmpty(nextResult) && nextResult.indexOf("isok=\"ok\"") != -1) {
            log.info(nextResult);
        }
        else {
            log.info("出现错误！");
            log.warn("自由组合下一步出现错误:\n"+nextResult);
            throw new Exception("自由组合下一步出现错误！");
        }

        String[] url13 = {
                "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/showMofficeEps/1&netTypeCode="
                        + data.getBill().getNetTypeCode() + "&brandCode=" + data.getBrandCode() + "&prodId="
                        + data.getBill().getSelectedProductId() + "&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement_first/1&contextCode=CREATE_USER_"
                        + data.getBill().getSelectedProductId()
                        + "%7CCREATE_USER%2CCREATE_USER_"
                        + data.getBill().getNetTypeCode()
                        + "_"
                        + data.getBrandCode()
                        + "%7CCREATE_USER%2CCREATE_USER_"
                        + data.getBill().getNetTypeCode()
                        + "%7CCREATE_USER%2CCREATE_USER&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement_first/1&contextCode=TRADE_ITEM_10_"
                        + data.getBill().getNetTypeCode()
                        + "_"
                        + data.getBrandCode()
                        + "%7CShowTradeItem%2CTRADE_ITEM_10_"
                        + data.getBill().getNetTypeCode()
                        + "%7CShowTradeItem%2CTRADE_ITEM_10%7CShowTradeItem%2CTRADE_ITEM_10_"
                        + data.getBill().getSelectedProductId()
                        + "%7CShowTradeItem%2CTRADE_ITEM_"
                        + data.getBill().getNetTypeCode()
                        + "_"
                        + data.getBrandCode()
                        + "%7CShowTradeItem%2CTRADE_ITEM_"
                        + data.getBill().getSelectedProductId()
                        + "%7CShowTradeItem%2CTRADE_ITEM%7CShowTradeItem&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/ifLHSerialNumber/1&serialNumber="
                        + data.getBill().getSerialNumber()
                        + "&ProductId="
                        + data.getBill().getSelectedProductId()
                        + "&globalPageName=personalserv.createuser.CreateUser" };
        for (String string : url13) {
            hcb.getUrlRespHtml(string, headParams, null);
        }

        return data;
    }

    /**
     * 点开产品列表之前的
     * @param hcb
     * @param data
     * @return
     * @throws ParserException
     * @throws DocumentException
     */
    private static String preparedBeforeChooseProduct(HCBrowser hcb, ParameterData data) throws Exception {

        // 进入开户页面
        String openAccountPage = visitOpenAccountPage(hcb, data);
        if (StringUtils.isNotEmpty(openAccountPage) && openAccountPage.indexOf("id=\"pagecontext\"") != -1) {
            log.info("页面跳转成功！");
        }
        else {
            log.info("页面跳转失败！");
            log.warn("跳转到开户页面出现错误:\n"+openAccountPage);
            throw new Exception("跳转到开户页面出现错误！");
        }

        log.info("页面初始化请求开始！");
        String[] url1 = {
                "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/needChkCust/1&SERIAL_NUMBER=&NET_TYPE_CODE=&TRADE_TYPE_CODE=10&judge=1&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getGuideProductType/1&mode=1",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getGuideBrand/1&mode=1",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getGuideHotProd/1&mode=1",
                // 校验员工？？？
                "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.createuser.CreateUser/getStaffInfoByDepartId/1&DEPART_ID="
                        + data.getDepartId() + "&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getGuideProductType/1&mode=2",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement/1&contextCode=PREPOSING_CREATE_USER&globalPageName=personalserv.createuser.CreateUser",
                "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getGuideBrand/1&mode=2" };
        for (String string : url1) {
            String html = hcb.getUrlRespHtml(string, headParams, null);
            log.info(string + "URL请求收到的响应 \n:" + html);
        }

        // 号码检查初始化
        // String initResult =
        initCheckSerailNumber(hcb, data);
        // Document doc = DocumentHelper.parseText(initResult);
        // Element docRoot = doc.getRootElement();
        // Element element =
        // docRoot.element("guideBrandByNetcode").element("data");
        // // 获取到产品类型编码设置到ParameterData
        // String productTypeCode =
        // element.attribute("productTypeCode").getValue();
        // log.info("productTypeCode：" + productTypeCode);
        // data.setProductTypeCode(productTypeCode);

        String url2 = "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement/1&contextCode=OPEN_PRODUCT_ITEM_"
                + data.getBill().getNetTypeCode() + "%7CProductItems&globalPageName=personalserv.createuser.CreateUser";
        hcb.getUrlRespHtml(url2, headParams, null);

        log.info("页面初始化请求结束！");

        // 校验号码是否被占用
        String checkResult = checkNewSerailNumber(hcb, data);
        if (StringUtils.isNotEmpty(checkResult) && checkResult.indexOf("afterSn") != -1) {
            
        	if(checkResult.contains("certCode")){
             String url2_1 = "https://gd.cbss.10010.com/custserv?service=page/component.Agent&random="+data.getRandom()+"&staffId="+data.getStaffId()+"&departId="+data.getDepartId()+"&subSysCode=custserv&eparchyCode="+data.getEparchyCode();

             hcb.getUrlRespHtml(url2_1, headParams, null);
        	
             String url2_2 = "https://gd.cbss.10010.com/custserv?service=page/popupdialog.CheckSnOccupyCert&listener=init&OCCUPY_TYPE_CODE1&CERT_CODE="+data.getBill().getPsptId()+"&staffId="+data.getStaffId()+"&departId="+data.getDepartId()+"&subSysCode=custserv&eparchyCode="+data.getEparchyCode()+"&random="+data.getRandom();
             hcb.getUrlRespHtml(url2_2, headParams, null);
             log.info("【号码预受理成功】");
        	}else{
        		//modify by lijinzhong 20140903
//        		 log.info("【号码预受理失败返回】");
        		 log.warn("活动选择->选号,出现预受理失败，继续让流程跑下去:\n"+checkResult);
//                 throw new Exception("【号码预受理失败返回】");
        		
        	}
        	
        	
        	
        	log.info(checkResult);
        
        }
        else {
            log.info("选占【号码不存在或者号码未上架或者号码已开户】");
            log.warn("活动选择->选号:\n"+checkResult);
            throw new Exception("选占【号码不存在或者号码未上架或者号码已开户】");
        }

        // 产品别名查询
        String productResult = selectProductName(hcb, data);
        if (StringUtils.isNotEmpty(productResult) && productResult.indexOf("id=\"prodType\"") != -1) {

            // 解析返回数据设置productTypeCode，brandCode
            Parser parser = new Parser(productResult);
            // 判断查询结果是否符合要求（必须是只有一条记录）
            NodeFilter divFilter = new TagNameFilter("input");
            NodeFilter hasAttrFilter = new HasAttributeFilter("selectedProductId", data.getBill()
                    .getSelectedProductId());
            NodeFilter andFilter = new AndFilter(divFilter, hasAttrFilter);
            NodeList list = parser.parse(andFilter);
            if (list != null && list.size() > 0) {
                InputTag inputTag = (InputTag) list.elementAt(0);
                String productTypeCode = inputTag.getAttribute("value");
                log.info("productTypeCode：" + productTypeCode);
                data.setProductTypeCode(productTypeCode);
                String brandCode = inputTag.getAttribute("selectedBrandCode");
                log.info("brandCode：" + brandCode);
                data.setBrandCode(brandCode);
            }
            else {
                log.info("没有相应的产品！");
                log.warn("通过产品别名["+data.getBill().getProductName()+"]查询产品:\n"+productResult);
                throw new Exception("通过产品别名["+data.getBill().getProductName()+"]没有查到相应的产品！");
            }
            log.info(productResult);
        }
        else {
            log.info("没有相应的产品！");
            log.warn("通过产品别名["+data.getBill().getProductName()+"]查询产品:\n"+productResult);
            throw new Exception("通过产品别名["+data.getBill().getProductName()+"]没有查到相应的产品！");
        }

        // 下一步 活动选择跳转到产品定制
        String productInfoResult = selectProductInfo(hcb, data);
        if (StringUtils.isNotEmpty(productInfoResult) && productInfoResult.indexOf("prepayTag=\"1\"") != -1) {
            log.info(productInfoResult);
        }
        else {
            log.info("活动选择跳转到产品定制出现错误！");
            log.warn("活动选择跳转到产品定制出现错误:\n"+productInfoResult);
            throw new Exception("活动选择跳转到产品定制出现错误！");
        }

        log.info("产品定制画面初始化！");
        String url3 = "https://gd.cbss.10010.com/custserv?service=swallow/common.UtilityPage/getInterfaceElement_first/1&contextCode=SALE_PTYPE%7CptypeArea&globalPageName=personalserv.createuser.CreateUser";
        String html6 = hcb.getUrlRespHtml(url3, headParams, null);
        if (StringUtils.isNotEmpty(html6) && html6.indexOf("intfContextCode=\"SALE_PTYPE\"") != -1) {
            log.info(html6);
        }
        else {
            log.info("产品定制页面初始化出现错误！");
            log.warn("产品定制页面初始化出现错误:\n"+html6);
            throw new Exception("产品定制页面初始化出现错误！");
        }

        String url4 = "https://gd.cbss.10010.com/custserv?service=swallow/common.product.ProductHelper/getIphoneFormula/1&selBaseProductId="
                + data.getBill().getSelectedProductId()
                + "&curProductId=&userId=&tradeTypeCode=10&netTypeCode=&globalPageName=personalserv.createuser.CreateUser";
        hcb.getUrlRespHtml(url4, headParams, null);

        // 查询产品包
        String packgeResult = selectPackage(hcb, data);
        Document doc = DocumentHelper.parseText(packgeResult);
        Element docRoot = doc.getRootElement();
        return productInfoResult;
    }

    /**
     * 根据html解析必要参数
     * @param html html格式字符串
     * @param data 参数数据对象
     * @return 新参数数据对象
     * @throws Exception
     */
    private static ParameterData analyzeParam(String html, ParameterData data) throws Exception {
        Parser parser, parser1;
        NodeList list, list1 = null;

        NodeFilter inputFilter = new TagNameFilter("input");
        NodeFilter hasAttrFilter = new HasAttributeFilter("id", "_all_infos");
        NodeFilter andFilter = new AndFilter(inputFilter, hasAttrFilter);

        NodeFilter inputFilter1 = new TagNameFilter("input");
        NodeFilter hasAttrFilter1 = new HasAttributeFilter("id", "_tradeBase");
        NodeFilter andFilter1 = new AndFilter(inputFilter1, hasAttrFilter1);

        try {
            parser = Parser.createParser(html, "gbk");
            list = parser.parse(andFilter);

            parser1 = Parser.createParser(html, "gbk");
            list1 = parser1.parse(andFilter1);
        } catch (ParserException e) {
            log.info("解析出错", e);
            log.info("活动选择页面的结构有变，不能正常解析:\n"+html);
            throw new Exception("活动选择页面的结构有变，不能正常解析!");
        }
        if (list == null || list.size() == 0) {
        	log.info("未获取到正常dom结构!无法解析tradeId:\n"+html);
            throw new Exception("未获取到正常dom结构!无法解析tradeId");
        }

        if (list1 == null || list1.size() == 0) {
        	log.info("未获取到正常dom结构!无法解析tradeId:\n"+html);
            throw new Exception("未获取到正常dom结构!无法解析tradeBase");
        }
        InputTag allInfoInput = (InputTag) list.elementAt(0);
        String allInfo = allInfoInput.getAttribute("value");
        allInfo = StringEscapeUtils.unescapeHtml(allInfo);

        InputTag tradeBaseInput = (InputTag) list1.elementAt(0);
        String tradeBase = tradeBaseInput.getAttribute("value");

        /** 获得TRADE_ID */
        JSONObject json = JSONObject.fromObject(allInfo);
        String tradeId = json.getString("TRADE_ID");
        if (StringUtils.isBlank(tradeId)) {
        	log.info("未正常获取到TRADE_ID:\n"+html);
            throw new Exception("未正常获取到TRADE_ID!");
        }

        data.setTradeId(tradeId);
        data.setTradeBase(tradeBase);

        return data;
    }

    public static void main(String[] args) throws Exception {
        //
        // HCBrowser hcb = Login.emulateLogin();
        // ParameterData data = new ParameterData();
        //
        // executeCreateUser(hcb, data);
        ParameterData data = new ParameterData();
        String html = MyTools.openFile("c:\\活动选择.html");
        analyzeParam(html, data);
    }
}
