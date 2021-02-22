package com.ztesoft.net.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.database.Page;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IVOX2WAVManager;
import com.ztesoft.net.util.SFTPChannel;
import com.ztesoft.soc.fastdfs.FileServerImpl;
import com.ztesoft.soc.fastdfs.IDfsManager;

public class VOX2WAVManager implements IVOX2WAVManager{

	private static VOX2WAVManager v3ToWav;

	private ChannelSftp sftpchannel;
	private SFTPChannel channel;
	
	 public static boolean flag = true;
	public static VOX2WAVManager getInstance() {
		if (v3ToWav == null) {
			v3ToWav = new VOX2WAVManager();
		}
		return v3ToWav;
	}

	private static final int WAV_HEAD = 36;// wav文件头长度

	private static final int VF_ADPCM = 1;// 编码格式

	private static final int BIT_RATE_VB_8 = 1;// 每个样本位数

	private static final int BIT_RATE_VB_16 = 2;// 每个样本位数

	private static final int RESET_VALUE = 48;

	private static byte out_val;

	/**
	 * 
	 * @param inFile
	 * @param outFile
	 * @param voxFormat
	 *            格式 取值范围:VF_ADPCM = 1, VF_MULAW = 2, VF_ALAW = 3
	 * @param voxRate
	 *            采样率 取值范围：VR_6K = 6000, VR_8K = 8000
	 * @param bit_rate
	 *            位数 取值范围：VB_8 = 1, VB_16 = 2
	 * @return
	 */

