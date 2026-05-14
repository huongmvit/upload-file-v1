package com.upload.utils;

public final class TraceContext {

    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> MERCHANT_ID = new ThreadLocal<>();


    private TraceContext() {
    }
    public static void setTrace(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String getTrace() {
        return TRACE_ID.get();
    }

    public static void clearTrace() {
        TRACE_ID.remove();
    }

    public static void setMerchant(String merchant) {
        MERCHANT_ID.set(merchant);
    }

    public static String getMerchant() {
        return MERCHANT_ID.get();
    }

    public static void clearMerchant() {
        MERCHANT_ID.remove();
    }
}
