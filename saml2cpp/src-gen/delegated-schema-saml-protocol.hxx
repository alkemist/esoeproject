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

#ifndef DELEGATED_SCHEMA_SAML_PROTOCOL_HXX
#define DELEGATED_SCHEMA_SAML_PROTOCOL_HXX

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
namespace middleware
{
  namespace DelegatedProtocolSchema
  {
    class RegisterPrincipalRequestType;
    class RegisterPrincipalResponseType;
  }
}


#include <memory>    // std::auto_ptr
#include <algorithm> // std::binary_search

#include <xsd/cxx/tree/exceptions.hxx>
#include <xsd/cxx/tree/elements.hxx>
#include <xsd/cxx/tree/containers.hxx>
#include <xsd/cxx/tree/list.hxx>

#include "saml-schema-assertion-2.0.hxx"

#include "saml-schema-protocol-2.0.hxx"

namespace middleware
{
  namespace DelegatedProtocolSchema
  {
    class RegisterPrincipalRequestType: public ::saml2::protocol::RequestAbstractType
    {
      public:

      struct _xsd_RegisterPrincipalRequestType
      {
        typedef ::saml2::protocol::RequestAbstractType base_;
      };

      // principalAuthnIdentifier
      // 
      public:
      struct principalAuthnIdentifier
      {
        typedef ::xml_schema::string type;
        typedef ::xsd::cxx::tree::traits< type, wchar_t > traits;
      };

      const principalAuthnIdentifier::type&
      principalAuthnIdentifier () const;

      principalAuthnIdentifier::type&
      principalAuthnIdentifier ();

      void
      principalAuthnIdentifier (const principalAuthnIdentifier::type&);

      void
      principalAuthnIdentifier (::std::auto_ptr< principalAuthnIdentifier::type >);

      // Attribute
      // 
      public:
      struct Attribute
      {
        typedef ::saml2::assertion::AttributeType type;
        typedef ::xsd::cxx::tree::traits< type, wchar_t > traits;
        typedef ::xsd::cxx::tree::sequence< type > container;
        typedef container::iterator iterator;
        typedef container::const_iterator const_iterator;
      };

      const Attribute::container&
      Attribute () const;

      Attribute::container&
      Attribute ();

      void
      Attribute (const Attribute::container&);

      // EncryptedAttribute
      // 
      public:
      struct EncryptedAttribute
      {
        typedef ::saml2::assertion::EncryptedElementType type;
        typedef ::xsd::cxx::tree::traits< type, wchar_t > traits;
        typedef ::xsd::cxx::tree::sequence< type > container;
        typedef container::iterator iterator;
        typedef container::const_iterator const_iterator;
      };

      const EncryptedAttribute::container&
      EncryptedAttribute () const;

      EncryptedAttribute::container&
      EncryptedAttribute ();

      void
      EncryptedAttribute (const EncryptedAttribute::container&);

      // Source
      // 
      public:
      struct Source
      {
        typedef ::xml_schema::string type;
        typedef ::xsd::cxx::tree::traits< type, wchar_t > traits;
      };

      const Source::type&
      Source () const;

      Source::type&
      Source ();

      void
      Source (const Source::type&);

      void
      Source (::std::auto_ptr< Source::type >);

#if defined(__EDG_VERSION__) || (defined(__HP_aCC) && __HP_aCC >= 60000)
      public:
      using _xsd_RegisterPrincipalRequestType::base_::ID;
      using _xsd_RegisterPrincipalRequestType::base_::Version;
      using _xsd_RegisterPrincipalRequestType::base_::IssueInstant;
#endif

      // Constructors.
      //
      public:
      RegisterPrincipalRequestType ();

      RegisterPrincipalRequestType (const ID::type&,
                                    const Version::type&,
                                    const IssueInstant::type&,
                                    const principalAuthnIdentifier::type&,
                                    const Source::type&);

      RegisterPrincipalRequestType (const ::xercesc::DOMElement&,
                                    ::xml_schema::flags = 0,
                                    ::xml_schema::type* = 0);

      RegisterPrincipalRequestType (const RegisterPrincipalRequestType&,
                                    ::xml_schema::flags = 0,
                                    ::xml_schema::type* = 0);

      virtual RegisterPrincipalRequestType*
      _clone (::xml_schema::flags = 0,
              ::xml_schema::type* = 0) const;

      // Implementation.
      //
      private:
      void
      parse (const ::xercesc::DOMElement&, ::xml_schema::flags);

      ::xsd::cxx::tree::one< principalAuthnIdentifier::type > _xsd_principalAuthnIdentifier_;
      ::xsd::cxx::tree::sequence< Attribute::type > _xsd_Attribute_;
      ::xsd::cxx::tree::sequence< EncryptedAttribute::type > _xsd_EncryptedAttribute_;
      ::xsd::cxx::tree::one< Source::type > _xsd_Source_;
    };

    class RegisterPrincipalResponseType: public ::saml2::protocol::StatusResponseType
    {
      public:

