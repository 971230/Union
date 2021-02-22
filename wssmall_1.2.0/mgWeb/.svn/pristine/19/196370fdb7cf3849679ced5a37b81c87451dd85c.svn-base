<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="js/Tag.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="" class="selected"><span class="word">修改类型</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>  
<div class="input">
 <form method="post" action="tag!saveEdit.do" name="theForm" id="theForm"  >
 <input type="hidden" name="tag.tag_id" value="${tag.tag_id }"/>
   <table cellspacing="1" cellpadding="3">
     <tr>
       <th><label class="text">标签名称</label></th>
       <td><input type="text" class="ipttxt"  name="tag.tag_name" id="name" maxlength="60" value="${tag.tag_name }"  />       </td>
       <td align="left"><div id="nameTip" style="width:80px;"></div></td>
     </tr>
     <tr>
       <th><label class="text">标签类型</label></th>
       <td>
         <html:selectdict curr_val="${tag.cat_type}" id="cat_type" name="tag.cat_type" attr_code="DC_CAT_TYPE" style="width:155px"></html:selectdict>
       </td>
       <td></td>
     </tr>
   </table>
   <br />
   <div class="submitlist" align="center">
 <table>
 <tr><td >
  <input type="button"	style="margin-left:230px"  value=" 修 改 " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>
 </form>
 </div>
<script type="text/javascript">
$(function(){
	$("form.validate").validate();
	Tag.intChkNameEvent();
});
</script>