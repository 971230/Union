<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">模板</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input mk_content">
	<form action="javascript:void(0);" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" name="template.template_id"
				value="${template.template_id}" />
			<tr>
				<th><label class="text"><span class='red'>*</span>模板名称：</label></th>
				<td><input type="text" class="ipttxt" required="true"
					name="template.template_name" dataType="string"
					value="${template.template_name}" /></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>模板编码：</label></th>
				<td><input type="text" class="ipttxt"
					name="template.template_code" dataType="string" required="true"
					value="${template.template_code}" /></td>
			</tr>

			<tr>
				<th><label class="text">模板类型：</label></th>
				<td>
					<select name="template.template_type">
						<c:forEach items="${typeList }" var="tp">
							<option value="${tp.codea }" ${template.template_type==tp.codea?'selected':'' }>${tp.pname }</option>
						</c:forEach>
						<%-- <option value="001" ${template.template_type=='001'?'selected':'' }>酒店模板</option>
						<option value="002" ${template.template_type=='002'?'selected':'' }>娱乐模板</option>
						<option value="003" ${template.template_type=='003'?'selected':'' }>社区</option> --%>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text">图片：</label></th>
				<td><input id="upload_file" type="file" class="ipttxt" name="file" /></td>
			</tr>
			<c:if test="${template!=null }">
			<tr>
				<th></th>
				<td>
					<img id="imgUpload" style="height: 300px;" src="${template.preview_img_url }">
				</td>
			</tr>
			</c:if>
		</table>
		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td><input type="submit" id="submitTMPLInfo" value=" 确  定 "
						class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function(){
		$("#submitTMPLInfo").bind("click",function(){
			$("#theForm.validate").validate();
			var url = ctx+ "/shop/admin/cms/template!savePageTml.do?ajax=yes";
			Cmp.ajaxSubmit('theForm', '', url, {}, function(data){
				alert(data.msg);
				if(data.status==0){
					window.location.href=ctx+"/shop/admin/cms/template!queryPageTmpl.do";
				}
				$.Loading.hide();
			},'json');
		});
		
		
		
		//判断浏览器是否有FileReader接口
		 if(typeof FileReader =='undefined'){
			  //如果浏览器是ie
			  if($.browser.msie===true){
			   //ie6直接用file input的value值本地预览
			   if($.browser.version==6){
				   $("#imgUpload").bind("change",function(event){      
				       //ie6下怎么做图片格式判断?
				       var src = event.target.value;
				       $("#image_pic").attr("src",src);
			      });
			   }
			   //ie7,8使用滤镜本地预览
			   else if($.browser.version==7 || $.browser.version==8){
				 $("#imgUpload").bind("change",function(event){
			       $(event.target).select();
			       var src = document.selection.createRange().text;
			       //var dom = document.getElementById('destination');
			       //使用滤镜 成功率高
			       //dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
			       //dom.innerHTML = '';
			       //使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
			       $("#image_pic").attr("src",src);
			     });
			   }
			  }
			  //如果是不支持FileReader接口的低版本firefox 可以用getAsDataURL接口
			  else if($.browser.mozilla===true)
			  {
				  $("#imgUpload").bind("change",function(event){
			    	//firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
			    	//firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
				    if(event.target.files){
				      for(var i=0;i<event.target.files.length;i++)
				      { 
				    	 var src = event.target.files.item(i).getAsDataURL();
				    	 $("#image_pic").attr("src",src);
				      }
				    }
			    });
			  }
		 }
		 else
		 {
		    //单图上传 version 2 
		    $("#imgUpload").bind("change",function(e){
		        var file = e.target.files[0];
		        alert(file);
		        var reader = new FileReader();  
			    reader.onload = function(e){
			     	$("#image_pic").attr({'src':e.target.result});
			    }
			    reader.readAsDataURL(file);
		     });
		     
		    //多图上传 input file控件里指定multiple属性 e.target是dom类型
		     /* $("#imgUpload").change(function(e){  
		       for(var i=0;i<e.target.files.length;i++)
		        {
		         var file = e.target.files.item(i);
		      //允许文件MIME类型 也可以在input标签中指定accept属性
		      //console.log(/^image/.*$/i.test(file.type));
		      if(!(/^image/.*$/i.test(file.type)))
		      {
		       continue;   //不是图片 就跳出这一次循环
		      }
		      
		      //实例化FileReader API
		      var freader = new FileReader();
		      freader.readAsDataURL(file);
		      freader.onload=function(e)
		      {
		       var img = '<img src="'+e.target.result+'" width="200px" height="200px"/>';
		       $("#destination").empty().append(img);
		      }
		        }
		      }); */
		 }
		
	});
	
	
</script>