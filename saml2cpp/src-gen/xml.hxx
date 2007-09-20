// Copyright (C) 2005-2007 Code Synthesis Tools CC
//
// This program was generated by XML Schema Definition Compiler (XSD)
//
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License. You may obtain a copy of 
// the License at 
// 
//   http://www.apache.org/licenses/LICENSE-2.0 
// 
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
// License for the specific language governing permissions and limitations under 
// the License.

#ifndef XML_HXX
#define XML_HXX

#include <xsd/cxx/version.hxx>

#if (XSD_INT_VERSION != 2030100L)
#error XSD runtime version mismatch
#endif

// Begin prologue.
//
//
// End prologue.

#include <xsd/cxx/pre.hxx>

#ifndef XSD_USE_WCHAR
#define XSD_USE_WCHAR
#endif

#ifndef XSD_CXX_TREE_USE_WCHAR
#define XSD_CXX_TREE_USE_WCHAR
#endif

#include "xsd/xml-schema.hxx"

// Forward declarations.
//
namespace namespace_
{
  class lang;
  class space;
}


#include <memory>    // std::auto_ptr
#include <algorithm> // std::binary_search

#include <xsd/cxx/tree/exceptions.hxx>
#include <xsd/cxx/tree/elements.hxx>
#include <xsd/cxx/tree/containers.hxx>
#include <xsd/cxx/tree/list.hxx>

namespace namespace_
{
  class lang: public ::xml_schema::string
  {
    public:

    lang ();

    lang (const ::std::basic_string< wchar_t >&);

    lang (const ::xercesc::DOMElement&,
          ::xml_schema::flags = 0,
          ::xml_schema::type* = 0);

    lang (const ::xercesc::DOMAttr&,
          ::xml_schema::flags = 0,
          ::xml_schema::type* = 0);

    lang (const ::std::basic_string< wchar_t >&,
          const ::xercesc::DOMElement*,
          ::xml_schema::flags = 0,
          ::xml_schema::type* = 0);

    lang (const lang&,
          ::xml_schema::flags = 0,
          ::xml_schema::type* = 0);

    virtual lang*
    _clone (::xml_schema::flags = 0,
            ::xml_schema::type* = 0) const;
  };

  class space: public ::xml_schema::ncname
  {
    public:
    enum _xsd_space
    {
      default_,
      preserve
    };

    space ();

    space (_xsd_space);

    space (const ::xml_schema::ncname&);

    space (const ::xercesc::DOMElement&,
           ::xml_schema::flags = 0,
           ::xml_schema::type* = 0);

    space (const ::xercesc::DOMAttr&,
           ::xml_schema::flags = 0,
           ::xml_schema::type* = 0);

    space (const ::std::basic_string< wchar_t >&,
           const ::xercesc::DOMElement*,
           ::xml_schema::flags = 0,
           ::xml_schema::type* = 0);

    space (const space&,
           ::xml_schema::flags = 0,
           ::xml_schema::type* = 0);

    virtual space*
    _clone (::xml_schema::flags = 0,
            ::xml_schema::type* = 0) const;

    space&
    operator= (_xsd_space);

    virtual
    operator _xsd_space () const
    {
      return _xsd_space_convert ();
    }

    protected:
    _xsd_space
    _xsd_space_convert () const;

    public:
    static const wchar_t* const _xsd_space_literals_[2];
    static const _xsd_space _xsd_space_indexes_[2];
  };
}

#include <iosfwd>

#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMInputSource.hpp>
#include <xercesc/dom/DOMErrorHandler.hpp>

namespace namespace_
{
}

#include <iosfwd> // std::ostream&

#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMErrorHandler.hpp>
#include <xercesc/framework/XMLFormatter.hpp>

#include <xsd/cxx/xml/dom/auto-ptr.hxx>

namespace namespace_
{
  void
  operator<< (::xercesc::DOMElement&,
              const lang&);

  void
  operator<< (::xercesc::DOMAttr&,
              const lang&);

  void
  operator<< (::xsd::cxx::tree::list_stream< wchar_t >&,
              const lang&);

  void
  operator<< (::xercesc::DOMElement&,
              space);

  void
  operator<< (::xercesc::DOMAttr&,
              space);

  void
  operator<< (::xsd::cxx::tree::list_stream< wchar_t >&,
              space);
}

#include <xsd/cxx/post.hxx>

// Begin epilogue.
//
//
// End epilogue.

#endif // XML_HXX