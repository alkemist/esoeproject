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

// Begin prologue.
//
//
// End prologue.

#include <xsd/cxx/pre.hxx>

#include "lxacml-schema-metadata.hxx"

namespace middleware
{
  namespace lxacmlPDPSchema
  {
    // LXACMLPDPDescriptorType
    // 

    const LXACMLPDPDescriptorType::AuthzService::container& LXACMLPDPDescriptorType::
    AuthzService () const
    {
      return this->_xsd_AuthzService_;
    }

    LXACMLPDPDescriptorType::AuthzService::container& LXACMLPDPDescriptorType::
    AuthzService ()
    {
      return this->_xsd_AuthzService_;
    }

    void LXACMLPDPDescriptorType::
    AuthzService (const AuthzService::container& AuthzService)
    {
      this->_xsd_AuthzService_ = AuthzService;
    }

    const LXACMLPDPDescriptorType::AssertionIDRequestService::container& LXACMLPDPDescriptorType::
    AssertionIDRequestService () const
    {
      return this->_xsd_AssertionIDRequestService_;
    }

    LXACMLPDPDescriptorType::AssertionIDRequestService::container& LXACMLPDPDescriptorType::
    AssertionIDRequestService ()
    {
      return this->_xsd_AssertionIDRequestService_;
    }

    void LXACMLPDPDescriptorType::
    AssertionIDRequestService (const AssertionIDRequestService::container& AssertionIDRequestService)
    {
      this->_xsd_AssertionIDRequestService_ = AssertionIDRequestService;
    }

    const LXACMLPDPDescriptorType::NameIDFormat::container& LXACMLPDPDescriptorType::
    NameIDFormat () const
    {
      return this->_xsd_NameIDFormat_;
    }

    LXACMLPDPDescriptorType::NameIDFormat::container& LXACMLPDPDescriptorType::
    NameIDFormat ()
    {
      return this->_xsd_NameIDFormat_;
    }

    void LXACMLPDPDescriptorType::
    NameIDFormat (const NameIDFormat::container& NameIDFormat)
    {
      this->_xsd_NameIDFormat_ = NameIDFormat;
    }
  }
}

#include <xsd/cxx/xml/dom/elements.hxx>
#include <xsd/cxx/xml/dom/parser.hxx>

#include <xsd/cxx/tree/type-factory-map.hxx>

namespace _xsd
{
  static
  ::xsd::cxx::tree::type_factory_plate< 0, wchar_t >
  type_factory_plate_init;
}

namespace middleware
{
  namespace lxacmlPDPSchema
  {
    // LXACMLPDPDescriptorType
    //

    LXACMLPDPDescriptorType::
    LXACMLPDPDescriptorType ()
    : ::saml2::metadata::RoleDescriptorType (),
    _xsd_AuthzService_ (::xml_schema::flags (), this),
    _xsd_AssertionIDRequestService_ (::xml_schema::flags (), this),
    _xsd_NameIDFormat_ (::xml_schema::flags (), this)
    {
    }

    LXACMLPDPDescriptorType::
    LXACMLPDPDescriptorType (const protocolSupportEnumeration::type& _xsd_protocolSupportEnumeration)
    : ::saml2::metadata::RoleDescriptorType (_xsd_protocolSupportEnumeration),
    _xsd_AuthzService_ (::xml_schema::flags (), this),
    _xsd_AssertionIDRequestService_ (::xml_schema::flags (), this),
    _xsd_NameIDFormat_ (::xml_schema::flags (), this)
    {
    }

    LXACMLPDPDescriptorType::
    LXACMLPDPDescriptorType (const LXACMLPDPDescriptorType& _xsd_LXACMLPDPDescriptorType,
                             ::xml_schema::flags f,
                             ::xml_schema::type* c)
    : ::saml2::metadata::RoleDescriptorType (_xsd_LXACMLPDPDescriptorType, f, c),
    _xsd_AuthzService_ (_xsd_LXACMLPDPDescriptorType._xsd_AuthzService_,
                        f | ::xml_schema::flags::not_root,
                        this),
    _xsd_AssertionIDRequestService_ (_xsd_LXACMLPDPDescriptorType._xsd_AssertionIDRequestService_,
                                     f | ::xml_schema::flags::not_root,
                                     this),
    _xsd_NameIDFormat_ (_xsd_LXACMLPDPDescriptorType._xsd_NameIDFormat_,
                        f | ::xml_schema::flags::not_root,
                        this)
    {
    }

