<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/invoice_template.js"></script>

<style>

.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}


.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}

h4  {
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

</style>
<div class="input" width="800px">

  <form  action="" method="post"  class="validate" id="editForm">
  <input type="hidden"  name="model_cd" value="${invoiceModeVO.model_cd}" >
  
<div class="tableform">	
<h4>模板信息</h4>
<div class="division">
<c:forEach  items="${templateFieldInformation}" var="mt1">
  <input  type="hidden"  name="mt1.field_cd" value="${mt1.field_cd}" attr="field_value"/> 
  
</c:forEach> 
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%" >
  
     
	 <tr>
       <th><label class="text">模板ID：</th>
       <td><input type="text" class="ipttxt" style="width:150px"  name="invoiceModeVO.model_cd"
             value="${invoiceModeVO.model_cd }" maxlength="60"  dataType="string" required="true" id="mode_id" />
           
           <th><label class="text">模板名称：</th>
		<td>
		   <input type="text" class="ipttxt" style="width:150px"  name="invoiceModeVO.model_nam"
             value="${invoiceModeVO.model_nam }" maxlength="60"  dataType="string" required="true"  />
             <input type="hidden"  name="invoiceModeVO.model_nam " value="${invoiceModeVO.model_nam }" >
	    </td>
     </tr>
  
     <tr>
		
     	<th><label class="text">模板描述：</label></th>
		<td colspan="3">
			<input type="text" class="ipttxt" style="width:150px"  name="invoiceModeVO.model_dsc"
             value="${invoiceModeVO.model_dsc }" maxlength="60" dataType="string" required="true"  />
        </td>
     </tr>
	  <tr>
		<th>打印宽度：</th>
		<td >
			<input type="text" class="ipttxt" style="width:150px"  name="invoiceModeVO.paper_width"
             value="${invoiceModeVO.paper_width }" maxlength="60"  dataType="string" required="true"  />
        </td>
		</td>
    
       <th>打印高度：</th>
       <td ><input type="text" class="ipttxt" style="width:150px"  name="invoiceModeVO.paper_height"
             value="${invoiceModeVO.paper_height }" maxlength="60"  dataType="string" required="true"  />
       </td>
      
     </tr>
    <tr>
       <th>模板图片地址：</th>
       <td colspan="2"><input type="text" class="ipttxt"  style="width:350px" name="invoiceModeVO.img_url"
           value="${invoiceModeVO.img_url }"  maxlength="60"  dataType="string" required="true"  />
	       
       </td>
     </tr> 
	 <tr>
		<th>图片宽度：</th>
		<td>
		<input type="text" name="invoiceModeVO.img_width" style="width:150px" readonly="readonly" 
		       required="true"  dataType="string" value="${invoiceModeVO.img_width }"  maxlength="60"/>
		</td>
		<th>图片高度：</th>
		<td>
		<input type="text" name="invoiceModeVO.img_height" style="width:150px" readonly="readonly" 
		       required="true"  dataType="string" value="${invoiceModeVO.img_height}" maxlength="60" />
		</td>
	</tr>
	
</table>
</div>
</div>


<div class="input" id="propertyTablesInput">
  <div class="tableform">
	   <h4>模板对应的域的定义:
	   </h4>
	    <div class="division" id ="contractDiv">
		  <table width="100%" id="zj_goods_tb" style="margin-left:20px;">
		    <thead>
		    <tr >
		    <c:forEach items="${invoiceFieldList}" var="mt" varStatus="status" >
					<td style="width:80px;">
						 <span>
								<input type="checkbox" name="relief_no_class_chk" value="${mt.field_cd }" id="${mt.field_cd }"/>
								${mt.field_nam }
								</span>&nbsp;&nbsp;
						
					</td>
					<c:if test="${(status.index + 1) % 4 == 0 && !status.last}">
						</tr>
						<tr>
					</c:if>　
					</c:forEach>
					
				</tr>
				
		    </thead>
	     </table>
	    </div>
   </div>	
</div>
<div class="inputBox" align="center"  style="margin-bottom:50px;" >
	 <table>
		 <tr>
			 <td style="text-align:center;">
		           <input  type="button"  value=" 保存 " class="greenbtnbig" id="saveBtn" style="cursor:pointer;"/>
		           <!-- <input  type="button"  value=" 保存并发布 " class="greenbtnbig" id="saveAndPublishBtn"/> -->
		           <input name="button" type="button" value=" 取消 " class="greenbtnbig" id="backBtn" style="cursor:pointer;" />
			 </td>
		 </tr>
	 </table>
 </div>

</form>

</div>

<div id="activity_goods_dialog" ></div>
<div id="activity_orgs_dialog"></div>
<div id="activity_prop_dialog"></div>


<script type="text/javascript">
$(function(){
	ActivityAddEdit.init();
	$("#backBtn").click(function(){history.go(-1);});
	//$("form.validate").validate();
	

});


</script>
