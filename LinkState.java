

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Node
{
    String name;

    public Node(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public boolean equals(Node n)
    {
        return n.name.equals(this.name);
    }
}

class Link
{
    Node n1, n2;
    double cost;

    public Link(Node n1, Node n2, double cost)
    {
        this.n1 = n1;
        this.n2 = n2;
        this.cost = cost;
    }

    public double getCost()
    {
        return cost;
    }

    public Node getN1()
    {
        return n1;
    }

    public Node getN2()
    {
        return n2;
    }

    public boolean equals(Link l)
    {
        return l.cost == this.cost && l.n1.equals(this.n1) && l.n2.equals(this.n2);
    }
}

class newDouble
{
    double value;

    public newDouble(double d)
    {
        value = d;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }
}

class LinkState
{
    private final List<Node> nodes;
    private final List<Link> links;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, newDouble> distance;

    public static void main(String[] args) throws FileNotFoundException
    {
        LinkState ls;
        File file = new File(args[0]);
        Scanner in = new Scanner(file);
        int count;

        List<Link> links1 = new ArrayList<>();
        List<Node> nodes1 = new ArrayList<>();

        count = in.nextInt();

        for (int i = 0; i < count; i++)
            nodes1.add(new Node("" + i));

        int node1, node2;
        double cost1;

        while (in.hasNext())
        {
            node1 = in.nextInt();
            node2 = in.nextInt();
            cost1 = in.nextDouble();

            //add links to list
            links1.add(new Link(nodes1.get(node1), nodes1.get(node2), cost1));
        }

        ls = new LinkState(nodes1, links1);
        ls.dijkstra(nodes1.get(0));

        for (Node node : ls.getNodes())
            System.out.println("shortest path to node " + node.getName() + " is " + ls.getPath(node) + " with cost " + ls.getCost(nodes1.get(0), nodes1.get(1)));
    }

    public List<Node> getNodes()
    {
        return nodes;
    }

    public LinkState(List<Node> nodes, List<Link> links)
    {
        // create a copy of the array so that we can operate on this array
        this.nodes = nodes;
        this.links = links;
    }

    public void dijkstra(Node source)
    {
        settledNodes = new HashSet<Node>();
        unSettledNodes = new HashSet<Node>();
        distance = new HashMap<Node, newDouble>();
        predecessors = new HashMap<Node, Node>();

        for (Node node : nodes)
        {
            distance.put(node, new newDouble(Double.MAX_VALUE));
        }

        distance.put(source, new newDouble(0));
        unSettledNodes.add(source);

        while (unSettledNodes.size() > 0)
        {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node)
    {
        List<Node> adjacentNodes = getNeighbors(node);

        for (Node target : adjacentNodes)
        {
            if (getShortestDistance(target) > getShortestDistance(node) + getCost(node, target))
            {
                distance.put(target, new newDouble(getShortestDistance(node) + getCost(node, target)));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private double getCost(Node node, Node target)
    {
        for (Link link : links)
        {
            if (link.getN1().equals(node)
                && link.getN2().equals(target))
            {
                return link.getCost();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Node> getNeighbors(Node node)
    {
        List<Node> neighbors = new ArrayList<Node>();
        for (Link link : links)
        {
            if (link.getN1().equals(node) && !isSettled(link.getN2()))
            {
                neighbors.add(link.getN2());
            }
        }
        return neighbors;
    }

    private Node getMinimum(Set<Node> Nodes)
    {
        Node minimum = null;

        for (Node Node : Nodes)
        {
            if (minimum == null)
            {
                minimum = Node;
            }  else {
                if (getShortestDistance(Node) < getShortestDistance(minimum))
                {
                    minimum = Node;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node Node)
    {
        return settledNodes.contains(Node);
    }

    private double getShortestDistance(Node destination)
    {
        double d = distance.get(destination).getValue();
        if (d == 0)
        {
            return Integer.MAX_VALUE;
        }
        else
        {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public String getPath(Node target)
    {
        String path = "0";
        Node step = target;

        // check if a path exists
//        if (predecessors.get(step) == null)
//        {
//            return null;
//        }

        //path += "->" + step;

        while (predecessors.get(step) != null)
        {
            step = predecessors.get(step);
            path += "->" + step.getName();
        }

        return path;
    }
}
