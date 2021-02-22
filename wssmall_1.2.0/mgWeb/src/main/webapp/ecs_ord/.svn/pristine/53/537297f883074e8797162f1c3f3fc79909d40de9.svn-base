<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script>
<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/ordAuto!getHeadquartersMallStaff.do'>
<div class="searchformDiv">
 <table>
	<tr>
	    <th>用户名：</th>
		<td><input type="text" class="ipttxt"  name="staffName"  id ="staffName"  value="${ staffName}"  /></td>
		

		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
	</tr>
 </table>
</div>
</form>
<div class="grid">
	<!--  <div class="toolbar">
		<ul>
			<li><a href="role!add.do">添加</a></li>
		</ul>
		<div style="clear:both"></div>
	</div>
	-->
	<div class="searchformDiv">
	<a href="ordAuto!addHeadquartersMallStaff.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
</div>
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell sort="name" width="15%">用户名</grid:cell> 
	<grid:cell sort="name" width="15%">用户名称</grid:cell> 
	<grid:cell sort="url">用户ID</grid:cell> 
	<grid:cell width="10%">描述</grid:cell>
	<!--<grid:cell width="10%">状态</grid:cell> -->
	<grid:cell width="10%">操作</grid:cell> 
	</grid:header>

  <grid:body item="staff">
        <grid:cell>${staff.staff_code } </grid:cell>
        <grid:cell> ${staff.staff_name }</grid:cell>
        <grid:cell> ${staff.staff_id }</grid:cell> 
        <grid:cell> ${staff.remark }</grid:cell> 
      <!--    <grid:cell> 
        	<c:if test="${staff.status == '0' }">
        		未登录
        	</c:if>
        	<c:if test="${staff.status == '1' }">
        		已登录
        	</c:if>
        </grid:cell>
        -->
        <grid:cell>
         <a  href="javascript:void(0);" name="loginDig" staff_code="${staff.staff_code}" pwd="${staff.password}" >登录</a><span class='tdsper'></span>
        			&nbsp;&nbsp; 
         <a  href="ordAuto!getHeadquartersMallStaffDetail.do?headquartersMall.staff_code=${staff.staff_code}" >修改</a><span class='tdsper'></span>
 					&nbsp;&nbsp; 
		  <a href="ordAuto!deleteHeadquartersMallStaff.do?headquartersMall.staff_code=${staff.staff_code}" onclick="javascript:return confirm('确认删除 吗？删除后不可恢复');" >删除</a>
        </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>

