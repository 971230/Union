<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}
</style>
<div id="select_div" style="display: none;">
	<select style='text-align: center; width: 100px;' name='ruleOptTypeArray'>
		<c:forEach items="${optTypeMap }" var="ot">
			<option value='${ot.key }'>${ot.value }</option>
		</c:forEach>
	</select>
</div>
<div class="input">
	<form id="rule_config_add_form" action="" method="post" class="validate">
	<input type="hidden" name="action" value="${action }" />
	<input type="hidden" name="ruleConfig.rule_id" value="${ruleConfig.rule_id }" />
		<div class="tableform">
			<h4>
				规则基本信息
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th>
								规则名称：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" name="ruleConfig.rule_name"
									value="${ruleConfig.rule_name }" class="searchipt" required="true" />
								<span ></span>
							</td>
						</tr>
						<tr>
							<th>
								规则编码：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" name="ruleConfig.rule_code"
									value="${ruleConfig.rule_code }" class="searchipt" required="true" />
								<span ></span>
							</td>
						</tr>
						<tr style="display: none;">
							<th>
								资源文件类型：
							</th>
							<td>
								<select name="ruleConfig.resource_type" class="searchipt" style="width: 142px;">
									<option value="DRL" ${ruleConfig.resource_type=='DRL'?'selected=selected':''} >规则流</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								生效时间：
							</th>
							<td>
								<input type="text"  name="ruleConfig.eff_date" 
									value='${ruleConfig.eff_date}'
									readonly="readonly" style="width: 138px;"
									maxlength="60" class="dateinput ipttxt"  dataType="date" required="true" /> 
								<span id="alarm_task_name_message"></span>
							</td>
						</tr>
						<tr>
							<th>
								失效时间：
							</th>
							<td>
								<input type="text"  name="ruleConfig.exp_date" 
									value='${ruleConfig.exp_date}'
									readonly="readonly" style="width: 138px;"
									maxlength="60" class="dateinput ipttxt"  dataType="date" required="true" /> 
								<span id="alarm_task_name_message"></span>
							</td>
						</tr>
						<tr>
							<th>
								规则描述：
							</th>
							<td>
								<input type="text" size="18" class="ipttxt" name="ruleConfig.rule_desc"
									value="${ruleConfig.rule_desc }" class="searchipt" />
								<span id="alarm_task_name_message"></span>
							</td>
						</tr>
						<tr>
							<th>
								实现方式：
							</th>
							<td>
								<input type="radio" name="ruleConfig.impl_type" value="CF" ${(ruleConfig.impl_type==null||ruleConfig.impl_type=='CF')?'checked':'' } />界面配置
								<input type="radio" name="ruleConfig.impl_type" value="CD" ${ruleConfig.impl_type=='CD'?'checked':'' }/>编码实现
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<!-- 界面配置实现  start -->
		<div id="rule_cf_div" class="tableform" style="display: ${ruleConfig.impl_type!='CD'?'block;':'none;'}">
			<h4>
				规则条件设置 &nbsp;&nbsp;<input type="button" id="add_obj_btn" class="graybtn1" value="添加对象" />
			</h4>
			<div class="division">
				<table id="rule_cond_tb" class="form-table" cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody id="reule_cfg_body">
					<c:forEach items="${ruleObjList }" var="obj">
						<tr id="objid${obj.obj_id}" style="background: #ccc;">
						    <th style="width: 70px;" >对象名称：</th>
						    <td style="width: 100px;" colspan="2">${obj.obj_name}
						    	<input type="hidden" name="selectObjId" value="${obj.obj_id}" />
						    	<input type="hidden" name="selectObjName" value="${obj.obj_name}" />
						    </td>
							<th>对象属性：</th>
							<td>
							<c:forEach items="${obj.objAttrList }" var="attr">
								<span>${attr.attr_name }<input type="button" name="add_obj_attr_btn" attrid="${attr.attr_id }" obj_name="${obj.obj_name}" obj_id="${attr.obj_id}" attr_code="${attr.attr_code}" obj_code="${obj.obj_code}" ename="${attr.attr_name}" class="graybtn1" value="+" />&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</c:forEach>
							</td>
						</tr>
					</c:forEach>
					
					<c:forEach items="${ruleCondList }" var="cd">
						<c:if test="${cd.attr_index>0 }">
							<tr name='connect'><td colspan='4'></td><td>
							<select name='connect_codeArray'>
							<option value='&&' ${cd.pre_log=='&&'?'selected=selected':'' } >AND</option>
							<option value='||' ${cd.pre_log=='||'?'selected=selected':'' }>OR</option>
							</select>
							</td>
							</tr>
						</c:if>
						
						<tr name='cfgs'><td style='text-align:right;width:60px;'><select name='left_bracketsArray'><option value="">&nbsp;&nbsp;</option><option value="(" ${cd.left_brackets=='('?'selected=selected':''}>(</option></select></td>
						<td style='text-align:right;'  colspan='2'>${cd.attr_cname }：</td><td>
						<select style='text-align: center; width: 100px;' name='ruleOptTypeArray'>
							<c:forEach items="${optTypeMap }" var="ot">
								<option value='${ot.key }' ${ot.key==cd.opt_type?'selected=selected':'' }>${ot.value }</option>
							</c:forEach>
						</select>
						</td><td>
						<input type='text' name='ruleCondCValueArray' value='${cd.z_cvalue }' readonly="readonly"/>
						<input type='hidden' name='ruleCondValueArray' value='${cd.z_value }'/>
						<input type='hidden' name='z_ruleObjAttrArray' value='${cd.z_attr_id }' />
						<input type='hidden' name='z_ruleObjIdArray' value='${cd.z_obj_id }' />
						<input type='hidden' name='ruleObjAttrArray' value='${cd.attr_id }'/>
						<input type='hidden' name='ruleObjIdArray' value='${cd.obj_id }' />
						<select name='right_bracketsArray'><option value=''>&nbsp;&nbsp;</option><option value=')' ${cd.right_brackets==')'?'selected=selected':''}>)</option></select>&nbsp;&nbsp;&nbsp;
						<input type='button' name='del_obj_attr_btn' class='graybtn1' value='-' /></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 界面配置实现  end -->
		
		<!-- 规则条件动作  start -->
		<div id="rule_action_div" class="tableform" style="display: ${ruleConfig.impl_type!='CD'?'block;':'none;'}">
			<h4>
				规则条件动作
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0" >
					<tbody id="rule_action_tbody">
						<c:forEach items="${ruleObjList }" var="obj">
							<tr id="objid${obj.obj_id}" style="background: #ccc;"><th style="width: 70px;">对象名称：</th><td style="width: 100px;" colspan="2">${obj.obj_name}</td>
								<th>对象属性：</th>
								<td>
								<c:forEach items="${obj.objAttrList }" var="attr">
									<span>${attr.attr_name }<input type="button" name="add_obj_attr_btn" attrid="${attr.attr_id }" obj_name="${obj.obj_name}" obj_id="${attr.obj_id}" attr_code="${attr.attr_code}" obj_code="${obj.obj_code}" ename="${attr.attr_name}" class="graybtn1" value="+" />&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</c:forEach>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<th style="width: 110px;">计算符号：</th>
							<td colspan='4'>
								<input type="button" name="action_sign" class="graybtn1" value="+" />&nbsp;&nbsp;
								<input type="button" name="action_sign" class="graybtn1" value="-" />&nbsp;&nbsp;
								<input type="button" name="action_sign" class="graybtn1" value="*" />&nbsp;&nbsp;
								<input type="button" name="action_sign" class="graybtn1" value="/" />&nbsp;&nbsp;
								<input type="button" name="action_sign" class="graybtn1" value="=" />&nbsp;&nbsp;
								<input type="button" name="action_sign" class="graybtn1" value="(" />&nbsp;&nbsp;
								<input type="button" name="action_sign" class="graybtn1" value=")" />&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan='5'>
								<textarea id="action_cfg_tax" style="width: 90%;height: 100px;margin-left: 40px;" name="action_script" >${ruleAction.action_script }</textarea>
								<span ></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 规则条件动作  end -->
		
		<!-- 编码实现  start -->
		<div id="rule_script_div" class="tableform" style="display: ${ruleConfig.impl_type=='CD'?'block;':'none;'}">
			<h4>
				规则编码
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0" style="margin-left: 40px;">
					<tbody>
						<tr>
							<td>
								<textarea style="width: 90%;height: 150px;" name="ruleConfig.rule_script" >${ruleConfig.rule_script }</textarea>
								<span ></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 编码实现  end -->
		
		<div class="submitlist" align="center">
			<table align="right">
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input id="rule_submit_btn" type="button" value=" 保存 "
							class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
		<div class="clear"></div>
	</form>
