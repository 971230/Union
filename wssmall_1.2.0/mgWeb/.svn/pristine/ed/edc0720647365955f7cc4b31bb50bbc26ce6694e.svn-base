<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th><input type="button" id="add_commounity_goods_real" name="add_commounity_goods_real" community_code="${activity.community_code}" value="添加商品关系" class="comBtn" /></th>
				</tr>
			</tbody>
		</table>
	</div>
<div class="grid">
	 <form method="POST" id="goodscomm_list_form"> 
		<grid:grid from="webpage" formId="goodscomm_list_form" ajax="yes">
			<grid:header>
				<grid:cell sort="name" width="50px">序号</grid:cell>
				<grid:cell sort="name" width="250px">商品SKU</grid:cell>
				<grid:cell sort="enable">商品名称</grid:cell>
				<grid:cell width="200px">操作</grid:cell>
			</grid:header>

			<grid:body item="communityGoods">
				<grid:cell>${index+1 } </grid:cell>
				<grid:cell>${communityGoods.sku } </grid:cell>
				<grid:cell>${communityGoods.name } </grid:cell>
				<grid:cell>
				  <img src="images/transparent.gif" id="del_commounity_goods_real" name="del_commounity_goods_real" class="delete" goods_id="${communityGoods.goods_id }" commounity_code="${communityGoods.community_code }">
				  <%-- <input type="button" id="del_commounity_goods_real" name="del_commounity_goods_real" goods_id="${communityGoods.goods_id }" commounity_code="${communityGoods.community_code }" value ="删除关系"  /> --%> 
				</grid:cell>
			</grid:body>
		</grid:grid>
	 </form> 
</div>
<div id="good_dialog"></div>
<script>
var esc_commountiygoods = {
		init : function() {
			esc_commountiygoods.community_good_relation();
			esc_commountiygoods.del_commounity_goods();
		},
		community_good_relation : function(){
			$("#add_commounity_goods_real").unbind("click").bind("click",function(){
				var community_code = $(this).attr("community_code");
				var url = ctx + "/shop/admin/communityAction!getGoodsList.do?ajax=yes&activity.community_code="+community_code;
				Eop.Dialog.open("good_dialog");
				$("#good_dialog").load(url,{},function(){});
			});
		},
		del_commounity_goods : function(){
			$("img[id='del_commounity_goods_real']").unbind("click").bind("click",function(){	
				var goods_id = $(this).attr("goods_id");
				var commounity_code = $(this).attr("commounity_code");
				var datas = "{\"goods_id\":\""+goods_id+"\"," +
				"\"community_code\":\""+commounity_code+
				"\"}";
				$.Loading.show('正在执行，请稍侯...');
				$.ajax({
					type : "post",
					async : false,
					url : "communityAction!del_commounity_goods_real.do?ajax=yes",
					data : {mapParams:datas},
					dataType : "json",
					success : function(rsp) {
						$.Loading.hide();
						alert(rsp.message);
						var url = ctx + "/shop/admin/communityAction!toGoodsCommunity.do?ajax=yes&activity.community_code="+commounity_code;
					    Cmp.ajaxSubmit('goodscomm_list_form','community_good_relation_dialog',url,{},function(){
						}); 
						
					},
					error:function(e){$.Loading.hide(); alert("删除失败！");}
				}); 
				
			}) 
		}
	}	
$(function() {
	Eop.Dialog.init({id:"good_dialog",modal:false,title:"商品选择",height:'300px',width:'850px'});
	esc_commountiygoods.init();
});

</script>