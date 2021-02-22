<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Tag.js"></script>

<form method="POST" action="tag!search.do?choose_tag_type=tag_good">
  <div class="searchformDiv">
    <table width="100%" cellspacing="0" cellpadding="0" border="0">
   	  <tbody>
   	    <tr>
   	      <th>标签名称:</th>
   	      <td><input type="text" class="ipttxt"  style="width:180px" name="tag_name" id="tag_name" value="${tag_name}" class="searchipt" /></td>
   	      
		  <th>标签类型:</th>
		  <td>
		  	<select  class="ipttxt"  style="width:100px" id="tag_type" name="tag_type">
				<option value="">--请选择--</option>
				<option value="G" <c:if test='${tag_type=="G"}'>selected="true"</c:if>>标签组</option>
				<option value="T" <c:if test='${tag_type=="T"}'>selected="true"</c:if>>标签</option>
			</select>
		  </td>
		  
		  <td><input type="submit" value="搜&nbsp;&nbsp;索" class="comBtn" /></td>
 	      </tr>
 	  </tbody>
    </table>
  </div>
 </form>
 
<div class="grid" >
<form method="post" name="invalForm" id="invalForm">

	<div class="comBtnDiv">
	操作:&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="tag!addTagList.do?choose_tag_type=tag_good" style="margin-right:10px;" id="addTagList" class="comBtn" ><span>添加标签组</span></a>
    <a href="tag!addTag.do?choose_tag_type=tag_good" style="margin-right:10px;" id="addTag" class="comBtn" ><span>添加标签</span></a>
     <a href="javascript:;" style="margin-right:10px;" id="goodInvalBtn" class="comBtn" ><span>失效</span></a>
		<div style="clear:both"></div>
	</div>
<grid:grid  from="webpage"  >

	<grid:header>
	<grid:cell ><input type="checkbox"  id="toggleChk" /></grid:cell>
	<grid:cell  width="250px">&nbsp;标签编码</grid:cell> 
	<grid:cell sort="type">标签名称</grid:cell> 
	<grid:cell sort="have_prop">状态</grid:cell> 
	<grid:cell sort="have_parm">类型</grid:cell> 
	<grid:cell sort="have_parm">标签值</grid:cell> 
	<grid:cell  width="100px">备注</grid:cell> 
	</grid:header>

  <grid:body item="tag">
  		<grid:cell><input type="checkbox" name="tag_ids" value="${tag.tag_code }" /></grid:cell>
        <grid:cell>${tag.tag_code}</grid:cell>
        <grid:cell>
         <a href="tag!editTag.do?tag_id=${tag.tag_id }&choose_tag_type=tag_good" style="margin-right:10px;color:FF0000;text-decoration:underline;"><span>${tag.tag_name }</span></a>
        </grid:cell>
       
        <!-- 0:有效      1:无效 -->
        <grid:cell>
		<c:if test="${tag.tag_status == '0' }">
		有效
        </c:if>
        
        <c:if test="${tag.tag_status == '1' }">
     	 无效
        </c:if>
		</grid:cell>
		
		<grid:cell>
        <c:if test="${tag.tag_type == 'G' }">
        	标签组
        </c:if>
        
        <c:if test="${tag.tag_type == 'T' }">
     	 标签
        </c:if>
        </grid:cell> 
        
        <grid:cell>${tag.tag_value}</grid:cell>
        <grid:cell>${tag.tag_desc}</grid:cell>
        
      
 
  </grid:body>  
  
</grid:grid> 
</form>
<div style="clear:both;padding-top:5px;"></div>
 </div>










