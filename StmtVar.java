public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }
    @Override
    public String toString() {
        return "StmtVar {name: " + name.lexema +
                ", initializer: " + (initializer != null ? initializer.toString() : "null") + "}";
    }
    @Override
    void exec(Tabla tabla) {
        if (tabla.existeIdentificador(name.lexema)) {
            throw new RuntimeException("Variable ya declarada: '" + name.lexema + "'.");
        }else {
            Object valorInicial = null;
            if(initializer!=null){
                valorInicial =  initializer.resolver(tabla);
            }
            tabla.declarar(name.lexema, valorInicial);
        }
    }
}
