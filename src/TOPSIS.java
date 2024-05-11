import java.lang.Math; 
import java.util.*; 
public class TOPSIS {

    String fileName;
    double[][] decisionMatrix;
    double[][] normalizeDecisionMatrix;


    public TOPSIS(fileName){


    }
    
    private double[][] normalizeDecisionMatrix(double[][] decisionMatrix){
        
        double normalising_constant=calculateNormalisingConstant(decisionMatrix)
        int normalizeDecisionMatrix[][];

        for (int r=0;r<decisionMatrix.length;r++){
            for (int c=0;c<decisionMatrix[0].length;c++){
                normalizeDecisionMatrix[r][c]= decisionMatrix[r][c]/normalising_constant;

            }
        }
        return normalizeDecisionMatrix;
    }

    private double [] calculateNormalisingConstants(double[][] decisionMatrix){

        //Each Column has its own Normalising Constant
        double sumOfElements=0;
        double [] normalising_constants=new double[decisionMatrix[0].length];

        for (int c=0;c<decisionMatrix[0].length;c++){
            sumOfElements=0;
            for(int r=0;r<decisionMatrix.length;r++){
                sumOfElements+=Math.pow(decisionMatrix[r][c],2);
            }
        normalising_constants[c]=Math.sqrt(sumOfElements);

        }

        return normalising_constants;

    }

    private double[][] weightedNormalizedMatrix(double[][] normalizeDecisionMatrix){
        return null;
    }

    private double[][] positiveNegativeIdeal(){
        return null
    }

    private double[][] distancesPositiveNegative(){
        return null;
    }

    private double[] relativeCloseness(){
        return null;
    }

    public List<RankingResult> Rank(int[][] decisionMatrix){
        return null
    }
}

public class RankingResult {
    private String string;
    private int number;

    public RankingResult(String string, int number) {
        this.string = string;
        this.number = number;
    }
}
