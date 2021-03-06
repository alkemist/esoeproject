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
 * Author: Shaun Mangelsdorf
 * Creation Date: 11/10/2006
 *
 * Purpose: Tests the sessions processor object.
 */
package com.qut.middleware.esoe.sessions;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qut.middleware.esoe.authn.bean.AuthnIdentityAttribute;
import com.qut.middleware.esoe.authn.bean.impl.AuthnIdentityAttributeImpl;
import com.qut.middleware.esoe.logout.LogoutThreadPool;
import com.qut.middleware.esoe.sessions.bean.IdentityAttribute;
import com.qut.middleware.esoe.sessions.bean.IdentityData;
import com.qut.middleware.esoe.sessions.bean.SessionConfigData;
import com.qut.middleware.esoe.sessions.bean.impl.IdentityAttributeImpl;
import com.qut.middleware.esoe.sessions.bean.impl.IdentityDataImpl;
import com.qut.middleware.esoe.sessions.bean.impl.SessionConfigDataImpl;
import com.qut.middleware.esoe.sessions.cache.SessionCache;
import com.qut.middleware.esoe.sessions.cache.impl.SessionCacheImpl;
import com.qut.middleware.esoe.sessions.exception.DataSourceException;
import com.qut.middleware.esoe.sessions.exception.DuplicateSessionException;
import com.qut.middleware.esoe.sessions.exception.InvalidDescriptorIdentifierException;
import com.qut.middleware.esoe.sessions.exception.InvalidSessionIdentifierException;
import com.qut.middleware.esoe.sessions.exception.SessionCacheUpdateException;
import com.qut.middleware.esoe.sessions.identity.IdentityResolver;
import com.qut.middleware.esoe.sessions.identity.impl.IdentityResolverImpl;
import com.qut.middleware.esoe.sessions.identity.pipeline.Handler;
import com.qut.middleware.esoe.sessions.identity.pipeline.impl.NullHandlerImpl;
import com.qut.middleware.esoe.sessions.impl.CreateImpl;
import com.qut.middleware.esoe.sessions.impl.PrincipalImpl;
import com.qut.middleware.esoe.sessions.impl.QueryImpl;
import com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl;
import com.qut.middleware.esoe.sessions.impl.TerminateImpl;
import com.qut.middleware.esoe.sessions.impl.UpdateImpl;
import com.qut.middleware.esoe.sessions.sqlmap.SessionsDAO;
import com.qut.middleware.metadata.processor.MetadataProcessor;
import com.qut.middleware.saml2.AuthenticationContextConstants;
import com.qut.middleware.saml2.identifier.IdentifierCache;
import com.qut.middleware.saml2.identifier.IdentifierGenerator;
import com.qut.middleware.saml2.identifier.impl.IdentifierCacheImpl;
import com.qut.middleware.saml2.identifier.impl.IdentifierGeneratorImpl;
import com.qut.middleware.saml2.schemas.assertion.AttributeType;
import com.qut.middleware.saml2.schemas.esoe.sessions.DataType;

/** */
@SuppressWarnings("nls")
public class SessionsProcessorTest
{

	private SessionCache sessionCache;
	private SessionConfigData sessionConfigData;
	private Create create;
	private Query query;
	private Terminate terminate;
	private Update update;
	private SessionsProcessor processor;
	private IdentityResolver identityResolver;
	private IdentifierGenerator identiferGenerator;
	private IdentifierCache identifierCache;
	private SessionsDAO sessionsDAO;
	private MetadataProcessor metadata;
	private LogoutThreadPool logout;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		String entityID = "http://test.service.com";
		Integer entID = new Integer("1");
		
		List<String> endpoints = new ArrayList<String>();
		endpoints.add(entityID);
		
		File config = new File(this.getClass().getResource("sessionDataNoAction.xml").toURI());
		
		logout = createMock(LogoutThreadPool.class);
		//expect(logout.getEndPoints(entityID)).andReturn(endpoints);
		//expect(logout.performSingleLogout((String)notNull(), (List<String>)notNull(), eq(entityID), anyBoolean())).andReturn(LogoutThreadPool.result.LogoutSuccessful).anyTimes();
		replay(logout);
		
		this.sessionCache = new SessionCacheImpl(logout);
		
		File attributePolicy = new File("tests" + File.separatorChar + "testdata" + File.separatorChar + "ReleasedAttributes.xml");
		FileInputStream attributeStream = new FileInputStream(attributePolicy);
		byte[] attributeData = new byte[(int)attributePolicy.length()];
		attributeStream.read(attributeData);
		
		this.metadata = createMock(MetadataProcessor.class);
		
