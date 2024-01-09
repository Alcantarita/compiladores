public class ExprLogical extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    @Override
    public String toString()
    {
        return "ExprLogical {left: " + left.toString() +
                ", operator: " + operator.lexema +
                ", right: " + right.toString() + "}";
    }
    @Override
    public Object resolver(Tabla tabla) {
        Object leftResult = left.resolver(tabla);
        Object rightResult = null;
        if (!(leftResult instanceof Boolean)) {
            throw new RuntimeException("Operador izquierdo no es un valor booleano");
        }

        switch (operator.tipo) {
            case AND:
                if (!(Boolean) leftResult) {
                    return false;
                }
                rightResult = right.resolver(tabla);
                break;
            case OR:
                if ((Boolean) leftResult) {
                    return true;
                }
                rightResult = right.resolver(tabla);
                break;
            default:
                throw new RuntimeException("Operador desconocido para una expresión lógica: " + operator.tipo);
        }

        if (!(rightResult instanceof Boolean)) {
            throw new RuntimeException("Operador derecho no es un valor booleano");
        }

        return rightResult;
    }
}

