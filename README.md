# Spring Cloud Microservice Project

## 1. 프로젝트 개요

본 프로젝트는 Spring Cloud를 기반으로 한 마이크로서비스 아키텍처(MSA) 샘플 프로젝트입니다. 각 서비스는 독립적으로 개발, 배포 및 확장이 가능하도록 설계되었습니다.

## 2. 프로젝트 구조

```
.
├── db                  # MariaDB Dockerfile
├── inner               # 핵심 비즈니스 서비스
│   ├── catalog-service
│   ├── order-service
│   └── user-service
├── kafka-docker        # Kafka 및 Zookeeper Docker Compose 설정
├── keystore            # SSL/TLS 인증서
├── outer               # 인프라 지원 서비스
│   ├── apigateway-service
│   ├── config-service
│   └── discoveryservice
└── README.md
```

-   **`outer`**: MSA의 인프라 역할을 담당하는 서비스들이 위치합니다.
    -   `discoveryservice`: 서비스 등록 및 검색을 위한 Eureka 서버입니다.
    -   `config-service`: 모든 마이크로서비스의 설정 정보를 중앙에서 관리합니다.
    -   `apigateway-service`: 외부 요청에 대한 단일 진입점 역할을 하며, 라우팅, 필터링, 인증 등을 처리합니다.
-   **`inner`**: 실제 비즈니스 로직을 처리하는 핵심 서비스들이 위치합니다.
    -   `user-service`: 사용자 정보 관리를 담당합니다.
    -   `catalog-service`: 상품 목록(카탈로그) 정보 관리를 담당합니다.
    -   `order-service`: 주문 처리를 담당합니다.
-   **`kafka-docker`**: 서비스 간 비동기 메시지 통신을 위한 Kafka와 Zookeeper를 Docker로 실행하기 위한 설정 파일이 있습니다.
-   **`db`**: `user-service`, `catalog-service`, `order-service`에서 사용할 MariaDB 데이터베이스를 Docker로 실행하기 위한 `Dockerfile`이 있습니다.
-   **`keystore`**: `config-service`의 설정 정보 암호화를 위한 키 저장소입니다.

## 3. 아키텍처

-   **Microservice Architecture (MSA)**: 각 기능이 독립적인 서비스로 분리되어 있습니다.
-   **Service Discovery (Eureka)**: `discoveryservice`가 각 서비스의 위치를 동적으로 파악하고 관리합니다.
-   **API Gateway**: `apigateway-service`를 통해 모든 서비스 요청이 라우팅됩니다.
-   **Centralized Configuration**: `config-service`를 통해 모든 서비스의 설정을 한 곳에서 관리합니다.
-   **Asynchronous Communication (Kafka)**: 서비스 간의 데이터 동기화 및 이벤트 기반 통신을 위해 Kafka를 사용합니다. (예: 주문 발생 시 카탈로그 서비스의 재고 업데이트)
-   **Database per Service**: 각 핵심 서비스(`user`, `catalog`, `order`)는 자체 데이터베이스를 가질 수 있도록 설계되었습니다. (본 프로젝트에서는 편의상 단일 MariaDB 인스턴스 내에 스키마를 분리하는 형태로 사용될 수 있습니다.)

## 4. 시작하기

### 사전 준비 사항

-   Java 11 이상
-   Docker
-   Docker Compose

### 실행 순서

안정적인 서비스 구동을 위해 아래 순서대로 서비스를 실행해야 합니다.

1.  **DB (MariaDB) 실행**
    ```bash
    cd db
    docker build -t st-mariadb .
    docker run -d --name mariadb -p 3306:3306 st-mariadb
    ```

2.  **Kafka & Zookeeper 실행**
    ```bash
    cd kafka-docker
    docker-compose up -d
    ```

3.  **Outer 서비스 실행 (인프라 서비스)**
    1.  `discoveryservice`
    2.  `config-service`
    3.  `apigateway-service`

4.  **Inner 서비스 실행 (핵심 서비스)**
    -   `user-service`
    -   `catalog-service`
    -   `order-service`

각 서비스는 Spring Boot 애플리케이션이므로, 해당 프로젝트 디렉터리로 이동하여 `./mvnw spring-boot:run` 또는 IDE를 통해 직접 실행할 수 있습니다.
