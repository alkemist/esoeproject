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
 */
package com.qut.middleware.esoestartup.pages;

import com.qut.middleware.esoemanager.pages.BorderPage;
import com.qut.middleware.esoestartup.bean.ESOEBean;

public class RegisterESOECompleteDeploymentPage extends BorderPage
{
	public ESOEBean esoeBean;

	public void onInit()
	{
		this.esoeBean = (ESOEBean) this.retrieveSession(PageConstants.STORED_ESOE_COMPLETED_DETAILS);
	}

	@Override
	public void onGet()
	{
		/* Check if previous registration stage completed */
		Boolean status = (Boolean) this.retrieveSession(PageConstants.STAGE8_RES);
		if (status == null || status.booleanValue() != true)
		{
			previousClick();
		}

		if (this.esoeBean == null)
		{
			//TODO Error page
		}
	}

	public boolean previousClick()
	{
		/* Move client to register service page */
		String path = getContext().getPagePath(RegisterESOECryptoPage.class);
		setRedirect(path);

		return false;
	}
}