    LXACMLPDPDescriptorType::
    LXACMLPDPDescriptorType (const ::xercesc::DOMElement& e,
                             ::xml_schema::flags f,
                             ::xml_schema::type* c)
    : ::saml2::metadata::RoleDescriptorType (e, f, c),
    _xsd_AuthzService_ (f | ::xml_schema::flags::not_root, this),
    _xsd_AssertionIDRequestService_ (f | ::xml_schema::flags::not_root, this),
    _xsd_NameIDFormat_ (f | ::xml_schema::flags::not_root, this)
    {
      parse (e, f);
    }

    void LXACMLPDPDescriptorType::
    parse (const ::xercesc::DOMElement& e, ::xml_schema::flags f)
    {
      ::xsd::cxx::xml::dom::parser< wchar_t > p (e);

      while (p.more_elements ())
      {
        const ::xsd::cxx::xml::dom::element< wchar_t > e (p.next_element ());

        // AuthzService
        //
        {
          ::xsd::cxx::tree::type_factory_map< wchar_t >& tfm (
            ::xsd::cxx::tree::type_factory_map_instance< 0, wchar_t > ());

          ::std::auto_ptr< AuthzService::type > r (
            tfm.create< AuthzService::type > (
              L"AuthzService",
              L"urn:oasis:names:tc:SAML:2.0:metadata",
              true,
              true,
              e,
              f | ::xml_schema::flags::not_root,
              this));

          if (r.get () != 0)
          {
            this->AuthzService ().push_back (r);
            continue;
          }
        }

        // AssertionIDRequestService
        //
        {
          ::xsd::cxx::tree::type_factory_map< wchar_t >& tfm (
            ::xsd::cxx::tree::type_factory_map_instance< 0, wchar_t > ());

          ::std::auto_ptr< AssertionIDRequestService::type > r (
            tfm.create< AssertionIDRequestService::type > (
              L"AssertionIDRequestService",
              L"urn:oasis:names:tc:SAML:2.0:metadata",
              true,
              true,
              e,
              f | ::xml_schema::flags::not_root,
              this));

          if (r.get () != 0)
          {
            this->AssertionIDRequestService ().push_back (r);
            continue;
          }
        }

        // NameIDFormat
        //
        {
          ::xsd::cxx::tree::type_factory_map< wchar_t >& tfm (
            ::xsd::cxx::tree::type_factory_map_instance< 0, wchar_t > ());

          ::std::auto_ptr< NameIDFormat::type > r (
            tfm.create< NameIDFormat::type > (
              L"NameIDFormat",
              L"urn:oasis:names:tc:SAML:2.0:metadata",
              true,
              true,
              e,
              f | ::xml_schema::flags::not_root,
              this));

          if (r.get () != 0)
          {
            this->NameIDFormat ().push_back (r);
            continue;
          }
        }
      }
    }

    LXACMLPDPDescriptorType* LXACMLPDPDescriptorType::
    _clone (::xml_schema::flags f,
            ::xml_schema::type* c) const
    {
      return new LXACMLPDPDescriptorType (*this, f, c);
    }

    static
    ::xsd::cxx::tree::type_factory_initializer< 0, wchar_t, LXACMLPDPDescriptorType >
    _xsd_LXACMLPDPDescriptorType_type_factory_init (
      L"LXACMLPDPDescriptorType http://www.qut.com/middleware/lxacmlPDPSchema");
  }
}

#include <istream>
#include <xercesc/framework/Wrapper4InputSource.hpp>
#include <xsd/cxx/xml/sax/std-input-source.hxx>
#include <xsd/cxx/tree/error-handler.hxx>

namespace middleware
{
  namespace lxacmlPDPSchema
  {
    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (const ::std::basic_string< wchar_t >& u,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0,
        (f & ::xml_schema::flags::keep_dom) == 0);

      ::xsd::cxx::tree::error_handler< wchar_t > h;

      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::xsd::cxx::xml::dom::parse< wchar_t > (u, h, p, f));

