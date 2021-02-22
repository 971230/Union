<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<div class="input">
 <form class="validate" method="post" action="activity!saveEdit.do" name="theForm" id="theForm" enctype="multipart/form-data">
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr>
       <th><label class="text">活动名称：</label><input type="hidden" name="activity.id" value="${activity_id }" id="b"/></th>
       <td><input type="text" class="ipttxt"  name="activity.name" maxlength="60" value="${activity.name }"  dataType="string" required="true" />       </td>
      </tr>
      <tr>
       <th><label class="text">上传活动图片：</label></th>
       <td>
         <input type="file" name="pic" id="pic" size="45"/>&nbsp;&nbsp;<span class="help_icon" helpid="ad_file"></span>
       </td>
      </tr>
      <c:if test="${activity.atturl != null}">
	    <tr>
		  <th style="width:140px">&nbsp;</th>
		  <td><img src="${activity.atturl}" /></td>
	    </tr>
	  </c:if> 
      <tr>
       <th><label class="text">活动标签：</label></th>
       <td>
         <c:forEach var="tag" items="${tagLst}">
       	   <input type="checkbox" style="outline-style: none;" name="tag_ids" 
       	          value="${tag.tag_id }" id="tag_${tag.tag_id }" /> ${tag.tag_name } &nbsp;&nbsp;
       	 </c:forEach>
       </td>
      </tr>
      <c:if test="${isNetStaff == 'T'}">
        <tr>
          <th><label class="text">活动权重：</label></th>
          <td>
            <input type="text" class="ipttxt" name="activity.rank" maxlength="60" value="${activity.rank}"  dataType="int"/>       
          </td>
        </tr>
      </c:if>
      <tr>
       <th><label class="text">是否开启：</label></th>
       <td>
       <input type="radio" name="activity.enable" value="0"
			<c:if test="${activity.enable == 0 }">checked</c:if> /> 否&nbsp;&nbsp; <input type="radio"
			name="activity.enable" value="1" <c:if test="${activity.enable == 1 }">checked</c:if>/> 是
       </td>
      </tr>
	
	<tr>
       <th><label class="text">起始时间：</label></th>
       <td><input type="text" class="dateinput ipttxt"  name="begin_time" maxlength="60" value="${begin_time }"  dataType="date" required="true" id="c"/></td>
      </tr>
      <tr>
       <th><label class="text">终止时间：</label></th>
       <td  ><input type="text" class="dateinput ipttxt"  name="end_time" maxlength="60" value="${end_time }"  dataType="date" required="true" /></td>
      </tr>
	<tr>
       <th><label class	="text">促销活动描述</label></th>
       <td><textarea rows="5" cols="30" name="activity.brief">${activity.brief }</textarea> </td>
     </tr>
 
    </table>
    <div class="submitlist" align="center">
      <table>
	    <tr>
	      <td><input  type="submit"	  value=" 确    定   " class="submitBtn" /></td>
	    </tr>
	  </table>
    </div>
  </form>
</div>

<script type="text/javascript">

    $("form.validate").validate();
    
    $(function() {
    	
    	//把该活动已有标签选上
        <c:forEach var="tag_id" items="${tag_ids}">
        
            $("input[name='tag_ids'][value='${tag_id}']").attr("checked", true);
            
	    </c:forEach>
	    
	    //如果是电信员工，可以修改标签和排名权重
	    <c:if test="${isNetStaff == 'T'}">
	    
	        //$("input").attr("disabled", true);
	        $("textarea").attr("disabled", true);
	        $("input[name='activity.rank']").attr("disabled", false);
    	    $("input[name='tag_ids']").attr("disabled", false);
    	    $(".submitBtn").attr("disabled", false);
    	    
	    </c:if>

    });
    
</script>