public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    @Override
    public String toString()
    {
        return "ExprBinary("+left.toString()+" "+operator.lexema+ " " + right.toString() + ")";
    }
    public Object resolver(Tabla tabla) {
        Object leftResult = left.resolver(tabla);
        Object rightResult = right.resolver(tabla);
        if (leftResult == null ) {
            System.out.println("el error esta en Left");
            throw new RuntimeException("Una de las variables de la expresión binaria es null");
        } else if (rightResult == null) {
            System.out.println("El error esta en right");
            throw new RuntimeException("Una de las variables de la expresión binaria es null");

        }
        switch (operator.tipo) {
            case PLUS:
                // Suma para números o concatenación para Strings
                if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult + (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult + (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Integer) leftResult + (Double) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult + (Integer) rightResult;
                } else if (leftResult instanceof String || rightResult instanceof String) {
                    return leftResult.toString() + rightResult.toString();
                }
                break;
            case MINUS:
                // Resta solo para números
                if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult - (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult - (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Integer) leftResult - (Double) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult - (Integer) rightResult;
                }
                break;
            case STAR:
                // Multiplicación solo para números
                if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult * (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult * (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Integer) leftResult * (Double) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult * (Integer) rightResult;
                }
                break;
            case SLASH:
                // División solo para números
                if ((rightResult instanceof Double && (Double) rightResult == 0.0) ||
                        (rightResult instanceof Integer && (Integer) rightResult == 0)) {
                    throw new ArithmeticException("División por cero");
                } else if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult / (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult / (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Double) leftResult / (Integer) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult / (Integer) rightResult;
                }
                break;
            case GREATER:
                if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult > (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult > (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Integer) leftResult > (Double) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult > (Integer) rightResult;
                }
                break;
            case GREATER_EQUAL:
                if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult >= (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult >= (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Integer) leftResult >= (Double) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult >= (Integer) rightResult;
                }
                break;
            case LESS:
                if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult < (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult < (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Integer) leftResult < (Double) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult < (Integer) rightResult;
                }
                break;
            case LESS_EQUAL:
                if (leftResult instanceof Double && rightResult instanceof Double) {
                    return (Double) leftResult <= (Double) rightResult;
                }else if(leftResult instanceof Double && rightResult instanceof Integer){
                    return (Double) leftResult <= (Integer) rightResult;
                }else if(leftResult instanceof Integer && rightResult instanceof Double){
                    return (Integer) leftResult <= (Double) rightResult;
                }
                else if (leftResult instanceof Integer && rightResult instanceof Integer) {
                    return (Integer) leftResult <= (Integer) rightResult;
                }
                break;
            case EQUAL_EQUAL:
                return leftResult.equals(rightResult);
            // Más casos para otros operadores...
            default:
                throw new RuntimeException("Operador desconocido: " + operator.tipo);
        }

        throw new RuntimeException("Tipo de operando no válido para " + operator.tipo);
    }
}
