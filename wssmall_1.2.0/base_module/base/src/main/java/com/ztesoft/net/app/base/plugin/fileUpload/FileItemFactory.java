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
public interface FileItemFactory
{

    FileItem createItem(
            String fieldName,
            String contentType,
            boolean isFormField,
            String fileName
            );
}
