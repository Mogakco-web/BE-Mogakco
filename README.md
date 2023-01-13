# BE-Mogakco
모각코 서비스 백엔드 레포지토리

# BE-Mogakco

# 0. 개발 환경, 언어, 도구

- Intellij
- SpringBoot
- Java (11 JDK)
- MariaDB (DB)
- Redis (Cache DB)
- Nginx(무중단배포)
- gradle (라이브러리 관리)
- AWS EC2 , RDS , SecretManager (컴퓨팅 서비스 , DB , 암호화)
- Jenkins (CI/CD)
- Docker (CI/CD)
- PostMan (API Test)

# 1. 코딩 컨벤션

## Entity Class

- 필드명은 전부 `camelCase` 로 작성
- Class Name은 `PascalCase` 로 작성 0

## Package Naming

- Controller,Service,Repository는 기능별로 Naming하여서 사용함
- DTO와 Entity는 Model 패키지 내부에서 관리하며 DTO내부는 Request와 Response 용도에 따라 패키지를 따로 두어 분리하며 그 내부에서도 기능별로 패키지를 Naming 하여 분리하여 사용함

## File Naming

- YML파일은 `Kebab Case` 로 Naming한다.
- DB 설정 및  프로젝트에 필요한 설정들은 `application.yml` 에 저장하여준다.
- jwt,aws,mail등을 별도의 yml파일을 만들어 관리한다.

## API 요청 URl

- 동사를 사용하지 않는다.
- /api/버전명/Entity Name 을 사용한다.

## DB & CI/CD

- DB는 Maria DB 즉 SQL DB를 사용하며 이는 AWS의 RDS를 사용하여 관리한다.
- 프로젝트 배포는 AWS EC2를 사용하여 진행한다.
- 파이프라인 구축을 위하여 스프링부트 배포와는 별도의 EC2를 생성하여 Jenkins를 설치하고 도커를 통하여 서로 빌드정보를 공유한다.
- GitHub WebHook을 사용하여 로컬에서 개발완료후 커밋 할경우 자동으로 정보를 Jenkins로 넘긴다.

## EC2 및 RDS 사양

- EC2의 경우 프리티어를 유지하기 위해 Ubuntu 환경에 t2.micro 환경을 사용한다.
- RDS 또한 프리티어를 유지하기 위해 mariaDB에 t3.micro환경을 사용한다.

# 2. 레파지토리 관리 기법

Organization에서 BackEnd 별도의 레파지토리에서 관리합니다.

### **⚙️** 브랜치 관리 전략

### **⚙️ Git-flow**

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/31a7203e-6bf2-4049-a700-0d2a32b32b4d/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220708%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220708T045236Z&X-Amz-Expires=86400&X-Amz-Signature=6db79eaefef1c6b16f4a9b090a6b443325fd41e9594371ae2711263d4ae1d505&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

**⚙️ 브랜치 구성**

- main : 테스트 서버에서 테스트가 끝나고 운영서버로 배포 할 수 있는 브랜치
- develop : 개발을 위한 브랜치
- feature  :  서비스 기능별 브랜치
- hotfix : main 브랜치에서 발생한 버그를 수정하는 브랜치
- `feature` 브랜치는 하나의 기능을 개발하기 위한 브랜치입니다. 부모는`develop`이며, 개발이 완료되면`develop`에 merge합니다. 브랜치 이름은 보통`feature/*`이 됩니다.
- `develop` 브랜치는 개발을 위한 브랜치입니다. 여러`feature`들이 merge되는 장소이며, 아직 release되지 않은 기능들이 모여 있게 됩니다.
- `master`브랜치는 실제 운영 중인 서비스의 브랜치입니다.
- `hotfix`브랜치는 서비스에 문제가 발생했을 때 핫픽스에 해당하는 브랜치입니다. 기능 개발(`feature`) 등과 달리 빠르게 대처해야 할 필요가 있기 때문에,`master`브랜치에 직접 merge하는 전략을 취합니다.`develop`과의 차이가 발생하기 때문에, 나중에 차이를 merge할 필요가 있습니다.

### 브랜치 네이밍

**⚙️ 네이밍 패턴**

`브랜치 종류/이슈번호-간단한 설명`

**Ex)** 이슈번호가 67인 '로그인 기능' 이슈를 구현하는 브랜치를 생성하는 경우, 브랜치 이름을`feature/67-login`로 작성한다.

### 커밋 메시지

**⚙ 메시지 구조**

`Type : 제목 #이슈번호`

`본문`

**Ex)**이슈번호가 67인 이슈의 기능을 구현한 뒤 커밋을 하는 상황이라면 커밋 메시지의 제목을`feat : A기능 구현 #67`으로 작성한다.

**⚙ Type**

- `feat` : 새로운 기능에 대한 커밋
- `fix`	: 버그 수정에 대한 커밋
- `ci/cd` : 배포 커밋
- `docs` : 문서 수정에 대한 커밋
- `style` : 코드 스타일 혹은 포맷 등에 관한 커밋
- `refactor` : 코드 리팩토링에 대한 커밋
- `test` : 테스트 코드 수정에 대한 커밋
- `chore` : 패키지 관련 및 빌드코드 수정
# 3. 라이브러리

- lombok
- JPA
- Spring Security
- JDBC
- Log4j2
- SockJS , STOMP
- JSON ObjectMapper
- JWT
- Aws Secret Manager
- Querydsl
- Swagger
- Oauth2.0
- Spring Rest docs
- Crawling
- RestAPI
- Redis

# 개발기간

2023 01 07 ~ ing

