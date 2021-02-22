<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/rule_cond.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/attr_factory.js" type="text/javascript"></script>
<form id="rule_config_add_form" action="" method="post">
	<div class="operCont bor">
	  	<div class="operTit">
	      	<span style="margin-right:15px;" class="f_bold">已设置条件</span>
	    	<a class="graybtn" href="javascript:void(0);" cond_type="cal_cond" name="add_rule_cond" rule_id="${rule_id}"><i class="ic_add"></i>添加</a>
		    <span style="margin-left:15px;" class="cutover" name="model_switch">
		    	<a class="r_curr" href="javascript:void(0);" name="simple_model">精简模式</a>
		    	<a href="javascript:void(0);" name="oper_model">操作模式</a>
		    </span>
		</div>
		<input type="hidden" name="action" value="1" />
		<input type="hidden" name="ruleConfig.rule_id" value="${ruleConfig.rule_id }" />
		<input type="hidden" name="ruleConfig.pid" value="${ruleConfig.pid }" />
		<input type="hidden" class="ipttxt" name="ruleConfig.rule_name" value="${ruleConfig.rule_name }" />
		<input type="hidden" class="ipttxt" name="ruleConfig.rule_code" value="${ruleConfig.rule_code }" />
		<!-- 资源文件类型 -->
		<select name="ruleConfig.resource_type" class="searchipt" style="display: none;">
			<option value="DRL" ${ruleConfig.resource_type=='DRL'?'selected=selected':''} >规则流</option>
		</select>
		<input type="hidden" name="ruleConfig.eff_date" value='${ruleConfig.eff_date}' />
		<input type="hidden" name="ruleConfig.exp_date" value='${ruleConfig.exp_date}' /> 
		<input type="hidden" name="ruleConfig.rule_desc" value="${ruleConfig.rule_desc }"/>
		<!-- 默认界面配置 -->
		<input type="hidden" name="ruleConfig.impl_type" value="CF" />
		<!-- 界面中的所有group都是为了将对象分组 -->
		<input type='hidden' name='ruleObjIdArray' value='group' />
		<div id="rule_cond_div">
			<c:choose>
				<c:when test="${condSize > 0}">
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="operTable">
				      	<tbody name="cond_config">
				      	<c:set var="cond_count" value="0" ></c:set>
				      	<c:forEach items="${configListAuditList}" var="cfglist" varStatus="cfglistStatus">
				      		<c:forEach items="${cfglist.ruleCondAuditList }" var="cd" varStatus="ruleCondStatus">
				      			<c:set var="cond_count" value="${ruleCondStatus.index}"></c:set>
			      				<tr tr_flag="val" attr_id="${cd.attr_id}">
						         	<td rule_model="oper" style="display: none;">
						                 <select  name="left_bracketsArray">
						                   	<option value="">&nbsp;&nbsp;</option>
						                   	<option value="(" ${cd.left_brackets=='('?'selected=selected':''} >(</option>
						                 </select>
						         	</td>
						         	<td>
						        	  	<p name="attr_name" class="name">${cd.attr_cname}</p>
						         		<p name="attr_value">
						         			<select name="ruleOptTypeArray">
												<c:forEach items="${ruleOperList }" var="ot">
													<option value='${ot.pkey }' ${ot.pkey == cd.opt_type ? 'selected=selected' : ''} >${ot.pname }</option>
												</c:forEach>
											</select>
											<c:choose>
												<c:when test="${cd.ele_type != null && (cd.ele_type == 'input' || cd.ele_type == 'date')}">
													<input type="text" name="ruleCondCValueArray" readonly="readonly" value="${cd.z_value}">
												</c:when>
												<c:when test="${cd.ele_type != null && cd.ele_type == 'checkbox'}">
													<label>
								         				<script type="text/javascript">
								         					var cVal = '${cd.z_cvalue}';
								         					if(null != cVal && cVal != ''){
								         						var cValArray = cVal.split(',');
									         					for(var i = 0; i < cValArray.length; i++){
									         						$('div#rule_cond_div tbody[name="cond_config"] tr[attr_id="${cd.attr_id}"] label').append('<input disabled="disabled" type="checkbox" checked="checked" value="' 
											         						+ cValArray[i] + '" />' + cValArray[i]);
									         					}
								         					}
								         				</script>
								                    </label>
								                    <input type="hidden" name="ruleCondCValueArray" value="${cd.z_cvalue }">
												</c:when>
												<c:when test="${cd.ele_type != null && cd.ele_type == 'radio'}">
													<label>
														<input disabled="disabled" type="radio" checked="checked" value="${cd.z_value}">${cd.z_cvalue}
													</label>
													<input type="hidden" name="ruleCondCValueArray" value="${cd.z_cvalue }">
												</c:when>
												<c:when test="${cd.ele_type != null && cd.ele_type == 'select'}">
								             		<select disabled="disabled" name="ruleCondCValueArray">
								             			<option selected="selected" value="${cd.z_cvalue}">${cd.z_cvalue}</option>
								             		</select>
								             		<input type="hidden" name="ruleCondCValueArray" value="${cd.z_cvalue }">
												</c:when>
												<c:when test="${cd.ele_type != null && cd.ele_type == 'list'}">
													<table cellspacing="0" cellpadding="0" width="80%" border="0" class="grid_g">
														<tbody>
															<tr>
																<th name="open_list"><a href="javascript:void(0);">选择</a></th>
																<th>编码</th>
																<th>名称</th>
															</tr>
															<tr style="display: none;" name="list_data_tpl">
																<td>
																	<input type="checkbox" checked="checked" disabled="disabled">
																</td>
																<td name="attr_value"></td>
																<td name="attr_name"></td>
															</tr>
															<script type="text/javascript">
										         					var cVal = '${cd.z_cvalue}';
										         					var val = '${cd.z_value}';
										         					var attr_id = '${cd.attr_id}';
										         					if(null != cVal && cVal != ''){
										         						var cValArray = cVal.split(',');
										         						var valArray = val.split(',');
										         						var objArray = [];
											         					for(var i = 0; i < cValArray.length; i++){
											         						var objData = {'attr_name':cValArray[i], 'attr_value':valArray[i]};
											         						objArray.push(objData);
											         					}
											         					AttrFactory.renderListEle(objArray,$('div#rule_cond_div tbody[name="cond_config"]' + 
											         							' tr[attr_id="${cd.attr_id}"][tr_flag="val"]'));
										         					}
										         				</script>
														</tbody>
													</table>
								             		<input type='hidden' name='ruleCondCValueArray' value="${cd.z_cvalue}"/>
												</c:when>
												<c:otherwise>
													<input type="text" name="ruleCondCValueArray" readonly="readonly" value="${cd.z_value}">
												</c:otherwise>
											</c:choose>
											<input type='hidden' name='ruleCondValueArray' value="${cd.z_value }" />
											<input type='hidden' name='z_ruleObjAttrArray' value="" />
											<input type='hidden' name='z_ruleObjIdArray' value="" />
											<input type='hidden' name='ruleObjAttrArray' value="${cd.attr_id }" />
											<input type='hidden' name='ruleObjIdArray' value="${cd.obj_id}" />
						        		</p>
						         	</td>
						       	</tr>
					      		<tr tr_flag="rel" rule_model="oper" style="display: none;">
						       		<td colspan="2">
						       			<p>
						                    <select name="right_bracketsArray">
						                    	<option value="">&nbsp;&nbsp;</option>
						                       	<option value=")" ${cd.right_brackets==')'?'selected=selected':''} >)</option>
						                    </select>
						                    <!-- 最后一个条件，不做连接运算 -->
					                        <c:if test="${!ruleCondStatus.last}">
					                        	<c:set var="cur_conn" value="&&"></c:set>
					                        	<c:forEach items="${cfglist.ruleCondAuditList}" var="cdt" varStatus="ruleCondTStatus">
					                        		<c:if test="${ruleCondTStatus.index == ruleCondStatus.index + 1}">
					                        			<c:set var="cur_conn" value="${cdt.pre_log}"></c:set>
					                        		</c:if>
					                        	</c:forEach>
				                        		<select name="connect_codeArray">
						                          <option value="&&" ${cur_conn == '&&' ? 'selected = selected' : ''} >AND</option>
						                          <option value="||" ${cur_conn == '||' ? 'selected = selected' : ''} >OR</option>
						                    	</select>
					                    	</c:if>
						            	</p>
						       		</td>
						       	</tr>
				      		</c:forEach>
				      	</c:forEach>
				      	<c:if test="${ruleCondSize == 0}">
				      		<c:choose>
				      			<c:when test="${ruleObjAttr.attr_code == 'never_run_cond'}">
				      				<tr tr_flag="val" attr_id="${ruleObjAttr.attr_id}">
										<td>
							        	  	<p name="attr_name" class="name">任何条件都不满足</p>
							         		<p name="attr_value">
							         			任何情况都不执行
							        		</p>
							        		<input type="hidden" name="ruleObjId" value="${ruleObjAttr.obj_id}">
							        		<input type='hidden' name='never_run_flag' value="never_run" />
							         	</td>
							       	</tr>
				      			</c:when>
				      			<c:otherwise>
				      				<tr tr_flag="val" attr_id="${ruleObjAttr.attr_id}">
										<td>
							        	  	<p name="attr_name" class="name">适用所有条件</p>
							         		<p name="attr_value">
							         			适用于任务条件，总是执行该规则
							        		</p>
							        		<input type="hidden" name="ruleObjId" value="${ruleObjAttr.obj_id}">
							         	</td>
							       	</tr>
				      			</c:otherwise>
				      		</c:choose>
				      	</c:if>
				        </tbody>
					</table>
				</c:when>
				<c:otherwise>
					没有设置运算条件！！！！！
				</c:otherwise>
			</c:choose>
	  	</div>
	</div>
	<!-- 计算规则，目前联通不需要 -->
	<div style="display: none;">
		<textarea  style="width: 90%;height: 60px;margin-left: 40px;" name="action_script" ></textarea>
	</div>
	<div class="operCont">
		<div class="operTit">
	    	<span style="margin-right:15px;" class="f_bold">规则结果</span>
	        <a class="graybtn" href="javascript:void(0);" cond_type="final_cond" name="add_rule_cond" rule_id="${rule_id}"><i class="ic_add"></i>添加</a>
	    </div>
		<div id="rule_final_cond">
			<c:choose>
				<c:when test="${constSize > 0}">
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="operTable">
						<tbody name="cond_config">
							<c:forEach items="${configListAuditList}" var="cfglist" varStatus="cfglistStatus">
								<c:forEach items="${cfglist.constAuditList }" var="cs">
									<tr tr_flag="val" attr_id="${cs.attr_id}">
							         	<td>
							        	  	<p name="attr_name" class="name">${cs.attr_name}</p>
							         		<p name="attr_value">
							         			<c:choose>
							         				<c:when test="${cs.ele_type != null && (cs.ele_type == 'input' || cs.ele_type == 'date')}">
							         					<input type="text" name="ruleConstValueArray" readonly="readonly" value="${cs.const_value }">
							         				</c:when>
							         				<c:when test="${cs.ele_type != null && cs.ele_type == 'checkbox'}">
							         					<label>
									         				<script type="text/javascript">
									         					var cVal = '${cs.const_name}';
									         					if(null != cVal && cVal != ''){
									         						var cValArray = cVal.split(',');
										         					for(var i = 0; i < cValArray.length; i++){
										         						$('div#rule_final_cond tbody[name="cond_config"] tr[attr_id="${cs.attr_id}"] label').append('<input disabled="disabled" type="checkbox" checked="checked" value="' 
												         						+ cValArray[i] + '" />' + cValArray[i]);
										         					}
									         					}
									         				</script>
									                    </label>
									                    <input type="hidden" name="ruleConstValueArray" value="${cs.const_value }">
							         				</c:when>
							         				<c:when test="${cs.ele_type != null && cs.ele_type == 'radio'}">
							         					<label>
							         						<input disabled="disabled" type="radio" checked="checked" value="${cs.const_value}">${cs.const_name}
							         					</label>
							         					<input type="hidden" name="ruleConstValueArray" value="${cs.const_value }">
							         				</c:when>
							         				<c:when test="${cs.ele_type != null && cs.ele_type == 'select'}">
							         					<select disabled="disabled" name="ruleConstCValueArray">
									             			<option selected="selected" value="${cs.const_value}">${cs.const_name}</option>
									             		</select>
									             		<input type="hidden" name="ruleConstValueArray" value="${cs.const_value }">
							         				</c:when>
							         				<c:when test="${cs.ele_type != null && cs.ele_type == 'list'}">
							         					<table cellspacing="0" cellpadding="0" width="80%" border="0" class="grid_g">
															<tbody>
																<tr>
																	<th name="open_list"><a href="javascript:void(0);">选择</a></th>
																	<th>编码</th>
																	<th>名称</th>
																</tr>
																<tr style="display: none;" name="list_data_tpl">
																	<td>
																		<input type="checkbox" checked="checked" disabled="disabled">
																	</td>
																	<td name="attr_value"></td>
																	<td name="attr_name"></td>
																</tr>
																<script type="text/javascript">
										         					var cVal = '${cs.const_name}';
										         					var val = '${cs.const_value}';
										         					var attr_id = '${cs.attr_id}';
										         					if(null != cVal && cVal != ''){
										         						var cValArray = cVal.split(',');
										         						var valArray = val.split(',');
										         						var objArray = [];
											         					for(var i = 0; i < cValArray.length; i++){
											         						var objData = {'attr_name':cValArray[i], 'attr_value':valArray[i]};
											         						objArray.push(objData);
											         					}
											         					AttrFactory.renderListEle(objArray,$('div#rule_final_cond tbody[name="cond_config"]' + 
											         							' tr[attr_id="${cs.attr_id}"][tr_flag="val"]'));
										         					}
										         				</script>
															</tbody>
														</table>
									             		<input type="hidden" name="ruleConstValueArray" value="${cs.const_value}">
							         				</c:when>
							         				<c:otherwise>
							         					<input type="text" name="ruleConstValueArray" readonly="readonly" value="${cs.const_value }">
							         				</c:otherwise>
							         			</c:choose>
							         			<input type="hidden" name="ruleConstCValueArray" value="${cs.const_name }">
							             		<input type='hidden' name='ruleConstObjIdArray' value="${cs.obj_id }" />
												<input type='hidden' name='ruleConstAttrIdArray' value="${cs.attr_id }" />
												<input type='hidden' name='ruleConstObjCodeArray' value="${cs.obj_code }" />
												<input type='hidden' name='ruleConstAttrCodeArray' value="${cs.attr_code }" />
							        		</p>
							         	</td>
							       	</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					没有配置不参与计算的常量！！！！！！
				</c:otherwise>
			</c:choose>
		</div>
		<input type='hidden' name='ruleConstCValueArray' value='group'/>
	    <input type='hidden' name='ruleConstValueArray' value='group'/>
		<input type='hidden' name='ruleConstObjIdArray' value='group' />
		<input type='hidden' name='ruleConstAttrIdArray' value='group' />
		<input type='hidden' name='ruleConstObjCodeArray' value='group' />
		<input type='hidden' name='ruleConstAttrCodeArray' value='group' />
		<input type="hidden" name="rule_id" value="${rule_id}"/>
	</div>
