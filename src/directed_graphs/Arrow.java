package directed_graphs;

public class Arrow {
	Arrow(double weight, String nextNodeName){
		this.weight = weight;
		this.nextNodeName = nextNodeName;
	}
	
	double weight;
	String nextNodeName;
}
