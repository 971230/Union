package com.ztesoft.autoprocess.base.common.OCR;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdesktop.swingx.util.OS;

/**
 * @Description: tesseract-ocr图片处理工具(获取图片验证码)
 * @author: shusx
 * @date: 2013-10-15 上午9:23:45
 */
public class OCR {
    private static Log log = LogFactory.getLog(OCR.class);
    private static final String LANG_OPTION = "-l";
    private static final String EOL = System.getProperty("line.separator");
    private static String tessPath = new File("tesseract").getAbsolutePath();
    
    // 图片验证码临时存在目录
    private static String tempFileDir;
    
    static {
        if (OS.isLinux()) {
            // 该目录为linux tesseract语言目录
            tempFileDir = "/usr/local/share/tessdata";
        } else if (OS.isWindows()) {
            // 该目录为工程下tesseract目录
            tempFileDir = "tesseract";
        } else {
            // 该目录为工程下tesseract目录
            tempFileDir = "tesseract";
        }
    }
    
    public static void main(String[] args) {
        try {
            /**
             *  downloadImage()参考获取图片byte[]方式
             *  开发时写在登陆的地方,只需要调用该类的read(byte[] image)方法
             */
            byte[] image = downloadImage();
            log.info("验证码：" + read(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] downloadImage() throws Exception {// 创建HttpClient实例
        DefaultHttpClient httpclient = new DefaultHttpClient();
        // 创建Get方法实例
        HttpGet httpgets = new HttpGet(
                "http://esales.10010.com/pages/sys/frame/frameValidationServlet?randamCode="
                        + new Random().nextInt(13));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 执行httpgets
            HttpResponse response = httpclient.execute(httpgets);
            HttpEntity entity = response.getEntity();
            // 读取内容
            if (entity != null) {
                InputStream is = entity.getContent();
                byte[] buf = new byte[1024];
                int len = -1;
                while ((len = is.read(buf)) > -1)
                    baos.write(buf, 0, len);
            }
        } catch (Exception e) {
            throw e;
        }

        return baos.toByteArray();
    }

    public static String read(byte[] image) throws Exception {
        // 返回验证码
        String randCode = "";

        // 图片验证码临时文件
        File file = null;
        String tempFileName = System.currentTimeMillis() + "";

        // 图片验证码临时文件简化后的临时文件
        File file_p = null;
        String tempFileName_p = tempFileName + "_p";

        try {
            // 保存图片到本地
            file = new File(tempFileDir, tempFileName);
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                    file));
            dos.write(image);
            dos.flush();
            dos.close();

            // 简化图片后保存
            ImageProcess.process(tempFileDir + "\\" + tempFileName, tempFileDir
                    + "\\" + tempFileName_p);

            // 解析图片
            file_p = new File(tempFileDir, tempFileName_p);
            randCode = read(file_p);

        } catch (Exception e) {
            throw e;
        } finally {
            if (file != null)
                file.delete();

            if (file_p != null)
                file_p.delete();
        }

        randCode = randCode.replaceAll("\\s", "");

        return randCode;
    }

    public static String read(File imageFile) throws Exception {
        String result = "";
        File outputFile = new File(imageFile.getParentFile(), "output");
        StringBuffer strB = new StringBuffer();

        // tesseract imageFile output -l eng
        List<String> cmd = new ArrayList<String>();
        if (OS.isWindows()) {
            cmd.add(tessPath + "//tesseract");
        } else if (OS.isLinux()) {
            cmd.add("tesseract");
        } else {
            cmd.add(tessPath + "//tesseract");
        }
        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
        cmd.add("eng");

        ProcessBuilder pb = new ProcessBuilder(new String[0]);
        pb.directory(imageFile.getParentFile());

        cmd.set(1, imageFile.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        int w = process.waitFor();

        if (w == 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
                    "UTF-8"));

            String str;
            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
        } else {
            String msg;
            switch (w) {
            case 1:
                msg = "Errors accessing files. There may be spaces in your image's filename.";
                break;
            case 29:
                msg = "Cannot recognize the image or its selected region.";
                break;
            case 31:
                msg = "Unsupported image format.";
                break;
            default:
                msg = "Errors occurred.";
            }
            log.info("验证码获取失败：" + msg);
        }

        new File(outputFile.getAbsolutePath() + ".txt").delete();
        result = strB.toString().trim();
        return result;
    }
}
