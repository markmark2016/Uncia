DROP TABLE IF EXISTS T_ENTERPRISE;
CREATE TABLE T_ENTERPRISE
(
  ID              BIGINT(20) PRIMARY KEY                        NOT NULL,
  ENTERPRISE_NAME VARCHAR(64) DEFAULT 'DEFAULT_ENTERPRISE_NAME' NOT NULL
  COMMENT '企业名称',
  ACCOUNT_ID      BIGINT(20) DEFAULT '-1'                       NOT NULL
  COMMENT '帐号表,一个企业可有n个帐号,用于管理企业下的内容(用户,书评等)',
  APP_ID          CHAR(20) DEFAULT 'DEFAULT_APP_ID'             NOT NULL
  COMMENT '企业公众号的appid, 作为除id外唯一标志一个企业公众号的依据. 如果企业没有公众号,那么appid为默认',
  DESCRIPTION     VARCHAR(512) DEFAULT ' '
  COMMENT '企业的描述信息',
  DELETE_STATUS   TINYINT(4) DEFAULT '0'                        NOT NULL
  COMMENT '删除状态,用于标志企业是否被删除（不再接入markmark）',
  CREATE_TIME     DATETIME DEFAULT '1970-00-00 00:00:00'        NOT NULL
  COMMENT '创建时间',
  MOD_TIME        TIMESTAMP DEFAULT CURRENT_TIMESTAMP           NOT NULL
  COMMENT '修改时间'
)
  COMMENT '企业信息表';

DROP TABLE IF EXISTS T_GROUP;
CREATE TABLE T_GROUP
(
  ID            BIGINT(20) PRIMARY KEY                 NOT NULL
  COMMENT '组id',
  GROUP_NAME    VARCHAR(128) DEFAULT 'DEFAULT_GROUP'
  COMMENT '组名称',
  ENTERPRISE_ID BIGINT(20) DEFAULT '1'                 NOT NULL
  COMMENT '组所属的企业',
  DESCRIPTION   VARCHAR(1024) DEFAULT ''               NOT NULL
  COMMENT '组的描述信息',
  GROUP_STATUS  VARCHAR(16) DEFAULT 'NEW'              NOT NULL
  COMMENT '组的状态,新组,消亡的组,活跃的组',
  CREATOR       VARCHAR(32) DEFAULT 'SYSTEM'           NOT NULL
  COMMENT '组的创建者',
  CREATE_TIME   DATETIME DEFAULT '1970-00-00 00:00:00' NOT NULL
  COMMENT '创建时间',
  MOD_TIME      TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NOT NULL
  COMMENT '修改时间'
)
  COMMENT '分组表';


DROP TABLE IF EXISTS T_ACCOUNT;
CREATE TABLE T_ACCOUNT
(
  ID            BIGINT(20) PRIMARY KEY                 NOT NULL
  COMMENT '账户id',
  USERNAME      VARCHAR(64)                            NOT NULL
  COMMENT '账户名,需唯一',
  PASSWORD      VARCHAR(128) DEFAULT 'default@Mark#'   NOT NULL
  COMMENT '用户密码',
  TYPE          SMALLINT(6) DEFAULT '0'                NOT NULL
  COMMENT '账户类型, 例如超级账户,企业账户,个人账户等',
  DELETE_STATUS TINYINT(4) DEFAULT '0'                 NOT NULL
  COMMENT '删除状态',
  CREATE_TIME   DATETIME DEFAULT '1970-00-00 00:00:00' NOT NULL
  COMMENT '创建时间',
  MOD_TIME      TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NOT NULL ON UPDATE CURRENT_TIMESTAMP
)
  COMMENT '账户表';


DROP TABLE IF EXISTS T_BOOK;
CREATE TABLE T_BOOK
(
  ID            BIGINT PRIMARY KEY                            NOT NULL
  COMMENT '图书ID'             AUTO_INCREMENT,
  NAME          VARCHAR(256) DEFAULT ' '                      NOT NULL
  COMMENT '图书名称',
  AUTHOR        VARCHAR(128) DEFAULT ' '
  COMMENT '书籍作者,多个作者以逗号分隔',
  SUMMARY       VARCHAR(512) DEFAULT ''
  COMMENT '书籍概括',
  COVER_IMG     VARCHAR(1024) DEFAULT ''                      NOT NULL
  COMMENT '书籍封面URL',
  DELETE_STATUS TINYINT(4) DEFAULT '0'                        NOT NULL
  COMMENT '删除状态',
  CREATE_TIME   DATETIME DEFAULT '1970-00-00 00:00:00'        NOT NULL
  COMMENT '创建时间',
  MOD_TIME      TIMESTAMP DEFAULT CURRENT_TIMESTAMP           NOT NULL
  COMMENT '修改时间'

)
  COMMENT '图书表';