</form>
<div style="text-align: center;padding-top: 20px;">
	<a href="javascript:void(0);" id="save_condition" rule_id="${rule_id}" class="blueBtns"><span>保存</span></a>
</div>
<!-- 条件设置弹出框 -->
<div id="showFactDlg"></div>
<!-- 条件值匹配类型 -->
<div id="select_div" style="display: none;">
	<select style='text-align: center; width: 80px;' name='ruleOptTypeArray'>
		<c:forEach items="${ruleOperList }" var="ot">
			<option value='${ot.pkey }'>${ot.pname }</option>
		</c:forEach>
	</select>
</div>
<!-- 条件元素模板 -->
<div style="display: none;">
	<!-- 条件配置table -->
	<table id="rule_cond_tpl" width="100%" cellspacing="0" cellpadding="0" border="0" class="operTable">
		<tbody name="cond_config">
		
		</tbody>
	</table>
	<!-- 复选框模板 -->
	<table>
		<tbody name="cond_checkbox_tpl" cond_type="rule_cond_div">
			<tr tr_flag="val">
	         	<td style="width:75px;display: none;" rule_model="oper">
	                 <select name="left_bracketsArray">
	                   	<option value="">&nbsp;&nbsp;</option>
	                   	<option  value="(">(</option>
	                 </select>
	             </td>
	         	<td>
	         		<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<select name="ruleOptTypeArray">
							<c:forEach items="${ruleOperList }" var="ot">
								<option value='${ot.pkey }'>${ot.pname }</option>
							</c:forEach>
						</select>
	         			<label></label>
	                    <input type="hidden" name="ruleCondCValueArray">
	             		<input type='hidden' name='ruleCondValueArray'/>
						<input type='hidden' name='z_ruleObjAttrArray' value="" />
						<input type='hidden' name='z_ruleObjIdArray' value="" />
						<input type='hidden' name='ruleObjAttrArray' />
						<input type='hidden' name='ruleObjIdArray' />
					</p>
	         	</td>
	       	</tr>
	       	<tr tr_flag="rel" rule_model="oper" style="display: none;">
	       		<td colspan="2">
	       			<p>
	                     <select name="right_bracketsArray">
	                       <option value="">&nbsp;&nbsp;</option>
	                       <option value=")">)</option>
	                     </select>
	                     <select  name="connect_codeArray">
	                       <option value="&&">AND</option>
	                       <option value="||">OR</option>
	                 	</select>
	                </p>
	       		</td>
	       	</tr>
    	</tbody>
    	<tbody name="cond_checkbox_tpl" cond_type="rule_final_cond">
			<tr tr_flag="val">
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<label></label>
	         			<input type="hidden" name="ruleConstValueArray" readonly="readonly">
         				<input type="hidden" name="ruleConstCValueArray">
	             		<input type='hidden' name='ruleConstObjIdArray' />
						<input type='hidden' name='ruleConstAttrIdArray' />
						<input type='hidden' name='ruleConstObjCodeArray' />
						<input type='hidden' name='ruleConstAttrCodeArray' />
	        		</p>
	         	</td>
	       	</tr>
    	</tbody>
	</table>
	<!-- 单选框模板 -->
	<table>
		<tbody name="cond_radio_tpl" cond_type="rule_cond_div">
			<tr tr_flag="val">
	         	<td rule_model="oper" style="display: none;">
	                 <select  name="left_bracketsArray">
	                   <option value="">&nbsp;&nbsp;</option>
	                   <option  value="(">(</option>
	                 </select>
	         	</td>
	         	<td>
	        	  	<p class="name" name="attr_name" ></p>
	         		<p name="attr_value">
	         			<select name="ruleOptTypeArray">
							<c:forEach items="${ruleOperList }" var="ot">
								<option value='${ot.pkey }'>${ot.pname }</option>
							</c:forEach>
						</select>
	           			<label></label>
	           			<input type="hidden" name="ruleCondCValueArray">
	             		<input type='hidden' name='ruleCondValueArray'/>
						<input type='hidden' name='z_ruleObjAttrArray' value="" />
						<input type='hidden' name='z_ruleObjIdArray' value="" />
						<input type='hidden' name='ruleObjAttrArray' />
						<input type='hidden' name='ruleObjIdArray' />
	         		</p>
	         	</td>
	       	</tr>
	       	<tr tr_flag="rel" rule_model="oper" style="display: none;">
	       		<td colspan="2">
	       			<p>
	                     <select name="right_bracketsArray">
	                       <option value="">&nbsp;&nbsp;</option>
	                       <option value=")">)</option>
	                     </select>
	                     <select  name="connect_codeArray">
	                       <option value="&&">AND</option>
	                       <option value="||">OR</option>
	                 	</select>
	            	</p>
	       		</td>
	       	</tr>
	    </tbody>
	    <tbody name="cond_radio_tpl" cond_type="rule_final_cond">
			<tr tr_flag="val">
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<label></label>
	         			<input type="hidden" name="ruleConstValueArray" readonly="readonly">
         				<input type="hidden" name="ruleConstCValueArray">
	             		<input type='hidden' name='ruleConstObjIdArray' />
						<input type='hidden' name='ruleConstAttrIdArray' />
						<input type='hidden' name='ruleConstObjCodeArray' />
						<input type='hidden' name='ruleConstAttrCodeArray' />
	        		</p>
	         	</td>
	       	</tr>
	    </tbody>
	</table>
	<!-- 文本框模板 -->
	<table>
		<tbody name="cond_text_tpl" cond_type="rule_cond_div">
			<tr tr_flag="val">
	         	<td rule_model="oper" style="display: none;">
	                 <select name="left_bracketsArray">
	                   	<option value="">&nbsp;&nbsp;</option>
	                   	<option value="(">(</option>
	                 </select>
	         	</td>
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<select name="ruleOptTypeArray">
							<c:forEach items="${ruleOperList }" var="ot">
								<option value='${ot.pkey }'>${ot.pname }</option>
							</c:forEach>
						</select>
	             		<input type="text" name="ruleCondCValueArray" readonly="readonly">
	             		<input type='hidden' name='ruleCondValueArray'/>
						<input type='hidden' name='z_ruleObjAttrArray' value="" />
						<input type='hidden' name='z_ruleObjIdArray' value="" />
						<input type='hidden' name='ruleObjAttrArray' />
						<input type='hidden' name='ruleObjIdArray' />
	        		</p>
	         	</td>
	       	</tr>
	       	<tr tr_flag="rel" rule_model="oper" style="display: none;">
	       		<td colspan="2">
	       			<p>
	                    <select name="right_bracketsArray">
	                    	<option value="">&nbsp;&nbsp;</option>
	                       	<option value=")">)</option>
	                    </select>
	                    <select  name="connect_codeArray">
	                       	<option value="&&">AND</option>
	                       	<option value="||">OR</option>
	                 	</select>
	            	</p>
	       		</td>
	       	</tr>
       	</tbody>
       	<tbody name="cond_text_tpl" cond_type="rule_final_cond">
			<tr tr_flag="val">
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	             		<input type="text" name="ruleConstValueArray" readonly="readonly">
	             		<input type="hidden" name="ruleConstCValueArray">
	             		<input type='hidden' name='ruleConstObjIdArray' />
						<input type='hidden' name='ruleConstAttrIdArray' />
						<input type='hidden' name='ruleConstObjCodeArray' />
						<input type='hidden' name='ruleConstAttrCodeArray' />
	        		</p>
	         	</td>
	       	</tr>
       	</tbody>
	</table>
	
	<!--下拉列表-->
	<table>
		<tbody name="cond_select_tpl" cond_type="rule_cond_div">
			<tr tr_flag="val">
	         	<td rule_model="oper" style="display: none;">
                 <select name="left_bracketsArray">
                   	<option value="">&nbsp;&nbsp;</option>
                   	<option value="(">(</option>
                 </select>
	         	</td>
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<select name="ruleOptTypeArray">
							<c:forEach items="${ruleOperList}" var="ot">
								<option value='${ot.pkey }'>${ot.pname }</option>
							</c:forEach>
						</select>
	             		<select name="ruleCondCValueArray" disabled="disabled"></select>
	             		<input type='hidden' name='ruleCondCValueArray'/>
	             		<input type='hidden' name='ruleCondValueArray'/>
						<input type='hidden' name='z_ruleObjAttrArray' value="" />
						<input type='hidden' name='z_ruleObjIdArray' value="" />
						<input type='hidden' name='ruleObjAttrArray' />
						<input type='hidden' name='ruleObjIdArray' />
	        		</p>
	         	</td>
	       	</tr>
	       	<tr tr_flag="rel" rule_model="oper" style="display: none;">
	       		<td colspan="2">
	       			<p>
	                    <select name="right_bracketsArray">
	                    	<option value="">&nbsp;&nbsp;</option>
	                       	<option value=")">)</option>
	                    </select>
	                    <select  name="connect_codeArray">
	                       	<option value="&&">AND</option>
	                       	<option value="||">OR</option>
	                 	</select>
	            	</p>
	       		</td>
	       	</tr>
       	</tbody>
       	<tbody name="cond_select_tpl" cond_type="rule_final_cond">
			<tr tr_flag="val">
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<select name="ruleConstCValueArray" disabled="disabled"></select>
	             		<input type="hidden" name="ruleConstCValueArray">
	             		<input type="hidden" name="ruleConstValueArray">
	             		<input type='hidden' name='ruleConstObjIdArray' />
						<input type='hidden' name='ruleConstAttrIdArray' />
						<input type='hidden' name='ruleConstObjCodeArray' />
						<input type='hidden' name='ruleConstAttrCodeArray' />
	        		</p>
	         	</td>
	       	</tr>
       	</tbody>
	</table>
	<!-- 列表模板 -->
	<table>
		<tbody name="cond_list_tpl" cond_type="rule_cond_div">
			<tr tr_flag="val">
	         	<td rule_model="oper" style="display: none;">
                 <select name="left_bracketsArray">
                   	<option value="">&nbsp;&nbsp;</option>
                   	<option value="(">(</option>
                 </select>
	         	</td>
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<select name="ruleOptTypeArray">
							<c:forEach items="${ruleOperList }" var="ot">
								<option value='${ot.pkey }'>${ot.pname }</option>
							</c:forEach>
						</select>
	             		<table cellspacing="0" cellpadding="0" width="80%" border="0" class="grid_g">
							<tbody>
								<tr>
									<th name="open_list"><a href="javascript:void(0);">选择</a></th>
									<th>编码</th>
									<th>名称</th>
								</tr>
								<tr style="display: none;" name="list_data_tpl">
									<td>
										<input type="checkbox" checked="checked" disabled="disabled">
									</td>
									<td name="attr_value"></td>
									<td name="attr_name"></td>
								</tr>
							</tbody>
						</table>
	             		<input type='hidden' name='ruleCondCValueArray'/>
	             		<input type='hidden' name='ruleCondValueArray'/>
						<input type='hidden' name='z_ruleObjAttrArray' value="" />
						<input type='hidden' name='z_ruleObjIdArray' value="" />
						<input type='hidden' name='ruleObjAttrArray' />
						<input type='hidden' name='ruleObjIdArray' />
	        		</p>
	         	</td>
	       	</tr>
	       	<tr tr_flag="rel" rule_model="oper" style="display: none;">
	       		<td colspan="2">
	       			<p>
	                    <select name="right_bracketsArray">
	                    	<option value="">&nbsp;&nbsp;</option>
	                       	<option value=")">)</option>
	                    </select>
	                    <select  name="connect_codeArray">
	                       	<option value="&&">AND</option>
	                       	<option value="||">OR</option>
	                 	</select>
	            	</p>
	       		</td>
	       	</tr>
       	</tbody>
       	<tbody name="cond_list_tpl" cond_type="rule_final_cond">
			<tr tr_flag="val">
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			<table cellspacing="0" cellpadding="0" width="80%" border="0" class="grid_g">
							<tbody>
								<tr>
									<th name="open_list"><a href="javascript:void(0);">选择</a></th>
									<th>编码</th>
									<th>名称</th>
								</tr>
								<tr style="display: none;" name="list_data_tpl">
									<td>
										<input type="checkbox" checked="checked" disabled="disabled">
									</td>
									<td name="attr_value"></td>
									<td name="attr_name"></td>
								</tr>
							</tbody>
						</table>
	             		<input type="hidden" name="ruleConstCValueArray">
	             		<input type="hidden" name="ruleConstValueArray">
	             		<input type='hidden' name='ruleConstObjIdArray' />
						<input type='hidden' name='ruleConstAttrIdArray' />
						<input type='hidden' name='ruleConstObjCodeArray' />
						<input type='hidden' name='ruleConstAttrCodeArray' />
	        		</p>
	         	</td>
	       	</tr>
       	</tbody>
	</table>
	
	<!-- 适用所有条件 -->
	<table>
		<tbody name="cond_html_tpl" cond_type="rule_cond_div">
			<tr tr_flag="val">
				<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			适用于任务条件，总是执行该规则
	        		</p>
	        		<input type='hidden' name='ruleObjId' />
	         	</td>
	       	</tr>
       	</tbody>
       	<tbody name="cond_html_tpl" cond_type="rule_final_cond">
			<tr tr_flag="val">
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			适用于任务条件，总是执行该规则
	        		</p>
	         	</td>
	       	</tr>
       	</tbody>
	</table>
	
	<!-- 适用所有条件 -->
	<table>
		<tbody name="cond_never_tpl" cond_type="rule_cond_div">
			<tr tr_flag="val">
				<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			任何情况都不执行
	        		</p>
	        		<input type='hidden' name='ruleObjId' />
	        		<input type='hidden' name='never_run_flag'/>
	         	</td>
	       	</tr>
       	</tbody>
       	<tbody name="cond_never_tpl" cond_type="rule_final_cond">
			<tr tr_flag="val">
	         	<td>
	        	  	<p class="name" name="attr_name"></p>
	         		<p name="attr_value">
	         			任何情况都不执行
	        		</p>
	         	</td>
	       	</tr>
       	</tbody>
	</table>
	
	<!-- 与或关系模板 -->
	<select id="and_or_tpl"  name="connect_codeArray">
       	<option value="&&">AND</option>
       	<option value="||">OR</option>
   	</select>
</div>
<script type="text/javascript">
	$(function(){
		RuleCond.init();
		RuleCond.renderCondition();
	});
</script>