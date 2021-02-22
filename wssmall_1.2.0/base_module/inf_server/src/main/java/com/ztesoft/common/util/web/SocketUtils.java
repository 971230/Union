package com.ztesoft.common.util.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;

public class SocketUtils {

	public static Socket newClient(int soLinger, int timeout, int sendBuffer,
		int recBuffer) throws Exception {
		Socket socket = new Socket();
		socket.setSoLinger(true, soLinger);
		socket.setSoTimeout(timeout);
		socket.setSendBufferSize(sendBuffer);
		socket.setReceiveBufferSize(recBuffer);
		socket.setTcpNoDelay(true);
		return socket;
	}
	public static String sendAndRecieve(Socket socket, String reqString) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(socket.getOutputStream());
			out.println(reqString);
			out.flush();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			int bufSize = 4096;
			StringWriter received = new StringWriter(bufSize);
			char[] charBuf = new char[bufSize];
			int size = 0;
			do {
				size = in.read(charBuf, 0, bufSize);
				if (size > 0) {
					received.write(charBuf, 0, size);
				}
			} while (size == bufSize);
			return received.toString();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
				}
			}
		}
	}
}
