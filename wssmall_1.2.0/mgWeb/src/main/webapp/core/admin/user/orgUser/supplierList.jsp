<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="supplierserchform" >
 <div class="searchformDiv" style ='width:76%' >
 <table>
	<tr>
		<th>供应商名称：</th>
		<td><input type="text" class="ipttxt"  name="supplier_name"  id ="supplierReq" value="${supplier_name}"/></td>
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="searchBtn" id="supplierSearchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
    </tr>
  </table>
</div>
</form>

 <div class="grid"  >
     <form method="POST"  id="roleform" >
       <grid:grid from="webpage"  formId="supplierserchform" ajax="yes">
                    <grid:header>
                        <grid:cell>选择</grid:cell>
					  	<grid:cell>供应商编码</grid:cell> 
					  	<grid:cell>供应商名称</grid:cell>
					</grid:header>
				    <grid:body item="obj">
					     <grid:cell><input type="radio" name="supplierId"  value="${obj.supplier_id},${obj.user_name },${obj.account_number},${obj.phone}"></grid:cell>
					     <grid:cell>${obj.supplier_id}</grid:cell>
					     <grid:cell>${obj.user_name}</grid:cell>
					</grid:body>  
				</grid:grid>
				 <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="supplierSelInsureBtn">
		         </div>
</form>
</div>