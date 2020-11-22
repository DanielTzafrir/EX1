package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class WGraph_AlgoTest {
    @Test
    public void test() throws FileNotFoundException {
            long start = System.currentTimeMillis();

            weighted_graph g = new WGraph_DS();
            g.addNode(0);
            g.addNode(1);
            g.addNode(2);
            g.addNode(3);
            g.addNode(4);
            g.connect(0,4, 29);
            g.connect(0,1, 41);
            g.connect(0,2, 17);
            g.connect(4,1, 9);
            g.connect(4,2, 9);
            g.connect(3,2, 11);

            Test2(g);

            System.out.println("Test 2 Runtime: " + (System.currentTimeMillis() - start)/1000F + "\n");

            start = System.currentTimeMillis();

            Test3(g);

            System.out.println("Test 3 Runtime: " + (System.currentTimeMillis() - start)/1000F + "\n");
        }

        // Shortest path + distance test
        public static void Test2(weighted_graph graph) {
            weighted_graph_algorithms wGraphAlgo = new WGraph_Algo(graph);

            for(int i = 0; i < graph.nodeSize(); i++) {
                for(int j = 0; j < graph.nodeSize(); j++) {
                    System.out.println("Shortest path from " + i + " to " + j + " is: " + wGraphAlgo.shortestPathDist(i, j));
                    List<node_info> path = wGraphAlgo.shortestPath(i,j);
                    System.out.println("Path: " + ((path == null) ? "None" : path.toString()) + "\n");
                }
            }
        }

        // Save, Load, Init and copy test
        public static void Test3(weighted_graph graph) throws FileNotFoundException {
            weighted_graph_algorithms wGraphAlgo = new WGraph_Algo(graph);
            weighted_graph initGraph;
            weighted_graph copyGraph;

            wGraphAlgo.save("graph1.txt");
            wGraphAlgo.load("mistake.txt"); // file not found exception
            wGraphAlgo.load("graph1.txt");

            initGraph = wGraphAlgo.getGraph();

            wGraphAlgo.init(initGraph);

            copyGraph = wGraphAlgo.copy();

            System.out.println(graph);
            System.out.println(initGraph);
            System.out.println(copyGraph);
        }
    }

