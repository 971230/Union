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
  <div id="receiptDivId" >

<table cellpadding="0" cellspacing="1" border="1" style="border:1px solid #000;font-weight:bold;scrollbar-highlight-color: #FFFFFF;" >
 <tr style="height:19;">
    <td width="884px" style="height:58;" colspan="5" rowspan="3" align="center" bgcolor="#FFFFFF" class="titlebox">
      <span class="titleword"><a href="">
                    中国联合网络通信有限公司<span id="provinceId"></span>分公司3G客户入网登记单
      </a></span><br />
    &nbsp;&nbsp;&nbsp;&nbsp;      &nbsp;      &nbsp;    &nbsp;      &nbsp;      &nbsp;        &nbsp;        &nbsp;      &nbsp;      &nbsp;      &nbsp;      &nbsp;      &nbsp;    &nbsp;        &nbsp;      &nbsp;        &nbsp;    &nbsp;       
                  受理编号：<span id="subscribeNoId"></span></td>
  </tr>
  <tr style="height:19;"> </tr>
  <tr style="height:20;"> </tr>
  <tr style="height:19;">
    <td width="60px" height="102" rowspan="4" class="wordMid">客户<br />
      基本<br />
    信息</td>
    <td class="tdbox" width="120px">客户名称</td>
    <td class="tdbox"  width="320px">
        <div id="custName" ></div>
    </td>
    <td class="tdbox" width="120px">客户类型</td>
    <td class="tdbox" width="280px">
        <div id="custType" >个人</div>
    </td>
  </tr>
  
  <tr style="height:19;">
    <td style="height:45;" class="tdbox" >证件类型</td>
    <td class="tdbox" >
        <div id="paperType" ></div>
    </td>
    <td class="tdbox" >证件号码</td>
    <td class="tdbox" >
        <div id="paperNo" ></div>
    </td>
  </tr>
  <tr style="height:19;">
    <td style="height:19;" class="tdbox" >证件有效期</td>
    <td class="tdbox" >
       <div id="paperExpr" ></div>
    </td>
    <td class="tdbox" >证件地址</td>
    <td class="tdbox" >
       <div id="paperAddr" ></div>
    </td>
  </tr>
  <tr style="height:19;">
    <td style="height:19;" class="tdbox">通信地址</td>
    <td class="tdbox" >
       <div id="contactAddr" ></div>
    </td>
    <td class="tdbox">联系电话</td>
    <td class="tdbox" >
       <div id="contactPhone" ></div>
    </td>
  </tr>
  
  <tr style="height:19;">
    <td style="height:505;" rowspan="2" class="wordMid">业务<br />
      受理<br />
    信息</td>
    <td class="tdbox">用户类型</td>
    <td class="tdbox" >
       <div id="userType" >3G普通用户</div>
    </td>
    <td class="tdbox">用户号码</td>
    <td class="tdbox" >
       <div id="userNo" ></div>
    </td>
  </tr>
  
  <tr style="height:236;">
    <td colspan="4" class="tdbox">
     <ul>
      <li>
         <div id="mainContentOne" ></div> 
      </li>
     </ul>
    </td>
  </tr>
    
  <tr style="height:19;">
    <td style="height:49;" rowspan="2" class="wordMid">经办<br />    
      信息</td>
    <td class="tdbox">经办人姓名</td>
    <td class="tdbox" >
       <div id="agentName" ></div>
    </td>
    <td class="tdbox">经办人联系电话</td>
    <td class="tdbox">
        <div id="agentPhone" ></div>
    </td>
  </tr>
  
  <tr style="height:30;">
    <td style="height:30;" class="tdbox">经办人证件类型</td>
    <td class="tdbox">
       <div id="agentPaperType" ></div>
    </td>
    <td class="tdbox">经办人证件号码</td>
    <td class="tdbox" >
      <div id="agentPaperNo" ></div>
    </td>
  </tr>
  
  <tr style="height:19;">
    <td style="height:19;" colspan="3" bgcolor="#FFFFFF" class="wordMid">客户签字（盖章）</td>
    <td colspan="2" bgcolor="#FFFFFF" class="wordMid">受理单位（盖章）</td>
  </tr>

  <tr style="height:19;">
    <td width="409px" height="38" colspan="3" rowspan="2" bgcolor="#FFFFFF" class="wordMid"><br />
       <span id="year1Id"></span>年<span id="month1Id"></span>月<span id="day1Id"></span>日</td>
    <td width="475px" colspan="2" rowspan="2" bgcolor="#FFFFFF" class="wordMid"><br />
       <span id="year2Id"></span>年<span id="month2Id"></span>月<span id="day2Id"></span>日</td>
  </tr>
  
  <tr style="height:19;"> </tr>
  <tr style="height:19;">
    <td style="height:19;" colspan="5" class="remarksbox">备注：
      <div id="remarkId">各分公司可根据本省实际情况填写备注信息</div>
    </td>
  </tr>
  <tr style="height:19;">
    <td style="height:19;" colspan="5" bgcolor="#FFFFFF" class="wordMid">客服热线：10010  充值专线:10011<br />
    登陆中国联通网上营业厅www.10010.com,查询、交费、办业务，方便又实惠</td>
  </tr>
  <tr style="height:19;">
    <td height="19" bgcolor="#FFFFFF" colspan="5"></td>
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