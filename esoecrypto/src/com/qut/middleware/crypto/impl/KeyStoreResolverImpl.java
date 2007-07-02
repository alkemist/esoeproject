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
 * Creation Date: 01/11/2006
 * 
 * Purpose: Resolves a key from a keystore.
 */
package com.qut.middleware.crypto.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.apache.log4j.Logger;

import com.qut.middleware.crypto.KeyStoreResolver;

public class KeyStoreResolverImpl implements KeyStoreResolver
{
	private KeyStore keyStore;
	private PrivateKey localPrivateKey;
	private String localPublicKeyAlias;
	private PublicKey localPublicKey;
	
	/* Local logging instance */
	private Logger logger = Logger.getLogger(KeyStoreResolverImpl.class.getName());

	/** 
	 * Constructor.
	 * 
	 */
	public KeyStoreResolverImpl(File keyStoreFile, String keyStorePassword, String localKeyAlias, String localKeyPassword)
	{
		this.localPublicKeyAlias = localKeyAlias;
		String keyPassword;
		
		if(keyStoreFile == null)
		{
			throw new InvalidParameterException(); 
		}
		if(keyStorePassword == null)
		{
			throw new InvalidParameterException(); 
		}
		if(localKeyAlias == null)
		{
			throw new InvalidParameterException(); 
		}
		if(localKeyPassword == null)
		{
			keyPassword = ""; 
		}
		else
			keyPassword = localKeyPassword;
		
		try
		{		
			this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream keyStoreStream = new FileInputStream(keyStoreFile);
			this.keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
			keyStoreStream.close();
		}
		catch (KeyStoreException e)
		{
			this.logger.error("Exception while operating on keystore " + e.getLocalizedMessage());
			this.logger.debug(e);
			throw new UnsupportedOperationException(e);
		}
		catch (FileNotFoundException e)
		{
			this.logger.error("Could not locate keystore " + e.getLocalizedMessage());
			this.logger.debug(e);
			throw new UnsupportedOperationException(e);
		}
		catch (NoSuchAlgorithmException e)
		{
			this.logger.error("Unknown algorithm when reading keystore " + e.getLocalizedMessage());
			this.logger.debug(e);
			throw new UnsupportedOperationException(e);
		}
		catch (CertificateException e)
		{
			this.logger.error("Certificate exception when reading keystore " + e.getLocalizedMessage());
			this.logger.debug(e);
			throw new UnsupportedOperationException(e);
		}
		catch (IOException e)
		{
			this.logger.error("IO exception when reading keystore " + e.getLocalizedMessage());
			this.logger.debug(e);
			throw new UnsupportedOperationException(e);
		}
		
		try
		{
			Key key = this.keyStore.getKey(localKeyAlias, keyPassword.toCharArray());
			if (key instanceof PrivateKey)
			{
				this.localPrivateKey = (PrivateKey)key;
			}
			else
			{
				this.logger.debug("No private key located in keystore"); 
				this.localPrivateKey = null;
			}
			
			this.localPublicKey = resolveKey(localKeyAlias);
		}
		catch (KeyStoreException e)
		{
			this.logger.error("Exception while operating on keystore " + e.getLocalizedMessage());
			this.logger.debug(e);
			throw new UnsupportedOperationException(e);
		}
		catch (NoSuchAlgorithmException e)
		{
			this.logger.error("Unknown algorithm when reading keys " + e.getLocalizedMessage());
			this.logger.debug(e);
			throw new UnsupportedOperationException(e);
		}
		catch (UnrecoverableKeyException e)
		{
			this.logger.error("Unrecoverable problem with keys occured " + e.getLocalizedMessage());
			this.logger.debug(e); 
			throw new UnsupportedOperationException(); 
		}
	}

	/* (non-Javadoc)
	 * @see com.qut.middleware.saml2.ExternalKeyResolver#resolveKey(java.lang.String)
	 */
	public PublicKey resolveKey(String alias)
	{
		Certificate certificate = this.resolveCertificate(alias);
		if(certificate == null) 
		{
			this.logger.debug("Public key for this keystore is null"); 
			return null;
		}
		return certificate.getPublicKey();
	}

	/*
	 * (non-Javadoc)
	 * @see com.qut.middleware.esoe.crypto.KeyStoreResolver#resolveCertificate(java.lang.String)
	 */
	public Certificate resolveCertificate(String alias)
	{
		try
		{
			return this.keyStore.getCertificate(alias);
		}
		catch (KeyStoreException e)
		{
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.qut.middleware.esoe.crypto.KeyStoreResolver#getPrivateKey()
	 */
	public PrivateKey getPrivateKey()
	{
		if (this.localPrivateKey != null)
		{
			return this.localPrivateKey;
		}
		
		this.logger.error("Private key for this keystore is null"); 
		throw new InvalidParameterException(); 
	}

	/*
	 * (non-Javadoc)
	 * @see com.qut.middleware.esoe.crypto.KeyStoreResolver#getKeyAlias()
	 */
	public String getKeyAlias()
	{
		return this.localPublicKeyAlias;
	}

	/*
	 * (non-Javadoc)
	 * @see com.qut.middleware.esoe.crypto.KeyStoreResolver#getPublicKey()
	 */
	public PublicKey getPublicKey()
	{
		return this.localPublicKey;
	}
	
	/* (non-Javadoc)
	 * @see com.qut.middleware.crypto.KeyStoreResolver#getKeyStore()
	 */
	public KeyStore getKeyStore()
	{
		return this.keyStore;
	}

}
