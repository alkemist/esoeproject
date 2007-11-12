// Copyright (C) 2005-2007 Code Synthesis Tools CC
//
// This program was generated by CodeSynthesis XSD, an XML Schema to
// C++ data binding compiler.
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

/**
 * @file
 * @brief Generated from ../saml2cpp/schema/lxacml-schema-grouptarget.xsd.
 */

#ifndef LXACML_SCHEMA_GROUPTARGET_HXX
#define LXACML_SCHEMA_GROUPTARGET_HXX

// Begin prologue.
//
#include "saml2/SAML2Defs.h"
//
// End prologue.

#include <xsd/cxx/version.hxx>

#if (XSD_INT_VERSION != 3000000L)
#error XSD runtime version mismatch
#endif

#include <xsd/cxx/pre.hxx>

#ifndef XSD_USE_WCHAR
#define XSD_USE_WCHAR
#endif

#ifndef XSD_CXX_TREE_USE_WCHAR
#define XSD_CXX_TREE_USE_WCHAR
#endif

#include "saml2/xsd/xml-schema.hxx"

// Forward declarations.
//
namespace middleware
{
  namespace lxacmlGroupTargetSchema
  {
    class GroupTargetType;
  }
}


#include <memory>    // std::auto_ptr
#include <algorithm> // std::binary_search

#include <xsd/cxx/tree/exceptions.hxx>
#include <xsd/cxx/tree/elements.hxx>
#include <xsd/cxx/tree/containers.hxx>
#include <xsd/cxx/tree/list.hxx>

#include <xsd/cxx/xml/dom/parsing-header.hxx>

#include <xsd/cxx/xml/dom/auto-ptr.hxx>
#include <xsd/cxx/tree/containers-wildcard.hxx>

namespace middleware
{
  /**
   * @brief C++ namespace for the %http://www.qut.com/middleware/lxacmlGroupTargetSchema
   * schema namespace.
   */
  namespace lxacmlGroupTargetSchema
  {
    /**
     * @brief Class corresponding to the %GroupTargetType schema type.
     *
     * @nosubgrouping
     */
    class SAML2EXPORT GroupTargetType: public ::xml_schema::type
    {
      public:
      /**
       * @name GroupTargetID
       *
       * @brief Accessor and modifier functions for the %GroupTargetID
       * required element.
       */
      //@{

      /**
       * @brief Element type.
       */
      typedef ::xml_schema::string GroupTargetID_type;

      /**
       * @brief Element traits type.
       */
      typedef ::xsd::cxx::tree::traits< GroupTargetID_type, wchar_t > GroupTargetID_traits;

      /**
       * @brief Return a read-only (constant) reference to the element.
       *
       * @return A constant reference to the element.
       */
      const GroupTargetID_type&
      GroupTargetID () const;

      /**
       * @brief Return a read-write reference to the element.
       *
       * @return A reference to the element.
       */
      GroupTargetID_type&
      GroupTargetID ();

      /**
       * @brief Set the element value.
       *
       * @param x A new value to set.
       *
       * This function makes a copy of its argument and sets it as
       * the new value of the element.
       */
      void
      GroupTargetID (const GroupTargetID_type& x);

      /**
       * @brief Set the element value without copying.
       *
       * @param p A new value to use.
       *
       * This function will try to use the passed value directly instead
       * of making a copy.
       */
      void
      GroupTargetID (::std::auto_ptr< GroupTargetID_type > p);

      //@}

      /**
       * @name AuthzTarget
       *
       * @brief Accessor and modifier functions for the %AuthzTarget
       * sequence element.
       */
      //@{

      /**
       * @brief Element type.
       */
      typedef ::xml_schema::string AuthzTarget_type;

      /**
       * @brief Element sequence container type.
       */
      typedef ::xsd::cxx::tree::sequence< AuthzTarget_type > AuthzTarget_sequence;

      /**
       * @brief Element iterator type.
       */
      typedef AuthzTarget_sequence::iterator AuthzTarget_iterator;

      /**
       * @brief Element constant iterator type.
       */
      typedef AuthzTarget_sequence::const_iterator AuthzTarget_const_iterator;

      /**
       * @brief Element traits type.
       */
      typedef ::xsd::cxx::tree::traits< AuthzTarget_type, wchar_t > AuthzTarget_traits;

