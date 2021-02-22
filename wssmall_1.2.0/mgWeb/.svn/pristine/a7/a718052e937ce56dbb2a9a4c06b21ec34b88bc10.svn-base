<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="javascript:void(0);" method="post" id="choiceform">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>仓库名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="house_name" value="${house_name }" class="searchipt" /></td>	    	 
	    	     	
	    	      <td><input type="button" style="margin-right:10px;" class="comBtn" value="查询"   id="searchBot" ></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>  	
	</form>
 <div class="grid" id="selectChoice" >	
 <form id="gridform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
	    <grid:cell width="50px">选择</grid:cell>
	    <grid:cell sort="house_name">仓库名称</grid:cell>
		<grid:cell>联系人姓名</grid:cell>
		<grid:cell>联系人手机</grid:cell>
		<grid:cell>联系人电话</grid:cell>
		<grid:cell>仓库编号</grid:cell>
		<grid:cell>发货属性</grid:cell>
		<grid:cell>仓库归属</grid:cell>
	</grid:header>
  <grid:body item="obj">
	    <grid:cell><input type="radio"  id = "checkBox" name="checkBox" seat_id="${obj.seat_id }" value="${obj.house_name}" /></grid:cell>
    	<grid:cell>${obj.house_name}</grid:cell>
		<grid:cell>${obj.contact_name}</grid:cell>
		<grid:cell>${obj.telephone} </grid:cell>
		<grid:cell>${obj.phone_num} </grid:cell>
		<grid:cell>${obj.house_code} </grid:cell>
		<grid:cell>
			<c:if test="${obj.attr_inline_type eq 'T'}">
     				发货仓库
     		</c:if>
			<c:if test="${obj.attr_inline_type eq 'F'}">
     				备货仓库
     		</c:if>
		</grid:cell>
		<grid:cell>
			<c:if test="${obj.attribution eq 'T'}">
     				自建仓库
     		</c:if>
			<c:if test="${obj.attribution eq 'F'}">
     				第三方仓库
     		</c:if>
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

<script type="text/javascript" src="warehouse/js/warehouseSeatDalog.js"></script>

