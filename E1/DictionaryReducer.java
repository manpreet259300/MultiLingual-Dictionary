package Dictionary;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;

public class DictionaryReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text word, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // TODO iterate through values, parse, transform, and write to context
        String finalValue = "";
        String value1;
        HashMap<String, String> languageMeaning = new HashMap<String, String>();

//values - french:conversion -same for other language
//keys   - french:[Noun]
        for (Text val : values) {
            String language = null;
            value1 = val.toString();


            switch(value1.charAt(0))
            { 
                case 'f':
                   language= "french";
                   break; 
                case 'g':
                   language = "german";
                   break;
                case 'i':
                   language = "italian";
                   break; 
                case 'p':
                   language = "portuguese";
                   break;
                case 's':
                   language = "spanish";
                   break;
                default:
                    language = null;
            }
    
            if (language != null) {
                if(!languageMeaning.containsKey(language))
                    languageMeaning.put(language, value1);
                else{
                    value1 = languageMeaning.get(language) + "," + value1.substring((value1.indexOf(':')+1), value1.length());
                    languageMeaning.put(language, value1);
                }
            }
        }

        if(!languageMeaning.containsKey("french"))
            languageMeaning.put("french","french:N/A");
        if(!languageMeaning.containsKey("german"))
            languageMeaning.put("german", "german:N/A");
        if(!languageMeaning.containsKey("italian"))
            languageMeaning.put("italian","italian:N/A");
        if(!languageMeaning.containsKey("portuguese"))
            languageMeaning.put("portuguese", "portuguese:N/A");
        if(!languageMeaning.containsKey("spanish"))
            languageMeaning.put("spanish","spanish:N/A");


        finalValue = languageMeaning.get("french")+ "|" + languageMeaning.get("german")+ "|" + languageMeaning.get("italian") + "|" + languageMeaning.get("portuguese") + "|"
                                + languageMeaning.get("spanish");

        context.write(word, new Text(finalValue));
    }
}