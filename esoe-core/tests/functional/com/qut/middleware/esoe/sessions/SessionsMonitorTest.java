package com.qut.middleware.esoe.sessions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.qut.middleware.esoe.sessions.bean.impl.IdentityDataImpl;
import com.qut.middleware.esoe.sessions.cache.SessionCache;
import com.qut.middleware.esoe.sessions.cache.impl.SessionCacheImpl;
import com.qut.middleware.esoe.sessions.exception.DuplicateSessionException;
import com.qut.middleware.esoe.sessions.impl.PrincipalImpl;
import com.qut.middleware.esoe.sessions.impl.SessionsMonitor;
import com.qut.middleware.saml2.identifier.IdentifierCache;
import com.qut.middleware.saml2.identifier.impl.IdentifierCacheImpl;
@SuppressWarnings("nls")
public class SessionsMonitorTest 
{
	
	private SessionsMonitor monitor;
	private IdentifierCache idcache;
	private SessionCache sessioncache;
	private int interval;
	private int timeout;
	
	@SuppressWarnings("unqualified-field-access")
	@Before
	public void setUp() throws Exception 
	{
		this.idcache = new IdentifierCacheImpl();
		this.sessioncache = new SessionCacheImpl();
		this.interval = 2;
		this.timeout = 5;

		this.monitor = new SessionsMonitor(idcache, sessioncache, interval, timeout);		
	}

	@Test
	public void testRun() 
	{
		assertTrue(this.monitor.isAlive());
	}

	@Test
	public void testSessionsMonitor() throws Exception
	{
		assertTrue(this.monitor.isAlive());
		
		// Add a few principals to test behaviour
		Principal data = new PrincipalImpl(new IdentityDataImpl(), 360);
		data.setPrincipalAuthnIdentifier("testuser-1");
		data.setSessionID("somerandomsessionID1");
		
		Principal data2 = new PrincipalImpl(new IdentityDataImpl(), 360);
		data2.setPrincipalAuthnIdentifier("testuser-2");
		data2.setSessionID("somerandomsessionID2");
		
		try
		{
			this.sessioncache.addSession(data);
			this.sessioncache.addSession(data2);
			
			// sleep while it polls
			Thread.sleep(4000);
			
			// gettting session data will refresh it's last accessed timestamp, so this one should not be removed.
			this.sessioncache.getSession("somerandomsessionID2");
			
			// sleep while it polls
			Thread.sleep(4000);
			
			// ensure the first record has been removed as its now expired
			assertEquals(null, this.sessioncache.getSession("somerandomsessionID1"));
			
			// ensure the second record still exists as it has been accessed and therefore not expired
			assertEquals(data2, this.sessioncache.getSession("somerandomsessionID2"));
	
		}
		catch(DuplicateSessionException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testShutdown() throws Exception
	{
		Thread.sleep(5000);
		
		assertTrue(this.monitor.isAlive());
		
		this.monitor.shutdown();
		
		Thread.sleep(5000);
		
		assertTrue("Monitor Thread should be dead.", !this.monitor.isAlive());
	}
	
}
