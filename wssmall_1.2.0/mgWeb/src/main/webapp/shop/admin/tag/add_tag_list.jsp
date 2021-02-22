<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Tag.js"></script>


  <div class="searchformDiv" >
  <form  action="tag!saveTag.do" id="searchTypeForm" method="post">
    <table width="100%" cellspacing="0" cellpadding="0" border="0">
   	  <tbody>
   	    <tr>
   	      <th>标签编码:</th><input type="hidden" name="choose_tag_type" id="choose_tag_type" value="${classTag.cat_type}" />
   	      <td><input type="text" class="ipttxt"  style="width:180px" name="tag_code" value="${tag_code}" class="searchipt" /></td>
   	      
		  <th>标签名称:</th>
   	      <td><input type="text" class="ipttxt"  style="width:180px" name="tag_name" value="${tag_name}" class="searchipt" /></td>
		  </td>
		  
		 <c:if test="${choose_tag_type == 'tag_good'}">
		  
		  <th>标签值:</th>
   	      <td><input type="text" class="ipttxt"  style="width:180px" name="tag_value" value="${tag_value}" class="searchipt" /></td>
		  </td>
		  
		  </c:if>
		  
 	      </tr>
 	      
 	       <tr>
   	      <th>标签类型:</th>
   	      <td>
		  	<select  class="ipttxt"  style="width:100px" id="tag_type" name="tag_type">
				<option value="G" <c:if test='${tag_type=="G"}'>selected="true"</c:if>>标签组</option>
			</select>
		  </td>
   	      
		  <th>标签状态:</th>
		 <td>
		  	<select  class="ipttxt"  style="width:100px" id="tag_status" name="tag_status">
				<option value="0" <c:if test='${tag_state=="0"}'>selected="true"</c:if>>有效</option>
				<option value="1" <c:if test='${tag_state=="1"}'>selected="true"</c:if>>无效</option>
			</select>
		  </td>
 	      </tr>
 	      
 	      <tr>
   	      <th>备注:</th>
   	      <td><textarea  style="width:180px;height: 160px" name="tag_desc" rows="3" cols="20" class="searchipt"></textarea></td>
 	      </tr>
 	      
 	      <tr align="center">
   	      <td><input type="submit" value="保存" class="comBtn"></td>
   	       <td>
   			<c:if test="${classTag.cat_type == 'tag_good' }">
   				<a href="tag!goodlist.do" style="margin-right:10px;" id="backBtn" class="comBtn" ><span>返回</span></a>
   			</c:if>
   			
   			<c:if test="${classTag.cat_type == 'tag_shop' }">
   				<a href="tag!shoplist.do" style="margin-right:10px;" id="backBtn" class="comBtn" ><span>返回</span></a>
   			</c:if>
   	       </td>
 	      </tr>
 	  </tbody> 

    </table>
    </form>
  </div>

<div class="grid" id="typeList"> 
<form validate="true" class="validate">
<input type="hidden" name="choose_tag_type" id="choose_tag_type1" value="${classTag.cat_type}" />

<a href="" style="margin-right:10px;" id="backBtn" class="comBtn" ><span>标签:</span></a><img class="modify" src="images/open.gif" >
<grid:grid  from="webpage"  >

	<grid:header>
		<grid:cell width="250px">&nbsp;标签编码</grid:cell> 
		<grid:cell width="250px">标签名称</grid:cell> 
		<grid:cell width="250px">状态</grid:cell> 
		<grid:cell width="250px">标签值</grid:cell> 
		<grid:cell width="250px">备注</grid:cell> 
		<grid:cell width="100px">操作</grid:cell> 
	</grid:header>

  	<grid:body item="tag">
        <grid:cell>${tag.tag_code }</grid:cell>
        <grid:cell>${tag.tag_name }</grid:cell>
        <grid:cell>
        	<c:if test="${tag.tag_status } == '0'">
        		有效
        	</c:if>
        	<c:if test="${tag.tag_status } == '1'">
        		无效
        	</c:if>
        </grid:cell>
        <grid:cell>${tag.tag_value }</grid:cell>
        <grid:cell>${tag.tag_desc }</grid:cell>
        <grid:cell><a  href="tag!deleteTag.do?tag_code=${tag.tag_id}&choose_tag_type=${classTag.cat_type}" style="margin-right:10px;" id="deleteBtn" class="graybtn1"><span>删除</span></a></grid:cell>
  	</grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>

