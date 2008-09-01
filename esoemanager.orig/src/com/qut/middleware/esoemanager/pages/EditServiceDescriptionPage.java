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
package com.qut.middleware.esoemanager.pages;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.click.control.Submit;

import com.qut.middleware.esoemanager.bean.ServiceBean;
import com.qut.middleware.esoemanager.bean.impl.ServiceBeanImpl;
import com.qut.middleware.esoemanager.exception.EditServiceDetailsException;
import com.qut.middleware.esoemanager.logic.EditServiceDescriptionLogic;
import com.qut.middleware.esoemanager.pages.forms.impl.ServiceForm;

public class EditServiceDescriptionPage extends BorderPage
{
	EditServiceDescriptionLogic logic;

	/* Service Details */
	public ServiceForm serviceDetails;
	public String eid;
	public String action;

	public EditServiceDescriptionPage()
	{
		this.serviceDetails = new ServiceForm();
	}

	@Override
	public void onInit()
	{
		this.serviceDetails.init();
		
		/* Don't support currently modification to entityID */
		this.serviceDetails.remove(this.serviceDetails.getField(PageConstants.SERVICE_IDENTIFIER));

		Submit completeButton = new Submit(PageConstants.NAV_COMPLETE_LABEL, this, PageConstants.NAV_COMPLETE_FUNC);
		this.serviceDetails.add(completeButton);
	}

	public EditServiceDescriptionLogic getEditServiceDescriptionLogic()
	{
		return this.logic;
	}

	public void setEditServiceDescriptionLogic(EditServiceDescriptionLogic logic)
	{
		this.logic = logic;
	}

	@Override
	public void onGet()
	{
		ServiceBean serviceDetailsBean;

		if (eid != null)
		{
			try
			{
				serviceDetailsBean = this.logic.getServiceDetails(new Integer(this.eid));	
				this.serviceDetails.getField(PageConstants.EID).setValue(eid);
				this.serviceDetails.getField(PageConstants.SERVICE_NAME).setValue(serviceDetailsBean.getServiceName());
				this.serviceDetails.getField(PageConstants.SERVICE_URL).setValue(serviceDetailsBean.getServiceURL());
				this.serviceDetails.getField(PageConstants.SERVICE_DESCRIPTION).setValue(serviceDetailsBean.getServiceDescription());
				this.serviceDetails.getField(PageConstants.SERVICE_AUTHZ_FAILURE_MESSAGE).setValue(serviceDetailsBean.getServiceAuthzFailureMsg());
			}
			catch (EditServiceDetailsException e)
			{
				setError();
			}
		}
		else
		{
			setError();
		}
	}

	public boolean completeClick()
	{
		ServiceBean serviceDetailsBean = new ServiceBeanImpl();

		if (this.serviceDetails.isValid())
		{
			try
			{
				URL validHost = new URL(this.serviceDetails.getFieldValue(PageConstants.SERVICE_URL));

				serviceDetailsBean.setServiceName(this.serviceDetails.getField(PageConstants.SERVICE_NAME).getValue());
				serviceDetailsBean.setEntityID(this.serviceDetails.getFieldValue(PageConstants.SERVICE_IDENTIFIER));
				serviceDetailsBean.setServiceURL(this.serviceDetails.getField(PageConstants.SERVICE_URL).getValue());
				serviceDetailsBean.setServiceDescription(this.serviceDetails.getField(PageConstants.SERVICE_DESCRIPTION).getValue());
				serviceDetailsBean.setServiceAuthzFailureMsg(this.serviceDetails.getField(PageConstants.SERVICE_AUTHZ_FAILURE_MESSAGE).getValue());

				this.logic.updateServiceDetails(new Integer(this.serviceDetails.getField(PageConstants.EID).getValue()), serviceDetailsBean);

				this.action = PageConstants.COMPLETED;
				return false;
			}
			catch (MalformedURLException e)
			{
				// TODO Log4j here
				this.serviceDetails.getField(PageConstants.SERVICE_URL).setError("Malformed server address submitted");
				return true;
			}
			catch (EditServiceDetailsException e)
			{
				// TODO Log4j here
				setError();
			}
		}

		return true;
	}

	private void setError()
	{
		/* Move users to error view */
		this.action = PageConstants.ERROR;
	}
}