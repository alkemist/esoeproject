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
 * Author: Andre Zitelli
 * Creation Date: 14/12/2006
 * 
 * Purpose: Thread safe implementation of FailedLogoutRepository.
 */
package com.qut.middleware.esoe.logout.bean.impl;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.qut.middleware.esoe.logout.bean.FailedLogout;
import com.qut.middleware.esoe.logout.bean.FailedLogoutRepository;

/** Thread safe implementation of FailedLogoutRepository. */

public class FailedLogoutRepositoryImpl implements FailedLogoutRepository {

	private List<FailedLogout> failures;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
  
    /** Initilaizes the internal failure repository to a 0 sized list.
     * 
     * Default constructor
     */
    public FailedLogoutRepositoryImpl()
    {
    	// using vector for synchronized behaviour
    	this.failures = new Vector<FailedLogout>();
    }
    
    
    /* Add the Logout failure object to the repository. Null parameters are ignored. Failure objects added
	 * MUST have all fields populated so that compare and removal operations are peroformed correctly.
	 * If all fields are not populated an exception is thrown. See below.
	 * 
	 * 
	 * @see com.qut.middleware.esoe.logout.bean.FailedLogoutRepository#add(com.qut.middleware.esoe.logout.bean.FailedLogout)
	 * @throws IllegalArgumentException if the object that is added does not contain necessary fields.
	 */
	public void add(FailedLogout failure)
	{
		if(failure != null)
		{
			if(failure.getEndPoint() == null || failure.getRequestDocument() == null || failure.getTimeStamp() == null || failure.getAuthnId() == null)
				throw new IllegalArgumentException("Attempted to add an invalid object to repository. Please check that all fields are populated.");
				
			this.rwl.writeLock().lock();
			
			try
			{
				this.failures.add(failure);
			}
			finally
			{
				this.rwl.writeLock().unlock();
			}
		}		
	}

	
	/* (non-Javadoc)
	 * @see com.qut.middleware.esoe.authz.cache.bean.FailedLogoutRepository#clearFailures()
	 */
	public void clearFailures()
	{
		this.rwl.writeLock().lock();
		
		try
		{
			this.failures.clear();
		}
		finally
		{
			this.rwl.writeLock().unlock();
		}
	}

	
	/* (non-Javadoc)
	 * @see com.qut.middleware.esoe.authz.cache.bean.FailedLogoutRepository#getFailures()
	 * 
	 * NOTE: This method returns a clone of the unerlying list. Modifications to the list of failures should be
	 * performed via add() and remove().
	 */
	public List<FailedLogout> getFailures()
	{
		this.rwl.readLock().lock();
		
		try
		{
			Vector<FailedLogout> clonedList = new Vector<FailedLogout>();
			
			clonedList.addAll(this.failures);
			
			return (Vector<FailedLogout>)clonedList.clone();
		}
		finally
		{
			this.rwl.readLock().unlock();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.qut.middleware.esoe.authz.cache.bean.FailedLogoutRepository#remove(com.qut.middleware.esoe.authz.cache.bean.FailedLogout)
	 */
	public void remove(FailedLogout failure)
	{
		this.rwl.writeLock().lock();
		
		try
		{
			this.failures.remove(failure);
		}
		finally
		{
			this.rwl.writeLock().unlock();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.qut.middleware.esoe.authz.cache.bean.FailedLogoutRepository#getSize()
	 */
	public int getSize()
	{
		this.rwl.readLock().lock();
		
		try
		{
			return(this.failures == null ? 0 : this.failures.size() );
		}
		finally
		{
			this.rwl.readLock().unlock();
		}
	}


	/*
	 * (non-Javadoc)
	 * @see com.qut.middleware.esoe.sso.bean.FailedLogoutRepository#containsFailure(com.qut.middleware.esoe.sso.bean.FailedLogout)
	 */
	public boolean containsFailure(FailedLogout failure)
	{
		this.rwl.readLock().lock();
		
		try
		{
			return (failure != null && this.failures.contains(failure) ? true : false);
		}
		finally
		{
			this.rwl.readLock().unlock();
		}
	}

	
}
