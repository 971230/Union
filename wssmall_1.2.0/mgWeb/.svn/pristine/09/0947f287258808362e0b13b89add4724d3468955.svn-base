<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript">
 loadScript("js/index.js");
</script>

<link href="${context}/css/list.css" rel="stylesheet"  type="text/css" >
<div class="userMsgDiv" style='overflow:hidden;border:0px solid red;'>
<div class="viewgrid" >
	<table cellspacing="0" cellpadding="0" border="0" width="100%">
		<tr><th>登录用户姓名：</th><td>${adminUser.realname}</td>
		<th>登录用户工号：</th><td> ${adminUser.username}</td></tr>
		<tr><th>登录次数：</th><td>${adminUser.success_logincount}</td>
		<th>您上一次登录时间：</th><td>${adminUser.last_logintime }</td></tr>
	</table>
</div>
<div class="columDiv">
	<div class="columDivCon">
    	<ul class="tab_a">
        	<li class="curr"><a href="#"><span>待处理事务</span><i class="tab_arrow"></i></a></li>
            <div class="clear"></div>
        </ul>
        <div class="tab_a_content">
         <c:if test="${adminUser.founder==4||adminUser.founder==1}">
        	<h3>订单</h3>
            <ul class="list_a">
            	<li id="wait_yuyue_order" ><a href="#">待预约单<span>(0)</span></a></li>
            	<li id="wait_handle_order" ><a href="#">待备货订单<span>(0)</span></a></li>
            	<li id="wait_pay_order"><a href="#">待支付订单<span>(0)</span></a></li>
            	<li class="bulefont" id="wait_deliverGoods_order"><a href="#">待发货订单<span>(0)</span></a></li>
                <li id="wait_confirm_ship" ><a href="#">待确认收货订单<span>(0)</span></a></li>
                <li id="wait_returned_ship" ><a href="#">待退货订单<span>(0)</span></a></li>
                <li class="bulefont" id="wait_refund_ship" ><a href="#">待退费订单<span>(0)</span></a></li>
                <div class="clear"></div>
            </ul>
         </c:if>
        	<h3>评价</h3>
            <ul class="list_a">
            	<li id="wait_assess"><a href="#">待回复评价<span>(0)</span></a></li>
            	<li id="wait_question"><a href="#">待回复咨询<span>(0)</span></a></li>
            	
                <div class="clear"></div>
            </ul>
             <c:if test="${adminUser.founder==0||adminUser.founder==1}">
        	<h3>商品</h3>
            <ul class="list_a">
            	<li class="bulefont" id="wait_finalist_goods"><a href="#">待入围审核商品<span>(0)</span></a></li>
            	<li class="bulefont" id="wait_change_goods"><a href="#">待审核资料变更<span>(0)</span></a></li>
            	
                <div class="clear"></div>
            </ul>
            <h3>小伙伴</h3>
            <ul class="list_a">
            	<li class="bulefont" id="wait_audit_supplier"><a href="#">待审核供货商<span>(0)</span></a></li>
            	<li class="bulefont" id="wait_audit_agency"><a href="#">待审核经销商<span>(0)</span></a></li>
                <div class="clear"></div>
            </ul>
            </c:if>
        </div>
    </div>
</div>
<div class="columDiv">
	<div class="columDivCon">
    	<ul class="tab_a">
        	<li class="curr"><a href="#"><span>业务概况</span><i class="tab_arrow"></i></a></li>
            <div class="clear"></div>
        </ul>
        <div class="tab_a_content">
        	<h3>订单</h3>
            <ul class="list_a">
            	<li id="yestoday_order"><a href="#">昨日新增订单<span>(0)</span></a></li>
            	<li id ="today_order" class="bulefont"><a href="#">今日新增订单<span>(0)</span></a></li>
                <div class="clear"></div>
            </ul>
             <c:if test="${adminUser.founder==0||adminUser.founder==1}">
        	<h3>供销商</h3>
            <ul class="list_a">
            	<li class="bulefont"  id="cooperation_supplier"><a href="#">合作中的供货商<span>(0)</span>位</a></li>
            	<li class="bulefont" id="end_supplier"><a href="#">已终止供货商<span>(0)</span>位</a></li>
            	<li id="today_newSupplier"><a href="#" >今日新增供货商<span>(0)</span>位</a></li>
            	<li id="yestoday_newSupplier"><a href="#" >昨日新增供货商<span>(0)</span>位</a></li>
            	<li id="today_newAgency"><a href="#" >今日新增经销商<span>(0)</span>位</a></li>
            	<li id="yestoday_newAgency"><a href="#">昨日新增经销商<span>(0)</span>位</a></li>
                <div class="clear"></div>
            </ul>
            </c:if>
        	<h3>商品</h3>
            <ul class="list_a">
            	<li id="grounding_goods"><a href="#" >上架商品<span>(0)</span></a></li>
            	<li id="undercarriage_goods"><a href="#" >已下架商品<span>(0)</span></a></li>
            	<c:if test="${adminUser.founder==0||adminUser.founder==4||adminUser.founder==1}">
            	<li class="bulefont" id="outof_register"><a href="#">缺货登记<span>(0)</span></a></li>
            	</c:if>
                <div class="clear"></div>
            </ul>
        	
        </div>
    </div>
</div>
<div class="columDiv">
	<div class="columDivCon">
    	<ul class="tab_a">
        	<li class="curr"><a href="#"><span>客服信息</span><i class="tab_arrow"></i></a></li>
            <div class="clear"></div>
        </ul>
        <div class="tab_a_content">
        	<h3>消息</h3>
            <ul class="list_a">
            	<li class="bulefont" id="wait_message"><a href="#" >待回复消息<span>(0)</span></a></li>
            	<li id="message_count"><a href="#" >消息总数<span >(0)</span></a></li>
                <div class="clear"></div>
            </ul>
            <c:if test="${adminUser.founder==0||adminUser.founder==1}"> 
            <h3>广告</h3>
            <ul class="list_a">
            	<li class="bulefont" id="wait_audit_adv"><a href="#">待审核广告<span>(0)</span></a></li>
            	<li id="adv_Count"><a href="#" >广告总数<span>(0)</span></a></li>
                <div class="clear"></div>
            </ul>
            
            <h3>快讯</h3>
            <ul class="list_a">
            	<li class="bulefont" id="wait_audit_flash"><a href="#">待审核快讯<span>(0)</span></a></li>
            	<li  id="flash_count"><a href="#">快讯总数<span>(0)</span></a></li>
                <div class="clear"></div>
            </ul>
            </c:if>
        </div>
    </div>
</div>
</div>