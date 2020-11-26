package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class WGraph_AlgoTest {
    @Test
    public void test() throws FileNotFoundException {
            long start = System.currentTimeMillis();

            weighted_graph weightedGraph = new WGraph_DS();
            weightedGraph.addNode(0);
            weightedGraph.addNode(1);
            weightedGraph.addNode(2);
            weightedGraph.connect(0,1, 6);
            weightedGraph.connect(0,2, 9);
            weightedGraph.connect(3,2, 13);

            Test2(weightedGraph);
            Test3(weightedGraph);

        }

        public static void Test2(weighted_graph weighted_graph) {
            weighted_graph_algorithms graph_algo = new WGraph_Algo(weighted_graph);

            for(int i = 0; i < weighted_graph.nodeSize(); i++) {
                for(int j = 0; j < weighted_graph.nodeSize(); j++) {
                    System.out.println(" The Shortest path: " + i + " ==> " + j + ": " + graph_algo.shortestPathDist(i, j));
                    List<node_info> path = graph_algo.shortestPath(i,j);
                    System.out.println(path.toString());
                }
            }
        }


        public static void Test3(weighted_graph graph) throws FileNotFoundException {
            weighted_graph_algorithms wGraphAlgo = new WGraph_Algo(graph);
            weighted_graph init;
            weighted_graph copy;

            wGraphAlgo.save("graph.txt");
            wGraphAlgo.load("graph.txt");

            init = wGraphAlgo.getGraph();
            copy = wGraphAlgo.copy();
            wGraphAlgo.init(init);


            System.out.println(graph);
            System.out.println(init);
            System.out.println(copy);
        }
    }

