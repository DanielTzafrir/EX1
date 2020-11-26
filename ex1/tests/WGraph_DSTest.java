package ex1.tests;

import ex1.src.*;

import java.util.List;
import org.junit.jupiter.api.Test;
public class WGraph_DSTest {
    @Test
    public static void main(String[] args) {
        Test1();
    }

    public static weighted_graph createGraph(int nodeRange, int edgeRange) {
        weighted_graph wGraph_ds = new WGraph_DS();
        int i = 0;

        while(i < nodeRange) {
            wGraph_ds.addNode(i++);
        }

        while(wGraph_ds.edgeSize() < edgeRange) {
            int randomNode1 = (int)(Math.random() * nodeRange);
            int randomNode2 = (int)(Math.random() * nodeRange);
            double w = Math.max(20,Math.random() * 50);

            wGraph_ds.connect(randomNode1, randomNode2, w);
        }

        return wGraph_ds;
    }

    // create a graph with 1,000,000 nodes and 2,000,000 edges
    public static void Test1() {

            weighted_graph g = createGraph(1000000, 2000000);
            System.out.println("Successfully created the graph in test 1!");

    }
}
