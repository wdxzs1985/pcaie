package jp.pcaie.common.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

public class ControllerUtils {

    public static final String ISO_8859_1 = "ISO-8859-1";

    public static final String UTF_8 = "UTF-8";

    public static String decodeQuery(final String query) {
        try {
            if (StringUtils.isNotBlank(query)) {
                final String decode = new String(query.getBytes(ISO_8859_1),
                                                 UTF_8);
                return decode;
            }
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
