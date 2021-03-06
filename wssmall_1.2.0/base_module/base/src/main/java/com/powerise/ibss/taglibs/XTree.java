package com.powerise.ibss.taglibs ;

import com.powerise.ibss.util.HashArray;
import com.powerise.ibss.util.Utility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.HashMap;

public class XTree
	extends TagSupport {

	private String head ; //根节点Title
	private String pidF ; //父编号字段名
	private String idF ; //编号字段名
	private String pid ; //最上层默认编号
	private String typeF ; //类别字段
	private String titleF ; //title 字段名
	private ArrayList treeData ; //数据集
	private String icon ; //图标
	private String headIcon ; //最上级图标
	private String hiddenF ; //要隐藏的字段列表

	private StringBuffer html ;
	@Override
	public int doStartTag() throws JspException {
		html = new StringBuffer () ;
		html.append ("<script>\n") ;
		html.append (" var tree = new WebFXTree('" + head + "');\n") ;
		if (headIcon != null) {
			html.append (" tree.icon = '" + headIcon + "'") ;
		}
		html.append (" tree.setBehavior('classic');\n") ;
		if (treeData != null && treeData.size () > 0) {
			buildXTree (treeData, "tree", pid, 0) ;
		}
		html.append (" document.write(tree);\n") ;
		html.append ("</script>") ;
		return (EVAL_PAGE) ;
	}

	private void buildXTree(ArrayList xTree, String handle, String pid,
									int level) {
		String node ;
		String hidden ;
		HashArray item ;
		for (int i = 0 ; i < xTree.size () ; i++) {
			item = new HashArray ( (HashMap) xTree.get (i)) ;
			if (item.get (pidF, "").toString ().equals (pid)) {
				node = "i_" + String.valueOf (level) + "_" + String.valueOf (i) ;
				hidden = "" ;
				//形成隐藏字段
				if (hiddenF != null) {
					String[] hid = Utility.split (hiddenF, ",") ;
					for (int j = 0 ; j < hid.length ; j++) {
						hidden += item.get (hid[j], "") + "," ;
					}
				}
				if (hidden.length () > 0) {
					hidden = hidden.substring (0, hidden.length () - 1) ;
					//形成隐藏字段
				}
				html.append (" var " + node + " = new WebFXTreeItem('" +
								 item.get (titleF) + "','" + hidden + "'") ;
				if (icon != null && icon.trim ().length () > 0) {
					if (typeF != null &&
						 item.get (typeF, "").toString ().length () > 0) {
						html.append (",'','images/" + item.get (typeF) + icon +
										 "','images/" + item.get (typeF) + "open_" + icon +
										 "'") ;
					} else {
						html.append (",'','images/" + icon + "','images/open_" + icon +
										 "'") ;
					}
				}
				html.append (");\n") ;
				html.append (" " + handle + ".add(" + node + ");\n") ;
				level = level + 1 ;
				buildXTree (xTree, node, (String) item.get (idF), level) ;
			}
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut ().print (html.toString ()) ;
		} catch (Exception e) {
			e.printStackTrace () ;
			throw new JspException (e.getMessage ()) ;
		}
		return (EVAL_PAGE) ;
	}

	//以下为属性设置
	public void setHead(String head) {
		this.head = head ;
	}

	public void setPidF(String pidF) {
		this.pidF = pidF ;
	}

	public void setIdF(String idF) {
		this.idF = idF ;
	}

	public void setPid(String pid) {
		this.pid = pid ;
	}

	public void setTitleF(String titleF) {
		this.titleF = titleF ;
	}

	public void setTreeData(ArrayList treeData) {
		this.treeData = treeData ;
	}

	public void setHiddenF(String hiddenF) {
		this.hiddenF = hiddenF ;
	}

	public void setIcon(String icon) {
		this.icon = icon ;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon ;
	}

	public void settypeF(String typeF) {
		this.typeF = typeF ;
	}
}
