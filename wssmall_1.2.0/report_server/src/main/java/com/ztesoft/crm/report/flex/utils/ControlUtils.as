package com.ztesoft.crm.report.flex.utils {
	import flash.events.ContextMenuEvent;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	import mx.core.UIComponent;

	public class ControlUtils {

		public function ControlUtils() {
		//TODO: implement function
		}

		public static function setPadding(c:UIComponent, left:int, top:int, right:int, bottom:int):void {
			if (left >= 0) {
				c.setStyle("paddingLeft", left);
			}
			if (top >= 0) {
				c.setStyle("paddingTop", top);
			}
			if (right >= 0) {
				c.setStyle("paddingRight", right);
			}
			if (bottom >= 0) {
				c.setStyle("paddingBottom", bottom);
			}
		}

		public static function setBorder(c:UIComponent, w:int, style:String, color:uint):void {
			c.setStyle("borderColor", color);
			c.setStyle("borderStyle", style);
			c.setStyle("borderThickness", w);
		}

		public static function setSidesBorder(c:UIComponent, w:int, style:String, color:uint, sides:String):void {
			c.setStyle("borderColor", color);
			c.setStyle("borderStyle", style);
			c.setStyle("borderThickness", w);
			c.setStyle("borderSides", sides);
		}

		public static function setDefaultBorder(c:UIComponent, sides:String):void {
			if (sides == null)
				sides="left top right bottom";
			c.setStyle("borderColor", 0xCCCCCC);
			c.setStyle("borderStyle", "solid");
			c.setStyle("borderThickness", (sides == "") ? 0 : 1);
			c.setStyle("borderSides", sides);
		}

		public static function getContextMenu(arr:Array):ContextMenu {
			var menu:ContextMenu=new ContextMenu();
			menu.builtInItems.print=false;
			menu.hideBuiltInItems();
			menu.customItems=[];
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var o:Object=arr[i];
				var item:ContextMenuItem=new ContextMenuItem(o.text);
				item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, o.handler);
				menu.customItems.push(item);
			}
			return menu;
		}
	}
}