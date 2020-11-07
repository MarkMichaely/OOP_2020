package ex0;


import java.util.*;

/**
 * this class represents an undirectional unweighted graph.
 * It should support a large number of nodes (over 10^6, with average degree of 10).
 */
public class Graph_DS implements graph {
    private HashMap<Integer, node_data> _hM; //hashMap
    private int _vertex;
    private int _edges;
    private int _modeCount;

    public Graph_DS() {
        this._hM = new HashMap<Integer, node_data>();
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
    public node_data getNode(int key) {
        return _hM.get(key);

    }

    @Override
    /**
     * * return true iff (if and only if) there is an edge between node1 and node2
     * 	 * Note: this method should run in O(1) time.
     */
    public boolean hasEdge(int node1, int node2) {
        return (this.getNode(node1)
                .hasNi(node2));

    }

    @Override
    /**
     * 	 * add a new node to the graph with the given node_data.
     * 	 * Note: this method should run in O(1) time.
     */
    public void addNode(node_data n) {
        if (!this._hM.containsValue(n)) {
            this._hM.put(n.getKey(), n);
            this._vertex++;
            this._modeCount++;
        }
    }


    @Override
    /**
     * Connect an edge between node1 and node2.
     *   Note: this method should run in O(1) time.
     *   Note2: if the edge node1-node2 already exists - the method simply does nothing.
     */
    public void connect(int node1, int node2) {
        if (this._hM.containsKey(node1) && this._hM.containsKey(node2) && !this.hasEdge(node1, node2)) {
            this.getNode(node1).addNi(this.getNode(node2));
            this._edges++;
            this._modeCount++;
        }
    }

    @Override
    /**
     * This method return a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     */
    public Collection<node_data> getV() {
        return this._hM.values();
    }

    @Override
    /**
     * This method returns a collection containing all the
     * nodes connected to node_id
     * Note: this method should run in O(1) time.
     */
    public Collection<node_data> getV(int node_id) {
        return this.getNode(node_id).
                getNi();
    }

    @Override
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     */
    public node_data removeNode(int key) {
        node_data node = this.getNode(key);
        if (node != null) {
            Iterator<node_data> it = this.getV(key)
                    .iterator();
            HashSet<node_data> set = new HashSet<node_data>();
            while (it.hasNext()) {
                node_data n = it.next();
                set.add(n);
                it.remove();
            }
            Iterator<node_data> itr = set.iterator();
            while (itr.hasNext()) {
                node_data n = itr.next();
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
     */
    public void removeEdge(int node1, int node2) {
        this.getNode(node1).removeNode(this.getNode(node2));
        this._edges--;
        this._modeCount++;

    }

    @Override
    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return number of vertex
     */
    public int nodeSize() {
        return _vertex;
    }

    @Override
    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return number of edges
     */
    public int edgeSize() {
        return _edges;
    }

    @Override
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return number of changes
     */
    public int getMC() {
        return _modeCount;
    }
}
