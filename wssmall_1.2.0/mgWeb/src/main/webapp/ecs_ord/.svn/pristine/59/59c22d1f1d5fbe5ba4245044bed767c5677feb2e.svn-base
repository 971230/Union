<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.searchformDiv table th {
	width: 90px;
}
</style>
<div class="searchBx">
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
             
             <tr>
                 <th>处理日期：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                   </td>
                   
                   <th>订单来源：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){
                			/* $("#order_from_vp,#order_from_a").bind("click",function(){
                				$("#order_from_dv").show();
                			});
                			$("#order_from_cancel1,#order_from_cancel2").bind("click",function(){
                    			$("#order_from_dv").hide();
                    		}); */
                			
                		////XMJ修改开始
                    		$("#order_from_vp,#order_from_a,#order_from_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                    	        $("#order_from_dv").show();    //显示DIV
                    	        e.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(){    
                    	        $("#order_from_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#order_from_cancel1,#order_from_cancel2").bind("click",function(e){
                    		   $("#order_from_dv").hide();
                               e.stopPropagation();//阻止事件冒泡
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
                            			<li name="order_from_li"><input type="checkbox" name="order_from" value="${of.pkey }" c_name="${of.pname }"><span name="order_from_span">${of.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                   
                <td>
                <th>导出状态：</th>
	             		<td>
                		<select name="params.sign_status" class="ipt_new" style="width:138px;">
	                       <option value="1">全部</option>
	                       <option value="2">未处理</option>
	                       <option value="3">已处理</option>
	                       <option value="4">已发货</option>
		               </select>
		               <script type="text/javascript">
		                  $("[name='params.sign_status'] option[value='${params.sign_status}']").attr("selected","selected");
						</script>
						                    
	               
	              </td> 
	              <td></td>
	              <td></td>
             </tr>
             
             <tr>
               <th>处理人选：</th>
               <td>
               <input type="text" class="ipt_new" style="width:138px;" id="username" name="username" value="${username }" onfocus="queryUserId()"/>
			   <input type="hidden" class="ipt_new" style="width:138px;" id="lock_user_id" name="params.lock_user_id" value="${params.lock_user_id }" />
               <a href="javascript:void(0);" id="resetBtn" class="dobtn" style="margin-right:10px;">清除</a>
               </td>
               <th></th>
               <td>
				     <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		            &nbsp; &nbsp;<input class="comBtn" type="button" name="excel" id="excelOrd" value="导出" style="margin-right:10px;"/>
               </td>
             </tr>
             
             </tbody>
            </table>
        </div>
        
        <div id="queryUserListDlg"></div>
<script type="text/javascript">

$(function (){
	Eop.Dialog.init({id:"queryUserListDlg",modal:true,title:"营业日报", width:"800px"});
	
	$("#resetBtn").click(function (){
		document.getElementById("username").value = "";
		document.getElementById("lock_user_id").value = "";
	});
	
	
});

	function queryUserId(){
		Eop.Dialog.open("queryUserListDlg");
		lock_user_id = $("#lock_user_id").val();
		var url= ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=query";
		    $("#queryUserListDlg").load(url,{},function(){});
	}  
	
</script>   