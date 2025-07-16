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

public class Movies2Driver {
    public static void main(String[] args) throws Exception {
        // Chemins d'entrée et de sortie codés en dur
        String inputPath = "src/main/resources/studios.csv";
        String outputPath = "output/results_movies2";

        // Configuration Hadoop
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "List Studios Names");

        job.setJarByClass(Movies2Driver.class);
        job.setMapperClass(Movies2Mapper.class);
        job.setReducerClass(Movies2Reducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Spécifie les chemins d'entrée et sortie
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        // Supprimer le répertoire de sortie s'il existe
        FileSystem fs = FileSystem.get(conf);
        Path outputDir = new Path(outputPath);
        if (fs.exists(outputDir)) {
            fs.delete(outputDir, true);
            System.out.println("Output directory deleted: " + outputDir);
        }

        // Lancer le job
        boolean success = job.waitForCompletion(true);

        // Lire et afficher les résultats dans la console
        if (success) {
            System.out.println("===== Résultats MapReduce =====");
            Path resultFile = new Path(outputPath, "part-r-00000");

            if (fs.exists(resultFile)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(resultFile)));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // Affiche chaque ligne du fichier
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


