/**
 *@auther:Reason.Yea 
 *分页控件
 *var pager = new Pager(div_id,action_id,is_sql,param,function(reply){});
 *@param div_id:页面放置table的div_id
 *@param action_id:java或者sql服务的action_id
 *@param is_sql:是否sql服务,true表示sql，false表示java服务
 *@param param:参数，可以设置初始每页展示数据大小：{page_size:15,key1:value1,key2:value2}
 *@param call_back:用户回调函数，返回一个list对象
 *@param hide_pager：隐藏分页控件
 */
(function(scope) {
	var Pager = Base.extend({
		constructor : function(div_id,func_id,is_sql,param,call_back,exp_excel,queryTable,hide_pager,class_name) {
			this.version = "1.0";
			this.config={};
			this.config.div_id =div_id;
			this.config.table_id =div_id+"_table";
			this.config.func_id =func_id;
			this.config.is_sql =is_sql;
			this.config.call_back =call_back;
			//alert(1)
			this.config.page_size =param.page_size||10;
			this.config.page_index =1;
			this.config.exp_excel = exp_excel;
			this.queryTable =queryTable;
			this.config.hide_pager =hide_pager || false;
			//alert(1)
			this.config.class_name = class_name;
			CommonUtils.apply(this.config,param);
			this.doService();
			if(this.config.exp_excel){ //集成excel导出功能
				try{excelBase._init()}catch(e){}
			}
		},
		setParam:function(param){
			CommonUtils.apply(this.config,param);
		},
		doService:function(){
			var me = this;
			me.clear();
			var outer_contain_id = this.config.outer_contain_id;
			if(outer_contain_id && $("#"+outer_contain_id).length>0)
				$("#"+outer_contain_id).empty();
			Service.excuteNoLoading(me.config.func_id, me.config.is_sql, me.config, function(reply){
				var retObj = reply.PAGE_MODEL;
				
				if(retObj==null){
					alert('暂无数据');
					me.config.call_back(null);
					return;//防止报错
				}
				
				me.config.total_count=retObj.total_count;//总记录数
				me.config.page_count=retObj.page_count;//总页数
				me.config.page_index=retObj.page_index;//当前页索引
				me.config.page_size=retObj.page_size;//每页的记录行数
				if(me.config.is_sql){//sql
					me.config.data_list =reply[me.config.func_id];//对象列表
				}else{//java
					me.config.data_list =retObj.data_list;//对象列表
				}
				me.renderPage();
				
				var retObj1 = reply.MY_RESULT;
				if(retObj1!=null){
					me.config.call_back(me.config.data_list,retObj1);
				}else{					
					me.config.call_back(me.config.data_list);
				}
					
			
				me.renderTable();
			});
		},
		renderPage:function(){
			this.getTableDiv().html(this.genPager());
			this.initEvent();
			this.initPagerInfo();
		},
		genPager:function(){
			var me = this;
			var divHeight = me.getTableDiv().height();
			var tabHeight = "100%";
			if(divHeight){ tabHeight=me.getTableDiv().height()-20}
			//隐藏分页套件
			var style='display:block;'
			if(this.config.hide_pager)
				style='display:none;'
					
			var className ="ListForm";
			var outer_contain_id = this.config.outer_contain_id;
			if(outer_contain_id && $("#"+outer_contain_id).length>0)
				className="";
			if(this.config.class_name)
				className = this.config.class_name;
          	var divHtml= 
          	   "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='"+className+"' id='"+me.config.table_id+"' ></table>"
          	 +"<div class='pageList' style="+style+">"
	          +"	<div class='pagin'>"
	          +"  		<a href='javascript:void(0)' class='prev' id='"+me.config.table_id+"_buttonFirstPage'>首页</a>"
	          +"  		<a href='javascript:void(0)' class='prev' id='"+me.config.table_id+"_buttonPrevPage'>上页</a>"
	          +"  		<a href='javascript:void(0)' class='prev' id='"+me.config.table_id+"_buttonNextPage'>下页</a>"
	          +"  		<a href='javascript:void(0)' class='prev' id='"+me.config.table_id+"_buttonLastPage'>尾页</a>";
	          
	          if(this.config.exp_excel){
	        		divHtml +="<a href='javascript:void(0)' class='prev' style='margin-left:3px;' id='"+me.config.table_id+"_export_excel'> 导出Excel</a>";
	        		divHtml +="<a href='javascript:void(0)' class='prev' style='margin-left:3px;' id='"+me.config.table_id+"_export_all_excel'> 导出全部</a>";
	          }
	          divHtml+="<span class='skip'>"
	          +"      	</span>"
	          +"      	<input class='paySub' type='submit' value='确定'>"
	          +"  	</div>"
	          +"</div>";
          return divHtml;
		},
		initEvent:function(){
			var me = this;
			$('#'+me.config.table_id+'_buttonFirstPage').bind("click",function(){
		  		if(new Number(me.config.page_index) !=1){
	          		me.config.page_index =1;
		          	me.doService();
	          	}
			});
			$('#'+me.config.table_id+'_buttonPrevPage').bind("click",function(){
		  		if(new Number(me.config.page_index) >1){
	          		me.config.page_index =new Number(me.config.page_index)-1;
		          	me.doService();
	          	}
			});
			$('#'+me.config.table_id+'_buttonNextPage').bind("click",function(){
		  		if(new Number(me.config.page_index) < new Number(me.config.page_count)){
	          		me.config.page_index =new Number(me.config.page_index)+1;
		          	me.doService();
	          	}
			});
			$('#'+me.config.table_id+'_buttonLastPage').bind("click",function(){
				var p_page_index =me.config.page_index+"";
				var p_page_count =me.config.page_count+"";
		  		if(p_page_index!=p_page_count){
	          		me.config.page_index =me.config.page_count;
		          	me.doService();
	          	}
			});
			$('#'+me.config.table_id+'_export_excel').bind("click",function(){ //导出excel对象
				queryTable && queryTable.exportExcel(me.config);
				return false;
			});
			
			$('#'+me.config.table_id+'_export_all_excel').bind("click",function(){ //导出excel对象
				me.config.all_page="yes";
				queryTable && queryTable.exportExcel(me.config);
				return false;
			});
			me.getPager().find(".paySub").bind("click",function(){
				var ps = $("#"+me.config.table_id+"_pSize").val();
				var number = /^\d+$/;
				if(!number.test(ps)){
					alert("页面显示条数请输入数字！");
					return ;
				}
				ps = new Number(ps);
				if(ps<1){
					alert("请输入大于0的数字!") ;
					return ;
				}
				var pageSize=30;
				if(ps > pageSize){
					alert("系统不建议使用超过"+pageSize+"条记录的分页，请使用更小的数字！");
					return ;
				}
				var pi = $("#"+me.config.table_id+"_pIndex").val();
				if(!number.test(pi)){
					alert("请输入数字类型的页数!") ;
					return ;
				}
				pi = new Number(pi);
				if( pi <= 0 || pi> new Number(me.config.page_count) ){
					alert("请输入大于0且小于或等于" + me.config.page_count + "的数字!");
					return ;
				}
				me.config.page_size = ps;
				me.config.page_index =pi ;
				me.doService();
			})
		},
		initPagerInfo:function(){
			var me = this;
			var skip="共 <font color='red'>"+me.config.total_count+" </font>条   "
					+"每页<input type='text' style='width:20px;' id='"+me.config.table_id+"_pSize' size='5' value='"+me.config.page_size+"'>   "
					+"<font color='red'>"+me.config.page_index+"</font>/"+me.config.page_count+"    "	
					+"到  <input type='text' style='width:20px;' id='"+me.config.table_id+"_pIndex' size='5' value='"+me.config.page_index+"'> 页";
			me.getPager().find(".skip").html(skip);
		},
		renderTable:function(){
			var me = this;
			var tableJq = this.getTablePanel();
			tableJq.find('tr').addClass("th2");
			tableJq.find('tr:even').removeClass("th2").addClass("th3");
			tableJq.find('tr').hover(function(){
					$(this).addClass("alt");
				},function(){
					$(this).removeClass("alt");
				});
			tableJq.find('tr').click(function(){
				$(this).addClass("clk").siblings().removeClass("clk");
				var curIndex = $(this).index();
				var curData = me.config.data_list[curIndex-1];//当前数据对象
				var exec_func = me.config.div_id+"_afterScroll";
				if(!exec_func)return;
				try{
					var fun = eval(exec_func);
					if(typeof(fun) != "function") return;
					fun.call(window,curData);//执行自定义函数
				}catch(e){}
			});
		},
		getTableDiv:function(){
			return $("#"+this.config.div_id);
		},
		getPager:function(){
			return this.getTablePanel().next(".pageList");
		},
		getTablePanel:function(){
			return $("#"+this.config.table_id);
		},
		clear:function(){
			this.getTableDiv().html("");
		}
		
	})
	window.Pager =Pager;
}(window));

