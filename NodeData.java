package ex0;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * this class represents a node (vertex) in an (undirectional) unweighted graph.
 * further reading :
 * https://en.wikipedia.org/wiki/Vertex_(geometry)
 */
public class NodeData implements node_data {
    private static int _num_of_nodes = 0;
    private int _key;
    private Set<node_data> _neighbors;
    private String _info;
    private int _tag;


    public NodeData() {
        this._key = _num_of_nodes;
        this._neighbors = new HashSet<node_data>();
        this._info = null;
        this._tag = 0;
        _num_of_nodes++;

    }

    @Override
    /**
     function returns node's key, each node has a diffrent key
     */
    public int getKey() {
        return this._key;
    }

    /**
     * function returns a collection with all this node's neighbor nodes
     */
    @Override
    public Collection<node_data> getNi() {
        return this._neighbors;
    }

    @Override
    /**
     * function return true if a node is adjacent to this node
     *
     */
    public boolean hasNi(int key) {
        Iterator<node_data> it = _neighbors.iterator();
        while (it.hasNext()) {
            node_data node = it.next();
            if (node.getKey() == key) return true;
        }
        return false;
    }

    @Override
    /**
     * This method adds the node_data (t) to this node.
     * creates an edge between the two nodes.
     */
    public void addNi(node_data t) {
        if ((t.getKey() != this.getKey())
        &&!this._neighbors.contains(t)) {
            this.getNi().add(t);
            t.getNi().add(this);
        }
    }

    @Override
    /**
     * Removes the edge this-node,
     */
    public void removeNode(node_data node) {
        this.getNi().remove(node);
        node.getNi().remove(this);
    }

    @Override
    /**
     * return the remark (meta data) associated with this node.
     */
    public String getInfo() {
        return this._info;
    }

    @Override
    /**
     *  Allows changing the remark (meta data) associated with this node.
     */
    public void setInfo(String s) {
        this._info = s;
    }

    @Override
    /**
     * 	 * Temporal data (aka color: e,g, white, gray, black)
     * 	 * which can be used be algorithms
     * 	 * @return
     *          */

    public int getTag() {
        return this._tag;
    }

    @Override
    /**
     *  * Allow setting the "tag" value for temporal marking an node - common
     * 	 * practice for marking by algorithms.
     * 	 * @param t - the new value of the tag
     */
    public void setTag(int t) {
        this._tag = t;
    }

}