</div>
<!-- 选择对象对话框 -->
<div id="rule_obj_select"></div>
<div id="ass_obj_div"></div>
<script type="text/javascript">

var Rule = {
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"rule_obj_select",modal:false,title:"对象选择", height:"650px",width:"700px"});
		$("#add_obj_btn").bind("click",function(){
			Eop.Dialog.open("rule_obj_select");
			Rule.selectObj();
		});
		
		$("form.validate").validate();
		
		$("#rule_submit_btn").bind("click",function(){
			var url = ctx + "/shop/admin/rule!saveConfigRule.do?ajax=yes";
			var impl_type = $("input[name='ruleConfig.impl_type']:checked").val();
			if("CF"==impl_type){
				var rules = $("input[name='ruleCondCValueArray']");
				if(!rules || rules.length==0){
					alert("请配置配置规则条件");
					return ;
				}
				var v = $("#action_cfg_tax").val();
				if(!v){
					alert("请配置配置规则条件动作");
					return ;
				}
			}
			Cmp.ajaxSubmit('rule_config_add_form','',url,{},function(data){
				alert(data.msg);
				if(data.status==1){
					var plan_rule_edit_v = $("#plan_rule_edit_v");
					if(plan_rule_edit_v && plan_rule_edit_v.val()=='yes'){
						//从方案点过来的修改
						Eop.Dialog.close("rule_edit_dialog");
					}else{
						window.location.href=ctx+"/shop/admin/rule!ruleConfigList.do";
					}
				}
				$.Loading.hide();
			},'json');
		});
		
		$("input[name='action_sign']").bind("click",function(){
			var ta = $("#action_cfg_tax");
			var val = "";
			if(ta && ta.val()){
				val = ta.val();
			}
			ta.val(val+$(this).val());
		});
		
		$("#rule_action_tbody input[name='add_obj_attr_btn']").live("click",function(){
			var ta = $("#action_cfg_tax");
			var val = "";
			if(ta && ta.val()){
				val = ta.val();
			}
			val += "\$"+$(this).attr("obj_name")+"\."+$(this).attr("ename");
			ta.val(val);
		});
		
		$("#reule_cfg_body input[name='add_obj_attr_btn']").live("click",Rule.addRuleCond);
		$("#reule_cfg_body input[name='del_obj_attr_btn']").live("click",function(){
			var tr = $(this).closest("tr");
			var ct = tr.prev("tr[name='connect']");
			if(ct && ct.length>0){
				ct.remove();
			}else{
				var nt = tr.next("tr[name='connect']");
				nt.remove();
			}
			tr.remove();	
		});
		
		$("input[name='ruleConfig.impl_type']").bind("click",function(){
			var val = $(this).attr("value");
			if(val=='CF'){
				$("#rule_cf_div").show();
				$("#rule_action_div").show();
				$("#rule_script_div").hide();
			}else{
				//CD
				$("#rule_cf_div").hide();
				$("#rule_action_div").hide();
				$("#rule_script_div").show();
			}
		});
	},selectObj:function(){
		var url = ctx+"/shop/admin/rule!qryRuleObj.do?ajax=yes";
		var name="";
		var obj = $("input[name='obj_name']");
		if(obj && obj.val()){
			name = obj.val();
		}
		$("#rule_obj_select").load(url,{obj_name:name},function(){
			$("#rule_obj_searchBtn").bind("click",Rule.selectObj);
			$("#obj_select_confirm_btn").bind("click",function(){
				Rule.addSelectObj();
			});
		});
	},addSelectObj:function(){
		var obj_id = $("input[type='radio'][name='objectid']:checked").val();
		if(!obj_id){
			alert("请选择一个对象");
			return ;
		}
		var objbody = $("#objid"+obj_id);
		if(objbody && objbody.length>0){
			alert("对象已存在，不能重复选择");
			return ;
		}
		var url = ctx + "/shop/admin/rule!objSelect.do?ajax=yes&obj_id="+obj_id;
		$.ajax({
			url:url,
			dataType:'json',
			method:'post',
			success:function(data){
				var tr = '<tr id="objid'+data.obj.obj_id+'" style="background: #ccc;"><th style="width: 70px;" >对象名称：</th><td style="width: 100px;" colspan="2">'+data.obj.obj_name+'<input type="hidden" name="selectObjId" value="'+data.obj.obj_id+'" /><input type="hidden" name="selectObjName" value="'+data.obj.obj_name+'" /></td>';
				tr += '<th>对象属性：</th><td>';
				$.each(data.attrs,function(idx,item){
					tr += '<span>'+item.attr_name+'<input type="button" name="add_obj_attr_btn" attrid="'+item.attr_id+'" obj_id="'+item.obj_id+'" attr_code="'+item.attr_code+'" obj_name="'+data.obj.obj_name+'" obj_code="'+data.obj.obj_code+'" ename="'+item.attr_name+'" class="graybtn1" value="+" />&nbsp;&nbsp;&nbsp;&nbsp;</span>';
				});
				tr += '</td></tr>';
				$("#reule_cfg_body").prepend(tr);
				$("#rule_action_tbody").prepend(tr);
				Eop.Dialog.close("rule_obj_select");
			},error:function(a,b){
				alert("添加失败");
			}
		});
	},addRuleCond:function(){
		var attid = $(this).attr("attrid");
		var ename = $(this).attr("ename");
		var attr_code = $(this).attr("attr_code");
		var obj_id = $(this).attr("obj_id");
		var attrs = $(this).closest("tbody").find("tr[name='cfgs']");
		if(attrs && attrs.length>0){
			var ctr = "<tr name='connect'><td colspan='4'></td><td><select name='connect_codeArray'><option value='&&'>AND</option><option value='||'>OR</option></select></td></tr>";
			$(this).closest("tbody").append(ctr);
		}
		var selects = $("#select_div").html();
		var tr = "<tr name='cfgs'><td style='text-align:right;width:60px;'><select name='left_bracketsArray'><option value=''>&nbsp;&nbsp;</option><option value='('>(</option></select></td><td style='text-align:right;'>"+ename+"：</td><td colspan='2'>"+selects+
			"</td><td><input type='text' name='ruleCondCValueArray' value='${cd.z_cvalue }' readonly='readonly'/><input type='hidden' name='ruleCondValueArray' /><input type='hidden' name='z_ruleObjAttrArray' value='' /><input type='hidden' name='z_ruleObjIdArray' value='' /><input type='hidden' name='ruleObjAttrArray' value='"+attid+"'/><input type='hidden' name='ruleObjIdArray' value='"+obj_id+"' />"+
			"<select name='right_bracketsArray'><option value=''>&nbsp;&nbsp;</option><option value=')'>)</option></select>&nbsp;&nbsp;&nbsp;<input type='button' name='del_obj_attr_btn' class='graybtn1' value='-' /></td></tr>";
		$(this).closest("tbody").append(tr);
	}
};

