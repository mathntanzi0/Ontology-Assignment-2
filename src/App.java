
public class App {

    public static RankingList rankinglist;
    public static void main(String[] args) throws Exception {
        TOPSIS topsis = new TOPSIS("dataset.txt");
        topsis.rankAlternatives();

        ELECTRE electre = new ELECTRE("dataset.txt");
        electre.rankAlternatives();
    }
}