<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
  body {
    background: url("../images/mainbg.gif") repeat-y scroll 0 0 rgba(0, 0, 0, 0);
    font-family: Arial,Helvetica,sans-serif;
    min-width:100%
   }
</style>

  <div class="grid" id="goodslist">
     <form method="POST"  id="userform" >
       <grid:grid from="webpage"  formId="serchform" ajax="yes">
       
      <grid:header>
	  <!-- <grid:cell width="5%">选择</grid:cell> -->  
		<grid:cell width="10%">用户工号</grid:cell>
		<grid:cell width="15%">用户名称</grid:cell>
		<grid:cell width="15%">所属组织</grid:cell>
		<grid:cell width="10%">城市</grid:cell>
		<grid:cell width="10%">同步工号</grid:cell>
		<grid:cell width="10%">用户类型</grid:cell>
		<grid:cell width="10%">状态</grid:cell>
		<grid:cell width="10%"> 创建时间</grid:cell>
		<grid:cell width="15%">操作</grid:cell>
	  </grid:header>
	  
	 <grid:body item="userAdmin" >
	   <!--  <grid:cell></grid:cell> -->
	    <input type="hidden"  name="userRadio" 
	    	org_id="${userAdmin.org_id }" lan_id="${userAdmin.lan_id}"  relcode="${userAdmin.relcode }" 
	    	dept_name="${userAdmin.dep_name}" value ="${userAdmin.userid }" username="${userAdmin.username}" 
	    	realname='${userAdmin.realname}' state="${userAdmin.state}" founder="${userAdmin.founder}" 
	    	sex="${userAdmin.sex}" phone_num="${userAdmin.phone_num }" email="${userAdmin.email}" 
	    	usertype="${userAdmin.usertype }" syn_dept_id="${userAdmin.syn_dept_id}" syn_hq_dept_id="${userAdmin.syn_hq_dept_id}"
	    	syn_dept_type="${userAdmin.syn_dept_type }" syn_county_id="${userAdmin.syn_county_id}" syn_user_id="${userAdmin.syn_user_id}"
	    	syn_employee_id="${userAdmin.syn_employee_id }" syn_use_domain="${userAdmin.syn_use_domain}" is_syn="${userAdmin.is_syn}"
	    	syn_date="${userAdmin.syn_date }" syn_remark="${userAdmin.syn_remark}" remark="${userAdmin.remark}" 
	    	>
		<grid:cell>&nbsp;${userAdmin.username }
		</grid:cell>
		<grid:cell>&nbsp;${userAdmin.realname }</grid:cell>
		<grid:cell>&nbsp;${userAdmin.dep_name }
		</grid:cell>
		<grid:cell>&nbsp;${userAdmin.lan_name } </grid:cell>
		
		<grid:cell> 
			<c:choose>
				<c:when test="${userAdmin.is_syn==1}">
					是
				</c:when>
				<c:otherwise>
					否
				</c:otherwise>
			</c:choose>
		</grid:cell>
		
		<grid:cell>${userAdmin.type_name }</grid:cell>
		<grid:cell> 
		 <c:if test="${userAdmin.state==1}">启用</c:if>
		 <c:if test="${userAdmin.state==0}">禁用</c:if>
		</grid:cell>
		
		 <!--<grid:cell>${userAdmin.create_time}</grid:cell>-->
		  <grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${userAdmin.create_time}"></html:dateformat></grid:cell>
		  <grid:cell>
		  	<c:if test="${currUserId==userAdmin.paruserid}">
		  		<a title="删除员工" userid ="${userAdmin.userid}" href="javascript:void(0);" style="margin-right:10px;" class="p_prted"   name="delUser">删除</a>
			</c:if>
			<c:if test="${userAdmin.fail_logincount>=limitFailCount}">
				<span class='tdsper'></span><a title="解冻员工账号" userid ="${userAdmin.userid}" href="javascript:void(0);" style="margin-right:10px;" class="p_prted"  cleanUserid="${userAdmin.userid}" name="cleanFailCount">解冻</a>
			</c:if>
		</grid:cell>
	</grid:body>
  </grid:grid>
  </form>
 
</div>

