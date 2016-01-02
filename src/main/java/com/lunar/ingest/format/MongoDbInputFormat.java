package com.lunar.ingest.format;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class MongoDbInputFormat extends InputFormat<Text, Text> {

	@Override
	public RecordReader<Text, Text> createRecordReader(InputSplit arg0,
			TaskAttemptContext arg1) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InputSplit> getSplits(JobContext arg0) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
