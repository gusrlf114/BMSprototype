**1. 프로젝트 개요**
• **목적**: Spring Boot와 Security, JWT를 활용하여 권한별 차등 기능을 제공하는 은행 시스템의 백엔드 핵심 로직을 구현한다.
• **핵심 기술**: Java 21, Spring Boot 4.x, Spring Data JPA/Mybatis/JdbcTemplate, Spring Security 6.x, JWT 12.x.x, Oracle Database.
****

**2. 요구사항 명세 (Functional Requirements)
A. 회원 관리 및 보안 (Security & JWT)**
• **회원가입**: ID, 비밀번호, 이름, 역할을 입력받아 가입한다. (비밀번호는 반드시 BCrypt 암호화)
• **로그인**: ID/PW 검증 후 **Access Token(JWT)**을 발급한다.
• **권한 체계**:
    ◦ **비회원**: 회원가입, 로그인, 공개된 은행 공지사항 조회 가능.
    ◦ **회원**: 입금, 송금, 내 계좌 조회 가능.
    ◦ **관리자**: 전체 회원 목록 조회, 특정 계좌 동결/해제 권한.
**B. 뱅킹 비즈니스 로직 (Core Logic)**
• **계좌 생성**: 회원은 가입 시 기본 계좌 1개가 자동 생성된다. (잔액 0원)
• **입금 (Deposit)**: 본인 계좌에 금액을 입력하면 잔액이 증가한다.
• **송금 (Transfer)**:
    ◦ 상대방 계좌번호와 금액을 입력한다.
    ◦ **트랜잭션 관리**: 내 계좌에서 출금과 상대방 계좌 입금은 하나의 트랜잭션으로 처리되어야 하며, 잔액 부족 시 `RuntimeExepction`을 발생시키고 롤백한다.
내 계좌의 잔액과 최근 거래 내역(최근 5건)을 조회한다.
****

**3. 기술적 상세 지시 (Technical Specifications)
① 계층 분리**
• **Service 계층 분리**: Controller -> Service -> Repository 구조를 엄격히 준수할 것.
**② Spring Security & JWT 설정**
• `OncePerRequestFilter`를 상속받은 `JwtAuthenticationFilter`를 구현하여 모든 요청에서 토큰을 검증할 것.
• `SecurityFilterChain` 설정 시 각 API 경로별로 `hasRole()`을 이용해 접근 권한을 설정할 것.
    ◦ `/api/admin/**` -> `ADMIN` 권한 필요
    ◦ `/api/banking/**` -> `USER` 권한 필요
**③ 데이터 모델 (Entity)**
• **Member**: id, username, password, role(ENUM)
• **Account**: id, accountNumber(Unique), balance, member_id(FK)
• **Transaction**: id, sender_account, receiver_account, amount, type(DEPOSIT/TRANSFER), createdAt
****

**4. 실기 평가 채점 기준 (Grading Rubric)평가 항목중요도세부 기준**

**보안 구현**     JWT 발급 및 검증 필터가 정상 작동하는가? 비밀번호가 암호화되었는가?

**트랜잭션 처리**    송금 시 `@Transactional`을 사용해 원자성을 보장했는가? (잔액 부족 시 실패 처리)

**권한 제어**   관리자 API에 일반 회원이 접근할 때 403 Forbidden을 정확히 반환하는가?

**DI 활용**   서비스 간 의존성 주입 시 생성자 주입 방식을 사용했는가?

**코드 가독성**   DTO와 Entity를 명확히 분리하고, 적절한 예외 처리(Exception Handling)를 했는가?
****

**5. 제출물 및 테스트 시나리오**
1. **제출물**: GitHub 리포지토리 링크.
2. **필수 테스트**:
    ◦ 로그인하지 않은 상태에서 송금 API 호출 시 401 에러 확인.
    ◦ 잔액 10,000원인 A가 B에게 15,000원 송금 시도 시 실패 및 잔액 유지 확인.
    ◦ ADMIN 계정으로 전체 사용자 계좌 잔액 총합 조회 가능 여부 확인.


배운점
1. restcontroller를 사용하여 프론트부분과 백부분을 확실하게 분리하여 사용할수있는점
2. 시큐리티,필터의 적용 방식
3. entity와dto의 사용방식(필요한데이터를 dto방식으로 만들어서 출력가능하게)
4. fetch 와 토큰 이용방식
5. jwt유틸 ,jwt필터 방식
6. throw new exception등 예외처리와 예외처리 메세지까지 구현하도록하는방식
7. @transactional 구동방식
8. 토큰으로인한 페이지이동에 어려움이있었는데 필터는 로직만 막히고 페이지이동은가능하게 +버튼형식으로 페이지이동때도 fetch token보내는방식
9. form 형식에 FormData() 해서 보냈었는데 이걸 다시 json.stringify해서 보내는방식
10. 컨트롤러에서 데이터를 뿌릴때 ResponseEntity 사용방식으로인하여 ok상황과 배드상황일때를 각각보낼수있고 js로 받았을떄 상태로 나눠서 처리할수있는점

사용해보고싶은것들
1.실제 송금전에 계좌번호 적고 사람이름뜨게해서 보내는사람이 맞는지 확인하기

2.여러가지값에 소문자대문자상관없이 검사하는거 만들어보기 

3.계속 로직들을 fetch로 필요할때마다 사용하는식이아닌 애초에 페이지구동시 fetch로 데이터 전부 받아와서 프론트에서만 처리해보기

4.토큰을 localstorage식으로 했는데 cookie방식으로 해보기

5.stream을이용한 list(entity)로받거나할때 dto식으로 바꿔서 해보기