      /**
       * @brief Return a read-only (constant) reference to the element
       * sequence.
       *
       * @return A constant reference to the sequence container.
       */
      const AuthzTarget_sequence&
      AuthzTarget () const;

      /**
       * @brief Return a read-write reference to the element sequence.
       *
       * @return A reference to the sequence container.
       */
      AuthzTarget_sequence&
      AuthzTarget ();

      /**
       * @brief Copy elements from a given sequence.
       *
       * @param s A sequence to copy elements from.
       *
       * For each element in @a s this function makes a copy and adds it 
       * to the sequence. Note that this operation completely changes the 
       * sequence and all old elements will be lost.
       */
      void
      AuthzTarget (const AuthzTarget_sequence& s);

      //@}

      /**
       * @name Constructors
       */
      //@{

      /**
       * @brief Default constructor.
       *
       * Note that this constructor leaves required elements and
       * attributes uninitialized.
       */
      GroupTargetType ();

      /**
       * @brief Construct an instance from the ultimate base and
       * initializers for required elements and attributes.
       */
      GroupTargetType (const GroupTargetID_type&);

      /**
       * @brief Construct an instance from a DOM element.
       *
       * @param e A DOM element to extract the data from.
       * @param f Flags to construct the new instance with.
       * @param c A pointer to the object that will contain the new
       * instance.
       */
      GroupTargetType (const ::xercesc::DOMElement& e,
                       ::xml_schema::flags f = 0,
                       ::xml_schema::type* c = 0);

      /**
       * @brief Copy constructor.
       *
       * @param x An instance to make a copy of.
       * @param f Flags to construct the copy with.
       * @param c A pointer to the object that will contain the copy.
       *
       * For polymorphic object models use the _clone function instead.
       */
      GroupTargetType (const GroupTargetType& x,
                       ::xml_schema::flags f = 0,
                       ::xml_schema::type* c = 0);

      /**
       * @brief Copy the object polymorphically.
       *
       * @param f Flags to construct the copy with.
       * @param c A pointer to the object that will contain the copy.
       * @return A pointer to the dynamically allocated copy.
       *
       * This function ensures that the dynamic type of an instance is
       * used for copying and should be used for polymorphic object
       * models instead of the copy constructor.
       */
      virtual GroupTargetType*
      _clone (::xml_schema::flags f = 0,
              ::xml_schema::type* c = 0) const;

      //@}

      // Implementation.
      //
      protected:
      void
      parse (::xsd::cxx::xml::dom::parser< wchar_t >&,
             ::xml_schema::flags);

      private:
      ::xsd::cxx::tree::one< GroupTargetID_type > GroupTargetID_;
      AuthzTarget_sequence AuthzTarget_;
    };
  }
}

#include <iosfwd>

#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMInputSource.hpp>
#include <xercesc/dom/DOMErrorHandler.hpp>

namespace middleware
{
  namespace lxacmlGroupTargetSchema
  {
    /**
     * @name Parsing functions for the %GroupTarget document root.
     */
    //@{

