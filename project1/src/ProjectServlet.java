import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ProjectServlet extends HttpServlet {
    private static String languages[] = {"Python", "JS", "Java", "PHP"};
    private static String frontend_languages[] = {"JS", "CSS"};
    private static String backend_languages[] = {"Python", "JS", "Java", "PHP"};
    private static String stacks[] = {"frontend", "backend"};
    private static String frontends[] = {"AngularJS", "ReactJS", "Bootstrap"};
    private static String backends[] = {"Django", "Flask", "ExpressJS", "Spring", "CodeIgniter", "Laravel"};

    /**
     * Loads /WEB-INF/templates/<name> as a string
     */
    private String loadTemplate(String name) throws IOException {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(getServletContext().getResourceAsStream("/WEB-INF/templates/" + name))
        );
        StringBuffer buf = new StringBuffer();
        String line;

        while ((line = in.readLine()) != null) {
            buf.append(line);
        }

        return buf.toString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            PrintWriter out;

            response.setContentType("text/html");
            out = response.getWriter();

            String text = request.getRequestURI();
            HashMap<String, String> cookies = cookiesMap(request.getCookies());

            if (text.equals("/project1/choose/")) {
                /* pick next form depending on which form was submitted */
                String which = request.getParameter("form_id");

                // no form == we came from index page, show language form// language form --> set cookies, show low level form
                if (which.equals("lang_form")) {
                    // handle request form
                    // crashes if none selected, good
                    String langs = "";
                    for (var lang : languages) {
                        if (request.getParameter(lang) != null) {
                            langs += lang + "-";
                        }
                    }
                    addPersistentCookie(response, "langs", langs);

                    text = stackForm(langs);
                }

                // stack form --> set cookies, open backend or frontend form
                else if (which.equals("stack_form")) {
                    // handle stack form
                    String sts = "";
                    for (var stack : stacks) {
                        if (request.getParameter(stack) != null) {
                            sts += stack + "-";
                            addPersistentCookie(response, stack, "to_visit");
                        } else {
                            addPersistentCookie(response, stack, "skip");
                        }
                    }

                    // choose what to show next depending on button pressed
                    if (request.getParameter("go_frontend") != null) {
                        text = frontendForm(cookies.get("langs"));
                    } else {
                        text = backendForm(cookies.get("langs"));
                    }
                }

                // frontend form --> open backend or final info
                else if (which.equals("frontend_form")) {
                    // handle frontend form
                    String fs = "";
                    for (var frontend : frontends) {
                        if (request.getParameter(frontend) != null) {
                            fs += frontend + "-";
                        }
                    }
                    addPersistentCookie(response, "frontend", fs);

                    // show final page, or backend
                    if (cookies.get("backend").equals("to_visit")) {
                        text = backendForm(cookies.get("langs"));
                    } else {
                        text = finalPage(fs, cookies.get("backend"));
                    }
                }

                // backend form --> open frontend or final info
                else if (which.equals("backend_form")) {
                    // handle backend form
                    String bs = "";
                    for (var backend : backends) {
                        if (request.getParameter(backend) != null) {
                            bs += backend + "-";
                        }
                    }
                    addPersistentCookie(response, "backend", bs);

                    // show final page, or frontend
                    if (cookies.get("frontend").equals("to_visit")) {
                        text = frontendForm(cookies.get("langs"));
                    } else {
                        text = finalPage(cookies.get("frontend"), bs);
                    }
                }

            } else {
                text = page404();
            }

            // write response
            out.println(text);
            out.close();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            PrintWriter out;
            response.setContentType("text/html");
            out = response.getWriter();

            String text = request.getRequestURI();
            HashMap<String, String> cookies = cookiesMap(request.getCookies());

            if (text.equals("/project1/")) {                // index
                text = indexPage();
            } else if (text.equals("/project1/choose/")) {  // start
                text = langForm();
            } else {                                        // 404
                text = page404();
            }

            // write response
            out.println(text);
            out.close();
    }

    private String indexPage() throws IOException {
        return loadTemplate("base.html").
            replace("{{TITLE}}", "Welcome").
            replace("{{CONTENT}}", loadTemplate("pages/welcome.html"));
    }

    private String page404() throws IOException {
        return loadTemplate("base.html").
            replace("{{TITLE}}", "404: Page Not Found").
            replace("{{CONTENT}}", loadTemplate("pages/page_404.html"));
    }

    private String langForm() throws IOException {
        return loadTemplate("base.html").
            replace("{{TITLE}}", "Select Languages").
            replace("{{CONTENT}}", loadTemplate("pages/lang_form.html"));
    }

    private String stackForm(String langs) throws IOException {
        String display = langs.substring(0, langs.length()-1).replace("-", ", ");
        String form = loadTemplate("pages/stack_form.html")
            .replace("{{TEXT}}", display);

        return loadTemplate("base.html").
            replace("{{TITLE}}", "Choose what you would like to work on").
            replace("{{CONTENT}}", form);
    }

    private String backendForm(String langs) throws IOException {
        StringBuffer inputs = new StringBuffer();
        for (var lang : backend_languages) {
            if (langs.contains(lang)) {
                inputs.append(
                    loadTemplate("items/backend/"+lang+".html")
                );
            }
        }

        String form = loadTemplate("pages/backend_form.html").
            replace("{{INPUTS}}", inputs.toString());

        return loadTemplate("base.html").
            replace("{{TITLE}}", "Choose backends that seem interesting to you").
            replace("{{CONTENT}}", form);
    }

    private String frontendForm(String langs) throws IOException {
        StringBuffer inputs = new StringBuffer();

        for (var lang : frontend_languages) {
            if (langs.contains(lang) || lang.equals("CSS")) {
                inputs.append(
                    loadTemplate("items/frontend/"+lang+".html")
                );
            }
        }

        String form = loadTemplate("pages/frontend_form.html").
            replace("{{INPUTS}}", inputs.toString());

        return loadTemplate("base.html").
            replace("{{TITLE}}", "Choose frontends that seem interesting to you").
            replace("{{CONTENT}}", form);
    }

    private String finalPage(String frontend, String backend) throws IOException {
        StringBuffer textBuf = new StringBuffer();
        String table = loadTemplate("items/table.html");

        if (!frontend.equals("skip")) {
            StringBuffer fdata = new StringBuffer();
            for (var f : frontends) {
                if (frontend.contains(f)) {
                    fdata.append(
                        loadTemplate("items/info/"+f+".html")
                    );
                }
            }

            textBuf.append(table.
                replace("{{TITLE}}", "Frontend Technologies").
                replace("{{DATA}}", fdata)
            );
        }

        if (!backend.equals("skip")) {
            StringBuffer bdata = new StringBuffer();
            for (var b : backends) {
                if (backend.contains(b)) {
                    bdata.append(
                        loadTemplate("items/info/"+b+".html")
                    );
                }
            }

            textBuf.append(table.
                replace("{{TITLE}}", "Backend Technologies").
                replace("{{DATA}}", bdata)
            );
        }

        textBuf.append(loadTemplate("items/start_over.html"));

        return loadTemplate("base.html").
            replace("{{TITLE}}", "Check the options below").
            replace("{{CONTENT}}", textBuf.toString());
    }

    // parse cookies in a [name, value] map for easier access
    private HashMap<String, String> cookiesMap(Cookie cookies[]) {
        HashMap<String, String> res = new HashMap<String, String>();
        if (cookies != null)
            for (var c : cookies)
                res.put(c.getName(), c.getValue());

        return res;
    }

    // add persistent cookie helper
    private void addPersistentCookie(HttpServletResponse response, String name, String value) {
        Cookie c = new Cookie(name, value);
        c.setMaxAge(-1);
        response.addCookie(c);
    }
}
