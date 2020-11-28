package ex1;

import ex0.*;
import org.junit.jupiter.api.MethodOrderer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class main {

        private static Random _rand;
        private static long _seed;
        public static void initSeed(long seed) {
                _seed = seed;
                _rand = new Random(_seed);
        }
        public static void initSeed() {
                initSeed(_seed);
        }

        public static void main(String[] args) {
            weighted_graph_algorithms algo=new WGrpah_Algo();
           weighted_graph_algorithms algo1=new WGrpah_Algo();
             weighted_graph g=graph_creator(10,12,1);
               System.out.println("hello");
           //    System.out.println(g.toString());
               algo.init(g);
//
               algo.save("test");
               algo1.load("test");
           LinkedList<node_info> list=(LinkedList<node_info>) algo1.shortestPath(0,4);
           System.out.println("list");
           System.out.println(list);
           System.out.println(algo1.shortestPathDist(0,4));

        //    algo.init(small_graph());
        //    System.out.println(algo.isConnected());
        //    System.out.println(algo.shortestPathDist(0,5));
        //    System.out.println(algo.shortestPath(0,5));
        //    algo.getGraph().removeNode(9);
        //    System.out.println(algo.shortestPathDist(0,5));
        //    System.out.println(algo.shortestPath(0,5));


        }

    private static weighted_graph small_graph(){
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
        private static weighted_graph graph_creator(int v_size, int e_size, int seed) {
                weighted_graph g = new WGraph_DS();
                initSeed(seed);
                int[] nodes =new int[v_size];
                for(int i=0;i<v_size;i++) {
                        nodes[i]=i;
                        g.addNode(i);
                }
                // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important

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
}

