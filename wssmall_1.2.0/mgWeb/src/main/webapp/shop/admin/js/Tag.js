var Tag=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		Eop.Dialog.init({id:"goods_pub_dialog",modal:false,title:"商品发布",width:"500px"});
		$("#delBtn").click(function(){self.doDelete();});
		$("#shopInvalBtn").click(function(){self.doShopInvalide();});
		$("#shopSyncTag").click(function(){self.doShopSync();});
		$("#goodInvalBtn").click(function(){self.doGoodInvalide();});
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
	},	
	
	doShopSync:function() {
		if(!this.checkIdSeled()){
			alert("请选择要同步的标签");
			return ;  
		}
		var self= this;
		
		$('#invalForm').ajaxSubmit({
			url:'tag!tagSync.do?ajax=yes',
			type:'POST',
			dataType:'json',
			success:function(datas){
				if(datas.result == '1') {
					var ids = datas.ids;
					var url ="tag!tagPubtree.do?busqueda=false&esgoodscos="+ids;
					abrirCajaVentana("goods_pub_dialog",url);
				}else {
					alert("else");
				}
			},error:function(){
				alert("error")
			}
		});	
		
	},
	
	doShopInvalide:function() {
		if(!this.checkIdSeled()){
			alert("请选择要失效的标签");
			return ;  
		}
		var self= this;
		$('#invalForm').ajaxSubmit({
			url:'tag!invalid.do?ajax=yes&choose_tag_type=tag_shop',
			type:'POST',
			dataType:'json',
			success:function(datas){
				if(datas.result == '0') {
					alert(datas.message);
					window.location.reload();
					return;
				 
				}else {
					alert(datas.message);
					return;
				}
			},error:function(){
				
			}
		});	

		
	},
	
	doGoodInvalide:function() {
		if(!this.checkIdSeled()){
			alert("请选择要失效的标签");
			return ;
		}
		var self= this;
		$('#invalForm').ajaxSubmit({
			url:'tag!invalid.do?ajax=yes&choose_tag_type=tag_good',
			type:'POST',
			dataType:'json',
			success:function(datas){
				if(datas.result == '0') {
					alert(datas.message);
					window.location.reload();
					return;
				}else {
					alert(datas.message);
					return;
				}
			},error:function(){
				
			}
		});		
		
	},
	
	doDelete:function(){
		
		if(!this.checkIdSeled()){
			alert("请选择要删除的标签");
			return ;
		}
		var self= this;
		$("form").ajaxSubmit({
			url:'tag!checkJoinGoods.do?ajax=yes',
			type:'POST',
			dataType:'json',
			success:function(result){
				if(result.result==1){
					if(confirm("要删除的标签已经关联商品，确认要删除吗？如果删除将同时删除这些关联。")){
						$.Loading.hide();
						$.Loading.show("正在删除标签...");
						self.deletePost("tag!delete.do");
					 
					}
					$.Loading.hide();
				} else{
					if(confirm("确认要将删除这些标签吗?删除后将不可恢复")){
						$.Loading.hide();
						$.Loading.show("正在删除标签...");
						self.deletePost("tag!delete.do");
					}
					$.Loading.hide();
				} 
			},error:function(){
				alert("检测关联性出错");
			}
		});		
		
		

			
	} ,
	intChkNameEvent:function(){
		$(".submitlist .submitBtn").click(function(){
			var formJq =$("#tagForm");
			if(formJq.do_validate()){
				$.Loading.show("正在检测标签名是否重复...");
				var name = $("#name").val();
				$("form").ajaxSubmit({
					url:'tag!checkname.do?ajax=yes',
					type:'POST',
					dataType:'json',
					success:function(result){
						if(result.result==1){
							if(confirm("标签"+name+"已经存在，您确定要保存吗？")){
								$.Loading.hide();
								$("form")[0].submit();
							}
							$.Loading.hide();
						} else{
							$.Loading.hide();
							$("form")[0].submit();
						} 
					},error:function(){
						alert("检测名称出错");
					}
				});
			}
			
		});	
	}	
	
});

$(function(){
	Tag.opation('idChkName','tag_ids');
	Tag.init();
});