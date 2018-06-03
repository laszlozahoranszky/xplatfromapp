package ch.zahoo.xplatformapp.requestHandlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class CreateNewFileHandler extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            String filename = request.getParameter("filePath");
            if (filename == null || filename.isEmpty()){
                throw new IllegalArgumentException("The filepath must be a non-empty string.");
            }
            String fileContent = request.getParameter("fileContent");
            createNewFile(filename, fileContent);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println("File created:" + filename);
        }
        catch (Exception exception) {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getOutputStream().println(exception.getMessage());
        }
    }

    private void createNewFile(String filename, String fileContent) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println(fileContent);
        writer.close();
    }
}