      struct _xsd_RegisterPrincipalResponseType
      {
        typedef ::saml2::protocol::StatusResponseType base_;
      };

      // sessionIdentifier
      // 
      public:
      struct sessionIdentifier
      {
        typedef ::xml_schema::string type;
        typedef ::xsd::cxx::tree::traits< type, wchar_t > traits;
        typedef ::xsd::cxx::tree::optional< type > container;
      };

      const sessionIdentifier::container&
      sessionIdentifier () const;

      sessionIdentifier::container&
      sessionIdentifier ();

      void
      sessionIdentifier (const sessionIdentifier::type&);

      void
      sessionIdentifier (const sessionIdentifier::container&);

      void
      sessionIdentifier (::std::auto_ptr< sessionIdentifier::type >);

#if defined(__EDG_VERSION__) || (defined(__HP_aCC) && __HP_aCC >= 60000)
      public:
      using _xsd_RegisterPrincipalResponseType::base_::Status;
      using _xsd_RegisterPrincipalResponseType::base_::ID;
      using _xsd_RegisterPrincipalResponseType::base_::Version;
      using _xsd_RegisterPrincipalResponseType::base_::IssueInstant;
#endif

      // Constructors.
      //
      public:
      RegisterPrincipalResponseType ();

      RegisterPrincipalResponseType (const Status::type&,
                                     const ID::type&,
                                     const Version::type&,
                                     const IssueInstant::type&);

      RegisterPrincipalResponseType (const ::xercesc::DOMElement&,
                                     ::xml_schema::flags = 0,
                                     ::xml_schema::type* = 0);

      RegisterPrincipalResponseType (const RegisterPrincipalResponseType&,
                                     ::xml_schema::flags = 0,
                                     ::xml_schema::type* = 0);

      virtual RegisterPrincipalResponseType*
      _clone (::xml_schema::flags = 0,
              ::xml_schema::type* = 0) const;

      // Implementation.
      //
      private:
      void
      parse (const ::xercesc::DOMElement&, ::xml_schema::flags);

      ::xsd::cxx::tree::optional< sessionIdentifier::type > _xsd_sessionIdentifier_;
    };
  }
}

#include <iosfwd>

#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMInputSource.hpp>
#include <xercesc/dom/DOMErrorHandler.hpp>

