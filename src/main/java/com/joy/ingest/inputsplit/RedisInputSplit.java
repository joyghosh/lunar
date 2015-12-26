package com.joy.ingest.inputsplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

public class RedisInputSplit extends InputSplit implements Writable{

	private String location;
	private String hashKey;
	
	public RedisInputSplit() {
		//default constructor.
	}
	
	public RedisInputSplit(String location, String hashKey){
		this.location = location;
		this.hashKey = hashKey;
	}
	
	@Override
	public long getLength() throws IOException, InterruptedException {
		return 0;
	}

	@Override
	public String[] getLocations() throws IOException, InterruptedException {
		return new String[] { location };
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.location = in.readUTF();
		this.hashKey = in.readUTF();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(location);
		out.writeUTF(hashKey);
	}
	
	public String getHashKey(){
		return this.hashKey;
	}
}
