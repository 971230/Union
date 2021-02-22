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


public class MultipartStream
{


    public static final int HEADER_PART_SIZE_MAX = 10240;

    protected static final int DEFAULT_BUFSIZE = 4096;

    protected static final byte[] HEADER_SEPARATOR = {0x0D, 0x0A, 0x0D, 0x0A};

    protected static final byte[] FIELD_SEPARATOR = { 0x0D, 0x0A };

    protected static final byte[] STREAM_TERMINATOR = { 0x2D, 0x2D };

    private InputStream input;

    private int boundaryLength;

    private int keepRegion;

    private byte[] boundary;

    private int bufSize;

    private byte[] buffer;

    private int head;

    private int tail;

    private String headerEncoding;

    //yoga add
    private InputStream out;

    public MultipartStream()
    {
    }

    public MultipartStream(InputStream input,
                           byte[] boundary,
                           int bufSize)
    {
        this.input = input;
        this.bufSize = bufSize;
        this.buffer = new byte[bufSize];

        this.boundary = new byte[boundary.length + 4];
        this.boundaryLength = boundary.length + 4;
        this.keepRegion = boundary.length + 3;
        this.boundary[0] = 0x0D;
        this.boundary[1] = 0x0A;
        this.boundary[2] = 0x2D;
        this.boundary[3] = 0x2D;
        System.arraycopy(boundary, 0, this.boundary, 4, boundary.length);

        head = 0;
        tail = 0;
    }

    public MultipartStream(InputStream input,
                           byte[] boundary)
        throws IOException
    {
        this(input, boundary, DEFAULT_BUFSIZE);
    }

    public String getHeaderEncoding()
    {
        return headerEncoding;
    }

    public void setHeaderEncoding(String encoding)
    {
        headerEncoding = encoding;
    }

    public byte readByte()
        throws IOException
    {
        if (head == tail)
        {
            head = 0;
            tail = input.read(buffer, head, bufSize);
            if (tail == -1)
            {
                throw new IOException("No more data is available");
            }
        }
        return buffer[head++];
    }

    public byte[] readStream()
    {
        return buffer;
    }

    public boolean readBoundary()
        throws MalformedStreamException
    {
        byte[] marker = new byte[2];
        boolean nextChunk = false;

        head += boundaryLength;
        try
        {
            marker[0] = readByte();
            marker[1] = readByte();
            if (arrayequals(marker, STREAM_TERMINATOR, 2))
            {
                nextChunk = false;
            }
            else if (arrayequals(marker, FIELD_SEPARATOR, 2))
            {
                nextChunk = true;
            }
            else
            {
                throw new MalformedStreamException(
                        "Unexpected characters follow a boundary");
            }
        }
        catch (IOException e)
        {
            throw new MalformedStreamException("Stream ended unexpectedly");
        }
        return nextChunk;
    }

    public void setBoundary(byte[] boundary)
        throws IllegalBoundaryException
    {
        if (boundary.length != boundaryLength - 4)
        {
            throw new IllegalBoundaryException(
                    "The length of a boundary token can not be changed");
        }
        System.arraycopy(boundary, 0, this.boundary, 4, boundary.length);
    }

    public String readHeaders()
        throws MalformedStreamException
    {
        int i = 0;
        byte b[] = new byte[1];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int sizeMax = HEADER_PART_SIZE_MAX;
        int size = 0;
        while (i < 4)
        {
            try
            {
                b[0] = readByte();
            }
            catch (IOException e)
            {
                throw new MalformedStreamException("Stream ended unexpectedly");
            }
            size++;
            if (b[0] == HEADER_SEPARATOR[i])
            {
                i++;
            }
            else
            {
                i = 0;
            }
            if (size <= sizeMax)
            {
                baos.write(b[0]);
            }
        }

        String headers = null;
        if (headerEncoding != null)
        {
            try
            {
                headers = baos.toString(headerEncoding);
            }
            catch (UnsupportedEncodingException e)
            {
                headers = baos.toString();
            }
        }
        else
        {
            headers = baos.toString();
        }
        try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return headers;
    }


