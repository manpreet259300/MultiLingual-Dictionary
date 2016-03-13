package Dictionary;

import org.apache.hadoop.io.LongWritable;
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class DictionaryMapper  extends Mapper<LongWritable, Text, Text, Text> {
      // TODO define class variables for translation, language, and file name
		String key1 ="";
		String languageName ="";
		String languageName1 ="";
		String value1 ="";
		String key2 = "";
		String compositeKey ="";
		String compositeValue = "";


      public void setup(Context context) {    	   
      // TODO determine the language of the current file by looking at it's name
      	FileSplit fileSplit = (FileSplit)context.getInputSplit();
		languageName1 = fileSplit.getPath().getName();
		languageName =languageName1.substring(0,languageName1.indexOf('.'));
      }

      public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      // TODO instantiate a tokenizer based on a regex for the file
		if(value.toString().matches("^[A-Za-z']+\\s+.+[A-Za-z]]$"))
			{		
				// iterate through the tokens of the line, parse, transform and write to context
					String[] s = value.toString().split("\\t");
                	key1 = s[0];
                	value1 = s[1].substring(0, s[1].lastIndexOf("[")).replace(";", ",");
            		key2 = value.toString().substring(value.toString().lastIndexOf("[")+1, value.toString().lastIndexOf("]"));
            		compositeKey = key1 + ": [" + key2+"]";
            		compositeValue = languageName+":"+value1;
            		// System.out.println("composite keys - " + compositeKey + " *** composite value - "+ compositeValue);
            		context.write(new Text(compositeKey), new Text(compositeValue));
           	}
      }
}