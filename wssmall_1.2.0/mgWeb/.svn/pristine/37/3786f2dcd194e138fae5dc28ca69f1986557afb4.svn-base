/**
 * 业务受理核心模块
 */
(function(scope) {
	var PageTable = Base.extend({
		constructor : function(config) {
			this.config ={};
			CommonUtils.apply(this.config,config);
			this.version = "1.0";
			this.id = '';
			this.fields = [];
			this.func_id = '';
			this.is_sql = false;
			this.chooseMethod = '';
			
		},
		createTable:function(title,table_id,fields,func_id,is_sql,chooseMethod,parent_id,is_show){
			this.fields = fields;
			this.func_id = func_id;
			this.chooseMethod = chooseMethod;
			this.is_sql = is_sql;
			this.parent_id =parent_id;
			this.is_show = is_show;
			this.table_id = table_id;
			if($("#"+this.getTableId()).length>0){
				this.addChoose();
				return;
			}
			acpSplite.addTableTitle(title,null,null,table_id+"_title_div",parent_id);
			var tableHtmlArr =[];
			tableHtmlArr.push("<div>");
			tableHtmlArr.push('<table width="100%" border="0" class="ListForm" cellspacing="0" cellpadding="0" id='+this.getTableId()+'>');
            tableHtmlArr.push('<tr class="th2">');
        	chooseMethod && tableHtmlArr.push('<th width="50px">选择</th>');
        	for(var i=0;i<this.fields.length;i++)
        	{
        		var field =this.fields[i];
        		var is_visible = field['is_visible'];
        		if(!is_visible)
        			is_visible ='';
        		var style ="display:'';"
        		if(is_visible =="no")
        			style ="display:none;"
        	    tableHtmlArr.push("<th style="+style+">"+field['cname']+"</th>");
        	} 
            tableHtmlArr.push('</tr>');
			tableHtmlArr.push("</div>");
			if(this.parent_id)
				$("#"+this.parent_id).append($(tableHtmlArr.join("")));
			else
				acpSplite.getDivPanel().append($(tableHtmlArr.join("")));
			this.addChoose();
			this.hide();
			
		},
		createPagerTable:function(title,table_id,fields,func_id,is_sql,chooseMethod,parent_id,is_show){ //兼容pager翻页对象
			this.fields = fields;
			this.func_id = func_id;
			this.chooseMethod = chooseMethod;
			this.is_sql = is_sql;
			this.parent_id =parent_id;
			this.is_show = is_show;
			this.table_id = table_id;
			if($("#"+this.getTableId()).length>0){
				this.addChoose();
				return;
			}
			//acpSplite.addTableTitle(title,null,null,table_id+"_title_div",parent_id);
			
			var tableHtmlArr =[];
			tableHtmlArr.push("<div>");
			tableHtmlArr.push('<table width="100%" border="0" class="ListForm" cellspacing="0" cellpadding="0" id='+this.getTableId()+'>');
            tableHtmlArr.push('<tr class="th2">');
        	chooseMethod && tableHtmlArr.push('<th width="50px">选择</th>');
        	for(var i=0;i<this.fields.length;i++)
        	{
        		var field =this.fields[i];
        		var is_visible = field['is_visible'];
        		if(!is_visible)
        			is_visible ='';
        		var style ="display:'';"
        		if(is_visible =="no")
        			style ="display:none;"
        	    tableHtmlArr.push("<th style="+style+">"+field['cname']+"</th>");
        	} 
            tableHtmlArr.push('</tr>');
			tableHtmlArr.push("</div>");
			if(this.parent_id)
				$("#"+this.parent_id).append($(tableHtmlArr.join("")));
			else
				acpSplite.getDivPanel().append($(tableHtmlArr.join("")));
			this.addChoose();
			this.hide();
		},
		createHeaderTable:function(title,table_id,fields,func_id,is_sql,chooseMethod,parent_id,is_show,headTitle,is_refresh){
			this.fields = fields;
			this.func_id = func_id;
			this.chooseMethod = chooseMethod;
			this.is_sql = is_sql;
			this.parent_id =parent_id;
			this.is_show = is_show;
			this.table_id = table_id;
			if($("#"+this.getTableId()).length>0 && is_refresh!=true){
				this.addChoose();
				return;
			}
			
			//acpSplite.addTableTitle(title,null,null,table_id+"_title_div",parent_id);
			
			var tableHtmlArr =[];
			tableHtmlArr.push("<div>");
			tableHtmlArr.push('<table align="center" cellspacing="0" cellpadding="0"  class="grayth"  id='+this.getTableId()+'><tbody>');
            headTitle && tableHtmlArr.push('<tr><th colspan="5">'+headTitle+'</th></tr>');
            tableHtmlArr.push('<tr>');
        	chooseMethod && tableHtmlArr.push('<th width="30px">选择</th>');
        	for(var i=0;i<this.fields.length;i++)
        	{
        		var field =this.fields[i];
        		var is_visible = field['is_visible'];
        		var p_style = field['style'];
        		if(!is_visible)
        			is_visible ='';
        		var style ="display:'';"
        		if(is_visible =="no")
        			style ="display:none;"
        		var width =field['width'];
        		if(width)
        			style+=";width:"+width;
        			style+=(p_style||'');
        	    tableHtmlArr.push("<th style="+style+">"+field['cname']+"</th>");
        	} 
            tableHtmlArr.push('</tr>');
            tableHtmlArr.push('</body></table>');
			tableHtmlArr.push("</div>");
			
			if(is_refresh)
			  $("#"+this.getTableId()).parent().remove();
			else
			 $("#"+this.getTableId()).remove(); //插入前先删除对象
			
			if(this.parent_id)
				$("#"+this.parent_id).append($(tableHtmlArr.join("")));
			else
				acpSplite.getDivPanel().append($(tableHtmlArr.join("")));
			this.addChoose();
			this.hide();
		},
		createV5HeaderTable:function(title,table_id,fields,func_id,is_sql,chooseMethod,parent_id,is_show,headTitle,is_refresh,style){
			this.fields = fields;
			this.func_id = func_id;
			this.chooseMethod = chooseMethod;
			this.is_sql = is_sql;
			this.parent_id =parent_id;
			this.is_show = is_show;
			this.table_id = table_id;
			if($("#"+this.getTableId()).length>0 && is_refresh!=true){
				this.addChoose();
				return;
			}
			
			var tableHtmlArr =[];
			tableHtmlArr.push("<div>");
			tableHtmlArr.push('<table align="center" cellspacing="0" style="'+style+'" cellpadding="0" class="goodsdetail_tab " border="1"   id='+this.getTableId()+'><tbody>');
            headTitle && tableHtmlArr.push('<tr><th colspan="5">'+headTitle+'</th></tr>');
            tableHtmlArr.push('<tr>');
        	chooseMethod && tableHtmlArr.push('<th width="30px">选择</th>');
        	for(var i=0;i<this.fields.length;i++)
        	{
        		var field =this.fields[i];
        		var is_visible = field['is_visible'];
        		var p_style = field['style'];
        		if(!is_visible)
        			is_visible ='';
        		var style ="display:'';"
        		if(is_visible =="no")
        			style ="display:none;"
        		var width =field['width'];
        		if(width)
        			style+=";width:"+width;
        			style+=(p_style||'');
        	    tableHtmlArr.push("<th style="+style+">"+field['cname']+"</th>");
        	} 
            tableHtmlArr.push('</tr>');
            tableHtmlArr.push('</body></table>');
			tableHtmlArr.push("</div>");
			
			if(is_refresh)
			  $("#"+this.getTableId()).parent().remove();
			else
			 $("#"+this.getTableId()).remove(); //插入前先删除对象
			
			if(this.parent_id)
				$("#"+this.parent_id).append($(tableHtmlArr.join("")));
			else
				acpSplite.getDivPanel().append($(tableHtmlArr.join("")));
			this.addChoose();
			this.hide();
		},
		addBtnRigthTitle:function(btnJq){
			var btnpParent =$("#"+this.table_id+"_title_div").find("span");
			btnpParent.empty();
			btnpParent.append(btnJq);
		},
		addChoose:function(){
			if(this.chooseMethod) {
				var fields = [];
				if("checkbox"==this.chooseMethod){
					fields[0] = {"field_name":this.getTableId()+"_checkbox","cname":"选择","input_method":"checkbox",'events':{'click':this.table_id+"_checkbox_onclick"}};
				}else if("radio"==this.chooseMethod){
					fields[0] = {"field_name":this.getTableId()+"select","radio":"选择","input_method":"radio",'events':{'click':this.table_id+"_radio_onclick"}}
				}
				this.fields = fields.concat(this.fields);
			}
		},
		addRecord:function(results,p_param,callBack){
			var me =this;
			
			if(results){
				this._genRecord(results);//追加记录
				callBack && callBack();
			}else
			{ //数据库查询记录
				var param = p_param || {};
				Service.asExcute(this.func_id.toUpperCase(),this.is_sql,param,function(reply){ //获取卡片信息
					var func_id  =me.func_id.toUpperCase();
					var result ,resultMap ={};
					if(me.is_sql){
						result=reply[func_id];
					}else
					{
						var resultMap = reply['RET_OBJ'];
						result = resultMap && resultMap['ret_obj'];
					}
					me.emptyRecords(); //数据库查询时先删除
					if(result){
						me._genRecord(result);
						callBack && callBack(result,resultMap);
					}
				})
				
			}
			
		},
		disabledTable:function(){ //设置文本框失效
			$("#"+this.getTableId()).find(":radio,:checkbox").attr("disabled",true);
		},
		hide:function(){
			$("#"+this.table_id+"_title_div").hide();
			$("#"+this.getTableId()).closest("div").hide();
		},
		getTdByExpress:function(express){
			return $("#"+this.getTableId()).find(express);
		},
		show:function(){
			$("#"+this.table_id+"_title_div").show();
			$("#"+this.getTableId()).closest("div").show();
		},
		_genRecord:function(records){
			
			var hasTrClass =this.config.hasTrClass;
			if(records!=null&&records!=""){
				if(this.is_show != false)
					this.show();
				for(var w=0;w<records.length;w++)
				{
					var result =records[w];
					var trJq = $("<tr class='th3'></tr>");
					if(hasTrClass == false)
					 	trJq = $("<tr></tr>");
					
					$("#"+this.getTableId()).append(trJq);
					
					//tr给予绑定事件
					trJq.bind("click",function(){
						try{
							eval(me.table_id+"_tr_onclick"+"(me,trJq,$(this))");
						}catch(e){}
					});
					var row_index =0;
					var me =this;
					for(var i=0;i<this.fields.length;i++)
					{
						var field = this.fields[i];
						
						
						var field_name = field['field_name'];
						var id=field_name+"_"+i;
						var name=field_name;
						var value = result[field_name];
						if(!value)
							value = result[field_name.toUpperCase()];
						row_index = result['index'];
						var cellElement =this.createElement(field,id,name,value,w,result);
						var class_name ='';
						
						w%2 ==0?class_name='tdb c':class_name='tdc c';
						var is_visible = field['is_visible'];
		        		var style ="display:''";
		        		if(is_visible =="no")
		        			style ='display:none;';
						var tdJq = $("<td class='"+class_name+"' style='"+style+"'></td>");
						if(hasTrClass == false)
							tdJq = $("<td  style='"+style+"'></td>");
						trJq.append(tdJq);
						tdJq.append(cellElement);
						if(w==0 && this.sel_record !="no"){
							trJq.find(":checkbox,:radio").attr("checked",true); 
							trJq.trigger("click");
						}
						trJq.data("record_data",result);
						
						if(this.config.background && row_index%2==0)
							tdJq.css("background",this.config.background);
						me.bindEvent(field,cellElement);
					}
					
				}
		 }else {
		 	
		 }
			
		},
		bindEvent:function(field,cellElement){
			var me =this;
			var events = field['events']
			
			//字符串解析为json对象
			if(typeof events === 'string')
			{
				var event_name = events.split(":")[0];
				var event_func = events.split(":")[1];
				events ={};
				event_name =event_name.substring(1);
				event_func =event_func.substring(0,event_func.length-1);
				events[event_name] = event_func;
				
			}
			for(p in events)
			{	
				var event_name = p;
				var event_func = events[event_name];
				cellElement.bind(event_name,function(){
					var trJq = $(this).closest("tr");
					if(event_name =="click")
					{
						$(this).closest("tr").find(":radio,:checkbox").attr("checked",true);
					}
					if(typeof(event_func) =="string"){
						try{
							eval(event_func+"(me,trJq,$(this))");
						}catch(e){}
					}else{
						event_func(me,trJq,$(this));
					}
					
				});
			}
		},
		exportExcel:function(req_param){ //导出excel对象
			var titles = [];
			var fieldnames = [];
			for(var i=0;i<this.fields.length;i++)
        	{
        		var field =this.fields[i];
        		var is_visible = field['is_visible'];
        		if(is_visible !='no' && field['field_name']!='more')
        		{
        			titles.push(field['cname']);
        			fieldnames.push(field['field_name'])
        		}
        	} 
        	var config ={};
        	config.titles = titles;
        	var p_req_param = {};
        	CommonUtils.applyStr(p_req_param,req_param);
        	if(p_req_param.all_page =="yes")
        		p_req_param['page_size'] = 500;
        	config['req_param'] = p_req_param;
        	delete config['req_param']['call_back'];
        	delete config['req_param']['div_id'];
        	config.fieldnames = fieldnames;
			excelBase.downLoadExcel(config);
		},
		getCheckLen:function(){
			return $("#"+this.getTableId()).find(":checked").length;
		},
		getLength:function(){
			return $("#"+this.getTableId()).find("tr:gt(0)").length;
		},
		getSelValue:function(trJq,field_name,inner_html){
			if(!trJq){
				trJq = $("#"+this.getTableId()).find(":checked").closest("tr");
				
			}
			if(!trJq || trJq.length==0) //不存在默认选择第一个
			{	
				trJq = $("#"+this.getTableId()).find("tr:eq(0)");
				trJq.find(":checkbox,:radio").attr("checked",true);
			}
			var value =trJq.find("[name='"+field_name+"']").text();
		
			if(inner_html){
				var sel_element =trJq.find("[name='"+field_name+"']");
				sel_element.find("img").width(""); //对图片的处理，进行略缩图的处理
				value =sel_element.html();
				sel_element.find("img").width("160px");
			}
			return value;
		},
		getSelTr:function(trJq){
			if(!trJq){
				trJq = $("#"+this.getTableId()).find(":checked").closest("tr");
				
			}
			if(!trJq || trJq.length==0) //不存在默认选择第一个
			{	
				trJq = $("#"+this.getTableId()).find("tr:eq(0)");
				trJq.find(":checkbox,:radio").attr("checked",true);
			}
			return trJq;
		},
		getSelValueAction:function(trJq,field_name){
			if(!trJq){
				trJq = $("#"+this.getTableId()).find(":checked").closest("tr");
			}
			if(!trJq || trJq.length==0) //不存在默认选择第一个
			{	
				trJq = $("#"+this.getTableId()).find("tr:eq(0)");
				trJq.find(":checkbox,:radio").attr("checked",true);
			}
			var sel_element =trJq.find("[name='"+field_name+"']");
			var cms_action = sel_element.attr("cms_action");
			return cms_action;
		},
		
		setValue:function(trJq,field_name,field_value){
			if(!trJq){
				trJq = $("#"+this.getTableId()).find(":checked").closest("tr");
				
			}
			if(!trJq || trJq.length==0) //不存在默认选择第一个
			{	
				trJq = $("#"+this.getTableId()).find("tr:eq(0)");
				trJq.find(":checkbox,:radio").attr("checked",true);
			}
			var value =trJq.find("[name='"+field_name+"']").html(field_value);
			trJq.find("[name='"+field_name+"']").find("img").width("160px"); 
			return value;
		},
		geAllValue:function(field_name){
			var fieldsArr = [];
			$("#"+this.getTableId()).find("tr:gt(0)").each(function(){
				var value =$(this).find("[name='"+field_name+"']").text();
				value && fieldsArr.push(value);
			})
			return fieldsArr;
		},
		getSelChecked:function(trJq){
			var check_val =trJq.find(":checkbox").attr("checked");
			var radio_val =trJq.find(":radio").attr("checked");
			value =check_val?check_val:radio_val;
			return value;
		},
		getTableId:function(){
			this.id = this.config.serv_no+"_list_"+this.table_id;
			
			return this.id;
		},
		emptyRecords:function(index){
			p_index =index||0;
			$("#"+this.getTableId()).find("tr:gt("+p_index+")").remove();
		},
		isImgPath:function(value){
			 if(value.match( /.jpg|.gif|.png|.bmp/i) && !value.match(/<img/i))
			 	return true;
			return false;
		},
		isHrefPath:function(value){
			 if(value.match( /^<a/ig))
			 	return true;
			return false;
		},
		createElement:function(field,id,name,value,index,result){
			var input_method =field['input_method'] ||'text' ,cellElement = null;
			this.id=id;
			this.name =name;
			switch(input_method) {
				case "text": { //
					value =value?value:'';
		  	 		cellElement=$("<input type='text' value="+value+this.toString()+" class='wd98'></input>");
		  	 		return cellElement;
				}
				case "mod_img": case "fee_img":case "cancel_img":{ //修改
					var html= [];
					var style=" style='width:100%;height:30px;margin-left:30px;'";
					html.push("<div "+style+"><div class='btn_s fl' "+this.toString()+"'>");
				    html.push("	<a href='javascript:void(0)' class='W_btn_c'><span><em>"+field['value']+"</em></span></a>");
					html.push("</div></div>");
					var btnJQ =$(html.join(""));
					return btnJQ;
	
				}
				case "radio": { //下拉框
		  	 		cellElement=$("<input type='radio'  "+this.toString()+"></input>");
					return cellElement;
				}
				case "checkbox": { //下拉框
		  	 		cellElement=$("<input type='checkbox'  "+this.toString()+"></input>");
					return cellElement;
				}
				case "span": { //下拉框
					//var state_value = result['state_name'];
					var imgHtml = "";
//					if(state_value =="接单" && name =="pre_ord_no"){
//						imgHtml = "<img src='/public/v4/common/ress/image/home/new.gif'/>";
//					}
					value =value?value:'';
					cellElement=$(imgHtml+"<span "+this.toString()+">"+value+"</span>");
					return cellElement;
				}
				case "inner_text": { //下拉框
					value =value?value:'';
					var is_img_path = this.isImgPath(value);
					var prefix ="";
					if(is_img_path){
						this.action_type=='A' && (prefix ="<span style='color:red;font-weight:bold;'>【例如：】</span>");
						value ="<img  src='"+value+"' />";
						value = value.replace(prefix,'')
					}
					var style = "";
					if(value.length>200){
						style = "height:100px;width:250px;overflow:auto;"
					}
					cellElement=$("<div style='heigth:10px;"+style+"' "+this.toString()+">"+prefix+value+"</div>");
					cellElement.find("img").width("160px");
				
					if(is_img_path)
						cellElement.attr("cms_action","cut_img");
					if(this.isHrefPath(value))
						cellElement.attr("cms_action","a_href");
					
					return cellElement;
				}
				
				case "hight": { //高亮
					
					value =value?value:'';
					cellElement=$("<span "+this.toString()+" class='f_red'>"+value+"</span>");
					return cellElement;
				}
				case "alink": { //高亮
					value =value?value:field['value'];
					!value && (value ='');
					cellElement=$("<span><a href='javascript:void(0);' "+this.toString()+" class='linkgreen'>"+value+"</a></span>");
					return cellElement;
				}
				case "more": { //更多
					value =value?value:field['value'];
					!value && (value ='');
					cellElement=$("<span><a href='javascript:void(0);' "+this.toString()+" class='linkgreen'>详细信息</a></span>");
					cellElement.bind("click",function(){
						window.more_onclick($(this));
					})
					return cellElement;
				}
				case "show_order": { //更多
					value =value?value:field['value'];
					!value && (value ='');
					cellElement=$("<span><a href='javascript:void(0);' "+this.toString()+" class='linkgreen'>查看订单</a></span>");
					cellElement.bind("click",function(){
						window.show_order_detail_onclick($(this));
					})
					return cellElement;
				}
				case "btnlink": { //高亮
					var events =field['events'];
					value =value?value:field['value'];
					value =value?value:'';
					cellElement=$("<span><a href='javascript:void(0);' "+this.toString()+" class='linkyellow'>"+value+"</a></span>");
					return cellElement;
				}
				case "dynamic_img": { //高亮
					value =value?value:field['value'];
					value =value?value:'';
					if(value =="未激活")
						cellElement=$("<span "+this.toString()+" ><img src ='/service/accept/image/wjh.gif'/></span>");
					else if(value =="使用中")
						cellElement=$("<span "+this.toString()+"><img src ='/service/accept/image/sy.gif'/></span>");
					else if(value =="开通")
						cellElement=$("<span "+this.toString()+"><img src ='/service/accept/image/kt.gif' style='cursor:hand;'/></span>");
					else if(value=="取消"){
						cellElement=$("<span "+this.toString()+"><img src ='/service/accept/image/qx.gif' style='cursor:hand;'/></span>");
					}else if(value=="设置"){
						cellElement=$("<span "+this.toString()+"><img src ='/service/accept/image/sz3.gif' style='cursor:hand;'/></span>");
					}else if(value=="未使用"){
						cellElement=$("<span "+this.toString()+"><img src ='/service/accept/image/wsy.gif' style='cursor:hand;'/></span>");
					}else
						cellElement=$("<span "+this.toString()+"><a href='javascript:void(0);' "+this.toString()+" class='linkgreen'>"+value+"</a></span>");
					return cellElement;
				}
				case "my_order_btn": { //我的订单
					value =value?value:field['value'];
					!value && (value ='');
					cellElement=$("<span>"
						+"<a href='javascript:void(0);' "+this.toString()+" class='linkblue' onclick='see_order_onclick(this)'>查看订单<br/></a>"
						+"<a href='javascript:void(0);' "+this.toString()+" class='linkblue' onclick='cancel_goods_onclick(this)'>退货申请<br/></a>"
						+"<a href='javascript:void(0);' "+this.toString()+" class='linkblue' onclick='trans_goods_onclick(this)'>换货申请</a>"
						+"</span>");
					return cellElement;
				}
				case "ord_app_ord_btn": { //我的订单
					value =value?value:field['value'];
					!value && (value ='');
					cellElement=$("<span>"
						+"<a href='javascript:void(0);' "+this.toString()+" class='linkblue' onclick='ord_app_onclick(this)'>申请</a>"
						+"</span>");
					return cellElement;
				}
			case "ord_app_qry_btn": { //我的订单
					value =value?value:field['value'];
					!value && (value ='');
					cellElement=$("<span>"
						+"<a href='javascript:void(0);' "+this.toString()+" class='linkblue' onclick='ord_app_qry_onclick(this)'>查看</a>"
						+"</span>");
					return cellElement;
				}
				
				
				
			}
		},
		toString:function(){
			return " id ="+this.id +"  name ="+this.name;
		}
	})
	window.PageTable = PageTable;
}(window));