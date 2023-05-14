alter table sys_user
    add salt varchar(50) null comment '盐值，用于加密';

alter table sys_user
    modify salt varchar(50) null comment '盐值，用于加密' after password;