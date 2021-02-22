/**
 * 业务受理核心模块
 */
(function(scope) {
	var BtnWizard = Base.extend({
		constructor : function(config) {
			this.version = "1.0";
			this.config ={};
			this.is_btn_click ="yes";
			this.tabMenu = {};
			this.btns=[{'id':'pre','name':'上一步','func':this.pre},
					   {'id':'next','name':'下一步','func':this.next},
					   {'id':'confirm','name':'确 定','func':this.confirm},
					   {'id':'confirm_no_pay','name':'确 定','func':this.confirm_no_pay},
					   {'id':'cancel','name':'返 回','func':this.cancel},
					   {'id':'login_in','name':'立即登录办理','func':this.login_in},
					   {'id':'fee','name':'在线支付','func':this.fee}
					   ]
		},
		submit_validate:function(step,agr1){ // 点击上一步，下一步，确认按钮验证函数
			if(!this.yw_validate())
				return false;
			var p_agr1 =agr1||'';
			var func_name =this.tabMenu['code']+"_"+step+"_validate";
		
			var func =this[func_name];
			//alert(func_name+":"+typeof(func)) 
			try{
				if(typeof(func) != "function") return true;
				return func(p_agr1);
			}catch(e){}
			return true;
		},
		yw_validate:function(){ //当传入产品id时，判断当选选择的号码是否允许办理业务，如：宽带账号进来，只能办理宽带账号的功能产品
			var product_id = acpBeanFactory.getRequestParameter('product_id');
			var accNbrSelect = acpSplite.getPageTable('acc_nbr_select');
			if(!accNbrSelect)
				accNbrSelect =  acpSplite.getPageSelect('acc_nbr_select');
			var unvalidate=acpBeanFactory.getRequestParameter('unvalidate');
			if(unvalidate && unvalidate=='yes')
				return true;
			if(product_id && accNbrSelect){
				var sel_product_id = accNbrSelect.getSelValue(null,'product_id');
				if(sel_product_id && product_id && product_id!=sel_product_id)
				{
					var acc_nbr = accNbrSelect.getSelValue(null,'acc_nbr');
					pop({title:'温馨提醒！',text:'提醒：尊敬的用户，号码'+acc_nbr+'不允许办理“'+pageWizard.serv_info.SERV_NAME+'”业务，请检查！'},3);
					return false;
				}
			}
			return true;
		},
		load_validate:function(step,code){ // 点击上一步，下一步，确认按钮验证函数
			this.load_def_end(step,code);
			var func_name =code+"_"+step+"_validate";
			var func =this[func_name];
			try{
				if(typeof(func) != "function") return true;
				return func();
			}catch(e){}
			
			return true;
		},
		load_def_end:function(step,code){ // 点击上一步，下一步，确认按钮验证函数
			if(code.indexOf("_end")>-1)
			{	
				var func_name ="re_load_validate";
				var func =this[func_name];
				try{
					if(typeof(func) != "function") return true;
					return func();
				}catch(e){}
			}
		},
		pre:function(){ //上一步
			var code =this.getTabCode('prev');
			if(!this.submit_validate('prev'))
				return;
			this.is_btn_click='yes';
			code && $("[id='"+code+"_li']").trigger("click",['btn']); 
			this.is_btn_click='no';
		},
		login_in:function(){
			var url = this.submit_validate('login_in');
			if(url ==true){
				url=acpBeanFactory.getUrl();
				if(!url)
					url = "/service/transaction/index.jsp?SERV_NO=FSS-3-1";
			}
			window.location.href = "/service/service_login.jsp?retUrl="+url;
			return;
		},
		next:function(agr1){ //下一步
			var p_agr1 =agr1 ||'';
			var code =this.getTabCode('next');
			if(!this.submit_validate('next',p_agr1))
				return;
			this.is_btn_click='yes';
			code && $("[id='"+code+"_li']").trigger("click",['btn']);
			this.load_validate('re_load',code);
			this.is_btn_click='no';
			
		},
		backTargetTab:function(code,is_reload){ //跳转到指定的环节
			var liJQ =$("[id='"+code+"_li']");
			liJQ.attr("is_reload",is_reload);
			liJQ.trigger("click",['btn']);
			liJQ.attr("is_reload",false);
		},
		fee:function(){ //支付,对流程表数据确认后支付
			if(!this.submit_validate('fee'))
			   return;
			var dataMap = acpBeanFactory.getFeeData(); //获取支付数据
			dataMap.method='FEE_ACCEPT';
			var me =this;
			Service.asExcute("TWB_ACCEPT", false,dataMap, function(reply){
				var results =reply['RET_OBJ'];
				me.next();
			})
		},
		confirm_no_pay:function(){ //点击确认按钮，直接送crm不需要支付费用
			
			if(!this.submit_validate('confirm_no_pay'))
				return;
			if(!this.vertify_vali())
				return;
			this.jk_call_back =this.confirm_no_pay_jx_call_back;
			$("#entrance").mask("数据处理...",500); 
			this._confirm();
		},
		vertify_vali:function(){
			var logon_valid_val =$("#logon_valid").val();
			if($("#logon_valid").length>0){
				if(!logon_valid_val){
					pop({title:'温馨提醒！',text:'请输入验证码！'},3);
					return false;
				}
			}
			acpBeanFactory.logon_valid_val =logon_valid_val;
			return true;
		},
		confirm:function(){ //订单提交，写入数据到流程表
			var result = this.submit_validate('confirm');
			if(!result)
				return;
			if(!this.vertify_vali())
				return;
			this.jk_call_back =this.confirm_jx_call_back;
			$("#entrance").mask("数据处理...",500); 
			this._confirm();
		},
		_confirm:function(){
			var serv_list = acpBeanFactory.assembleAcpBeanData();
			var dataMap={};
			dataMap['serv_list'] = serv_list;
			dataMap['logon_valid_val'] = acpBeanFactory.logon_valid_val;
			dataMap.method='SAVE_ACCEPT';
			var me =this;
			Service.excuteNoLoading("TWB_ACCEPT", false,dataMap, function(reply){ //获取卡片信息
				var results =reply['RET_OBJ'];
				var code = results['code']
				if(code && code !='0000'){
					pop({title:'温馨提醒！',text:results['message']},3);
					$("#entrance").unmask();
					return;
				}
			    for(var i = 0; i< results.length;i++){
			    	var retInfo = results[i];
			    	me.config.method='CALL_SERVICE';
					me.config.PRE_ORD_NO = retInfo.PRE_ORD_NO;
					me.config.SERV_NO = retInfo.SERV_NO;
					me.config.PRE_MEM_ORD_NO = retInfo.PRE_MEM_ORD_NO;
					me.config.jx_method = acpBeanFactory.getJkMethod();
					var SERV_NO = me.config.SERV_NO;
					var acpBean = acpBeanFactory.getAcpBeanByServNo(SERV_NO);
					acpBean.config.method='CALL_SERVICE';
					acpBean.config.PRE_ORD_NO = retInfo.PRE_ORD_NO;
					acpBean.config.SERV_NO = retInfo.SERV_NO;
					acpBean.config.PRE_MEM_ORD_NO = retInfo.PRE_MEM_ORD_NO;
					
					//调用接口
					Service.excuteNoLoading("TWB_ACCEPT", false, me.config, function(reply){
						me.jk_call_back(reply);
						$("#entrance").unmask();
					})
				}
				
			})
		},
		confirm_no_pay_jx_call_back:function(reply){
			var result =reply['CALLMAP'];
			if(!result){
				try{this.visibleBtn('F','confirm_no_pay');}catch(e){}
				pop({text:'尊敬的客户，网络超时，请稍后在试！'},0);
				return;
			}
			var code = result['code'];
			var message =result['message'];
			var SERV_NO = this.config.SERV_NO;
			var acpBean = acpBeanFactory.getAcpBeanByServNo(SERV_NO);
			if(code =="0000") //成功
			{	
				acpBean.DEL_PRE_ORD_NO ='';
				acpBean.DEL_PRE_MEM_ORD_NO ='';
				result['PRE_MEM_ORD_NO'] = this.config.PRE_ORD_NO;
				acpBeanFactory.setCallBackData(result); //设置返回数据,订单提交后的处理
				acpBeanFactory.is_succ = true;
				acpBeanFactory.msg = "";
				
			}else
			{ //失败
				var askPreData = {};
				CommonUtils.apply(askPreData,this.config);
				acpBean.DEL_PRE_ORD_NO =this.config.PRE_ORD_NO;
				acpBean.DEL_PRE_MEM_ORD_NO =this.config.PRE_MEM_ORD_NO;
				acpBeanFactory.is_succ = false;
				acpBeanFactory.msg = message;
				try{this.visibleBtn('F','confirm_no_pay');}catch(e){}
				//pop({title:'提交失败！',text:'尊敬的客户，您的业务受理失败！<br/>错误信息：<font color=red>'+message+'</font>'},3);
			}
			this.next();
		},
		confirm_jx_call_back:function(reply){
			var CALLMAP = reply['CALLMAP'];
			if(!CALLMAP){
				try{this.visibleBtn('F','confirm');}catch(e){}
				pop({text:'尊敬的客户，网络超时，请稍后在试！'},0);
				return;
			}
			var callMap = CALLMAP['callMap'];
			if(!callMap){
				try{this.visibleBtn('F','confirm');}catch(e){}
				pop({text:'尊敬的客户，网络超时，请稍后在试！'},0);
				return;
			}
			
			var code = CALLMAP['code'];
			var msg = CALLMAP['message'];
			var SERV_NO = this.config.SERV_NO;
			var acpBean = acpBeanFactory.getAcpBeanByServNo(SERV_NO);
			if(code =="0000") //成功
			{
				acpBean.DEL_PRE_ORD_NO ='';
				acpBean.DEL_PRE_MEM_ORD_NO ='';
				acpBeanFactory.setCallBackData(callMap); //设置返回数据,订单提交后的处理
				acpBeanFactory.is_succ = true;
				acpBeanFactory.msg = "";
				this.next();
			}else
			{  //失败
				var askPreData = {};
				CommonUtils.apply(askPreData,this.config);
				acpBean.DEL_PRE_ORD_NO =this.config.PRE_ORD_NO;
				acpBean.DEL_PRE_MEM_ORD_NO =this.config.PRE_MEM_ORD_NO;
				acpBeanFactory.is_succ = false;
				acpBeanFactory.msg = msg;
				try{this.visibleBtn('F','confirm');}catch(e){}
				pop({text:msg},1);
			}
			
		},
		cancel:function(){ //取消
			if(!this.submit_validate('cancel'))
				return;
			var url =acpBeanFactory.getUrl();
			if(!url)
				url = "/service/transaction/";
			window.location.href=url;
		},
		getTabMenu:function(lx){
			var tabMenus = pageWizard.tabMenus;
			for(var i=0;i<tabMenus.length;i++)
			{
				var curr = tabMenus[i];
				if(this.tabMenu['code'] ==curr['code']){
					if(lx =="prev")
						return tabMenus[i-1];
					if(lx =="next")	
						return tabMenus[i+1]
					if(lx =="curr")	
						return this.tabMenu;
				}
			}
		},
		getTabCode:function(lx){
			var tabMenu =this.getTabMenu(lx);
			return tabMenu?tabMenu['code']:'';
		},
		changeBtn:function(tabMenu){
			var user_no = acpBeanFactory.getRequestParameter("user_no");
			var is_login = pageWizard.serv_info.PROPS; //pageWizard.serv_info.IS_LOGIN;
			this.tabMenu =tabMenu;
			if(!user_no && is_login&& is_login.indexOf('LOGIN')>-1)
			{
				this.visibleBtn('F','pre');
				this.visibleBtn('F','next');
				this.visibleBtn('F','fee');
				this.visibleBtn('F','confirm');
				//this.visibleBtn('T','login_in');
				this.visibleBtn('F','cancel');
				this.visibleBtn('F','confirm_no_pay');
				if(this.config.serv_no =="ADSL_SPEED_NEW" || this.config.serv_no =="ADSL_SPEED"){
					//loginPop.popLogin(); //弹出登录
				}else{
					loginPop.popLogin(); //弹出登录
				}
			}else
			{
				this.visibleBtn(tabMenu['prev_btn'],'pre');
				this.visibleBtn(tabMenu['next_btn'],'next');
				this.visibleBtn(tabMenu['fee_btn'],'fee');
				this.visibleBtn(tabMenu['confirm_btn'],'confirm');
				this.visibleBtn(tabMenu['login_in'],'login_in');
				this.visibleBtn(tabMenu['cancel_btn'],'cancel');
				this.visibleBtn(tabMenu['confirm_no_pay_btn'],'confirm_no_pay');
			}
			this.visibleVertify();
		},
		hideBtn:function(tabMenu){
			this.tabMenu =tabMenu;
			this.visibleBtn('F','pre');
			this.visibleBtn('F','next');
			this.visibleBtn('F','fee');
			this.visibleBtn('F','confirm');
			this.visibleBtn('F','login_in');
			this.visibleBtn('F','cancel');
			this.visibleBtn('F','confirm_no_pay');
		},
		visibleBtn:function(prev_btn,btn_id){
			if($("#"+btn_id).attr("must_hide") =="yes"){
				$("#"+btn_id).hide();
			}else if(prev_btn =="T"){
				$("#"+btn_id).show()
			}else{
				$("#"+btn_id).hide();
			}
		},
		hideBtnMust:function(prev_btn,btn_id){
			$("#"+btn_id).attr("must_hide","yes");
			$("#"+btn_id).hide();
		},
		visibleVertify:function(){
			var verBtn =$("#verify_key_div");
			if(verBtn.length>0){
				var confirm_len = $("[id='confirm']:visible").length;
				var confirm_no_len = $("[id='confirm_no_pay']:visible").length;
				if(confirm_no_len ==1 || confirm_len==1){
					if(verBtn.attr("must_hide") =="yes"){
						verBtn.hide();
					}else{
						verBtn.show();
						window.getImg();
					}
					
				}else
				{
					verBtn.hide();
				}
			}
		},
		genBtns:function(){ //构造跳转按钮
			var me =this;
			for(i=0;i<this.btns.length;i++)
			{
				var btn = this.btns[i];
				var btnHtml= [];
				btnHtml.push("<div class='btn fl' id='"+btn['id']+"' style='display:none;margin-left:20px;'>");
				if(btn['id'] =="cancel")
					btnHtml.push("	<a href='#' class='W_btn_c'><span><em>"+btn['name']+"</em></span></a>");
				else
	            	btnHtml.push("	<a href='javascript:void(0)' class='W_btn_c'><span><em>"+btn['name']+"</em></span></a>");
	       		btnHtml.push("</div>");
	       		var btnJq = $(btnHtml.join(""));
	       		me.getBtnParent().append(btnJq);
	       		btnJq.bind("click",function(){
	       			var id = $(this).attr("id");
	       			var func = me.getBtnById(id)['func'];
	       			func.call(me);
	       		})
	       		
			}
		},
		genBtns2:function(){ //构造跳转按钮
			var me =this;
			var className='btna_bo';
			for(i=0;i<this.btns.length;i++)
			{
				var btn = this.btns[i];
				var btnHtml= [];
				if(btn['id'].indexOf("confirm")>-1){
					className = "btna_g";
				}else{
					className = 'btna_bo';
				}
				btnHtml.push("<div class='fl' id='"+btn['id']+"' style='display:none;margin-left:20px;'>");
				if(btn['id'] =="cancel")
	           		 btnHtml.push("	<a href='#' class='"+className+"' style='cursor:pointer;'><span><em>"+btn['name']+"</em></span></a>");
	       		else
	       			 btnHtml.push("	<a href='javascript:void(0)' class='"+className+"' style='cursor:pointer;'><span><em>"+btn['name']+"</em></span></a>");
	       		btnHtml.push("</div>");
	       		var btnJq = $(btnHtml.join(""));
	       		me.getBtnParent().append(btnJq);
	       		btnJq.bind("click",function(){
	       			var id = $(this).attr("id");
	       			var func = me.getBtnById(id)['func'];
	       			func.call(me);
	       		})
	       		
			}
		},
		getBtnById:function(p_id){
			for(i=0;i<this.btns.length;i++)
			{
				var btn = this.btns[i];
				var id = btn['id'];
				if(p_id == id)
					return btn;
	       		
			}
		},
		getBtnParent:function(){
			return $("#btnLayOut");
		},
		sendRandomPwd:function(){
			var vNum= $("#cdma_acc_nbr").val();
			if(!vNum){
				pop({title:'消息',text:"请输入手机号码"},0);
				return;
			}
			if(vNum && !VTypes.mobile(vNum)){
				pop({title:'消息',text:"请输入正确的手机号码，如：1890731****"},0);
				return;
			}
			Service.excuteNoLoading("ACCEPT_GOODS", false, {acc_nbr:vNum,method:"goodsOrderQry"}, function(reply){
				if(reply.CALLMAP){
					var flag = reply.CALLMAP.flag;
					if(flag==1){
						pop({title:'消息',text:"您输入的号码已参加过网上营业厅团购活动，不能再次参与，谢谢！"},0);
						return;
					}
				}
				var param = {};
				param.RECV_NUM=vNum;
				param.SMS_OPERTYPE = "CR0";
				param.RAND_TYPE = "005";
				param.NEED_VAL="no";
				$("#form_get_rand_pwd").attr('disabled','disabled');
				Service.excuteNoLoading("MWB_WT_SEND_RANDOM_PWD", false, param, function(reply){
					$("#form_get_rand_pwd").attr('disabled','');
					var flag = reply.flag;
					var msg = reply._msg;
					if(flag==0){
						pop({title:'消息',text:'短信随机码发送成功，请查收！'},1);
					}else{
						pop({title:'消息',text:msg},2);
					}
				});
			});
		},
		validateRandom:function(p_param){ //短信随机码验证
			var can_next = true;
			var cdma_acc_nbr= $("#cdma_acc_nbr").val();
			var random_pwd = $("#random_pwd").val();
			if(!cdma_acc_nbr){
				pop({title:'消息',text:"请输入手机号码"},0);
				return;
			}
			if(cdma_acc_nbr && !VTypes.mobile(cdma_acc_nbr)){
				pop({title:'消息',text:"请输入正确的手机号码，如：1890731****"},0);
				return;
			}
			if(!random_pwd){
				pop({title:'消息',text:"请输入短信密码"},0);
				return;
			}
			
			var param = {};
			CommonUtils.apply(param,p_param);
			param.random_pwd =random_pwd;
			param.cdma_acc_nbr =cdma_acc_nbr;
			param.method = 'VALIDATE_RANDOM_PWS';
			Service.asExcute("TWB_ACCEPT", false, param, function(reply){
				var result = reply['RESPONSE'];
				var code = result['code'];
				var message = result['message'];
				if(code !="0000"){
					can_next = false;
					pop({title:'消息',text:message},0);
				}
			});
			return can_next;
		}
	})
	window.btnWizard = new BtnWizard();
}(window));