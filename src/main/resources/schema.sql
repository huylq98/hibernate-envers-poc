drop table if exists "rev_info" CASCADE;
drop table if exists "category_audit" CASCADE;
drop table if exists "domain_audit" CASCADE;
create table "rev_info"
(
    "rev"      integer generated by default as identity,
    "revtstmp" datetime,
    primary key ("rev")
);
create table "category_audit"
(
    "id"        bigint  not null,
    "rev"       integer not null,
    "revtype"   tinyint,
    "name"      varchar(255),
    "domain_id" bigint,
    primary key ("id", "rev")
);
create table "domain_audit"
(
    "id"              bigint  not null,
    "rev"             integer not null,
    "revtype"         tinyint,
    "category_amount" integer,
    "name"            varchar(255),
    primary key ("id", "rev")
);
alter table "category_audit"
    add constraint "category_audit_revinfo" foreign key ("rev") references "rev_info";
alter table "domain_audit"
    add constraint "domain_audit_revinfo" foreign key ("rev") references "rev_info";