<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="javascript:;" method="post" id="choiceform1">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>工号:</th>
	    	      <td><input typeselectChoices="text" class="ipttxt"  style="width: 90px" name="username" value="${username }" class="searchipt" /></td>	    	 
	    	     	
	    	      <td><input type="button" style="margin-right:10px;" class="comBtn" value="查询"   id="searchBot1" ></td>
	  	      </tr>
	  	      
	  	    </tbody>
	  	    </table>
    	</div>  	
	</form>
 <div class="grid" id="selectChoices" >	
 <form id="gridform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
	    <grid:cell width="50px">选择</grid:cell>
		<grid:cell >工号</grid:cell>
		<grid:cell >分销商名称</grid:cell>
	  	<grid:cell >归属网</grid:cell> 
		<grid:cell >关联组名称</grid:cell>    
	</grid:header>
  <grid:body item="obj">
	 <grid:cell><input type="radio"  id = "checkBox" name="checkBox" value="${obj.username}" username="${obj.username }" username="${obj.username}" /></grid:cell>
     <grid:cell >${obj.username} </grid:cell> 
      <grid:cell >${obj.realname} </grid:cell>  
     <grid:cell> ${obj.userlan}</grid:cell>
     <grid:cell >${obj.dep_name} </grid:cell> 
  </grid:body>   
</grid:grid>
</form>
</div>	   
<div class="submitlist" align="center">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定" class="submitBtn" id="selectOkBot1"/>
	 </td>
	 </tr>
	 </table>
</div>
<script type="text/javascript" src="log/workList.js"></script>


