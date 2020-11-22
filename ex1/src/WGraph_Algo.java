package ex1.src;

import java.io.*;
import java.util.*;


public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;
    static final String FILE_NAME = "graph.txt";

    public WGraph_Algo(weighted_graph g) {
        this.graph = g;
    }

    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph copy = new WGraph_DS();
        WGraph_DS.NodeInfo temp, node;

        // Copy Nodes
        for (int i = 0; i < graph.nodeSize(); i++) {
            node = (WGraph_DS.NodeInfo) graph.getNode(i);

            if (node == null)
                continue;

            temp = (WGraph_DS.NodeInfo) copyNode(node);
            copy.addNode(temp.getKey());
        }

        // Copy Neighbours
        for (int i = 0; i < graph.nodeSize(); i++) {
            node = (WGraph_DS.NodeInfo) graph.getNode(i);

            if (node == null || node.getNi() == null)
                continue;

            Collection<node_info> ni = node.getNi();

            for (node_info ver : ni) {

                copy.connect(i, ver.getKey(), graph.getEdge(i, ver.getKey()));
            }
        }

        return copy;
    }

    // this method creating a deep copy of a node.
    public node_info copyNode(node_info node) {
        WGraph_DS.NodeInfo temp = new WGraph_DS.NodeInfo(node.getKey());
        temp.setInfo(node.getInfo());
        temp.setTag(node.getTag());


        return temp;
    }

    @Override
    public boolean isConnected() {
        Collection<node_info> ver = graph.getV();
        Iterator<node_info> iterator = ver.iterator();

        if (!iterator.hasNext())
            return true;

        int numOfNodes = graph.nodeSize();


        boolean[] checked = new boolean[numOfNodes];

        WGraph_DS.NodeInfo n = (WGraph_DS.NodeInfo) iterator.next();

        checkingNodes(n, checked);

        for (node_info node : ver) {
            if (!checked[graph.nodeSize()]) {
                return false;
            }
        }
        return true;
    }

    public void checkingNodes(WGraph_DS.NodeInfo node, boolean[] checked) {
        if (node == null) {
            return;
        }
        if (!checked[graph.nodeSize()]) {
            checked[graph.nodeSize()] = true;

            if (node.getNi() != null) {
                for (node_info n : node.getNi()) {
                    if (!checked[graph.nodeSize()]) {
                        checkingNodes((WGraph_DS.NodeInfo) n, checked);
                    }
                }
            }
        }
    }

//    @Override
//    public double shortestPathDist(int src, int dest) {
//        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
//            return -1;
//        }
//        LinkedList<node_info> list = new LinkedList<>();
//        list.add(graph.getNode(src));
//
//        //boolean[] visited = new boolean[graph.nodeSize()];
//
//        for (node_info temp : graph.getV()) {
//            temp.setTag(0);
//        }
//        System.out.println("a");
//        while (!list.isEmpty()) {
//            WGraph_DS.NodeInfo node = (WGraph_DS.NodeInfo) list.poll();
//            //visited[node.getKey()] = true;
//
//            System.out.println("b");
//            if (node != null) {
//                System.out.println("c");
//                for (node_info next : node.getNi()) {
//                    System.out.println("d");
//                    if (node.getParent() != next.getKey() ) {
//                        System.out.println("e");
//                        next.setTag(node.getTag() + getGraph().getEdge(node.getKey(), next.getKey()));
//                        list.add(next);
//                        ((WGraph_DS.NodeInfo) next).setParent(node.getKey());
//
//                    }
//                }
//            }
//
//        }
//        if (graph.getNode(dest).getTag() == 0) {
//            return -1;
//        } else return graph.getNode(dest).getTag();
//    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(src == dest)
            return 0;

        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return -1;
        }
        LinkedList<node_info> list = new LinkedList<>();

        node_info begin = graph.getNode(src);

        list.add(begin);

        boolean[] visited = new boolean[graph.nodeSize()];

        for (node_info temp : graph.getV()) {
            temp.setTag(0);
            temp.setInfo(src + "");
        }

        while (!list.isEmpty()) {
            WGraph_DS.NodeInfo node = (WGraph_DS.NodeInfo) list.poll();

            if (!visited[node.getKey()]) {
                visited[node.getKey()] = true;

                for (node_info next : node.getNi()) {
                    double w = next.getTag();

                    if (node.getTag() + graph.getEdge(node.getKey(), next.getKey()) < w || w == 0) {
                        visited[next.getKey()] = false;
                        next.setTag(node.getTag() + getGraph().getEdge(node.getKey(), next.getKey()));

                        String info = node.getKey() + "";

                        next.setInfo(info);

                        list.add(next);
                    }
                }
            }
        }
        if (graph.getNode(dest).getTag() == 0) {
            return -1;
        } else return graph.getNode(dest).getTag();
    }

    public List<node_info> shortestPath(int src, int dest) {
            shortestPathDist(src, dest);

            node_info node = graph.getNode(dest);
            node_info nodesrc = graph.getNode(src);

            ArrayList<node_info> list = new ArrayList<>();

            list.add(node);

            while(node.getInfo() != null && !node.getInfo().equals((src+""))) {
                node_info temp = graph.getNode(Integer.parseInt(node.getInfo()));

                if(temp != null) {
                    list.add(temp);

                    node = temp;
                }
            }

            list.add(nodesrc);

            return list;
    }

//    @Override
//    public List<node_info> shortestPath(int src, int dest) {
//        ArrayList<node_info> answer = new ArrayList<>();
//        double noPath = shortestPathDist(src, dest);
//        if (noPath == -1) {
//            return null;
//        }
//        WGraph_DS.NodeInfo node = ((WGraph_DS.NodeInfo) graph.getNode(dest));
//
//        if (node != null) {
//            while (node.getKey() != src) {
//                answer.add(node);
//                node = ((WGraph_DS.NodeInfo) graph.getNode(node.getParent()));
//            }
//            answer.add(node);
//        }
//        return answer;
//    }


    @Override
    public boolean save(String file) {
        try {
            PrintWriter pw = new PrintWriter(new File(FILE_NAME));

            String str1 = graph.toString();
            pw.write(str1);
            pw.close();
            System.out.println("The file have been saved");
            return true;

        } catch (Exception e) {
            System.out.println("There is a problem with saving this file");
            return false;
        }


    }

    @Override
    public boolean load(String file) {
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            while ((line = br.readLine()) != null) {
                String[] userInfo = line.split(";");

                System.out.println("graph:" + userInfo[0] + " , " + userInfo[1] + " , " + userInfo[2] + " , " + userInfo[3] + "\n");

            }
            return true;

        } catch (IOException e) {
            System.out.println("The file does not exist");
            return false;
        }
    }

}
