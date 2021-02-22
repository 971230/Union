<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/ecs_ord/common/jslibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath() %>/ecs_ord/order/print_templates/css/n_order.css" rel="stylesheet" type="text/css" media="all" />
<style type="text/css" media="print">
	#printButton{display: none;}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/order/print_templates/js/receipt.js"></script>
<title>3G开户白打免填单</title>
  </head>
  <body>
 <div id="receiptDivId">
      <table cellpadding="0" cellspacing="1" width="880px">
        <tr style="height:19;" >
          <td style="height:58;" colspan="5" rowspan="3" align="center" class="tdcss">
            <span class="titleword">
              <a href="">中国联合网络通信有限公司
                <span id="provinceId"></span>分公司
                <span id="tradeTypeName">3G客户</span>入网登记单
              </a>
            </span><br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        受理编号：<span id="subscribeNoId"></span>
          </td>
        </tr>
        <tr style="height:19;"></tr>
        <tr style="height:20;"></tr>
        <tr style="height:19;" >
          <td width="40px" height="102" rowspan="4" class="tdcss">客户<br />基本<br />信息</td>
          <td class="tdcss1" width="120px">客户名称</td>
          <td class="tdcss1" width="280px">
            <div id="custName" ></div>
          </td>
          <td class="tdcss1" width="120px">客户类型</td>
          <td class="tdcss1" width="280px">
            <div id="custType" >个人</div>
          </td>
        </tr>
        <tr style="height:45;">
          <td style="height:45;" class="tdcss1" width="120px">证件类型</td>
          <td class="tdcss1" width="280px">
            <div id="paperType" ></div>
          </td>
          <td class="tdcss1" width="120px">证件号码</td>
          <td class="tdcss1" width="280px">
            <div id="paperNo" ></div>
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:19;" class="tdcss1" width="120px">证件有效期</td>
          <td class="tdcss1" width="280px">
            <div id="paperExpr" ></div>
          </td>
          <td class="tdcss1" width="120px">证件地址</td>
          <td class="tdcss1" width="280px">
            <div id="paperAddr" ></div>
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:19;" class="tdcss1" width="120px">通信地址</td>
          <td class="tdcss1" width="280px">
            <div id="contactAddr" ></div>
          </td>
          <td class="tdcss1" width="120px">联系电话</td>
          <td class="tdcss1" width="280px">
            <div id="contactPhone" ></div>
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:87;" rowspan="4" class="tdcss">帐户<br />信息</td>
          <td class="tdcss1" width="120px">帐户名称</td>
          <td class="tdcss1" width="280px">
            <div id="acctName" ></div>
          </td>
          <td class="tdcss1" width="120px">帐户地址</td>
          <td class="tdcss1" width="280px">
            <div id="acctAddr" ></div>
          </td>
        </tr>
        <tr style="height:30;">
          <td style="height:30;" class="tdcss1" width="120px">付费方式</td>
          <td class="tdcss1" width="280px">
            <div id="payMethod" ></div>
          </td>
          <td class="tdcss1"></td>
          <td class="tdcss1"></td>
        </tr>
        <tr style="height:19;">
          <td style="height:19;" class="tdcss1" width="120px">托收银行名称</td>
          <td class="tdcss1" width="280px">
            <div id="bankName" ></div>
          </td>
          <td class="tdcss1" width="120px">银行代码</td>
          <td class="tdcss1" width="280px">
            <div id="bankCode" ></div>
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:19;" class="tdcss1" width="120px">银行户名</td>
          <td class="tdcss1" width="280px">
            <div id="bankAcctName" ></div>
          </td>
          <td class="tdcss1" width="120px">银行帐号</td>
          <td class="tdcss1" width="280px">
            <div id="bankAcct" ></div> 
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:505;" rowspan="3" class="tdcss">业务<br />受理<br />信息</td>
          <td class="tdcss1" width="120px">用户类型</td>
          <td class="tdcss1" width="280px">
            <div id="userType" >3G普通用户</div>
          </td>
          <td class="tdcss1" width="120px">用户号码</td>
          <td class="tdcss1" width="280px">
            <div id="userNo" ></div>
          </td>
        </tr>
        <!-- 
        <tr style="height:19;">
          <td class="tdcss1" width="120px">信用等级:</td>
          <td class="tdcss1" width="280px" colspan="3">
            <div id="creditLevel" />
          </td>
        </tr>
         -->
        <tr style="height:236;">
          <td style="height:236;" width="40" class="tdcss1">3G iPhone合约计划 </td>
          <td colspan="3" class="tdcss1" width="800px">
            <ul>
              <li>
                <div id="mainContentOne" ></div> 
              </li>
            </ul>
          </td>
        </tr>
        <tr style="height:250;">
          <td style="height:250;" class="tdcss1">3G普通用户开通<br />业务及功能</td>
          <td colspan="3" class="tdcss1" width="800px">
            <ul>
              <li>
                <div id="mainContentTwo" ></div> 
              </li>
            </ul>
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:49;" rowspan="2" class="tdcss">经办<br />信息</td>
          <td class="tdcss1" width="120px">经办人姓名</td>
          <td class="tdcss1" >
            <div id="agentName" ></div>
          </td>
          <td class="tdcss1" width="120px">经办人联系电话</td>
          <td class="tdcss1" width="280px">
            <div id="agentPhone" ></div>
          </td>
        </tr>
        <tr style="height:30;">
          <td style="height:30;" class="tdcss1" width="120px">经办人证件类型</td>
          <td class="tdcss1" width="280px">
            <div id="agentPaperType" ></div>
          </td>
          <td class="tdcss1" width="120px">经办人证件号码</td>
          <td class="tdcss1" width="280px">
            <div id="agentPaperNo" ></div>
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:19;" colspan="5" class="tdcss1">本人证实上述资料属实,并已阅读及同意本登记表内容和背面所载之协议。</td>
        </tr>
        <tr style="height:19;">
          <td style="height:19;" colspan="3" class="tdcss">客户签字（盖章）</td>
          <td colspan="2" class="tdcss">受理单位（盖章）</td>
        </tr>
        <tr style="height:19;">
          <td width="409" height="38" colspan="3" rowspan="2" class="tdcss"><br />
            <span id="year1Id"></span>年<span id="month1Id"></span>月<span id="day1Id"></span>日</td>
          <td width="475" colspan="2" rowspan="2" class="tdcss"><br />
            <span id="year2Id"></span>年<span id="month2Id"></span>月<span id="day2Id"></span>日</td>
        </tr>
        <tr style="height:19;"></tr>
        <tr style="height:19;">
          <td style="height:19;" colspan="5" class="remarksbox">备注：
            <div id="remarkId">
             1、“套餐包外资费”指用户入网当月免收月租费，其他按套餐包外资费执行；<br />
             2、“全月套餐“指申请套餐即时生效，用户入网当月即按照用户所选的基本套餐收取套餐月费，所含内容不变；<br />
             3、“套餐减半”指用户入网当月在其所选基本套餐的基础上，对套餐月费和套餐内所含内容均减半，减半处理本着“惠及<br />
                               用户”原则进行取整，其中来电显示、手机邮箱照常赠送。
            </div>
          </td>
        </tr>
        <tr style="height:19;">
          <td style="height:19;" colspan="5" class="tdcss">客服热线：10010  充值专线:10011<br />登陆中国联通网上营业厅www.10010.com,查询、交费、办业务，方便又实惠</td>
        </tr>
        <tr style="height:19;">
          <td height="19" class="tdcss1" colspan="5"></td>
        </tr>
      </table>
    </div>
<div align="center">
<input id="order_is_his" type="hidden" value="${order_is_his}"/>	
	<input id="order_id" type="hidden" value="${order_id}"/>	 	
    <input id="printButton" value="打印" type="button" style="width: 50px;" class="sbtn" onclick="doPrint();">
</div>
<script type="text/javascript">
var retInfo = ${retInfo};
putData(retInfo);
</script>
</body>

</html>