	public void voxConvert(String inFile, String outFile, int voxFormat,
			int voxRate, int bit_rate) throws Exception {
		if (outFile == null) {
			outFile = inFile.substring(0, inFile.length() - 2);
			outFile = outFile + "wav";
		}
		File outF = new File(outFile);
		if(outF.length()==0){
			File inF = new File(inFile);
			long inFileSize = inF.length();
			System.out.println(inFileSize);
			if (voxFormat == VF_ADPCM) { // if using ADPCM input format...
				inFileSize = inFileSize * 2;
			}// change from bytes to samples

			FileOutputStream filewriter = new FileOutputStream(outF, false);
			String wavBegin = "RIFF";
			filewriter.write(wavBegin.getBytes());// WAV 文件头

			long wavLength = inFileSize * bit_rate + WAV_HEAD;
			byte[] tmpArr = new byte[4];
			longToIntBinary(wavLength, tmpArr, 0);
			filewriter.write(tmpArr);// 文件总长度

			String wavTag = "WAVEfmt ";
			filewriter.write(wavTag.getBytes());// WAV 文件标识

			int headLength = 16;
			tmpArr = new byte[4];
			longToIntBinary(headLength, tmpArr, 0);
			filewriter.write(tmpArr);// size of .WAV file header

			int wFormatTag = 1; // format tag (01 = Windows PCM)
			tmpArr = new byte[2];
			toShortBinary(wFormatTag, tmpArr, 0);
			filewriter.write(tmpArr);// format tag (01 = Windows PCM)

			int nChannels = 1; // channels (1=mono, 2=stereo)
			tmpArr = new byte[2];
			toShortBinary(nChannels, tmpArr, 0);
			filewriter.write(tmpArr);// channels (1=mono, 2=stereo)

			int nSamplesPerSec = voxRate; // samples per second
			tmpArr = new byte[4];
			longToIntBinary(nSamplesPerSec, tmpArr, 0);
			filewriter.write(tmpArr);

			int nAvgBytesPerSec = voxRate * bit_rate; // bytes per second
			// during play
			tmpArr = new byte[4];
			longToIntBinary(nAvgBytesPerSec, tmpArr, 0);
			filewriter.write(tmpArr);

			int nBlockAlign = bit_rate; // bytes per sample
			tmpArr = new byte[2];
			toShortBinary(nBlockAlign, tmpArr, 0);
			filewriter.write(tmpArr);// bytes per sample

			int wBitsPerSample = 8 * bit_rate; // bits per sample
			tmpArr = new byte[2];
			toShortBinary(wBitsPerSample, tmpArr, 0);
			filewriter.write(tmpArr);// bits per sample

			/** ******以下是数据头********* */
			String dataTag = "data";
			filewriter.write(dataTag.getBytes());// data tag

			long Datalen = inFileSize * bit_rate; // write size of .WAV data
			// portion
			tmpArr = new byte[4];
			longToIntBinary(Datalen, tmpArr, 0);
			filewriter.write(tmpArr);// 数据总长度

			FileInputStream fileReader = new FileInputStream(inF);
			byte[] outbytebuffer = new byte[20000];
			int[] outintbuffer = new int[20000];
			switch (voxFormat) {
			case VF_ADPCM:// VF_ADPCM
			{
				short Sn = 0; // initialize the ADPCM variables
				int SS = 16; // initialize the Step
				int SSindex = 1;
				int i = 0;
				byte[] b = new byte[10000];

				int outindex;
				byte sample;// 一个采样样本

				int allBytes = fileReader.available();
				while (allBytes > 0) {
					int thisRead = allBytes > 10000 ? 10000 : allBytes;
					int bytes = fileReader.read(b, 0, thisRead);

					allBytes -= thisRead;// 剩余可读的字节数
					outindex = 0;
					for (int index = 0; index < bytes; index++) {
						sample = b[index];
						byte highByte = (byte) (sample >>> 4 & 0xff);
						byte lowByte = (byte) (sample & 0x0f);

						/** *****开始高字节处理******** */
						if ((highByte == 0) || (highByte == 8)) {
							i++;
						}
						Object[] retVal = decode((byte) (sample >>> 4), Sn, SS,
								SSindex);
						Sn = ((Short) retVal[0]).shortValue();
						SS = ((Integer) retVal[1]).intValue();
						SSindex = ((Integer) retVal[2]).intValue();

						int out_int;
						if (bit_rate == BIT_RATE_VB_8) {// if 8 bits per sample...
							out_int = Sn / 16;
							if (out_int > 127) { // clip if above or below WAV
								// bounds
								out_int = 127;
							}
							if (out_int < -128) {
								out_int = -128;
							}
							out_val = (byte) ((out_int - 128) & 0xff); // convert

							// to
							// .WAV format
							outbytebuffer[outindex] = out_val; // write the output
							// byte
						} else {// else 16 bits per sample
							out_int = Sn * 16; // rescale to 16 bits
							outintbuffer[outindex] = out_int; // write the output
							// int
						}
						outindex++;

						if (i == RESET_VALUE) { // Reset ADPCM variables 48位时重置各个值
							Sn = 0; // initialize the ADPCM variables
							SS = 16; // initialize the Step
							i = 0;
						}

						/** *******低字节处理********* */
						if (lowByte == 0 || lowByte == 8) {
							i++;
						}
						retVal = decode((byte) (sample & 0x0f), Sn, SS, SSindex);
						Sn = ((Short) retVal[0]).shortValue();
						SS = ((Integer) retVal[1]).intValue();
						SSindex = ((Integer) retVal[2]).intValue();

						if (bit_rate == BIT_RATE_VB_8) {// if 8 bits per sample...
							out_int = Sn / 16;
							if (out_int > 127) { // clip if above or below WAV
								// bounds
								out_int = 127;
							}
							if (out_int < -128) {
								out_int = -128;
							}
							out_val = (byte) ((out_int - 128) & 0xff); // convert

							// to
							// .WAV format
							outbytebuffer[outindex] = out_val; // write the output
							// byte
						} else {// else 16 bits per sample
							out_int = Sn * 16; // rescale to 16 bits
							outintbuffer[outindex] = out_int; // write the output
							// int
						}
						outindex++;

						if (i == RESET_VALUE) { // Reset ADPCM variables 48位时重置各个值
							Sn = 0; // initialize the ADPCM variables
							SS = 16; // initialize the Step
							i = 0;
						}
					}
					if (bit_rate == BIT_RATE_VB_8) {
						filewriter.write(outbytebuffer);

					} else {
						for (int j = 0; j < outintbuffer.length; j++) {
							byte[] arr = new byte[4];
							longToIntBinary(outintbuffer[j], arr, 0);
							filewriter.write(arr);

						}
					}
				}
				break;

			}
			default:
				break;
			}

			fileReader.close();
			filewriter.close();
		}
		

	}

