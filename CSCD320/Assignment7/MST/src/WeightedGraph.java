import java.util.*;

class WeightedGraph {
    private int size;
    private LinkedList<Edge>[] adjList;

    WeightedGraph(int i) {
        this.size = i;
        adjList = new LinkedList[size];
        for (int n = 0; n < size; n++) {
            adjList[n] = new LinkedList<>();
        }
    }

    void addEdge(int n1, int n2, int w) {
        Edge e = new Edge(n1, n2, w);
        adjList[e.vertex1].add(e);
        adjList[e.vertex2].add(e);
    }

    void Kruskals() {
        Set<Edge> mst = new HashSet<>();
        SortedSet<Edge> edges = new TreeSet<>();
        for (LinkedList l : adjList) {
            for (Object e : l) {
                edges.add((Edge) e);
            }
        }
        UnionFind uf = new UnionFind();
        int n = 0;
        for (Edge e : edges) {
            if(n == adjList.length)
                break;
<<<<<<< Updated upstream
            if(!uf.find(e.vertex1, e.vertex2)){
                uf.unite(e.vertex1, e.vertex2);
=======
            if (uf.merge(uf.find(e.vertex1), uf.find(e.vertex2))) {
>>>>>>> Stashed changes
                mst.add(e);
            }
            n++;
        }
        print(mst);
    }

    private void print(Set<Edge> a) {
        for (Edge i : a) {
            System.out.print(i.vertex1 + "->" + i.vertex2 + "\n");

        }
    }

    private class Edge implements Comparable<Edge> {
        private int vertex1, vertex2;
        private final double weight;

        Edge(int v1, int v2, double w) {
            this.vertex1 = v1;
            this.vertex2 = v2;
            this.weight = w;
        }

        public int compareTo(Edge that) {
            return (int) (this.weight - that.weight);
        }
    }

    private class UnionFind {
        private ArrayList<HashSet<Integer>> components;

        UnionFind(){
            components = new ArrayList<>();
        }
        boolean find(int vertex1, int vertex2){
            if(components.isEmpty())
                return false;
            else{
                for(HashSet<Integer> h : components){
                    if (h.contains(vertex1) && h.contains(vertex2)) {
                        return true;
                    }
                }
            }
            return false;
        }
        void unite(int vertex1, int vertex2){
            HashSet<Integer> temp = new HashSet<>();
            temp.add(vertex1);
            temp.add(vertex2);
            if(components.isEmpty()){
                components.add(temp);
            }
            else{
                int n = components.size();
                for(int i = 0; i < n; i++){
                    HashSet<Integer> temp2 = components.get(i);
                    if(temp2.contains(vertex1) && !temp2.contains(vertex2))
                        temp2.add(vertex2);
                    else if(temp2.contains(vertex2) && !temp2.contains(vertex1))
                        temp2.add(vertex1);
                    else
                        components.add(temp);
                    components.remove(i);
                    components.add(i, temp2);
                }
            }
        }

    }
}