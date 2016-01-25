package com.auth0.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Base64 {

    private static final byte[] ENCODE_MAP = new byte[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '-', '_'
    };

    public static String encodeUrlSafe(String string) {
        byte[] in = string.getBytes(Charset.defaultCharset());
        int length = (in.length + 2) * 4 / 3;
        byte[] out = new byte[length];
        int index = 0, end = in.length - in.length % 3;
        for (int i = 0; i < end; i += 3) {
            out[index++] = ENCODE_MAP[(in[i] & 0xff) >> 2];
            out[index++] = ENCODE_MAP[((in[i] & 0x03) << 4) | ((in[i + 1] & 0xff) >> 4)];
            out[index++] = ENCODE_MAP[((in[i + 1] & 0x0f) << 2) | ((in[i + 2] & 0xff) >> 6)];
            out[index++] = ENCODE_MAP[(in[i + 2] & 0x3f)];
        }
        switch (in.length % 3) {
            case 1:
                out[index++] = ENCODE_MAP[(in[end] & 0xff) >> 2];
                out[index++] = ENCODE_MAP[(in[end] & 0x03) << 4];
                out[index++] = '=';
                out[index++] = '=';
                break;
            case 2:
                out[index++] = ENCODE_MAP[(in[end] & 0xff) >> 2];
                out[index++] = ENCODE_MAP[((in[end] & 0x03) << 4) | ((in[end + 1] & 0xff) >> 4)];
                out[index++] = ENCODE_MAP[((in[end + 1] & 0x0f) << 2)];
                out[index++] = '=';
                break;
        }
        try {
            return new String(out, 0, index, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
