package com.qut.middleware.esoe.sso.impl;


import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.notNull;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.w3._2000._09.xmldsig_.Signature;
import org.w3c.dom.Element;

import com.qut.middleware.crypto.KeystoreResolver;
import com.qut.middleware.crypto.impl.KeystoreResolverImpl;
import com.qut.middleware.esoe.ConfigurationConstants;
import com.qut.middleware.esoe.logout.LogoutMechanism;
import com.qut.middleware.esoe.logout.bean.FailedLogout;
import com.qut.middleware.esoe.logout.bean.FailedLogoutRepository;
import com.qut.middleware.esoe.logout.bean.impl.FailedLogoutImpl;
import com.qut.middleware.esoe.logout.bean.impl.FailedLogoutRepositoryImpl;
import com.qut.middleware.esoe.logout.impl.FailedLogoutMonitor;
import com.qut.middleware.esoe.ws.WSClient;
import com.qut.middleware.metadata.processor.MetadataProcessor;
import com.qut.middleware.saml2.SchemaConstants;
import com.qut.middleware.saml2.StatusCodeConstants;
import com.qut.middleware.saml2.VersionConstants;
import com.qut.middleware.saml2.exception.MarshallerException;
import com.qut.middleware.saml2.handler.Marshaller;
import com.qut.middleware.saml2.handler.impl.MarshallerImpl;
import com.qut.middleware.saml2.identifier.IdentifierCache;
import com.qut.middleware.saml2.identifier.IdentifierGenerator;
import com.qut.middleware.saml2.identifier.impl.IdentifierCacheImpl;
import com.qut.middleware.saml2.identifier.impl.IdentifierGeneratorImpl;
import com.qut.middleware.saml2.schemas.assertion.NameIDType;
import com.qut.middleware.saml2.schemas.protocol.LogoutRequest;
import com.qut.middleware.saml2.schemas.protocol.Response;
import com.qut.middleware.saml2.schemas.protocol.Status;
import com.qut.middleware.saml2.schemas.protocol.StatusCode;
import com.qut.middleware.saml2.validator.SAMLValidator;
import com.qut.middleware.saml2.validator.impl.SAMLValidatorImpl;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

@SuppressWarnings({ "nls", "unqualified-field-access" })
public class LogoutFailureMonitorTest {

	private FailedLogoutMonitor monitor;
	private FailedLogoutRepository failures;
	private IdentifierGenerator idGenerator;
	private String esoeID = "_782g0d7fg72r54";
	private MetadataProcessor metadata;
	private KeystoreResolver keyStoreResolver;
	private WSClient webServiceClient;
	SAMLValidator samlValidator;
	
	int retryInterval = 2;
	int maxAge = 10;
		
	// generated ID of the logout request documents
	String authnID1;
	String authnID2;
	String authnID3;
	String authnID4;
	String authnID5;
	private LogoutMechanism logout;
	
	@Before
	public void setUp() throws Exception
	{	
		String keyStorePath = "tests" + File.separator + "testdata" + File.separator + "testskeystore.ks";
		String keyStorePassword = "Es0EKs54P4SSPK";
		String esoeKeyAlias = "esoeprimary";
		String esoeKeyPassword = "Es0EKs54P4SSPK";
		
		this.keyStoreResolver = new KeystoreResolverImpl(new File(keyStorePath), keyStorePassword, esoeKeyAlias, esoeKeyPassword);
		
		IdentifierCache idCache = new IdentifierCacheImpl();
		this.idGenerator = new IdentifierGeneratorImpl(idCache);
			
		// setup some authz failures and add to failure repository
		failures = new FailedLogoutRepositoryImpl();	
		
		this.metadata = createMock(MetadataProcessor.class);
		expect(this.metadata.resolveKey("esoeprimary")).andReturn(this.keyStoreResolver.getLocalPublicKey()).anyTimes();
		replay(metadata);
		
		this.webServiceClient = createMock(WSClient.class);
			
		samlValidator = new SAMLValidatorImpl(idCache, Integer.MAX_VALUE/1000);
				
		this.logout = createMock(LogoutMechanism.class);
		//expect(logout.getEndPoints(entityID)).andReturn(endpoints);
		//expect(logout.performSingleLogout((String)notNull(), (List<String>)notNull(), eq(entityID), anyBoolean())).andReturn(LogoutMechanism.result.LogoutSuccessful).anyTimes();
		replay(this.logout);
	}

