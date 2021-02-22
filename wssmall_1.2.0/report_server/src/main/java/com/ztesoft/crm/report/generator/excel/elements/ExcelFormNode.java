package com.ztesoft.crm.report.generator.excel.elements;

import com.ztesoft.crm.report.ReportContext;
import com.ztesoft.crm.report.actions.ParameterMap;
import com.ztesoft.crm.report.config.elements.Field;
import com.ztesoft.crm.report.config.elements.Panel;
import com.ztesoft.crm.report.config.elements.Row;
import com.ztesoft.crm.report.lang.Utils;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelFormNode extends ExcelNode
{
  private Panel panel;
  private ParameterMap map;

  public void setPanel(Panel panel)
  {
    this.panel = panel;
  }

  public void setMap(ParameterMap map) {
    this.map = map;
  }

  @Override
protected int export(HSSFWorkbook book, HSSFSheet sheet, int start)
  {
    if ((this.panel == null) || (this.map == null))
      return start;
    for (Iterator rows = this.panel.childs().iterator(); rows.hasNext(); ++start)
    {
      Row r = (Row)rows.next();
      if (Utils.isTrue(r.getButtons()))
        continue;
      HSSFRow row = sheet.createRow(start);
      short i = 0;
      for (Iterator cells = r.childs().iterator(); cells.hasNext(); ) {
        Field f = (Field)cells.next();

        if ("button,submit,reset,excel,chartset".indexOf(f.getType()) >= 0)
          continue;
        String v = this.map.getString(f.getTextName());
        if (Utils.isEmpty(v)) {
          v = f.getValue();
        }
        if (Utils.isEmpty(v)) {
          v = this.map.getString(f.getName());
        }
        HSSFCell cell = null;

        if ((Utils.isTrue(this.panel.getLabelVisiable())) && 
          (!(Utils.isEmpty(f.getLabel()))))
        {
          short tmp190_188 = i; i = (short)(tmp190_188 + 1); cell = row.createCell(tmp190_188);
          HSSFCellStyle style = book.createCellStyle();
          style.setFillPattern((short) 1);

          style.setFillForegroundColor((short) 44);
          //cell.setEncoding(1);
          cell.setCellType(1);
          cell.setCellStyle(style);
          cell.setCellValue(
            Utils.encode(f.getLabel() + this.panel.getLabelSeparator(), 
            ReportContext.getContext().getExcelEncoding()));
        }

        short tmp282_280 = i; i = (short)(tmp282_280 + 1); cell = row.createCell(tmp282_280);
        //cell.setEncoding((short) 1);
        cell.setCellType(1);

        cell.setCellValue(
          Utils.encode(v, 
          ReportContext.getContext().getExcelEncoding()));
      }
    }
    return start;
  }

  @Override
protected void release()
  {
    this.panel = null;
    this.map = null;
  }
}