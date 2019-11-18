import java.util.HashMap;


public class PageRank2 {
    private Website[] websites;
    private double d; //Damping Factor / Teleport --Value from 0-1
    private double[][] H;

    public PageRank2(Website[] websites) {
        this.websites = websites;
        this.d = 0.85;
        this.H = new double[this.websites.length][this.websites.length];
    }

    public void calculateRanks() {
        for (int i=0; i<this.websites.length; i++) {
            for(int j=0; j<this.websites.length; j++) {
                if (i!=j && this.websites[j].getB().containsKey(i)) this.H[j][i] = 1.0/this.websites[i].getL();
                else this.H[j][i] = 0;
            }
        }

        System.out.println("-------------------------------------------");
        System.out.println("I - dH");
        for (int i=0; i<this.websites.length; i++) {
            for(int j=0; j<this.websites.length; j++) System.out.print(this.H[i][j] + ", ");
            System.out.println();
        }
        System.out.println("-------------------------------------------");
        System.out.println("(I - dH)^-1");

        double initialRank = 1.0/this.websites.length;
        double[] ranks = new double[this.websites.length];
        for (int rank=0; rank<ranks.length;rank++) ranks[rank] = initialRank;
        for (int iter=0; iter < 3; iter++) {
            double[] ranksTemp = new double[this.websites.length];
            for (int i=0; i<this.websites.length; i++) {
                double rankI = 0;
                for(int j=0; j<this.websites.length; j++) {
                    rankI += this.H[i][j]*ranks[j];
                }
                ranksTemp[i] = rankI;
                this.websites[i].setRank(rankI);
            }
            ranks = ranksTemp;
        }
    }
  
   

    public static void main(String[] args) {
        // HashMap<Integer,Integer> A = new HashMap<>(),B = new HashMap<>(),C = new HashMap<>(),D = new HashMap<>(),E = new HashMap<>();
     
     
        // B.put(0, 1);
        // C.put(1, 1);
        // D.put(4, 1);
        // E.put(0, 1);
        // E.put(1, 1);
        // E.put(2, 1);
        // E.put(3, 1);



        // Website[] websites = {new Website(2, A,0),new Website(2, B,1),new Website(1, C,2),new Website(1, D,3),new Website(1, E,4)};
        // PageRank pg = new PageRank(websites);
        // pg.calculateRanks();

        // for(Website ws : pg.websites) {
        //     System.out.println("Page "+ws.getId()+": "+ws.getRank());
        // }
    }

}