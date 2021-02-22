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
	<select style='text-align: center; width: 80px;' name='ruleOptTypeArray'>
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
		<div id="cf_edit_div" style="display: ${ruleConfig.impl_type!='CD'?'block;':'none;'}">
		<c:if test="${condSize==0 }">
		<div name="rulepackage" >
			<input type='hidden' name='ruleObjIdArray' value='group' />
			<div class="tableform">
				<h4>
					规则条件设置 &nbsp;&nbsp;<input type="button" name="add_obj_btn" class="graybtn1" value="添加对象" />
					&nbsp;<span style="color: red;">比较符号说明请看页尾</span>
				</h4>
				<div class="division">
					<table id="rule_cond_tb" class="form-table" cellspacing="0" cellpadding="0" border="0" width="100%">
						<tbody name="reule_cfg_body">
						</tbody>
					</table>
				</div>
				<h4>
					规则条件结果参与计算
				</h4>
				<div class="division">
					<table class="form-table" cellspacing="0" cellpadding="0" border="0" >
						<tbody name="rule_action_tbody">
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
									<textarea  style="width: 90%;height: 60px;margin-left: 40px;" name="action_script" ></textarea>
									<span ></span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<h4>
					规则条件常量不参与计算
				</h4>
				<div class="division">
					<table class="form-table" cellspacing="0" cellpadding="0" border="0" >
						<tbody name="rule_consts_tbody">
							
						</tbody>
						<tr >
							<td colspan='5'>
							<br />
							<input type='hidden' name='ruleConstValueArray' style='width:160px;' value='group'/>
							<input type='hidden' name='ruleConstObjIdArray' value='group' />
							<input type='hidden' name='ruleConstAttrIdArray' value='group' />
							<input type='hidden' name='ruleConstObjCodeArray' value='group' />
							<input type='hidden' name='ruleConstAttrCodeArray' value='group' />
								<span style="float: right;">
								<input type="button" name="add_rules_btn" class="graybtn1" value="添加规则" /> &nbsp;&nbsp;&nbsp;
								</span>
							</td>
						</tr>
					</table>
				</div>
				
			</div>
			</div>
			</c:if>
			
		
		<c:forEach items="${configListAuditList }" var="cfglist" varStatus="cfglistStatus">
		  <div name="rulepackage">
		  	<input type='hidden' name='ruleObjIdArray' value='group' />
			<div class="tableform">
				<h4>
					规则条件设置 &nbsp;&nbsp;<input type="button" name="add_obj_btn" class="graybtn1" value="添加对象" />
					&nbsp;<span style="color: red;">比较符号说明请看页尾</span>
				</h4>
				<div class="division">
					<table id="rule_cond_tb" class="form-table" cellspacing="0" cellpadding="0" border="0" width="100%">
						<tbody name="reule_cfg_body">
						<c:forEach items="${cfglist.ruleObjList }" var="obj">
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
						
						<c:forEach items="${cfglist.ruleCondAuditList }" var="cd">
							<c:if test="${cd.pre_log!=null }">
								<tr name='connect'><td colspan='4'></td><td>
								<select name='connect_codeArray'>
								<option value='&&' ${cd.pre_log=='&&'?'selected=selected':'' } >AND</option>
								<option value='||' ${cd.pre_log=='||'?'selected=selected':'' }>OR</option>
								</select>
								</td>
								</tr>
							</c:if>
							
							<tr name='cfgs'><td style='text-align:right;width:60px;'><select name='left_bracketsArray'><option value="">&nbsp;&nbsp;</option><option value="(" ${cd.left_brackets=='('?'selected=selected':''}>(</option></select></td>
							<td style='text-align:right;width: 150px;' colspan='2'>${cd.attr_cname }：</td><td>
							<select style='text-align: center; width: 80px;' name='ruleOptTypeArray'>
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
							<input type='hidden' name='ruleObjIdArray' value='${cd.obj_id }' />&nbsp;&nbsp;
							<select name='right_bracketsArray'><option value=''>&nbsp;&nbsp;</option><option value=')' ${cd.right_brackets==')'?'selected=selected':''}>)</option></select>&nbsp;&nbsp;&nbsp;
							<input type='button' name='del_obj_attr_btn' class='graybtn1' value='-' /></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			<!-- </div> -->
			<!-- 规则条件动作  start -->
			<!-- <div id="rule_action_div" class="tableform"> -->
				<h4>
					规则条件结果参与计算
				</h4>
				<div class="division">
					<table class="form-table" cellspacing="0" cellpadding="0" border="0" >
						<tbody name="rule_action_tbody">
							<c:forEach items="${cfglist.ruleObjList }" var="obj">
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
								<!-- id="action_cfg_tax" -->
									<textarea  style="width: 90%;height: 60px;margin-left: 40px;" name="action_script" >${cfglist.ruleActionAudit.action_script }</textarea>
									<span ></span>
								</td>
							</tr>
							
						</tbody>
					</table>
				</div>
				
				<h4>
					规则条件常量不参与计算
				</h4>
				<div class="division">
					<table class="form-table" cellspacing="0" cellpadding="0" border="0" >
						<tbody name="rule_consts_tbody">
							<c:forEach items="${cfglist.ruleObjList }" var="obj">
								<tr id="objid${obj.obj_id}" style="background: #ccc;"><th style="width: 70px;">对象名称：</th><td style="width: 100px;" colspan="2">${obj.obj_name}</td>
									<th>对象属性：</th>
									<td>
									<c:forEach items="${obj.objAttrList }" var="attr">
										<span>${attr.attr_name }<input type="button" name="add_obj_attr_btn" attrid="${attr.attr_id }" obj_name="${obj.obj_name}" obj_id="${attr.obj_id}" attr_code="${attr.attr_code}" obj_code="${obj.obj_code}" ename="${attr.attr_name}" class="graybtn1" value="+" />&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</c:forEach>
									</td>
								</tr>
							</c:forEach>
							<c:forEach items="${cfglist.constAuditList }" var="cs">
							<tr>
								<td style='width:160px;text-align:right;'>${cs.attr_name }：</td>
								<td>
									<input type='text' name='ruleConstValueArray' style='width:160px;' value='${cs.const_value }'/>
									<input type='hidden' name='ruleConstObjIdArray' value='${cs.obj_id }' />
									<input type='hidden' name='ruleConstAttrIdArray' value='${cs.attr_id }' />
									<input type='hidden' name='ruleConstObjCodeArray' value='${cs.obj_code }' />
									<input type='hidden' name='ruleConstAttrCodeArray' value='${cs.attr_code }' />
								</td>
								<td colspan="2">
									&nbsp;&nbsp;<input type='button' name='del_consts_btn' class='graybtn1' value=' - ' />
								</td>
							</tr>
							</c:forEach>
						</tbody>
						<tr >
							<td colspan='5'>
							<br />
							<input type='hidden' name='ruleConstValueArray' style='width:160px;' value='group'/>
							<input type='hidden' name='ruleConstObjIdArray' value='group' />
							<input type='hidden' name='ruleConstAttrIdArray' value='group' />
							<input type='hidden' name='ruleConstObjCodeArray' value='group' />
							<input type='hidden' name='ruleConstAttrCodeArray' value='group' />
								<span style="float: right;">
								<input type="button" name="add_rules_btn" class="graybtn1" value="添加规则" /> &nbsp;
								<c:if test="${cfglistStatus.index>0 }">
									<input type="button" name="del_rules_btn" class="graybtn1" value="删除规则" /> 
								</c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</td>
						</tr>
					</table>
				</div>
				
			</div>
		    <!-- 规则条件动作  end -->
		  </div>
		  </c:forEach>
		</div>
		<!-- 界面配置实现  end -->
		
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
						<input name="rule_submit_btn" type="button" isaudit="no" value=" 保存  "
							class="submitBtn" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="rule_submit_btn" type="button" isaudit="yes" value=" 保存 并提交审核"
							class="submitBtn" />	
					</td>
				</tr>
			</table>
		</div>
		<div class="clear"></div>
	</form>
	
	<div class="tableform">
			<h4>
				比较符号说明
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<td>
							    数据类型比较符：大于、大于等于、小于、小于等于、等于、不等于<span style="color: red;">（注：只能比较数字）</span>
							</td>
						</tr>
						<tr>
							<td>
							    正侧表达式操作符：matches、not matches（正则表达式进行相似匹配，被比较的字符串可以是一个标准的Java 正则表达式。字符串类型可以使用该表达比较）
							</td>
						</tr>
						<tr>
							<td>
							    包含操作符：contains、not contains（是用来检查一个Fact 对象的某个字段[该字段要是一个Collection 或是一个Array 类型的对象]是否包含一个指定的对象）
							</td>
						</tr>
						<tr>
							<td>
							    包含操作符：memberof、not memberof（是用来判断某个Fact 对象的某个字段是否在一个集合[Collection/Array]当中）
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
</div>


