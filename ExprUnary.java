public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }
    @Override
    public String toString() {
        return "ExprUnary {operator: " + operator.lexema + ", right: " + right.toString() + "}";
    }

    @Override
    public Object resolver(Tabla tabla) {
        Object result = right.resolver(tabla);

        if(operator.tipo == TipoToken.BANG){
            if(result instanceof Boolean){
                return !((Boolean)result);
            }
            else{
                //Lanzas un error
            }
        }

        if(operator.tipo == TipoToken.MINUS){
            if(result instanceof Double){
                return -((Double) result);
            }
            else{
                // Lanzas error
            }
        }

        //Lanzas error
        return result;
    }
}
