package r403;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class Movies4Mapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim(); // Remove leading/trailing spaces


        try {
            String[] fields = line.split(";");

            // Ensure we have at least 4 fields and skip the header
            if (fields.length >= 4 && !fields[3].equals("runtime")) {
                String releaseDate = fields[2];  // release date: yyyy-mm-dd
                String runtimeStr = fields[3];   // runtime
                String title = fields[1];        // movie title

                // Validate fields are not empty and runtime is numeric and > 0
                if (!releaseDate.isEmpty() && !runtimeStr.isEmpty() && !title.isEmpty()) {
                    try {
                        int runtime = Integer.parseInt(runtimeStr);
                        if (runtime > 0) { // Filter invalid runtimes
                            String year = releaseDate.split("-")[0]; // Extract the year
                            String combinedValue = runtimeStr + "|" + title;
                            context.write(new Text(year), new Text(combinedValue));
                        } else {
                            System.err.println("Skipping invalid runtime: " + runtimeStr + " for line: " + line);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid runtime: " + runtimeStr + " for line: " + line);
                    }
                } else {
                    System.err.println("Skipping incomplete line: " + line);
                }
            } else {
                System.out.println("Skipping header or invalid line: " + line);
            }
        } catch (Exception e) {
            System.err.println("Error processing line: " + line);
            e.printStackTrace();
        }
    }
}

