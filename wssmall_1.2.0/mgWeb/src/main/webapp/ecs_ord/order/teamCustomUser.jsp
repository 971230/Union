<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ include file="/commons/taglibs.jsp"%>

<style type="text/css">
.thstyle {
	width: 150px;
	text-align: right;
	-web-kit-appearance: none;
	-moz-appearance: none;
	font-size: 1.0em;
	height: 2.5em;
	outline: 0;
}
.icoFontlist {
		width: 200px;
		font-size: 12px;
		border: 0px solid #ddd;
		color: #5f5f5f;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
.icoFontlist:hover {
	width: 200px;
	font-size: 12px;
	border: 0px solid #ddd;
	overflow: scroll;
	text-overflow: ellipsis;
	white-space: nowrap;
	cursor: pointer;
}
	
.dialog, .dialogIf .body{
	height:auto;
}
a {
	cursor: pointer;
	text-decoration: none;
}

textarea {
	border: 1px solid #c8cccf;
	border-radius: 4px;
}
</style>
<div class="input">
	<form class="validate" method="post" id="teamUserStaff"  action='teamAction!teamUserStaff.do'  style="background:#fbfcfd">
		<div >
			<div style="margin-top: 5px;" >
				<table cellspacing="0" cellpadding="0" border="0" width="100%" class="tab_form">
					<tbody>
						<tr>
							<th>团队编号：</th>
							<td><input type="text" name="team.team_id" id="team_ids" value="${teamlist.team_id}" disabled="disabled"	style="width: 145px;" class="ipt_new"></td>
							<th>团队名称：</th>
							<td><input type="text" id="team_name" name="team.team_name" disabled="disabled" value="${teamlist.team_name}" style="width: 145px;"	class="ipt_new"></td>
							<th>状态：</th>
							<td>
							<select name="team.state" class="ipt_new" id="states" disabled="disabled" style="width: 145px;">
									<option value="1" ${'1'==teamlist.state?'selected':'' }>正常</option>
									<option value="2" ${'2'==teamlist.state?'selected':'' }>禁用</option>
							</select>
						   </td>
						</tr>
						<tr>
							<th>团队层级：</th>
							<td><select name="team.team_level" id="team_level" disabled="disabled" class="ipt_new"	style="width: 145px;" >
									<option value="-1">--请选择--</option>
									<option value="province" ${'province'==teamlist.team_level?'selected':'' }>省</option>
									<option value="region" ${'region'==teamlist.team_level?'selected':'' }>地市</option>
									<option value="county" ${'county'==teamlist.team_level?'selected':'' }>县分</option>
							</select></td>
							<th id="region_name_t_change">地市：</th>
							<td>
							<select id="region_name_change" name="team.region_id" disabled="disabled" class="ipt_new" style="width: 145px;" >
									<option value="-99">--请选择--</option>
									<c:forEach items="${dc_MODE_REGIONList}" var="ds">
										<option value="${ds.value }"	${ds.value==teamlist.region_id?'selected':'' }>${ds.value_desc }</option>
									</c:forEach>
							</select>
							</td>
							<th id="order_county_code_t_change">县分：</th>
							<td><select id="order_county_code_change" disabled="disabled" name="team.county_id" class="ipt_new" style="width: 145px;">
									<option value="-99">--请选择--</option>
									<c:forEach items="${countyList}" var="ds">
										<option value="${ds.field_value}"	${ds.field_value==teamlist.county_id?'selected':''}>${ds.field_value_desc}</option>
									</c:forEach>
							</select>
							</td>
						</tr>
						<input type="hidden" name="team_id" id="team_id" value="${teamlist.team_id}" style="width: 145px;" class="ipt_new">
						<input type="hidden" name="region_id" id="region_id" value="${teamlist.region_id}" 	style="width: 145px;" class="ipt_new">
						<input type="hidden" name="county_id" id="county_id" value="${teamlist.county_id}" 	style="width: 145px;" class="ipt_new">
						<input type="hidden" name="team_level" id="team_level" value="${teamlist.team_level}" 	style="width: 145px;" class="ipt_new">
					</tbody>
				</table>
			</div>
		</div>
		</form>
			
		<div style="width:auto;height:450px;">
			<div style="width:480px;height:50px;float:left">
				<div style="width:auto;text-align:center">
				<br/>
					<h1 style="font-size:20px;">团队人员</h1>
				</div>
				<div style="width:480px;height:40px; margin-top:5px;float:left">
				</div>
				<div id="teamUserOld" style="min-height:350px;overflow-y:auto; max-height:350px;border:1px solid ">
					<table cellspacing="0" cellpadding="0" border="0" width="100%" class="tableOld">
						<thead style="background:#ccc">
							<tr>
								<td style="text-align:center;width:30px">选择</td>
								<td style="text-align:center;width:80px">工号</td>
								<td style="text-align:center;width:200px;">姓名</td>
								<td style="width:80px;text-align:center;">地市</td>
								<td>县分</td>
							</tr>
						</thead>
						<tbody id = "oldList">
						</tbody>
					</table>
				</div>
			</div>
			<div style="width:80px;height:400px;margin-left:5px;float:left">
				<div style="margin-top:150px;text-align:center">
					<a href="javascript:void(0)" class="comBtn" style="margin-right:10px;" id="id_save" onclick="searchAdminAdd()"> <<添加 </a>
					<br/>
					<br/>
					<a href="javascript:void(0)" class="comBtn" style="margin-right:10px;" id="id_save" onclick="searchAdminRemove()"> 移除>> </a>
				</div>
			</div>
			<div style="width:480px;height:100px; margin-left:5px;float:left">
				<div style="width:auto;text-align:center">
					<br/>
					<h1 style="font-size:20px;">可选人员</h1>
				</div>
				<div style="width:480px;height:40px; margin-top:5px;float:left">
					<div>
						人员姓名 / 工号：<input type="text" id="user_id" name="user_id" style="width: 165px;"	class="ipt_new"/>&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:void(0)" class="comBtn" style="margin-right:10px;" id="id_save" onclick="searchAdminUser()">搜索</a>
					</div>
				</div>
				<div id="teamUserNew" style="min-height:350px;overflow-y:auto;max-height:350px;border:1px solid ">
					<table cellspacing="0" cellpadding="0" border="0" margin-left="2px" width="98%" class="tableOld">
					<thead style="background:#ccc;">
						<tr >
							<td style="text-align:center;center;width:30px" >选择</td>
							<td style="text-align:center;width:80px" >工号</td>
							<td style=" text-align:center;width:200px">姓名</td>
							<td style="width:80px;text-align:center;">地市</td>
							<td>县分</td>
						</tr>
					</thead>
					<tbody id = "newList">
					</tbody>
				</table>
				</div>
				
			</div>
		</div>
</div>

<script type="text/javascript">
	$(function() {
	});
	function searchAdminUser(){
		var team_id = $("#team_id").val().trim();
		var user_id = $("#user_id").val().trim();
		var team_level = $("#team_level").val().trim();
		if(team_level == 'province'){//如果是省
			if(user_id == null || user_id==''){
				alert("请输入要查询的人员工号或姓名");
				$("#newList").empty(); //清空
				return;
			}
		}else{
			$("#newList").empty(); //清空
		}
		var region_id = $("#region_id").val();
		var county_id = $("#county_id").val();
		$.ajax({  
	        type: "POST",  
	        data:"ajax=yes&user_id=" + user_id+"&region_id="+region_id+"&county_id="+county_id+"&team_id="+team_id,  
	        url: ctx + "/shop/admin/teamAction!teamUserAll.do",  
	        dataType: "json",  
	        success: function(responseText){
	        	if (responseText.result == 0) {
	        		var str = responseText.list;
	    			var list=eval("("+str+")"); 
	    			$("#newList").empty(); //清空  
	    			$.each(list, function (index, obj) { 
	     				var inputList = "<tr><td style='text-align:center'> <input type='checkbox' name ='useridNew' class='ipt_new' id='useridNew' style ='15px' value='"+obj.userid+"'></input></td><td style='text-align:center;'>"+obj.userid +"</td><td ><div class='icoFontlist' style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis;' title='"+obj.realname +"'><input style='border: 0px;width:200px;text-align:center;' value='"+obj.realname+"' readonly='readonly'/></div></td><td style='text-align:center;'>"+obj.lan_name+"</td><td>"+obj.county_name+"</td></tr>";
	    				$("#newList").append(inputList);
	    			});
				} else {
					alert(responseText.message);
				}  
	   		 }  
		 	});
	};
	var state_check= $('#team_level option:selected').val();
	if(state_check=='province'){
		$("#region_name_change").hide();
		$("#order_county_code_change").hide();
		$("#region_name_t_change").hide();
		$("#order_county_code_t_change").hide();
	}
	if(state_check=='region'){
		$("#order_county_code_change").hide();
		$("#order_county_code_t_change").hide();
	}
	function searchAdminRemove(){
		var strgetSelectValue = "";
		var team_id = $("#team_id").val();
		if(confirm("确定移除团队人员!")) {
		} else {
			return;
		}
		$("input[name='useridOld']:checked").each(function(j) {
			if(j>=0){
				 if(0==j){
					 strgetSelectValue = $(this).val();
				  }else{
					  strgetSelectValue += (","+$(this).val());
				 }
			}
		});
		$("input[name='useridOld']:checked").each(function(j) {
			if(j>=0){
				var user_id= $(this).parent().next().html();
				var user_name= $(this).parent().next().next().children().children().val();
				var lan_id= $(this).parent().next().next().next().html();
				var county_name= $(this).parent().next().next().next().next().html();
 				var inputList = "<tr><td style='text-align:center'> <input type='checkbox' name ='useridNew' class='ipt_new' id='useridNew' style ='15px' value='"+user_id+"'></input></td><td style='text-align:center;'>"+user_id +"</td><td  ><div class='icoFontlist' style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis;' title='"+user_name +"'><input style='border: 0px;width:200px;text-align:center;' value='"+user_name+"' readonly='readonly'/></div></td><td style='text-align:center;'>"+lan_id+"</td><td>"+county_name+"</td></tr>";
				$("#newList").prepend(inputList);
				$(this).parent().parent().remove();
			}
		});
		if(strgetSelectValue == null || strgetSelectValue==''){
			alert("请选择要移除的人员");
			return;
		}
		if(team_id == null || team_id == ''){
			alert("团队编号为空");
			return ;
		}
		$.ajax({  
	        type: "POST",  
	        data:"ajax=yes&team_id=" + team_id+"&team_rel="+strgetSelectValue,  
	        url: ctx + "/shop/admin/teamAction!teamRelRemove.do",  
	        dataType: "json",  
	        success: function(reply){
	        	if(typeof(reply) != "undefined"){
	     			if("0" == reply["result"]){
	     				alert("移除成功");
	     				loadTeamList();
	     			}else{
	     				alert("移除失败");
	     			}
	     		}else{
	     			alert("请求失败");
	     		}
	   		 }  
		 });
		
	};
	function searchAdminAdd(){
		var strgetSelectValue = "";
		var team_id = $("#team_id").val();
		$("input[name='useridNew']:checked").each(function(j) {
			if(j>=0){
				 if(0==j){
					 strgetSelectValue = $(this).val();
				  }else{
					  strgetSelectValue += (","+$(this).val());
				 }
			}
		});
		$("input[name='useridNew']:checked").each(function(j) {
			if(j>=0){
				$(this).parent().parent().remove();
			}
		});
		if(strgetSelectValue == null || strgetSelectValue==''){
			alert("请选择要添加的人员");
			return;
		}
		if(team_id == null || team_id == ''){
			alert("团队编号为空");
			return ;
		}
		$.ajax({  
	        type: "POST",  
	        data:"ajax=yes&team_id=" + team_id+"&team_rel="+strgetSelectValue,  
	        url: ctx + "/shop/admin/teamAction!teamRelAdd.do",  
	        dataType: "json",  
	        success: function(reply){
	        	if(typeof(reply) != "undefined"){
	     			if("0" == reply["result"]){
	     				alert("添加成功");
	     				loadTeamList();
	     			}else if("2" == reply["result"]){
	     				var msg = reply["message"];
	     				alert(msg);
	     			}else{
	     				var msg = reply["message"];
	     				alert(msg);
	     			}
	     		}else{
	     			alert("请求失败");
	     		}
	   		 }  
		 	});
	};
	
	loadTeamList();
	function loadTeamList(){
		var team_id=$("#team_id").val();
		$("#oldList").empty(); //清空
		debugger;
		$.ajax({  
	        type: "POST",  
	        data:"ajax=yes&team_id=" + team_id,  
	        url: ctx + "/shop/admin/teamAction!teamRelList.do",  
	        dataType: "json",  
	        success: function(responseText){
	        	if (responseText.result == 0) {
	        		var str = responseText.list;
	    			var list=eval("("+str+")"); 
	    			$("#oldList").empty(); //清空
	    			debugger;
	    			$.each(list, function (index, obj) {
 	    				var inputList = "<tr><td style='text-align:center'> <input type='checkbox' name ='useridOld' class='ipt_new' id='useridOld' style ='15px' value='"+obj.userid+"'></input></td><td style='text-align:center;'>"+obj.userid +"</td><td > <div class='icoFontlist' style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis;' title='"+obj.realname +"'><input style='border: 0px;width:200px;text-align:center;' value='"+obj.realname+"' readonly='readonly'/></div></td><td style='text-align:center;'>"+obj.lan_name+"</td><td>"+obj.county_name+"</td></tr>";
	    				debugger;
 	    				$("#oldList").append(inputList);
	    			});
				} else {
					alert(responseText.message);
				}  
	   		 }  
		 	});
	};
	(function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {
			});
			$(this).bind("mouseover", function() {
			});
		};
	})(jQuery);
</script>