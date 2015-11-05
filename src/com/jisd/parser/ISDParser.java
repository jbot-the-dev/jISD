package com.jisd.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * When passed a BufferedReader, it tries to parse isd/ish information from it
 */


public class ISDParser {
	
	private static final Map<String, Integer> ADDSections;
	static {
		Map<String, Integer> addSections = new HashMap<String, Integer>();
		for (Integer i = 1;i <= 9; ++i){

			if (i <= 1){
				addSections.put("AB" + i.toString(), 10);
				addSections.put("AC" + i.toString(), 6);
				addSections.put("AD" + i.toString(), 22);
				addSections.put("AE" + i.toString(), 15);
				addSections.put("AG" + i.toString(), 7);
				addSections.put("AJ" + i.toString(), 17);
				addSections.put("AK" + i.toString(), 15);
				addSections.put("AM" + i.toString(), 21);
				addSections.put("AN" + i.toString(), 12);
				addSections.put("CI" + i.toString(), 31);
				addSections.put("CO" + i.toString(), 8);
				addSections.put("CR" + i.toString(), 10);
				addSections.put("CW" + i.toString(), 17);
				addSections.put("ED" + i.toString(), 11);
				addSections.put("GE" + i.toString(), 22);
				addSections.put("GF" + i.toString(), 26);
				addSections.put("GH" + i.toString(), 31);
				addSections.put("GJ" + i.toString(), 8);
				addSections.put("GK" + i.toString(), 7);
				addSections.put("GL" + i.toString(), 9);
				addSections.put("GM" + i.toString(), 33);
				addSections.put("GN" + i.toString(), 31);
				addSections.put("GO" + i.toString(), 22);
				addSections.put("GP" + i.toString(), 34);
				addSections.put("GQ" + i.toString(), 17);
				addSections.put("GR" + i.toString(), 17);
				addSections.put("HL" + i.toString(), 7);
				addSections.put("IA" + i.toString(), 6);
				addSections.put("IB" + i.toString(), 30);
				addSections.put("IC" + i.toString(), 28);
				addSections.put("KE" + i.toString(), 15);
				addSections.put("KF" + i.toString(), 9);
				addSections.put("MA" + i.toString(), 15);
				addSections.put("MD" + i.toString(), 14);
				addSections.put("ME" + i.toString(), 9);
				addSections.put("MF" + i.toString(), 15);
				addSections.put("MG" + i.toString(), 15);
				addSections.put("MH" + i.toString(), 15);
				addSections.put("MK" + i.toString(), 27);
				addSections.put("OC" + i.toString(), 8);
				addSections.put("SA" + i.toString(), 8);
				addSections.put("ST" + i.toString(), 20);
				addSections.put("UA" + i.toString(), 13);
				addSections.put("WA" + i.toString(), 9);
				addSections.put("WD" + i.toString(), 23);
				addSections.put("WG" + i.toString(), 36);
			}
			if (i <= 2){
				addSections.put("AY" + i.toString(), 8);
				addSections.put("AZ" + i.toString(), 8);
				addSections.put("CB" + i.toString(), 13);
				addSections.put("CH" + i.toString(), 18);
				addSections.put("CN" + i.toString(), 21);
				addSections.put("KC" + i.toString(), 17);
				addSections.put("KD" + i.toString(), 12);
				addSections.put("KG" + i.toString(), 14);
				addSections.put("OB" + i.toString(), 31);
				addSections.put("UG" + i.toString(), 12);
				if (i > 1){
					addSections.put("IA" + i.toString(), 12);
					addSections.put("IB" + i.toString(), 16);
				}
			}
			if (i <= 3){
				addSections.put("CF" + i.toString(), 9);
				addSections.put("CG" + i.toString(), 11);
				addSections.put("CT" + i.toString(), 10);
				addSections.put("CU" + i.toString(), 16);
				addSections.put("CV" + i.toString(), 28);
				addSections.put("CX" + i.toString(), 29);
				addSections.put("KB" + i.toString(), 13);
				addSections.put("OA" + i.toString(), 11);
				addSections.put("OD" + i.toString(), 14);
				addSections.put("OE" + i.toString(), 19);
				addSections.put("RH" + i.toString(), 12);
			}
			if (i <= 4){
				addSections.put("AA" + i.toString(), 11);
				addSections.put("AO" + i.toString(), 11);
				addSections.put("AL" + i.toString(), 10);
				addSections.put("AP" + i.toString(), 9);
				addSections.put("KA" + i.toString(), 13);
				if (i > 2){
					addSections.put("CN" + i.toString(), 19);
				}
			}
			if (i <= 5){
				addSections.put("AW" + i.toString(), 6);
			}
			if (i <= 6){
				addSections.put("AH" + i.toString(), 18);
				addSections.put("AI" + i.toString(), 18);
				addSections.put("AX" + i.toString(), 9);
				addSections.put("GA" + i.toString(), 16);
				addSections.put("GD" + i.toString(), 15);
				addSections.put("GG" + i.toString(), 18);
			}
			if (i <= 7){
				addSections.put("MV" + i.toString(), 6);
				addSections.put("MW" + i.toString(), 6);
			}
			if (i <= 8){
				addSections.put("AT" + i.toString(), 12);
			}
			if (i > 1){
				addSections.put("CO" + i.toString(), 11);
			}
			addSections.put("AU" + i.toString(), 11);
			
		}
		
		ADDSections = Collections.unmodifiableMap(addSections);
	}
	
