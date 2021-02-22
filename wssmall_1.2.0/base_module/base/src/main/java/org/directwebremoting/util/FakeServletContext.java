/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.directwebremoting.util;

import javax.servlet.*;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Fake implementation of the ServletContext interface.
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class FakeServletContext implements ServletContext
{
    /**
     * Create a new FakeServletContext, using no base path and a
     * DefaultResourceLoader (i.e. the classpath root as WAR root).
     */
    public FakeServletContext()
    {
        this("");
    }

    /**
     * Create a new FakeServletContext, using a DefaultResourceLoader.
     * @param resourceBasePath the WAR root directory (should not end with a slash)
     */
    public FakeServletContext(String resourceBasePath)
    {
        this.resourceBasePath = (resourceBasePath != null ? resourceBasePath : "");

        // Use JVM temp dir as ServletContext temp dir.
        String tempDir = System.getProperty("java.io.tmpdir");
        if (tempDir != null)
        {
            attributes.put("javax.servlet.context.tempdir", new File(tempDir));
        }
    }

    /**
     * Build a full resource location for the given path,
     * prepending the resource base path of this FakeServletContext.
     * @param path the path as specified
     * @return the full resource path
     */
    protected String getResourceLocation(String path)
    {
        String output = path;
        if (!output.startsWith("/"))
        {
            output = "/" + output;
        }
        output = resourceBasePath + output;

        return output;
    }

    @Override
	public ServletContext getContext(String name)
    {
        throw new UnsupportedOperationException("getContext");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getMajorVersion()
     */
    @Override
	public int getMajorVersion()
    {
        return 2;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getMinorVersion()
     */
    @Override
	public int getMinorVersion()
    {
        return 4;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getMimeType(java.lang.String)
     */
    @Override
	public String getMimeType(String filePath)
    {
        throw new UnsupportedOperationException("getMimeType");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
     */
    @Override
	public Set getResourcePaths(String path)
    {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getResource(java.lang.String)
     */
    @Override
	public URL getResource(String path) throws MalformedURLException
    {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getResourceAsStream(java.lang.String)
     */
    @Override
	public InputStream getResourceAsStream(String path)
    {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
     */
    @Override
	public RequestDispatcher getRequestDispatcher(String path)
    {
        if (!path.startsWith("/"))
        {
            throw new IllegalArgumentException("RequestDispatcher path at ServletContext level must start with '/'");
        }
        return new FakeRequestDispatcher(path);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
     */
    @Override
	public RequestDispatcher getNamedDispatcher(String path)
    {
        throw new UnsupportedOperationException("getNamedDispatcher");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getServlet(java.lang.String)
     */
    @Override
	public Servlet getServlet(String name)
    {
        throw new UnsupportedOperationException("getServlet");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getServlets()
     */
    @Override
	public Enumeration getServlets()
    {
        throw new UnsupportedOperationException("getServlets");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getServletNames()
     */
    @Override
	public Enumeration getServletNames()
    {
        throw new UnsupportedOperationException("getServletNames");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#log(java.lang.String)
     */
    @Override
	public void log(String message)
    {
        log.info(message);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#log(java.lang.Exception, java.lang.String)
     */
    @Override
	public void log(Exception ex, String message)
    {
        log.warn(message, ex);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#log(java.lang.String, java.lang.Throwable)
     */
    @Override
	public void log(String message, Throwable ex)
    {
        log.warn(message, ex);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getRealPath(java.lang.String)
     */
    @Override
	public String getRealPath(String path)
    {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getServerInfo()
     */
    @Override
	public String getServerInfo()
    {
        return "FakeServletContext";
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getInitParameter(java.lang.String)
     */
    @Override
	public String getInitParameter(String name)
    {
        return initParameters.getProperty(name);
    }

    /**
     * Add an init parameter to the list that we hand out
     * @param name The name of the new init parameter
     * @param value The value of the new init parameter
     */
    public void addInitParameter(String name, String value)
    {
        initParameters.setProperty(name, value);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getInitParameterNames()
     */
    @Override
	public Enumeration getInitParameterNames()
    {
        return initParameters.keys();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getAttribute(java.lang.String)
     */
    @Override
	public Object getAttribute(String name)
    {
        return attributes.get(name);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getAttributeNames()
     */
    @Override
	public Enumeration getAttributeNames()
    {
        return Collections.enumeration(attributes.keySet());
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#setAttribute(java.lang.String, java.lang.Object)
     */
    @Override
	public void setAttribute(String name, Object value)
    {
        if (value != null)
        {
            attributes.put(name, value);
        }
        else
        {
            attributes.remove(name);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#removeAttribute(java.lang.String)
     */
    @Override
	public void removeAttribute(String name)
    {
        attributes.remove(name);
    }

    /**
     * Accessor for the servlet context name. Normally read-only, but read
     * write in this fake context
     * @param servletContextName The new servlet context name
     */
    public void setServletContextName(String servletContextName)
    {
        this.servletContextName = servletContextName;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContext#getServletContextName()
     */
    @Override
	public String getServletContextName()
    {
        return servletContextName;
    }

    /**
     * See Servlet API version 2.5
     * @return The context path for this servlet
     */
    @Override
	public String getContextPath()
    {
        throw new UnsupportedOperationException("getContextPath");
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(FakeServletContext.class);

    /**
     *
     */
    private final String resourceBasePath;

    /**
     * The init parameters to this servlet
     */
    private final Properties initParameters = new Properties();

    /**
     * The servlet level attributes
     */
    private final Map attributes = new HashMap();

    /**
     * The servlet context name
     */
    private String servletContextName = "FakeServletContext";

    
    @Override
	public Dynamic addFilter(String arg0, String arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public Dynamic addFilter(String arg0, Filter arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public void addListener(String arg0) {
        // TODO Auto-generated method stub

    }

    
    @Override
	public <T extends EventListener> void addListener(T arg0) {
        // TODO Auto-generated method stub

    }

    
    @Override
	public void addListener(Class<? extends EventListener> arg0) {
        // TODO Auto-generated method stub

    }

    
    @Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
                                                                String arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
                                                                Servlet arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
                                                                Class<? extends Servlet> arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public <T extends Filter> T createFilter(Class<T> arg0)
            throws ServletException {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public <T extends EventListener> T createListener(Class<T> arg0)
            throws ServletException {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public <T extends Servlet> T createServlet(Class<T> arg0)
            throws ServletException {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public void declareRoles(String... arg0) {
        // TODO Auto-generated method stub

    }

    
    @Override
	public ClassLoader getClassLoader() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public int getEffectiveMajorVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    
    @Override
	public int getEffectiveMinorVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    
    @Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public FilterRegistration getFilterRegistration(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public JspConfigDescriptor getJspConfigDescriptor() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public ServletRegistration getServletRegistration(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public SessionCookieConfig getSessionCookieConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
	public boolean setInitParameter(String arg0, String arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    
    @Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) {
        // TODO Auto-generated method stub

    }
}

