package directed_graphs;

import java.util.ArrayList;

public class Node {
	ArrayList<Arrow> arrow;
	
	Node(String name){
		this.name = name;
		this.arrow = new ArrayList<>();
		this.visited=false;
	}
	
	public Node addArrow(Arrow newArrow)
    {
        this.arrow.add(newArrow);
        return this;
    }
	
	public void setVisited()
    {
		this.visited=true;
    }
	
	String name;
	boolean visited;

}
