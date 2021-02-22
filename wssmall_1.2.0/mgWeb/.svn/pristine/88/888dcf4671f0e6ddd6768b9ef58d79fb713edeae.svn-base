<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/fact_list.js" type="text/javascript"></script>
<style>
	.trees ul li:hover{cursor:pointer;}   
</style>
<form method="post" id="rule_show_form" action="${pageContext.request.contextPath}/shop/admin/rule!ruleConfigList.do" >
<div style="width:770px;">
    <div style="height:470px;" class="pop_cont">
    	<div class="pop_left">
        	<div class="trees">
        		<ul>
        			<c:forEach items="${ruleObjList}" var="ruleObj" varStatus="ruleObjStatus">
                    <li name="rule_obj" obj_id="${ruleObj.obj_id}" obj_name="${ruleObj.obj_name}" obj_code="${ruleObj.obj_code}">
                    	<a href="javascript:void(0);">
                    		<i class="treeic6"></i>${ruleObj.obj_name}
                    	</a>
                    	<c:choose>
                    		<c:when test="${ruleObjStatus.index == 0}">
                    			<ul>
                    		</c:when>
                    		<c:otherwise>
                    			<ul style="display: none;">
                    		</c:otherwise>
                    	</c:choose>
                    		<c:if test="${cond_type=='cal_cond'}">
	                    		<li attr_data="适用于任何场景，总是执行" attr_type="html" 
			        				attr_id="${ruleObj.obj_id}0" attr_name="适用所有条件" 
			        				attr_code="without_cond" name="rule_obj_attr">
			        					<input type="checkbox" name="checkbox" id="checkbox">适用所有条件
			        			</li>
			        			<li attr_data="任何情况都不执行" attr_type="never_run" 
			        				attr_id="${ruleObj.obj_id}1" attr_name="任何条件都不满足" 
			        				attr_code="never_run_cond" name="rule_obj_attr">
			        					<input type="checkbox" name="checkbox" id="checkbox">任何条件都不满足
			        			</li>
                    		</c:if>
                    		<c:forEach items="${ruleObj.objAttrList}" var="objAttr">
                        		<li name="rule_obj_attr" attr_code="${objAttr.attr_code}" attr_name="${objAttr.attr_name}" attr_id="${objAttr.attr_id}" attr_type="${objAttr.ele_type }" attr_data="${objAttr.stype_code}"><input type="checkbox" id="checkbox" name="checkbox">${objAttr.attr_name }</li>
                        	</c:forEach>
                        </ul>
                    </li>
                    <c:if test="${ruleObjStatus.last}">
                    	<script type="text/javascript">
                    		FactList.initSelected(${ruleObjAttrRelList});
                    	</script>
                    </c:if>
                    </c:forEach>
                </ul>
        	</div>
        	<a href="javascript:void(0);" id="refresh_fact"  class="blueBtns" name="fact_refresh" style="margin-top:10px;"><span>刷新Fact对象</span></a>
        	
        	
        </div>
      	<div class="pop_right">
        	<h3>属性值展示</h3>
        	<div class="ptit" id="attr_name">
        		<span class="name"></span>
        	</div>
        	<div id="attr_value">
            	
            </div>
            <div class="clear"></div>
            <div class="pop_btn">
            	<a class="blueBtns" id="confirm_condition" href="javascript:void(0);"><span>确认</span></a>
            </div>
      	</div>
    </div>
    <div>
    	<input id="rule_id" type="hidden" value="${rule_id}" />
    </div>
</div>
<!-- 列表属性，弹出框 -->
<div id="list_attr_dlg"></div>
<!-- 页面属性模板 -->
<div style="display: none;">
	<!-- 复选框模板 -->
	<div class="formCont" id="checkbox_tpl">
		<label> 
			<input type="checkbox" checked="checked" value="" name="CheckboxGroup1">省仓
		</label>
	</div>
	<!-- 单选框模板 -->
	<div class="formCont" id="radio_tpl">
		<label> 
			<input type="radio" checked="checked" value="" name="RadioGroup1"> 自动化模式
		</label>
	</div>
	<!-- 列表模板 -->
	<div id="grid_tpl" class="formCont" style="padding-top: 10px;">
		<div style="height: 60px;padding-bottom: 10px; position: absolute; right: 380px; top: 35px;">
			<a class="graybtn1" style="margin-left:5px;" name="open_list" href="javascript:void(0);">选择</a>
		</div>
		<table width="90%" cellspacing="0" cellpadding="0" border="0" class="grid_g">
			<tbody>
				<tr>
					<th name="open_list"><a href="javascript:void(0);">选择</a></th>
					<th>编码</th>
					<th>名称</th>
					<th>操作</th>
				</tr>
				<tr name="list_data_tpl" style="display: none;">
					<td>
						<input type="checkbox" disabled="disabled" checked="checked">
					</td>
					<td name="attr_value"></td>
					<td name="attr_name"></td>
					<td><a href="javascript:void(0);" name="del_attr" >删除</a></td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- 日期模板-->
	<div class="formCont" id="date_tpl">
		<input type="text" class="dateinput" value="" name="textfield">
	</div>
	<!-- 下拉框模板-->
	<div class="formCont" id="select_tpl">
		<select name="select" ></select>
	</div>
	<!-- 文本模板-->
	<div class="formCont" id="text_tpl">
		<input type="text" value="" name="textfield">
	</div>
	<!-- 原样输出 -->
	<div class="formCont" id="html_tpl">
		适用于任务条件，总是执行该规则
	</div>
	<div class="formCont" id="never_run_tpl">
		任何情况都不执行
	</div>
</div>
</form>
<script type="text/javascript">
$(function(){
	FactList.cond_type = '${cond_type}';
	$("#refresh_fact").die("click").live("click",function(){
			if(window.confirm("确定要刷新Fact对象？")){
			var url = ctx+"/shop/admin/rule!reScanObjInfo.do?ajax=yes";
			$.ajax({
				url:url,
				type:'get',
				dataType:'json',
				success:function(data){
					alert(data.msg);
				},error:function(a,b){
					alert("失败");
				}
			});
		}
	}); 
});
</script>