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
 * Creation Date: 09/11/2006
 * 
 * Purpose: Responsible for initializing the SPEP.
 */
package com.qut.middleware.spep;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.PublicKey;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.qut.middleware.saml2.exception.MarshallerException;
import com.qut.middleware.saml2.exception.UnmarshallerException;
import com.qut.middleware.saml2.identifier.IdentifierCache;
import com.qut.middleware.saml2.identifier.IdentifierGenerator;
import com.qut.middleware.saml2.identifier.impl.IdentifierCacheImpl;
import com.qut.middleware.saml2.identifier.impl.IdentifierGeneratorImpl;
import com.qut.middleware.saml2.validator.SAMLValidator;
import com.qut.middleware.saml2.validator.impl.SAMLValidatorImpl;
import com.qut.middleware.spep.attribute.impl.AttributeProcessorImpl;
import com.qut.middleware.spep.authn.impl.AuthnProcessorImpl;
import com.qut.middleware.spep.exception.SPEPInitializationException;
import com.qut.middleware.spep.impl.IdentifierCacheMonitor;
import com.qut.middleware.spep.impl.SPEPImpl;
import com.qut.middleware.spep.impl.SPEPProxyImpl;
import com.qut.middleware.spep.impl.StartupProcessorImpl;
import com.qut.middleware.spep.metadata.KeyStoreResolver;
import com.qut.middleware.spep.metadata.impl.KeyStoreResolverImpl;
import com.qut.middleware.spep.metadata.impl.MetadataImpl;
import com.qut.middleware.spep.pep.PolicyEnforcementProcessor.decision;
import com.qut.middleware.spep.pep.impl.PolicyEnforcementProcessorImpl;
import com.qut.middleware.spep.pep.impl.SessionGroupCacheImpl;
import com.qut.middleware.spep.sessions.SessionCache;
import com.qut.middleware.spep.sessions.impl.SessionCacheImpl;
import com.qut.middleware.spep.ws.WSClient;
import com.qut.middleware.spep.ws.impl.WSClientImpl;

/** Responsible for initializing the SPEP. */
public class Initializer
{
	/* Local logging instance */
	static private Logger logger = Logger.getLogger(Initializer.class.getName());
	
	private final static String DENY = "deny";
	private final static String PERMIT = "permit";
	
	private static String SPEP_CONFIGURATION_PATH = null;
	
	private static String resolveProperty(Properties properties, String propName) throws SPEPInitializationException
	{
		String tmpProp = properties.getProperty(propName);
		
		if(tmpProp == null)
		{
			throw new SPEPInitializationException("Unable to find any value for requested property " + propName);
		}
		
		if(tmpProp.contains("${"+ ConfigurationConstants.SPEP_PATH_PROP + "}"))
		{
			tmpProp = tmpProp.replace("${"+ ConfigurationConstants.SPEP_PATH_PROP + "}", SPEP_CONFIGURATION_PATH);
		}
		
		return tmpProp;
	}
	