		this.sessionsDAO = createMock(SessionsDAO.class);
		expect(sessionsDAO.getEntID(entityID)).andReturn(entID);
		expect(sessionsDAO.selectActiveAttributePolicy(entID)).andReturn(attributeData);
		
		replay(this.metadata);
		replay(this.sessionsDAO);

		this.sessionConfigData = new SessionConfigDataImpl(sessionsDAO, metadata, entityID);

		Handler handler = new NullHandlerImpl();

		this.identityResolver = new IdentityResolverImpl(new Vector<Handler>(0,1));
		this.identityResolver.registerHandler(handler);
		this.identifierCache = new IdentifierCacheImpl();
		this.identiferGenerator = new IdentifierGeneratorImpl(this.identifierCache);

		this.create = new CreateImpl(this.sessionCache, this.sessionConfigData, this.identityResolver, this.identiferGenerator, 360);

		// first test construction with invalid params
		try
		{
			this.query = new QueryImpl(null);
		}
		catch(IllegalArgumentException e)
		{
			// good
		}
		this.query = new QueryImpl(this.sessionCache);
		
		// first test construction with invalid params
		try
		{
			this.terminate = new TerminateImpl(null);
		}
		catch(IllegalArgumentException e)
		{
			// good
		}
		this.terminate = new TerminateImpl(this.sessionCache);
		
		// first test construction with invalid params
		try
		{
			this.update = new UpdateImpl(null);
		}
		catch(IllegalArgumentException e)
		{
			// good
		}
		this.update = new UpdateImpl(this.sessionCache);

