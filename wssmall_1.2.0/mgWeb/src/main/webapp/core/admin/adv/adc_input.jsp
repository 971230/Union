<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">新增广告位置</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
<div class="input">
 <form class="validate" method="post" action="adColumn!addSave.do" name="theForm" id="theForm">

   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr>
       <th  class="label"><label class="text">位置名称：</label></th>
       <td  ><input type="text" class="ipttxt"  name="adColumn.cname" maxlength="60" value=""  dataType="string" required="true" />&nbsp;&nbsp;<span class="help_icon" helpid="adcolumn_name"></span>       </td>
      </tr>
	 <tr>
       <th  class="label"><label class="text">广告类型：</label></th>
       <td  ><select  class="ipttxt"  name="adColumn.atype">
       			<option value=0>图片</option>
       			<option value=1>Flash</option> 
             </select>      
       </td>
      </tr>      
      <tr>
       <th  class="label"><label class="text">宽度：</label></th>
       <td  ><input type="text" class="ipttxt"  name="adColumn.width" maxlength="60" value=""  dataType="string" required="true" />&nbsp;&nbsp;<span class="help_icon" helpid="adcolumn_width"></span></td>
      </tr>
      <tr>
       <th  class="label"><label class="text">高度：</label></th>
       <td  ><input type="text" class="ipttxt"  name="adColumn.height" maxlength="60" value=""  dataType="string" required="true" />&nbsp;&nbsp;<span class="help_icon" helpid="adcolumn_height"></span></td>
      </tr>
   </table>
   <div class="submitlist" align="center">
 <table>
 <tr>
 <th></th>
 <td >
  <input  type="submit"	  value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>
 </form>
 <script type="text/javascript">
$("form.validate").validate();
</script>
 </div>
