INSERT INTO users VALUES (1000, 'ADMIN', 'admin@test.com', '$2a$10$F00U/hsXrqV/p/b1XDilDeGNhCAFbi6dXlMe503FGCjLuC0ttXMRW', 'admin');
INSERT INTO users VALUES (1001, 'Tayfur Ünal', 'test@test.com', '$2a$10$F00U/hsXrqV/p/b1XDilDeGNhCAFbi6dXlMe503FGCjLuC0ttXMRW', 'user1');
INSERT INTO mentor VALUES (1000, 'Backend Development', 'IN_PROGRESS', '.Net Core, SQL', 'Merhaba, ben Tayfur Ünal. Sakarya Üniversitesi Bilgisayar Mühendisliği mezunuyum ve 10 senedir yazılım mühendisi olarak çalışıyorum. ASP.NET, Javascript ve .NET Core kullanarak geliştirme yapıyorum.', 1001);

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