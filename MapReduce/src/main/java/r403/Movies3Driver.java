package r403;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Movies3Driver {
    public static void main(String[] args) throws Exception {
        // Paths
        String inputPath = "src/main/resources/studios.csv";
        String outputPath = "output/results_movies3";

        // Delete output directory if it exists
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path outputDir = new Path(outputPath);
        if (fs.exists(outputDir)) {
            fs.delete(outputDir, true);
        }

        // Job setup
        Job job = Job.getInstance(conf, "Studios in FR");
        job.setJarByClass(Movies3Driver.class);

        // Set Mapper and Reducer
        job.setMapperClass(Movies3Mapper.class);
        job.setReducerClass(Movies3Reducer.class);

        // Set output types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Set input and output paths
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, outputDir);

        // Run job and display results
        boolean success = job.waitForCompletion(true);
        if (success) {
            System.out.println("===== Résultats MapReduce = Selection: studios where country is FR =====");
            Path resultFile = new Path(outputPath + "/part-r-00000");
            if (fs.exists(resultFile)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(resultFile)));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } else {
                System.err.println("Le fichier de résultats n'existe pas : " + resultFile);
            }
        } else {
            System.err.println("Le job a échoué !");
        }

        System.exit(success ? 0 : 1);
    }
}
