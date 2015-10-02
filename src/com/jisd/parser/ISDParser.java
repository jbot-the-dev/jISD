package com.jisd.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
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
		isdRecords.add(r);
		
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

}
