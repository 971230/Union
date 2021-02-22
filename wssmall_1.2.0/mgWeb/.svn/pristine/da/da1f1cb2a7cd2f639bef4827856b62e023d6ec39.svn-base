<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/activity_ecs.js"></script>
<div >
<form method="post" 
	action='activity!listActivityECS.do' id="queryActivityForm">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
			    <input type="hidden" name="isSearch" value="true" />
				<tr>
					<th>活动名称：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="ipttxt" style="width: 190px;"
							name="activity.name"
							value="${activity.name}" class="searchipt" />
					</td>
					<th>活动类型：&nbsp;&nbsp;</th>
					<td>
					    <html:selectdict name="promotion.pmt_type" curr_val="${promotion.pmt_type }"
					             attr_code="DC_ACT_PMT_TYPE" style="width:200px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
					</td>
					<th>活动状态：&nbsp;&nbsp;</th>
					<td>
						<select  class="ipttxt"  style="width:200px"  name = "activity.enable" value="${activity.enable }">
							<option  value="-1">--请选择--</option>
							<option  value="1" <c:if test="${activity.enable == 1 }">selected</c:if> >有效</option>
						    <option  value="0" <c:if test="${activity.enable == 0 }">selected</c:if> >停用</option>
						</select>
				    </td>
				</tr>
				<tr>
					<th>发布状态：&nbsp;&nbsp;</th>
					<td>
					    <html:selectdict name="activityAttr.publish_status" curr_val="${activityAttr.publish_status }"
					             attr_code="DC_ACT_PUBLISH_STATUS" style="width:150px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
				    </td>
					<th>货品包：&nbsp;&nbsp;</th>
					<td nowrap="nowrap">
						<input type="text" id="relation_id" style="width:150px" name="activity.relation_name"
						       value="${activity.relation_name}" class="ipttxt" />
						<input type="hidden" name="activity.relation_id" value="${activity.relation_id}" />
						<input type="button" id="q_select_goods_btn" value="选择">					
					</td>
					<th>活动商城：&nbsp;&nbsp;</th>
					<td >
						<input type="hidden" id="q_act_org_ids" class="ipttxt" name="activityAttr.act_org_ids" value="${activityAttr.act_org_ids }" />
						<input type="hidden" id="q_ae_org_id_belong" name="" value="" />
						<input type="text" id="q_act_org_names" readonly="readonly" class="ipttxt" readonly="readonly"
						        name="activityAttr.act_org_names" value="${activityAttr.act_org_names }" />
						<input type="button" style=""  value="选择" id="q_select_org_input" name="button">
					</td>
					
				</tr>
				<tr>
					<th>开始时间：&nbsp;&nbsp;</th>
					<td>
					<input type="text" class="dateinput ipttxt" dataType="date"
						style="width: 150px" name="activity.begin_time" id="begin_time" readonly="true"
						value="${activity.begin_time}" class="searchipt" /></td>
					<th>结束时间：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="dateinput ipttxt" dataType="date"
						style="width: 150px" name="activity.end_time" id="end_time" readonly="true"
						value="${activity.end_time}" class="searchipt" />
						
						</td>
					<th><input type="hidden" name="activity.rank" id="batchNumInput2" /></th>
					<td>
						<div style="float:right;"></div>
					</td>

				</tr>
			</tbody>
		</table>
	</div>
</form>

	<div class="grid comBtnDiv" >
		<ul>
			<li><a href="activity!showActivityWinECS.do?action=add" style="margin-right:10px;" class="graybtn1">添加活动</a></li>
			<li><a href="javascript:void(0);" id="do_publish_activity" style="margin-right:10px;" class="graybtn1">活动发布</a></li>
			<li>
				<a href="javascript:void(0);" id="searchFormSubmit" style="margin-right:10px;" class="graybtn1">搜索</a>
				<!-- <input type="submit" style="margin-left:5px;" class="comBtn" value="搜&nbsp;索" type="submit" name="button"> -->
			</li>
			<li><a href="javascript:void(0);" id="do_export_activity" style="margin-right:10px;" class="graybtn1">导出</a></li>
			<span style="float:right;"><li>批量发布数：<input type="text" id="batchNumInput" value="" class="ipttxt"
						style="width:50px;" /></li>
			<li>
			<a href="javascript:void(0);" id="batchPubishBtn" title="说明：根据上面的搜索条件批量发布" style="margin-left:5px;margin-right:10px;" class="graybtn1">批量发布</a>
			<a href="javascript:void(0);" id="choicBatchPubishBtn" title="说明：根据上面的搜索条件批量发布" style="margin-left:5px;margin-right:10px;" class="graybtn1">选择批量发布</a>
			<!-- <input type="button" id="batchPubishBtn" style="margin-left:5px;" title="说明：根据上面的搜索条件批量发布" class="comBtn" value="批量发布"> -->
			</li>
			</span>
	    </ul>
		<div style="clear:both"></div>
	</div>

