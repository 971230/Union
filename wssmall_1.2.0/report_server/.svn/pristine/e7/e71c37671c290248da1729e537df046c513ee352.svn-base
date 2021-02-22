package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.display.DisplayObject;

	import mx.controls.Button;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.Text;
	import mx.controls.TextArea;

	public class RPTFormField extends RPTComponent {
		[Embed(source="assets/form/combo.gif")]
		[Bindable]
		public var image1:Class;
		[Embed(source="assets/form/date.gif")]
		[Bindable]
		public var image2:Class;
		[Embed(source="assets/form/text.gif")]
		[Bindable]
		public var image3:Class;
		[Embed(source="assets/form/checkbox.gif")]
		[Bindable]
		public var image4:Class;
		[Embed(source="assets/form/radiobox.gif")]
		[Bindable]
		public var image5:Class;
		[Embed(source="assets/form/button.gif")]
		[Bindable]
		public var image6:Class;
		[Embed(source="assets/form/excel.png")]
		[Bindable]
		public var image7:Class;
		[Embed(source="assets/form/treefield.gif")]
		[Bindable]
		public var image8:Class;
		[Embed(source="assets/form/popupselect.gif")]
		[Bindable]
		public var image9:Class;
		[Embed(source="assets/tools/printer.gif")]
		[Bindable]
		public var image10:Class;

		public function RPTFormField() {
			//TODO: implement function
			super();
			horizontal();
			setSides("left");
		}

		override public function restoreBorder():void {
			ControlUtils.setBorder(this, 1, "solid", 0xFFFFFF);
		}

		override public function getPropertiesConfig():Object {
			return getPropertiesConfig1(getData().getReport(), getData().getAttribute("type"));
		}

		public function getPropertiesConfig1(report:XMLNodeObject, type:String):Object {
			var propertiesConfig:Object={cols: 1, data: null, labelWidth: 80, eventsName: [{text: "值改变", name: "change", desc: "参数：form,field,oldValue,oldText"}]};
			var config:Array=[{label: "数据域名", name: "name"}, {label: "使用单引号", name: "sqlParameter", type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE}, {label: "中文标签", name: "label"}, {label: "数据类型",
					type: "radiobox", name: "dataType", data: ReportConstanst.FIELD_DATATYPE}, {label: "空白说明", name: "emptyText"}, {label: "允许为空", name: "empty", type: "radiobox", data: ReportConstanst.
						TRUE_OR_FALSE}, {label: "宽度", name: "width"}, {label: "样式", name: "style", type: "textarea", fullline: true}];
			var arr:Array=null;
			propertiesConfig["callback"]=function(d:Object):void {
				if (!isNullData() && d != null && d is XMLNodeObject) {
					var x:XMLNodeObject=XMLNodeObject(d);

					getData().setChildren(x.children());
					getData().setAttributes(x.attributes());
				}
			}

			switch (type) {
				case "checkbox":
					arr=[{label: "静态编码", name: "attrCode"}];
					propertiesConfig["items"]=arr.concat(config);
					break;
				case "radiobox":
					arr=[{label: "静态编码", name: "attrCode"}];
					propertiesConfig["items"]=arr.concat(config);
					break;
				case "combo":
					arr=[{label: "根节点值", name: "root"}, {label: "静态编码", name: "attrCode"}, {label: "下拉样式", name: "dropdown", type: "radiobox", data: ReportConstanst.FIELD_DROPDOWN}];
					if (!this.isNullData()) {
						arr.push({label: "依赖控件", name: "depend", type: "checkbox", data: getData().parent.parent.getChildsBefore("field", getData().getId(), true)});
					}
					propertiesConfig["items"]=arr.concat(config);
					break;
				case "date":
					arr=[{label: "最大值", name: "maxValue"}, {label: "最小值", name: "minValue"}, {label: "最大值参数", type: "combo", data: data1, name: "maxParameter"}, {label: "最小值参数", type: "combo", name: "minParameter",
							data: data1}];
					propertiesConfig["cols"]=1;
					propertiesConfig["items"]=arr.concat(config);
					break;
				case "textarea":
					break;

				case "tree":
					arr=[{label: "根节点值", name: "root"}, {label: "静态编码", name: "attrCode"}, {label: "下拉样式", name: "dropdown", type: "combo", data: ReportConstanst.TREE_DROPDOWN}];
					propertiesConfig["items"]=arr.concat(config);
					break;
				case "link":
					arr=[{label: "链接", name: "url", type: "text"}, {label: "样式", name: "style", type: "text"}, {label: "链接内容", name: "content", type: "textarea", height: 80}];
					propertiesConfig["items"]=arr;
					propertiesConfig["eventsName"]=[{text: "鼠标点击", name: "click", desc: "参数：form,field,oldValue,oldText"}]
					break;
				case "popup":
					arr=[{label: "路径", name: "url"}, {label: "窗口宽度", name: "dialogWidth"}, {label: "窗口高度", name: "dialogHeight"}];
					arr.push({label: "默认值", name: "value"});
					arr.push({label: "默认值(中文)", name: "text"});
					if (!this.isNullData()) {
						arr.push({label: "依赖控件", name: "depend", type: "checkbox", data: getData().parent.parent.getChildsBefore("field", getData().getId(), true)});
					}
					propertiesConfig["items"]=arr.concat(config);
					break;
				case "text":
					arr=[{label: "最大长度", name: "maxSize"}, {label: "最小长度", name: "minSize"}];
					propertiesConfig["items"]=arr.concat(config);
					break;
				default: //按钮	
					propertiesConfig["eventsName"]=null;
					arr=[{label: "按钮文字", name: "text"}, {label: "宽度", name: "width"}];
					propertiesConfig["items"]=arr;
					break;
			}
			/**最大最小参数设置**/
			var data1:Array=[];
			var a:Array=report.getChilds("parameter");
			var len:int=a.length;
			for (var i:int=0; i < len; i++) {
				var n:XMLNodeObject=XMLNodeObject(a[i]);
				data1.push({value: n.getAttribute("name"), label: n.getAttribute("name")});
			}
			len=propertiesConfig.items.length;
			for (var j:int=0; j < len; j++) {
				var o:Object=propertiesConfig.items[j];
				if (o.name == "minParameter" || o.name == "maxParameter") {
					o["data"]=data1;
				}
			}
			return propertiesConfig;
		}

		override public function load(n:XMLNodeObject):void {
			//TODO: implement function
			this.type=n.getAttribute("type");
			switch (this.type) {
				case "checkbox":
					createCheckbox(n);
					break;
				case "radiobox":
					createRadiobox(n);
					break;
				case "combo":
					createCombobox(n);
					break;
				case "date":
					createDate(n);
					break;
				case "textarea":
					createTextarea(n);
					break;
				case "link":
					createLink(n);
					break;
				case "popup":
					createPopup(n);
					break;
				case "tree":
					createTreefield(n);
					break;
				case "label":
					createLabelfield(n);
					break;
				case "text":
					createText(n);
					break;
				default: //按钮
					createButton(n);
					break;
			}
			//this.doubleClickEnabled=true;
			//this.addEventListener(MouseEvent.DOUBLE_CLICK, doubleClick);
		}
		private var labela:Label;
		private var type:String;
		private var childTypes:Array=["checkbox", "radiobox", "text", "combo", "date", "submit", "excel", "treefield", "labelfield", "popupselect", "prompt"];

		override public function acceptChild(type:String):int {
			return (childTypes.indexOf(type) >= 0) ? RPTComponent.SIBLING : 0;
		}

		private function createLabel(n:XMLNodeObject):void {
			var l:String=n.getAttribute("label");
			if (ObjectUtil.isEmpty(l) || l == "") {
				if (labela != null) {
					body.removeChild(labela);
					labela=null;
				}
				return;
			}
			if (labela != null) {
				labela.text=l;
				return;
			}
			labela=new Label();
			labela.text=l;
			body.addChildAt(labela, 0);
		}

		override public function change(node:XMLNodeObject):void {
			createLabel(node);
			var o:DisplayObject=body.getChildAt(body.numChildren - 1);
			if (o is Button) {
				Button(o).label=node.getAttribute("text");
			}
		}

		private function createDate(n:XMLNodeObject):void {
			var t:Image=new Image();
			t.source=this.image2;
			t.height=21;
			t.width=71
			this.createLabel(n);
			body.addChild(t);
		}

		private function createText(n:XMLNodeObject):void {
			var t:Image=new Image();
			t.source=this.image3;
			t.height=21;
			t.width=71
			this.createLabel(n);
			body.addChild(t);
		}

		private function createTreefield(n:XMLNodeObject):void {
			var t:Image=new Image();
			t.source=this.image8;
			t.height=21;
			t.width=71
			this.createLabel(n);
			body.addChild(t);
		}

		private function createLabelfield(n:XMLNodeObject):void {
			var t:Text=new Text();
			t.text="Aa中";
			t.height=21;
			t.width=71
			this.createLabel(n);
			body.addChild(t);
		}

		private function createCheckbox(n:XMLNodeObject):void {
			var t:Image=new Image();
			t.source=this.image4;
			t.height=21;
			t.width=71
			this.createLabel(n);
			body.addChild(t);
		}

		private function createRadiobox(n:XMLNodeObject):void {
			var t:Image=new Image();
			t.source=this.image5;
			t.height=21;
			t.width=71
			this.createLabel(n);
			body.addChild(t);
		}

		private function createCombobox(n:XMLNodeObject):void {
			var t:Image=new Image();
			this.createLabel(n);
			t.source=this.image1;
			t.width=71;
			t.height=21;
			body.addChild(t);
		}

		private function createPopup(n:XMLNodeObject):void {
			var t:Image=new Image();
			this.createLabel(n);
			t.source=this.image9;
			t.width=71;
			t.height=21;
			body.addChild(t);
		}

		private function createLink(n:XMLNodeObject):void {
			var t:Label=new Label();
			t.height=21;
			t.setStyle("textDecoration", "underline");
			t.text=n.getContent();
			/**
			   var t:Box=new Box();
			   t.setStyle("backgroundImage", this.image6);
			   t.setStyle("horizontalGap", 0);
			   t.setStyle("verticalGap", 0);
			   t.width=71;
			   t.height=21;
			   t.setStyle("horizontalAlign", "center");
			   var a:Label=new Label();
			   a.text=n.getAttribute("text");
			   t.addChild(a);
			 * **/
			body.addChild(t);
		}

		private function createTextarea(n:XMLNodeObject):void {
			var t:TextArea=new TextArea();
			this.createLabel(n);
			t.enabled=false;
			body.addChild(t);
		}

		private function createButton(n:XMLNodeObject):void {
			var t:Button=new Button();
			t.width=71;
			t.height=21;
			t.setStyle("skin", this.image6);
			t.label=n.getAttribute("text");
			if (n.getAttribute("type") == "excel") {
				t.setStyle("icon", this.image7);
			}
			if (n.getAttribute("type") == "print") {
				t.setStyle("icon", this.image10);
			}
			/**
			   var t:Box=new Box();
			   t.setStyle("backgroundImage", this.image6);
			   t.setStyle("horizontalGap", 0);
			   t.setStyle("verticalGap", 0);
			   t.width=71;
			   t.height=21;
			   t.setStyle("horizontalAlign", "center");
			   var a:Label=new Label();
			   a.text=n.getAttribute("text");
			   t.addChild(a);
			 * **/
			body.addChild(t);
		}
	}
}