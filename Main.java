import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long starttime = System.currentTimeMillis();
        ArrayList<ArrayList<Literal>> output = new ArrayList<>();
        SATSolver solver = new SATSolver();
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
                            } catch (Exception e) {
                                solver.addLiterals(arraylist.get(0), arraylist.get(1));
                                solver.addLiterals(arraylist.get(1), arraylist.get(0));
                            }
                        }
                    }
                }
            }
            System.out.println(System.currentTimeMillis());
            ArrayList<List<Literal>> scc = solver.findStrongConected();
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
