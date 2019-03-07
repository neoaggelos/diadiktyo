import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class HTTPGetServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            PrintWriter out;

            response.setContentType("text/html");
            out = response.getWriter();

            StringBuffer buf = new StringBuffer();
            buf.append("<html><head><title>");
            buf.append("Simple Servlet Example");
            buf.append("</title></head><body>");
            buf.append("<h1>It actually worked lol</h1>");
            buf.append("</body></html>");

            out.println(buf.toString());
            out.close();
    }
}
