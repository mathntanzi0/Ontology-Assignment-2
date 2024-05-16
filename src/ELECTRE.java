import java.io.*; 
import java.util.*; 

/**
 * Class representing the ELECTRE method for multi-criteria decision-making.
 * 
 * @author Kyle Marry
 * @author Muhammad Shah
 * @author Sithembiso Ntanzi
 * @author Yurvan Ramjan
 * */

<<<<<<< HEAD
public class ELECTRE{ 

=======
public class Electre{ 
//variables for the algorithm
>>>>>>> dfc8acd0556fc29714adc748da4a21a46e3de560
	String fileName; 
    double[][] decisionMatrix; 
    double[][] normalizeDecisionMatrix; 
    double[] weights; 
    double[][] weightedNormalizeDecisionMatrix; 
    HashMap<String, ArrayList<Integer>> concordance; 
    HashMap<String, ArrayList<Integer>> discordance; 
    double[][] concordanceMatrix; 
    double[][] discordanceMatrix; 
    double[][] concordanceDominanceMatrix; 
    double[][] discordanceDominanceMatrix; 
    int[][] aggregateDominanceMatrix; 
    int[] scores; 
    File file;

<<<<<<< HEAD

    /**
     * Constructs an ELECTRE object with the specified file name.
     * 
     * @param fileName The name of the file containing the decision matrix.
     */
    public ELECTRE(String fileName){ 
        this.fileName=fileName;
        this.file = new File(fileName);
    }
    
    /**
     * Constructs an ELECTRE object with the specified file.
     * 
     * @param file The file containing the decision matrix.
     */
    public ELECTRE(File file){ 
        this.fileName=file.getName(); 
        this.file = file;
    }

