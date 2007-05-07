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
 * Creation Date: 27/04/2007
 * 
 * Purpose: Interface for policy guard feature of esoe manager webapp, controls acceptance of new policies into the system
 */
package com.qut.middleware.esoemanager.guard;

import com.qut.middleware.esoemanager.exception.PolicyGuardException;

public interface PolicyGuard
{
	public void validatePolicy(String submittedPolicy) throws PolicyGuardException;
}
