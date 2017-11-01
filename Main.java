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
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/largeUnsat.cnf"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && !(line.substring(0,1).equals("p")) && !(line.substring(0,1).equals("c"))) {
                    ArrayList<Literal> arraylist = new ArrayList<>();
                    for (String linesplit : line.split("\\s+")) {
                        Literal newLiteral = new Literal();
                        Literal newLiteral2 = new Literal();
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
                            myLiterals.add(newLiteral);
                            arraylist.add(newLiteral2);
                            myLiterals.add(newLiteral2);
                        } else{
                            myClauses.add(new Clause(newLiteral, newLiteral2));
                            output.add(arraylist);
                            try {
                                solver.addLiterals(arraylist.get(0), arraylist.get(2));
                                solver.addLiterals(arraylist.get(1), arraylist.get(0));
                            } catch (Exception e) {
                                solver.addLiterals(arraylist.get(0), arraylist.get(1));
                                solver.addLiterals(arraylist.get(1), arraylist.get(0));


                            }
                        }
                    }
                }
            }
            long starttime = System.currentTimeMillis();
            ArrayList<List<Literal>> scc = solver.findStrongConected();
            if (solver.checkForUnsatisfiability(scc)) {
                System.out.println("FORMULA UNSATISFIABLE");
            } else {
                int length = scc.size();
                String binStr = "";
                for (int i = 0; i < length; i++) {
                    binStr = Integer.toBinaryString(i);
                    for (int j = 0; j < binStr.length(); j++) {
                        boolean value = 49 - (int) binStr.charAt(j) == 0;
                        for (int k = 0; k < scc.get(i).size(); k++) {
                            Literal temp = scc.get(i).get(k);
                            temp.value = value;
                        }
                    }
                }
                for (int i = 0; i < myClauses.size(); i++) {
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
