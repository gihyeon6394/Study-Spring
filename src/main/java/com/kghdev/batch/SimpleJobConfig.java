package com.kghdev.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Batch?
 * 엔터프라이즈 시스템 운영에 있어 대용량 일괄처리를 위해 설계된 Batch Framework
 * Spring의 특성을 그대로 가져와 DI, AOP, Interface 등 사용 가능
 * <p>
 * 주사용처
 * - 대용량 데이터를 이용하는 비즈니스 로직을 복잡하게 일괄 처리
 * - 특정한 시점에 Scheduler를 통해 자동화된 작업 (ex. 푸쉬 알림, 알림톡 발송, 월 별 리포트 등)
 * - 대용량 데이터 변경, 유효성 검사 등 transaction 안에서 처리 / 기록
 * <p>
 * 배치 어플케이션의 조건
 * 대용량 데이터 : 대용량 데이터 핸들링이 가능 (가져옴, 전달, 계산, 처리 등)
 * 자동화 : 사용자 개입 (상호작용) 없이 실행 가능
 * 견고성 : 잘못된 데이터를 충돌 / 중단 없이 처리
 * 신뢰성 : 잘못된 곳을 추적 가능 (로깅, 알림)
 * 성능 : 주어진 시간안에 처리를 완료하고, 다른 어플레키션을 방해하면 안됨
 * <p>
 * <p>
 * Spring Batch Architecture
 * Application : Spring Batch를 사용하여 개발자가 작성한 모든 작업, 코드
 * Batch Core : Batch 작업을 시작 / 제어하는데 필요한 핵심 런타임 클래스
 * Batch Infrastructure : 개발자가와 어플리케이션이 사용하는 일반적인 Reader, Writer, RetryTemplate과 같은 서비스
 * JobRepository : 배치 수행과 관련된 수치 데이터, 잡의 상태 유지 관리
 *
 * Spring Batch 메타 테이블 BATCH_JOB_INSTANCE
 * Caused by: org.springframework.jdbc.UncategorizedSQLException: PreparedStatementCallback; ...
 * ../org/springframework/batch/core/schema-mysql.sql 를 사용하면 됨
 *
 */
@Configuration // Spring Batch의 Job들은 모두 Bean이 되어야함
public class SimpleJobConfig {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJobConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob").start(simpleJobStep1()).build();

    }

    @Bean
    public Step simpleJobStep1() {
        return stepBuilderFactory.get("simpleJobStep1").tasklet((stepContribution, chunkContext) -> {

            //step 1 내용
            for (int i = 0; i < 100; i++) {
                if (i % 2 == 0) {
                    logger.info("batch step 1 msg idx :" + i);
                }
            }
            return RepeatStatus.FINISHED;
        }).build();
    }
}
