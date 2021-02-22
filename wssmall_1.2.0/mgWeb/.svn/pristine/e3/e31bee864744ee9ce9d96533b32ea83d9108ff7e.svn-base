

	//所有class为demo1的span标签都会绑定此右键菜单  
	$('span.demo1').contextMenu('myMenu1', {
		bindings : {
			'open' : function(t) {
				alert('Trigger was ' + t.id + '\nAction was Open');
			},
			'email' : function(t) {
				alert('Trigger was ' + t.id + '\nAction was Email');
			},
			'save' : function(t) {
				alert('Trigger was ' + t.id + '\nAction was Save');
			},
			'delete' : function(t) {
				alert('Trigger was ' + t.id + '\nAction was Delete');
			}
		}

	});	
	//所有div标签class为demo3的绑定此右键菜单  
	$('div.demo3').contextMenu('myMenu3', {
		//重写onContextMenu和onShowMenu事件  
		onContextMenu : function(e) {
			if ($(e.target).attr('id') == 'dontShow')
				return false;
			else
				return true;
		},

		onShowMenu : function(e, menu) {
			if ($(e.target).attr('id') == 'showOne') {
				$('#item_2, #item_3', menu).remove();
			}
			return menu;
		}

	});