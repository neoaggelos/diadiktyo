import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.PrintWriter;


public class Servlet extends HttpServlet {
    Document doc;
    TransformerFactory tf_factory;
    ServletContext ctx;

    public void init(ServletConfig config) {
        try {
            ctx = config.getServletContext();
            String absPath = ctx.getRealPath("/WEB-INF/xsl.xsl");

            tf_factory = TransformerFactory.newInstance();

            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            fact.setNamespaceAware(true);
            DocumentBuilder builder = fact.newDocumentBuilder();
            doc = builder.parse(absPath);
        } catch (Exception e) {	e.printStackTrace(); }
    }

    private void updateDom(Document doc, HttpServletRequest request) {
        NodeList nl = doc.getElementsByTagName("body");

        String bg_color = request.getParameter("color");
        if (bg_color == null) bg_color = "white";

        String align = request.getParameter("align");
        if (align == null) align = "left";

        Attr a = doc.createAttribute("style");
        a.setValue("color: "+bg_color + "; text-align: " + align);

        nl.item(0).getAttributes().setNamedItem(a);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pwr = response.getWriter();
        response.setContentType("text/html");

        // update loaded dom
        updateDom(doc, request);
        try {
            Transformer tf = tf_factory.newTransformer(new DOMSource(doc));

            // choose correct xml
            String which = request.getParameter("content");
            if (which.equals("albums")) which = "Albums.xml";
            else if (which.equals("artists")) which = "Artists.xml";
            else if (which.equals("songs")) which = "Songs.xml";
            else which = "Cars.xml";

            StreamSource xmlSource = new StreamSource(
                ctx.getResourceAsStream("/WEB-INF/" + which)
            );
            tf.transform(xmlSource, new StreamResult(pwr));

        } catch (TransformerException e) {
            System.out.println("KYK");
        }
        pwr.close();
    }

}

