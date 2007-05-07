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
 * Author: Andre Zitelli
 * Creation Date: 18/12/2006
 * 
 */
/**
 * Purpose: A bean to store the state of LogoutRequests associated with a particular SPEP.
 */

package com.qut.middleware.esoe.sso.bean;

public interface SSOLogoutState
{
	/** Set the state of the LogoutRequest.
	 * 
	 * @param state True for Successfull, false otherwise.
	 */
	public void setLogoutState(boolean state);
	
	/** Get the state of the LogoutRequest.
	*/	
	public boolean getLogoutState();
	
	/** Set a description of the logout state of the SPEP.
	 * 
	 * @param state A string describing the state.
	 */
	public void setLogoutStateDescription(String state);
	
	/** Get a description of the logout state of the SPEP.
	*/
	public String getLogoutStateDescription();
	
	/** Set the URL of the associated SPEP.
	 * 
	 * @param url String representation of the SPEPs URL.
	 */
	public void setSPEPURL(String url);
	
	/** Get the URL of the associated SPEP.
	*/
	public String getSPEPURL();
	
}