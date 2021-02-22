package com.ztesoft.crm.report.flex.admin {
	import com.adobe.serialization.json.JSON;
	import com.ztesoft.crm.report.flex.URLConnection;
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.widgets.DynamicTreeDataDescriptor;
	import com.ztesoft.crm.report.flex.admin.widgets.ExporlerTreeItemRenderer;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.ContextMenuEvent;
	import flash.events.Event;

	import mx.collections.ArrayCollection;
	import mx.controls.Tree;
	import mx.core.ClassFactory;
	import mx.core.mx_internal;
	import mx.events.FlexEvent;
	import mx.events.TreeEvent;

	public class ReportExplorer extends Tree {
		[Embed(source="com/ztesoft/crm/report/resouce/html/images/tree/elbow-end-plus-nl.gif")]
		[Bindable]
		public var icon1:Class;
		[Embed(source="com/ztesoft/crm/report/resouce/html/images/tree/elbow-end-minus-nl.gif")]
		[Bindable]
		public var icon2:Class;
		[Embed(source="com/ztesoft/crm/report/resouce/html/images/tree/folder-open.gif")]
		[Bindable]
		public var icon3:Class;
		[Embed(source="com/ztesoft/crm/report/resouce/html/images/tree/folder.gif")]
		[Bindable]
		public var icon4:Class;
		[Embed(source="com/ztesoft/crm/report/resouce/html/images/tree/leaf.gif")]
		[Bindable]
		public var icon5:Class;
		private var rootNode:Object={text: "所有报表", value: "", leaf: "false", children: []};

		public function ReportExplorer(expandRoot:Boolean=true) {
			//TODO: implement function
			super();
			this.dataProvider=[rootNode];
			this.dataDescriptor=new DynamicTreeDataDescriptor();
			this.addEventListener(TreeEvent.ITEM_OPEN, nodeExpand);
			this.showRoot=false;
			this.doubleClickEnabled=true;
			this.labelField="text";
			this.setStyle("defaultLeafIcon", icon5);
			this.setStyle("folderOpenIcon", icon3);
			this.setStyle("folderClosedIcon", icon4);
			this.setStyle("disclosureClosedIcon", icon1);
			this.setStyle("disclosureOpenIcon", icon2);
			this.setStyle("verticalAligh", "bottom");
			this.rowHeight=20;
			var tree:Tree=this;
			this.contextMenu=ControlUtils.getContextMenu([{text: "添加目录", handler: addSlibingDir}, {text: "添加子目录", handler: addChildDir}, {text: "新建报表", handler: createReport}, {text: "修改名字", handler: modifyName},
				{text: "下载", handler: downloadFile}, {text: "上传报表", handler: updateFile}, {text: "修改报表属性", handler: editReportProperties}]);
			this.itemRenderer=new ClassFactory(ExporlerTreeItemRenderer);
			this.nullItemRenderer=new ClassFactory(ExporlerTreeItemRenderer);
			this.addEventListener(FlexEvent.CREATION_COMPLETE, function(d:FlexEvent):void {
				var pe:ReportExplorer=ReportExplorer(d.currentTarget);
				if (expandRoot)
					pe.expandItem(rootNode, true);
			});
		}
		private var ajax:String;
		public var preffixURL:String;

		public function shrinkAll():void {
			this.expandItem(this.rootNode, false);
		}

		private function downloadFile(e:ContextMenuEvent):void {
			var item:Object=this.selectedItem;
			if (item != null) {
				new ReportDownload().downloadAll(this.preffixURL + "/" + item.value + ".zip");
			}
		}

		public function getSelectedPath():String {
			var item:Object=selectedItem;
			if (item != null)
				return item.value;
			return "";
		}

		private function editReportProperties(e:ContextMenuEvent):void {
			var item:Object=this.selectedItem;
			if (ObjectUtil.isTrue(item.temp)) {
				MessageBox.alert("选中的节点是过渡节点，不允许编辑属性！");
				return;
			}
			if (!ObjectUtil.isTrue(item.leaf)) {
				MessageBox.alert("选中的节点是目录，不允许编辑属性！");
				return;
			}
			var app:ReportApplication=ReportApplication(this.parentApplication);
			if (app.reportFile == null || app.reportFile != item.value) {
				MessageBox.alert("报表还未打开，请双击去打开报表以后才能编辑！");
				return;
			}
			var o:Object={cols: 1, data: app.reportData, items: [{label: "首次加载", type: "combo", name: "autoLoad", data: ReportConstanst.TRUE_OR_FALSE}, {label: "报表名称", name: "title"}]};
			new FormDialog().open(this, o, "编辑报表属性", null);
		}

		private function updateFile(e:ContextMenuEvent):void {
			var item:Object=this.selectedItem;
			if (ObjectUtil.isTrue(item.temp)) {
				MessageBox.alert("选中的节点是过渡节点，不允许上传报表到它下面！");
				return;
			}
			if (item != null) {
				new ReportDownload().upload(this.ajax + "/VersionBean/flexUpload.do", item.value, (ObjectUtil.isTrue(item.leaf) ? null : function(fn:String):void {
					reload(item);
				}));
			}
		}

		private function addSlibingDir(e:ContextMenuEvent):void {
			var app:Tree=this;
			new FormDialog().open(this, {cols: 1, items: [{label: "名称", name: "text"}]}, "添加目录", function(d:XMLNodeObject):Boolean {
				d.setAttribute("leaf", "false");
				var v:String=d.getAttribute("text");
				if (!checkZN1(v)) {
					MessageBox.alert("目录只能使用英文字母、下划线、数字组合!");
					return false;
				}
				addNode(d.attributes());
				return true;
			});
		}

		private function addNode(o:Object):void {
			var item:Object=this.selectedItem;
			var i:int=0;
			if (!ObjectUtil.isTrue(o.leaf)) {
				var tempNode:Object=new Object();
				tempNode[this.labelField]="正在加载子节点...";
				tempNode["leaf"]="true";
				tempNode["temp"]="true";
				//this.dataDescriptor.addChildAt(d[i], tempNode, 0, null);
				o["children"]=[tempNode];
			}
			if (item == null)
				item=rootNode;
			if (item != null && ObjectUtil.isTrue(item.leaf))
				item=this.getParentItem(item);
			if (item != null) {
				o.value=(ObjectUtil.isEmpty(item.value) ? "" : (item.value + "/")) + o.text;
				if (!ObjectUtil.isEmpty(item.children[0]) && ObjectUtil.isTrue(item.children[0].temp)) {
					dataDescriptor.removeChildAt(item, item.children[0], 0, null);
				} else
					i=item.children.length;
				dataDescriptor.addChildAt(item, o, i, null);
			} else {
				var arr:Array=ArrayCollection(this.dataProvider).source;
				o.value=o.text;
				arr.push(o);
			}
			this.expandItem(item, true);
		}

		private function addChildDir(e:ContextMenuEvent):void {
			var item:Object=this.selectedItem;
			if (item == null)
				return;
			if (ObjectUtil.isTrue(item.temp)) {
				MessageBox.alert("选中的节点是过渡节点，不允许创建子目录");
				return;
			}
			if (ObjectUtil.isTrue(item.leaf))
				return;
			new FormDialog().open(this, {cols: 1, items: [{label: "名称", name: "text"}]}, "添加子目录", function(d:XMLNodeObject):Boolean {
				d.setAttribute("leaf", "false");
				var v:String=d.getAttribute("text");
				if (!checkZN1(v)) {
					MessageBox.alert("目录只能使用英文字母、下划线、数字组合!");
					return false;
				}
				addNode(d.attributes());
				return true;
			});
		}

		private function checkZN(str:String):Boolean {
			var r:RegExp=new RegExp("[a-zA-z0-9_]+[.][x|X][m|M][l|L]", "g");
			return r.test(str);
		}

		private function checkZN1(str:String):Boolean {
			var r:RegExp=new RegExp("[a-zA-z0-9_]+", "g");
			return r.test(str);
		}

		private function createReport(e:ContextMenuEvent):void {
			var item:Object=this.selectedItem;
			if (item == null)
				return;
			if (ObjectUtil.isTrue(item.temp)) {
				MessageBox.alert("选中的节点是过渡节点，不允许在上面创建报表");
				return;
			}
			if (ObjectUtil.isTrue(item.leaf))
				return;
			new FormDialog().open(this, {cols: 1, items: [{label: "名称", name: "text"}]}, "添加报表", function(d:XMLNodeObject):Boolean {
				d.setAttribute("leaf", "true");
				var v:String=d.getAttribute("text");
				if (!checkZN(v)) {
					MessageBox.alert("报表名只能使用英文字母、下划线、数字组合,并以.xml结尾!");
					return false;
				}
				addNode(d.attributes());
				return true;
			});
		}

		private function modifyName(e:ContextMenuEvent):void {
			var item:Object=this.selectedItem;
			if (item == null)
				return;
			if (ObjectUtil.isTrue(item.temp)) {
				MessageBox.alert("选中的节点是过渡节点，不允许修改名字");
				return;
			}
			var p:Object=this.getParentItem(item);
			var leaf:Boolean=ObjectUtil.isTrue(item.leaf);
			if (p == null)
				p={value: ""};
			var app:ReportExplorer=this;
			if (leaf != true) {
				MessageBox.alert("目录名字不能修改，只能在后台修改");
				return;
			}
			new FormDialog().open(this, {cols: 1, items: [{label: "名称", name: "text"}], data: {text: item.text}}, "修改名称", function(d:XMLNodeObject):Boolean {
				d.setAttribute("leaf", "true");
				var v:String=d.getAttribute("text");
				if (leaf) {
					if (!checkZN(v)) {
						MessageBox.alert("报表名只能使用英文字母、下划线、数字组合,并以.xml结尾!");
						return false;
					}
				}
				var text:String=d.getAttribute("text");
				var value:String=(ObjectUtil.isEmpty(p.value) ? "" : (p.value + "/")) + text;
				var ir:ExporlerTreeItemRenderer=ExporlerTreeItemRenderer(app.indexToItemRenderer(app.selectedIndex));
				URLConnection.doPostJSON(app.ajax + "/VersionBean/rename.do", {old_report: item.value, new_report: value}, function(o:Object):void {
					if (o == null)
						return;
					if (ObjectUtil.isTrue(o.data)) {
						item.value=value;
						item.text=text;
						ir.setText(d.getAttribute("text"));
					}
				});
				return true;
			});
		}

		public function setAjax(str:String):void {
			this.ajax=str;
		}

		public function select(path:String):void {
			if (ObjectUtil.isEmpty(path))
				return;


			path=path.replace(/[\\]/gi, "/");
			var arr:Array=path.split("/");
			var i:int=0;
			if (arr == null || arr.length == 0)
				return;
			if (arr[0] == "")
				i=1;


			this.selectNode(this.rootNode, arr, i);
		}

		private var firstLoad:int=0;

		private function selectNode(node:Object, name:Array, level:int):void {
			if (ObjectUtil.isTrue(node.leaf))
				return;
			var tree:Tree=this;


			if (!ObjectUtil.isTrue(node.hasLoad)) {
				this.reload(node, function(cn:Object, s:String):void {
					tree.expandItem(cn, true, false, false, null);


					selectNode(cn, name, level);
				});
			} else {
				var arr:Array=node.children;
				if (ObjectUtil.isEmpty(arr) || arr.length == 0 || ObjectUtil.isTrue(node.leaf)) {
					if (node.text.replace(".xml", "") == name[level]) {

						tree.selectedItem=node;
					}
					return;
				}
				var len:int=arr.length;
				for (var i:int=0; i < len; i++) {
					var n:Object=arr[i];
					if (n.text.replace(".xml", "") == name[level]) {
						if (!ObjectUtil.isTrue(n.leaf)) {
							this.selectNode(n, name, level + 1);
						} else
							tree.selectedItem=n;
					}
				}

			}
		}

		private function nodeExpand(e:TreeEvent):void {
			var node:Object=e.item;
			this.currNode=node;
			this.currNode["parentValue"]=this.currNode["value"];

			if (node.hasLoad != true) {
				this.load();
			}
		}
		private var currNode:Object;

		public function load():void {
			this.reload(this.currNode);
		}

		public function reload(o:Object, cb:Function=null):void {
			this.currNode=o;
			if (o != null && o.hasLoad == true)
				return;
			if (this.currNode != null)
				this.currNode["parentValue"]=this.currNode["value"];
			var uc:URLConnection=new URLConnection(function(str:String):void {

				addChildren(str);
				if (!ObjectUtil.isEmpty(cb))
					cb(o, str);
			});

			uc.open(this.ajax + "/ReportDirectoryTreeBean/list.do", o);
			uc=null;
		}

		private function addChildren(str:String):void {

			var d:Object=JSON.decode(str, true).data;
			if (!d)
				return;

			if (this.currNode == null)
				this.currNode=this.rootNode;

			if (this.currNode != null)
				this.currNode["hasLoad"]=true;



			try {
				if (this.currNode != null) {
					this.currNode["children"]=new Array();
				}
				for (var i:int=0; i < d.length; i++) {
					var o:Object=d[i];
					//this.dataDescriptor.addChildAt(this.currNode, d[i], i, null);
					if (o.leaf != "true" || o.leaf != true) {
						var tempNode:Object=new Object();
						tempNode[this.labelField]="正在加载子节点...";
						tempNode["leaf"]="true";
						tempNode["temp"]="true";
						//this.dataDescriptor.addChildAt(d[i], tempNode, 0, null);
						o["children"]=[tempNode];
					}
				}
				if (this.currNode != null && this.currNode.children != null) {
					for (var a:int=this.currNode.children.length - 1; a >= 0; a--) {
						this.dataDescriptor.removeChildAt(this.currNode, this.currNode.children[a], a, null);
					}
				}
				for (var n:int=0; n < d.length; n++) {
					this.dataDescriptor.addChildAt(this.currNode, d[n], n, null);
				}
			} catch (e:Error) {
				MessageBox.alert(e.message);
			}
		}

		override public function get maxHorizontalScrollPosition():Number {
			if (isNaN(mx_internal::_maxHorizontalScrollPosition))
				return 0;
			return mx_internal::_maxHorizontalScrollPosition;
		}

		override public function set maxHorizontalScrollPosition(value:Number):void {
			mx_internal::_maxHorizontalScrollPosition=value;
			dispatchEvent(new Event("maxHorizontalScrollPositionChanged"));
			scrollAreaChanged=true;
			invalidateDisplayList();
		}

		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			var diffWidth:Number=measureWidthOfItems(0, 0) - (unscaledWidth - viewMetrics.left - viewMetrics.right);
			var indentation:Number=getStyle("indentation");
			if (diffWidth <= 0)
				maxHorizontalScrollPosition=NaN;
			else
				maxHorizontalScrollPosition=diffWidth + indentation;
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
	}
}