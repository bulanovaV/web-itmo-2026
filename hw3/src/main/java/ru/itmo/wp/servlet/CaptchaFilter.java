package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    private static final Random rnd = new Random();

    private static final String CAPTCHA = "captcha";
    private static final String CAPTCHA_FLAG = "captchaFlag";
    private static final String REDIRECT_URI = "redirectUri";

    private static final String CAPTCHA_IMAGE_URI = "/captcha.png";

    private static final String CAPTCHA_HTML =
            "<html>" +
            "<body>" +
            "<img src='/captcha.png'>" +
            "<form method='post'>" +
            "<input name='captcha'>" +
            "<button type='submit'>OK</button>" +
            "</form>" +
            "</body>" +
            "</html>";

    private static final int CAPTCHA_MIN = 100;
    private static final int CAPTCHA_MAX = 999;


    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isCaptchaPass(request)) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getMethod().equals("POST")) {
            checkCaptcha(request, response);
            return;
        }
        if (request.getMethod().equals("GET")) {
            showCaptcha(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isCaptchaPass(HttpServletRequest request){
        Boolean captchaFlag = (Boolean) request.getSession().getAttribute(CAPTCHA_FLAG);
        return captchaFlag != null && captchaFlag;
    }

    private void checkCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String captcha = request.getParameter(CAPTCHA);
        Integer curCaptcha = (Integer) request.getSession().getAttribute(CAPTCHA);
        if (captcha != null && curCaptcha != null && captcha.equals(String.valueOf(curCaptcha))) {
            request.getSession().setAttribute(CAPTCHA_FLAG, true);
            response.sendRedirect((String) request.getSession().getAttribute(REDIRECT_URI));
        } else {
            request.getSession().removeAttribute(CAPTCHA);
            showCaptcha(request, response);
        }
    }

    private void showCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute(REDIRECT_URI) == null) {
            request.getSession().setAttribute(REDIRECT_URI, request.getRequestURI());
        }

        int captcha = getCaptcha(request);

        if (request.getRequestURI().equals(CAPTCHA_IMAGE_URI)) {
            byte[] image = ImageUtils.toPng(String.valueOf(captcha));
            response.setContentType("image/png");
            response.getOutputStream().write(image);
        } else {
            response.setContentType("text/html");
            response.getOutputStream().println(CAPTCHA_HTML);
        }
    }

    private int getCaptcha(HttpServletRequest request){
        Integer captcha = (Integer) request.getSession().getAttribute(CAPTCHA);

        if (captcha == null) {
            captcha = CAPTCHA_MIN + rnd.nextInt(CAPTCHA_MAX - CAPTCHA_MIN + 1);
            request.getSession().setAttribute(CAPTCHA, captcha);
        }
        return captcha;
    }
}

