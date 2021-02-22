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
	<form action="goodsN!listInbox.do" method="post">
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<th>
					<label class="text">
						商品名称:
					</label>
				</th>
				<td>
					<input type="text" style="width: 130px" name="params.name"
						value="${name }" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						商品编号:
					</label>
				</th>
				<td>
					<input type="text" style="width: 130px" name="params.sn" value="${sn }" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						商品类别:
					</label>
				</th>
				<td>
					<select class="ipttxt" id="catid" name="params.catid"
						class="inputstyle">
						<option value="">
							--请选择--
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						品牌:
					</label>
				</th>
				<td>
					<select class="ipttxt" id="brand_id" name="params.brand_id" class="inputstyle">
						<option value="">
							--请选择--
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						标签:
					</label>
				</th>
				<td>
					<select class="ipttxt" id="tagids" name="params.tagids" class="inputstyle">
						<option value="">
							--请选择--
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						是否有效:
					</label>
				</th>
				<td>
					<input type="radio" checked="checked" name="params.market_enable"
						value="2" />
					全部
					<input type="radio" name="params.market_enable" value="1" />
					是
					<input type="radio" name="params.market_enable" value="0" />
					否
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
