public class SearchResult implements Comparable<SearchResult>{
    private Website website;
    private double overallRank;

    public SearchResult(Website website, double overallRank) {
        this.website = website;
        this.overallRank = overallRank;
    }

    public double getOverallRank() {
        return this.overallRank;
    }

    public Website getWebsite() {
        return this.website;
    }

    public int compareTo(SearchResult sResult) {
        if (sResult.overallRank > this.overallRank) return 1;
        else if (sResult.overallRank < this.overallRank) return -1;
        else return 0;
    }

    public String toString() {
        return this.website.getUrl();
    }
}