DROP TABLE IF EXISTS T_GROUP_USER_REL;
CREATE TABLE T_GROUP_USER_REL
(
  ID            BIGINT PRIMARY KEY                            NOT NULL AUTO_INCREMENT,
  GROUP_ID      BIGINT DEFAULT 1                              NOT NULL
  COMMENT '小组ID',
  USER_ID       BIGINT DEFAULT 1                              NOT NULL
  COMMENT '用户ID',
  USER_ROLE     VARCHAR(16)                                   NOT NULL DEFAULT 'READER'
  COMMENT 'User在小组中的角色',
  DELETE_STATUS TINYINT DEFAULT FALSE                         NOT NULL
  COMMENT '删除状态,用于标志用户是否在所在的组',
  CREATE_TIME   DATETIME DEFAULT '1970-00-00 00:00:00'        NOT NULL
  COMMENT '创建时间',
  MOD_TIME      TIMESTAMP DEFAULT CURRENT_TIMESTAMP           NOT NULL
  COMMENT '修改时间'
)
  COMMENT '小组和用户关系表';

DROP TABLE IF EXISTS T_GROUP_BOOK_REL;
CREATE TABLE T_GROUP_BOOK_REL
(
  ID          BIGINT DEFAULT 1                              NOT NULL
  COMMENT '关系ID',
  GROUP_ID    BIGINT DEFAULT 1                              NOT NULL
  COMMENT '小组ID',
  BOOK_ID     BIGINT DEFAULT 1                              NOT NULL
  COMMENT '图书ID',
  SLOGN       VARCHAR(1204) DEFAULT ' '                     NOT NULL
  COMMENT '阅读标语',
  STATUS      VARCHAR(16) DEFAULT 'NOT_STARTED'             NOT NULL
  COMMENT '状态,未开始,进行中,已经完成等',
  CREATE_TIME DATETIME DEFAULT '1970-00-00 00:00:00'        NOT NULL
  COMMENT '创建时间',
  MOD_TIME    TIMESTAMP DEFAULT CURRENT_TIMESTAMP           NOT NULL
  COMMENT '修改时间'
)
  COMMENT '小组和图书关系表';

DROP TABLE IF EXISTS T_USER;
CREATE TABLE T_USER
(
  ID             BIGINT(20) PRIMARY KEY                 NOT NULL
  COMMENT '用户id',
  SUBSCRIBE      TINYINT(4) DEFAULT '0'                 NOT NULL
  COMMENT '订阅状态,0 表示未订阅,1 表示订阅. ',
  OPENID         VARCHAR(32) DEFAULT 'DEFAULT_OPENID'   NOT NULL
  COMMENT '微信用户对应于一个公众号的openid',
  NICKNAME       VARCHAR(128) DEFAULT ' '               NOT NULL
  COMMENT '微信昵称',
  SEX            TINYINT(4) DEFAULT '0'                 NOT NULL
  COMMENT '性别',
  CITY           VARCHAR(64) DEFAULT ' '                NOT NULL
  COMMENT '城市',
  PROVINCE       VARCHAR(32) DEFAULT ' '
  COMMENT '省份',
  COUNTRY        VARCHAR(64) DEFAULT '中国'               NOT NULL
  COMMENT '国家',
  HEAD_IMG_URL   VARCHAR(1024) DEFAULT ''               NOT NULL
  COMMENT '头像,默认为空字符,客户端需要对无头像用户设置默认头像',
  SUBSCRIBE_TIME DATETIME    DEFAULT '1970-01-01 00:00:00'
  COMMENT '订阅时间',
  UNIONID        VARCHAR(128) DEFAULT 'DEFAULT_UNIONID' NOT NULL,
  REMARK         VARCHAR(1024) DEFAULT ' '              NOT NULL
  COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
  GROUP_ID       BIGINT(20) DEFAULT '1'                 NOT NULL
  COMMENT '用户所在的分组信息,默认为1(默认组)',
  ENTERPRISE_ID  BIGINT(20) DEFAULT '1'                 NOT NULL
  COMMENT '用户所属的企业,默认为1,默认企业',
  DELETE_STATUS  TINYINT(4) DEFAULT '0'                 NOT NULL
  COMMENT '用户删除状态',
  CREATE_TIME    DATETIME    DEFAULT '1970-01-01 00:00:00'
  COMMENT '记录创建时间',
  MOD_TIME       TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NOT NULL
  COMMENT '修改时间'
)
  COMMENT '用户表';

