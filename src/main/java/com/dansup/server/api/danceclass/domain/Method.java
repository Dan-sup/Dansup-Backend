package com.dansup.server.api.danceclass.domain;

public enum Method {

    OD("원데이"), R("정규반"), P("공연반"), M("전문반"), S("세션");

    private String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

}
