package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.form.EventForm;
	import com.ztesoft.crm.report.flex.admin.form.Field;
	import com.ztesoft.crm.report.flex.admin.form.XMLNodeForm;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.Event;

	import mx.containers.TabNavigator;
	import mx.containers.VBox;
	import mx.controls.TextArea;
	import mx.core.UIComponent;


	public class PropertiesDialog extends ModalDialog {
		public function PropertiesDialog() {
			//TODO: implement function
			super();
			this.setTitle("编辑属性");
		}

		private function linkTypeChangeListener(e:Event):void {
			var field:Field=Field(e.currentTarget);
			effectLinkType(field.getValue());

		}

		private function effectLinkType(v:String):void {
			if (v == "1" || v == "") {
				linkForm.setFieldVisible(1, true);
				linkForm.setFieldVisible(2, true);
				linkForm.setFieldVisible(3, false);
				linkForm.setFieldVisible(4, false);
				linkForm.setFieldVisible(5, false);
				linkForm.setFieldVisible(6, false);

			}

			if (v == "2") {
				linkForm.setFieldVisible(1, false);
				linkForm.setFieldVisible(2, false);
				linkForm.setFieldVisible(3, true);
				linkForm.setFieldVisible(4, false);
				linkForm.setFieldVisible(5, true);
				linkForm.setFieldVisible(6, true);
			}

			if (v == "3") {
				linkForm.setFieldVisible(1, false);
				linkForm.setFieldVisible(2, false);
				linkForm.setFieldVisible(3, false);
				linkForm.setFieldVisible(4, true);
				linkForm.setFieldVisible(5, true);
				linkForm.setFieldVisible(6, true);
			}


		}

		override public function load(data:Object):void {
			this.form.render(data);
			if (data.data != null) {
				this.form.load(data.data);
			}


			if (data.eventsName != null && data.eventsName.length > 0) {
				this.events=new EventForm();
				this.events.label="事件";
				this.events.render(data.eventsName);
				if (data.data != null && data.data is XMLNodeObject) {
					this.events.load(XMLNodeObject(data.data).children());
				}
				tabs.addChild(this.events);
			}

			if (ObjectUtil.isTrue(data.styleRule)) {
				var v:VBox=new VBox();
				v.label="自定义规则";
				this.styleRule=new TextArea();
				v.addChild(this.styleRule);
				v.percentHeight=100;
				v.percentWidth=100;
				ControlUtils.setPadding(v, 4, 0, 4, 4);
				this.styleRule.percentHeight=100;
				this.styleRule.percentWidth=100;
				tabs.addChild(v);
				if (!ObjectUtil.isEmpty(data.data)) {
					var o:XMLNodeObject=XMLNodeObject(data.data).getChild("styleRule");
					if (!ObjectUtil.isEmpty(o))
						this.styleRule.text=o.getContent();
				}
			}

			if (ObjectUtil.isTrue(data.linkEnable)) {
				this.linkForm=new XMLNodeForm();
				var fields:Array=[];
				this.linkForm.label="超链接"

				fields.push({type: "combo", label: "类型", name: "linkType", data: ReportConstanst.HEADER_LINK_TYPE, changeListener: linkTypeChangeListener});
				fields.push({type: "combo", label: "事件", name: "href", data: ReportConstanst.HEADER_EVENT_TYPE});
				fields.push({type: "checkbox", label: "目标面板", name: "target"});
				fields.push({type: "reporttree", label: "报表", name: "href", height: 300});
				fields.push({type: "textarea", label: "外部链接", name: "href", height: 300});
				fields.push({type: "combo", label: "链接范围", name: "linkScope", data: ReportConstanst.HEADER_LINK_SCOPE});
				fields.push({type: "combo", label: "目标窗口", name: "target", data: ReportConstanst.WINDOW_TYPE});

				linkForm.render({cols: 1, labelWidth: 65, items: fields});

				//初始化数据
				var dd:Object={};
				if (!ObjectUtil.isEmpty(data.data)) {
					dd=(data.data is XMLNodeObject) ? XMLNodeObject(data.data).getAttributes() : data.data;
				}
				if (ObjectUtil.isEmpty(dd.linkType)) {
					if (!ObjectUtil.isEmpty(dd.href)) {
						if (dd.href.indexOf("javascript:") == 0) {
							dd["linkType"]="1";
						} else
							dd["linkType"]="2";
					} else
						dd["linkType"]="1";
				}
				linkForm.getField(3).setData(data.reportRoot);
				linkForm.getField(2).setData(data.panel);
				effectLinkType(dd.linkType);
				linkForm.setValues(dd, true);

				tabs.addChild(linkForm);

			}

		}

		private var form:XMLNodeForm;
		private var tabs:TabNavigator
		private var events:EventForm;
		private var styleRule:TextArea;
		private var linkForm:XMLNodeForm;


		override public function getData():Object {
			var xml:XMLNodeObject=this.form.getValues();
			if (this.events != null)
				xml.setChildren(this.events.getData());
			if (this.styleRule != null) {
				xml.removeChildByTagName("styleRule");
				var o:XMLNodeObject=new XMLNodeObject("styleRule");
				o.setContent(this.styleRule.text);
				xml.add(o);
			}
			if (this.linkForm != null) {
				xml.setAttributes(this.linkForm.getValues(true).attributes());
			}
			var c:String=xml.getAttribute("content");
			xml.removeAttribute("content");
			xml.setContent(c);

			return xml;
		}

		override public function createContent():UIComponent {
			tabs=new TabNavigator();
			form=new XMLNodeForm();
			form.label="属性";
			tabs.addChild(form);
			return tabs;
		}

		override public function createButtons():Array {
			return [];
		}
	}
}