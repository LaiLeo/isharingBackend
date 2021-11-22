package com.fih.ishareing.service.token;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fih.ishareing.service.user.model.AuthUser;
import com.fih.ishareing.service.user.model.IeqUser;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.MapUtils;

import io.jsonwebtoken.Claims;

public class ClaimsUtils {
    private final static String UID = "uid";
    private final static String APP = "app";

    private final static String SU_USER = "su";
    private final static String ADMIN_USER = "admin";

    private final static List<String> KEYS = Arrays.asList(UID, APP, SU_USER, ADMIN_USER);

    public static Map<String, Object> toClaims(final IeqUser user) {
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();
        builder.put(UID, user.getUserId());
        builder.put(APP, user.getApplicationCode());
        builder.put(SU_USER, user.isSu());
        builder.put(ADMIN_USER, user.isAdmin());
        return builder.build();
    }

    public static Map<String, Object> toClaims(final AuthUser user) {
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();
        builder.put(UID, user.getUserId());
        builder.put(APP, user.getApplicationCode());
        builder.put(SU_USER, user.isSu());
        builder.put(ADMIN_USER, user.isAdmin());
        return builder.build();
    }

    public static Map<String, Object> toClaims(final Claims claims) {
        return claims.entrySet().stream().filter(p -> KEYS.contains(p.getKey()))
                .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
    }

    public static Integer getUID(final Claims claims) {
        return MapUtils.getInteger(claims, UID);
    }

    public static String getApplicationCode(final Claims claims) {
        return MapUtils.getString(claims, APP);
    }

    public static boolean isSu(final Claims claims) {
        return MapUtils.getBoolean(claims, SU_USER, false);
    }

    public static boolean isAdmin(final Claims claims) {
        return MapUtils.getBoolean(claims, ADMIN_USER, false);
    }
}
