public class Literal{
    Integer var;
    boolean value;
    public Literal() {

    }
    public Literal(Integer var) {
        this.var = var;
    }
    @Override
    public boolean equals(Object o) {
        if (this.var.equals(((Literal) o).var)) {
            return true;
        } return false;
    }
    @Override
    public int hashCode() {
        return var;
    }
    public int getVar() {
        return var;
    }
}
