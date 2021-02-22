/*
 * Copyright 2002-2004 the original author or authors.
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
 *
 * $Id: SqlDateConverter.java,v 1.1 2006/10/01 15:03:43 mechiland Exp $
 */ 
package net.buffalo.protocal.converters;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.map.AbstractMapConverter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public class SqlDateConverter extends AbstractMapConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		if (type == null) {
			return false;
		}
		
		return java.sql.Date.class.equals(type);
	}
	
	@Override
	public Object unmarshal(StreamReader reader,
			UnmarshallingContext unmarshallingContext) {
		throw new UnsupportedOperationException("done in the MapConverter");
	}
	
	@Override
	protected void marshalMapObject(Object value, MarshallingContext context, StreamWriter streamWriter) {
		java.sql.Date date = (java.sql.Date) value;
		streamWriter.startNode(ProtocalTag.TAG_STRING);
		streamWriter.setValue("value");
		streamWriter.endNode();
		context.convertAnother(new java.util.Date(date.getTime()));
	}

}