	/**
	 * 
	 * @param sample
	 * @param Sn
	 * @param ss
	 *            引用变量
	 * @param SSindex
	 *            引用变量
	 * @return
	 */
	private static Object[] decode(byte sample, short Sn, int SS, int SSindex) {
		Object[] retArr = new Object[3];
		int[] SSadjust = new int[] { -1, -1, -1, -1, 2, 4, 6, 8 };
		// Calculated stepsizes per Dialogic Application Note 1366
		int[] SStable = new int[] { 0, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37,
				41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143,
				157, 173, 190, 209, 230, 253, 279, 307, 337, 371, 408, 449,
				494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411,
				1552 };
		int Mn = 0; // calculate the linear adjustment
		if ((sample & 0x4) != 0) {
			Mn = SS;
		}
		if ((sample & 0x2) != 0) {
			Mn = Mn + (SS >>> 1);
		}// div 2
		if ((sample & 0x1) != 0) {
			Mn = Mn + (SS >>> 2);
		}// div 4

		Mn = Mn + (SS >>> 3); // div 8
		// 取Sample的符号位，即最高位
		if ((sample & 0x8) != 0) { // 最高位为1，则符号位为负
			Sn = (short) (((int) Sn - Mn) & 0xffff); // ...subtract the
			// adjustment
		} else { // 符号位为正
			Sn = (short) (((int) Sn + Mn) & 0xffff); // ...add the adjustment
		}

		if (Sn > 2047) { // adjust if sample too large...
			Sn = 2047;
		}
		if (Sn < -2048) { // ...or too small
			Sn = -2048;
		}

		// use as index into step size adjustment, adjust step size index
		SSindex = SSindex + SSadjust[sample & 0x07];

		if (SSindex < 1) { // keep SSindex within bounds...
			SSindex = 1;
		}
		if (SSindex > 49) {
			SSindex = 49;
		}

		SS = SStable[SSindex]; // get new step size from table

		retArr[0] = Sn;
		retArr[1] = SS;
		retArr[2] = SSindex;
		return retArr;
	}

	/**
	 * 整型转数组
	 * 
	 * @param val
	 * @param array
	 * @param offset
	 */
	private static void longToIntBinary(long val, byte[] array, int offset) {
		array[offset] = (byte) (val & 0xff);
		array[offset + 1] = (byte) (val >>> 8 & 0xff);
		array[offset + 2] = (byte) (val >>> 16 & 0xff);
		array[offset + 3] = (byte) (val >>> 24 & 0xff);
	}

	private void byteToShortBinary(byte val, byte[] array, int offset) {
		array[offset] = (byte) (val & 0xff);
		array[offset + 1] = 0x0;
	}

	/**
	 * java中没有有符号类型，所以将超过0x7FFF的short类型保存为int类型。本方法提供了将有符号short类型
	 * 转换保存在字节数组中，占据两个字节
	 * 
	 * @param val
	 *            int
	 * @param array
	 *            byte[]
	 * @param offset
	 *            int
	 */
	private static void toShortBinary(int val, byte[] array, int offset) {
		array[offset] = (byte) (val & 0xff);
		array[offset + 1] = (byte) (val >>> 8 & 0xff);
	}

	
	/**
	     * 音频转码 转成MP3格式
	     * @param source
	     * @param targetPath
	     * @return
	     */
	/*public static boolean transcodingToMP3(File source,String targetPath){
		 File target = new File(targetPath);
		 AudioAttributes audio = new AudioAttributes();// 音频属性
		 audio.setCodec("libmp3lame");// libmp3lame 音频编码
		 audio.setBitRate(new Integer(128000));// 音频比特率
		 audio.setChannels(new Integer(1));// 声道
		 audio.setSamplingRate(new Integer(44100));// 采样率
		 EncodingAttributes attrs = new EncodingAttributes();// 视频属性
		 attrs.setFormat("mp3");// 转码格式
		 attrs.setAudioAttributes(audio);// 音频属性
		 Encoder encoder = new Encoder();// 创建解码器
		 long beginTime = System.currentTimeMillis();
		 try {
			    // 获取时长
			    MultimediaInfo m = encoder.getInfo(source);
			    System.out.println(m.getDuration()/1000 + "秒");
			    System.out.println("获取时长花费时间是：" + ((System.currentTimeMillis() - beginTime))/1000 + "秒");
			    beginTime = System.currentTimeMillis();
			    encoder.encode(source, target, attrs);
			    System.out.println("音频转码花费时间是：" + ((System.currentTimeMillis() - beginTime)/1000) + "秒");
			    flag = true;
		 } catch (IllegalArgumentException e) {
			 flag = false;
			   e.printStackTrace();
		 } catch (InputFormatException e) {
			 flag = false;
			 e.printStackTrace();
		 } catch (EncoderException e) {
			 flag = false;
			 e.printStackTrace();
		 }
		 	return flag;
	    }*/

