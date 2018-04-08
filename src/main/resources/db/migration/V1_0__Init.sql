create table organization (
    ORG_GUID CHAR(36) NOT NULL,
    NAME VARCHAR(50) NOT NULL,
    DESCRIPTION VARCHAR(250) NOT NULL,
    ADDRESS1 VARCHAR(100) NULL,
    CITY VARCHAR(50) NULL,
    STATE VARCHAR(2) NULL,
    ZIP VARCHAR(5) NULL,
    PHONE VARCHAR(10) NULL,
    EMAIL VARCHAR(100) NULL,
    WEB_URL VARCHAR(100) NOT NULL,
    FACEBOOK VARCHAR(100),
    TWITTER VARCHAR(100),
    INSTAGRAM VARCHAR(100),
    PRIMARY KEY (ORG_GUID)
);

create table user (
  USER_GUID CHAR(36) NOT NULL,
  USER_ID VARCHAR(36) NOT NULL,
  FIRST_NAME VARCHAR(50) NOT NULL,
  LAST_NAME VARCHAR(50) NOT NULL,
  EMAIL VARCHAR(50) NOT NULL,
  ROLE VARCHAR(50) NOT NULL,
  PRIMARY KEY (USER_GUID));

create table project (
  PROJ_GUID CHAR(36) NOT NULL,
  PROJ_ID VARCHAR(20) NOT NULL,
  ORG_GUID CHAR(36) NOT NULL,
  NAME VARCHAR(50) NOT NULL,
  DESCRIPTION VARCHAR(250) NOT NULL,
  START_DT TIMESTAMP NOT NULL,
  END_DT TIMESTAMP null default null,
  CONSTRAINT PRJ_ORG_FK FOREIGN KEY (ORG_GUID) REFERENCES organization (ORG_GUID),
  PRIMARY KEY (PROJ_GUID));

create table user_org (
  ORG_GUID CHAR(36) NOT NULL,
  USER_GUID CHAR(36) NOT NULL,
  CONSTRAINT USR_ORG_U_FK FOREIGN KEY (USER_GUID) REFERENCES user (USER_GUID),
  CONSTRAINT USR_ORG_O_FK FOREIGN KEY (ORG_GUID) REFERENCES organization (ORG_GUID),
  PRIMARY KEY (ORG_GUID,USER_GUID));

create table user_project (
  PROJ_GUID CHAR(36) NOT NULL,
  USER_GUID CHAR(36) NOT NULL,
  CONSTRAINT USR_PRJ_U_FK FOREIGN KEY (USER_GUID) REFERENCES user (USER_GUID),
  CONSTRAINT USR_PRJ_P_FK FOREIGN KEY (PROJ_GUID) REFERENCES project (PROJ_GUID),
  PRIMARY KEY (PROJ_GUID,USER_GUID));

create table login_token (
  USER_GUID CHAR(36) NOT NULL,
  TOKEN CHAR(200) NOT NULL,
  TOKEN_EXP_DATE TIMESTAMP NOT NULL,
  CONSTRAINT login_user_FK FOREIGN KEY (USER_GUID) REFERENCES user (USER_GUID),
  PRIMARY KEY (USER_GUID,TOKEN,TOKEN_EXP_DATE));

create table check_in (
  USER_GUID CHAR(36) NOT NULL,
  PROJ_GUID CHAR(20) NOT NULL,
  TIME_IN TIMESTAMP NOT NULL,
  TIME_OUT TIMESTAMP null DEFAULT null,
  PRIMARY KEY (USER_GUID,PROJ_GUID,TIME_IN));