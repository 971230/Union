<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<form method="post" id='goods_list_form' action='communityAction!getGoodsList.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr> 
				    <th>商品名称：</th>
				    <td><input type="text" class="ipttxt"  name="goods_name" value="${goods_name}" class="searchipt" /></td>
				    <input type="hidden" class="ipttxt" id="activity.community_code" name="activity.community_code" value="${activity.community_code}" class="searchipt" />
					<th><input class="comBtn" type="button" name="FileSearchButton" id="FileSearchButton" value="搜索" style="margin-right:10px;" /></th>
					<th><input type="button" id="sure_commounity_goods_real" name="sure_commounity_goods_real" value="确定" class="comBtn" /></th>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="grid">
	<form method="POST" id="goods_list_form">
		<grid:grid from="webpage" ajax="yes"><!-- formId="goods_list_form" -->
			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="name" width="50px">序号</grid:cell>
				<grid:cell sort="name" width="250px">商品SKU</grid:cell>
				<grid:cell sort="enable">商品名称</grid:cell>
			</grid:header>
			<grid:body item="goods">
				<grid:cell>
					<input type="checkbox" name="id" goods_id="${goods.goods_id }" />
				</grid:cell>
				<grid:cell>${index+1 } </grid:cell>
				<grid:cell>${goods.sku } </grid:cell>
				<grid:cell>${goods.name } </grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
<script>
var esc_goods = {
		init : function() {
			esc_goods.good_search();
			esc_goods.add_commounity_goods();
		},
		good_search : function(){
			$("#FileSearchButton").click(function(){
				var url=ctx+"/shop/admin/communityAction!getGoodsList.do?ajax=yes";
				/* Cmp.ajaxSubmit('goodscomm_list_form','community_good_relation_dialog',url,{},function(){
				});  */
				Cmp.ajaxSubmit('goods_list_form','good_dialog',url,{},function(){
				});
			}) 
		},
		add_commounity_goods : function(){
			$("input[id='sure_commounity_goods_real']").unbind("click").bind("click",function(){	
				var datas = "[";
				var i = 0;
				var objs = $("input[type='checkbox'][name='id']:checked");
				var len = objs.length;
				if(len=='0'){
					alert("请【勾选】需要关联的商品！")
					return false;
				}
				//var community_code = $(this).attr("community_code");
				var community_code = $("input[type='hidden'][id='activity.community_code']").val();
				$("input[type='checkbox'][name='id']:checked").each(function() {	
					var goods_id = $(this).attr("goods_id");
					if (i == len - 1) {
						datas += "{\"community_code\":\""+community_code+"\"," +
								"\"goods_id\":\""+goods_id+
								"\"}";
					} else {
						datas += "{\"community_code\":\""+community_code+"\"," +
								"\"goods_id\":\""+goods_id+
								"\"},";
					}
					i ++;
				});
				datas += "]";
			    $.Loading.show('正在执行，请稍侯...');
				$.ajax({
					type : "post",
					async : false,
					url : "communityAction!add_commounity_goods_real.do?ajax=yes",
					data : {listParams:datas},
					dataType : "json",
					success : function(rsp) {
						$.Loading.hide();
						alert(rsp.message);
						//刷新商品
					    /* var url = ctx + "/shop/admin/communityAction!getGoodsList.do?ajax=yes&activity.community_code="+community_code;
					    Cmp.ajaxSubmit('goods_list_form','good_dialog',url,{},function(){
						}); */ 
						Eop.Dialog.close("good_dialog");
					    //刷新商品与小区关系
						var url = ctx + "/shop/admin/communityAction!toGoodsCommunity.do?ajax=yes&activity.community_code="+community_code;
						Cmp.ajaxSubmit('goodscomm_list_form','community_good_relation_dialog',url,{},function(){
						});  
						
					},
					error:function(e){$.Loading.hide(); alert("添加失败！");}
				}); 
				
			}) 
		}
}
$(function() {
	esc_goods.init();
});

</script>