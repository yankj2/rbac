
--=============================================================
--一些基础表，非RBAC模型中核心表，但是为了更好开发而需要的表
--=============================================================

CREATE TABLE
    sequence
    (
        name VARCHAR(50) NOT NULL,
        current_value bigint NOT NULL,
        increment INT DEFAULT '1' NOT NULL,
        PRIMARY KEY (name)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

--=============================================================
--以下是RBAC权限模型中的核心表
--=============================================================


--用户表
CREATE TABLE
    rbac_user
    (
        userId VARCHAR(20) NOT NULL,
        userName VARCHAR(60),
        validStatus VARCHAR(2) DEFAULT '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (userId)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

--角色表
CREATE TABLE
    rbac_role
    (
        roleId VARCHAR(20) NOT NULL,
        roleName VARCHAR(40),
        validStatus VARCHAR(2) DEFAULT '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (roleId)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

--用户组表
CREATE TABLE
    rbac_group
    (
        groupId VARCHAR(20) NOT NULL,
        groupName VARCHAR(60),
        validStatus VARCHAR(2) DEFAULT '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (groupId)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

--组角色关系表
CREATE TABLE
    rbac_group_role
    (
        groupId VARCHAR(20) NOT NULL,
        groupName VARCHAR(60),
        roleId VARCHAR(20) NOT NULL,
        roleName VARCHAR(60),
        validStatus VARCHAR(2) DEFAULT '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (groupId, roleId)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

--用户组关系表
CREATE TABLE
    rbac_user_group
    (
        userId VARCHAR(20) NOT NULL,
        userName VARCHAR(60),
        groupId VARCHAR(20) NOT NULL,
        groupName VARCHAR(60),
        validStatus VARCHAR(2) DEFAULT '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (userId, groupId)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

--用户角色关系表
CREATE TABLE
    rbac_user_role
    (
        userId VARCHAR(20) NOT NULL,
        userName VARCHAR(60),
        roleId VARCHAR(20) NOT NULL,
        roleName VARCHAR(60),
        validStatus VARCHAR(2) DEFAULT '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (userId, roleId)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

