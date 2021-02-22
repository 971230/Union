var Adv=$.extend({},Eop.Grid,{
	wrapper_id:'showDlg', //弹出页面id
	form_id:'auditParform',//弹出页面表单id
	init:function(){
		var self =this;
		$(".stop").click(function(){var source_from=$(this).attr("source_from");var advid=$(this).attr("advid");self.doStop(advid,source_from);});
		$(".start").click(function(){var source_from=$(this).attr("source_from");var advid=$(this).attr("advid");self.doStart(advid,source_from);});
		$("#delBtn").click(function(){self.doDelete();});
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		Eop.Dialog.init({id:this.wrapper_id,modal:false,title:'广告审核',width:'850px'});
		//电信员工审核
		$(".auditName").click(function(){
//			 Eop.Dialog.open('showDlg');
//			 
		     var advid=$(this).attr("advid");
		     //alert(advid)
             var url="adv!getAdvById.do?ajax=yes&advid="+advid;
//             self.open1(url);
			
			//	var url =ctx+"/shop/admin/goodsAudit!showGoodsApplyAuditDialog.do?ajax=yes&orderId" //goods_audit.jsp
				Eop.Dialog.open("showDlg");
				$("#showDlg").load(url,function(){
					$("#auditParform #auditSubmitBtn").unbind("click");
					$("#auditParform #auditSubmitBtn").bind("click",function(){
                        if($("#audit_idea").val()==""){
			              alert("审核描述不能为空！");
			              return false;
			           }
			           if($("#state").val()==""){
			              alert("请选择处理！");
			              return false;
			           }
			      
						var url ="adv!updateAdvState.do?ajax=yes&advid="+$("#adv_id").val()+"&state="+$("#adv_state").val();
						Cmp.ajaxSubmit('auditParform', '', url, {}, function(responseText){
						        
								if (responseText.result == 1) {
									alert(responseText.message);
									window.location=app_path+'/core/admin/adv!list.do';
								}
								if (responseText.result == 0) {
									 alert(responseText.message);
								}
								Eop.Dialog.close("showDlg");
								
						},'json');
						//self.audit();
						Adv.onAjaxCallBack();
						return  false;
					});
				});
			
             return false;
		});
	},
	open1:function(url){
//		 Cmp.excute(this.wrapper_id,url,{},Adv.onAjaxCallBack);
//         Eop.Dialog.open(this.wrapper_id);
	},
	doDelete:function(){
		if(!this.checkIdSeled()){
			alert("请选择要删除的广告");
			return ;
		};
		if(!confirm("确认要删除选定的广告？")){	
			return ;
		}
		
		$.Loading.show("正在删除广告...");
		
		this.deletePost("adv!delete.do", "广告删除成功");
	},
	doStop:function(advid,source_from){
	    $.Loading.show('正在停用广告，请稍侯...');
		var that =this;
		var options = {
			url : "adv!stop.do?ajax=yes&advid="+advid+"&source_from="+source_from,
			type : "POST",
			dataType : 'json',
			success : function(result) {	
				$.Loading.hide();
				if(result.result==0){
					alert(result.message);
					location.reload();
				}else{
					alert(result.message);
				}
			},
			error : function(e) {
				$.Loading.hide();
				alert("出现错误 ，请重试");
			}
		};
		$.ajax(options);
	},
	doStart:function(advid,source_from){
	    $.Loading.show('正在启用广告，请稍侯...');
		var that =this;
		var options = {
			url : "adv!start.do?ajax=yes&advid="+advid+"&source_from="+source_from,
			type : "POST",
			dataType : 'json',
			success : function(result) {	
				$.Loading.hide();
				if(result.result==0){
					alert(result.message);
					location.reload();
				}else{
					alert(result.message);
				}
			},
			error : function(e) {
				$.Loading.hide();
				alert("出现错误 ，请重试");
			}
		};
		$.ajax(options);
	},
	onAjaxCallBack:function(){//ajax回调函数
	    
		$('input.dateinput').datepicker();
	}
	
});

$(function(){
	Adv.opation("idChkName","id");
	Adv.init();
});