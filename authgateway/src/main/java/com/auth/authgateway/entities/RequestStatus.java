package com.auth.authgateway.entities;

import java.util.HashMap;
import java.util.Map;

public enum RequestStatus {
    APPROVED, REJECTED, REQUEST, PENDING;

    private static final Map<String, RequestStatus> map = new HashMap<>();
    static {
        for (final RequestStatus rs: RequestStatus.values()) {
            map.put(rs.toString(), rs);
        }
    }
}