	/**
	 * @param context The servlet context in which to initialize a SPEP
	 * @return The SPEP for the given servlet context.
	 * @throws SPEPInitializationException 
	 */
	public static synchronized SPEP init(ServletContext context) throws SPEPInitializationException
	{
		if(context == null)
		{
			throw new IllegalArgumentException(Messages.getString("Initializer.14"));  //$NON-NLS-1$
		}
		
		if(context.getAttribute(ConfigurationConstants.SERVLET_CONTEXT_NAME) == null)
		{
			SPEPImpl spep = new SPEPImpl();
			
			/* Determine the location of spep.data either from local spep config or from system property */
			InputStream varPropertyInputStream = context.getResourceAsStream(ConfigurationConstants.SPEP_CONFIG_LOCAL);
			if(varPropertyInputStream != null)
			{
				Properties varProperties = new Properties();
				try
				{
					varProperties.load(varPropertyInputStream);
				}
				catch (IOException e)
				{
					throw new SPEPInitializationException(Messages.getString("Initializer.0"), e); //$NON-NLS-1$
				}
				
				SPEP_CONFIGURATION_PATH = varProperties.getProperty(ConfigurationConstants.SPEP_PATH_PROP);
			}
			
			if(SPEP_CONFIGURATION_PATH != null && SPEP_CONFIGURATION_PATH.length() > 0)
				logger.info("Configured spep.data locally from spepvars.config, with a value of: " + SPEP_CONFIGURATION_PATH);
			else
			{
				SPEP_CONFIGURATION_PATH = System.getProperty(ConfigurationConstants.SPEP_PATH_PROP);
				if(SPEP_CONFIGURATION_PATH != null)
					logger.info("Configured spep.data from java property spep.data, with a value of: " + SPEP_CONFIGURATION_PATH);
				else
				{
					logger.fatal("Unable to resolve location of spep config and keystores from either local file of WEB-INF/spepvars.config (spep.data) or java property spep.data");
					throw new IllegalArgumentException("Unable to resolve location of spep config and keystores from either local file of WEB-INF/spepvars.config (spep.data) or java property spep.data");
				}
			}
			
			/* Get the core properties file */
			FileInputStream propertyInputStream;
			try
			{
				propertyInputStream = new FileInputStream( SPEP_CONFIGURATION_PATH + ConfigurationConstants.SPEP_CONFIG);
			}
			catch (FileNotFoundException e1)
			{
				throw new IllegalArgumentException(Messages.getString("Initializer.15") + SPEP_CONFIGURATION_PATH  +  ConfigurationConstants.SPEP_CONFIG); //$NON-NLS-1$
			}
			
			Properties properties = new Properties();
			try
			{
				properties.load(propertyInputStream);
			}
			catch (IOException e)
			{
				throw new SPEPInitializationException(Messages.getString("Initializer.0"), e); //$NON-NLS-1$
			}
			
			// Grab all the configuration data from the properties file.
			String spepIdentifier = resolveProperty(properties, "spepIdentifier"); //$NON-NLS-1$
			String esoeIdentifier = resolveProperty(properties, "esoeIdentifier"); //$NON-NLS-1$
			String metadataUrl = resolveProperty(properties, "metadataUrl"); //$NON-NLS-1$
			
			// Path, password and aliases for keystore
			String keystorePath = resolveProperty(properties, "keystorePath"); //$NON-NLS-1$
			
			String keystorePassword = resolveProperty(properties, "keystorePassword"); //$NON-NLS-1$
			String metadataKeyAlias = resolveProperty(properties, "metadataKeyAlias"); //$NON-NLS-1$
			String spepKeyAlias = resolveProperty(properties, "spepKeyAlias"); //$NON-NLS-1$
			String spepKeyPassword = resolveProperty(properties, "spepKeyPassword");  //$NON-NLS-1$
			
			// Information line about the server
			String serverInfo = resolveProperty(properties, "serverInfo");  //$NON-NLS-1$
			
			// Node identifier configured in the metadata
			int nodeID = Integer.parseInt(resolveProperty(properties, "nodeIdentifier"));  //$NON-NLS-1$
			
			// Interval on which to refresh the metadata (seconds)
			int metadataInterval = Integer.parseInt(resolveProperty(properties, "metadataInterval")); //$NON-NLS-1$
			
			// Allowable time skew for SAML document expiry (seconds)
			int allowedTimeSkew = Integer.parseInt(resolveProperty(properties, "allowedTimeSkew")); //$NON-NLS-1$
			
			// Timeout and interval on which to check the session caches.
			long sessionCacheTimeout = Long.parseLong(resolveProperty(properties, "sessionCacheTimeout")); //$NON-NLS-1$
			long sessionCacheInterval = Long.parseLong(resolveProperty(properties, "sessionCacheInterval")); //$NON-NLS-1$
			
			// Timeout on data in the identifier cache. Should be longer than SAML document lifetime + time skew allowed
			long identifierCacheTimeout = Long.parseLong(resolveProperty(properties, "identifierCacheTimeout")); //$NON-NLS-1$
			
			// Default policy decision for LAXCML
			decision defaultPolicyDecision = decision.valueOf(resolveProperty(properties, "defaultPolicyDecision")); //$NON-NLS-1$
			
			// IP Address list for this host
			String ipAddresses = resolveProperty(properties, "ipAddresses"); //$NON-NLS-1$
			List<String> ipAddressList = new Vector<String>();
			StringTokenizer ipAddressTokenizer = new StringTokenizer(ipAddresses);
			while (ipAddressTokenizer.hasMoreTokens())
			{
				ipAddressList.add(ipAddressTokenizer.nextToken());
			}
			
			// Cookie and redirect information for authn
			spep.setTokenName(resolveProperty(properties, "spepTokenName")); //$NON-NLS-1$
			spep.setEsoeGlobalTokenName(resolveProperty(properties, "commonDomainTokenName"));
			spep.setServiceHost(resolveProperty(properties, "serviceHost")); //$NON-NLS-1$
			spep.setSsoRedirect(resolveProperty(properties, "ssoRedirect"));
			
			// Default url for users with no unauthenticated session
			spep.setDefaultURL(resolveProperty(properties, "defaultURL")); //$NON-NLS-1$
			
			// List of cookies to clear when an invalid session is encountered.
			List<Cookie> logoutClearCookies = new Vector<Cookie>();
			String clearCookiePropertyValue = null;
			
			for( int i = 1; (clearCookiePropertyValue = properties.getProperty("logoutClearCookie." + i )) != null; ++i ) //$NON-NLS-1$
			{
				StringTokenizer cookieTokenizer = new StringTokenizer( clearCookiePropertyValue );
				
				// If there is a token, there will be a cookie name
				if ( cookieTokenizer.hasMoreTokens() )
				{
					// Construct the cookie from the name in the parameter.
					Cookie cookie = new Cookie( cookieTokenizer.nextToken().trim(), "" ); //$NON-NLS-1$
					
					// If there is another token it will be the cookie domain.
					if ( cookieTokenizer.hasMoreTokens() )
						cookie.setDomain( cookieTokenizer.nextToken().trim() );
					
					// If there is another token it will be the cookie path.
					if ( cookieTokenizer.hasMoreTokens() )
						cookie.setPath( cookieTokenizer.nextToken().trim() );
					
					logoutClearCookies.add( cookie );
				}
			}
			
			// Determine if SPEP is operating is lazy mode
			spep.setLazyInit(new Boolean(properties.getProperty("lazyInit")).booleanValue());
			
			// If its in lazy mode then load up hardInit URL's there are the URLS that will force the SPEP to establish a session for the user
			if(spep.isLazyInit())
			{
				String lazyInitDefaultAction = properties.getProperty("lazyInitDefaultAction");
				if(lazyInitDefaultAction == null)
				{
					logger.fatal("Failed to retrieve lazyInitDefaultAction value");
					throw new SPEPInitializationException("Failed to retrieve lazyInitDefaultAction value");
				}
				
				if(lazyInitDefaultAction.equals(Initializer.DENY))
					spep.setLazyInitDefaultAction(SPEP.defaultAction.Deny);
				else
					if(lazyInitDefaultAction.equals(Initializer.PERMIT))
						spep.setLazyInitDefaultAction(SPEP.defaultAction.Permit);
					else
					{
						logger.fatal("Failed to retrieve lazyInitDefaultAction, invalid value must be deny or permit");
						throw new SPEPInitializationException("Failed to retrieve lazyInitDefaultAction, invalid value must be deny or permit");
					}
				
				List<String> lazyInitResources = new ArrayList<String>();
				String url;
				
				for(int i = 1; (url = properties.getProperty("lazyInit-resource-" + i)) != null; ++i)
				{
						lazyInitResources.add(url);
				}
				
				if(lazyInitResources.size() <= 0)
				{
					logger.fatal("Failed to retrieve any lazyinit-resource values, at least one URL MUST be specified");
					throw new SPEPInitializationException("Failed to retrieve any hardInit-URL-[] values, at least one URL MUST be specified");
				}
				
				spep.setLazyInitResources(lazyInitResources);
			}
			
			
			// Instantiate the input streams for other configuration.
			InputStream keyStoreInputStream;
			try
			{
				keyStoreInputStream = new FileInputStream(keystorePath);
			}
			catch (FileNotFoundException e)
			{
				logger.fatal("Failed to open config input stream " + e.getLocalizedMessage());
				logger.debug(e);
				throw new SPEPInitializationException("Failed to open config input stream " + e.getLocalizedMessage());
			}
			
			// Initialize the keystore resolver from the stream, and grab the metadata public key.
			KeyStoreResolver keyStoreResolver = new KeyStoreResolverImpl(keyStoreInputStream, keystorePassword, spepKeyAlias, spepKeyPassword);
			PublicKey metadataPublicKey = keyStoreResolver.resolveKey(metadataKeyAlias);

			// Create metadata instance
			spep.setMetadata(new MetadataImpl(spepIdentifier, esoeIdentifier, metadataUrl, metadataPublicKey, nodeID, metadataInterval));

			// Web services client instance
			WSClient wsClient = new WSClientImpl();
			
			// Create the identifier cache and generator.
			IdentifierCache identifierCache = new IdentifierCacheImpl();
			IdentifierGenerator identifierGenerator = new IdentifierGeneratorImpl(identifierCache);
			
			// SAML validator instance
			SAMLValidator samlValidator = new SAMLValidatorImpl(identifierCache, allowedTimeSkew);
			
			// Session cache instance
			SessionCache sessionCache = new SessionCacheImpl(sessionCacheTimeout, sessionCacheInterval);

			// start the identifier cache monitor thread
			new IdentifierCacheMonitor(identifierCache, sessionCacheInterval, identifierCacheTimeout);
			
			// Try to create the attribute processor instance
			try
			{
				spep.setAttributeProcessor(new AttributeProcessorImpl(spep.getMetadata(), wsClient, identifierGenerator, samlValidator, keyStoreResolver));
			}
			catch (MarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.7"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.1"), e); //$NON-NLS-1$
			}
			catch (UnmarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.7"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.1"), e); //$NON-NLS-1$
			}
			catch (IOException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.7"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.1"), e); //$NON-NLS-1$
			}
			
			// Try to create the authn processor instance
			try
			{
				spep.setAuthnProcessor(new AuthnProcessorImpl(spep.getAttributeProcessor(), spep.getMetadata(), sessionCache, samlValidator, identifierGenerator, keyStoreResolver, spep.getServiceHost(), spep.getSsoRedirect(), nodeID, nodeID));
			}
			catch (MarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.8"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.4"), e); //$NON-NLS-1$
			}
			catch (UnmarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.8"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.4"), e); //$NON-NLS-1$
			}
			catch (MalformedURLException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.8"), e.getMessage()));
				throw new SPEPInitializationException(Messages.getString("Initializer.4"), e); //$NON-NLS-1$
			}
			
			// Create the session group cache, then attempt to create the policy enforcement processor
			spep.setSessionGroupCache(new SessionGroupCacheImpl(defaultPolicyDecision));
			try
			{
				spep.setPolicyEnforcementProcessor(new PolicyEnforcementProcessorImpl(sessionCache, spep.getSessionGroupCache(), wsClient, identifierGenerator, spep.getMetadata(), keyStoreResolver, samlValidator));
			}
			catch (MarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.9"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.6"), e); //$NON-NLS-1$
			}
			catch (UnmarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.9"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.6"), e); //$NON-NLS-1$
			}

			// Store the SPEP object in the servlet context.
			context.setAttribute(ConfigurationConstants.SERVLET_CONTEXT_NAME, spep);
			
			// Create a SPEPProxyImpl for use in external classloaders as a dynamic proxy and store in servlet context
			SPEPProxyImpl spepProxy = new SPEPProxyImpl(spep);
			context.setAttribute(ConfigurationConstants.SPEP_PROXY, spepProxy);
						
			// Create the SPEP startup processor
			try
			{
				spep.setStartupProcessor(new StartupProcessorImpl(spep.getMetadata(), spepIdentifier, identifierGenerator, wsClient, samlValidator, keyStoreResolver, ipAddressList, serverInfo, nodeID));
			}
			catch (MarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.10"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.11"), e);			 //$NON-NLS-1$
			}
			catch (UnmarshallerException e)
			{
				logger.fatal(MessageFormat.format(Messages.getString("Initializer.12"), e.getMessage())); //$NON-NLS-1$
				throw new SPEPInitializationException(Messages.getString("Initializer.13"), e);			 //$NON-NLS-1$
			}
			
			// Fire off a background thread to communicate to the ESOE about starting up.
			spep.getStartupProcessor().beginSPEPStartup();
			
			return spep;
		}
		
		return (SPEP)context.getAttribute(ConfigurationConstants.SERVLET_CONTEXT_NAME);
	}
	
}
