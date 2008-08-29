/* Copyright 2006-2007, Queensland University of Technology
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
 * Creation Date: Jul 9, 2007
 * 
 * Purpose: 
 */

#include "StreamLogHandler.h"

#include "spep/reporting/ReportingProcessor.h"

#include <iostream>

spep::isapi::StreamLogHandler::StreamLogHandler( std::ostream &out, spep::Level level )
:
_out(out),
_level( level )
{
}

void spep::isapi::StreamLogHandler::log( const std::string &name, const spep::Level level, const std::string &message )
{
	if( level >= this->_level )
	{
		_out << spep::ReportingProcessor::timestamp() << " [" << level << "] " << name << " - " << message << std::endl << std::flush;
	}
}

spep::isapi::StreamLogHandler::~StreamLogHandler()
{
}
