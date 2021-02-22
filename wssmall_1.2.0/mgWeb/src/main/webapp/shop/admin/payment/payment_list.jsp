<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Payment.js"></script>
<script>
$(function(){
	Payment.init();
});
</script>

<div class="grid">
	<div class="comBtnDiv">
		<a href="payCfg!add.do" style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
		<a href="javascript:;" style="margin-right:10px;" class="graybtn1"  id="delBtn"><span>删除</span></a>
		<div style="width:200px;float:right"><a href="http://act.life.alipay.com/systembiz/javashop/" target="_blank">点击进入支付宝快速申请通道</a></div>
		<div style="clear:both"></div>
	</div>
	
	<!-- <div class="toolbar">
		<a href="payCfg!add.do"><input class="comBtn" type="button" name="" id="" value="添加" style="margin-right:10px;outline-style:none"/></a></li>
		<a href="javascript:;" id="delBtn"><input class="comBtn" type="button" name="" id="" value="删除" style="margin-right:10px;outline-style:none"/></a>
		<div style="width:200px;float:right"><a href="http://act.life.alipay.com/systembiz/javashop/" target="_blank">点击进入支付宝快速申请通道</a></div>
		<div style="clear:both"></div>
	</div>
	 -->
	
<form method="POST" >
<grid:grid  from="list">

	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell sort="name" width="250px">支付方式名称</grid:cell> 
	<grid:cell sort="name"  >修改</grid:cell> 
	</grid:header> 

  <grid:body item="payment">
  		<grid:cell><input type="checkbox" name="id" value="${payment.id}" />${payment.id}</grid:cell>
        <grid:cell>${payment.name}</grid:cell>
        <grid:cell><a href="payCfg!edit.do?paymentId=${payment.id }"  ><img class="modify" src="images/transparent.gif" ></a> </grid:cell>
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