      h.throw_if_failed< ::xsd::cxx::tree::parsing< wchar_t > > ();

      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (
        static_cast< const ::xercesc::DOMDocument& > (*d), f);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (const ::std::basic_string< wchar_t >& u,
                         ::xsd::cxx::xml::error_handler< wchar_t >& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0,
        (f & ::xml_schema::flags::keep_dom) == 0);

      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::xsd::cxx::xml::dom::parse< wchar_t > (u, h, p, f));

      if (!d)
      {
        throw ::xsd::cxx::tree::parsing< wchar_t > ();
      }

      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (
        static_cast< const ::xercesc::DOMDocument& > (*d), f);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (const ::std::basic_string< wchar_t >& u,
                         ::xercesc::DOMErrorHandler& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::xsd::cxx::xml::dom::parse< wchar_t > (u, h, p, f));

      if (!d)
      {
        throw ::xsd::cxx::tree::parsing< wchar_t > ();
      }

      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (
        static_cast< const ::xercesc::DOMDocument& > (*d), f);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (::std::istream& is,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0,
        (f & ::xml_schema::flags::keep_dom) == 0);

      ::xsd::cxx::xml::sax::std_input_source isrc (is);
      ::xercesc::Wrapper4InputSource wrap (&isrc, false);
      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (wrap, f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (::std::istream& is,
                         ::xsd::cxx::xml::error_handler< wchar_t >& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0,
        (f & ::xml_schema::flags::keep_dom) == 0);

      ::xsd::cxx::xml::sax::std_input_source isrc (is);
      ::xercesc::Wrapper4InputSource wrap (&isrc, false);
      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (wrap, h, f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (::std::istream& is,
                         ::xercesc::DOMErrorHandler& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::sax::std_input_source isrc (is);
      ::xercesc::Wrapper4InputSource wrap (&isrc, false);
      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (wrap, h, f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (::std::istream& is,
                         const ::std::basic_string< wchar_t >& sid,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0,
        (f & ::xml_schema::flags::keep_dom) == 0);

      ::xsd::cxx::xml::sax::std_input_source isrc (is, sid);
      ::xercesc::Wrapper4InputSource wrap (&isrc, false);
      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (wrap, f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (::std::istream& is,
                         const ::std::basic_string< wchar_t >& sid,
                         ::xsd::cxx::xml::error_handler< wchar_t >& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0,
        (f & ::xml_schema::flags::keep_dom) == 0);

      ::xsd::cxx::xml::sax::std_input_source isrc (is, sid);
      ::xercesc::Wrapper4InputSource wrap (&isrc, false);
      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (wrap, h, f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (::std::istream& is,
                         const ::std::basic_string< wchar_t >& sid,
                         ::xercesc::DOMErrorHandler& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::sax::std_input_source isrc (is, sid);
      ::xercesc::Wrapper4InputSource wrap (&isrc, false);
      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (wrap, h, f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (const ::xercesc::DOMInputSource& i,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::tree::error_handler< wchar_t > h;

      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::xsd::cxx::xml::dom::parse< wchar_t > (i, h, p, f));

      h.throw_if_failed< ::xsd::cxx::tree::parsing< wchar_t > > ();

      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (
        static_cast< const ::xercesc::DOMDocument& > (*d), f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (const ::xercesc::DOMInputSource& i,
                         ::xsd::cxx::xml::error_handler< wchar_t >& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::xsd::cxx::xml::dom::parse< wchar_t > (i, h, p, f));

      if (!d)
      {
        throw ::xsd::cxx::tree::parsing< wchar_t > ();
      }

      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (
        static_cast< const ::xercesc::DOMDocument& > (*d), f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (const ::xercesc::DOMInputSource& i,
                         ::xercesc::DOMErrorHandler& h,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >& p)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::xsd::cxx::xml::dom::parse< wchar_t > (i, h, p, f));

      if (!d)
      {
        throw ::xsd::cxx::tree::parsing< wchar_t > ();
      }

      return ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (
        static_cast< const ::xercesc::DOMDocument& > (*d), f, p);
    }

    ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType >
    LXACMLPDPDescriptor (const ::xercesc::DOMDocument& d,
                         ::xml_schema::flags f,
                         const ::xsd::cxx::tree::properties< wchar_t >&)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > c (
        (f & ::xml_schema::flags::keep_dom) 
        ? static_cast< ::xercesc::DOMDocument* > (d.cloneNode (true))
        : 0);

      const ::xsd::cxx::xml::dom::element< wchar_t > e (
        c.get ()
        ? *c->getDocumentElement ()
        : *d.getDocumentElement ());

      ::xsd::cxx::tree::type_factory_map< wchar_t >& tfm (
        ::xsd::cxx::tree::type_factory_map_instance< 0, wchar_t > ());

      ::std::auto_ptr< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType > r (
        tfm.create< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType > (
          L"LXACMLPDPDescriptor",
          L"http://www.qut.com/middleware/lxacmlPDPSchema",
          true,
          true,
          e,
          f,
          0));

      if (r.get () != 0)
      {
        if (f & ::xml_schema::flags::keep_dom) c.release ();
        return r;
      }

      throw ::xsd::cxx::tree::unexpected_element < wchar_t > (
        e.name (),
        e.namespace_ (),
        L"LXACMLPDPDescriptor",
        L"http://www.qut.com/middleware/lxacmlPDPSchema");
    }
  }
}

#include <ostream>
#include <xsd/cxx/xml/dom/elements.hxx>
#include <xsd/cxx/xml/dom/serialization.hxx>
#include <xsd/cxx/tree/error-handler.hxx>

#include <xsd/cxx/tree/type-serializer-map.hxx>

namespace _xsd
{
  static
  ::xsd::cxx::tree::type_serializer_plate< 0, wchar_t >
  type_serializer_plate_init;
}

namespace middleware
{
  namespace lxacmlPDPSchema
  {
    void
    LXACMLPDPDescriptor (::xercesc::DOMDocument& d,
                         const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         ::xml_schema::flags)
    {
      ::xsd::cxx::xml::dom::element< wchar_t > e (*d.getDocumentElement ());

      ::xsd::cxx::tree::type_serializer_map< wchar_t >& tsm (
        ::xsd::cxx::tree::type_serializer_map_instance< 0, wchar_t > ());

      tsm.serialize< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType > (
        L"LXACMLPDPDescriptor",
        L"http://www.qut.com/middleware/lxacmlPDPSchema",
        e,
        s);
    }

    ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument >
    LXACMLPDPDescriptor (const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >& m,
                         ::xml_schema::flags f)
    {
      try
      {
        ::xsd::cxx::tree::type_serializer_map< wchar_t >& tsm (
          ::xsd::cxx::tree::type_serializer_map_instance< 0, wchar_t > ());

        ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
          tsm.serialize< ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType > (
            L"LXACMLPDPDescriptor",
            L"http://www.qut.com/middleware/lxacmlPDPSchema",
            m,
            s,
            f));
        ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (*d, s, f);
        return d;
      }
      catch (const ::xsd::cxx::xml::dom::mapping< wchar_t >& e)
      {
        throw ::xsd::cxx::tree::no_namespace_mapping< wchar_t > (e.name ());
      }
      catch (const ::xsd::cxx::xml::dom::xsi_already_in_use&)
      {
        throw ::xsd::cxx::tree::xsi_already_in_use< wchar_t > ();
      }
    }

    void
    LXACMLPDPDescriptor (::xercesc::XMLFormatTarget& t,
                         const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >& m,
                         const ::std::basic_string< wchar_t >& e,
                         ::xml_schema::flags f)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (s, m, f));

      ::xsd::cxx::tree::error_handler< wchar_t > h;

      if (!::xsd::cxx::xml::dom::serialize (t, *d, e, h, f))
      {
        h.throw_if_failed< ::xsd::cxx::tree::serialization< wchar_t > > ();
      }
    }

    void
    LXACMLPDPDescriptor (::xercesc::XMLFormatTarget& t,
                         const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >& m,
                         ::xsd::cxx::xml::error_handler< wchar_t >& h,
                         const ::std::basic_string< wchar_t >& e,
                         ::xml_schema::flags f)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (s, m, f));
      if (!::xsd::cxx::xml::dom::serialize (t, *d, e, h, f))
      {
        throw ::xsd::cxx::tree::serialization< wchar_t > ();
      }
    }

    void
    LXACMLPDPDescriptor (::xercesc::XMLFormatTarget& t,
                         const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >& m,
                         ::xercesc::DOMErrorHandler& h,
                         const ::std::basic_string< wchar_t >& e,
                         ::xml_schema::flags f)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (s, m, f));
      if (!::xsd::cxx::xml::dom::serialize (t, *d, e, h, f))
      {
        throw ::xsd::cxx::tree::serialization< wchar_t > ();
      }
    }

    void
    LXACMLPDPDescriptor (::std::ostream& o,
                         const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >& m,
                         const ::std::basic_string< wchar_t >& e,
                         ::xml_schema::flags f)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0);

      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (s, m, f));

      ::xsd::cxx::tree::error_handler< wchar_t > h;

      ::xsd::cxx::xml::dom::ostream_format_target t (o);
      if (!::xsd::cxx::xml::dom::serialize (t, *d, e, h, f))
      {
        h.throw_if_failed< ::xsd::cxx::tree::serialization< wchar_t > > ();
      }
    }

    void
    LXACMLPDPDescriptor (::std::ostream& o,
                         const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >& m,
                         ::xsd::cxx::xml::error_handler< wchar_t >& h,
                         const ::std::basic_string< wchar_t >& e,
                         ::xml_schema::flags f)
    {
      ::xsd::cxx::xml::auto_initializer i (
        (f & ::xml_schema::flags::dont_initialize) == 0);

      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (s, m, f));
      ::xsd::cxx::xml::dom::ostream_format_target t (o);
      if (!::xsd::cxx::xml::dom::serialize (t, *d, e, h, f))
      {
        throw ::xsd::cxx::tree::serialization< wchar_t > ();
      }
    }

    void
    LXACMLPDPDescriptor (::std::ostream& o,
                         const ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptorType& s,
                         const ::xsd::cxx::xml::dom::namespace_infomap< wchar_t >& m,
                         ::xercesc::DOMErrorHandler& h,
                         const ::std::basic_string< wchar_t >& e,
                         ::xml_schema::flags f)
    {
      ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument > d (
        ::middleware::lxacmlPDPSchema::LXACMLPDPDescriptor (s, m, f));
      ::xsd::cxx::xml::dom::ostream_format_target t (o);
      if (!::xsd::cxx::xml::dom::serialize (t, *d, e, h, f))
      {
        throw ::xsd::cxx::tree::serialization< wchar_t > ();
      }
    }

    void
    operator<< (::xercesc::DOMElement& e,
                const LXACMLPDPDescriptorType& i)
    {
      e << static_cast< const ::saml2::metadata::RoleDescriptorType& > (i);

      {
        ::xsd::cxx::tree::type_serializer_map< wchar_t >& tsm (
          ::xsd::cxx::tree::type_serializer_map_instance< 0, wchar_t > ());

        for (LXACMLPDPDescriptorType::AuthzService::const_iterator
             b (i.AuthzService ().begin ()), n (i.AuthzService ().end ());
             b != n; ++b)
        {
          tsm.serialize< LXACMLPDPDescriptorType::AuthzService::type > (
            L"AuthzService",
            L"urn:oasis:names:tc:SAML:2.0:metadata",
            true,
            true,
            e,
            *b);
        }
      }

      {
        ::xsd::cxx::tree::type_serializer_map< wchar_t >& tsm (
          ::xsd::cxx::tree::type_serializer_map_instance< 0, wchar_t > ());

        for (LXACMLPDPDescriptorType::AssertionIDRequestService::const_iterator
             b (i.AssertionIDRequestService ().begin ()), n (i.AssertionIDRequestService ().end ());
             b != n; ++b)
        {
          tsm.serialize< LXACMLPDPDescriptorType::AssertionIDRequestService::type > (
            L"AssertionIDRequestService",
            L"urn:oasis:names:tc:SAML:2.0:metadata",
            true,
            true,
            e,
            *b);
        }
      }

      {
        ::xsd::cxx::tree::type_serializer_map< wchar_t >& tsm (
          ::xsd::cxx::tree::type_serializer_map_instance< 0, wchar_t > ());

        for (LXACMLPDPDescriptorType::NameIDFormat::const_iterator
             b (i.NameIDFormat ().begin ()), n (i.NameIDFormat ().end ());
             b != n; ++b)
        {
          tsm.serialize< LXACMLPDPDescriptorType::NameIDFormat::type > (
            L"NameIDFormat",
            L"urn:oasis:names:tc:SAML:2.0:metadata",
            true,
            true,
            e,
            *b);
        }
      }
    }

    static
    ::xsd::cxx::tree::type_serializer_initializer< 0, wchar_t, LXACMLPDPDescriptorType >
    _xsd_LXACMLPDPDescriptorType_type_serializer_init (
      L"LXACMLPDPDescriptorType",
      L"http://www.qut.com/middleware/lxacmlPDPSchema");
  }
}

#include <xsd/cxx/post.hxx>

// Begin epilogue.
//
//
// End epilogue.
