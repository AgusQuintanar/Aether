import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Test {
	

    public static void main(String[] args) {
        ServerSocket server;
        Socket client, responder;
        InputStream input;
        Trie trie = new Trie();
        
        trie.add("perro");
		trie.add("pedigree");
		trie.add("perros en venta");
		trie.add("perro chihuahua");
		trie.add("perros adoptados");
		trie.add("perro dibujo");
		trie.add("perro aguayo");
		trie.add("pug");
		trie.add("perros pequeños");
		trie.add("los perros cagan mucho");
		trie.add("los perros son malos");
		trie.add("lola la perra");
		
        
	        try {
	        	while(true) {
	        		System.out.println("Ready");
	        		server = new ServerSocket(1120);
	        		client = server.accept();
		            input = client.getInputStream();
		            String inputString = Test.inputStreamAsString(input);
		            Request request = new Request(inputString);
		            
		            if(request.getType().equals("GET")) {
		            	if(request.getQuery().equals("term")) {
		            		//Autocompletes with trie
		            		System.out.println(request);
		            		ArrayList<String> results = trie.autocomplete(request.getValue(), 6);
		            		Thread.sleep(50);
			        		responder = new Socket("127.0.0.1", 1121);
		            		PrintWriter writer = new PrintWriter(responder.getOutputStream());
				            writer.print(results);
				            writer.flush();
				            responder.close();
		            	}
		            	else if(request.getQuery().equals("results")) {
		            		//Returns search results
		            		System.out.println(request);
		            		
		            		Thread.sleep(50);
			        		responder = new Socket("127.0.0.1", 1121);
		            		PrintWriter writer = new PrintWriter(responder.getOutputStream());
		            		
		            		//Title
		            		writer.println("Multimedios - Ridículos bailando banda todo el día");
		            		//PublicUrl
		            		writer.println("https://www.multimedios.com/");
		            		//PrivateUrl
		            		writer.println("https://www.multimedios.com/");
		            		//MetaDescription
		            		writer.println("Sigue aquí toda la programación de Multimedios Televisión y Multimedios Radio; noticias, deportes y entretenimiento. Sitio de libre acceso.");
		            		//Rank
		            		writer.println("890");

		            		
		            		
				            writer.flush();
				            responder.close();
		            	}
		            }
		            
		            
		            client.close();
		            server.close();
		            Thread.sleep(50);
		    
	        	}
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
    }

    public static String inputStreamAsString(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        return sb.toString();
    }

}