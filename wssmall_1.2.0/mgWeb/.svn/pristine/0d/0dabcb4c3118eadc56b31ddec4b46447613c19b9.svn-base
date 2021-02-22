
/**
行业用户
   
*/
var Partner=$.extend({},Eop.Grid,{
    dlg_id:'showDlg',
    title:'行业用户',
	init:function(){
		var self =this;
		
		var curid=$("#currentPartnerId").val();
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		
		//恢复申请
		$(".againApply").click(function (){
		     var partner_id=$(this).attr("partner_id");
             var state=$(this).attr("state");
             var sequ=$(this).attr("sequ");
		     var url = app_path+ "/shop/admin/cmsAgent!applyAgain.do?partner_id="+partner_id+"&state="+state+"&sequ="+sequ+"&ajax=yes";
			Cmp.ajaxSubmit('parListForm', '', url, {}, function(responseText){
			        
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						//0成功
						alert(responseText.message);
						window.location.href=app_path+ "/shop/admin/cmsAgent!list.do?stateType=-1";
				    }
		
					
			},'json');
		});
		
		//删除员工
		$("#delStaffBtn").click(function(){self.doStaffDelete();});
		
		//删除行业用户
		$("#delPartnerBtn").click(function(){self.doPartnerDelete();});
		
		//冻结
		$("#blockBtn").click(function(){
		    self.doBlockPar();
		});
		
		//分级解冻
		$("#gradingOpenBtn").click(function (){
		     self.doGradingOpenPar();
		});
		//分级冻结
		$("#gradingBtn").click(function (){
		     self.doGradingPar();
		});
		//预存金冻结/解冻
		$("#amountBlockBtn").click(function (){
		     self.doAmountBlock();
		});
		
		//预存金信息
		$("#amountInfoBtn").click(function (){
		     self.doAmountInfo();
		});
		
        //续签
		$("#keeponBtn").click(function(){
		   if($("input[type='checkbox']:checked").length==0){
				alert("请选择要续签的行业用户！");
				return ;
			}
			 if($("input[name='id']:checked").length==1){
			    var partner_id=$("input[name='id']:checked").val();
			    var state=$("input[name='id']:checked").attr("state");
	            var sequ=$("input[name='id']:checked").attr("sequ");
	            var userid=$("input[name='id']:checked").attr("userid");
			    if(curid==partner_id){
			       alert("抱歉，你不能对自己续签！");
			       return false;
			    }
			    if(userid!="" &&(state==1 || state==2) && sequ==0){
	                self.doKeeponPar(partner_id,state,sequ);
	             }else{
	                 if(userid==""){
	                     alert("该行业用户未关联工号不能做此操作!");
	                 }
	             }
			    
			 }else{
			    alert("请选择一条要续签的行业用户数据！");
				return ;
			 }
            
        });
		 //恢复
		$("#renewBtn").click(function(){
		   if($("input[type='checkbox']:checked").length==0){
				alert("请选择要解冻的行业用户！");
				return ;
			}
			 if($("input[name='id']:checked").length==1){
			    var partner_id=$("input[name='id']:checked").val();
			    if(self.curid==partner_id){
			       alert("抱歉，你不能对自己解冻！");
			       return false;
			    }
			    var state=$("input[name='id']:checked").attr("state");
	            var sequ=$("input[name='id']:checked").attr("sequ");
	            var userid=$("input[name='id']:checked").attr("userid");
	            if(userid!="" &&state==2 && sequ==0){
	                self.doRenewPar(partner_id);
	             }else{
	                 if(userid==""){
	                     alert("该行业用户未关联工号不能做此操作!");
	                 }else if(state!=2){
	                    alert("该行业用户未被冻结不能做此操作!");
	                 }
	             }
	             
			 }else{
			    alert("请选择一条要解冻的行业用户数据！");
				return ;
			 }
            
        });
		//恢复(disable)
		$(".renewName").click(function(){
		     var partner_id=$(this).attr("partner_id");
		      self.doRenewPar(partner_id);
		});
		//行业用户申请审核
		$(".auditName").click(function(){
		     var partner_id=$(this).attr("partner_id");
		      self.doAuditPar(partner_id);
		});
		
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
	
	//删除的行业用户员工
	doStaffDelete:function (){
	  if(!this.checkIdSeled()){
			alert("请选择要删除的行业用户员工");
			return ;
		}
	 
		if(!confirm("确认要将这些行业用户员工彻底删除吗？删除后将不可恢复")){	
			return ;
		}
		$.Loading.show("正在删除行业用户员工...");
		
		this.deletePost1(basePath+"cmsAgent!deleteStaff.do");
	},
	//删除行业用户
	doPartnerDelete:function (){
	    var userid=[];
	    $("input[name='id']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).attr("userid")!=""){
	              userid.push($(this).attr("userid"));
	            }
	         }
	     });
	     if(userid.length>0){
			      alert("不能删除已关联工号的行业用户！");
			      return;
		  }
	    
		if(!this.checkIdSeled()){
			alert("请选择要删除的行业用户！");
			return ;
		}  
		  
	   if(!confirm("确认要将这些行业用户彻底删除吗？删除后将不可恢复")){	
		   return ;
	    }
		$.Loading.show("正在删除行业用户...");
		
		//by liqingyi
