package com.yapily.util;

import javax.xml.bind.DatatypeConverter;

import org.springframework.util.DigestUtils;

public class MD5Hash {

    public static String getHash(String value) {
            final byte[] bytes = DigestUtils.md5Digest(value.getBytes());
            return DatatypeConverter
                    .printHexBinary(bytes).toLowerCase();
    }
}
