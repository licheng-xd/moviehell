create table movie_detail (
  `id` bigint(20) auto_increment,
  `name` VARCHAR(64) NOT NULL comment '名称',
  `img` VARCHAR(128) NOT NULL comment '图片url',
  `intro` VARCHAR(2048) NOT NULL COMMENT '简介',
  `href` VARCHAR(128) NOT NULL COMMENT '下载地址',
  `time` VARCHAR(32) NOT NULL COMMENT '发布时间',
  `createtime` bigint(20) not null comment '创建时间',
  PRIMARY KEY (id),
  KEY idx_name (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '电影详细信息表';


create table movie_item (
  `id` bigint(20) NOT NULL comment'id',
  `name` VARCHAR(64) NOT NULL comment '名称',
  `href` VARCHAR(128) NOT NULL COMMENT '下载地址',
  `date` VARCHAR(32) NOT NULL COMMENT '发布时间',
  `type` VARCHAR(16) NOT NULL COMMENT '类型',
  `subtype` VARCHAR(16) NOT NULL COMMENT '子类型',
  `url` VARCHAR(128) NOT NULL COMMENT '源地址',
  `createtime` bigint(20) not null comment '创建时间',
  PRIMARY KEY (`id`, `type`),
  KEY idx_name (`name`),
  KEY idx_id (`id`),
  KEY idx_type_date (`type`, `date`)
  KEY idx_url (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '电影信息表';

create table url_record (
  `url` varchar(128) not null comment 'crawled url',
  `createtime` bigint(20) not null comment '创建时间',
  PRIMARY KEY (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '记录表';
