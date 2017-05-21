import java.util.*;

public class Tester
{

   public static void main(String[] args)
   {
      WeightedGraph g = new WeightedGraph(8);
 
      g.addEdge(0,1);
      g.addEdge(0,2);
      g.addEdge(0,3);
      g.addEdge(1,7);
      g.addEdge(2,3);
      g.addEdge(2,5);
      g.addEdge(2,7);
      g.addEdge(4,6);
      g.addEdge(5,4);
      g.addEdge(5,6);
      g.addEdge(5,7);
      g.addEdge(6,7);
      g.BFS(0);
   }//end main

}//end class