DROP TABLE IF EXISTS T_REMARK;
CREATE TABLE T_REMARK
(
  ID            BIGINT PRIMARY KEY                     NOT NULL
  COMMENT '书评ID'                                                AUTO_INCREMENT,
  BOOK_ID       BIGINT DEFAULT 1                       NOT NULL
  COMMENT '关联的图书ID',
  USER_ID       BIGINT DEFAULT 1                       NOT NULL
  COMMENT '用户ID',
  GROUP_ID      BIGINT                                 NOT NULL
  COMMENT '小组ID',
  START_PAGE    INT DEFAULT 0                          NOT NULL
  COMMENT '开始页',
  END_PAGE      INT DEFAULT 0                          NOT NULL
  COMMENT '结束页',
  TITLE         VARCHAR(256)                           NOT NULL DEFAULT ''
  COMMENT '书评标题',
  CONTENT       VARCHAR(2048)                          NOT NULL DEFAULT ''
  COMMENT '书评内容',
  DELETE_STATUS TINYINT                                NOT NULL DEFAULT FALSE
  COMMENT '删除状态',
  CREATE_TIME   DATETIME                                        DEFAULT '1970-01-01 00:00:00'
  COMMENT '记录创建时间',
  MOD_TIME      TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NOT NULL
  COMMENT '修改时间'
)
  COMMENT '书评';

DROP TABLE IF EXISTS T_INTERACT;
CREATE TABLE T_INTERACT
(
  ID            BIGINT PRIMARY KEY                     NOT NULL,
  REMARK_ID     BIGINT DEFAULT 1                       NOT NULL
  COMMENT '书评ID',
  USER_ID       BIGINT                                 NOT NULL
  COMMENT '用户ID',
  TYPE          VARCHAR(16) DEFAULT 'COMMENT'          NOT NULL
  COMMENT '交互类型,COMMENT,LIKE',
  COMMENTS      VARCHAR(1024)                                   DEFAULT ' '
  COMMENT '如果是评论则填充改字段,否则为空',
  DELETE_STATUS TINYINT                                NOT NULL DEFAULT FALSE
  COMMENT '删除状态',
  CREATE_TIME   DATETIME                                        DEFAULT '1970-01-01 00:00:00'
  COMMENT '记录创建时间',
  MOD_TIME      TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NOT NULL
  COMMENT '修改时间'
)
  COMMENT '用户交互, 赞和评论';

DROP TABLE IF EXISTS T_SCORE;
CREATE TABLE T_SCORE
(
  ID            BIGINT PRIMARY KEY                     NOT NULL
  COMMENT 'ID'           AUTO_INCREMENT,
  USER_ID       BIGINT DEFAULT 1                       NOT NULL
  COMMENT '用户ID',
  SCORE         INT                                    NOT NULL
  COMMENT '得分',
  TYPE          VARCHAR(16) DEFAULT 'REMARK'           NOT NULL
  COMMENT '得分类型,例如REMARK打卡, COMMENT,评论,LIKE点赞',
  DELETE_STATUS TINYINT DEFAULT FALSE                  NOT NULL
  COMMENT '删除状态,删除某次打卡或者赞之后取消相应的分数',
  CREATE_TIME   DATETIME DEFAULT '1970-01-01 00:00:00'
  COMMENT '记录创建时间',
  MOD_TIME      TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NOT NULL
  COMMENT '修改时间'
)
  COMMENT '得分表';


DROP TABLE IF EXISTS T_MESSAGE;

CREATE TABLE T_MESSAGE
(
  ID          BIGINT PRIMARY KEY                     NOT NULL
  COMMENT '消息ID'       AUTO_INCREMENT,
  USER_ID     BIGINT DEFAULT 1                       NOT NULL
  COMMENT '推送的用户',
  INTERACT_ID BIGINT DEFAULT 1                       NOT NULL
  COMMENT '交互ID',
  TYPE        VARCHAR(16) DEFAULT 'LIKE'             NOT NULL
  COMMENT '消息类型,例如 SYSTEM, LIKE,COMMENT等',
  CHECKED     TINYINT DEFAULT FALSE                  NOT NULL
  COMMENT '用于标记用户是否已经阅读',
  CONTENT     VARCHAR(256) DEFAULT ' '               NOT NULL
  COMMENT '消息内容',
  CREATE_TIME DATETIME DEFAULT '1970-01-01 00:00:00'
  COMMENT '记录创建时间',
  MOD_TIME    TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NOT NULL
  COMMENT '修改时间'
)
  COMMENT '消息表,记录对用户推送的消息,例如点赞,评论等';