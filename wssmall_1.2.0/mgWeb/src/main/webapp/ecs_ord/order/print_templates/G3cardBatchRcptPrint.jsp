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
<body>
  <div id="receiptDivId">
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="620px">
    <tr >
      <td bgcolor="#FFFFFF" class="firsttitle">代理商信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="620px">
    <tr height="30px">
      <td bgcolor="#FFFFFF" width="20px;">&nbsp;</td>
      <td bgcolor="#FFFFFF" width="120px;" class="secondtitle">代理商名称</td>
      <td bgcolor="#FFFFFF" width="170px;" id="agentsName"/>
      <td bgcolor="#FFFFFF" width="120px;" class="secondtitle">渠道编码</td>
      <td bgcolor="#FFFFFF" width="190px;" id="channelCode"></td>
    </tr>
  </table>
    
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="620px" style="border-top:#000000 1px solid;">
    <tr >
      <td bgcolor="#FFFFFF" class="firsttitle">业务信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="620px">
    <tr height="30px">
      <td bgcolor="#FFFFFF" width="20px;">&nbsp;</td>
      <td bgcolor="#FFFFFF" width="120px;" class="secondtitle">产品类型及名称</td>
      <td bgcolor="#FFFFFF" width="170px;" id="cardType"></td>
      <td bgcolor="#FFFFFF" width="120px;" class="secondtitle">数量</td>
      <td bgcolor="#FFFFFF" width="190px;" id="IccidNum"/>
    </tr>
   <tr height="30px">
      <td bgcolor="#FFFFFF" width="20px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">产品单价(元)</td>
      <td bgcolor="#FFFFFF" id="RelieUnitFee"/>
      <td bgcolor="#FFFFFF" class="secondtitle">付款金额</td>
      <td bgcolor="#FFFFFF" id="RealTotalFee"/>
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
        
  <table  cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="620px" style="border-top:#000000 1px solid;border-bottom:#000000 1px solid;">
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="20px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" width="120px;">代理商签字（盖章）</td>
      <td bgcolor="#FFFFFF" width="170px;" ></td>
      <td bgcolor="#FFFFFF" class="secondtitle" width="120px;">受理单位（盖章）</td>
      <td bgcolor="#FFFFFF" width="190px;"></td>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="20px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" width="120px;"/>
      <td bgcolor="#FFFFFF" width="170px;" ></td>
      <td bgcolor="#FFFFFF" class="secondtitle" width="120px;">受理人及工号</td>
      <td bgcolor="#FFFFFF"  width="190px;">${bss_operator }
      </td>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="20px;" rowspan="2">&nbsp;</td>
      <td bgcolor="#FFFFFF" class="secondtitle" width="120px;" rowspan="2"/>
      <td height="38" rowspan="2" bgcolor="#FFFFFF" style="text-align:right;font-size:14px" width="170px;"><br />
        <span id="year1Id"></span>年<span id="month1Id"></span>月<span id="day1Id"></span>日
      </td>
       <td bgcolor="#FFFFFF" class="secondtitle" width="120px;" rowspan="2"/>
      <td rowspan="2" bgcolor="#FFFFFF" style="text-align:right;font-size:14px" colspan="2" width="190px;"><br />
        <span id="year2Id"></span>年<span id="month2Id"></span>月<span id="day2Id"></span>日
      </td>
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