public class Ontology{
    String name;
    double[] performances;//Scores obtained for the differnt metrics of the ontology
<<<<<<< HEAD
=======
    double relativeCloseness;
>>>>>>> dfc8acd0556fc29714adc748da4a21a46e3de560
    double score;

    public Ontology(String name,double[] performances){
        this.name=name;
        this.performances=performances;
    }
	public double getScore() {
		return score;
	}
        
<<<<<<< HEAD
    public void setScore(double score) {
=======
   public void setScore(double score) {
>>>>>>> dfc8acd0556fc29714adc748da4a21a46e3de560
		this.score = score;
	}
    public void setRelativeCloseness(double relativeCloseness){
        this.score =relativeCloseness;
    }
    public String getName(){
        return this.name;
    }

    public double getRelativeCloseness(){
        return score;
    }

}