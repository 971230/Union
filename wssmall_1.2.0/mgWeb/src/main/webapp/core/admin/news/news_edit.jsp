<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck" %>
<script type="text/javascript" src="js/news.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">修改快讯列表</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
<div class="input">
 <form class="validate" method="post" action="news!editSave.do" name="theForm" id="theForm">
 <input type="hidden" name="newsVO.news_id" value="${newsVO.news_id }" />
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table" align="center">
   
     <tr >
       <th  class="label"><label class="text" >标题：</label></th>
       <td><input type="text" class="ipttxt"  name="newsVO.title" maxlength="60" value="${newsVO.title }"  dataType="string" required="true" />       </td>
      </tr>
      <tr>
       <th><label class="text">有限期：</label></th>
       <td  ><input type="text"  class="dateinput"  class="ipttxt"  name="newsVO.end_time" maxlength="60" value="<html:dateformat pattern="yyyy-MM-dd" d_time="${newsVO.end_time}"></html:dateformat>"  dataType="date" required="true" class="dateinput"/></td>
      </tr>
       <tr>
       <th><label class="text">会员：</label></th>
       <td>
       	 <c:forEach items="${lvList }" var="lv">
       	 	<input type="checkbox" name="lvids" value="${lv.lv_id }" <c:if test="${lv.selected }">checked</c:if> />&nbsp;${lv.name }&nbsp;&nbsp;
       	 </c:forEach>
       </td>
      </tr>
      <tr>
       <th><label class="text">内容：</label></th>
       <td >
          <div class="inputBox">
          <textarea class="textareaClass" name="newsVO.content" id='content' required="true" 
									cols="100" rows="8" style="width: 98%; height: 300px;">
									<c:out value="${newsVO.content}" escapeXml="false"></c:out>
								</textarea>
		  </div>
       </td>
      </tr>
      
 
   </table>
   <div class="submitlist" align="left">
 <table>
 <tr>
  <th></th>
    <td >
      <input  type="submit" value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>
 </form>

 <script type="text/javascript">
$("form.validate").validate();
</script>
</div>