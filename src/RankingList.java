import java.util.*; 
public class RankingList {
    private ArrayList<Ontology> ontologies;

    public ArrayList<Ontology> getOntologies() {
        return ontologies;
    }

    public RankingList(){
        ontologies=new ArrayList<>();
    }
    //Add Ontology to List
    public void addOntology(Ontology ont){
        ontologies.add(ont);
    }

    public void sortRankingList(){
        //Sort the array list in descending order of relative closeness
        Collections.sort(ontologies,Comparator.comparingDouble(Ontology::getRelativeCloseness).reversed());
    }
}
    