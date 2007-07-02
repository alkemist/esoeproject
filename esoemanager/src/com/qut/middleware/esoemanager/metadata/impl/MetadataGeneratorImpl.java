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
 * Purpose: Metadata generator default implementation
 */
package com.qut.middleware.esoemanager.metadata.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3._2000._09.xmldsig_.Signature;

import com.qut.middleware.crypto.KeyStoreResolver;
import com.qut.middleware.esoemanager.Constants;
import com.qut.middleware.esoemanager.metadata.MetadataGenerator;
import com.qut.middleware.esoemanager.metadata.sqlmap.MetadataDAO;
import com.qut.middleware.saml2.exception.MarshallerException;
import com.qut.middleware.saml2.exception.UnmarshallerException;
import com.qut.middleware.saml2.handler.Marshaller;
import com.qut.middleware.saml2.handler.Unmarshaller;
import com.qut.middleware.saml2.handler.impl.MarshallerImpl;
import com.qut.middleware.saml2.handler.impl.UnmarshallerImpl;
import com.qut.middleware.saml2.identifier.IdentifierGenerator;
import com.qut.middleware.saml2.schemas.metadata.AttributeAuthorityDescriptor;
import com.qut.middleware.saml2.schemas.metadata.ContactPerson;
import com.qut.middleware.saml2.schemas.metadata.ContactTypeType;
import com.qut.middleware.saml2.schemas.metadata.EntitiesDescriptor;
import com.qut.middleware.saml2.schemas.metadata.EntityDescriptor;
import com.qut.middleware.saml2.schemas.metadata.IDPSSODescriptor;
import com.qut.middleware.saml2.schemas.metadata.RoleDescriptorType;
import com.qut.middleware.saml2.schemas.metadata.SPSSODescriptor;
import com.qut.middleware.saml2.schemas.metadata.lxacml.LXACMLPDPDescriptor;

public class MetadataGeneratorImpl implements MetadataGenerator
{
	private MetadataDAO metadataDAO;
	private KeyStoreResolver keystoreResolver;

	/* SAML Integration */
	private IdentifierGenerator identifierGenerator;
	private Unmarshaller<IDPSSODescriptor> idpUnmarshaller;
	private Unmarshaller<SPSSODescriptor> spUnmarshaller;
	private Unmarshaller<AttributeAuthorityDescriptor> attribAuthUnmarshaller;
	private Unmarshaller<LXACMLPDPDescriptor> lxacmlPDPUnmarshaller;
	private Marshaller<EntitiesDescriptor> metadataMarshaller;

	private final String UNMAR_PKGNAMES = IDPSSODescriptor.class.getPackage().getName();
	private final String UNMAR_PKGNAMES2 = SPSSODescriptor.class.getPackage().getName();
	private final String UNMAR_PKGNAMES3 = AttributeAuthorityDescriptor.class.getPackage().getName();
	private final String UNMAR_PKGNAMES4 = LXACMLPDPDescriptor.class.getPackage().getName() + ":" + RoleDescriptorType.class.getPackage().getName();

	private final String MAR_PKGNAMES = EntitiesDescriptor.class.getPackage().getName() + ":" + LXACMLPDPDescriptor.class.getPackage().getName();

	/* Local logging instance */
	private Logger logger = Logger.getLogger(MetadataGeneratorImpl.class.getName());

