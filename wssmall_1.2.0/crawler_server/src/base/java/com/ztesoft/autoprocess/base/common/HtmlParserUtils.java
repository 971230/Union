package com.ztesoft.autoprocess.base.common;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * htmlparser辅助工具类
 * 
 * @author shusx
 */
public class HtmlParserUtils {
	/**
	 * @Description: 从htmlparser中获取指定id的select选中值
	 * @author: shusx
	 * @date: 2013-10-8 下午4:18:02
	 * 
	 * @param parser
	 * @param id
	 * @return
	 * @throws ParserException
	 */
	public static String getSelectedVal(Parser parser, String id)
			throws ParserException {
		String selectVal = "";

		// 先重置
		parser.reset();

		NodeFilter selectFilter = new TagNameFilter("SELECT");
		NodeFilter hasAttrFilter = new HasAttributeFilter("id", id);
		NodeFilter andFilter = new AndFilter(selectFilter, hasAttrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		SelectTag node = (SelectTag) nodeList.elementAt(0);

		if (node == null)
			return "";
		// 获取所有子节点
		NodeList optionList = node.getChildren();
		for (int i = 0; i < optionList.size(); i++) {
			if (optionList.elementAt(i) instanceof TagNode) {
				TagNode option = (TagNode) optionList.elementAt(i);
				if (option.getText().indexOf("selected") != -1) {
					// 重置一下selected,有些列表不是selected="selected",而是selected
					option.removeAttribute("selected");
					option.setAttribute("selected", "selected");
				}
			}
		}

		// 获取所有子节点
		OptionTag[] options = node.getOptionTags();
		for (int i = 0; i < options.length; i++) {
			OptionTag option = options[i];
			String select = option.getAttribute("selected");
			if (select != null && "selected".equals(select)) {
				selectVal = option.getAttribute("value");
				break;
			}
		}

		return selectVal;
	}

	/**
	 * @Description: 从htmlparser中获取指定id的input选中值
	 * @author: shusx
	 * @date: 2013-10-8 下午4:18:02
	 * 
	 * @param parser
	 * @param id
	 * @return
	 * @throws ParserException
	 */
	public static String getInputVal(Parser parser, String id)
			throws ParserException {
		String inputVal = "";

		// 先重置
		parser.reset();

		NodeFilter inputFilter = new TagNameFilter("INPUT");
		NodeFilter hasAttrFilter = new HasAttributeFilter("id", id);
		NodeFilter andFilter = new AndFilter(inputFilter, hasAttrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		InputTag node = (InputTag) nodeList.elementAt(0);

		inputVal = node == null ? "" : node.getAttribute("value");

		return inputVal;
	}
	
	/**
	 * 根据Id 判断改文本域是否存在
	 * @param parser
	 * @param id
	 * @return
	 * @throws ParserException
	 */
	public static boolean ifInputExistById(Parser parser,String id) throws ParserException{
		// 先重置
		parser.reset();

		NodeFilter inputFilter = new TagNameFilter("INPUT");
		NodeFilter hasAttrFilter = new HasAttributeFilter("id", id);
		NodeFilter andFilter = new AndFilter(inputFilter, hasAttrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		InputTag node = (InputTag) nodeList.elementAt(0);
		return null != node;
	}

	/**
	 * 从HTML中 获取组件 html 内容
	 * 
	 * @param parser
	 * @param id
	 * @return
	 * @throws ParserException
	 */
	public static String getSpanHtml(Parser parser, String id)
			throws ParserException {
		String inputVal = "";

		// 先重置
		parser.reset();

		NodeFilter inputFilter = new TagNameFilter("span");
		NodeFilter hasAttrFilter = new HasAttributeFilter("id", id);
		NodeFilter andFilter = new AndFilter(inputFilter, hasAttrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		Span node = (Span) nodeList.elementAt(0);

		inputVal = node == null ? "" : node.getStringText();

		return inputVal;
	}

	/**
	 * 从HTML中 获取组件 html 内容
	 * 
	 * @param parser
	 * @param class
	 * @return
	 * @throws ParserException
	 */
	public static String getSpanHtmlByClass(Parser parser, String classes)
			throws ParserException {
		String inputVal = "";

		// 先重置
		parser.reset();

		NodeFilter inputFilter = new TagNameFilter("span");
		NodeFilter hasAttrFilter = new HasAttributeFilter("class", classes);
		NodeFilter andFilter = new AndFilter(inputFilter, hasAttrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		Span node = (Span) nodeList.elementAt(0);

		inputVal = node == null ? "" : node.getStringText();

		return inputVal;
	}

	public static String getLiHtml(Parser parser, String id)
			throws ParserException {
		String liVal = "";

		// 先重置
		parser.reset();

		NodeFilter inputFilter = new TagNameFilter("li");
		NodeFilter hasAttrFilter = new HasAttributeFilter("id", id);
		NodeFilter andFilter = new AndFilter(inputFilter, hasAttrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		Bullet node = (Bullet) nodeList.elementAt(0);

		liVal = node == null ? "" : node.getAttribute("onclick");

		return liVal;
	}

	/**
	 * @Description: 从htmlparser中获取指定name的input选中值
	 * @author: shusx
	 * @date: 2013-10-8 下午4:18:02
	 * 
	 * @param parser
	 * @param id
	 * @return
	 * @throws ParserException
	 */
	public static String getInputValByName(Parser parser, String id)
			throws ParserException {
		String inputVal = "";

		// 先重置
		parser.reset();

		NodeFilter inputFilter = new TagNameFilter("INPUT");
		NodeFilter hasAttrFilter = new HasAttributeFilter("name", id);
		NodeFilter andFilter = new AndFilter(inputFilter, hasAttrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		InputTag node = (InputTag) nodeList.elementAt(0);

		inputVal = node == null ? "" : node.getAttribute("value");

		return inputVal;
	}

	/**
	 * 
	 * @param parser
	 * @param classes
	 * @return
	 * @throws ParserException
	 */
	public static Node getEmText(Parser parser, String classes)
			throws ParserException {
		// 先重置
		parser.reset();
		NodeFilter attrFilter = new HasAttributeFilter("class", classes);
		NodeList nodeList = parser.extractAllNodesThatMatch(attrFilter);
		Node node = nodeList.elementAt(0);
		return node;
	}

	public static String getLabelTextByClass(Parser parser,String classes)throws ParserException{
		String text="";
		parser.reset();
		NodeFilter attrFilter = new HasAttributeFilter("class", classes);
		NodeList nodeList = parser.extractAllNodesThatMatch(attrFilter);
		Node node = nodeList.elementAt(0);
		text =node.toPlainTextString();
		return text;
	}
	
	/**
	 * 
	 * @param parser
	 * @param classes
	 * @return
	 * @throws ParserException
	 */
	public static String getUlHtml(Parser parser ,String classes,int index)throws ParserException{
		String text="";
		parser.reset();
		NodeFilter attrFilter = new HasAttributeFilter("class", classes);
		NodeList nodeList = parser.extractAllNodesThatMatch(attrFilter);
		Bullet node = (Bullet)nodeList.elementAt(index);
		text =node.getChild(1).toPlainTextString();
		return text;
	}

}
