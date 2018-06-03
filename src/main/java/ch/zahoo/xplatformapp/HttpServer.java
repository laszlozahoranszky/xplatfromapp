package ch.zahoo.xplatformapp;

import ch.zahoo.xplatformapp.requestHandlers.CreateNewFileHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer
{
    public final static int PSEUDO_RANDOM_PORT = 45612;

    private final static Server server = new Server(PSEUDO_RANDOM_PORT);

    public static void startHttpServer() throws Exception
    {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new ViewHtmlProvider()),"/*");
        context.addServlet(new ServletHolder(new CreateNewFileHandler()), "/createNewFile");
        server.setHandler(context);
        server.start();
        server.join();
    }

    public static void stopHttpServer() throws Exception {
        server.stop();
    }
}