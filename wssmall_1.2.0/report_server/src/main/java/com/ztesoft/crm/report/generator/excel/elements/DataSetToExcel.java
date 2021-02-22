package com.ztesoft.crm.report.generator.excel.elements;

import com.ztesoft.crm.report.ReportContext;
import com.ztesoft.crm.report.db.data_new.Cell;
import com.ztesoft.crm.report.db.data_new.CrosstabDataSet;
import com.ztesoft.crm.report.db.data_new.DataFormat;
import com.ztesoft.crm.report.db.data_new.DataSet;
import com.ztesoft.crm.report.db.data_new.Key;
import com.ztesoft.crm.report.db.data_new.ListtabDataSet;
import com.ztesoft.crm.report.lang.ArrayListEx;
import com.ztesoft.crm.report.lang.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

class DataSetToExcel
{
	private static Logger logger = Logger.getLogger(DataSetToExcel.class);
  private DataSet ds;
  private HSSFSheet sheet;
  private int start;
  private boolean isNoRowHeader = false;

  private ArrayListEx xLeafHeaders = new ArrayListEx();
  private HSSFWorkbook book;
  private String columnAlign;

  DataSetToExcel(HSSFWorkbook book, HSSFSheet sheet, String columnAlign)
  {
    this.sheet = sheet;
    this.book = book;
    this.columnAlign = columnAlign;
  }

  private short getLastCellNum(HSSFRow row)
  {
    short s = row.getLastCellNum();
    if(s==-1){
    	return 0;
    }
    else
    	return s;
  }

  private String getAlign(Key k) {
    return ((Utils.isEmpty(k.getAlign())) ? this.columnAlign : k.getAlign());
  }

  private void createBody(List headers) {
    HashMap map = new HashMap();
    int maxSpan = (this.ds.getY() == null) ? 0 : this.ds.getY().getMaxSpan();
    if (maxSpan > 0) {
      for (int i = 0; i < headers.size(); ++i) {
        Key k = (Key)headers.get(i);
        k.setColSpan(maxSpan - k.getLevel() + 1);
        sliceY(k, 1, map);
      }

    }

    Key xk = null; Key yk = null;

    for (int i = 0; i < this.ds.getRows(); this.start += 1)
    {
      int cols = 0;
      HSSFRow row = this.sheet.getRow(this.start);
      if (row == null) {
        row = this.sheet.createRow(this.start);
      }
      for (Iterator objs = getYHeaders(map, maxSpan, i).iterator(); objs
        .hasNext(); )
      {
        Key k = (Key)objs.next();
        cols = getLastCellNum(row);

        setCell(k.getText(), this.start, cols, k.getRowSpan(), 
          k.getColSpan(), true, getAlign(k));
        yk = k;
      }
      cols = getLastCellNum(row);

      for (Iterator objs = this.xLeafHeaders.iterator(); objs.hasNext(); ++cols) {
        xk = (Key)objs.next();

        Cell v = (this.ds instanceof CrosstabDataSet) ? this.ds.get(xk, yk) : this.ds.get(xk, new Key(String.valueOf(i)));
        String str = (v == null) ? "" : v.getExcelHtml(this.ds instanceof ListtabDataSet);
        str = createFormat(yk).format(str);
        str = createFormat(xk).format(str);

        setCell(str, this.start, cols, 1, 1, false, getAlign(xk));
      }
      ++i;
    }
  }

  private DataFormat createFormat(Key k)
  {
    if (k == null)
      return new DataFormat(null, null);
    return new DataFormat(k.getDataType(), k.getFormat());
  }

  public static void main(String[] args) throws FileNotFoundException, IOException
  {
    HSSFWorkbook book = new HSSFWorkbook();
    HSSFSheet sheet = book.createSheet();

    sheet.createRow(0).createCell(0);
  }

  private void createHead(List headers)
  {
    HashMap map = new HashMap();

    for (int i = 0; i < headers.size(); ++i) {
      Key k = (Key)headers.get(i);
      k.setRowSpan(this.ds.getX().getMaxSpan() - k.getLevel() + 1);
      sliceX(k, 1, map);
    }

    if (this.ds.getMetric() != null) {
      String text = (this.ds.getMetric().getValue() != null) ? 
        this.ds.getMetric().getValue().getText() : this.ds.getMetric().getText();
      int colspan = (this.ds.getY() == null) ? 0 : this.ds.getY().getMaxSpan();
      if (colspan > 0) {
        setCell(text, this.start, 0, this.ds.getX().getMaxSpan(), colspan, true, 
          getAlign(new Key(this.ds.getMetric())));
      }
    }

    for (int i = 1; i <= this.ds.getX().getMaxSpan(); this.start += 1) {
      ArrayListEx arr = (ArrayListEx)map.get(String.valueOf(i));
      int cols = 0;
      HSSFRow row = this.sheet.getRow(this.start);
      if (row == null) {
        row = this.sheet.createRow(this.start);
      }
      for (Iterator objs = arr.iterator(); objs.hasNext(); ) {
        Key k = (Key)objs.next();
        cols = getLastCellNum(row);
        setCell(k.getText(), this.start, cols, k.getRowSpan(), k.getColSpan(), true, getAlign(k));
        cols += k.getColSpan();
      }
      ++i;
    }
  }

