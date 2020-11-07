package ex0;


import java.util.*;


public class Graph_Algo implements graph_algorithms {
    private graph _grph;

    public Graph_Algo() {
        this._grph = new Graph_DS();
    }

    @Override
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    public void init(graph g) {
       this._grph=g;
        }


    @Override
    /**
     * Compute and return a deep copy of this graph.
     */
    public graph copy() {
        graph g = new Graph_DS();
        Iterator<node_data> it = this._grph.getV().iterator();
        while (it.hasNext()) g.addNode(it.next());
        return g;
    }

    @Override
    /**
     * Returns true if and only if (iff) there is a valid path from every node to each
     * other node. NOTE: assumes ubdirectional graph.
     */
    public boolean isConnected() {
        boolean flag = true;
        if (!this._grph.getV().isEmpty()) {
            int count = 1;
            Iterator<node_data> it = this._grph.getV().iterator();
            node_data node = it.next();
            this.BFS(node.getKey(), node.getKey());
            while (it.hasNext()) {
                node_data n = it.next();
                if (n.getTag() != 0) count++;
            }
            if (count == this._grph.nodeSize()) flag= true;
            else flag= false;
        }
        resetTag();
        return flag;
    }

    @Override
    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     */
    public int shortestPathDist(int src, int dest) {
        this.BFS(src, dest);
        node_data n = (NodeData) this._grph.getNode(dest);
        if (n.getTag() == 0) {
            return -1;
        }
        int ans = n.getTag();
        this.resetTag();
        return ans;

    }

    @Override
    /**
     *  returns the the shortest path between src to dest - as an ordered List of nodes:
     *  src--> n1-->n2-->...dest
     *  function uses BFS(breath first search) and should only be used on unweighted graphs
     *  see: https://en.wikipedia.org/wiki/Shortest_path_problem
     *  Note if no such path --> returns null;
     *  @param src - start node
     *  @param dest - end (target) node
     */
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> shortestPath = this.BFS(src, dest);
        resetTag();
        return shortestPath;

    }

    private void resetTag() {
        Iterator<node_data> it = this._grph.
                getV().iterator();
        while (it.hasNext()) {
            it.next().setTag(0);

        }
    }

    /**
     * function that returns shortest path from src to dest.
     * it also colors nodes away from source by setting the tag of each node by the level of each node from src node
     * if used should always be use resetTag method to reset tags to 0
     * inspired by the following
     * https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
     * using queue https://www.youtube.com/watch?v=oDqjPvD54Ss&t=325s
     *
     */
    private List<node_data> BFS(int src, int dest) {
        Queue<node_data> queue = new ArrayDeque<>();
        LinkedList<node_data> shortestPath = new LinkedList<node_data>();
        Map<node_data, node_data> parentNodes = new HashMap<node_data, node_data>();
        HashSet<node_data> visited = new HashSet<node_data>();
        node_data cN = this._grph.getNode(src); //current node
        queue.add(cN);
        visited.add(cN);
        int level = 1;

        while (!queue.isEmpty()) {
            cN = queue.remove();
            Set<node_data> nI = (HashSet<node_data>) cN.getNi();
            level = cN.getTag() + 1;
            Iterator<node_data> it = nI.iterator();
            while (it.hasNext()) {
                node_data nN = it.next(); //next node
                if (!visited.contains(nN)) {
                    queue.add(nN);
                    visited.add(nN);
                    parentNodes.put(nN, cN);
                    nN.setTag(level);
                }

            }
        }
        node_data node = this._grph.getNode(dest);
        while (node != null) {
            shortestPath.add(node);
            node = parentNodes.get(node);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }
}
