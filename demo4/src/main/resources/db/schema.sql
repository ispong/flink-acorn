DROP TABLE FLINK_FLOW;
DROP TABLE FLINK_NODE;

-- 工作流信息表
create table FLINK_FLOW
(
    IS_DELETE          int          null comment '是否被删除 (1 删除/0 未删除)',
    CREATED_BY         varchar(64)  null comment '创建者',
    CREATED_DATE       datetime     null comment '创建时间',
    LAST_MODIFIED_BY   varchar(64)  null comment '更新者',
    LAST_MODIFIED_DATE datetime     null comment '更新时间',
    VERSION            int          null comment '版本号',
    FLOW_ID            varchar(500) null,
    WEB_CONFIG         varchar(500) null,
    NODE_LINK          varchar(500) null
);

-- 节点表
create table FLINK_NODE
(
    IS_DELETE          int          null comment '是否被删除 (1 删除/0 未删除)',
    CREATED_BY         varchar(64)  null comment '创建者',
    CREATED_DATE       datetime     null comment '创建时间',
    LAST_MODIFIED_BY   varchar(64)  null comment '更新者',
    LAST_MODIFIED_DATE datetime     null comment '更新时间',
    VERSION            int          null comment '版本号',
    NODE_ID            varchar(500) null,
    FLOW_ID            varchar(500) null,
    NAME               varchar(500) null,
    TYPE               varchar(500) null,
    CONFIG             varchar(500) null
);
