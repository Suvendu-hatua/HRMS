use hrms;

--For all the users---password:hrms@123

insert into person values(1,"suvendu@hrms.com","{bcrypt}$2a$12$EfKfkDwuRqWsWI/h4QEXp.Y43IV6JbgZnPgdbDbrxnTtxyMqjGmQO","HR");
insert into person values(2,"prakash@hrms.com","{bcrypt}$2a$12$EfKfkDwuRqWsWI/h4QEXp.Y43IV6JbgZnPgdbDbrxnTtxyMqjGmQO","HR");
insert into person values(3,"admin@hrms.com","{bcrypt}$2a$12$EfKfkDwuRqWsWI/h4QEXp.Y43IV6JbgZnPgdbDbrxnTtxyMqjGmQO","ADMIN");

insert into hr values(1,"Suvendu","Hatua","9876543216",1);
insert into hr values(2,"Prakash","Khatua","9876543216",2);
insert into admin values(1,"Admin","Admin","9876543216",3);