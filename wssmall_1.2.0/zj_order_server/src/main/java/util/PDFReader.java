package util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.AcroFields.Item;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * https://sourceforge.net/projects/itext/files/
 * http://zhuchengzzcc.iteye.com/blog/1603671
 * @author shusx
 * 
 */
public class PDFReader {
	private static Logger logger = Logger.getLogger(PDFReader.class);
	public static String URL = "";
	public final static String FILE_URL_HK = "ecs_ord/order/pdf/HK.pdf";
	public final static String FILE_URL_HYJ = "ecs_ord/order/pdf/HYJ.pdf";
	public final static String FILE_URL_SWK = "ecs_ord/order/pdf/SWK.pdf";
	
	static {
		URL = Thread
				.currentThread()
				.getContextClassLoader()
				.getResource(
						PDFReader.class.getPackage().getName()
								.replace(".", "/")).toString();
		URL = URL.substring(URL.indexOf("file:/") + "file:/".length(),
				URL.indexOf("WEB-INF/"));
	}
	
	/**
	 * 传入受理单模版路径及需要填充的数据，返回pdf的字节数组
	 * @param model
	 * @param data_map
	 * @return
	 */
	public static byte[] editPdfTemplate(String file_url, Map<String, String> data_map) {
		String file_path = URL + file_url;
		try {
			file_path = URLDecoder.decode(file_path,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		PdfReader reader = null;
		PdfStamper ps = null;
		ByteArrayOutputStream bos = null;
		byte[] pdfByte = null;
		
		try {
			reader = new PdfReader(file_path); // 模版文件目录
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		try {
			bos = new ByteArrayOutputStream();
			ps = new PdfStamper(reader, bos);
			
		    AcroFields s = ps.getAcroFields();  
		    for (Entry<String, String> entry : data_map.entrySet()) { // 给pdf中预留的文本域填充数据
			    s.setField(entry.getKey(), entry.getValue());
		    }
		    ps.setFormFlattening(true); // 这句不能少  
			ps.close(); // 这句不能少  
			
		    pdfByte = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
			    try {
					ps.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pdfByte;
	}
	
	public static void main(String[] args) {
//		editPdfTemplate("E://hk.pdf", "E://hk2222.pdf","E://vip.jpg");
		
		Map<String,String> data_map = new HashMap<String, String>();
		data_map.put("title", "测试标题");
        FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("E:/hkss.pdf");
	        fos.write(editPdfTemplate("E:/HK.pdf", data_map));
	        fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void editPdfTemplate(String templateFile, String outFile, String image) {
		PdfReader reader = null;
		PdfStamper ps = null;
		try {
			reader = new PdfReader(templateFile); // 模版文件目录  
//		    ps = new PdfStamper(reader, new FileOutputStream(  
//		    		outFile)); // 生成的输出流  
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		    ps = new PdfStamper(reader, bos);  
		  
		    AcroFields s = ps.getAcroFields();  
		    
		    Map<String, Object> fieldMap = s.getFields(); // pdf表单相关信息展示  
		    for (Entry<String, Object> entry : fieldMap.entrySet()) {  
		        String name = entry.getKey(); // name就是pdf模版中各个文本域的名字  
		        Item item = (Item) entry.getValue();  
		        logger.info("[name]:" + name + ", [value]: " + item.toString());  
		    }  
		  
		    s.setField("shusx", "ssssxxxx");
		    
		    if(!"".equals(image)){
	            Image gif = Image.getInstance(image);
	            //指定pdf插入位置
	            gif.setAbsolutePosition(500, 80);
	            gif.scaleAbsolute(mmTopx(10), mmTopx(10));
	            PdfContentByte under = ps.getUnderContent(reader.getNumberOfPages());
	            under.addImage(gif);
	        }
		    ps.setFormFlattening(true); // 这句不能少  
		    ps.close();  
		    reader.close(); 
		    
			String ss = "";
			if (bos.toByteArray() != null) {
				ss = new BASE64Encoder().encode(bos.toByteArray());
				logger.info(((ss.getBytes().length)/1024)+"----" + ss.length());
			} 
		    
	        FileOutputStream fos = new FileOutputStream(outFile);
	        fos.write(new BASE64Decoder().decodeBuffer(ss));
	        fos.close();
	        bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	/**
	 * 毫米转像素
	 * 
	 * @param mm
	 * @return
	 */
	public static float mmTopx(float mm) {
		mm = (float) (mm * 3.33);
		return mm;
	}
	
	public static void editPdfTemplate1(String templateFile, String outFile) {
		try {
			int count = 8;// 总记录数
			int pageCount = 4;// 每页记录数
			int index = 1; // 表格序号
			int page = 0;// 总共页数
			/** 主要控制总共的页数 */
			if (count >= pageCount && count % pageCount == 0) {
				page = count / pageCount;
			} else {
				page = count / pageCount + 1;
			}
			String TemplatePDF = templateFile;// 设置模板路径
			FileOutputStream fos = new FileOutputStream(outFile);// 需要生成PDF

			ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page];// 用于存储每页生成PDF流
			/** 向PDF模板中插入数据 */
			for (int item = 0; item < page; item++) {
				baos[item] = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(TemplatePDF);
				PdfStamper stamp = new PdfStamper(reader, baos[item]);
				AcroFields form = stamp.getAcroFields();
				form.setField("DepartmnetNmae", "蓝飞");// 插入的数据都为字符类型
				form.setField("qq", "252462807");
				form.setField("pageNumber", "第" + (item + 1) + "页,共" + page
						+ "页");
				if (count % pageCount != 0 && item == page - 1) {
					logger.info("====pageCount+" + pageCount + "=====");
					pageCount = count % pageCount;
				}
				/** 因为PDF中的表格其实是众多的文本域组成,就是一个数组,所以把它循环出来就可以了 */
				for (int j = 0; j < pageCount; j++) {
					form.setField("ProjectTask[" + j + "]", index + "");
					form.setField("星期一[" + j + "]", "星期一[" + index + "]");
					form.setField("星期二[" + j + "]", "星期二[" + index + "]");
					form.setField("星期三[" + j + "]", "星期三[" + index + "]");
					form.setField("星期四[" + j + "]", "星期四[" + index + "]");
					form.setField("星期五[" + j + "]", "星期五[" + index + "]");
					form.setField("星期六[" + j + "]", "星期六[" + index + "]");
					form.setField("星期日[" + j + "]", "星期日[" + index + "]");
					form.setField("意见[" + j + "]", "同意[" + j + "]");
					index++;
				}
				stamp.setFormFlattening(true); // 千万不漏了这句啊, */
				stamp.close();
			}
			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();
			PdfImportedPage impPage = null;
			/** 取出之前保存的每页内容 */
			for (int i = 0; i < page; i++) {
				impPage = pdfCopy.getImportedPage(
						new PdfReader(baos[i].toByteArray()), 1);
				pdfCopy.addPage(impPage);
			}
			doc.close();// 当文件拷贝 记得关闭doc
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}
}