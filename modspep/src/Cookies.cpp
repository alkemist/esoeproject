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
 * Creation Date: 18/06/2007
 * 
 * Purpose: 
 */
 
#include "Cookies.h"
#include "APRDefinitions.h"

#include <sstream>
#include <exception>

spep::apache::Cookies::Cookies()
:
_cookieTable( NULL ),
_cookieStrings(),
_req( NULL )
{
}

spep::apache::Cookies::Cookies( request_rec *req )
:
_cookieTable( this->createCookieTableFromRequest( req ) ),
_cookieStrings(),
_req( req )
{
}

const char* spep::apache::Cookies::operator[]( std::string name )
{
	
	if( this->_cookieTable != NULL )
		return apr_table_get( this->_cookieTable, name.c_str() );
	
	return NULL;
	
}

/* *** Begin Apache2 code section *** */
#ifndef APACHE1

#include "apreq2/apreq.h"
#include "apreq2/apreq_cookie.h"

apr_table_t *spep::apache::Cookies::createCookieTableFromRequest( request_rec *req )
{
	const char *cookieString = apr_table_get( req->headers_in, "Cookie" );
	apr_table_t *cookieTable = NULL;
	
	if( cookieString != NULL )
	{
		// Default to 10 just so we don't have to resize unless the user has
		// a stupidly large number of cookies.
		cookieTable = apr_table_make( req->pool, 10 );
	
		if( apreq_parse_cookie_header( req->pool, cookieTable, cookieString ) != APR_SUCCESS )
		{
			// TODO Maybe throw an exception here instead.
			cookieTable = NULL;
		}
	}
	
	return cookieTable;
}


void spep::apache::Cookies::addCookie( request_rec *req, const char *name, const char *value, const char *path, const char *domain, bool secureOnly, int expires )
{
	
	apr_pool_t *pool = req->pool;
	apreq_cookie_t *cookie = apreq_cookie_make( pool, name, strlen(name), value, strlen(value) );
	
	if( secureOnly )
		apreq_cookie_secure_on( cookie );
	else
		apreq_cookie_secure_off( cookie );
	
	if( domain )
		cookie->domain = apr_pstrdup( pool, domain );
	
	if( path )
		cookie->path = apr_pstrdup( pool, path );
	else
		cookie->path = apr_pstrdup( pool, "/" );
	
	if( expires > 0 )
	{
		char timeBuffer[26];
		/* libapreq2 expects, for example:
		 * +15s for 15 seconds
		 * The largest number represented by a 64-bit int is 20 digits, so
		 * when we take the 2 extra characters, the null terminator, and the
		 * possibility of a minus sign in front, 26 will be the size we need. 
		 */
		
		snprintf( timeBuffer, 26, "%d s", expires );
		
		apreq_cookie_expires( cookie, timeBuffer );
	}	
	
	this->_cookieStrings.push_back( apreq_cookie_as_string( cookie, pool ) );
	
}

void spep::apache::Cookies::sendCookies( request_rec *req )
{
	
	std::stringstream cookieStringStream;
	bool first = true;
	
	for( std::vector<char*>::iterator cookieIterator = this->_cookieStrings.begin();
		cookieIterator != this->_cookieStrings.end();
		++cookieIterator )
	{
		
		if( !first ) cookieStringStream << ",";
		first = false;
		cookieStringStream << *cookieIterator;
		
	}
	if( first ) return;
	
	std::string cookieString( cookieStringStream.str() );
	apr_table_set( req->err_headers_out, "Set-Cookie", cookieString.c_str() );
	
}

/* *** Begin Apache1 code section *** */
#else /*ifdef APACHE1*/

#include "libapreq/apache_cookie.h"

#include <boost/date_time/posix_time/posix_time.hpp>

apr_table_t *spep::apache::Cookies::createCookieTableFromRequest( request_rec *req )
{
	ApacheCookieJar *jar = ApacheCookie_parse( req, NULL );
	
	if( jar != NULL )
	{
		// Default to 10 just so we don't have to resize unless the user has
		// a stupidly large number of cookies.
		apr_table_t *cookieTable = apr_table_make( req->pool, 10 );
		
		for( int i = 0; i < ApacheCookieJarItems(jar); ++i )
		{
			ApacheCookie *cookie = ApacheCookieJarFetch(jar, i);
			
			if( ApacheCookieItems( cookie ) > 0 )
			{
				apr_table_set( cookieTable, cookie->name, ApacheCookieFetch( cookie, 0 ) );
			}
		}
		
		return cookieTable;
	}
	
	return NULL;
}

#include <iostream>
void spep::apache::Cookies::addCookie( request_rec *req, const char *name, const char *value, const char *path, const char *domain, bool secureOnly, int expires )
{
	// This is a really weird API. Perl style calls from C. Nice one libapreq-1.33
	ApacheCookie *cookie = ApacheCookie_new( req, "-name", name, "-value", value, "-domain", domain, "path", path, NULL );
	
	apr_pool_t *pool = req->pool;
	
	if( secureOnly )
		cookie->secure = 1;
	else
		cookie->secure  = 0;
	
	if( domain )
		cookie->domain = apr_pstrdup( pool, domain );
	
	if( path )
		cookie->path = apr_pstrdup( pool, path );
	else
		cookie->path = apr_pstrdup( pool, "/" );
	
	if( expires > 0 )
	{
		boost::posix_time::ptime expiry = 
			boost::posix_time::second_clock::universal_time() 
			+ boost::posix_time::seconds( expires );
		
		boost::posix_time::time_facet *facet = new boost::posix_time::time_facet( COOKIES_EXPIRES_TIME_STRING_FORMAT );
		
		std::stringstream ss;
		ss.imbue( std::locale( ss.getloc(), facet ) );
		
		ss << expiry << std::ends;
		
		std::string expiryString( ss.str() );
		cookie->expires = apr_pstrdup( pool, expiryString.c_str() );
	}	
	
	this->_cookieStrings.push_back( ApacheCookie_as_string( cookie ) );
}

void spep::apache::Cookies::sendCookies( request_rec *req )
{
	
	std::stringstream cookieStringStream;
	bool first = true;
	
	for( std::vector<char*>::iterator cookieIterator = this->_cookieStrings.begin();
		cookieIterator != this->_cookieStrings.end();
		++cookieIterator )
	{
		
		if( !first ) cookieStringStream << ",";
		first = false;
		cookieStringStream << *cookieIterator;
		
	}
	
	std::string cookieString( cookieStringStream.str() );
	apr_table_set( req->err_headers_out, "Set-Cookie", cookieString.c_str() );
}

#endif
