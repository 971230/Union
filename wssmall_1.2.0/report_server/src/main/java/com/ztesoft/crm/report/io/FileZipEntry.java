package com.ztesoft.crm.report.io;

import java.io.File;

public abstract interface FileZipEntry
{
  public abstract void doRead(String paramString, byte[] paramArrayOfByte);

  public abstract void doWrite(String paramString, File paramFile);
}

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.io.FileZipEntry
 * JD-Core Version:    0.5.3
 */