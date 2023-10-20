package com.dansup.server.api.danceclass.domain;

public enum Method {
//팝업/그룹레슨/개인레슨/쉐어
    OD("원데이"), R("정규반"), P("공연반"), M("전문반"), S("세션"), PU("팝업"), GL("그룹레슨"), PL("개인레슨"), SH("쉐어");

    private String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

}
