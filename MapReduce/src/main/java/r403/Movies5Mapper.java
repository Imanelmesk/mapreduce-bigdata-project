package r403;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class Movies5Mapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();


        try {
            String[] fields = line.split(";");
            if (fields.length >= 3 && !fields[2].equals("release")) { // Skip header
                String releaseDate = fields[2]; // release date: yyyy-mm-dd
                String title = fields[1];       // movie title

                // Validate release date format
                if (!releaseDate.isEmpty() && !title.isEmpty()) {

                    context.write(new Text("LAST_MOVIE"), new Text(releaseDate + "|" + title));
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing line: " + line);
            e.printStackTrace();
        }
    }
}
