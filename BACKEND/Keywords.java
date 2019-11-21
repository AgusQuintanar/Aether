import java.util.HashMap;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Keywords extends HashMap<String,LinkedList<Website>>{

    private static final String pathFile = "keywords.txt";

    public Keywords() {
        super(100000); //Initial keyword size
        this.loadKeywords();
    }
    
    public void loadKeywords() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathFile));
            String line;
            while((line = br.readLine()) != null){
                if (line.length() > 0) {
                    String[] data = line.toLowerCase().split("---");
                    LinkedList<Website> kwWebsites = new LinkedList<>(); //websites of each keyword
                    for(String url : data[1].split(",")) {
                        
                        kwWebsites.add(I);
                    }

                    this.put(data[0], kwWebsites); 
                } 
            }
            br.close();
        }
        catch (FileNotFoundException ex){
            System.out.println(pathFile + " not found!. "+ex);
        }
        catch (IOException ex){
            System.out.println("There was an I/O error.");
        }
    }

    public boolean writeKeywords() {
        try {
            FileWriter fw = new FileWriter(pathFile);
            PrintWriter pw = new PrintWriter(fw);

            this.forEach((keyword, websites) -> {
                String urlsString = "";
                for (Website ws : websites) urlsString += ws.getUrl() + ",";
                pw.println(keyword+"---"+urlsString);
            });
            pw.close();
            return true;
          }
        catch(IOException ex) {
            System.out.println(pathFile+" can not be accessed.");
            return false;
        }
    }
    public static void main(String[] args) {
       
    }


}