<div id="conpackage">
<div name="rulepackage" style="display: none;">
<input type='hidden' name='ruleObjIdArray' value='group' />
<div class="tableform">
	<h4>
		规则条件设置 &nbsp;&nbsp;<input type="button" name="add_obj_btn" class="graybtn1" value="添加对象" />
		&nbsp;<span style="color: red;">操作符号说明请看页尾</span>
	</h4>
	<div class="division">
		<table id="rule_cond_tb" class="form-table" cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody name="reule_cfg_body">
			</tbody>
		</table>
	</div>
	<h4>
		规则条件结果参与计算
	</h4>
	<div class="division">
		<table class="form-table" cellspacing="0" cellpadding="0" border="0" >
			<tbody name="rule_action_tbody">
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
						<textarea  style="width: 90%;height: 60px;margin-left: 40px;" name="action_script" ></textarea>
						<span ></span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<h4>
		规则条件常量不参与计算
	</h4>
	<div class="division">
		<table class="form-table" cellspacing="0" cellpadding="0" border="0" >
			<tbody name="rule_consts_tbody">
				
			</tbody>
			<tr >
				<td colspan='5'>
				<br />
				<input type='hidden' name='ruleConstValueArray' style='width:160px;' value='group'/>
				<input type='hidden' name='ruleConstObjIdArray' value='group' />
				<input type='hidden' name='ruleConstAttrIdArray' value='group' />
				<input type='hidden' name='ruleConstObjCodeArray' value='group' />
				<input type='hidden' name='ruleConstAttrCodeArray' value='group' />
					<span style="float: right;">
					    <input type="button" name="add_rules_btn" class="graybtn1" value="添加规则" /> &nbsp;
						<input type="button" name="del_rules_btn" class="graybtn1" value="删除规则" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</span>
				</td>
			</tr>
		</table>
	</div>
				
