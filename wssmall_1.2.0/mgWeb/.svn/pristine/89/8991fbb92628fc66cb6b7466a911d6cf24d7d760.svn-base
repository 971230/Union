<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">添加号段</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="ciudad!save.do" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
		    <tr>
			    <th>
				    <label class="text"><span class='red'>*</span>号段：</label>
				</th>
				<td>
				    <input type="text" id="seg_no" name="ciudad.seg_no" dataType="int" maxlength="7" class="ipttxt" value="${ciudad.seg_no}" required="true" />
				</td>
			</tr>
			
			<tr>
				<th>
				    <label class="text"><span class='red'>*</span>归属地市：</label>
				</th>
				<td>
				    <html:selectdict name="ciudad.region_id" curr_val="${ciudad.region_id }"
	    	              attr_code="DC_COMMON_REGION_GUANGDONG" style="width:150px" 
					      appen_options='<option value="">--请选择--</option>'></html:selectdict>
				</td>
			</tr>

		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
					<input type="hidden" name="ciudad.seg_id" value="${ciudad.seg_id}"> 
					<input type="submit" id="btn"  value=" 确  定 " class="greenbtnbig" style="cursor:pointer;" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>

<script>

    $("form.validate").validate();

</script>