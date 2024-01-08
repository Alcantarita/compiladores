class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "ExprVariable {name: " + name.lexema + "}";
    }
}