
(function(scope) {
	var PageSelect = Base.extend({
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
		createSelect:function(title,select_id,fields,func_id,is_sql,chooseMethod,parent_id,is_show,show_title){
			this.fields = fields;
			this.func_id = func_id;
			this.chooseMethod = chooseMethod;
			this.is_sql = is_sql;
			this.select_id = select_id;
			this.show_title =show_title;
			this.is_show = is_show;
			this.parent_id =parent_id;
			if($("#"+this.getSelectId()).length>0)
				return;
			//if(this.is_show != false)
			//	acpSplite.addTableTitle(title,null,null,select_id+"_title_div",parent_id);
			var tableHtmlArr =[];
    		var style ="display:'';"
    		if(this.is_show == false)
    			style ="display:none;"
    		var cname = this.getCname();
			tableHtmlArr.push("<div style="+style+">");
			
			var style ="display:'';"
    		if(this.show_title == false)
    			style ="display:none;"
			
			tableHtmlArr.push('<span style='+style+'>'+cname+':</span><select id='+this.getSelectId()+' style="width:200px;margin-top:5px;height:25px;">');
            tableHtmlArr.push('</select>');
			tableHtmlArr.push("</div>");
			
			if(this.parent_id){
				
				$("#"+this.parent_id).append($(tableHtmlArr.join("")));
			}else{
				acpSplite.getDivPanel().append($(tableHtmlArr.join("")));
			}
		},
		getCname:function(){
			for(var i=0;i<this.fields.length;i++)
			{
				var field = this.fields[i];
				var field_name = field['field_name'];
				var cname = field['cname'];
				var is_visible = field['is_visible'];
				if(is_visible !="no"){
					return cname;
				}
			}
			return "";
		},
		addRecord:function(results,p_param,callBack){
			var me =this;
			if(results){
				this._genRecord(results);
			}else
			{ 
				var param = p_param || {};
				Service.asExcute(this.func_id.toUpperCase(),this.is_sql,param,function(reply){ 
					var func_id  =me.func_id.toUpperCase();
					var result ,resultMap ={};
					if(me.is_sql){
						result=reply[func_id];
					}else
					{
						var resultMap = reply['RET_OBJ'];
						if(resultMap)
							result = resultMap['ret_obj'];
					}
					if(result){
						me._genRecord(result);
						callBack && callBack(result,resultMap);
					}
				})
				
			}
			
		},
		hide:function(){
			$("#"+this.select_id+"_title_div").hide();
			$("#"+this.getSelectId()).hide();
		},
		show:function(){
			$("#"+this.select_id+"_title_div").show();
			$("#"+this.getSelectId()).show();
		},
		_genRecord:function(records){
			for(var w=0;w<records.length;w++)
			{
				var result =records[w];
				var optionjQ =null;
				for(var i=0;i<this.fields.length;i++)
				{
					var field = this.fields[i];
					var field_name = field['field_name'];
					var value = result[field_name];
					
					var is_visible = field['is_visible'];
					if(is_visible !="no"){
						optionjQ =$("<option value='"+value+"'>"+value+"</option>");
						$("#"+this.getSelectId()).append(optionjQ);
					}
					if(w==0)
						this.bindEvent(field,$("#"+this.getSelectId()));
				}
				
				
				for(var i=0;i<this.fields.length;i++)
				{
					var field = this.fields[i];
					var field_name = field['field_name'];
					var is_visible = field['is_visible'];
					var value = result[field_name];
					optionjQ.attr(field_name,value);
				}
			}
		},
		bindEvent:function(field,cellElement){
			var me =this;
			var events = field['events']
			for(p in events)
			{
				var event_name = p;
				var event_func = events[event_name];
				cellElement.bind(event_name,function(){
					var trJq = $(this).closest("tr");
					if(typeof(event_func) =="string"){
						try{
							eval(event_func+"(me,trJq)");
						}catch(e){}
					}else{
						event_func(me,trJq);
					}
					
				});
			}
		},
		disabledForm:function(){ //设置文本框失效
			$("#"+this.getSelectId()).attr("disabled",true);
		},
		enableForm:function(){ //设置文本框失效
			$("#"+this.getSelectId()).removeAttr("disabled");
		},
		getCheckLen:function(){
			return $("#"+this.getSelectId()).find("option").length;
		},
		getSelValue:function(trJq,field_name){
			var opriontJq =$("#"+this.getSelectId()).find("option:selected");
			return opriontJq.attr(field_name)
		},
		getSelectId:function(){
			this.id = this.config.serv_no+"_select_"+this.select_id;
			return this.id;
		},
		emptyRecords:function(){
			$("#"+this.getSelectId()).find(":option)").remove();
		},
		getSlectLen:function(){
			return $("#"+this.getSelectId()).find("option").length;
		},
		getFirstRecord:function(trJq,field_name){
			var opriontJq =$("#"+this.getSelectId()).find("option:first");
			return opriontJq.attr(field_name)
		}
	})
	window.PageSelect = PageSelect;
}(window));