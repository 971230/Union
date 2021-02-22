package com.rop.core.compress;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GZIPFilter implements Filter {

    private static Logger logger = Logger.getLogger(GZIPFilter.class);

    @Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            String ae = request.getHeader("accept-encoding");
            if (ae != null && ae.indexOf("gzip") != -1) {
                GZIPResponseWrapper gZIPResponseWrapper = new GZIPResponseWrapper(response);
                chain.doFilter(req, gZIPResponseWrapper);
                gZIPResponseWrapper.finishResponse();
                return;
            }
            chain.doFilter(req, res);
        } else {
            logger.debug("not servlet request...");
        }
    }

    @Override
	public void init(FilterConfig filterConfig) {
        logger.debug("The GZIP Is Enable");
    }

    @Override
	public void destroy() {

    }
}
