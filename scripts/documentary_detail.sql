
-- 纪录片表

create table documentary_detail (
  `id` VARCHAR(32) NOT NULL comment'id',
  `name` VARCHAR(64) NOT NULL comment '名称',
  `img` VARCHAR(256) NOT NULL comment '图片url',
  `intro` text NOT NULL COMMENT '简介',
  `hrefs` text NOT NULL COMMENT '下载列表',
  `url` VARCHAR(256) NOT NULL COMMENT '源地址',
  `time` VARCHAR(32) NOT NULL COMMENT '更新时间',
  `createtime` bigint(20) not null comment '创建时间',
  `updatetime` bigint(20) not null comment '更新时间',
  PRIMARY KEY (id),
  KEY idx_name (`name`),
  KEY idx_time (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '纪录片详细信息表';
