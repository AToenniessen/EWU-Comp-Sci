import java.util.*;

// Weighted Undirected Graph
//Used your example code from canvas and modified it to make it more understanable for me
public class WeightedGraph
{
   private int V; 
   private LinkedList<Integer> adj[];

   public WeightedGraph(int v)
   {
       V = v;
       adj = new LinkedList[v];
       for (int i=0; i<v; ++i)
          adj[i] = new LinkedList();
   }

   public void addEdge(int v,int w)
   {
      adj[v].add(w);
   }
   
   public void BFS(int s)
   {
      boolean visited[] = new boolean[V];
      LinkedList<Integer> queue = new LinkedList<Integer>();
      visited[s]=true;
      ArrayList ara = new ArrayList();
      queue.add(s);
      while (queue.size() != 0)
      {
         s = queue.poll();
         ara.add(s);
         //System.out.print(s+"->");
         Iterator<Integer> i = adj[s].listIterator();
         while (i.hasNext())
         {
            int n = i.next();
            if (!visited[n])
            {
               visited[n] = true;
               queue.add(n);
            }//end if
         }//end inner while
      }//end outer while
      print(ara);
   }//end method
   
   public void print(ArrayList ara)
   {
      int x = 0;
      System.out.print(ara.get(x));
      for(x=1; x<ara.size();x++)
      {
         System.out.print("->" + ara.get(x));
      }
   }//end method
   
}//end class