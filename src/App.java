
public class App {

    public static RankingList rankinglist = new RankingList();
    public static void main(String[] args) throws Exception {
        //TOPSIS topsis = new TOPSIS("dataset.txt");
        //topsis.rankAlternatives();

        ELECTRE electre = new ELECTRE("dataset.txt");
        electre.rankAlternatives();
    }
}



/*For Testing Purposes
 * 
 *  
        double [][]dm= topsis.buildDecisonMatrix("dataset.txt");
        /* 
        for (int r=0;r<dm.length;r++){
            for (int c=0;c<dm[0].length;c++){
      
                System.out.print(String.valueOf(dm[r][c])+"  ");
            }
            System.out.println();
        }*/

        //double[][] normalised_dm= topsis.normalizeDecisionMatrix(dm);
        /* 
        for (int r=0;r<dm.length;r++){
            for (int c=0;c<normalised_dm[0].length;c++){
      
                System.out.print(String.valueOf(normalised_dm[r][c])+"  ");
    
            }
            System.out.println();
        }
        double[] idealBest=positiveIdeal(normalised_dm);
       
        for(double x: idealBest ){
            System.out.print(x+" ");
        }*/

 