package com.example.akis.standard.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Ut {

    public static class json {

        public static String toStr(Object obj) {
            try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class reflection {
        public static boolean setFieldValue(Object o, String fieldName, Object value) {
            Field field = null;

            try {
                field = o.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return false;
            }

            field.setAccessible(true);

            try {
                field.set(o, value);
            } catch (IllegalAccessException e) {
                return false;
            }

            return true;
        }

        public static <T> T getFieldValue(Object o, String fieldName, T defaultValue) {
            Field field = null;

            try {
                field = o.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return defaultValue;
            }

            field.setAccessible(true);

            try {
                return (T) field.get(o);
            } catch (IllegalAccessException e) {
                return defaultValue;
            }
        }

        public static <T> T call(Object obj, String methodName, Object... args) {
            try {
                Class<?>[] argTypes = Arrays.stream(args)
                        .map(Object::getClass)
                        .toArray(Class<?>[]::new);

                Method method = obj.getClass().getDeclaredMethod(methodName, argTypes);
                method.setAccessible(true);
                return (T) method.invoke(obj, args);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public static <T> T callArr(Object obj, String methodName, Object... args) {
            return call(obj, methodName, new Object[]{args});
        }
    }
    public static class hash {
        private static final MessageDigest md;

        static {
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        public static String sha256(String str) {
            // Convert the input string to bytes and update the MessageDigest
            byte[] inputBytes = str.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes = md.digest(inputBytes);

            // Convert the hashed bytes to a Base64 encoded string
            return Base64.getEncoder().encodeToString(hashBytes);
        }
    }

    public static class url {
        public static String encode(String str) {
            return URLEncoder.encode(str, StandardCharsets.UTF_8);
        }

        public static String modifyQueryParam(String url, String paramName, String paramValue) {
            url = deleteQueryParam(url, paramName);
            url = addQueryParam(url, paramName, paramValue);

            return url;
        }

        public static String addQueryParam(String url, String paramName, String paramValue) {
            if (!url.contains("?")) {
                url += "?";
            }

            if (!url.endsWith("?") && !url.endsWith("&")) {
                url += "&";
            }

            url += paramName + "=" + paramValue;

            return url;
        }

        private static String deleteQueryParam(String url, String paramName) {
            int startPoint = url.indexOf(paramName + "=");
            if (startPoint == -1) return url;

            int endPoint = url.substring(startPoint).indexOf("&");

            if (endPoint == -1) {
                return url.substring(0, startPoint - 1);
            }

            String urlAfter = url.substring(startPoint + endPoint + 1);

            return url.substring(0, startPoint) + urlAfter;
        }
    }
}
