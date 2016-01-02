package com.lunar;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.lunar.ingest.format.RedisInputFormat;

public class App 
{
    public static void main( String[] args ) throws IOException, 
    			ClassNotFoundException, InterruptedException{
    	
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        
        if(otherArgs.length != 3){
        	System.err.println("Usage: RedisInput <redis hosts> <hash name> <output>");
        	System.exit(1);
        }
        
        String hosts = otherArgs[0];
        String hashKey = otherArgs[1];
        Path outputDir = new Path(otherArgs[2]);
        
        
        Job job = Job.getInstance(conf, "Redis Input");
		job.setJarByClass(App.class);

		// Use the identity mapper
		job.setNumReduceTasks(0);

		job.setInputFormatClass(RedisInputFormat.class);
		RedisInputFormat.setRedisHosts(job, hosts);
		RedisInputFormat.setRedisHashKey(job, hashKey);

		job.setOutputFormatClass(TextOutputFormat.class);
		TextOutputFormat.setOutputPath(job, outputDir);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.waitForCompletion(true);
//		System.exit(job.waitForCompletion(true) ? 0 : 3);
    }
}
