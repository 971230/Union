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
                   <th>外部订单号：</th>
                <td>
                <input type="text" name="params.out_tid" value="${params.out_tid }" style="width:150px;" class="ipt_new">
                </td>
                   <th>内部订单号：</th>
                <td>
                <input type="text" name="params.order_id" value="${params.order_id }" style="width:150px;" class="ipt_new">
                </td>
                 <th>操作类型：</th>
		          <td>
		           <html:selectdict  name="params.type"  appen_options="<option value=''>---请选择---</option>"  curr_val="${params.type}"   attr_code="DC_RELEASE_FAIL_INF" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:138px;"></html:selectdict>
		          </td>
               </tr>
               
               <tr>
                <th>终端串号/号码：</th>
                <td>
                <input type="text" name="params.phone_num" value="${params.phone_num }" style="width:150px;" class="ipt_new">            
                </td>
               <th>预占工号：</th>
                <td>
                <input type="text" name="params.lock_user_id" value="${params.lock_user_id }" style="width:150px;" class="ipt_new">
                </td>
		         <th>首次释放失败时间：</th>
                 <td>
                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                 </td>
               </tr>
               <tr>
                 <th>预占关键字：</th>
	                <td>
	                <input type="text" name="params.prokey" value="${params.prokey }" style="width:150px;" class="ipt_new">
	                </td>
	                <th>处理结果：</th>
	                <td>
	                <html:selectdict  name="params.deal_type"  appen_options="<option value='-1'>---请选择---</option>"  curr_val="${params.deal_type}"   attr_code="DC_RELEASE_IS_DEAL" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:138px;"></html:selectdict>
	                </td>
	                <th></th>
	                <td>   
				     <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
				     </td>
               </tr>
             </tbody>
            </table>
        </div>

<script type="text/javascript">
	
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