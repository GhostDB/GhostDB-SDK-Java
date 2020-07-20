package com.ghostdb.sdk;

import java.util.zip.CRC32;

/**
 * VirtualPoint
 */
public class VirtualPoint {

    private final String ip;
    private final CRC32 index;

    public VirtualPoint(CRC32 index, String ip) {
        this.ip = ip;
        this.index = index;
    }

    public String getIp() {
        return ip;
    }

    public CRC32 getIndex() {
        return index;
    }
}