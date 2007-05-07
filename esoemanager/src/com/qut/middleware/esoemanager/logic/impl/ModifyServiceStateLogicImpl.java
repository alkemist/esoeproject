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
 * Purpose: Modify Service state logic default implementation
 */
package com.qut.middleware.esoemanager.logic.impl;

import org.apache.log4j.Logger;

import com.qut.middleware.esoemanager.Constants;
import com.qut.middleware.esoemanager.exception.ModifyServiceStateException;
import com.qut.middleware.esoemanager.exception.SPEPDAOException;
import com.qut.middleware.esoemanager.logic.ModifyServiceStateLogic;
import com.qut.middleware.esoemanager.spep.sqlmap.SPEPDAO;

public class ModifyServiceStateLogicImpl implements ModifyServiceStateLogic
{
	private SPEPDAO spepDAO;

	/* Local logging instance */
	private Logger logger = Logger.getLogger(ViewServiceLogicImpl.class.getName());

	public ModifyServiceStateLogicImpl(SPEPDAO spepDAO)
	{
		if (spepDAO == null)
		{
			this.logger.error("spepDAO for ModifyServiceStateLogicImpl was NULL");
			throw new IllegalArgumentException("spepDAO for ModifyServiceStateLogicImpl was NULL");
		}

		this.spepDAO = spepDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.qut.middleware.esoemanager.logic.impl.ModifyServiceStateLogic#setActive(java.lang.String)
	 */
	public void setActive(String entityID) throws ModifyServiceStateException
	{
		try
		{
			this.spepDAO.updateServiceActiveState(entityID, Constants.SERVICE_ACTIVE);
		}
		catch (SPEPDAOException e)
		{
			this.logger.error("SPEPDAOException thrown when setting service as active " + e.getLocalizedMessage());
			throw new ModifyServiceStateException("SPEPDAOException thrown when setting service as active " + e.getLocalizedMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.qut.middleware.esoemanager.logic.impl.ModifyServiceStateLogic#setInActive(java.lang.String)
	 */
	public void setInActive(String entityID) throws ModifyServiceStateException
	{
		try
		{
			this.spepDAO.updateServiceActiveState(entityID, Constants.SERVICE_INACTIVE);
		}
		catch (SPEPDAOException e)
		{
			this.logger.error("SPEPDAOException thrown when setting service as inactive " + e.getLocalizedMessage());
			throw new ModifyServiceStateException("SPEPDAOException thrown when setting service as inactive " + e.getLocalizedMessage(), e);
		}
	}
}