</div>
</div>
</div>
<!-- 选择对象对话框 -->
<div id="rule_obj_select"></div>
<div id="ass_obj_div"></div>
<script type="text/javascript">

var Rule = {
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"rule_obj_select",modal:false,title:"对象选择", height:"650px",width:"700px"});
		$("div[name=rulepackage] input[name=add_obj_btn]").live("click",function(){
			var self = this;
			Eop.Dialog.open("rule_obj_select");
			Rule.selectObj(self);
		});
		
		$("form.validate").validate();
		
		$("input[name=add_rules_btn]").live("click",function(){
			var ruleDiv = $("#conpackage div[name=rulepackage]").clone();
			$("#cf_edit_div").append(ruleDiv);
			ruleDiv.show();
		});
		$("input[name=del_rules_btn]").live("click",function(){
			$(this).closest("div[name=rulepackage]").remove();
		});
		
		$("input[name=del_consts_btn]").live("click",function(){
			$(this).closest("tr").remove();
		});
		
		
		$("input[name=rule_submit_btn]").bind("click",function(){
			var isaudit = $(this).attr("isaudit");
			var url = ctx + "/shop/admin/rule!saveConfigRule.do?ajax=yes&isaudit="+isaudit;
			var impl_type = $("input[name='ruleConfig.impl_type']:checked").val();
			if("CF"==impl_type){
				var ruledivs = $("#cf_edit_div div[name=rulepackage]");
				if(ruledivs && ruledivs.length>0){
					for(var i=0;i<ruledivs.length;i++){	
						var item = ruledivs[i];
						var rules = $(item).find("input[name='ruleCondCValueArray']");
						if(!rules || rules.length==0){
							alert("请配置配置规则条件");
							return ;
						}
						var action = $(item).find("textarea[name=action_script]").val();
						if(!action){
							alert("请配置配置规则条件动作");
							return ;
						}
					}
				}else{
					alert("请配置配置规则条件");
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
		
		$("input[name='action_sign']").live("click",function(){
			var ta = $(this).closest("div[name=rulepackage]").find("textarea[name=action_script]");
			var val = "";
			if(ta && ta.val()){
				val = ta.val();
			}
			ta.val(val+$(this).val());
		});
		
		$("tbody[name=rule_action_tbody] input[name='add_obj_attr_btn']").live("click",function(){
			var ta =$(this).closest("div[name=rulepackage]").find("textarea[name=action_script]");
			var val = "";
			if(ta && ta.val()){
				val = ta.val();
			}
			val += "\$"+$(this).attr("obj_name")+"\."+$(this).attr("ename");
			ta.val(val);
		});
		
		$("tbody[name=rule_consts_tbody] input[name='add_obj_attr_btn']").live("click",function(){
			//attrid="${attr.attr_id }" obj_name="${obj.obj_name}" obj_id="${attr.obj_id}" attr_code="${attr.attr_code}" obj_code="${obj.obj_code}" ename="${attr.attr_name}" 
			var attrid = $(this).attr("attrid");
			var isexist = $(this).closest("tbody[name=rule_consts_tbody]").find("input[name=ruleConstAttrIdArray][value="+attrid+"]");
			if(isexist && isexist.length>0){
				alert("属性已存在，不能重复设置。");
				return ;
			}
			var tr = "<tr><td style='width:160px;text-align:right;'>"+$(this).attr("ename")+"：</td><td>";
			tr += "<input type='text' name='ruleConstValueArray' style='width:160px;'/>";
			tr += "<input type='hidden' name='ruleConstObjIdArray' value='"+$(this).attr("obj_id")+"' />";
			tr += "<input type='hidden' name='ruleConstAttrIdArray' value='"+attrid+"' />";
			tr += "<input type='hidden' name='ruleConstObjCodeArray' value='"+$(this).attr("obj_code")+"' />";
			tr += "<input type='hidden' name='ruleConstAttrCodeArray' value='"+$(this).attr("attr_code")+"' />";
			tr += "</td><td colspan='2'>&nbsp;&nbsp;<input type='button' name='del_consts_btn' class='graybtn1' value=' - ' />";
			tr += "</td></tr>";
			$(this).closest("tbody[name=rule_consts_tbody]").append(tr);
		});
		
		$("tbody[name=reule_cfg_body] input[name='add_obj_attr_btn']").live("click",Rule.addRuleCond);
		$("tbody[name=reule_cfg_body] input[name='del_obj_attr_btn']").live("click",function(){
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
				//$("#rule_cf_div").show();
				//$("#rule_action_div").show();
				$("#cf_edit_div").show();
				$("#rule_script_div").hide();
			}else{
				//CD
				//$("#rule_cf_div").hide();
				//$("#rule_action_div").hide();
				$("#cf_edit_div").hide();
				$("#rule_script_div").show();
			}
			try{
				if (event.stopPropagation) { 
					event.stopPropagation(); 
				}else if (window.event) { 
					window.event.cancelBubble = true; 
				}
			}catch(e){
				
			}
		});
	},selectObj:function(cobj){
		var url = ctx+"/shop/admin/rule!qryRuleObj.do?ajax=yes";
		var name="";
		var obj = $("input[name='obj_name']");
		if(obj && obj.val()){
			name = obj.val();
		}
		$("#rule_obj_select").load(url,{obj_name:name},function(){
			$("#rule_obj_searchBtn").bind("click",function(){Rule.selectObj(cobj);});
			$("#obj_select_confirm_btn").bind("click",function(){Rule.addSelectObj(cobj);});
		});
	},addSelectObj:function(cobj){
		var obj_id = $("input[type='radio'][name='objectid']:checked").val();
		if(!obj_id){
			alert("请选择一个对象");
			return ;
		}
		var objbody = $(cobj).closest("div[name=rulepackage]").find("#objid"+obj_id);
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
				$(cobj).closest("div[name=rulepackage]").find("tbody[name=reule_cfg_body]").prepend(tr);
				$(cobj).closest("div[name=rulepackage]").find("tbody[name=rule_action_tbody]").prepend(tr);
				$(cobj).closest("div[name=rulepackage]").find("tbody[name=rule_consts_tbody]").prepend(tr);
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
		var tr = "<tr name='cfgs'><td style='text-align:right;width:60px;'><select name='left_bracketsArray'><option value=''>&nbsp;&nbsp;</option><option value='('>(</option></select></td><td style='text-align:right;width: 150px;'>"+ename+"：</td><td colspan='2'>"+selects+
			"</td><td><input type='text' name='ruleCondCValueArray' value='${cd.z_cvalue }' readonly='readonly'/><input type='hidden' name='ruleCondValueArray' /><input type='hidden' name='z_ruleObjAttrArray' value='' /><input type='hidden' name='z_ruleObjIdArray' value='' /><input type='hidden' name='ruleObjAttrArray' value='"+attid+"'/><input type='hidden' name='ruleObjIdArray' value='"+obj_id+"' />"+
			"&nbsp;&nbsp;&nbsp;&nbsp;<select name='right_bracketsArray'><option value=''>&nbsp;&nbsp;</option><option value=')'>)</option></select>&nbsp;&nbsp;&nbsp;<input type='button' name='del_obj_attr_btn' class='graybtn1' value='-' /></td></tr>";
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
					var li_item = $("li[class='last clk']");
					var flag = true;
					//去重  add by hu.yi
					li_item.each(function(){
						var item_value = $(this).attr("attr");
						if(typeof(item_value) != "undefined" && item_value == $(item).val()){
							flag = false;
						}
					});
					if(flag){
						var li = '<li class="last clk" attr="'+$(item).val()+'" url="'+ctx+'/shop/admin/ruleObject!queryObjAttrs.do?ajax=yes&obj_id='+$(item).val()+'"><a><span></span>'+$(selectObjNameArray[idx]).val()+'</a></li>';
						$("#select_obj_uls").append(li);
					}
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
			
			$("input[name='obj_attr_id']").live("click",function(){
				$("#obj_dialog_tr").siblings("tr").remove();
				var ele_type = $(this).attr("ele_type");
				var stype_code = $(this).attr("stype_code");
				if(ele_type != ""){
					if(ele_type == "select"){
						var url = ctx + "/shop/admin/rule!listPublics.do?ajax=yes&stype_code="+stype_code;
						$.ajax({
							url:url,
							dataType:'json',
							method:'post',
							success:function(data){
								$("#obj_dialog_tr").siblings("tr").remove();
								var str = data.list;
								var list = eval("("+str+")");
								if(list.length > 0){
										var html = "";
										html += "<tr>";
										html += "<td>";
										html += "<select class='ipttxt' style='width: 100px' id='selObj'>";
											for(var i=0;i<list.length;i++){
												var selObj = list[i];
												var pkey = selObj.pkey;
												var pname = selObj.pname;
												html += "<option value='"+pkey+"'>"+pname+"</option>";
											}
										html += "</select>";
										html += "</td>";
										html += "</tr>";
										$("#obj_dialog_tr").after(html);
								}
							},error:function(a,b){
								alert("添加失败");
							}
						});
					}
				}
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
					if($("#right_panel #selObj").val() != null && $("#right_panel #selObj").val() != ""){
						curr.val($("#right_panel #selObj :checked").text());
						curr.closest("td").find("input[name=ruleCondValueArray]").val($("#right_panel #selObj").val());
					}else{
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
				}
				
				Eop.Dialog.close("ass_obj_div");
			});
		}); 
	});
});
</script>
