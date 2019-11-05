//Jonathan Chavez A01636160
//Agustin Quintanar A01636142

import java.util.Date;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Website {
	
	private String url,
				   title,
				   metaDescription,
				   rawHtml;

	private String[] keywords;

	private int visitors = 0;

	private Date created;

	private long id, //Website identifier
				 l; //Number of outgoing links from this website

	private HashMap B; //Set of websites linking to this website
				 
	private double rank; 

	
	
	Website(String url, String rawHtml, long l, HashMap B){
		this.url = url;
		this.rawHtml = rawHtml;
		this.parseHtml();
		this.created = new Date();
		this.id = generateID();
		this.rank = 0;
		this.l = l;
		this.B = B;
	}

	Website(long l, HashMap B, long id){
		this.id = id;
		this.rank = 0;
		this.l = l;
		this.B = B;
	}

	private long generateID() {
		long lastID = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader("lastID.txt"));
			String line = br.readLine();
			if(isLong(line)) lastID = Long.parseLong(line); 
			br.close();
		}
		catch (FileNotFoundException ex){
			System.out.println("File lastID.txt is missing. "+ex);
		}
		catch (IOException ex){
			System.out.println("It ocuured an I/O error.");
		}

		try {
			FileWriter fw = new FileWriter("lastID.txt");
			PrintWriter pw = new PrintWriter(fw);
			pw.println(Long.toString(lastID+1));
			pw.close();
		}
		  catch(IOException ex) {
			System.out.println("There is no access to the file named lastID.txt");
		}
		return lastID+1;
	}

	private boolean isLong(String s) {
		try {
			double d = Long.parseLong(s);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	//Metodo auxiliar que toma el html del objeto y asigna las propiedades [metaTags, metaDescription, title]
	private void parseHtml() {
		
		//Parsing title
		int titleOpeningTagIndex = this.rawHtml.indexOf("<title>") + 7;
		int titleClosingTagIndex = this.rawHtml.indexOf("</title>");
		this.title = rawHtml.substring(titleOpeningTagIndex, titleClosingTagIndex);
		
		//Parsing description
		int descriptionOpeningTagIndex = this.rawHtml.indexOf("<meta name=\"description\" content=\"")+34;
		int descriptionClosingTagIndex = 0;
		for(int i=descriptionOpeningTagIndex; this.rawHtml.charAt(i) != ('"'); i++) {
			descriptionClosingTagIndex = i;
		}
		this.metaDescription = rawHtml.substring(descriptionOpeningTagIndex, descriptionClosingTagIndex+1);
		
		//Parsing keywords
		int keywordsOpeningTagIndex = this.rawHtml.indexOf("<meta name=\"keywords\" content=\"")+31;
		int keywordsClosingTagIndex = 0;
		for(int i=keywordsOpeningTagIndex; this.rawHtml.charAt(i) != ('"'); i++) {
			keywordsClosingTagIndex = i;
		}
		this.keywords = rawHtml.substring(keywordsOpeningTagIndex, keywordsClosingTagIndex+1).split(",");
	}
	
	public String toString() {
		String res = "- Website (Object) -\n";
		
		res += "URL: "+this.url+"\n";
		
		res += "TITLE: "+this.title+"\n";
		
		res += "DESCRIPTION: "+this.metaDescription+"\n";
		
		res += "META TAGS: ";
		for(int i=0; i<this.keywords.length; i++) {
			res += keywords[i];
			if(i != this.keywords.length-1) res+= ", ";
		}
		res += "\n";
		
		res += "VISITORS: "+this.visitors+"\n";
		
		res += "CREATED: "+this.created+"\n";
		
		return res;
	}
	
	public int getVisitors() {
		return visitors;
	}

	public void visit() {
		this.visitors++;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getRawHtml() {
		return rawHtml;
	}

	public Date getCreated() {
		return created;
	}

	public HashMap getB() {
		return this.B;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public double getRank() {
		return this.rank;
	}

	public long getL() {
		return this.l;
	}

	public long getId() {
		return this.id;
	}

	public static void main(String[] args) {
		// Website trivago = new Website("trivago.com", "<html><head><title>Trivago -  El mejor lugar para tu viaje</title><meta charset=\"UTF-8\">\n" + 
		// 		"  <meta name=\"description\" content=\"Free Web tutorials\">\n" + 
		// 		"  <meta name=\"keywords\" content=\"HTML,CSS,XML,JavaScript\">\n" + 
		// 		"  <meta name=\"author\" content=\"John Doe\">\n" + 
		// 		"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head><body><h1>Encuentra hoteles aqui</h1></body></html>");
		// System.out.println(trivago);
	}
	
	
}
