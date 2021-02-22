<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<div class="input">
  <form class="validate" method="post" action="activity!saveAdd.do" name="theForm" id="theForm" enctype="multipart/form-data">
    <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
      <tr>
       <th><label class="text">活动名称：</label></th>
       <td><input type="text" class="ipttxt"  name="activity.name" maxlength="60" value=""  dataType="string" required="true"  />       </td>
      </tr>
       <tr>
     	<th><label class="text">活动编码：</th>
     	<td><input type="text" id="pmt_code" name="activity.pmt_code" dataType="string" required="true"></td>
     </tr>
      <tr>
       <th><label class="text">上传活动图片：</label></th>
       <td>
         <input type="file" name="pic" id="pic" size="45"/>&nbsp;&nbsp;<span class="help_icon" helpid="ad_file"></span>
       </td>
      </tr>
      <tr>
       <th><label class="text">活动标签：</label></th>
       <td>
         <c:forEach var="tag" items="${tagLst}">
       	   <input type="checkbox" style="outline-style: none;" name="tag_ids" 
       	          value="${tag.tag_id }" id="tag_${tag.tag_id }" /> ${tag.tag_name } &nbsp;&nbsp;
       	 </c:forEach>
       </td>
      </tr>
      <tr>
       <th><label class="text">是否开启：</label></th>
       <td>
       <input type="radio" name="activity.enable" value="0"
			checked /> 否&nbsp;&nbsp; <input type="radio"
			name="activity.enable" value="1" /> 是
       </td>
      </tr>
	
	<tr>
       <th><label class="text">起始时间：</label></th>
       <td><input type="text" name="begin_time" style="width:100px" readonly="readonly"  required="true" 
									maxlength="60" class="dateinput ipttxt" dataType="date" /></td>
      </tr>
      <tr>
       <th><label class="text">终止时间：</label></th>
       <td><input type="text" name="end_time" 
        			style="width:100px" readonly="readonly"  required="true" 
									maxlength="60" class="dateinput ipttxt" dataType="date" /></td>
      </tr>
	<tr>
       <th><label class="text">促销活动描述</label></th>
       <td><textarea rows="5" cols="30" name="activity.brief"></textarea> </td>
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
    
</script>
