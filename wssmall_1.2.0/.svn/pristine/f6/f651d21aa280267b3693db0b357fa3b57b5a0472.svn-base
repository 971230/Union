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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FileUploadBase {

	public static final boolean isMultipartContent(HttpServletRequest req) {
		String contentType = req.getHeader(CONTENT_TYPE);
		if (contentType == null) {
			return false;
		}
		if (contentType.startsWith(MULTIPART)) {
			return true;
		}
		return false;
	}

	public static final String CONTENT_TYPE = "Content-type";

	public static final String CONTENT_DISPOSITION = "Content-disposition";

	public static final String FORM_DATA = "form-data";

	public static final String ATTACHMENT = "attachment";

	public static final String MULTIPART = "multipart/";

	public static final String MULTIPART_FORM_DATA = "multipart/form-data";

	public static final String MULTIPART_MIXED = "multipart/mixed";

	public static final int MAX_HEADER_SIZE = 1024;

	private long sizeMax = -1;

	private String headerEncoding;

	public abstract FileItemFactory getFileItemFactory();

	public abstract void setFileItemFactory(FileItemFactory factory);

	private InputStream is;

	public InputStream readIs() {
		return this.is;
	}

	public long getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}

	public String getHeaderEncoding() {
		return headerEncoding;
	}

	public void setHeaderEncoding(String encoding) {
		headerEncoding = encoding;
	}

	public List parseRequest(HttpServletRequest req) throws FileUploadException {
		if (null == req) {
			throw new NullPointerException("req parameter");
		}

		ArrayList items = new ArrayList();
		String contentType = req.getHeader(CONTENT_TYPE);

		if ((null == contentType) || (!contentType.startsWith(MULTIPART))) {
			throw new InvalidContentTypeException(
					"the request doesn't contain a " + MULTIPART_FORM_DATA
							+ " or " + MULTIPART_MIXED
							+ " stream, content type header is " + contentType);
		}
		int requestSize = req.getContentLength();

		if (requestSize == -1) {
			throw new UnknownSizeException(
					"the request was rejected because it's size is unknown");
		}

		if (sizeMax >= 0 && requestSize > sizeMax) {
			throw new SizeLimitExceededException(
					"the request was rejected because "
							+ "it's size exceeds allowed range");
		}
		InputStream input = null;
		try {
			int boundaryIndex = contentType.indexOf("boundary=");
			if (boundaryIndex < 0) {
				throw new FileUploadException(
						"the request was rejected because "
								+ "no multipart boundary was found");
			}
			byte[] boundary = contentType.substring(boundaryIndex + 9)
					.getBytes();
			input = req.getInputStream();
			MultipartStream multi = new MultipartStream(input, boundary);
			multi.setHeaderEncoding(headerEncoding);

			boolean nextPart = multi.skipPreamble();
			while (nextPart) {
				Map headers = parseHeaders(multi.readHeaders());
				String fieldName = getFieldName(headers);
				if (fieldName != null) {
					String subContentType = getHeader(headers, CONTENT_TYPE);

					if (subContentType != null
							&& subContentType.startsWith(MULTIPART_MIXED)) {
						byte[] subBoundary = subContentType.substring(
								subContentType.indexOf("boundary=") + 9)
								.getBytes();
						multi.setBoundary(subBoundary);
						boolean nextSubPart = multi.skipPreamble();
						while (nextSubPart) {
							headers = parseHeaders(multi.readHeaders());
							if (getFileName(headers) != null) {
								FileItem item = createItem(headers, false);
								OutputStream os = item.getOutputStream();
								try {
									multi.readBodyData(os);
								} finally {
									os.close();
								}
								items.add(item);
							} else {
								multi.discardBodyData();
							}
							nextSubPart = multi.readBoundary();
						}
						multi.setBoundary(boundary);
					} else {
						if (getFileName(headers) != null) {
							FileItem item = createItem(headers, false);
							OutputStream os = item.getOutputStream();
							try {
								multi.readBodyData(os);
							} finally {
								os.close();
							}
							items.add(item);
						} else {
	
							FileItem item = createItem(headers, true);
							OutputStream os = item.getOutputStream();
							try {
								multi.readBodyData(os);
							} finally {
								os.close();
							}
							items.add(item);
						}
					}
				} else {
					multi.discardBodyData();
				}
				nextPart = multi.readBoundary();
			}
		} catch (IOException e) {
			throw new FileUploadException("Processing of "
					+ MULTIPART_FORM_DATA + " request failed. "
					+ e.getMessage());
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return items;
	}

	protected String getFileName(Map headers) {
		String fileName = null;
		String cd = getHeader(headers, CONTENT_DISPOSITION);
		if (cd.startsWith(FORM_DATA) || cd.startsWith(ATTACHMENT)) {
			int start = cd.indexOf("filename=\"");
			int end = cd.indexOf('"', start + 10);
			if (start != -1 && end != -1) {
				fileName = cd.substring(start + 10, end).trim();
			}
		}
		return fileName;
	}

	protected String getFieldName(Map headers) {
		String fieldName = null;
		String cd = getHeader(headers, CONTENT_DISPOSITION);
		if (cd != null && cd.startsWith(FORM_DATA)) {
			int start = cd.indexOf("name=\"");
			int end = cd.indexOf('"', start + 6);
			if (start != -1 && end != -1) {
				fieldName = cd.substring(start + 6, end);
			}
		}
		return fieldName;
	}

	protected FileItem createItem(Map headers, boolean isFormField)
			throws FileUploadException {
		return getFileItemFactory().createItem(getFieldName(headers),
				getHeader(headers, CONTENT_TYPE), isFormField,
				getFileName(headers));
	}

	protected Map parseHeaders(String headerPart) {
		Map headers = new HashMap();
		char buffer[] = new char[MAX_HEADER_SIZE];
		boolean done = false;
		int j = 0;
		int i;
		String header, headerName, headerValue;
		try {
			while (!done) {
				i = 0;

				while (i < 2 || buffer[i - 2] != '\r' || buffer[i - 1] != '\n') {
					buffer[i++] = headerPart.charAt(j++);
				}
				header = new String(buffer, 0, i - 2);
				if (header.equals("")) {
					done = true;
				} else {
					if (header.indexOf(':') == -1) {

						continue;
					}
					headerName = header.substring(0, header.indexOf(':'))
							.trim().toLowerCase();
					headerValue = header.substring(header.indexOf(':') + 1)
							.trim();
					if (getHeader(headers, headerName) != null) {

						headers.put(headerName, getHeader(headers, headerName)
								+ ',' + headerValue);
					} else {
						headers.put(headerName, headerValue);
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {

		}
		return headers;
	}

	protected final String getHeader(Map headers, String name) {
		return (String) headers.get(name.toLowerCase());
	}

	public static class InvalidContentTypeException extends FileUploadException {

		public InvalidContentTypeException() {
			super();
		}

		public InvalidContentTypeException(String message) {
			super(message);
		}
	}

	public static class UnknownSizeException extends FileUploadException {

		public UnknownSizeException() {
			super();
		}

		public UnknownSizeException(String message) {
			super(message);
		}
	}

	public static class SizeLimitExceededException extends FileUploadException {

		public SizeLimitExceededException() {
			super();
		}

		public SizeLimitExceededException(String message) {
			super(message);
		}
	}

}
