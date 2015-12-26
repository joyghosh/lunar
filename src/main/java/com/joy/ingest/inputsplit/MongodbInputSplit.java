package com.joy.ingest.inputsplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

public class MongodbInputSplit extends InputSplit implements Writable{

	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getLength() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getLocations() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
