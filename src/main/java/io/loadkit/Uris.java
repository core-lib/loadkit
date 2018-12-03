/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.loadkit;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for URI encoding and decoding based on RFC 3986.
 *
 * <p>There are two types of encode methods:
 * <ul>
 * <li>{@code "encodeXyz"} -- these encode a specific URI component (e.g. path,
 * query) by percent encoding illegal characters, which includes non-US-ASCII
 * characters, and also characters that are otherwise illegal within the given
 * URI component type, as defined in RFC 3986. The effect of this method, with
 * regards to encoding, is comparable to using the multi-argument constructor
 * of {@link URI}.
 * <li>{@code "encode"} and {@code "encodeUriVariables"} -- these can be used
 * to encode URI variable values by percent encoding all characters that are
 * either illegal, or have any reserved meaning, anywhere within a URI.
 * </ul>
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986</a>
 * @since 3.0
 */
public abstract class Uris {

    /**
     * Encode the given URI scheme with the given encoding.
     *
     * @param scheme   the scheme to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded scheme
     */
    public static String encodeScheme(String scheme, String encoding) {
        return encode(scheme, encoding, Type.SCHEME);
    }

    /**
     * Encode the given URI scheme with the given encoding.
     *
     * @param scheme  the scheme to be encoded
     * @param charset the character encoding to encode to
     * @return the encoded scheme
     * @since 5.0
     */
    public static String encodeScheme(String scheme, Charset charset) {
        return encode(scheme, charset, Type.SCHEME);
    }

    /**
     * Encode the given URI authority with the given encoding.
     *
     * @param authority the authority to be encoded
     * @param encoding  the character encoding to encode to
     * @return the encoded authority
     */
    public static String encodeAuthority(String authority, String encoding) {
        return encode(authority, encoding, Type.AUTHORITY);
    }

    /**
     * Encode the given URI authority with the given encoding.
     *
     * @param authority the authority to be encoded
     * @param charset   the character encoding to encode to
     * @return the encoded authority
     * @since 5.0
     */
    public static String encodeAuthority(String authority, Charset charset) {
        return encode(authority, charset, Type.AUTHORITY);
    }

    /**
     * Encode the given URI user info with the given encoding.
     *
     * @param userInfo the user info to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded user info
     */
    public static String encodeUserInfo(String userInfo, String encoding) {
        return encode(userInfo, encoding, Type.USER_INFO);
    }

    /**
     * Encode the given URI user info with the given encoding.
     *
     * @param userInfo the user info to be encoded
     * @param charset  the character encoding to encode to
     * @return the encoded user info
     * @since 5.0
     */
    public static String encodeUserInfo(String userInfo, Charset charset) {
        return encode(userInfo, charset, Type.USER_INFO);
    }

    /**
     * Encode the given URI host with the given encoding.
     *
     * @param host     the host to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded host
     */
    public static String encodeHost(String host, String encoding) {
        return encode(host, encoding, Type.HOST_IPV4);
    }

    /**
     * Encode the given URI host with the given encoding.
     *
     * @param host    the host to be encoded
     * @param charset the character encoding to encode to
     * @return the encoded host
     * @since 5.0
     */
    public static String encodeHost(String host, Charset charset) {
        return encode(host, charset, Type.HOST_IPV4);
    }

    /**
     * Encode the given URI port with the given encoding.
     *
     * @param port     the port to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded port
     */
    public static String encodePort(String port, String encoding) {
        return encode(port, encoding, Type.PORT);
    }

    /**
     * Encode the given URI port with the given encoding.
     *
     * @param port    the port to be encoded
     * @param charset the character encoding to encode to
     * @return the encoded port
     * @since 5.0
     */
    public static String encodePort(String port, Charset charset) {
        return encode(port, charset, Type.PORT);
    }

    /**
     * Encode the given URI path with the given encoding.
     *
     * @param path     the path to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded path
     */
    public static String encodePath(String path, String encoding) {
        return encode(path, encoding, Type.PATH);
    }

