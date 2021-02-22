<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="write_msg.js"></script>
<script type="text/javascript" src="select_user.js"></script>
<script type="text/javascript">

</script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">写信</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   
<div class="input">
<!-- messageAction.do -->
 <form   class="validate" method="post" action="javascript:void(0)"  name="theForm"   id="theForm" >
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr>
       <th><label class="text">消息主题</label></th>
       <td><input type="text" class="ipttxt"  name="message.m_topic" maxlength="60" value=""  dataType="string"  />       </td>
      </tr>
      <tr>
      <th><lable class="text">发送范围</th>
     
      <td><!--  <select  class="ipttxt"  id ="sel_user" onchange=selVau()  style="width:150px">
      <option value=-1>选特定的收件人</option>
      <option value=0>所有的电信管理员</option>
      <option value=4>所有的供货商</option>
      <option value=5>所有的供货商员工</option>
    </select>-->
      
       <html:selectdict  attr_code="DC_MSG_SENDER_TYPE" id ="sel_user"></html:selectdict> 
     
    </td>
      </tr>
     
    
      <tr id ="Sel_all">
        <input type='hidden' name="user_num" id="user_id"  value="" />
       <th><label class="text"><span class='red'>*</span>收 件 人</label></th>
       <td><input type="text" class="ipttxt"  id = "user_name" name="user_name" maxlength="60" value=""  dataType="string"  readonly="true"/>
        <!--  <input type="button" id="refAgentBtn"  value="选择收件人" class="comBtn" /> -->
         <input type="button" id="refAgentBtn" class="comBtn" name="" value="选择收件人">
     <!-- <a class="sgreenbtn" href="#"><span id="refAgentBtn">选择收件人</span></a> -->  
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
    
    
   </table>
   <div class="submitlist" align="center">
   <table>
    <tr>
      <th></th>
     <td >
   <input class="submitBtn"  id="btn" type="submit" value="确 定"  />
   <input class="submitBtn" name="reset" type="reset" value="重 置"  />
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