import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        long starttime = System.currentTimeMillis();
        ArrayList<ArrayList<Literal>> output = new ArrayList<>();
        LinkedList<Clause> myClauses = new LinkedList<>();
        SATSolver solver = new SATSolver();
        HashMap<Literal, Boolean> myLiterals = new HashMap<>();
        HashMap<Literal, Integer> position = new HashMap<>();
        HashMap<Literal, Integer> pos = new HashMap<>();
        int[][] arr = {{4, 4}, {-4, -4}};
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/s8Sat.cnf"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && !(line.substring(0,1).equals("p")) && !(line.substring(0,1).equals("c"))) {
                    ArrayList<Literal> arraylist = new ArrayList<>();
                    for (String linesplit : line.split("\\s+")) {
                        Literal newLiteral;
                        Literal newLiteral2;
                        if (!linesplit.equals("0")) {
                            if (arraylist.isEmpty()) {
                                newLiteral = new Literal(-Integer.valueOf(linesplit));
                                newLiteral2 = new Literal(Integer.valueOf(linesplit));
                            }
                            else {
                                newLiteral = new Literal(Integer.valueOf(linesplit));
                                newLiteral2 = new Literal(-Integer.valueOf(linesplit));
                            }
                            arraylist.add(newLiteral);
                            arraylist.add(newLiteral2);
                        } else{
                            output.add(arraylist);
                            try {
                                solver.addLiterals(arraylist.get(0), arraylist.get(2));
                                solver.addLiterals(arraylist.get(3), arraylist.get(1));
                                myClauses.add(new Clause(arraylist.get(1), arraylist.get(2)));
                            } catch (Exception e) {
                                solver.addLiterals(arraylist.get(0), arraylist.get(1));
                                solver.addLiterals(arraylist.get(0), arraylist.get(1));
                                myClauses.add(new Clause(arraylist.get(1), arraylist.get(1)));
                            }
                        }
                    }
                }
            }
            ArrayList<List<Literal>> scc = solver.findStrongConected();
            if (solver.checkForUnsatisfiability(scc)) {
                System.out.println("FORMULA UNSATISFIABLE");
                System.out.println(scc);
            } else {
                System.out.println("FORMULA SATISFIABLE");
                HashMap<Literal, Literal> opposite = new HashMap<>();
                String toPrint = "";

                int counter = 0;
                Literal[] literals = new Literal[solver.implicationgraph.vertices.size()];
                for (int i = 0; i<scc.size(); i++) {
                    for (int j = 0; j < scc.get(i).size(); j++) {
                        Literal a = scc.get(i).get(j);
                        if (a.var > 0) {
                            a.value = true;
                            myLiterals.put(a, true);
                        } else {
                            a.value = false;
                            myLiterals.put(a, false);
                        }
                        position.put(a, i);
                        literals[counter] = a;
                        pos.put(a, counter++);
                    }
                }
                List<Literal> keys =  myLiterals.keySet().stream().filter(o -> o.var > 0).sorted(Comparator.comparing(Literal::getVar)).collect(Collectors.toList());
                for (int k = 0; k < keys.size(); k++) {
                    opposite.put(keys.get(k), literals[pos.get(new Literal(-keys.get(k).var))]);
                    opposite.put(new Literal(-keys.get(k).var), literals[pos.get(keys.get(k))]);
                }
                while (true) {
                    int numberClause = myClauses.size();
                    Clause temp = new Clause();
                    int count = 0;
                    for (Clause clause : myClauses) {
                        clause.setValue1(myLiterals.get(clause.node1));
                        clause.setValue2(myLiterals.get(clause.node2));
                        if (clause.solve()) count++;
                        else {
                            temp = clause;
                            break;
                        }
                    }
                    if (count == numberClause) {

                        for (Literal key : keys) {
                            toPrint += ((key.value) ? 1 : 0) ;
                        }
                        System.out.println(toPrint);
                        break;
                    }
                    temp.value1 = !temp.value1;
                    if (!temp.solve()) {
                        // flip all
                        temp.value2 = !temp.value2;
                        temp.value1 = !temp.value1;
                        for (int i = 0; i < scc.get(position.get(temp.node2)).size(); i++) {
                            scc.get(position.get(temp.node2)).get(i).value = temp.value2;
                            myLiterals.replace(scc.get(position.get(temp.node2)).get(i), temp.value2);
                        }
                        for (int i = 0; i < scc.get(position.get(opposite.get(temp.node2))).size(); i++) {
                            scc.get(position.get(opposite.get(temp.node2))).get(i).value = !temp.value2;
                            myLiterals.replace(scc.get(position.get(opposite.get(temp.node2))).get(i), !temp.value2);
                        }
                    } else {
                        for (int i = 0; i < scc.get(position.get(temp.node1)).size(); i++) {
                            scc.get(position.get(temp.node1)).get(i).value = temp.value1;
                            myLiterals.replace(scc.get(position.get(temp.node1)).get(i), temp.value1);
                        }
                        for (int i = 0; i < scc.get(position.get(opposite.get(temp.node1))).size(); i++) {
                            scc.get(position.get(opposite.get(temp.node1))).get(i).value = !temp.value1;
                            myLiterals.replace(scc.get(position.get(opposite.get(temp.node1))).get(i), !temp.value1);
                        }

                    }
                }
            }
            System.out.println(System.currentTimeMillis() - starttime);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
