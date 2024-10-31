# KAKAO-TOUR-API BACKEND
<!--①②③④⑤⑥⑦⑧⑨⑩-->
##  🍊 back-end developers
|[박해인](https://github.com/femmefatalehaein)|[이유진](https://github.com/yyujin1231)|
|:---:|:---:|
| <img src="https://avatars.githubusercontent.com/u/75514808?v=4" width="100">  |<img src="https://avatars.githubusercontent.com/u/118620724?v=4" width="100">|
|BACKEND| BACKEND |

## 🪴 역할
| 이름 | 포지션 |
| --- | --- | 
| [박해인](https://github.com/femmefatalehaein) | 로그인/회원가입/마이페이지, api 활용 데이터 수집, 업체 정보 개요/상세, 사진첩(마이제주), 추천시스템, 관심목록 |
| [이유진](https://github.com/yyujin1231) | 크롤링 기반 데이터 수집, 사용자 채팅(반갑수다), 응급지도, 아이짝꿍|


## 🔗 Backend URL
- BACK </br>
  https://back.ijeju.shop
- swagger </br>
  https://back.ijeju.shop/swagger-ui/index.html
  

## ⚡️ 상세
**① 로그인/회원가입**
- JWT 토큰을 이용한 사용자 인증/인가 처리
- Interceptor를 이용한 로그인 인증처리
</br>

**② Open API 활용 데이터 수집**
- Open API를 활용하여 효용성 있는 데이터 수집
</br>

**③ 사진첩 (마이제주)**
- UUID 토큰 기반 비회원도 볼 수 있는 공유링크 생성
</br>

**④ 추천시스템**  [Recommend Repository](https://github.com/KAKAO-TOUR-API-CONTEST/kakao-recommendation)
- `kafka`를 이용한 사용자 이벤트 수집
-  수집한 사용자 이벤트 기반으로 `spark`를 통해 유사도가 높은 업체의 정보를 제공
</br>

**⑤ 검색 / 필터링**
- `QueryDSL`을 이용하여 검색/필터링 기능 구현
</br>

**⑥ 채팅 (반갑수다)**
- STOMP와 WebSocket을 활용한 실시간 채팅 구현
</br>

**⑦ 크롤링 기반 데이터 수집**
- 네이버 지도를 활용하여 유아의자, 노키즈존, 놀이방등 추가정보 수집




## ⚙️ SETTING
- Spring boot3
- JDK 21
- Spring JPA
- MySQL 3.xx

## 📎 ERD
![image](https://github.com/user-attachments/assets/550d130b-9ac9-4d66-a4e1-3788aa1f0739)

##  file structure
```
└───ai_jeju
    ├───config
    ├───controller
    ├───domain
    ├───dto
    ├───exception
    ├───filter
    ├───generator
    ├───handler
    ├───jwt
    ├───repository
    ├───service
    └───util
```

## [🍏 API 명세서](....)
> 클릭 시 API 명세서를 확인하실 수 있습니다.

<br/><br/>
