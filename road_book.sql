set character set utf8;

create database road_book default character set utf8 collate utf8_bin;
set global log_bin_trust_function_creators=TRUE;

use road_book;
create table users (
	user_id varchar(14) NOT NULL,
    user_name varchar(64) NOT NULL,
    sex varchar(16),
    age int,
    email varchar(128) unique,
    phone varchar(32) unique,
    primary key (user_id)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create table rights (
	user_id varchar(14) NOT NULL,
	password varchar(64) NOT NULL,
    role varchar(32) NOT NULL,
    primary key (user_id)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create table routes (
	route_id varchar(14) NOT NUll,
	route_name varchar(64) NOT NULL,
	start varchar(128) NOT NULL,
	destination varchar(128) NOT NULL,
	mileage double,
	cumulative_climb double,
	road_condition varchar(64),
	author varchar(14),
	primary key (route_id)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create table keypoints (
	route_id varchar(14),
	keypoint_1 varchar(128) NOT NULL,
	keypoint_2 varchar(128) NOT NULL,
	keypoint_3 varchar(128),
	keypoint_4 varchar(128),
	primary key (route_id)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create table activities (
	activity_id varchar(14),
	activity_name varchar(64) NOT NULL,
	route_id varchar(14),
	date date,
	participants_number int,
	introduction varchar(255),
	host varchar(14),
	primary key (activity_id)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create table participants (
	activity_id varchar(14),
	stu_id varchar(64),
	stu_name varchar(64) NOT NULL,
	stu_sex varchar(16),
	stu_age int,
	stu_phone varchar(32),
	campus varchar(128),
	primary key (activity_id,stu_id)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

create trigger trig_user_rights
BEFORE DELETE on users for each row
DELETE FROM rights where (rights.user_id = old.user_id);

delimiter $$
create trigger trig_user_rou_act
before delete on users for each row
begin
	update routes set author = 'null' where (author = old.user_id);
	update activities set host = 'null' where (host = old.user_id);
end $$
delimiter ;

create trigger trig_routes_keypoints
before delete on routes for each row
delete from keypoints where (keypoints.route_id = old.route_id);

create trigger trig_routes_activities
before delete on routes for each row
delete from activities where (activities.route_id = old.route_id);

create trigger trig_act_partici
before delete on activities for each row
delete from participants where (participants.activity_id = old.activity_id);

delimiter $$
create function get_user_id(in_id varchar(14))
returns varchar(14)
begin 
	declare out_ varchar(14);
	declare out_id bigint;
	declare out_type varchar(1);
	
	declare read_ varchar(14);
	declare read_id bigint;
	
	declare done int default false;
	declare cur cursor for select user_id from users;
	declare continue HANDLER for not found set done = true;
	
	select left(in_id,1) into out_type;
	select cast(right(in_id,13)as unsigned) into out_id;
	
	open cur;
	read_loop:loop
	fetch cur into read_;
	if done then 
		leave read_loop;
	end if;
	
	select cast(right(read_,13) as unsigned) into read_id;
	if(read_id >= out_id) then
		set out_id = read_id + 1;
	end if;
	
	end loop;
	close cur;
	
	select concat(out_type,lpad(out_id,13,0)) into out_;
	return out_;
end $$
delimiter ;

create trigger trig_user_id
before insert on users for each row
set new.user_id = get_user_id(new.user_id);

delimiter $$
create function get_route_id(in_id varchar(14))
returns varchar(14)
begin 
	declare out_ varchar(14);
	declare out_id bigint;
	declare out_type varchar(1);
	
	declare read_ varchar(14);
	declare read_id bigint;
	
	declare done int default false;
	declare cur cursor for select route_id from routes;
	declare continue HANDLER for not found set done = true;
	
	select left(in_id,1) into out_type;
	select cast(right(in_id,13)as unsigned) into out_id;
	
	open cur;
	read_loop:loop
	fetch cur into read_;
	if done then 
		leave read_loop;
	end if;
	
	select cast(right(read_,13) as unsigned) into read_id;
	if(read_id >= out_id) then
		set out_id = read_id + 1;
	end if;
	
	end loop;
	close cur;
	
	select concat(out_type,lpad(out_id,13,0)) into out_;
	return out_;
end $$
delimiter ;

create trigger trig_route_id
before insert on routes for each row
set new.route_id = get_route_id(new.route_id);

delimiter $$
create function get_activity_id(in_id varchar(14))
returns varchar(14)
begin 
	declare out_ varchar(14);
	declare out_id bigint;
	declare out_type varchar(1);
	
	declare read_ varchar(14);
	declare read_id bigint;
	
	declare done int default false;
	declare cur cursor for select activity_id from activities;
	declare continue HANDLER for not found set done = true;
	
	select left(in_id,1) into out_type;
	select cast(right(in_id,13)as unsigned) into out_id;
	
	open cur;
	read_loop:loop
	fetch cur into read_;
	if done then 
		leave read_loop;
	end if;
	
	select cast(right(read_,13) as unsigned) into read_id;
	if(read_id >= out_id) then
		set out_id = read_id + 1;
	end if;
	
	end loop;
	close cur;
	
	select concat(out_type,lpad(out_id,13,0)) into out_;
	return out_;
end $$
delimiter ;

create trigger trig_activity_id
before insert on activities for each row
set new.activity_id = get_activity_id(new.activity_id);