
import java.io.*;
import java.lang.System.Logger;
import java.util.*;

public class Electre{
	String fileName;
    double[][] decisionMatrix;
    double[][] normalizeDecisionMatrix;
    double[] weights;
    double[][] weightedMatrix;

    HashMap<String, ArrayList<Integer>> concordance;
    HashMap<String, ArrayList<Integer>> discordance;

    double[][] concodanceMatrix;
    double[][] discordanceMatrix;

    double[][] concodanceDominanceMatrix;
    double[][] discordanceDominanceMatrix;

    int[][] AggregateDominanceMatrix;
    double[] scores;
	RankingList rankinglist;




    public Electre(String fileName){
        this.fileName=fileName;
        apply_Electre(fileName);
    }
    public void apply_Electre(String fileName){

        rankinglist=new RankingList();

        this.fileName=fileName;
        try {
            this.decisionMatrix=buildDecisonMatrix(fileName);
            this.normalizeDecisionMatrix=normalizeDecisionMatrix(decisionMatrix);
            this.weights = calculateWeights(normalizeDecisionMatrix);
            this.weightedMatrix = weightedNormalizedMatrix(normalizeDecisionMatrix, weights);

            this.concordance = getConcordance(weightedMatrix);
            this.discordance = getDiscordance(weightedMatrix);

            this.concodanceMatrix = getConcordanceMatrix(concordance);
            this.discordanceMatrix = getDiscordanceMatrix(discordance, weightedMatrix);
            this.concodanceDominanceMatrix = getdominanceMatrix(concodanceMatrix);
            this.discordanceDominanceMatrix = getdominanceMatrix(discordanceMatrix);
	this.AggregateDominanceMatrix = getAggregateDominanceMatrix(concodanceDominanceMatrix, discordanceDominanceMatrix);
            this.scores = computeScores(AggregateDominanceMatrix);
            
        } catch (Exception ex) {
            System.out.println("Problem with file");
            ex.printStackTrace();
        }

        /*
        orderRankingList();
        printRankingList(getRankings());*/


    }
    
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
            rankinglist.addOntology(ontology);
        }
        scanner.close();

          return temp_DecisionMatrix;

	}//eof
	
	public  double[][] normalizeDecisionMatrix(double[][] decisionMatrix){
        double []normalising_constant= calculateNormalisingConstants(decisionMatrix);

        //Divide Each element of the Decision Matrix by the Normalising Constant of its Column
        double[][] normalizedDecisionMatrix=new double[decisionMatrix.length][decisionMatrix[0].length];

        for (int c=0;c<decisionMatrix[0].length;c++){

            for (int r=0;r<decisionMatrix.length;r++){
                normalizedDecisionMatrix[r][c]= decisionMatrix[r][c]/normalising_constant[c];
                System.out.print(normalizedDecisionMatrix[r][c]+"\t");
            }
            System.out.println("");
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
	

    public static double[][] weightedNormalizedMatrix(double[][] normalizeDecisionMatrix, double[] weights) {
        int numRows = normalizeDecisionMatrix.length;
        int numCols = normalizeDecisionMatrix[0].length;
        double[][] weightedMatrix = new double[numRows][numCols];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                weightedMatrix[r][c] = normalizeDecisionMatrix[r][c] * (1.0/11.0);
                //System.out.print(weightedMatrix[r][c]+"\t");
            }
            //System.out.println("");
        }
        return weightedMatrix;
    }
        
    public static double[] calculateWeights(double[][] normalizeDecisionMatrix) {
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

        return normalizedGeometricMeans;
    }

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
                        //add index to list
                        x.get(rs).add(k);
                    }
                }//k
                if(x.get(rs).size() == 0){
                    x.get(rs).add(-1);
                }
            }//j
        }//i
        System.out.println(x.get("0,1"));
        return x;
    }//eof

    public HashMap<String, ArrayList<Integer>> getDiscordance(double[][] weighteddMatrix){
        HashMap<String, ArrayList<Integer>> discordanceMap = new HashMap<String, ArrayList<Integer>>();
        HashMap<String, ArrayList<Integer>> concMap = getConcordance(weighteddMatrix);
        for (int r = 0; r < 50; r++) {
            for (int s = 0; s < weighteddMatrix.length; s++) {
                if(r == s){
                    continue;
                }
                String rs = String.valueOf(r) + ","+ String.valueOf(s);
                discordanceMap.put(rs,new ArrayList<Integer>());
                for (int k = 0; k < 11; k++) {
                    if(concMap.get(rs).contains(k) != true){
                        discordanceMap.get(rs).add(k);
                    }
                }//k
                if(discordanceMap.get(rs).size() == 0){
                    discordanceMap.get(rs).add(-1);
                }
            }//j
        }//i
        return discordanceMap;
    }//eof

    public double[][] getConcordanceMatrix(HashMap<String, ArrayList<Integer>> conHashMap){
        double[][] concMatrix = new double[50][50];

        Set<String> keys = conHashMap.keySet();
        for (String key : keys) {
            String[] temp = key.split(",");
            int r = Integer.valueOf(temp[0]);
            int s = Integer.valueOf(temp[1]);
            double sum = 0;
            for (int i = 0; i < 11; i++) {
                if(conHashMap.get(key).contains(i)){
                    sum += weights[i];
                }
            }
            concMatrix[r][s] = sum;
        }



        return concMatrix;
    }

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

    public double[][] getdominanceMatrix(double[][] Matrix){
        double[][] dominanceMatrix = new double[50][50];
        double l = 50*(50-1);
        double threshold = 0;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if(i != j){
                    threshold += Matrix[i][j];
                }
            }
        }

        threshold /= l;
        for (int i = 0; i < Matrix.length; i++) {
            for (int j = 0; j < Matrix.length; j++) {
                if((Matrix[i][j] >= threshold) && (i != j)){
                    dominanceMatrix[i][j] = 1;
                }
                else if((Matrix[i][j] < threshold) && (i != j)){
                    dominanceMatrix[i][j] = 0;
                }
            }
        }

        return dominanceMatrix;
    }

    public int[][] getAggregateDominanceMatrix(double[][] cdm, double[][] ddm){
        int[][] AggregateDominanceMatrix = new int[50][50];
        for (int i = 0; i < AggregateDominanceMatrix.length; i++) {
            for (int j = 0; j < AggregateDominanceMatrix[i].length; j++) {
                if(i != j){
                    AggregateDominanceMatrix[i][j] = (int)(cdm[j][i] * ddm[i][j]);
                }
            }
            
        }
        return AggregateDominanceMatrix;
    }


    public double[] computeScores(double[][] decisionMatrix) {
    	int numRows = decisionMatrix.length;
    	int numColumns = decisionMatrix[0].length;
    	double[] scores = new double[numAlternatives];

    	for (int k = 0; k < numRows; k++) {
        	double score = 0.0;
        	for (int i = 0; i < numColumns; i++) {
            		double sumRows = 0.0;
            		double sumCols = 0.0;
            		for (int j = 0; j < numRows; j++) {
                		sumRows += decisionMatrix[k][i];
                		sumCols += decisionMatrix[j][k];
            		}
            		score += sumRows - sumCols;
        	}
        	scores[k] = score;
    	}
    	return scores; //need to order the scores (the lower the score, the higher the rank)
    }



}//class
