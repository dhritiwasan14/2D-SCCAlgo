public class Edge {

    private Literal from;
    private Literal to;

    public Edge(Literal from, Literal to) {
        this.from = new Literal(from.var);
        this.to = new Literal(to.var);
    }

    public Literal to() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {
            if (((Edge) o).from.equals(this.from)) {
                return ((Edge) o).to.equals(this.to);
            }
        }
        return false;
    }
    public Literal getFrom() {
        return new Literal(from.var);
    }
    public Literal getTo() {
        return new Literal(to.var);
    }

}
