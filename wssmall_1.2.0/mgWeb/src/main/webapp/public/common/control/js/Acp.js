/**
 * 业务受理核心模块
 * 
 */
(function(scope) {
	var AcpBean = Base.extend({
		constructor : function(config) {
			this.version = "1.0";
			this.config ={};
			this.pageForms =[];
			this.pageTables = [];
			this.pageSelects = [];
		},
		render:function(css_lx,no_menu){// 构造菜单布局
			try{
				CommonUtils.apply(pageWizard.config,this.config);
				CommonUtils.apply(btnWizard.config,this.config);
				if(!no_menu){
					if("css2" ==css_lx){
						btnWizard.genBtns2();
						pageWizard.genMenu2();
					}else{
						btnWizard.genBtns();
						pageWizard.genMenu();
					}
				}
			}catch(e){}
			CommonUtils.apply(acpSplite.config,this.config);
			
		},
		getPageTables:function(){
			var pageTables =[];
			for(var i=0;i<this.pageTables.length;i++)
			{
				var pageTableMap =this.pageTables[i];
				var pageTable = pageTableMap['pageTable'];
				pageTables.push(pageTable);
			}
			return pageTables;
		},
		addPageForms:function(obj){
			this.pageForms.push(obj);
		},
		getPageForm:function(){
			for(var i=0;i<this.pageForms.length;i++)
			{
				var pageFormMap =this.pageForms[i];
				var pageForm = pageFormMap['pageForm'];
					return pageForm;
			}
		},
		getPageSelectById:function(){
			for(var i=0;i<this.pageSelects.length;i++)
			{
				var pageSelectMap =this.pageSelects[i];
				var pageSelect = pageSelectMap['pageSelect'];
					return pageSelect;
			}
		},
		getPageTableById:function(table_id){
			for(var i=0;i<this.pageTables.length;i++)
			{
				
				var pageTableMap =this.pageTables[i];
				var pageTable = pageTableMap['pageTable'];
				if(pageTable.table_id ==table_id){
					return pageTable;
				}
			}
			return null;
		},
		getPageFormById:function(form_id){
			for(var i=0;i<this.pageForms.length;i++)
			{
				
				var pageTableMap =this.pageForms[i];
				var pageForm = pageTableMap['pageForm'];
				if(pageForm.form_id ==form_id){
					return pageForm;
				}
			}
			return null;
		},
		
		addPageTables:function(obj){
			this.pageTables.push(obj);
		},
		removePageTables:function(table_id){
			for(var i=0;i<this.pageTables.length;i++)
			{
				var pageTableMap =this.pageTables[i];
				var pageTable = pageTableMap['pageTable'];
				if(pageTable.table_id ==table_id){
					this.pageTables.splice(i,1);
					break;
				}
			}
		},
		addPageSelects:function(obj){
			this.pageSelects.push(obj);
		}
	})
	
	//serv_no工厂,根据serv_no缓存应用对象
	var AcpBeanFactory = Base.extend({
		constructor : function(config) {
			this.version = "1.0";
			this.config ={};
			this.acpBeans = [];
			this.callBackData=[];
			this.feeData ={};
			this.config.jx_method = '';
			this.req_param ={};
			this.fail_pre_asks ={}; //保存失败受理单，再次点击确定的时候不需要再新增
			this.serv_nos ='';
			this.temp_value=''; //页面操作的时候设置需要跳转到哪一步 
			this.is_succ =false; //判断是否处理成功
			this.msg ="";
			this.logon_valid_val ='';
		},
		getFailPreAsk:function(){
			return this.fail_pre_asks;
		},
		setFailPreAsk:function(preAskInfo){
			var pre_mem_ord_no = preAskInfo['PRE_MEM_ORD_NO'];
			var exist = false;
			for(p in this.fail_pre_asks)
			{
				var temp_pre_mem_ord_no = this.fail_pre_asks[p]['PRE_MEM_ORD_NO'];
				if(temp_pre_mem_ord_no ==pre_mem_ord_no)
					exist = true;
			}
			if(!exist)
				this.fail_pre_asks[pre_mem_ord_no] =preAskInfo;
		},
		removeFailPreAsk:function(p_pre_mem_ord_no){
			for(p in this.fail_pre_asks)
			{
				var pre_mem_ord_no = p;
				var preAskInfo = this.fail_pre_asks[p];
				if(pre_mem_ord_no == p_pre_mem_ord_no)
				{
					delete this.fail_pre_asks[p];
				}
			}
		},
		getFailAskByPreNo:function(){
			
		},
		genAcpBeanByServNo:function(serv_no){
			var acpBean = new AcpBean();
			acpBean.config.serv_no = serv_no;
			this.acpBeans.push(acpBean);
		},
		getAcpBeanByServNo:function(serv_no){
			for(var i=0;i<this.acpBeans.length;i++)
			{
				
				var acpBean = this.acpBeans[i];
				if(acpBean.config.serv_no == serv_no)
					return acpBean;
			}
		},
		assembleAcpBeanData:function(){ //组装保存数据
			var datas =[];
			for(var i=0;i<this.acpBeans.length;i++)
			{
				var acpBean = this.acpBeans[i];
				var pageForms = acpBean.pageForms;
				var serv_no =acpBean.config.serv_no;
				var obj ={};
				for(var i=0;i<pageForms.length;i++)
				{	
					var id =pageForms[i].id;
					if(id.indexOf("_only_view")>-1)
						continue;
					var domValues =getInputDomain(id);
					var card ={};
					delete domValues['logon_valid'];
					for(p in domValues)
					{
						var field_name = p.substring(serv_no.length+1,p.length);
						card[field_name.toUpperCase()] = domValues[p];
					}
					var DEL_PRE_ORD_NO = acpBean.DEL_PRE_ORD_NO;
					var DEL_PRE_MEM_ORD_NO = acpBean.DEL_PRE_MEM_ORD_NO;
					obj['serv_no'] =serv_no;
					obj['card'] = card;
					DEL_PRE_ORD_NO && (obj['DEL_PRE_ORD_NO'] = DEL_PRE_ORD_NO);
					DEL_PRE_MEM_ORD_NO && (obj['DEL_PRE_MEM_ORD_NO'] = DEL_PRE_MEM_ORD_NO);
//					var msg ="";
//					for(p in obj)
//						msg+=p+":"+obj[p];
//					alert(msg)
				}
				datas.push(obj);
			}
			return datas;
		}, 
		//返回接口数据
		setCallBackData:function(result){
			this.callBackData = result;
		},
		getCallBackData:function(result){
			return this.callBackData 
		},
		setFeeData:function(feeData){
			this.feeData =feeData;
		},
		getFeeData:function(feeData){ //返回支付数据
			return this.feeData
		},
		setJkMethod:function(jx_method){
			this.config.jx_method =jx_method;
		},
		getJkMethod:function(){
			var jx_method = this.config.jx_method;
			return jx_method;	
		},
		setFeeJkMethod:function(fee_jx_method){
			this.config.fee_jx_method =fee_jx_method;
		},
		getFeeJkMethod:function(){
			var fee_jx_method = this.config.fee_jx_method;
			return fee_jx_method;	
		},
		setReq_param:function(req_param){
			this.req_param =req_param;
			var action_type =this.req_param['action_type'];
			if(!action_type)
				this.req_param['action_type'] ='A';
		},
		getRequestParameter:function(name){
			return this.req_param[name]
		},
		getUrl:function(){
			var pageUrl = document.location.href;
			var source_url = document.referrer;
			if (!source_url) {
		        try {
		            if (window.opener) {
		            	
		                source_url = window.opener.location.href;
		            }
			    }catch (e) {}
		    }
		    return source_url;
		}
	})
	window.acpBeanFactory = new AcpBeanFactory();
	window.AcpBean = AcpBean;
}(window));


