<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="../../../core/admin/js/PasswordOperator.js"></script>
<script type="text/javascript" src="edit_msg.js"></script>

<form method="post" id="serchform" action='messageAction!listById.do?num=1'>

<div class="searchformDiv">
<table class="form-table">
	<tr>

	    <th>消息类型：</th>
		<td><html:selectdict curr_val="${type}" name="type" attr_code="msg_type"></html:selectdict></td>
		<th>消息标题：</th>
		<td><input type="text" class="ipttxt"  name="topic"  value="${topic}"/></td>
		<th>消息状态：</th>

		<td><html:selectdict curr_val="${reciverState}" id ="sel_state" name="reciverState" attr_code="ES_USERMSG_READSTATE"></html:selectdict></td>
		<th>发送时间：</th>
			<td><input type="text"  class="ipttxt dateinput"   name="starttime" id="starttime" readonly="readonly"   value='${starttime}'  maxlength="60"  dataType="date" /></td>

		<td>到</td><td>
		<input type="text" class="ipttxt dateinput"  name="endtime" id="endtime" readonly="readonly"   value='${endtime}'  maxlength="60"  dataType="date" />

		</td>	
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
	    </td>
	   <!-- <td>  <input class="comBtn" type="reset" name="resetbtn" id="searchBtn" value="重置" style="margin-right:10px;"/></td> -->
	</tr>
	 <div style="clear:both"></div>
</table>		
</div>
</form>
<form class="grid" id ="btn_form">
<c:if test="${reciverState!=2}">
<div class="comBtnDiv">
     <input type="hidden" name = "messId" id="messId" value="${messId}">

	<a href="" style="margin-right:10px;" id="reciver_deleteBtn" class="graybtn1" ><span>删除</span></a>
	<!--  <a href="writemsg.jsp" style="margin-right:10px;"  class="graybtn1" ><span>写信</span></a>-->
     <div style="clear:both"></div>
</div>
</c:if>
</form>
<form id="gridform" class="grid" ajax="yes">
<div class="grid" id="goodslist">
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
	    <grid:cell width="50px">
			选择
		</grid:cell>
		<grid:cell width="110px">消息标题</grid:cell>
		<grid:cell width="180px">消息状态</grid:cell>
		<grid:cell width="180px">消息类型</grid:cell>
		<grid:cell width="200px">发件人姓名</grid:cell>
		<grid:cell width="180px">发送者类型</grid:cell>
		<grid:cell width="200px">发送时间</grid:cell>
	    <grid:cell width="180px">
	    <c:if test="${reciverState!=2}">操作 </c:if>
	    </grid:cell>
	  
		
	</grid:header>
	<grid:body item="msg">
	<div id= "msg_checkBox">
	 <grid:cell><input type="radio"  id = "msgid_checkbox" name="msgid_checkbox" value="${msg.m_id }" /></grid:cell>
	</div>
		<grid:cell>
			${msg.m_topic}
		</grid:cell>
		<grid:cell>&nbsp;
		 <c:if test="${msg.relState==0}">未读</c:if>
		 <c:if test="${msg.relState==1}">已读</c:if>
		 <c:if test="${msg.relState==2}">已删除</c:if>
		 <c:if test="${msg.relState==4}">已回复</c:if>
		</grid:cell>
		<grid:cell>
			<c:if test="${msg.m_type=='0'}">
			   任意类型
			</c:if>
			<c:if test="${msg.m_type!='0'}">
			  ${msg.m_type}
			</c:if>
		</grid:cell>
		<grid:cell>&nbsp;${msg.m_sendername}
		</grid:cell>
		<grid:cell> 
		 <c:if test="${msg.m_sendertype==1}">超级管理员</c:if>
		 <c:if test="${msg.m_sendertype==0}">电信员工</c:if>
		 <c:if test="${msg.m_sendertype==2}">二级分销商</c:if>
		 <c:if test="${msg.m_sendertype==3}">一级分销商</c:if>
		 <c:if test="${msg.m_sendertype==4}">供货商</c:if>
		 <c:if test="${msg.m_sendertype==5}">供货商员工</c:if>
		 <c:if test="${msg.m_sendertype==10}">会员</c:if>
		</grid:cell>
		<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${msg.m_sendtime}"></html:dateformat></grid:cell>
		<grid:cell>&nbsp; 
		<c:if test="${reciverState!=2}">
			<a title="查看收件箱信息" href="messageAction!read.do?readMsgId=${msg.m_id}" style="margin-right:10px;" id="readBtn" class="p_prted" >查看[回复]</a>   
		</c:if>
		</grid:cell>
		
	</grid:body>
</grid:grid></div>
</form>
<script type="text/javascript">
$(function(){
     $("#sel_state option[value='${reciverState}']").attr("selected", true);
});
</script>