    /**
     * @brief Parse a URI or a local file.
     *
     * @param uri A URI or a local file name.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function uses exceptions to report parsing errors.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (const ::std::wstring& uri,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a URI or a local file with an error handler.
     *
     * @param uri A URI or a local file name.
     * @param eh An error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (const ::std::wstring& uri,
                 ::xml_schema::error_handler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a URI or a local file with a Xerces-C++ DOM error
     * handler.
     *
     * @param uri A URI or a local file name.
     * @param eh A Xerces-C++ DOM error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (const ::std::wstring& uri,
                 ::xercesc::DOMErrorHandler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a standard input stream.
     *
     * @param is A standrad input stream.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function uses exceptions to report parsing errors.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (::std::istream& is,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a standard input stream with an error handler.
     *
     * @param is A standrad input stream.
     * @param eh An error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (::std::istream& is,
                 ::xml_schema::error_handler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a standard input stream with a Xerces-C++ DOM error
     * handler.
     *
     * @param is A standrad input stream.
     * @param eh A Xerces-C++ DOM error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (::std::istream& is,
                 ::xercesc::DOMErrorHandler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a standard input stream with a resource id.
     *
     * @param is A standrad input stream.
     * @param id A resource id.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * The resource id is used to identify the document being parsed in
     * diagnostics as well as to resolve relative paths.
     *
     * This function uses exceptions to report parsing errors.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (::std::istream& is,
                 const ::std::wstring& id,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a standard input stream with a resource id and an
     * error handler.
     *
     * @param is A standrad input stream.
     * @param id A resource id.
     * @param eh An error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * The resource id is used to identify the document being parsed in
     * diagnostics as well as to resolve relative paths.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (::std::istream& is,
                 const ::std::wstring& id,
                 ::xml_schema::error_handler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a standard input stream with a resource id and a
     * Xerces-C++ DOM error handler.
     *
     * @param is A standrad input stream.
     * @param id A resource id.
     * @param eh A Xerces-C++ DOM error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * The resource id is used to identify the document being parsed in
     * diagnostics as well as to resolve relative paths.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (::std::istream& is,
                 const ::std::wstring& id,
                 ::xercesc::DOMErrorHandler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a Xerces-C++ DOM input source.
     *
     * @param is A Xerces-C++ DOM input source.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function uses exceptions to report parsing errors.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (const ::xercesc::DOMInputSource& is,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a Xerces-C++ DOM input source with an error handler.
     *
     * @param is A Xerces-C++ DOM input source.
     * @param eh An error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (const ::xercesc::DOMInputSource& is,
                 ::xml_schema::error_handler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a Xerces-C++ DOM input source with a Xerces-C++ DOM
     * error handler.
     *
     * @param is A Xerces-C++ DOM input source.
     * @param eh A Xerces-C++ DOM error handler.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function reports parsing errors by calling the error handler.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (const ::xercesc::DOMInputSource& is,
                 ::xercesc::DOMErrorHandler& eh,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a Xerces-C++ DOM document.
     *
     * @param d A Xerces-C++ DOM document.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (const ::xercesc::DOMDocument& d,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    /**
     * @brief Parse a Xerces-C++ DOM document.
     *
     * @param d A pointer to the Xerces-C++ DOM document.
     * @param f Parsing flags.
     * @param p Parsing properties. 
     * @return A pointer to the root of the object model.
     *
     * This function is normally used together with the keep_dom and
     * own_dom parsing flags to assign ownership of the DOM document
     * to the object model.
     */
    SAML2EXPORT
    ::std::auto_ptr< ::middleware::lxacmlGroupTargetSchema::GroupTargetType >
    GroupTarget (::xercesc::DOMDocument* d,
                 ::xml_schema::flags f = 0,
                 const ::xml_schema::properties& p = ::xml_schema::properties ());

    //@}
  }
}

#include <iosfwd>

#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMErrorHandler.hpp>
#include <xercesc/framework/XMLFormatter.hpp>

#include <xsd/cxx/xml/dom/auto-ptr.hxx>

namespace middleware
{
  namespace lxacmlGroupTargetSchema
  {
    /**
     * @name Serialization functions for the %GroupTarget document root.
     */
    //@{

    /**
     * @brief Serialize to a standard output stream.
     *
     * @param os A standrad output stream.
     * @param x An object model to serialize.
     * @param m A namespace information map.
     * @param e A character encoding to produce XML in.
     * @param f Serialization flags.
     *
     * This function uses exceptions to report serialization errors.
     */
    SAML2EXPORT
    void
    GroupTarget (::std::ostream& os,
                 const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x, 
                 const ::xml_schema::namespace_infomap& m,
                 const ::std::wstring& e = L"UTF-8",
                 ::xml_schema::flags f = 0);

    /**
     * @brief Serialize to a standard output stream with an error handler.
     *
     * @param os A standrad output stream.
     * @param x An object model to serialize.
     * @param m A namespace information map.
     * @param eh An error handler.
     * @param e A character encoding to produce XML in.
     * @param f Serialization flags.
     *
     * This function reports serialization errors by calling the error
     * handler.
     */
    SAML2EXPORT
    void
    GroupTarget (::std::ostream& os,
                 const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x, 
                 const ::xml_schema::namespace_infomap& m,
                 ::xml_schema::error_handler& eh,
                 const ::std::wstring& e = L"UTF-8",
                 ::xml_schema::flags f = 0);

