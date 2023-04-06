package com.tob.part3;

import java.io.BufferedReader;
import java.io.IOException;

public interface DoSomeThingWithReader {


    /**
     * 문제점 : 초기화, 라인별 읽기, 라이별로 ? 연산이 모두 공통임
     * solution : 이걸 다시 공통으로 분리해보자 {@link DoSomeThingWithReaderWithLines}
     *
     * */
    public Integer doSomething(BufferedReader br) throws IOException;
}
