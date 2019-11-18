import java.util.HashMap;
import java.util.Map;

public class SearchResults {
    private SearchResult[] searchResults;
    
    public SearchResults(HashMap<Website, Double> websitesFound) {
        this.searchResults = new SearchResult[websitesFound.size()];
        int index = 0;
        for(Map.Entry<Website, Double> wsFound : websitesFound.entrySet()) {
            this.searchResults[index++] = new SearchResult(wsFound.getKey(), wsFound.getValue());
        }
        Quicksort.quicksort(this.searchResults);
    }

    public SearchResult[] getSearchResults() {
        return this.searchResults;
    }

    public static void main(String[] args) {

        HashMap<Website, Double> wsFound = new HashMap<>();
        wsFound.put(new Website("Oracle.com"), 52543.34);
        wsFound.put(new Website("StackOverFlow.com"), 155654.23);
        wsFound.put(new Website("Java.com"), 543.87);
        wsFound.put(new Website("w3schools.com"), 3.34);
        wsFound.put(new Website("geeksforgeeks.com"), 233.74);

        SearchResults srs = new SearchResults(wsFound);

        SearchResult[] orderedResults = srs.getSearchResults();

        for (SearchResult sr : orderedResults) System.out.print(sr + " | ");
        System.out.println();
    }
}

class Quicksort {
    public static <E extends Comparable <E>> void quicksort(E[] datos) {
        quicksort(datos,0,datos.length-1);
    }

    private static <E extends Comparable <E>> void quicksort(E[] datos, int left, int right) {
        if (left < right) {
            int p = particionar(datos, left, right);
            quicksort(datos,left,p-1);
            quicksort(datos, p+1, right);
        }
    }

    private static <E extends Comparable <E>>int particionar(E[] datos, int left, int right) {
        E pivote = datos[left];
        int i = left + 1;
		for(int j = left+1; j<=right; j++) {
			if (datos[j].compareTo(pivote)<0) {
				swap(datos,i,j);
				i++;
            }
        }
        swap(datos,left,i-1);
		return i-1;
    }

    public static <E> void swap(E[] lista, int x, int y) {
        E temp = lista[x];
        lista[x] = lista[y];
        lista[y] = temp;
    }
}