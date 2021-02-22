<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="write_msg.js"></script>
<script type="text/javascript" src="select_user.js"></script>

<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">查看发件箱信息</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>  
<div class="input">
<!-- messageAction.do -->
 <form class="validate" method="post" action='' name="theForm" id="theForm" >
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
    <input type="hidden" name="message.m_senderid" value="${message.m_senderid}">
     <tr>
       <th><label class="text">消息主题</label>
       <td><label>${message.m_topic}</label> </td>
      </tr>
       <tr>
       <th><label class="text">收件人</label>
       <td><label name="message.m_sendername">${message.m_recivername}</label></td>
      </tr>
       <tr>
       <th><label class="text">消息类型</label>
       <td><label>${message.m_type}</label></td>
      </tr>
     <tr>
       <th><label class="text">发送时间</label>
       <td><label class="text">${message.m_sendtime}</label></td>
      </tr>
	<tr>
       <th><label class="text">消息内容</label></th>
       <td><textarea rows="5" cols="30"  name="message.m_content" readonly="readonly" >${message.m_content}</textarea> </td>
     </tr>
    
   </table>
   <div class="submitlist" align="center">
	 <table>
			<tr>
	<th></th>
	<td> <a href="messageAction!listById.do?num=2" style="margin-right:10px;" id="readBtn" class="submitBtn" ><span>返回列表</span></a>
  </td>

	</tr>
	 </table>
	</div> 
 </form>
 <script type="text/javascript">
$("form.validate").validate();
</script>
<div title="收件人" id="refAgentDlg"></div>
</div>