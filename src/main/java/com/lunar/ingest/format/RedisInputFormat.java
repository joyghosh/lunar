package com.lunar.ingest.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.log4j.Logger;

import com.lunar.ingest.inputsplit.RedisInputSplit;
import com.lunar.ingest.recordreader.RedisRecordReader;

public class RedisInputFormat extends InputFormat<Text, Text> {

	public static final String REDIS_HOSTS_CONF = "mapred.redisinputformat.hosts";
	public static final String REDIS_HASH_KEY_CONF = "mapred.redisinputformat.key";
	private static final Logger logger = Logger.getLogger(RedisInputFormat.class);
	
	
	/**
	 * Sets the CSV string of Redis hosts.
	 * 
	 * @param job
	 *            The job conf
	 * @param hosts
	 *            The CSV string of Redis hosts
	 */
	public static void setRedisHosts(Job job, String hosts) {
		job.getConfiguration().set(REDIS_HOSTS_CONF, hosts);
	}

	/**
	 * Sets the key of the hash to write to.
	 * 
	 * @param job
	 *            The job conf
	 * @param hashKey
	 *            The name of the hash key
	 */
	public static void setRedisHashKey(Job job, String hashKey) {
		job.getConfiguration().set(REDIS_HASH_KEY_CONF, hashKey);
	}

	
	@Override
	public RecordReader<Text, Text> createRecordReader(InputSplit split,
			TaskAttemptContext context) throws IOException, InterruptedException {
		return new RedisRecordReader();
	}

	@Override
	public List<InputSplit> getSplits(JobContext context) throws IOException,
			InterruptedException {
		
		String hosts = context.getConfiguration().get(REDIS_HOSTS_CONF);
		if(hosts == null || hosts.isEmpty()){
			throw new IOException(REDIS_HOSTS_CONF+ " is not set.");
		}
		
		String hashKey = context.getConfiguration().get(REDIS_HASH_KEY_CONF);
		if(hashKey == null || hashKey.isEmpty()){
			throw new IOException(REDIS_HASH_KEY_CONF+ " is not set.");
		}
		
		List<InputSplit> splits = new ArrayList<InputSplit>();
		for(String host : hosts.split(",")){
			splits.add(new RedisInputSplit(host, hashKey));
		}
		
		logger.info("Input splits found for processing: "+splits.size());
		return splits;
	}

}
