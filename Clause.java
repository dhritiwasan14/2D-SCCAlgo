public class Clause {
    Literal one;
    Literal two;
    public Clause(Literal one, Literal two) {
        this.one = one;
        this.two = two;
    }
    public Clause(Integer one, Integer two) {
        this.one = new Literal(one);
        this.two = new Literal(two);
    }
    public Literal[] toImplicate() {
        Literal[] toReturn = {one, two};
        return toReturn;
    }
}
