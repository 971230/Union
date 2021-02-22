package com.ztesoft.net.app.base.plugin.fileUpload;

import java.io.*;

public interface FileItem
    extends Serializable
{

    InputStream getInputStream()
        throws IOException;

    String getContentType();

    String getName();

    boolean isInMemory();

    long getSize();

    byte[] get();

    String getString(String encoding)
        throws UnsupportedEncodingException;

    String getString();

    void write(File file) throws Exception;

    void delete();

    String getFieldName();

    void setFieldName(String name);

    boolean isFormField();

    void setFormField(boolean state);

    OutputStream getOutputStream() throws IOException;

}
