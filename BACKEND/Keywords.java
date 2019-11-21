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

    public Keywords(Index index) {
        super(100000); //Initial keyword size
        this.loadKeywords(index);
        this.writeKeywords();
    }
    
    public void updateKeywordsFromWebsites(Index index) {
        this.clear();
        this.clearKeywordsTXT();
        this.loadKeywordsFromWebsites(index);
        this.writeKeywords();
    }

    public void loadKeywords(Index index) {
        this.loadKeywordsFromKeywordsTXT(index);
    }

    private void clearKeywordsTXT() {
        try {
            FileWriter fw = new FileWriter(pathFile);
            PrintWriter pw = new PrintWriter(fw);
            pw.println("");
            pw.close();
          }
        catch(IOException ex) {
            System.out.println(pathFile+" can not be accessed.");
        }
    }

    private void loadKeywordsFromWebsites(Index index) {
      

            for (Website websiteInKeyword : index.getWebsites()) { 

                if (websiteInKeyword != null) {
                    for (String keyword : websiteInKeyword.getKeywords()) {
                        if (keyword != null) {
                            if (this.containsKey(keyword)) { //If the keyword is already in Keywords hashmap
                                LinkedList<Website> oldWebsites = this.get(keyword),
                                                    newWebsites = oldWebsites;
                                newWebsites.add(websiteInKeyword);
                                this.replace(keyword, oldWebsites, newWebsites);
        
                            }
                            else { //If the keyword is not in Keywords hashmap
                                LinkedList<Website> newKeywordWebsites = new LinkedList<>();
                                newKeywordWebsites.add(websiteInKeyword);
                                this.put(keyword, newKeywordWebsites);
                            }
                        }
                    
                    }
                }
                
            }
       
        
    }

    private void loadKeywordsFromKeywordsTXT(Index index) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathFile));
            String line;
            while((line = br.readLine()) != null){
                if (line.length() > 0) {
                    String[] data = line.toLowerCase().split("---");
                    LinkedList<Website> kwWebsites = new LinkedList<>(); //websites of each keyword
                    for(String url : data[1].split(",")) {
                        int websiteID = index.getDns().get(url);
                        kwWebsites.add(index.getWebsites()[websiteID]);
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
        } catch (NullPointerException npe) {
            System.out.println("Sitios no validos encontrados en un keyword");
        }
    }

    public boolean writeKeywords() {
        try {
            FileWriter fw = new FileWriter(pathFile);
            PrintWriter pw = new PrintWriter(fw);

            this.forEach((keyword, websites) -> {
                String urlsString = "";
                for (Website ws : websites) urlsString += ws.getUrl() + ",";
                pw.println(keyword.trim().toLowerCase()+"---"+urlsString.trim().toLowerCase());
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