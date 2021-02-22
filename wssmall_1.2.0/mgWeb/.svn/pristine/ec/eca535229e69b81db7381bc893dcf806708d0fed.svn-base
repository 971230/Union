<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="javascript:void(0);" method="post" id="choiceform">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>供应商名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="user_name" value="${user_name }" class="searchipt" /></td>	    	 
	    	     <th>电话号码:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="phone" value="${phone }" class="searchipt" /></td>
	    	      <td><input type="button" style="margin-right:10px;" class="comBtn" value="查询"   id="searchBot" ></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>  	
	</form>
 <div class="grid" id="selectChoice" >	
 <form id="serchform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
	    <grid:cell width="50px">选择</grid:cell>
		<grid:cell sort="company_name">公司名称</grid:cell>
		<grid:cell>电子邮件</grid:cell>
		<grid:cell>QQ号</grid:cell>
		<grid:cell>注册人姓名</grid:cell>
		<grid:cell>性别</grid:cell>
		<grid:cell>身份证号码</grid:cell>
		<grid:cell>联系电话</grid:cell>
		<grid:cell>状态</grid:cell>
		<grid:cell>创建时间</grid:cell>
	</grid:header>
  <grid:body item="obj">
	    <grid:cell><input type="radio"  id = "checkBox" name="checkBox" user_name="${obj.user_name }" value="${obj.supplier_id}" /></grid:cell>
    	<grid:cell>
			<a title="查看"href="supplier!detail.do?flag=view&supplier_state=${obj.supplier_state }&supplier_id=${obj.supplier_id }&supplier_type=${obj.supplier_type }">${obj.company_name}</a>
		</grid:cell>
		<grid:cell>${obj.email} </grid:cell>
		<grid:cell>${obj.qq} </grid:cell>
		<grid:cell>${obj.user_name} </grid:cell>
		<grid:cell>
			<c:if test="${obj.sex eq '1'}">
     			男
     		</c:if>
			<c:if test="${obj.sex eq '2'}">
     			女
     		</c:if>
		</grid:cell>
		<grid:cell>${obj.id_card}</grid:cell>
		<grid:cell>${obj.phone}</grid:cell>
		<grid:cell>
			<c:if test="${obj.supplier_state eq '0' }">待审核</c:if>
			<c:if test="${obj.supplier_state eq '1'}">正常</c:if>
			<c:if test="${obj.supplier_state eq '2' }">注销</c:if>
			<c:if test="${obj.supplier_state eq '-1' }">审核不通过</c:if>
		</grid:cell>
		<grid:cell>
			<html:dateformat pattern="yyyy-MM-dd" d_time="${obj.register_time}"></html:dateformat>
		</grid:cell>
  </grid:body>   
</grid:grid>  
</form>
</div>	   
<div class="submitlist" align="center">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectOkBot"/>
	 </td>
	 </tr>
	 </table>
</div>

<script type="text/javascript" src="js/searchSupplier.js"></script>

