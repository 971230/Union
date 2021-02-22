<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="searchBx">
	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
		<tbody id="tbody_A">
			<tr>
				<th>外部单号：</th>
				<td>
					<input type="text" name="params.out_tid" value="${params.out_tid }" style="width:138px;" class="ipt_new">
					<input type="hidden" name="params.query_btn_flag" value="yes" />
				</td>
				<th>内部单号：</th>
				<td>
					<input type="text" name="params.order_id" value="${params.order_id }" style="width:138px;" class="ipt_new">
				</td>
				<th>订单来源：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){

												////XMJ修改开始
                    		$("#order_from_vp,#order_from_a,#order_from_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
                    	        $("#order_from_dv").show();    //显示DIV
                    	        e1.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(e1){    
                    	        $("#order_from_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#order_from_cancel1,#order_from_cancel2").bind("click",function(e1){
                    		   $("#order_from_dv").hide();
                               e1.stopPropagation();//阻止事件冒泡
                    		}); 
                    	   ////XMJ修改结束                			
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
				<th>创建时间：</th>
				<td>
					<input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
					<input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
				</td>
			</tr>
		</tbody>
		<tbody id="tbody_A">
			<tr>
				<th>选择城市：</th>
				<td>
					<script type="text/javascript">
					$(function(){
						$("#region_ivp,#region_a,#region_div").bind("click",function(e){
							$("#region_div").show();
							e.stopPropagation();
						});
						
						$(document).bind("click",function(){
							$("#region_div").hide();
						});
						
						$("#regionCancel,#regionCancel2").bind("click",function(e){
							$("#region_div").hide();
							e.stopPropagation();
						});
						
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
				<th>商品名称：</th>
				<td>
					<input type="text" name="params.goods_name" value="${params.goods_name }" style="width:138px;" class="ipt_new">
				</td>
				
				<th>审核状态：</th>
				<td colspan="2">
					<select name="params.handle_status" class="ipt_new" style="width:138px;">
                      <option value="">---请选择---</option>
                      <option value="0" <c:if test="${params.handle_status eq '0' }">selected</c:if> >未审核</option>
                      <option value="1" <c:if test="${params.handle_status eq '1' }">selected</c:if> >审核失败</option>
	               	</select>
					<a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<script type="text/javascript">
$(function(){
	$("#query_order_s").bind("click",function(){
		$.Loading.show("正在加载所需内容，请稍侯...");
		$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showBatchPreOrderList.do").submit();
	});	
});
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