		this.processor = new SessionsProcessorImpl(this.create, this.query, this.terminate, this.update);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		verify(sessionsDAO);
		verify(metadata);
		verify(logout);
	}


	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getCreate()}.
	 */
	@Test
	public final void testCreate1()
	{
		String sessionID = "587643987562983476598236597823456";
		String principalName = "hguoiqwnpehtmp";
		assertSame("Create was expected to return the same object.", this.create, this.processor.getCreate());

		// Just in case...
		try {
			this.sessionCache.removeSession(sessionID);
		} catch (SessionCacheUpdateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail("Failed to remove session");
		}

		/*
		 * Test case: Creating a session normally.
		 */
		try
		{
			this.create.createLocalSession(sessionID, principalName, AuthenticationContextConstants.unspecified, new Vector<AuthnIdentityAttribute>());
			
			assertTrue(this.query.validAuthnSession(sessionID));
			
			assertTrue(!this.query.validAuthnSession("some random id"));
						
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
			return;
		}
	
		Principal principal = this.sessionCache.getSession(sessionID);

		assertEquals("Session ID mismatch.", sessionID, principal.getSessionID());
		assertEquals("Principal mismatch.", principalName, principal.getPrincipalAuthnIdentifier());
		assertEquals("Authentication context class mismatch.", AuthenticationContextConstants.unspecified, principal.getAuthenticationContextClass());
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getCreate()}.
	 */
	@Test
	public final void testCreate2()
	{
		String sessionID = "587643987562983476598236597823456";
		String principalName = "hguoiqwnpehtmp";
		assertSame("Create was expected to return the same object.", this.create, this.processor.getCreate());

		boolean caught = false;

		try
		{
			this.create.createLocalSession(sessionID, principalName, AuthenticationContextConstants.unspecified, new Vector<AuthnIdentityAttribute>());
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
			return;
		}

		/*
		 * Test case: Creating a session that already exists.
		 */
		try
		{
			this.create.createLocalSession(sessionID, principalName, AuthenticationContextConstants.unspecified, new Vector<AuthnIdentityAttribute>());
	
			fail("Expected exception not thrown");
		}
		catch (SessionCacheUpdateException ex)
		{			
			return;
		}
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getCreate()}.
	 */
	@Test
	public final void testCreate3()
	{
		String principalName = "hguoiqwnpehtmp";
		assertSame("Create was expected to return the same object.", this.create, this.processor.getCreate());

		boolean caught = false;
		/*
		 * Test case: Creating a session with an empty sessionID
		 */
		try
		{
			this.create.createLocalSession("", principalName, AuthenticationContextConstants.unspecified, new Vector<AuthnIdentityAttribute>());
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}
		catch (IllegalArgumentException ex)
		{
			caught = true;
		}

		assertTrue("Empty session ID did not trigger error condition.", caught);

	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getCreate()}.
	 */
	@Test
	public final void testCreate4()
	{
		String sessionID = "587643987562983476598236597823456";
		assertSame("Create was expected to return the same object.", this.create, this.processor.getCreate());

		boolean caught = false;
		/*
		 * Test case: Creating a session with an empty principal name.
		 */
		try
		{
			this.create.createLocalSession(sessionID, "", AuthenticationContextConstants.unspecified, new Vector<AuthnIdentityAttribute>());
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}
		catch (IllegalArgumentException ex)
		{
			caught = true;
		}

		assertTrue("Empty principal did not trigger error condition.", caught);
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getCreate()}.
	 */
	@Test
	public final void testCreate5()
	{
		String sessionID = "587643987562983476598236597823456";
		String principalName = "hguoiqwnpehtmp";

		// Create a 'Create' without creating a handler
		Create brokenCreate = new CreateImpl(this.sessionCache, this.sessionConfigData, new IdentityResolverImpl(new Vector<Handler>(0,1)), this.identiferGenerator, 360);

		boolean caught = false;
		/*
		 * Test case: Generating a HandlerRegistrationException
		 */
		try
		{
			brokenCreate.createLocalSession(sessionID, principalName, AuthenticationContextConstants.unspecified, new Vector());

			fail("Expected exception not thrown.");
		}
		catch (SessionCacheUpdateException ex)
		{
			// this should be thrown because handler is screwed
		}
	}
	
	/**
	 * Tests to ensure successful generation of principal from a remote source
	 */
	@Test
	public final void testCreate6()
	{
		String sessionID = "12345-12345";
		String principalAuthnIdentifier = "beddoes-openID";
		List<AttributeType> principalAttributes = new ArrayList<AttributeType>();
		AttributeType attrib = new AttributeType();
		attrib.setName("mail");
		attrib.getAttributeValues().add("beddoes@openid.net");
		attrib.getAttributeValues().add("beddoes@mycoolhost.net");
		
		AttributeType attrib2 = new AttributeType();
		attrib2.setName("sn");
		attrib2.getAttributeValues().add("beddoes");
		
		principalAttributes.add(attrib);
		principalAttributes.add(attrib2);
		
		// Create a 'Create' without creating a handler
		Create remoteCreate = new CreateImpl(this.sessionCache, this.sessionConfigData, new IdentityResolverImpl(new Vector<Handler>(0,1)), this.identiferGenerator, 360);
		try
		{
			create.createDelegatedSession(sessionID, principalAuthnIdentifier, AuthenticationContextConstants.unspecified, principalAttributes);
		}
		catch (Exception e)
		{
			fail("Exception shouldn't be thrown in this test " + e.getLocalizedMessage());
		}
		
		/* Ensure our principal was created correctly */
		Principal principal = this.sessionCache.getSession("12345-12345");
		
		assertTrue("Ensure principal has attrib", principal.getAttributes().containsKey("mail"));
		assertTrue("Ensure principal has attrib2", principal.getAttributes().containsKey("sn"));
		assertTrue("Ensure principal has correct authn identifier", principal.getPrincipalAuthnIdentifier().equals("beddoes-openID"));
		assertTrue("Ensure principal has values associated with mail", principal.getAttributes().get("mail").getValues().get(0).equals("beddoes@openid.net"));
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getQuery()}.
	 */
	@Test
	public final void testQuery1()
	{
		String sessionID = "98327598243687569876197619287";
		String samlID = "u984698y109458y098qy058y12029";
		String principal = "kajshtoiuqweh";
		assertSame("Query was expected to return the same object.", this.query, this.processor.getQuery());

		PrincipalImpl data = new PrincipalImpl();
		data.setPrincipalAuthnIdentifier(principal);
		data.setSAMLAuthnIdentifier(samlID);
		data.setSessionID(sessionID);
		data.setSessionNotOnOrAfter(System.currentTimeMillis() + 300000);
		
		try
		{
			// Just in case...
			this.sessionCache.removeSession(sessionID);

			this.sessionCache.addSession(data);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}

		Principal result =  this.query.queryAuthnSession(sessionID);

		assertSame("Query did not return the same session", data, result);

		if (result != null)
		{
			assertEquals("Returned session did not match session identifier.", sessionID, result.getSessionID());
		}
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getQuery()}.
	 */
	@Test
	public final void testQuery2()
	{
		String sessionID = "98327598243687569876197619287";
		String samlID = "u984698y109458y098qy058y12029";
		String principal = "kajshtoiuqweh";
		assertSame("Query was expected to return the same object.", this.query, this.processor.getQuery());

		PrincipalImpl data = new PrincipalImpl();
		data.setPrincipalAuthnIdentifier(principal);
		data.setSAMLAuthnIdentifier(samlID);
		data.setSessionID(sessionID);
		data.setSessionNotOnOrAfter(System.currentTimeMillis() + 300000);
		
		try
		{
			// Just in case...
			this.sessionCache.removeSession(sessionID);

			this.sessionCache.addSession(data);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}
		
		Principal result =  this.query.querySAMLSession(samlID);

		assertSame("Query did not return the same session", data, result);

		if (result != null)
		{
			assertEquals("Returned session did not match SAML identifier.", samlID, result.getSAMLAuthnIdentifier());
		}
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getQuery()}.
	 */
	@Test
	public final void testQuery3()
	{
		String sessionID = "98327598243687569876197619287";
		assertSame("Query was expected to return the same object.", this.query, this.processor.getQuery());

		Principal result =  this.query.queryAuthnSession(sessionID);

		assertNull("Object returned when no object should exist.", result);
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getQuery()}.
	 */
	@Test
	public final void testQuery4()
	{
		String samlID = "u984698y109458y098qy058y12029";
		assertSame("Query was expected to return the same object.", this.query, this.processor.getQuery());

		Principal result =  this.query.querySAMLSession(samlID);

		assertNull("Object returned by SAML ID when no object should exist.", result);
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getTerminate()}.
	 */
	@Test
	public final void testTerminate1()
	{
		String sessionID1 = "094867091820594817230984712";
		String principal = "jasldjfhakj";
		assertSame("Terminate was expected to return the same object.", this.terminate, this.processor.getTerminate());

		PrincipalImpl data = new PrincipalImpl();
		data.setPrincipalAuthnIdentifier(principal);
		data.setSessionID(sessionID1);
		data.setSAMLAuthnIdentifier("blee");
		
		/*
		 * Test case: Removing a session normally
		 */
		try
		{
			// Just in case...
			this.sessionCache.removeSession(sessionID1);

			this.sessionCache.addSession(data);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}

		if (this.sessionCache.getSession(sessionID1) == null)
		{
			fail("No session created.");
		}

		boolean error = false;
		/*
		 * Test case: Removing a session that has already been removed.
		 */
		try
		{
			this.terminate.terminateSession(sessionID1);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}

		assertFalse("Invalid session identifier trying to terminate session", error);

		assertNull("Session still exists.", this.sessionCache.getSession(sessionID1));

	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getTerminate()}.
	 */
	@Test
	public final void testTerminate2()
	{
		String sessionID2 = "96481723569817629875691872";
		assertSame("Terminate was expected to return the same object.", this.terminate, this.processor.getTerminate());

		boolean error = false;
		/*
		 * Test case: Removing a session that has never been added.
		 */
		try
		{
			this.terminate.terminateSession(sessionID2);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}
	}

	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getUpdate()}.
	 */
	@Test
	public final void testUpdate1()
	{
		String sessionID = "98327598243687569876197619287";
		String samlID = "u984698y109458y098qy058y12029";
		String principalName = "kajshtoiuqweh";
		assertSame("Update was expected to return the same object.", this.update, this.processor.getUpdate());

		PrincipalImpl data = new PrincipalImpl();
		data.setPrincipalAuthnIdentifier(principalName);
		data.setSessionID(sessionID);
		data.setSAMLAuthnIdentifier("");		
		try
		{
			// Just in case...
			this.sessionCache.removeSession(sessionID);

			this.sessionCache.addSession(data);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Session already exists in empty cache.");
		}

		/*
		 * Test case: Get object without updating SAML ID.
		 */
		Principal principal = this.sessionCache.getSessionBySAMLID(samlID);

		assertNull("Principal shouldn't yet be cached by SAML ID", principal);

	}

	
	/**
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getUpdate()}.
	 */
	@Test
	public final void testUpdate3()
	{
		String sessionID = "98327598243687569876197619287";
		String principalName = "kajshtoiuqweh";
		String entityID = "ajshkjfhaksjhdfkjhsadkjfh";
		String index = "80085";
		
		assertSame("Update was expected to return the same object.", this.update, this.processor.getUpdate());

		PrincipalImpl data = new PrincipalImpl();
		data.setPrincipalAuthnIdentifier(principalName);
		data.setSessionID(sessionID);
		data.setSAMLAuthnIdentifier("");		
	
		try
		{
			
			// Just in case...
			this.sessionCache.removeSession(sessionID);

			this.sessionCache.addSession(data);
			this.update.addEntitySessionIndex(data, entityID, index);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Error accessing session cache.");
		}

		/*
		 * Test case: Check entity ID was added successfully.
		 */

		assertTrue("Entity ID was not added.", data.getActiveEntityList().contains(entityID));
	}

	
	/** Test updating of attributes that don't currently exis in principal.
	 * 
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getUpdate()}.
	 */
	@Test
	public final void testUpdate5()
	{
		String sessionID = "98327598243687569876197619287";
		String principalName = "kajshtoiuqweh";
		assertSame("Update was expected to return the same object.", this.update, this.processor.getUpdate());

		PrincipalImpl data = new PrincipalImpl();
		data.setPrincipalAuthnIdentifier(principalName);
		data.setSessionID(sessionID);
		data.setSAMLAuthnIdentifier("");		
	
		List<AuthnIdentityAttribute> authnIdentityAttributes = new Vector<AuthnIdentityAttribute>();
		AuthnIdentityAttribute attr = new AuthnIdentityAttributeImpl();
		List<String> values = new Vector<String>();
		values.add("a@b.c");
		attr.setName("email");
		attr.setValues(values);
		authnIdentityAttributes.add(attr);
		
		try
		{
			// Just in case...
			this.sessionCache.removeSession(sessionID);
			
			this.sessionCache.addSession(data);
			
			// will update attributes that dont currently exist
			this.update.addPrincipalAttributes(data, authnIdentityAttributes);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Error accessing session cache.");
		}
	
		/*
		 * Test case: Check attributes were updated
		 */
		
		Map<String, IdentityAttribute> attributes = data.getAttributes();
		Iterator<IdentityAttribute> idAttribs = attributes.values().iterator();
		while(idAttribs.hasNext())
		{
			IdentityAttribute attrib = idAttribs.next();
			if(attrib.getValues().contains("a@b.c"))
				return;
		}
		
		fail("Updated attribute was not found in principal data.");
		
		
	}
	
	
	/** Test updating of attributes that DO currently exist in principal.
	 * 
	 * Test method for {@link com.qut.middleware.esoe.sessions.impl.SessionsProcessorImpl#getUpdate()}.
	 */
	@Test
	public final void testUpdate6()
	{
		String sessionID = "98327598243687569876197619287";
		String principalName = "kajshtoiuqweh";
		
		assertSame("Update was expected to return the same object.", this.update, this.processor.getUpdate());

		// create the first piece of identity data, en email attribute
		IdentityData idData = new IdentityDataImpl();
		IdentityAttribute idAttr = new IdentityAttributeImpl();
		idAttr.setType(DataType.STRING.name());
		idAttr.getValues().add("first#test.com");		
		idData.getAttributes().put("email", idAttr);
		
		PrincipalImpl data = new PrincipalImpl();
		data.setPrincipalAuthnIdentifier(principalName);
		data.setSessionID(sessionID);
		data.setSAMLAuthnIdentifier("");		


		
		// update the data by adding another email attribute
		List<AuthnIdentityAttribute> authnIdentityAttributes = new Vector<AuthnIdentityAttribute>();
		AuthnIdentityAttribute attr = new AuthnIdentityAttributeImpl();
		List<String> values = new Vector<String>();
		values.add("second@test.com");
		attr.setName("email");
		attr.setValues(values);
		authnIdentityAttributes.add(attr);
		
		try
		{
			// Just in case...
			this.sessionCache.removeSession(sessionID);
		
			this.sessionCache.addSession(data);
			
			// will update attributes that currently exist, adding new email to list
			this.update.addPrincipalAttributes(data, authnIdentityAttributes);
		}
		catch (SessionCacheUpdateException ex)
		{
			fail("Error accessing session cache.");
		}
	
		/*
		 * Test case: Check attributes were updated
		 */
		
		Map<String, IdentityAttribute> attributes = data.getAttributes();
		Iterator idAttribs = attributes.values().iterator();
		while(idAttribs.hasNext())
		{
			IdentityAttribute attrib = (IdentityAttribute)idAttribs.next();
			if(!attrib.getValues().contains("second@test.com") &&
			   !attrib.getValues().contains("first@test.com")	)
				fail("Updated attribute was not found in principal data.");
		}	
		
	}
	
	
	/** Test invalid constructor args
	 * 
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction()
	{
		this.processor = new SessionsProcessorImpl(null, this.query, this.terminate, this.update);	
	}
	
	/** Test invalid constructor args
	 * 
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction1()
	{
		this.processor = new SessionsProcessorImpl(this.create, null, this.terminate, this.update);	
	}
	
	/** Test invalid constructor args
	 * 
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction2()
	{
		this.processor = new SessionsProcessorImpl(this.create, this.query, null, this.update);	
	}
	
	/** Test invalid constructor args
	 * 
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction3()
	{
		this.processor = new SessionsProcessorImpl(this.create, this.query, this.terminate, null);	
	}
	
}
