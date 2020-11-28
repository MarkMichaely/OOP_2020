import ex1.WGraph_DS;
import ex1.WGrpah_Algo;
import ex1.weighted_graph;
import ex1.weighted_graph_algorithms;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this class represents a test class for WGraph_Algo
 */
public class WAlgo_Test {
    private static Random _rand;
    private static long _seed;

    public static void initSeed(long seed) {
        _seed = seed;
        _rand = new Random(_seed);
    }

    public static void initSeed() {
        initSeed(_seed);
    }

    /**
     * function to create random graph with given num of vertex and edge
     *
     * @author boaz.benmoshe
     */
    private static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        initSeed(seed);
        int[] nodes = new int[v_size];
        for (int i = 0; i < v_size; i++) {
            nodes[i] = i;
            g.addNode(i);
        }

        while (g.edgeSize() < e_size) {
            int a = nextRnd(0, v_size);
            int b = nextRnd(0, v_size);
            double w = a + b;
            int i = nodes[a];
            int j = nodes[b];
            g.connect(i, j, w);
        }
        return g;
    }

    private  weighted_graph small_graph(){
        weighted_graph g=new WGraph_DS();
        for (int i = 0; i <12 ; i++) {
            g.addNode(i);
        }
        g.connect(0,1,10);
        g.connect(0,4,5);
        g.connect(0,9,3);
        g.connect(1,2,2);
        g.connect(1,5,5);
        g.connect(4,9,7);
        g.connect(5,8,1);
        g.connect(2,6,4);
        g.connect(2,3,3);
        g.connect(2,7,12);
        g.connect(2,9,2);
        g.connect(3,7,1);
        g.connect(6,10,1.5);
        g.connect(6,11,3);
        g.connect(9,10,2);
        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = _rand.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }

    @Test
    public void testBuild() {
        WGrpah_Algo algo = new WGrpah_Algo();
        assertNotNull(algo);
    }

    @Test
    public void testInit(){
        weighted_graph_algorithms algo =new WGrpah_Algo();
        assertNull(algo.getGraph());
        weighted_graph graph=graph_creator(1,0,1);
        algo.init(graph);
        assertNotNull(algo.getGraph());
    }
    @Test
    public void testCopy(){
        weighted_graph_algorithms algo =new WGrpah_Algo();
        weighted_graph graph= graph_creator(10,10,1);
        algo.init(graph);
        weighted_graph graph2=graph_creator(10,10,2);
        assertNotEquals(graph,graph2);
        graph2=algo.copy();
        assertEquals(graph,graph2);
    }
    @Test
    public void testIsConnected(){
        weighted_graph_algorithms algo =new WGrpah_Algo();
        assertTrue(algo.isConnected());

        weighted_graph graph=graph_creator(0,0,1);
        algo.init(graph);
        assertTrue(algo.isConnected());

        graph=graph_creator(1,0,1);
        algo.init(graph);
        assertTrue(algo.isConnected());

        graph=graph_creator(2,0,1);
        algo.init(graph);
        assertFalse(algo.isConnected());

        graph=small_graph();
        algo.init(graph);
        assertTrue(algo.isConnected());

        graph.removeEdge(8,5);
        algo.init(graph);
        assertFalse(algo.isConnected());

        graph.removeNode(8);
        algo.init(graph);
        assertTrue(algo.isConnected());

        graph.removeNode(6);
        algo.init(graph);
        assertFalse(algo.isConnected());

        graph.removeNode(11);
        algo.init(graph);
        assertTrue(algo.isConnected());

    }
    @Test
    public void shortestPathTest(){
        weighted_graph_algorithms algo =new WGrpah_Algo();
        weighted_graph graph=small_graph();
        algo.init(graph);
        assertEquals(algo.shortestPathDist(0,5),12.0);

        ArrayList list =new ArrayList();
        list.add(graph.getNode(0));
        list.add(graph.getNode(9));
        list.add(graph.getNode(2));
        list.add(graph.getNode(1));
        list.add(graph.getNode(5));
        assertEquals(algo.shortestPath(0,5),list);

        list.remove(graph.getNode(9));
        list.remove(graph.getNode(2));
        graph.removeNode(9);

        assertEquals(algo.shortestPathDist(0,5),15.0);
        assertEquals(algo.shortestPath(0,5),list);

        graph.removeNode(6);
        assertEquals(algo.shortestPathDist(0,11),-1);
        assertNull(algo.shortestPath(0,11));
    }
    @Test
    public void saveLoadTest(){
        weighted_graph_algorithms algo =new WGrpah_Algo();
        weighted_graph graph= graph_creator(10,15,1);
        algo.init(graph);
        String s="log/Graph# "+Math.random()+".txt";
        algo.save(s);
        weighted_graph_algorithms algo1 =new WGrpah_Algo();
        algo1.load(s);
        assertEquals(algo1.getGraph(),algo.getGraph());
        algo1.getGraph().removeNode(0);
        assertNotEquals(algo1.getGraph(),algo.getGraph());
        graph.removeNode(0);
        assertEquals(algo1.getGraph(),algo.getGraph());
    }
}
