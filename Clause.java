public class Clause {
    Literal node1;
    Literal node2;
    boolean value1;
    boolean value2;
    public Clause(Literal node1, Literal node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.value1 = false;
        this.value2 = false;
    }
    public boolean solve() {
        return value1 || value2;
    }
}
