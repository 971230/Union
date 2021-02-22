<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="goods/js/es_terminal_list.js"></script>
<div class="input">
    <div class="main-div">
         <form class="validate" method="post" action="goods!saveOrUpdateEsTerminal.do" name="editForm" id="editForm"  >
           <input type="hidden" name="sn" value="${esTerminal.sn }" />
           <input type="hidden" name="action" value="${action }" />
           <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
             <tr>
                <th><span class="red">*</span>机型编码：</th>
                <td colspan="3">
                    <input type="text" id="sn" style="width:150" name="esTerminal.sn"
                     value="${esTerminal.sn }" class="ipttxt" required="true"/>
                </td>             
             </tr>
             <tr>
                <th><span class="red">*</span>虚拟串号：</th>
                <td><input type="text" class="ipttxt"  name="esTerminal.terminal_no" id="model_name" maxlength="60"
                    value="${esTerminal.terminal_no }" dataType="string" required="true" /></td>
            </tr>

           </table>
<div class="submitlist" align="center">
 <table>
 <tr>
   <th></th>
   <td >
     <input  type="button" value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>         
         </form>
    </div>
</div>

<script type="text/javascript">
$(function(){
	esTerminalInput.init();
});

</script>