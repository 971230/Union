<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style type="text/css">
.red {
	color: red;
}
.icoFontlist{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    color:#5f5f5f;  
    overflow: hidden;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
} 

.icoFontlist:hover  
{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    overflow: scroll;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
    cursor:pointer;   
} 
.detailDiv 
{  
    display: none;   
} 
</style>
<script src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
<script type="text/javascript">
	$(function() {
		 $("#hide_params_tb").bind("click", function() {
			$("#params_tb").hide();
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
		});
		$("#show_params_tb").bind("click", function() {
			$("#params_tb").show();
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
		});
		var state_check= $('#team_levels option:selected').val();
		if(state_check=='province'){
			$("#region_name").hide();
			$("#order_county_code").hide();
			$("#region_name_t").hide();
			$("#order_county_code_t").hide();
		}
		if(state_check=='region'){
			$("#order_county_code").hide();
			$("#order_county_code_t").hide();
		}
		$("a[name='deleteTeam']").bind("click",function(){
			if(confirm("确定删除团队?")) {
			} else {
				return;
			}
			var team_id = $(this).attr("value");
			$.ajax({  
                      type: "POST",  
                      data:"ajax=yes&team_id=" + team_id,  
                      url: ctx + "/shop/admin/teamAction!TeamDelete.do",  
                      dataType: "json",  
                      cache: false,  
                      success: function(responseText){   
                      	if (responseText.result == 0) {
                      		alert(responseText.message);
                      		$("#TeamCustomForm").attr("action", ctx + "/shop/admin/teamAction!showTeamList.do").submit();
					} else {
						alert(responseText.message);
					}  
                  }  
              }); 
		});
		$("#queryTeam").bind("click",function() {
			$("#TeamCustomForm").attr("action", ctx + "/shop/admin/teamAction!showTeamList.do").submit();
		});
		//修改人员团队信息
		Eop.Dialog.init({id:"teamChange",modal:true,title:"团队修改",width:'1100px'});
		$("a[name='teamChange']").bind("click",function(){
			$("#teamChange").empty(); 
			var team_id = $(this).attr("value");
			if(team_id == null || team_id==""){
				alert("异常：team_id为空");
				return;
			}
			var url= ctx+"/shop/admin/teamAction!teamInfoChange.do?ajax=yes&team_id="+team_id;
			Eop.Dialog.open("teamChange");
			$("#teamChange").load(url,{},function(){});
		});
		Eop.Dialog.init({id:"insertTeamInfo",modal:true,title:"人员团队新增",width:'1100px'});
		//人员团队新增
		$("a[name='insertTeam']").bind("click",function() {
			$("#insertTeamInfo").empty(); 
			Eop.Dialog.open("insertTeamInfo");
			var url= ctx+"/shop/admin/teamAction!toInsertTeamJsp.do?ajax=yes";
			$("#insertTeamInfo").load(url,{},function(){});
		});
		
		//团队人员配置
		Eop.Dialog.init({id:"teamUserStaff",modal:true,title:"团队人员配置",width:'1100px'});
		$("a[name='teamUserStaff']").bind("click",function() {
			$("#teamUserStaff").empty(); 
			var team_id = $(this).attr("value");
				if(team_id == null || team_id==""){
					alert("异常：team_id为空");
					return;
			}
			Eop.Dialog.open("teamUserStaff");
			var url= ctx+"/shop/admin/teamAction!teamUserStaff.do?ajax=yes&team_id="+team_id;
			$("#teamUserStaff").load(url,{},function(){});
		});
		
	});
	 (function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {});
			$(this).bind("mouseover", function() {});
		};
	})(jQuery); 
	 
	//县分由地市联动展示
	function queryCountyByCity(e){
		var url = ctx + "/shop/admin/teamAction!getCountyListByCity.do?ajax=yes";
		Cmp.ajaxSubmit('TeamCustomForm', '', url, {}, function(responseText) {
			if (responseText.result == 0) {
				var str = responseText.list;
				var list=eval("("+str+")"); 
				$("#order_county_code").empty(); //清空
				$("#order_county_code").append("<option value='-99'>--请选择--</option>");
				$.each(list, function (index, obj) {
					$("#order_county_code").append("<option value='"+obj.field_value+"'>"+obj.field_value_desc+"</option>");
				});
			} else {
				alert(responseText.message);
			}
		}, 'json');
	};
	function checkByTeam(e){
		var team_level = e.value;
		if(team_level=='province'){//省份
			$("#region_name").hide();
			$("#region_name_t").hide();
			$("#order_county_code").hide();
			$("#order_county_code_t").hide();
		}else if(team_level=='region'){
			$("#order_county_code").hide();
			$("#order_county_code_t").hide();
		}else{
			$("#region_name").show();
			$("#region_name_t").show();
			$("#order_county_code").show();
			$("#order_county_code_t").show();
		}
	};
	function closeCustomWork1(){
		Eop.Dialog.close("teamChange");
		$("#TeamCustomForm").attr("action", ctx + "/shop/admin/teamAction!showTeamList.do").submit();
	};
	function closeAdd(){
		Eop.Dialog.close("insertTeamInfo");
		$("#TeamCustomForm").attr("action", ctx + "/shop/admin/teamAction!showTeamList.do").submit();
	};