    /**
     * Ranks the alternatives using the ELECTRE method.
     * Reads the decision matrix from a file, normalizes it, calculates weights, and computes the weighted normalized matrix.
     * Calculates concordance and discordance matrices, dominance matrices, and aggregate dominance matrix.
     * Computes scores based on the aggregate dominance matrix, sets the scores, and displays the output to a file.
     */
    public void rankAlternatives(){  
=======
    public Electre(String fileName){ 
        this.fileName=fileName; 
        apply_Electre(fileName); 
//constructor
    } 

    public void apply_Electre(String fileName){ //This method will determine the output results
        rankinglist=new RankingList(); 
        this.fileName=fileName; 
>>>>>>> dfc8acd0556fc29714adc748da4a21a46e3de560
        try { 
            this.decisionMatrix = Utility.getDecisionMatrixFromFile(file);
            this.normalizeDecisionMatrix = Utility.normalizeDecisionMatrix(decisionMatrix); 

            this.weights = Utility.calculateWeights(normalizeDecisionMatrix); 
            this.weightedNormalizeDecisionMatrix = Utility.weightedNormalizedMatrix(normalizeDecisionMatrix, weights); 

            this.concordance = getConcordance(weightedNormalizeDecisionMatrix); 
            this.discordance = getDiscordance(weightedNormalizeDecisionMatrix); 

            this.concordanceMatrix = getConcordanceMatrix(concordance); 
            this.discordanceMatrix = getDiscordanceMatrix(discordance, weightedNormalizeDecisionMatrix); 
            this.concordanceDominanceMatrix = computeDominanceMatrix(concordanceMatrix, calculateThreshold(concordanceMatrix)); 
            this.discordanceDominanceMatrix = computeDominanceMatrix(discordanceMatrix, calculateThreshold(discordanceMatrix)); 
            this.aggregateDominanceMatrix = getAggregateDominanceMatrix(concordanceDominanceMatrix, discordanceDominanceMatrix); 

            this.scores = computeScores(aggregateDominanceMatrix); 
            this.setScores();
            this.displayOutputToFile();
        } catch (Exception ex) { 
            System.out.println("Problem with file"); 
            ex.printStackTrace(); 
        } 
    } 
<<<<<<< HEAD
    
    /**
     * Displays various outputs related to ELECTRE analysis to a text file.
     * Outputs include decision matrix, normalized decision matrix, weighted normalized matrix,
     * concordance and discordance values, concordance and discordance matrices,
     * dominance matrices, and aggregate dominance matrix.
     * Additionally, prints the ranked list of ontologies.
     */
    public void displayOutputToFile(){

=======

     
    public void displayOutputToFile(double[][] weightedNormalizeDecisionMatrix){
//This method will add all the results of each method into a textfile
>>>>>>> dfc8acd0556fc29714adc748da4a21a46e3de560
        String Filename="ELECTRE_output.txt";
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

            writer.write("\n");
            writer.write("Concordance values: \n");
            writer.write("\n");
            for (int i = 0; i < weightedNormalizeDecisionMatrix.length; i++) {
                String line ="";
                for (int j = 0; j < weightedNormalizeDecisionMatrix.length; j++) {
                    String rs = String.valueOf(i) + ","+ String.valueOf(j);
                    if(i != j){
                        line += this.concordance.get(rs)+",";
                    }
                    else{
                        line += "[]";
                    }
                }
                writer.write(line+"\n");
            }

            writer.write("\n");
            writer.write("Discordance values: \n");
            writer.write("\n");
            for (int i = 0; i < weightedNormalizeDecisionMatrix.length; i++) {
                String line ="";
                for (int j = 0; j < weightedNormalizeDecisionMatrix.length; j++) {
                    String rs = String.valueOf(i) + ","+ String.valueOf(j);
                    if(i != j){
                        line += this.discordance.get(rs)+",";
                    }
                    else{
                        line += "[]";
                    }
                }
                writer.write(line+"\n");
            }

            writer.write("\n");
            writer.write("Concordance Matrix: \n");
            writer.write("\n");
            for (int i = 0; i < this.concordanceMatrix.length; i++) {
                String line ="";
                for (int j = 0; j < this.concordanceMatrix[i].length; j++) {
                    line += this.concordanceMatrix[i][j]+"\t";
                }
                writer.write(line+"\n");
            }

            writer.write("\n");
            writer.write("Discordance Matrix: \n");
            writer.write("\n");
            for (int i = 0; i < this.discordanceMatrix.length; i++) {
                String line ="";
                for (int j = 0; j < this.discordanceMatrix[i].length; j++) {
                    line += this.discordanceMatrix[i][j]+"\t";
                }
                writer.write(line+"\n");
            }

            writer.write("\n");
            writer.write("Concordance Dominance Matrix: \n");
            writer.write("\n");
            for (int i = 0; i < this.concordanceDominanceMatrix.length; i++) {
                String line ="";
                for (int j = 0; j < this.concordanceDominanceMatrix[i].length; j++) {
                    line += this.concordanceDominanceMatrix[i][j]+"\t";
                }
                writer.write(line+"\n");
            }

            writer.write("\n");
            writer.write("Discordance Dominance Matrix: \n");
            writer.write("\n");
            for (int i = 0; i < this.discordanceDominanceMatrix.length; i++) {
                String line ="";
                for (int j = 0; j < this.discordanceDominanceMatrix[i].length; j++) {
                    line += this.discordanceDominanceMatrix[i][j]+"\t";
                }
                writer.write(line+"\n");
            }

            writer.write("\n");
            writer.write("Aggregate Dominance Matrix: \n");
            writer.write("\n");
            for (int i = 0; i < this.aggregateDominanceMatrix.length; i++) {
                String line ="";
                for (int j = 0; j < this.aggregateDominanceMatrix[i].length; j++) {
                    line += this.aggregateDominanceMatrix[i][j]+"\t";
                }
                writer.write(line+"\n");
            }

              writer.write("\n");
              writer.write("Ranked List: \n");
              writer.write("\n");
              for(Ontology o: App.rankinglist.getOntologies()){
                  String ontology=o.getName();
                  String distance=String.valueOf(o.getScore());
                  String line= String.format("%-15s %10s%n",ontology,distance);
                  writer.write(line);
              }
              writer.close();
      }
      catch(IOException e){
              System.out.print("Problem displaying outputs to tetxfile");
      }

    }
<<<<<<< HEAD
    
    /**
     * Computes the concordance values between alternatives based on the weighted decision matrix.
     * 
     * @param weighteddMatrix The weighted decision matrix.
     * @return A HashMap containing concordance values for each pair of alternatives.
     */
=======

//will read the dataset and create the initial matrix
	public double[][] buildDecisonMatrix(String fileName) throws Exception{ 
        File file = new File(fileName); 
        Scanner scanner = new Scanner(file); 
        double[][] temp_DecisionMatrix=new double[50][11]; 
        int lineNumber=-1; 

        while (scanner.hasNextLine()) { 
            lineNumber++; 
            String line = scanner.nextLine(); 
            String[] elements = line.split("\t"); // Breaks line into token seprated by tab space thus we have ontolgy name and the 11 metrics as tokens 
            double[] evaluation_scores = new double[elements.length-1];//1st token i.e. Ontolgy name not needed 
            //Get Ontology Name 
            String ontology_name=elements[0]; 
            for(int i=1;i<elements.length;i++){ 
                //Build Row for our Decision Matrix 
                temp_DecisionMatrix[lineNumber][i-1] = Double.parseDouble(elements[i]); 
                // We also want to keep track of the evaluation metrics to store along with Ontology 
                evaluation_scores[i-1] = Double.parseDouble(elements[i]); 
            } 

            //Create Ontology Object and store it in our Ranking List 
            Ontology ontology = new Ontology(ontology_name,evaluation_scores); 
            this.rankinglist.addOntology(ontology); 
        } 
        scanner.close(); 
        return temp_DecisionMatrix; 
	}//eof 

	 
//will normalise the initial matrix
	public  double[][] normalizeDecisionMatrix(double[][] decisionMatrix){ 
        double []normalising_constant= calculateNormalisingConstants(decisionMatrix); 
        //Divide Each element of the Decision Matrix by the Normalising Constant of its Column 
        double[][] normalizedDecisionMatrix=new double[decisionMatrix.length][decisionMatrix[0].length]; 

        for (int r=0;r<decisionMatrix.length;r++){ 
            for (int c=0;c<decisionMatrix[r].length;c++){ 
                normalizedDecisionMatrix[r][c]= decisionMatrix[r][c]/normalising_constant[c]; 
                //System.out.print(normalizedDecisionMatrix[r][c]+"\t"); 
            } 
            //System.out.println(""); 
        } 
        return normalizedDecisionMatrix; 
    } 

	 

	public double[] calculateNormalisingConstants(double[][] decisionMatrix){ 
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

//this function will return the matrix after being multiplied by the weights
    public static double[][] weightedNormalizedMatrix(double[][] normalizeDecisionMatrix, double[] weights) { 
        int numRows = normalizeDecisionMatrix.length; 
        int numCols = normalizeDecisionMatrix[0].length; 
        double[][] weightedMatrix = new double[numRows][numCols]; 

        for (int r = 0; r < numRows; r++) { 
            for (int c = 0; c < numCols; c++) { 
                weightedMatrix[r][c] = normalizeDecisionMatrix[r][c] * weights[c]; 
                //System.out.print(weightedMatrix[r][c]+"\t"); 
            } 
            //System.out.println(""); 
        } 
        return weightedMatrix; 
    } 

         

    public static double[] calculateWeights(double[][] normalizeDecisionMatrix) { 
/*
        int numCriteria = normalizeDecisionMatrix[0].length; 
        int numAlternatives = normalizeDecisionMatrix.length; 
        //Calculate geometric mean 
        double[] geometricMeans = new double[numCriteria]; 
        for (int j = 0; j < numCriteria; j++) { 
            double product = 1; 
            for (int i = 0; i < numAlternatives; i++) 
                product *= normalizeDecisionMatrix[i][j]; 
            geometricMeans[j] = Math.pow(product, 1.0 / numAlternatives); 
        } 
        //Normalize geometric means 
        double sumGeometricMeans = Arrays.stream(geometricMeans).sum(); 
        double[] normalizedGeometricMeans = new double[numCriteria]; 
        for (int j = 0; j < numCriteria; j++) 
            normalizedGeometricMeans[j] = geometricMeans[j] / sumGeometricMeans; 
 */
        double[] w = new double[11];
        for (int i = 0; i < w.length; i++) {
            w[i] = (double)(1.0/11.0);
        }

        return w; 

    } 

  
//the concordance is calculated
>>>>>>> dfc8acd0556fc29714adc748da4a21a46e3de560
    public HashMap<String, ArrayList<Integer>> getConcordance(double[][] weighteddMatrix){ 
        HashMap<String, ArrayList<Integer>> x = new HashMap<String, ArrayList<Integer>>(); 
        for (int r = 0; r < 50; r++) { 
            for (int s = 0; s < weighteddMatrix.length; s++) { 
                if(r == s){ 
                    continue; 
                } 
                String rs = String.valueOf(r) + ","+ String.valueOf(s); 
                x.put(rs,new ArrayList<Integer>()); 
                for (int k = 0; k < 11; k++) { 
                    if(weighteddMatrix[s][k] >= weighteddMatrix[r][k] ){
                        x.get(rs).add(k); 
                    } 
                }
                if(x.get(rs).size() == 0){ 
                    x.get(rs).add(-1); 
                } 
            }
        }
        return x; 
<<<<<<< HEAD
    }
    
    /**
     * Computes the discordance values between alternatives based on the weighted decision matrix.
     * 
     * @param weighteddMatrix The weighted decision matrix.
     * @return A HashMap containing discordance values for each pair of alternatives.
     */
=======
    }//eof 

//the discordance is calculated
>>>>>>> dfc8acd0556fc29714adc748da4a21a46e3de560
    public HashMap<String, ArrayList<Integer>> getDiscordance(double[][] weighteddMatrix){ 
        HashMap<String, ArrayList<Integer>> x = new HashMap<String, ArrayList<Integer>>(); 
        for (int r = 0; r < 50; r++) { 
            for (int s = 0; s < weighteddMatrix.length; s++) { 
                if(r == s){ 
                    continue; 
                } 
                String rs = String.valueOf(r) + ","+ String.valueOf(s); 
                x.put(rs,new ArrayList<Integer>()); 
                for (int j = 0; j < 11; j++) { 
                    if(weighteddMatrix[s][j] <= weighteddMatrix[r][j] ){ 
                        //add index to list 
                        x.get(rs).add(j); 
                    } 
                }
                if(x.get(rs).size() == 0){ 
                    x.get(rs).add(-1); 
                } 
            }
        }
        return x; 
    }
    
    /**
     * Computes the concordance matrix based on the concordance HashMap.
     * 
     * @param conHashMap The HashMap containing concordance values for each pair of alternatives.
     * @return The concordance matrix.
     */
    public double[][] getConcordanceMatrix(HashMap<String, ArrayList<Integer>> conHashMap){ 
        double[][] concMatrix = new double[decisionMatrix.length][decisionMatrix.length]; 
        Set<String> keys = conHashMap.keySet(); 
        for (String key : keys) { 
            String[] temp = key.split(","); 
            int r = Integer.valueOf(temp[0]); 
            int s = Integer.valueOf(temp[1]); 
            double sum = 0; 
            for (int i = 0; i < decisionMatrix[0].length; i++) { 
                if(conHashMap.get(key).contains(i)){ 
                    sum += weights[i]; 
                } 
            } 
            concMatrix[r][s] = sum; 
        } 
        return concMatrix; 
    } 
   
    /**
     * Computes the discordance matrix based on the discordance HashMap and the weighted normalized matrix.
     * 
     * @param discHashMap The HashMap containing discordance values for each pair of alternatives.
     * @param weightedNormalizedMatrix The weighted normalized decision matrix.
     * @return The discordance matrix.
     */
    public double[][] getDiscordanceMatrix(HashMap<String, ArrayList<Integer>> discHashMap, double[][] weightedNormalizedMatrix){ 
        double[][] discMatrix = new double[50][50]; 
        Set<String> keys = discHashMap.keySet(); 

        for (String key : keys) { 
            String[] temp = key.split(","); 
            int r = Integer.valueOf(temp[0]); 
            int s = Integer.valueOf(temp[1]); 
            double numerator = 0; 
            double denom = 0; 
            for (int i = 0; i < 11; i++) { 
                if(discHashMap.get(key).contains(i)){ 
                    double tempNumerator = Math.abs(weightedNormalizedMatrix[s][i] - weightedNormalizedMatrix[r][i]); 
                    if(tempNumerator > numerator){ 
                        numerator = tempNumerator; 
                    } 
                } 
            } 

            for (int i = 0; i < 11; i++) { 
                double tempDenom = Math.abs(weightedNormalizedMatrix[s][i] - weightedNormalizedMatrix[r][i]); 
                if(tempDenom > denom){ 
                    denom = tempDenom; 
                } 
            } 
            if (denom != 0) { 
                discMatrix[r][s] = numerator / denom; 
            } else { 
                discMatrix[r][s] = 0;  // or some other handling of zero division 
            } 
        } 
        return discMatrix; 
    } 
   
    /**
     * Calculates the threshold value for the given matrix.
     * 
     * @param matrix The matrix for which the threshold value is to be calculated.
     * @return The threshold value.
     */
    public static double calculateThreshold(double[][] matrix) { 
        int rows = matrix.length; 
        double sum = 0; 
        for (int r = 0; r < rows; r++) { 
            for (int c = 0; c < rows; c++) { 
                sum += matrix[r][c]; 
            } 
        } 
        return sum / (rows * (rows - 1)); 
    }
   
    /**
     * Computes the dominance matrix based on the given matrix and threshold value.
     * 
     * @param matrix    The matrix to compute the dominance matrix from.
     * @param threshold The threshold value used for dominance comparison.
     * @return The computed dominance matrix.
     */
    public double[][] computeDominanceMatrix(double[][] matrix, double threshold) { 
    	int rows = matrix.length; 
    	double[][] dominanceMatrix = new double[matrix.length][matrix.length]; 
    	 
    	for (int r = 0; r < rows; r++) { 
    		for (int c = 0; c < rows; c++) { 
    			dominanceMatrix[r][c] = (matrix[r][c] >= threshold) ? 1 : 0; 
    		} 
    	} 
    	return dominanceMatrix; 
    } 
   
    /**
     * Computes the aggregate dominance matrix based on the concordance dominance matrix and discordance dominance matrix.
     * 
     * @param cdm The concordance dominance matrix.
     * @param ddm The discordance dominance matrix.
     * @return The computed aggregate dominance matrix.
     */
    public int[][] getAggregateDominanceMatrix(double[][] cdm, double[][] ddm){ 
        int[][] aggregateDominanceMatrix = new int[cdm.length][cdm.length]; 
        for (int i = 0; i < aggregateDominanceMatrix.length; i++) { 
            for (int j = 0; j < aggregateDominanceMatrix[i].length; j++) { 
                if(i != j){ 
                aggregateDominanceMatrix[i][j] = (int)(cdm[j][i] * ddm[i][j]); 
                } 
            } 
        } 
        return aggregateDominanceMatrix; 
    } 
   
    /**
     * Computes the scores of each alternative based on the aggregate dominance matrix.
     * 
     * @param aggMatrix The aggregate dominance matrix.
     * @return The computed scores for each alternative.
     */
    public int[] computeScores(int[][] aggMatrix) { 
    	int numRows = aggMatrix.length; 
    	int numColumns = aggMatrix[0].length; 
    	int[] scores = new int[aggMatrix.length]; 

    	for (int k = 0; k < numRows; k++) { 
        	int sumRows = 0; 
    		int sumCols = 0; 
        	for (int i = 0; i < numColumns; i++) { 
        		sumRows += aggMatrix[k][i]; 
        	} 
        	for (int j = 0; j < numRows; j++) { 
        		sumCols += aggMatrix[j][k]; 
    		} 
        	scores[k] = sumRows - sumCols; 
    	} 
    	return scores; //need to order the scores (the lower the score, the higher the rank) 
    }
   
    /**
     * Sets the computed scores for each ontology and ranks the list in ascending order based on the scores.
     */
    public void setScores(){
        ArrayList<Ontology> ontologies = App.rankinglist.getOntologies();
        for(int i=0;i<scores.length;i++){
            ontologies.get(i).setScore(scores[i]);
        }
        App.rankinglist.rankListAsc();
    }
}
