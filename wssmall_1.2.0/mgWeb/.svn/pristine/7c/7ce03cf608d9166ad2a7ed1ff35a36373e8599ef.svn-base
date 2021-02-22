<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="edit_msg.js"></script>
<form method="post" id="serchform" action='messageAction!listById.do?num=2'>
<div class="searchformDiv">
 <script type="text/javascript">
$(function(){

    $("#senderState option[value='${senderState}']").attr("selected", true);
});
</script>
<table class="form-table">
	<tr>
	    <th>消息类型：</th>
		<td><html:selectdict curr_val="${type}" name="type" attr_code="msg_type"></html:selectdict></td>
		<th>消息标题：</th>
		<td><input type="text" class="ipttxt"  name="topic"  value="${topic}"/></td>
		<th>消息状态：</th>
		<input type ="hidden" id = "sendStaVal">
		<td><select  class="ipttxt"  name ="senderState" id ="senderState">
		<option value =0>未删除</option>
		<option value=1>已删除</option>
		</select></td>
		<th>发送时间：</th>
		<td><input type="text" readonly="readonly" maxlength="60" class="ipttxt dateinput" dataType="date"    name="starttime" id="starttime"   value='${starttime}' /></td>
		<td>到</td>
		<td><input type="text" readonly="readonly" maxlength="60" class="ipttxt dateinput" dataType="date" name="endtime" id="endtime"   value='${endtime}' />
		</td>	
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
	   </td>
	   
	   <!-- <td> <input class="comBtn" type="reset" name="resetbtn" id="searchBtn" value="重置" style="margin-right:10px;"/>
	    </td> -->
	</tr>
	 <div style="clear:both"></div>
</table>		
</div>
</form>

<form id="btn_form" class="grid">

<div class="comBtnDiv" id="delbtn">
     <input type="hidden" name = "messId" id="messId" value="${messId}">
	<a href="writemsg.jsp" style="margin-right:10px;"  class="graybtn1" ><span>写信</span></a>
	<c:if test="${senderState==0}">
	<a href="javascript:void(0)" style="margin-right:10px;" id="send_delete_Btn" class="graybtn1" ><span>删除</span></a>
	</c:if>
	 <div style="clear:both"></div>
</div>



</form>
<form id="gridform" class="grid" ajax="yes">
<div class="grid" id="goodslist">
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
	    <grid:cell width="50px">
			选择
		</grid:cell>
		<grid:cell width="110px">消息标题</grid:cell>
		<grid:cell width="120px">消息类型</grid:cell>
		<grid:cell width="150px">收件人类型</grid:cell>
		<grid:cell width="180px">&nbsp&nbsp收件人姓名</grid:cell>
		<grid:cell width="200px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp收件人工号</grid:cell>
		<grid:cell width="200px">发送时间</grid:cell>
		<grid:cell width="200px">操作</grid:cell>
	
		
	</grid:header>
	<grid:body item="msg">
	<div id= "msg_checkBox">
	 <grid:cell>
	 <input type="radio"  id = "msgid_checkbox" name="msgid_checkbox" value="${msg.m_id }" /></grid:cell>
	 </div>
		<grid:cell>
			${msg.m_topic}
		</grid:cell>
		<grid:cell>
		<c:if test="${msg.m_type=='0'}">
		   任意类型
		</c:if>
		<c:if test="${msg.m_type!='0'}">
		  ${msg.m_type}
		</c:if>
		</grid:cell>
		<grid:cell> 
		 
		 <c:forEach items="${typeList}" var="type">
		 	<c:if test="${type.value eq msg.m_recivertype}"> ${type.value_desc}</c:if>
		 </c:forEach>
		</grid:cell>
		<grid:cell>&nbsp;${msg.m_recivername}</grid:cell>
		<grid:cell>&nbsp;${msg.username}</grid:cell>
		<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${msg.m_sendtime}"></html:dateformat></grid:cell>
		
		<grid:cell>&nbsp; 
		<c:if test="${reciverState!=2}">
		<a title="已发信息查看" href="messageAction!senderRead.do?readMsgId=${msg.m_id}" style="margin-right:10px;" id="readBtn" class="p_prted" >查看</a>
		</c:if>
		
        	
		</grid:cell>
		
	
	</grid:body>
</grid:grid>
</div>
</form>

