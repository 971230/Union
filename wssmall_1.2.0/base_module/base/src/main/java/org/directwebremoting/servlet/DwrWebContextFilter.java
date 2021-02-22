/*
 * Copyright 2005 Joe Walker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.directwebremoting.servlet;

import org.directwebremoting.Container;
import org.directwebremoting.WebContextFactory.WebContextBuilder;
import org.directwebremoting.util.Logger;
import org.directwebremoting.util.ServletLoggingOutput;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A Servlet Filter that can be used with other web frameworks to allow use of
 * WebContextFactory. Any servlet threads that have their request URL mapped
 * through a DwrWebContextFilter will have a WebContext available to them.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class DwrWebContextFilter implements Filter
{
    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
	public void init(FilterConfig aFilterConfig) throws ServletException
    {
        this.filterConfig = aFilterConfig;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        ServletContext servletContext = filterConfig.getServletContext();

        Container container = (Container) servletContext.getAttribute(Container.class.getName());
        if (container == null)
        {
            log.info("DwrWebContextFilter can not find ServletContext attribute for the DWR Container. Is DwrServlet configured in this web-application?");
        }

        ServletConfig servletConfig = (ServletConfig) servletContext.getAttribute(ServletConfig.class.getName());
        if (servletConfig == null)
        {
            log.info("DwrWebContextFilter can not find ServletContext attribute for the ServletConfig.");
        }

        WebContextBuilder webContextBuilder = (WebContextBuilder) servletContext.getAttribute(WebContextBuilder.class.getName());
        if (webContextBuilder == null)
        {
            log.info("DwrWebContextFilter can not find ServletContext attribute for the WebContextBuilder. WebContext will not be available.");
        }
        else
        {
            try
            {
                webContextBuilder.set((HttpServletRequest) request, (HttpServletResponse) response, servletConfig, servletContext, container);

                // It is totally legitimate for a servlet to be unavailable
                // (e.g. Spring DwrController)
                HttpServlet servlet = (HttpServlet) servletContext.getAttribute(HttpServlet.class.getName());
                if (servlet != null)
                {
                    ServletLoggingOutput.setExecutionContext(servlet);
                }

                chain.doFilter(request, response);
            }
            finally
            {
                webContextBuilder.unset();
                ServletLoggingOutput.unsetExecutionContext();
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
	public void destroy()
    {
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(DwrWebContextFilter.class);

    /**
     * The filter config, that we use to get at the servlet context
     */
    private FilterConfig filterConfig;
}