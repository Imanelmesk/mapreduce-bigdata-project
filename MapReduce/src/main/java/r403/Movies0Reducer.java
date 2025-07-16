package r403;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class Movies0Reducer
        extends Reducer<Text, DoubleWritable, Text, DoubleWritable>
{
    private Text outKey;
    private DoubleWritable outValue = new DoubleWritable();
    @Override
    public void reduce(Text inKey, Iterable<DoubleWritable> inValues, Context context)
            throws IOException, InterruptedException
    {
        outKey = inKey;
        int sum=0;
        for (DoubleWritable inValue : inValues) {
            sum++;
        }
        double result = sum;
        outValue.set(result);
        System.out.println(outKey+" - "+sum);
        context.write(outKey, outValue);
    }
}
