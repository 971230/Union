<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
 <div>
	<form action="userAdmin!list.do" method="post" id="orgForm">
		<div class="searchformDiv">
   	  		<table width="100%" cellspacing="0" cellpadding="0" border="0">
   	    		<tbody>
   	    			<tr>
		    	      <th>组织ID:</th>
		    	      <td><input type="text" class="ipttxt" class="searchipt"  style="width: 90px" id="party_id" name="party_id" value="${party_id}" /></td>
		    	      <th>组织名称:</th>
		    	      <td><input type="text" class="ipttxt" class="searchipt"  style="width: 90px" id="org_name" name="org_name" value="${org_name}"  /></td>
		    	      <th>地市:</th>
					   <td>
							<select id="lan_city" name= "lan_city" class="ipttxt" style="width:150px">
								<option value=''>--全部--</option>
							 	<c:forEach var="lan" items="${lanList}">
							 		<option value="${lan.lan_code}">${lan.lan_name }</option>
							 	</c:forEach>
							</select>	
							<input type="hidden" name="lan_id" id="lan_id" value="${lan_id}" />
					   </td>

					<td>
					     <input style="width:40px;"  class="comBtn schConBtn"
							value="搜索" />
							<input  style="width:40px;" id="selOrgBtn" class="comBtn insureBtn"
							value="确定" />
					</td>
					
		  	      </tr>
    			</tbody>
 	   		</table>
  		</div>
		</form>
		
		<form id="gridform" class="grid">
		<grid:grid  from="webpage" ajax='yes' formId="orgForm">
			<grid:header>
				<grid:cell></grid:cell> 
			  	<grid:cell>组织编码</grid:cell> 
				<grid:cell>组织名称</grid:cell> 
				<grid:cell>上级组织名称</grid:cell> 
				<grid:cell>组织类型</grid:cell> 
				<grid:cell>组织简介</grid:cell> 
				<grid:cell>统一渠道编码</grid:cell> 
				<grid:cell>地市</grid:cell> 
			</grid:header>
		    <grid:body item="obj">
		   		 <grid:cell>
		   		 	<input type="radio" name="selectOrg" ele="${obj.party_id}_${obj.org_name}" />
		   		 </grid:cell> 
			     <grid:cell>${obj.org_code} </grid:cell> 
			     <grid:cell>${obj.org_name} </grid:cell> 
			     <grid:cell>${obj.parent_org_name} </grid:cell> 
			     <grid:cell>${obj.org_type_name} </grid:cell>
			     <grid:cell>${obj.org_content}</grid:cell> 
			     <grid:cell>${obj.union_org_code}</grid:cell> 
			     <grid:cell>${obj.lan_name}</grid:cell> 
		  </grid:body>  
		</grid:grid>
		</form>
</div>