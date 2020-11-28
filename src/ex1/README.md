# OOP_2020 ex1
An implementation of unidirectional weighted graph in Java. it is able to calculate and retrieve the shortest path from any node on the graph by using Dijkstra's algorithm.


**Links:**
* https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
* https://stackoverflow.com/questions/5804043/convert-long-into-integer
* https://www.tutorialspoint.com/json_simple/json_simple_quick_guide.htm
* https://mkyong.com/java/json-simple-how-to-parse-json/
* https://stackoverflow.com/questions/19195492/extracting-keys-from-a-jsonobject-using-keyset
* https://crunchify.com/how-to-write-json-object-to-file-in-java/
* https://www.coursera.org/lecture/advanced-data-structures/core-dijkstras-algorithm-2ctyF
* https://stackoverflow.com/questions/35017393/how-to-create-file-in-a-project-folder-in-java

**methods**

* `_grph` represents the graph that the algorithm is used on
* `init(graph g)` is the function that initialize the algorithm on given graph
* `copy()` a function that returns a deep copy of the graph inside the algorithm
* `isConnected()` a Boolean function to check whether the function is connected. meaning whether every node in graph is reachable by every other node in graph.
* `shortestPathDist()` and `shortestPath()` are both function the invoke Dijkstra's algorithm, the first to get the weight of the shortest path as a double. The latter to get the actual path traveled from a source node to it's destination.
* `save`  is a method to save the graph to text file by using JSON and implemented with json_simple
* `load`  is a method that reads a text file and parses it to create a new graph. the text file has to be written in JSON as follows : ["edge":{"edge key n":value},"key":key number] for each node in graph.
