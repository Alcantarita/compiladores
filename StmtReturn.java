public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StmtReturn {expression: " + (value != null ? value.toString() : "null") + "}";
    }

    @Override
    void exec(Tabla tabla) {
    }
}


