<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" onclick="show_div('div_refreshOrder')" class="selected"><span class="word">刷新订单下单环境</span><span class="bg"></span></li>
                       <li onclick="show_div('div_refreshLimitCount')" class="selected"><span class="word">刷新分流到解耦系统的限制数量</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>  
<div class="input" id="div_refreshOrder" >	
	<form  class="validate" method="post" name="refreshOrderform" id="refreshOrderform">
	<table class="form-table">
		<tbody>
			<tr>
				<td style="text-align: center; width: 15%;" >订单号</td>
				<td style="text-align: left;">
					<textarea  id="order_id" name="order_id" rows="4" cols="50"></textarea>
					<red>*多个订单用逗号分割</red>
				</td>
			</tr>
		</tbody>
	</table>
		<div class="submitlist" align="center">
		  <table>
			<tr>
			 <td> 
		     	<input  type="button" id="refreshOrderfirm"	 value=" 确  定 "  class="submitBtn"  />
		     </td>
		   </tr>
		  </table>
		</div> 
	</form>
</div>

<div class="input" id="div_refreshLimitCount" >	
<form  class="validate" method="post" name="qryCountform" id="qryCountform" ></form>
	<form  class="validate" method="post" name="refreshLimitCount" id="refreshLimitCount">
	<table class="form-table">
		<tbody>
			<tr>
				<td style="text-align: center; width: 15%;" >设置限流订单数</td>
				<td style="text-align: left;">
					<input  type="text" id="limit_count"	name="limit_count" type="text" class="ipttxt"  />
				</td>
			</tr>
			<tr>
				<td style="text-align: center; width: 15%;" >设置已分流订单数</td>
				<td style="text-align: left;">
					<input  type="text" id="execed_count"	 name="execed_count" type="text" class="ipttxt"  />
				</td>
			</tr>
			<tr>
				<td style="text-align: center; width: 15%;" ><span id="limit_count_span" style="color: red" ></span></td>
				
			</tr>
		</tbody>
	</table>
		<div class="submitlist" align="center">
		  <table>
			<tr>
			 <td> 
		     	<input  type="button" id="refreshLimitCountConfirm"	 value=" 确  定 "  class="submitBtn"  />
		     </td>
		   </tr>
		  </table>
		</div> 
	</form>
</div>
<script>
$(document).ready(function(){ 
	init();
	$("#refreshLimitCountConfirm").bind("click",function(){
		var limit_count = $("#limit_count").val();
		var execed_count = $("#execed_count").val();
		if((limit_count ==null || limit_count=="") &&(execed_count ==null || execed_count=="")){
			alert("请输入值！");
			return ;
		}
		if(limit_count !=null && limit_count!=""&&isNaN(limit_count)){
			alert("请输入数字！");
			return ;
		}
		if(execed_count !=null && execed_count!=""&&isNaN(execed_count)){
			alert("请输入数字！");
			return ;
		}
		 var url =app_path +"/core/admin/gray/rGrategyAdmin!setLimitCount.do?ajax=yes";
	        Cmp.ajaxSubmit('refreshLimitCount', '', url, {}, function(reply){
	        	 alert(reply.message);
	        	 this.qryCount();
		        }, 'json');
	});
});
$(function(){
	$("#refreshOrderfirm").bind("click",function(){
		var order_id = $("#order_id").val();
		if(order_id ==null || order_id==""){
			alert("订单号不能为空！");
			return ;
		}
		 var url =app_path + "/shop/admin/grayDataSyncAction!doRefreshOrderEnv.do?ajax=yes";
	        Cmp.ajaxSubmit('refreshOrderform', '', url, {}, function(reply){
	        	 alert(reply.message);
		        }, 'json');
	});
});
function  init(){
	$(".input").hide();
	$("#div_refreshOrder").show();
}
function  show_div(div_id){
	$(".input").hide();
	$("#"+div_id).show();
	if(div_id == 'div_refreshLimitCount'){
		this.qryCount();
	}
}
function qryCount(){
	var self =this;
    	var url =  app_path +"/core/admin/gray/rGrategyAdmin!getLimitCount.do?ajax=yes";
	    Cmp.ajaxSubmit('qryCountform', '', url, {},  function jsonback(responseText) { // json回调函数
			if (responseText.result == 1) {
				$("#limit_count_span").text(responseText.message);
				//window.location.reload();
			}
			if (responseText.result == 0) {
				$("#limit_count_span").text("查询限流数量失败");
			}
		}, 'json');
}
</script>
<div id="rfsResult">
</div>
