package r403;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.FSDataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Movies4Driver {
    public static void main(String[] args) throws Exception {
        String inputPath = "src/main/resources/movies.csv";
        String outputPath = "output/results_movies4";

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        Path outputDir = new Path(outputPath);
        if (fs.exists(outputDir)) {
            fs.delete(outputDir, true); // Supprimer le répertoire existant
            System.out.println("Output directory deleted: " + outputDir);
        }

        Job job = Job.getInstance(conf, "Longest Movie Per Year with Title");
        job.setJarByClass(Movies4Driver.class);

        job.setMapperClass(Movies4Mapper.class);
        job.setReducerClass(Movies4Reducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, outputDir);

        boolean success = job.waitForCompletion(true);
        if (success) {
            System.out.println("===== Résultats MapReduce : Longest movie per year =====");
            Path resultFile = new Path(outputPath, "part-r-00000");
            FSDataInputStream inputStream = fs.open(resultFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
            inputStream.close();
        } else {
            System.err.println("Le job a échoué !");
        }

        System.exit(success ? 0 : 1);
    }
}
