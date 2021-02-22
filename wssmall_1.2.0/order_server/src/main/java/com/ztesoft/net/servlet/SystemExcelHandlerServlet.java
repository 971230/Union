package com.ztesoft.net.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.app.base.plugin.fileUpload.FileUpload;
import com.ztesoft.net.consts.SysExcelConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.service.IOrderOuterManager;
import com.ztesoft.net.mall.core.service.ISystemExcelHandlerManager;
import com.ztesoft.net.mall.core.service.impl.cache.IGoodsCacheUtil;
import com.ztesoft.net.model.TempInst;
import com.ztesoft.net.model.TplAttr;
import com.ztesoft.net.model.TplContentAttr;
import commons.CommonTools;

/**
 * 扩展属性导入导出处理servlet
 *
 * @author hu.yi
 * @date 2013.9.26
 */
@SuppressWarnings("unchecked")
public class SystemExcelHandlerServlet extends HttpServlet {

    ISystemExcelHandlerManager systemExcelHandlerManager = (ISystemExcelHandlerManager) SpringContextHolder.getBean("systemExcelHandlerManager");

    public SystemExcelHandlerServlet() {
        super();
    }

    @Override
	public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servType = request.getParameter("servType") == null ? "" : request.getParameter("servType");
        if (SysExcelConsts.EXCEL_EXPORT.equals(servType)) {
            exportExcel(response, request);
        } else {
            doGet(request, response);
        }

    }

    // 导出模板单
    public boolean exportExcel(HttpServletResponse response, HttpServletRequest request) {
        OutputStream os = null;
        WritableWorkbook wbook = null;

        //获取商品或订单id，以及来源，根据id及来源寻找模板
        String temp_inst_id = request.getParameter("temp_inst_id") == null ?
                "" : request.getParameter("temp_inst_id");
        String source_from = request.getParameter("source_from") == null ?
                "" : request.getParameter("source_from");

        try {
            TempInst tempInst = getFormatTemp(temp_inst_id, source_from);

            String temp_name = tempInst.getTemp_name();
            String temp_notes = tempInst.getTemp_notes();
            String[] titles = tempInst.getTitles();
            String[] contents = tempInst.getContents();

            String fileName = new String(temp_name.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
            os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型

            wbook = Workbook.createWorkbook(os); // 建立excel文件
            WritableSheet wsheet = wbook.createSheet(temp_name, 0); // sheet名称


            // 设置注释
            WritableFont nfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
            WritableCellFormat ncfFc = new WritableCellFormat(nfont);
            ncfFc.setBackground(jxl.format.Colour.AQUA);
            ncfFc = new WritableCellFormat(nfont);

            // 生成注释
            wsheet.addCell(new Label(0, 0, temp_notes, ncfFc));
            wsheet.setColumnView(0, 23);// 设置宽度为23

            // 设置excel标题
            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(jxl.format.Colour.AQUA);
            wcfFC = new WritableCellFormat(wfont);

            // 开始生成主体内容
            for (int i = 0; i < titles.length; i += 2) {
                wsheet.addCell(new Label(i / 2, 1, titles[i], wcfFC));
                wsheet.setColumnView(i / 2, 23);// 设置宽度为23
            }

            // 设置样板数据
            WritableFont cfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
            WritableCellFormat ccfFC = new WritableCellFormat(cfont);
            ccfFC.setBackground(jxl.format.Colour.AQUA);
            ccfFC = new WritableCellFormat(cfont);

            // 生成样板数据
            for (int i = 0; i < contents.length; i += 2) {
                wsheet.addCell(new Label(i / 2, 2, contents[i], ccfFC));
                wsheet.setColumnView(i / 2, 23);// 设置宽度为23
            }

            // 主体内容生成结束
            wbook.write(); // 写入文件
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (wbook != null) {
                try {
                    wbook.close();
                } catch (WriteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close(); // 关闭流
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 组装格式化的模板实例对象
     *
     * @param temp_inst_id
     * @param source_from
     * @return
     * @throws UnsupportedEncodingException
     */
    private TempInst getFormatTemp(String temp_inst_id, String source_from) throws UnsupportedEncodingException {

        //查询模板对象，进行组装
        TempInst tempInst = systemExcelHandlerManager.getTempInstByArgs(temp_inst_id, source_from);

        if (tempInst == null) {
            throw new RuntimeException("模板不存在，请联系管理员配置模板！");
        }

        String temp_cols = tempInst.getTemp_cols();            //jsonarray 第二列及列名
        String temp_contents = tempInst.getTemp_contents();    //jsonarray	第三列样板数据

        List<TplAttr> colsList = getTplAttrByStr(temp_cols);
        List<TplContentAttr> contentsList = getTplContentAttrByStr(temp_contents);

        String title = "";
        String content = "";
        String dupicate = "";

        if (colsList != null && !colsList.isEmpty()) {
            for (int i = 0; i < colsList.size(); i++) {
                TplAttr tplAttr = colsList.get(i);
                //String c_name =tplAttr.getC_name();
                String e_name = tplAttr.getE_name();
//				String is_dupicate = tplAttr.getIs_dupicate();
                String attr_id = tplAttr.getField_attr_id();
                //String field_name = (String) map.get("field_name");		//扩展属性的字段
//				if(is_dupicate != null && SysExcelConsts.NOT_DUPICATE.equals(is_dupicate)){
//					dupicate += e_name + ",";
//				}
                //TODO attr_id
                if (!StringUtils.isEmpty(attr_id)) {
                    e_name = SysExcelConsts.EXTEND_PARAM_PREFIX + "_" + attr_id + "_" + e_name + "_" + temp_inst_id;
                }
//				title += c_name + "," + e_name + ",";
            }
        }
        if (contentsList != null && !contentsList.isEmpty()) {
            for (int i = 0; i < contentsList.size(); i++) {
                TplContentAttr tplContentAttr = contentsList.get(i);
                String c_name = tplContentAttr.getC_name();
                String e_name = tplContentAttr.getE_name();
                content += c_name + "," + e_name + ",";
            }
        }

        String titles[] = new String[]{};
        String contents[] = new String[]{};
        String dupicates[] = new String[]{};
        if (title.indexOf(",") > -1) {
            title = title.substring(0, title.length() - 1);
            title = new String(title.getBytes("gb2312"), "gbk");
            titles = title.split(",");
        }
        if (content.indexOf(",") > -1) {
            content = content.substring(0, content.length() - 1);
            content = new String(content.getBytes("gb2312"), "gbk");
            contents = content.split(",");
        }
        if (dupicate.indexOf(",") > -1) {
            dupicate = dupicate.substring(0, dupicate.length() - 1);
            dupicate = new String(dupicate.getBytes("gb2312"), "gbk");
            dupicates = dupicate.split(",");
        }

        tempInst.setTitles(titles);
        tempInst.setContents(contents);
        tempInst.setDupicates(dupicates);

        return tempInst;
    }


    /**
     * 将jsonarray格式的数据转为list返回
     *
     * @param str
     * @return
     */
    private List<TplAttr> getTplAttrByStr(String str) {

        return CommonTools.jsonToList(str, TplAttr.class);
    }

    /**
     * 将jsonarray格式的数据转为list返回
     *
     * @param str
     * @return
     */
    private List<TplContentAttr> getTplContentAttrByStr(String str) {

        return CommonTools.jsonToList(str, TplContentAttr.class);
    }


    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String errMsg = "";
        FileUpload fupload = null;
        InputStream is = null;
        String msg = "";
        String flag = "1";
        fupload = new FileUpload();
        BatchResult batchResult = null;
        List inList = null;
        List<Map<String, Object>> prefixList = null;
        String url = request.getParameter("url") == null ? "" : request.getParameter("url");
        String service = request.getParameter("service") == null ? "" : request.getParameter("service");
        //标志批导还是单独流程导入
        String order_type = request.getParameter("order_type") == null ? "" : request.getParameter("order_type");

        //商户id
        String temp_inst_id = request.getParameter("temp_inst_id") == null ? "" : request.getParameter("temp_inst_id");
        String source_from = request.getParameter("source_from") == null ? "" : request.getParameter("source_from");

        IGoodsCacheUtil goodsCacheUtil = (IGoodsCacheUtil) SpringContextHolder.getBean("cacheUtil");

        //暂时只考虑商品的情况，获取商品类型
        Goods goods = goodsCacheUtil.getGoodsById(temp_inst_id);
        GoodsType goodsType = goodsCacheUtil.getGoodsTypeById(goods.getType_id());
        String type = goodsType.getType_code();

        // 上传失败时，直接退出,不进行数据的插入
        try {
            fupload.load(request);
            is = fupload.getIS("uploadFile");

            Map inMap = new HashMap();


            //查询对应模板
            TempInst tempInst = getFormatTemp(temp_inst_id, source_from);

            if (tempInst == null) {
                errMsg = "无对应模板，请检查选择文件！";
            }

            //解析excel文件
            if (errMsg == null || "".equals(errMsg)) {
                inMap = iStreamToArrayListEx(is, tempInst);
            }

            prefixList = (List) inMap.get("prefixList");
            inList = (List) inMap.get("list");
            String inMsg = (String) inMap.get("msg");

            if (inList == null && StringUtils.isNotEmpty(errMsg)) {
                msg = "文件上传失败：" + errMsg;
                flag = "-1";
                forwardJsp(request, response, url, msg, flag);
                try {

                } catch (Exception e) {
                    fupload.fileClose();
                }
                return;
            }
            if ((inList != null && inList.size() == 0) && StringUtils.isEmpty(inMsg)) {

                if (StringUtils.isEmpty(errMsg))
                    errMsg = "导入模板数据无效，请检查！";
                msg = "文件上传失败：" + errMsg;
                flag = "-1";
                forwardJsp(request, response, url, msg, flag);
                try {

                } catch (Exception e) {
                    fupload.fileClose();
                }
                return;
            }

            if (!StringUtils.isEmpty(inMsg)) {
                if (Consts.MSG_NULL.equals(inMsg)) {
                    errMsg = "导入模板中存在空数据，请检查！";
                } else if (Consts.MSG_DUPICATE.equals(inMsg)) {
                    errMsg = "导入模板中存在重复数据，请检查！";
                } else if (Consts.MSG_WRONG_EXCEL.equals(inMsg)) {
                    errMsg = "导入模板有误，请下载模板后重新检查！";
                } else if (Consts.MSG_WRONG_GOOD.equals(inMsg)) {
                    errMsg = "导入商品与所选商品面值不匹配，请重新选择！";
                }

                msg = "文件上传失败：" + errMsg;
                flag = "-1";
                forwardJsp(request, response, url, msg, flag);
                try {

                } catch (Exception e) {
                    fupload.fileClose();
                }
                return;
            }

            //验证导入数据中是否存在数据库已存在记录
            int count = 0;
            IOrderOuterManager orderOuterManager = (IOrderOuterManager) SpringContextHolder.getBean("orderOuterManager");
            count = orderOuterManager.isExistsOrder(inList);

            if (count > 0) {
                errMsg = "已存在" + count + "条记录，请检查后重新导入！";

                msg = "文件上传失败：" + errMsg;
                flag = "-1";
                forwardJsp(request, response, url, msg, flag);
                try {

                } catch (Exception e) {
                    fupload.fileClose();
                }
                return;
            }

            int oneBatch = 2000;
            if (!inList.isEmpty() && inList.size() > oneBatch) {

                msg = "单次导入超出 配置的最大限度" + oneBatch + "条，请减少上传数量,上传失败。";
                flag = "-1";
                forwardJsp(request, response, url, msg, flag);
                try {

                } catch (Exception e) {
                    fupload.fileClose();
                }
                return;
            }

            //如果是合约机订单导入，调用批量受理的方法，order_type为action直接传入，与inMap.get("order_type")不同源
//			if(!StringUtil.isEmpty(order_type) && Consts.BATCH_ORDER_CONTRACT.equals(order_type)){
//				batchResult = orderOuterManager.importOrd(inList,type,Consts.BATCH_CONTRACT_FLOW);
//			}else{
//				batchResult = orderOuterManager.importOrd(inList,type,Consts.BATCH_CONTRACT_NOT_FLOW);
//			}

            request.setAttribute("batchId", batchResult.getBatchId());
            request.setAttribute("sucNum", batchResult.getSucNum());
            request.setAttribute("failNum", batchResult.getFailNum());
            request.setAttribute("errMsg", batchResult.getErr_msg());

            boolean isHavingMsg = true;
            if (!StringUtils.isEmpty(batchResult.getErr_msg())) {
                isHavingMsg = false;
            }

            if (isHavingMsg) {
                msg = "本次成功导入" + tempInst.getTemp_name() + ":" + batchResult.getSucNum() + "条，失败：" + batchResult.getFailNum() + "条";
            } else {
                msg = "导入" + tempInst.getTemp_name() + "失败：" + batchResult.getErr_msg();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            msg = "文件上传失败：" + ex.getCause() + ex.getMessage();
            flag = "-1";
            forwardJsp(request, response, url, msg, flag);
            try {

            } catch (Exception e) {
                fupload.fileClose();
                e.printStackTrace();
            }
            return;

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            forwardJsp(request, response, url, msg, flag);
        }
    }

    /**
     * 跳转到导入界面
     *
     * @param request
     * @param response
     * @param url
     * @param msg
     * @param flag
     * @throws ServletException
     * @throws IOException
     */
    private void forwardJsp(HttpServletRequest request, HttpServletResponse response, String url, String msg,
                            String flag) throws ServletException, IOException {
        // msg编码
        request.setAttribute("msg", msg);
        request.setAttribute("flag", flag);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }

    /**
     * 将导入数据转为List数据
     *
     * @param is 输入流
     * @return
     * @throws Exception
     */
    private Map<String, Object> iStreamToArrayListEx(InputStream is, TempInst tempInst) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();            //存放原属性的list
        List<Map<String, Object>> prefixList = new ArrayList<Map<String, Object>>();        //存放扩展属性的list
        jxl.Workbook workBook = null;
        String errMsg = "";

        try {
            workBook = jxl.Workbook.getWorkbook(is);
        } catch (BiffException e) {
            errMsg = "当前导入Excel版本过高，请先将Excel文件另存为2003版本。";
            map.put("msg", errMsg);
            return map;
        }

        Sheet sheet = workBook.getSheet(0);
        int rowsNum = sheet.getRows();
        int colsNum = sheet.getColumns();
        String colName = "";
        String col = "";
        String value = "";
        String msg = "";
        String order_type = "";


        //验证模板是否正确
        String[] cols = tempInst.getTitles();
        if (cols.length / 2 == colsNum) {
            for (int i = 0; i < colsNum; i++) {
                String colName_1 = sheet.getCell(i, 1).getContents().trim();
                String colName_2 = cols[i * 2].trim();
                if (!colName_1.equals(colName_2)) {
                    msg = Consts.MSG_WRONG_EXCEL;
                }
            }
        } else {
            msg = Consts.MSG_WRONG_EXCEL;
        }

        //如果模板正确，进行下一步验证
        if ("".equals(msg)) {
            for (int i = 2; i < rowsNum; i++) {    // 一二行固定
                Map<String, Object> mapCol = new HashMap<String, Object>();
                Map<String, Object> prefixMap = new HashMap<String, Object>();
                for (int j = 0; j < colsNum; j++) { // 列,获取excel每行数据并放入map对象中
                    colName = sheet.getCell(j, 1).getContents().trim();

                    value = sheet.getCell(j, i).getContents().trim();

                    col = transeCol(colName, cols);

                    //如果是注释列跟空出列，不进行空验证
                    if (!"".equals(col)) {
                        if (StringUtils.isEmpty(value)) {
                            msg = Consts.MSG_NULL;
                        }
                    }
                    mapCol.put(col, value);
                    if (col.indexOf(SysExcelConsts.EXTEND_PARAM_PREFIX) > -1) {
                        prefixMap.put(col, value);
                    }
                }

                //如果某一行全部为空，取消空验证，并且不增加此行
                //如果此行为模板数据行，不插入数据
                boolean isMould = false;

                Collection c = mapCol.values();
                Iterator it = c.iterator();
                int j = 0;
                while (it.hasNext()) {
                    String com = (String) it.next();
                    if ("".equals(com)) {
                        j++;
                    }
                    if (com.indexOf("(样板数据)") > -1) {
                        isMould = true;
                    }
                }
                if (mapCol.size() == j) {
                    msg = "";
                } else {
                    if (!isMould) {
                        aList.add(mapCol);
                        prefixList.add(prefixMap);
                    }
                }

            }

            //判断是否有重复字段
            String[] compare = tempInst.getDupicates();
            for (int i = 0; i < compare.length; i++) {
                List conList = new ArrayList();
                String compareName = compare[i];
                for (int j = 0; j < aList.size(); j++) {
                    Map aMap = aList.get(j);
                    String conVal = (String) aMap.get(compareName);
                    conList.add(conVal);
                }
                Set set = new HashSet(conList);
                if (set.size() != conList.size()) {
                    msg = Consts.MSG_DUPICATE;
                    break;
                }
            }
        }

        map.put("prefixList", prefixList);
        map.put("list", aList);
        map.put("msg", msg);
        map.put("order_type", order_type);

        return map;
    }


    /**
     * 列名称转换
     *
     * @param colName
     * @return
     */
    private String transeCol(String colName, String[] cols) {
        String col = "";
        for (int i = 0; i < cols.length; i += 2) {
            if (cols[i].equals(colName)) {
                col = cols[i + 1];
            }
        }
        return col;
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occure
     */
    @Override
	public void init() throws ServletException {
        // Put your code here
    }


}
