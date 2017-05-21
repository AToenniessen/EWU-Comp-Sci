import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class WeightedGraph {
    private int size;
    private LinkedList<Edge>[] adjList;

    WeightedGraph(int i){
        this.size = i;
        adjList = new LinkedList[size];
        for(int n = 0; n < size; n++){
            adjList[n] = new LinkedList<>();
        }
    }
    void addEdge(int n1, int n2, int w){
        Edge e = new Edge(n1, n2, w);
        adjList[e.vertex1].add(e);
        adjList[e.vertex2].add(e);
    }
    void breadthFirstTraversal(int n){
        boolean visited[] = new boolean[this.size];
        Queue<Integer> list = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();
        visited[n] = true;
        list.add(n);
        while(!list.isEmpty()){
            n = list.poll();
            res.add(n);
            for(Edge o : adjList[n]){
                if(!visited[o.vertex2]){
                    visited[o.vertex2] = true;
                    list.add(o.vertex2);
                }
            }
        }
        print(res);
    }
    private void print(ArrayList a){
        int n = 1;
        System.out.println("The result of the BFS traversal starting from vertex 0 is:");
        for(Object i : a){
            if(n < this.size)
                System.out.print(i + "->");
            else
                System.out.print(i + "\n");
            n++;
        }
    }

    private class Edge{
        private int vertex1, vertex2;
        private final Double weight;
        Edge(int v1, int v2, double w){
            this.vertex1 = v1;
            this.vertex2 = v2;
            this.weight = w;
        }
    }
}
