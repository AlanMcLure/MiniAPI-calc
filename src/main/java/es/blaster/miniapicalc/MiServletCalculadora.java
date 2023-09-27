package es.blaster.miniapicalc;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MiServletCalculadora extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            response.setContentType("application/json");
            
            PrintWriter out = response.getWriter();
            Gson oGson = new Gson();
            try {
                ResponseBean oRB = oGson.fromJson(request.getReader(), ResponseBean.class);
                if (oRB.getOperador().equalsIgnoreCase("suma")) {
                    oRB.setResultado(oRB.getOperando1() + oRB.getOperando2());
                } else if (oRB.getOperador().equalsIgnoreCase("resta")) {
                    oRB.setResultado(oRB.getOperando1() - oRB.getOperando2());
                } else if (oRB.getOperador().equalsIgnoreCase("multiplicacion")) {
                    oRB.setResultado(oRB.getOperando1() * oRB.getOperando2());
                } else if (oRB.getOperador().equalsIgnoreCase("division")) {
                    if (oRB.getOperando2() != 0) {
                        oRB.setResultado(oRB.getOperando1() / oRB.getOperando2());
                    } else {
                        // Manejar la división por cero aquí si es necesario
                        oRB.setResultado(Double.POSITIVE_INFINITY); // o algún otro valor apropiado
                    }
                }
                response.setStatus(200);
                out.print(oGson.toJson(oRB));
            } catch (JsonIOException e) {
                response.setStatus(500);
                ResponseBean oRB = new ResponseBean();
                oRB.setErrorDescription("Error en JSON 1");
                out.print(oGson.toJson(oRB));
            } catch (IOException e) {
                response.setStatus(500);
                ResponseBean oRB = new ResponseBean();
                oRB.setErrorDescription("Error en JSON 2");
                out.print(oGson.toJson(oRB));
            } catch (JsonSyntaxException e) {
                response.setStatus(500);
                ResponseBean oRB = new ResponseBean();
                oRB.setErrorDescription("Error en JSON 3");
                out.print(oGson.toJson(oRB));
            }
    }
}
