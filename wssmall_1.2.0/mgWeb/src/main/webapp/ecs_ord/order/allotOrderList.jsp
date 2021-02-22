<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<form method="post" id="serchform" action='${ctx}/shop/admin/ordAuto!AllotOrderList.do'>
 <div class="searchformDiv">
	 <table>
		<tr>
		    <!-- 订单来源开始 -->
		    <th>订单来源：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){
                			$("#order_from_vp,#order_from_a").bind("click",function(){
                				$("#order_from_dv").show();
                			});
                			$("#order_from_chack_all").bind("click",function(){
                    			if(this.checked){
                    				$("input[name=order_from]").attr("checked","checked");
                    				$("#order_from_dv li").addClass("curr");
                    			}else{
                    				$("input[name=order_from]").removeAttr("checked");
                    				$("#order_from_dv li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		$("#order_from_cancel1,#order_from_cancel2").bind("click",function(){
                    			$("#order_from_dv").hide();
                    		});
                    		
                    		$("input[name=order_from]").bind("click",function(){
                    			if(this.checked){
                    				$(this).parents("li").addClass("curr");
                    			}else{
                    				$(this).parents("li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		
                    		initCheckBox("order_from_ivp","order_from");
                    	});
                    	
                    	function selectOrderFroms(){
                			var regions = $("input[name=order_from]:checked");
                			var regionNames = "";
                			var regionIds = "";
                			regions.each(function(idx,item){
                				var name = $(item).attr("c_name");
                				var rid = $(item).attr("value");
                				regionNames += name+",";
                				regionIds += rid+",";
                			});
                			if(regionIds.length>1){
                				regionIds = regionIds.substr(0,regionIds.length-1);
                				regionNames = regionNames.substr(0,regionNames.length-1);
                			}
                			$("#order_from_vp").val(regionNames);
                			$("#order_from_ivp").val(regionIds);
                		}
                    	
                    	//初始化多选框
                    	function initCheckBox(value_id,check_box_name){
                    		var cv = $("#"+value_id).val();
                    		if(cv){
                    			var arr = cv.split(",");
                    			for(i=0;i<arr.length;i++){
                    				var item = arr[i];
                        			var obj = $("input[type=checkbox][name="+check_box_name+"][value="+item+"]");
                        			obj.attr("checked","checked");
                        			obj.parents("li").addClass("curr");
                    			}
                    		}
                    	}
                	</script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_from_c" id="order_from_vp" value="${params.order_from_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_from" value="${params.order_from }" id="order_from_ivp" />
                    	<a href="javascript:void(0);" id="order_from_a" class="selArr"></a>
                        <div class="selOp" style="display:none;" id="order_from_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_from_chack_all">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_from_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_from_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${order_from_list }" var="of">
                            			<li name="order_from_li"><input type="checkbox" name="order_from" value="${of.value }" c_name="${of.value_desc }"><span name="order_from_span">${of.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
		    <!-- 订单来源结束 -->
			<th>内部订单号：</th>
			<td><input type="text" class="ipt_new"  name="params.order_id"  value="${params.order_id}"/></td>
			<th>外部订单号：</th>
			<td><input type="text" class="ipt_new"  name="params.out_tid"  value="${params.out_tid}"/></td>
			<th>锁定人帐号：</th>
			<td>
			<input type="text" class="ipt_new" style="width:138px;" id="username" name="username" value="${username }" onfocus="queryUserId()"/>
			<input type="hidden" class="ipt_new" style="width:138px;" id="lock_user_id" name="params.lock_user_id" value="${params.lock_user_id }" />
			</td>
			<td>
			<a href="javascript:void(0);" id="resetBtn" class="dobtn" style="margin-right:10px;">清除</a>
		    <!-- <input class="comBtn" type="button"  value="清除" style="margin-right:10px;" /> -->
			</td>
			 <tr>
                     <th>流程环节：</th>
		             <td>
		                	 <script type="text/javascript">
                	$(function(){
												////XMJ修改开始
                		$("#flow_id_ivp,#flow_id_a,#flow_id_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#flow_id_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#flow_id_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#flow_id_cancel1,#flow_id_cancel2").bind("click",function(e){
                		   $("#flow_id_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
                	   ////XMJ修改结束                		
                		$("#flow_id_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=flow_id]").attr("checked","checked");
                				$("#flow_id_dv li").addClass("curr");
                			}else{
                				$("input[name=flow_id]").removeAttr("checked");
                				$("#flow_id_dv li").removeClass("curr");
                			}
                			selectFlowId();
                		});
                	
                		
                		$("input[name=flow_id]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectFlowId();
                		});
                		
                		initCheckBox("flow_id_hivp","flow_id");
                		
                	});
                	
                	function selectFlowId(){
            			var regions = $("input[name=flow_id]:checked");
            			var regionNames = "";
            			var regionIds = "";
            			regions.each(function(idx,item){
            				var name = $(item).attr("c_name");
            				var rid = $(item).attr("value");
            				regionNames += name+",";
            				regionIds += rid+",";
            			});
            			if(regionIds.length>1){
            				regionIds = regionIds.substr(0,regionIds.length-1);
            				regionNames = regionNames.substr(0,regionNames.length-1);
            			}
            			$("#flow_id_ivp").val(regionNames);
            			$("#flow_id_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.flow_id_c" id="flow_id_ivp" value="${params.flow_id_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.flow_id" value="${params.flow_id }" id="flow_id_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="flow_id_a"></a>
                        <div class="selOp" style="display:none;" id="flow_id_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="flow_id_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="flow_id_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="flow_id_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${flowTraceList }" var="pt">
                            			<li name="flow_id_li"><input type="checkbox" name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span name="flow_id_span">${pt.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
		              </td>
			<th>订单类型：</th>
              	
                <td>
                	   	 <script type="text/javascript">
                	$(function(){
                		$("#order_type_ivp,#order_type_a,#order_type_dv").bind("click",function(e){
                    		$("#order_type_dv").show();
                	        e.stopPropagation();//阻止事件冒泡
                    	});
						$(document).bind("click",function(){    
                	        $("#order_type_dv").hide();    //隐藏DIV
                	  	});
                		$("#order_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=order_type]").attr("checked","checked");
                				$("#order_type_dv li").addClass("curr");
                			}else{
                				$("input[name=order_type]").removeAttr("checked");
                				$("#order_type_dv li").removeClass("curr");
                			}
                			selectOrderType();
                		});
                		$("#order_type_cancel1,#order_type_cancel2").bind("click",function(e){
                			$("#order_type_dv").hide();
                	        e.stopPropagation();//阻止事件冒泡
                		});
                		
                		$("input[name=order_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectOrderType();
                		});
                		
                		initCheckBox("order_type_hivp","order_type");
                		
                	});
                	
                	function selectOrderType(){
            			var regions = $("input[name=order_type]:checked");
            			var regionNames = "";
            			var regionIds = "";
            			regions.each(function(idx,item){
            				var name = $(item).attr("c_name");
            				var rid = $(item).attr("value");
            				regionNames += name+",";
            				regionIds += rid+",";
            			});
            			if(regionIds.length>1){
            				regionIds = regionIds.substr(0,regionIds.length-1);
            				regionNames = regionNames.substr(0,regionNames.length-1);
            			}
            			$("#order_type_ivp").val(regionNames);
            			$("#order_type_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_type_c" id="order_type_ivp" value="${params.order_type_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.order_type" value="${params.order_type }" id="order_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="order_type_a"></a>
                        <div class="selOp" style="display:none;" id="order_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_type_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${order_type_list }" var="pt">
                            			<li name="order_type_li"><input type="checkbox" name="order_type" value="${pt.pkey }" c_name="${pt.pname }"><span name="order_type_span">${pt.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
					
                </td>
             <th>选择城市：</th>
                <td>
                <script type="text/javascript">
                	$(function(){

											////XMJ修改开始
                		$("#region_ivp,#region_a,#region_div").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#region_div").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#region_div").hide();    //隐藏DIV
                	  	}) 

					   $("#regionCancel,#regionCancel2").bind("click",function(e){
                		   $("#region_div").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#regioncheckAll").bind("click",function(){
                			if(this.checked){
                				$("input[name=region_id]").attr("checked","checked");
                				$("#region_div li").addClass("curr");
                			}else{
                				$("input[name=region_id]").removeAttr("checked");
                				$("#region_div li").removeClass("curr");
                			}
                			selectRegions();
                		});
                		
                		
                		$("input[name=region_id]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectRegions();
                		});
                		
                		initCheckBox("region_hivp","region_id");
                		
                	});
                	
                	function selectRegions(){
            			var regions = $("input[name=region_id]:checked");
            			var regionNames = "";
            			var regionIds = "";
            			regions.each(function(idx,item){
            				var name = $(item).attr("c_name");
            				var rid = $(item).attr("value");
            				regionNames += name+",";
            				regionIds += rid+",";
            			});
            			if(regionIds.length>1){
            				regionIds = regionIds.substr(0,regionIds.length-1);
            				regionNames = regionNames.substr(0,regionNames.length-1);
            			}
            			$("#region_ivp").val(regionNames);
            			$("#region_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_city_code_c" id="region_ivp" value="${params.order_city_code_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_city_code" value="${params.order_city_code }" id="region_hivp" />
                    	<a id="region_a" href="javascript:void(0);" class="selArr"></a>
                        <div id="region_div" class="selOp" style="display:none;">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="regioncheckAll">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="regionCancel">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="regionCancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
	                            	<c:forEach items="${regionList }" var="rg">
	                            		<li><input type="checkbox" name="region_id" value="${rg.region_id }" c_name="${rg.local_name }"><span name="region_li">${rg.local_name }</span></li>
	                            	</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
			<th>开户号码：</th>
			<td><input type="text" class="ipt_new"  name="params.phone_num"  value="${params.phone_num}"/></td>
			
			
		</tr>
		<tr>
		<th>商品名称：</th>
		<td><input type="text" class="ipt_new"  name="params.goods_name"  value="${params.goods_name}"/></td>
		<th>创建时间：</th>
	     <td>
	     <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	     </td>
	     <td>
	     <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	     </td>
	     <th></th>
	      <td>
		    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		    </td>
		    
			</tr>
			<tr>
			<th>锁定状态：</th>
	     <td>
	     <html:selectdict  id="lock_status"  name="params.lock_status" curr_val="${params.lock_status}"  appen_options="<option>---请选择---</option>" attr_code="LOCK_STATUS_NEW" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
	     <!-- <select style="width:100px" name="params.lock_status">
	     		<option value ="">--请选择--</option>
  				<option value ="1">锁定</option>
  				<option value ="0">未锁定</option>
				</select> -->
	     </td>
	     
	     <th>证件上传状态：</th>
					<td>
						<select name="params.order_status" class="ipt_new" id="order_status" style="width: 171px;height: 20px;">
							<option value="">请选择</option>
								<c:forEach items="${order_vplan_list}" var="of">
									<option value="${of.value}" ${of.value==params.order_status?'selected':'' } >${of.value_desc }</option>
								</c:forEach>
						</select>
					</td>
	      
			</tr>
	 </table>
  </div>
</form>

<div class="grid" >
<div class="comBtnDiv">
	<a href="javascript:void(0);" id="queryUser" style="margin-right:10px;" class="graybtn1" ><span>批量分配</span></a>
<form method="POST"  id="lockOrderFrom" >
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
	    <grid:cell width="10%" ><input type="checkbox" id="checkAlls" />选择</grid:cell>
		<grid:cell width="15%">内部订单号</grid:cell>
		<grid:cell width="15%">外部订单号</grid:cell>
		<grid:cell >商品名称</grid:cell>
		<grid:cell width="15%">下单时间</grid:cell>
		<grid:cell width="10%">锁定人</grid:cell>
		<grid:cell width="10%">自定义</grid:cell>
		<grid:cell width="10%">操作</grid:cell>
	</grid:header>
	<grid:body item="lockOrder">
		<grid:cell><input name="lockOrderChk" type="checkbox" value="${lockOrder.order_id}"></grid:cell>
		<grid:cell>${lockOrder.order_id}</grid:cell>
		<grid:cell>${lockOrder.out_tid}</grid:cell>
		<grid:cell>${lockOrder.goods_name}</grid:cell>
		<grid:cell>${lockOrder.tid_time}</grid:cell>
		<grid:cell>
		<c:if test="${ lockOrder.lock_user_name !=null && lockOrder.lock_user_realname !=null}">
            ${lockOrder.lock_user_name }/${lockOrder.lock_user_realname }
        </c:if>
		</grid:cell>
		<grid:cell><c:if test="${ lockOrder.is_work_custom =='1'}"> 是</c:if></grid:cell>
		<grid:cell><a href="javascript:void(0);" id="queryUserOne" style="margin-right:10px;" class="graybtn1" 
		order_id="${lockOrder.order_id}" onclick="queryUserOne(this)"><span>订单分配</span></a></grid:cell>
	</grid:body>
</grid:grid>
</form>
<div id="queryUserListDlg"></div>
<div id="queryCountyListDlg"></div>
<script type="text/javascript">
$(function (){
	Eop.Dialog.init({id:"queryUserListDlg",modal:true,title:"订单分配", width:"800px"});
	Eop.Dialog.init({id:"queryCountyListDlg",modal:true,title:"订单分配", width:"800px"});
	$("#queryUser").click(function(){
		 
		  var len = $("[name='lockOrderChk']:checked").length;
		  if(len==0){
			  alert("请选择要分配的锁定订单");
			  return false;
		  }
		  if(window.confirm("确认分配选中的锁定订单吗?")){
			  var order_idArr = [];
			  $("[name='lockOrderChk']:checked").each(function(){
				 var order_id = $(this).val();
				 order_idArr.push(order_id);
			  });
			  var lockOrderIdStr = order_idArr.join(",");
			  checkFlowTrace(lockOrderIdStr);
			  
			  
			 
		  }
			
		 
	  });
	
	$("#resetBtn").click(function (){
		document.getElementById("username").value = "";
		document.getElementById("lock_user_id").value = "";
	});
	
	
});
function showUserSelect(lockOrderIdStr){
	 Eop.Dialog.open("queryUserListDlg");
	 
	 var url= ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=allot&lockOrderIdStr="+lockOrderIdStr;
	 
	  
	    $("#queryUserListDlg").load(url,{},function(){});
	    
	    
}
function queryUserOne(e){
	var lockOrderIdStr =e.getAttribute("order_id");
	  if(window.confirm("确认分配选中的锁定订单吗?")){
		  
		  showUserSelect(lockOrderIdStr);
		  
		 
	  }
	  
	    
}

$("#checkAlls").bind("click", function() {
	$("input[type=checkbox][name=lockOrderChk]").trigger("click");
});

function queryUserId(){
		Eop.Dialog.open("queryUserListDlg");
		lock_user_id = $("#lock_user_id").val();
		var url= ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=query";
		    $("#queryUserListDlg").load(url,{},function(){});
	}   
function querycountyId(){
	Eop.Dialog.open("queryCountyListDlg");
	lock_user_id = $("#lock_user_id").val();
	var url= ctx+"/shop/admin/ordAuto!queryCounty.do?ajax=yes";
	    $("#queryCountyListDlg").load(url,{},function(){});
}	
function checkFlowTrace(lockOrderIdStr){	
	$.ajax({
		type : "post",
		async : false,
		url:"ordAuto!checkFlowTrace.do?ajax=yes",
		data : {
			"lockOrderIdStr" : lockOrderIdStr
		},
		dataType : "json",
		success : function(data) {
			if(data.result==0){
				showUserSelect(lockOrderIdStr);
				
			}else{
				alert(data.msg);
				 return;
			}
			 
		}
	});
}  

</script>