package ch.zahoo.xplatformapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

class ViewHtmlProvider extends HttpServlet
{
    public ViewHtmlProvider(){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        String htmlStr = getIndexHtml();
        htmlStr = htmlStr.replaceAll("localport_paceholder_c03861b4-0c09-4bc9-aca9-3a3bd2fe8aad", new Integer(HttpServer.PSEUDO_RANDOM_PORT).toString());
        response.getWriter().println(htmlStr);
    }

    private String getIndexHtml() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("index.html").getFile());
        return readFile(file.getPath(), Charset.defaultCharset());
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
