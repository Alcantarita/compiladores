public class ExprAssign extends Expression{
    final Token name;
    final Expression value;

    ExprAssign(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }
    @Override
    public String toString()
    {
        return "ExprAssing ("+value.toString()+")";
    }

    @Override
    public Object resolver(Tabla tabla) {
        if (!tabla.existeIdentificador(name.lexema)) {
            throw new RuntimeException("Variable no declarada: '" + name.lexema + "'.");
        }
        tabla.asignar(name.lexema,value.resolver(tabla));
        return null;
    }
}
