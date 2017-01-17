package directed_graphs;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class FindGraphPath {
	
	final static String fileAddress = "C:\\Users\\Tal Weissler\\workspace\\directed_graphs\\words3.txt"; //change this to the path to the desired file

	static ArrayList<Node> nodesList = new ArrayList<>();
	static ArrayList<TerNode> terminationNodes = new ArrayList<>();
	
	final static String loop = "Endless Loop";
	static boolean negativeWeights = false;
	static boolean forbiddenInput = false;
	
	public static void main(String[] args){

		createGraph(); //turn the text file into an arraylist of nodes
		
		if(!negativeWeights && !forbiddenInput){
			System.out.println("Please enter a search word:");
			Scanner input = new Scanner(System.in);
			String inputWord = input.next();
			
			/* there are 3 possible cases for the input word: 
			1. the word is a termination word (then just print it with chance 1)
			2. the word isn't in the graph (then put up an error)
			3. the word is an originating word (then call a recursive function)
	
			*/
			
	
			if (searchListNodes(inputWord,nodesList)==-1){ //check if the word is a terminating word
				if(searchListArrows(inputWord,nodesList)){ //check if the word is an originating word
					terminationNodes.add(new TerNode(inputWord,1.0)); // case no.1
				}
				else{
					//the word isn't a terminating word or an originating word - it's not in the graph
					System.out.println("This word was not found in the graph"); // case no.2
				}
			}
			else{
				
				//call a recursive function that finds termination words and the chance of getting to them
				findTerminationNodes(nodesList.get(searchListNodes(inputWord,nodesList)),1.0); //case no.3
	
			}
			
			//print all of the termination words and the chances of getting to them
			for(TerNode printNode:terminationNodes){
				System.out.print("termination word: " + printNode.name + ", ");
				System.out.println("probability: " + printNode.chance);
			}
			
			input.close();
		} else if (negativeWeights){
			System.out.println("Negetive weights are not allowed");
		} else if (forbiddenInput){
			System.out.println("the word 'Endless Loop' not allowed");
		}
	}
	
	
	private static void createGraph(){
		try{
			
			Scanner scan = new Scanner(new File(fileAddress));
					
			while (scan.hasNext()) {
				//get each line and break into an array of 3 strings - ORIGINATING_WORD,WEIGHT_NUMBER,TERMINATING_WORD
				String nextLine = scan.nextLine();
				String[] edge = nextLine.split(":");
				
				//check if any of the weights are negative
				if(Double.parseDouble(edge[1])<0){
					negativeWeights = true;
				}
				//check if forbidden strings are inserted
				else if(edge[0].equals(loop) || edge[2].equals(loop)){
					forbiddenInput=true;
				}
				else{
				
					int nodeIndex = searchListNodes(edge[0],nodesList);
					
					//check if the word is in the list - if it isn't then add it, if it is then add another arrow to it
					if (nodeIndex==-1){
						//create new node with the originating word
						Node newNode = new Node(edge[0]);
						//add an arrow to it with the weight and the terminating word
						newNode.addArrow(new Arrow(Double.parseDouble(edge[1]),edge[2]));
						//add it to the list
						nodesList.add(newNode);
					}
					else{
						//add another arrow to the existing word
						nodesList.set(nodeIndex,nodesList.get(nodeIndex).addArrow(new Arrow(Double.parseDouble(edge[1]),edge[2])));
					}
				}
			}
			
			scan.close();
			
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	private static void findTerminationNodes(Node node, Double chance){
		
		//calculate the total weights of all of the arrows of the node
		Double totalWeights=0.0;
		for(Arrow arrow: node.arrow){
			totalWeights = totalWeights + arrow.weight;
		}
		
		//go through each arrow of the node
		for(Arrow arrow: node.arrow){
			//reset the chance to this node's chance
			Double thisChance = chance;
			//update the chance to this arrow's chance
			thisChance = thisChance*arrow.weight/totalWeights;
			if(searchListNodes(arrow.nextNodeName,nodesList)!=-1){
				if(!nodesList.get(searchListNodes(arrow.nextNodeName,nodesList)).visited){
					//if the node appear in the nodesList (which means it's not a termination node) then check it's arrows
					nodesList.get(searchListNodes(arrow.nextNodeName,nodesList)).setVisited();
					findTerminationNodes(nodesList.get(searchListNodes(arrow.nextNodeName,nodesList)),thisChance);
				}
				else{
					//if it gets stuck in an endless loop it will add it to the list with the chance of getting stuck in the loop
					int index = searchListTerNode(loop,terminationNodes);
					if(index==-1){
						//if it doesn't appear in the termination node list, we add it
						terminationNodes.add(new TerNode(loop,thisChance));
					}
					else{
						//if it appears in the termination node list, we update it by adding the new chance to the existing one 
						terminationNodes.set(index, new TerNode(loop,thisChance+terminationNodes.get(index).chance));
					}
				}
			}
			else{
				//if the node doesn't appear in the nodesList (which means it's a termination node) 
				//then add or update it to the termination nodes list
				int index = searchListTerNode(arrow.nextNodeName,terminationNodes);
				if(index==-1){
					//if it doesn't appear in the termination node list, we add it
					terminationNodes.add(new TerNode(arrow.nextNodeName,thisChance));
				}
				else{
					//if it appears in the termination node list, we update it by adding the new chance to the existing one 
					//(there is more than one rout to get to that node, so we add the chances of all of them) 
					terminationNodes.set(index, new TerNode(arrow.nextNodeName,thisChance+terminationNodes.get(index).chance));
				}
			}
		}
	}
	
	
	
	///////helper search methods////////
	
	
	//search a word in an arraylist of nodes
	//this function finds only originating words (finds words that aren't in termination nodes) and returns it's index in the list
	private static int searchListNodes(String name, ArrayList<Node> list){
		for(int i=0;i<list.size();i++){
			if (list.get(i).name.equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	
	//search a word in an arraylist of nodes
	//this function finds only terminating words and returns true if the word is found
	private static boolean searchListArrows(String name, ArrayList<Node> list){
		for(int i=0;i<list.size();i++){
			Node searchNode = list.get(i);
			for(int j=0;j<searchNode.arrow.size();j++){
				if (searchNode.arrow.get(j).nextNodeName.equals(name)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	//search a word in an arraylist of termination nodes and return it's index in the list
	private static int searchListTerNode(String name, ArrayList<TerNode> list){
		for(int i=0;i<list.size();i++){
			if (list.get(i).name.equals(name)){
				return i;
			}
		}
		return -1;
	}

}