<script type="text/javascript">
$(function(){
	roleEdit.trClick();
	
	roleEdit.cleanFailCount();
	roleEdit.delUser();
});
  
   
 var roleEdit={
		trClick:function(){
			   $("#userform table tr").live("click",function(){
				   //$(this).click(function(){
					 //  userform table tr
					 //[name='userRadio']
					    //var 
					     $("#userform table tr").attr("class","");
				       
					     $(this).attr("class","clickClass");
				         var obj = $(".clickClass [name='userRadio']");
				         
				         //设置组织信息
					     $("#orgid", window.parent.document).val(obj.attr("org_id"));
					     $("#dep_name", window.parent.document).val(obj.attr("dept_name"));
					     $("#userid", window.parent.document).val(obj.val());
					     $("#dept_name", window.parent.document).val(obj.attr("dept_name"));
					     $("#user_name", window.parent.document).val(obj.attr("username"));
					     $("#real_name", window.parent.document).val(obj.attr("realname"));
					     $("#userstate", window.parent.document).val(obj.attr("state"));
					     $("#user_type",window.parent.document).val(obj.attr("founder"));
					     $("[name='adminUser.sex'][value='"+obj.attr("sex")+"']",window.parent.document).attr("checked",true);
					     $("#phone_num",window.parent.document).val(obj.attr("phone_num"));
					     $("#email",window.parent.document).val(obj.attr("email"));
					     $("#lan_id",window.parent.document).val(obj.attr("lan_id"));
					     $("#user_no", window.parent.document).val(obj.attr("username"));
					     $("#remark", window.parent.document).val(obj.attr("remark"));
					     
					     $("#is_syn", window.parent.document).val(obj.attr("is_syn"));
					     $("#syn_hq_dept_id", window.parent.document).val(obj.attr("syn_hq_dept_id"));
					     $("#syn_dept_id", window.parent.document).val(obj.attr("syn_dept_id"));
					     $("#syn_dept_type", window.parent.document).val(obj.attr("syn_dept_type"));
					     $("#syn_county_id", window.parent.document).val(obj.attr("syn_county_id"));
					     
					     $("#syn_user_id", window.parent.document).val(obj.attr("syn_user_id"));
					     $("#syn_employee_id", window.parent.document).val(obj.attr("syn_employee_id"));
					     $("#syn_use_domain", window.parent.document).val(obj.attr("syn_use_domain"));
					     $("#syn_date", window.parent.document).val(obj.attr("syn_date"));
					     
					     if("1" == obj.attr("is_syn")){
					    	 $(".tr_syn_info", window.parent.document).show();
					     }else{
					    	 $(".tr_syn_info", window.parent.document).hide();
					     }
					     
					     //展示角色
					     roleEdit.showRole(obj.val());
					     
					     $("#add_btn",window.parent.document).attr("selected","");
						 $("#mod_btn",window.parent.document).attr("selected","");
					     $("#addSaveBtn",window.parent.document).hide();
					     $("#editSaveBtn",window.parent.document).hide();
					     $("#cancelBtn", window.parent.document).hide();
					     $("#usertype", window.parent.document).hide();
					     $("#adminuser_type", window.parent.document).val(obj.attr("usertype"));
					     $("#user_name", window.parent.document).attr("disabled",true);
					    
					   
					     if(obj.attr("founder")==3){
					    	
					    	 $("#reltype",window.parent.document).val("partner");
				             $("#relcode",window.parent.document).val(obj.attr("relcode"));
				             $("#partner_name",window.parent.document).val(obj.attr("realname"));
				             $("[name='agentLine']",window.parent.document).show();
					     }else if(obj.attr("founder")==4){
					    	 $("#reltype",window.parent.document).val("supplier");
				             $("#relcode",window.parent.document).val(obj.attr("relcode"));
				             $("#supplier_name",window.parent.document).val(obj.attr("realname"));
				             $("[name='supplierLine']",window.parent.document).show();
					     }else{
					    	 $("#reltype",window.parent.document).val();
				             $("#relcode",window.parent.document).val("");
				             $("#partner_name",window.parent.document).val("");
				             $("[name='agentLine']",window.parent.document).hide();
				             
				             $("#supplier_name",window.parent.document).val("");
				             $("[name='supplierLine']",window.parent.document).hide();
					     }
					     
				  // });
			   });  
		},
   		showRole:function(userid){
   			
   			$.ajax({
   	         	url:ctx + "/core/admin/user/userAdmin!editInit.do?ajax=yes&id="+userid,
   	         	dataType:"json",
   	         	data:{},
   	         	success:function(reply){
   	         		var htmlStr ="";
   	         		for(var i=0;i<reply.length;i++){
   	         			var obj = reply[i];
   	         		    htmlStr += roleEdit.appendHtmlStr(obj.roleid,obj.rolename,obj.is_def);
   	         			
   	         		}
   	         	    $("#roleBody",window.parent.document).empty().append(htmlStr);
        			roleEdit.removeTr();
   	         	},
   	         	error:function(){
   	         		
   	         	}
   	    	});
   			
   		},	
   		appendHtmlStr:function(roleid,name,is_def){
   			var is_def_str = "不存在";
   			if(is_def>0){
   				is_def_str = "存在"
   			}
   			var htmlStr =   "<tr id='tr_"+roleid+"'>"+
   							" <input type='hidden' value='"+roleid+"' name=roleids>"+
   							"<td style='text-align:center'>"+roleid+"</td>"+
   							"<td style='text-align:center'>"+name+"</td>"+
   							"<td style='text-align:center'>"+is_def_str+"</td>"+
   							"<td style='text-align:center'>"+
   							" <a title ='删除' href='javascript:;' class='p_prted' roleid='"+roleid+"' name=removeRole>删除</a>"+
   							"</td>"+
   							"</tr>";
   			return htmlStr;
   		},
   		removeTr:function(){
   			
   			 $("[name='removeRole']",window.parent.document).each(function(){
   				 $(this).click(function(){
   					 var roleid = $(this).attr("roleid");
   					 $("#tr_"+roleid,window.parent.document).remove();
   				 });
   				
   			   });
   		},
   		cleanFailCount:function(){
   			var self = this;
   			$("[name='cleanFailCount']").live("click",function(){
   			    var userid = $(this).attr("userid");
   				var url = ctx+"/core/admin/user/userAdmin!cleanFailCount.do?ajax=yes&id="+userid;
   				Cmp.excute('', url, {}, self.cleanJsonBack, 'json');
   			});
   		},
   		delUser:function(){
   			var self = this;
   			$("[name='delUser']").live("click",function(){
   			  if(window.confirm('确认要删除此工号？')){
   			    var userid = $(this).attr("userid");
   				var url = ctx+"/core/admin/user/userAdmin!delete.do?ajax=yes&id="+userid;
   				Cmp.excute('', url, {}, self.cleanJsonBack, 'json');
   			  }
   			});
   		},
   		cleanJsonBack:function(reply){
   			if(reply.result==0){
   				alert(reply.message);
   				window.location.reload();
   			}else{
   				alert(reply.message);
   			}
   		}
   	
   };
</script>
