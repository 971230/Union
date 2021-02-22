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
	height: 2.0em;
	outline: 0;
}

.dialog, .dialogIf .body{
height:200px;
}
.accept {
	width: 400px;
	-web-kit-appearance: none;
	-moz-appearance: none;
	font-size: 1.2em;
	height: 1.5em;
	border-radius: 4px;
	border: 1px solid #c8cccf;
	color: #6a6f77;
	outline: 0;
}

a {
	cursor: pointer;
	text-decoration: none;
}

textarea {
	border: 1px solid #c8cccf;
	border-radius: 4px;
}

.sl {
	width: 200px;
	appearance: none;
	-web-kit-appearance: none;
	-moz-appearance: none;
	border: 1px solid #c8cccf;
	border-radius: 4px;
	padding-right: 14px;
}
</style>
<%
%>
<div class="input">
	<form class="validate" method="post" id="teamTochangeInfo" >
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th>团队编号：</th>
							<td><input type="text" name="team.team_id" id="team_ids" value="${teamlist.team_id}" disabled="disabled"	style="width: 145px;" class="ipt_new"></td>
							<th>团队名称：</th>
							<td><input type="text" id="team_name" name="team.team_name" value="${teamlist.team_name}" style="width: 145px;"	class="ipt_new"></td>
							<th>状态：</th>
							<td>
							<select name="team.state" class="ipt_new" id="states" style="width: 145px;">
									<option value="1" ${'1'==teamlist.state?'selected':'' }>正常</option>
									<option value="2" ${'2'==teamlist.state?'selected':'' }>禁用</option>
							</select>
						   </td>
						</tr>
						<tr>
							<th>团队层级：</th>
							<td><select name="team.team_level" disabled="disabled" id="team_level" class="ipt_new"	style="width: 145px;" onchange="checkByChangeinfo(this)">
									<option value="-1">--请选择--</option>
									<option value="province" ${'province'==teamlist.team_level?'selected':'' }>省</option>
									<option value="region" ${'region'==teamlist.team_level?'selected':'' }>地市</option>
									<option value="county" ${'county'==teamlist.team_level?'selected':'' }>县分</option>
							</select></td>
							<th id="region_name_t_change">地市：</th>
							<td>
							<select id="region_name_change" disabled="disabled" name="team.region_id" class="ipt_new" style="width: 145px;" onchange="queryCountyByCityChangeinfo(this)">
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
							</select></td>
						</tr>
						<input type="hidden" name="team.team_id" id="team_id" value="${teamlist.team_id}" 	style="width: 145px;" class="ipt_new">
					   <input type="hidden" id ="region_names" name="team.region_name"  value=""  style="width: 145px;" class="ipt_new">
				       <input type="hidden" id ="county_name" name="team.county_name"  value=""  style="width: 145px;" class="ipt_new">
					</tbody>
				</table>
			</div>

			<div class="pop_btn" align="center" style="margin-top:5px">
				<a name="saveTeamInfo" id="saveTeamsin"  value="${team.team_id}" class="blueBtns"><span>保 存</span></a> &nbsp;&nbsp; 
				<a name="cancelTeam" id="cancelTeam" class="blueBtns"><span>取消</span></a>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		//保存
		$("a[name='saveTeamInfo']").bind("click",function() {
							var region_id = $("#region_name_change").val();
							var team_id = $("#team_ids").val();
							var county_id = $("#order_county_code_change").val();
							var team_name = $("#team_name").val().trim();
							var state = $("#states").val();
							var team_level = $("#team_level").val();
							if (team_name == null ) {
								alert("请输入团队名称");
								return;
							}
							if (team_level == null || team_level == '-1') {
								alert("请选择团队层级");
								return;
							}
							if (state == null || state == '-1') {
								alert("请选择团队状态");
								return;
							}
							if (team_level != null && team_level != '-1') {
								if (team_level == 'region') {
									if (region_id == null || region_id == '-99') {
										alert("请选择地市");
										return;
									}
								}
								if (team_level == 'county') {
									if (county_id == null || county_id == '-99' || county_id == '') {
										alert("请选择县分");
										return;
									}
									if (region_id == null || region_id == '-99' || region_id == '') {
										alert("请选择地市");
										return;
									}
								}
							}
							var region_name = $("#region_name_change").find("option:selected").text();
							var county_name = $("#order_county_code_change").find("option:selected").text();
							if (region_id != '-99' && region_id != '') {
								$("#region_names").val(region_name);
							}
							if (county_id != '-99' && county_id != '') {
								$("#county_name").val(county_name);
							}
							var region_names = $("#region_names").val();
							var county_name = $("#county_name").val();
							var param = {
									  "team.region_id":region_id,
									  "team.team_id": team_id,
									  "team.county_id":county_id,
									  "team.team_name":team_name,
									  "team.state":state,
									  "team.team_level":team_level, 
									  "team.region_names":region_names,
									  "team.county_name":county_name
							 }; 
							 $.ajax({
						     	url:ctx+"/shop/admin/teamAction!teamUpdateInfo.do?ajax=yes",
						     	type: "POST",
						     	dataType:"json",
						     	async:false,
						     	data:param,
						     	success:function(reply){
						     		if(typeof(reply) != "undefined"){
						     			if("0" == reply["result"]){
						     				alert("更新成功");
						     				closeCustomWork1(); 
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
		$("a[name='cancelTeam']").bind("click", function() {
			Eop.Dialog.close("teamChange");
		});
	});
	function checkByChangeinfo(e) {
		var team_level = e.value;
		if (team_level == 'province') {//省份
			$("#region_name_change").hide();
			$("#region_name_t_change").hide();
			$("#order_county_code_change").hide();
			$("#order_county_code_t_change").hide();
		} else if (team_level == 'region') {
			$("#region_name_change").show();
			$("#region_name_t_change").show();
			$("#order_county_code_change").hide();
			$("#order_county_code_t_change").hide();
		} else {
			$("#region_name_change").show();
			$("#region_name_t_change").show();
			$("#order_county_code_change").show();
			$("#order_county_code_t_change").show();
		}
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
	//县分由地市联动展示
	function queryCountyByCityChangeinfo(e) {
		var url = ctx+ "/shop/admin/teamAction!getCountyListByCity.do?ajax=yes";
		Cmp.ajaxSubmit('teamTochangeInfo', '', url, {},
				function(responseText) {
					if (responseText.result == 0) {
						var str = responseText.list;
						var list = eval("(" + str + ")");
						$("#order_county_code_change").empty(); //清空
						$("#order_county_code_change").append(
								"<option value='-99'>--请选择--</option>");
						$.each(list, function(index, obj) {
							$("#order_county_code_change").append(
									"<option value='"+obj.field_value+"'>"
											+ obj.field_value_desc
											+ "</option>");
						});
					} else {
						alert(responseText.message);
					}
			}, 'json');
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