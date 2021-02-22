package com.ztesoft.crm.report.flex.admin.grid {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.form.ComboBoxEx;
	import com.ztesoft.crm.report.flex.admin.form.FieldClassFactory;
	import com.ztesoft.crm.report.flex.admin.form.TextInputEx;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.xml.XMLNode;

	import mx.controls.dataGridClasses.DataGridColumn;

	public class DataGridColumnEx extends DataGridColumn {
		public function DataGridColumnEx(o:Object) {
			//TODO: implement function
			super("");
			init(o);
		}

		private function init(o:Object):void {
			this.headerText=o.label;
			this.dataField=o.name;
			this.width=(o.width == null) ? 1 : o.width;
			this.editable=ObjectUtil.isTrue(o.editable);
			if (o.data != null) {
				var len:int=(o.data as Array).length;
				this.data={};
				for (var i:int=0; i < len; i++) {
					var a:Object=(o.data as Array)[i];
					this.data[a.value]=a.label;
				}
			}

			this.labelFunction=function(o:Object, dc:DataGridColumn):String {
				var dcex:DataGridColumnEx=DataGridColumnEx(dc);
				var v:String="";

				if (o is XMLNodeObject) {
					v=XMLNodeObject(o).getAttribute(dcex.dataField);
					if (dcex.dataField == "content") {
						v=XMLNodeObject(o).getContent();
					}
				} else {
					if (o is XMLNode) {
						v=XMLNode(o).attributes[dcex.dataField];
						if (!v && dcex.dataField == "content") {
							v=XMLNode(o).firstChild.nodeValue;
						}
					} else {
						v=o[dcex.dataField];
					}
				}

				if (dcex.data != null) {
					return dcex.data[v];
				}
				return v;
			}

			if (this.editable == true) {
				if (o.data != null)
					this.itemEditor=new FieldClassFactory(ComboBoxEx, o);

				else {
					this.itemEditor=new FieldClassFactory(TextInputEx, null);

				}
				this.editorDataField="data";

			}

		}

		private var data:Object=null;


	}
}