package com.lunar.ingest.recordreader;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.lunar.ingest.inputsplit.RedisInputSplit;

public class RedisRecordReader extends RecordReader<Text, Text> {
	
	private static final Logger logger = Logger.getLogger(RedisRecordReader.class);
	
	private Iterator<Entry<String,String>> keyValueMapIter = null;
	private Text key = new Text();
	private Text value = new Text();
	private float keyValuesProcessed = 0, totalKeyValues = 0;
	private Entry<String, String> current = null;

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		return key;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return keyValuesProcessed / totalKeyValues;
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		
		String host = split.getLocations()[0];
		String hashKey = ((RedisInputSplit) split).getHashKey();
	
		logger.info("Connecting to "+host+" and reading from "+hashKey);
		
		Jedis jedis = new Jedis(host);
		jedis.connect();
		jedis.getClient().setTimeoutInfinite();
		
		totalKeyValues = jedis.hlen(hashKey);
		keyValueMapIter = jedis.hgetAll(hashKey).entrySet().iterator();
		
		logger.info("Fetched "+totalKeyValues+" from "+hashKey);
		jedis.close();
		jedis.disconnect();
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		
			if(keyValueMapIter.hasNext()){
				current = keyValueMapIter.next();
				key.set(current.getKey());
				value.set(current.getValue());
				return true;
			}
			
			return false;
	}

}
