import java.util.HashMap;

public class Aether {

    //private Index   index;
    private Keywords    keywords;
    private PageRank    pageRank;
    private SearchResults   searchResults;

    public Aether() {
        super();
        //this.index = new Index();
        this.keywords = new Keywords();
        //this.pageRank = new PageRank();
    }

    private void search(String searchPhrase) {
        SearchPhrase sf = new SearchPhrase(searchPhrase);
        HashMap<Website, Double> websitesFound = new HashMap<>();
        
        for (String comb : sf.getComninations()) {
            if (this.keywords.containsKey(comb)) {
                for (Website wsFound : this.keywords.get(comb)) {
                    if (websitesFound.containsKey(wsFound)) {
                        double oldRank = websitesFound.get(wsFound);
                        websitesFound.replace(wsFound, oldRank, 2*oldRank);
                    }
                    else {
                        websitesFound.put(wsFound, Math.pow(2,comb.length())*(0.85*wsFound.getRank()+0.15*wsFound.getVisitors()));
                    }
                }
            }
        }
        this.searchResults = new SearchResults(websitesFound);

    }

    public static void main(String[] args) {
        String[] frase = {"Hola","que","hace"};
        String comb = "";
        for (String word : frase) comb+=word+" ";


        System.out.println(comb.trim());
    }

}