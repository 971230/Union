<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ include file="/commons/taglibs.jsp"%>

<style type="text/css">
/* 	.thstyle {
		width: 150px;
		text-align: right;
		-web-kit-appearance:none;
  		-moz-appearance: none;
  		font-size:1.0em;
		height:1.5em;
		outline:0;
	}
	a{
		cursor:pointer;
		text-decoration:none;
	}
	textarea{
		border:1px solid #c8cccf;
		border-radius:4px;
	}
	.sl{
		width: 200px;
		appearance:none;
		-web-kit-appearance:none;
  		-moz-appearance: none;
		border:1px solid #c8cccf;
		border-radius:4px;
		padding-right: 14px;
	} */
</style>
<%
%>
<div class="input">
	<form class="validate"  method="post" id="teamCustomAdd" >
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%" >
				 <tbody>
					<tr>
						<th style="width:105px">团队名称：</th>
						<td style="width:145px">
							<input type="text" name="team.team_name" id="team_name"  style="width: 145px;" class="ipt_new">
						</td  >
						<th style="width:105px">状态：</th>
						<td style="width:145px">
							<select name="team.state" class="ipt_new" id="states" style="width: 145px;">
								<option value="1" selected >正常</option>
								<option value="2" >禁用</option>
							</select>	
						</td>
						<th style="width:105px"></th>
						<td style="width:145px"></td>
					</tr>
					<tr>
						<th>团队层级：</th>
						<td>
							<select name="team.team_level" class="ipt_new"  id="team_level" style="width: 145px; " onchange="checkByTeamAdd(this)">
								<option value="-1"  >--请选择--</option>
								<option value="province"  >省</option>
							    <option value="region" >地市</option>
							    <option value="county">县分</option>
							</select>	
						</td>
						<th id="region_name_t_add">地市：</th>
						<td>
							<select id="region_name_add" name="team.region_id" class="ipt_new" style="width: 145px;" onchange="queryCountyByCityAdd(this)">
							<option value="-99">--请选择--</option>
								<c:forEach items="${dc_MODE_REGIONList}" var="ds">
			               			<option value="${ds.value }" >${ds.value_desc }</option>
			               		</c:forEach>
							</select>
						</td>
						<th id="order_county_code_t_add">县分：</th>
						<td>
							<select id="order_county_code_add" name="team.county_id"  class="ipt_new" style="width: 145px;">
								<option value="-99">--请选择--</option>
								<c:forEach items="${countyList}" var="ds">
			               			<option value="${ds.field_value}" >${ds.field_value_desc}</option>
			               		</c:forEach>
							</select>	
						</td>
				       <input type="hidden" id ="region_names" name="team.region_name"    style="width: 145px;" class="ipt_new">
				       <input type="hidden" id ="county_name" name="team.county_name"    style="width: 145px;" class="ipt_new">
					</tr>
				</tbody>
			</table>
			</div>

			<div class="pop_btn" align="center">
				<a name="saveTeam" id="saveTeam"  class="blueBtns"><span>保 存</span></a>
				&nbsp;&nbsp;
				<a name="cancelTeam" id="cancelTeam" class="blueBtns"><span>取消</span></a>
			</div>
		 </div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		//保存
		$("a[name='saveTeam']").bind("click",function(){
			var region_id=$("#region_name_add").val();
			var county_id = $("#order_county_code_add").val();
			var team_name= $("#team_name").val().trim();
			var state = $("#states").val();
			var team_level = $("#team_level").val();
		    if(team_name == null){
				alert("请输入团队名称");
				return;
		    }
		    if(team_level == null || team_level == '-1'){
		    	alert("请选择团队层级");
		    	return;
		    }
		    if(state == null || state == '-1'){
		    	alert("请选择团队状态");
		    	return;
		    }
		    if(team_level != null && team_level !='-1'){
		    	if(team_level == 'region'){
		    		if(region_id == null || region_id =='-99' || region_id==''){
		    			alert("请选择地市");
		    			return;
		    		}
		    	}
		    	if(team_level == 'county'){
		    		if(region_id == null || region_id =='-99' || region_id==''){
		    			alert("请选择地市");
		    			return;
		    		}
		    		if(county_id == null || county_id == '-99' || county_id=='' ){
		    			alert("请选择县分");
		    			return;
		    		}
		    	}
		    }
		   var region_name= $("#region_name_add").find("option:selected").text();
		   var county_name =  $("#order_county_code_add").find("option:selected").text();
		   if(region_id !='-99' && region_id != ''){
		     $("#region_names").val(region_name);
		   }
		   if(county_id !='-99' && county_id != ''){
		     $("#county_name").val(county_name);
		   }
		   var region_names = $("#region_names").val();
			var county_name = $("#county_name").val();
			 var param = {
					  "team.region_id":region_id,
					  "team.county_id":county_id,
					  "team.team_name":team_name,
					  "team.state":state,
					  "team.team_level":team_level, 
					  "team.region_names":region_names,
					  "team.county_name":county_name
			   };
			 $.ajax({
			     	url:ctx +"/shop/admin/teamAction!teamInfoInsert.do?ajax=yes",
			     	type: "POST",
			     	dataType:"json",
			     	async:false,
			     	data:param,
			     	success:function(reply){
			     		if(typeof(reply) != "undefined"){
			     			if("0" == reply["result"]){
			     				alert("添加成功");
			     				closeAdd();
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
			     	},
				});
		  	});
		
		$("a[name='cancelTeam']").bind("click",
			function() {
					Eop.Dialog.close("insertTeamInfo");
			});
	});
	function checkByTeamAdd(e){
		var team_level = e.value;
		if(team_level=='province'){//省份
			$("#region_name_add").hide();
			$("#region_name_t_add").hide();
			$("#order_county_code_add").hide();
			$("#order_county_code_t_add").hide();
		}else if(team_level=='region'){
			$("#region_name_add").show();
			$("#region_name_t_add").show();
			$("#order_county_code_add").hide();
			$("#order_county_code_t_add").hide();
		}else{
			$("#region_name_add").show();
			$("#region_name_t_add").show();
			$("#order_county_code_add").show();
			$("#order_county_code_t_add").show();
		}
	};
	 
	//县分由地市联动展示
	function queryCountyByCityAdd(e){
		var url = ctx + "/shop/admin/teamAction!getCountyListByCity.do?ajax=yes";
		Cmp.ajaxSubmit('teamCustomAdd', '', url, {}, function(responseText) {
			if (responseText.result == 0) {
				var str = responseText.list;
				var list=eval("("+str+")"); 
				$("#order_county_code_add").empty(); //清空
				$("#order_county_code_add").append("<option value='-99'>--请选择--</option>");
				$.each(list, function (index, obj) {
					$("#order_county_code_add").append("<option value='"+obj.field_value+"'>"+obj.field_value_desc+"</option>");
				});
			} else {
				alert(responseText.message);
			}
		}, 'json');
	};
	(function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {});
			$(this).bind("mouseover", function() {});
		};
	})(jQuery);
	
	
	
</script>