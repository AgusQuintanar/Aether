import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList; 

//Agustin Quintanar A01636142
//Jonathan Chavez A01636160
public class Index {

    private Website[] websites;
    private int count; 
    private HashMap<String, Integer> dns;

    private final String websitesPath = "../WEBSITES";

    public Index() {
        this.count = 0;
        this.dns = new HashMap<>(1000000);
        this.websites = new Website[1000000];
        this.loadMetadata();
    }

    public void addWebsitesFromWindow() {
        AddNewWebsiteWindow addNewWebsiteWindow = new AddNewWebsiteWindow(this);
    }

    public void addWebsite(String publicUrl, String privateUrl, String rawHtml) {
        Website newWebsite = new Website(publicUrl, privateUrl, rawHtml);
        this.websites[this.count] = newWebsite; //Adds the website previously created to websites[]
        this.dns.put(newWebsite.getPublicUrl(), this.count); //Inserts to the DNS the new website and increases the count
        File websiteFolder = this.createEmptyWebsiteFiles();
        this.createWebsiteMetadataFile(websiteFolder);
        this.createMetaDescriptionFile(websiteFolder);
        this.count++;
    }

    public File createEmptyWebsiteFiles() {
        String newWebsiteFolderPath = websitesPath+"/"+Integer.toString(this.count);

        File newWebsiteFolder = new File(newWebsiteFolderPath);
        newWebsiteFolder.mkdirs(); //Creates empty website folder 


        return newWebsiteFolder;

    }

  
    public void loadMetadata() {
    
            File[] websitesDirectory = new File(this.websitesPath).listFiles(File::isDirectory); //Array of websites directories

            for (File websiteFolder : websitesDirectory) { 
                //System.out.println("folder: " + websiteFolder);
                try {
                    Website newWebsite = this.buildWebsite(websiteFolder); 
                    this.websites[Integer.parseInt(websiteFolder.getName())] = newWebsite; //Adds the website previously created to websites[]
                    this.dns.put(newWebsite.getPublicUrl(), Integer.parseInt(websiteFolder.getName())); //Inserts to the DNS the new website and increases the count
                    this.count++;
                }
                catch (NullPointerException npe) {
                    System.out.println("Null value found while loading metadata.");
                }
                catch(IndexOutOfBoundsException iobe) {
                    
                } 
            }
        
        
    }

    public void writeMetadata() { 
        File[] websitesDirectory = new File(this.websitesPath).listFiles(File::isDirectory);

        for (File websiteFolder : websitesDirectory) {
            //System.out.println("folderPro: " + websiteFolder);
            try {
                this.createWebsiteMetadataFile(websiteFolder);
                this.createMetaDescriptionFile(websiteFolder);
            } catch(NullPointerException npe) {

            }
   
        }
    }

    private boolean createMetaDescriptionFile(File websiteFolder) {
        String metaDescriptionPath = websiteFolder.getPath()+"/metaDescription.txt";

        try {
            FileWriter fw = new FileWriter(metaDescriptionPath);
            PrintWriter pw = new PrintWriter(fw);

            //System.out.println("index Website: "+Integer.parseInt(websiteFolder.getName()));
            Website website = this.websites[Integer.parseInt(websiteFolder.getName())];

            pw.println(website.getMetaDescription());
            pw.close();
            return true;
        }
        catch(IOException ex) {
            System.out.println(metaDescriptionPath+" can not be accessed.");
        }
        return false;
    }

    private boolean createWebsiteMetadataFile(File websiteFolder) {
        String metadataPath = websiteFolder.getPath()+"/website.metadata";

        try {
            FileWriter fw = new FileWriter(metadataPath);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("publicUrl!---!privateUrl!---!TITLE!---!KEYWORDS!---!LINKS_TO!---!CREATED!---!VISITORS!---!RANKS");

            Website website = this.websites[Integer.parseInt(websiteFolder.getName())];

            String  keywordString = "",
                    linksToString = "";
            for (String keyword : website.getKeywords()) keywordString += keyword+",";
            for (String link : website.getLinksTo()) linksToString += link+",";

            pw.println(
                website.getPublicUrl() + "!---!" +
                website.getPrivateUrl() + "!---!" +
                website.getTitle() + "!---!" +
                keywordString + "!---!" +
                linksToString + "!---!" +
                website.getCreated().toString() + "!---!" +
                Integer.toString(website.getVisitors()) + "!---!" +
                Double.toString(website.getRank())
            );
            pw.close();
            return true;
        }
        catch(IOException ex) {
            System.out.println(metadataPath+" can not be accessed.");
        }
        return false;
    }

    private Website buildWebsite(File websiteFolder) {

            String metadataPath = websiteFolder.getPath()+"/website.metadata";

            String[] websiteMetadata = this.readWebsiteMetaData(metadataPath);
    
            String  publicUrl = websiteMetadata[0],
                    privateUrl = websiteMetadata[1],
                    title = websiteMetadata[2],
                    metaDescription = getFileContent(websiteFolder.getPath()+"/metaDescription.txt");
            String[]    keywords = websiteMetadata[3].split(",");

        try {
            HashSet<String> linksTo = new HashSet<>();
        
            for (String link : websiteMetadata[4].split(",")) linksTo.add(link);
            Date created = new Date();
            try {
                created = new SimpleDateFormat("dd/MM/yyyy").parse(websiteMetadata[5]);
            } catch(ParseException pe) {
                //System.out.println("Error while parsing date.");
            }
            int visitors = Integer.parseInt(websiteMetadata[6]);
            double  rank = Double.parseDouble(websiteMetadata[7]);
    
            return new Website(publicUrl, privateUrl, title, metaDescription, keywords, linksTo, visitors, created, rank);
        } 
        catch(IndexOutOfBoundsException iobe) {
            return new Website(publicUrl, privateUrl, "");
        }
        catch(NullPointerException npe) {
            return new Website(publicUrl, privateUrl, "");
        }


       
        
    }



    private String[] readWebsiteMetaData(String pathFile) {
        String[] websiteMetadata = new String[8];

        try {
            BufferedReader br = new BufferedReader(new FileReader(pathFile));
            br.readLine(); //Discards header file
            String line = br.readLine(); 
            if (line != null && line.length()>0) {
                websiteMetadata = line.toLowerCase().split("!---!");
            }
            br.close();
        }
        catch (FileNotFoundException ex){
            System.out.println(pathFile + " not found!. "+ex);
        }
        catch (IOException ex){
            System.out.println("There was an I/O error.");
        }
        return websiteMetadata;
    }

    private String getFileContent(String pathFile) {
        String fileContent = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(pathFile));
            String line;
            while((line = br.readLine()) != null){
                if (line.length() > 0) {
                    fileContent += line += "\n";
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

        return fileContent;
    }

    public int getCount() {
        return this.count;
    }

    public Website[] getWebsites() {
        return this.websites;
    }

    public HashMap<String, Integer> getDns() {
        return this.dns;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setWebsites(Website[] websites) {
        this.websites = websites;
    }



    public static void main(String[] args) {

    }

}