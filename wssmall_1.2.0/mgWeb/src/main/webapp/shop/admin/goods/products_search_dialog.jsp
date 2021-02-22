<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin: 10px
}
</style>
<div class="input">
	<form action="goods!productListInbox.do" method="post">
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<th>
					<label class="text">
						货品状态:
					</label>
				</th>
				<td>
					<select  class="ipttxt"  style="width:100px" id="market_enable" name="market_enable">
						<option value="">--请选择--</option>
						<option value="1">正常</option>
						<option value="0">停用</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						SKU:
					</label>
				</th>
				<td>
					<input type="text" style="width: 130px" name="sku" value="${sku }" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						发布状态:
					</label>
				</th>
				<td>
					<select  class="ipttxt"  style="width:100px" id="product_state">
						<option value="">--请选择--</option>
						<option value="1">已发布</option>
						<option value="0">未发布</option>
					</select>
				</td>
			</tr>
			<!--${goodstype} ${goodstag}  -->
			<div class="submitlist" align="center">
				<table>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<input type="submit" value=" 搜索 " class="graybtn1" style="margin-right:10px;">
						</td>
					</tr>
				</table>
			</div>

		</table>
	</form>
</div>


<script type="text/javascript">
var goodsSearchDiglog={
	init:{
	},
	/**
	 * 加载一级商品类型
	 * */
	loadFirstNode : function() {
		var me = this;
		$.ajax({
			url : "cat!findCatListByParentId.do?ajax=yes&parentid=0",
			type : "post",
			dataType : "json",
			success : function(result) {
				if(result.length>0){
					for(var i=0;i<result.length;i++){
						var temp="<option value="+result[i]['cat_id']+">"+result[i]['name']+"</option>";
						$("#catid").append(temp);
					}
				}
			}
		});
	},
	/**
	 * 加载商品品牌
	 * */
	loadBrand : function(catid) {
		var me = this;
		$.ajax({
			url : "cat!findBrandByCatId.do?ajax=yes&catid="+catid,
			type : "post",
			dataType : "json",
			success : function(result) {
				$("#brand_id").empty();
				var temp="<option value=''>--请选择--</option>";
				$("#brand_id").append(temp);
				if(result.length>0){
					for(var i=0;i<result.length;i++){
						temp="<option value="+result[i]['brand_id']+">"+result[i]['name']+"</option>";
						$("#brand_id").append(temp);
					}
				}
			}
		});
	},
	/**
	 * 加载商品标签
	 * */
	loadTag : function() {
		var me = this;
		$.ajax({
			url : "cat!findTagList.do?ajax=yes",
			type : "post",
			dataType : "json",
			success : function(result) {
				if(result.length>0){
					for(var i=0;i<result.length;i++){
						var temp="<option value="+result[i]['tag_id']+">"+result[i]['tag_name']+"</option>";
						$("#tagids").append(temp);
					}
				}
			}
		});
	}
}
$(function(){
	goodsSearchDiglog.loadFirstNode();
	goodsSearchDiglog.loadTag();
	
	$("#catid").change(function(){
		var catid=$(this).val();
		goodsSearchDiglog.loadBrand(catid);
	});
	
});

</script>
