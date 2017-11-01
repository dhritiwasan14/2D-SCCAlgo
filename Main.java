import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        HashSet<Literal> myLiterals = new HashSet<>();
        ArrayList<ArrayList<Literal>> output = new ArrayList<>();
        ArrayList<Clause> myClauses = new ArrayList<>();
        SATSolver solver = new SATSolver();
        int[][] arr = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 1}, {1, -1}, {-1, 5}};
        solver.addLiterals(arr);
        System.out.println(solver.findStrongConected());
        System.out.println(solver.checkForUnsatisfiability(solver.findStrongConected()));

        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/largeUnsat.cnf"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && !(line.substring(0,1).equals("p")) && !(line.substring(0,1).equals("c"))) {
                    ArrayList<Literal> arraylist = new ArrayList<>();
                    for (String linesplit : line.split("\\s+")) {
                        if (!linesplit.equals("0")) {
                            Literal newLiteral;
                            if (arraylist.isEmpty())
                            newLiteral = new Literal(-Integer.valueOf(linesplit));
                            else newLiteral = new Literal(Integer.valueOf(linesplit));
                            arraylist.add(newLiteral);
                            myLiterals.add(newLiteral);
                        } else{
                            myClauses.add(new Clause(arraylist.get(0), arraylist.get(0)));
                            output.add(arraylist);
                            try {
                                solver.addLiterals(arraylist.get(0), arraylist.get(1));
                            } catch (Exception e) {
                                solver.addLiterals(arraylist.get(0), new Literal(arraylist.get(0).var));
                            }
                        }
                    }
                }
            }
            long starttime = System.currentTimeMillis();
            ArrayList<List<Literal>> scc = solver.findStrongConected();
            System.out.println(scc);
            if (solver.checkForUnsatisfiability(scc)) {
                System.out.println("FORMULA UNSATISFIABLE");
            } else {
                int length = scc.size();
                System.out.println(scc);
                String binStr = "";
                for (int i = 0; i < length; i++) {
                    binStr = Integer.toBinaryString(i);
                    for (int j = 0; j < binStr.length(); j++) {
                        boolean value = 49 - (int) binStr.charAt(j) == 0;
                        for (int k = 0; k < scc.get(i).size(); k++) {
                            Literal temp = scc.get(i).get(k);
                            temp.value = value;
                            System.out.println(myLiterals.contains(temp));
                        }
                    }
                }
                System.out.println(length);
                for (int i = 0; i < myClauses.size(); i++) {
                    System.out.println("hi there");
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
