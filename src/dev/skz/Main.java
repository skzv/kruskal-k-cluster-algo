package dev.skz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	    Graph graph = readGraph();
        ClusterResults clusterResults = kruskalCluster(graph, /* k= */ 4);
        System.out.println(clusterResults.minSpacing);
    }

    private static ClusterResults kruskalCluster(Graph graph, int k) {
        UnionFind unionFind = new UnionFind(graph.numNodes);

        List<Edge> edges = new ArrayList<>(graph.edges);

        // sort edges
        edges.sort(Comparator.comparingInt(i -> i.cost));

        // TODO: populate clusters
        List<List<Integer>> clusters = new ArrayList<>();
        int minSpacing = Integer.MIN_VALUE;

        Iterator<Edge> iter = edges.iterator();

        // pick off edges and check if adding edge creates a cycle
        while (iter.hasNext()) {
            Edge e = iter.next();

            if (unionFind.find(e.u) == unionFind.find(e.v)) {
                continue;
            }

            unionFind.union(e.u, e.v);

            if (unionFind.getNumClusters() == k) {
                clusters = unionFind.getClusters();
                break;
            }
        }

        while (iter.hasNext()) {
            Edge e = iter.next();

            if (unionFind.find(e.u) == unionFind.find(e.v)) {
                continue;
            }

            minSpacing = e.cost;
            break;
        }

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
        String file = "input.txt";
        List<Edge> edges = new ArrayList<>();
        int numNodes = 0;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                numNodes = Integer.parseInt(line);
            }
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                int u = Integer.parseInt(tokens[0]);
                int v = Integer.parseInt(tokens[1]);
                int cost = Integer.parseInt(tokens[2]);
                edges.add(new Edge(u, v, cost));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Graph(numNodes, edges);
    }

    public static class UnionFind {
        private final int[] parent;
        private final int[] size;
        private int numClusters;

        public UnionFind(int numNodes) {
            parent = new int[numNodes + 1];
            size = new int[numNodes + 1];
            Arrays.fill(size, 1);
            for (int i = 1; i <= numNodes; i++) {
                parent[i] = i;
            }
            numClusters = numNodes;
        }

        /* Returns root parent of node n. */
        public int find(int n) {
            int p = parent[n];
            while (p != parent[p]) {
                p = parent[p];
            }
            return p;
        }

        public void union(int x, int y) {
            // Invoke Find twice to locate the positions i and j of the roots of the parent graph trees that contain
            // x and y, respectively. If i = j, return.
            int i = find(x);
            int j = find(y);
            if (i == j) {
                return;
            }

            // If size(i) >= size(j), set parent(j) := i and size(i) := size(i) + size(j).
            if (size[i] >= size[j]) {
                parent[j] = i;
                size[i] = size[i] + size[j];
            } else {
                // If size(i) < size(j), set parent(i) := j and size(j) := size(i) + size(j).
                parent[i] = j;
                size[j] = size[i] + size[j];
            }

            numClusters--;
        }

        public int getNumClusters() {
            return numClusters;
        }

        public List<List<Integer>> getClusters() {
            return new ArrayList<>(new ArrayList<>());
        }
    }
}