<div id="loginDig">
<div class="searchformDiv">
<form method="post" id="saveCommentsForm" action="">
<table  border="0" cellpadding="0">
	<tr>
		<th>用户名：</th><td><input type="text" class="ipttxt"  name="staff_code" id="staff_code" style="width:280px;margin-top:6px;" readonly="readonly" /></td>
	</tr>
	<tr>
		<th>密码：</th><td><input type="password" class="ipttxt"  name="password" id="password" style="width:280px;margin-top:6px;" readonly="readonly" /></td>
	</tr>
	<tr>
	<th>登录进程：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){
                			$("#order_from_vp,#order_from_a").bind("click",function(){
                				$("#order_from_dv").show();
                			});
                			$("#order_from_chack_all").bind("click",function(){
                    			if(this.checked){
                    				$("input[name=order_from]").attr("checked","checked");
                    				$("#order_from_dv li").addClass("curr");
                    			}else{
                    				$("input[name=order_from]").removeAttr("checked");
                    				$("#order_from_dv li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		$("#order_from_cancel1,#order_from_cancel2").bind("click",function(){
                    			$("#order_from_dv").hide();
                    		});
                    		
                    		$("input[name=order_from]").bind("click",function(){
                    			if(this.checked){
                    				$(this).parents("li").addClass("curr");
                    			}else{
                    				$(this).parents("li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		
                    		initCheckBox("order_from_ivp","order_from");
                    	});
                    	
                    	function selectOrderFroms(){
                			var regions = $("input[name=order_from]:checked");
                			var regionNames = "";
                			var regionIds = "";
                			regions.each(function(idx,item){
                				var name = $(item).attr("c_name");
                				var rid = $(item).attr("value");
                				regionNames += name+",";
                				regionIds += rid+",";
                			});
                			if(regionIds.length>1){
                				regionIds = regionIds.substr(0,regionIds.length-1);
                				regionNames = regionNames.substr(0,regionNames.length-1);
                			}
                			$("#order_from_vp").val(regionNames);
                			$("#order_from_ivp").val(regionIds);
                		}
                    	
                    	//初始化多选框
                    	function initCheckBox(value_id,check_box_name){
                    		var cv = $("#"+value_id).val();
                    		if(cv){
                    			var arr = cv.split(",");
                    			for(i=0;i<arr.length;i++){
                    				var item = arr[i];
                        			var obj = $("input[type=checkbox][name="+check_box_name+"][value="+item+"]");
                        			obj.attr("checked","checked");
                        			obj.parents("li").addClass("curr");
                    			}
                    		}
                    	}
                	</script>
                	<span class="selBox" style="margin-top:6px;width:265px;">
                    	<input type="text" style="width:265px;" name="" value="" id="order_from_vp"  class="ipt" readonly="readonly">
                    	<input type="hidden" name="" value="" id="order_from_ivp" />
                    	<a href="javascript:void(0);" id="order_from_a" class="selArr"></a>
                        <div class="selOp" style="display:none;width:275px;" id="order_from_dv" >
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_from_chack_all">全选</label>
                                <label style="margin-left:70px;" ><a href="javascript:void(0);" class="aCancel" id="order_from_cancel1">关闭</a></label>
                                <label style="margin-left:70px;" ><a href="javascript:void(0);" class="aClear" id="order_from_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${crawlerProcList }" var="item">
                            			<li name="order_from_li"><input type="checkbox" name="order_from" value="${item.ip }:${item.port }" c_name="${item.proc_name }"><span name="order_from_span">&nbsp;&nbsp;${item.ip }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${item.port }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${item.proc_name }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
	
	</tr>
	<tr>
		<th>cookie：</th><td><input type="text" class="ipttxt"  name="cookie" id="cookie" style="width:280px;margin-top:6px;" /></td>
	</tr>
	<tr><th>发送方式：</th><td>
	<input id="phone" type="radio" checked="checked" name="sendType" value="phone" />
	<label for="phone">手机</label>
	<input id="email" type="radio" name="sendType" value="email" />
	<label for="email">邮箱</label>
	</td></tr>
	<tr>
		<th>验证码：</th><td><input type="text" class="ipttxt"  name="message_code" id="message_code"  style="width:280px;margin-top:6px;"/><input type="button" id="sendMessage" class="graybtn1" style="width:80px;margin-left:15px;" value="发送验证码" /></td>
	</tr>
	<tr>
		<th></th><td><div id="login-tips" class="login-tips" style="color: red;"></div></td>
	</tr>

</table>
	<input type="button" id="login" style="margin-left:130px;margin-top:6px;width:200px;hight:120px" class="graybtn1" value="登录" />
</form>
</div>
</div>
<script>
$(function(){
	var time;
	var isSendMsg = false;
	Eop.Dialog.init({id:"loginDig",modal:true,title:"登录",width:'520px'});
	$("a[name='loginDig']").click(function(){
		var staff_code= $(this).attr("staff_code");
		var password = $(this).attr("pwd");
		$("#staff_code").val(staff_code);;
		$("#message_code").val("");
		$("#password").val(password);
		$("#login-tips").text("");
		isSendMsg = false;
		openCommentsDlg();
		return false;
	});
	
	function openCommentsDlg(){
		$("#loginDig").load();
		Eop.Dialog.open("loginDig");
	}
	
	$("#sendMessage").click(function(){
		
		$.ajax({
			type : "post",
			async : false,
			url : ctx + "/shop/admin/ordAuto!sendHeadquartersMallMessage.do?ajax=yes",
			data : {
				staffName:$("#staff_code").val(),
				staffPassword:$("#password").val(),
				sendType:$(":input:radio:checked").next().attr("for")
				},
			dataType : "json",
			success : function(data) {
				if(data.result == 0){
					$("#login-tips").text("验证码发送成功");
					isSendMsg = true;
				}else {
					$("#login-tips").text("验证码发送失败");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	});
	
	$("#login").click(function(){
			$("#login-tips").text("");
			var params = {};
			params.staffName = $("#staff_code").val();
			params.staffPassword = $("#password").val();
			params.messageCode = $("#message_code").val();
			params.cookie = $("#cookie").val();
			params.ip = $("#order_from_ivp").val();
			$.ajax({
				type : "post",
				async : false,
				url : ctx + "/shop/admin/ordAuto!loginHeadquartersMallMessage.do?ajax=yes",
				data : params,
				dataType : "json",
				success : function(data) {
					if(data.result == 0){
						Eop.Dialog.close("loginDig");
						alert("登录成功");
						$("#searchBtn").click();
					}else {
						alert(data.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

				}
			});
			
	});
	
});
</script>