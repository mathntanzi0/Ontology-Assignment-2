    public class Ontology{
    String name;
    double[] performances;//Scores obtained for the differnt metrics of the ontology
    double relativeCloseness;

    public Ontology(String name,double[] performances){
        this.name=name;

        this.performances=performances;

    }

   
    public void setRelativeCloseness(double relativeCloseness){
        this.relativeCloseness =relativeCloseness;
    }

    public double getRelativeCloseness(){
        return relativeCloseness;
    }



}
