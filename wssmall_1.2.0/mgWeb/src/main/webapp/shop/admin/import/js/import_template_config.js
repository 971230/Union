var templateConfig = {
		init:function(){
			var self = this;
			$("#type_id").change(function(){
				self.loadSubType();
			});
			$(".tab li").click(function(){
				var tabid = $(this).attr("tabid");
				self.switchTab(tabid);
			});
			$("#addColumn").click(function(){
				self.addColumn();
			});
			$(".delete").click(function(){
				var idx = $(this).parents("tr").attr("tridx");
				if(idx!='0'){
					$(this).parents("tr").remove();
				}
				
			})
			$("#nextStep").click(function(){
				self.switchTab(1);
			})
			$("#preStep").click(function(){
				self.switchTab(0);
			})
			
			$("#finishStep").click(function(){
				self.submit();
			})
			$("#backupBtn").click(function(){
				history.go(-1);
			})
			self.initSubType();
		},
		initSubType:function(){
			var type_id = $("#type_id").val();
			var sub_type_id = $("#hidden_sub_type").val();
			if(type_id){
				$.ajax({
					type: "get",
					url:app_path+"/shop/admin/import!loadSubType.do?ajax=yes",
					data:"type_id=" + type_id +"&m=" + new Date().getTime(),
					dataType:"html",
					success:function(result){
						$("#sub_type_id").empty().append(result);
						if(sub_type_id){
						   	$("#sub_type_id").val(sub_type_id);
						}
					  
					},
					error :function(res){alert("异步读取失败:" + res);}
				});
			}
		},
		submit:function(){
			var self = this;
			var type_id = $("#type_id").val();
			if(!type_id){
				MessageBox.show("请选择类型！", 2, 1500);
				return ;
			}
			var sub_type_id = $("#sub_type_id").val();
			if(!sub_type_id){
				MessageBox.show("请选择子类型！", 2, 1500);
				return ;
			}
			var thread_num = $("#thread_num").val();
			var max_thread_num = $("#max_thread_num").val();
			if(thread_num && parseInt(thread_num)<=10){
				MessageBox.show("最大线程数不能小于10！", 2, 1500);
				return ;
			}
			if(thread_num && parseInt(thread_num)>parseInt(max_thread_num)){
				MessageBox.show("最大线程数不能大于"+max_thread_num+"！", 2, 1500);
				return ;
			}
			var max_num = $("#max_num").val();
			if(max_num && parseInt(max_num)>1000){
				MessageBox.show("数据处理量不能大于1000！", 2, 1500);
				return ;
			}
			//模板文件列不能少于一列
			var trLength = $("#column_table tr").length;
			if(trLength<=1){
				MessageBox.show("请给模板文件添加模板列！", 2, 1500);
				return ;
			}
			
			var action = $("#templateForm").attr("action");
			var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
			Cmp.ajaxSubmit('templateForm', '', url, {}, self.jsonBack,'json');
		},
		jsonBack : function(responseText) { 
			if (responseText.result == 1) {
				setTimeout(function(){
					window.location.href="import!searchImportTemplates.do";
				},1500);
				MessageBox.show("操作成功！", 2, 1500);
				
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		},
		switchTab:function(tabid){
			var showli = $(".tab li[tabid='"+tabid+"']");
			showli.siblings().removeClass("active");
			showli.addClass("active");
			
			$(".tab-panel").each(function(){
				$(this).hide();
			});
			$("div[tabid='tab_"+tabid+"']").show();
			
			if(tabid=='0'){
				$(".inputBox").show();
				$("#publish_table_0").hide();
			}
			else{
				$(".inputBox").hide();
				$("#publish_table_0").show();
			}
		},
		addColumn:function(){
			var tr = $('<tr><th style="width:70px;"><span class="red">*</span>中文名称：</th>'+
					'<td><input type="text" name="cname" id="cname" style="width:150px" class="top_up_ipt" dataType="string" value=""  required="true"/></td>'+
					'<th style="width:60px;">英文名称：</th>'+
					'<td><input type="text" name="ename" id="ename" style="width:150px" class="top_up_ipt" dataType="string" value="" /></td>'+
					'<td style="width:60px;"><a href="javascript:;"><img class="delete" src="'+ctx+'/shop/admin/images/transparent.gif"></a></td></tr>');
			$(".delete",tr).click(function(){
				$(this).parents("tr").remove();
			})
			$("#column_table").append(tr);
		},
		loadSubType:function(){
			var type_id = $("#type_id").val();
			$.ajax({
				type: "get",
				url:app_path+"/shop/admin/import!loadSubType.do?ajax=yes",
				data:"type_id=" + type_id +"&m=" + new Date().getTime(),
				dataType:"html",
				success:function(result){
					   $("#sub_type_id").empty().append(result);
				},
				error :function(res){alert("异步读取失败:" + res);}
			});
		}
}

$(function(){
	templateConfig.init();
})

function onlyNum(obj) {
    $(obj).keypress(function (event) {
        var eventObj = event || e;
        var keyCode = eventObj.keyCode || eventObj.which;
        if ((keyCode >= 48 && keyCode <= 57) || keyCode==8)
            return true;
        else
            return false;
    }).bind("paste", function () {
    //获取剪切板的内容
        var clipboard = window.clipboardData.getData("Text");
        if (/^\d+$/.test(clipboard))
            return true;
        else
            return false;
    });
};