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
 * Creation Date: Aug 31, 2007
 * 
 * Purpose: 
 */

#ifndef ATTRIBUTEEXCEPTION_H_
#define ATTRIBUTEEXCEPTION_H_

#include <exception>
#include <string>

#include "spep/Util.h"

namespace spep
{
	
	class SPEPEXPORT AttributeException : public std::exception
	{
		
		private:
		std::string _message;
		
		public:
		AttributeException( std::string message );
		virtual ~AttributeException() throw();
		virtual const char *what() const throw();
		
	};
}

#endif /*ATTRIBUTEEXCEPTION_H_*/