    public ISDParser(BufferedReader input){
	Set<ISDRecord> isdRecords = new HashSet<ISDRecord>();
	String temp = null;
	try {
	    while((temp = input.readLine()) != null){
		ISDRecord r = new ISDRecord();
		// Parse isd record
		//System.out.println(temp);
		//System.out.println(getCDS(temp));
		//System.out.println(getMDS(temp));
		getCDS(temp);
		getMDS(temp);
		getADS(temp);
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
	controlData.put("sCDS", 			p_sRecd.substring(0,60));
	controlData.put("sCDS_Fill1",		p_sRecd.substring(0,4)); // Record length excluding guaranteed sections
	controlData.put("sCDS_ID",			p_sRecd.substring(4,10)); // USAF
	controlData.put("sCDS_Wban",		p_sRecd.substring(10,15)); // WBAN
	controlData.put("sCDS_Year",		p_sRecd.substring(15,19)); // Year
	controlData.put("sCDS_Month",		p_sRecd.substring(19,21)); // Month
	controlData.put("sCDS_Day",			p_sRecd.substring(21,23)); // Day
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
	mandatoryData.put("sMDS", 			p_sRecd.substring(60,105));
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
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // getADS - Get Additional Data Section and format its output.
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static Map<String, String> getADS(String p_sRecd)
    {
    //  Extract fields making up the Additional Data Section.
    	Map<String, String> additionalData = new HashMap<String, String>();
    	p_sRecd = p_sRecd.substring(105);
    	if (p_sRecd.substring(0, 3).equals("ADD")){
    		String mySub = p_sRecd.substring(3);
    		while(mySub.length() > 3){
    			String key = mySub.substring(0, 3);
    			if (ADDSections.containsKey(key)){
    				mySub = mySub.substring(ADDSections.get(mySub.substring(0, 3)));
    			}else if (key.equals("REM")){
    				getREM(mySub);
    				break;
    			}else if (key.equals("EQD")){
    				getEQD(mySub);
    				break;
    			}else if (key.equals("QNN")){
    				getQNN(mySub);
    				break;
    			}else{
    				System.out.println(mySub.substring(0, 3));
    				System.out.println(mySub);
    				break;
    			}
    		}
    	}
		return additionalData;
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // getREM - Get Remarks Section and format its output.
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static Map<String, String> getREM(String p_sRecd)
    {
    //  Extract fields making up the Remarks Section.
    	Map<String, String> remarks = new HashMap<String, String>();
    	Integer remLength = Integer.parseInt(p_sRecd.substring(6,9));
    	if (p_sRecd.substring(remLength+9).length() >= 3){
    		String key = p_sRecd.substring(remLength + 9, remLength + 12);
    		if (key.equals("EQD")){
				getEQD(p_sRecd);
			}else if (key.equals("QNN")){
				getQNN(p_sRecd);
			}else{
				System.out.println(key);
				System.out.println(p_sRecd);
			}
    	}
    	p_sRecd = p_sRecd.substring(0,remLength+9);
		return remarks;
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // getEQD - Get Element Quality Data Section and format its output.
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static Map<String, String> getEQD(String p_sRecd)
    {
    //  Extract fields making up the Element Quality Data Section.
    	Map<String, String> eqData = new HashMap<String, String>();
    	
    	Integer eqdLength = p_sRecd.indexOf("QNN");
    	if (eqdLength != -1){
    		getQNN(p_sRecd.substring(eqdLength));
    		p_sRecd = p_sRecd.substring(0,eqdLength);
    	}
		return eqData;
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // getQNN - Get Original Observation Data Section and format its output.
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static Map<String, String> getQNN(String p_sRecd)
    {
    //  Extract fields making up the Original Observation Data Section.
    	Map<String, String> obData = new HashMap<String, String>();
		return obData;
    }

}
