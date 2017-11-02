
import java.util.ArrayList;
import java.util.List;

public class SATSolver {

    Graph implicationgraph;
    public SATSolver() {
        implicationgraph = new Graph();
    }
    public void addLiterals(Literal one, Literal two) {
        implicationgraph.addEdge(one, two);
    }
    public ArrayList<List<Literal>> findStrongConected() {
        return implicationgraph.getStrongConnected();
    }
    public boolean checkForUnsatisfiability(ArrayList<List<Literal>> scc) {
        for (List list: scc) {
            for (Object i : list) {
                if (((Literal) i).var < 0) {
                    for (int j = list.indexOf(i)+1; j < list.size(); j++) {
                        if (((Literal)list.get(j)).var <= 0) {
                            break;
                        } else {
                            if (((Literal)list.get(j)).var + ((Literal) i).var  == 0) {
                                return true;
                            }
                        }
                    }
                } else {
                    break;
                }
            }
        } return false;
    }


}
