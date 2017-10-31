
import java.util.ArrayList;
import java.util.LinkedList;

public class SATSolver {

    Graph implicationgraph;
    public SATSolver() {
        implicationgraph = new Graph();
    }
    public void addLiterals(int[][] arr) {
        for (int i = 0; i< arr.length; i++) {
            Literal one = new Literal(arr[i][0]);
            Literal two = new Literal(arr[i][1]);
            implicationgraph.addEdge(one, two);
        }
    }
    public ArrayList<ArrayList<Literal>> findStrongConected() {
        return implicationgraph.getStrongConnected();
    }



}
