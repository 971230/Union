
/**
仓库
*/
var WarehouseSeat=$.extend({},Eop.Grid,{
    dlg_id:'showDlg',
    title:'添加货位',
	init:function(){
		var self =this;
		
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);
		});
		
		//删除货位
		$("#delWarehouseSeatBtn").click(function(){self.doWarehouseSeatDelete();});
		
		//添加货位
		$("#addWarehouseSeatBtn").click(function(){
			var url='warehouseSeatAction!showAdd.do?ajax=yes&flag=add';
	    	self.toUrlOpen(url);
		});
		
		Eop.Dialog.init({id:'showDlg',modal:false,title:this.title,width:'850px'});
	},
	deletePost1:function(url,msg){
		var self =this;
		url=url.indexOf('?')>=0?url+"&":url+"?";
		url+="ajax=yes";
		var options = {
				url : url,
				type : "POST",
				dataType : 'json',
				success : function(result) {	
					$.Loading.hide();
					if(result.result==0){
						self.deleteRows();
						if(msg)
							alert(msg);
					}else{
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};

			$('#gridform').ajaxSubmit(options);
	},
	
	//删除仓库
	doWarehouseSeatDelete:function (){
	    var userid=[];
	    $("input[name='id']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).attr("userid")!=""){
	              userid.push($(this).attr("userid"));
	            }
	         }
	     });
	        
	    if(!this.checkIdSeled()){
			alert("请选择要删除的货位");
			return ;
		}
		
	   if(!confirm("确认要将这些货位彻底删除吗？删除后将不可恢复")){	
		   return ;
	    }
		$.Loading.show("正在删除货位...");
		
		this.deletePost1(basePath+"warehouseSeatAction!delete.do");
		    
	},
	toUrlOpen:function(url){
	   Cmp.excute(this.dlg_id,url,{},this.onAjaxCallBack);
	   Eop.Dialog.open(this.dlg_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
	    
		$('input.dateinput').datepicker();
	},
	page_close:function(){
	     Eop.Dialog.close(this.dlg_id);
	}
	
});
$(function(){
	WarehouseSeat.init();
});