    public int readBodyData(OutputStream output)
        throws MalformedStreamException,
               IOException
    {
        boolean done = false;
        int pad;
        int pos;
        int bytesRead;
        int total = 0;
        while (!done)
        {
            pos = findSeparator();
            if (pos != -1)
            {
                output.write(buffer, head, pos - head);
                total += pos - head;
                head = pos;
                done = true;
            }
            else
            {

                if (tail - head > keepRegion)
                {
                    pad = keepRegion;
                }
                else
                {
                    pad = tail - head;
                }
                output.write(buffer, head, tail - head - pad);

                total += tail - head - pad;
                System.arraycopy(buffer, tail - pad, buffer, 0, pad);

                head = 0;
                bytesRead = input.read(buffer, pad, bufSize - pad);


                if (bytesRead != -1)
                {
                    tail = pad + bytesRead;
                }
                else
                {

                    output.write(buffer, 0, pad);
                    output.flush();
                    total += pad;
                    throw new MalformedStreamException(
                            "Stream ended unexpectedly");
                }
            }
        }
        output.flush();
        return total;
    }


    public int discardBodyData()
        throws MalformedStreamException,
               IOException
    {
        boolean done = false;
        int pad;
        int pos;
        int bytesRead;
        int total = 0;
        while (!done)
        {
            pos = findSeparator();
            if (pos != -1)
            {
                total += pos - head;
                head = pos;
                done = true;
            }
            else
            {

                if (tail - head > keepRegion)
                {
                    pad = keepRegion;
                }
                else
                {
                    pad = tail - head;
                }
                total += tail - head - pad;

                System.arraycopy(buffer, tail - pad, buffer, 0, pad);

                head = 0;
                bytesRead = input.read(buffer, pad, bufSize - pad);

                if (bytesRead != -1)
                {
                    tail = pad + bytesRead;
                }
                else
                {

                    total += pad;
                    throw new MalformedStreamException(
                            "Stream ended unexpectedly");
                }
            }
        }
        return total;
    }

    public boolean skipPreamble()
        throws IOException
    {
        System.arraycopy(boundary, 2, boundary, 0, boundary.length - 2);
        boundaryLength = boundary.length - 2;
        try
        {
            discardBodyData();

            return readBoundary();
        }
        catch (MalformedStreamException e)
        {
            return false;
        }
        finally
        {
            System.arraycopy(boundary, 0, boundary, 2, boundary.length - 2);
            boundaryLength = boundary.length;
            boundary[0] = 0x0D;
            boundary[1] = 0x0A;
        }
    }

    public static boolean arrayequals(byte[] a,
                                      byte[] b,
                                      int count)
    {
        for (int i = 0; i < count; i++)
        {
            if (a[i] != b[i])
            {
                return false;
            }
        }
        return true;
    }

    protected int findByte(byte value,
                           int pos)
    {
        for (int i = pos; i < tail; i++)
        {
            if (buffer[i] == value)
            {
                return i;
            }
        }

        return -1;
    }

    protected int findSeparator()
    {
        int first;
        int match = 0;
        int maxpos = tail - boundaryLength;
        for (first = head;
             (first <= maxpos) && (match != boundaryLength);
             first++)
        {
            first = findByte(boundary[0], first);
            if (first == -1 || (first > maxpos))
            {
                return -1;
            }
            for (match = 1; match < boundaryLength; match++)
            {
                if (buffer[first + match] != boundary[match])
                {
                    break;
                }
            }
        }
        if (match == boundaryLength)
        {
            return first - 1;
        }
        return -1;
    }

    @Override
	public String toString()
    {
        StringBuffer sbTemp = new StringBuffer();
        sbTemp.append("boundary='");
        sbTemp.append(String.valueOf(boundary));
        sbTemp.append("'\nbufSize=");
        sbTemp.append(bufSize);
        return sbTemp.toString();
    }

    public class MalformedStreamException
        extends IOException
    {

        public MalformedStreamException()
        {
            super();
        }


        public MalformedStreamException(String message)
        {
            super(message);
        }
    }


    public class IllegalBoundaryException
        extends IOException
    {

        public IllegalBoundaryException()
        {
            super();
        }

        public IllegalBoundaryException(String message)
        {
            super(message);
        }
    }
}
