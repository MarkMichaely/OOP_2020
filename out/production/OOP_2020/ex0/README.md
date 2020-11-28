# OOP_2020
An implemantion of undirectional unwighted graph in Java. it is able to calculate and retrieve the shortest path from any node on the graph.
it is able to do so by using breath first search alogrithem

**Links:**
* https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)
* https://www.youtube.com/watch?v=oDqjPvD54Ss&t=325s
* https://www.youtube.com/watch?v=s-CYnVz-uh4&t=2305s
* https://en.wikipedia.org/wiki/Breadth-first_search

**methods**

* `_grph` represents the graph that the algorithem is used on
* `init(graph g)` is the function that initilize the algorithem on given graph
* `copy()` a function to make a deep copy of the graph inside the algorithem
* `isConnected` a boolean function to check wether the function is connected. meaning wether every node in graph is reachable by everty other node in graph.
* `shortestPathDist` and `shortestPath` are both function the invoke BFS algorithem, the first to get the shortest path as an integer and the latter to get the actual path from a source node to it's destination.
