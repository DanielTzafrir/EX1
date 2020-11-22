package ex1.src;

import java.util.*;


public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, node_info> graph;
    public int counterEdges;
    public int counterNodes;

    public WGraph_DS() {
        this.graph = new HashMap<>();
        counterEdges = 0;
        counterNodes = 0;
    }

    @Override
    public node_info getNode(int key) {
        return this.graph.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        node_info node = getNode(node1);
        return ((NodeInfo)node).hasNi(node2);
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (node1 == node2 || !this.hasEdge(node1,node2))
            return -1;

        node_info node = getNode(node1);

        return ((NodeInfo)node).getEdges(node2);
    }

    @Override
    public void addNode(int key) {
        if (!graph.containsKey(key)) {
            node_info node = new NodeInfo();
            ((NodeInfo)node).setKey(key);
            graph.put(key, node);
            counterNodes++;

        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        NodeInfo temp1 = ((NodeInfo) getNode(node1));
        NodeInfo temp2 = ((NodeInfo) getNode(node2));

        if (temp1 == null || temp2 == null) {
            return;
        }
        if (!hasEdge(node1, node2)) {
            temp1.addNi(temp2);
            temp2.addNi(temp1);
            counterEdges++;
        }

        temp1.edges.put(node2, w);
        temp2.edges.put(node1, w);

    }

    @Override
    public Collection<node_info> getV() {
        return this.graph.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        NodeInfo temp = ((NodeInfo)getNode(node_id));

        return temp.getNi();
    }

    @Override
    public node_info removeNode(int key) {
        if (graph.containsKey(key)) {
            NodeInfo temp = ((NodeInfo) graph.get(key));

            graph.remove(key);

            Collection<Integer> tem = temp.edges.keySet();

            for (int nodesWithEdges : tem) {
                    removeEdge(nodesWithEdges,key);
            }
            counterNodes--;

            return temp;
        }

        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        NodeInfo temp1 = ((NodeInfo) getNode(node1));
        NodeInfo temp2 = ((NodeInfo) getNode(node2));


        temp1.getNi().remove(temp2);
        temp2.getNi().remove(temp1);

        temp1.edges.remove(node2);
        temp2.edges.remove(node1);
        counterEdges--;
    }

    @Override
    public int nodeSize() {
        return counterNodes;
    }

    @Override
    public int edgeSize() {
        return counterEdges;
    }

    @Override
    public int getMC() {
        return 0;
    }

    @Override
    public String toString() {
        ArrayList<Integer> nodes = new ArrayList<>();
        HashMap<Double,String> edges = new HashMap<>();
        int temp = 0;

        while (!graph.containsKey(temp)){
            temp++;
        }

        for (int i = temp; i < counterNodes + temp; i++) {
            node_info node = graph.get(i);

            if (node != null) {
                nodes.add(graph.get(i).getKey());

                for (node_info ni : ((NodeInfo)node).getNi()) {

                    String edge;

                    if (node.getKey() > ni.getKey())
                        edge = node.getKey() + " " + ni.getKey();
                    else
                        edge = ni.getKey() + " " + node.getKey();

                    if (!edges.containsValue(edge))
                        edges.put(((NodeInfo) node).getEdges(ni.getKey()),edge);
                }
            }
        }

        return "Nodes: " + nodes.toString() + "; Numbers of nodes: " + counterNodes +  "; "
                + "Edges: " + edges.toString() + "; numbers of edges: " + counterEdges + "\n";
    }

    public static class NodeInfo implements node_info {
        private int key = 0;
        private String Info;
        private double tag;
        private Collection<node_info> ni;
        private HashMap<Integer, Double> edges;
        private static int count = 0;
        private int parent = -1;


        public NodeInfo() {
            this.key++;
            this.tag = -1;
            this.ni = new ArrayList<>();
            this.edges = new HashMap<>();

        }
        public NodeInfo(int key) {
            this.key = key;
            this.ni = new ArrayList<>();
            NodeInfo.count++;
        }

        public void setParent(int parent){
            this.parent = parent;
        }

        public int getParent(){
            return this.parent;
        }

        public void setKey(int key){
            this.key = key;
        }
        public Collection<node_info> getNi() {
            return this.ni;
        }

        public Double getEdges(int neighborKey) {
            if(!this.edges.containsKey(neighborKey))
                return -1.0;

            return this.edges.get(neighborKey);
        }

        public boolean hasNi(int key) {
            for (node_info temp : this.ni) {
                if (temp.getKey() == key) return true;
            }
            return false;
        }

        public void addNi(node_info t) {
            ni.add(t);
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.Info;
        }

        @Override
        public void setInfo(String s) {
            this.Info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public String toString() {
            return this.key + "";
        }
    }
}