    /**
     * @brief Serialize to a standard output stream with a Xerces-C++ DOM
     * error handler.
     *
     * @param os A standrad output stream.
     * @param x An object model to serialize.
     * @param m A namespace information map.
     * @param eh A Xerces-C++ DOM error handler.
     * @param e A character encoding to produce XML in.
     * @param f Serialization flags.
     *
     * This function reports serialization errors by calling the error
     * handler.
     */
    SAML2EXPORT
    void
    GroupTarget (::std::ostream& os,
                 const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x, 
                 const ::xml_schema::namespace_infomap& m,
                 ::xercesc::DOMErrorHandler& eh,
                 const ::std::wstring& e = L"UTF-8",
                 ::xml_schema::flags f = 0);

    /**
     * @brief Serialize to a Xerces-C++ XML format target.
     *
     * @param ft A Xerces-C++ XML format target.
     * @param x An object model to serialize.
     * @param m A namespace information map.
     * @param e A character encoding to produce XML in.
     * @param f Serialization flags.
     *
     * This function uses exceptions to report serialization errors.
     */
    SAML2EXPORT
    void
    GroupTarget (::xercesc::XMLFormatTarget& ft,
                 const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x, 
                 const ::xml_schema::namespace_infomap& m,
                 const ::std::wstring& e = L"UTF-8",
                 ::xml_schema::flags f = 0);

    /**
     * @brief Serialize to a Xerces-C++ XML format target with an error
     * handler.
     *
     * @param ft A Xerces-C++ XML format target.
     * @param x An object model to serialize.
     * @param m A namespace information map.
     * @param eh An error handler.
     * @param e A character encoding to produce XML in.
     * @param f Serialization flags.
     *
     * This function reports serialization errors by calling the error
     * handler.
     */
    SAML2EXPORT
    void
    GroupTarget (::xercesc::XMLFormatTarget& ft,
                 const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x, 
                 const ::xml_schema::namespace_infomap& m,
                 ::xml_schema::error_handler& eh,
                 const ::std::wstring& e = L"UTF-8",
                 ::xml_schema::flags f = 0);

    /**
     * @brief Serialize to a Xerces-C++ XML format target with a
     * Xerces-C++ DOM error handler.
     *
     * @param ft A Xerces-C++ XML format target.
     * @param x An object model to serialize.
     * @param m A namespace information map.
     * @param eh A Xerces-C++ DOM error handler.
     * @param e A character encoding to produce XML in.
     * @param f Serialization flags.
     *
     * This function reports serialization errors by calling the error
     * handler.
     */
    SAML2EXPORT
    void
    GroupTarget (::xercesc::XMLFormatTarget& ft,
                 const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x, 
                 const ::xml_schema::namespace_infomap& m,
                 ::xercesc::DOMErrorHandler& eh,
                 const ::std::wstring& e = L"UTF-8",
                 ::xml_schema::flags f = 0);

    /**
     * @brief Serialize to an existing Xerces-C++ DOM document.
     *
     * @param d A Xerces-C++ DOM document.
     * @param x An object model to serialize.
     * @param f Serialization flags.
     *
     * Note that it is your responsibility to create the DOM document
     * with the correct root element as well as set the necessary
     * namespace mapping attributes.
     */
    SAML2EXPORT
    void
    GroupTarget (::xercesc::DOMDocument& d,
                 const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x,
                 ::xml_schema::flags f = 0);

    /**
     * @brief Serialize to a new Xerces-C++ DOM document.
     *
     * @param x An object model to serialize.
     * @param m A namespace information map.
     * @param f Serialization flags.
     * @return A pointer to the new Xerces-C++ DOM document.
     */
    SAML2EXPORT
    ::xsd::cxx::xml::dom::auto_ptr< ::xercesc::DOMDocument >
    GroupTarget (const ::middleware::lxacmlGroupTargetSchema::GroupTargetType& x, 
                 const ::xml_schema::namespace_infomap& m,
                 ::xml_schema::flags f = 0);

    //@}

    SAML2EXPORT
    void
    operator<< (::xercesc::DOMElement&, const GroupTargetType&);
  }
}

#include <xsd/cxx/post.hxx>

// Begin epilogue.
//
//
// End epilogue.

#endif // LXACML_SCHEMA_GROUPTARGET_HXX