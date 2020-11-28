package ex1;

import ex0.node_data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * this class represents an undirectional weighted graph.
 */
public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, node_info> _hM; //hashMap
    private int _vertex;
    private int _edges;
    private int _modeCount;

    public WGraph_DS() {
        this._hM = new HashMap<Integer, node_info>();
        this._vertex = 0;
        this._edges = 0;
        this._modeCount = 0;
    }


    @Override
    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    public node_info getNode(int key) {
        return _hM.get(key);
    }

    @Override
    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    public boolean hasEdge(int node1, int node2) {
        return ((Node_Info) this.getNode(node1)).hasNi(node2);
    }

    @Override
    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    public double getEdge(int node1, int node2) {
        if (((Node_Info) this.getNode(node1)).hasNi(node2)) return ((Node_Info) this.getNode(node1)).getEdge(node2);
        return -1;
    }

    @Override
    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    public void addNode(int key) {
        if (this.getNode(key) == null) {
            this._hM.put(key, new Node_Info(key));
            this._vertex++;
            this._modeCount++;
        }
    }

    @Override
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    public void connect(int node1, int node2, double w) {
        if ((w >= 0) && this._hM.containsKey(node1) && this._hM.containsKey(node2) && (node1 != node2) ) {
            if (!hasEdge(node1,node2)){
                this._edges++;
                this._modeCount++;
            }else{
                this._modeCount++;
            }
            ((Node_Info)this.getNode(node1)).addNi(this._hM.get(node2),w);
            ((Node_Info)this.getNode(node2)).addNi(this._hM.get(node1),w);
        }

    }

    @Override
    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     * @return Collection<node_data>
     */
    public Collection<node_info> getV() {
        return this._hM.values();
    }

    @Override
    /**
     *
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     * @return Collection<node_info>
     */
    public Collection<node_info> getV(int node_id) {
        return ((Node_Info) this.getNode(node_id)).getNi().values();
    }

 //  /**
 //   * this method returns a HashMap of all the edges connected to node_id
 //   *
 //   * @param node_id
 //   * @return
 //   */
 //  public HashMap<Integer, Double> getE(int node_id) {
 //      return ((Node_Info) this.getNode(node_id)).getEdges();
 //  }

    @Override
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    public node_info removeNode(int key) {
        node_info node = this.getNode(key);
        if (node != null) {
            Iterator<node_info> it = this.getV(key)
                    .iterator();
            HashSet<node_info> set = new HashSet<node_info>();
            while (it.hasNext()) {
                node_info n = it.next();
                set.add(n);
                //   it.remove();
            }
            Iterator<node_info> itr = set.iterator();
            while (itr.hasNext()) {
                node_info n = itr.next();
                removeEdge(n.getKey(), key);
            }
            this._vertex--;
            this._modeCount++;
            this.getV().remove(node);
        }
        return node;
    }

    @Override
    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     */
    public void removeEdge(int node1, int node2) {
        if (this.hasEdge(node1, node2)) {
            ((Node_Info) this.getNode(node2)).removeNi(node1);
            ((Node_Info) this.getNode(node1)).removeNi(node2);
            this._edges--;
            this._modeCount++;
        }
    }

    @Override
    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    public int nodeSize() {
        return this._vertex;
    }

    @Override
    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    public int edgeSize() {
        return this._edges;
    }

    @Override
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    public int getMC() {
        return this._modeCount;
    }

    public String toString() {
        String s = "@@";
        s += "WGrpah:\n";
        s += "Num of Vertex:" + this.nodeSize() + "\n";
        s += "Num of Edges:" + this.edgeSize() + "\n";
        Iterator it = this._hM.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            s+="\t"+((Node_Info) pair.getValue()).toString();
            s+="\t"+((Node_Info) pair.getValue()).toStringNi();
        }
        return s;
    }

    /**
     * this method used for algorithm
     * it changes all the tags to infinite
     * tags are used to represent distance from src node in algorithm
     */
    public void setTagToInf(){
        Iterator it= this.getV().iterator();
        while (it.hasNext()){
            ((Node_Info)it.next()).setTag(Double.MAX_VALUE);
        }
    }

    /**
     * this method creates Json from graph
     * @return
     */
    public String GraphtoJSON(){
        JSONObject node=new JSONObject();
        JSONObject edges=new JSONObject();
        JSONArray arr=new JSONArray();
        Iterator<node_info> nodeIt =this.getV().iterator();
        while (nodeIt.hasNext()){
            node_info n= nodeIt.next();
            node.put("key",n.getKey());
            Iterator<node_info> edgeIt=this.getV(n.getKey()).iterator();
            while (edgeIt.hasNext()){
                node_info edge=edgeIt.next();
                edges.put(""+edge.getKey(),this.getEdge(n.getKey(),edge.getKey()) );
            }
            node.put("edge",edges);
            arr.add(node);
            node=new JSONObject();
            edges=new JSONObject();
        }
        String string=arr.toJSONString();
        return string;
    }

    public weighted_graph JSONtoGraph (FileReader reader){
        weighted_graph g=new WGraph_DS();
        JSONParser parser=new JSONParser();
        try {
            Object obj=parser.parse(reader);
            JSONArray array=(JSONArray) obj;
            Iterator nodeIt= array.iterator();
            JSONObject node=new JSONObject();
            while (nodeIt.hasNext()){
                node=(JSONObject) nodeIt.next();
                g.addNode(Integer.valueOf  (((Long) node.get("key")).intValue()));
            //    Integer i=Integer.valueOf  (((Long) node.get("key")).intValue());
            }
            nodeIt=array.iterator();
            while (nodeIt.hasNext()){
                node=(JSONObject) nodeIt.next();
                int key=Integer.valueOf  (((Long) node.get("key")).intValue());
                JSONObject edge=(JSONObject)node.get("edge");
                Iterator<String> edgeIt =edge.keySet().iterator();
                while(edgeIt.hasNext()){
                    String s=edgeIt.next();
                    int edgeKey=Integer.parseInt(s);
                    double edgeW=(Double) edge.get(s);
                    g.connect(key,edgeKey,edgeW);
                }

            }

        }
        catch (ParseException e){
            System.out.println("position "+e.getPosition());
            System.out.println(e);;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return g;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return _vertex == wGraph_ds._vertex &&
                _edges == wGraph_ds._edges &&
                Objects.equals(_hM, wGraph_ds._hM)&&
                (new ArrayList<>(_hM.values()).equals(new ArrayList<>(wGraph_ds._hM.values())));

    }

    @Override
    public int hashCode() {
        return Objects.hash(_hM, _vertex, _edges);
    }

    /**
     *this class represents a node in weighted undirectional graph
     */

    private static class Node_Info implements node_info {
        private static int _num_of_nodes = 0;
        private HashMap<Integer, Double> _edges;
        private int _key;
        private HashMap<Integer, node_info> _neighbors;
        private String _info;
        private double _tag;

        public Node_Info() {
            this._key = _num_of_nodes;
            this._neighbors = new HashMap<Integer, node_info>();
            this._edges = new HashMap<Integer, Double>();
            this._info = null;
            this._tag = Double.MAX_VALUE;
            _num_of_nodes++;

        }

        public Node_Info(int key) {
            this._key = key;
            this._neighbors = new HashMap<Integer, node_info>();
            this._edges = new HashMap<Integer, Double>();
            this._info = null;
            this._tag = Double.MAX_VALUE;
            _num_of_nodes += key;
        }

        /**
         * function returns Hsahmap with all this node's neighbor nodes
         */

        public HashMap<Integer, node_info> getNi() {
            return this._neighbors;
        }

        /**
         * function retruns hashmap of this node's edge's weight and destenation.
         *
         * @return Hashmap of keys representing nodes and values representing weight
         */
        public HashMap<Integer, Double> getEdges() {
            return this._edges;
        }

        /**
         * this method returns weight of edge between this node and another node
         * @param key represent the other node
         * @return value of weight. if no edge return -1
         */
        public double getEdge(int key) {
            if (this.hasNi(key)) {
                return this.getEdges().get(key);
            } else return -1;
        }

        /**
         * function return true if a node is adjacent to this node
         */
        public boolean hasNi(int key) {
            return (_neighbors.containsKey(key) && _edges.containsKey(key));

        }

        /**
         * This method adds the node_info (t) to this node.
         * creates an edge between the two nodes.
         * if edge already exists changes the value
         * if w<0 does nothing
         */
        public void addNi(node_info t, double w) {
            if ((t != null && this.getKey() != t.getKey()) && w >= 0) {
                if (!this.hasNi(t.getKey())) {
                    this.getNi().put(t.getKey(), t);
                    this.getEdges().put(t.getKey(), w);
                    ((Node_Info) t).getEdges().put(this.getKey(), w);
                    ((Node_Info) t).getNi().put(this.getKey(), this);
                } else {
                    this.getEdges().replace(t.getKey(), w);
                    ((Node_Info) t).getEdges().replace(this.getKey(), w);
                }
            }
        }

        /**
         * method to remove edge from give node key
         *
         * @param key of node to be removed
         */
        public void removeNi(int key) {
            if (this.hasNi(key)) {
                this._neighbors.remove(key);
                this._edges.remove(key);
            }
        }

        @Override
        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         * @return
         */
        public int getKey() {
            return this._key;
        }

        @Override
        /**
         * return the remark (meta data) associated with this node.
         * @return
         */
        public String getInfo() {
            return this._info;
        }

        @Override
        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s
         */
        public void setInfo(String s) {
            this._info = s;
        }

        @Override
        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         * @return
         */
        public double getTag() {
            return this._tag;
        }

        @Override
        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * @param t - the new value of the tag
         */
        public void setTag(double t) {
            this._tag = t;
        }

        @Override
        public String toString() {
            String s = "**";
            s += "Node# " + this.getKey() + "\n";
            //s+="info= "+this.getInfo()+ "\n";
            return s;
        }

        /**
         * method to print edges of node
         * @return
         */
        public String toStringNi() {
            String s = "\tAdjacent Nodes:\n";
            Iterator it=this.getEdges().entrySet().iterator();
            while (it.hasNext()){
                HashMap.Entry pair=(HashMap.Entry)it.next();
                s += "\t\tNode#: " + pair.getKey() + " weight ->"+pair.getValue()+"\n";
                //s+="info= "+this.getInfo()+ "\n";
            }
            return s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node_Info node_info = (Node_Info) o;
            return _key == node_info._key &&
                    Objects.equals(_edges, node_info._edges)&&
                    (new ArrayList<>(_edges.values()).equals(new ArrayList<>(node_info._edges.values())));
        }

        @Override
        public int hashCode() {
            return Objects.hash(_key);
        }
    }

}

