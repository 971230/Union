/**
 *列表树控件
 *@auther:pzh
 *
 */
var GridEdit = {
	tree_oper_mode : 'simple_model',
	click_num : 1,
	click_timer : null,
	editTables : function(){
		for(var i=0;i<arguments.length;i++){
			this.setTableCanEdit(arguments[i]);
		}
	},
	setTableCanEdit : function(table){		//设置表格是可编辑的
		for(var i=1; i<table.rows.length;i++){
			this.setRowCanEdit(table.rows[i]);
		}
	},
	setRowCanEdit : function(row){
		var me = this;
		for(var j=0;j<row.cells.length; j++){
			//如果当前单元格指定了编辑类型，则表示允许编辑
			var editType = row.cells[j].getAttribute("EditType");
			if(!editType){
				//如果当前单元格没有指定，则查看当前列是否指定
				editType = row.parentNode.rows[0].cells[j].getAttribute("EditType");
			}
			if(editType){
				row.cells[j].ondblclick = function (){
					me.editCell(this);
				}
			}
		}
	},
	editCell : function(element, editType){
		this.click_num = 2;
		clearTimeout(GridEdit.click_timer);
		if(this.tree_oper_mode != 'oper_model')
			return ;
		var db_click = element.getAttribute("db_click");
		if(db_click == 'no')
			return ;
		var editType = element.getAttribute("EditType");
		if(!editType){
			//如果当前单元格没有指定，则查看当前列是否指定
			editType = element.parentNode.parentNode.rows[0].cells[element.cellIndex].getAttribute("EditType");
		}
		
		switch(editType){
			case "TextBox":
				var ele_content = $("span[fist_ele='yes']",element);
				var value = "";
				if(null != ele_content && ele_content.length > 0){
					value = $(ele_content).html();
				}else {
					value = element.innerHTML;
				}
				this.createTextBox(element, value, element.innerHTML);
				break;
			case "DropDownList":
				this.createDropDownList(element);
				break;
			default:
				break;
		}
	},
	createTextBox : function(element, value, html){	//为单元格创建可编辑输入框
		var me = this;
		//检查编辑状态，如果已经是编辑状态，跳过
		var editState = element.getAttribute("EditState");
		if(editState != "true"){
			//创建文本框
			var textBox = document.createElement("INPUT");
			textBox.type = "text";
			textBox.className="EditCell_TextBox";
			
			
			//设置文本框当前值
			if(!value){
				value = element.getAttribute("Value");
			}
			if(html != value){
				$(textBox).data('html_content',html);
			}
			textBox.value = value;
			//设置文本框的失去焦点事件
			textBox.onblur = function (){
				me.cancelEditCell(this.parentNode, this.value);
			}
			//向当前单元格添加文本框
			this.clearChild(element);
			element.appendChild(textBox);
			textBox.focus();
			textBox.select();
			
			//改变状态变量
			element.setAttribute("EditState", "true");
			element.parentNode.parentNode.setAttribute("CurrentRow", element.parentNode.rowIndex);
		}
	},
	createDropDownList : function(element, value){
		var me = this;
		//检查编辑状态，如果已经是编辑状态，跳过
		var editState = element.getAttribute("EditState");
		if(editState != "true"){
			//创建下接框
			var downList = document.createElement("Select");
			downList.className="EditCell_DropDownList";
			
			//添加列表项
			var items = element.getAttribute("DataItems");
			if(!items){
				items = element.parentNode.parentNode.rows[0].cells[element.cellIndex].getAttribute("DataItems");
			}
			
			if(items){
				items = eval("[" + items + "]");
				for(var i=0; i<items.length; i++){
					var oOption = document.createElement("OPTION");
					oOption.text = items[i].text;
					oOption.value = items[i].value;
					downList.options.add(oOption);
				}
			}
			
			//设置列表当前值
			if(!value){
				value = element.getAttribute("Value");
			}
			downList.value = value;

			//设置创建下接框的失去焦点事件
			downList.onblur = function (){
				me.cancelEditCell(this.parentNode, this.value, this.options[this.selectedIndex].text);
			}
			
			//向当前单元格添加创建下接框
			this.clearChild(element);
			element.appendChild(downList);
			downList.focus();
			
			//记录状态的改变
			element.setAttribute("EditState", "true");
			element.parentNode.parentNode.setAttribute("LastEditRow", element.parentNode.rowIndex);
		}
	},
	cancelEditCell : function(element, value, text){	//取消单元格编辑状态
		element.setAttribute("Value", value);
		if(text){
			element.innerHTML = text;
		}else{
			var html_content = $("input.EditCell_TextBox", element).data('html_content');
			if(null != html_content && "" != html_content && "undefined" != html_content){
				var htmlObj = $("<div>" + html_content + "</div>");
				htmlObj.find("span[fist_ele='yes']").html(value);
				value = htmlObj.html();
			}
			element.innerHTML = value;
		}
		element.setAttribute("EditState", "false");
		//检查是否有公式计算
		this.event_call(element);
	},
	clearChild : function(element){	//清空指定对象的所有字节点
		element.innerHTML = "";
	},
	getRowData : function(row){
		var rowData = {};
		for(var j=0;j<row.cells.length; j++){
			name = row.parentNode.rows[0].cells[j].getAttribute("Name");
			if(name){
				var value = row.cells[j].getAttribute("Value");
				if(!value){
					value = row.cells[j].innerHTML;
				}
				rowData[name] = value;
			}
		}
		return rowData;
	},
	getCellData : function(cell){
		if(cell){
			if($(cell).find('span').length > 0){
				return $(cell).find('span').html();
			}else 
				return cell.innerHTML;
		}
			
	},
	event_call : function(cell){			//事件绑定
		var index_id = $(cell).attr('index_id');
		var cell_val = this.getCellData(cell);
		var callBack = $('tr[tree_title = "yes"]').find("th[index_id='" + index_id + "']").attr('col_event');
		var curRec = $(cell).closest('tr').data('node_info');
		var filed_name = $(cell).attr('field_name');
		if(null != callBack && callBack != '' && callBack != 'undefined'){
			eval(callBack + "(" + JSON.stringify(curRec) + ",'" + filed_name + "','" + cell_val + "')");
		}
	}
};