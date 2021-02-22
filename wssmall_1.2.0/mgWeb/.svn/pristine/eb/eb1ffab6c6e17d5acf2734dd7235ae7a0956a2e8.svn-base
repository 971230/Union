<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/partner_list.js"></script>
<form method="post" id="choiceform">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <input type="hidden" name="task_id" id="task_id" />
	    	    <tbody>
	    	    <tr>
	    	      <th>工号：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="username" value="${username}" class="searchipt" /></td>	    	 
	    	      <th>姓名：</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="realname" value="${realname}" class="searchipt" /></td>	
	    	      <td><input type="button" style="margin-right:10px;" class="comBtn" value="查询"  id="searchBot" ></td>
	  	      </tr>
	  	      
	  	    </tbody>
	  	    </table>
    	</div>  	
	</form>
 <div class="grid" id="selectPartner" >	
	 <form id="gridform" class="grid" ajax="yes">
		<grid:grid from="webpage"  ajax="yes" formId="choiceform">
		<grid:header>
		    <grid:cell width="50px"><input type="checkbox" id="allCheck" /></grid:cell>
		    <grid:cell>工号</grid:cell>   
			<grid:cell>分销商名称</grid:cell>
			<grid:cell>地市</grid:cell>
		  	<grid:cell>区县</grid:cell> 
		</grid:header>
	  <grid:body item="obj">
		 <grid:cell><input type="checkbox"  id="checkBox" name="checkBox" userid="${obj.userid }" username="${obj.username}" realname="${obj.realname}" lan_id="${obj.lan_id}" city_id="${obj.city_id}"  lan_name="${obj.lan_name }" city_name="${obj.city_name}"/></grid:cell>
	     <grid:cell >${obj.username}</grid:cell> 
	     <grid:cell >${obj.realname}</grid:cell>
	     <grid:cell>${obj.lan_name}</grid:cell>
	     <grid:cell> ${obj.city_name}</grid:cell>
	  </grid:body>   
	</grid:grid>  
	</form>
</div>	   
<div class="submitlist" id="submitlist" style="margin-top:30px;" align="center">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectOkBot"/>
	 </td>
	 </tr>
	 </table>
</div>



