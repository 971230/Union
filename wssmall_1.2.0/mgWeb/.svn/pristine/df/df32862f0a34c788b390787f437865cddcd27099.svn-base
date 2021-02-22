<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/flowAudit.js"></script>
<div class="reserved_div">
    <div id="businissInfo">
         <c:forEach items="${flowItemList}" var="flowItem">
               <c:if test="${flowItem.flow_state=='00A'}">
               		<script>  auditObjMsgUrl =ctx+'${flowItem.apply_url}';</script>
		   	  </c:if>
	     </c:forEach>
	</div>
	
      <table width="100%" cellspacing="0" cellpadding="0" border="0" class="reserved_table">
		<tbody>
		    <c:forEach items="${flowItemList}" var="flowItem">
               <c:if test="${flowItem.flow_state=='00A'}">
    	           <tr>         
          		      <th>申请人部门:</th>
          		      <td>${flowItem.apply_dep}</td>
          		      <th>申请人:</th>
          		      <td>${flowItem.apply_name}</td>
          		      <th>申请时间:</th>
          		      <td>${flowItem.apply_date}</td>
      		       </tr>
                </c:if>
	          </c:forEach>
    	</tbody>
     </table>
   </div>
  <div class="form_history">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
      <th style="width:40px;">&nbsp;</th>
      <th>审批人</th>
      <th>审批人部门</th>
      <th>审批决策</th>
      <th>审批意见</th>
      <th>审批时间</th>
    </tr>
     <c:forEach items="${flowItemList}" var="flowItem">
      <c:if test="${flowItem.flow_state=='00F'}">
          <tr>
            <td>1</td>
      		<td>${flowItem.audit_name}</td>
      		<td>${flowItem.audit_dep}</td>
      		<td>${flowItem.audit_state}</td>
      		<td>${flowItem.audit_note}</td>
      		<td>${flowItem.audit_date}</td>
    	</tr>
      </c:if>
	</c:forEach>
	
  </table>
</div>
<div class="form_grid" >
    
            <c:forEach items="${flowItemList}" var="flowItem">
               <c:if test="${flowItem.flow_state=='00A'}">
                  <form class="validate" method="post" action="" id='editAuditform' validate="true">
                     <div class="form_grid_title">当前审批环节：${flowItem.item_name}</div>
   						 <table width="100%" cellpadding="0" cellspacing="0">
        					<tr>
           						 <th>审批决策：</th> 
            						<td colspan="3">
                						<label><input type="radio" name="RadioGroup1" value="PASS" />通过</label>
                						<label><input type="radio" name="RadioGroup1" value="NO" />不通过</label>
                						<!--   <c:if test="${flowItem.audit_buttons eq 3 }">
	    										 <label>
                 									 <input type="radio" name="RadioGroup1" value="NEXT"  />
                  									给下一个领导</label>
										</c:if>-->
                					</td> 
        				    </tr>
       						<tr>
            					<th>审批意见：</th> 
           					   <td colspan="3"><textarea name="textarea" id="textarea" cols="45" rows="5" class="iptarea"></textarea></td> 
       						 </tr>
        					<tr>
            					<th>部门：</th>
            					<td>${flowItem.audit_dep}</td>
            					<th>审批人：</th>
            					<td>${flowItem.audit_name}</td>  
        					</tr>  
                       </table>
    				<div class="form_grid_btn">
    				        <a href="#" class="formbtn" id="showAuditInfo" style="margin-left:10px;">查看审批详情</a>
    						<a href="#" class="formbtn" id="nextStep">确定</a>
    						
    				</div>
					</div> 
					
					<input type="hidden" name="modInfoAudit.flow_name" value="${flowItem.flow_name }" />
		            <input type="hidden" name="modInfoAudit.item_name" value="${flowItem.item_name }" />
					<input type="hidden" name="modInfoAudit.apply_dep" value="${flowItem.apply_dep }" />
					<input type="hidden" name="modInfoAudit.apply_name" value="${flowItem.apply_name }" />
					<input type="hidden" name="modInfoAudit.apply_date" value="${flowItem.apply_date }" />
					<input type="hidden" name="modInfoAudit.processor" value="${flowItem.processor }" />
					<input type="hidden" name="modInfoAudit.item_id" value="${flowItem.item_id }" />
					<input type="hidden" name="modInfoAudit.item_inst_id" value="${flowItem.item_inst_id }" />
					<input type="hidden" name="modInfoAudit.flow_id" value="${flowItem.flow_id }" />
					<input type="hidden" name="modInfoAudit.apply_url" value="${flowItem.apply_url }" />
					<input type="hidden" name="modInfoAudit.flow_inst_id" value="${flowItem.flow_inst_id }" />
					<input type="hidden" name="modInfoAudit.ref_obj_id" value="${flowItem.ref_obj_id }" />
					<input type="hidden" name="modInfoAudit.audit_dep" value="${flowItem.audit_dep }" />
					<input type="hidden" name="modInfoAudit.audit_name" value="${flowItem.audit_name }" />
		            <input type="hidden" name="modInfoAudit.flow_state" value="${flowItem.flow_state }" />
					<input type="hidden" name="modInfoAudit.audit_note" id="modInfoAudit.audit_note" value="${flowItem.audit_note }" />
					<input type="hidden" name="modInfoAudit.audit_state" id="modInfoAudit.audit_state" value="${flowItem.audit_state }" />
		
					
    	          </form>
                </c:if>
	          </c:forEach>
<div>
<div id='auditObjMsgDialog'></div>
