create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create table category
(
    id varchar(255) not null
        constraint category_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    name varchar(255)
);

alter table category owner to postgres;

create table publisher
(
    id varchar(255) not null
        constraint publisher_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    name varchar(255)
);

alter table publisher owner to postgres;

create table app_user
(
    id varchar(255) not null
        constraint app_user_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    first_name varchar(255),
    last_name varchar(255),
    password_hash varchar(255),
    user_name varchar(255)
        constraint uk_cpt2jpnop7mcpds1sv2i5629w
            unique,
    publisher_id varchar(255)
        constraint fkc0xana8b64tou9cwfb9cavtho
            references publisher
);

alter table app_user owner to postgres;

create table app_user_subscribed_categories
(
    app_user_id varchar(255) not null
        constraint fklnchn0670f701trv1n77tmv1
            references app_user,
    subscribed_categories_id varchar(255) not null
        constraint fkm29at21hlujok5reiw5bkfahe
            references category
);

alter table app_user_subscribed_categories owner to postgres;

create table article
(
    id varchar(255) not null
        constraint article_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    content text,
    title varchar(255),
    publisher_id varchar(255)
        constraint fkad5iivhsmwwugsh8yj77jlk0i
            references publisher
);

alter table article owner to postgres;

create table app_user_article_relation
(
    id varchar(255) not null
        constraint app_user_article_relation_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    relation_type varchar(255),
    app_user_id varchar(255)
        constraint fkaxk56ip9c1o9insb040235sa4
            references app_user,
    article_id varchar(255)
        constraint fkph0jvg1c386diiqs6oetailil
            references article
);

alter table app_user_article_relation owner to postgres;

create index idxp6am8mryjykoe3n5amtbqqbck
    on app_user_article_relation (article_id);

create index idxefq25xury3o8eouemsxp45dik
    on app_user_article_relation (app_user_id);

create table article_categories
(
    article_id varchar(255) not null
        constraint fk2574p9xuuvh3pxhlfvq6f7rbf
            references article,
    categories_id varchar(255) not null
        constraint fkojn3kggha3c6fptgfv60b68vu
            references category
);

alter table article_categories owner to postgres;

create table collection
(
    id varchar(255) not null
        constraint collection_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    description varchar(255),
    author_id varchar(255)
        constraint fk5uhjwgg1e40r811gus67k3les
            references app_user
);

alter table collection owner to postgres;

create table collection_articles
(
    collection_id varchar(255) not null
        constraint fk25hk7s36fgsgh4fhan8si9mvk
            references collection,
    articles_id varchar(255) not null
        constraint fkhf5uia4xjeiogvotsm8qry8nc
            references article
);

alter table collection_articles owner to postgres;

create table comment
(
    id varchar(255) not null
        constraint comment_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    content text,
    article_id varchar(255)
        constraint fk5yx0uphgjc6ik6hb82kkw501y
            references article,
    author_id varchar(255)
        constraint fkemqjawvkiyejurssvwm5c21y7
            references app_user
);

alter table comment owner to postgres;

create table publisher_articles
(
    publisher_id varchar(255) not null
        constraint fks4qvn2lowuxsf1ge6abpv9vy6
            references publisher,
    articles_id varchar(255) not null
        constraint uk_prp0ccxow2rvgimtxgv8xupy4
            unique
        constraint fk7teuyfjfp9l96jbqcy1jowf6e
            references article
);

alter table publisher_articles owner to postgres;

create table publisher_authors
(
    publisher_id varchar(255) not null
        constraint fkojslxvsg6nel09vd9xsbn8953
            references publisher,
    authors_id varchar(255) not null
        constraint uk_i5jow4se0vdmsnq18beg872lp
            unique
        constraint fkcye98610sqlbxv4qk5gb2dhs1
            references app_user
);

alter table publisher_authors owner to postgres;

create table role
(
    id varchar(255) not null
        constraint role_pkey
            primary key,
    created_at timestamp,
    state varchar(255),
    updated_at timestamp,
    name varchar(255)
);

alter table role owner to postgres;

create table app_user_roles
(
    app_user_id varchar(255) not null
        constraint fkkwxexnudtp5gmt82j0qtytnoe
            references app_user,
    roles_id varchar(255) not null
        constraint fk23e7b5jyl3ql41rk3566gywdd
            references role
);

alter table app_user_roles owner to postgres;

