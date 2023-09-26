package es.blaster.miniapicalc;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MiServletCalculadora extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            Gson oGson = new Gson();
            Operacion op = oGson.fromJson(request.getReader(), Operacion.class);
            if (op.getOperador().equalsIgnoreCase("suma")) {
                op.setResultado(op.getOperando1() + op.getOperando2());
            } else if (op.getOperador().equalsIgnoreCase("resta")) {
                op.setResultado(op.getOperando1() - op.getOperando2());
            } else if (op.getOperador().equalsIgnoreCase("multiplicacion")) {
                op.setResultado(op.getOperando1() * op.getOperando2());
            } else if (op.getOperador().equalsIgnoreCase("division")) {
                if (op.getOperando2() != 0.0) {
                op.setResultado(op.getOperando1() / op.getOperando2());
                } else {
                    // Manejar la división por cero aquí si es necesario
                    op.setResultado(Double.POSITIVE_INFINITY); // o algún otro valor apropiado
                }
            }
            out.print(oGson.toJson(op));
        }

    }

}
