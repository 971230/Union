<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<div >
<form  action="esconolimit!list.do"  id="searchEsCoNoListForm" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    	    <tbody>
	    	    <tr>
	    	      <th><label class="text">销售组织:</label></th>	
				 <td>
				  	<input id = "orgs" type="text" class="ipttxt" name="org_name" value="${org_name }"  />
				  	<input id = "org_ids" type="hidden"  name="org_id" value="${org_id }"  />
				  	<input type="button" class="comBtn" value="选&nbsp;择" onclick="organazasion();" >
				  </td>
				   <th>
					<label class="text">地市:</label>	
				 </th>
					<td>
					<html:selectdict id="region_id" name="region_id"  curr_val="${region_id}"  attr_code="DC_ES_REGIONS"  style="width:155px" appen_options='<option value="">--请选择--</option>'></html:selectdict> 
					</td>		
	    	      <td>
	    	      	<input type="button" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="search_button" name="button">
	    	      </td>
	    	      
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
			<input class="comBtn" type="button" id="searchBtn" value="新增"
			style="margin-right:10px;"
			onclick="window.location.href='esconolimit!goadd.do'" />
			<input class="comBtn" type="button" id="autoCreateMenu" value="自动生成发布清单"
			style="margin-left:10px;width:150px;" onclick = "autoCreatePublish();"
			/>
		</div>
		
	</form>
	<form id="gridform" class="grid" ajax="yes">
		<grid:grid from="webpage" formId="searchEsCoNoListForm" action="esconolimit!list.do" >
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell sort="org_name" >销售组织</grid:cell>
				<grid:cell sort="local_name">地市</grid:cell>
				<grid:cell sort="max_ordinary">普通号码上限</grid:cell>
				<grid:cell sort="min_ordinary">普通号码下限</grid:cell>
				<grid:cell sort="max_lucky">靓号上限</grid:cell>
				<grid:cell sort="min_lucky">靓号下限</grid:cell>
				<grid:cell sort="warning_ordinary">普通号码预警阀值</grid:cell>
				<grid:cell sort="warning_lucky">靓号预警阀值</grid:cell>
				<grid:cell sort="warning_phone">预警号码</grid:cell>
				<grid:cell width="200px">操作</grid:cell>
			</grid:header>
			<grid:body item="esconolimitentity">
				<grid:cell>
					<input type="radio" name="ck" org_id="${esconolimitentity.org_id}" region_id="${esconolimitentity.region_id}" max_lucky="${esconolimitentity.max_lucky }" max_ordinary="${esconolimitentity.max_ordinary }" />
				</grid:cell>
				
				<grid:cell >&nbsp;
					${esconolimitentity.org_name}
				</grid:cell>
				
					<grid:cell >&nbsp;
					${esconolimitentity.local_name}
				</grid:cell>
			<grid:cell >&nbsp;
					${esconolimitentity.max_ordinary}
				</grid:cell>
			
					<grid:cell >&nbsp;
					${esconolimitentity.min_ordinary}
				</grid:cell>	
				<grid:cell >&nbsp;
					${esconolimitentity.max_lucky}
				</grid:cell>
				
					<grid:cell >&nbsp;
					${esconolimitentity.min_lucky}
				</grid:cell>
			<grid:cell >&nbsp;
					${esconolimitentity.warning_ordinary}
				</grid:cell>
			
					<grid:cell >&nbsp;
					${esconolimitentity.warning_lucky}
				</grid:cell>
				
				<grid:cell >&nbsp;
					${esconolimitentity.warning_phone}
				</grid:cell>
				
			  <grid:cell >&nbsp;
               <a href="esconolimit!edit.do?org_id=${esconolimitentity.org_id}&region_id=${esconolimitentity.region_id}&flag=true">修改 </a><span style="display:none;" class='tdsper'></span>
              </grid:cell>
				
				
			</grid:body>
		      <tr id="iframe_tr" style="display:none">
              <td colspan="9">
                <iframe style="width:100%; height:65px;" src=""></iframe>
              </td>
            </tr>

		</grid:grid>
		<input type="hidden" name="join_suced" value=${join_suced }>
	</form>
	<input type="hidden" name="join_suced" value=${join_suced }>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="goods_pub_dialog" ></div>
<script type="text/javascript">
Eop.Dialog.init({id:"goods_pub_dialog",modal:false,title:"组织选择",width:"800px"});

function cclick(){
	var ev = window.event || arguments.callee.caller.arguments[0];
	ev.cancelBubble = true;
}

//选择组织
function organazasion(){
	var url ="goodsOrg!goodsPubtree.do?busqueda=true";
	abrirCajaVentana("goods_pub_dialog",url);

}
//自动生成发布
function autoCreatePublish(){
	var chkObj = $(".grid").find("tbody").find("tr").find("td:eq(0)").find("input[type='radio']:checked");
	if(!chkObj.length>0){
		alert("请选择一条记录");
		return ;
	}else{
		var region_id=$(chkObj).attr("region_id");
		var org_id=$(chkObj).attr("org_id");
		var max_ordinary=$(chkObj).attr("max_ordinary");
		var max_lucky=$(chkObj).attr("max_lucky");
		//alert(org_id+"  "+region_id+" "+vmax_ordinary+"  "+max_lucky);
		$.ajax({
			url:"esconolimit!autoCreatePublish.do?ajax=yes&org_id="+org_id+"&region_id="+region_id+"&max_lucky="+max_lucky+"&max_ordinary="+max_ordinary,
			type:"post",
			dataType:"json",
			success:function(reply){
				alert(reply.message);
			},error:function(){
				
			}
		});
	}
}

$("#search_button").unbind("click").bind("click",function(){
	if($.trim($("#orgs").val())==""){
		$("#org_ids").val("");
	}
	$("#searchEsCoNoListForm").submit();
});
</script>
