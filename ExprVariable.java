class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "ExprVariable {name: " + name.lexema + "}";
    }

    @Override
    public Object resolver(Tabla tabla) {
        // Verificar si la variable existe en la tabla de símbolos
        if (!tabla.existeIdentificador(name.lexema)) {
            throw new RuntimeException("Variable no declarada: '" + name.lexema + "'.");
        }
        // Obtener y retornar el valor de la variable
        return tabla.obtener(name.lexema);
    }
}