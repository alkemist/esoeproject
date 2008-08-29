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
 */
package com.qut.middleware.deployer.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MethodInvocationException;

import com.qut.middleware.deployer.bean.DeploymentBean;
import com.qut.middleware.deployer.exception.RenderConfigException;

public class RenderESOEConfigLogic
{	
	private File esoeConfig;
	private File esoeManagerConfig;
	private File esoeManagerSPEPConfig;
	
	private final String COMMENT = "#";
	private final String COMMENT_REPLACE = "_#";
	private final String VELOCITY_BEAN_NAME = "esoeBean";
	private final String VELOCITY_NODE_ID_NAME = "nodeID";
	private final String VELOCITY_SERVICE_URL = "serviceURL";
	private final String VELOCITY_SERVICE_NODE_URL = "serviceNodeURL";
	
	/* Local logging instance */
	private Logger logger = LoggerFactory.getLogger(RenderESOEConfigLogic.class.getName());

	public RenderESOEConfigLogic(File esoeConfig, File esoeManagerConfig, File esoeManagerSPEPConfig)
	{
		this.esoeConfig = esoeConfig;
		this.esoeManagerConfig = esoeManagerConfig;
		this.esoeManagerSPEPConfig = esoeManagerSPEPConfig;
	}
	
	public String renderESOEConfig(DeploymentBean esoeBean, int nodeID) throws RenderConfigException
	{
		return generateConfig(this.esoeConfig, esoeBean, nodeID);
	}

	public String renderESOEManagerConfig(DeploymentBean esoeBean, int nodeID) throws RenderConfigException
	{
		return generateConfig(this.esoeManagerConfig, esoeBean, nodeID);
	}
	
	public String renderESOEManagerSPEPConfig(DeploymentBean esoeBean, int nodeID) throws RenderConfigException
	{
		return generateConfig(this.esoeManagerSPEPConfig, esoeBean, nodeID);
	}
	
	private String generateConfig(File input, DeploymentBean esoeBean, int nodeID) throws RenderConfigException
	{
		StringBuffer inBuffer = null;
		InputStream fileStream = null;
		Reader reader = null;
		BufferedReader in = null;

		try
		{
			/* So whats going on here....
			 * Because java properties files and velocity both use # to indicate comments
			 * and velocity always strips comments, we're forced to replace our # comment symbols with a temporary value _#,
			 * thats ignored by velocity, then on the way out we simply restore.
			 */
			StringWriter stringWriter = new StringWriter();
			String tmp;
			String ammendedDocument;
			inBuffer = new StringBuffer();
			fileStream = new FileInputStream(input);
			reader = new InputStreamReader(fileStream);
			in = new BufferedReader(reader);
						
			try
			{
				while ((tmp = in.readLine()) != null)
				{
					inBuffer.append(tmp);
					inBuffer.append(System.getProperty("line.separator"));
				}
			}
			finally
			{
					in.close();
					reader.close();
					fileStream.close();
			}
			
			ammendedDocument = inBuffer.toString().replace(this.COMMENT, this.COMMENT_REPLACE);
			
			Velocity.init();
			VelocityContext context = new VelocityContext();
			
			/* Put the esoe configuration bean and the ID of the node being configured into velocity */
			context.put(this.VELOCITY_BEAN_NAME, esoeBean);
			context.put(this.VELOCITY_NODE_ID_NAME, nodeID);
			context.put(this.VELOCITY_SERVICE_NODE_URL, esoeBean.getEsoeNode());
			
			/* Get the base service URL without application data*/
			context.put(this.VELOCITY_SERVICE_URL, esoeBean.getEsoeNode());

			Velocity.evaluate(context, stringWriter, input.getName(), ammendedDocument);
			
			return stringWriter.toString().replace(this.COMMENT_REPLACE, this.COMMENT);
		}
		catch (ParseErrorException e)
		{
			this.logger.error("ParseErrorException when creating config file " + e.getLocalizedMessage());
			this.logger.debug(e.toString());
			throw new RenderConfigException("ParseErrorException when creating config file " + e.getLocalizedMessage());
		}
		catch (MethodInvocationException e)
		{
			this.logger.error("MethodInvocationException when creating config file " + e.getLocalizedMessage());
			this.logger.debug(e.toString());
			throw new RenderConfigException("MethodInvocationException when creating config file " + e.getLocalizedMessage());
		}
		catch (ResourceNotFoundException e)
		{
			this.logger.error("ResourceNotFoundException when creating config file " + e.getLocalizedMessage());
			this.logger.debug(e.toString());
			throw new RenderConfigException("ResourceNotFoundException when creating config file " + e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			this.logger.error("IOException when creating config file " + e.getLocalizedMessage());
			this.logger.debug(e.toString());
			throw new RenderConfigException("IOException when creating config file " + e.getLocalizedMessage());
		}
		catch (Exception e)
		{
			this.logger.error("Exception when creating config file " + e.getLocalizedMessage());
			this.logger.debug(e.toString());
			throw new RenderConfigException("Exception when creating config file " + e.getLocalizedMessage());
		}
	}
}
