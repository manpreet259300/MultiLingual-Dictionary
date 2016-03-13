package Dictionary;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class CacheDriver extends Configured implements Tool {

   public static void main(String[] args) throws Exception {
	  
      Configuration conf = new Configuration();
      System.exit(ToolRunner.run(conf, new CacheDriver(), args));
      
   }
   
   public int run(String[] args) throws Exception {
	   if (args.length != 2) {
		   System.err.println("usage: hadoop jar . . . -files <sidefile> <in> <out>");
		   System.exit(1);
	   }
	   Job job = new Job(getConf(), "james dictionary");
	      job.setJarByClass(CacheDriver.class);
	      job.setMapperClass(CacheMapper.class);
	      job.setReducerClass(CacheReducer.class);
	      job.setOutputKeyClass(Text.class);
	      job.setOutputValueClass(Text.class);
	      job.setInputFormatClass(TextInputFormat.class);
	      job.setOutputFormatClass(TextOutputFormat.class);
	      FileInputFormat.addInputPath(job, new Path(args[0]));
	      FileOutputFormat.setOutputPath(job, new Path(args[1]));
	
	      
	      return job.waitForCompletion(true) ? 0:1;
   }
   

}

