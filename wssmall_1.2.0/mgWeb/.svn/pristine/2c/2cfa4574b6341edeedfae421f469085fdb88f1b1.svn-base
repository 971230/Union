<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

  <div class="searchformDiv" >
  <form  action="tag!updateTag.do?choose_tag_type=${tag.cat_type }&tag_id=${tag.tag_id }" id="searchTypeForm" method="post">
    <table width="100%" cellspacing="0" cellpadding="0" border="0">
   	  <tbody>
   	    <tr>
   	      <th>标签编码:</th>
   	      <td><input type="text" readonly="" class="ipttxt"  style="width:180px" id="tag_code" name="tag_code" value="${tag.tag_code}" class="searchipt" /></td>
   	    </tr>
   	    
   	    <tr>
		  <th>标签名称:</th>
   	      <td><input type="text" class="ipttxt"  style="width:180px" name="tag_name" value="${tag.tag_name}" class="searchipt" /></td>
		 </tr>
		 
		 <tr>
		  <th>标签类型:</th>
		  <td>
		  	<select  class="ipttxt"  style="width:100px" id="tag_type" name="tag_type">
				<option value="G" <c:if test='${tag.tag_type=="G"}'>selected="true"</c:if>>标签组</option>
				<option value="T" <c:if test='${tag.tag_type=="T"}'>selected="true"</c:if>>标签</option>
			</select>
		  </td>
		 </tr> 
		 
		 <tr> 
		  <th>标签状态:</th>
		  <td>
		  	<select  class="ipttxt"  style="width:100px" id="tag_status" name="tag_status">
				<option value="0" <c:if test='${tag.tag_status=="0"}'>selected="true"</c:if>>有效</option>
				<option value="1" <c:if test='${tag.tag_status=="1"}'>selected="true"</c:if>>无效</option>
			</select>
		  </td>
		 </tr> 
		 
		 <c:if test="${tag.cat_type == 'tag_good' }">
		 	<tr>
		  	<th>标签值:</th>
   	      	<td><input type="text" class="ipttxt"  style="width:180px" name="tag_value" value="${tag.tag_value}" class="searchipt" /></td>
		 	</tr>
		 </c:if>
		
		 <tr>
		  <th>备注:</th>
   	      <td><input type="text" class="ipttxt"  style="width:180px" name="tag_desc" value="${tag.tag_desc}" class="searchipt" /></td>
		 </tr> 
		 
		  <tr align="center">
   	      <td><input type="submit" value="修改" class="comBtn"></td>
   	       <td>
   			<c:if test="${tag.cat_type == 'tag_good' }">
   				<a href="tag!goodlist.do" style="margin-right:10px;" id="backBtn" class="comBtn" ><span>返回</span></a>
   			</c:if>
   			
   			<c:if test="${tag.cat_type == 'tag_shop' }">
   				<a href="tag!shoplist.do" style="margin-right:10px;" id="backBtn" class="comBtn" ><span>返回</span></a>
   			</c:if>
   	       </td>
 	      </tr>
 	      
 	  </tbody> 

    </table>
    </form>
  </div>
