package r403;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class Movies6Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private String maxCountry = "";
    private int maxCount = 0;

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }


        if (sum > maxCount) {
            maxCount = sum;
            maxCountry = key.toString();
        }
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        System.out.println("Final Result - Country: " + maxCountry + ", Studios: " + maxCount);
        context.write(new Text("Country with Max Studios: " + maxCountry), new IntWritable(maxCount));
    }
}

