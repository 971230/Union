<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='server_offer_form'
	action='sysRule!listServer.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>服务名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="serviceOffer.service_offer_name"
						value="${serviceOffer.service_offer_name}" class="searchipt" /></td>
					<th>开始时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="serviceOffer.start_time"
						value="${serviceOffer.start_time}" class="searchipt" /></td>
					<th>结束时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="serviceOffer.end_time"
						value="${serviceOffer.end_time}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="button"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a style="margin-right:10px;" class="graybtn1"
			href="${ctx}/shop/admin/sysRule!addServer.do"><span>添加</span></a>
	</div>
</form>

<div id="serverList">
	<div class="grid">
		<form method="POST" id="server_offer_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell sort="sn" style="width: 100px;">服务名称</grid:cell>
					<grid:cell style="width: 100px;">服务描述</grid:cell>
					<grid:cell style="width: 80px;">创建时间</grid:cell>
					<grid:cell style="width: 80px;">服务编码</grid:cell>
					<grid:cell style="width: 80px;">操作</grid:cell>
				</grid:header>
				<grid:body item="service">
					<grid:cell>${service.service_offer_name}</grid:cell>
					<grid:cell>${service.remark}</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd"
							d_time="${service.create_date}"></html:dateformat>
					</grid:cell>
					<grid:cell>${service.service_code}</grid:cell>
					<grid:cell>
						<a href="sysRule!editServer.do?service_id=${service.service_id}">修改
						</a>
						<span class='tdsper'></span>
						<a href="javascript:void(0);" attr="${service.service_id}"
							class="delServer">删除 </a>
						<span class='tdsper'></span>
						<a href="javascript:void(0);" attr="${service.service_id}"
							class="linkRule">关联规则 </a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div id="del_server_div"></div>
<div id="link_rule_div"></div>
<div id="list_rule_div"></div>
<div id="save_rule_div"></div>
<script>
	$(function() {
		Eop.Dialog.init({id:"link_rule_div",modal:false,title:"关联规则",width:"1000px"});
		Eop.Dialog.init({id:"list_rule_div",modal:false,title:"规则列表",width:"1000px"});
		
		$("input[dataType='date']").datepicker();
		
		$(".delServer").bind("click",function() {
			if(!confirm("是否确认删除此条记录？")){
				return;
			}
			var service_id = $(this).attr("attr");
			var url = ctx
					+ "/shop/admin/sysRule!delServer.do?ajax=yes&serviceOffer.service_id="
					+ service_id;
			Cmp.ajaxSubmit('del_server_div', '', url, {},ServerList.jsonBack, 'json');
		});
		$(".linkRule").bind("click",function(){
			var service_id = $(this).attr("attr");
			Eop.Dialog.open("link_rule_div");
			var url =ctx + "/shop/admin/sysRule!linkRule.do?ajax=yes&service_id="+service_id;
			$("#link_rule_div").load(url,function(){
				ServerList.link_rule_init();
			}); 
		});
	});
	
	
	var ServerList = {
		jsonBack : function(responseText) {
			if (responseText.result == 1) {
				alert("操作成功");
				$("#submitButton").trigger("click");
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}
		},
		link_rule_init:function(){
			$(".addRules").unbind("click").bind("click",function(){
				Eop.Dialog.open("list_rule_div");
				var url = ctx + "/shop/admin/sysRule!listRule.do?ajax=yes&is_select=0";
				$("#list_rule_div").load(url,function(){
					ServerList.list_rule_init();
				}); 
			});
			$(".delLinkRule").live("click",function(){
				$(this).closest("tr").remove();
			});
			$(".insureRules").unbind("click").bind("click",function(){
				var rule_id = "";
				var service_id = $("#hidden_service_id").val();
				$(".hidden_rule_id").each(function(){
					rule_id += $(this).val()+",";
				});
				var url = ctx + "/shop/admin/sysRule!saveLinkRule.do?ajax=yes&rule_id="+rule_id+"&service_id="+service_id;
				Cmp.ajaxSubmit('save_rule_div','',url,{},ServerList.saveJsonBack,'json');
			});
		},
		list_rule_init:function(){
			function ruleListSpec(){
				this.list = [];
			}
			function ruleSpec(){
				this.rule_id = "";
				this.rule_name = "";
				this.rule_code = "";
				this.rule_type = "";
			}
			
			var ruleList = new ruleListSpec();
			$("input[name='ruleSubBtn']").unbind("click").bind("click",function(){
				var url = ctx + "/shop/admin/sysRule!listRule.do?ajax=yes&is_select=0";
				Cmp.ajaxSubmit('rule_list_form','list_rule_div',url,{},ServerList.list_rule_init);
				return false;
			});
			$("#sltRule").unbind("click").bind("click",function(){
				$("input[name='select_rule']:checked").each(function(){
					var rule = new ruleSpec();
					rule.rule_id = $(this).attr("rule_id");
					rule.rule_name = $(this).attr("rule_name");
					rule.rule_code = $(this).attr("rule_code");
					rule.rule_type = $(this).attr("rule_type");
					ruleList.list.push(rule);
				});
				
				
				//取数据绘制页面
				var html = "";
				var list = ruleList.list;
				for(var i=0;i<list.length;i++){
					var rule = list[i];
					var id = rule.rule_id;
					var flag = true;
					
					$(".hidden_rule_id").each(function(){
						var h_id = $(this).val();
						if(id == h_id){
							flag = false;
						}
					});
					
					if(flag){
						html += "<tr>";
						html += "<td>"+rule.rule_name+"</td>";
						if(rule.rule_type == "accept"){
							html += "<td>受理类规则</td>";
						}
						if(rule.rule_type == "delvery"){
							html += "<td>发货类规则</td>";
						}
						if(rule.rule_type == "pay"){
							html += "<td>支付类规则</td>";
						}
						if(rule.rule_type == "insure"){
							html += "<td>确认类规则</td>";
						}
						html += "<td>"+rule.rule_code+"</td>";
						html += "<td><a href='javascript:void(0);' class='delLinkRule'>删除 </a></td>";
						html += "<input type='hidden' class='hidden_rule_id' value='"+rule.rule_id+"'/>";
						html += "</tr>";
					}
				}
				if($("#rule_tr").find("tr").length == 0){
					$("#rule_tr").html(html);
				}else{
					$("#rule_tr>tr:eq(0)").after(html);
				}
				Eop.Dialog.close("list_rule_div");
			});
		},
		saveJsonBack: function(responseText) {
			if (responseText.result == 1) {
				alert("保存成功");
				Eop.Dialog.close("link_rule_div");
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}
		}
	}
</script>
