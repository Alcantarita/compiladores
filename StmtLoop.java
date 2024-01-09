public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "StmtLoop {condition: " + condition.toString() +
                ", body: " + body.toString() + "}";
    }

    @Override
    void exec(Tabla tabla) {
        while (true) {
            Object condResult = condition.resolver(tabla);
            if (!(condResult instanceof Boolean)) {
                throw new RuntimeException("La condición del bucle no es booleana");
            }
            if (!(Boolean) condResult) {
                break;
            }
            body.exec(tabla);
        }
    }
}
