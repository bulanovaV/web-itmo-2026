package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {

    private final static String SOURCE_PATH = "src/main/webapp/static";
    private final static String DEPLOY_PATH = "/static";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        File file = getLegalPath(SOURCE_PATH, uri);
        if (file == null) {
            file = getLegalPath(getServletContext().getRealPath(DEPLOY_PATH), uri);
        }
        if (file != null) {
            response.setContentType(getServletContext().getMimeType(file.getName()));
            try (OutputStream outputStream = response.getOutputStream()) {
                Files.copy(file.toPath(), outputStream);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private File getLegalPath(String rootName, String uri) throws IOException {
        File root = new File(rootName).getCanonicalFile();
        File file = new File(root, uri).getCanonicalFile();
        return (file.isFile() && file.toPath().startsWith(root.toPath())) ? file : null;
    }
}
