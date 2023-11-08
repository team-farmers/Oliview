/* -------- MEMBER 테이블 생성 -------*/

DROP TABLE "MEMBER";

CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL PRIMARY KEY,
	"MEMBER_ID"	VARCHAR2(30)		NOT NULL UNIQUE,
	"MEMBER_PW"	VARCHAR2(100)		NOT NULL,
	"MEMBER_EMAIL"	VARCHAR2(50)		NOT NULL,
	"MEMBER_NAME"	VARCHAR2(30)		NOT NULL,
	"MEMBER_NICKNAME"	VARCHAR2(30)		NOT NULL UNIQUE,
	"MEMBER_PROFILE"	VARCHAR2(300)		NULL,
	"MEMBER_ENROLL_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	CHECK(MEMBER_DEL_FL IN ('N', 'Y')) NOT NULL,
	"AUTHORITY"	CHAR(1)	DEFAULT 'N'	CHECK(AUTHORITY IN ('N', 'Y')) NOT NULL,
	"AGREEMENT"	CHAR(1)	DEFAULT 'Y'	NULL
);

COMMENT ON COLUMN "MEMBER"."MEMBER_NO" IS '회원 번호(SEQ_MEMBER_NO)';

COMMENT ON COLUMN "MEMBER"."MEMBER_ID" IS '회원 아이디';

COMMENT ON COLUMN "MEMBER"."MEMBER_PW" IS '비밀번호(암호화)';

COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL" IS '이메일(인증 후 가입)';

COMMENT ON COLUMN "MEMBER"."MEMBER_NAME" IS '회원 이름';

COMMENT ON COLUMN "MEMBER"."MEMBER_NICKNAME" IS '회원 닉네임';

COMMENT ON COLUMN "MEMBER"."MEMBER_PROFILE" IS '프로필 이미지 경로(선택)';

COMMENT ON COLUMN "MEMBER"."MEMBER_ENROLL_DATE" IS '회원 가입일자';

COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL" IS '탈퇴여부("N" : 정상, "Y":탈퇴)';

COMMENT ON COLUMN "MEMBER"."AUTHORITY" IS '관리자권한("N":회원, "Y":관리자)';

COMMENT ON COLUMN "MEMBER"."AGREEMENT" IS '선택약관 동의여부("Y": 동의, NULL:동의 안함)';


-- 회원번호 시퀀스 생성
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;


--로그인 샘플 계정 데이터 생성
INSERT INTO "MEMBER"
VALUES (SEQ_MEMBER_NO.NEXTVAL, 'test01', 'pass01', 'test@naver.com', '김테스트', '테스트중', 
	NULL, DEFAULT, DEFAULT, DEFAULT, NULL);

INSERT INTO "MEMBER"
VALUES (SEQ_MEMBER_NO.NEXTVAL, 'test02', 'pass02', 'test2@naver.com', '이테스트', '테스트중임', 
	NULL, DEFAULT, DEFAULT, DEFAULT, 'Y');

COMMIT;



