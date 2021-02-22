<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="edit_msg.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">回复信息</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   

<div class="input">
<!-- messageAction.do -->
 <form class="validate"  method="post" action="messageAction!replyAdd.do" name="replyForm"  id="replyForm" >
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
   <input type="hidden" name="reply_mid" value="${reply_mid}" />
     <tr>
       <th><label class="text">消息标题</label></th>
       <td><input type="text" class="ipttxt"  name="message.m_topic" maxlength="60" value="${message.m_topic}"  dataType="string"  />
       </td>
      </tr>
      <tr>
        <input type='hidden' name="message.m_reciverid" id="user_id"  value="${replyId}" />
       <th><label class="text"><span class='red'>*</span>收 件 人</label></th>
       <td><input type="text" class="ipttxt"  id = "user_name" name="message.m_recivername" maxlength="60" value="${message.m_sendername}"  dataType="string"  readonly="true"/>
         <input type="hidden" name = "reply_reciverType" value="${message.m_sendertype}">
        </td>
      </tr>
      <tr>
       <th><label class="text">消息类型</label></th>
       <td>
       <html:selectdict curr_val="${message.m_type}" name="message.m_type" attr_code="msg_type"></html:selectdict>
       </td>
      </tr>
	<tr>
       <th><label class="text">消息内容</label></th>
       <td><textarea rows="5" cols="30" name="message.m_content"></textarea> </td>
     </tr>
    <tr>
    <td></td>
    <td>
     </td>
    </tr>
   </table>
   <div class="submitlist" align="center">
 <table>
   <tr><th>
   </th>
   <td>
   <input  id="updatebtn" type="submit" value="确 定" class="submitBtn" />
    <input name="reset" type="reset" value="重 置" class="submitBtn" />
   </td>
   </tr>
 </table>
</div>
 </form>
 <script type="text/javascript">
$("form.validate").validate();
</script>

</div>