</script>
	<div class="searchBx">
		<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
		<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display: none;">展开</a>
		<form action="/shop/admin/teamAction!showTeamList.do" method="post" id="TeamCustomForm" >
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>团队编号：</th>
						<td>
							<input type="text" name="team.team_id" value="${team.team_id}" style="width: 145px;" class="ipt_new">
						</td>
						<th>团队名称：</th>
						<td>
							<input type="text" name="team.team_name" value="${team.team_name}" style="width: 145px;" class="ipt_new">
						</td>
						<th>状态：</th>
						<td>
							<select id="state" name="team.state" class="ipt_new" style="width: 145px;">
								<option value="-1"  >--请选择--</option>
								<option value="1" ${'1'==team.state?'selected':'' }>正常</option>
								<option value="2" ${'2'==team.state?'selected':'' }>禁用</option>
							</select>	
						</td>
					</tr>
					<tr>
						<th>团队层级：</th>
						<td>
							<select id = "team_levels" name="team.team_level" class="ipt_new" style="width: 145px; " onchange="checkByTeam(this)">
								<option value="-1"  >--请选择--</option>
								<option value="province"  ${'province'==team.team_level?'selected':'' }>省</option>
							    <option value="region" ${'region'==team.team_level?'selected':'' }>地市</option>
							    <option value="county" ${'county'==team.team_level?'selected':'' }>县分</option>
							</select>	
						</td>
						<th id="region_name_t">地市：</th>
						<td>
							<select id="region_name" name="team.region_id" class="ipt_new" style="width: 145px;" onchange="queryCountyByCity(this)">
							<option value="-99">--请选择--</option>
								<c:forEach items="${dc_MODE_REGIONList}" var="ds">
			               			<option value="${ds.value }" ${ds.value==team.region_id?'selected':'' }>${ds.value_desc }</option>
			               		</c:forEach>
							</select>
						</td>
						<th id="order_county_code_t">县分：</th>
						<td>
							<select id="order_county_code" name="team.county_id" class="ipt_new" style="width: 145px;">
								<option value="-99">--请选择--</option>
								<c:forEach items="${countyList}" var="ds">
			               			<option value="${ds.field_value}" ${ds.field_value==team.county_id?'selected':''}>${ds.field_value_desc}</option>
			               		</c:forEach>
							</select>	
						</td>
					</tr>
					<tr>
						<th>人员工号：</th>
						<td>
							<input type="text"  name="user_id" value="${user_id}" style="width: 145px;" class="ipt_new">
						<td>
						<td>
							<a href="javascript:void(0);" id="queryTeam" class="dobtn" style="">查询</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" name="insertTeam" id="insertTeams" class="dobtn" style="margin-left: 40px;">新增</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div>
		<form id="gridform" class="grid">
			<grid:grid from="webpage" formId="TeamCustomForm" action="/shop/admin/teamAction!showTeamList.do">
				<grid:header>
					<grid:cell>团队编号</grid:cell>
					<grid:cell>团队名称</grid:cell>
					<grid:cell>团队层级</grid:cell>
					<grid:cell>地市</grid:cell>
					<grid:cell>县分</grid:cell>
					<grid:cell>状态</grid:cell>
					<grid:cell>操作</grid:cell>
				</grid:header>
				<grid:body item="team">
					<grid:cell style="width: 90px;">${team.team_id}</grid:cell>
					<grid:cell style="width: 220px;">${team.team_name}</grid:cell>
					<grid:cell style="width: 120px;">${team.team_level} </grid:cell>
					<grid:cell style="width: 120px;">${team.region_name} </grid:cell>
					<grid:cell style="width: 120px;">${team.county_name}</grid:cell>
					<grid:cell style="width: 100px;">${team.state}</grid:cell>
					<grid:cell> 
						<a href="javascript:void(0);" value="${team.team_id}" name="teamChange" class="dobtn" style="margin-left: 1px;">修改</a>
						<a href="javascript:void(0);" value="${team.team_id}" name="deleteTeam" class="dobtn" style="margin-left: 1px;">删除</a>
						<a href="javascript:void(0);" value="${team.team_id}"  name="teamUserStaff" class="dobtn" style="margin-left: 1px;">人员配置</a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
	<div id="teamChange"></div>
	<div id="insertTeamInfo"></div>
	<div id="teamUserStaff"></div>
