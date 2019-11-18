//Jonathan Chavez A01636160
//Agustin Quintanar A01636142


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Website {
	
	private String url,
				   title,
				   metaDescription,
				   rawHtml;

	private String[] keywords;
  
	private ArrayList<String> linksTo = new ArrayList<String>();

	private int visitors = 0;

	private Date created;

	private double rank; 

	
	

	Website(String url, String rawHtml){
		this.url = url;
		this.rawHtml = rawHtml;
		this.parseHtml();
		this.created = new Date();
		this.rank = 0;
	}

	
	//Auxiliar method called in constructor that parses the HTML of the object and sets the properties [metaTags, metaDescription, title, outside_links]
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

		//Parsing outside links
		HashSet<String> uniqueLinks = new HashSet<String>();
		int beginIndex = 0;
		
		while(beginIndex != -1) {
			int linkOpeningTagIndex = this.rawHtml.indexOf("<a href=\"", beginIndex);
			
			if(linkOpeningTagIndex == -1) {
				break;
			} else {
				linkOpeningTagIndex += 9; // Adds the size of "<a href=" string
			}
			int linkClosingTagIndex = this.rawHtml.indexOf("\"", linkOpeningTagIndex);
		
			beginIndex = linkClosingTagIndex;
			
			//Trims subpages and only leaves domain name
			String link = rawHtml.substring(linkOpeningTagIndex, linkClosingTagIndex);
			int idxFirstSlash = link.indexOf('/', 8);
			if(idxFirstSlash != -1) {
				link = link.substring(0, idxFirstSlash);
			}
			
			if(!uniqueLinks.contains(link)) {
				uniqueLinks.add(link);
				this.linksTo.add(link);
			}
		}
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
		
		res += "THIS PAGE POINTS TO: ";
		for(int i=0; i<this.linksTo.size(); i++) {
			res += this.linksTo.get(i);
			if(i != this.linksTo.size()-1) res+= ", ";
		}
		res += "\n";

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

	public void setRank(double rank) {
		this.rank = rank;
	}

	public double getRank() {
		return this.rank;
	}
	
	public String[] getKeywords() {
		return this.keywords;
	}
	
	public ArrayList<String> getLinksTo() {
		return this.linksTo;
	}

	public static void main(String[] args) {
		 Website trivago = new Website("trivago.com", "<html><head><title>Trivago -  El mejor lugar para tu viaje</title><meta charset=\"UTF-8\">\n" + 
		 		"  <meta name=\"description\" content=\"Free Web tutorials\">\n" + 
		 		"  <meta name=\"keywords\" content=\"HTML,CSS,XML,JavaScript\">\n" + 
		 		"  <meta name=\"author\" content=\"John Doe\">\n" + 
		 		"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head><body><h1>Encuentra hoteles <a href=\"https://hoteles.com\">aquí</a> y <a href=\"https://hola.com\"></a>allá <a href=\"https://hola.com/caca\"></a></h1></body></html>");
		 System.out.println(trivago);
	}
	
	
}