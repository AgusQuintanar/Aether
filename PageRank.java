import java.util.HashMap;

import Jama.Matrix;


public class PageRank {
    private Website[] websites;
    private double d; //Damping Factor / Teleport --Value from 0-1
    private double[][] H;

    public PageRank(Website[] websites) {
        this.websites = websites;
        this.d = 0.85;
        this.H = new double[this.websites.length][this.websites.length];
    }

    public void calculateRanks() {
        for (int i=0; i<this.websites.length; i++) {
            for(int j=0; j<this.websites.length; j++) {
                if (i==j) this.H[j][i] = 1; 
                else if (this.websites[j].getB().containsKey(i)) this.H[j][i] = -this.d/this.websites[i].getL();
                else this.H[j][i] = 0;
            }
        }

        this.H = new Matrix(this.H).inverse().getArray();

        for (int i=0; i<this.websites.length; i++) {
            double rankI = 0;
            for(int j=0; j<this.websites.length; j++) {
                rankI += this.H[i][j];
            }
            this.websites[i].setRank(rankI*(1-this.d));
        }
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

        Website[] websites = {new Website(2, B1,0),new Website(2, B2,1),new Website(1, B3,2),new Website(1, B4,3),new Website(1, B5,4)};
        PageRank pg = new PageRank(websites);
        pg.calculateRanks();

        for(Website ws : pg.websites) {
            System.out.println("Rank of page "+ws.getId()+": "+ws.getRank());
        }
    }

}