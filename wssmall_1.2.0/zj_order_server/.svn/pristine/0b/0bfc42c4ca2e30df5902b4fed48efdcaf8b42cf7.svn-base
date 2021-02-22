package com.ztesoft.net.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MakeExcel {
    private static WritableCellFormat titleFormat = null;
    private static WritableCellFormat bodyFormat = null;
    private static WritableCellFormat noteFormat = null;
    private static WritableCellFormat floatFormat = null;
    private static WritableCellFormat intFormat = null;
    private static boolean init = false;

    public MakeExcel() {
    }

    public void init() throws WriteException {
        WritableFont font1, font2, font3, font4;
        font1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD,
                false);
        titleFormat = new WritableCellFormat(font1);
        titleFormat.setBackground(Colour.ORANGE);
        titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        titleFormat.setAlignment(Alignment.CENTRE);
        font2 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD,
                false);
        noteFormat = new WritableCellFormat(font2);
        noteFormat.setBackground(Colour.ORANGE);
        noteFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        noteFormat.setAlignment(Alignment.CENTRE);
        noteFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        noteFormat.setWrap(true);
        font3 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD,
                false);
        bodyFormat = new WritableCellFormat(font3);
        bodyFormat.setBackground(Colour.LIGHT_GREEN);
        bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        font4 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD,
                false);
        floatFormat = new WritableCellFormat(font4, NumberFormats.FLOAT);
        floatFormat.setBackground(Colour.LIGHT_GREEN);
        floatFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        // Arial���壬9�ţ��Ǵ��壬��Ԫ����ɫ�����ֱ߿�
        font4 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD,
                false);
        intFormat = new WritableCellFormat(font4, NumberFormats.INTEGER);
        intFormat.setBackground(Colour.LIGHT_GREEN);
        intFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        init = true;
    }
//入参是一个list,一条数据存一个map对象，map对象中存列和值得对应关系，destFile当然就是要存的文件信息。  headList很重要，它是列的展示，当然和数据的列要对应不然找不到对应的地方存储。
    public static void CreateExcelFile(List<Map<String,Object>> list, File destFile,List<String> headList,String message)
            throws WriteException, IOException {
        int sizeAll=list.size();
        int zheng=sizeAll/65534;
        int yu=sizeAll%65534;
        int sheetSize=1;
        int flagList=1;
        if(sizeAll<=65534){
            sheetSize=1;
        }else{
            if(yu>0){
                sheetSize=zheng+1;
            }else{
                sheetSize=zheng;
            }
        }
        if (init == false)
        new MakeExcel().init();
        WritableWorkbook book = null;
        book = Workbook.createWorkbook(destFile);
        if(list.size()==0){
            book.write();
            }else{
            
                
                
                for(int j=0;j<sheetSize;j++){
                    int index;
                    System.out.println("*************************sheet"+j+"***************************");
                    WritableSheet sheet = book.createSheet(destFile.getName().replace(".xls", "")+j, j);
                    //WritableFont BoldFont=new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                    WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
            		        UnderlineStyle.NO_UNDERLINE, Colour.RED);
                    WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
                    wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
                    wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
                    wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
                    wcf_center.setWrap(false); // 文字是否换行
                    
                        for(int i=0;i<headList.size()+1;i++){
                            sheet.setColumnView(i, 60);
                        }
                        //sheet.mergeCells(0, 0, headList.size()-1, 0);
                        //sheet.addCell(new Label(0, 0, message, wcf_center));
                        
                        index = 0;
                        for(String  name :headList){
                            sheet.addCell(new Label(index, 0, name,wcf_center));
                            index++;
                        }
                        int i=0;
                        int t=1;
                            while(flagList<=list.size()){
                                index = 0;
                            if(i<65534){
                            for(String  name :headList){
                                sheet.addCell(new Label(index, t, (list.get(flagList-1).get(name)+"").replace("null", "")));
                                index++;
                            }
                            i++;
                            t++;
                            flagList++;
                            }else{
                                    break;
                                }
                            }
                }
                
                
                
            }
        book.write();    
        if (book != null)
            book.close();
    }
    public static void main(String[] args) {
        List<Map<String, Object>> list=new ArrayList();
        for(int i=0;i<195534;i++){
            Map<String, Object> map=new HashMap();
            map.put("a","a"+i );
            map.put("b","b"+i  );
            map.put("c","c"+i  );
            map.put("d","d"+i  );
            list.add(map);
        }
        List<String> ll=new ArrayList();
        ll.add("a");
        ll.add("b");
        ll.add("d");
        ll.add("c");
        try {
            CreateExcelFile(list, new File("d:/a.xls"),ll,"");
        } catch (WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
//            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    }