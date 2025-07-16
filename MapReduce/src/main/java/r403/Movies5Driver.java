package r403;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class Movies5Driver implements Tool {
    private Configuration conf;

    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public Configuration getConf() {
        return conf;
    }

    @Override
    public int run(String[] args) throws Exception {
        // Input/Output directories
        String inputPath = "src/main/resources/movies.csv";
        String outputPath = "output/results_question5";

        // Delete existing output directory
        FileSystem fs = FileSystem.get(conf);
        Path outputDir = new Path(outputPath);
        if (fs.exists(outputDir)) {
            fs.delete(outputDir, true);
            System.out.println("Deleted existing output directory: " + outputPath);
        }

        // Job configuration
        Job job = Job.getInstance(conf, "Last Movie Finder");
        job.setJarByClass(Movies5Driver.class);

        // Set Mapper and Reducer classes
        job.setMapperClass(Movies5Mapper.class);
        job.setReducerClass(Movies5Reducer.class);

        // Set output key and value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Set input and output paths
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        // Submit the job
        boolean success = job.waitForCompletion(true);
        if (success) {
            System.out.println("===== Résultats MapReduce : last movie released =====");
            Path resultFile = new Path(outputPath + "/part-r-00000");
            if (fs.exists(resultFile)) {
                try (org.apache.hadoop.fs.FSDataInputStream inputStream = fs.open(resultFile);
                     java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        } else {
            System.err.println("Le job a échoué !");
        }
        return success ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Movies5Driver(), args);
        System.exit(exitCode);
    }
}
