INSERT INTO Regions VALUES(111, '서울특별시', '강남구', '자곡동');
INSERT INTO Regions VALUES(112, '서울특별시', '강남구', '논현동');
INSERT INTO Regions VALUES(121, '서울특별시', '강동구', '성내제1동');
INSERT INTO Regions VALUES(122, '서울특별시', '강동구', '성내제3동');
INSERT INTO Regions VALUES(131, '서울특별시', '강북구', '삼각산동');
INSERT INTO Regions VALUES(132, '서울특별시', '강북구', '우이동');
INSERT INTO Regions VALUES(211, '부산광역시', '강서구', '가덕도동');
INSERT INTO Regions VALUES(212, '부산광역시', '강서구', '성북동');
INSERT INTO Regions VALUES(221, '부산광역시', '금정구', '회동동');
INSERT INTO Regions VALUES(222, '부산광역시', '금정구', '금사동');
INSERT INTO Regions VALUES(311, '대구광역시', '남구', '대명4동');
INSERT INTO Regions VALUES(312, '대구광역시', '남구', '봉덕동');
INSERT INTO Regions VALUES(321, '대구광역시', '달서구', '도원동');
INSERT INTO Regions VALUES(322, '대구광역시', '달서구', '용덕동');
INSERT INTO Regions VALUES(411, '인천광역시', '부평구', '십정동');
INSERT INTO Regions VALUES(412, '인천광역시', '부평구', '부개2동');
INSERT INTO Regions VALUES(421, '인천광역시', '연수구', '송도2동');
INSERT INTO Regions VALUES(422, '인천광역시', '연수구', '송도5동');
INSERT INTO Regions VALUES(423, '인천광역시', '연수구', '연수2동');
INSERT INTO Regions VALUES(424, '인천광역시', '연수구', '동춘동');

-- userid, nickname, manner_temperaturem, resident
INSERT INTO Users VALUES('asd1234', '매너거래남', '40.5',  311);
INSERT INTO Users VALUES('qwer123', '신속거래녀', '38.0',  312);
INSERT INTO Users VALUES('zxcvbn1', '쿨거래원함', '42.0',  222);
INSERT INTO Users VALUES('ehdtjr0', '마동석', '51.0',  112);
INSERT INTO Users VALUES('dmdkr7', '행복하세요', '35.0',  412);
INSERT INTO Users VALUES('dkanro5', '멍멍왈왈', '36.5',  424);
-- 실제 글 좀 배껴와야 하려나

INSERT INTO Posts(title, price, content, userid) VALUES('갤럭시탭S7 FE', 520000, '미개봉 128기가 WIFI\n블랙', 'asd1234');
INSERT INTO Posts(title, price, content, userid) VALUES('갤럭시탭S6', 400000, '개봉 64기가 LTE\n네이비', 'ehdtjr0');
INSERT INTO Posts(title, price, content, userid) VALUES('아이폰13 미니', 600000, '얼마 사용 안했습니다', 'dkanro5');
INSERT INTO Posts(title, price, content, userid) VALUES('아수스 노트북', 1200000, '급전 필요해서 팝니다', 'dmdkr7');
INSERT INTO Posts(title, price, content, deal_status, userid) VALUES('손목시계 급처합니다.', 200000, '선물로 받은 손목시계 급처합니다', '거래완료', 'asd1234');
INSERT INTO Posts(title, price, content, deal_status, userid) VALUES('쿠폰', 0, 'bhc 1장\n티엔2장\n지코바2장\n\n나눔받은거 재나눔합니다', '예약중', 'zxcvbn1');