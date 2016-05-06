
-- 电影

create table movie_detail (
  `id` bigint(20) NOT NULL comment'id',
  `name` VARCHAR(64) NOT NULL comment '名称',
  `img` VARCHAR(128) NOT NULL comment '图片url',
  `intro` VARCHAR(2048) NOT NULL COMMENT '简介',
  `href` VARCHAR(128) NOT NULL COMMENT '下载地址',
  `time` VARCHAR(32) NOT NULL COMMENT '发布时间',
  `createtime` bigint(20) not null comment '创建时间',
  PRIMARY KEY (id),
  KEY idx_name (`name`)
  KEY idx_time (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '电影详细信息表';