    /**
     * Encode the given URI path with the given encoding.
     *
     * @param path    the path to be encoded
     * @param charset the character encoding to encode to
     * @return the encoded path
     * @since 5.0
     */
    public static String encodePath(String path, Charset charset) {
        return encode(path, charset, Type.PATH);
    }

    /**
     * Encode the given URI path segment with the given encoding.
     *
     * @param segment  the segment to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded segment
     */
    public static String encodePathSegment(String segment, String encoding) {
        return encode(segment, encoding, Type.PATH_SEGMENT);
    }

    /**
     * Encode the given URI path segment with the given encoding.
     *
     * @param segment the segment to be encoded
     * @param charset the character encoding to encode to
     * @return the encoded segment
     * @since 5.0
     */
    public static String encodePathSegment(String segment, Charset charset) {
        return encode(segment, charset, Type.PATH_SEGMENT);
    }

    /**
     * Encode the given URI query with the given encoding.
     *
     * @param query    the query to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded query
     */
    public static String encodeQuery(String query, String encoding) {
        return encode(query, encoding, Type.QUERY);
    }

    /**
     * Encode the given URI query with the given encoding.
     *
     * @param query   the query to be encoded
     * @param charset the character encoding to encode to
     * @return the encoded query
     * @since 5.0
     */
    public static String encodeQuery(String query, Charset charset) {
        return encode(query, charset, Type.QUERY);
    }

    /**
     * Encode the given URI query parameter with the given encoding.
     *
     * @param queryParam the query parameter to be encoded
     * @param encoding   the character encoding to encode to
     * @return the encoded query parameter
     */
    public static String encodeQueryParam(String queryParam, String encoding) {

        return encode(queryParam, encoding, Type.QUERY_PARAM);
    }

    /**
     * Encode the given URI query parameter with the given encoding.
     *
     * @param queryParam the query parameter to be encoded
     * @param charset    the character encoding to encode to
     * @return the encoded query parameter
     * @since 5.0
     */
    public static String encodeQueryParam(String queryParam, Charset charset) {
        return encode(queryParam, charset, Type.QUERY_PARAM);
    }

    /**
     * Encode the given URI fragment with the given encoding.
     *
     * @param fragment the fragment to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded fragment
     */
    public static String encodeFragment(String fragment, String encoding) {
        return encode(fragment, encoding, Type.FRAGMENT);
    }

    /**
     * Encode the given URI fragment with the given encoding.
     *
     * @param fragment the fragment to be encoded
     * @param charset  the character encoding to encode to
     * @return the encoded fragment
     * @since 5.0
     */
    public static String encodeFragment(String fragment, Charset charset) {
        return encode(fragment, charset, Type.FRAGMENT);
    }

    /**
     * Variant of {@link #decode(String, Charset)} with a String charset.
     *
     * @param source   the String to be encoded
     * @param encoding the character encoding to encode to
     * @return the encoded String
     */
    public static String encode(String source, String encoding) {
        return encode(source, encoding, Type.URI);
    }

    /**
     * Encode all characters that are either illegal, or have any reserved
     * meaning, anywhere within a URI, as defined in
     * <a href="https://tools.ietf.org/html/rfc3986">RFC 3986</a>.
     * This is useful to ensure that the given String will be preserved as-is
     * and will not have any o impact on the structure or meaning of the URI.
     *
     * @param source  the String to be encoded
     * @param charset the character encoding to encode to
     * @return the encoded String
     * @since 5.0
     */
    public static String encode(String source, Charset charset) {
        return encode(source, charset, Type.URI);
    }

    /**
     * Convenience method to apply {@link #encode(String, Charset)} to all
     * given URI variable values.
     *
     * @param uriVariables the URI variable values to be encoded
     * @return the encoded String
     * @since 5.0
     */
    public static Map<String, String> encodeUriVariables(Map<String, ?> uriVariables) {
        Map<String, String> result = new LinkedHashMap<String, String>(uriVariables.size());
        for (Map.Entry<String, ?> entry : uriVariables.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String stringValue = (value != null ? value.toString() : "");
            result.put(key, encode(stringValue, Charset.forName("UTF-8")));
        }
        return result;
    }

