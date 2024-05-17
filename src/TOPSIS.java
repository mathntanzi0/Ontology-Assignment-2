import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Class representing the TOPSIS method for multi-criteria decision-making.
 * 
 * @author Kyle Marry
 * @author Muhammad Shah
 * @author Sithembiso Ntanzi
 * @author Yurvan Ramjan
 * */

 
public class TOPSIS {
   
    String fileName; // File name of the decision matrix
    double[][] decisionMatrix; // Original decision matrix
    double[][] normalizeDecisionMatrix; // Normalized decision matrix
    File file;
    
    /**
     * Constructs a TOPSIS object with the specified file name.
     * 
     * @param fileName The name of the file containing the decision matrix.
     */
    public TOPSIS(String fileName){
        this.fileName=fileName;
        this.file = new File(fileName);
    }
    
    /**
     * Constructs a TOPSIS object with the specified file.
     * 
     * @param file The file containing the decision matrix.
     */
    public TOPSIS(File file){
        this.fileName = file.getName();
        this.file = file;
    }
    
    /**
     * Ranks the alternatives based on the decision matrix and calculates closeness scores.
     * Reads the decision matrix from a file, normalizes it, and calculates weighted normalized matrix.
     * Calculates distances from positive and negative ideal solutions, computes relative closeness scores,
     * orders the ranking list, and prints the ranking list.
     */
    public void rankAlternatives(){
        try {
            this.decisionMatrix= Utility.getDecisionMatrixFromFile(this.file);
        } catch (Exception ex) {
            System.out.println("Problem with file");
            System.out.println(ex);
        }

        this.normalizeDecisionMatrix= Utility.normalizeDecisionMatrix(decisionMatrix);
        //weightedNormalizedMatrix(normalizeDecisionMatrix)

        double[][] weightedNormalizeDecisionMatrix = Utility.weightedNormalizedMatrix(normalizeDecisionMatrix);
        double[] positiveIdeal=positiveIdeal(weightedNormalizeDecisionMatrix);
        double[] negativeIdeal=negativeIdeals(weightedNormalizeDecisionMatrix);
      
        
        
      //This Function gets the Positive Ideal solutions and then calculates the distance of each metric of the ontolgoy from the ideal solution
        double [] distanceFromPos=distancesFromPositiveIdeals(weightedNormalizeDecisionMatrix);
        
        //This Function gets the Negative Ideal solutions and then calculates the distance of each metric of the ontolgoy from the ideal solution
        double[] distanceFromNeg=distancesFromNegativeIdeals(weightedNormalizeDecisionMatrix);
        
        double[] closenessScores=relativeCloseness(distanceFromPos, distanceFromNeg);
        orderRankingList();
        printRankingList(getRankings());
        displayOutputToFile(weightedNormalizeDecisionMatrix, positiveIdeal, negativeIdeal, distanceFromPos, distanceFromNeg,closenessScores);
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
    public double[] relativeCloseness(double[] distanceFromNeg,double[] distanceFromPos){
        //Relative Closeness Ci for each ontology,is computed based on distance from pos and neg ideal solutions

        double[] closeness=new double[distanceFromNeg.length];

        for(int i=0;i<closeness.length;i++){
            closeness[i]=distanceFromNeg[i]/(distanceFromNeg[i]+distanceFromPos[i]);
            App.rankinglist.getOntologies().get(i).setRelativeCloseness(closeness[i]);//Add the closeness score to The relevent Ontology
        }

        return closeness;
    
    }
   
    /**
     * Retrieves the list of ontologies with their relative closeness scores.
     * 
     * @return The list of ontologies with their relative closeness scores.
     */
    public ArrayList<Ontology> getRankings(){
        return App.rankinglist.getOntologies();
    }
    
    /**
     * Prints the ranking list to the console.
     * 
     * @param rankings The list of ontologies with their relative closeness scores.
     */
    public void printRankingList(ArrayList<Ontology>rankings){
        for(Ontology o: rankings){
            System.out.println(o.getName()+"  "+o.getRelativeCloseness());
        }
    }

    /**
     * Displays various outputs related to TOPSIS analysis to a text file.
     * Outputs include decision matrix, normalized decision matrix, weighted normalized matrix,
     * positive and negative ideal solutions, distances from ideal solutions, and relative closeness scores.
     * Additionally, prints the ranked list of ontologies.
     * 
     * @param weightedNormalizeDecisionMatrix The weighted normalized decision matrix.
     * @param positiveIdeals The positive ideal solutions for each metric.
     * @param negativeIdeals The negative ideal solutions for each metric.
     * @param distanceFromPos The distances from positive ideal solutions.
     * @param distanceFromNeg The distances from negative ideal solutions.
     * @param closenessScores The relative closeness scores of ontologies.
     */
    public void displayOutputToFile(double[][] weightedNormalizeDecisionMatrix,double[]positiveIdeals,double[]negativeIdeals ,double[] distanceFromPos,double[] distanceFromNeg,double[]closenessScores){

          String Filename="TOPSIS_output.txt";
        try{
                FileWriter writer= new FileWriter(Filename,false);
                //Display Decsion Matrix
                writer.write("Decision Matrix: \n");
                writer.write("\n");
                for (int r=0;r<decisionMatrix.length;r++){
                    for (int c=0;c<decisionMatrix[0].length;c++){
                        writer.write(String.format("%-" +10 + "s",decisionMatrix[r][c])+"\t");
                    }
                    writer.write("\n");
                }

                //Normalised Decision Matrix
                writer.write("\n");

                writer.write("Normalised Decision Matrix: \n");
                writer.write("\n");
                for (int r=0;r<normalizeDecisionMatrix.length;r++){
                    for (int c=0;c<normalizeDecisionMatrix[0].length;c++){
                        writer.write(String.format("%-" +20 + "s",normalizeDecisionMatrix[r][c])+"\t");
                    }
                    writer.write("\n");
                }
                writer.write("\n");
                writer.write("Weighted Normalised Matrix: \n");
                writer.write("\n");
                for (int r=0;r<weightedNormalizeDecisionMatrix.length;r++){
                    for (int c=0;c<weightedNormalizeDecisionMatrix[0].length;c++){
                        writer.write(String.format("%-" +21 + "s",weightedNormalizeDecisionMatrix[r][c])+"\t");
                    }
                    writer.write("\n");
                }

                //Positive Ideal Solutions For each Metric
                writer.write("\n");
                writer.write("Positive Ideal Solutions: \n");
                writer.write("\n");
                for(int i=0;i<positiveIdeals.length;i++){
                    writer.write(String.valueOf(positiveIdeals[i])+"\n");
                }

                //Negative Ideal Solutions For each Metric
                writer.write("\n");
                writer.write("Negative Ideal Solutions: \n");
                writer.write("\n");
                for(int i=0;i<negativeIdeals.length;i++){
                    writer.write(String.valueOf(negativeIdeals[i])+"\n");
                }

                //Distance from Positive Ideal Solutions
                writer.write("\n");
                writer.write("Distance From Positive Ideal Solutions: \n");
                writer.write("\n");
                for(int i=0;i<distanceFromPos.length;i++){
                    String ontology="Ontology "+ (i+1);
                    String distance=String.valueOf(distanceFromPos[i]);
                    String line= String.format("%-15s %10s%n",ontology,distance);
                    writer.write(line);
                    
                }
                //Distance from Negative Ideal Solutions

                writer.write("\n");
                writer.write("Distance From Negative Ideal Solutions: \n");
                writer.write("\n");
                for(int i=0;i<distanceFromNeg.length;i++){
                    String ontology="Ontology "+ (i+1);
                    String distance=String.valueOf(distanceFromNeg[i]);
                    String line= String.format("%-15s %10s%n",ontology,distance);
                    writer.write(line);
                    
                }

                //Relative Closeness Scores

                writer.write("\n");
                writer.write("Closeness Scores of Ontologies: \n");
                writer.write("\n");
                for(int i=0;i<closenessScores.length;i++){
                    String ontology="Ontology "+ (i+1);
                    String distance=String.valueOf(closenessScores[i]);
                    String line= String.format("%-15s %10s%n",ontology,distance);
                    writer.write(line);
                   
                }

                //Print Ranked List
                writer.write("\n");
                writer.write("Ranked List: \n");
                writer.write("\n");
                for(Ontology o: getRankings()){
                    String ontology=o.getName();
                    String distance=String.valueOf(o.getRelativeCloseness());
                    String line= String.format("%-15s %10s%n",ontology,distance);
                    writer.write(line);
                }

                writer.close();
        }
        catch(IOException e){
                System.out.print("Problem displaying outputs to tetxfile");
        }

        

    }
    
    /**
     * Orders the ranking list of ontologies based on their relative closeness scores.
     */
    public void orderRankingList(){
        App.rankinglist.rankListDesc();
    }
}
