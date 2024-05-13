
package electre;

public class Main {
	String fileName;
    double[][] decisionMatrix;
    double[][] normalizeDecisionMatrix;
    RankingList rankinglist;

	
	public static void main(String[] args) {
		ELECTRE elec = new ELECTRE("Dataset.txt");
	}

}
