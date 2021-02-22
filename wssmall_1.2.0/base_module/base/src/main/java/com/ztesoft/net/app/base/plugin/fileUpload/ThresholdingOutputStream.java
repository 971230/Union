package com.ztesoft.net.app.base.plugin.fileUpload;

import java.io.IOException;
import java.io.OutputStream;


public abstract class ThresholdingOutputStream
    extends OutputStream
{

    private int threshold;

    private long written;

    private boolean thresholdExceeded;

    public ThresholdingOutputStream(int threshold)
    {
        this.threshold = threshold;
    }

    @Override
	public void write(int b) throws IOException
    {
        checkThreshold(1);
        getStream().write(b);
        written++;
    }

    @Override
	public void write(byte b[]) throws IOException
    {
        checkThreshold(b.length);
        getStream().write(b);
        written += b.length;
    }

    @Override
	public void write(byte b[], int off, int len) throws IOException
    {
        checkThreshold(len);
        getStream().write(b, off, len);
        written += len;
    }

    @Override
	public void flush() throws IOException
    {
        getStream().flush();
    }

    @Override
	public void close() throws IOException
    {
        try
        {
            flush();
        }
        catch (IOException ignored)
        {
        }
        getStream().close();
    }

    public int getThreshold()
    {
        return threshold;
    }

    public long getByteCount()
    {
        return written;
    }


    public boolean isThresholdExceeded()
    {
        return (written > threshold);
    }


    protected void checkThreshold(int count) throws IOException
    {
        if (!thresholdExceeded && (written + count > threshold))
        {
            thresholdReached();
            thresholdExceeded = true;
        }
    }


    protected abstract OutputStream getStream() throws IOException;

    protected abstract void thresholdReached() throws IOException;
}
