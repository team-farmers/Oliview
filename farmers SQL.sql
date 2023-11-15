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

UPDATE "MEMBER" SET
AUTHORITY = 'Y'
WHERE MEMBER_NO = 1;

COMMIT;

SELECT * FROM "MEMBER";


/*===============================================================================================*/
/* -------- REVIEW 테이블 생성 -------*/
DROP TABLE "REVIEW" CASCADE CONSTRAINTS;

CREATE TABLE "REVIEW" (
	"REVIEW_NO"	NUMBER		NOT NULL,
	"REVIEW_TITLE"	VARCHAR2(50)		NOT NULL,
	"REVIEW_ADDRESS"	VARCHAR2(200)		NOT NULL,
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
	'디저트가 맛있어요','loadImage','소금빵', DEFAULT, DEFAULT, 'N', 6);

-- 리뷰 샘플 데이터-2 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '칠리칠리', '세종특별자치시 장군면 금암리', 3.0, 2.0, 4.0, 
	'별로였어요','loadImage2','칠리빵', DEFAULT, DEFAULT, 'N', 6);

-- 리뷰 샘플 데이터-3 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '바삭돈까스', '경기도', 5.0, 3.0, 4.0, 
	'바삭합니다','loadImage3','안심돈까스', DEFAULT, DEFAULT, 'N', 6);

-- 리뷰 샘플 데이터-4 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '김밥일번가', '대구광역시', 1.0, 5.0, 3.0, 
	'참치김밥 추천','loadImage4','참치김밥, 잔치국수', DEFAULT, DEFAULT, 'N', 6);

-- 리뷰 샘플 데이터-5 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '서브웨이', '서울시 강서구', 1.0, 5.0, 3.0, 
	'신선해요','loadImage4','에그마요', DEFAULT, DEFAULT, 'N', 6);


-- 리뷰 샘플 데이터-6 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '빵빵베이커리', '서울시 강남구', 3.0, 2.0, 3.0, 
	'빵이 많아요','loadImage6','무화과 파운드', DEFAULT, DEFAULT, 'N', 1);

-- 리뷰 샘플 데이터-7 생성
INSERT INTO "REVIEW"
VALUES (SEQ_REVIEW_NO.NEXTVAL, '신의주찹쌀순대', '서울시 은평구', 4.5, 2.5, 3.5, 
	'순대국이 맛있어요','loadImage6','순대국', DEFAULT, DEFAULT, 'N', 5);


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



--------------------------------------------------- 리뷰 찜 테이블 생성
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


-- 전체 리뷰 수 조회
SELECT COUNT(*) FROM "REVIEW"
WHERE REVIEW_DEL_FL = 'N';


-- 찜 테이블 리셋
DELETE FROM "LIKE";


-- 다른 리뷰 조회
SELECT REVIEW_NO, REVIEW_IMG, REVIEW_TITLE, MEMBER_NICKNAME, STAR_TASTE, STAR_AMOUNT, STAR_CLEAN, 
			TO_CHAR(WRITE_DATE, 'YYYY"." MM"." DD"."') WRITE_DATE, READ_COUNT
FROM "REVIEW"
JOIN "MEMBER" USING (MEMBER_NO)
WHERE REVIEW_TITLE = '스윗레시피'
AND REVIEW_NO != 6
AND ROWNUM BETWEEN 1 AND 4
ORDER BY READ_COUNT;



