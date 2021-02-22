package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
public class IfTag extends BodyTagSupport {
	private static Logger logger = Logger.getLogger(IfTag.class);
	private String  m_test     = null ;//数据条目

	public IfTag() {
		super();
		init();
	}
	public void init() {
		m_test   = null;
	}
	@Override
	public void release() {
		init();
	}
	//值
    public void setTest(String s_test) {
		this.m_test = s_test;
    }
	@Override
	public int doStartTag() throws JspException {
		boolean bFlag = true;


		bFlag = parseExprE(pageContext,m_test);
		if ( bFlag ) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	//条件(  ( ${a.id}==${b.id} && 1>2  )
    public void setItem(String s_test) {
		this.m_test = s_test.trim();
    }
	public static String getExprValue(PageContext pg,String s_expr){
		int    iPos1      = 0;
		int    iPos2      = 0;
		Object obj        = null;
		if(s_expr==null || s_expr.trim().equals("")) return null;
		//需要从request的属性中取值
		if(s_expr.startsWith("$")){
			s_expr = s_expr.substring(1);
			iPos1  = s_expr.indexOf("{");
			iPos2  = s_expr.indexOf("}");

			if(iPos1<0 || iPos2<0 || iPos2<=iPos1  ){
				logger.info("表达式错误,格式应该为-->${a.id} "
				+"iPos1="+iPos1 + " iPos2="+iPos2 + " " + s_expr);
				return null;
			}
			s_expr = s_expr.substring(iPos1+1,iPos2);
			s_expr = s_expr.trim();
			obj    = TagFunc.getObject(pg,s_expr,null,null);
			if(obj==null || !obj.getClass().getName().equals("java.lang.String")) return null;
			return (String)obj;
		}
		//本身为字符串
		if(s_expr.startsWith("'")){
			iPos1  = 0;
			iPos2  = s_expr.lastIndexOf("'");
			if(iPos1<0 || iPos2<0 || iPos2<=iPos1  ){
				logger.info("表达式错误,格式应该为-->'{a.id' ");
				return null;
			}
			return s_expr.substring(iPos1+1,iPos2);
		}
		if(s_expr.startsWith("\"")){
			iPos1  = 0 ;
			iPos2  = s_expr.lastIndexOf("\"");
			if(iPos1<0 || iPos2<0 || iPos2<=iPos1  ){
				logger.info("表达式错误,格式应该为-->\"{a.id}\" ");
				return null;
			}
			return s_expr.substring(iPos1+1,iPos2);
		}
		return s_expr;
	}
	public static boolean  parseExpr(PageContext pg,String s_expr){
		String s_cur_expr= "";
		String s_remain  = "";
		int    iPos      = 0;
		boolean bFlag    = true;
		boolean bIsChar  = true;
		String  s_link   = "";
		String  s_logic  = "";
		String  s_prev   = "";
		String  s_last   = "";

		s_remain= s_expr;

		s_link  = " && ";
		iPos    = s_remain.indexOf(s_link);
		if(iPos<0){
			s_link  = " || ";
			iPos    = s_remain.indexOf(s_link);
		}
		if(iPos<0){
			s_link  = "&&";
			iPos    = s_remain.indexOf(s_link);
		}
		if(iPos<0){
			s_link  = "||";
			iPos    = s_remain.indexOf(s_link);
		}

		if(iPos<0){
			s_cur_expr = s_remain;
			s_remain   = "";
		}else{
			s_cur_expr = s_remain.substring(0,iPos);
			s_remain   = s_remain.substring(iPos+s_link.length());
		}
		s_cur_expr = s_cur_expr.trim();


		s_logic = "==";
		iPos    = s_cur_expr.indexOf(s_logic);
		if(iPos<0){
			s_logic  = "!=";
			iPos    = s_cur_expr.indexOf(s_logic);
		}
		if(iPos<0){
			s_logic  = ">=";
			iPos    = s_cur_expr.indexOf(s_logic);
		}
		if(iPos<0){
			s_logic  = "<=";
			iPos    = s_cur_expr.indexOf(s_logic);
		}
		if(iPos<0){
			s_logic  = ">";
			iPos    = s_cur_expr.indexOf(s_logic);
		}
		if(iPos<0){
			s_logic  = "<";
			iPos    = s_cur_expr.indexOf(s_logic);
		}
		if(iPos<0){
			logger.info("表达式错误,没有比较符 "+s_cur_expr + " -->" + s_logic);
			return false;
		}

		s_prev = s_cur_expr.substring(0,iPos);
		s_last = s_cur_expr.substring(iPos+s_logic.length());

		s_prev = s_prev.trim();
		s_last = s_last.trim();

		//字符类型

		bIsChar = false;
		if(s_prev.startsWith("'" )) bIsChar = true;
		if(s_prev.startsWith("\"")) bIsChar = true;
		if(s_last.startsWith("'" )) bIsChar = true;
		if(s_last.startsWith("\"")) bIsChar = true;
		if(s_last.startsWith("$" )) bIsChar = true;

		s_prev = getExprValue(pg,s_prev);
		s_last = getExprValue(pg,s_last);

		if(s_prev==null || s_last==null) return false;


			double d1=0;
			double d2=0;
			try{
				d1 = Double.parseDouble(s_prev);
				d2 = Double.parseDouble(s_last);

				if(s_logic.equals("==")) bFlag = d1==d2;
				if(s_logic.equals("!=")) bFlag = d1!=d2;
				if(s_logic.equals(">=")) bFlag = d1>=d2;
				if(s_logic.equals(">"))  bFlag = d1> d2;
				if(s_logic.equals("<"))  bFlag = d1< d2;
				if(s_logic.equals("<=")) bFlag = d1<=d2;
			}catch(Exception e){
				if(s_logic.equals("==")) bFlag = s_prev.equals(s_last);
				if(s_logic.equals("!=")) bFlag = !s_prev.equals(s_last);
			}
		if(s_remain.equals("")) return bFlag;

		if(s_link.trim().equals("&&")){
			if(!bFlag) return  false;
			return parseExpr(pg,s_remain);
		}
		if(bFlag) return  true;

		return parseExpr(pg,s_remain);
	}

	public static boolean parseExprE(PageContext pg,String s_expr){


		String s_bracket = "";
		String s_remain  = "";
		String s_prev    = "";

		boolean bFlag    = true;
		boolean bPrev    = true;
		boolean bBracket = true;
		boolean bRemain  = true;

		int    iPos1     = 0;
		int    iPos2     = 0;

		s_expr   = s_expr.trim();
		if(s_expr.equals("")) return true;

		s_remain = s_expr;
		iPos1    = s_remain.indexOf("(");
		iPos2    = s_remain.indexOf(")");

		if(iPos1>iPos2 && iPos1 >-1) {
			logger.info("表达式错误,括号有嵌套");
			return false;
		}
		if((iPos2 * iPos1<0) ) {
			logger.info("表达式错误,括号没有配对");
			return false;
		}

		if(iPos1<0){
			s_bracket = s_remain;
			s_remain  = "";
		}else{
			s_prev    = s_remain.substring(0       , iPos1);
			s_bracket = s_remain.substring(iPos1+1 , iPos2);
			s_remain  = s_remain.substring(iPos2+1 );
		}

		s_prev = s_prev.trim();
		if(!s_prev.equals("")){
			if(!s_prev.endsWith("&&") && !s_prev.endsWith("||")){
				logger.info("表达式错误,表达式不完整");
				return false;
			}
			if(!s_prev.equals("&&") && !s_prev.equals("||")) {
				bFlag = parseExpr(pg,s_prev);
				if(s_prev.endsWith("||")){
					if(bFlag) return true;
				}
				if(s_prev.endsWith("&&")){
					if(!bFlag) return false;
				}
			}
		}
		s_bracket = s_bracket.trim();
		if(!s_bracket.equals("")){
			bFlag = parseExpr(pg,s_bracket);
			if(s_bracket.endsWith("||")){
				if(bFlag) return true;
			}
			if(s_bracket.endsWith("&&")){
				if(!bFlag) return false;
			}
		}
		s_remain= s_remain.trim();
		if(s_remain.startsWith("||")){
			s_remain = s_remain.substring(2);
			return bFlag || parseExprE(pg,s_remain);
		}
		if(s_remain.startsWith("&&")) s_remain = s_remain.substring(2);
		return bFlag && parseExprE(pg,s_remain);
	}
}
