package r403;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Movies1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text year = new Text();
    private final static IntWritable one = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Ignore l'en-tête (première ligne)
        if (key.get() == 0 && value.toString().contains("idmdb")) {
            return;
        }

        String line = value.toString();


        String[] fields = line.split(";"); // Utiliser le point-virgule comme séparateur

        // Assurez-vous que le fichier a suffisamment de colonnes
        if (fields.length > 2) {
            String releaseDate = fields[2]; // Colonne "release"
            if (releaseDate != null && !releaseDate.isEmpty()) {
                String[] dateParts = releaseDate.split("-"); // Format YYYY-MM-DD
                if (dateParts.length == 3) {
                    year.set(dateParts[0]); // Récupère l'année
                    context.write(year, one); // Émet <année, 1>
                }
            }
        }
    }
}

