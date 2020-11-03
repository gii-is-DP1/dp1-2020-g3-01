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

INSERT INTO managers VALUES(1,'1960-01-01','Herrera','Carlos','España','España','manager1');
