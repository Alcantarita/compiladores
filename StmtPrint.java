public class StmtPrint extends Statement {
    final Expression expression;

    StmtPrint(Expression expression) {
        this.expression = expression;
    }
    @Override
    public String toString() {
        return "StmtPrint {expression: " + expression.toString() + "}";
    }

    @Override
    void exec(Tabla tabla) {
        Object resultado = expression.resolver(tabla);
        System.out.println(resultado);
    }
}