  private void setCell(String value, int rows, int cols, int rowspan, int colspan, boolean bg, String align)
  {
    HSSFCellStyle style = this.book.createCellStyle();
    style.setVerticalAlignment((short) 1);

    style.setAlignment((short) 1);
    if ("center".equals(align))
      style.setAlignment((short) 2);
    if ("right".equals(align))
      style.setAlignment((short) 3);
    style.setBorderBottom((short) 1);
    style.setBorderRight((short) 1);
    style.setBorderLeft((short) 1);
    style.setBorderTop((short) 1);
    style.setLeftBorderColor((short) 56);
    style.setRightBorderColor((short) 56);
    style.setBottomBorderColor((short) 56);
    style.setTopBorderColor((short) 56);
    if (bg) {
      style.setFillPattern((short) 1);

      style.setFillForegroundColor((short) 44);
    }

    for (int i = 0; i < rowspan; ++i) {
      HSSFRow row = this.sheet.getRow(rows + i);
      if (row == null)
        row = this.sheet.createRow(rows + i);
      int n = getLastCellNum(row);
      for (; n < cols + colspan; ++n)
      {
        row.createCell((short)n);
      }
      if (i == 0) {
        HSSFCell cell = row.getCell((short)cols);
        if(cell!=null){
        	//cell.setEncoding((short) 1);
        	cell.setCellStyle(style);
            cell.setCellType(1);
            cell.setCellValue(
                    Utils.encode(value, 
                    ReportContext.getContext().getExcelEncoding()));
        }
        else{
        	logger.info("");
        }
        
      }

    }

    if (colspan * rowspan == 1)
      return;
    Region r = new Region((short)rows, (short)cols, 
      (short)(rows + 
      rowspan - 1), (short)(cols + colspan - 1));
    this.sheet.addMergedRegion(r);
    setRegion(r, style);
  }

  private void setRegion(Region r, HSSFCellStyle style)
  {
    for (int i = r.getRowFrom(); i < r.getRowTo(); ++i) {
      HSSFRow row = this.sheet.getRow(i);
      for (int n = r.getColumnFrom(); n < r.getColumnTo(); ++n)
        row.getCell((short)n).setCellStyle(style);
    }
  }

  private List getYHeaders(HashMap map, int cells, int rowIndex)
  {
    ArrayListEx arr = new ArrayListEx();
    for (int i = 1; i <= cells; ++i) {
      ArrayListEx al = (ArrayListEx)map.get(String.valueOf(i));
      int startRows = 0;
      if (al == null)
        continue;
      for (Iterator objs = al.iterator(); objs.hasNext(); ) {
        Key key = (Key)objs.next();
        if (rowIndex == startRows) {
          arr.add(key);
          break;
        }
        startRows += key.getRowSpan();
      }
    }
    return arr;
  }

  private void sliceX(Key key, int startLevel, HashMap map) {
    ArrayListEx arr = (ArrayListEx)map.get(String.valueOf(startLevel));
    if (arr == null) {
      arr = new ArrayListEx();
      map.put(String.valueOf(startLevel), arr);
    }
    arr.add(key);

    if (key.isLeaf())
    {
      this.xLeafHeaders.add(key);
    }
    for (Iterator objs = key.children().iterator(); objs.hasNext(); ) {
      Key k = (Key)objs.next();
      sliceX(k, startLevel + key.getRowSpan(), map);
    }
  }

  private void sliceY(Key key, int startLevel, HashMap map) {
    ArrayListEx arr = (ArrayListEx)map.get(String.valueOf(startLevel));
    if (arr == null) {
      arr = new ArrayListEx();
      map.put(String.valueOf(startLevel), arr);
    }
    arr.add(key);
    for (Iterator objs = key.children().iterator(); objs.hasNext(); ) {
      Key k = (Key)objs.next();
      sliceY(k, startLevel + key.getColSpan(), map);
    }
  }

  int convert(DataSet ds, int rows) {
    this.start = rows;
    this.ds = ds;
    this.isNoRowHeader = (ds.getY() == null);
    this.xLeafHeaders.clear();

    createHead(ds.getX().getHeaders());
    createBody((this.isNoRowHeader) ? new ArrayListEx() : ds.getY().getHeaders());
    this.ds = null;
    this.sheet = null;
    this.book = null;
    this.xLeafHeaders.clear();
    this.xLeafHeaders = null;
    return this.start;
  }
}