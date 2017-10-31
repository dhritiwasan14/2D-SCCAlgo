public class Main {

    public static void main(String[] args) {
        SATSolver solver = new SATSolver();
        int[][] arr1 = {{4, 2},{-4, 4}, {3, 4}};
        solver.addLiterals(arr1);
        System.out.println(solver.findStrongConected());
    }

}
