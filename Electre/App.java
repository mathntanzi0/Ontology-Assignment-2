public class App{
	String fileName;
    double[][] decisionMatrix;
    double[][] normalizeDecisionMatrix;
    RankingList rankinglist;

	
	public static void main(String[] args) {
		Electre elec = new Electre("Dataset.txt");
	}

}