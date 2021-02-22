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
package org.directwebremoting.convert;

import org.directwebremoting.dwrp.SimpleOutboundVariable;
import org.directwebremoting.extend.*;
import org.directwebremoting.util.JavascriptUtil;
import org.directwebremoting.util.LocalUtil;
import org.directwebremoting.util.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * An implementation of Converter for Strings.
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id: URLConverter.java,v 1.4 2006/10/08 09:22:31 joe_walker Exp $
 */
public class URLConverter extends BaseV20Converter implements Converter
{
    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     */
    @Override
	public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
    {
        String urlString = LocalUtil.decode(iv.getValue());
        try
        {
            return new URL(urlString);
        }
        catch (MalformedURLException ex)
        {
            log.warn("Failed to create URL from string '" + urlString + "'. Returning null");
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     */
    @Override
	public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
    {
        URL url = (URL) data;
        String escaped = JavascriptUtil.escapeJavaScript(url.toExternalForm());
        return new SimpleOutboundVariable('\"' + escaped + '\"', outctx, true);
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(URLConverter.class);
}
