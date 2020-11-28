package ex1;

import ex0.Graph_DS;
import ex0.node_data;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * this class represents a set of Undirected (positive) Weighted Graph Theory algorithms.
 */
public class WGrpah_Algo implements weighted_graph_algorithms {
    private WGraph_DS _grph;

    public WGrpah_Algo() {
        this._grph = null;
    }

    @Override
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    public void init(weighted_graph g) {
        this._grph = (WGraph_DS) g;
    }

    @Override
    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    public weighted_graph getGraph() {
        return this._grph;
    }

    @Override
    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    public weighted_graph copy() {
        if (this._grph==null) return null;
        weighted_graph g = new WGraph_DS();
        Iterator<node_info> it = this._grph.getV().iterator();
        while (it.hasNext()) {
            node_info n=it.next();
            g.addNode(n.getKey());
        }
        it = g.getV().iterator();
        while (it.hasNext()) {
            node_info n=it.next();
            int node1 = n.getKey();
            Iterator<node_info> nodeit = this._grph.getV(n.getKey()).iterator();
            while (nodeit.hasNext()) {
                node_info edge=nodeit.next();
                int node2 = edge.getKey();
                g.connect(node1, node2, this._grph.getEdge(node1, node2));
            }
        }
        return g;
    }

    @Override
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
    public boolean isConnected() {
        if (this.getGraph()==null) return true;
        else if (this.getGraph().getV().isEmpty()) return true;
        else {
      //      int count = 1;
            node_info nodes[] = this.getGraph().getV().toArray(new node_info[this.getGraph().getV().size()]);
            shortestPath(nodes[0].getKey(), nodes[0].getKey());
            for (node_info node : nodes) {
                if (node.getTag() == Double.MAX_VALUE) return false;
            }
        }
        return true;
    }

    @Override
    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * this method uses the Dijkstra algorithm while changing tag of nodes to its distance from src node
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    public double shortestPathDist(int src, int dest) {
        this.shortestPath(src, dest);
        if (this.getGraph().getNode(dest).getTag() == Double.MAX_VALUE) return -1;
        else return this.getGraph().getNode(dest).getTag();
    }

    @Override
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * this method uses the Dijkstra algorithm while changing tag of nodes to its distance from src node
     * see: https://en.wikipedia.org/wiki/Dijkstra's_algorithm
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    public List<node_info> shortestPath(int src, int dest) {
        PriorityQueue<node_info> queue = new PriorityQueue<node_info>(new nodeComperator());
        this._grph.setTagToInf();
        this.getGraph().getNode(src).setTag(0);
        Map<node_info, node_info> parentMap = new HashMap<node_info, node_info>();
        queue.add(this.getGraph().getNode(src));
        HashSet<node_info> visited = new HashSet<node_info>();
        while (!queue.isEmpty()) {

            int currNode = queue.poll().getKey();
            //   visited.add(this.getGraph().getNode(currNode));
            Iterator<node_info> it = this.getGraph().getV(currNode).iterator();
            if (!visited.contains(this.getGraph().getNode(currNode))) {
                visited.add(this.getGraph().getNode(currNode));
                while (it.hasNext()) {
                    int n = it.next().getKey();
                    Double DIST = this.getGraph().getNode(currNode).getTag() + this.getGraph().getEdge(currNode, n);
                    if (DIST < this.getGraph().getNode(n).getTag()) {
                        this.getGraph().getNode(n).setTag(DIST);
                        parentMap.put(this.getGraph().getNode(n), this.getGraph().getNode(currNode));
                    }
                    queue.add(this.getGraph().getNode(n));
                }
            }
        }
        if (this.getGraph().getNode(dest).getTag() == Double.MAX_VALUE) return null;
        else {
            node_info node = this.getGraph().getNode(dest);
            List<node_info> shortestPath = new LinkedList<node_info>();
            while (node != null) {
                shortestPath.add(node);
                node = parentMap.get(node);
            }
            Collections.reverse(shortestPath);
            return shortestPath;
        }
    }

    @Override

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    public boolean save(String file) {
        try {
            File f = new File(file);
            if (!f.exists()) f.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(this._grph.GraphtoJSON());
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    public boolean load(String file) {
    //    weighted_graph og = this.copy();
        try {
            FileReader fileReader = new FileReader(file);
            weighted_graph g = new WGraph_DS();
            this.init(((WGraph_DS) g).JSONtoGraph(fileReader));
            fileReader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("A New graph wasn't loaded,current graph remained as is");
        }
        return false;
    }

    /**
     * comperator for priority queue of Dijkstra algorithm
     */
    public class nodeComperator implements Comparator<node_info> {

        @Override
        public int compare(node_info node1, node_info node2) {
            if (node1.getTag() > node2.getTag()) return 1;
            else if (node1.getTag() < node2.getTag()) return -1;
            else return 0;
        }
    }


}
