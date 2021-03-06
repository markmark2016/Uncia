DROP TABLE IF EXISTS `T_ACCOUNT`;
CREATE TABLE `T_ACCOUNT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `USERNAME` varchar(64) NOT NULL COMMENT '账户名,需唯一',
  `PASSWORD` varchar(128) NOT NULL DEFAULT 'default@Mark#' COMMENT '用户密码',
  `TYPE` smallint(6) NOT NULL DEFAULT '0' COMMENT '账户类型, 例如超级账户,企业账户,个人账户等',
  `ENABLED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='账户表';

DROP TABLE IF EXISTS `T_BOOK`;
CREATE TABLE `T_BOOK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
  `NAME` varchar(256) NOT NULL DEFAULT ' ' COMMENT '图书名称',
  `AUTHOR` varchar(128) DEFAULT ' ' COMMENT '书籍作者,多个作者以逗号分隔',
  `SUMMARY` varchar(512) DEFAULT '' COMMENT '书籍概括',
  `COVER_IMG` varchar(1024) NOT NULL DEFAULT '' COMMENT '书籍封面URL',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='图书表';

DROP TABLE IF EXISTS `T_CATEGORY`;
CREATE TABLE `T_CATEGORY` (
  `ID` bigint(20)  NOT NULL AUTO_INCREMENT,
  `NAME` varchar(128) NOT NULL,
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='分类表,社群类别';


DROP TABLE IF EXISTS `T_COMMUNITY`;
CREATE TABLE `T_COMMUNITY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `NAME` varchar(128) NOT NULL DEFAULT 'DEFAULT_COMMUNITY' COMMENT '社群名称',
  `DESCRIPTION` varchar(512) NOT NULL DEFAULT '' COMMENT '社群描述信息',
  `IMAGE` varchar(512) NOT NULL DEFAULT '' COMMENT '社群图片ID',
  `SLOGAN` varchar(128) NOT NULL DEFAULT '' COMMENT '阅读口号',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='社群表';

DROP TABLE IF EXISTS `T_COMMUNITY_CATEGORY_REL`;
CREATE TABLE `T_COMMUNITY_CATEGORY_REL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMUNITY_ID` bigint(20) NOT NULL,
  `CATEGORY_ID` bigint(20) NOT NULL,
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='社群分类联系';


DROP TABLE IF EXISTS `T_COMMUNITY_GROUP_REL`;
CREATE TABLE `T_COMMUNITY_GROUP_REL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMUNITY_ID` bigint(20) NOT NULL DEFAULT '1',
  `GROUP_ID` bigint(20) NOT NULL DEFAULT '1',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='社群与组联系表';


DROP TABLE IF EXISTS `T_ENTERPRISE`;
CREATE TABLE `T_ENTERPRISE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ENTERPRISE_NAME` varchar(64) NOT NULL DEFAULT 'DEFAULT_ENTERPRISE_NAME' COMMENT '企业名称',
  `ACCOUNT_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '帐号表,一个企业可有n个帐号,用于管理企业下的内容(用户,书评等)',
  `APP_ID` char(20) NOT NULL DEFAULT 'DEFAULT_APP_ID' COMMENT '企业公众号的appid, 作为除id外唯一标志一个企业公众号的依据. 如果企业没有公众号,那么appid为默认',
  `DESCRIPTION` varchar(512) DEFAULT ' ' COMMENT '企业的描述信息',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态,用于标志企业是否被删除（不再接入markmark）',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '创建时间',
 `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='企业信息表';


DROP TABLE IF EXISTS `T_GROUP`;
CREATE TABLE `T_GROUP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `GROUP_NAME` varchar(128) DEFAULT 'DEFAULT_GROUP' COMMENT '组名称',
  `ENTERPRISE_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '组所属的企业',
  `DESCRIPTION` varchar(1024) NOT NULL DEFAULT '' COMMENT '组的描述信息',
  `START_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '开始时间',
  `END_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '开始时间',
  `DEADLINE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '开始时间',
  `FREQENCY` int(20) NOT NULL DEFAULT 1 COMMENT '打卡频率',
  `VISIABLE` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否可见',
  `ADD_MEMBER_TYPE` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否可见',
  `REMARK_VISIABLE` tinyint(4) NOT NULL DEFAULT 0 COMMENT '书评是否可见',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `CREATOR` varchar(32) NOT NULL DEFAULT 'SYSTEM' COMMENT '组的创建者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='分组表';


DROP TABLE IF EXISTS `T_GROUP_BOOK_REL`;
CREATE TABLE `T_GROUP_BOOK_REL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT DEFAULT '1' AUTO_INCREMENT COMMENT '关系ID',
  `GROUP_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '小组ID',
  `BOOK_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '图书ID',
  `SLOGAN` varchar(1204) NOT NULL DEFAULT ' ' COMMENT '阅读标语',
  `STATUS` varchar(16) NOT NULL DEFAULT 'NOT_STARTED' COMMENT '状态,未开始,进行中,已经完成等',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='小组和图书关系表';