	@Test
	public final void testFailureMonitorImpl() throws Exception
	{
		this.monitor = new FailedLogoutMonitor(this.failures, this.keyStoreResolver, this.logout, this.retryInterval, this.maxAge);		
		
		assertTrue(this.monitor.isAlive());
	}

	/* Test the bahaviour of the monitor when a valid failure exists. Essentially what we have is: 
	 * 
	 * 2 failures added to repository.
	 * 1 is invalid and should be removed on first poll, without calling the singleLogout service.
	 * 1 is valid and should be removed on first poll becuse it was sent succesfully
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public final void testBehaviour1() throws Exception
	{			
		// we can't set the expected value of the request document in the call to singleLogout(), because
		// the monitor will regenerate the request document. As such, we can only ever test for ONE failure
		// or ONE success (this is because we cannot differentiate between failures and so can not correctly
		// set the inResponseTo field when generating responses). 
		
		FailedLogout failure1 = new FailedLogoutImpl();
		failure1.setEndPoint("blah.com");
		// generate a SAML authnID to use
		authnID1 = this.idGenerator.generateSAMLID();
		failure1.setRequestDocument(this.generateLogoutRequest(authnID1));
		failure1.setAuthnId("12345");
		failure1.setTimeStamp(new Date());
		
		// represents a successful endpoint
		FailedLogout failure2 = new FailedLogoutImpl();
		failure2.setEndPoint("successful.com");
		// generate a SAML authnID to use
		authnID2 = this.idGenerator.generateSAMLID();
		failure2.setRequestDocument(this.generateLogoutRequest(authnID2));
		
		failures.add(failure1);
	
		// failure two is an invalid failure object as it has missing fields, shouldnt be added by repo
		try
		{
			failures.add(failure2);
			
			// dont want to get here
			fail("Expected Exception was not thrown for Illegal Failure addition");
		}
		catch(IllegalArgumentException e)
		{
			// cool, as expected
		}
		
		// return a success response when the singleLogout call is made
		expect(webServiceClient.singleLogout((Element)notNull(), (String)notNull())).andReturn(this.generateSuccessLogoutResponse(authnID2)).atLeastOnce();
		replay(webServiceClient);
	
		this.monitor = new FailedLogoutMonitor(this.failures, this.keyStoreResolver, this.logout, this.retryInterval, this.maxAge);		
		
		assertTrue(this.monitor.isAlive());		
		
		// before first poll only the valid should be in repo
		assertEquals("Failure repository size is incorrect", 1, this.failures.getSize());
	
	}
	
	/* One failure should be removed from the repository, as the failure date has not been set.
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public final void testBehaviour2() throws Exception
	{				
		// we can't set the expected value of the request document in the call to singleLogout(), because
		// the monitor will regenerate the request document. As such, we can only ever test for ONE failure
		// or ONE success (this is because we cannot differentiate between failures and so can not correctly
		// set the inResponseTo field when generating responses). 
		
		// no request document == invalid. This should be removed from the repo by monitor BEFORE the call to
		// singleLogout is made, and so should not affect our mock invocations.
		FailedLogout failure1 = new FailedLogoutImpl();
		failure1.setEndPoint("blah.com");
		failure1.setTimeStamp(new Date(System.currentTimeMillis()));
		// generate a SAML authnID to use
		authnID1 = this.idGenerator.generateSAMLID();
		failure1.setRequestDocument(this.generateLogoutRequest(authnID1));
		failure1.setAuthnId(authnID1);
		
		FailedLogout failure2 = new FailedLogoutImpl();
		// generate a SAML authnID to use
		authnID1 = this.idGenerator.generateSAMLID();
		failure2.setEndPoint("unsuccessful.com.2");		
		failure2.setRequestDocument(this.generateLogoutRequest(authnID1));
		failure2.setTimeStamp(new Date(System.currentTimeMillis()));
		failure2.setAuthnId(authnID1);
		
		// represents an unsuccessful endpoint
		FailedLogout failure3 = new FailedLogoutImpl();
		failure3.setEndPoint("unsuccessful.com");		
		// generate a SAML authnID to use
		authnID2 = this.idGenerator.generateSAMLID();
		failure3.setRequestDocument(this.generateLogoutRequest(authnID2));
		failure3.setTimeStamp(new Date(System.currentTimeMillis()));
		failure3.setAuthnId("123");
		
		failures.add(failure1);
		failures.add(failure2);
		failures.add(failure3);
		
		// return a failure (invalid) response when the singleLogout call is made. 
		expect(webServiceClient.singleLogout((Element)notNull(), (String)notNull())).andReturn(this.generateFailedLogoutResponse(authnID2)).atLeastOnce();
		replay(webServiceClient);
	
		this.monitor = new FailedLogoutMonitor(this.failures, this.keyStoreResolver, this.logout, this.retryInterval, this.maxAge);		
		
		assertTrue(this.monitor.isAlive());		
		
		// before first poll all 3 should be in repo
		assertEquals("Failure repository size is incorrect", 3, this.failures.getSize());
		
	}
	
	
	@Test
	public final void testShutdown() throws Exception
	{
		this.monitor = new FailedLogoutMonitor(this.failures, this.keyStoreResolver, this.logout, this.retryInterval, this.maxAge);		
		
		Thread.sleep(10000);
		
		this.monitor.shutdown();
		
		Thread.sleep(10000);
		
		assertTrue(!this.monitor.isAlive());
	}
	
	/*
	 * Generate the Logout Response to be sent back to the ESOE.
	 * 
	 */
	private Element generateSuccessLogoutResponse(String inResponseTo)
			throws Exception
	{		
		Element responseDocument = null;

		NameIDType issuer = new NameIDType();
		issuer.setValue("some.spep.identifier");

		Status status = new Status();
		StatusCode statusCode = new StatusCode();
		statusCode.setValue(StatusCodeConstants.success);
		status.setStatusCode(statusCode);
		status.setStatusMessage("test recieved");

//		 create a local id generator so we dont get collisions in our response ID
		IdentifierCache idCache2 = new IdentifierCacheImpl();
		IdentifierGenerator localIdGenerator = new IdentifierGeneratorImpl(idCache2);
		
		Response response = new Response();
		response.setID(localIdGenerator.generateSAMLID());
		response.setInResponseTo(inResponseTo);
		response.setIssueInstant(new XMLGregorianCalendarImpl(new GregorianCalendar()));
		response.setIssuer(issuer);
		response.setSignature(new Signature());
		response.setStatus(status);
		response.setVersion(VersionConstants.saml20);

		String MAR_PKGNAMES = LogoutRequest.class.getPackage().getName();
		String[] logoutSchemas = new String[] { SchemaConstants.samlProtocol };
		
		String keyStorePath = "tests" + File.separator + "testdata" + File.separator + "testskeystore.ks";
		String keyStorePassword = "Es0EKs54P4SSPK";
		String esoeKeyAlias = "esoeprimary";
		String esoeKeyPassword = "Es0EKs54P4SSPK";
		
		KeystoreResolver keyStoreResolver = new KeystoreResolverImpl(new File(keyStorePath), keyStorePassword, esoeKeyAlias, esoeKeyPassword);
		
		Marshaller<Response> logoutResponseMarshaller = new MarshallerImpl<Response>(MAR_PKGNAMES, logoutSchemas, keyStoreResolver);
		
		responseDocument = logoutResponseMarshaller.marshallSignedElement(response);

		return responseDocument;
	}
	
	
	/*
	 * Generate the Logout Response to be sent back to the ESOE.
	 * 
	 */
	private Element generateFailedLogoutResponse(String inResponseTo) throws Exception
	{		
		Element responseDocument = null;

		NameIDType issuer = new NameIDType();
		issuer.setValue("some.spep.identifier");

		Status status = new Status();
		StatusCode statusCode = new StatusCode();
		statusCode.setValue(StatusCodeConstants.requester);
		status.setStatusCode(statusCode);
		status.setStatusMessage("test recieved");

		// create a local id generator so we dont get collisions in our response ID
		IdentifierCache idCache2 = new IdentifierCacheImpl();
		IdentifierGenerator localIdGenerator = new IdentifierGeneratorImpl(idCache2);
		
		Response response = new Response();
		response.setID(localIdGenerator.generateSAMLID());
		response.setInResponseTo("invalidID");
		response.setIssueInstant(new XMLGregorianCalendarImpl(new GregorianCalendar()));
		response.setIssuer(issuer);
		response.setSignature(new Signature());
		response.setStatus(status);
		response.setVersion(VersionConstants.saml20);

		String MAR_PKGNAMES = LogoutRequest.class.getPackage().getName();
		String[] logoutSchemas = new String[] { SchemaConstants.samlProtocol };
		
		String keyStorePath = "tests" + File.separator + "testdata" + File.separator + "testskeystore.ks";
		String keyStorePassword = "Es0EKs54P4SSPK";
		String esoeKeyAlias = "esoeprimary";
		String esoeKeyPassword = "Es0EKs54P4SSPK";
		
		KeystoreResolver keyStoreResolver = new KeystoreResolverImpl(new File(keyStorePath), keyStorePassword, esoeKeyAlias, esoeKeyPassword);
		
		Marshaller<Response> logoutResponseMarshaller = new MarshallerImpl<Response>(MAR_PKGNAMES, logoutSchemas,
				keyStoreResolver);
		
		responseDocument = logoutResponseMarshaller.marshallSignedElement(response);

		return responseDocument;
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction1() throws Exception
	{
		this.monitor = new FailedLogoutMonitor(null, this.keyStoreResolver, this.logout, this.retryInterval, this.maxAge);		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction2() throws Exception
	{
		this.monitor = new FailedLogoutMonitor(this.failures, null, this.logout, this.retryInterval, this.maxAge);		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction3() throws Exception
	{
		this.monitor = new FailedLogoutMonitor(this.failures, this.keyStoreResolver, null, this.retryInterval, this.maxAge);		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction4() throws Exception
	{
		this.monitor = new FailedLogoutMonitor(this.failures, this.keyStoreResolver, this.logout, -1, this.maxAge);		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction5() throws Exception
	{
		this.monitor = new FailedLogoutMonitor(this.failures, this.keyStoreResolver, this.logout, this.retryInterval, -993);		
	}
	
	private Element generateLogoutRequest(String samlAuthnID) throws MarshallerException
	{
		Element requestDocument = null;
		LogoutRequest request = new LogoutRequest();
				
		NameIDType subject = new NameIDType();
		NameIDType issuer = new NameIDType();
		subject.setValue(samlAuthnID);
		request.setNameID(subject);
		request.setID(samlAuthnID);
		request.setReason("Test logout request");
		request.setVersion(VersionConstants.saml20);
		issuer.setValue(this.esoeID);
		request.setIssuer(issuer);
		
		// Timestamps MUST be set to UTC, no offset
		TimeZone utc = new SimpleTimeZone(0, ConfigurationConstants.timeZone); 
		GregorianCalendar cal = new GregorianCalendar(utc);
		request.setIssueInstant(new XMLGregorianCalendarImpl(cal));
		
		request.setSignature(new Signature());
		
		List<String>sessionIndicies = null;
		if(sessionIndicies != null)
		{
			Iterator<String> iterator = sessionIndicies.iterator();
			while(iterator.hasNext())
			{
				String sessionIndex = iterator.next();
				if(sessionIndex != null)
					request.getSessionIndices().add(sessionIndex);
			}
			
		}
		
		String[] schemas = new String[] { SchemaConstants.samlProtocol };
		String MAR_PKGNAMES = LogoutRequest.class.getPackage().getName();
		
		Marshaller<LogoutRequest> logoutRequestMarshaller = new MarshallerImpl<LogoutRequest>(MAR_PKGNAMES, schemas, this.keyStoreResolver);
		
		requestDocument = logoutRequestMarshaller.marshallSignedElement(request);
	
		return requestDocument;
	}
	
}
