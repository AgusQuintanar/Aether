import java.util.HashMap;

public class Aether {

    public Index   index;
    public Keywords    keywords;
    private PageRank    pageRank;
    private SearchResults   searchResults;

    public Aether() {
        this.index = new Index();
        this.keywords = new Keywords(this.index);
        this.pageRank = new PageRank(this.index.getCount());
    }


    public Index getIndex() {
        return this.index;
    }

    public Keywords getKeywords() {
        return this.keywords;
    }

    public SearchResults getSearchResults() {
        return this.searchResults;
    }

    public void updateWebsitesRanks() {
        Website[] websitesWithNewRanks = this.pageRank.calculateRanks(this.index);
        this.index.setWebsites(websitesWithNewRanks);
        this.index.writeMetadata();
    }

    public void updateKeywordsFromWebsites() {
        this.keywords.updateKeywordsFromWebsites(this.index);
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
        
        Aether aether = new Aether();
        aether.updateWebsitesRanks();

        //for (int i=0; i<aether.getIndex().getCount(); i++) System.out.println(aether.getIndex().getWebsites()[i].getUrl()+", ");

        //aether.updateKeywordsFromWebsites();

        // System.out.println("Resultados de la busqueda");
        // aether.search("comedor");

        // for (SearchResult searchResult : aether.getSearchResults().getSearchResults()) System.out.println(searchResult + ", ");
        
        //aether.getIndex().addWebsitesFromWindow();
    }

}