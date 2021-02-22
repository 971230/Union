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
<style type="text/css" id="receipt">

    BODY {background: #FFFFFF;font-size: 12px;font-family: Arial;font-style:italic;}
    html{color:#000;width:100%;}
    .content{width:550px;margin-left:120px;margin-top:46px;}
    .top{width:550px;height:219px;}
    .centerone{width:510px;height:260px;}
    .centertwo{width:510px;height:270px;}
    .bottom{width:550px;height:38px;}
    .top .topleft{width:200px;height:209px;float:left;}
    .top .topright{width:240px;height:209px;float:right;}
    .top .topleft .leftone{width:200px;height:19px;}
    .top .topleft .lefttwo{width:200px;height:19px;}
    .top .topleft .leftthree{width:200px;height:29px;}
    .top .topleft .leftfour{width:200px;height:29px;}
    .top .topleft .leftfive{width:200px;height:29px;}
    .top .topleft .leftsix{width:200px;height:19px;}
    .top .topleft .leftseven{width:200px;height:19px;}
    .top .topleft .lefteight{width:200px;height:19px;}
    .top .topleft .leftnine{width:200px;height:19px;}
    .top .topleft .leftten{width:200px;height:19px;}
    .top .topright .rightone{width:240px;height:19px;}
    .top .topright .righttwo{width:240px;height:19px;}
    .top .topright .rightthree{width:240px;height:29px;}
    .top .topright .rightfour{width:240px;height:29px;}
    .top .topright .rightfive{width:240px;height:29px;}
    .top .topright .rightsix{width:240px;height:19px;}
    .top .topright .rightseven{width:240px;height:19px;}
    .top .topright .righteight{width:240px;height:19px;}
    .top .topright .rightnine{width:240px;height:19px;}
    .top .topright .rightten{width:240px;height:19px;}
    .bottom .bottomleft{width:200px;height:38px;float:left;}
    .bottom .bottomright{width:230px;height:38px;float:right;}
    .bottom .bottomleft .leftone{width:200px;height:19px;}
    .bottom .bottomleft .lefttwo{width:200px;height:19px;}
    .bottom .bottomright .rightone{width:230px;height:19px;}
    .bottom .bottomright .righttwo{width:230px;height:19px;}
    .date{width:100%;height:20px;margin-top:52px;}
    .date .year{height:20px;width:19%;text-align:right;float:left;}
    .date .month{height:20px;width:4%;text-align:center;float:left;}
    .date .day{height:20px;width:4%;text-align:center;float:left;}
    .date .yeartwo{height:20px;width:48%;text-align:right;float:left;}
    .date .monthtwo{height:20px;width:4%;text-align:center;float:left;}
    .date .daytwo{height:20px;width:20%;text-align:left;float:left;}
</style>
</head>
<body>

<div id="receiptDivId">
      <div class="content">
        <div class="top">
          <div class="topleft">
            <div class="leftone" id="custName"></div>
            <div class="lefttwo" id="paperType"></div>
            <div class="leftthree" id="paperExpr"></div>
            <div class="leftfour" id="contactAddr"></div>
            <div class="leftfive" id="acctName"></div>
            <div class="leftsix" id="payMethod"></div>
            <div class="leftseven" id="bankName"></div>
            <div class="lefteight" id="bankAcctName"></div>
            <div class="leftnine" id="userType">3G普通用户</div>
            <!-- <div class="leftten" id="creditLevel"></div> -->
          </div>
          <div class="topright">
            <div class="rightone" id="custType">个人</div>
            <div class="righttwo" id="paperNo"></div>
            <div class="rightthree" id="paperAddr"></div>
            <div class="rightfour" id="contactPhone"></div>
            <div class="rightfive" id="acctAddr"></div>
            <div class="rightsix"></div>
            <div class="rightseven" id="bankCode"></div>
            <div class="righteight" id="bankAcct"></div>
            <div class="rightnine" id="userNo"></div>
            <div class="rightten"></div>  
          </div>
        </div>
        <div class="centerone" id="mainContentOne"></div>
        <div class="centertwo" id="mainContentTwo"></div>
        <div class="bottom">
          <div class="bottomleft">
            <div class="leftone" id="agentName"></div>
            <div class="lefttwo" id="agentPaperType"></div>
          </div>
          <div class="bottomright">
            <div class="rightone" id="agentPhone"></div>
            <div class="righttwo" id="agentPaperNo"></div>
          </div>
        </div>
      </div>
      <div class="date">
        <div class="year"><span id="year1Id"></span></div> 
        <div class="month"><span id="month1Id"></span></div>
        <div class="day"><span id="day1Id"></span></div>
        <div class="yeartwo"><span id="year2Id"></span></div>
        <div class="monthtwo"><span id="month2Id"></span></div>
        <div class="daytwo"><span id="day2Id"></span></div>
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
    