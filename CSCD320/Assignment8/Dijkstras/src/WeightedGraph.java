import java.util.*;

class WeightedGraph {
    private int size;
    private LinkedList<Edge>[] adjList;
    private ArrayList<Vertex> vertices = new ArrayList<>();

    WeightedGraph(int i) {
        this.size = i;
        adjList = new LinkedList[size];
        for (int n = 0; n < size; n++) {
            adjList[n] = new LinkedList<>();
        }
        vertices.add(new Vertex(1, 0));
        for(int n = 2; n < size; n++){
            vertices.add(new Vertex(n, Integer.MAX_VALUE));
        }
    }
    private void setAdj(){
        for(int l = 1; l < adjList.length-1; l++){
            Vertex temp = vertices.get(l - 1);
            for(Edge e : adjList[l]){
                temp.adj.add(e);
            }
        }
    }

    void addEdge(int n1, int n2, int w) {
        Edge e = new Edge(n1, n2, w);
        adjList[e.vertex1.vertex].add(e);
    }
    void Dijkstras(int n){
        setAdj();
        Vertex source = vertices.get(n - 1);
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(source);
        while(!queue.isEmpty()){
            Vertex temp1 = queue.poll();
            for(Edge e : temp1.adj){
                Vertex temp2 = vertices.get(e.vertex2.vertex - 1);
                int weight = e.weight;
                int distanceTo = temp1.distance + weight;
                if(distanceTo < temp2.distance){
                    queue.remove(temp2);
                    temp2.distance = distanceTo;
                    temp2.previous = temp1;
                    queue.add(temp2);
                }
            }
        }
        int i = 1;
        String ans;
        for(Vertex v : vertices){
                ans = shortestPath(v);
                System.out.println("Path " + i + "): " + "vertex 1 to vertex " + v.vertex + ", " + ans + " length " + v.distance);
                i++;
            }
    }
    private String shortestPath(Vertex targ){
        List<Vertex> path = new ArrayList<>();
        for(Vertex ver = targ; ver != null; ver = ver.previous){
            path.add(ver);
        }
        Collections.reverse(path);
        String res = "";
        int n = 0;
        int end = path.size();
        for(Vertex v : path){
            if(n == end - 1)
                res = res + v.vertex;
            else
                res = res + v.vertex + " -> ";
            n++;
        }
        return res;
    }
    private class Vertex implements Comparable<Vertex>{
        int vertex, distance;
        Vertex previous;
        ArrayList<Edge> adj = new ArrayList<>();
        Vertex(int v, int d){
            vertex = v;
            distance = d;
        }
        public int compareTo(Vertex that){
            return this.distance - that.distance;
        }
    }
    private class Edge implements Comparable<Edge> {
        private Vertex vertex1, vertex2;
        private final int weight;

        Edge(int v1, int v2, int w) {
            this.vertex1 = new Vertex(v1, w);
            this.vertex2 = new Vertex(v2, w);
            this.weight = w;
        }

        public int compareTo(Edge that) {
            return this.weight - that.weight;
        }
    }
}