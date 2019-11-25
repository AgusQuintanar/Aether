import java.util.HashMap;
import java.util.LinkedList;
import Jama.Matrix;
import java.util.Random;

//Agustin Quintanar A01636142
//Jonathan Chavez A01636160
public class PageRank {
    private double d; //Damping Factor / Teleport --Value from 0-1
    private double[][] H;

    public PageRank(int numberOfWebsites) {
        this.d = .85;
        this.H = new double[numberOfWebsites][numberOfWebsites];
    }

    public Website[] calculateRanks(Index index) {
        Website[] websites = index.getWebsites();
        int numOfPages = index.getCount(); //Number of available websites in Index

        for (int i=0; i<numOfPages; i++) {
            // System.out.println(websites[i].getPublicUrl()+", linksTo: "+websites[i].getLinksTo());
            for(int j=0; j<numOfPages; j++) {
                try {
                    if (i==j) this.H[i][j] = 1; 
                    else if (websites[j].getLinksTo().contains(websites[i].getPublicUrl())) {
                        //System.out.println("entro");
                        this.H[i][j] = -this.d/websites[j].getLinksTo().size();
                    }
                    else this.H[i][j] = 0;
                } catch(NullPointerException npe) {

                }
               
            }
        }

        this.printMatrix(this.H);
        this.H = new Matrix(this.H).inverse().getArray();
        this.printMatrix(this.H);

        for (int i=0; i<numOfPages; i++) {
            double rankI = 0;
            for(int j=0; j<numOfPages; j++) {
                rankI += this.H[i][j];
            }
            try {
                websites[i].setRank(rankI*(1-this.d));
                //System.out.println("rank "+i+": "+rankI*(1-this.d));
            } catch(NullPointerException npe) {

            }
        
        }

        return websites;
    }

    private void printMatrix(double[][] matrix) {
        for (int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ,");
            } System.out.println();
        }
    }
    public static void main(String[] args) {
       
    }

}