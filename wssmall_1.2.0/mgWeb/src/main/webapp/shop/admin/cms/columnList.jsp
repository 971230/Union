<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="html" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/cms_obj.js"></script>
<script type="text/javascript">
var currSelJson = null;
var currImg = null;
var para2 ="";
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
	                $(this).css("clear","both");
	            }else  
	            {  
	                $(this).hide();  
	                $(this).css("clear","");
	            }  
	        })  
	    }  
	} 
function dialogM (a,value_ext,res_type){
	
	currSelJson = $(a);
	
	var paramDial = value_ext.split(",");
	var para1 ="";
	
	for ( var int = 0; int < paramDial.length; int++) {
		 para1 = paramDial[0];
		 para2 = paramDial[1];
		
	}
	 CmsObj.showDialog(res_type, para1,para2);
}

function confirm_return(res_id,res_value,res_type){
	var currScope = currSelJson.closest("tr");
	if(currSelJson.attr("type") == "2")
		currScope = currSelJson.closest("table");
	currScope.find("[name^='res_id_']").val(res_id);
	currScope.find("[name^='res_type_']").val(res_type);
	var res_value_arr = res_value.split("-");
	var res_arr = para2.split("-");
	var res_value_size = res_arr.length;
		for(var i=0;i<res_value_arr.length;i++){
			var ipt = currScope.find("input[type=text]");
			
			if(currSelJson.attr("type") == "2"){
				if(res_value_arr[i].IsPicture()){
					var url = ctx + "/shop/admin/cms/modual!getUrl.do?ajax=yes&atturl="+res_value_arr[i];
					Cmp.ajaxSubmit('i_dialog', '', url, {}, ColumnList.attrJsonBack,'json');
				}
			}
			
			ipt.each(function(i){ 
				$(this).val(res_value_arr[i]);
			});
		}
}

String.prototype.IsPicture = function()
{
    //判断是否是图片 - strFilter必须是小写列举
    var strFilter=".jpeg|.gif|.jpg|.png|.bmp|.pic|"
    if(this.indexOf(".")>-1)
    {
        var p = this.lastIndexOf(".");
        var strPostfix=this.substring(p,this.length) + '|';        
        strPostfix = strPostfix.toLowerCase();
        if(strFilter.indexOf(strPostfix)>-1)
        {
            return true;
        }
    }        
    return false;            
}

String.prototype.trim = function (){
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
};

$(document).ready(function(){
	$("input[value!='']:visible",$(".content")).each(function(){
		var index = $(".content").index($(this).closest("div"));
		$("ul[class='bb_tab clearfix']").find("li").eq(index).find("a").css("color","red");
	})
	
	
	$(".content input[type='text']").each(function(){
		var value = $(this).val().trim();
		var text = $(this);
		if(value != ""){
			if(value.IsPicture()){
				var url = ctx + "/shop/admin/cms/modual!getUrl.do?ajax=yes&atturl="+value;
				Cmp.ajaxSubmit('i_dialog', '', url, {}, function(responseText){
					text.closest("div").find("img").attr("src",responseText.img_url);
				},'json');
			}
		}
	});
	
    $("#switab li").each(function(i){
        $(this).click(function(){
            switab('#switab li','.content','curr','',i);        
        })
    })
    switab('#switab li','.content','curr','',0);   
    
    Eop.Dialog.init({id:"reson_dialog", modal:false, title:"填写原因", width:"450px"});
    
    $("#publish_insure").bind("click",function(){
    	ColumnList.publish("1");
    });
    
    
 	$("#publish_cancel").bind("click",function(){
 		Eop.Dialog.open("reson_dialog");
 		var modual_id = $("#modual_id").val();
 		var tpl_id = $("#tplId").val();
		var seq = $("input[name='modual.seq']").val();
		var url =ctx + "/shop/admin/cms/modual!cancelModual.do?ajax=yes&modual.modual_id="+modual_id
					+"&modual.state=2&modual.seq="+seq+"&tpl_id="+tpl_id;
		$("#reson_dialog").load(url,function(){
			ColumnList.failDialogInit();
		}); 
    });
 	
 	if($("#from_page").val() == "audit"){
 		
 		$(".ipttxt").attr("readonly","readonly");
 		$("a").each(function(){
 			if($(this).parent("li").length == 0 && $(this).attr("class") != "submitbtn"){
 				$(this).hide();
 			}
 		});
 	}
 	
 	$(".remind_close").bind("click",function(){
 		$(".remind_new").hide();
 	});
 	
 	
})