//		var id="";
//		$("#gridform").find("tr").each(function(i,data){
//			var obj=$(data).find("input[type='checkbox'][name='id']");
//			if($(obj).is(":checked")){
//				id+=$(obj).attr("value")+",";
//			}
//		});
//		id=id.substring(0,id.length-1);
		this.deletePost1(basePath+"cmsAgent!delete.do");
		    
	},
	//冻结
    doBlockPar:function(){
		var self =this;
		if(!this.checkIdSeled()){
			alert("请选择要冻结的行业用户！");
			return ;
		}
		var curid=$("#currentPartnerId").val();
		 if($("input[name='id']:checked").length==1){
		    var partner_id=$("input[name='id']:checked").val();
		    var state=$("input[name='id']:checked").attr("state");
            var sequ=$("input[name='id']:checked").attr("sequ");
            var userid=$("input[name='id']:checked").attr("userid");
            if(curid==partner_id){
			       alert("抱歉，你不能对自己冻结！");
			       return false;
			    }
			if(userid!="" && state==1 && sequ==0){
			  var url=app_path+'/shop/admin/cmsAgent!showblock.do?ajax=yes&partner_id='+partner_id+"&userid="+userid;
		      self.toUrlOpen(url);
			}else{
			   if(userid==""){
	                alert("该行业用户未关联工号不能做此操作!");
                 }else if(state==2){
                    alert("该行业用户已冻结不能做此操作!");
                 }
			}
		    
		    
		 }else{
		    alert("请选择一条要冻结的行业用户数据！");
			return ;
		 }
	},
	//分级冻结
	doGradingPar:function(){
		var self =this;
		if(!this.checkIdSeled()){
			alert("请选择要冻结业务的行业用户！");
			return ;
		}
		var curid=$("#currentPartnerId").val();
		 if($("input[name='id']:checked").length==1){
		    var partner_id=$("input[name='id']:checked").val();
		    if(curid==partner_id){
			       alert("抱歉，你不能对自己做冻结业务！");
			       return false;
			    }
		    var state=$("input[name='id']:checked").attr("state");
            var sequ=$("input[name='id']:checked").attr("sequ");
            var userid=$("input[name='id']:checked").attr("userid");
            if(userid!="" && state==1 && sequ==0){
			   var url=app_path+'/shop/admin/cmsAgent!showGrading.do?ajax=yes&partner_id='+partner_id+'&state='+state+'&sequ='+sequ;
		       self.toUrlOpen(url);
			}else{
			   if(userid==""){
	                alert("该行业用户未关联工号不能做此操作!");
                 }else if(state!=1){
                    alert("该行业用户已冻结不能做此操作!");
                 }
			}
		   
		    
		    
		 }else{
		    alert("请选择一条要冻结的行业用户数据！");
			return ;
		 }
	},
	//分级解冻
	doGradingOpenPar:function(){
		var self =this;
		if(!this.checkIdSeled()){
			alert("请选择要解冻业务的行业用户！");
			return ;
		}
		var curid=$("#currentPartnerId").val();
		 if($("input[name='id']:checked").length==1){
		    var partner_id=$("input[name='id']:checked").val();
		    if(curid==partner_id){
			       alert("抱歉，你不能对自己做解冻业务！");
			       return false;
			    }
		    var state=$("input[name='id']:checked").attr("state");
            var sequ=$("input[name='id']:checked").attr("sequ");
            var userid=$("input[name='id']:checked").attr("userid");
            if(userid!="" && state==1 && sequ==0){
			   var url=app_path+'/shop/admin/cmsAgent!showGradingOpen.do?ajax=yes&partner_id='+partner_id+'&state='+state+'&sequ='+sequ;
		       self.toUrlOpen(url);
			}else{
			   if(userid==""){
	                alert("该行业用户未关联工号不能做此操作!");
                 }else if(state!=1){
                    alert("该行业用户已冻结不能做此操作!");
                 }
			}
		   
		    
		    
		 }else{
		    alert("请选择一条要冻结的行业用户数据！");
			return ;
		 }
	},
	//预存金冻结/解冻
	doAmountBlock:function(partner_id){
		var self =this;
		if(!this.checkIdSeled()){
			alert("请选择要预存金冻结或解冻的行业用户！");
			return ;
		}
		var curid=$("#currentPartnerId").val();
		 if($("input[name='id']:checked").length==1){
		    var partner_id=$("input[name='id']:checked").val();
		    var state=$("input[name='id']:checked").attr("state");
            var sequ=$("input[name='id']:checked").attr("sequ");
            var userid=$("input[name='id']:checked").attr("userid");
		   if(curid==partner_id){
			       alert("抱歉，你不能对自己做预存金冻结解冻！");
			       return false;
			}
			 if(userid!="" && state==1 && sequ==0){
			    var url=app_path+'/llkj/admin/freeze!list.do?ajax=yes&partnerId='+partner_id;
			    self.toUrlOpen(url);
		    }else{
		        if(userid==""){
	                alert("该行业用户未关联工号不能做此操作!");
                 }else if(state!=1){
                    alert("该行业用户已冻结不能做此操作!");
                 }
		    }
		    
		 }else{
		    alert("请选择一条行业用户数据！");
			return ;
		 }
	},
	//预存金信息
	doAmountInfo:function(partner_id){
		var self =this;
		if(!this.checkIdSeled()){
			alert("请选择行业用户！");
			return ;
		}
		if($("input[name='id']:checked").length==1){
		    var partner_id=$("input[name='id']:checked").val();
		    var state=$("input[name='id']:checked").attr("state");
            var sequ=$("input[name='id']:checked").attr("sequ");
            var userid=$("input[name='id']:checked").attr("userid");
            var founder = $("input[name='id']:checked").attr("founder");
             if(userid!="" && sequ==0){
             	var url = "";
             	if(founder == 3){
             		url = app_path + '/llkj/admin/freeze!changeLogF.do?ajax=yes&userid='+userid;
             	}else {
             		url = app_path + '/llkj/admin/freeze!changeLog.do?ajax=yes&partnerId='+partner_id;
             	}
			    self.toUrlOpen(url);
		    }else{
		        if(userid==""){
	                alert("该行业用户未关联工号不能做此操作!");
                 }
		    }
		 }else{
		    alert("请选择一条行业用户数据！");
			return ;
		 }
	},
	//续签
	doKeeponPar:function(partner_id,state,sequ){
		var self =this;
	    var url=app_path+'/shop/admin/cmsAgent!showkeepon.do?ajax=yes&partner_id='+partner_id+'&state='+state+'&sequ='+sequ;
	    this.title="行业用户续签";
	    self.toUrlOpen(url);
	},
	//恢复
	doRenewPar:function(partner_id){
		var self =this;
	    var url=app_path+'/shop/admin/cmsAgent!showrenew.do?ajax=yes&partner_id='+partner_id;
	     self.toUrlOpen(url);
	},
	//审核
	doAuditPar:function(partner_id){
		var self =this;
		//确认行业用户是否已被审核
		var isAudit = false;
		Cmp.asExcute('',ctx + '/shop/admin/cmsAgent!isAudit.do?ajax=yes&partner_id=' + partner_id,{},function(response){
			if(response.result == 1){
				isAudit = true;
			}
		},'json');
		if(!isAudit){
			var url=app_path+'/shop/admin/cmsAgent!showaudit.do?ajax=yes&partner_id='+partner_id;
	    	self.toUrlOpen(url);
		}else {
			alert('该行业用户已被审核');
		}
	},
	
	//资料变更审核
	doAuditAlterPar:function(partner_id){
		var self =this;
	    var url=app_path+'/shop/admin/cmsAgent!showAuditAlter.do?ajax=yes&partner_id='+partner_id;
	    self.toUrlOpen(url);
	},
	//查看变更前后历时记录
	doAuditAlterHistory:function(partner_id,sequ){
		var self =this;
	    var url=app_path+'/shop/admin/cmsAgent!showAuditAlterHistory.do?ajax=yes&partner_id='+partner_id+'&sequ='+sequ;
	    self.toUrlOpen(url);
	},
	
	//查看原因
	doLookResonPar:function(partner_id,msequ,ssequ){
		var self =this;
	    var url=app_path+'/shop/admin/cmsAgent!showLookReson.do?ajax=yes&partner_id='+partner_id+'&msequ='+msequ+'&ssequ='+ssequ;
	    self.toUrlOpen(url);
	},
	//网店资料变更审核
	doAuditAlterShopPar:function(partner_id){
		var self =this;
	    var url=app_path+'/shop/admin/cmsAgent!showAuditShopAlter.do?ajax=yes&partner_id='+partner_id;
	    self.toUrlOpen(url);
	},
	//查看网店审核原因
	doLookResonShopPar:function(partner_id,sequ){
		var self =this;
	    var url=app_path+'/shop/admin/cmsAgent!showLookResonShop.do?ajax=yes&partner_id='+partner_id+'&sequ='+sequ;
	    self.toUrlOpen(url);
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
var PartnerDetail ={
		partnerid : undefined,
		init:function(partnerid,is_edit,flag,state,sequ){
		    var self = this; 
		    this.partnerid = partnerid;
		    this.is_edit=is_edit;
		    this.flag=flag;
		    this.state=state;
		    this.sequ=sequ;
		    
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
			$("#shop").click(function(){ 
			   if($("#shopTab").html()){
			      return ;
			   }else{
			   self.showShop(); }});
			$("#staff").click(function(){ self.showStaff(); });
		},
		
		/**
		 * 显示基本信息
		 */
		showBase:function(){
			$("#baseTab").load("cmsAgent!baseTab.do?ajax=yes&is_edit="+this.is_edit+"&partner_id="+this.partnerid+"&state="+this.state+"+&sequ="+this.sequ+"&flag="+this.flag,function(){
			    $('input.dateinput').datepicker();
			});
			
		},
		
		/**
		 * 显示网店信息
		 */
		showShop:function(){
		   $("#shopTab").load("cmsAgent!shopTab.do?ajax=yes&is_edit="+this.is_edit+"&partner_id="+this.partnerid+"&state="+this.state+"+&sequ="+this.sequ+"&flag="+this.flag,function(){
				$('input.dateinput').datepicker();
			});
		},
		
		/**
		 * 显示员工
		 */
		showStaff:function(){
			$("#staffTab").load("cmsAgent!staffTab.do?ajax=yes&is_edit="+this.is_edit+"&partner_id="+this.partnerid+"&state="+this.state+"+&sequ="+this.sequ+"&flag="+this.flag);
		},
		
		showBaseAddEnd:function(pid){
			this.partnerid=pid;
			$("#baseTab").load("cmsAgent!baseTab.do?ajax=yes&is_edit="+this.is_edit+"&partner_id="+this.partnerid+"&flag="+this.flag,function(){
			    $('input.dateinput').datepicker();
			});
			
		}
		
};