	MetadataGeneratorImpl(MetadataDAO metadataDAO, IdentifierGenerator identifierGenerator,
			KeyStoreResolver keystoreResolver) throws UnmarshallerException, MarshallerException
	{
		if (metadataDAO == null)
		{
			this.logger.error("Supplied metadataDAO was NULL for MetadataGeneratorImpl");
			throw new IllegalArgumentException("Supplied metadataDAO was NULL for MetadataGeneratorImpl");
		}
		if (identifierGenerator == null)
		{
			this.logger.error("Supplied identifierGenerator was NULL for MetadataGeneratorImpl");
			throw new IllegalArgumentException("Supplied identifierGenerator was NULL for MetadataGeneratorImpl");
		}
		if (keystoreResolver == null)
		{
			this.logger.error("Supplied keystoreResolver was NULL for MetadataGeneratorImpl");
			throw new IllegalArgumentException("Supplied keystoreResolver was NULL for MetadataGeneratorImpl");
		}

		String[] schemas = { Constants.lxacmlMetadata, Constants.samlMetadata };

		this.metadataDAO = metadataDAO;
		this.identifierGenerator = identifierGenerator;
		this.keystoreResolver = keystoreResolver;

		this.idpUnmarshaller = new UnmarshallerImpl<IDPSSODescriptor>(this.UNMAR_PKGNAMES, schemas);
		this.spUnmarshaller = new UnmarshallerImpl<SPSSODescriptor>(this.UNMAR_PKGNAMES2, schemas);
		this.attribAuthUnmarshaller = new UnmarshallerImpl<AttributeAuthorityDescriptor>(this.UNMAR_PKGNAMES3, schemas);
		this.lxacmlPDPUnmarshaller = new UnmarshallerImpl<LXACMLPDPDescriptor>(this.UNMAR_PKGNAMES4, schemas);

		this.metadataMarshaller = new MarshallerImpl<EntitiesDescriptor>(this.MAR_PKGNAMES, schemas,
				this.keystoreResolver.getKeyAlias(), this.keystoreResolver.getPrivateKey());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qut.middleware.esoemanager.metadata.MetadataGenerator#generateMetadata()
	 */
	public String generateMetadata()
	{
		List<String> activeEntities;
		List<Map<String, String>> contacts;
		List<Map<String, String>> idpDescriptors;
		List<Map<String, String>> spDescriptors;
		List<Map<String, String>> lxacmlPDPDescriptors;
		List<Map<String, String>> attribAuthDescriptors;

		EntitiesDescriptor entitiesDescriptor;

		/* Build metadata document from data repository */
		entitiesDescriptor = new EntitiesDescriptor();
		entitiesDescriptor.setID(this.identifierGenerator.generateSAMLID());

		/* Ensure entities descriptor is signed */
		entitiesDescriptor.setSignature(new Signature());

		activeEntities = this.metadataDAO.queryActiveEntities();
		if (activeEntities != null)
		{
			try
			{
				for (String entityID : activeEntities)
				{
					EntityDescriptor entityDescriptor = new EntityDescriptor();
					entityDescriptor.setEntityID(entityID);
					entityDescriptor.setID(this.identifierGenerator.generateSAMLID());

					contacts = this.metadataDAO.queryContacts(entityID);
					for (Map<String, String> contact : contacts)
					{
						ContactPerson contactPerson = new ContactPerson();
						contactPerson.setCompany(contact.get(Constants.FIELD_CONTACT_COMPANY));
						contactPerson.setGivenName(contact.get(Constants.FIELD_CONTACT_GIVEN_NAME));
						contactPerson.setSurName(contact.get(Constants.FIELD_CONTACT_SURNAME));
						contactPerson.getEmailAddress().add(contact.get(Constants.FIELD_CONTACT_EMAIL_ADDRESS));
						contactPerson.getTelephoneNumbers().add(contact.get(Constants.FIELD_CONTACT_TELEPHONE_NUMBER));
						contactPerson.setContactType(ContactTypeType.fromValue(contact
								.get(Constants.FIELD_CONTACT_TYPE)));

						entityDescriptor.getContactPersons().add(contactPerson);
					}

					idpDescriptors = this.metadataDAO.queryIDPDescriptor(entityID);
					for (Map<String, String> idpDescriptor : idpDescriptors)
					{
						String tmp = idpDescriptor.get(Constants.FIELD_DESCRIPTOR_XML);
						IDPSSODescriptor idp = this.idpUnmarshaller.unMarshallUnSigned(tmp);
						entityDescriptor.getIDPDescriptorAndSSODescriptorAndRoleDescriptors().add(idp);
						this.logger.debug("Retrieved idpDescriptor of: \n"
								+ idpDescriptor.get(Constants.FIELD_DESCRIPTOR_XML));
					}

					spDescriptors = this.metadataDAO.querySPDescriptors(entityID);
					for (Map<String, String> spDescriptor : spDescriptors)
					{
						SPSSODescriptor sp = this.spUnmarshaller.unMarshallUnSigned(spDescriptor
								.get(Constants.FIELD_DESCRIPTOR_XML));
						entityDescriptor.getIDPDescriptorAndSSODescriptorAndRoleDescriptors().add(sp);
						this.logger.debug("Retrieved spDescriptor of: \n"
								+ spDescriptor.get(Constants.FIELD_DESCRIPTOR_XML));
					}

					attribAuthDescriptors = this.metadataDAO.queryAttributeAuthorityDescriptor(entityID);
					for (Map<String, String> attribAuthDescriptor : attribAuthDescriptors)
					{
						AttributeAuthorityDescriptor sp = this.attribAuthUnmarshaller
								.unMarshallUnSigned(attribAuthDescriptor.get(Constants.FIELD_DESCRIPTOR_XML));
						entityDescriptor.getIDPDescriptorAndSSODescriptorAndRoleDescriptors().add(sp);
						this.logger.debug("Retrieved attribAuthDescriptor of: \n"
								+ attribAuthDescriptor.get(Constants.FIELD_DESCRIPTOR_XML));
					}

					lxacmlPDPDescriptors = this.metadataDAO.queryLXACMLPDPDescriptor(entityID);
					for (Map<String, String> lxacmlPDPDescriptor : lxacmlPDPDescriptors)
					{
						LXACMLPDPDescriptor sp = this.lxacmlPDPUnmarshaller.unMarshallUnSigned(lxacmlPDPDescriptor
								.get(Constants.FIELD_DESCRIPTOR_XML));
						entityDescriptor.getIDPDescriptorAndSSODescriptorAndRoleDescriptors().add(sp);
						this.logger.debug("Retrieved lxacmlPDPDescriptor of: \n"
								+ lxacmlPDPDescriptor.get(Constants.FIELD_DESCRIPTOR_XML));
					}

					/*
					 * If this entity actually had descriptors associated with it, add to metadata otherwise its
					 * considered lame
					 */
					if (entityDescriptor.getIDPDescriptorAndSSODescriptorAndRoleDescriptors().size() > 0)
						entitiesDescriptor.getEntitiesDescriptorsAndEntityDescriptors().add(entityDescriptor);
				}

				this.logger.debug("About to marshall metadata, all data loaded from repository correctly");
				return this.metadataMarshaller.marshallSigned(entitiesDescriptor);
			}
			catch (UnmarshallerException e)
			{
				this.logger.error("Exception when attempting to unmarshall metadata");
				this.logger.debug(e.getLocalizedMessage(), e);
			}
			catch (MarshallerException e)
			{
				this.logger.error("Exception when attempting to marshall completed metadata document");
				this.logger.debug(e.getLocalizedMessage(), e);
			}
		}

		return null;
	}

}
