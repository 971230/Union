package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.admin.form.Field;
	import com.ztesoft.crm.report.flex.admin.form.FieldFactory;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.UIComponent;

	public class ReportInterceptor extends VBox {

		public function ReportInterceptor() {
			//TODO: implement function
			super();
			this.init();
			this.initInterceptorGrid();
		}
		private var replaceField:Field;
		private var extendField:Field;
		private var interceptorGrid:DataGrid;
		private var parameterGrid:DataGrid;

		private function init():void {
			var hBox:HBox=new HBox();
			var l:Label=null;
			addChild(hBox);
			hBox.percentWidth=100;
			/****/
			l=new Label();
			hBox.addChild(l);
			l.text="是否替换：";
			this.replaceField=FieldFactory.createField("combo");
			this.replaceField.setName("replace");
			this.replaceField.setData(ReportConstanst.TRUE_OR_FALSE);
			hBox.addChild(UIComponent(this.replaceField));
			/****/
			l=new Label();
			hBox.addChild(l);
			l.text="是否继承：";
			this.extendField=FieldFactory.createField("combo");
			this.extendField.setName("extend");
			this.extendField.setData(ReportConstanst.TRUE_OR_FALSE);
			hBox.addChild(UIComponent(this.extendField));
			/****/
			hBox=new HBox();
			hBox.percentHeight=100;
			hBox.percentWidth=100;
			addChild(hBox);
			this.interceptorGrid=new DataGrid();
			hBox.addChild(this.interceptorGrid);
			this.interceptorGrid.percentHeight=100;
			this.interceptorGrid.percentWidth=45;
			this.interceptorGrid.columns=this.createColumns([{text: "拦截器名", name: "name", width: 1}, {text: "拦截器类型", name: "type", width: 1}]);
			/****/
			this.parameterGrid=new DataGrid();
			hBox.addChild(this.parameterGrid);
			this.parameterGrid.percentHeight=100;
			this.parameterGrid.percentWidth=55;
			this.parameterGrid.columns=this.createColumns([{text: "参数名", name: "name", width: 1}, {text: "数据类型", name: "dataType", width: 1}]);
			this.interceptorGrid.doubleClickEnabled=true;
			this.interceptorGrid.addEventListener(MouseEvent.DOUBLE_CLICK, editInterceptor);
			this.interceptorGrid.addEventListener(MouseEvent.CLICK, loadParameter);
		}

		private function createColumns(cols:Array):Array {
			var columns:Array=[];
			var len:int=cols.length;
			for (var i:int=0; i < len; i++) {
				var c:DataGridColumn=new DataGridColumn();
				var o:Object=cols[i];
				c.headerText=o.text;
				c.dataField=o.name;
				c.width=o.width;
				c.labelFunction=function(item:Object, col:DataGridColumn):String {
					return XMLNodeObject(item).getAttribute(col.dataField);
				};
				columns.push(c);
			}
			return columns;
		}

		private function initInterceptorGrid():void {
			var ctx:Array=[];
			ctx.push({text: "添加SQL拦截器", handler: function(e:ContextMenuEvent):void {
						addInterceptor("sql");
					}});
			ctx.push({text: "添加函数拦截器", handler: function(e:ContextMenuEvent):void {
						addInterceptor("function");
					}});
			ctx.push({text: "添加存储过程拦截器", handler: function(e:ContextMenuEvent):void {
						addInterceptor("procedure");
					}});
			ctx.push({text: "添加JAVA类拦截器", handler: function(e:ContextMenuEvent):void {
						addInterceptor("class");
					}});
			ctx.push({text: "删除拦截器", handler: function(e:ContextMenuEvent):void {
						delInterceptor();
					}});
			this.interceptorGrid.contextMenu=ControlUtils.getContextMenu(ctx);
			ctx=[];
			ctx.push({text: "添加参数", handler: function(e:ContextMenuEvent):void {
						addParameter();
					}});
			ctx.push({text: "删除参数", handler: function(e:ContextMenuEvent):void {
						delParameter();
					}});
			this.parameterGrid.contextMenu=ControlUtils.getContextMenu(ctx);
		}

		private function getEditProperties(t:String):Object {
			var o:Object={cols: 1, tagName: "interceptor", data: {type: t}};
			var fields:Array=[{label: "拦截器名", name: "name"}];
			switch (t) {
				case "sql":
					fields.push({label: "脚本", name: "impl", type: "textarea", height: 320});
					fields.push({label: "数据源", name: "dataSource", type: "combo", data: ReportConstanst.DATASOURCE});
					break;
				case "function":
					fields.push({label: "函数脚本", name: "impl", type: "textarea", height: 160});
					fields.push({label: "数据源", name: "dataSource", type: "combo", data: ReportConstanst.DATASOURCE});
					fields.push({name: "dataSource1", type: "label", data: "脚本需要按照这样写：{ ?=call 函数名(?,?,?,?) }\n上面这个写法是Oracle，需要根据数据库不同来写。"});
					break;
				case "procedure":
					fields.push({label: "存储过程脚本", name: "impl", type: "textarea", height: 160});
					fields.push({label: "数据源", name: "dataSource", type: "combo", data: ReportConstanst.DATASOURCE});
					fields.push({name: "dataSource1", type: "label", data: "脚本需要按照这样写：{ call 函数名(?,?,?,?) }\n上面这个写法是Oracle，需要根据数据库不同来写。"});
					break;
				case "class":
					fields.push({label: "接口类", name: "impl", type: "textarea", height: 320});
					fields.push({label: "数据源", name: "dataSource", type: "combo", data: ReportConstanst.DATASOURCE});
					fields.push({name: "dataSource1", type: "label", data: "完整的类名，包含包路径"});
					break;
			}
			o["items"]=fields;
			return o;
		}

		private function addInterceptor(t:String):void {
			new FormDialog().open(this, getEditProperties(t), "新建拦截器", function(d:XMLNodeObject):void {
				var impl:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("impl");
				impl.setContent(d.removeAttribute("impl"));
				d.add(impl);
				d.setAttribute("type", t);
				interceptors.add(d);
				interceptorGrid.dataProvider=interceptors.getChilds("interceptor");
			});
		}

		private function delInterceptor():void {
			var item:XMLNodeObject=XMLNodeObject(this.interceptorGrid.selectedItem);
			if (item == null)
				return;
			this.interceptors.removeChildById(item.getId());
			interceptorGrid.dataProvider=interceptors.getChilds("interceptor");
		}

		private function editInterceptor(e:MouseEvent):void {
			var item:XMLNodeObject=XMLNodeObject(this.interceptorGrid.selectedItem);
			if (item == null)
				return;
			var type:String=item.getAttribute("type");
			var o:Object=getEditProperties(type);
			o["data"]=item.getAttributes();
			var impl:XMLNodeObject=item.getChild("impl");
			o.data["impl"]=impl.getContent();
			new FormDialog().open(this, o, "修改拦截器", function(d:XMLNodeObject):void {
				impl.setContent(d.removeAttribute("impl"));
				item.setAttributes(d.getAttributes());
				interceptorGrid.dataProvider=interceptors.getChilds("interceptor");
			});
		}
		private var interceptors:XMLNodeObject;

		private function loadParameter(e:MouseEvent):void {
			var item:XMLNodeObject=XMLNodeObject(this.interceptorGrid.selectedItem);
			if (item == null)
				return;
			this.parameterGrid.dataProvider=item.getChilds("parameter");
		}

		private function addParameter():void {
			var item:XMLNodeObject=XMLNodeObject(this.interceptorGrid.selectedItem);
			if (item == null) {
				MessageBox.alert("请单击鼠标选择一个拦截器!");
				return;
			}
			var o:Object={cols: 1, tagName: "parameter", items: [{label: "参数名", name: "name"}, {label: "数据类型", name: "dataType", type: "radiobox", data: ReportConstanst.FIELD_DATATYPE}]};
			new FormDialog().open(this, o, "新建参数", function(d:XMLNodeObject):void {
				item.add(d);
				loadParameter(null);
			});
		}

		private function delParameter():void {
			var interceptor:XMLNodeObject=XMLNodeObject(this.interceptorGrid.selectedItem);
			if (interceptor == null) {
				MessageBox.alert("请单击鼠标选择一个拦截器!");
				return;
			}
			var item:XMLNodeObject=XMLNodeObject(this.parameterGrid.selectedItem);
			if (item == null) {
				MessageBox.alert("请选择一个参数删除！");
				return;
			}
			interceptor.removeChildById(item.getId());
			loadParameter(null);
		}

		public function load(n:XMLNodeObject):void {
			this.interceptors=n;
			this.replaceField.setObserver(this.interceptors);
			this.extendField.setObserver(this.interceptors);
			interceptorGrid.dataProvider=interceptors.getChilds("interceptor");
		}
	}
}