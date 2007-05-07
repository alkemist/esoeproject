/* Copyright 2006, Queensland University of Technology
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
 * Author: Shaun Mangelsdorf
 * Creation Date: 28/09/2006
 * 
 * Purpose: Indicates that the request made was invalid and prevented processing from succeeding
 */
package com.qut.middleware.esoe.spep.exception;

/** */
public class InvalidRequestException extends Exception
{
	private static final long serialVersionUID = 856785946252251464L;

	/**
	 * @param message The message identifying the cause of the exception
	 */
	public InvalidRequestException(String message)
	{
		super(message);
	}
	
	/**
	 * @param cause The exception that caused this exception to be thrown
	 */
	public InvalidRequestException(Exception cause)
	{
		super(cause);
	}
	
	/**
	 * @param message The message identifying the cause of the exception
	 * @param cause The exception that caused this exception to be thrown
	 */
	public InvalidRequestException(String message, Exception cause)
	{
		super(message,cause);
	}
}
