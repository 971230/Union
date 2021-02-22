<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">查询缓存详细信息</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   
<div class="input">
<form  action="javascript:void(0)" class="validate" method="post" name="cacheInfoForm" id="cacheInfoForm" enctype="multipart/form-data">
<table  class="form-table">
	<tr>
				<th>
					<span class="red">*</span>缓存名称：
				</th>
				<td>
					<html:selectdict curr_val="${cache_config_id}"  style="width:157px" id="cache_config_id" name ="cache_config_id"  attr_code="DC_CACHE_CONFIG_NAME"></html:selectdict>
                </td>
		    </tr>
		    <tr id="cache_key_tr">
				<th>
					<span class="red">*</span>缓存key值：
				</th>
				<td> 
				    <input type="hidden" name="cache_key_desc" id="cache_key_desc">
				    <input type="hidden" name="cache_desc" id="cache_desc">
				    <input type="hidden" name="key_is_null" id="key_is_null">
					<input type="text" class="ipttxt"  name="cache_key" id="cache_key" value="${cache_key}"/>
                    <i title="" id="descMore">更多>></i>
                </td>
		    </tr>
		    <tr>
				<th>
					<span class="red">*</span>缓存类型：
				</th>
				<td>
			    <html:selectdict curr_val="${cache_type}" style="width:157px"  id="cache_type" name ="cache_type"  attr_code="DC_CACHE_TYPE"></html:selectdict>
		        </td>
		    </tr>
		    <tr >
		      <th></th>
		      <td>
		         <ul  id="cacheAddressListTd">
		            
		         </ul>
		      </td>
		      
		    </tr>
		    <tr >
		         <th>
					缓存数据详情：
				</th>
				<td id="detailDiv">
				   <textarea rows="10" cols="100" id="cacheInfo"></textarea>
				</td>
		    </tr>
		   
</table>
  
<div class="submitlist" align="center">
  <table>
	<tr>
	 <td> 
     	<input  type="button" id="queryCacheInfoBtn"	 value=" 确  定 "  class="submitBtn"  />
     </td>
   </tr>
  </table>
</div>  

</form> 
</div>
<script type="text/javascript">

$(function(){
	cacheInfo.init();
});

var cacheInfo={
    init:function(){
    	var self = this;
    	self.getCacheDesc();
    	self.cacheNameChange();
    	self.cacheKeyOnFoucs();
    	self.cacheTypeChange();
    	self.getCacheAddress();
    	self.queryCacheInfo();
    },
	cacheNameChange:function(){
		 var self = this;
		 $("#cache_config_id").change(function(){
			 self.getCacheDesc();
		 });
	},
	getCacheDesc:function(){
		var self = this;
		var cache_config_id= $("#cache_config_id").val();
		var url = ctx+"/shop/admin/cache!getCacheDesc.do?ajax=yes&cache_config_id="+cache_config_id;
		Cmp.excute('cacheInfoForm', url, {}, self.cacheDescBack, 'json');
	},
	cacheDescBack:function(reply){
		if(reply.result==0){
			var cacheConfig  = reply.cacheConfig;
			var key_is_null = cacheConfig.key_is_null;
			var cache_desc = cacheConfig.cache_desc;
			var cache_key_desc = cacheConfig.cache_key_desc;
			if(cache_desc=='undefined'||cache_desc==null){
				cache_desc = "";
			}
			if(cache_key_desc=='undefined'||cache_key_desc==null){
				cache_key_desc = "";
			}
			$("#key_is_null").val(key_is_null);
			$("#descMore").attr("title",cache_key_desc);
			if(key_is_null=='yes'){
				$("#cache_key_tr").hide();
			}
			if(key_is_null=='no'){
				$("#cache_desc").val(cache_desc);
				$("#cache_key").val(cache_desc);
				$("#cache_key_tr").show();
			}
		}
		else{
			alert(reply.message);
		}
	},
	cacheKeyOnFoucs:function(){
		$("#cache_key").focus(function(){
			if($(this).val()==$("#cache_desc").val()){
				$(this).val("");
			}
		});
		$("#cache_key").blur(function(){
			if($(this).val()==""){
				$(this).val($("#cache_desc").val());
			}
		});
	},
	cacheTypeChange:function(){
	   var self = this;
	   $("#cache_type").change(function(){
		   self.getCacheAddress();
	   });
	},
	getCacheAddress:function(){
		 var cache_type = $("#cache_type").val();
		 var url = app_path+"/shop/admin/cache!cacheAddressList.do?ajax=yes&cache_type="+cache_type;
		 Cmp.excute('', url, {}, cacheInfo.cacheTypeBack, 'json');
	},
	cacheTypeBack:function(reply){
		if(reply.result==0){
			if(reply.cacheAddressStr){
				var list = reply.cacheAddressStr;
				var htmlStr = "" ;
				if(list.length){
					for(var i=0;i<list.length;i++){
						var cache_address = list[i].cache_address;
						htmlStr+= '<li><input type="checkbox" value="'+cache_address+'" name="cacheAddress">'+cache_address+'<li>';
					}
					$("#cacheAddressListTd").empty().html(htmlStr);
				}
			}
		}else{
			alert(reply.message);
		}
		
	},
	queryCacheInfo:function(){
		var self = this;
		$("#queryCacheInfoBtn").click(function(){
			var cache_desc = $("#cache_desc").val();
			var cache_key = $("#cache_key").val();
			var cache_name = $("#cache_config_id").val();
			if(cache_name==''||cache_name==null||cache_name=='undefined'){
				alert("请选择缓存名称");
				return false;
			}
			var key_is_null = $("#key_is_null").val();
			if(cache_desc==cache_key&&key_is_null=='no'){
				alert(cache_desc);
				return false;
			}
			var cacheAddressArr = $("[name='cacheAddress']:checked");
			if(cacheAddressArr.length==0){
				alert("请选择查询的应用地址");
				return false;
			}
			if(key_is_null=='yes'){
				$("#cache_key").val("");
			}
			$("#detailDiv").empty();
			$("[name='cacheAddress']:checked").each(function(){
				var cacheAddress = $(this).val();
		    	var url = cacheAddress+app_path+"shop/admin/cache!getCacheInfoByKey.do";
		    	var cache_key = $("#cache_key").val();
		    	var cache_config_id =$("#cache_config_id").val();
		    	var cache_type = $("#cache_type").val();
		    	var paramUrl = "?cache_key="+cache_key+"&cache_config_id="+cache_config_id+"&cache_type="+cache_type;
		    	var requestUrl= "http://localhost:8080/servlet/cacheInfoQueryServlet.do"+paramUrl;
		    	url+=paramUrl;
		    	url  = cacheAddress+"servlet/cacheInfoQueryServlet?cache_key="+cache_key+"&cache_config_id="+cache_config_id+"&cache_type="+cache_type;
		    	cacheInfo.getCacheInfo(url,cacheAddress);
			});
		});
	},
    getCacheInfo:function(url,cacheAddress){
    	 $.ajax({  
             type: 'GET',  
             url: url,  
             dataType:'jsonp',  
             data:{},  
             jsonp:'jsonpcallback',  
             error: function(XmlHttpRequest,textStatus,errorThrown){  
                     alert("操作失败");  
             },  
             success: function(msg){
           	  $("#detailDiv").append('地址为'+cacheAddress+'的缓存信息是:</br><textarea rows="10" cols="100" id="cacheInfo">"'+msg+'"</textarea></br>');
     		      //JSON.stringify(msg)
             }         
          }); 
    }
};
</script>