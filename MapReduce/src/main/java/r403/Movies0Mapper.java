package r403;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class Movies0Mapper
        extends Mapper<LongWritable, Text, Text, DoubleWritable>{
    private Text outKey = new Text();
    private DoubleWritable outValue = new DoubleWritable();
    public void map(LongWritable inKey, Text inValue, Context context)
            throws IOException, InterruptedException{
        try{
            if (inKey.get() == 0L) return;
            String line = inValue.toString();
            Studio.fromLine(line);
            String countrytudio=Studio.getCountry();
            outKey.set(countrytudio);
            outValue.set(Double.valueOf(1.0));
            context.write(outKey, outValue);
        }
        catch(Exception e){}
    }
}
