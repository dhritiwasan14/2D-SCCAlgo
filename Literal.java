public class Literal{
    Integer var;
    int start;
    int end;
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
}
