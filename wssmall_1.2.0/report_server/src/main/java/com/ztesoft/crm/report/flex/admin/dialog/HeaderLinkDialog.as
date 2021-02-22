package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.form.Field;
	import com.ztesoft.crm.report.flex.admin.form.XMLNodeForm;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.Event;

	import mx.core.UIComponent;


	public class HeaderLinkDialog extends ModalDialog {
		public function HeaderLinkDialog() {
			//TODO: implement function
			super();
			setTitle("字段关联")
			setSize(360, 360);
		}

		private var form:XMLNodeForm=null;

		private function linkTypeChangeListener(e:Event):void {
			var field:Field=Field(e.currentTarget);
			effectLinkType(field.getValue());

		}

		override public function showOkCloseButton():Boolean {
			return true;
		}

		private function effectLinkType(v:String):void {
			if (v == "1" || v == "") {
				form.setFieldVisible(1, true);
				form.setFieldVisible(2, true);
				form.setFieldVisible(3, false);
				form.setFieldVisible(4, false);
				form.setFieldVisible(5, false);
				form.setFieldVisible(6, false);

			}

			if (v == "2") {
				form.setFieldVisible(1, false);
				form.setFieldVisible(2, false);
				form.setFieldVisible(3, true);
				form.setFieldVisible(4, false);
				form.setFieldVisible(5, true);
				form.setFieldVisible(6, true);
			}

			if (v == "3") {
				form.setFieldVisible(1, false);
				form.setFieldVisible(2, false);
				form.setFieldVisible(3, false);
				form.setFieldVisible(4, true);
				form.setFieldVisible(5, true);
				form.setFieldVisible(6, true);
			}


		}

		override public function load(data:Object):void {

			if (ObjectUtil.isEmpty(data.linkType)) {
				if (!ObjectUtil.isEmpty(data.href)) {
					if (data.href.indexOf("javascript:") == 0) {
						data["linkType"]="1";
					} else
						data["linkType"]="2";
				}
			}
			form.getField(3).setData(data.reportRoot);
			form.getField(2).setData(data.panel);
			form.setValues(data.data, true);
		}

		override public function getData():Object {
			var o:Object=form.getValues(true).attributes();
			MessageBox.alert(o.href);
			return o;
		}


		override public function createContent():UIComponent {
			var fields:Array=[];
			this.form=new XMLNodeForm();

			fields.push({type: "combo", label: "类型", name: "linkType", data: ReportConstanst.HEADER_LINK_TYPE, changeListener: linkTypeChangeListener});
			fields.push({type: "combo", label: "事件", name: "href", data: ReportConstanst.HEADER_EVENT_TYPE});
			fields.push({type: "checkbox", label: "目标面板", name: "target"});
			fields.push({type: "reporttree", label: "报表", name: "href", height: 160});
			fields.push({type: "textarea", label: "外部链接", name: "href", height: 160});
			fields.push({type: "combo", label: "链接范围", name: "hrefScope", data: ReportConstanst.HEADER_LINK_SCOPE});
			fields.push({type: "combo", label: "目标窗口", name: "target", data: ReportConstanst.WINDOW_TYPE});

			form.render({cols: 1, labelWidth: 65, items: fields});
			effectLinkType("");

			return this.form;

		}

	}
}