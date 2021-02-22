<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<div id="recover_pro_win" class="input" style="">
   <form id="recover_form" action="">
	<table class="form-table" style="width: 100%; float: left"
		id='base_table'>
		<input type="hidden" name="recover.sku"  />
		<input type="hidden" name="recover.house_id"  />
		<input type="hidden" name="recover.virtual_house_id"  />
		<input type="hidden" name="recover.org_id"  />
		<input type="hidden" name="recover.product_id"  />
		<input type="hidden" name="recover.goods_id"  />
		<tr>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>虚拟仓：</label>
			</td>
			<td>
				<input type="text" name="recover.v_house_name" class="top_up_ipt" readonly="readonly" />
			</td>
			<td nowrap="nowrap">
			    <label id="recover_pro_or_goods_name"></label>
			</td>
			<td>
				<input type="text" id="" name="recover.name" class="top_up_ipt"  readonly="readonly"/>
			</td>
			<td nowrap="nowrap">
			     <label><span class="red">*</span>可回收数量：</label>
			</td>
			<td>
				<input type="text" id="" name="recover.inventory_num" class="top_up_ipt"  readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>虚拟仓类型：</label>
			</td>
			<td>
				<select style="width:150px;" name="recover.is_share" disabled="disabled">
				   <option value="1" >共享仓</option>
				   <option value="0" >独享仓</option>
				</select>
			</td>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>商城：</label>
			</td>
			<td>
				<input type="text"  name="recover.org_name" class="top_up_ipt" readonly="readonly" />
			</td>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>物理仓：</label>
			</td>
			<td>
				<input type="text"  name="recover.house_name" class="top_up_ipt" readonly="readonly" />
			</td>
		</tr> 
		<tr >
			<td  nowrap="nowrap">
			    <label><span class="red">*</span>回收数量：</label>
			</td>
			<td>
				<input type="text" name="inventory_num" class="top_up_ipt"  readonly="readonly" />
			</td>
		</tr>
		<tr >
		    <td colspan="2"></td>
			<td colspan="2" style="text-align:center;">
                <input  type="button"  value="库存回收" class="greenbtnbig" id="goodsInventoryRecoverBtn"/>
			</td>
			<td colspan="2"><span style="color:red;">注：必须按最大数量回收后再分配</span></td>
		</tr>
	</table>
  </form>
</div>
<div id="sel_virtual_div"></div>
<script type="text/javascript">
var recoverWin = {
		
		init:function(){
			var self =this;
			if(type == 'product'){
				$("#recover_pro_or_goods_name").html('<span class="red">*</span>货品名称：');
			}else{
				$("#recover_pro_or_goods_name").html('<span class="red">*</span>商品名称：');
			}
			$("#goodsInventoryRecoverBtn").click(function(){
				self.recover();
			});
		},
		//保存分配
		recover:function(){
			var self =this;
			var params = $("#recover_form").serialize();
			params +="&recover.type="+type;
			
			var is_share = $("#recover_pro_win").find("select[name='recover.is_share']").val();
			params +="&recover.is_share="+is_share;
			
			var url = "warehouseAssign!recycleInventoryApply.do?ajax=yes&"+params;

			$.ajax({
				async:false,
				type : "POST",
				dataType : 'json',
				url : url,
				success : function(result) {
					if(result){
				    	if(result.result=='true'){
				    		alert("回收成功!");
				    		if(type == "product"){
				    			WarehouseProduct.showAssignedPro();
				    			Eop.Dialog.close("recover_pro_div");
				    			
				    		}else if(type == "goods"){
				    			WarehouseGoods.showAssignedGoods();
				    			Eop.Dialog.close("recover_goods_div");
				    		}
				    		
				    	}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert("出错了");
				}
			}); 
		}
};
recoverWin.init();
</script>

