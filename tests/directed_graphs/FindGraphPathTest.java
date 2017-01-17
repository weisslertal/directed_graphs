package directed_graphs;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class FindGraphPathTest {

	@Test
	public void test() {
		FindGraphPath.createGraph();
		String inputWord = "boom";
		FindGraphPath.findTerminationNodes(FindGraphPath.nodesList.get(FindGraphPath.searchListNodes(inputWord,FindGraphPath.nodesList)),1.0);
		
		ArrayList<TerNode> expected = new ArrayList<>();
		expected.add(new TerNode("alimentary",0.16666666666666666));
		expected.add(new TerNode("littoral", 0.0625));
		expected.add(new TerNode("minibus",0.1875));
		expected.add(new TerNode("shellfish",0.08333333333333333));
		expected.add(new TerNode("gesture",0.38461538461538464));
		expected.add(new TerNode("turn",0.11538461538461539));
				
		for(int i=0;i<expected.size();i++){
			assertEquals(expected.get(i).name,FindGraphPath.terminationNodes.get(i).name);
			assertEquals(expected.get(i).chance,FindGraphPath.terminationNodes.get(i).chance);
		}

	}

}
