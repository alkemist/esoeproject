/* 
 * Copyright 2006, Queensland University of Technology
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy of 
 * the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations under 
 * the License.
 * 
 * Author: Bradley Beddoes
 * Creation Date: 23/10/2006
 * 
 * Purpose: Exception thrown when a signature validation exception has occured
 */
package com.qut.middleware.saml2.exception;

/** Exception thrown when a signature validation exception has occured.  */
public class SignatureValueException extends Exception
{
	private static final long serialVersionUID = 4412781289512424446L;

	/**
	 * Exception thrown when a signature validation exception has occured.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 * @param cause Any exception which caused this exception to be thrown, may be null
	 */
	public SignatureValueException(String message, Exception cause)
	{
		super(message, cause);
	}
	
	/**
	 * Exception thrown when a signature validation exception has occured.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 */
	public SignatureValueException(String message)
	{
		super(message);
	}
}
