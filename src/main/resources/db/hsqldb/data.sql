-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- One manager user, named manager1 with passwor m4n4g3r1
INSERT INTO users(username,password,enabled) VALUES ('manager1','m4n4g3r1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'manager1','manager');
-- One manager user, named manager2 with passwor m4n4g3r2
INSERT INTO users(username,password,enabled) VALUES ('manager2','m4n4g3r2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'manager2','manager');

-- One manager user, named pilot1 with password p1l0t1
INSERT INTO users(username,password,enabled) VALUES ('pilot1','p1l0t1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'pilot1','pilot');
-- One manager user, named pilot2 with password p1l0t2
INSERT INTO users(username,password,enabled) VALUES ('pilot2','p1l0t2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'pilot2','pilot');

-- One manager user, named mechanic1 with password m3ch4n1c1
INSERT INTO users(username,password,enabled) VALUES ('mechanic1','m3ch4n1c1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'mechanic1','mechanic');
-- One manager user, named pilot2 with password p1l0t2
INSERT INTO users(username,password,enabled) VALUES ('mechanic2','m3ch4n1c2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'mechanic2','mechanic');

-- One pilot user, named pilot3 with password p1l0t3
INSERT INTO users(username,password,enabled) VALUES ('pilot3','p1l0t3',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'pilot3','pilot');

INSERT INTO managers VALUES(1,'1960-01-01','12345678A','Herrera','Carlos','España','España','manager1');

INSERT INTO managers VALUES(2,'1960-01-01','12345678B','Herrera','Carlos','España','España','manager2');

INSERT INTO pilots VALUES(1,'1960-01-01','12345678C','Valentino','Rossi','Italia','Italia','1.82','12','72.5','pilot1');

INSERT INTO pilots VALUES(2,'1960-01-01','12345678D','Giacomo','Agostini','Italia','Italia','1.83','45','71.4','pilot2');

INSERT INTO pilots VALUES(3,'1993-06-23','12345678G','Marc','Marquez','España','España','1.70','93','65.2','pilot3');

INSERT INTO mechanics VALUES(1,'1960-01-01','12345678E','Cesar','Antonini','Italia','Italia','1','mechanic1');

INSERT INTO mechanics VALUES(2,'1960-01-01','12345678F','Marco','Alcasini','Italia','Italia','2','mechanic2');

INSERT INTO motorcycles VALUES(1,'YAMAHA','250','320','180.0','20.0','240',1);
INSERT INTO motorcycles VALUES(2,'HONDA','250','302','201.0','21.0','240',2);

INSERT INTO grandprix VALUES(1, 'KymiRing', '2021-03-22', '116.3', '25', 'Finlandia');

INSERT INTO grandprix_pilots VALUES(1,1);
INSERT INTO grandprix_pilots VALUES(1,2);

INSERT INTO teams VALUES(1,'1960-01-01 12:40:01','LAS DIVINAS','12345678D','1');
--INSERT INTO teams VALUES(2,'1960-01-01 12:40:01','LAS POPULARES','12345674D','2');

INSERT INTO forums VALUES(1,'2020-01-01','FORUM',1);

INSERT INTO teams_pilot VALUES(1,1);
INSERT INTO teams_pilot VALUES(1,2);
--INSERT INTO teams_pilot VALUES(2,3);
INSERT INTO teams_mechanic VALUES(1,1);
INSERT INTO teams_mechanic VALUES(1,2);

--INSERT INTO positions VALUES(1,25,1,1);
--INSERT INTO positions VALUES(2,20,2,2);

--INSERT INTO grandprix_positions VALUES(1,1);
--INSERT INTO grandprix_positions VALUES(1,2);

INSERT INTO messages VALUES(1,'1960-01-01 12:40:01','TEXT','TITLE','manager1');

