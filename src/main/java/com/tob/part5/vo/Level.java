package com.tob.part5.vo;

/**
 * 사용자 레벨을 관리
 */
public enum Level {

    /**
     * final int 방식이 안좋은 이유
     * - 타입 불안정성 : 동일 타입의 다른 값 (디비에 없는 값)을 넣어도 컴파일러가 체크를 못함
     */
    //public  static final int BASIC = 1;

    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    private final int value;

    private final Level nextLevel;

    Level(int value, Level nextLevel) {
        this.value = value;
        this.nextLevel = nextLevel;
    }

    public Level getNextLevel() {
        return nextLevel;
    }

    public int intValue() {
        return value;
    }

    public static Level valueOf(int value) {
        switch (value) {
            case 1:
                return BASIC;
            case 2:
                return SILVER;
            case 3:
                return GOLD;
            default:
                throw new AssertionError("Unknown value: " + value);
        }
    }
}
