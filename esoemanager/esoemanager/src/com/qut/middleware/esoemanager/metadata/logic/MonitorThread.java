/* Copyright 2008, Queensland University of Technology
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
package com.qut.middleware.esoemanager.metadata.logic;

public interface MonitorThread 
{
	
	/** Tell the thread to shutdown. The implementation must ensure that the thread will
	 *  terminate regardless of current state. For example: it may not be sufficient to
	 *  interrupt the thread if it does large tasks, as Threads not recieve an Interrupted
	 *  Exception if they are running. In these cases Interrupts must be checked via 
	 *  Thread.isInterrupted() or some equivelent method.
	 *
	 */
	public void shutdown();
	
}
