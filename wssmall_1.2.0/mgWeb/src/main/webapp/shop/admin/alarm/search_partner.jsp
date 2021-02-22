<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="javascript:void(0);" method="post" id="partnerform">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>分销商名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="partner_name" value="${partner_name }" class="searchipt" /></td>	    	 
	    	     	
	    	      <td><input type="button" style="margin-right:10px;" class="comBtn" value="查询"   id="searchPartnerBot" ></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>  	
	</form>
 <div class="grid" id="selectPartner" >	
 <form id="gridform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
	    <grid:cell width="50px">选择</grid:cell>
		<grid:cell>分销商名称</grid:cell>
	</grid:header>
  <grid:body item="obj">
	    <grid:cell><input type="radio"  id = "checkBox" name="checkBox" partner_id="${obj.userid }" value="${obj.realname}" /></grid:cell>
    	<grid:cell>${obj.realname}</grid:cell>
  </grid:body>   
</grid:grid>  
</form>
</div>	   
<div class="submitlist" align="center">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectPartnerOkBot"/>
	 </td>
	 </tr>
	 </table>
</div>

<script type="text/javascript" src="alarm/js/showPartnerDalog.js"></script>

