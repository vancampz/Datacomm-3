My program reads in the data from network.dat by use of a Scanner from java util.

I have several classes, a link, to represent an edge, a newdouble which is just a non-primitive wrapper for a double, and a node class.

The entry-point for the algorithm is the dijsktra method. it takes in a source node, then loops through the nodes and assigns an initial cost, then goes through and finds the minimum distance to each node.
To do this, it uses the findMinimumDistances algorithm which find those distances given a source node
This method adds the distances, adds the predessecors to the list to get the path that the algorthm would take.

There is also a getNeighbors class that returns a list of all of the neighbors tot eh given node.
There is a getminimum method to find the shortest path to another node from the current node.
To determine if a node has been checked, the algorithm consults whether the node has been settled or not.
lastly there are functions to return the shortest path length, and to return the path itself.