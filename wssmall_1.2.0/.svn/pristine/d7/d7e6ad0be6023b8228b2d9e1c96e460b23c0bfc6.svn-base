package org.springframework.web.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract interface MultipartFile
{
  public abstract String getName();

  public abstract boolean isEmpty();

  public abstract String getOriginalFilename();

  public abstract String getContentType();

  public abstract long getSize();

  public abstract byte[] getBytes()
    throws IOException;

  public abstract InputStream getInputStream()
    throws IOException;

  public abstract void transferTo(File paramFile)
    throws IOException, IllegalStateException;
}

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.multipart.MultipartFile
 * JD-Core Version:    0.6.0
 */