import java.util.ArrayList;
import java.util.List;
public class ExprCallFunction extends Expression{
    final Expression callee;
    // final Token paren;
    final List<Expression> arguments;

    ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = arguments;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ExprCallFunction (");
        sb.append("callee=" + callee.toString());
        sb.append(", arguments=[");
        for (Expression arg : arguments)
        {
            sb.append(arg.toString() + ", ");
        }
        sb.append("])");
        return sb.toString();
    }

    @Override
    public Object resolver(Tabla tabla) {
        // Obtener la función
        Object funcion = callee.resolver(tabla);
        if (!(funcion instanceof StmtFunction)) {
            throw new RuntimeException("Intento de llamar a algo que no es una función");
        }

        // Evaluar los argumentos
        List<Object> argumentosEvaluados = new ArrayList<>();
        for (Expression arg : arguments) {
            argumentosEvaluados.add(arg.resolver(tabla));
        }

        // Ejecutar la función
        return ejecutarFuncion((StmtFunction) funcion, argumentosEvaluados, tabla);
    }

    private Object ejecutarFuncion(StmtFunction funcion, List<Object> argumentosEvaluados, Tabla tabla) {
        if (argumentosEvaluados.size() != funcion.params.size()) {
            throw new RuntimeException("Número incorrecto de argumentos para la función " + funcion.name.lexema);
        }
        tabla.iniciarNuevoAlcance();

        try {
            for (int i = 0; i < funcion.params.size(); i++) {
                Token param = funcion.params.get(i);
                Object valorArg = argumentosEvaluados.get(i);
                tabla.declarar(param.lexema, valorArg);
            }
            funcion.body.exec(tabla);

        } finally {
            tabla.cerrarAlcanceActual();
        }

        return null;
    }
}
