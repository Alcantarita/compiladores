public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }
    @Override
    public String toString()
    {
        return "ExprGrouping {expression: " + expression.toString() + "}";
    }
    @Override
    public Object resolver(Tabla tabla) {
        return expression.resolver(tabla);
    }
}
