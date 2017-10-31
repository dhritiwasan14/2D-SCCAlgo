
import java.util.*;

public class Graph {

    private HashSet<Edge> edges;
    private LinkedList<Literal> vertices;
    private HashSet<Literal> verticesSet;
    private HashMap<Literal, LinkedList<Literal>> connectedTo;
    public Graph() {
        edges = new HashSet<>();
        vertices = new LinkedList<>();
        verticesSet = new HashSet<>();
        connectedTo = new HashMap<>();
    }
    public void addVertice(Literal vertice) {
        if (verticesSet.contains(vertice));
        else {
            vertices.add(vertice);
            verticesSet.add(vertice);
        }
    }

    public void addEdge(Literal edge1, Literal edge2) {

        addVertice(edge1);
        addVertice(edge2);
        if (connectedTo.containsKey(edge1)) {
            LinkedList<Literal> temp;
            temp = connectedTo.get(edge1);
            temp.add(edge2);
            connectedTo.put(edge1, temp);
        } else {
            LinkedList<Literal> toAdd = new LinkedList<>();
            toAdd.add(edge2);
            connectedTo.put(edge1, toAdd);
        }
        edges.add(new Edge(edge1, edge2));
    }
    public void addEdge(Edge e) {
        addEdge(e.getFrom(), e.getTo());
    }

    public ArrayList<ArrayList<Literal>> getStrongConnected() {
        Stack<Literal> stack = new Stack<>();
        HashSet<Literal> startTime = new HashSet<>();
        stackBuilder(startTime, vertices.get(0), stack);
        LinkedList<Literal> notVisited = new LinkedList<>();
        for (Literal e : vertices) {
            if (!startTime.contains(e)) {
                notVisited.add(e);
            }
        }
        System.out.println("not visited"+notVisited.size());
        for (Literal e: notVisited) {
            if (!startTime.contains(e)) {
                stackBuilder(startTime, e, stack);
            }
        }
        System.out.println(startTime.size());
        Graph transpose = getTranspose();
        Stack<Literal> visited = new Stack<>();
        ArrayList<ArrayList<Literal>> scc = new ArrayList<>();
        while (!stack.empty()) {
            Literal c = stack.pop();
            if (!visited.contains(c))
            scc.add(dfsFinder(transpose, transpose.getEquivalent(c), visited));

        }
        return scc;
    }
    public ArrayList<Literal> dfsFinder(Graph g, Literal node) {
        ArrayList<Literal> toReturn = new ArrayList<>();
        DFSIterator iterator = new DFSIterator(node, this);
        while (iterator.hasNext()) {
            toReturn.add(iterator.next());
        }
        return toReturn;
    }
    public ArrayList<Literal> dfsFinder(Graph g, Literal node, Stack<Literal> visit) {
        ArrayList<Literal> toReturn = new ArrayList<>();
        DFSIterator iterator = new DFSIterator(node, g);
        while (iterator.hasNext()) {
            Literal e = iterator.next();
            System.out.println("node "+node.var+" neighbour "+e.var);

            if (!visit.contains(e)) {
                toReturn.add(e);
                visit.add(e);
            }
        }
        return toReturn;
    }
    public void stackBuilder(HashSet<Literal> visited, Literal node, Stack<Literal> stack) {
        ArrayList<Literal> neighbours = dfsFinder(this, node);
        visited.add(node);
        for (Literal e: neighbours) {
            if (visited.contains(e));
            else
            stackBuilder(visited, e, stack);
        }
        stack.push(node);
    }
    public Literal getEquivalent(Literal node) {
        for (Literal i:this.vertices) {
            if (i.equals(node)) {
                return i;
            }
        } return null;
    }
    public Graph getTranspose() {
        Graph transpose = new Graph();
        for (Edge e: edges) {
            transpose.addEdge(reverseEdge(e));
        }
        return transpose;
    }
    public Edge reverseEdge(Edge e) {
        return new Edge(e.getTo(), e.getFrom());
    }
    private class DFSIterator implements Iterator<Literal> {

        private Stack<Literal> fringe;
        private HashSet<Literal> visited;
        private Graph g;
        public DFSIterator(Literal start, Graph graph) {
            fringe = new Stack<>();
            fringe.push(start);
            visited = new HashSet<>();
            visited.add(start);
            g = graph;
        }
        @Override
        public boolean hasNext() {
            return !fringe.empty();
        }

        @Override
        public Literal next() {
            while(!fringe.empty()) {
                Literal next = fringe.pop();
                List<Literal> neighbours = g.getNeighbours(next);
                if (neighbours != null) {
                    for (Literal i : neighbours) {
                        if (!visited.contains(i)) {
                            fringe.push(i);
                            visited.add(i);
                        }
                    }
                } return next;

            }
            throw new NoSuchElementException();
        }
    }
    public List<Literal> getNeighbours(Literal next) {
        return connectedTo.get(next);
    }



}
