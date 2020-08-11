package com.tayfurunal.mentorship.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This utility class contains methods for convenient handling of cookies
 * (such as addCookie and deleteCookie)
 */
public class CookieUtils {

    /**
     * Returns Optional<Cookie> from the request object if a cookie with this name exists,
     * otherwise returns Optional.Empty()
     *
     * @param request which contains cookies
     * @param name    name of the required cookie
     * @return required cookie
     */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Adds a cookie with the passed parameters to the response
     *
     * @param response which needs to set cookie
     * @param name     cookie name
     * @param value    cookie value
     * @param maxAge   cookie max age
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Deletes cookie with the passed name received from the request
     *
     * @param request  which contains the necessary cookies
     * @param response to delete cookie from
     * @param name     of the cookie to delete
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * Serializes the passed object to a string
     *
     * @param object serialization object
     * @return string that is the result of serialization
     */
    public static String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    /**
     * Returns an object of the class stored in the cookie (as cookie value)
     *
     * @param cookie a cookie object that stores a serialized class object
     * @param cls    class to get an instance of from the cookie
     * @return instance of the cls class
     */
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}