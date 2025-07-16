package r403;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Movies5Reducer extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String latestDate = "0000-00-00"; // Initialize with a very old date
        String latestMovie = "";

        for (Text value : values) {
            String[] fields = value.toString().split("\\|");
            if (fields.length == 2) {
                String date = fields[0];
                String title = fields[1];

                // Compare dates lexicographically (works for yyyy-mm-dd format)
                if (date.compareTo(latestDate) > 0) {
                    latestDate = date;
                    latestMovie = title;
                }
            }
        }

        context.write(new Text("Last Movie"), new Text(latestMovie + " (" + latestDate + ")"));
    }
}

