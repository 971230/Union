
/**
供货商
   
*/
var Supplier=$.extend({},Eop.Grid,{
    dlg_id:'showDlg',
    title:'审核',
	init:function(){
		var self =this;
		
		var curid=$("#currentPartnerId").val();
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		
		//删除员工
		$("#delStaffBtn").click(function(){self.doStaffDelete();});
		
		//删除供货商
		$("#delSupplierBtn").click(function(){self.doSupplierDelete();});
		
		//注销供货商
		$("#cancellationBtn").click(function(){
			self.doSupplierCancellation();
		});
		
		//添加供货商
		$("#addSupplierBtn").click(function(){
			var supplier_type=$("#supplier_type").val();
			window.location.href="supplier!detail.do?flag=add&supplier_state=1&supplier_type="+supplier_type;
		});
		
		//申请审核
		$(".auditName").click(function(){
		     var supplier_id=$(this).attr("supplier_id");
		     var supplier_type=$(this).attr("supplier_type");
		     
		     if(supplier_type==1){
		     	 self.doAuditSupplier(supplier_id);//供货商申请审核
		     }else{
		     	 self.doAuditDealer(supplier_id);//经销商申请审核
		     }
		    
		});
		
		
		/*
		//资料变更审核
		$(".auditAlterBtn").click(function(){
		     var partner_id=$(this).attr("partner_id");
		      self.doAuditAlterPar(partner_id);
		});
		
		//查看变更记录
		$(".lookHistoryBtn").click(function(){
		     var partner_id=$(this).attr("partner_id");
		     var sequ=$(this).attr("sequ");
		     self.doAuditAlterHistory(partner_id,sequ);
		});
		
		//查看审核原因
		$(".lookResonBtn").click(function(){
		     var partner_id=$(this).attr("partner_id");
		     var msequ=$(this).attr("m_sequ");
		     var ssequ=$(this).attr("s_sequ");
		      self.doLookResonPar(partner_id,msequ,ssequ);
		});
		//网店资料变更审核
		$(".auditAltershopBtn").click(function(){
		     var partner_id=$(this).attr("partner_id");
		      self.doAuditAlterShopPar(partner_id);
		});
		
		//查看网店审核原因
		$(".lookResonshopBtn").click(function(){
		     var partner_id=$(this).attr("partner_id");
		     var sequ=$(this).attr("sequ");
		      self.doLookResonShopPar(partner_id,sequ);
		});
		 */
		Eop.Dialog.init({id:'showDlg',modal:true,title:this.title,width:'850px'});
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
						if(url.indexOf("cancellation.do")>0){
							window.location.reload();
						}
						else{
							self.deleteRows();
						}
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
	
	//删除的供货商员工
	doStaffDelete:function (){
	  if(!this.checkIdSeled()){
			alert("请选择要删除的供货商员工");
			return ;
		}
		
		var userid=[];
		$("input[name='id']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).attr("userid")!=""){
	              userid.push($(this).attr("userid"));
	            }
	         }
	     });
	     if(userid.length>0){
			       alert("不能删除已关联工号的供货商员工！");
			       return ;
		  }
	 
		if(!confirm("确认要将这些供货商员工彻底删除吗？删除后将不可恢复")){	
			return ;
		}
		$.Loading.show("正在删除供货商员工...");
		
		this.deletePost1(basePath+"supplier!deleteStaff.do");
	},
	//删除供货商
	doSupplierDelete:function (){
	    var userid=[];
	    $("input[name='id']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).attr("userid")!=""){
	              userid.push($(this).attr("userid"));
	            }
	         }
	     });
	     if(userid.length>0){
			       alert("不能删除已关联工号的供货商！");
			       return ;
		  }
	        
	    if(!this.checkIdSeled()){
			alert("请选择要删除的供货商");
			return ;
		}
		
	   if(!confirm("确认要将这些供货商彻底删除吗？删除后将不可恢复")){	
		   return ;
	    }
		$.Loading.show("正在删除供货商...");
		
		this.deletePost1(basePath+"supplier!delete.do");
		    
	},
	
	//注销供货商
	doSupplierCancellation:function (){
	    var userid=[];
	    $("input[name='id']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).attr("userid")!=""){
	              userid.push($(this).attr("userid"));
	            }
	         }
	     });
	   
	   if(!this.checkIdSeled()){
			alert("请选择要注销的供货商");
			return ;
		}
		
	   if(!confirm("确认要将这些供货商注销吗？")){	
		   return ;
	    }
		$.Loading.show("正在注销供货商...");
		
		this.deletePost1(basePath+"supplier!cancellation.do");
		    
	},
	//审核
	doAuditSupplier:function(supplier_id){
		var self =this;
		//确认供货商是否已被审核
		var isAudit = false;
		Cmp.asExcute('',ctx + '/shop/admin/supplier!isAudit.do?ajax=yes&supplier_id=' + supplier_id,{},function(response){
			if(response.result == 1){
				isAudit = true;
			}
		},'json');
		if(!isAudit){
			var url=app_path+'/shop/admin/supplier!showaudit.do?ajax=yes&supplier_id='+supplier_id;
	    	self.toUrlOpen(url);
		}else {
			alert('该供货商已被审核');
		}
	},
	
	doAuditDealer:function(supplier_id){
		var self =this;
		//确认经销商是否已被审核
		var isAudit = false;
		Cmp.asExcute('',ctx + '/shop/admin/supplier!isAudit.do?ajax=yes&supplier_id=' + supplier_id,{},function(response){
			if(response.result == 1){
				isAudit = true;
			}
		},'json');
		if(!isAudit){
			var url=app_path+'/shop/admin/supplier!showauditdealer.do?ajax=yes&supplier_id='+supplier_id;
	    	self.toUrlOpen(url);
		}else {
			alert('该经销商已被审核');
		}
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

//切换tab页面
var SupplierDetail ={
		supplier_id : undefined,
		init:function(supplier_id,is_edit,flag,supplier_state){
		    var self = this; 
		    this.supplier_id = supplier_id;
		    this.is_edit=is_edit;
		    this.flag=flag;
		    this.supplier_state=supplier_state;
		    
			new Tab(".partner_detail");
			this.bindTabEvent();			
			self.showBase();
			
			
		},
		/**
		 * 绑定tab事件
		 */
		bindTabEvent:function(){
			var self = this;
			$("#base").click(function(){
			    if($("#baseTab").html()){
			       return ;
			    }else{
			        self.showBase(); 
			}});
			$("#company").click(function(){ 
			   if($("#companyTab").html()){
			      return ;
			   }else{
			   self.showCompany(); }});
			   
			$("#account").click(function(){ 
			   if($("#accountTab").html()){
			      return ;
			   }else{
			   self.showaccountTab(); }});
			   
			$("#agent").click(function(){ 
			   if($("#agentTab").html()){
			      return ;
			   }else{
			   self.showagentTab(); }});   
			   
			$("#certificate").click(function(){ 
			   if($("#certificateTab").html()){
			      return ;
			   }else{
			   self.showcertificateTab(); }}); 
			   
			$("#resources").click(function(){ 
			   if($("#resourcesTab").html()){
			      return ;
			   }else{
			   self.showresourcesTab(); }});     
							   
			   
			$("#staff").click(function(){ 
			   if($("#staffTab").html()){
			      return ;
			   }else{
			   self.showStaff(); }});
		},
		
		/**
		 * 显示基本信息
		 */
		showBase:function(){
			$("#baseTab").load("supplier!baseTab.do?ajax=yes&is_edit="+this.is_edit+"&supplier_id="+this.supplier_id+"&supplier_state="+this.supplier_state+"&flag="+this.flag,function(){
			    $('input.dateinput').datepicker();
			});
			
		},
		
		/**
		 * 显示公司信息
		 */
		showCompany:function(){
		   $("#companyTab").load("supplier!companyTab.do?ajax=yes&is_edit="+this.is_edit+"&supplier_id="+this.supplier_id+"&supplier_state="+this.supplier_state+"&flag="+this.flag,function(){
				$('input.dateinput').datepicker();
			});
		},
		
		/**
		 * 显示账户信息
		 */
		showaccountTab:function(){
		   $("#accountTab").load("supplier!accountTab.do?ajax=yes&is_edit="+this.is_edit+"&supplier_id="+this.supplier_id+"&supplier_state="+this.supplier_state+"&flag="+this.flag,function(){
				$('input.dateinput').datepicker();
			});
		},
		
		/**
		 * 显示代理原产商信息
		 */
		showagentTab:function(){
		   $("#agentTab").load("supplier!agentTab.do?ajax=yes&is_edit="+this.is_edit+"&supplier_id="+this.supplier_id+"&supplier_state="+this.supplier_state+"&flag="+this.flag,function(){
				$('input.dateinput').datepicker();
			});
		},
		
		/**
		 * 显示认证证书信息
		 */
		showcertificateTab:function(){
		   $("#certificateTab").load("supplier!certificateTab.do?ajax=yes&is_edit="+this.is_edit+"&supplier_id="+this.supplier_id+"&supplier_state="+this.supplier_state+"&flag="+this.flag,function(){
				$('input.dateinput').datepicker();
			});
		},
		
		/**
		 * 显示人力资源信息
		 */
		showresourcesTab:function(){
		   $("#resourcesTab").load("supplier!resourcesTab.do?ajax=yes&is_edit="+this.is_edit+"&supplier_id="+this.supplier_id+"&supplier_state="+this.supplier_state+"&flag="+this.flag,function(){
				$('input.dateinput').datepicker();
			});
		},
		
		/**
		 * 显示员工信息
		 */
		showStaff:function(){
			$("#staffTab").load("supplier!staffTab.do?ajax=yes&is_edit="+this.is_edit+"&supplier_id="+this.supplier_id+"&supplier_state="+this.supplier_state+"&flag="+this.flag);
		}
};
