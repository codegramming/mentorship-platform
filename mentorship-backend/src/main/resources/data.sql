INSERT INTO users VALUES (1000, 'Tayfur Ünal', 'test1@test.com', '$2a$10$F00U/hsXrqV/p/b1XDilDeGNhCAFbi6dXlMe503FGCjLuC0ttXMRW','LOCAL',NULL, 'user1');
INSERT INTO users VALUES (1001, 'Mehmet Kaya', 'test2@test.com', '$2a$10$F00U/hsXrqV/p/b1XDilDeGNhCAFbi6dXlMe503FGCjLuC0ttXMRW','LOCAL',NULL, 'user2');
INSERT INTO users VALUES (1002, 'Fatma Deniz', 'test3@test.com', '$2a$10$F00U/hsXrqV/p/b1XDilDeGNhCAFbi6dXlMe503FGCjLuC0ttXMRW','LOCAL',NULL, 'user3');
INSERT INTO users VALUES (2000, 'Tayfur Ünal', 'admin@test.com', '$2a$10$F00U/hsXrqV/p/b1XDilDeGNhCAFbi6dXlMe503FGCjLuC0ttXMRW','LOCAL',NULL, 'admin');
INSERT INTO mentor VALUES (300, 'Backend Development', 'ACCEPTED', 'Spring Framework, Java', 'Merhaba, ben Tayfur Ünal. Sakarya Üniversitesi Bilgisayar Mühendisliği mezunuyum ve 10 senedir yazılım mühendisi olarak çalışıyorum. Spring Framework, Java ve Javascript kullanarak geliştirme yapıyorum.', 1000);
INSERT INTO mentor VALUES (301, 'Frontend Development', 'ACCEPTED', 'Javascript, React', 'Merhaba, ben Mehmet Kaya. İstanbul Üniversitesi Bilgisayar Mühendisliği mezunuyum ve 5 senedir yazılım mühendisi olarak çalışıyorum. Javascript, React ve CSS kullanarak geliştirme yapıyorum.', 1001);
INSERT INTO mentor VALUES (302, 'iOS Development', 'IN_PROGRESS', 'Swift', 'Merhaba, ben Fatma Deniz. Marmara Üniversitesi Bilgisayar Mühendisliği mezunuyum ve 7 senedir yazılım mühendisi olarak çalışıyorum. Swift ve React Native kullanarak geliştirme yapıyorum.', 1002);

INSERT INTO mentor_user VALUES (1000,300);
INSERT INTO mentor_user VALUES (1001,301);
INSERT INTO mentor_user VALUES (1002,302);

INSERT INTO topic VALUES (1000, 'Backend Development');
INSERT INTO topic VALUES (2000, 'iOS Development');
INSERT INTO topic VALUES (3000, 'Frontend Development');

INSERT INTO topic_sub_title VALUES (1000, 'C#');
INSERT INTO topic_sub_title VALUES (1000, '.Net Core');
INSERT INTO topic_sub_title VALUES (1000, 'Java');
INSERT INTO topic_sub_title VALUES (1000, 'Spring Framework');
INSERT INTO topic_sub_title VALUES (2000, 'Swift');
INSERT INTO topic_sub_title VALUES (2000, 'Swift UI');
INSERT INTO topic_sub_title VALUES (3000, 'Javascript');
INSERT INTO topic_sub_title VALUES (3000, 'HTML');
INSERT INTO topic_sub_title VALUES (3000, 'CSS');
INSERT INTO topic_sub_title VALUES (3000, 'React');
INSERT INTO topic_sub_title VALUES (3000, 'Angular');