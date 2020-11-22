package ex1.tests;

import ex1.src.*;

import java.util.List;
import org.junit.jupiter.api.Test;
public class WGraph_DSTest {
    @Test
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Test1();
        System.out.println("Test 1 Runtime: " + (System.currentTimeMillis() - start)/1000F + "\n");
    }

    public static weighted_graph createGraph(int node_range, int edge_range) {
        weighted_graph g = new WGraph_DS();

        int i = 0;

        while(i < node_range) {
            g.addNode(i++);
        }

        while(g.edgeSize() < edge_range) {
            int a = (int)(Math.random() * node_range);
            int b = (int)(Math.random() * node_range);
            double w = Math.max(50,Math.random() * 100);

            g.connect(a, b, w);
        }

        return g;
    }

    // create a graph with 1,000,000 nodes and 2,000,000 edges
    public static void Test1() {

            weighted_graph g = createGraph(1000000, 2000000);
            System.out.println("Successfully created graph with " + 1000000 + " nodes and " + 2000000 + " edges!");

    }
}
