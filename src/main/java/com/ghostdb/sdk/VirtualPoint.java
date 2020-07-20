package com.ghostdb.sdk;

import java.util.zip.CRC32;

/**
 * VirtualPoint
 */
public class VirtualPoint {

    private final String ip;
    private final long index;

    public VirtualPoint(long index, String ip) {
        this.ip = ip;
        this.index = index;
    }

    public String getIp() {
        return ip;
    }

	public long getIndex() {
		return index;
	}
}
