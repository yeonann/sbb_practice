# DB 생성
DROP DATABASE IF EXISTS sbb__dev;
CREATE DATABASE sbb__dev;
USE sbb__dev;

# 각 테이블 내용
SELECT *
FROM question;

SELECT *
FROM answer;

## 외래키 제약 끄기
SET FOREIGN_KEY_CHECKS = 0;

## 테이블 초기화
TRUNCATE answer;
TRUNCATE question;

# 외래키 제약 복구
SET FOREIGN_KEY_CHECKS = 1;