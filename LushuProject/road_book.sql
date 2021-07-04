set character set utf8;

create database `Road_Book` default character set utf8 collate utf8_bin;

CREATE TABLE `road_book`.`user_basic_data` (
  `User_id` VARCHAR(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `sex` ENUM('男', '女') NULL,
  `age` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`User_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

INSERT INTO `road_book`.`user_basic_data` (`User_id`, `name`, `sex`, `age`, `email`, `phone`) VALUES ('001', '曾玉欣', '男', '20', '18373189@buaa.edu.cn', '18813026542');
INSERT INTO `road_book`.`user_basic_data` (`User_id`, `name`, `sex`, `age`, `email`, `phone`) VALUES ('002', '潭幸', '男', '20', '18373000@buaa.edu.cn', '12345678900');
INSERT INTO `road_book`.`user_basic_data` (`User_id`, `name`, `sex`, `age`, `email`, `phone`) VALUES ('003', '马泽祁', '男', '20', '18373999@buaa.edu.cn', '09876543211');
INSERT INTO `road_book`.`user_basic_data` (`User_id`, `name`, `sex`, `age`, `email`, `phone`) VALUES ('101', '小猫', '女', '18', '123456@qq.com', '123456');

CREATE TABLE `road_book`.`user_password_permission` (
  `User_id` VARCHAR(11) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `permission` ENUM('普通用户', '管理员', '老板') NOT NULL,
  PRIMARY KEY (`User_id`),
CONSTRAINT `fk_column_User_id` FOREIGN KEY (`User_id`) REFERENCES `user_basic_data` (`User_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

INSERT INTO `road_book`.`user_password_permission` (`User_id`, `password`, `permission`) VALUES ('001', '123456', '普通用户');
INSERT INTO `road_book`.`user_password_permission` (`User_id`, `password`, `permission`) VALUES ('002', '123456', '老板');
INSERT INTO `road_book`.`user_password_permission` (`User_id`, `password`, `permission`) VALUES ('003', '123456', '管理员');
INSERT INTO `road_book`.`user_password_permission` (`User_id`, `password`, `permission`) VALUES ('101', '123456', '普通用户');

CREATE TABLE `road_book`.`route_basic_data` (
  `Route_id` VARCHAR(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `start` VARCHAR(45) NOT NULL,
  `end` VARCHAR(45) NOT NULL,
  `mile` INT NOT NULL,
  `路况` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Route_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

INSERT INTO `road_book`.`Route_basic_data` (`Route_id`, `name`, `start`, `end`, `mile`, `路况`) VALUES ('a01', '路线1', '知春路', '西二旗', '100', '未知');
INSERT INTO `road_book`.`Route_basic_data` (`Route_id`, `name`, `start`, `end`, `mile`, `路况`) VALUES ('b01', '路线2', '新北15公寓', '故宫', '20', '好');
INSERT INTO `road_book`.`Route_basic_data` (`Route_id`, `name`, `start`, `end`, `mile`, `路况`) VALUES ('c01', '路线3', '北航学院路校区', '颐和园', '8', '啦啦啦啦啦啦啦啦啦');
INSERT INTO `road_book`.`Route_basic_data` (`Route_id`, `name`, `start`, `end`, `mile`, `路况`) VALUES ('a02', '路线4', '北航学院路校区', '古北水镇', '126', '啦啦啦啦啦啦啦啦啦');

CREATE TABLE `road_book`.`route_key_point` (
  `Route_id` VARCHAR(11) NOT NULL,
  `point1` VARCHAR(45) NOT NULL,
  `point2` VARCHAR(45) NOT NULL,
  `point3` VARCHAR(45) NULL,
  `point4` VARCHAR(45) NULL,
  PRIMARY KEY (`Route_id`),
CONSTRAINT `fk_column_Route_id` FOREIGN KEY (`Route_id`) REFERENCES `Route_basic_data` (`Route_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

INSERT INTO `road_book`.`route_key_point` (`Route_id`, `point1`, `point2`, `point3`) VALUES ('a01', '知春路', '五道口', '西二旗');
INSERT INTO `road_book`.`route_key_point` (`Route_id`, `point1`, `point2`, `point3`) VALUES ('b01', '新北15公寓', '知春路', '天安门西站');
INSERT INTO `road_book`.`route_key_point` (`Route_id`, `point1`, `point2`, `point3`) VALUES ('c01', '北航学院路校区', '海淀公园', '颐和园');
INSERT INTO `road_book`.`route_key_point` (`Route_id`, `point1`, `point2`, `point3`,`point4`) VALUES ('a02', '北航学院路校区', '休息地1', '休息地2','古北水镇');

CREATE TABLE `road_book`.`activity_basic_data` (
  `activity_id` varchar(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `Route_id` varchar(11) NOT NULL,
  `Date` date NOT NULL,
  `nums` int NOT NULL,
  `about` varchar(200) NOT NULL,
  PRIMARY KEY (`activity_id`),
  CONSTRAINT `fk_activity_Route_id` FOREIGN KEY (`Route_id`) REFERENCES `route_basic_data` (`Route_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

INSERT INTO `road_book`.`activity_basic_data` (`activity_id`, `name`, `Route_id`, `Date`, `nums`, `about`) VALUES ('cx1', '踏春', 'a01', '2021.01.01', '10', '啦啦啦啦啦');
INSERT INTO `road_book`.`activity_basic_data` (`activity_id`, `name`, `Route_id`, `Date`, `nums`, `about`) VALUES ('cx2', '自由行', 'b01', '2021-3-23', '12', '哈哈哈哈哈哈');
INSERT INTO `road_book`.`activity_basic_data` (`activity_id`, `name`, `Route_id`, `Date`, `nums`, `about`) VALUES ('cx3', '活动3', 'a01', '2021.01.01', '10', '啦啦啦啦啦');
INSERT INTO `road_book`.`activity_basic_data` (`activity_id`, `name`, `Route_id`, `Date`, `nums`, `about`) VALUES ('cx4', '活动4', 'a02', '2021-3-23', '12', '哈哈哈哈哈哈');

CREATE TABLE `road_book`.`activity_participants` (
  `activity_id` VARCHAR(11) NOT NULL,
  `stu_id` VARCHAR(8) NOT NULL,
  `sname` VARCHAR(45) NOT NULL,
  `sex` ENUM('男', '女') NOT NULL,
  `age` INT NOT NULL,
  `campus` ENUM('沙河校区', '学院路校区') NOT NULL,
  PRIMARY KEY (`activity_id`,`stu_id`),
CONSTRAINT `fk_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity_basic_data` (`activity_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

INSERT INTO `road_book`.`activity_participants` (`activity_id`, `stu_id`, `sname`, `sex`, `age`, `campus`) VALUES ('cx1', '18373189', '曾玉欣', '男', '19', '学院路校区');
INSERT INTO `road_book`.`activity_participants` (`activity_id`, `stu_id`, `sname`, `sex`, `age`, `campus`) VALUES ('cx2', '18373111', '小a', '女', '20', '沙河校区');
INSERT INTO `road_book`.`activity_participants` (`activity_id`, `stu_id`, `sname`, `sex`, `age`, `campus`) VALUES ('cx3', '18373189', '曾玉欣', '男', '19', '学院路校区');
INSERT INTO `road_book`.`activity_participants` (`activity_id`, `stu_id`, `sname`, `sex`, `age`, `campus`) VALUES ('cx4', '18373111', '马泽祁', '男', '20', '沙河校区');
INSERT INTO `road_book`.`activity_participants` (`activity_id`, `stu_id`, `sname`, `sex`, `age`, `campus`) VALUES ('cx3', '18373000', '潭幸', '男', '19', '学院路校区');
INSERT INTO `road_book`.`activity_participants` (`activity_id`, `stu_id`, `sname`, `sex`, `age`, `campus`) VALUES ('cx4', '18373999', 'ccc', '女', '20', '沙河校区');