-- 로그인 회원정보 조회
        SELECT MEMBER_NO, MEMBER_ID, MEMBER_PW, MEMBER_EMAIL, MEMBER_NICKNAME , MEMBER_NAME,
		MEMBER_PROFILE , AUTHORITY ,
		 TO_CHAR(MEMBER_ENROLL_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') ENROLL_DATE 
		FROM "MEMBER"
		WHERE MEMBER_DEL_FL = 'N'
		AND MEMBER_ID = 'test01';
 ;
 

<<<<<<< HEAD
-- 이메일 인증 테이블 생성

DROP TABLE "AUTH_KEY";

CREATE TABLE "AUTH_KEY" (
	"KEY_NO"	NUMBER		NOT NULL,
	"AUTH_KEY"	CHAR(6)		NOT NULL,
	"CREATE_TIME"	DATE	DEFAULT SYSDATE	NOT NULL,
	"EMAIL"	VARCHAR2(50)		NOT NULL
);

COMMENT ON COLUMN "AUTH_KEY"."KEY_NO" IS '인증키 구분 번호(SEQ_KEY_NO)';

COMMENT ON COLUMN "AUTH_KEY"."AUTH_KEY" IS '코드';

COMMENT ON COLUMN "AUTH_KEY"."CREATE_TIME" IS '인증코드 생성 시간';

COMMENT ON COLUMN "AUTH_KEY"."EMAIL" IS '이메일';

ALTER TABLE "AUTH_KEY" ADD CONSTRAINT "PK_AUTH_KEY" PRIMARY KEY (
	"KEY_NO"
);

CREATE SEQUENCE SEQ_KEY_NO NOCACHE;



/*===============================================================================================*/
/* -------- REVIEW 테이블 생성 -------*/
DROP TABLE "REVIEW";

CREATE TABLE "REVIEW" (
	"REVIEW_NO"	NUMBER		NOT NULL,
	"REVIEW_TITLE"	VARCHAR2(20)		NOT NULL,
	"REVIEW_ADDRESS"	VARCHAR2(100)		NOT NULL,
	"STAR_TASTE"	NUMBER		NOT NULL,
	"STAR_AMOUNT"	NUMBER		NOT NULL,
	"STAR_CLEAN"	NUMBER		NOT NULL,
	"REVIEW_ONELINE"	VARCHAR2(200)		NOT NULL,
	"REVIEW_IMG"	VARCHAR2(300)		NOT NULL,
	"REVIEW_MENU"	VARCHAR2(300)		NOT NULL,
	"WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"READ_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"REVIEW_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "REVIEW"."REVIEW_NO" IS '리뷰 번호(SEQ_REVIEW_NO)';

COMMENT ON COLUMN "REVIEW"."REVIEW_TITLE" IS '리뷰 제목(가게 이름)';

COMMENT ON COLUMN "REVIEW"."REVIEW_ADDRESS" IS '가게 위치';

COMMENT ON COLUMN "REVIEW"."STAR_TASTE" IS '평점(맛)';

COMMENT ON COLUMN "REVIEW"."STAR_AMOUNT" IS '평점(양)';

COMMENT ON COLUMN "REVIEW"."STAR_CLEAN" IS '평점(청결도)';

COMMENT ON COLUMN "REVIEW"."REVIEW_ONELINE" IS '한줄평';

COMMENT ON COLUMN "REVIEW"."REVIEW_IMG" IS '리뷰 이미지 경로';

COMMENT ON COLUMN "REVIEW"."REVIEW_MENU" IS '리뷰 음식';

COMMENT ON COLUMN "REVIEW"."WRITE_DATE" IS '작성일';

COMMENT ON COLUMN "REVIEW"."READ_COUNT" IS '조회수';

COMMENT ON COLUMN "REVIEW"."REVIEW_DEL_FL" IS '삭제 여부("N" : 존재 "Y" : 삭제)';

COMMENT ON COLUMN "REVIEW"."MEMBER_NO" IS '회원번호(SEQ_MEMBER_NO)';

ALTER TABLE "REVIEW" ADD CONSTRAINT "PK_REVIEW" PRIMARY KEY (
	"REVIEW_NO"
);

ALTER TABLE "REVIEW" ADD CONSTRAINT "FK_MEMBER_TO_REVIEW_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);




-- CHECK 제약조건 추가
ALTER TABLE "REVIEW" ADD CONSTRAINT CHECK_REVIEW_DEL_FL
CHECK(REVIEW_DEL_FL IN ('Y','N'));


-- REVIEW 시퀀스 생성
DROP SEQUENCE SEQ_REVIEW_NO;
CREATE SEQUENCE SEQ_REVIEW_NO NOCACHE;


-- 리뷰 샘플 데이터-1 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '스윗레시피', '서울시 중구', 4.5, 4.5, 5.0, 
	'디저트가 맛있어요','loadImage','소금빵', DEFAULT, DEFAULT, 'N', 1);

-- 리뷰 샘플 데이터-2 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '칠리칠리', '세종특별자치시 장군면 금암리', 3.0, 2.0, 4.0, 
	'별로였어요','loadImage2','칠리빵', DEFAULT, DEFAULT, 'N', 1);

-- 리뷰 샘플 데이터-3 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '바삭돈까스', '경기도', 5.0, 3.0, 4.0, 
	'바삭합니다','loadImage3','안심돈까스', DEFAULT, DEFAULT, 'N', 1);

-- 리뷰 샘플 데이터-4 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '김밥일번가', '대구광역시', 1.0, 5.0, 3.0, 
	'참치김밥 추천','loadImage4','참치김밥, 잔치국수', DEFAULT, DEFAULT, 'N', 1);