	public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String params = cacheUtil.getConfigInfo("VOX2WAV_PARAM");
		net.sf.json.JSONObject jsonobj = net.sf.json.JSONObject.fromObject(params);
		filePath = jsonobj.getString("local_url")+filePath;
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); // 非常重要
        if (isOnLine) { // 在线打开方式
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }

	public String getUserPhone(String userid) throws Exception{
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String phone_sql = " select mobile from es_adminuser_call_mobile where userid='"+userid+"' ";
		String phone = "";
		Page page =  baseDaoSupport.queryForPage(phone_sql, 1, 20, new RowMapper(){
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data  = new HashMap();
				data.put("mobile",rs.getString("mobile"));
			return data;
			}
		}, null);
		List list = page.getResult();
		if(list.size()>0){
			Map map = (Map) list.get(0);
			phone = Const.getStrValue(map, "mobile");
		}
		return phone;
	}
	
	public Map getParams(String cf_id) throws Exception{
		Map params = new HashMap();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String sql = " select cf_value from es_config_info where cf_id = '"+cf_id+"' ";
		Page page =  baseDaoSupport.queryForPage(sql, 1, 20, new RowMapper(){
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data  = new HashMap();
				data.put("cf_value",rs.getString("cf_value"));
			return data;
			}
		}, null);
		List list = page.getResult();
		if(list.size()>0){
			Map map = (Map) list.get(0);
			String cf_value = Const.getStrValue(map, "cf_value");
			if(!StringUtil.isEmpty(cf_value)){
				params = JSON.parseObject(cf_value);
			}
			
		}
		return params;
	}
	
	public Page queryCalllog(String order_id,int pageNo, int pageSize){
		String sql = " select ad.lan_name,ad.dep_name,ad.realname,ofi.create_time,d.caller as mobile,d.callee as ship_mobile,ofi.file_id,ofi.order_id"
				   + " from es_order_file ofi "
				   + " left join es_adminuser ad on ofi.operator_id=ad.userid "
				   + " left join es_order_call_sms_logs d on d.order_id=ofi.order_id and d.recordoutid=ofi.file_id"
				   + " where ofi.order_id='"+order_id+"' and file_type='vox' and status='1' and ofi.source_from = '"+ManagerUtils.getSourceFrom()+"' ";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		Page page =  baseDaoSupport.queryForPage(sql, pageNo, pageSize, new RowMapper(){
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data  = new HashMap();
				data.put("lan_name",rs.getString("lan_name"));
				data.put("dep_name",rs.getString("dep_name"));
				data.put("realname",rs.getString("realname"));
				data.put("create_time",rs.getString("create_time"));
				data.put("mobile",rs.getString("mobile"));
				data.put("ship_mobile",rs.getString("ship_mobile"));
				data.put("file_id",rs.getString("file_id"));
				data.put("order_id",rs.getString("order_id"));
			return data;
			}
		}, null);
		return page;
	}
	
	public Page queryIntentCalllog(String order_id,int pageNo, int pageSize){
		String sql = " select ad.lan_name,ad.dep_name,ad.realname,ofi.create_time,d.caller as mobile,d.callee as ship_mobile,ofi.file_id,ofi.order_id"
				   + " from es_order_intent_file ofi "
				   + " left join es_adminuser ad on ofi.operator_id=ad.userid "
				   + " left join es_order_call_sms_logs d on  d.recordoutid=ofi.file_id"
				   + " where ofi.order_id='"+order_id+"' and file_type='vox' and status='1' and ofi.source_from = '"+ManagerUtils.getSourceFrom()+"' ";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		Page page =  baseDaoSupport.queryForPage(sql, pageNo, pageSize, new RowMapper(){
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data  = new HashMap();
				data.put("lan_name",rs.getString("lan_name"));
				data.put("dep_name",rs.getString("dep_name"));
				data.put("realname",rs.getString("realname"));
				data.put("create_time",rs.getString("create_time"));
				data.put("mobile",rs.getString("mobile"));
				data.put("ship_mobile",rs.getString("ship_mobile"));
				data.put("file_id",rs.getString("file_id"));
				data.put("order_id",rs.getString("order_id"));
			return data;
			}
		}, null);
		return page;
	}
	
	public String playVoiceFile() throws Exception {
		String filePath = "D:\\vox\\13488612131100147.wav";
		if (!filePath.equals("")) {
			//Get audio input stream
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
			//Get audio coding object
			AudioFormat audioFormat = audioInputStream.getFormat();
			//Set data entry
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat,
					AudioSystem.NOT_SPECIFIED);
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			//Read from the data sent to the mixer input stream
			int count;
			byte tempBuffer[] = new byte[1024];
			while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if (count > 0) {
					sourceDataLine.write(tempBuffer, 0, count);
				}
			}
			//Empty the data buffer, and close the input
			sourceDataLine.drain();
			sourceDataLine.close();
		}
 
		return null;
	}

	public void upload(String voxFileName,String wavFileName) {
		
		try {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String params = cacheUtil.getConfigInfo("VOX2WAV_PARAM");
			net.sf.json.JSONObject jsonobj = net.sf.json.JSONObject.fromObject(params);
			Map<String,String> login_map = new HashMap<String,String>();
			login_map.put("ip", jsonobj.getString("ip"));
			login_map.put("port", jsonobj.getString("port"));
			login_map.put("userName", jsonobj.getString("userName"));
			login_map.put("passWord", jsonobj.getString("passWord"));
			channel = new SFTPChannel();
			sftpchannel = channel.getChannel(login_map, 200000);
			String uplod_url = jsonobj.getString("uplod_url");
			String local_url = jsonobj.getString("local_url");
			sftpchannel.get(uplod_url+voxFileName, local_url+voxFileName);
			
			voxConvert(local_url+voxFileName, local_url+wavFileName, 1, 6000, 1);
			File file = new File(local_url+wavFileName);
			FileInputStream in = new FileInputStream(file);
			sftpchannel.cd(uplod_url);
			sftpchannel.put(in,wavFileName);
			in.close();
			channel.deleteFile(local_url+voxFileName);
	        
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				channel.closeChannel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
        
	}
	
	public String queryShipTel(String order_id){
		String ship_tel = "";
		String qry_sql = " select ship_tel from es_order_intent where order_id='"+order_id+"' ";
		try{
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			ship_tel = baseDaoSupport.queryForString(qry_sql);
		}catch(Exception e){
			ship_tel = "";
		}
		return ship_tel;
	}
	
	public Map qryCallFileIntent(String file_id){
		Map map = new HashMap();
		try{
			String qry_sql = " select a.create_time,a.file_path,a.file_name from es_order_intent_file a "
					       + " where a.file_id='"+file_id+"' and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			Page page =  baseDaoSupport.queryForPage(qry_sql, 1, 20, new RowMapper(){
				public Object mapRow(ResultSet rs, int c) throws SQLException {
					Map data  = new HashMap();
					data.put("create_time",rs.getString("create_time"));
					data.put("file_path",rs.getString("file_path"));
					data.put("file_name",rs.getString("file_name"));
				return data;
				}
			}, null);
			List list = page.getResult();
			if(list.size()>0){
				map = (Map)list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	public static void main(String[] args) {

		String inFile = "D:\\vox\\13488612131100147.vox";
		String outFile = "D:\\vox\\13488612131100147.wav";

		File file = new File(inFile);

		try {
			InputStream input = new FileInputStream(file);
			System.out.println(file.length());
			System.out.println(input.available());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			VOX2WAVManager a = new VOX2WAVManager();
			a.voxConvert(inFile, outFile, 1, 6000, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
