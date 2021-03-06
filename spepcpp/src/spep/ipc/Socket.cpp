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
 * Creation Date: 30/01/2007
 * 
 * Purpose: 
 */

#include "spep/ipc/Socket.h"
#include <iostream>

#include <boost/thread.hpp>

spep::ipc::platform::socket_t spep::ipc::ClientSocket::newSocket( int port )
{
	platform::socket_t socket = platform::openSocket();
	platform::connectLoopbackSocket( socket, port );
	return socket;
}

spep::ipc::ClientSocket::ClientSocket( spep::ipc::ClientSocketPool* pool, int port )
:
_pool( pool ),
_socket( ),
_engine( NULL ),
_port( port )
{
}

void spep::ipc::ClientSocket::reconnect( int retry )
{
	int delay = ( (retry>2) ? 3 : retry );
	
	boost::xtime retryAt;
	// TODO Maybe I should care if this fails [i.e. if (boost::xtime_get(..) == 0)]
	boost::xtime_get( &retryAt, boost::TIME_UTC );
	retryAt.sec += delay;

	if( _engine != NULL )
	{
		// Unset it so we don't keep hammering a broken connection. The OS might not like that so much.
		delete _engine;
		_engine = NULL;
	}
	
	boost::thread::sleep( retryAt );

	{
		ScopedLock lock( _mutex );
		
		try
		{
			_socket = this->newSocket(_port);
			_engine = new Engine( _socket );
			
			std::string serviceID;
			_engine->getObject( serviceID );
			_pool->setServiceID( serviceID );
		}
		catch( SocketException e )
		{
			if( _engine != NULL ) delete _engine;
			_engine = NULL;
		}
	}
}

spep::ipc::ClientSocketPool::ClientSocketPool( int port, std::size_t n )
{
	for( std::size_t i=0; i<n; ++i )
	{
		_free.push( new ClientSocket( this, port ) );
	}
}

spep::ipc::ClientSocket* spep::ipc::ClientSocketPool::get()
{
	ScopedLock lock( _mutex );
	if( _free.empty() )
	{
		_condition.wait( lock );
	}
	
	ClientSocket* sock = _free.front();
	_free.pop();
	return sock;
}

void spep::ipc::ClientSocketPool::release( spep::ipc::ClientSocket* socket )
{
	ScopedLock lock( _mutex );
	_free.push( socket );
	_condition.notify_one();
}

const std::string& spep::ipc::ClientSocketPool::getServiceID()
{
	ScopedLock lock( _mutex );
	
	return this->_serviceID;
}

void spep::ipc::ClientSocketPool::setServiceID( const std::string& serviceID )
{
	ScopedLock lock( _mutex );
	
	this->_serviceID = serviceID;
}

spep::ipc::ClientSocketLease::ClientSocketLease( spep::ipc::ClientSocketPool* pool )
:
_pool( pool ),
_socket( pool->get() )
{
}

spep::ipc::ClientSocketLease::~ClientSocketLease()
{
	_pool->release( _socket );
}

spep::ipc::ClientSocket* spep::ipc::ClientSocketLease::operator->()
{
	return _socket;
}

spep::ipc::ClientSocket* spep::ipc::ClientSocketLease::operator*()
{
	return _socket;
}
