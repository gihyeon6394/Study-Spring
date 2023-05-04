<h2>5장 서비스 추상화</h2>

> 스프링이 어떻게 여러 종류의 비슷한 기술을 추상화하고, 일관된 방법으로 사용하게 하는가


1. 사용자 레벨 관리 기능 추가
    1. 필드 추가
        - Level 이늄
            > static final int 보다 enum 이 낫다    
            타입 안정성

        - User 필드 추가
        - UserDaoTest 테스트 수정
        - UserDaoJdbc 수정
    2. 사용자 수정 기능 추가
        - 수정 기능 테스트 추가
        - UserDao와 UserDaoJdbc 수정
        - 수정 테스트 보완
    3. UserService.upgradeLevels()
        - UserService 클래스와 빈 등록
          > service 원칙 : dao 구현체가 바뀌어도 service 로직이 수정되면 안됨

        - UserServiceTest 테스트 클래스
        - upgradeLevels() 메소드
        - upgradeLevels() 테스트
    4. UserService.add()
        > 필드의 기본값 지정은 비즈니스 로직이므로 클래스, DAO, service 중 service가 적절
        
    5. 코드 개선
        - upgradeLevels() 메소드 코드의 문제점
        - upgradeLevels() 리팩토링
        - User 테스트
        - UserServiceTest 개선
2. 트랜잭션 서비스 추상화
    1. 모 아니면 도
        - 테스트용 UserService 대역
        - 강제 에외 발생을 통한 테스트
        - 테스트 실패의 원인
    2. 트랜잭션 경계 설정
        - JDBC 트랜잭션의 트랜잭션 경계설정
        - UserService와 UserDao의 트랜잭션 문제
        - 비즈니스 로직 내의 트랜잭션 경계설정
        - UserService 트랜잭션 경계설정의 문제점
    3. 트랜잭션 동기화
        - Connection 파라미터 제거
        - 트랜잭션 동기화 적용
        - 트랜개션 테스트 보완
        - JdbcTemplate과 트랜잭션 동기화
    4. 트랜개션 서비스 추상화
        - 기술과 환경에 종속되는 트랜잭션 경계설정 코드
        - 트랜잭션 API의 의존관계 문제와 해결책
        - 스프링의 트랜잭션 서비스 추상화
        - 트랝개션 기술 설정의 분리
3. 서비스 차상화의 단일 책임 원칙
    - 수직, 수평 계층구조와 의존관계
    - 단일 책임 원칙
    - 단일 책임 원칙의 장점
4. 메일 서비스 추상화
    1. JavaMail을 이용한 메일 발송 기능
        - JavaMail 메일 발송
    2. JavaMail이 포함된 코드의 테스트
    3. 테스트를 위한 서비스 추상화
        - JavaMail을 이용한 테스트의 문제점
        - 메일 발송 기능 추상화
        - 테스트용 메일 발송 오브젝트
        - 테스트와 서비스 추상화
    4. 테스트 대역
        - 의존 오브젝트의 변경을 통한 테스트 방법
        - 테스트 대역의 종류와 특징
        - 목 오브젝트를 이용한 테스트
5. 정리
