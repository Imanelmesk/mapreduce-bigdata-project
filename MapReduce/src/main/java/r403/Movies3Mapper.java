package r403;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Movies3Mapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split(";");

        // Ignore header line and ensure valid data
        if (fields.length >= 3 && !fields[0].equals("id")) {
            String country = fields[2]; // Column 3: country
            String studioName = fields[1]; // Column 2: name

            if ("FR".equals(country)) {
                context.write(new Text(studioName), new Text("")); // Emit the studio name
            }
        }
    }
}

