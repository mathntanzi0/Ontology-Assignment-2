import java.io.File;
import java.util.*;

/**
 * Class representing the TOPSIS method for multi-criteria decision-making.
 * 
 * @author Kyle Marry
 * @author Muhammad Shah
 * @author Sithembiso Ntanzi
 * @author Yurvan Ramjan
 * @author ChatGPT
 */

 
public class TOPSIS {
   
    String fileName; // File name of the decision matrix
    double[][] decisionMatrix; // Original decision matrix
    double[][] normalizeDecisionMatrix; // Normalized decision matrix


    public TOPSIS(String fileName){
        this.fileName=fileName;
        apply_TOPSIS(fileName);
    }
    public void apply_TOPSIS(String fileName){

        rankinglist=new RankingList();

        this.fileName=fileName;
        try {
            this.decisionMatrix= Utility.getDecisionMatrixFromFile(new File(fileName));
        } catch (Exception ex) {
            System.out.println("Problem with file");
            System.out.println(ex);
        }

        this.normalizeDecisionMatrix= Utility.normalizeDecisionMatrix(decisionMatrix);
        //weightedNormalizedMatrix(normalizeDecisionMatrix)
        
        //This Function gets the Positive Ideal solutions and then calculates the distance of each metric of the ontolgoy from the ideal solution
        double [] distanceFromPos=distancesFromPositiveIdeals(normalizeDecisionMatrix);
        
        //This Function gets the Negative Ideal solutions and then calculates the distance of each metric of the ontolgoy from the ideal solution
        double[] distanceFromNeg=distancesFromNegativeIdeals(decisionMatrix);
        
        relativeCloseness(distanceFromPos, distanceFromNeg);
        orderRankingList();
        printRankingList(getRankings());
    }
    /**
     * Calculates the positive ideal solution by finding the best value in each column of the decision matrix.
     * 
     * @param ndm The decision matrix.
     * @return The positive ideal solution.
     */
    public  double[] positiveIdeal(double[][]ndm){
        double[] positiveIdeals= new double[ndm[0].length];
        double max;
        //We need to find the best in each COLUMN
        for (int c=0;c<ndm[0].length;c++){
            max=ndm[c][0];
            for (int r=0;r<ndm.length;r++){
                if(ndm[r][c] > max)
                    max=ndm[r][c];
            }
            //Ideal best for the column is the max element we find
            positiveIdeals[c]=max;
        }
        return positiveIdeals;
    }
    /**
     * Calculates the negative ideal solution by finding the worst value in each column of the decision matrix.
     * 
     * @param ndm The normalized decision matrix.
     * @return The negative ideal solution.
     */
    public double[] negativeIdeals(double[][]ndm){
        //Store our Ideal best solutions in a 1d array
        double[] negativeIdeals= new double[ndm[0].length];
        double min;
        //We need to find the worst in each COLUMN
        for (int c=0;c<ndm[0].length;c++){

            min=ndm[c][0];
            for (int r=0;r<ndm.length;r++){
                if(ndm[r][c]<min)
                    min=ndm[r][c];
            }
            //Ideal worst for the column is the min element we find
            negativeIdeals[c]=min;
        }
        return negativeIdeals;
    }
    /**
     * Calculates the distances of alternatives from the positive ideal solution.
     * 
     * @param ndm The normalized decision matrix.
     * @return The distances of alternatives from the positive ideal solution.
     */
    public double[]distancesFromPositiveIdeals(double[][] ndm){
        double[] posIdeals=positiveIdeal(ndm);
        double distance;
        double[] distancesFromIdeal=new double[ndm.length];

        for (int r=0;r<ndm.length;r++){
            distance=0;
          for (int c=0;c<ndm[0].length;c++){
            //For each metric of the ontology we need to find the distance away from the best solution for that metric
            distance+=Math.pow((ndm[r][c]-posIdeals[c]),2);

            }
            distancesFromIdeal[r]=Math.sqrt(distance);
        }

        return distancesFromIdeal;
    }
    /**
     * Calculates the distances of alternatives from the negative ideal solution.
     * 
     * @param ndm The normalized decision matrix.
     * @return The distances of alternatives from the negative ideal solution.
     */
    public double[]distancesFromNegativeIdeals(double[][] ndm){
        double[] negIdeals=negativeIdeals(ndm);
        double distance;
        double[] distancesFromIdeal=new double[ndm.length];

        for (int r=0;r<ndm.length;r++){
            distance=0;
          for (int c=0;c<ndm[0].length;c++){
            //For each metric of the ontology we need to find the disstance away from the worst solution for that metric
            distance+=Math.pow((ndm[r][c]-negIdeals[c]),2);

            }
            distancesFromIdeal[r]=Math.sqrt(distance);
        }

        return distancesFromIdeal;
    }
    /**
     * Computes the relative closeness for each ontology based on the distances from the negative and positive ideal solutions.
     * 
     * @param distanceFromNeg The distances of alternatives from the negative ideal solution.
     * @param distanceFromPos The distances of alternatives from the positive ideal solution.
     */
    public void relativeCloseness(double[] distanceFromNeg,double[] distanceFromPos){
        //Relative Closeness Ci for each ontology,is computed based on distance from pos and neg ideal solutions

        double[] closeness=new double[distanceFromNeg.length];

        for(int i=0;i<closeness.length;i++){
            closeness[i]=distanceFromNeg[i]/(distanceFromNeg[i]+distanceFromPos[i]);
            App.rankinglist.getOntologies().get(i).setRelativeCloseness(closeness[i]);//Add the closeness score to The relevent Ontology
        }
    
    }
    /**
     * Retrieves the list of ontologies with their relative closeness scores.
     * 
     * @return The list of ontologies with their relative closeness scores.
     */
    public ArrayList<Ontology> getRankings(){
        return App.rankinglist.getOntologies();
    }
    //Print Ranking Lst
    public void printRankingList(ArrayList<Ontology>rankings){
        for(Ontology o: rankings){
            System.out.println(o.getName()+"  "+o.getRelativeCloseness());
        }
    }
    /**
     * Orders the ranking list of ontologies based on their relative closeness scores.
     */
    public void orderRankingList(){
        this.rankinglist.sortRankingList();
    }
}