<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="js/PropsEdit.js"></script>
<script type="text/javascript">$(function(){Prop.init();})</script>
<style>
	#props_table tr th{text-align:left;}
</style>
<div class="input">
	<div style="text-align:center">
	<div class="list-div" id="listDiv"   style="width:100%;text-align:left">
	
		<div class="toolbar">
			<a href="javascript:;" id="propAddBtn"><input class="comBtn" type="button" name="" id="" value="新增属性" style="margin-right:10px;outline-style:none"/></a>
			<div style="clear:both"></div>
		</div>
	 <form method="post" action="type!savePropsEdit.do" name="propsEditForm" id="propsEditForm"  >
		<input type="hidden" name="is_edit" value="${is_edit }" />
		<input type="hidden" name="type_id" value="${type_id }" />
	   <table  id="props_table" cellpadding="3" cellspacing="1" style="width:100%"  >
	   <tbody>
	     <tr>
	       <th>属性名</th>
	       <th>类型</th>
	       <th>选择项(逗号分隔)</th>
	       <th>&nbsp;</th>
	     </tr>
	
	
	     
	<c:if test="${goodsType!=null}" >     
	          
	<c:forEach items="${goodsType.propList }" var="prop" varStatus="pStatus">
	
	     <tr >
	       <td width="28%"  ><input type="text" class="ipttxt"  name="propnames"  maxlength="60" value="${prop.name }"  dataType="string" required="true"/></td>
	       <td width="28%">&nbsp;
		   <select  class="ipttxt"  name="proptypes">
		   
		   <optgroup label="输入项">
		   	<option value="1" <c:if test="${prop.type==1 }">selected</c:if>>输入项 可搜索 </option>
			<option value="2" <c:if test="${prop.type==2 }">selected</c:if>>输入项 不可搜索 </option>
			</optgroup> 	
	
			<optgroup label="选择项">
		   	<option value="3" <c:if test="${prop.type==3 }">selected</c:if>>选择项 渐进式搜索 </option>
		   	<option value="4" <c:if test="${prop.type==4 }">selected</c:if>>选择项 普通搜索 </option>
			<option value="5" <c:if test="${prop.type==5 }">selected</c:if>>选择项 不可搜索 </option>
			</optgroup>
			
		   </select>
		   </td>
	       <td width="34%">&nbsp;<input type="text" class="ipttxt"  name="options" style="width:300px" value="${prop.options }" /></td>
	       <td width="10%"><a href="javascript:;"  ><img class="delete" src="images/transparent.gif" ></a></td>
	     </tr>
	</c:forEach>
	</c:if>
	<c:if test="${(goodsType.props==null || goodsType.props=='') && is_edit==1}">
	 <tr id="tr_prop">
	       <td width="28%"  ><input type="text" class="ipttxt"  name="propnames"  maxlength="60" value=""  dataType="string" required="true"/></td>
	       <td width="28%">&nbsp;
		   <select  class="ipttxt"  name="proptypes">
		   
		   <optgroup label="输入项">
		   	<option value="1" >输入项 可搜索 </option>
			<option value="2">输入项 不可搜索 </option>
			</optgroup> 	
	
			<optgroup label="选择项">
		   	<option value="3">选择项 渐进式搜索 </option>
		   	<option value="4">选择项 普通搜索 </option>
			<option value="5">选择项 不可搜索 </option>
			</optgroup>
			
		   </select>
		   </td>
	       <td width="34%">&nbsp;<input type="text" class="ipttxt"  name="options"  value="" /></td>
	       <td width="10%"><a href="javascript:;"   ><img class="delete" src="images/transparent.gif" ></a></td>
	     </tr>
	</c:if>
	 
	     </tbody>
	   </table>
	   
<div class="btn_box" align="center">
 <a href="javascript:void(0);" class="blue_b" style="margin-right:5px;" id="savePropsBtn">保存</a>
</div>	
	   
	 </form>
	</div>
	</div>
</div>