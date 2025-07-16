package r403;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Movies4Reducer extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        int maxRuntime = Integer.MIN_VALUE;
        String longestMovie = "";

        for (Text value : values) {
            String[] parts = value.toString().split("\\|");
            try {
                int runtime = Integer.parseInt(parts[0]);
                String movieTitle = parts[1];

                if (runtime > maxRuntime) {
                    maxRuntime = runtime;
                    longestMovie = movieTitle;
                }
            } catch (Exception e) {
                System.err.println("Error parsing value: " + value.toString());
            }
        }

        String result = longestMovie + " (" + maxRuntime + " min)";
        context.write(key, new Text(result)); // Key = Year, Value = Movie title (runtime)
    }
}



