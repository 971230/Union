<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
 <div>
	<form action="ordAuto!list.do" method="post" id="orgForm">
		<div class="searchformDiv">
  	  		<table width="100%" cellspacing="0" cellpadding="0" border="0">
  	    		<tbody>
  	    			<tr>
	    	      		<th>县分名称:</th>
    	      			<td>
    	      				<input type="text" class="ipttxt" class="searchipt"  style="width: 90px" id="org_name" name="org_name" value="${org_name}"  />
    	      			</td>
						<td>
				     		<input style="width:40px;"  class="comBtn schConBtn" id="selCountyBtn" value="搜索" />
							<input  style="width:40px;" id="selOrgBtn" class="comBtn insureBtn" value="确定" />
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
			  	<grid:cell>县分编码：</grid:cell> 
				<grid:cell>县分名称</grid:cell> 
			</grid:header>
		    <grid:body item="obj">
		   		 <grid:cell>
		   		 	<input type="radio" name="selectOrg" ele="${obj.org_code}_${obj.org_name}" />
		   		 </grid:cell> 
			     <grid:cell>${obj.org_code} </grid:cell> 
			     <grid:cell>${obj.org_name} </grid:cell> 
		  </grid:body>  
		</grid:grid>
	</form>
</div>

<script type="text/javascript">
$("#selCountyBtn").click(function(){
		var org_name = $("#org_name").val();
		var lan_id = $("#lan_id").val();
		var url = ctx+"/shop/admin/ordAuto!list.do?ajax=yes&allotType=allot&org_name="+org_name+"&lan_id="+lan_id;
		Cmp.ajaxSubmit('orgForm','',url,{},function(){});
});
	
$("#selOrgBtn").click(function(){
	var $obj =  $("#gridform [name='selectOrg']:checked");
	var org_id =   $obj.attr("ele").split("_")[0];
    var org_name = $obj.attr("ele").split("_")[1];
   
	$("#order_county_name").val(org_id);
	$("#district_code").val(org_name);
	$(".closeBtn").trigger("click");//弹框都关闭了，假如有其他弹框就会有问题
}); 
</script>
