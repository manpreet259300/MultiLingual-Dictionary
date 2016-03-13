package Dictionary;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class DictionaryDriver extends Configured implements Tool {
   public static void main(String[] args) throws Exception {
      Configuration conf = new Configuration();
      System.exit(ToolRunner.run(conf, new DictionaryDriver(), args));
   }
   
   public int run(String[] args) throws Exception {
      Job job = new Job(getConf(), "my dictionary");
      job.setJarByClass(DictionaryDriver.class);
      job.setMapperClass(DictionaryMapper.class);
      job.setReducerClass(DictionaryReducer.class);
   //   job.setNumReduceTasks(0); // Added for the purpose of testing
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);
      job.setInputFormatClass(TextInputFormat.class);
      FileInputFormat.addInputPath(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1]));
      return job.waitForCompletion(true) ? 0:1;
   }
}

