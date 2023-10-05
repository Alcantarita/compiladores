import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;
    public static int estadoComentario=0;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);

            switch (estado){
                case 0:
                    if(Character.isLetter(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;

                        /*while(Character.isDigit(c)){
                            lexema += c;
                            i++;
                            c = source.charAt(i);
                        }
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        lexema = "";
                        estado = 0;
                        tokens.add(t);
                        */

                    }
                    else if(c == '>'){
                        estado = 1;
                        lexema += c;
                    }
                    else if(c == '<'){
                        estado = 4;
                        lexema += c;
                    }
                    else if(c == '='){
                        estado = 7;
                        lexema += c;
                    }
                    else if(c == '!'){
                        estado = 10;
                        lexema += c;
                    } else if (c=='"') {
                        estado = 24;
                        lexema += c;

                    }
                    break;

                case 1:
                    if(c == '='){
                        lexema += c;

                        Token t = new Token(TipoToken.GREATER_EQUAL, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    else{
                        Token t = new Token(TipoToken.GREATER, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    break;
                case 4:
                    if(c == '='){
                        lexema += c;

                        Token t = new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    else{
                        Token t = new Token(TipoToken.LESS, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    break;
                case 7:
                    if(c == '='){
                        lexema += c;

                        Token t = new Token(TipoToken.EQUAL_EQUAL, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    else {
                        Token t = new Token(TipoToken.EQUAL, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    break;
                case 10:
                    if(c == '='){
                        lexema += c;

                        Token t = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    else {
                        Token t = new Token(TipoToken.BANG, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        //i--;
                    }
                    break;
                case 13:
                    if(Character.isLetterOrDigit(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;

                    }
                    break;

                case 15:
                    if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }
                    else if(c == '.'){
                        estado = 16;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado = 18;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 16:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }
                    break;
                case 17:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado = 18;
                        lexema += c;

                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Float.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 18:
                    if(c == '+'||c=='-'){
                        estado = 19;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    break;
                case 19:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    break;
                case 20:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER,lexema,Float.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 24:
                    if(c == '"'){
                        Token t= new Token(TipoToken.STRING,lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    else if (c == '\n'){
                        System.out.println("Cadena incompleta");
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    else{
                        lexema += c;
                        estado = 24;
                    }
            }



        }

        return tokens;
    }
    public static class Cadena{
        private static final int E_Inicial = 0;
        private static final int E_Inside = 1;
        private static final int E_Aceptado = 2;

        public boolean reconocer(String input){
            int E_Actual = E_Inicial;

            for(char c: input.toCharArray()){
                switch(E_Actual){
                    case E_Inicial:
                        if(c == '"')
                            E_Actual = E_Inside;
                        else
                            return false;
                        break;
                    case E_Inside:
                        if(c == '"')
                            E_Actual = E_Aceptado;
                        break;
                    case E_Aceptado:
                        return false;
                }
            }
            return E_Actual == E_Aceptado;
        }
    }

    public static boolean esComentario(char c, int i)
    {
        switch(estadoComentario)
        {
            case 0:
            if(c=='/')
            {
                estadoComentario=26;
                return true;
            }
            break;

            case 26:
            if(c=='*')
            {
                estadoComentario=27;
                return true;
            }
            else if (c=='/')
            {
                estadoComentario=30;
                return true;
            }
            else
            {
                estadoComentario=0;//reste
                estadoComentario=0;
            }
            break;

            case 27:
            if(c=='*')
            {
                estadoComentario=28;
                return true;
            }
            else
            {
                //Aqui es en cualquier otro caracter
                estadoComentario=27;
                return true;
            }

            case 28:
            if(c=='/')
            {
                estadoComentario=29;
                return true;
            }
            else if (c=='*')
            {
                return true;//aqui permanecemos en 28
            }
            else
            {
                estadoComentario=27;
                return true;//Se queda en el estado 28
            }

            case 29:
            estadoComentario=0;//final del comentario
            estadoComentario=0;//Final del comentario
            return true;

            case 30:
            if(c=='\n')
            {
                estadoComentario=31;
                return true;
            }
            else
            {
                estadoComentario=30;//Permanecemos hasta el salto de linea
                return true;
            }

            case 31:
            estadoComentario=0;//reset
            return true;

            default:
            estadoComentario=0;
        }
        return false;
    }
}
