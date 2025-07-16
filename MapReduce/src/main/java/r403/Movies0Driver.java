package r403;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
public class Movies0Driver
        extends Configured
        implements Tool
{
    @Override
    public int run(String[] args) throws Exception
    {
// time to start
        Long initTime = System.currentTimeMillis();
//parameters checking
        if (args.length != 2) {
            System.err.println("Usage: ModeleMapReduceDriver <inputpath> <outputpath>");
            System.exit(-1);
        }
//map-reduce job creation
//Configuration conf = this.getConf();
//local version
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");
        conf.set("mapreduce.framework.name", "local");

        Job job = Job.getInstance(conf, "ModeleMapReduce Job");
        job.setJarByClass(Movies0Driver.class);
//Mapper and Reducer classes definition
        job.setMapperClass(Movies0Mapper.class);
        /*job.setCombinerClass(ModeleMapReduceCombiner.class);*/
        job.setReducerClass(Movies0Reducer.class);
////input definition
//TextInputFormat => keys=LongWritable, values=Text
        FileInputFormat.setInputDirRecursive(job, false); // true if folder
        FileInputFormat.addInputPath(job, new Path(args[0]));
        job.setInputFormatClass(TextInputFormat.class);

//mapper output=reducer input
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);
//output definition: folder and key value types
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
//run the job and measure of the response time
        Long startTime = System.currentTimeMillis();
        boolean success = job.waitForCompletion(true);
        Long endTime = System.currentTimeMillis();
        System.out.println("Job Duration seconds: " + ((endTime-startTime)/1000L));
        System.out.println("Total Duration seconds: " + ((endTime-initTime)/1000L));
        return success ? 0 : 1;
    }
    public static void main(String[] args) throws Exception
    {
        args=new String[2];
        args[0] = "src/main/resources/studios.csv"; // Chemin local vers le fichier d'entrée
        args[1] = "output/results";     // Chemin de sortie sur HDFS
//prepare and run next job
        Movies0Driver driver = new Movies0Driver();
        int exitCode = ToolRunner.run(driver, args);
        System.exit(exitCode);
    }
}
