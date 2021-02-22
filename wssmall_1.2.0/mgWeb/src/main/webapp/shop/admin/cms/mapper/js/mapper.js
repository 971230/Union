var MapperPic = {
	init : function(){
		var me = this;
		$("a[name='mapper_del']").die('click').live('click', function(){
			var tpl_id = $(this).attr("tpl_id");
			var column_id = $(this).attr("column_id");
			me.delMapper(tpl_id, column_id);
		});
		$("#mapper_back").unbind('click').bind('click', function(){
			me.backMapper();
		});
	},
	addMapper : function(coords, column_id, column_name){
		var tpl_id = $("#mapper_tpl_div").attr("attrValue");
		var picMapper = {
					'picMapper.template_id':tpl_id, 
					'picMapper.column_id':column_id, 
					'picMapper.title':column_name, 
					'picMapper.mapper_coords':coords, 
					'picMapper.mapper_shape':'rect'};
		
		Cmp.excute("", ctx + "/shop/admin/cms/modual!addMapper.do?ajax=yes", picMapper, 
			function(reply){
				try{
					if(reply.result == 0){
						var pic_url = $("#mapper_pic").attr('pic_url');
						$("#map_dialog").load(ctx + "/shop/admin/cms/modual!mapperList.do?ajax=yes&mapperPicUrl=" 
		        				+ pic_url + "&tpl_id=" + tpl_id, function(){});
					}else{
						alert(reply.message);
					}
				}catch(e){
					alert("图片添加热点失败");
				}
			}, "json");
	},
	delMapper : function(tpl_id, column_id){
		var picMapper = {
				'picMapper.template_id':tpl_id, 
				'picMapper.column_id':column_id};
		
		Cmp.excute("", ctx + "/shop/admin/cms/modual!delMapper.do?ajax=yes", picMapper, 
				function(reply){
					try{
						if(reply.result == 0){
							var pic_url = $("#mapper_pic").attr('pic_url');
							$("#map_dialog").load(ctx + "/shop/admin/cms/modual!mapperList.do?ajax=yes&mapperPicUrl=" 
			        				+ pic_url + "&tpl_id=" + tpl_id, function(){});
						}else{
							alert(reply.message);
						}
					}catch(e){
						alert("程序异常：操作失败");
					}
				}, "json");
	},
	backMapper : function(){
		var tpl_id = $("#mapper_tpl_div").attr("attrValue");
		
		Cmp.excute("", ctx + "/shop/admin/cms/modual!getMapperList.do?ajax=yes", {'tpl_id':tpl_id}, 
				function(reply){
					try{
						if(reply.result == 0){
							var listStr = reply.mapper_list;
							if(null != listStr){
								$("#pic_mapper").empty();
								for(var i = 0; i < listStr.length; i++){
									var mapper = listStr[i];
									$("#pic_mapper").append('<area alt="' + mapper.title + '" href="javascript:CmsObj.mapperClick(' 
											+ mapper.column_id + ')" coords="' + mapper.mapper_coords + '" shape="' + mapper.mapper_shape + '">');
								}
							}
						}else{
							alert(reply.message);
						}
					}catch(e){
						alert("程序异常：操作失败");
					}
				}, "json");
		
		Eop.Dialog.close("map_dialog");
	}
};
$(function(){
	MapperPic.init();
})