package dev.skz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	    Graph graph = readGraph();
        ClusterResults clusterResults = kruskalCluster(graph);
        System.out.println(clusterResults.minSpacing);
    }

    private static ClusterResults kruskalCluster(Graph graph) {
        UnionFind unionFind = new UnionFind(graph.numNodes);

        List<Edge> edges = new ArrayList<>(graph.edges);

        // sort edges
        edges.sort(Comparator.comparingInt(i -> i.cost));

        // pick off edges and check if adding edge creates a cycle
        for (Edge e : edges) {
            if (unionFind.find(e.u) == unionFind.find(e.v)) {
                continue;
            }
            
        }

        List<List<Integer>> clusters = new ArrayList<>();
        int minSpacing = 0;

        return new ClusterResults(clusters, minSpacing);
    }

    public static class ClusterResults {
        List<List<Integer>> clusters;
        int minSpacing;

        public ClusterResults(List<List<Integer>> clusters, int minSpacing) {
            this.clusters = clusters;
            this.minSpacing = minSpacing;
        }
    }

    public static class Graph {
        int numNodes;
        List<Edge> edges;

        public Graph(int numNodes, List<Edge> edges) {
            this.numNodes = numNodes;
            this.edges = edges;
        }
    }

    public static class Edge {
        int u;
        int v;
        int cost;

        public Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
    }

    public static Graph readGraph() {
        return new Graph(0, new ArrayList<>());
    }

    public static class UnionFind {
        int[] parent;

        public UnionFind(int numNodes) {
            parent = new int[numNodes];
        }

        /* Returns root parent of node i. */
        public int find(int i) {
            return 0;
        }

        public void union(int i, int j) {
            return;
        }
    }
}
