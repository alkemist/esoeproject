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
 * Author:
 * Creation Date:
 * 
 * Purpose:
 */
package com.qut.middleware.delegator.openid.pages.forms.impl;

import net.sf.click.control.Form;
import net.sf.click.control.Submit;

import com.qut.middleware.delegator.openid.ConfigurationConstants;
import com.qut.middleware.delegator.openid.pages.forms.BaseForm;

public class AcceptForm extends Form implements BaseForm
{

	/* (non-Javadoc)
	 * @see com.qut.middleware.delegator.pages.forms.BaseForm#init()
	 */
	public void init()
	{
		/* Setup contact details form */
		this.setColumns(3);
		this.setValidate(true);
		this.setErrorsPosition(Form.POSITION_TOP);
		this.setAttribute("action", "/openiddelegator/login");
		
		Submit submitAcceptance = new Submit(ConfigurationConstants.ACCEPTED_POLICY, ConfigurationConstants.ACCEPTED_POLICY);
		Submit submitNonAcceptance = new Submit(ConfigurationConstants.DENIED_POLICY, ConfigurationConstants.DENIED_POLICY);
		
		this.add(submitAcceptance);
	}

}
