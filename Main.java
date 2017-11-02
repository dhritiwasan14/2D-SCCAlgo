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
        int[][] arr = {{4, 4}, {-4, -4}};
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/largeUnsat.cnf"))) {
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
                                solver.addLiterals(arraylist.get(1), arraylist.get(3));
                                myClauses.add(new Clause(arraylist.get(1), arraylist.get(2)));
                            } catch (Exception e) {
                                solver.addLiterals(arraylist.get(0), arraylist.get(1));
                                solver.addLiterals(arraylist.get(1), arraylist.get(0));
                                myClauses.add(new Clause(arraylist.get(1), arraylist.get(1)));
                            }
                        }
                    }
                }
            }
            ArrayList<List<Literal>> scc = solver.findStrongConected();
            if (solver.checkForUnsatisfiability(scc)) {
                System.out.println("FORMULA UNSATISFIABLE");
            } else {
                System.out.println("FORMULA SATISFIABLE");
                int length = scc.size();
                String binStr;
                String toPrint = "";
                binStr = Integer.toBinaryString(length*2);
                while (binStr.length() == Integer.toBinaryString((2*length)+1).length()) {
                    length++;
                }
                for (int i =length*2-1; i >= 0;i--) {
                    binStr = Integer.toBinaryString(i);
                    for (int j = 0; j < binStr.length(); j++) {
                        boolean value = 49 - (int) binStr.charAt(j) == 0;
                        for (int k = 0; k < scc.get(j).size(); k++) {

                            Literal temp = scc.get(j).get(k);
                            temp.value = value;
                            myLiterals.put(temp, value);
                        }
                    }

                    int numberClause = myClauses.size();
                    int count = 0;
                    for (Clause clause : myClauses) {
                        clause.setValue1(myLiterals.get(clause.node1));
                        clause.setValue2(myLiterals.get(clause.node2));
                        if (clause.solve()) count++;
                    }
                    if (count == numberClause) {
                        List<Literal> keys = myLiterals.keySet().stream().filter(o -> o.var > 0).sorted(Comparator.comparing(Literal::getVar)).collect(Collectors.toList());
                        for (Literal key : keys) {
                            toPrint += (key.value) ? 1 : 0;
                        }
                        System.out.println(toPrint);
                        break;
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
