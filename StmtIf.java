public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    public String toString() {
        return "StmtIf {condition: " + condition.toString() +
                ", thenBranch: " + thenBranch.toString() +
                ", elseBranch: " + (elseBranch != null ? elseBranch.toString() : "null") + "}";
    }
    @Override
    void exec(Tabla tabla) {
        Object condResult = condition.resolver(tabla);
        if (!(condResult instanceof Boolean)) {
            throw new RuntimeException("La condición del if no es booleana");
        }

        if ((Boolean) condResult) {
            thenBranch.exec(tabla);
        } else if (elseBranch != null) {
            elseBranch.exec(tabla);
        }
    }
}
