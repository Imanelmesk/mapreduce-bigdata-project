package r403;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class Movies2Mapper extends Mapper<LongWritable, Text, Text, Text> {
    private Text studioName = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Ignore l'en-tête (première ligne)
        if (key.get() == 0 && value.toString().contains("id")) {
            return;
        }

        String line = value.toString();

        String[] fields = line.split(";"); // Utilise le point-virgule comme séparateur

        if (fields.length > 1) { // Assurez-vous qu'il y a assez de colonnes
            String name = fields[1].trim(); // Colonne "name"
            if (!name.isEmpty()) {
                studioName.set(name);
                context.write(studioName, new Text("")); // Émet <nom du studio, "">
            }
        }
    }
}

