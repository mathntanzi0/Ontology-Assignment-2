import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility class containing static methods for performing various operations.
 * 
 * @author Kyle Marry
 * @author Muhammad Shah
 * @author Sithembiso Ntanzi
 * @author Yurvan Ramjan
 */

public class Utility {
    
    /**
     * Normalizes the decision matrix by dividing each element of the matrix by the normalizing constant of its column.
     * 
     * @param decisionMatrix The decision matrix to be normalized.
     * @return The normalized decision matrix.
     */
    public static double[][] normalizeDecisionMatrix(double[][] decisionMatrix){
        
        double []normalising_constant= calculateNormalisingConstants(decisionMatrix);

        //Divide Each element of the Decision Matrix by the Normalising Constant of its Column
        double[][] normalizedDecisionMatrix=new double[decisionMatrix.length][decisionMatrix[0].length];

        for (int c=0;c<decisionMatrix[0].length;c++){
            for (int r=0;r<decisionMatrix.length;r++)
                normalizedDecisionMatrix[r][c]= decisionMatrix[r][c]/normalising_constant[c];
        }
        return normalizedDecisionMatrix;
    }
    
    /**
     * Calculates the normalizing constants for each column of the decision matrix.
     * Each column has its own normalizing constant.
     * 
     * @param decisionMatrix The decision matrix for which to calculate the normalizing constants.
     * @return An array containing the normalizing constants for each column of the decision matrix.
     */
    public static double [] calculateNormalisingConstants(double[][] decisionMatrix){
        //Each Column has its own Normalising Constant
        double sumOfElements=0;
        double [] normalising_constants=new double[decisionMatrix[0].length];
        //We need to square each elemnent and sum them for each Column
        for (int c=0;c<decisionMatrix[0].length;c++){
            sumOfElements=0;
            for(int r=0;r<decisionMatrix.length;r++)
                sumOfElements+=Math.pow(decisionMatrix[r][c],2);
            //Square root sum to get Constant
        normalising_constants[c]=Math.sqrt(sumOfElements);
        }

        return normalising_constants;

    }
   
    /**
     * Calculates the weights of each criterion uniformly based on the number of criteria.
     * 
     * @param normalizeDecisionMatrix The normalized decision matrix.
     * @return The weights of each criterion.
     */
    public static double[] calculateWeights(double[][] normalizeDecisionMatrix) {
        int numCriteria = normalizeDecisionMatrix[0].length;
        double[] weights = new double[numCriteria];
    
        double value = 1.0 / numCriteria;
        for (int i = 0; i < numCriteria; i++) 
            weights[i] = value;
    
        return weights;
    }    
   
    /**
     * Calculates the weighted normalized matrix by multiplying the normalized decision matrix 
     * with the weights for each criterion.
     * 
     * @param normalizeDecisionMatrix The normalized decision matrix.
     * @param weights The weights for each criterion.
     * @return The weighted normalized matrix.
     */
    public static double[][] weightedNormalizedMatrix(double[][] normalizeDecisionMatrix, double[] weights) {
        int numRows = normalizeDecisionMatrix.length;
        int numCols = normalizeDecisionMatrix[0].length;
        double[][] weightedMatrix = new double[numRows][numCols];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                weightedMatrix[r][c] = normalizeDecisionMatrix[r][c] * weights[c];
            }
        }
        return weightedMatrix;
    }
   
    /**
    * Calculates the weighted normalized matrix using the weights calculated with the geometric mean method.
    * 
    * @param normalizeDecisionMatrix The normalized decision matrix.
    * @return The weighted normalized matrix.
    */
    public static double[][] weightedNormalizedMatrix(double[][] normalizeDecisionMatrix){
        double[] weights = calculateWeights(normalizeDecisionMatrix);
        return weightedNormalizedMatrix(normalizeDecisionMatrix, weights);
    }
   
    /**
     * Reads a decision matrix from the given file and returns it.
     * 
     * @param file The file containing the decision matrix.
     * @return The decision matrix read from the file.
     * @throws FileNotFoundException If the file is not found.
     */
    public static double[][] getDecisionMatrixFromFile(File file) throws FileNotFoundException {
        App.rankinglist = new RankingList();
        Scanner scanner = new Scanner(file);
        int numRows = 0;
        int numCols = 0;

        // Number of rows and columns in the matrix
        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().trim().split("\t");
            numCols = Math.max(numCols, values.length);
            numRows++;
        }
        scanner.close();

        scanner = new Scanner(file);
        double[][] decisionMatrix = new double[numRows][numCols-1];

        // Read data from file into the decision matrix
        for (int i = 0; i < numRows; i++) {

            String[] values = scanner.nextLine().trim().split("\t");
            double[] metricsValue = new double[numCols-1];
            for (int j = 1; j < numCols; j++) {
                decisionMatrix[i][j-1] = Double.parseDouble(values[j]);
                metricsValue[j - 1] = Double.parseDouble(values[j]);
            }

            Ontology ontology = new Ontology(values[0], metricsValue);
            App.rankinglist.addOntology(ontology);
        }
        scanner.close();
        return decisionMatrix;
    }
}
