package com.jisd.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * When passed a BufferedReader, it tries to parse isd/ish information from it
 */

public class ISDParser {
    public ISDParser(BufferedReader input){
	Set<ISDRecord> isdRecords = new HashSet<ISDRecord>();
	String temp = null;
	try {
	    while((temp = input.readLine()) != null){
		ISDRecord r = new ISDRecord();
		// Parse isd record
		System.out.println(getCDS(temp));
		System.out.println(getMDS(temp));
		isdRecords.add(r);

	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // getCDS - Get Control Data Section and format its output.
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static Map<String, String> getCDS(String p_sRecd)
    {
	//  Extract fields making up the Control Data Section.
	Map<String, String> controlData = new HashMap<String, String>();
	controlData.put("sCDS", 		p_sRecd.substring(0,60));
	controlData.put("sCDS_Fill1",		p_sRecd.substring(0,4)); // Record length excluding guaranteed sections
	controlData.put("sCDS_ID",		p_sRecd.substring(4,10)); // USAF
	controlData.put("sCDS_Wban",		p_sRecd.substring(10,15)); // WBAN
	controlData.put("sCDS_Year",		p_sRecd.substring(15,19)); // Year
	controlData.put("sCDS_Month",		p_sRecd.substring(19,21)); // Month
	controlData.put("sCDS_Day",		p_sRecd.substring(21,23)); // Day
	controlData.put("sCDS_Hour",		p_sRecd.substring(23,25)); // Hour
	controlData.put("sCDS_Minute",		p_sRecd.substring(25,27)); // Minute
	controlData.put("sCDS_Fill2",		p_sRecd.substring(27,60));
	return controlData;
    }  // End of getCDS

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // getMDS - Get MDS section and format its output.
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static Map<String, String> getMDS(String p_sRecd)
    {
	//  Extract fields making up the Mandatory Data Section.
	Map<String, String> mandatoryData = new HashMap<String, String>();
	mandatoryData.put("sMDS", 		p_sRecd.substring(60,105));
	mandatoryData.put("sMDS_Dir",		p_sRecd.substring(60,63)); // Wind dir (deg)
	mandatoryData.put("sMDS_DirQ",		p_sRecd.substring(63,64)); // Wind dir quality
	mandatoryData.put("sMDS_DirType",	p_sRecd.substring(64,65)); // Wind type
	mandatoryData.put("sMDS_Spd",		p_sRecd.substring(65,69)); // Wind speed (m/s)
	mandatoryData.put("sMDS_Fill2",		p_sRecd.substring(69,70)); // Wind speed quality
	mandatoryData.put("sMDS_Clg",		p_sRecd.substring(70,75)); // Ceiling height (m)
	mandatoryData.put("sMDS_Fill3",		p_sRecd.substring(75,78)); // Ceiling quality
	mandatoryData.put("sMDS_Vsb",		p_sRecd.substring(78,84)); // Visibility distance (m)
	mandatoryData.put("sMDS_Fill4",		p_sRecd.substring(84,87)); // Visibility quality
	mandatoryData.put("sMDS_Temp",		p_sRecd.substring(87,92)); // Air temp (C)
	mandatoryData.put("sMDS_Fill5",		p_sRecd.substring(92,93)); // Air temp quality
	mandatoryData.put("sMDS_Dewp",		p_sRecd.substring(93,98)); // Dew point (C)
	mandatoryData.put("sMDS_Fill6",		p_sRecd.substring(98,99)); // Dew point quality
	mandatoryData.put("sMDS_Slp",		p_sRecd.substring(99,104)); // Sea level pressure (hPa)
	mandatoryData.put("sMDS_Fill7",		p_sRecd.substring(104,105)); // Sea level pressure quality

	if(mandatoryData.get("sMDS_Dir").equals("999"))
	{
	    mandatoryData.put("sMDS_Dir", null);	// Data missing
	}

	if(mandatoryData.get("sMDS_DirType").equals("V"))
	{
	    // Wind direction varies, which doesn't translate to integer
	    // just use the direction type flag to identify
	    mandatoryData.put("sMDS_Dir", null);
	}

	if(mandatoryData.get("sMDS_Spd").equals("9999"))
	{    
	    mandatoryData.put("sMDS_Spd",null);	// Data missing
	}
	else
	{
	    float fWork     = Float.parseFloat(mandatoryData.get("sMDS_Spd"));
	    fWork /= 10.0;	// Reverse scale
	    mandatoryData.put("sMDS_Spd",String.format("%.2f", fWork));
	}

	if(mandatoryData.get("sMDS_Clg").equals("99999"))
	{
	    mandatoryData.put("sMDS_Clg",null);	// Data missing
	}
	else
	{
	    try
	    {
		int iWork = Integer.parseInt(mandatoryData.get("sMDS_Clg"));
		mandatoryData.put("sMDS_Clg",String.format("%d", iWork));
	    }
	    catch (Exception e)
	    {
		mandatoryData.put("sMDS_Clg", null);	// Data missing
	    }
	}

	if(mandatoryData.get("sMDS_Vsb").equals("999999"))
	{
	    mandatoryData.put("sMDS_Vsb",null);	// Data missing
	}
	else
	{
	    int iWork     = Integer.parseInt(mandatoryData.get("sMDS_Vsb"));
	    mandatoryData.put("sMDS_Vsb",String.format("%d", iWork));
	}

	if(mandatoryData.get("sMDS_Temp").equals("9999"))
	{
	    mandatoryData.put("sMDS_Temp",null);	// Data missing
	}
	else
	{
	    float fWork     = Float.parseFloat(mandatoryData.get("sMDS_Temp"));
	    fWork /= 10.0; // Reverse scale
	    mandatoryData.put("sMDS_Temp",String.format("%.2f", fWork));
	}

	if(mandatoryData.get("sMDS_Dewp").equals("9999"))
	{
	    mandatoryData.put("sMDS_Dewp",null);	// Data missing
	}
	else
	{
	    float fWork     = Float.parseFloat(mandatoryData.get("sMDS_Dewp"));
	    fWork /= 10.0;
	    mandatoryData.put("sMDS_Dewp",String.format("%.2f", fWork));
	}

	if(mandatoryData.get("sMDS_Slp").equals("99999"))
	{
	    mandatoryData.put("sMDS_Slp",null);	// Data missing
	}
	else
	{
	    float fWork     = Float.parseFloat(mandatoryData.get("sMDS_Slp"));
	    fWork /= 10.0; // Reverse scale
	    mandatoryData.put("sMDS_Slp",String.format("d", fWork));
	}
	return mandatoryData;
    }  // End of getMDS

}
