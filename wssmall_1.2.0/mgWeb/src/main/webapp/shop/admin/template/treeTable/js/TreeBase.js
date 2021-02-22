/**
 *列表树控件
 *@auther:pzh
 *
 */
(function(scope) {
	var TreeBase = Base.extend({
		tree_pointer : null,
		input_filed : 'node_table_field_code',
		node_seq : 'node_seq',
		node_depth : 'node_depth',
		node_path : 'node_path',
		node_code : 'node_code',
		keyWord : 're_field_name',
		search_url : 'lucenceAttrTableAction!search.do?ajax=yes',
		show_content : 're_show_name',
		constructor : function(div, width, titleWidth, tableHead, field_code ,event) {
			// 目标div
			if (!div) {
				alert("请输入第一个参数：Div对象！");
				return false;
			}
			this.aimDiv = MyJQ(div);
			// width宽度
			if (!width) {
				alert("请输入第二个参数：表格宽度！");
				return false;
			}
			this.width = width;
			// titleWidth标题宽度
			if (!titleWidth) {
				alert("请输入第三个参数：标题宽度！");
				return false;
			}
			this.titleWidth = titleWidth;
			// 要显示的标题
			if (!tableHead) {
				alert("请输入第四个参数：标题对象！");
				return false;
			}
			this.treeTableHead = tableHead;

			if(!field_code) {
				alert("请输入第五个参数：标题编码！");
				return false;
			}
			
			if (tableHead.length != titleWidth.length) {
				alert('标题宽度的数据长度与标题对象的数据长度不相等！');
				return false;
			}
			
			if(tableHead.length != field_code.length){
				alert('标题对象的数据长度与标题编码的数据长度不相等！');
				return false;
			}
			this.field_code = field_code;
			//标题事件
			if (null != event && event.length != titleWidth.length){
				alert('标题对象长度与事件长度不一致');
				return false;
			}
			this.colEvent = event;
			// 子节点数据量大于多少时弹出窗口.默认为10
			this.numForOpenWin = 10;
			// 层级空格的长度
			this.blankValue = '&nbsp;&nbsp;&nbsp;&nbsp;';
			// 根节点标识
			this.root = '-0';
			//保存当前鼠标指向的节点
			this.mouseRoot = this.root;
			tree_pointer = this;
			this.buildTreeTable();
			
			$("#" + div).data('tree_obj', this);
		},
		buildTreeTable : function(){
			var aimDiv = this.aimDiv;
			// 创建table
			var treeTable = document.createElement('table');
			treeTable.id = 'dataTable';
			treeTable.style.width = this.width;
			treeTable.cellSpacing = '1px';
			treeTable.cellPadding = '0';
			var trTitle = document.createElement('tr');
			trTitle.style.width = this.width;
			trTitle.style.fontWeight = 'bold';
			trTitle.setAttribute('tree_title','yes');
			// 设置每个标题单元格
			for ( var i = 0; i < this.treeTableHead.length; i++) {
				var th = document.createElement('th');
				th.width = this.titleWidth[i];
				th.setAttribute('col_event', this.colEvent[i]);
				th.setAttribute('col_filed_name', this.field_code[i]);
				th.setAttribute('index_id', i);
				th.innerHTML = "&nbsp;" + this.treeTableHead[i];
				trTitle.appendChild(th);
			}
			var tbody = document.createElement("tbody");
			tbody.appendChild(trTitle);
			tbody.id = 'dataTableTbody';
			treeTable.appendChild(tbody);
			aimDiv.appendChild(treeTable);
		},
		addRows : function(list){	// 填充行，pid父节点id,没有则为root级行,有则新创建的tr都为pid下的子节点
			var list = list;
			var root = MyJQ('dataTableTbody');
			for ( var i = 0; i < list.length; i++) {
				// 如果为指定单元行对象就添加，不然就退出报错
				if (list[i] instanceof TreeObj) {
					var tr = document.createElement('tr');
					tr.id = list[i].trId;
					// 单元行隔行变色
					this.rowChangeBkColor(tr);
					// 设置父节点
					tr.setAttribute('pid', list[i].trPid);
					tr.setAttribute('tree_state', 'close');
					tr.setAttribute(this.node_seq, list[i].tdValue[this.node_seq]);
					tr.setAttribute('is_leaf','yes');
					// 给每行的单元格赋值
					for ( var j = 0; j < this.field_code.length; j++) {
						var td = document.createElement('td');
						td.setAttribute('EditType','TextBox');
						td.setAttribute('index_id', j);
						td.setAttribute('field_name', this.field_code[j]);
						// 第一个单元格要设置事件与图片
						if (j == 0) {
							this.rootRowsSetImg(td, list[i].trPid);
							var span = document.createElement('span');
							span.innerHTML = list[i].tdValue[this.field_code[j]];
							span.setAttribute('fist_ele','yes');
							td.appendChild(span);
							td.onclick = this.rowOnClick.bindEvent(td, this);
							var trData = this.addBlank(list[i].trPid, list[i].trId);
							td.innerHTML = trData['blank'] + td.innerHTML;
							tr.setAttribute('tree_dp', trData['tree_dp']);
							tr.setAttribute(this.node_path, trData[this.node_path]);
							list[i].tdValue[this.node_depth] = trData['tree_dp'];
							list[i].tdValue[this.node_path] = trData[this.node_path];
							$(tr).data('node_info',list[i].tdValue);
							tr.appendChild(td);
						} else {
							// 标题中没有的字段，全部为隐藏字段
							if (j >= this.treeTableHead.length) {
								td.style.display = 'none';
							}
							if(GridEdit.tree_oper_mode == 'oper_model'){
								if(this.input_filed == this.field_code[j]){
									var height = $('tr[pid="' + this.root + '"]').height() - 5;
									var dom = tree_pointer.genInputDom(list[i].trId, height, this.field_code[j], list[i].tdValue[this.field_code[j]]);
									td.setAttribute('db_click','no');
									$(td).empty();
									$(td).append(dom);
								}else {
									td.innerHTML = list[i].tdValue[this.field_code[j]];
								}
							}else{
								td.innerHTML = list[i].tdValue[this.field_code[j]];
							}
							tr.appendChild(td);
						}
					}
					GridEdit.setRowCanEdit(tr);
					$(tr).unbind('contextmenu').bind('contextmenu',function(event){
//						tree_pointer.rowRightClick(event, $(this));				//屏蔽右键事件
					});
					$(tr).unbind('click').bind('click', function(){
						$(this).attr('curr_check', 'yes');
						tree_pointer.setBkColor($(this), "row_checked");
						$(this).siblings('tr').each(function(){
							tree_pointer.setBkColor($(this), "row_uncheck");
						});
						$(this).siblings('tr').attr('curr_check', '');
					});
					if (list[i].trPid != this.root) {
						var last_id = tree_pointer.getLastNode(list[i].trPid);
						$(tr).insertAfter($('tr[id="' + last_id + '"]'));
					}else {
						root.appendChild(tr);
					}
					if(GridEdit.tree_oper_mode == 'oper_model')
						Lucene.init('node_' + list[i].trId);
				} else {
					alert('addRows方法传入的list参数包含的数据不是指定的trObj对象，在第' + i + '行时开始出错！');
				}
			}
		},
		addData : function(trPid,num){
			//子节点	
			var list = [];
			var ltr = $('tr[pid="' + trPid + '"]:last');
			var length = 1;
			if(null != ltr && ltr.length > 0){
				var cur_sort = ltr.attr(this.node_seq);
				length = Number(cur_sort) + 1;
			}
			for(var j=0;j<num;j++){
				var treeObj = new TreeObj();
				var nodeInfo = {};
				treeObj.tdValue[this.node_seq] = length++;
				for(var i = 0; i < this.field_code.length; i++){
					treeObj.tdValue[this.field_code[i]] = this.field_code[i] + "_value";
				}
				//文字的颜色,默认黑色
				treeObj.tdColor = "#000000";
				var nowDate=this.formatDate(new Date());
				//节点id
				treeObj.trId = nowDate+""+Math.floor(Math.random()*100);
				//父节点id
				treeObj.trPid = trPid;
				list.push(treeObj);
			}
			this.addRows(list);
			return list;
		},
		formatDate: function(date){
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var da = date.getDate();
			var hour = date.getHours();
			var minute = date.getMinutes();
			var second = date.getSeconds();
			var mill=date.getMilliseconds()
			return year +"" + month+""  + da+""  + hour+"" + minute +""+ second+""+mill;
		},
		rowOnClick : function(even, obj, root){		// 单元格单击事件，合闭时单击展开，展开时单击合闭

//			GridEdit.click_timer = setTimeout(function(){}, 200);			// 解决单击、双击事件冲突
//			if(GridEdit.click_num == 2){
//				GridEdit.click_num = 1;
//				return ;
//			}
//			clearTimeout(GridEdit.click_timer);
			
			var tree_state = $(obj).closest('tr').attr('tree_state');
			var aimObj = obj.getElementsByTagName('img')[0];
			var rowBkImg = aimObj.src;
			var e = even.target ? even.target : window.event.srcElement
			// 点击超链接则不展开
			if (e.tagName == 'A')
				return;
			if (tree_state == 'open') {
				tree_pointer.closeTree(obj.parentNode.attributes['id'].nodeValue);
			} else {
				tree_pointer.openTree(obj.parentNode.attributes['id'].nodeValue);
			}
		},
		rowRightClick : function(event, row){
			event.preventDefault();
			var cur_id = row.attr('id');				//当前点击行的id
			//创建右键菜单
			var oMenu = $("#tree_menu ul.menu_style");
			$("li", oMenu).each(function(){
				$(this).unbind('mouseover').bind('mouseover', function(){
					$(this).addClass("active");
				});
				$(this).unbind('mouseout').bind('mouseout', function(){
					$(this).removeClass("active");
				});
				$(this).unbind('click').bind('click', function(){
					var callback = $(this).attr('click_method');
					var oper_code = $(this).attr('name');
					var params = null;
					if(oper_code == 'add_node'){
						tree_pointer.addData(cur_id,1);
						params = $("tr[id='" + cur_id + "']").data('node_info');
					}else if(oper_code == 'del_node'){
						params = [];
						params.push($("tr[id='" + cur_id + "']").data('node_info'));
						$("tr[pid='" + cur_id + "']").each(function(){
							params.push($(this).data('node_info'));
							$(this).remove();
						});
						$("tr[id='" + cur_id + "']").remove();
					}
					eval(callback + "(" + JSON.stringify(params) + ")");
				});
			});
			//页面点击后自定义菜单消失
			document.onclick = function () {
				oMenu.hide();
			}
			var event = event;
			oMenu.css('display','block');
			var X = (event.pageX || event.clientX + document.body.scroolLeft)+"px";
			var Y = (event.pageY || event.clientY + document.body.scrollTop)+"px";
			oMenu.css('top',Y);
			oMenu.css('left',X);
		},
		rootRowsSetImg : function(objTd, pId){	// 设置开启闭合背景图
			var img = document.createElement('img');
			if(pId != this.root && 'undefined' != pId && '' != pId && null != pId){
				$("tr[id='" + pId + "']", this.aimDiv).find("img").attr('src', ctx + '/shop/admin/template/treeTable/image/minus.gif');
				$("tr[id='" + pId + "']", this.aimDiv).attr('tree_state', 'open');
				$("tr[id='" + pId + "']", this.aimDiv).attr('is_leaf', 'no');
			}
			img.src = ctx + '/shop/admin/template/treeTable/image/arr.png';
			img.style.verticalAlign = 'middle';
			img.style.marginRight = '5px';
			objTd.appendChild(img);
		},
		rowChangeBkColor : function(objTr){	// 设置单元行变色事件
			objTr.onmouseover = this.rowOnMouseOver.bindEvent(objTr, this);
			objTr.onmouseout = this.rowOnMouseOut.bindEvent(objTr, this);
		},
		rowOnMouseOver : function(e, tr, obj){			// 鼠标移动到某行上时触发事件
			var tObj = tr;
			// 如果当前鼠标指向的根目录与之前的不同
			if (obj.mouseRoot != obj.root) {
				var trObjs = MyJQ('dataTable').getElementsByTagName('tr');
				for ( var i = 0; i < trObjs.length; i++) {
					tree_pointer.setBkColor(trObjs[i], 'row_uncheck');
				}
			}
			tree_pointer.setBkColor(tObj, 'row_checked');
			// 头节点变色
			if (tObj.attributes['pid'].nodeValue == obj.root) {
				tree_pointer.setBkColor(tObj, 'row_checked');
				obj.mouseRoot = tObj.attributes['id'].nodeValue;
			}
		},
		rowOnMouseOut : function(e, tr, obj){		// 鼠标离开某行上时触发事件
			tree_pointer.setBkColor(tr, 'row_uncheck');
			if($(this.aimDiv).find('tr.row_checked').length == 0){
				tree_pointer.setBkColor($(this.aimDiv).find('tr[curr_check="yes"]'), 'row_checked');
			}
		},
		addBlank : function(pid, id){ // 按菜单等级添加treeTable层级空格
			var result = {};
			if (pid != this.root) {
				result[this.node_path] = this.getTreePath(pid);
				var blank = this.blankValue;
				var tree_dp = 2;
				while (MyJQ(pid) != null && this.treeParent(pid) != this.root) {
					pid = (MyJQ(pid).attributes['pid'].nodeValue);
					blank += this.blankValue;
					tree_dp++;
				}
				result['blank'] = blank;
				result['tree_dp'] = tree_dp;
				return result;
			} else {
				result['blank'] = "";
				result[this.node_path] = "";
				result['tree_dp'] = 1;
				return result;
			}
		},
		getTreePath : function(pid){
			var path = $('tr[id="' + pid + '"]').attr(this.node_path);
			var code = $('tr[id="' + pid + '"]').find('td[field_name="' + this.node_code + '"] span').html();
			if(null != path && '' != path && 'undefined' != path){
				path = path + "." + code
			}else {
				path = code;
			}
			return path;
		},
		treeParent : function(id){	// 返回指定节点的父节点
			if (id != null) {
				;
				var temp = MyJQ(id).attributes['pid'].nodeValue;
				if (temp != this.root) {
					return temp;
				}
				return this.root;
			} else {
				return null;
			}
		},
		setBkColor : function(obj,style){
			if('row_checked' == style){
				$(obj).removeClass('row_uncheck');
			}else {
				$(obj).removeClass('row_checked');
			}
			$(obj).addClass(style);
		},
		closeTree : function(id){
			var is_leaf = $("tr[id='" + id + "']", this.aimDiv).attr('is_leaf');
			if(is_leaf == 'no')
				$("tr[id='" + id + "']", this.aimDiv).find("img").attr('src',ctx + '/shop/admin/template/treeTable/image/plus.gif');
			$("tr[id='" + id + "']", this.aimDiv).attr('tree_state','close')
			var subTree = $("tr[pid='" + id + "']", this.aimDiv);
			if(null != subTree && subTree.length > 0){
				subTree.each(function(){
					var cur_id = $(this).attr('id');
					tree_pointer.closeTree(cur_id);
					$(this).hide();
				});
			}
		},
		openTree : function(id){
			var is_leaf = $("tr[id='" + id + "']", this.aimDiv).attr('is_leaf');
			if(is_leaf == 'no')
				$("tr[id='" + id + "']", this.aimDiv).find("img").attr('src',ctx + '/shop/admin/template/treeTable/image/minus.gif');
			$("tr[id='" + id + "']", this.aimDiv).attr('tree_state','open')
			var subTree = $("tr[pid='" + id + "']", this.aimDiv);
			if(null != subTree && subTree.length > 0){
				subTree.show();
			}
		},
		modeChange : function(oper_mode){
			GridEdit.tree_oper_mode = oper_mode;
		},
		operChange : function(oper_mode){
			this.modeChange(oper_mode);
			if(oper_mode == 'oper_model'){
				$('td[field_name="' + tree_pointer.input_filed +'"]').each(function(){
					var value = $(this).html();
					var height = $(this).height() - 5;
					var node_id = $(this).closest('tr').attr('id');
					var dom = tree_pointer.genInputDom(node_id, height, tree_pointer.input_filed, value);
					$(this).empty();
					$(this).append(dom);
					$(this).attr('db_click', 'no');
					Lucene.init('node_' + node_id);
				});
			}else if(oper_mode == 'simple_model'){
				$('td[field_name="' + tree_pointer.input_filed +'"]').each(function(){
					var value = $(this).find('input').val();
					$(this).html(value);
				});
			}
		},
		genInputDom : function(node_id, height, field_name, value){
			var callBack = $('th[col_filed_name="' + this.input_filed + '"]').attr('col_event');
			var domObj = $("<input type='text' id='node_" + node_id + "' class='treetxt' style='width:80%;height:" 
					+ height + "px;' field_name='" + field_name 
					+ "' value='" + value + "' name='" + this.keyWord +"' " +
							"tableColumn='" + this.show_content + 
							"' keyFun='" + callBack + "' search_url='" + this.search_url + "' />");
			return  domObj;
		},
		isLeaf : function(id){
			var is_leaf = $('tr[id="' + id + '"]',this.aimDiv).attr('is_leaf');
			if(is_leaf == 'yes')
				return true;
			return false;
		},
		delTreeNode :function(pid){
			//$("tr[id='"+pid+"']").remove();
			$('tr[pid="' + pid + '"]').each(function(){
				var cur_id = $(this).attr('id');
				$("tr[id='"+cur_id+"']").remove();
				tree_pointer.delTreeNode(cur_id);
			});
		},
		getLastNode :function(pid){
			var subObj = $('tr[pid="' + pid + '"]');
			while(subObj != null && subObj.length > 0){
				pid = $(subObj[subObj.length - 1]).attr('id');
				subObj = $('tr[pid="' + pid + '"]');
			}
			return pid;
		},
		copyTreeNode: function(pid){
			var treeList=[];
			tree_pointer.getTeeList(pid,treeList);
			return treeList;
		},
		pasteTreeNode :function(pid,treeList){
			var retList=[];
			tree_pointer.sortTreeList(pid,treeList,retList);
			this.addRows(treeList);
			return treeList;
		},
		sortTreeList :function (pid,treeList,retList){
			for(var i=0;i<treeList.length;i++){
				var treeObj=treeList[i];
				var trId=treeObj.trId;
				var nowDate=this.formatDate(new Date());
				var newtrId = nowDate+""+Math.floor(Math.random()*100);
				treeObj.trId=newtrId;
				if(i==0){
					treeObj.trPid=pid;
				}
				for(var j=i;j<treeList.length;j++){
					var treeObj2=treeList[j];
					var pid2=treeObj2.trPid;
					if(pid2==trId){
						treeObj2.trPid=newtrId;
						retList.push(treeObj2);
					}
				}
				
			}
			
		},
        getParentNode : function(nodeId,node_depth){
        	var upTr=$("tr[id='" + nodeId + "']").prev('tr');
        	var data=$("tr[id='" + nodeId + "']").prev('tr').data('node_info');
        	var upNodeId=upTr[0].id;
        	var cur_depth=data.node_depth;
        	if(cur_depth==node_depth){
        		return upNodeId;
        	}else{
        		return tree_pointer.getParentNode(upNodeId,node_depth);
        	}
		},
		upMove : function (nodeId,superNodeId,node_depth){
			var retStr="";
			//当前节点
			var cur_list=[];
			tree_pointer.getTeeList(nodeId,cur_list);
			
			//前一个TR
			var upTr=$("tr[id='" + nodeId + "']").prev('tr');
			var upData=$("tr[id='" + nodeId + "']").prev('tr').data('node_info');
			var upNodeId=upTr[0].id;
			var upDepth=upData.node_depth;
			if(upNodeId==superNodeId){
				alert("第一个子节点不能上移！!");
        		return;
			}
			
			//上一个节点
			var prevNodeId="";
			if(upDepth==node_depth){
				prevNodeId=upNodeId;
			}else{
				prevNodeId=tree_pointer.getParentNode(upNodeId,node_depth);
			}
			
			//改变序列值
        	var curTr=$("tr[id='" + nodeId + "']");
			var curData=$("tr[id='" + nodeId + "']").data('node_info');
			var curSeq=curData.node_seq;
			//alert(JSON.stringify(curData));
			var upTr=$("tr[id='" + prevNodeId + "']");
			var upData=$("tr[id='" + prevNodeId + "']").data('node_info');
			var upSeq=upData.node_seq;
			//alert(JSON.stringify(upData));
			curData.node_seq=upSeq;
			upData.node_seq=curSeq;
			curTr.attr(this.node_seq,upSeq);
			upTr.attr(this.node_seq,curSeq);
			retStr=nodeId+":"+upSeq+";"+upTr[0].id+":"+curSeq;
			
			//上移
			for(var i=0;i<cur_list.length;i++){
        		var obj=cur_list[i];
        		var moveNodeId=obj.trId;
        		var movePid=obj.trPid;
        		if(i==0){
        			$("tr[id='"+moveNodeId+"']").insertBefore($("tr[id='"+prevNodeId+"']"));
        		}else{
        			var prevObj=cur_list[i-1];
                	var tmpId=prevObj.trId;
        			$("tr[id='"+moveNodeId+"']").insertAfter($("tr[id='"+tmpId+"']"));
        		}
        	}
			
			return retStr;
			
		},
		downMove : function (nodeId,superNodeId,node_depth){
			
			var retStr="";
			var cur_list=[];
			tree_pointer.getTeeList(nodeId,cur_list);
        	var lastNode=cur_list[cur_list.length-1];
        	var lastNodeId=lastNode.trId;
        	//alert("第一最后子节点"+lastNode.trId);
        	
        	var nextTr=$("tr[id='" + lastNodeId + "']").next('tr');
        	var nextData=$("tr[id='" + lastNodeId + "']").next('tr').data('node_info');
        	if(nextTr[0]==null||nextTr[0].id==undefined||nextTr[0].id=='undefined'||nextTr[0].id==null){
        		alert("最后一个子节点不能下移！!");
        		return;
        	}
        	var nextDepth=nextData.node_depth;
        	if(nextDepth<node_depth){
        		alert("同级的最后一个子节点不能下移！");
        		return;
        	}
        	var nextNodeId=nextTr[0].id;
        	var next_list=[];
        	tree_pointer.getTeeList(nextNodeId,next_list);
        	var nextLastNode=next_list[next_list.length-1];
        	var lastNextNodeId=nextLastNode.trId;
        	//alert("第二最后子节点"+lastNextNodeId);
        	
        	//改变序列值
        	var curTr=$("tr[id='" + nodeId + "']");
			var curData=$("tr[id='" + nodeId + "']").data('node_info');
			var curSeq=curData.node_seq;
			//alert(JSON.stringify(curData));
			var nextData=$("tr[id='" + lastNodeId + "']").next('tr').data('node_info');
			var nextSeq=nextData.node_seq;
			//alert(JSON.stringify(nextData));
			curData.node_seq=nextSeq;
			nextData.node_seq=curSeq;
			curTr.attr(this.node_seq,nextSeq);
			nextTr.attr(this.node_seq,curSeq);
			
			retStr=nodeId+":"+nextSeq+";"+nextTr[0].id+":"+curSeq;
			
        	//下移
			
        	for(var i=0;i<cur_list.length;i++){
        		var obj=cur_list[i];
        		var moveNodeId=obj.trId;
        		var movePid=obj.trPid;
        		if(i==0){
        			$("tr[id='"+moveNodeId+"']").insertAfter($("tr[id='"+lastNextNodeId+"']"));
        		}else{
        			var prevObj=cur_list[i-1];
                	var tmpId=prevObj.trId;
        			$("tr[id='"+moveNodeId+"']").insertAfter($("tr[id='"+tmpId+"']"));
        		}
        	}
        	
        	
        	return retStr;
		},
		getTeeList : function(pid, treeList){
			var treeObj = new TreeObj();
			treeObj.trId = pid;
			//父节点id
			treeObj.trPid = $('tr[id="' + pid + '"]').attr('pid');
			treeObj.tdValue = $('tr[id="' + pid + '"]').data('node_info');
			treeList.push(treeObj);
			$('tr[pid="' + pid + '"]').each(function(){
				var cur_id = $(this).attr('id');
				tree_pointer.getTeeList(cur_id, treeList);
			});
		},
		setTreeList : function(treeList){
			for(var i = 0; i < treeList.length; i++){
				var id = treeList[i].trId;
				$('tr[id="' + id + '"]').find('td').each(function(){
					var field_name = $(this).attr('field_name');
					tree_pointer.setCellVal($(this),treeList[i].tdValue[field_name]);
				});
			}
		},
		setCellVal : function(obj, filed_value){
			if(obj.find('input') > 0){
				obj.find('input').val(filed_value);
			}else if(obj.find('span') > 0){
				obj.find('span').html(filed_value);
			}else {
				obj.html(filed_value);
			}
		}
	});
	window.TreeBase = TreeBase;
}(window));
