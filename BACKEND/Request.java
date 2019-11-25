//Agustin Quintanar A01636142
//Jonathan Chavez A01636160
public class Request {
	private String type;
	private String query;
	private String value;
	
	Request(String inputStream){
		int divisor1 = inputStream.indexOf(':', 0);
		int divisor2 = inputStream.indexOf(':', divisor1+2);
		
		this.type = inputStream.substring(0, divisor1);
		this.query = inputStream.substring(divisor1+2, divisor2);
		this.value = inputStream.substring(divisor2+2, inputStream.length());
	}

	public String getType() {
		return type;
	}

	public String getQuery() {
		return query;
	}

	public String getValue() {
		return value;
	}
	
	public String toString() {
		String res = "--- REQUEST ---";
		res += "METHOD: " + this.type;
		res += "\nQUERY: " + this.query;
		res += "\nVALUE: " + this.value;
		res += "\n";
		return res;
	}
	
	
}
