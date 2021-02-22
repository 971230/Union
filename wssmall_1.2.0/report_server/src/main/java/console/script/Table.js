var Table = Class.create();
Table.prototype = {
	initialize : function(renderTo, cfg) {
		Element.extend(renderTo);
		renderTo.update("<table class='listtab' cellspacing='0' cellpadding='0' border='0'></table>");
		var tab = renderTo.firstChild;
		Element.extend(tab);
		if (cfg.id)
			tab.id = cfg.id;
		this.table=tab;
		var tHead = document.createElement("THEAD");
		tab.appendChild(tHead);
		var tHeadTr = document.createElement("TR");
		tHead.appendChild(tHeadTr);
		for ( var i = 0; i < cfg.headers.length; i++) {
			var td = tHeadTr.insertCell(-1);
			Element.extend(td);
			td.update(cfg.headers[i].text);
			td.setAttribute("dataIndex", cfg.headers[i].name);
			td.addClassName("header");
		}
		var id = tab.getAttribute("id");
		var url = cfg.url;
		tab["headers"]=cfg.headers;
		if (!id) {
			if (document["elementsCount"])
				document["elementsCount"] = 0;
			document["elementsCount"]++;
			id = "tab_" + document["elementsCount"];
			tab.id = id;
		}
		tab["reload"] = function(param,tid) {
			Report.doGet(url, param, function(d) {
				var tt = $((tid)?tid:id);
				var arr = [];
				var cells = tt.rows[0].cells;

				for ( var n = tt.rows.length - 1; n >= 1; n--) {
					tt.deleteRow(n);
				}
				if (d.data) {

					for ( var i = 0; i < d.data.length; i++) {
						var row = tt.insertRow(-1);
						
						for ( var j = 0; j < tt.headers.length; j++) {
							var h=tt.headers[j];
							var td = row.insertCell(-1);
							Element.extend(td);
							var txt=d.data[i][h.name];
							if(h.tpl) td.update(Report.format(h.tpl,[txt,Object.toQueryString(d.data[i])]));
							else td.update(txt);
						}
					}
				}
			});
		}
		
		if(!(cfg.autoLoad==false))tab.reload( {});
	},
    reload:function(param){
		
		this.table.reload(param,this.table.id);
	}
   
};