<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<div>
	<form action="" method="post" id="qryFrm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>业务名称：</th>
	    	      <td>
	    	      	  <input type="hidden" class="ipttxt" name="biz.biz_id" value="${biz.biz_id}"/>
	    	          <input type="text" class="ipttxt" name="biz.biz_name" value="${biz.biz_name}" id="biz_name"/> 
	    	      </td>	
	    	      <th>业务编码：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="biz.flow_id" value="${biz.flow_id }" id="flow_id"/> 
	    	      </td>		    	      
	    	      <th>状态：</th>
	    	      <td>
						<select id="status" class="ipttxt" name="biz.status" style="width: 155px">
						<c:choose>
							<c:when test="${biz.status eq '00A'}">
								<option value="00A" checked>有效</option>
								<option value="00X">无效</option>
							</c:when>
							<c:when test="${biz.status eq '00X'}">
								<option value="00X" checked>无效</option>
								<option value="00A">有效</option>
							</c:when>
							
						</c:choose>
						</select>
	    	      </td>

	    	      <th></th>
	    	      <td>
	    	          <input type="button" style="margin-right:10px;" class="comBtn" value="确&nbsp;定"  id="button" name="button" onclick="buttonReturn();">
	    	          <a app_menu_id="251126031" href="/core/admin/ecc/bizCheckCfgAdmin!bizCfgList.do"><input type="button" style="margin-right:10px;" class="comBtn" value="返&nbsp;回"  id="back_list" name="button"></a>
	    	      </td>       
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>		
	  	 
	  	<table class="form-table" style="width: 100%; float: left" id='base_table'>
	  	<c:forEach var="bizFactorCfg" items="${bizFactorCfgList}" varStatus="status">
	  		<input type="hidden" name="bizRequirementsList[${status.index}].factor_cfg_id" value="${bizFactorCfg.factor_id}" />
			<input type="hidden" name="bizRequirementsList[${status.index}].attr_code" value="${bizFactorCfg.attr_code}" />
			<input type="hidden" name="bizRequirementsList[${status.index}].z_cvalue" value="${bizFactorCfg.attr_name}" />
			<tr id="${bizFactorCfg.attr_code}">
				<th>
					<c:choose>
						<c:when test="${not empty  bizFactorCfg.code_check}">
							<label><input type="checkbox" checked value="1" name="bizRequirementsList[${status.index}].check" onclick="if(this.checked==true){this.value='1'}else{this.value=''}"> ${bizFactorCfg.attr_name}</label>
						</c:when>
						<c:otherwise>
							<label><input type="checkbox" value="" name="bizRequirementsList[${status.index}].check" onclick="if(this.checked==true){this.value='1'}else{this.value=''}"> ${bizFactorCfg.attr_name}</label>
						</c:otherwise>
					</c:choose>
				</th>
				<td>
					<select name="bizRequirementsList[${status.index}].opt_type">			
						<c:choose>
							<c:when test="${bizFactorCfg.opt_type eq '!＝'}">
								<option value="!=" selected="selected">不等于</option>
								<option value="<">小于</option>
								<option value="<=">小于等于</option>
								<option value="==">等于</option>
								<option value=">">大于</option>
								<option value=">=">大于等于</option>
								<option value="contains">包含</option>
								<option value="matches" >匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq '&lt;'}">
								<option value="!=">不等于</option>
								<option value="<" selected="selected">小于</option>
								<option value="<=">小于等于</option>
								<option value="==">等于</option>
								<option value=">">大于</option>
								<option value=">=">大于等于</option>
								<option value="contains">包含</option>
								<option value="matches" >匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq '&lt;＝'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" selected="selected">小于等于</option>
								<option value="==">等于</option>
								<option value=">">大于</option>
								<option value=">=">大于等于</option>
								<option value="contains">包含</option>
								<option value="matches" >匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq '＝＝'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" selected="selected">等于</option>
								<option value=">">大于</option>
								<option value=">=">大于等于</option>
								<option value="contains">包含</option>
								<option value="matches" >匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq '>'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" selected="selected">大于</option>
								<option value=">=">大于等于</option>
								<option value="contains">包含</option>
								<option value="matches" >匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq '>＝'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" selected="selected">大于等于</option>
								<option value="contains">包含</option>
								<option value="matches" >匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq 'contains'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" >大于等于</option>
								<option value="contains" selected="selected">包含</option>
								<option value="matches" >匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq 'matches'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" >大于等于</option>
								<option value="contains" >包含</option>
								<option value="matches" selected="selected">匹配</option>
								<option value="in">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq 'in'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" >大于等于</option>
								<option value="contains" >包含</option>
								<option value="matches" >匹配</option>
								<option value="in" selected="selected">存在</option>
								<option value="not contains">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq 'not contains'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" >大于等于</option>
								<option value="contains" >包含</option>
								<option value="matches" >匹配</option>
								<option value="in" >存在</option>
								<option value="not contains" selected="selected">不包含</option>
								<option value="not matches">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq 'not matches'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" >大于等于</option>
								<option value="contains" >包含</option>
								<option value="matches" >匹配</option>
								<option value="in" >存在</option>
								<option value="not contains" >不包含</option>
								<option value="not matches" selected="selected">不匹配</option>
								<option value="not in">不存在</option>
							</c:when>
							<c:when test="${bizFactorCfg.opt_type eq 'not in'}">
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" >大于等于</option>
								<option value="contains" >包含</option>
								<option value="matches" >匹配</option>
								<option value="in" >存在</option>
								<option value="not contains" >不包含</option>
								<option value="not matches" >不匹配</option>
								<option value="not in" selected="selected">不存在</option>
							</c:when>
							<c:otherwise>
								<option value="!=">不等于</option>
								<option value="<">小于</option>
								<option value="<=" >小于等于</option>
								<option value="==" >等于</option>
								<option value=">" >大于</option>
								<option value=">=" >大于等于</option>
								<option value="contains" >包含</option>
								<option value="matches" selected="selected">匹配</option>
								<option value="in" >存在</option>
								<option value="not_contains" >不包含</option>
								<option value="not_matches" >不匹配</option>
								<option value="not_in" >不存在</option>
							</c:otherwise>
						</c:choose>												
					</select>
				</td>
				<td>
			   		<c:choose>
				           <c:when test="${bizFactorCfg.attr_code eq 'wm_isreservation_from' }">
				           		<c:forEach var="obj" items="${DC_IS_OR_NO}">
							  		<c:choose>
						  			<c:when test="${not empty obj.check_type }">
						  				<label><input type="checkbox" checked value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
						  			</c:when>
						  			<c:when test="${empty obj.check_type }">
						  				<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
						  			</c:when>
						  		</c:choose>
							  	</c:forEach>
				  			</c:when>
				           <c:when test="${bizFactorCfg.attr_code eq 'need_open_act' }">
				             <c:forEach var="obj" items="${DC_IS_OR_NO_NEED}">
				  				<c:choose>
						  			<c:when test="${not empty obj.check_type }">
						  				<label><input type="checkbox" checked value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
						  			</c:when>
						  			<c:when test="${empty obj.check_type }">
						  				<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
						  			</c:when>
						  		</c:choose>
						  	  </c:forEach>
				  			</c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_NEW_USER_TYPE'}">
							  	<c:forEach var="obj" items="${DC_NEW_USER_TYPE}">
							  		<c:choose>
							  			<c:when test="${not empty obj.check_type }">
							  				<label><input type="checkbox" checked value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  			<c:when test="${empty obj.check_type }">
							  				<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  		</c:choose>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_MODE_GOODS_TYPE'}">
							  	<c:forEach var="obj" items="${DC_MODE_GOODS_TYPE}">
							  		<c:choose>
							  			<c:when test="${not empty obj.check_type }">
							  				<label><input type="checkbox" checked value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  			<c:when test="${empty obj.check_type }">
							  				<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  		</c:choose>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_BUSINESS_TYPE'}">
							  	<c:forEach var="obj" items="${DC_BUSINESS_TYPE}">
							  		<c:choose>
							  			<c:when test="${not empty obj.check_type }">
							  				<label><input type="checkbox" checked value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  			<c:when test="${empty obj.check_type }">
							  				<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  		</c:choose>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_MODE_NET_TYPE'}">
							  	<c:forEach var="obj" items="${DC_MODE_NET_TYPE}">
							  		<c:choose>
							  			<c:when test="${not empty obj.check_type }">
							  				<label><input type="checkbox" checked value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  			<c:when test="${empty obj.check_type }">
							  				<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  			</c:when>
							  		</c:choose>
							  	</c:forEach>
				           </c:when>
			        </c:choose>
					
				</td>	
				<td>
					<select name="bizRequirementsList[${status.index}].pre_log">
						<c:choose>
							<c:when test="${bizFactorCfg.pre_log eq '||'}">
								<option value="&amp;&amp;" >AND</option>
								<option value="||" selected="selected">OR</option>
							</c:when>
							<c:otherwise>
								<option value="&amp;&amp;" selected="selected">AND</option>
								<option value="||">OR</option>
								
							</c:otherwise>
						</c:choose>
						
					</select>
				</td>			
			</tr>
		</c:forEach>
		</table>
		<div style="height:50px;"></div>
		<div class="tab-bar" >
				<ul class="tab" id="ul_tab">
					<li tabid="0" class="active" id="li_before" val="0">
						环节前编辑
					</li>
					<li tabid="3" id="li_after" val="1">
						环节后编辑
					</li>
				</ul>
			</div>
		<div id="tab_after" style="display:none;">
			<c:forTokens items="B,C,D,F,H,J,L,V,X,Y,R" delims="," var="trace" varStatus="status">
			<input type="hidden" name="bizFunList[${status.index}].tache_code" value="${trace}" />
			<input type="hidden" name="bizFunList[${status.index}].exe_time" value="after" />
		<div style="width:100%;" id="productbody2" class="grid">
		<table cellspacing="0" cellpadding="0" border="0" id="${trace}">
			<tr>
				<th style="width: 120px;">订单环节：</th>
				<td style="width: auto;">
				<label>
				<c:choose>
					<c:when test="${not empty afterExeMethodMap[trace]}">
						<input type="checkbox" checked value="1"  name="bizFunList[${status.index}].check" onclick="if(this.checked==true){this.value='1'}else{this.value=''}">
					</c:when>
					<c:otherwise>
						<input type="checkbox" value="" name="bizFunList[${status.index}].check" onclick="if(this.checked==true){this.value='1'}else{this.value=''}">
					</c:otherwise>
				</c:choose>
				<c:choose>
			           <c:when test="${trace eq 'V'}">订单标准化（${trace}）</c:when>
			           <c:when test="${trace eq 'B'}">客户回访（${trace}）</c:when>
			           <c:when test="${trace eq 'C'}">预拣货（${trace}）</c:when>
			           <c:when test="${trace eq 'D'}">开户（${trace}）</c:when>
			           <c:when test="${trace eq 'X'}">写卡（${trace}）</c:when>
			           <c:when test="${trace eq 'Y'}">业务办理（${trace}）</c:when>
			           <c:when test="${trace eq 'F'}">质检稽核（${trace}）</c:when>
			           <c:when test="${trace eq 'H'}">发货（${trace}）</c:when>
			           <c:when test="${trace eq 'J'}">回单（${trace}）</c:when>
			           <c:when test="${trace eq 'L'}">归档（${trace}）</c:when>
			           <c:when test="${trace eq 'R'}">订单标准化执行方案（${trace}）</c:when>
			    </c:choose>
				</label>
				</td>
			</tr>	
			<tr>
				<th style="width: 120px;">执行方式：</th>
				<td style="width: auto;">
					<select class="ipttxt" name="bizFunList[${status.index}].exe_method" style="width: 155px">
						<c:choose>
							<c:when test="${not empty afterExeMethodMap[trace]}">
								<c:choose>
								<c:when test="${afterExeMethodMap[trace] eq 1}">
									<option value="1" selected="selected">串行</option>
									<option value="2" >并行</option>
								</c:when>
								<c:otherwise>
									<option value="2" selected="selected">并行</option>
									<option value="1">串行</option>
								</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<option value="2">并行</option>
								<option value="1">串行</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>	
			<tr>
				<th style="width: 120px;">环节校验：</th>
				<td style="width: auto;">
					<c:forEach var="afterFunMapList" items="${afterFunMap}">
						<c:choose>
							<c:when test="${afterFunMapList.key eq trace}">
								<c:forEach var="fun" items="${afterFunMapList.value}">
									<c:choose>
										<c:when test="${fun.checked eq 'checked'}">
											<label><input type="checkbox" name="bizFunList[${status.index}].fun_id" value="${fun.fun_id}" onclick="if(this.checked==true){this.value='${fun.fun_id}'}else{this.value=''}" checked>${fun.fun_name}</label>
										</c:when>
										<c:otherwise>
											<label><input type="checkbox" name="bizFunList[${status.index}].fun_id" value="${fun.fun_id}" onclick="if(this.checked==true){this.value='${fun.fun_id}'}else{this.value=''}">${fun.fun_name}</label>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:forEach>
				</td>
			</tr>			
		</table>
		</div>					
		</c:forTokens>
		</div>				 

		<div id="tab_before" style="display:none;">
			<c:forTokens items="B,C,D,F,H,J,L,V,X,Y,R" delims="," var="trace" varStatus="status">
			<input type="hidden" name="beforeBizFunList[${status.index}].tache_code" value="${trace}" />
			<input type="hidden" name="beforeBizFunList[${status.index}].exe_time" value="before" />
		<div style="width:100%;" id="productbody2" class="grid">
		<table cellspacing="0" cellpadding="0" border="0" id="${trace}">
			<tr>
				<th style="width: 120px;">订单环节：</th>
				<td style="width: auto;">
				<label>
				<c:choose>
					<c:when test="${not empty beforeExeMethodMap[trace]}">
						<input type="checkbox" checked value="1"  name="beforeBizFunList[${status.index}].check" onclick="if(this.checked==true){this.value='1'}else{this.value=''}">
					</c:when>
					<c:otherwise>
						<input type="checkbox" value="" name="beforeBizFunList[${status.index}].check" onclick="if(this.checked==true){this.value='1'}else{this.value=''}">
					</c:otherwise>
				</c:choose>
				<c:choose>
			           <c:when test="${trace eq 'V'}">订单标准化（${trace}）</c:when>
			           <c:when test="${trace eq 'B'}">客户回访（${trace}）</c:when>
			           <c:when test="${trace eq 'C'}">预拣货（${trace}）</c:when>
			           <c:when test="${trace eq 'D'}">开户（${trace}）</c:when>
			           <c:when test="${trace eq 'X'}">写卡（${trace}）</c:when>
			           <c:when test="${trace eq 'Y'}">业务办理（${trace}）</c:when>
			           <c:when test="${trace eq 'F'}">质检稽核（${trace}）</c:when>
			           <c:when test="${trace eq 'H'}">发货（${trace}）</c:when>
			           <c:when test="${trace eq 'J'}">回单（${trace}）</c:when>
			           <c:when test="${trace eq 'L'}">归档（${trace}）</c:when>
			           <c:when test="${trace eq 'R'}">订单标准化执行方案（${trace}）</c:when>
			    </c:choose>
				</label>
				</td>
			</tr>	
			<tr>
				<th style="width: 120px;">执行方式：</th>
				<td style="width: auto;">
					<select class="ipttxt" name="beforeBizFunList[${status.index}].exe_method" style="width: 155px">
						<c:choose>
							<c:when test="${not empty beforeExeMethodMap[trace]}">
								<c:choose>
								<c:when test="${beforeExeMethodMap[trace] eq 1}">
									<option value="1" selected="selected">串行</option>
									<option value="2" >并行</option>
								</c:when>
								<c:otherwise>
									<option value="2" selected="selected">并行</option>
									<option value="1">串行</option>
								</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<option value="2">并行</option>
								<option value="1">串行</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>	
			<tr>
				<th style="width: 120px;">环节校验：</th>
				<td style="width: auto;">
					<c:forEach var="beforeFunMapList" items="${beforeFunMap}">
						<c:choose>
							<c:when test="${beforeFunMapList.key eq trace}">
								<c:forEach var="fun" items="${beforeFunMapList.value}">
									<c:choose>
										<c:when test="${fun.checked eq 'checked'}">
											<label><input type="checkbox" name="beforeBizFunList[${status.index}].fun_id" value="${fun.fun_id}" onclick="if(this.checked==true){this.value='${fun.fun_id}'}else{this.value=''}" checked>${fun.fun_name}</label>
										</c:when>
										<c:otherwise>
											<label><input type="checkbox" name="beforeBizFunList[${status.index}].fun_id" value="${fun.fun_id}" onclick="if(this.checked==true){this.value='${fun.fun_id}'}else{this.value=''}">${fun.fun_name}</label>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:forEach>
				</td>
			</tr>			
		</table>
		</div>					
		</c:forTokens>
		</div>
		
       <div class="grid_n_div">
         		
       </div>
        
	  	 
	</form>

   
    
