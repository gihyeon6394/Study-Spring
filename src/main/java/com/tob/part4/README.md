<h2>4장 템플릿</h2>

1. 사라진 SQLException  
    1. 초난감 예외 처리  
       - 예외 블랙홀  
       - 무의미하고 무책임한 throws  
    2. 예외의 종류와 특징  
    3. 예외처리 방법  
        - 예외 복구  
        - 예외처리 회피  
        - 예외 전환  
    4. 예외처리 전략  
       - 런타임 예외의 보편화  
       - add() 메소드의 예외처리  
       - 애플리케이션 예외  
    5. SQLException은 어떻게 됐나?  

2. 예외 전환  
   1. JDBC의 한계  
      - 비표준 SQL  
      - 호환성 없는 SQLException의 DB 에러정보  
   2. DB 에러 코드 매핑을 통한 전환  
   3. DAO 인터페이스와 DataAccessException 계층 구조  
      - DAO 인터페이스와 구현의 분리  
      - 데티어 액세스 예외 추상화와 DataAccessException 계층구조  
   4. 기술에 독립적인 UserDao 만들기  
      - 인터페이스 적용  
      - 테스트 보완  
      - DataAccessException 활용 시 주의사항  
  
3. 정리  