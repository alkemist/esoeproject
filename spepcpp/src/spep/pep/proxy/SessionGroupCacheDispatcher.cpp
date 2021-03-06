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
 * Creation Date: 14/02/2007
 * 
 * Purpose: 
 */

#include "spep/pep/proxy/SessionGroupCacheDispatcher.h"

static const char *updateCache = SESSIONGROUPCACHE_updateCache;
static const char *clearCache = SESSIONGROUPCACHE_clearCache;
static const char *makeCachedAuthzDecision = SESSIONGROUPCACHE_makeCachedAuthzDecision;

spep::ipc::SessionGroupCacheDispatcher::SessionGroupCacheDispatcher(spep::SessionGroupCache *sessionGroupCache)
:
_sessionGroupCache(sessionGroupCache),
_prefix(SESSIONGROUPCACHE)
{
}

spep::ipc::SessionGroupCacheDispatcher::~SessionGroupCacheDispatcher()
{
}

bool spep::ipc::SessionGroupCacheDispatcher::dispatch( spep::ipc::MessageHeader &header, spep::ipc::Engine &en )
{
	std::string dispatch = header.getDispatch();
	
	if ( dispatch.compare( 0, strlen(SESSIONGROUPCACHE), _prefix ) != 0 )
		return false;
	
	if ( dispatch.compare( updateCache ) == 0 )
	{
		// Destination method is:
		//virtual void updateCache( std::string &sessionID, UnicodeString groupTarget, std::vector<UnicodeString> &authzTargets, Decision decision );
		
		SessionGroupCache_UpdateCacheCommand command;
		en.getObject( command );
		
		_sessionGroupCache->updateCache( command.sessionID, command.groupTarget, command.authzTargets, command.decision );
		
		if ( header.getType() == SPEPIPC_REQUEST )
		{
			throw InvocationTargetException( "No return type from this method" );
		}
		
		return true;
	}
	
	if ( dispatch.compare( clearCache ) == 0 )
	{
		// Destination method is:
		//virtual void clearCache( std::map< UnicodeString, std::vector<UnicodeString> > &groupTargets );
		
		std::map< UnicodeString, std::vector<UnicodeString> > groupTargets;
		en.getObject( groupTargets );
		
		// Try to clear the cache
		_sessionGroupCache->clearCache( groupTargets );
		
		if ( header.getType() == SPEPIPC_REQUEST )
		{
			throw InvocationTargetException( "No return type from this method" );
		}
		
		return true;
	}
	
	if ( dispatch.compare( makeCachedAuthzDecision ) == 0 )
	{
		// Destination method is:
		//virtual Decision makeCachedAuthzDecision( std::string &sessionID, UnicodeString resource );
		
		SessionGroupCache_MakeCachedAuthzDecisionCommand command;
		en.getObject( command );
		
		// Try to get the authz decision
		Decision decision( _sessionGroupCache->makeCachedAuthzDecision( command.sessionID, command.resource ) );
		
		if ( header.getType() == SPEPIPC_REQUEST )
		{
			en.sendResponseHeader();
			en.sendObject( decision );
		}
		
		return true;
	}
	
	return false;
}
