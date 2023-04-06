package com.tob.part3;

import java.io.*;

public class Calculator {

    /**
     * file의 모든 숫자를 곱하는 연산을 추가해달라고한다면?
     * => 중복 발생
     */
    public int sum1(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        int sum = 0;
        String line = null;
        while ((line = br.readLine()) != null) {
            sum += Integer.valueOf(line);
        }
        br.close();
        return sum;

    }


    /**
     * file의 모든 숫자를 곱하는 연산을 추가해달라고한다면?
     * => 중복 발생
     * <p>
     * 개선 : "곱하기" 앞 뒤로 중복인 부분을 모두 분리하자
     * <p>
     * 템플릿 (공통) : 파일읽기, 자원 반납
     * 콜백 (전략) : 사칙연산
     */

    public int multiply(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        int sum = 0;
        String line = null;
        while ((line = br.readLine()) != null) {
            sum *= Integer.valueOf(line);
        }
        br.close();
        return sum;
    }

    /**
     * teampalte (공통)
     * 1. 파일 일기
     * 2. 전달받은 전략 수행 (사칙연산)
     * 3. 자원 반납
     */
    public Integer templateFileReader(File file, DoSomeThingWithReader doSomeThingWithReader) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        try {
            Integer returnValue = doSomeThingWithReader.doSomething(br);
            return returnValue;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    public int sum2(File file) throws IOException {
        /**
         * 전략 (콜백) 정의
         * */
        int sum = templateFileReader(file, new DoSomeThingWithReader() {
            @Override
            public Integer doSomething(BufferedReader br) throws IOException {
                int sum = 0;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum += Integer.valueOf(line);
                }
                return sum;
            }
        });
        return sum;
    }

    /**
     * 곱하기
     */
    public int multiple1(File file) throws IOException {
        int sum = templateFileReader(file, new DoSomeThingWithReader() {
            @Override
            public Integer doSomething(BufferedReader br) throws IOException {
                int sum = 1;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum *= Integer.valueOf(line);
                }
                return sum;
            }

        });
        return sum;
    }

    /**
     * teampalte 고도화
     * 1. 파일 일기
     * 2. 전달받은 전략을 라인별로 수행
     * 3. 자원 반납
     */
    public Integer templateFileReaderWithLines(File file, Integer initialVal, DoSomeThingWithReaderWithLines doLines) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        try {
            Integer returnValue = initialVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                returnValue = doLines.doSomething(line, returnValue);
            }
            return returnValue;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public int sum3(File file) throws IOException {
        int sum = templateFileReaderWithLines(file, 0, new DoSomeThingWithReaderWithLines() {
            @Override
            public Integer doSomething(String line, Integer val) {
                val += Integer.valueOf(line);
                return val;
            }
        });
        return sum;
    }

    public int multiple2(File file) throws IOException {
        int sum = templateFileReaderWithLines(file, 1, new DoSomeThingWithReaderWithLines() {
            @Override
            public Integer doSomething(String line, Integer val) {
                val *= Integer.valueOf(line);
                return val;
            }
        });
        return sum;
    }

    /**
     * 문제점 : 반환할 값의 타입이 Integer로 제한되어있음
     * solution : 제네릭스를 사용하자
     */

    public <T> T templateFileReaderWithLines(File file, T initialVal, DoSomeThingGenerics<T> doLines) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        try {
            T returnValue = initialVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                returnValue = doLines.doSomething(line, returnValue);
            }
            return returnValue;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public String concatenate(File file) throws IOException {
        String sum = templateFileReaderWithLines(file, "", new DoSomeThingGenerics<String>() {
            @Override
            public String doSomething(String line, String val) {
                val += line;
                return val;
            }
        });
        return sum;
    }

    public Integer multiple4(File file) throws IOException {
        int sum = templateFileReaderWithLines(file, 1, new DoSomeThingGenerics<Integer>() {
            @Override
            public Integer doSomething(String line, Integer val) {
                val *= Integer.valueOf(line);
                return val;
            }
        });
        return sum;
    }


}
