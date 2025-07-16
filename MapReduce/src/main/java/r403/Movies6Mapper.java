package r403;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class Movies6Mapper extends Mapper<Object, Text, Text, IntWritable> {
    private static final IntWritable ONE = new IntWritable(1);

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();

        try {
            String[] fields = line.split(";");
            if (fields.length >= 3 && !fields[2].equals("country")) { // Skip header
                String country = fields[2].trim(); // country column
                if (!country.isEmpty()) {
                    context.write(new Text(country), ONE);
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing line: " + line);
            e.printStackTrace();
        }
    }
}

