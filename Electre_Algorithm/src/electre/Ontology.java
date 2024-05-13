package electre;

public class Ontology {
	String name;
	double[] performances;
	double score;
	
	public Ontology(String name, double[] performances){
        this.name=name;
        this.performances=performances;
    }
	
	public void setScore(double score) {
		this.setScore(score);
	}
	
	public String getName(){
        return this.name;
    }
	
	public double getScore() {
		return score;
	}
}