<div style="clear:both;padding-top:5px;"></div>
</div>
<script type="text/javascript">
$(function(){
	$("#ul_tab").find("li").unbind("click").bind("click",function(){
		var tab_value=$(this).attr("val");
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		if(tab_value=='0'){
			$("#tab_before").show();
			$("#tab_after").hide();
		}else{
			$("#tab_before").hide();
			$("#tab_after").show();
		}
	});
	$("#tab_before").show();
	
	
});

function buttonReturn(){
	var biz_name=$("#biz_name").val();
	var flow_id=$("#flow_id").val();
	if(biz_name==""){
		alert("业务名称不能为空！");
		return;
	}
	if(flow_id==""){
		alert("业务编码不能为空！");
		return;
	}
	 var reg = new RegExp("^[0-9]*$");  
	 if(!(reg.test(flow_id))){
		alert("业务编码中请输入数字！");
		return;
	 }
	var checkdata="-1";
	for(var i=0;i<6;i++){
		var check=$("input[name='bizRequirementsList["+i+"].check']").val();
		var code_value=$("input[name='bizRequirementsList["+i+"].check']").parent().text();
		if(check!=""){
			checkdata="0";
			if($("input[name='bizRequirementsList["+i+"].z_value']").is(':checked')) {
			}else{
				alert("“"+code_value+"”子项没有勾选");
			    return;
			}
		}
	}
	if(checkdata!="0"){
		alert("因子必须勾选一条数据");
		return;
	}
	
	//环节前校验
	for(var i=0;i<11;i++){
		if($("input[name='beforeBizFunList["+i+"].fun_id']").is(':checked')) {
			var check=$("input[name='beforeBizFunList["+i+"].check']").val();
			var tache_code=$("input[name='beforeBizFunList["+i+"].tache_code']").val();
			if(check==""){
				var code_value=tranferValue(tache_code);
				alert("环节前编辑的“"+code_value+"”没有勾选");
			    return;
			}
		}
	}
	
	//环节后校验
	for(var i=0;i<11;i++){
		if($("input[name='bizFunList["+i+"].fun_id']").is(':checked')) {
			var check=$("input[name='bizFunList["+i+"].check']").val();
			var tache_code=$("input[name='bizFunList["+i+"].tache_code']").val();
			if(check==""){
				var code_value=tranferValue(tache_code);
				alert("环节后编辑的“"+code_value+"”没有勾选");
			    return;
			}
		}
	}
	$("#qryFrm").attr("action","/core/admin/ecc/bizCheckCfgAdmin!saveEditBiz.do");
	$("#qryFrm").submit();
}
function tranferValue(code){
	var code_value="";
	switch (code){ 
	case "V" : 
		code_value="订单标准化" ;
	    break; 
	case "B" : 
		code_value="客户回访" ;
	    break; 
	case "C" : 
		code_value="预拣货" ;
	    break;
	case "D" : 
		code_value="开户" ;
	    break; 
	case "X" : 
		code_value="写卡" ;
	    break; 
	case "Y" : 
		code_value="业务办理" ;
	    break; 
	case "F" : 
		code_value="质检稽核" ;
	    break;
	case "H" : 
		code_value="发货" ;
	    break; 
	case "J" : 
		code_value="回单" ;
	    break;
	case "L" : 
		code_value="归档" ;
	    break; 
	
	default : 
		code_value="订单标准化执行方案" ;
    	break; 
	} 
	return code_value;
}

</script>