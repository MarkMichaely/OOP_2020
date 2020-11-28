import ex1.WGraph_DS;
import ex1.weighted_graph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this class reprsents a test class for WGrpah_DS
 */
public class WGrpah_Test {
    private static Random _rand;
    private static long _seed;
    public static void initSeed(long seed) {
        _seed = seed;
        _rand = new Random(_seed);
    }
    public static void initSeed() {
        initSeed(_seed);
    }

    /**function to create a random graph with given num of vertex and edge
     * @author boaz.benmoshe
     */
    private static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        initSeed(seed);
        int[] nodes =new int[v_size];
        for(int i=0;i<v_size;i++) {
            nodes[i]=i;
            g.addNode(i);
        }
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            double w=a+b;
            int i = nodes[a];
            int j = nodes[b];
            g.connect(i,j,w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rand.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
  //  @BeforeAll
  //  private static void beforeAll(){
  //      weighted_graph g=new WGraph_DS();
  //  }
    @Test
    public void testCreation(){
        weighted_graph g=new WGraph_DS();
        assertNotNull(g);
    }
    @Test
    public void test2(){
        weighted_graph g=new WGraph_DS();
        g.addNode(1);
        assertNotNull(g.getNode(1));
        assertEquals(1,g.getNode(1).getKey());
    }
    @Test
    public void test3(){
        weighted_graph g=new WGraph_DS();
        g.addNode(1);
        assertNotNull(g.getNode(1));
        assertNull(g.getNode(0));
        assertNotEquals(2,g.getNode(1).getKey());
    }
    @Test
    public void test4(){
        weighted_graph graph=graph_creator(2,1,1);
        assertTrue(graph.hasEdge(0,1));
        assertNotNull(graph.getEdge(0,1));
        assertTrue(graph.getEdge(0,1)>0);
    }
    @Test
    public void test5(){
        weighted_graph g=new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.connect(1,2,10.5);
        assertEquals(g.getEdge(1,2),10.5);
    }
    @Test
    public void test6(){
        weighted_graph g=new WGraph_DS();
        g.addNode(0);
        for (int i=1;i<10;i++){
            g.addNode(i);
            g.connect(0,i,i*i);
        }
        assertNotNull(g.getV());
        assertNotNull(g.getV(0));
        assertNotNull(g.getV(1));
        assertEquals(g.getV().size(),10);
        assertEquals(g.nodeSize(),g.getV().size());
        assertEquals(g.edgeSize(),g.getV(0).size());
        assertEquals(g.getV(0).size(),9);
        assertEquals(g.getV(1).size(),1);
        assertTrue(g.getV().contains(g.getNode(0)));
        assertTrue(g.getV().contains(g.getNode(1)));
        assertTrue(g.getV(0).contains(g.getNode(1)));

    }

}
