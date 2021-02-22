<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck" %>
<script type="text/javascript" src="notice/notice_edit.js"></script>

<div class="input">
 <form class="validate" method="post" name="editNoticeForm" id="editNoticeForm" enctype="multipart/form-data">
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table" align="center">
     <input type="hidden" value="${notice.notice_id }" name="notice.notice_id">
     <tr>
       <th  class="label"><label class="text" >公告标题：</label></th>
       <td><input type="text" class="ipttxt" disabled="disabled"  name="notice.title" maxlength="60" value="${notice.title }"  dataType="string" required="true" />       </td>
      </tr>
      
      <tr>
       <th><label class="text">生效时间：</label></th>
       <td  ><input type="text" disabled="disabled"  class="dateinput ipttxt"    name="notice.begin_time" maxlength="60" value="<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.begin_time}"></html:dateformat>"  dataType="date" required="true" class="dateinput"/></td>
      </tr>
      <tr>
       <th><label class="text">失效时间：</label></th>
       <td  ><input type="text"  class="dateinput ipttxt" disabled="disabled"   name="notice.end_time" maxlength="60" value="<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.end_time}"></html:dateformat>"  dataType="date" required="true" class="dateinput"/></td>
      </tr>
    <tr>
       <th><label class="text">内容：</label></th>
       <td >
          <div class="inputBox">
          <textarea class="textareaClass" disabled="disabled"  name="notice.content" id='content' required="true" 
									cols="100" rows="8" style="width: 98%; height: 300px;">
								${notice.content}
		  </textarea>
		  </div>
       </td>
      </tr>
 
   </table>

 </form>

</div>
