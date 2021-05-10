-- Create user hiuser & set password hipwd
CREATE USER `hiuser`@`%` IDENTIFIED WITH mysql_native_password BY 'hipwd';

-- Create database
CREATE DATABASE `hiregister` CHARACTER SET 'utf8' COLLATE 'utf8_bin';

-- Add hiuser rights
GRANT Alter, Alter Routine, Create, Create Routine, Create Temporary Tables, Create View, Delete, Drop, Event, Execute, Grant Option, Index, Insert, Lock Tables, References, Select, Show View, Trigger, Update ON `hiregister`.* TO `hiuser`@`%`;

use hiregister;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
                        `id` varchar(50) COLLATE utf8_bin NOT NULL,
                        `deptid` varchar(10) COLLATE utf8_bin NOT NULL,
                        `dname` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                        `pareid` varchar(10) COLLATE utf8_bin DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for drinfo
-- ----------------------------
DROP TABLE IF EXISTS `drinfo`;
CREATE TABLE `drinfo` (
                          `id` varchar(50) COLLATE utf8_bin NOT NULL,
                          `drinfoid` varchar(50) COLLATE utf8_bin NOT NULL,
                          `jobage` int(3) DEFAULT NULL,
                          `hiredate` date DEFAULT NULL,
                          `deptid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
                            `mid` varchar(50) COLLATE utf8_bin NOT NULL,
                            `pdid` varchar(50) COLLATE utf8_bin NOT NULL,
                            `name` varchar(50) COLLATE utf8_bin NOT NULL,
                            `function` varchar(100) COLLATE utf8_bin NOT NULL,
                            `specification` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '规格',
                            `ingredient` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '药物成分',
                            `price` double NOT NULL,
                            `details` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                            `type` varchar(20) COLLATE utf8_bin DEFAULT NULL,
                            PRIMARY KEY (`mid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for producer
-- ----------------------------
DROP TABLE IF EXISTS `producer`;
CREATE TABLE `producer` (
                            `pdid` varchar(50) COLLATE utf8_bin NOT NULL,
                            `pdname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                            `pdcode` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                            PRIMARY KEY (`pdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for regbill
-- ----------------------------
DROP TABLE IF EXISTS `regbill`;
CREATE TABLE `regbill` (
                           `rbid` varchar(50) COLLATE utf8_bin NOT NULL,
                           `regbillid` varchar(50) COLLATE utf8_bin NOT NULL,
                           `aptdate` datetime DEFAULT NULL,
                           `time` int(5) NOT NULL,
                           `ptinfoid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                           `userid` varchar(50) COLLATE utf8_bin NOT NULL,
                           `createtime` datetime NOT NULL,
                           `drid` varchar(50) COLLATE utf8_bin NOT NULL,
                           `deptid` varchar(50) COLLATE utf8_bin NOT NULL,
                           `state` varchar(4) COLLATE utf8_bin NOT NULL,
                           `total` double(10,2) DEFAULT NULL,
                           `extra` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                           PRIMARY KEY (`rbid`),
                           KEY `regbillid` (`regbillid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for regbill_ep
-- ----------------------------
DROP TABLE IF EXISTS `regbill_ep`;
CREATE TABLE `regbill_ep` (
                              `epid` varchar(50) COLLATE utf8_bin NOT NULL,
                              `rbid` varchar(50) COLLATE utf8_bin NOT NULL,
                              `amount` int(10) DEFAULT NULL,
                              `createtime` datetime NOT NULL,
                              `epname` varchar(80) COLLATE utf8_bin NOT NULL,
                              `epmoney` double(10,2) NOT NULL,
                              `extra` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                              PRIMARY KEY (`epid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for regbill_info
-- ----------------------------
DROP TABLE IF EXISTS `regbill_info`;
CREATE TABLE `regbill_info` (
                                `rbinfoid` varchar(50) COLLATE utf8_bin NOT NULL,
                                `content` varchar(255) COLLATE utf8_bin NOT NULL,
                                `rbid` varchar(50) COLLATE utf8_bin NOT NULL,
                                `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`rbinfoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for regright
-- ----------------------------
DROP TABLE IF EXISTS `regright`;
CREATE TABLE `regright` (
                            `rrid` varchar(50) COLLATE utf8_bin NOT NULL,
                            `userid` varchar(50) COLLATE utf8_bin NOT NULL,
                            `fromrole` varchar(10) COLLATE utf8_bin NOT NULL,
                            `torole` varchar(10) COLLATE utf8_bin NOT NULL,
                            `handler` varchar(50) COLLATE utf8_bin NOT NULL,
                            `state` varchar(10) COLLATE utf8_bin NOT NULL,
                            `extra` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                            PRIMARY KEY (`rrid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for rights
-- ----------------------------
DROP TABLE IF EXISTS `rights`;
CREATE TABLE `rights` (
                          `rightid` varchar(50) COLLATE utf8_bin NOT NULL,
                          `name` varchar(50) COLLATE utf8_bin NOT NULL,
                          `url` varchar(100) COLLATE utf8_bin NOT NULL,
                          `description` varchar(100) COLLATE utf8_bin DEFAULT NULL,
                          `roleid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                          PRIMARY KEY (`rightid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
                        `roleid` varchar(50) COLLATE utf8_bin NOT NULL,
                        `rolename` varchar(50) COLLATE utf8_bin NOT NULL,
                        PRIMARY KEY (`roleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage` (
                           `sid` varchar(50) COLLATE utf8_bin NOT NULL,
                           `mid` varchar(50) COLLATE utf8_bin NOT NULL,
                           `amount` int(11) DEFAULT NULL,
                           PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` varchar(50) COLLATE utf8_bin NOT NULL,
                        `loginid` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '登录帐号',
                        `password` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '密码',
                        `state` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '帐号状态',
                        `infoid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户信息外键',
                        PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
                            `id` varchar(50) COLLATE utf8_bin NOT NULL,
                            `infoid` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'uuid生成主键',
                            `username` varchar(30) COLLATE utf8_bin NOT NULL,
                            `realname` varchar(30) COLLATE utf8_bin NOT NULL,
                            `idtype` varchar(4) COLLATE utf8_bin DEFAULT NULL,
                            `idcard` varchar(30) COLLATE utf8_bin DEFAULT NULL,
                            `gender` varchar(4) COLLATE utf8_bin DEFAULT NULL,
                            `age` int(3) DEFAULT NULL,
                            `birthday` varchar(20) COLLATE utf8_bin DEFAULT NULL,
                            `email` varchar(35) COLLATE utf8_bin DEFAULT NULL,
                            `phone` int(14) DEFAULT NULL,
                            `picurl` varchar(40) COLLATE utf8_bin DEFAULT NULL,
                            `regtime` datetime DEFAULT NULL COMMENT '注册时间',
                            `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                            `roleid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '角色类型外键',
                            `opwds` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- View structure for storageinfo
-- ----------------------------
DROP VIEW IF EXISTS `storageinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `storageinfo` AS select `s`.`sid` AS `sid`,`s`.`mid` AS `mid`,`s`.`amount` AS `amount`,`m`.`name` AS `name`,`m`.`type` AS `type` from (`storage` `s` left join `medicine` `m` on((`s`.`mid` = `m`.`mid`))) ;
SET FOREIGN_KEY_CHECKS=1;


-- dept data 部门信息
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('5768a50de3fc41f0b725c5fcd7acbcd8', '1', '内科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('9aecffb335f0451dabff6e74f2980375', '1-0', '心血管内科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('6097d7ee84d14f5d993ac513bc26eb6c', '1-1', '神经内科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b8ffc67679ae412d867d2d1691cf1601', '1-2', '消化内科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('53b580f9220c41aca8da9b4da1542167', '1-3', '内分泌科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('7f98bc70b110457eae29ffa3cf571308', '1-4', '免疫科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b7ebae6ed7f540558d07e9b314982544', '1-5', '呼吸科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('237c32e8d0044631918069144a63d2c1', '1-6', '肾病内科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('189ba19d9e6443e88814a91471f62e49', '1-7', '血液科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a0f02503ed7b4854a96864cde6f363cc', '1-8', '感染内科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e5caa814f271432a9fa34a2ba780d654', '1-9', '过敏反应科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d23dc06f680744b58c7a8ec6226bc232', '1-10', '老年病科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d089d446cbb24edd88117d341a2dddd2', '1-11', '普通内科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('69efa765513d483ab887f0a3c1343703', '1-12', '高压氧科', '1');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('1228f0242cc14a73a02391e5d8c8dc7e', '2', '外科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('49d3f69cd3854086979a5bc590b049e7', '2-0', '神经外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d14782adecb44d7cbb6c146b7d06639f', '2-1', '功能神经外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('df516a3d4cae49b19146c582b6636300', '2-2', '心血管外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e7e46ac1ad5b4a8ab26d0812c75df89e', '2-3', '胸外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d39282c0c0d54e138cca9b532a62b661', '2-4', '整形科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('1f301f286d334c20ae79aa07081e3b79', '2-5', '乳腺外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('4ef18b0c1fc24546963fabe7fd33f3fa', '2-6', '泌尿外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('52b56eaa7912490d8d5dc5035b225766', '2-7', '肝胆外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('cc53aae6abb1452bb3529c10b2240e04', '2-8', '肛肠科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('05f2e3587d03468f85dcf110e536c03d', '2-9', '血管外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e46f273a505d4ce5a87bce29a5431667', '2-10', '器官移植', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b2d032baf5264c21908c84de3cb273dc', '2-11', '微创外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('55a0701e92914b7eaca1a8a60ee83d7d', '2-12', '普外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e134b19457bd4f08ab884d21983bcde8', '2-13', '脑外科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('4d6f2b14d049466ea8a6bc88f9d840f6', '2-14', '烧伤科', '2');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e818d6eadd774145a213c5c9e4df9d31', '3', '妇产科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a69e2b20b79c45218bde3415f7e95175', '3-0', '妇科', '3');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('4e2f4f82ccd64f6da86f794974d88abc', '3-1', '产科', '3');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('61127f41230b4cfb9ac46faa1cdab79b', '3-2', '妇科内分泌', '3');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('820b580a7b0949a3984dc93efb2ff564', '3-3', '妇泌尿科', '3');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d1d08c8bcb1349a7b777d0824d43c58b', '3-4', '遗传咨询科', '3');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('684913cc52c34eda9b460ca33e4fe24d', '3-5', '计划生育科', '3');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('461d645c046744ed91f9c2eaff9871bd', '3-6', '妇产科', '3');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('05abc4bd601f433689a21cec953c4dc8', '4', '生殖中心', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('5023d74b98084e66ab1432dbb3735e15', '4-0', '生殖中心', '4');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('c8b141841f4a4fb7afffa9a004cc28cc', '5', '儿科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('071f3fcf026b406da1bb192c5a669814', '5-0', '儿科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('0e7cbb3e88f745b3868c505e47d0a6b1', '5-1', '新生儿科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('20724ba6f1ab4c48ae86d954ffbc2c66', '5-2', '小儿呼吸科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('9f479a7e1dd04e448464a3f9d8012d14', '5-3', '小儿营养保健科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('383361f4f2b44d87b3196be9c70cfd9a', '5-4', '小儿心内科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('6ee0131e3f6f4f2bbe4949f2b38c9b43', '5-5', '小儿皮肤科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('cdb7d3d3c587454985653b050a7d09ae', '5-6', '小儿精神科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ac46a84ee3f74a6090d53254a26e8900', '5-7', '小儿外科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('02658027d6a24082b88d2acdcb1ff386', '5-8', '小儿心外科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('efd128e75d364660a864b9ecc4ff9faa', '5-9', '小儿骨科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('6b8527e897ab458691001d6c092442fc', '5-10', '小儿神经外科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('66c777e4dc9d4d079293b025e4e63fdb', '5-11', '小儿康复科', '5');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('2e65126909a843de8c2ad59443dd6f52', '6', '骨外科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('27321d1919cb491e877aa2e4613f8dea', '6-0', '骨科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a4badafd13104eb1933ce2b982b5fa69', '6-1', '脊柱外科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('f1164165548149b489e99877e2fe932e', '6-2', '手外科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ab6ca248d34144fa8f782b7cd9347957', '6-3', '创伤骨科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d7512be58d4146d3bacc378fe6949be6', '6-4', '骨关节科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('11d979c1de13422c9718023986fd8c97', '6-5', '矫形骨科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('4a02915e5f6543268e72d92c2b7a9f3f', '6-6', '骨质疏松科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ac1e62660c20484ba96a6390ff7c85e1', '6-7', '骨伤专科', '6');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('cd90e31dd27945748fb71c789443d660', '7', '眼科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e98edca9c792412a898c43547f6407ed', '7-0', '眼科', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('3748eb64146048fba6af1bc9de4d3ddd', '7-1', '小儿眼科', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('696b265dfe824ba7b8dbee2d64a565b0', '7-2', '眼底', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('02465075d13d407aacf58ecd4f8e8695', '7-3', '角膜科', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('818a357110264d7fa2a55d1ae4aaee50', '7-4', '青光眼', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('642631f54bee42289c7bef0acd650750', '7-5', '白内障', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('4c69708fb8ee4cff84cff88c0675177e', '7-6', '眼外伤', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('3e3c038c22a64820a356cbf20a0e2f18', '7-7', '眼眶及肿瘤', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ba0d9315fa4a48a8ae64511f3bd838f2', '7-8', '屈光', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('18a1661791204fdc86b0702f0010c0f8', '7-9', '眼整形', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('08c1e4ddf05540b595b991a4dba2e523', '7-10', '中医眼科', '7');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('92e9553a9e1048ab92d5209132c2bae7', '8', '口腔科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b9c5b7a5d5a349b987ac9099079eb766', '8-0', '口腔科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('c7717393a0cd4be7902e3b771ba2738c', '8-1', '颌面外科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d79ddc38bc7d4c37993caced15ef2a41', '8-2', '正畸科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('7871d9cdf106468395854cd8fd62ccc6', '8-3', '牙体牙髓科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('384fa64ad5804cc9899b455b37e94abe', '8-4', '牙周科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('9e9001a9c1af4fbc91f0807862eb2df7', '8-5', '口腔粘膜科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('948fe5b700d143c5a33cd3e35556754a', '8-6', '儿童口腔科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('15e3fd0e755b49ee882515f228418c91', '8-7', '口腔修复科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('6e1186820efa4f33a340af087aafdf39', '8-8', '种植科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('44710ac6ac3d4741b0d2a07745eba0a7', '8-9', '口腔预防科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('f18a7510be5d4011a61e7d599f3e3ffb', '8-10', '口腔特诊科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('c67f4182004e46a8b8b8b7448355d5ae', '8-11', '口腔急诊科', '8');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ccb15da6200041248ab95988545d07a2', '9', '耳鼻咽喉头颈科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('1b11ef89399244be9cb135c1fa4fb3b4', '9-0', '耳鼻喉', '9');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('04442378f7b24accb39c79b460ac8d1f', '9-1', '头颈外科', '9');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('cc3264b6b86d4188a2b838ad2f71d248', '10', '肿瘤科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ac49b699e6ce4795954700453a7d16f7', '10-0', '肿瘤内科', '10');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('1525fcf4f35e4a94a6f1bf3253d41fcb', '10-1', '肿瘤外科', '10');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('4d1b4e858831480a89098f7eaada46e5', '10-2', '肿瘤妇科', '10');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('2c96dc0e1b68474c89fdac71e690d536', '10-3', '放疗科', '10');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('842b1d856a8a411abf6a36dd2b7a7d6b', '10-4', '骨肿瘤科', '10');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('c7cfd513596541298a47aac10e33df92', '10-5', '肿瘤康复科', '10');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('12b1808c5c6f47c9bac1c163d22eab5b', '10-6', '肿瘤综合科', '10');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('274fe60f3a024625974e069b5e8c6826', '11', '皮肤性病科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a0ba60224af54b29834b4afbf158654b', '11-0', '皮肤科', '11');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('15ccf84ad38341ef90f9fb0633b5c0a4', '11-1', '性病科', '11');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('30a9bd4c63dc4c4a85f253bddd517ef9', '12', '男性学科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a285030a78c04b2ab53c824da631a0fd', '12-0', '男性学科', '12');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('14d3662912314766b2237b33ac57dfa9', '13', '皮肤美容', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('5251a46df6e24d2ba6808644a513e363', '13-0', '皮肤美容', '13');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b4c6cefc08cc4c92afc7b46a6103dc95', '14', '烧伤科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('94fc70a6a67248feb3436fc281853884', '14-0', '烧伤科', '14');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('af2a66f2c7a44f3fb83812e4ffed0ef1', '15', '精神心理科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('86be4a65ae8440f5b2cace62bc474579', '15-0', '精神科', '15');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b02d542090e44a768940c79e4a04f807', '15-1', '心理咨询科', '15');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ee4e174513dd4e0b9a20494e942e8596', '15-2', '司法鉴定科', '15');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('842fba6f79fc4191a6952f738cc82de4', '15-3', '双相障碍科', '15');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('8383dc56a0c74863a47662ee33f23a96', '15-4', '药物依赖科', '15');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('6107ad29da7343f0a680e466e044cf19', '16', '中医科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('de400bf22e884d4fb006e3743f814209', '16-0', '中医妇产科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('cc3f0ceec46041eaa6288f9865cf88cf', '16-1', '中医儿科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('53902b231802477a87d7ab67eddd6b6d', '16-2', '中医骨科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('97c924b3b659411a8e44dae053d2a5b4', '16-3', '中医皮肤科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a9490b62364c4907b3ee6961d3cdc534', '16-4', '中医内分泌', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('5481fe35fc0f402ba2e67afdac630e28', '16-5', '中医消化科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('8b99ac50ddc34297855de307aa24d916', '16-6', '中医呼吸科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e2548e1c4daa48e4964daafd0536a9d8', '16-7', '中医肾病内科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('12f90925538040139d7545b888b95f9f', '16-8', '中医免疫内科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e3d92ac0c8b34d1a81e3adbd7ef7c98d', '16-9', '中医心内科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('4b55b8ad0efc4809aa70c0402c9e0b63', '16-10', '中医神经内科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b3a6275f5f9e42a196a30ff5360bb1be', '16-11', '中医肿瘤科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('7d57f7408d3243b3ae48df3a0c098e03', '16-12', '中医血液科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('279e269a5293423d8bd29b990766d275', '16-13', '中医肝病科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('abed951d61f444118377d10d11da0659', '16-14', '中医五官科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ae964ca9508d48fba3c019b2309c60de', '16-15', '中医男科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('fc324b27092446d58a4331cf5b8188c1', '16-16', '针灸科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('760c59d6f0584eaf985e71928a27ddb8', '16-17', '中医按摩科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('29886cbd00144f6c80f9e9f29cfff1c1', '16-18', '中医外科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('05c99c52fb4444a9afd11e88a6aff7ef', '16-19', '中医乳腺外科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('fc05519b1c684fd58c94234fecddbcd7', '16-20', '中医肛肠科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('3a055c0383c34c41b6bb6a2d962d4367', '16-21', '中医老年病科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a244c5d451c24e39a75b2f21c048227d', '16-22', '中医科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('aafc9a9e35ff4951bf27232bbe363512', '16-23', '中医内科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('55d0fbeeee434697bb1c46db4dcb81e3', '16-24', '中医脾胃科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ce3d66fd19bc408096d0fe591190737c', '16-25', '膏方门诊', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ce6f4337734b47e5b28e4faf0b430ba8', '16-26', '中医骨伤科', '16');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d7302f63f2a34b338b94bd0661aa3872', '17', '中西医结合科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('f80e25823cc74cd1ae45a820f89870e8', '17-0', '中西医结合科', '17');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b8e73c2677854450aa1e195c0d1966e7', '18', '传染病科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('0aa8063ba2f14a84a62a95e016e26b62', '18-0', '肝病科', '18');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('cbaf8a6b41c64e7ba0ad19ef56979619', '18-1', '传染科', '18');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('cc7e9f6a87c04acc83dad2cd7f0b325d', '19', '结核病科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('8f9757e7aa3d491daba08cec71a76808', '19-0', '结核病科', '19');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('c3ca4e36b90d4279b6c7f7030876fe50', '20', '介入医学科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ca246459b5f04746b74c89673fdec740', '20-0', '介入医学科', '20');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('b9731ebb3f954901b5ba2bee270e6968', '21', '康复医学科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('e7c9b2472c164415bd89afbffd17ab86', '21-0', '康复科', '21');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('560e3c6cfa8b4057aa508deb002faf63', '21-1', '理疗科', '21');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('02ddd0d18ef44a81b6815a15b040d2e8', '22', '运动医学科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('364d4e1a443c46089c2ec85b46958640', '22-0', '运动医学科', '22');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('aadb57f79f754b238d8236fe8c723d06', '23', '麻醉医学科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('fe5ade1b8fcc4aae9c82df79b52603ba', '23-0', '疼痛科', '23');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('9071293c860348eeb0fe87a0800d37e3', '23-1', '麻醉科', '23');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('dbbe0d390e334705ba102d0636971854', '24', '职业病科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('af1088befd194f3893a023bcbf707214', '24-0', '职业病科', '24');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('d1ec54743f854b14b44d27ec07034960', '25', '地方病科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('70acb469dc084d358ca9d6f5777ac38b', '25-0', '地方病科', '25');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('525a2373c45147c4a8f0cbdaeec33160', '26', '营养科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a52806495f884c16953878757a1f7a34', '26-0', '营养科', '26');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('30e5ad4231ab42a3b4f5be0a4a09714d', '27', '医学影像学', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('19f994cd3f1f4693aa4d70e0a382ee90', '27-0', '核医学科', '27');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('18acc943e36448a8abd74ec908440a0c', '27-1', '放射科', '27');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('8f0b9478124b4cea93dc91bf86f3c07a', '27-2', '超声科', '27');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('c1c3d9b013e8441094c2fe4636616b03', '27-3', '医学影像科', '27');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('a879983f807d465db181e987480a072a', '28', '病理科', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('5c9f4e09c2cb462ba66deea7b649e5fa', '28-0', '病理科', '28');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('766b323bf4114d9ba7656a33cb27dfd7', '29', '其他科室', '0');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('8b9d8c1f101641738fdde112ccaaaa5a', '29-0', '急诊科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('227a7be2cddc4dabae57de9128355712', '29-1', '特色医疗科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('df6ecbd05c2f4319a6c283312505f314', '29-2', '干部诊疗科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ae680db5d2b54e37af8137d87c32c21f', '29-3', '重症监护室', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('2ed2386d51ed4c53a91b9869758c233d', '29-4', '特诊科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('1bf4fe72a0e6476ea62e2110c7aa0366', '29-5', '检验科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('1183fee8a0e14596b05c233796304314', '29-6', '预防保健科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('acc4394918f844eea2cd00c41bde24fa', '29-7', '功能检查科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('25b81eb5eb1a48eaaeba22e04d429a07', '29-8', '全科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('ed80b48d2e6f43d7887c91bc05d15567', '29-9', '体检科', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('07307b593294461bb797758b6e50bd9b', '29-10', '血透中心', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('46ab5d68dd034cfa88493e98436a33be', '29-11', '实验中心', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('6ba8bf23c1dd463a8f8a77967f7ed89b', '29-12', '碎石中心', '29');
INSERT INTO dept (id, deptid, dname, pareid) VALUES ('713789b9d8d04a9cad90a61fc00a7b43', '29-13', '变态反应科', '29');


-- medicine data 药品信息数据
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1001', 'prd-001', '青霉素 Benzylpenicillin', '这里是功效', '（钾盐）注射用无菌粉末：0.25g（40 万单位）、0.5g（80 万单位）（钠盐）注射用无菌粉末：0.24g（40 万单位）、0.48g（80 万单位）、0.96g（160 万单位）', '这里是主要成分', '21', '这里是详细说明', '青霉素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1002', 'prd-002', '苄星青霉素 Benzathine Benzylpenicillin', '这里是功效', '注射用无菌粉末：30 万单位、60 万单位、120 万单位', '这里是主要成分', '23', '这里是详细说明', '青霉素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1003', 'prd-003', '苯唑西林 Oxacillin', '这里是功效', '片剂、胶囊：0.25g注射用无菌粉末：0.5g、1.0g', '这里是主要成分', '14', '这里是详细说明', '青霉素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1004', 'prd-001', '氨苄西林 Ampicillin', '这里是功效', '注射用无菌粉末：0.5g、1.0g', '这里是主要成分', '32', '这里是详细说明', '青霉素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1005', 'prd-002', '哌拉西林 Piperacillin', '这里是功效', '注射用无菌粉末：0.5g、1.0g、2.0g', '这里是主要成分', '33', '这里是详细说明', '青霉素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1006', 'prd-003', '阿莫西林 Amoxicillin', '这里是功效', '片剂、胶囊、颗粒剂、干混悬剂：0.125g、0.25g', '这里是主要成分', '21', '这里是详细说明', '青霉素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1007', 'prd-003', '阿莫西林克拉维酸钾 Amoxicillin and Clavulanate Potassium', '这里是功效', '片剂：阿莫西林:克拉维酸=2:1、4:1、7:1颗粒剂：125mg:31.25mg（4:1）、200mg:28.5mg（7:1）（阿莫西林:克拉维酸）干混悬剂：250mg:62.5mg（4:1）、200mg:28.5mg（7:1）（阿莫西林:克拉维酸）注射用无菌粉末： 250mg:50mg（ 5:1 ）、500mg:100mg （ 5:1 ）、1000mg:200mg（5:1）（阿莫西林:克拉维酸）', '这里是主要成分', '12', '这里是详细说明', '青霉素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1008', 'prd-001', '头孢唑林 Cefazolin tennipoay', '这里是功效', '注射用无菌粉末：0.5g、1.0g', '这里是主要成分', '21', '这里是详细说明', '头孢菌素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1009', 'prd-002', '头孢拉定 Cefradine', '这里是功效', '片剂、胶囊：0.25g、0.5g', '这里是主要成分', '19', '这里是详细说明', '头孢菌素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1010', 'prd-003', '头孢氨苄 Cefalexin', '这里是功效', '片剂、胶囊：0.125g、0.25g颗粒剂：0.05g、0.125g', '这里是主要成分', '21', '这里是详细说明', '头孢菌素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1011', 'prd-001', '头孢呋辛 Cefuroxime', '这里是功效', '（头孢呋辛酯）片剂、胶囊：0.125g、0.25g（钠盐）注射用无菌粉末：0.25g、0.5g、0.75g、1.5g', '这里是主要成分', '6', '这里是详细说明', '头孢菌素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1012', 'prd-002', '头孢曲松 Ceftriaxone', '这里是功效', '注射用无菌粉末：0.25g、0.5g、1.0g、2.0g', '这里是主要成分', '17', '这里是详细说明', '头孢菌素类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1013', 'prd-001', '头孢他啶 Ceftazidime', '这里是功效', '注射用无菌粉末：0.5g、1.0g ', '这里是主要成分', '20', '这里是详细说明', '氨基糖苷类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1014', 'prd-002', '阿米卡星 Amikacin', '这里是功效', '注射液：1ml:0.1g（10 万单位）、2ml:0.2g（20 万单位）', '这里是主要成分', '23', '这里是详细说明', '氨基糖苷类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1015', 'prd-003', '庆大霉素 Gentamycin', '这里是功效', '注射液：1ml:40mg（4 万单位）、2ml:80mg（8 万单位）', '这里是主要成分', '10', '这里是详细说明', '氨基糖苷类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1017', 'prd-001', '红霉素 Erythromycin', '这里是功效', '肠溶（片剂、胶囊）、（琥珀酸乙酯）片剂、胶囊：0.125g（12.5 万单位）、0.25g（25 万单位）注射用无菌粉末：0.25g（25 万单位）、0.3g（30 万单位）', '这里是主要成分', '18', '这里是详细说明', '大环内酯类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1018', 'prd-002', '阿奇霉素 Azithromycin', '修改成功了吗', '肠溶（片剂、胶囊）：0.125g、0.25g', '这里是主要成分', '20', '这里是详细说明', '大环内酯类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1019', 'prd-003', '地红霉素 Dirithromycin', '这里是功效', '肠溶（片剂、胶囊）：0.125g、0.25g', '这里是主要成分', '9', '这里是详细说明', '大环内酯类');
INSERT INTO medicine (`mid`, `pdid`, `name`, `function`, `specification`, `ingredient`, `price`, `details`, `type`) VALUES ('md-1020', 'prd-001', '克拉霉素 Clarithromycin', '这里是功效', '片剂、胶囊、颗粒剂：0.125g、0.25g', '这里是主要成分', '20', '这里是详细说明', '大环内酯类');


-- producer data 供应商数据
INSERT INTO producer (pdid, pdname, pdcode) VALUES ('prd-001', '广药', 'gy0101');
INSERT INTO producer (pdid, pdname, pdcode) VALUES ('prd-002', '哈药', 'hy0202');
INSERT INTO producer (pdid, pdname, pdcode) VALUES ('prd-003', '中药', 'zy1001');


-- role data 角色数据
INSERT INTO role (roleid, rolename) VALUES ('1', '用户');
INSERT INTO role (roleid, rolename) VALUES ('2', '医生');
INSERT INTO role (roleid, rolename) VALUES ('3', '管理员');
INSERT INTO role (roleid, rolename) VALUES ('4', '医生，管理员');
INSERT INTO role (roleid, rolename) VALUES ('5', '超级管理员');
INSERT INTO role (roleid, rolename) VALUES ('6', '医生，超级管理员');


-- storage data 医院库存数据
INSERT INTO storage (sid, mid, amount) VALUES ('st-05ee00943ba0-20180511132805', 'md-1018', '10');
INSERT INTO storage (sid, mid, amount) VALUES ('st-147d9dc1afa4-20180511132804', 'md-1001', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-2ce08a714d90-20180511132805', 'md-1008', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-311e3d90e85d-20180511132805', 'md-1013', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-36738d52b6b5-20180511132805', 'md-1003', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-3b90c4048279-20180511132805', 'md-1015', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-44b69a8fca1d-20180511132805', 'md-1007', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-4d723fefc4a5-20180511132805', 'md-1017', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-65625b12e8c6-20180511132805', 'md-1005', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-9db811de1dec-20180511132805', 'md-1019', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-9f4433436a13-20180511132805', 'md-1011', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-a8a439c16d08-20180511132805', 'md-1009', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-a91b0ed9b815-20180511132805', 'md-1006', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-c029f0fbde32-20180511132805', 'md-1014', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-c40e950a895c-20180511132805', 'md-1012', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-db09b3b086f9-20180511132805', 'md-1010', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-f21ff6d0600c-20180511132805', 'md-1020', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-f4122e3a51e6-20180511132805', 'md-1004', '50');
INSERT INTO storage (sid, mid, amount) VALUES ('st-fda5251f0bb1-20180511132805', 'md-1002', '50');


-- Defauld User Data 用户数据
INSERT INTO user (id, loginid, password, state, infoid) VALUES ('user-66b56d6e15b3-20180424220827', 'scott', 'scott', 'B', 'info-45a97050c536-20180424221018');

-- Default UserInfo Data 用户信息数据
INSERT INTO userinfo (id, infoid, username, realname, idtype, idcard, gender, age, birthday, email, phone, picurl, regtime, updatetime, roleid, opwds) VALUES ('13ffeda34d044bcaa8aadffe56e0d72a', 'info-45a97050c536-20180424221018', 'scott', 'scott', 'A', '3001', '男', '3', '2018-06-10', 'scott@ht.com', '1231231231', 'assets/img/defaultpic.jpg', '2018-05-07 11:26:53', '2018-06-10 11:22:33', '5', NULL);