-- 리뷰 샘플 데이터-5 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '서브웨이', '서울시 강서구', 1.0, 5.0, 3.0, 
	'신선해요','loadImage4','에그마요', DEFAULT, DEFAULT, 'N', 1);


-- 샘플 데이터 모두 삭제
DELETE FROM "REVIEW";

COMMIT;

-- 리뷰 검색(제목+)
SELECT REVIEW_NO, REVIEW_IMG, REVIEW_TITLE, MEMBER_NICKNAME, STAR_TASTE, STAR_AMOUNT, STAR_CLEAN, 
	TO_CHAR(WRITE_DATE, 'YYYY"." MM"." DD"."') WRITE_DATE
FROM "REVIEW"
JOIN "MEMBER" USING (MEMBER_NO)
WHERE REVIEW_TITLE LIKE '%${searchInput}%' 
ORDER BY READ_COUNT;


-- 리뷰 상세조회(임시)
SELECT REVIEW_TITLE, REVIEW_ADDRESS, REVIEW_IMG, MEMBER_PROFILE, MEMBER_NICKNAME, STAR_TASTE,
		STAR_AMOUNT, STAR_CLEAN, (STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3.0 STAR_TOTAL ,
		REVIEW_MENU, REVIEW_ONELINE
FROM "REVIEW"
JOIN "MEMBER" USING (MEMBER_NO)
WHERE REVIEW_DEL_FL = 'N'
AND REVIEW_NO = 2;




-- 리뷰 찜 테이블 생성
DROP TABLE "LIKE";

CREATE TABLE "LIKE" (
	"REVIEW_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "LIKE"."REVIEW_NO" IS '리뷰 번호(SEQ_REVIEW_NO)';

COMMENT ON COLUMN "LIKE"."MEMBER_NO" IS '회원 번호(SEQ_MEMBER_NO)';

ALTER TABLE "LIKE" ADD CONSTRAINT "PK_LIKE" PRIMARY KEY (
	"REVIEW_NO",
	"MEMBER_NO"
);

ALTER TABLE "LIKE" ADD CONSTRAINT "FK_REVIEW_TO_LIKE_1" FOREIGN KEY (
	"REVIEW_NO"
)
REFERENCES "REVIEW" (
	"REVIEW_NO"
);

ALTER TABLE "LIKE" ADD CONSTRAINT "FK_MEMBER_TO_LIKE_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

COMMIT;








/*===============================================================================================*/
-->>>>>>> b25f3785adeb328c0d8efb402d656edcd560ac08;





-- 회원가입 SQL
INSERT INTO "MEMBER"
VALUES (SEQ_MEMBER_NO.NEXTVAL, 'id', 'pw', 'email', 'name', 'nickname', NULL, DEFAULT, DEFAULT, DEFAULT, NULL);


-- 회원정보 일치하는 회원찾기
SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_EMAIL = 'test@naver.com' 
AND MEMBER_NAME = '테스트'
;

-- 아이디 찾기
SELECT MEMBER_ID 
FROM "MEMBER"
WHERE MEMBER_EMAIL = 'test@naver.com' 
AND MEMBER_NAME = 
;

-- 비밀번호 변경
UPDATE "MEMBER" SET 
MEMBER_PW = 'pass01'
WHERE MEMBER_NO = 1
;

COMMIT;

SELECT * FROM "MEMBER" m ;


-- =========================================

-- 같이먹어요 게시판
DROP TABLE "TOGETHER";

CREATE TABLE "TOGETHER" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"BOARD_TITLE"	VARCHAR2(30)		NOT NULL,
	"DATE_CREATED"	DATE	DEFAULT SYSDATE	NOT NULL,
	"BOARD_IMG"	VARCHAR2(200)		NULL,
	"BOARD_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT "N"	NOT NULL,
	"BOARD_SUBTITLE"	VARCHAR2(30)		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"METTING_DATE"	DATE	DEFAULT SYSDATE	NULL,
	"READ_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"BOARD_CODE"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "TOGETHER"."BOARD_NO" IS '게시글번호(SEQ_BOARD_NO)';

COMMENT ON COLUMN "TOGETHER"."BOARD_TITLE" IS '모임찾기 제목';

COMMENT ON COLUMN "TOGETHER"."DATE_CREATED" IS '작성일';

COMMENT ON COLUMN "TOGETHER"."BOARD_IMG" IS '게시글이미지';

COMMENT ON COLUMN "TOGETHER"."BOARD_CONTENT" IS '게시글 내용';

COMMENT ON COLUMN "TOGETHER"."BOARD_DEL_FL" IS '삭제여부 ("Y"  정상, "N" 유지)';

COMMENT ON COLUMN "TOGETHER"."BOARD_SUBTITLE" IS '게시글 부제목';

COMMENT ON COLUMN "TOGETHER"."MEMBER_NO" IS '회원번호(SEQ_MEMBER_NO)';

COMMENT ON COLUMN "TOGETHER"."METTING_DATE" IS '모임날짜';

COMMENT ON COLUMN "TOGETHER"."READ_COUNT" IS '조회수';

COMMENT ON COLUMN "TOGETHER"."BOARD_CODE" IS '게시판 종류별 코드번호 (SEQ_BOARD_CODE)';

ALTER TABLE "TOGETHER" ADD CONSTRAINT "PK_TOGETHER" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "TOGETHER" ADD CONSTRAINT "FK_MEMBER_TO_TOGETHER_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

-- 같이먹어요 이미지
DROP TABLE "TOGETHER_IMG";

CREATE TABLE "TOGETHER_IMG" (
	"IMG_NO"	NUMBER		NOT NULL,
	"IMG_PATH"	VARCHAR2(300)		NOT NULL,
	"IMG_RENAME"	VARCHAR2(30)		NOT NULL,
	"IMG_ORIGINAL_NAME"	VARCHAR2(300)		NOT NULL,
	"IMG_ORDER"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

-- 같이 먹어요 참여인원

DROP TABLE "Participation";

CREATE TABLE "Participation" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"USER_PEOPLE"	NUMBER		NOT NULL,
	"MAX_PEOPLE"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "Participation"."BOARD_NO" IS '게시글번호(SEQ_BOARD_NO)';

COMMENT ON COLUMN "Participation"."USER_PEOPLE" IS '같이먹어요 참여인원';

COMMENT ON COLUMN "Participation"."MAX_PEOPLE" IS '같이먹어요 참여인원 제한';

ALTER TABLE "Participation" ADD CONSTRAINT "PK_PARTICIPATION" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "Participation" ADD CONSTRAINT "FK_TOGETHER_TO_Participation_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "TOGETHER" (
	"BOARD_NO"
);


-- 같이먹어요 이미지

DROP TABLE "TOGETHER_IMG";

CREATE TABLE "TOGETHER_IMG" (
	"IMG_NO"	NUMBER		NOT NULL,
	"IMG_PATH"	VARCHAR2(300)		NOT NULL,
	"IMG_RENAME"	VARCHAR2(30)		NOT NULL,
	"IMG_ORIGINAL_NAME"	VARCHAR2(300)		NOT NULL,
	"IMG_ORDER"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "TOGETHER_IMG"."IMG_NO" IS '이미지번호 (SEQ_IMG_NO)';

COMMENT ON COLUMN "TOGETHER_IMG"."IMG_PATH" IS '이미지 저장 폴더 경로';

COMMENT ON COLUMN "TOGETHER_IMG"."IMG_RENAME" IS '변경된 이미지 파일 이름';

COMMENT ON COLUMN "TOGETHER_IMG"."IMG_ORIGINAL_NAME" IS '원본 이미지 파일 이름';

COMMENT ON COLUMN "TOGETHER_IMG"."IMG_ORDER" IS '이미지 파일 순서 번호';

COMMENT ON COLUMN "TOGETHER_IMG"."BOARD_NO" IS '게시글 번호(SEQ_BOARD_NO)';

ALTER TABLE "TOGETHER_IMG" ADD CONSTRAINT "PK_TOGETHER_IMG" PRIMARY KEY (
	"IMG_NO"
);

ALTER TABLE "TOGETHER_IMG" ADD CONSTRAINT "FK_TOGETHER_TO_TOGETHER_IMG_1" FOREIGN KEY (
	"IMG_NO"
)
REFERENCES "TOGETHER" (
	"BOARD_NO"
);
















-- 게시글번호 시퀀스 생성
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE;


COMMIT;

SELECT * FROM TOGETHER ;