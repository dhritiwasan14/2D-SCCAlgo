public class Literal{
    Integer var;
    int start;
    int end;
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
    public void setStart(int start) {
        this.start = start;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    public int getVar() {
        return var;
    }
    public void setValue(boolean value) {
        this.value = value;
    }
    public boolean getValue() {
        return value;
    }
}
