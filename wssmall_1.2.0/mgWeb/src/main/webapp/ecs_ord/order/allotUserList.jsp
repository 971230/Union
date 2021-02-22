<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div>
<form method="post" id="serchUserform" action='ordAuto!queryUser.do' ajax="yes">
 <div class="searchformDiv">
	 <table>
		
			<th>关键字：</th>
			<td><input type="text" class="ipttxt" id="userparams"   value="${userparams}"/></td>
			<input type="text" class="ipttxt" id="lockOrderIdStr"  value="${lockOrderIdStr}" style="display :none;"/>
			<th></th>
			<td>
			
		    <c:if test="${allotType == 'allot' }">
		    <th>区县：</th>
			<td>
			<input type="text" class="ipt_new" style="width:138px;" id="allot_county_name" value="${allot_county_name }" onfocus="querycountyId()"/>
			<input type="hidden" class="ipt_new" style="width:138px;" id="allot_county_id"  value="${allot_county_id }" />
			</td>
			<td>
		    <input style="width:40px;"  class="comBtn schConBtn" id="selUserBtn"
					value="搜索" />
		    <input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定"  id="updateLockBtn">
		    </c:if>
		    
			<c:if test="${allotType == 'query' }">
			<input style="width:40px;"  class="comBtn schConBtn" id="qryUserBtn"
					value="搜索" />
		    <input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定"  id="queryLockIdBtn">
		    </c:if>
		    <!-- <a href="javascript:void(0)" id="search_btn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a> -->
			</td>
		</tr>
	 </table>
  </div>
</form>

<div class="grid" >
<div class="comBtnDiv">
<grid:grid from="webpage"  formId="serchUserform" ajax="yes">
	<grid:header>
	    <grid:cell width="10%" >选择</grid:cell>
		<grid:cell width="15%">姓名</grid:cell>
		<grid:cell width="15%">工号</grid:cell>
		<grid:cell >电话</grid:cell>
		<grid:cell ><c:if test="${allotType == 'allot' }">县区</c:if></grid:cell>
	</grid:header>
	<grid:body item="adminUser">
		<grid:cell><input name="lockUserChk" type="radio" userid="${adminUser.userid}" realname="${adminUser.realname}" username="${adminUser.username}"></grid:cell>
		<grid:cell>${adminUser.realname}</grid:cell>
		<grid:cell>${adminUser.username}</grid:cell>
		<grid:cell>${adminUser.phone_num}</grid:cell>
		<grid:cell><c:if test="${allotType == 'allot' }">${adminUser.busicityAuth}</c:if></grid:cell>
		<grid:cell><input type="text" class="ipttxt" id="col5"  value="${adminUser.col5}" style="display :none;"/></grid:cell>
	</grid:body>
</grid:grid>
</div>
</div>
</div>

<!-- var cobj = $("input[type='radio'][name='radionames']:checked"); -->
<script type="text/javascript">
$(function() {
	
	   			$("#selUserBtn").click(function(){
	   				var userparams = $("#userparams").val();
	   				var lockOrderIdStr = $("#lockOrderIdStr").val();
	   				var allot_county_name = $("#allot_county_name").val();
	   				var allot_county_id = $("#allot_county_id").val();
	   				var url = ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=allot&userparams="+userparams+"&lockOrderIdStr="+lockOrderIdStr
	   						+"&allot_county_name="+allot_county_name+"&allot_county_id="+allot_county_id;
	   				
	   				Cmp.ajaxSubmit('serchUserform','queryUserListDlg',url,
	   						{},function(){});
	   			});
	   			$("#qryUserBtn").click(function(){
	   				var userparams = $("#userparams").val();
	   				var lockOrderIdStr = $("#lockOrderIdStr").val();
	   				var url = ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=query&userparams="+userparams+"&lockOrderIdStr="+lockOrderIdStr;
	   				
	   				Cmp.ajaxSubmit('serchUserform','queryUserListDlg',url,
	   						{},function(){});
	   			});
	   			$("#queryLockIdBtn").click(function(){
	   				var obj = $("[name='lockUserChk']:checked");
	   				var  lock_user_id = null;
	   				lock_user_id = obj.attr("userid");
	   				username = obj.attr("username");
	   				if(username==null || username.length==0){
	   					document.getElementById("username").value = "";
	   					document.getElementById("lock_user_id").value = "";
	   				}else{
	   					document.getElementById("username").value = username;
	   					document.getElementById("lock_user_id").value = lock_user_id;
	   				}
	   					
	   				
	   				Eop.Dialog.close("queryUserListDlg");
	   			});
	   			$("#updateLockBtn").unbind("click").bind("click",function(){
	   				
	   				var code = $("[name='lockUserChk']:checked").val();
					if(code==null){
						alert("还未选中工号");
					}else{
						var obj = $("[name='lockUserChk']:checked");
						var realname = obj.attr("realname");
						var userid = obj.attr("userid");
						var lockOrderIdStr = $("#lockOrderIdStr").val();
						var col5 = $("#col5").val();
		   				$.ajax({
		   					type : "post",
		   					async : true,
		   					url : ctx+"/shop/admin/ordAuto!updateLock.do?ajax=yes&userid="+userid+"&col5="+col5+"&realname="+encodeURI(encodeURI(realname))+"&lockOrderIdStr="+lockOrderIdStr,
		   					data : {},
		   					dataType : "json",
		   					success : function(data) {
		   						alert(data.message);
		   						Eop.Dialog.close("queryUserListDlg");
		   						//location.href = ctx+"/shop/admin/ordAuto!AllotOrderList.do";
		   						//获取显示数据表单a标签的文本 的页码 <a class="selected">2</a>
		   						var obj = $("a[class='selected']");
		   						var pageraw=obj.text();
		   						var page=pageraw.substring(0,pageraw.length-1);
		   						var link=ctx+"/shop/admin/ordAuto!AllotOrderList.do?"+"page="+page;
		   						$("#serchform").attr('action',link);
		   						$("#serchform").submit();//用于修改后刷新显示锁定人
		   						var linkdefault=ctx+"/shop/admin/ordAuto!AllotOrderList.do";
		   						$("#serchform").attr('action',linkdefault);//恢复ACTION到默认
		   					}
		   					
		   				});
		   				
					}
	   				
	   				
	   			});
	   	
});
</script>