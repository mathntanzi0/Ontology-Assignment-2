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

    public void rankListDesc(){
        //Sort the array list in descending order of score
    	Collections.sort(ontologies, Comparator.comparingDouble(Ontology::getScore).reversed());
    } //end method
    
    public void rankListAsc(){
        //Sort the array list in ascending order of score
    	Collections.sort(ontologies, Comparator.comparingDouble(Ontology::getScore));
    } //end method
}
    
