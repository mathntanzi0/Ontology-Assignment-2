package electre;

import java.io.*;
import java.util.*;

public class ELECTRE {
	String fileName;
    double[][] decisionMatrix;
    double[][] normalizeDecisionMatrix;
    RankingList rankinglist;
	
    public ELECTRE(String fileName){
        this.fileName=fileName;
        apply_ELECTRE(fileName);
    }
    public void apply_ELECTRE(String fileName){

        rankinglist=new RankingList();

        this.fileName=fileName;
        try {
            this.decisionMatrix=buildDecisonMatrix(fileName);
            this.normalizeDecisionMatrix=normalizeDecisionMatrix(decisionMatrix);
            this.calcWeights(normalizeDecisionMatrix);
        } catch (Exception ex) {
            System.out.println("Problem with file");
        }

        
        //weightedNormalizedMatrix(normalizeDecisionMatrix)
        
        /*score(distanceFromPos, distanceFromNeg);
        orderRankingList();
        printRankingList(getRankings());*/


    }
    
	public double[][] buildDecisonMatrix(String fileName) throws Exception{

        File file=new File(fileName);
       
        return null;
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
	
	public void calcWeights(double[][] normalizeDecisionMatrix) {
		double[] weights = new double[11];
		double numerator, denominator = 0.0;
		double[] num = new double[11];
		
		for (int i = 0; i < 50; i++) {
			double temp = normalizeDecisionMatrix[i][0];
			for (int j = 1; j < 11; j++) {
				if (normalizeDecisionMatrix[i][j] != 0) {
					temp *= normalizeDecisionMatrix[i][j];
				}
			} //end inner
			num[i] = temp;
			denominator += temp;
			weights[i] = num[i] / denominator;
		} // end outer
		
		System.out.println(weights);
	}
}