-- 같은 가게의 평점 조회
SELECT REVIEW_NO, ROUND((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3,1) STAR_TOTAL
FROM "REVIEW"
WHERE REVIEW_TITLE = '서브웨이';

SELECT ROUND((AVG(STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3),1)
FROM "REVIEW"
WHERE REVIEW_TITLE = (SELECT REVIEW_TITLE FROM "REVIEW" WHERE REVIEW_NO=12);

SELECT REVIEW_NO, REVIEW_TITLE, REVIEW_ADDRESS, REVIEW_IMG, MEMBER_PROFILE, MEMBER_NICKNAME, STAR_TASTE,
		STAR_AMOUNT, STAR_CLEAN,ROUND((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3,1) STAR_TOTAL , REVIEW_MENU, REVIEW_ONELINE,
		(SELECT ROUND(AVG(STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3)),1)
		FROM "REVIEW"
		WHERE REVIEW_TITLE = '서브웨이') STORE_STAR_TOTAL
FROM "REVIEW"
JOIN "MEMBER" USING (MEMBER_NO)
WHERE REVIEW_DEL_FL = 'N'
AND REVIEW_NO = 12;


-- 조회수 조작
UPDATE "REVIEW" 
SET READ_COUNT = 150 
WHERE REVIEW_TITLE='후니도니';

COMMIT;

-- 가게 평점순 조회
SELECT REVIEW_TITLE , COUNT(*) STORE_COUNT, ROUND(AVG((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3),1) STORE_STAR_TOTAL
FROM "REVIEW"
WHERE REVIEW_TITLE ='서브웨이'
GROUP BY REVIEW_TITLE
ORDER BY STORE_STAR_TOTAL DESC;



SELECT REVIEW_TITLE , COUNT(*) STORE_COUNT, ROUND(AVG((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3),1) STORE_STAR_TOTAL
FROM "REVIEW"
WHERE REVIEW_TITLE LIKE '서브웨이'
OR REVIEW_MENU LIKE '서브웨이'
OR REVIEW_ADDRESS LIKE '서브웨이'
OR MEMBER_NICKNAME LIKE '서브웨이'
GROUP BY REVIEW_TITLE
ORDER BY STORE_STAR_TOTAL DESC;


-- 가게 제일 최근 사진
SELECT REVIEW_IMG, WRITE_DATE 
FROM "REVIEW"
WHERE REVIEW_TITLE ='서브웨이'
ORDER BY WRITE_DATE DESC ;

SELECT REVIEW_TITLE , COUNT(*) STORE_COUNT, 
		ROUND(AVG((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3),1) STORE_STAR_TOTAL_RATE,
		(SELECT MAX(REVIEW_IMG) REVIEW_IMG FROM REVIEW R2 WHERE R2.REVIEW_TITLE = R1.REVIEW_TITLE) REVIEW_IMG
FROM "REVIEW" R1
GROUP BY REVIEW_TITLE
ORDER BY STORE_STAR_TOTAL_RATE DESC;


--평점순 가게 리스트 조회
SELECT REVIEW_NO, REVIEW_IMG, REVIEW_TITLE, MEMBER_NICKNAME, STAR_TASTE, STAR_AMOUNT, STAR_CLEAN, ROUND((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3,1) STAR_TOTAL ,
			TO_CHAR(WRITE_DATE, 'YYYY"." MM"." DD"."') WRITE_DATE,
			
			(SELECT ROUND(AVG((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3),1) STORE_STAR_TOTAL
			FROM "REVIEW"
			WHERE REVIEW_TITLE = '서브웨이'
			GROUP BY REVIEW_TITLE) STORE_STAR_TOTAL,					
FROM "REVIEW"
JOIN "MEMBER" USING (MEMBER_NO)
WHERE REVIEW_TITLE = '서브웨이'
ORDER BY WRITE_DATE DESC;


SELECT REVIEW_TITLE , COUNT(*) STORE_COUNT, 
	ROUND(AVG((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3),1) STORE_STAR_TOTAL_RATE,
	(SELECT MAX(REVIEW_IMG) REVIEW_IMG
	FROM REVIEW R2 WHERE R2.REVIEW_TITLE = R1.REVIEW_TITLE) REVIEW_IMG
FROM "REVIEW" R1
GROUP BY REVIEW_TITLE
ORDER BY STORE_STAR_TOTAL_RATE DESC;

-- 검색 평점 리뷰 개수
SELECT COUNT(*)
FROM(SELECT 1
	FROM "REVIEW"
	WHERE REVIEW_DEL_FL = 'N'
	AND REVIEW_TITLE LIKE '%추어%'
	GROUP BY REVIEW_TITLE
);


SELECT COUNT(*) FROM "REVIEW"
WHERE REVIEW_DEL_FL = 'N'
AND REVIEW_TITLE LIKE '%추어%';


SELECT REVIEW_TITLE , COUNT(*) STORE_COUNT, 
		ROUND(AVG((STAR_TASTE+STAR_AMOUNT+STAR_CLEAN)/3),1) STORE_STAR_TOTAL_RATE,
		(SELECT MAX(REVIEW_IMG) REVIEW_IMG FROM REVIEW R2 WHERE R2.REVIEW_TITLE = R1.REVIEW_TITLE) REVIEW_IMG
FROM "REVIEW" R1
GROUP BY REVIEW_TITLE
ORDER BY STORE_STAR_TOTAL_RATE DESC;



COMMIT;


------------------ 댓글 테이블 생성
DROP TABLE "COMMENT";

CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"COMMENT_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"COMMENT_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"REVIEW_NO"	NUMBER		NOT NULL,
	"PARENT_NO"	NUMBER		NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS '댓글 번호(SEQ_COMMENT_NO)';

COMMENT ON COLUMN "COMMENT"."COMMENT_CONTENT" IS '댓글 내용';

COMMENT ON COLUMN "COMMENT"."COMMENT_WRITE_DATE" IS '댓글 작성일';

COMMENT ON COLUMN "COMMENT"."COMMENT_DEL_FL" IS '댓글 삭제 여부(N:삭제X, Y:삭제O)';

COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원 번호(SEQ_MEMBER_NO)';

COMMENT ON COLUMN "COMMENT"."REVIEW_NO" IS '리뷰 번호(SEQ_REVIEW_NO)';

COMMENT ON COLUMN "COMMENT"."PARENT_NO" IS '부모 댓글 번호(대댓글)';

ALTER TABLE "COMMENT" ADD CONSTRAINT "PK_COMMENT" PRIMARY KEY (
	"COMMENT_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_MEMBER_TO_COMMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_REVIEW_TO_COMMENT_1" FOREIGN KEY (
	"REVIEW_NO"
)
REFERENCES "REVIEW" (
	"REVIEW_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_COMMENT_TO_COMMENT_1" FOREIGN KEY (
	"PARENT_NO"
)
REFERENCES "COMMENT" (
	"COMMENT_NO"
);



-- CHECK 제약조건 추가
ALTER TABLE "COMMENT" ADD CONSTRAINT CHECK_COMMENT_DEL_FL
CHECK(COMMENT_DEL_FL IN ('Y','N'));


-- COMMENT 시퀀스 생성
DROP SEQUENCE SEQ_COMMENT_NO;
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE;


-- 샘플 댓글 데이터 모두 삭제
DELETE FROM "COMMENT";


-- 댓글 샘플 데이터 생성
INSERT INTO "COMMENT"
VALUES (SEQ_COMMENT_NO.NEXTVAL, '댓글 테스트', DEFAULT, DEFAULT , 5 , 8, NULL);



COMMIT;



/*===============================================================================================*/


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

-- 닉네임 10글자 테스트
UPDATE MEMBER SET 
MEMBER_NICKNAME = '하둘셋넷다여일여아열'
WHERE MEMBER_NO = 1;

COMMIT;

--회원 탈퇴 SQL
UPDATE "MEMBER" 
SET MEMBER_DEL_FL ='Y'
WHERE MEMBER_NO = 6
;

-- 멤버 샘플계정 입력
INSERT INTO "MEMBER"
VALUES (SEQ_MEMBER_NO.NEXTVAL, 'mem01', '1234', 'test2@naver.com', '장테스트', '탈퇴테스트중', 
	NULL, DEFAULT, DEFAULT, DEFAULT, NULL);

COMMIT;

ROLLBACK;

SELECT * FROM "MEMBER" m ;



-- 프로필 이미지 수정
UPDATE "MEMBER" SET
MEMBER_PROFILE =
WHERE MEMBER_NO =
;
---------------------------------together

DROP TABLE "TOGETHER";

CREATE TABLE "TOGETHER" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"BOARD_TITLE"	VARCHAR2(30)		NOT NULL,
	"DATE_CREATED"	DATE	DEFAULT SYSDATE	NOT NULL,
	"BOARD_IMG"	VARCHAR2(200)		NULL,
	"BOARD_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"BOARD_SUBTITLE"	VARCHAR2(30)		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"METTING_DATE"	DATE	DEFAULT SYSDATE	NULL,
	"READ_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"MAX_PEOPLE"	NUMBER		NOT NULL
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

COMMENT ON COLUMN "TOGETHER"."MAX_PEOPLE" IS '최대 인원';

ALTER TABLE "TOGETHER" ADD CONSTRAINT "PK_TOGETHER" PRIMARY KEY (
	"BOARD_NO"
);

COMMIT;

-- 비밀번호 찾기
SELECT MEMBER_PW 
FROM "MEMBER" m 
WHERE MEMBER_NO = 2
;

-- 내가 쓴 글 게시글 목록 불러오기
SELECT * FROM 
	(SELECT 'REIVEW' "boardName", REVIEW_TITLE "title", 
	TO_CHAR(WRITE_DATE, 'YYYY-MM-DD HH24:MI:SS') "writeDate",
	'/review/' || REVIEW_NO "url"
	FROM REVIEW
	WHERE MEMBER_NO = 1
	AND REVIEW_DEL_FL = 'N'
	UNION
	SELECT '같이 먹어요', BOARD_TITLE , 
	TO_CHAR(DATE_CREATED, 'YYYY-MM-DD HH24:MI:SS') "writeDate",
	'/together/' || BOARD_CODE || '/' || BOARD_NO "url"
	FROM TOGETHER
	WHERE MEMBER_NO = 1
	AND BOARD_DEL_FL = 'N')
	
ORDER BY "writeDate" DESC
;


-- 샘플계정 비밀번호 수정
UPDATE "MEMBER" SET 
MEMBER_PW = '$2a$10$RrOuvmAv4lsQYmmiE4XihOWr01LgZjZtENyap0qw3YplTnWK04Jkm'
WHERE MEMBER_NO = 3;


--내가 찜한 글 목록 불러오기
SELECT 'REIVEW' "boardName", REVIEW_TITLE "title", 
	TO_CHAR(WRITE_DATE, 'YYYY-MM-DD HH24:MI:SS') "writeDate",
	'/review/' || REVIEW_NO "url"
FROM "LIKE" L
JOIN REVIEW USING (REVIEW_NO)
WHERE L.MEMBER_NO = 41
;

--내가 찜한 글 게시글수 얻어오기
SELECT COUNT(*)
FROM "LIKE" L
JOIN REVIEW USING (REVIEW_NO)
WHERE L.MEMBER_NO = 41
AND REVIEW_DEL_FL = 'N'
;


--내가 작성한 댓글 불러오기 (댓글 내용, 게시글 제목, 작성일, 게시글 댓글 수, URL)
SELECT COMMENT_NO, COMMENT_CONTENT, REVIEW_TITLE, 
	TO_CHAR(WRITE_DATE, 'YYYY-MM-DD HH24:MI:SS') COMMENT_WRITE_DATE,
	'/review/' || R.REVIEW_NO "URL",
	(SELECT COUNT(*)
		FROM "COMMENT" C2
		WHERE C2.REVIEW_NO = C.REVIEW_NO)
		COMMENT_COUNT
FROM "COMMENT" C
JOIN REVIEW R ON (C.REVIEW_NO = R.REVIEW_NO)
WHERE C.MEMBER_NO = 41
AND COMMENT_DEL_FL = 'N'
;


-- 특정 리뷰의 댓글 수
SELECT COUNT(*)
FROM REVIEW R
WHERE R.REVIEW_NO = C.REVIEW_NO
;

SELECT COUNT(*)
FROM "COMMENT" C
JOIN REVIEW USING (REVIEW_NO)
WHERE C.REVIEW_NO = R.REVIEW_NO;

-- 내가 작성한 댓글 수
SELECT COUNT(*)
FROM "COMMENT" C
WHERE MEMBER_NO = 41
;

=======
ALTER TABLE "TOGETHER" ADD CONSTRAINT "FK_MEMBER_TO_TOGETHER_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);



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



DROP TABLE "PARTICIPATION";


CREATE TABLE "PARTICIPATION" (
	"BOARD_NO"	NUMBER	NOT NULL,
	"MEMBER_NO"	NUMBER	NOT NULL
);

COMMENT ON COLUMN "PARTICIPATION"."BOARD_NO" IS '같이 먹어요 게시글 번호';
COMMENT ON COLUMN "PARTICIPATION"."MEMBER_NO" IS '참여 회원 번호';


ALTER TABLE "PARTICIPATION" ADD CONSTRAINT "PK_PARTICIPATION" PRIMARY KEY (
	"BOARD_NO", "MEMBER_NO"
);

ALTER TABLE "PARTICIPATION" ADD CONSTRAINT "FK_TOGETHER_TO_PARTICIPATION_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "TOGETHER" (
	"BOARD_NO"
);

ALTER TABLE "PARTICIPATION" ADD CONSTRAINT "FK_MEMBER_TO_PARTICIPATION" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);




INSERT INTO "TOGETHER"
VALUES (SEQ_BOARD_NO.NEXTVAL, '오리', SYSDATE, NULL, '배고프다2', DEFAULT
	,'배고파',1,'2023-12-02',DEFAULT, 3);

INSERT INTO "TOGETHER"
VALUES (SEQ_BOARD_NO.NEXTVAL, '오리3', SYSDATE, NULL, '배고프다3', DEFAULT
	,'배고파3',1,'2023-12-02',DEFAULT, 5);

INSERT INTO "TOGETHER"
VALUES (SEQ_BOARD_NO.NEXTVAL, '오리4', SYSDATE, NULL, '배고프다4', DEFAULT
	,'배고파4',1,'2023-12-02',DEFAULT, 2);

INSERT INTO "TOGETHER"
VALUES (SEQ_BOARD_NO.NEXTVAL, '오리5', SYSDATE, NULL, '배고프다5', DEFAULT
	,'배고파5',1,'2023-12-02',DEFAULT, 7);



ALTER TABLE TOGETHER MODIFY BOARD_DEL_FL CHAR(9);

COMMIT;



SELECT COUNT(*) FROM PARTICIPATION 
WHERE BOARD_NO =14;



SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, READ_COUNT,
	TO_CHAR(DATE_CREATED, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') DATE_CREATED,
	MEMBER_NICKNAME, MEMBER_PROFILE, MEMBER_NO, MAX_PEOPLE,
	(SELECT COUNT(*) FROM PARTICIPATION 
		WHERE BOARD_NO =14) CURRENT_PEOPLE
FROM "TOGETHER" 
JOIN "MEMBER" USING(MEMBER_NO)
WHERE BOARD_DEL_FL = 'N'
AND BOARD_NO = 14;



-- 비밀번호 찾기
SELECT MEMBER_PW 
FROM "MEMBER" m 
WHERE MEMBER_NO = 1
;


-- 메인페이지_ 리뷰 최신글 3개 불러오기 (review dto로 매핑)
SELECT *
FROM ( SELECT REVIEW_TITLE, 
		TO_CHAR(WRITE_DATE, 'YYYY-MM-DD HH24:MI:SS') WRITE_DATE,
		MEMBER_NICKNAME, REVIEW_MENU,
		'/review/' || REVIEW_NO "url"
		FROM REVIEW
		JOIN "MEMBER" USING(MEMBER_NO)
		WHERE REVIEW_DEL_FL = 'N'
		ORDER BY WRITE_DATE DESC )
WHERE ROWNUM <= 3
;


-- 메인페이지_ 같이먹어요 최신글 3개 불러오기 (together dto로 매핑)
SELECT *
FROM ( SELECT  TO_CHAR(METTING_DATE, 'YYYY-MM-DD HH24:MI:SS') METTING_DATE,
		BOARD_TITLE, BOARD_IMG, MAX_PEOPLE,
		'/together/' || '/' || BOARD_NO "url"
		FROM "TOGETHER"
		WHERE BOARD_DEL_FL = 'N'
		ORDER BY DATE_CREATED DESC)
WHERE ROWNUM <= 3
;


COMMIT;







--채팅룸 테이블 생성
CREATE TABLE "CHATTING_ROOM" (
	"CHATTING_NO"	NUMBER		NOT NULL,
	"CREATE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"OPEN_MEMBER"	NUMBER		NOT NULL,
	"PARTICIPANT"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "CHATTING_ROOM"."CHATTING_NO" IS '채팅방 번호';

COMMENT ON COLUMN "CHATTING_ROOM"."CREATE_DATE" IS '채팅방 생성일';

COMMENT ON COLUMN "CHATTING_ROOM"."OPEN_MEMBER" IS '개설자 회원 번호';

COMMENT ON COLUMN "CHATTING_ROOM"."PARTICIPANT" IS '참여자 회원 번호';

CREATE TABLE "MESSAGE" (
	"MESSAGE_NO"	NUMBER		NOT NULL,
	"MESSAGE_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"READ_FL"	CHAR	DEFAULT 'N'	NOT NULL,
	"SEND_TIME"	DATE	DEFAULT SYSDATE	NOT NULL,
	"SENDER_NO"	NUMBER		NOT NULL,
	"CHATTING_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "MESSAGE"."MESSAGE_NO" IS '메세지 번호';

COMMENT ON COLUMN "MESSAGE"."MESSAGE_CONTENT" IS '메세지 내용';

COMMENT ON COLUMN "MESSAGE"."READ_FL" IS '읽음 여부';

COMMENT ON COLUMN "MESSAGE"."SEND_TIME" IS '메세지 보낸 시간';

COMMENT ON COLUMN "MESSAGE"."SENDER_NO" IS '보낸 회원의 번호';

COMMENT ON COLUMN "MESSAGE"."CHATTING_NO" IS '채팅방 번호';



CREATE SEQUENCE SEQ_ROOM_NO NOCACHE;
CREATE SEQUENCE SEQ_MESSAGE_NO NOCACHE;


ALTER TABLE "CHATTING_ROOM" ADD CONSTRAINT "PK_CHATTING_ROOM" PRIMARY KEY (
	"CHATTING_NO"
);

ALTER TABLE "MESSAGE" ADD CONSTRAINT "PK_MESSAGE" PRIMARY KEY (
	"MESSAGE_NO"
);

ALTER TABLE "CHATTING_ROOM" ADD CONSTRAINT "FK_MEMBER_TO_CHATTING_ROOM_1" FOREIGN KEY (
	"OPEN_MEMBER"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
) ON DELETE CASCADE;

ALTER TABLE "CHATTING_ROOM" ADD CONSTRAINT "FK_MEMBER_TO_CHATTING_ROOM_2" FOREIGN KEY (
	"PARTICIPANT"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
) ON DELETE CASCADE;

ALTER TABLE "MESSAGE" ADD CONSTRAINT "FK_MEMBER_TO_MESSAGE_1" FOREIGN KEY (
	"SENDER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
) ON DELETE CASCADE;

ALTER TABLE "MESSAGE" ADD CONSTRAINT "FK_CHATTING_ROOM_TO_MESSAGE_1" FOREIGN KEY (
	"CHATTING_NO"
)
REFERENCES "CHATTING_ROOM" (
	"CHATTING_NO"
) ON DELETE CASCADE;












SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, READ_COUNT, BOARD_IMG,
		TO_CHAR(DATE_CREATED, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') DATE_CREATED,
		TO_CHAR(METTING_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') METTING_DATE,
		TO_CHAR(METTING_DATE, 'YYYY-MM-DD"T"HH24:MI') METTING_DATE2,
		MEMBER_NICKNAME, MEMBER_PROFILE, MEMBER_NO, MAX_PEOPLE,
		(SELECT COUNT(*) FROM PARTICIPATION
		WHERE BOARD_NO = 84 ) CURRENT_PEOPLE
		FROM "TOGETHER"
		JOIN "MEMBER" USING(MEMBER_NO)
		WHERE BOARD_DEL_FL = 'N'
		AND BOARD_NO = 84;
		

-- ====================================================================================
-- ************* 신고하기 테이블 *******************

DROP TABLE "REVIEW_REPORT";

CREATE TABLE "REVIEW_REPORT" (
	"REPORT_NO"	NUMBER		NOT NULL,
	"REPORT_CONTENT"	VARCHAR2(300)		NOT NULL,
	"REPORT_ACCRUE"	NUMBER	DEFAULT 0	NOT NULL,
	"REPORT_DEL_FL"	VARCHAR(255)	DEFAULT 'N'	NOT NULL,
	"REVIEW_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "REVIEW_REPORT"."REPORT_NO" IS '신고 번호(SEQ_REPORT_NO)';

COMMENT ON COLUMN "REVIEW_REPORT"."REPORT_CONTENT" IS '신고 내용';

COMMENT ON COLUMN "REVIEW_REPORT"."REPORT_ACCRUE" IS '신고 누적';

COMMENT ON COLUMN "REVIEW_REPORT"."REPORT_DEL_FL" IS '삭제 여부';

COMMENT ON COLUMN "REVIEW_REPORT"."REVIEW_NO" IS '리뷰 번호(SEQ_REVIEW_NO)';

ALTER TABLE "REVIEW_REPORT" ADD CONSTRAINT "PK_UNTITLED3" PRIMARY KEY (
	"REPORT_NO"
);

ALTER TABLE "REVIEW_REPORT" ADD CONSTRAINT "FK_REVIEW_TO_REVIEW_REPORT_1" FOREIGN KEY (
	"REVIEW_NO"
)
REFERENCES "REVIEW" (
	"REVIEW_NO"
);

CREATE SEQUENCE SEQ_REVIEW_REPORT NOCACHE;


INSERT INTO "REVIEW_REPORT"
VALUES (SEQ_REVIEW_REPORT_NO.NEXTVAL, '스팸홍보/도배글', 3, DEFAULT,47);

INSERT INTO "REVIEW_REPORT"
VALUES (SEQ_REVIEW_REPORT_NO.NEXTVAL, '스팸홍보/도배글', 2, DEFAULT ,46);
INSERT INTO "REVIEW_REPORT"
VALUES (SEQ_REVIEW_REPORT_NO.NEXTVAL, '스팸홍보/도배글', 3, DEFAULT,45);

SELECT * FROM REVIEW_REPORT;


	
COMMIT;




		SELECT R.REPORT_NO, R.REPORT_CONTENT, R.REPORT_ACCRUE, RR.REVIEW_NO,
		REVIEW_TITLE, MEMBER_NICKNAME
		FROM "REVIEW_REPORT" R
		JOIN "REVIEW" RR ON RR.REVIEW_NO = R.REVIEW_NO
		JOIN "MEMBER" M ON RR.MEMBER_NO = M.MEMBER_NO
		WHERE REPORT_DEL_FL = 'N'




