 CREATE VIEW AUTORYZACJA_VIEW AS 
 SELECT login, HASLO, KONTO FROM KONTO WHERE AKTYWNE>0 AND NOT KONTO  = 'nowe konto';
--  

 INSERT INTO SHOP.KONTO(ID, KONTO, AKTYWNE ,AUTORYZOWANE, DATA_UTWORZENIA, EMAIL, HASLO, IMIE, LOGIN, NAZWISKO, ODPOWIEDZKONTROLNA, PYTANIEKONTROLNE, WERSJA)
 VALUES(-5,'administrator', 1, 1,'2019-09-19 10:25:30.628',  'admin@shop.pl', '2269669a8dbbe5f19c183b239cb1c1aa187b6a0dc23668d7e54d2c1f86ac7ca4', 'Jan', 'admin', 'Nowak', 'Ewa', 'Imię matki?', 1); 
 INSERT INTO SHOP.KONTO(ID, KONTO, AKTYWNE ,AUTORYZOWANE, DATA_UTWORZENIA, EMAIL, HASLO, IMIE, LOGIN, NAZWISKO, ODPOWIEDZKONTROLNA, PYTANIEKONTROLNE, WERSJA)
 VALUES(-6,'pracownik', 1, 1,'2019-09-19 10:25:30.628',  'pracownik@shop.pl', '2269669a8dbbe5f19c183b239cb1c1aa187b6a0dc23668d7e54d2c1f86ac7ca4', 'Jan', 'pracownik', 'Nowak', 'Ewa', 'Imię matki?', 1); 
 INSERT INTO SHOP.KONTO(ID, KONTO, AKTYWNE ,AUTORYZOWANE, DATA_UTWORZENIA, EMAIL, HASLO, IMIE, LOGIN, NAZWISKO, ODPOWIEDZKONTROLNA, PYTANIEKONTROLNE, WERSJA)
 VALUES(-7,'klient', 1, 1,'2019-09-19 10:25:30.628',  'klient@shop.pl', '2269669a8dbbe5f19c183b239cb1c1aa187b6a0dc23668d7e54d2c1f86ac7ca4', 'Jan', 'klient', 'Nowak', 'Ewa', 'Imię matki?', 1); 
 INSERT INTO SHOP.KONTO(ID, KONTO, AKTYWNE ,AUTORYZOWANE, DATA_UTWORZENIA, EMAIL, HASLO, IMIE, LOGIN, NAZWISKO, ODPOWIEDZKONTROLNA, PYTANIEKONTROLNE, WERSJA)
 VALUES(-8,'nowe konto', 0, 0,'2019-09-19 10:25:30.628',  'nk@shop.pl', '2269669a8dbbe5f19c183b239cb1c1aa187b6a0dc23668d7e54d2c1f86ac7ca4', 'Jan', 'nk', 'Nowak', 'Ewa', 'Imię matki?', 1); 

-- 
 INSERT INTO SHOP.KONCERT(ID, CENA, DATA, DATA_MODYFIKACJI, DATA_UTWORZENIA, MIEJSCE, PULABILETOW, WERSJA, WYKONAWCA,  WPROWADZKONCERT)
 VALUES(10, 150, '2019-12-10 20:00:30.628', '2019-09-20 20:25:30.628', '2019-09-19 10:25:30.628', 'Atlas Arena', 1000, 1, 'Sting' ,-6);
 INSERT INTO SHOP.KONCERT(ID, CENA, DATA, DATA_MODYFIKACJI, DATA_UTWORZENIA, MIEJSCE, PULABILETOW, WERSJA, WYKONAWCA,  WPROWADZKONCERT)
 VALUES(20, 100, '2019-11-10 20:00:30.628', '2019-09-20 20:25:30.628', '2019-09-19 10:25:30.628', 'Dekompresja', 100, 1, 'Kult' ,-6);
 INSERT INTO SHOP.KONCERT(ID, CENA, DATA, DATA_MODYFIKACJI, DATA_UTWORZENIA, MIEJSCE, PULABILETOW, WERSJA, WYKONAWCA,  WPROWADZKONCERT)
 VALUES(30, 50, '2019-10-10 20:00:30.628', '2019-09-20 20:25:30.628', '2019-09-19 10:25:30.628', 'Wytwórnia', 150, 1, 'Bajm' ,-6);
-- 
INSERT INTO SHOP.BILET(ID, DATA_MODYFIKACJI, DATA_UTWORZENIA, WERSJA, KLIENTID, IDKONCERTU)
VALUES(10, '2019-09-22 20:25:30.628', '2019-09-20 10:25:30.628', 1, -7, 10);
INSERT INTO SHOP.BILET(ID, DATA_MODYFIKACJI, DATA_UTWORZENIA, WERSJA, KLIENTID, IDKONCERTU)
VALUES(20, '2019-09-22 20:25:30.628', '2019-09-19 10:25:30.628', 1, -7, 20);
INSERT INTO SHOP.BILET(ID, DATA_MODYFIKACJI, DATA_UTWORZENIA, WERSJA, KLIENTID, IDKONCERTU)
VALUES(30, '2019-09-22 20:25:30.628', '2019-09-18 10:25:30.628', 1, -7, 30);