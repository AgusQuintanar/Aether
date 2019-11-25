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
		Aether aether = new Aether();

		Keywords kw = aether.getKeywords();

		for (String s : kw.keySet()) {
			System.out.println("key: "+s);
			trie.add(s);
		}

		try {
			while (true) {
				System.out.println("Ready");
				server = new ServerSocket(1120);
				client = server.accept();
				input = client.getInputStream();
				String inputString = Test.inputStreamAsString(input);
				Request request = new Request(inputString);

				if (request.getType().equals("GET")) {
					if (request.getQuery().equals("term")) {
						// Autocompletes with trie
						ArrayList<String> results = trie.autocomplete(request.getValue(), 6);
						Thread.sleep(50);
						responder = new Socket("127.0.0.1", 1121);
						PrintWriter writer = new PrintWriter(responder.getOutputStream());
						writer.print(results);
						writer.flush();
						responder.close();
					} else if (request.getQuery().equals("results")) {
						// Returns search results

						Thread.sleep(50);
						responder = new Socket("127.0.0.1", 1121);
						PrintWriter writer = new PrintWriter(responder.getOutputStream());
						aether.search(request.getValue());
						SearchResult[] results = aether.getSearchResults().getSearchResults();
						for (SearchResult sr : results) {


							String title = sr.getWebsite().getTitle(),
								   publicUrl = sr.getWebsite().getPublicUrl(),
								   description = sr.getWebsite().getMetaDescription();

							if (title.length() > 0 && publicUrl.length() > 0 && description.length() > 0) {
								// Title
								writer.println(title);
								// PublicUrl
								writer.println(publicUrl);
								// // PrivateUrl
								writer.println(sr.getWebsite().getPublicUrl());
								// MetaDescription
								writer.println(description);
								// Rank
								writer.println(sr.getOverallRank() + "-#-#");
							}

							

						}

						writer.flush();
						responder.close();
					}
				}

				client.close();
				server.close();
				Thread.sleep(50);

			}
		} catch (Exception e) {
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
