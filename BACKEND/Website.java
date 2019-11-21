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
	
	private HashSet<String> linksTo = new HashSet<String>();

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

	Website(String url, String title, String metaDescription, String rawHtml, String[] keywords, 
	HashSet<String> linksTo, int visitors, Date created, double rank){
		this.url = url;
		this.title = title;
		this.metaDescription = metaDescription;
		this.rawHtml = rawHtml;
		this.keywords = keywords;
		this.linksTo = linksTo;
		this.visitors = visitors;
		this.created = created;
		this.rank = rank;
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
		for(int i=0; i<keywords.length; i++) {
			this.keywords[i] = this.keywords[i].toLowerCase().trim();
		}
		
		
		//Parsing outside links
		HashSet<String> uniqueLinks = new HashSet<String>();
		int beginIndex = 0;
		
		while(beginIndex != -1) {
			int linkOpeningTagIndex = this.rawHtml.indexOf("<a href=\"", beginIndex);
			if(linkOpeningTagIndex == -1) {
				return;
			}
			linkOpeningTagIndex = this.rawHtml.indexOf(">", linkOpeningTagIndex+1);
			
			
			if(linkOpeningTagIndex == -1) {
				return;
			} else {
				linkOpeningTagIndex += 1; // Adds the size of ">" string
			}
			
			int linkClosingTagIndex = this.rawHtml.indexOf("<", linkOpeningTagIndex);
			beginIndex = linkOpeningTagIndex+1;
			
			//Trims subpages and only leaves domain name
			String link = rawHtml.substring(linkOpeningTagIndex, linkClosingTagIndex);
			int idxFirstSlash = link.indexOf('/', 8);
			if(idxFirstSlash != -1) {
				link = link.substring(0, idxFirstSlash);
			}
			link = link.toLowerCase().trim();
			
			if(!uniqueLinks.contains(link)) {
				uniqueLinks.add(link);
				this.linksTo.add(link);
			}
		}
	}
	
	public String toString() {
		// String res = "- Website (Object) -\n";
		
		// res += "URL: "+this.url+"\n";
		
		// res += "TITLE: "+this.title+"\n";
		
		// res += "DESCRIPTION: "+this.metaDescription+"\n";
		
		// res += "META TAGS: ";
		// for(int i=0; i<this.keywords.length; i++) {
		// 	res += keywords[i];
		// 	if(i != this.keywords.length-1) res+= ", ";
		// }
		// res += "\n";
		
		// res += "VISITORS: "+this.visitors+"\n";
		
		// res += "CREATED: "+this.created+"\n";
		
		// res += "THIS PAGE POINTS TO: ";
		// for(String pageLinked : this.linksTo) {
		// 	res += pageLinked;
		// }
		// res += "\n";
		
		return this.url;
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
	
	public HashSet<String> getLinksTo() {
		return this.linksTo;
	}

	public String getMetaDescription() {
		return this.metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public static void main(String[] args) {
		 Website p1 = new Website("https://blogdelperro.com", "<!DOCTYPE html>\n" + 
		 		"<html lang=\"en\">\n" + 
				 "\n" +
				"<title> Titulo de la pagina de perros </title>"+ 
		 		"<head>\n" + 
		 		"    <meta charset=\"UTF-8\" />\n" + 
		 		"    <meta name=\"keywords\" content=\"Perros    ,    perros limpios, el blog del perro, el rincón del perro, perros flacos, perros occisos, perros muertos, noticias sobre perros, perros, perruno\" />\n" + 
		 		"    <meta name=\"description\" content=\"Lo mejor de las noticias matutinas, entérate del chisme en la ciudad, vive, disfruta. Conéctate con tu comunidad a través del blog del perro.\" />\n" + 
		 		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" + 
		 		"    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n" + 
		 		"    <style>\n" + 
		 		"        body {\n" + 
		 		"            background: rgb(80, 67, 67);\n" + 
		 		"            color: white;\n" + 
		 		"            font-family: 'Times New Roman', Times, serif;\n" + 
		 		"        }\n" + 
		 		"        \n" + 
		 		"        a {\n" + 
		 		"            color: lightpink;\n" + 
		 		"        }\n" + 
		 		"    </style>\n" + 
		 		"    <title></title>\n" + 
		 		"</head>\n" + 
		 		"\n" + 
		 		"<body>\n" + 
		 		"    <div>\n" + 
		 		"        <h1>Perros enkuerados</h1>\n" + 
		 		"        <section>\n" + 
		 		"            <div>\n" + 
		 		"                <p>Mira lo que los perros no quieren que veas</p>\n" + 
		 		"                <ol>\n" + 
		 		"                    <li>\n" + 
		 		"                        <a href=\"../Comida canina/index.html\">https://comida.net</a\n" + 
		 		"              >\n" + 
		 		"            </li>\n" + 
		 		"            <li>\n" + 
		 		"              <a href=\"../El rincón de Guauf/index.html\">https://rincondelguauf.com</a>\n" + 
		 		"                    </li>\n" + 
		 		"                    <li>\n" + 
		 		"                        <a href=\"../Perros locos/index.html\">https://perroslocos.com</a>\n" + 
		 		"                    </li>\n" + 
		 		"                </ol>\n" + 
		 		"            </div>\n" + 
		 		"        </section>\n" + 
		 		"    </div>\n" + 
		 		"</body>\n" + 
		 		"\n" + 
		 		"</html>");
		 System.out.println(p1);
	}
	
	
}