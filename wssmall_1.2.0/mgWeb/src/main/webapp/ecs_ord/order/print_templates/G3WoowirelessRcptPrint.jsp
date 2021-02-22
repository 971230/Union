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
<title>免填单</title>
</head>
<body   >
  <div id="receiptDivId">
      <div style="margin-top: 58px; height: 643px; font-style: normal;font-size:12px;">
        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">
              客户基本信息：
            </td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              客户名称
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="custName" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              客户类型
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="custType">
              个人
            </td>
          </tr>

          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              证件类型
            </td>
            <td bgcolor="#FFFFFF" id="paperType" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              证件号码
            </td>
            <td bgcolor="#FFFFFF" id="paperNo" />
          </tr>
          <tr height="30px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">
              证件有效期
            </td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="paperExpr" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">
              证件地址
            </td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="paperAddr" />
          </tr>
          <tr height="30px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">
              通信地址
            </td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="contactAddr" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">
              联系电话
            </td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="contactPhone" />
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
          style="border-top: #000000 1px solid;">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">
              账户信息：
            </td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              账户名称
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="acctName" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              账户地址
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="acctAddr" />
          </tr>
          <tr height="25px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              付费方式
            </td>
            <td bgcolor="#FFFFFF" colspan="3" id="payMethod" />
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              托收银行名称
            </td>
            <td bgcolor="#FFFFFF" id="bankName" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              银行代码
            </td>
            <td bgcolor="#FFFFFF" id="bankCode" />
          </tr>
          <tr height="30px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              银行户名
            </td>
            <td bgcolor="#FFFFFF" id="bankAcctName" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              银行账号
            </td>
            <td bgcolor="#FFFFFF" id="bankAcct" />
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
          style="border-top: #000000 1px solid;">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">
              业务受理信息：
            </td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              用户类型
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="userType" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              用户号码
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="userNo" />
          </tr>


          <tr height="242px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" width="100px;">
              套餐名称及终端信息
            </td>
            <td bgcolor="#FFFFFF" colspan="3" id="mainContentOne" width="524px;"/>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
          style="border-top: #000000 1px solid;">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">
              代理信息：
            </td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              代理人姓名
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="agentName" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">
              代理人联系电话
            </td>
            <td bgcolor="#FFFFFF" width="212px;" id="agentPhone" />
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              代理人证件类型
            </td>
            <td bgcolor="#FFFFFF" id="agentPaperType" />
            <td bgcolor="#FFFFFF" class="secondtitle">
              代理人证件号码
            </td>
            <td bgcolor="#FFFFFF" id="agentPaperNo" />
          </tr>
        </table>
        
    <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
     	style="border-top: #000000 1px solid; " id='warmTable' >
       <tr height="50px">
           <td bgcolor="#FFFFFF" width="15px;"/>
           <td bgcolor="#FFFFFF" class="secondtitle" width="100px;">温馨提示：</td>
           <td bgcolor="#FFFFFF" colspan="3" id="warmTips" width="524px;">${warmTips}</td>
       </tr>
    </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
          style="border-top: 1px solid #000000; border-bottom: solid 1px #000000;">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" colspan="4" width="624">
              本人证实上述资料属实,并已阅读及同意本登记表内容和背面所载之协议。
            </td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;">
              &nbsp;
            </td>
            <td bgcolor="#FFFFFF" class="secondtitle" width="312px" colspan="2">
              客户签字（盖章）
            </td>
            <td bgcolor="#FFFFFF" class="secondtitle" width="312px" colspan="2">
              受理单位（盖章）
            </td>
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" width="312px" colspan="2"></td>
            <td bgcolor="#FFFFFF" class="secondtitle" width="120px">
              受理人及工号
            </td>
            <td bgcolor="#FFFFFF" width="192px;">${bss_operator }
              #{sessionScope.operatorInfo.subScribe_StaffName}#{sessionScope.operatorInfo.bss_Staff_ID}
            </td>
          </tr>
        </table>
        <table width="639px">
          <tr height="40px">
            <td bgcolor="#FFFFFF" width="15px;" rowspan="2" />
            <td height="38" rowspan="2" bgcolor="#FFFFFF" style="text-align: right; font-size: 13px" width="312px" colspan="2">
              <br />
              <span id="year1Id"></span>年
              <span id="month1Id"></span>月
              <span id="day1Id"></span>日
            </td>
            <td rowspan="2" bgcolor="#FFFFFF" style="text-align: right; font-size: 13px" colspan="2" width="312px;">
              <br />
              <span id="year2Id"></span>年
              <span id="month2Id"></span>月
              <span id="day2Id"></span>日
            </td>
          </tr>

          <tr height="20"></tr>

          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" style="font-size: 13px; text-indent: 25px;" colspan="4">
              微型USIM卡仅供iPhone4/iPad使用，不能用于非iPhone4/iPad终端。如果您将该卡放入非iPhone4/iPad终端使用，由此造成的风险及损失将由您自行承担。
            </td>
          </tr>
        </table>
      </div>
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