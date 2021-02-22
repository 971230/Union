<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String exception_type = request.getParameter("exception_type");
	request.setAttribute("exception_type", exception_type);
%>
<div class="searchBx">
        	<!-- <a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
        	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a> -->
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
                <tr>
                
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
	                 <th>外部单号：</th>
		                <td>
		                	<input type="text" name="params.out_tid" value="${params.out_tid }" style="width:138px;" class="ipt_new">
		                </td>
		              <th>内部单号：</th>
		                <td><input type="text" name="params.order_id" value="${params.order_id }" style="width:138px;" class="ipt_new"></td>
		                
		                
			    </td>
                </tr>
	            <tr>
	              <th>处理状态：</th>
	                <td>
	                	<select name="params.status" style="width:154px;height:22px;" class='ipt_new'>
	                		<c:forEach items="${deal_status }" var="ds">
	                			<c:if test="${ds.pkey != 4 }">
	                				<option value="${ds.pkey }" ${ds.pkey==params.status?'selected':'' }>${ds.pname }</option>
	                			</c:if>
	                		</c:forEach>
						</select>
	                </td>
	            	
	            	<th>外呼类型：</th>
		             <td>
		        <script type="text/javascript">
                	$(function(){
												////XMJ修改开始
                		$("#outcall_type_vp,#outcall_type_a,#outcall_type_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#outcall_type_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#outcall_type_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#outcall_type_cancel1,#outcall_type_cancel2").bind("click",function(e){
                		   $("#outcall_type_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
                	   ////XMJ修改结束                		
                		$("#outcall_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=outcall_type]").attr("checked","checked");
                				$("#outcall_type_dv li").addClass("curr");
                			}else{
                				$("input[name=outcall_type]").removeAttr("checked");
                				$("#outcall_type_dv li").removeClass("curr");
                			}
                			selectOutCallType();
                		});
                	
                		
                		$("input[name=outcall_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectOutCallType();
                		});
                		
                		initCheckBox("outcall_type_hivp","outcall_type");
                		
                	});
                	
                	function selectOutCallType(){
            			var regions = $("input[name=outcall_type]:checked");
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
            			$("#outcall_type_vp").val(regionNames);
            			$("#outcall_type_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.outcall_type_c" id="outcall_type_vp" value="${params.outcall_type_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.outcall_type" value="${params.outcall_type }" id="outcall_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="outcall_type_a"></a>
                        <div class="selOp" style="display:none;" id="outcall_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="outcall_type_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="outcall_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="outcall_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${outcallTypeList }" var="pt">
                            			<li name="outcall_type_li"><input type="checkbox" name="outcall_type" value="${pt.value }" c_name="${pt.value_desc }"><span name="outcall_type_span">${pt.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
		              </td>
	            	
		            <th></th>
	              	<td><input type="hidden" name="query_list_by_bt" value="Y" /><!-- 单点登录用到 -->
						<a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
	            </tr>
	
	            </tbody>
                
            </table>
        </div>

<script type="text/javascript">
	$(function(){
		/* $("#param_tb_F").show(); */
		$("#hide_params_tb").show();
		$("#show_params_tb").show();
		/* var count = -2;
		$("#hide_params_tb").bind("click",function(){
			if(count==1){
				$("#param_tb_F").hide();
				count = 2;
			}else{
				$("#tbody_A").hide();
				count = -1; 
				$("#hide_params_tb").hide();
				$("#show_params_tb").show();
			}
			
		});
		$("#show_params_tb").bind("click",function(){
			if(count==-1){
				$("#tbody_A").show();
				count =-2;
			}else{
				$("#param_tb_F").show();
				count = 1;
				$("#hide_params_tb").show();
				$("#show_params_tb").hide();
			}
			
		}); */
		
		$("#query_order_s").bind("click",function(){
			$.Loading.show("正在加载所需内容，请稍侯...");
			$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showCallOutOrderList.do").submit();
		});
		
		//$("#order_from_dv").aramsDiv();
		$("#boxCodeId").bind("blur", function() {
			var boxCode = $("#boxCodeId").val();
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!getOrdByBoxIdFromWMS.do?ajax=yes&boxCode="+boxCode,
				data : {},
				dataType : "json",
				success : function(data) {
					if (data!= undefined && data.result=="0") {
						$("input[name='params.order_id']").val(data.message);
						$("#query_order_s").click();
					}
				}
			});
		});
	});
	
	(function($){
		$.fn.aramsDiv = function(){
			//this.isOut = true;
			var $this = $(this);
			$this.bind("mouseout",function(){
				//isOut = false;
				//alert(111);
				//$this.hide();
				//console.log("===mouseout===========");
			});
			$(this).bind("mouseover",function(){
				//isOut = true;
				//console.log("===mouseover===========");
			});
		};
	})(jQuery);
</script>        