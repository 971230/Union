package com.powerise.ibss.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.ibss.common.dao.DAOUtils;

public class ExportExcelServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ExportExcelServlet.class);
	/**
     * Constructor of the object.
     */
    public ExportExcelServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    @Override
	public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//      设置字符集(显示中文)
        request.setCharacterEncoding("GBK");
        Connection conn = null;
        
        String startDate = request.getParameter("startDate");
        String finishDate = request.getParameter("finishDate");
        Map usermap = (Map)request.getSession().getAttribute("user");
        String userNo = "18900000000";
        if(usermap != null){
        	userNo = (String)usermap.get("USER_NO");
        }
        //logger.info("userNouserNouserNouserNouserNo:  " +userNo);
        logger.info("userNouserNouserNouserNouserNo:  " +userNo);
        String[] headers = getHeaders();
        String[] columns = getColumns();
        String sql = getSQL();

        try {
            // 文件原名称
        	List<Map<String,String>>  pm = new ArrayList<Map<String,String>>();// service.qryRcEntityCount(lanId, countType, sheetType, date);
//            conn = DBUtil.getConnection();
            pm = SqlMapExe.getInstance().execForMapList(sql, new String[] { startDate, finishDate,
                    userNo, startDate, finishDate, userNo, startDate, finishDate, userNo, startDate, finishDate, userNo });
            if (pm != null) {
				response.reset();
				response.setContentType("application/x-msdownload");
				response.addHeader("Content-Disposition",
						"attachment; filename=\"" + "orderList.xls\"");

				ImportExcelUtil util = new ImportExcelUtil();
				util.exportExcel("sheet1", "", headers, columns, pm, response
						.getOutputStream());

			}
        } catch (Exception e) {
            e.printStackTrace();

            try {
                response.setContentType("text/html;charset=gb2312");

                PrintWriter out;
                out = response.getWriter();
                out.println("<script>alert('系统异常错误');</script>");
                out.flush();
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
        	DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.print("    This is ");
        out.print(this.getClass());
        out.println(", using the POST method");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    @Override
	public void init() throws ServletException {
        // Put your code here
    }

    public String[] getColumns() {

        return new String[] { "pre_ord_no", "serv_name", "accept_time", "deal_name" };

    }

    public String[] getHeaders() {

        return new String[] { "订单号", "业务名", "受理日期", "处理状态" };

    }
    
    public String getSQL(){
        String sql =  "select PRE_ORD_NO, TO_CHAR2(ACCEPT_TIME, 'YYYY-MM-DD hh24:mi:ss') ACCEPT_TIME, SERV_NAME, DEAL_NAME"
                + "  from (SELECT A.PRE_ORD_NO, A.ACCEPT_TIME,  B.SERV_NAME,"
                + "               (select d.attr_value_name from attr_spec c, attr_value d where c.attr_code = 'TACHE_NAME' and c.attr_id = d.attr_id and A.deal_flag = d.attr_value) DEAL_NAME"
                + "          FROM TWB_PRE_ASK A, TWB_SERV B"
                + "         WHERE A.SERV_NO = B.SERV_NO"
                + "           AND B.STATE = '1'"
                + "           and A.ACCEPT_TIME >= to_date(? || '00:00:00', 'yyyy-mm-dd hh24:mi:ss')"
                + "           AND A.ACCEPT_TIME < to_date(? || ' 23:59:59', 'yyyy-mm-dd hh24:mi:ss')"
                + "           AND A.USER_NO = ?"
                + "        UNION"
                + "        SELECT A.PRE_ORD_NO, A.ACCEPT_TIME, B.SERV_NAME,"
                + "               (select d.attr_value_name from attr_spec c, attr_value d where c.attr_code = 'TACHE_NAME' and c.attr_id = d.attr_id and A.deal_flag = d.attr_value) DEAL_NAME"
                + "          FROM TWB_PRE_ASK_HIS A, TWB_SERV B"
                + "         WHERE A.SERV_NO = B.SERV_NO"
                + "           AND B.STATE = '1'"
                + "           AND A.ACCEPT_TIME >= to_date(? || ' 00:00:00', 'yyyy-mm-dd hh24:mi:ss')"
                + "           AND A.ACCEPT_TIME < to_date(? || ' 23:59:59', 'yyyy-mm-dd hh24:mi:ss')"
                + "           AND A.USER_NO = ?"
                + "        union"
                + "        SELECT a.cust_ord_no PRE_ORD_NO, b.oper_date ACCEPT_TIME,"
                + "               '商城购物 ' || a.goods_name SERV_NAME, "
                + "               (select d.attr_value_name from attr_spec c, attr_value d where c.attr_code = 'ORDER_STATE' and c.attr_id = d.attr_id and b.state = d.attr_value) DEAL_NAME"
                + "          from TWB_CUST_ORD_DETAIL a, TWB_CUST_ORD b"
                + "         where b.oper_date >= to_date(? || ' 00:00:00', 'yyyy-mm-dd hh24:mi:ss')"
                + "           AND b.oper_date < to_date(? || ' 23:59:59', 'yyyy-mm-dd hh24:mi:ss')"
                + "           and b.sequ = 0"
                + "           and a.cust_ord_no = b.cust_ord_no"
                + "           and b.rela_tel = ?"
                + "        union"
                + "        select a.order_code PRE_ORD_NO, a.state_date ACCEPT_TIME, b.action_name SERV_NAME,"
                + "               (select f.attr_value_name from attr_spec e, attr_value f where e.attr_code = 'ORDER_STATE' and e.attr_id = f.attr_id and a.order_state = f.attr_value) DEAL_NAME"
                + "          from twb_action_order a, twb_action b"
                + "         where a.action_id = b.action_id"
                + "           and b.action_id = a.action_id"
                + "           and a.sequ = '0'"
                + "           and a.state_date >= to_date(? || ' 00:00:00', 'YYYY-MM-DD HH24:MI:SS')"
                + "           and a.state_date <= to_date(? || ' 23:59:59', 'YYYY-MM-DD HH24:MI:SS')"
                + "           and a.user_phone_no = ?)" + " order by ACCEPT_TIME desc";
        return sql;
    }
}
