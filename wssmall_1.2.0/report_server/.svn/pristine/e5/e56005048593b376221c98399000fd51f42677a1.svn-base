package com.ztesoft.crm.report.flex.admin.form {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.display.DisplayObject;
	import flash.events.MouseEvent;

	import mx.containers.BoxDirection;
	import mx.containers.Form;
	import mx.containers.FormItem;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.core.UIComponent;

	public class XMLNodeForm extends Form {

		public function XMLNodeForm(... args) {
			//TODO: implement function
			super();

			if (typeof(args) == "undefined" || args == null)
				return;

			if (args.length > 0 && args[0] != null && args[0].items != null)
				render(args[0]);

		}

		public function render(config:Object):void {
			this.percentWidth=100;
			this.tagName=config.tagName;
			draw(config);
			if (!ObjectUtil.isEmpty(config.data)) {
				this.load(config.data);
			}
			ControlUtils.setPadding(this, 0, 5, 15, 5);
		}

		private var tagName:String;
		private var fields:Array=[];

		public function getField(inx:int):Field {
			return Field(fields[inx]);
		}

		private function draw(config:Object):void {
			var cols:int=config.cols;
			var len:int=config.items.length;
			var row:FormItem=null;
			var count:int=0;
			for (var i:int=0; i < len; i++) {
				var f:Object=config.items[i];
				var label:Label=null;
				var field:Field=FieldFactory.createField(f.type);
				var w:int=100 / cols;
				var colSpan:int=1;
				if ((count % cols) == 0 || f.fullline == true) {
					row=new FormItem();
					row.percentWidth=100;
					row.direction=BoxDirection.HORIZONTAL;
					addChild(row);
				}
				if (i < (len - 1) && config.items[i + 1].fullline == true) {
					colSpan=(cols - (count % cols));
				}
				if (f.fullline == true)
					colSpan=cols;
				w=w * colSpan;
				count=count + colSpan;
				/****/
				if (!ObjectUtil.isEmpty(f.height))
					UIComponent(field).height=f.height;
				if (!ObjectUtil.isEmpty(f.name))
					field.setName(f.name);
				if (!ObjectUtil.isEmpty(f.data))
					field.setData(f.data);
				/****/
				var a:UIComponent=new HBox();
				a.percentWidth=w;
				if (config.labelAlign == "top") {
					a=new VBox();

					a.percentWidth=w;
					w=100;
				}
				row.addChild(a);
				UIComponent(field).percentWidth=w;
				if (!ObjectUtil.isEmpty(f.label)) {
					label=new Label();
					label.text=f.label;
					label.width=ObjectUtil.nvl(config.labelWidth, 100) as int;
					a.addChild(label);
				}
				if (!ObjectUtil.isEmpty(f.value)) {
					field.setValue(f.value);
				}
				a.addChild(field as DisplayObject);
				if (!(field is Button))
					this.fields.push(field);
				else {
					Button(field).addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
						if (!ObjectUtil.isEmpty(f.handler)) {
							Function(f.handler).call(e.currentTarget, null);
						}
					});
				}

				if (!ObjectUtil.isEmpty(f.changeListener)) {
					field.addChangeListener(f.changeListener);
				}

				if (!ObjectUtil.isEmpty(f.height)) {
					UIComponent(field).height=f.height;
				}

				if (!ObjectUtil.isEmpty(f.width)) {
					UIComponent(field).percentWidth=100;
				}
			}
		}
		private var node:XMLNodeObject;

		public function load(o:Object):void {
			if (o is XMLNodeObject) {
				var m:XMLNodeObject=XMLNodeObject(o);
				this.node=m;
				var len:int=this.fields.length;
				for (var i:int=0; i < len; i++) {
					var f:Field=this.fields[i] as Field;
					if (f.getName() == "content") {

						f.setValue(this.node.getContent());
					} else {
						f.setValue(this.node.getAttribute(f.getName()));

					}
					f.setObserver(this.node);
				}
			} else {
				this.node=new XMLNodeObject();
				this.node.setAttributes(o);
				load(this.node);
			}
		}

		public function loadQuite(o:Object):void {
			if (o is XMLNodeObject) {
				loadQuite(XMLNodeObject(o).getAttributes());
			} else {
				var len:int=this.fields.length;
				for (var i:int=0; i < len; i++) {
					var f:Field=this.fields[i] as Field;
					f.setValue(o[f.getName()]);
				}
			}
		}

		public function reset():void {
			var len:int=this.fields.length;
			for (var i:int=0; i < len; i++) {
				var f:Field=this.fields[i] as Field;
				f.setValue("");
			}
		}

		private var visibles:Object={};

		public function setFieldVisible(inx:int, v:Boolean):void {
			var f:UIComponent=UIComponent(UIComponent(fields[inx]).parent);
			f.includeInLayout=v;
			f.setVisible(v);
			this.visibles["v" + inx]=v;
		}

		public function setValues(o:Object, b:Boolean=false):void {
			var len:int=this.fields.length;
			for (var i:int=0; i < len; i++) {
				var f:Field=this.fields[i] as Field;

				if (ObjectUtil.isFalse(this.visibles["v" + i]) && b)
					continue;

				f.setValue(o[f.getName()]);
			}
		}

		public function getValues(b:Boolean=false):XMLNodeObject {
			var o:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject();
			o.setTagName(this.tagName);
			var len:int=this.fields.length;
			for (var i:int=0; i < len; i++) {
				var f:Field=this.fields[i] as Field;
				if (ObjectUtil.isFalse(this.visibles["v" + i]) && b)
					continue;
				var v:String=f.getValue();

				if (typeof(v) == "undefined")
					v="";
				o.setAttribute(f.getName(), v);
			}
			return o;
		}
	}
}