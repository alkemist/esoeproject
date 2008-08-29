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
 * Creation Date: 1/5/07
 * 
 * Purpose: Allows client t configure LXACML policies for each service they are an dministrator for
 */
package com.qut.middleware.esoemanager.logic;

import com.qut.middleware.esoemanager.bean.AuthorizationPolicyBean;
import com.qut.middleware.esoemanager.exception.PolicyGuardException;
import com.qut.middleware.esoemanager.exception.ServiceAuthorizationPolicyException;

public interface ConfigureServiceAuthorizationPolicyLogic
{

	public AuthorizationPolicyBean getActiveServiceAuthorizationPolicy(Integer descID)
			throws ServiceAuthorizationPolicyException;

	public void updateServiceAuthorizationPolicy(Integer entID, byte[] policy)
			throws PolicyGuardException, ServiceAuthorizationPolicyException;

}