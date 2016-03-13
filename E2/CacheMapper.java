package Dictionary;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class CacheMapper extends Mapper<LongWritable, Text, Text, Text> {
    String fileName = "", language = "";
    BufferedReader br = null;
    String newValue = null;
    String compositeKey =null;
    String compositeValue =null, value1 =null, key1 =null, key2 = null;
    String str;
    
    public HashMap<String, String> hm = new HashMap<String, String>();
    Path[] cachedFilePaths = null;


    public void setup(Context context) throws IOException, InterruptedException {
        // TODO: determine the name of the additional language based on the file name
        cachedFilePaths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        fileName = cachedFilePaths[0].getName();
        language = fileName.substring(0, fileName.lastIndexOf('.'));

        // TODO: OPTIONAL: depends on your implementation -- create a HashMap of translations (word, part of speech, translations) from output of exercise 1
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
	        while((str = bufferedReader.readLine())!=null){
	            if(str.toString().matches("^[A-Za-z]+\\s+.+[A-Za-z]]$"))
	                {       
	                    // iterate through the tokens of the line, parse, transform and write to context
	                        String[] s = str.toString().split("\\t");
	                        key1 = s[0];
	                        value1 = s[1].substring(0, s[1].lastIndexOf("[")).replace(";", ",");
	                        key2 = str.toString().substring(str.toString().lastIndexOf("[")+1, str.toString().lastIndexOf("]"));
	                        compositeKey = key1 + ": [" + key2+"]";
	                        compositeValue = language+":"+value1;
	                        hm.put(compositeKey, compositeValue);
	                }
	        }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // TODO: perform a map-side join between the word/part-of-speech from exercise 1 and the word/part-of-speech from the distributed cache file
        String partOfSpeech = null, word = null, finalValue = null;
        String[] strValue = value.toString().split("\\t");
        String localKey=null, localValue=null;

        word = strValue[0];
        String dictionaryOf5 = strValue[1];
        if(hm.containsKey(word))
        {
            finalValue = dictionaryOf5+"|"+hm.get(word);
        }
        else
        {
            finalValue = dictionaryOf5+"|latin:N/A";
        }

        context.write(new Text(word), new Text(finalValue));
    }
}