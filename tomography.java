//https://open.kattis.com/problems/tomography

import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.LinkedList; 
  
public class tomography
{  
  
    /* Returns true if there is a path from source 's' to sink 
      't' in residual graph. Also fills parent[] to store the 
      path */
    public static boolean bfs(int rGraph[][], int s, int t, int parent[], int V) 
    { 
        // Create a visited array and mark all vertices as not 
        // visited 
        boolean visited[] = new boolean[V]; 
        for(int i=0; i<V; ++i) 
            visited[i]=false; 
  
        // Create a queue, enqueue source vertex and mark 
        // source vertex as visited 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        queue.add(s); 
        visited[s] = true; 
        parent[s]=-1; 
  
        // Standard BFS Loop 
        while (queue.size()!=0) 
        { 
            int u = queue.poll(); 
  
            for (int v=0; v<V; v++) 
            { 
                if (visited[v]==false && rGraph[u][v] > 0) 
                { 
                    queue.add(v); 
                    parent[v] = u; 
                    visited[v] = true; 
                } 
            } 
        } 
  
        // If we reached sink in BFS starting from source, then 
        // return true, else false 
        return (visited[t] == true); 
    } 
  
    // Returns tne maximum flow from s to t in the given graph 
    public static int fordFulkerson(int graph[][], int s, int t, int V) 
    { 
        int u, v; 
  
        // Create a residual graph and fill the residual graph 
        // with given capacities in the original graph as 
        // residual capacities in residual graph 
  
        // Residual graph where rGraph[i][j] indicates 
        // residual capacity of edge from i to j (if there 
        // is an edge. If rGraph[i][j] is 0, then there is 
        // not) 
        int rGraph[][] = new int[V][V]; 
  
        for (u = 0; u < V; u++) 
            for (v = 0; v < V; v++) 
                rGraph[u][v] = graph[u][v]; 
  
        // This array is filled by BFS and to store path 
        int parent[] = new int[V]; 
  
        int max_flow = 0;  // There is no flow initially 
  
        // Augment the flow while tere is path from source 
        // to sink 
        while (bfs(rGraph, s, t, parent, V)) 
        { 
            // Find minimum residual capacity of the edhes 
            // along the path filled by BFS. Or we can say 
            // find the maximum flow through the path found. 
            int path_flow = Integer.MAX_VALUE; 
            for (v=t; v!=s; v=parent[v]) 
            { 
                u = parent[v]; 
                path_flow = Math.min(path_flow, rGraph[u][v]); 
            } 
  
            // update residual capacities of the edges and 
            // reverse edges along the path 
            for (v=t; v != s; v=parent[v]) 
            { 
                u = parent[v]; 
                rGraph[u][v] -= path_flow; 
                rGraph[v][u] += path_flow; 
            } 
  
            // Add path flow to overall flow 
            max_flow += path_flow; 
        } 
  
        // Return the overall flow 
        return max_flow; 
    } 



    public static String tomography(int graph[][], int s, int t, int V, int sum) 
    { if(fordFulkerson(graph, 0, t, V) == sum)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }
        
  
// Driver program to test above functions 
public static void main (String[] args) throws java.lang.Exception
{
        int mfGraph[][];

        Scanner input = new Scanner(System.in);    

        int rows = input.nextInt();
        int columns = input.nextInt();


        mfGraph = new int[rows + columns +  2][rows + columns + 2];


    //Connects rows to target
    int sum = 0;

    for(int i = 1; i <= rows; i++)
    {
        int curr = input.nextInt();
        mfGraph[i + columns ][rows + columns  + 1] = curr;
        sum += curr;
    }
    

    //Connects start vertex to columns
     for(int i = 1; i <= columns; i++)
    {
        mfGraph[0][i] = input.nextInt();

        //Connects every column vertex to every row vertex
        for(int j = 1; j <= rows; j++)
        {
            mfGraph[i][ columns + j] = 1;
        }
    }


System.out.print(tomography(mfGraph, 0, columns + rows + 1, columns + rows + 2, sum));
}

} 