$(function(){
	Eop.Dialog.init({id:"ass_obj_div", modal:false, title:"添加属性", width:"800px"});
	Rule.init();
	$("input[name='ruleCondCValueArray']").live("click", function(){
		var curr = $(this);
		Eop.Dialog.open("ass_obj_div");
		var url = ctx + "/shop/admin/ruleObject!getObjPage.do?ajax=yes";
		$("#ass_obj_div").load(url, function(){
			
			var selectObjIdArray = $("input[name=selectObjId]");
			var selectObjNameArray = $("input[name=selectObjName]");
			
			if(selectObjIdArray && selectObjIdArray.length>0){
				$.each(selectObjIdArray,function(idx,item){
					var li = '<li class="last clk" url="'+ctx+'/shop/admin/ruleObject!queryObjAttrs.do?ajax=yes&obj_id='+$(item).val()+'"><a><span></span>'+$(selectObjNameArray[idx]).val()+'</a></li>';
					$("#select_obj_uls").append(li);
				});
			}
			
			
			$(".clk").unbind("click").bind("click", function(){
				$(".clk").removeClass("curr");
				$(this).addClass("curr");
				var url = $(this).attr("url");
				$("#right_panel").empty();
				$(".submitlist").show();
				$("#right_panel").load(url, function(){
					if(url.indexOf("getGoodsPage.do") > -1){
						 rule_obj.initGoodsClk();
					}
					if(url.indexOf("getPartnerPage.do") > -1){
						rule_obj.initPartnerClk();
					}			
				});
			});
			
			
			$("#ass_obj_div #confirm_btn").unbind("click").click(function(){
				
				if($("#right_panel #create_time").val() != null && $("#right_panel #create_time").val()!= ""){
					curr.val($("#right_panel #create_time").val());
					curr.closest("td").find("input[name=ruleCondValueArray]").val($("#right_panel #create_time").val());
				}
				if($("#right_panel #const_text").val() != null && $("#right_panel #const_text").val()!= ""){
					curr.val($("#right_panel #const_text").val());
					curr.closest("td").find("input[name=ruleCondValueArray]").val($("#right_panel #const_text").val());
				}
				if($("#right_panel #text_page").val() != null && $("#right_panel #text_page").val()!= ""){
					curr.val($("#right_panel #text_page").val());
					curr.closest("td").find("input[name=ruleCondValueArray]").val($("#right_panel #text_page").val());
				}
				//商品
				if($("#right_panel [name='goods_attr']:checked").length>0){
					var goodsInfo = $("#right_panel [name='goods_attr']:checked");
					curr.val($(goodsInfo).attr("goods_name"));
					curr.closest("td").find("input[name=ruleCondValueArray]").val($(goodsInfo).attr("goods_id"));
				}
				//分销商
				if($("#right_panel [name='partner_attr']:checked").length>0){
					var partnerInfo = $("#right_panel [name='partner_attr']:checked");
					curr.val($(partnerInfo).attr("realname"));
					curr.closest("td").find("input[name=ruleCondValueArray]").val($(partnerInfo).attr("username"));
				}
				
				if($("#right_panel #sales_num").val() != null && $("#right_panel #sales_num").val()!= ""){
					curr.val($("#right_panel #sales_num").val());
					curr.closest("td").find("input[name=ruleCondValueArray]").val($("#right_panel #sales_num").val());
				}
				
				if($("#right_panel #sales_amount").val() != null && $("#right_panel #sales_amount").val()!= ""){
					curr.val($("#right_panel #sales_amount").val());
					curr.closest("td").find("input[name=ruleCondValueArray]").val($("#right_panel #sales_amount").val());
				}
				
				if($("#right_panel #obj_attr_select_dlg").val()!=null){
					//对象
					var attr = $("input[name=obj_attr_id]:checked");
					if(!attr || attr.length==0){
						alert("请选择一项属性");
						return ;
					}
					curr.val(attr.attr("attr_name"));
					var td = curr.closest("td");
					td.find("input[name=ruleCondValueArray]").val(attr.attr("attr_code"));
					td.find("input[name=z_ruleObjAttrArray]").val(attr.attr("value"));
					td.find("input[name=z_ruleObjIdArray]").val(attr.attr("obj_id"));
				}
				
				Eop.Dialog.close("ass_obj_div");
			});
		}); 
	});
});
</script>
