import java.util.LinkedList;

public class SearchPhrase {
    private String[] phrase;

    public SearchPhrase(String phrase) {
        this.phrase = phrase.toLowerCase().split(" ");
    }

    public LinkedList<String[]> getComninations() {
        LinkedList<String[]> combinations = new LinkedList<>();
        if (this.phrase.length <= 7) {
            combinations.add(this.phrase);
            combinations = getComninationsAux(combinations,this.phrase,0,0,0,0,0,0,0);
        } 
        else {
            String[] fragmentedPhrase = new String[7];
            int word = 0;
            while (word < 7) fragmentedPhrase[word] = this.phrase[word++];
            combinations = getComninationsAux(combinations,fragmentedPhrase,0,0,0,0,0,0,0);
            while (word < this.phrase.length) combinations.add(this.phrase[word++].split(" "));
        } 
        return combinations;
        


    } 

    private LinkedList<String[]> getComninationsAux(LinkedList<String[]> combinations, String[] substring, int index, int last, int k, int p, int t, int d, int h) {
        if (substring.length==1) return combinations;
        else {
            if (index < substring.length) { 
                combinations.add(getCombination(substring, new String[substring.length-1], index, 0, 0));
                return getComninationsAux(combinations,substring,index+1,last,k+1,p,t,d,h);
            } else {
                if(k==0) p=++t;
                else if(k+p-1 == combinations.getLast().length) p++;
                if (p > substring.length) p = t = (d <= substring.length) ? ++d : ++h;
                if (substring.length != combinations.get(last+1).length) t = k = p = d = h = 0;
                return getComninationsAux(combinations,combinations.get(last+1),p,last+1,0,p,t,d,h);
            }
        }
    }

    private String[] getCombination(String[] substring, String[] combination, int index, int i, int j) {
        if (i == substring.length) return combination;
        else if (i != index) {
            combination[j] = substring[i];
            return getCombination(substring, combination, index, i+1, j+1);
        } else return getCombination(substring, combination, index, i+1, j);
    }



    public static void main(String[] args) {
        SearchPhrase sf = new SearchPhrase("Agus ve a misa");
        LinkedList<String[]> combs = sf.getComninations();
        //System.out.println(combs);
        for (String[] comb : combs) {
            for(String word : comb) {
                System.out.print(word + " ");
            }
            System.out.println();
        }
        //System.out.println(sf.combinations);
    }
}