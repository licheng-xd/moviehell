
-- P1080表

create table p1080_detail (
  `id` VARCHAR(64) NOT NULL comment'id',
  `name` VARCHAR(64) NOT NULL comment '名称',
  `img` VARCHAR(128) NOT NULL comment '图片url',
  `intro` text NOT NULL COMMENT '简介',
  `href` VARCHAR(512) NOT NULL COMMENT '下载列表',
  `time` VARCHAR(32) NOT NULL COMMENT '更新时间',
  `createtime` bigint(20) not null comment '创建时间',
  `updatetime` bigint(20) not null comment '更新时间',
  PRIMARY KEY (id),
  KEY idx_name (`name`),
  KEY idx_time (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '高清详细信息表';