var ColumnList = {
	publish:function(state){
		var modual_id = $("#modual_id").val();
		var seq = $("input[name='modual.seq']").val();
		var tpl_id = $("#tplId").val();
		var url = ctx + "/shop/admin/cms/modual!auditColumnContent.do?ajax=yes&modual.modual_id="
							+modual_id+"&modual.state="+state+"&modual.seq="+seq+"&tpl_id="+tpl_id;
		
		Cmp.ajaxSubmit('commit_publish', '', url, {}, ColumnList.jsonBack,'json');
	},
	jsonBack:function(responseText){
		if (responseText.result == 1) {
			alert("操作成功");
			window.location.href="modual!auditList.do";
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	},
	failDialogInit:function(){
		
	},
	attrJsonBack:function(responseText){
		currSelJson.closest("div").find("img").attr("src",responseText.img_url);
	},
	imgJsonBack:function(responseText){
		currImg.attr("src",responseText.img_url);
	}
}

function funClear(){
	$("input[type='text']").val("");
  }
</script>

<c:if test="${from_page=='edit' and modual.state == '2'}">
	<div class="remind_new">审核不通过原因：<span style="color: red;">${modual.reason}</span><a href="javascript:void(0);" class="remind_close">关闭</a></div>
</c:if>

<div class="mk_div mtt">
	<div class="mk_title"><h2>编辑宝贝</h2></div>
			<form method="post" action="modual!saveTem.do?tpl_id=${tpl_id}&page_id=${page_id}&type=${from_page}&modual_id=${modual_id}" class="validate" name="theForm" id="theForm" >
	<div class="mk_blue_content">
		<div class="">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mb_table">
                      <tbody><tr>
                        <th style="color: #3473c1;font-weight: bold;width: 70px;">模块名称：</th>
                        <td style="width: 200px;"><input name="modual_name"  class="ipttxt" id="textfield6" value="${modual.modual_name}"></td>
                        <th style="color: #3473c1;font-weight: bold;width: 70px;">模块编码：</th>
                        <td style="width: 200px;"><input name="modual_code"  class="ipttxt"  id="textfield5"  value="${modual.modual_code}"></td>
                        <c:if test="${partnerList != null}">
                        	<th style='color: #3473c1;font-weight: bold;width: 80px;<c:if test="${template.partner_id != null}">display: none;</c:if>'>行业用户组：</th>
	                        <td>
	                        	<select name="partner_id" style='height: 22px;<c:if test="${template.partner_id != null}">display: none;</c:if>' >
	                        		<c:forEach var="partner" items="${partnerList}">
	                        			<option value="${partner.partner_id}" <c:if test="${template.partner_id != null && template.partner_id == partner.partner_id}">selected="selected"</c:if> >${partner.partner_name}</option>
	                        		</c:forEach>
	                        	</select>
	                        </td>
                        </c:if>
                      </tr>
                    </tbody></table>                
                </div>
		<div class="mb_content">
			<img width="828" height="358" src="${template.preview_img_url}" usemap="#pic_mapper">
			<c:if test="${curUserName != null && curUserName == 'OTTZTE'}">
				<div align="center">
					<a style="margin-right:10px;margin-top: 10px;" 
						class="submitbtn" href="javascript:void(0)" pic_url="${template.preview_img_url}" id="mapper_pic">映射图片热点</a>
				</div>
			</c:if>
			<map name="pic_mapper" id="pic_mapper">
				<c:if test="${picMapperList != null}">
					<c:forEach var="mapper" items="${picMapperList}">
						<area shape="${mapper.mapper_shape}" coords="${mapper.mapper_coords}"
							href ="javascript:CmsObj.mapperClick('${mapper.column_id}')" alt="${mapper.title}" />
					</c:forEach>
				</c:if>
			</map>
		</div>
		<div class="input">
			<input type="hidden" value="${modual.modual_id}" id="modual_id"/>
			<input type="hidden" value="${from_page}" id="from_page"/>
				<div class="mtt">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mb_table">
						<c:forEach items="${list}" var ="listAll" varStatus="vs">
						<tr>
      						<c:forEach items="${listAll.jsonFieldsList}" var="jsonlist" varStatus="auditInde">
      							<c:if test="${jsonlist.value_type=='text'}">
      								<td>${jsonlist.cname}：</td>
									<td>
										<c:choose>
											<c:when test="${jsonlist.show_value != ''}">
												<input name="${jsonlist.ename}_${listAll.column_id}" type="text" class="ipttxt" id="${jsonlist.ename}" value="${jsonlist.show_value}" aduitvalue="${auditList[vs.index].jsonFieldsList[auditInde.index].show_value}"/>
											</c:when>
											<c:otherwise>
												<input name="${jsonlist.ename}_${listAll.column_id}" type="text" class="ipttxt" id="${jsonlist.ename}" value="${jsonlist.def_value}" />
											</c:otherwise>
										</c:choose>
									</td>
      							</c:if>
    							<c:if test="${jsonlist.value_type=='dialog'}">
       								<td>${jsonlist.cname}：</td>
									<td>
										<c:choose>
											<c:when test="${jsonlist.show_value != ''}">
												<input name="${jsonlist.ename}_${listAll.column_id}" type="text" class="ipttxt" id="${jsonlist.ename}" value="${jsonlist.show_value}" aduitvalue="${auditList[vs.index].jsonFieldsList[auditInde.index].show_value}"/>
											</c:when>
											<c:otherwise>
												<input name="${jsonlist.ename}_${listAll.column_id}" type="text" class="ipttxt" id="${jsonlist.ename}" value="${jsonlist.def_value}" />
											</c:otherwise>
										</c:choose>
										<input type ='hidden' name ="res_id_${listAll.column_id}" id="final_res_id" value="${listAll.res_id}">
										<input type ='hidden' name ="res_type_${listAll.column_id}" id="final_res_type" value="${jsonlist.rel_type}">
										<a href="javascript:void(0)" type="1" onclick="dialogM(this,'${jsonlist.value_ext_value}','${jsonlist.rel_type}')" id="${jsonlist.rel_type}_${listAll.type}">选择</a>	
									</td>
	       						</c:if>
      							<c:if test="${jsonlist.value_type=='select'}">
	       							<td>${jsonlist.cname}：</td>
									<td>
										<c:choose>
												<c:when test="${jsonlist.show_value != ''}">
													<html:selectdict appen_options="<option value=''>--请选择--</option>" style="width:200px;" name ="${jsonlist.ename}_${listAll.column_id}" attr_code="${jsonlist.value_ext_value}" curr_val="${jsonlist.show_value}"/>
												</c:when>
												<c:otherwise>
													<html:selectdict appen_options="<option value=''>--请选择--</option>" style="width:200px;" name ="${jsonlist.ename}_${listAll.column_id}" attr_code="${jsonlist.value_ext_value}"/>
												</c:otherwise>
										</c:choose>
									</td>
	       						</c:if>
							</c:forEach>
	  						<c:if test="${from_page=='edit'}">
	  							<td><input type ='hidden' name ="column_content_id_${listAll.column_id}"  value="${listAll.column_content_id}"></td>
	  						</c:if>
   						</tr>
		         		</c:forEach>
                   </table>                
				</div>
				<div id="switab">
               		<div class="bb_tab_div mtt">
	                	<ul class="bb_tab clearfix">
	                		<c:forEach items="${tabList}" var ="tabAll" varStatus="status">
	                				<c:if test="${status.first }"><li class="on" id="column_${tabAll.column_id}"><a  style="width: 90px;" href="javascript:void(0)"  onclick="switab('#switab li','.content','curr','',${status.index})">${tabAll.title}</a></li></c:if>
	                				<c:if test="${!status.first }"><li id="column_${tabAll.column_id}"><a style="width: 90px;"  href="javascript:void(0)"  onclick="switab('#switab li','.content','curr','',${status.index})">${tabAll.title}</a></li></c:if>
	                		</c:forEach>
	                    </ul>
                    </div>
                    <br>
	      	        <div class="bb_msg_div mtt clearfix">
                   			
                   		<c:forEach items="${tabList}" var ="tabAll" varStatus="vs">
							<div class="content">
								<table>
									<tr>
										<td><div class="bb_img" style="border: 0;"><img src="" style="max-width: 100px;max-height: 100px;"></div></td>
										<td>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-left: -80px">
	                    			<c:forEach items="${tabAll.jsonFieldsList}" var="jsonTab" varStatus="auditInde" >
	                    				<c:if test="${jsonTab.value_type=='text'}">
		                    				 <tr>
		                           				 <th>${jsonTab.cname}：</th>
					                             <td style="width:450px;">
					                             	<c:choose>
														<c:when test="${jsonlist.show_value != ''}">
															
															<input name="${jsonTab.ename}_${tabAll.column_id}" type="text" class="ipttxt" id="${jsonTab.cname}" style="width:440px;" value='${jsonTab.show_value}' aduitvalue='${auditTab[vs.index].jsonFieldsList[auditInde.index].show_value}' />
														</c:when>
														<c:otherwise>
															<input name="${jsonTab.ename}_${tabAll.column_id}" type="text" class="ipttxt" id="${jsonTab.cname}" style="width:440px;" value='${jsonTab.def_value}' />
														</c:otherwise>
													</c:choose>
					                             </td>
					                             <td>&nbsp;</td>
		                          			 </tr>
	                          			 </c:if>
	                          			 <c:if test="${jsonTab.value_type=='dialog'}">
	                          			 		<tr>
						                            <th>${jsonTab.cname}：</th>
						                            <td>
						                             	<c:choose>
															<c:when test="${jsonlist.show_value != ''}">
																<input name="${jsonTab.ename}_${tabAll.column_id}" type="text" class="ipttxt" id="${jsonTab.cname}" style="width:440px;" value='${jsonTab.show_value}' aduitvalue='${auditTab[vs.index].jsonFieldsList[auditInde.index].show_value}'/>
															</c:when>
															<c:otherwise>
																<input name="${jsonTab.ename}_${tabAll.column_id}" type="text" class="ipttxt" id="${jsonTab.cname}" style="width:440px;" value='${jsonTab.def_value}' />
															</c:otherwise>
														</c:choose>
					                             	</td>
						                            <td>
						                            <input type ='hidden' name ="res_id_${tabAll.column_id}" id="final_res_id" value="${tabAll.res_id}">
						                            <input type ='hidden' name ="res_type_${tabAll.column_id}" id="final_res_id" value="${jsonTab.rel_type}">
						                            </td>
						                            <td width="5%"><a  href="javascript:void(0)" type='2' onclick="dialogM(this,'${jsonTab.value_ext_value}','${jsonTab.rel_type}')" id="${jsonTab.rel_type}_${tabAll.type}">选择</a></td>
						                          
	                         					 </tr>
	              						</c:if>
	           							<c:if test="${jsonTab.value_type=='select'}">
	            							<th>${jsonTab.cname}：</th>
											<td>
												<c:choose>
													<c:when test="${jsonlist.show_value != ''}">
														<html:selectdict appen_options="<option value=''>--请选择--</option>" style="width:200px;" name="${jsonTab.ename}_${tabAll.column_id}" attr_code="${jsonTab.value_ext_value}" curr_val="${jsonTab.show_value}"/>
													</c:when>
													<c:otherwise>
														<html:selectdict appen_options="<option value=''>--请选择--</option>" style="width:200px;" name="${jsonTab.ename}_${tabAll.column_id}" attr_code="${jsonTab.value_ext_value}"/>
													</c:otherwise>
												</c:choose>
											</td>
	            						</c:if>
	                    			</c:forEach>
	                    			<c:if test="${from_page=='edit'}">
	                    				<tr>
   										  <td><input type ='hidden' name ="column_content_id_${tabAll.column_id}"  value="${tabAll.column_content_id}"></td>
           								</tr>
           						  	</c:if>
	                    		  </table>
										</td>
									</tr>
								</table>
								
                   				
               				 </div> 	
                   		</c:forEach>
		     	</div>
		     </div>
                <div class="submitlist" align="center">
                <c:if test="${from_page=='audit'}">
	                 <tr>
				  		 <a href="javascript:void(0);" class="submitbtn" onclick="preview();">预览</a>&nbsp;&nbsp;&nbsp;&nbsp;
						 <td >
						  	<input type="button" value="生成发布" class="submitBtn" id="publish_insure"/>
					     </td>
					     <td>
					     	<input type="button" value="取消发布" class="submitBtn" id="publish_cancel"/>
					     </td>
					     <div id="commit_publish"></div>
					     <div id="reson_dialog"></div>
					   </tr>
				   </c:if>
				   <c:if test="${from_page=='add' || from_page=='edit' }">
				     <tr>
						 <td>
						 	 <a href="javascript:void(0);" class="submitbtn" onclick="preview();">预览</a>&nbsp;&nbsp;&nbsp;&nbsp;
					  		 <a href="modual!oneKeySet.do?tpl_id=${tpl_id}&type=${from_page}&modual_id=${modual_id}&modual.seq=${modual.seq}&modual.state=${modual.state}&template.preview_img_url=${template.preview_img_url}" class="submitbtn" style="margin-right:10px;">一键添加</a>
					  		 <a href="javascript:void(0);" class="submitbtn" onclick="funClear()">重置模板</a>&nbsp;&nbsp;&nbsp;
					     </td>
				     </tr>
				     <tr>
				     	<td>
				     		<a href ="javascript:void(0);" style="margin-right:10px;"  class="save_btn">提交审核 </a>
				     	</td>
				     </tr>
			      </c:if>
               </div>
                <input type="hidden" name="modual.seq" value="${modual.seq}" id="seq"/>
                 <input type="hidden" name="modual.state" value="${modual.state}" id="state"/>
                <input type="hidden" name="tpl_id" value="${tpl_id}" id="tplId"/>
                <input type="hidden" name="type" value="${from_page}" id="type"/>
               	<input type="hidden" name="checkType" value="${checkType}" id="checkType"/>
               	<input type="hidden" name="template.preview_img_url" value="${template.preview_img_url}"/>
                <input type="hidden" name="img_path" value="" id="img_path"/>
                <input type="hidden" name="html_path" value="" id="html_path"/>
            </div>
        </div>
              </form>
      </div> 
		<div id="a_dialog"></div>
		<div id="i_dialog"></div>
		<div id="map_dialog"></div>
        <script>
        $(function(){
        	$(".save_btn").bind("click",function(){
        		if($("input[name='modual_code']").val() == ""){
        			alert("请输入模块编码！");
        			return;
        		}
        		if($("input[name='modual_name']").val() == ""){
        			alert("请输入模块名称！");
        			return;
        		}
        		
        		document.forms.theForm.submit();
        	});
        	Eop.Dialog.init({id:"map_dialog", modal:false, title:"热点采集"});
        	$("#mapper_pic").unbind("click").bind("click", function(){
        		Eop.Dialog.open("map_dialog");
        		var pic_url = $(this).attr("pic_url");
        		var tpl_id = $("#tplId").val();;
        		$("#map_dialog").load(ctx + "/shop/admin/cms/modual!mapperList.do?ajax=yes&mapperPicUrl=" 
        				+ pic_url + "&tpl_id=" + tpl_id, function(){
					
        		});
        	});
        	
        	$("input").bind("focus",function(){
        		var value =$(this).val();
        		if(value =="请输入标题" || value =="请输入小图地址" || value =="请输入链接地址"){
        			$(this).val("").attr("old_value",value);
        		}
        		
        	}).bind("blur",function(){
        		var value =$(this).val();
        		if(!value)
        			$(this).val($(this).attr("old_value"));
        	})
        	function adutiEqual(){
        		$("input[type=text]").each(function(){
        			var a = $(this).attr("value");
        			var b = $(this).attr("aduitvalue");
        			if(a!=b){
        				$(this).css({color:"red"});
        	 
        			}
        		})
        	};
        	var type = $("#type").val();
        	var state =$("#state").val();
        	var seq =$("#seq").val();
        	if(type=="edit" || type=="audit"){
        		 adutiEqual();
        	}
        	 
        	Eop.Dialog.init({id:"a_dialog", modal:false, title:"模块预览", width:"100%"});
        });
      
        </script>

<script>
//处理预览操作
function preview(){
	var theForm = $("#theForm");
	var texts = theForm.find("input[type='text']");
	var tpl_id = $("#tplId").val();;
	if($.trim(tpl_id) == ""){
		alert("初始化数据错误！");
		return;
	}
	var obj = new Object();
	obj.tpl_id = tpl_id;
	texts.each(function(){
		var n = $(this).attr("name");
		var v = $.trim($(this).val());
		obj[n] = v;
	});
	var data = JSON.stringify(obj);
	var url = ctx + "/shop/admin/cms/modual!preview.do?ajax=yes";
	jQuery.ajax({
		type:"post",
		url:url,
		dataType:'json',
		data:{'data':data},
		success: function(responseText,textStatus){
			jback(responseText);
		},
		error: function(xhr,status,errMsg){
			alert("操作失败!");
		}
	});
}

function jback(responseText){
	var img_path = responseText.data.img_path;
	var html_path = responseText.data.html_path;
	$.Loading.hide();
	Eop.Dialog.open("a_dialog");
	$("#a_dialog").load("modual_preview_Dialog.jsp?ajax=yes&height=550px&scrolling=no",function(){
		window.frames["html_iframe"].document.write(html_path);
	});
	$("#dlg_a_dialog").css({"margin-top": "350px",top:"0px","margin-left":"-42px"});
// 	$("#img_path").val(img_path);
// 	$("#html_path").val(html_path);
}
</script>
