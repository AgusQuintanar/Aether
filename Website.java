//Jonathan Chavez A01636160
import java.util.Date;

public class Website {
	
	private String url;
	private String title;
	private String metaDescription;
	private String keywords[];
	
	
	private String rawHtml;
	private int visitors = 0;
	private Date created;
	
	
	Website(String url, String rawHtml){
		this.url = url;
		this.rawHtml = rawHtml;
		this.parseHtml();
		this.created = new Date();
	}
	
	//Método auxiliar que toma el html del objeto y asigna las propiedades [metaTags, metaDescription, title]
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

	public static void main(String[] args) {
		Website trivago = new Website("trivago.com", "<html><head><title>Trivago -  El mejor lugar para tu viaje</title><meta charset=\"UTF-8\">\n" + 
				"  <meta name=\"description\" content=\"Free Web tutorials\">\n" + 
				"  <meta name=\"keywords\" content=\"HTML,CSS,XML,JavaScript\">\n" + 
				"  <meta name=\"author\" content=\"John Doe\">\n" + 
				"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head><body><h1>Encuentra hoteles aquí</h1></body></html>");
		System.out.println(trivago);
	}
	
	
}
