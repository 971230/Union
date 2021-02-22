<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src=js/cms_column.js ></script>
<h3>
	<div class="stat_graph">
	<h3>
		<div class="graph_tab" style="width:150%">
			<ul>
				<li id="show_click_2" class="selected"><span class="word">添加栏目</span><span class="bg"></span></li>
				<div class="clear"></div>
			</ul>
		</div>
	</h3>
	</div>
</h3>
<div class="mk_div mtt">
	<div class="mk_blue_content" style="border-width:medium 0px 0px">
		<div class="form_new mtt">
			<form method="post" action="column!addSave.do" name="subForm">
			 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_new_table">
			 	<tr style="display: none">
                	<th>父栏目名称：</th>
                	<td>
                	<input type="text" name="" id="" class="ipttxt" />
                	<a href="#" class="graybtn1" style="margin-left:10px;"><span>选择</span></a>
                	<input type="hidden" name="column.parent_id" id="parentId" value="-1"/>
                	</td>
              	</tr>
              	<tr>
                	<th>栏目名称：</th>
                	<td>
                	<input type="text" name="column.title" id="columnName" class="ipttxt" />
                	</td>
              	</tr>
              	<tr>
              		<th>栏目大类:</th>
    	      		<td><html:selectdict name='column.column_type' attr_code="DC_COLUMN_BIGTYPE"></html:selectdict></td>
              	</tr>
              	<tr>
                	<th>栏目展示类型：</th>
                	<td>
                	<select name="column.type" id="columnShowType" class="ipt_gray">
                		<option value="list">列表</option>
                		<option value="tab">Tab页</option>
                	</select>
                	</td>
              	</tr>
              	<tr style="display: none;">
                	<th>栏目类型：</th>
                	<td>
                	<select name="" id="" class="ipt_gray">
                		<option value="">OTT酒店行业菜单栏目</option>
                	</select>
                	</td>
              	</tr>
              	<tr>
                	<th>栏目配置模式：</th>
                	<td>
                	<label><input type="radio" name="radioGroup" value="1" id="radio1" onclick="checkRadio(1);" checked/>配置模式</label>
<!--                 	<label><input type="radio" name="radioGroup" value="2" id="radio2" onclick="checkRadio(2);"/>代码模式</label> -->
                	<input type="hidden" name="configMode" value=""/>
                	</td>
              	</tr>
			 </table>
			 <table width="100%" border="0" class="form_new_table" id="tab1" style="display:none">
			 	<c:forEach items="${elements }" var="ele" varStatus="status">
			 		<th style="width: 1%;"><label><input type="checkbox" name="checkboxGroup" value="" id="checkbox_${status.index }" /></label></th>
			 		<td>${ele.c_name }：<input type="hidden" name="ename" value="${ele.e_name }" cname="${ele.c_name }"></td>
			 		<td title="栏目字段标题"><input type="text" id="${ele.e_name }" name="" class="ipttxt" value="${ele.def_value }" 
			 			def_value="${ele.def_value }" onblur="checkValOnBlur(this);" onclick="clickText(this);"/></td>
			 		<td title="栏目字段标题缺省值"><input type="text" name="" id="defValue_${status.index }" class="ipttxt" 
			 			value="缺省值" def_value="缺省值" onblur="checkValOnBlur(this);" onclick="clickText(this);"/></td>
			 		<td title="栏目字段类型">
			 			<select name="" id="valueType_${status.index }" index="${status.index }" class="ipt_gray" onchange="changeValueType(this);">
			 				<option value="text">文本框 </option>
			 				<option value="select">下拉框 </option>
			 				<option value="dialog">弹出框 </option>
	                		<option value="textarea">富文本框</option>
	                	</select>
			 		</td>
			 		<td title="弹出框选择素材类型">
			 			<select style="display: none;" name="" id="relType_${status.index }" index="${status.index }" class="ipt_gray">
			 				<option value="" selected="selected">请选择</option>
			 				<option value="goods">商品 </option>
			 				<option value="adv_0">广告</option>
			 				<option value="adv_1">视频 </option>
	                		<option value="notice">公告</option>
	                	</select>
			 		</td>
			 		<td title="栏目字段类型值"><input type="text" name="" id="valueExtValue_${status.index }" class="ipttxt" value="" disabled/></td>
			 		<td title="是否可编辑">
			 			<select name="" id="valueDisable_${status.index }" class="ipt_gray">
			 				<option value="true">可编辑 </option>
			 				<option value="false">只读 </option>
	                	</select>
			 		</td>
			 		<td style="width: 15%" title="值保存对应的表字段"><input type="text" name="" id="dbName_${status.index }" class="ipttxt" style="width: 90%"/></td>
			 	</tr>
               	<input type="hidden" id="valueDisplay_${status.index }" value=""/>
<!--                	<input type="hidden" id="relType_${status.index }" value=""/> -->
               	<input type="hidden" id="relId_${status.index }" value=""/>
               	<input type="hidden" id="showValue_${status.index }" value=""/>
			 	</c:forEach>
			 </table>
			 <table width="100%" border="0" class="form_new_table" id="tab2" style="display:none">
			 	<tr>
			 		<td>
			 			<textarea rows="20" cols="95" style="margin-left: 25px" id="codes"></textarea>
			 		</td>
			 	</tr>
			 </table>
			 <div class="bb_btns">
			 	<input type="hidden" name="column.json_fields" id="json_fields"/>
				 <a href="javascript:void;" onclick="checkValOnSubmit();" class="submitbtn">提交</a>
			 </div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function() {
});

function checkValOnSubmit(){
	if($("input[name='column.title']").val().trim() == ""){
		alert("请输入栏目名称！");
		return false;
	}
	
	var b = true;
	if($("input[name=configMode]").val() == "1"){
		setJsonFields();
	}else if($("input[name=configMode]").val() == "2"){
		var codes = $("#codes").val();
		codes = codes.replace("'","\'");
		codes = codes.replace("\"","\"");
		$("#json_fields").val(codes);
	}
	//alert($("#json_fields").val());
	document.forms.subForm.submit();
}

String.prototype.trim = function (){
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
};
</script>