namespace middleware
{
  namespace DelegatedProtocolSchema
  {
    // Read from a URI or a local file.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (const ::std::basic_string< wchar_t >&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (const ::std::basic_string< wchar_t >&,
                              ::xsd::cxx::xml::error_handler< wchar_t >&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (const ::std::basic_string< wchar_t >&,
                              ::xercesc::DOMErrorHandler&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    // Read from std::istream.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (::std::istream&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (::std::istream&,
                              ::xsd::cxx::xml::error_handler< wchar_t >&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (::std::istream&,
                              ::xercesc::DOMErrorHandler&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (::std::istream&,
                              const ::std::basic_string< wchar_t >& id,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (::std::istream&,
                              const ::std::basic_string< wchar_t >& id,
                              ::xsd::cxx::xml::error_handler< wchar_t >&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (::std::istream&,
                              const ::std::basic_string< wchar_t >& id,
                              ::xercesc::DOMErrorHandler&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    // Read from InputSource.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (const ::xercesc::DOMInputSource&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (const ::xercesc::DOMInputSource&,
                              ::xsd::cxx::xml::error_handler< wchar_t >&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (const ::xercesc::DOMInputSource&,
                              ::xercesc::DOMErrorHandler&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    // Read from DOM.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType >
    RegisterPrincipalRequest (const ::xercesc::DOMDocument&,
                              ::xml_schema::flags = 0,
                              const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    // Read from a URI or a local file.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (const ::std::basic_string< wchar_t >&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (const ::std::basic_string< wchar_t >&,
                               ::xsd::cxx::xml::error_handler< wchar_t >&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (const ::std::basic_string< wchar_t >&,
                               ::xercesc::DOMErrorHandler&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    // Read from std::istream.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (::std::istream&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (::std::istream&,
                               ::xsd::cxx::xml::error_handler< wchar_t >&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (::std::istream&,
                               ::xercesc::DOMErrorHandler&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (::std::istream&,
                               const ::std::basic_string< wchar_t >& id,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (::std::istream&,
                               const ::std::basic_string< wchar_t >& id,
                               ::xsd::cxx::xml::error_handler< wchar_t >&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (::std::istream&,
                               const ::std::basic_string< wchar_t >& id,
                               ::xercesc::DOMErrorHandler&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    // Read from InputSource.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (const ::xercesc::DOMInputSource&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (const ::xercesc::DOMInputSource&,
                               ::xsd::cxx::xml::error_handler< wchar_t >&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (const ::xercesc::DOMInputSource&,
                               ::xercesc::DOMErrorHandler&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());


    // Read from DOM.
    //

    ::std::auto_ptr< ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType >
    RegisterPrincipalResponse (const ::xercesc::DOMDocument&,
                               ::xml_schema::flags = 0,
                               const ::xsd::cxx::tree::properties< wchar_t >& = ::xsd::cxx::tree::properties< wchar_t > ());
  }
}

#include <iosfwd> // std::ostream&

#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMErrorHandler.hpp>
#include <xercesc/framework/XMLFormatter.hpp>

#include <xsd/cxx/xml/dom/auto-ptr.hxx>

namespace middleware
{
  namespace DelegatedProtocolSchema
  {
    // Serialize to an existing DOM instance.
    //
    void
    RegisterPrincipalRequest (::xercesc::DOMDocument&,
                              const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&,
                              ::xml_schema::flags = 0);


    // Serialize to a new DOM instance.
    //
    ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument >
    RegisterPrincipalRequest (const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&, 
                              const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                              ::xml_schema::flags = 0);


    // Serialize to XMLFormatTarget.
    //
    void
    RegisterPrincipalRequest (::xercesc::XMLFormatTarget&,
                              const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&, 
                              const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                              const ::std::basic_string< wchar_t >& = L"UTF-8",
                              ::xml_schema::flags = 0);


    void
    RegisterPrincipalRequest (::xercesc::XMLFormatTarget&,
                              const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&, 
                              const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                              ::xsd::cxx::xml::error_handler< wchar_t >&,
                              const ::std::basic_string< wchar_t >& = L"UTF-8",
                              ::xml_schema::flags = 0);

    void
    RegisterPrincipalRequest (::xercesc::XMLFormatTarget&,
                              const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&, 
                              const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                              ::xercesc::DOMErrorHandler&,
                              const ::std::basic_string< wchar_t >& = L"UTF-8",
                              ::xml_schema::flags = 0);


    // Serialize to std::ostream.
    //
    void
    RegisterPrincipalRequest (::std::ostream&,
                              const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&, 
                              const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                              const ::std::basic_string< wchar_t >& = L"UTF-8",
                              ::xml_schema::flags = 0);


    void
    RegisterPrincipalRequest (::std::ostream&,
                              const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&, 
                              const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                              ::xsd::cxx::xml::error_handler< wchar_t >&,
                              const ::std::basic_string< wchar_t >& = L"UTF-8",
                              ::xml_schema::flags = 0);

    void
    RegisterPrincipalRequest (::std::ostream&,
                              const ::middleware::DelegatedProtocolSchema::RegisterPrincipalRequestType&, 
                              const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                              ::xercesc::DOMErrorHandler&,
                              const ::std::basic_string< wchar_t >& = L"UTF-8",
                              ::xml_schema::flags = 0);


    void
    operator<< (::xercesc::DOMElement&,
                const RegisterPrincipalRequestType&);

    // Serialize to an existing DOM instance.
    //
    void
    RegisterPrincipalResponse (::xercesc::DOMDocument&,
                               const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&,
                               ::xml_schema::flags = 0);


    // Serialize to a new DOM instance.
    //
    ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument >
    RegisterPrincipalResponse (const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&, 
                               const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                               ::xml_schema::flags = 0);


    // Serialize to XMLFormatTarget.
    //
    void
    RegisterPrincipalResponse (::xercesc::XMLFormatTarget&,
                               const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&, 
                               const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                               const ::std::basic_string< wchar_t >& = L"UTF-8",
                               ::xml_schema::flags = 0);


    void
    RegisterPrincipalResponse (::xercesc::XMLFormatTarget&,
                               const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&, 
                               const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                               ::xsd::cxx::xml::error_handler< wchar_t >&,
                               const ::std::basic_string< wchar_t >& = L"UTF-8",
                               ::xml_schema::flags = 0);

    void
    RegisterPrincipalResponse (::xercesc::XMLFormatTarget&,
                               const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&, 
                               const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                               ::xercesc::DOMErrorHandler&,
                               const ::std::basic_string< wchar_t >& = L"UTF-8",
                               ::xml_schema::flags = 0);


    // Serialize to std::ostream.
    //
    void
    RegisterPrincipalResponse (::std::ostream&,
                               const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&, 
                               const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                               const ::std::basic_string< wchar_t >& = L"UTF-8",
                               ::xml_schema::flags = 0);


    void
    RegisterPrincipalResponse (::std::ostream&,
                               const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&, 
                               const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                               ::xsd::cxx::xml::error_handler< wchar_t >&,
                               const ::std::basic_string< wchar_t >& = L"UTF-8",
                               ::xml_schema::flags = 0);

    void
    RegisterPrincipalResponse (::std::ostream&,
                               const ::middleware::DelegatedProtocolSchema::RegisterPrincipalResponseType&, 
                               const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >&,
                               ::xercesc::DOMErrorHandler&,
                               const ::std::basic_string< wchar_t >& = L"UTF-8",
                               ::xml_schema::flags = 0);


    void
    operator<< (::xercesc::DOMElement&,
                const RegisterPrincipalResponseType&);
  }
}

#include <xsd/cxx/post.hxx>

// Begin epilogue.
//
//
// End epilogue.

#endif // DELEGATED_SCHEMA_SAML_PROTOCOL_HXX