Author: Tal Weissler

This program reads a directed graph from a text file. when a search word is entered (that appears in the graph) it prints all possible termination nodes from that word  and the probability of getting to them. probabilities are calculated according to the weights.

Explenation of what a directed graph is and of basic related terms (such as nodes and edges):
http://mathinsight.org/definition/directed_graph

Input:
The input will be a text file. Change the variable "fileAddress" to the path to your file.
The file describes the edges of the graph. It is formatted as a simple text file where each line describes a single edge using a colon-delimited format as such:

ORIGINATING_WORD:WEIGHT_NUMBER:TERMINATING_WORD

For example:       someWord:1:anotherWord

Restrictions: the weights have to be non-negative numbers but don't have to be integers (1.5384 is allowed).
The words can contain all keys including whitespace, except for the string "Endless Loop".
The input search word has to be a word that exists in the graph for the program to compute a proper output.

Output:
The program prints every possible termination node (nodes from which there is no outgoing edge) that can be reached from the starting node (chosen by entered search word). The program also prints the chances of getting to each termination node assuming a traveler will travel the graph by selecting an outgoing edge in a weighted (according to the edge weight)uniformly distributed random selection.

Special cases: in case a termination node is inserted as input, that word will be printed with the probability of 1. 
In case of loops in the graphs, the program will print "Endless Loop" as one of the termination nodes along with the probability of entering and getting stuck in a loop. 

Example:
For the input file:
dad:1:mom/* !&
dad:3:child
child:5.84765:sister
child:10:brother
sister:1:child
And input search word:
	dad
The output will be:
termination word: mom/* !&, probability: 0.25
termination word: Endless Loop, probability: 0.27674371279022436
termination word: brother, probability: 0.4732562872097756
