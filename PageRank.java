import java.util.HashMap;
import java.util.Arrays;


public class PageRank {
    private Website[] websites;
    private double d; //Value from 0-1
    private double[][] H;

    public PageRank(Website[] websites) {
        this.websites = websites;
        this.d = 0.85;
        this.H = new double[this.websites.length][this.websites.length];
    }

    public void calculateRanks() {
        for (int i=0; i<this.websites.length; i++) {
            for(int j=0; j<this.websites.length; j++) {
                if (i!=j && this.websites[j].getB().containsKey(i)) this.H[j][i] = this.d/this.websites[i].getL();
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

        this.H = inverse(this.H);

        for (int i=0; i<this.websites.length; i++) {
            for(int j=0; j<this.websites.length; j++) System.out.print(this.H[i][j] + ", ");
            System.out.println();
        }

        for (int i=0; i<this.websites.length; i++) {
            double rankI = 0;
            for(int j=0; j<this.websites.length; j++) {
                rankI += this.H[i][j];
            }
            this.websites[i].setRank(rankI*(1-this.d));
        }
    }


	private static double[][] inverse(double[][] matrix) {
		double[][] inverse = new double[matrix.length][matrix.length];

		return inverse;
    }
    

    public static void main(String[] args) {
        HashMap<Integer,Integer> B1 = new HashMap<>(),B2 = new HashMap<>(),B3 = new HashMap<>(),B4 = new HashMap<>(),B5 = new HashMap<>();

        B2.put(0, 1);
        B3.put(1, 1);
        B4.put(4, 1);
        B5.put(0, 1);
        B5.put(1, 1);
        B5.put(2, 1);
        B5.put(3, 1);

        Website[] websites = {new Website(2, B1),new Website(2, B2),new Website(1, B3),new Website(1, B4),new Website(1, B5)};
        PageRank pg = new PageRank(websites);
        pg.calculateRanks();

        for(Website ws : pg.websites) {
            System.out.println("Page "+ws.getId()+": "+ws.getRank());
        }
    }

}