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
 * Creation Date: 30/08/2007
 * 
 * Purpose: Thrown when an error occurs within SessionsDAO
 */
package com.qut.middleware.esoe.sessions.exception;

public class SessionsDAOException extends Exception
{

	private static final long serialVersionUID = -7822758017898531526L;

	public SessionsDAOException(String message)
	{
		super(message);
	}
	
	public SessionsDAOException(String message, Exception cause)
	{
		super(message, cause);
	}
	
	public SessionsDAOException(Exception cause)
	{
		super(cause);
	}
}
