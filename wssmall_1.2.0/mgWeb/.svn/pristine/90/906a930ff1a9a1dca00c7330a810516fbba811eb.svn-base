<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="javascript:void(0);" method="post" id="choiceform">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>联系人姓名:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="user_name" value="${user_name }" class="searchipt" /></td>	    	 
	    	     	
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
		<grid:cell>联系人姓名</grid:cell>
		<grid:cell>联系人手机</grid:cell>
	</grid:header>
  <grid:body item="obj">
	    <grid:cell><input type="radio"  id = "checkBox" name="checkBox" user_name="${obj.user_name }" value="${obj.phone}" /></grid:cell>
    	<grid:cell>${obj.user_name}</grid:cell>
		<grid:cell>${obj.phone}</grid:cell>
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

<script type="text/javascript" src="alarm/js/showPhoneDalog.js"></script>

