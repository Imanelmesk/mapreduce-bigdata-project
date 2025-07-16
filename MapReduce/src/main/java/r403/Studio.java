package r403;

public class Studio {
    static String[] fields;
    public static void fromLine(String line){
        fields = line.split(";");
    }
    public static int getId() throws NumberFormatException{
        return Integer.parseInt(fields[0]);
    }
    public static String getCountry(){
        return fields[2];
    }
}
