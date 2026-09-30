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
        String[] uris = uri.split("\\+");
        File[] files = new File[uris.length];
        for (int i = 0; i < uris.length; i++) {
            files[i] = getLegalPath(SOURCE_PATH, uris[i]);
            if (files[i] == null) {
                files[i] = getLegalPath(getServletContext().getRealPath(DEPLOY_PATH), uris[i]);
            }
            if (files[i] == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        response.setContentType(getServletContext().getMimeType(files[0].getName()));
        try (OutputStream outputStream = response.getOutputStream()) {
            for (File file : files) {
                Files.copy(file.toPath(), outputStream);
            }
        }
    }

    private File getLegalPath(String rootName, String uri) throws IOException {
        File root = new File(rootName).getCanonicalFile();
        File file = new File(root, uri).getCanonicalFile();
        return (file.isFile() && file.toPath().startsWith(root.toPath())) ? file : null;
    }
}