DROP TABLE IF EXISTS `T_GROUP_USER_REL`;
CREATE TABLE `T_GROUP_USER_REL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GROUP_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '小组ID',
  `USER_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '用户ID',
  `USER_ROLE` varchar(16) NOT NULL DEFAULT 'READER' COMMENT 'User在小组中的角色',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态,用于标志用户是否在所在的组',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='小组和用户关系表';


DROP TABLE IF EXISTS `T_INTERACT`;
CREATE TABLE `T_INTERACT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `REMARK_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '书评ID',
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `TYPE` varchar(16) NOT NULL DEFAULT 'COMMENT' COMMENT '交互类型,COMMENT,LIKE',
  `COMMENTS` varchar(1024) DEFAULT ' ' COMMENT '如果是评论则填充改字段,否则为空',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '记录创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户交互, 赞和评论';


DROP TABLE IF EXISTS `T_MESSAGE`;
CREATE TABLE `T_MESSAGE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `USER_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '推送的用户',
  `INTERACT_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '交互ID',
  `TYPE` varchar(16) NOT NULL DEFAULT 'LIKE' COMMENT '消息类型,例如 SYSTEM, LIKE,COMMENT等',
  `CHECKED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用于标记用户是否已经阅读',
  `CONTENT` varchar(256) NOT NULL DEFAULT ' ' COMMENT '消息内容',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '记录创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='消息表,记录对用户推送的消息,例如点赞,评论等';



DROP TABLE IF EXISTS `T_REMARK`;
CREATE TABLE `T_REMARK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '书评ID',
  `BOOK_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '关联的图书ID',
  `USER_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '用户ID',
  `GROUP_ID` bigint(20) NOT NULL COMMENT '小组ID',
  `START_PAGE` int(11) NOT NULL DEFAULT '0' COMMENT '开始页',
  `END_PAGE` int(11) NOT NULL DEFAULT '0' COMMENT '结束页',
  `TITLE` varchar(256) NOT NULL DEFAULT '' COMMENT '书评标题',
  `CONTENT` varchar(2048) NOT NULL DEFAULT '' COMMENT '书评内容',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '记录创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='书评';


DROP TABLE IF EXISTS `T_ROLE`;
CREATE TABLE `T_ROLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '账户ID',
  `ROLE` varchar(32) NOT NULL DEFAULT 'USER' COMMENT '用户角色',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-00-00 00:00:00' COMMENT '创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `T_SCORE`;
CREATE TABLE `T_SCORE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `USER_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '用户ID',
  `SCORE` int(11) NOT NULL COMMENT '得分',
  `TYPE` varchar(16) NOT NULL DEFAULT 'REMARK' COMMENT '得分类型,例如REMARK打卡, COMMENT,评论,LIKE点赞',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态,删除某次打卡或者赞之后取消相应的分数',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '记录创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='得分表';


DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `SUBSCRIBE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订阅状态,0 表示未订阅,1 表示订阅. ',
  `OPENID` varchar(32) NOT NULL DEFAULT 'DEFAULT_OPENID' COMMENT '微信用户对应于一个公众号的openid',
  `NICKNAME` varchar(128) NOT NULL DEFAULT ' ' COMMENT '微信昵称',
  `SEX` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别',
  `CITY` varchar(64) NOT NULL DEFAULT ' ' COMMENT '城市',
  `PROVINCE` varchar(32) DEFAULT ' ' COMMENT '省份',
  `COUNTRY` varchar(64) NOT NULL DEFAULT '中国' COMMENT '国家',
  `HEAD_IMG_URL` varchar(1024) NOT NULL DEFAULT '' COMMENT '头像,默认为空字符,客户端需要对无头像用户设置默认头像',
  `SUBSCRIBE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '订阅时间',
  `UNIONID` varchar(128) NOT NULL DEFAULT 'DEFAULT_UNIONID',
  `REMARK` varchar(1024) NOT NULL DEFAULT ' ' COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
  `GROUPID` bigint(20) NOT NULL DEFAULT '1' COMMENT '微信用户所在的分组信息,默认为1(默认组),不同于本系统的分组',
  `ENTERPRISE_ID` bigint(20) NOT NULL DEFAULT '1' COMMENT '用户所属的企业,默认为1,默认企业',
  `DELETE_STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户删除状态',
  `CREATE_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '记录创建时间',
  `MOD_TIME` datetime NOT NULL DEFAULT '1970-1-1 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户表';
