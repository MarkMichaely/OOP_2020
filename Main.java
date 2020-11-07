package ex0;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();
        node_data n3 = new NodeData();
        node_data n4 = new NodeData();
        graph g = new Graph_DS();
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.connect(n1.getKey(),n2.getKey());
        g.connect(n1.getKey(),n3.getKey());
        g.connect(n2.getKey(),n4.getKey());
        graph g0=new Graph_DS();
     // g.removeNode(n1.getKey());
     // g.removeNode(n2.getKey());
     // g.removeNode(n3.getKey());
     // Iterator<node_data> it = g.getV().iterator();
     // while (it.hasNext()) {
     //     System.out.println(it.next().getKey());
     //  }
    graph_algorithms ga=new Graph_Algo();
        ga.init(g);
        System.out.println("nodes"+g.nodeSize());
        System.out.println("edge"+g.edgeSize());
        System.out.println(ga.isConnected());
        System.out.println(ga.shortestPath(n1.getKey(),n4.getKey()));
        ga.init(g0);
        System.out.println(g0.nodeSize());
        System.out.println(g0.nodeSize());
        System.out.println(ga.isConnected());



    }
}
