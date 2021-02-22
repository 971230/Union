package com.ztesoft.net.app.base.plugin.fileUpload;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

import java.io.*;

public class DefaultFileItem implements FileItem {

	private static int counter = 0;

	private String fieldName;

	private String contentType;

	private boolean isFormField;

	private String fileName;

	private int sizeThreshold;

	private File repository;

	private byte[] cachedContent;

	private DeferredFileOutputStream dfos;

	DefaultFileItem(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold,
			File repository) {
		this.fieldName = fieldName;
		this.contentType = contentType;
		this.isFormField = isFormField;
		this.fileName = fileName;
		this.sizeThreshold = sizeThreshold;
		this.repository = repository;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (!dfos.isInMemory()) {
			
			return new FileInputStream(dfos.getFile());
		}
		
		if (cachedContent == null) {

			cachedContent = dfos.getData();
		}
		return new ByteArrayInputStream(cachedContent);
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public String getName() {
		return fileName;
	}

	@Override
	public boolean isInMemory() {
		return (dfos.isInMemory());
	}

	@Override
	public long getSize() {
		if (cachedContent != null) {
			return cachedContent.length;
		} else if (dfos.isInMemory()) {
			return dfos.getData().length;
		} else {
			return dfos.getFile().length();
		}
	}

	@Override
	public byte[] get() {
		if (dfos.isInMemory()) {
			if (cachedContent == null) {
				cachedContent = dfos.getData();
			}
			return cachedContent;
		}

		byte[] fileData = new byte[(int) getSize()];
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(dfos.getFile());
			fis.read(fileData);
		} catch (IOException e) {
			fileData = null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		return fileData;
	}

	@Override
	public String getString(String encoding) throws UnsupportedEncodingException {
		return new String(get(), encoding);
	}

	@Override
	public String getString() {
		return new String(get());
	}

	@Override
	public void write(File file) throws Exception {
		FileOutputStream fos = new FileOutputStream(file);
		FileInputStream fis =null;
		if (isInMemory()) {
			FileOutputStream fout = null;
			try {
				fout = fos;
				fout.write(get());
			} finally {
				if (fout != null) {
					fout.close();
				}
			}
		} else {
			File outputFile = getStoreLocation();
			if (outputFile != null) {

				if (!outputFile.renameTo(file)) {
					BufferedInputStream in = null;
					BufferedOutputStream out = null;
					try {
						fis= new FileInputStream(outputFile);
						in = new BufferedInputStream(fis);
						out = new BufferedOutputStream(fos);
						byte[] bytes = new byte[2048];
						int s = 0;
						while ((s = in.read(bytes)) != -1) {
							out.write(bytes, 0, s);
						}
					} finally {
						try {
							if(in!=null)
							{
							in.close();
							}
						} catch (IOException e) {
							// ignore
						}
						try {
							if(out!=null)
							{
							out.close();
							}
							if(fis!=null){
							fis.close();
							}
							if(fos!=null){
							fos.close();
							}
						} catch (IOException e) {
							// ignore
						}
					}
				}
			} else {

				throw new FileUploadException("Cannot write uploaded file to disk!");
			}
		}
	}

	@Override
	public void delete() {
		cachedContent = null;
		File outputFile = getStoreLocation();
		if (outputFile != null && outputFile.exists()) {
			outputFile.delete();
		}
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public boolean isFormField() {
		return isFormField;
	}

	@Override
	public void setFormField(boolean state) {
		isFormField = state;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		if (dfos == null) {
			File outputFile = getTempFile();
			dfos = new DeferredFileOutputStream(sizeThreshold, outputFile);
		}
		return dfos;
	}

	public File getStoreLocation() {
		return dfos.getFile();
	}

	@Override
	protected void finalize() {
		File outputFile = dfos.getFile();

		if (outputFile != null && outputFile.exists()) {
			outputFile.delete();
		}
	}

	protected File getTempFile() {
		File tempDir = repository;
		if (tempDir == null) {
			tempDir = new File(System.getProperty("java.io.tmpdir"));
		}

		String fileName = "upload_" + getUniqueId() + ".tmp";

		File f = new File(tempDir, fileName);
		f.deleteOnExit();
		return f;
	}

	private static String getUniqueId() {
		int current;
		synchronized (DefaultFileItem.class) {
			current = counter++;
		}
		String id = Integer.toString(current);

		if (current < 100000000) {
			id = ("00000000" + id).substring(id.length());
		}
		return id;
	}

}
