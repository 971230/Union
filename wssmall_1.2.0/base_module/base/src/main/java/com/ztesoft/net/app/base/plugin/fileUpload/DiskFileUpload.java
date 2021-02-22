package com.ztesoft.net.app.base.plugin.fileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

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
public class DiskFileUpload
    extends FileUploadBase
 {

    private DefaultFileItemFactory fileItemFactory;

    public DiskFileUpload()
    {
        super();
        this.fileItemFactory = new DefaultFileItemFactory();
    }


    public DiskFileUpload(DefaultFileItemFactory fileItemFactory)
    {
        super();
        this.fileItemFactory = fileItemFactory;
    }


    @Override
	public FileItemFactory getFileItemFactory()
    {
        return fileItemFactory;
    }


    @Override
	public void setFileItemFactory(FileItemFactory factory)
    {
        this.fileItemFactory = (DefaultFileItemFactory) factory;
    }

    public int getSizeThreshold()
    {
        return fileItemFactory.getSizeThreshold();
    }


    public void setSizeThreshold(int sizeThreshold)
    {
        fileItemFactory.setSizeThreshold(sizeThreshold);
    }


    public String getRepositoryPath()
    {
        return fileItemFactory.getRepository().getPath();
    }


    public void setRepositoryPath(String repositoryPath)
    {
        fileItemFactory.setRepository(new File(repositoryPath));
    }


    public List parseRequest(HttpServletRequest req,
                                            int sizeThreshold,
                                            long sizeMax, String path)
        throws FileUploadException
    {
        setSizeThreshold(sizeThreshold);
        setSizeMax(sizeMax);
        setRepositoryPath(path);
        return parseRequest(req);
    }

}
