<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<style type="text/css">
	 table th {
	    color: #666666;
	    padding-right: 3px;
	    text-align: right;
	    vertical-align: middle;
	    width: 85px;
	}
	</style>
 
	<script type="text/javascript">
		function uploadGoods(){
			 $.ajax({
		            url: "goods!uploadSuccess.do?ajax=yes,
		            type: "json",
		            success: function (json) {
		                json = eval("(" + json + ")")
		                if (json.result == -1) {
		                    $("#price_notice_error").empty();
		                    $("#price_notice_error").text(json.message);
		                    return;
		                }
		                if (json.result == 1) {
		                	var newDiv = document.getElementById("sale-notice");
		                	newDiv.style.display="none";
		                	document.body.removeChild(docEle("mask"));
		                	location.reload() ;
		                }
		            },
		            error: function () {
		            }
		        });
		}
	</script>
 <div class="searchformDiv" style="background-color: white;">
<table  border="0" cellspacing="0" cellpadding="0" >
<tbody>
<tr>
	 <form id="form4" method="post" action="batchGoodAction!getModel.do">
		<th>请选择模板：</th>
		<td><select  class="ipttxt"  id="model_choose"  name = "model_choose" >
							<option  value="2">--请选择--</option>
							<option  value="1">电脑</option>
						    <option  value="0">手机</option>
		</select>
		</td>
		<td><input type="submit" value="提交" style="margin-right:10px;"  class="comBtn" /></td>
	</form>
</tr>
<tr>	
	  <form method="post" action="goods!batchAddDetails.do" class="validate" id="goodsForm" enctype="multipart/form-data">
    	
				<th>商品详情导入:</th>
				<td> <input type="file" name="fileName" id="fileName" size="30" />  </td>
				
				<td>	<input type="submit" value="导入" id="sure" name="sure" style="margin-right:10px;"  class="comBtn"></td>
</form>
</tr>			
</tbody>
</table>
</div>			
  </form>		

