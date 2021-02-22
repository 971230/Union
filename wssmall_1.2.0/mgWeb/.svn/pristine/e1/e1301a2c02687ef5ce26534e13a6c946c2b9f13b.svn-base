<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="js/Brand.js"></script>
<div class="input">
	<div class="main-div">
		 <form class="validate" method="post" action="brand!saveEdit.do" name="theForm" id="theForm"  enctype="multipart/form-data">
		 <input type="hidden" name="brand.brand_id" value="${brand.brand_id }" />
		  <input type="hidden" name="oldlogo" value="${brand.logo }" />
		   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
		     <tr>
		       <th><label class="text">品牌名称</label></th>
		       <td><input type="text" class="ipttxt"  name="brand.name" id="name" maxlength="60" value="${brand.name }"   dataType="string" required="true" /></td>
		      </tr>
		      <tr>
				<th><label class="text">品牌编码:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.brand_code" id="brand_code" maxlength="60"
					value="${brand.brand_code }" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">外部品牌编码:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.brand_outer_code" id="brand_outer_code" maxlength="60"
					value="${brand.brand_outer_code }" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">总部品牌编码:</label></th>
				<td><input type="text" class="ipttxt"  name="brand.brand_group_code" id="brand_group_code" maxlength="60"
					value="${brand.brand_group_code }" dataType="string" required="true" /></td>
			</tr>
		     <tr >
		       <th><label class="text">品牌网址</label></th>
		       <td><input type="text" class="ipttxt"  name="brand.url" id="url" maxlength="60" size="40" value="${brand.url }"   dataType="url"/>
		       以http://开头
		       </td>
		      </tr>
<%--		     <tr>
		       <th><label class="text">品牌LOGO</label></th>
		       <td><input type="file" name="logo" id="logo" size="45"/>
		      	 <input type="hidden" name="brand.logo" value="${brand.logo }" />
		           <span class="notice-span"  id="warn_brandlogo">请上传图片，做为品牌的LOGO</span></td>
		     </tr> --%>
		     
		     <c:if test="${brand.logo!=null }">
			     <tr>
			       <th>&nbsp;</th>
			       <td> 
			       <img src="${brand.logo }"  /></td>
			     </tr>
		     </c:if>
		      <tr>
				<th><label class="text">机型:</label></th>
				<td><a class="back"><span id="search_machine">查看</span></a></td>
			</tr>    
		     <tr >
		       <th><label class="text">详细说明</label></th>
		       <td>
		        <textarea id="brief" name="brand.brief"><c:out value="${brand.brief }" escapeXml="false"></c:out></textarea>
				</td>
		     </tr>
		   </table>
<div class="submitlist" align="center">
 <table>
 <tr>
   <th></th>
   <td >
     <input  type="button" value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>		   
		 </form>
	</div>
</div>
<!-- 机型选择对话框 -->
<div id="brand_model_dialog"></div>
<script type="text/javascript">
$(function(){
	$("form.validate").validate();
	BrandInput.init();
	$('#brief' ).ckeditor( );	
});

</script>