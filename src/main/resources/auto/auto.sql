create sequence hibernate_sequence start 1 increment 1
create table app_role (role_id int8 not null, role_name varchar(30) not null, primary key (role_id))
create table app_user (user_id int8 not null, email varchar(128) not null, enabled boolean not null, encryted_password varchar(128) not null, first_name varchar(36), last_name varchar(36), user_name varchar(36) not null, primary key (user_id))
create table persistent_logins (series varchar(64) not null, last_used timestamp not null, token varchar(64) not null, username varchar(64) not null, primary key (series))
create table user_role (role_id int8, user_id int8 not null, primary key (user_id))
create table userconnection (userid varchar(255) not null, provideruserid varchar(255) not null, providerid varchar(255) not null, accesstoken varchar(512), displayname varchar(255), expiretime int8, imageurl varchar(512), profileurl varchar(512), rank int4 not null, refreshtoken varchar(512), secret varchar(512), primary key (userid, provideruserid, providerid))
alter table app_role add constraint APP_ROLE_UK unique (role_name)
alter table app_user add constraint APP_USER_UK unique (user_name)
alter table app_user add constraint APP_USER_UK2 unique (email)
alter table user_role add constraint FKp6m37g6n6c288s096400uw8fw foreign key (role_id) references app_role
alter table user_role add constraint FKg7fr1r7o0fkk41nfhnjdyqn7b foreign key (user_id) references app_user

create table app_role (role_id  bigserial not null, role_name varchar(30) not null, primary key (role_id))
create table app_user (user_id  bigserial not null, email varchar(128) not null, enabled boolean not null, encryted_password varchar(128) not null, first_name varchar(36), last_name varchar(36), user_name varchar(36) not null, primary key (user_id))
create table persistent_logins (series varchar(64) not null, last_used timestamp not null, token varchar(64) not null, username varchar(64) not null, primary key (series))
create table user_role (role_id int8, user_id int8 not null, primary key (user_id))
create table userconnection (userid varchar(255) not null, provideruserid varchar(255) not null, providerid varchar(255) not null, accesstoken varchar(512), displayname varchar(255), expiretime int8, imageurl varchar(512), profileurl varchar(512), rank int4 not null, refreshtoken varchar(512), secret varchar(512), primary key (userid, provideruserid, providerid))
create table verification_token (id  bigserial not null, created_date date, expiry_date date, token varchar(255), user_id int8 not null, primary key (id))
alter table app_role add constraint APP_ROLE_UK unique (role_name)
alter table app_user add constraint APP_USER_UK unique (user_name)
alter table app_user add constraint APP_USER_UK2 unique (email)
alter table user_role add constraint FKp6m37g6n6c288s096400uw8fw foreign key (role_id) references app_role
alter table user_role add constraint FKg7fr1r7o0fkk41nfhnjdyqn7b foreign key (user_id) references app_user
alter table verification_token add constraint FK1tc78gv9fnf5oyv26e2uy0xy0 foreign key (user_id) references app_user
