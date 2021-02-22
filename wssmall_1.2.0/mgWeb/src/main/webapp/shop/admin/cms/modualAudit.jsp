<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="html" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/cms_obj.js"></script>
<script type="text/javascript">
function switab(tab,con,tab_c_css,tab_n_css,no) {  
    $(tab).each(function(i){  
	        if(i == no)  
	        {  
	            $(this).addClass(tab_c_css);  
	        }else  
	        {  
	            $(this).removeClass(tab_c_css);  
	            $(this).addClass(tab_n_css);  
        }  
	    })  
	    if (con)  
	    {  
	        $(con).each(function(i){  
	            if(i == no)  
	            {  
	                $(this).show();  
	            }else  
	            {  
	                $(this).hide();  
	            }  
	        })  
	    }  
	} 
function dialogM (value_ext,res_type){
	var paramDial = value_ext.split(",");
	var para1 ="";
	var para2 ="";
	for ( var int = 0; int < paramDial.length; int++) {
		 para1 = paramDial[0];
		 para2 = paramDial[1];
		
	}
	  CmsObj.showDialog(res_type, para1,para2);
}

$(document).ready(function(){
    $("#switab li").each(function(i){
        $(this).click(function(){
            switab('#switab li','.content','on','',i);        
        })
    })
    switab('#switab li','.content','on','',0);   
})

</script>

<div class="input">
	 <form method="post" action="modual!saveTem.do?tpl_id=${tpl_id}&type=edit" class="validate" name="theForm" id="theForm" >
	  		  <div class="mtt">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mb_table">
								<tr>
								模块名称：<input type="text" name="module_name" value="${modual_name}" class="ipttxt" >
								</tr>
								<c:forEach items="${list}" var ="listAll">
								<tr>
              						<c:forEach items="${listAll.json_fields}" var="jsonlist" >
              						
              							<c:if test="${jsonlist.value_type=='text'}">
              								<td>${jsonlist.cname}：</td>
											<td><input name="${jsonlist.ename}_${listAll.column_id}" type="text" class="ipttxt" id="${jsonlist.ename}" value="${jsonlist.show_value}" /></td>
              							</c:if>
	           							<c:if test="${jsonlist.value_type=='dialog'}">
	              							<td>${jsonlist.cname}：</td>
											<td><input type="text" class="ipttxt" id="final_res_value" name ="${jsonlist.ename}_${listAll.column_id}" readonly value="${jsonlist.show_value}" />
												<input type ='hidden' name ="rel_id_${listAll.column_id}" id="final_res_id" value="${listAll.rel_id}">
												<a href="javascript:void(0)" onclick="dialogM('${jsonlist.value_ext_value}','${jsonlist.rel_type}')">选择</a>	
											</td>
										
	              						</c:if>
              							<c:if test="${jsonlist.value_type=='select'}">
	              							<td>${jsonlist.cname}：</td>
											<td><html:selectdict appen_options="<option value=''>--请选择--</option>" style="width:200px;" name ="${jsonlist.ename}_${listAll.column_id}" attr_code="${jsonlist.value_ext_value}">
												</html:selectdict>
											</td>
	              						</c:if>
	              						
              						</c:forEach>
              						</tr>
			              		</c:forEach>
							
                    </table>                
                </div>
                <div id="switab">
               		<div class="bb_tab clearfix">
	                	<ul class="tab_title">
	                		<c:forEach items="${tabList}" var ="tabAll" varStatus="status">
	                				<c:if test="${status.first }"><li class="on"><a  href="javascript:void(0)"  onclick="switab('#switab li','.content','on','',${status.index})">${tabAll.title}</a></li></c:if>
	                				<c:if test="${!status.first }"><li ><a  href="javascript:void(0)"  onclick="switab('#switab li','.content','on','',${status.index})">${tabAll.title}</a></li></c:if>
	                		</c:forEach>
	                    </ul>
                    </div>
      	        <div class="bb_msg_div mtt clearfix">
                   
                    		<c:forEach items="${tabList}" var ="tabAll">
                    			 <div class="content">
                    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                    			<c:forEach items="${tabAll.json_fields}" var="jsonTab" >
		                    				<c:if test="${jsonTab.value_type=='text'}">
			                    				 <tr>
			                           				 <th>${jsonTab.cname}：</th>
						                             <td style="width:450px;"><input name="${jsonTab.ename}_${tabAll.column_id}" type="text" class="ipttxt" id="${jsonTab.cname}" style="width:440px;" value="${jsonTab.show_value}" /></td>
						                             <td>&nbsp;</td>
			                          			 </tr>
		                          			 </c:if>
		                          			 <c:if test="${jsonTab.value_type=='dialog'}">
		                          			 		<tr>
							                            <th>${jsonTab.cname}：</th>
							                            <td><input name="${jsonTab.ename}_${tabAll.column_id}" type="text" class="ipttxt" id="final_res_value" style="width:440px;"readonly value="${jsonTab.show_value}" /></td>
							                            <td><input type ='hidden' name ="rel_id_${tabAll.column_id}" id="final_res_id" value="${tabAll.rel_id}"></td>
							                            <td><a href="javascript:void(0)" onclick="dialogM('${jsonTab.value_ext_value}','${jsonTab.rel_type}')">选择</a></td>
		              						</c:if>
		           							<c:if test="${jsonlist.value_type=='select'}">
		            							<td>${jsonlist.cname}：</td>
												<td><html:selectdict appen_options="<option value=''>--请选择--</option>" style="width:200px;" name ="res_id" attr_code="${jsonTab.value_ext_value}">
													</html:selectdict>
												</td>
		            						</c:if>
		                    			</c:forEach>
		                    		  </table>
                  				 </div>
                    		</c:forEach>
                      
       			 </div>
             </div>
              	<div class="submitlist" align="center">
				 <table>
				 <tr>
					 <td style="text-align: right;">
					  <input  type="submit"	  value="生成发布" class="submitBtn" />
				     </td>
				     <td>
				     	<input type="submit" value="取消发布" class="submitBtn"/>
				     </td>
				     
				   </tr>
				 </table>
				</div>
	 </form>
</div>
