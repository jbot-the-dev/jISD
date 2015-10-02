package com.jisd.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;

import com.jisd.parser.ISDParser;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

/*
 * Connect to NOAA ftp, grab an isd/ish file and test the parser on it.
 */

public class Test {
    
    private FTPClient noaaFTP;
    private String ftpRoot;

    public Test(){
	this.ftpRoot = "/pub/data/noaa/";
	try {
	    this.noaaFTP = new FTPClient();
	    this.noaaFTP.connect("ftp.ncdc.noaa.gov");
	    this.noaaFTP.login("anonymous", "");
	} catch (IllegalStateException | IOException | FTPIllegalReplyException
		| FTPException e1) {
	    this.noaaFTP = null;
	}
    }
    
    private void downloadWeather(String usaf, String wban, Timestamp t){
	downloadWeather(usaf + "-" + wban, t);
    }

    private void downloadWeather(String station,Timestamp t){
	try {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(t);

	    GZIPInputStream a = new GZIPInputStream(streamData(this.ftpRoot
		    + calendar.get(Calendar.YEAR)
		    + "/" + station + "-" + calendar.get(Calendar.YEAR)
		    + ".gz").getInputStream());
	    BufferedReader bfReader = new BufferedReader(new InputStreamReader(a));
//	    String temp = null;
	    ISDParser parser = new ISDParser(bfReader);
//	    while((temp = bfReader.readLine()) != null){
//		parser.parse(temp);
//	    }
	    //	    Set<String> types = new HashSet<String>();
	    //	    while((temp = bfReader.readLine()) != null){
	    ////		types.add(temp.substring(41,46));
	    //            }
	    ////	    for (String type: types){
	    ////		System.out.println(type);
	    ////	    }
	    //	    
	    a.close();


	} catch (IOException e) {
	    e.printStackTrace();
	} catch (IllegalStateException e) {
	    e.printStackTrace();
	} catch (NullPointerException e){

	}
    }
    
    public ByteArrayInOutStream streamData(String path){
	/*
	 *  Would return a BufferedReader if it weren't for zipped files.
	 */

	if (this.noaaFTP != null){
	    ByteArrayInOutStream s = new ByteArrayInOutStream();
	    try {
		this.noaaFTP.download(path, s, 0, null);
		return s;
	    } catch (Exception e) {}
	}
	return null;



    }

    public static void main(String[] args) {
	Test test = new Test();
	test.downloadWeather("725315-94870",new Timestamp(1443544668646L));

    }

}

class ByteArrayInOutStream extends ByteArrayOutputStream {
    /**
     * Creates a new ByteArrayInOutStream. The buffer capacity is
     * initially 32 bytes, though its size increases if necessary.
     */
    public ByteArrayInOutStream() {
	super();
    }

    /**
     * Creates a new ByteArrayInOutStream, with a buffer capacity of
     * the specified size, in bytes.
     *
     * @param   size   the initial size.
     * @exception  IllegalArgumentException if size is negative.
     */
    public ByteArrayInOutStream(int size) {
	super(size);
    }

    /**
     * Creates a new ByteArrayInputStream that uses the internal byte array buffer 
     * of this ByteArrayInOutStream instance as its buffer array. The initial value 
     * of pos is set to zero and the initial value of count is the number of bytes 
     * that can be read from the byte array. The buffer array is not copied. This 
     * instance of ByteArrayInOutStream can not be used anymore after calling this
     * method.
     * @return the ByteArrayInputStream instance
     */
    public ByteArrayInputStream getInputStream() {
	// create new ByteArrayInputStream that respect the current count
	ByteArrayInputStream in = new ByteArrayInputStream(this.buf, 0, this.count);

	// set the buffer of the ByteArrayOutputStream 
	// to null so it can't be altered anymore
	this.buf = null;

	return in;
    }
}