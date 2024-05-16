    public class Ontology{
    String name;
    double[] performances;//Scores obtained for the differnt metrics of the ontology
    double relativeCloseness;
    double score;

    public Ontology(String name,double[] performances){
        this.name=name;

        this.performances=performances;

    }
	public double getScore() {
		return score;
	}
        
   public void setScore(double score) {
		this.score = score;
	}
    public void setRelativeCloseness(double relativeCloseness){
        this.relativeCloseness =relativeCloseness;
    }
    public String getName(){
        return this.name;
    }

    public double getRelativeCloseness(){
        return relativeCloseness;
    }



}
