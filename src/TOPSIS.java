import java.io.File;
import java.util.*; 
public class TOPSIS {

   
    String fileName;
    double[][] decisionMatrix;
    double[][] normalizeDecisionMatrix;
    RankingList rankinglist;

    public TOPSIS(String fileName){
        rankinglist=new RankingList();
        
        this.fileName=fileName;
        try {
            this.decisionMatrix=buildDecisonMatrix(fileName);
        } catch (Exception ex) {
            System.out.println("Problem with file");
        }

        this.normalizeDecisionMatrix=normalizeDecisionMatrix(decisionMatrix);
        //weightedNormalizedMatrix(normalizeDecisionMatrix)
        double[]best_solutions=positiveIdeal(normalizeDecisionMatrix);
        double[]worst_solutions=negativeIdeals(decisionMatrix);

        double [] distanceFromPos=distancesFromPositiveIdeals(normalizeDecisionMatrix);
        double[] distanceFromNeg=distancesFromNegativeIdeals(decisionMatrix);
        
        double[] relativeCloseness=relativeCloseness(distanceFromPos, distanceFromNeg);



       
    }

    public double[][] buildDecisonMatrix(String fileName) throws Exception{

        File file=new File(fileName);
        Scanner sc= new Scanner(file);
        double[][] temp_DecisionMatrix=new double[50][11];
        int lineNumber=-1;

        while(sc.hasNextLine()){
            lineNumber++;
            String line = sc.nextLine();
            String[] elements=line.split("\t"); // Breaks line into token seprated by tab space thus we have ontolgy name and the 11 metrics as tokens
            double[] evaluation_scores=new double[elements.length-1];//1st token i.e. Ontolgy name not needed
            //Get Ontology Name
            String ontology_name=elements[0];

            for(int i=1;i<elements.length;i++){
                //Build Row for our Decision Matrix
                temp_DecisionMatrix[lineNumber][i-1]=Double.parseDouble(elements[i]);
                // We also want to keep track of the evaluation metrics to store along with Ontology
                evaluation_scores[i-1]=Double.parseDouble(elements[i]);

            }
            //Create Ontology Object and store it in our Ranking List
            Ontology ont=new Ontology(ontology_name,evaluation_scores);
            rankinglist.addOntology(ont);//For now our ranking List is not sorted but later we will sort it
        }
        return temp_DecisionMatrix;

    }
    
    public  double[][] normalizeDecisionMatrix(double[][] decisionMatrix){
        
        double []normalising_constant= calculateNormalisingConstants(decisionMatrix);

        //Divide Each element of the Decision Matrix by the Normalising Constant of its Column
        double[][] normalizedDecisionMatrix=new double[decisionMatrix.length][decisionMatrix[0].length];

        for (int c=0;c<decisionMatrix[0].length;c++){

            for (int r=0;r<decisionMatrix.length;r++){
                normalizedDecisionMatrix[r][c]= decisionMatrix[r][c]/normalising_constant[c];

            }
        }
        return normalizedDecisionMatrix;

    }

    public  double [] calculateNormalisingConstants(double[][] decisionMatrix){

        //Each Column has its own Normalising Constant
        double sumOfElements=0;
        double [] normalising_constants=new double[decisionMatrix[0].length];
        //We need to square each elemnent and sum them for each Column
        for (int c=0;c<decisionMatrix[0].length;c++){
            sumOfElements=0;
            for(int r=0;r<decisionMatrix.length;r++){
                sumOfElements+=Math.pow(decisionMatrix[r][c],2);
            }
            //Square root sum to get Constant
        normalising_constants[c]=Math.sqrt(sumOfElements);
        }

        return normalising_constants;

    }
//INeed to work on this

public double[][] weightedNormalizedMatrix(double[][] normalizeDecisionMatrix){
        return null;
    }


    //Find the Ideal Best Solution for each metric of the ontology which is the max value in that column
    public  double[]positiveIdeal(double[][]dm){

        //Store our Ideal best solutions in a 1d array
        double[] positiveIdeals= new double[dm[0].length];
        double max;
        //We need to find the best in each COLUMN
        for (int c=0;c<dm[0].length;c++){

            max=dm[c][0];

            for (int r=0;r<dm.length;r++){
                if(dm[r][c]>max){
                    max=dm[r][c];
                }  
            }
            //Ideal best for the column is the max element we find
            positiveIdeals[c]=max;

        }


        return positiveIdeals;

    }
    //Find the Ideal Worst Solution for each metric of the ontology which is the min value in that column
    public double[] negativeIdeals(double[][]dm){

        //Store our Ideal best solutions in a 1d array
        double[] negativeIdeals= new double[dm[0].length];
        double min;
        //We need to find the best in each COLUMN
        for (int c=0;c<dm[0].length;c++){

            min=dm[c][0];
            for (int r=0;r<dm.length;r++){
                if(dm[r][c]<min){
                    min=dm[r][c];
                }  
            }
            //Ideal worst for the column is the min element we find
            negativeIdeals[c]=min;
        }

        return negativeIdeals;
        
    }

    //Find distance of each ontology's metrics from the positive ideal solution for that metrics

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
    //Find distance of each ontology's metrics from the negative ideal soltion for that metric

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

   
    public double[] relativeCloseness(double[] distanceFromNeg,double[] distanceFromPos){

        //RelativeCloseness Ci for each ontology,is computed based on distance from pos and neg ideal solutions
        double[] closeness=new double[distanceFromNeg.length];

        for(int i=0;i<closeness.length;i++){
            closeness[i]=distanceFromNeg[i]/(distanceFromNeg[i]+distanceFromPos[i]);
            rankinglist.getOntologies().get(i).setRelativeCloseness(closeness[i]);
        }
        return closeness;
    
    }
    //Rank the Onotolgy list according to their relative closeness

    public ArrayList<Ontology> getRankings(){
        rankinglist.sortRankingList();
        return rankinglist.getOntologies();
    }
}


