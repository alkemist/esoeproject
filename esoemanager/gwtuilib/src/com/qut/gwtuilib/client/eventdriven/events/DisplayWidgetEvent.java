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
package com.qut.gwtuilib.client.eventdriven.events;

import com.qut.gwtuilib.client.eventdriven.eventmgr.BaseEvent;

public class DisplayWidgetEvent extends BaseEvent
{
	Object parent;
	String areaID;
	
	public DisplayWidgetEvent(String name, String areaID, Object parent)
	{
		super(name);
		
		this.areaID = areaID;
		this.parent = parent;
	}
	
	public Object getParent()
	{
		return this.parent;
	}
	
	public String getAreaID()
	{
		return this.areaID;
	}
	
}
