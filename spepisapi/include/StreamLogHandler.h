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

#ifndef STREAMLOGHANDLER_H_
#define STREAMLOGHANDLER_H_

#include "spep/reporting/Handler.h"

namespace spep { namespace isapi {
	
	class StreamLogHandler : public spep::Handler
	{
		
		private:
		std::ostream &_out;
		Level _level;
		
		public:
		
		StreamLogHandler( std::ostream &out, Level level );
		/**
		 * @see spep::Handler::log
		 */
		virtual void log( const std::string &name, const Level level, const std::string &message );
		virtual ~StreamLogHandler();
		
	};
	
} }

#endif /*STREAMLOGHANDLER_H_*/
