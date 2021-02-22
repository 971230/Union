package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportApplication;
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.dialog.HeaderLinkDialog;
	import com.ztesoft.crm.report.flex.admin.dialog.ModalDialog;
	import com.ztesoft.crm.report.flex.admin.dialog.ModelColumnDialog;
	import com.ztesoft.crm.report.flex.admin.dialog.ReportBrowserDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;

	import mx.controls.Button;

	public class RPTDND {

		public function RPTDND() {
			//TODO: implement function
		}
		public static var data:String;
		public static var opType:int;
		public static var TYPE:int=1;
		public static var ID:int=2;
		public static var rpt:RPTComponent=null;

		public function dragFinish(e:MouseEvent):void {
			release();
			RPTDND.data=null;
			RPTDND.opType=-1;
			RPTDND.rpt=null;
			e.stopImmediatePropagation();
		}

		private function release():void {
			var bg:String="backgroundColor";
			if (RPTDND.rpt != null) {
				var rl:RPTComponent=RPTDND.rpt;
				rl.leftRegion.setStyle(bg, rl.getBackgroundColor());
				rl.rightRegion.setStyle(bg, rl.getBackgroundColor());
				rl.topRegion.setStyle(bg, rl.getBackgroundColor());
				rl.bottomRegion.setStyle(bg, rl.getBackgroundColor());
				rl.forceFocus(false);
				RPTDND.rpt=null;
			}
		}

		private function createXMLNodeObject(item:RPTComponentItem):XMLNodeObject {
			var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject(item.tagName);
			n.setAttributes(item.attributes);
			return n;
		}

		public function dragDrop(e:MouseEvent):void {
			var rl:RPTComponent=RPTComponent(e.currentTarget);
			var app:ReportApplication=ReportApplication(rl.parentApplication);
			var p:Object=null;
			var dlg:ModalDialog=null;
			while (rl != null) {
				if (rl.acceptChild(RPTDND.data))
					break;
				rl=rl.getParent();
			}
			if (rl == null) {
				dragFinish(e);
				return;
			}
			switch (RPTDND.opType) {
				case RPTDND.TYPE:
					var item:RPTComponentItem=RPTComponentItem.map[RPTDND.data];

					if (item != null) {

						rl.addChildData(rl.selectedRegion, createXMLNodeObject(item));
					} else {
						switch (RPTDND.data) {
							case "textheader":
								p=rl.getPropertiesConfig();
								if (p == null) {
									p={cols: 1, labelWidth: 60, items: [{label: "中文名", name: "text"}, {label: "名称", name: "name"}, {label: "宽度", name: "width"}, {label: "对齐", name: "align", data: ReportConstanst.
														HEADER_ALIGN}]};
								}
								new FormDialog().open(rl, p, "自定义", function(d:XMLNodeObject):void {
									rl.addChildData(rl.selectedRegion, d.getAttributes());
								});
								break;
							case "columnheader":
								var m:XMLNodeObject=rl.getModel();
								if (m == null) {
									MessageBox.alert("交叉表或者列表没有关联数据库模型");
									break;
								}
								new ModelColumnDialog().open(rl, rl.getModel(), "选择表字段", function(d:XMLNodeObject):void {
									rl.addChildData(rl.selectedRegion, d);
								});
								break;
							case "cncolumnheader":
								if (rl.isNullData())
									break;
								new ModelColumnDialog().open(rl, rl.getModel(), "选择中文表字段", function(d:XMLNodeObject):void {
									rl.addChildData("cncolumnheader", d);
								});
								break;
							case "headerevent":

								var o1:Object={panel: rl.getData().getPanelDataWithoutForm(), reportRoot: app.ajax, data: rl.getData().getAttributes()};


								dlg=new HeaderLinkDialog();
								dlg.setCallback(function(d:Object):void {
									rl.getData().setAttributes(d);
								});
								dlg.open(rl, o1);
								break;
							case "headerreport":
								dlg=new ReportBrowserDialog();
								dlg.setCallback(function(d:Object):void {
									rl.getData().setAttributes(d);
								});
								dlg.open(rl, app.ajax);
								break;
							default:
								break;
						}
					}
					rl.selectedRegion=null;
					break;
				case RPTDND.ID:
					break;
				default:
					break;
			}
			dlg=null;
			dragFinish(e);

		}

		public function dragStart(e:MouseEvent):void {
			var b:Button=Button(e.currentTarget);
			RPTDND.data=b.data as String;
			RPTDND.opType=RPTDND.TYPE;
		}

		//当拖进去时  
		public function dragEnter(e:MouseEvent):void {
			if (ObjectUtil.isEmpty(RPTDND.data)) {
				return;
			}
			var rl:RPTComponent=RPTComponent(e.currentTarget);
			var color:uint=0x888888;
			var bg:String="backgroundColor";
			var flag:int=rl.acceptChild(RPTDND.data);
			if (flag > 0) {
				if (flag == RPTComponent.SIBLING) {
					var r:Rectangle=rl.getBounds(rl);
					var x:int=r.width / 2;
					var y:int=r.height / 2;
					var p:Point=rl.localToContent(new Point(rl.mouseX, rl.mouseY));
					if (p.x < x) {
						rl.leftRegion.setStyle(bg, color);
						rl.rightRegion.setStyle(bg, null);
						rl.selectedRegion=rl.leftRegion.data as String;
					} else {
						rl.rightRegion.setStyle(bg, color);
						rl.leftRegion.setStyle(bg, null);
						rl.selectedRegion=rl.rightRegion.data as String;
					}
					if (p.y < y) {
						rl.topRegion.setStyle(bg, color);
						rl.bottomRegion.setStyle(bg, null);
						rl.selectedRegion=rl.topRegion.data as String;
					} else {
						rl.bottomRegion.setStyle(bg, color);
						rl.topRegion.setStyle(bg, null);
						rl.selectedRegion=rl.bottomRegion.data as String;
					}
				}
				if (RPTDND.rpt != null && RPTDND.rpt.uid != rl.uid) {
					RPTDND.rpt.forceFocus(false);
				}
				RPTDND.rpt=rl;
				rl.forceFocus(true);
				e.stopImmediatePropagation();
			}
		}

		//当拖进去时  
		public function dragExit(e:MouseEvent):void {
			var bg:String="backgroundColor";
			var rl:RPTComponent=RPTComponent(e.currentTarget);
			if (RPTDND.opType < 0)
				return;
			if (rl.acceptChild(RPTDND.data) > 0) {
				//e.stopImmediatePropagation();
				rl.leftRegion.setStyle(bg, rl.getBackgroundColor());
				rl.rightRegion.setStyle(bg, rl.getBackgroundColor());
				rl.topRegion.setStyle(bg, rl.getBackgroundColor());
				rl.bottomRegion.setStyle(bg, rl.getBackgroundColor());
				rl.setRegioBackgroundAlpha(1);
				rl.forceFocus(false);
			}
		}
	}
}