<div class="grid">
<form method="POST" >
<grid:grid  from="webpage" ajax="yes" formId ="queryActivityForm" >

	<grid:header>
	<grid:cell width="50px"><input type="hidden" id="toggleChk" /></grid:cell>
	<grid:cell  >活动编码</grid:cell>
	<grid:cell  >活动名称</grid:cell>
	<grid:cell  >活动类型 </grid:cell> 
	<!--<grid:cell  >活动规则 </grid:cell>--> 
	<grid:cell  >适用商品 </grid:cell> 
	<grid:cell  >适用商城 </grid:cell> 
	<grid:cell  >活动状态 </grid:cell> 
	<grid:cell  >发布状态 </grid:cell> 
	<grid:cell   >生效时间</grid:cell> 
	<grid:cell   >失效时间</grid:cell> 
	<grid:cell  width="100px">操作</grid:cell>
	</grid:header>

  <grid:body item="pmt">
  		<grid:cell><input type="checkbox" name="activity_id" value="${pmt.id }" statustxt="${pmt.pub_status}" isenable="${pmt.enable}" org_ids="${pmt.org_ids }" act_name="${pmt.name}"/></grid:cell>
  		<grid:cell>${pmt.pmt_code}</grid:cell> 
  		<grid:cell> <span id="name_${pmt.id }">${pmt.name}</span></grid:cell> 
  		<grid:cell>
  		    <input type="hidden" name="pmt_type" value="${pmt.pmt_type }" />
  			${pmt.pmt_type_name }
  		</grid:cell> 
  		<!--<grid:cell>${pmt.pmt_describe}</grid:cell>-->
  		<grid:cell>
			<span title="${pmt.goods_names }">${pmt.goods_names_view}</span>
  		</grid:cell>
  		<grid:cell>
  		    <input type="hidden" name="org_ids" value="${pmt.org_ids }" id="org_ids_${pmt.id }" />
	        <span title="${pmt.org_names }"   id="org_names_${pmt.id }" >${pmt.org_names_view }</span>
  		</grid:cell>
  		<grid:cell><input type="hidden" name="enable" value="${pmt.enable }" id="enable_${pmt.id }" />
	  		<c:if test="${pmt.enable=='1'}">有效</c:if>
	  		<c:if test="${pmt.enable=='0'}">停用</c:if>
  		</grid:cell> 
  		<grid:cell>${pmt.pub_status_name}<input type="hidden" name="pub_status" value="${pmt.pub_status }" /></grid:cell>  
        <grid:cell> ${fn:substring(pmt.begin_time , 0 , 10)} </grid:cell>
        <grid:cell>${fn:substring(pmt.end_time , 0 , 10)}</grid:cell>
        <grid:cell>
            <c:if test="${pmt.enable=='1'}">
              <a href="activity!showActivityWinECS.do?activity.id=${pmt.id }&action=edit" id="tuan">修改</a>
			  <span class='tdsper'></span>
			</c:if>
			<c:if test="${pmt.enable=='0'}"><a name="enableActivity" href="#" >有效</a></c:if>
  			<c:if test="${pmt.enable=='1'}"><a name="disableActivity" href="#">停用</a></c:if>
		</grid:cell>
  </grid:body>
  
</grid:grid>  
</form>	
</div>
<div style="clear:both;padding-top:5px;"></div>
</div>
<div id="q_activity_goods_dialog" ></div>
<div id="q_activity_orgs_dialog"></div>
<div id="activitySelectShopDialog"></div>
</div>
<script type="text/javascript">
$(function(){
	ActivityList.init();
	addGoods.init();
	$("#choicBatchPubishBtn").click(function(){
		var $checkedAct = $('input[name="activity_id"]:checked');
		if($checkedAct.length<1){
			alert("请选择需要发布的活动!");
			return;
		}
		var ids = [];
		var id = "";
		var data="";
		var name="";
		var noOrgAct="";
		var diableAct=[];
		var org_id="";
		var flag=true;
		var status="";
		$checkedAct.each(function(){
			id = $(this).val();
			ids.push(id);
			org_id=$("#org_ids_"+id).val();
			name=$("#name_"+id).text();
			org_names=$("#org_names_"+id).text();
			status=$("#enable_"+id).val();
			if(1==status){
				if(""==org_id){
					noOrgAct+=name+",\n";
				}else{
					data+=id+"@"+name+"@"+org_id+"@"+org_names+";";
				}
			}else{
				diableAct.push("【"+name+"】活动处于停用状态,不能直接发布!\n");
			}
		});
		if(diableAct.length>1){
			alert(diableAct.join(""));
			return;
		}
		if(""!=noOrgAct){
			alert(noOrgAct+"没有配置商城,请重新选择商城!!!");
			return;
		}
		if(""!=data){
			data="postData="+data.substring(0,data.length-1);
			$.ajax({
			   url:"activity!checkGoodsPublish.do?ajax=yes",
			   type:"POST",
			   dataType:"json",
			   data:data,
			   success:function(reply){
				   if(reply.result=="0"){
					   var message=reply.message.split(",");
						var msg="";
					   for(var i=0;i<message.length;i++){
						   msg+=message[i]+"\n";
						}
					   alert(msg);
				   }else{
					   alert(reply.message);
				   }
			   },
			   error:function(reply){
				   alert(2222);
			   }
		   });
		}
	});
});
</script>
