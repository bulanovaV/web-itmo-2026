package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {

    private static class Message {
        private final String user;
        private final String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }

    }

    private static final List<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        response.setContentType("application/json");
        Gson gson = new Gson();
        String user;
        switch (uri) {
            case "/message/auth":
                user = request.getParameter("user");
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                } else {
                    user = (String) request.getSession().getAttribute("user");
                    if (user == null) {
                        user = "";
                    }
                }
                response.getWriter().print(gson.toJson(user));
                response.getWriter().flush();
                break;
            case "/message/findAll":
                response.getWriter().print(gson.toJson(messages));
                response.getWriter().flush();
                break;
            case "/message/add":
                user = (String) request.getSession().getAttribute("user");
                String text = request.getParameter("text");
                if (user == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                if (text == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Message message = new Message(user, text);
                messages.add(message);
                break;
        }
    }

}
