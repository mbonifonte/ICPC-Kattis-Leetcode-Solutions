//https://open.kattis.com/problems/waif

import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.LinkedList; 
  
public class waif
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
  
// Driver program to test above functions 
public static void main (String[] args) throws java.lang.Exception
{
        int toyGraph[][];
        int mfGraph[][];

        Scanner input = new Scanner(System.in);    

        int childCount = input.nextInt();
        int toyCount = input.nextInt();
        int categoryCount = input.nextInt();
//Creates an n x m matrix
        int childGraph[][] = new int[childCount][toyCount + 1];

        //Create the distance graph of size |p|x|m|
        toyGraph = new int[categoryCount][toyCount+2];

        //Create the flight graph of size |toys + child + 2| x |toys + child + 2|

        mfGraph = new int[(categoryCount +toyCount + childCount + 2)][(categoryCount +toyCount + childCount + 2)];

        //Store the children into graph
    for(int i = 1; i <= childCount; i++)
    {
        int k = input.nextInt();
        childGraph[i-1][0] = k;
        for(int j = 0; j <= k-1; j++)
        {
            childGraph[i-1][j+1 ]= input.nextInt();
        }
    }


   if(categoryCount != 0){

    for(int i = 1; i <= categoryCount; i++)
    {
        int l = input.nextInt();
        toyGraph[i-1][0] = l;
        for(int j = 0; j <= l; j++)
        {
            toyGraph[i-1][j+1] = input.nextInt();
        }
    }
}






   //Connect source to categories
    for(int i = 1; i <= categoryCount; i++)
    {
        int l = toyGraph[i-1][0];
        
         mfGraph[0][i] = toyGraph[i-1][(l+1)];
    }
    
    
    //Connect categories to toys
   if(categoryCount != 0){


    for(int i = 1; i <= categoryCount; i++)
    {
        int l = toyGraph[i-1][0];
        for(int j = 0; j <= l-1; j++)
        {
         mfGraph[i][categoryCount + toyGraph[i-1][(j+1)]] = 1;
        }
    }
}

    //Connects source to uncategorized toys
    for(int i = 1; i <= toyCount; i++)
    {
        int counter = 0;
        for(int j = 1; j <= categoryCount; j++)
        {
            int l = toyGraph[j-1][0];
            for(int k = 1; k <= l; k++)
             {
             if(toyGraph[j-1][k] == i)
                {
                    counter++;
            }
         }
    }
    if(counter == 0)
        {
            mfGraph[0][categoryCount + i] = 1;
        }
}


    //Connects toys to children

    for(int i = 1; i <= childCount;i++)
    {
        int k = childGraph[i-1][0];
        for(int j = 0; j <= k-1; j++)
        {
          mfGraph[categoryCount + (childGraph[i-1][j + 1])][(i + categoryCount + toyCount )] = 1;
        }
    }

    //connect children to target
    for(int i = 1; i <= childCount; i++)
    {
        mfGraph[i + toyCount + categoryCount][(categoryCount + toyCount + childCount + 1)] = 1;
    }



System.out.print(fordFulkerson(mfGraph, 0,categoryCount + toyCount + childCount + 1,categoryCount + toyCount + childCount + 2));
}

} 