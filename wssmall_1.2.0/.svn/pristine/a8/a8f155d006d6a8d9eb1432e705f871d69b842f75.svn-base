/*
 * Created on 2003-12-28 To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.powerise.ibss.framework;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
/**
 * @author Administrator 为了提高xml 的解析性能，将xml parser做成pool来处理，类似于Connection pool
 */
public class XMLParserPool
{
	private DocumentBuilder[] DBPool;
	private int[] DBStatus;
	private long[] DBLockTime, DBCreateDate;
	private String[] DBID;
	private int maxParsers, minParsers, curParsers, lastParser;
	//available: set to false on destroy, checked by getConnection()
	private boolean available = true;
	private static XMLParserPool hPool = null;
	public static DocumentBuilder getParser() throws FrameException
	{
		if (hPool == null)
		{
			hPool = new XMLParserPool();
		}
		return hPool.getUseParser();
	}
	public static void freeParser(DocumentBuilder db) throws FrameException
	{
		if (hPool == null)
		{
			hPool = new XMLParserPool();
		}
		hPool.freeUsedParser(db);
	}
	public static int getSize() throws FrameException
	{
		if (hPool == null)
		{
		
			hPool = new XMLParserPool();
		}
		return hPool.getUsedSize();
	}
	public int getUsedSize()
	{
		return curParsers;
	}
	public XMLParserPool() throws FrameException
	{
		setupPool();
	}
	private void setupPool() throws FrameException
	{
		minParsers=20;maxParsers=50;
		DBPool = new DocumentBuilder[maxParsers];
		DBStatus = new int[maxParsers];
		DBLockTime = new long[maxParsers];
		DBCreateDate = new long[maxParsers];
		DBID = new String[maxParsers];
		curParsers = minParsers;
		for (int i = 0; i < curParsers; i++)
		{
			createParser(i);
		}
	}
	public  DocumentBuilder getUseParser() throws FrameException
	{
		DocumentBuilder db = null;
		for (int outerloop = 0; outerloop < 10; outerloop++)
		{
			int iSearch = lastParser + 1;
			if (iSearch >= curParsers)
				iSearch = 0; //首先从已建立的Parser中查找
			int loop = 0;
			boolean bFind = false;
			do
			{
				synchronized (DBStatus)
				{
					if (DBStatus[iSearch] < 1) //空闲
					{
						db = DBPool[iSearch];
						DBStatus[iSearch] = 1; //占用
						lastParser = iSearch;
						bFind = true;
						break;
					}
					else
					{
						loop++;
						iSearch++;
						if (iSearch >= curParsers)
							iSearch = 0;
					}
				}
			}
			while ((bFind == false) && (loop < curParsers));
			if (bFind)
			{
				break;
			}
			else
			{
				synchronized (this)
				{
					if (curParsers < maxParsers)
					{
						createParser(curParsers);
						curParsers++;
					}
				}
			}
			try { Thread.sleep(1000); } //sleep 一段时间，以免造成冲突
				catch(InterruptedException e) {}
			
		}
		return db;
	}
	private DocumentBuilder createParser(int iParser) throws FrameException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
			DBStatus[iParser] = 0;
			DBID[iParser] = DBPool[iParser].toString();
			//DBCreateDate[iParser] = now.getTime();
		}
		catch (ParserConfigurationException e)
		{
			throw new FrameException(-22992001, "创建XML生成器时出现异常。");
		}
		return db;
	}
	public int idOfParser(DocumentBuilder db)
	{
		int match = 0;
		String strTag;
		strTag = db.toString();
		match = -1;
		for (int i = 0; i < curParsers; i++)
		{
			if (DBID[i].equals(strTag))
			{
				match = i;
				break;
			}
		}
		return match;
	}
	public  void freeUsedParser(DocumentBuilder db) throws FrameException
	{
		int iMatch;
		iMatch = idOfParser(db);
		if (iMatch >= 0)
		{
			DBStatus[iMatch] = 0;
		}
	}
}