    /**
     * Convenience method to apply {@link #encode(String, Charset)} to all
     * given URI variable values.
     *
     * @param uriVariables the URI variable values to be encoded
     * @return the encoded String
     * @since 5.0
     */
    public static Object[] encodeUriVariables(Object... uriVariables) {
        List<String> result = new ArrayList<String>();
        for (Object value : uriVariables) {
            String stringValue = (value != null ? value.toString() : "");
            result.add(encode(stringValue, Charset.forName("UTF-8")));
        }
        return result.toArray();
    }

    private static String encode(String scheme, String encoding, Type type) {
        return encodeUriComponent(scheme, encoding, type);
    }

    private static String encode(String scheme, Charset charset, Type type) {
        return encodeUriComponent(scheme, charset, type);
    }

    /**
     * Encode the given source into an encoded String using the rules specified
     * by the given component and with the given options.
     *
     * @param source   the source String
     * @param encoding the encoding of the source String
     * @param type     the URI component for the source
     * @return the encoded URI
     * @throws IllegalArgumentException when the given value is not a valid URI component
     */
    static String encodeUriComponent(String source, String encoding, Type type) {
        return encodeUriComponent(source, Charset.forName(encoding), type);
    }

    /**
     * Encode the given source into an encoded String using the rules specified
     * by the given component and with the given options.
     *
     * @param source  the source String
     * @param charset the encoding of the source String
     * @param type    the URI component for the source
     * @return the encoded URI
     * @throws IllegalArgumentException when the given value is not a valid URI component
     */
    static String encodeUriComponent(String source, Charset charset, Type type) {
        if (!(source != null && source.length() > 0)) {
            return source;
        }
        if (charset == null) {
            throw new IllegalArgumentException("Charset must not be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }

        byte[] bytes;
        try {
            bytes = source.getBytes(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        boolean changed = false;
        for (byte b : bytes) {
            if (b < 0) {
                b += 256;
            }
            if (type.isAllowed(b)) {
                bos.write(b);
            } else {
                bos.write('%');
                char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
                char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
                bos.write(hex1);
                bos.write(hex2);
                changed = true;
            }
        }
        try {
            return (changed ? new String(bos.toByteArray(), charset.name()) : source);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String uriDecode(String source, Charset charset) {
        int length = source.length();
        if (length == 0) {
            return source;
        }
        if (charset == null) {
            throw new IllegalArgumentException("Charset must not be null");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
        boolean changed = false;
        for (int i = 0; i < length; i++) {
            int ch = source.charAt(i);
            if (ch == '%') {
                if (i + 2 < length) {
                    char hex1 = source.charAt(i + 1);
                    char hex2 = source.charAt(i + 2);
                    int u = Character.digit(hex1, 16);
                    int l = Character.digit(hex2, 16);
                    if (u == -1 || l == -1) {
                        throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
                    }
                    bos.write((char) ((u << 4) + l));
                    i += 2;
                    changed = true;
                } else {
                    throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
                }
            } else {
                bos.write(ch);
            }
        }
        try {
            return (changed ? new String(bos.toByteArray(), charset.name()) : source);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Decode the given encoded URI component.
     * <p>See {@link Uris#uriDecode(String, Charset)} for the decoding rules.
     *
     * @param source   the encoded String
     * @param encoding the character encoding to use
     * @return the decoded value
     * @throws IllegalArgumentException when the given source contains invalid encoded sequences
     * @see Uris#uriDecode(String, Charset)
     * @see java.net.URLDecoder#decode(String, String)
     */
    public static String decode(String source, String encoding) {
        return uriDecode(source, Charset.forName(encoding));
    }

    /**
     * Decode the given encoded URI component.
     * <p>See {@link Uris#uriDecode(String, Charset)} for the decoding rules.
     *
     * @param source  the encoded String
     * @param charset the character encoding to use
     * @return the decoded value
     * @throws IllegalArgumentException when the given source contains invalid encoded sequences
     * @see Uris#uriDecode(String, Charset)
     * @see java.net.URLDecoder#decode(String, String)
     * @since 5.0
     */
    public static String decode(String source, Charset charset) {
        return uriDecode(source, charset);
    }

    /**
     * Extract the file extension from the given URI path.
     *
     * @param path the URI path (e.g. "/products/index.html")
     * @return the extracted file extension (e.g. "html")
     * @since 4.3.2
     */

    public static String extractFileExtension(String path) {
        int end = path.indexOf('?');
        int fragmentIndex = path.indexOf('#');
        if (fragmentIndex != -1 && (end == -1 || fragmentIndex < end)) {
            end = fragmentIndex;
        }
        if (end == -1) {
            end = path.length();
        }
        int begin = path.lastIndexOf('/', end) + 1;
        int paramIndex = path.indexOf(';', begin);
        end = (paramIndex != -1 && paramIndex < end ? paramIndex : end);
        int extIndex = path.lastIndexOf('.', end);
        if (extIndex != -1 && extIndex > begin) {
            return path.substring(extIndex + 1, end);
        }
        return null;
    }

    /**
     * Enumeration used to identify the allowed characters per URI component.
     * <p>Contains methods to indicate whether a given character is valid in a specific URI component.
     *
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986</a>
     */
    public enum Type {

        SCHEME {
            @Override
            public boolean isAllowed(int c) {
                return isAlpha(c) || isDigit(c) || '+' == c || '-' == c || '.' == c;
            }
        },
        AUTHORITY {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c) || isSubDelimiter(c) || ':' == c || '@' == c;
            }
        },
        USER_INFO {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c) || isSubDelimiter(c) || ':' == c;
            }
        },
        HOST_IPV4 {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c) || isSubDelimiter(c);
            }
        },
        HOST_IPV6 {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c) || isSubDelimiter(c) || '[' == c || ']' == c || ':' == c;
            }
        },
        PORT {
            @Override
            public boolean isAllowed(int c) {
                return isDigit(c);
            }
        },
        PATH {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c) || '/' == c;
            }
        },
        PATH_SEGMENT {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c);
            }
        },
        QUERY {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c) || '/' == c || '?' == c;
            }
        },
        QUERY_PARAM {
            @Override
            public boolean isAllowed(int c) {
                if ('=' == c || '&' == c) {
                    return false;
                } else {
                    return isPchar(c) || '/' == c || '?' == c;
                }
            }
        },
        FRAGMENT {
            @Override
            public boolean isAllowed(int c) {
                return isPchar(c) || '/' == c || '?' == c;
            }
        },
        URI {
            @Override
            public boolean isAllowed(int c) {
                return isUnreserved(c);
            }
        };

        /**
         * Indicates whether the given character is allowed in this URI component.
         *
         * @return {@code true} if the character is allowed; {@code false} otherwise
         */
        public abstract boolean isAllowed(int c);

        /**
         * Indicates whether the given character is in the {@code ALPHA} set.
         *
         * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isAlpha(int c) {
            return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
        }

        /**
         * Indicates whether the given character is in the {@code DIGIT} set.
         *
         * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isDigit(int c) {
            return (c >= '0' && c <= '9');
        }

        /**
         * Indicates whether the given character is in the {@code gen-delims} set.
         *
         * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isGenericDelimiter(int c) {
            return (':' == c || '/' == c || '?' == c || '#' == c || '[' == c || ']' == c || '@' == c);
        }

        /**
         * Indicates whether the given character is in the {@code sub-delims} set.
         *
         * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isSubDelimiter(int c) {
            return ('!' == c || '$' == c || '&' == c || '\'' == c || '(' == c || ')' == c || '*' == c || '+' == c ||
                    ',' == c || ';' == c || '=' == c);
        }

        /**
         * Indicates whether the given character is in the {@code reserved} set.
         *
         * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isReserved(int c) {
            return (isGenericDelimiter(c) || isSubDelimiter(c));
        }

        /**
         * Indicates whether the given character is in the {@code unreserved} set.
         *
         * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isUnreserved(int c) {
            return (isAlpha(c) || isDigit(c) || '-' == c || '.' == c || '_' == c || '~' == c);
        }

        /**
         * Indicates whether the given character is in the {@code pchar} set.
         *
         * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
         */
        protected boolean isPchar(int c) {
            return (isUnreserved(c) || isSubDelimiter(c) || ':' == c || '@' == c);
        }
    }
}
