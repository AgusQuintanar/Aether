public class Aether {

    private Index   index;
    private Keywords    keywords;
    private SearchResults   searchResults;
    private PageRank    pageRank;

    public Aether() {
        this.websites = new Website[10000000];
        this.keywords = new Keywords();
        this.searchPhrase = "";
        this.searchResults = new SearchResults();
        this.addAllPages();
        //Parsear paginas

        this.pageRank = new PageRank(this.websites);

    }

    private void search(String searchPhrase) {
        
        
    }

}