import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class AST implements Parser{
    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    private List<Statement> statements;

    public AST(List<Token> tokens){
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
        statements = new  ArrayList<>();
    }

    @Override
    public boolean parse() {
        statements = program();
        if (preanalisis.tipo == TipoToken.EOF && !hayErrores) {
            //System.out.println("Entrada correcta");
            //printTree();
            return true;
        } else {
            System.out.println("Se encontraron errores");
            return false;
        }
    }
    public List<Statement> program(){
        statements.clear();
        if(preanalisis.tipo!=TipoToken.EOF) {
            declaration(statements);
            return statements;
        }
        return null;
    }
    private void declaration(List<Statement> statements){
        switch (preanalisis.tipo){
            case FUN:
                Statement funDecl = funDecl();
                statements.add(funDecl);
                declaration(statements);
                break;
            case VAR:
                Statement varDecl = varDecl();
                statements.add(varDecl);
                declaration(statements);
                break;
            case TRUE, FALSE, NUMBER, STRING, NULL,  IDENTIFIER, LEFT_PAREN, EQUAL, BANG, MINUS, FOR, IF, PRINT, RETURN, WHILE, LEFT_BRACE:
                Statement statement = statement();
                statements.add(statement);
                declaration(statements);
                break;
        }
    }
    private Statement funDecl(){
        if(preanalisis.tipo==TipoToken.FUN) {
            match(TipoToken.FUN);
            return function();
        }else{
            hayErrores = true;
            System.out.println("Error en la sintaxis, se esperaba una funcion");
            return null;
        }
    }
    private Statement varDecl(){
        if(preanalisis.tipo == TipoToken.VAR) {
            match(TipoToken.VAR);
            match(TipoToken.IDENTIFIER);
            Token nombre = previous();
            Expression inicio = varInit();
            match(TipoToken.SEMICOLON);
            return new StmtVar(nombre, inicio);
        }
        return null;
    }
    private Expression varInit(){
        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            return expresion();
        }
        return null;
    }
    private Statement statement(){
        switch (preanalisis.tipo){
            case BANG, MINUS, TRUE, FALSE, NULL, NUMBER, STRING, IDENTIFIER, LEFT_PAREN:
                return exprStmt();
            case FOR:
                return forStmt();
            case IF:
                return ifStmt();
            case PRINT:
                return printStmt();
            case RETURN:
                return returnStmt();
            case WHILE:
                return whileStmt();
            case LEFT_BRACE:
                return block();
            default:
                hayErrores = true;
                System.out.println("Error de sentencia");
                return null;
        }
    }
    private Statement  exprStmt(){
        Expression exp = expresion();
        match(TipoToken.SEMICOLON);
        return new StmtExpression(exp);
    }
    private Statement forStmt(){
        match(TipoToken.FOR);
        match(TipoToken.LEFT_PAREN);
        Statement inicio = forStmt1();
        Expression condicion =  forStmt2();
        Expression incremento = forStmt3();
        match(TipoToken.RIGHT_PAREN);
        Statement cuerpo = statement();
        if(incremento!=null){
            cuerpo = new StmtBlock(Arrays.asList(cuerpo, new StmtExpression(incremento)));
        }
        if(condicion==null){
            condicion = new ExprLiteral(true);
        }
        cuerpo = new StmtLoop(condicion,cuerpo);
        if(inicio!=null){
            cuerpo= new StmtBlock(Arrays.asList(inicio,cuerpo));
        }
        return cuerpo;
    }
    private Statement forStmt1() {
        switch (preanalisis.tipo) {
            case VAR:
                return varDecl();
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
                Statement stmt = exprStmt();
                return stmt;
            case SEMICOLON:
                match(TipoToken.SEMICOLON);
                return null;
            default:
                hayErrores = true;
                System.out.println("Error en la posicion inicial del for");
                return null;
        }
    }
    private Expression forStmt2() {
        switch (preanalisis.tipo) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
                Expression expr = expresion();
                match(TipoToken.SEMICOLON);
                return expr;
            case SEMICOLON:
                match(TipoToken.SEMICOLON);
                return null;

            default:
                hayErrores = true;
                System.out.println("Error: "+ preanalisis.lexema+" "+i );
                return null;
        }
    }
    private Expression forStmt3(){
        switch (preanalisis.tipo) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
                return expresion();
        }
        return null;
    }
    private Statement ifStmt(){
        match(TipoToken.IF);
        match(TipoToken.LEFT_PAREN);
        Expression condicion = expresion();
        match(TipoToken.RIGHT_PAREN);
        Statement llave = statement();
        Statement elseLlave = elseStmt();
        return new StmtIf(condicion,llave,elseLlave);
    }
    private Statement elseStmt(){
        if(preanalisis.tipo==TipoToken.ELSE) {
            match(TipoToken.ELSE);
            return statement();
        }
        return null;
    }
    private Statement printStmt(){
        match(TipoToken.PRINT);
        Expression exp = expresion();
        match(TipoToken.SEMICOLON);
        return new StmtPrint(exp);
    }
    private Statement returnStmt(){
        if(preanalisis.tipo==TipoToken.RETURN) {
            match(TipoToken.RETURN);
            Expression exp = null;
            exp = returnExpOpc(exp);
            match(TipoToken.SEMICOLON);
            return new StmtReturn(exp);
        }else{
            hayErrores = true;
            System.out.println("Error, se esperaba un retorno");
            return null;
        }
    }
    private Expression returnExpOpc(Expression exp){
        exp = expresion();
        return exp;
    }
    private Statement whileStmt(){
        if(preanalisis.tipo == TipoToken.WHILE) {
            match(TipoToken.WHILE);
            match(TipoToken.LEFT_PAREN);
            Expression condicion = expresion();
            match(TipoToken.RIGHT_PAREN);
            Statement cuerpo = statement();
            return new StmtLoop(condicion, cuerpo);
        }
        return null;
    }
    private Statement block(){
        if(preanalisis.tipo==TipoToken.LEFT_BRACE) {
            List<Statement> sentencias = new ArrayList<>();
            match(TipoToken.LEFT_BRACE);
            declaration(sentencias);
            match(TipoToken.RIGHT_BRACE);
            return  new StmtBlock(sentencias);
        }
        return null;
    }
    private void match(TipoToken tt) {
        if (preanalisis.tipo == tt) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            hayErrores = true;
            System.out.println("Error encontrado en el token "+ preanalisis.lexema+i + " Token esperado: "+ tt + "Token encontrado: "+ preanalisis.tipo);
        }
    }

    private Token previous() {
        return this.tokens.get(i - 1);
    }
}
