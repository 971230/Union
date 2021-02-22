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
 * $Id: NoSuchServiceException.java,v 1.1 2006/01/07 03:26:39 mechiland Exp $
 */ 
package net.buffalo.service;

/**
 * Can not find the specified service.
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha1
 */
public class NoSuchServiceException extends RuntimeException {

	private static final long serialVersionUID = 664802552465490277L;

	public NoSuchServiceException() {
		super();
	}

	public NoSuchServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchServiceException(String message) {
		super(message);
	}

	public NoSuchServiceException(Throwable cause) {
		super(cause);
	}
	
}
