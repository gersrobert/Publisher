drop table if exists app_user cascade;
drop table if exists app_user_liked_articles cascade;
drop table if exists app_user_read_articles cascade;
drop table if exists app_user_roles cascade;
drop table if exists app_user_subscribed_categories cascade;
drop table if exists article cascade;
drop table if exists article_authors cascade;
drop table if exists article_categories cascade;
drop table if exists article_comments cascade;
drop table if exists category cascade;
drop table if exists comment cascade;
drop table if exists publisher cascade;
drop table if exists publisher_articles cascade;
drop table if exists publisher_authors cascade;
drop table if exists role cascade;
create table app_user
(
    id           varchar(255) not null,
    created_at   timestamp,
    state        varchar(255),
    updated_at   timestamp,
    first_name   varchar(255),
    last_name    varchar(255),
    user_name    varchar(255),
    publisher_id varchar(255),
    primary key (id)
);
create table app_user_liked_articles
(
    app_user_id       varchar(255) not null,
    liked_articles_id varchar(255) not null
);
create table app_user_read_articles
(
    app_user_id      varchar(255) not null,
    read_articles_id varchar(255) not null
);
create table app_user_roles
(
    app_user_id varchar(255) not null,
    roles_id    varchar(255) not null
);
create table app_user_subscribed_categories
(
    app_user_id              varchar(255) not null,
    subscribed_categories_id varchar(255) not null
);
create table article
(
    id           varchar(255) not null,
    created_at   timestamp,
    state        varchar(255),
    updated_at   timestamp,
    content      text,
    title        varchar(255),
    publisher_id varchar(255),
    primary key (id)
);
create table article_authors
(
    article_id varchar(255) not null,
    authors_id varchar(255) not null
);
create table article_categories
(
    article_id    varchar(255) not null,
    categories_id varchar(255) not null
);
create table article_comments
(
    article_id  varchar(255) not null,
    comments_id varchar(255) not null
);
create table category
(
    id         varchar(255) not null,
    created_at timestamp,
    state      varchar(255),
    updated_at timestamp,
    name       varchar(255),
    primary key (id)
);
create table comment
(
    id         varchar(255) not null,
    created_at timestamp,
    state      varchar(255),
    updated_at timestamp,
    author_id  varchar(255),
    primary key (id)
);
create table publisher
(
    id         varchar(255) not null,
    created_at timestamp,
    state      varchar(255),
    updated_at timestamp,
    name       varchar(255),
    primary key (id)
);
create table publisher_articles
(
    publisher_id varchar(255) not null,
    articles_id  varchar(255) not null
);
create table publisher_authors
(
    publisher_id varchar(255) not null,
    authors_id   varchar(255) not null
);
create table role
(
    id         varchar(255) not null,
    created_at timestamp,
    state      varchar(255),
    updated_at timestamp,
    name       varchar(255),
    primary key (id)
);
alter table if exists app_user
    add constraint UK_cpt2jpnop7mcpds1sv2i5629w unique (user_name);
alter table if exists publisher_articles
    add constraint UK_prp0ccxow2rvgimtxgv8xupy4 unique (articles_id);
alter table if exists publisher_authors
    add constraint UK_i5jow4se0vdmsnq18beg872lp unique (authors_id);
alter table if exists app_user
    add constraint FKc0xana8b64tou9cwfb9cavtho foreign key (publisher_id) references publisher;
alter table if exists app_user_liked_articles
    add constraint FKlxa9kgihu7w3xylw337875i82 foreign key (liked_articles_id) references article;
alter table if exists app_user_liked_articles
    add constraint FKqp3a0twoq86usn1pocmpfrdvi foreign key (app_user_id) references app_user;
alter table if exists app_user_read_articles
    add constraint FK3glif5aoa1bl0jq0ckit09sw2 foreign key (read_articles_id) references article;
alter table if exists app_user_read_articles
    add constraint FKbnqwx9qic1yivhrg9aq6axivs foreign key (app_user_id) references app_user;
alter table if exists app_user_roles
    add constraint FK23e7b5jyl3ql41rk3566gywdd foreign key (roles_id) references role;
alter table if exists app_user_roles
    add constraint FKkwxexnudtp5gmt82j0qtytnoe foreign key (app_user_id) references app_user;
alter table if exists app_user_subscribed_categories
    add constraint FKm29at21hlujok5reiw5bkfahe foreign key (subscribed_categories_id) references category;
alter table if exists app_user_subscribed_categories
    add constraint FKlnchn0670f701trv1n77tmv1 foreign key (app_user_id) references app_user;
alter table if exists article
    add constraint FKad5iivhsmwwugsh8yj77jlk0i foreign key (publisher_id) references publisher;
alter table if exists article_authors
    add constraint FK4cx2yo83tped2dgnjaxi556g6 foreign key (authors_id) references app_user;
alter table if exists article_authors
    add constraint FKspag2w8eoclck7kfq9nhd2g88 foreign key (article_id) references article;
alter table if exists article_categories
    add constraint FKojn3kggha3c6fptgfv60b68vu foreign key (categories_id) references category;
alter table if exists article_categories
    add constraint FK2574p9xuuvh3pxhlfvq6f7rbf foreign key (article_id) references article;
alter table if exists article_comments
    add constraint FKpodmjhlfsry50qirst9nv0q38 foreign key (comments_id) references comment;
alter table if exists article_comments
    add constraint FKhdo7dtp0o8cn5wo7j1cs1gokg foreign key (article_id) references article;
alter table if exists comment
    add constraint FKemqjawvkiyejurssvwm5c21y7 foreign key (author_id) references app_user;
alter table if exists publisher_articles
    add constraint FK7teuyfjfp9l96jbqcy1jowf6e foreign key (articles_id) references article;
alter table if exists publisher_articles
    add constraint FKs4qvn2lowuxsf1ge6abpv9vy6 foreign key (publisher_id) references publisher;
alter table if exists publisher_authors
    add constraint FKcye98610sqlbxv4qk5gb2dhs1 foreign key (authors_id) references app_user;
alter table if exists publisher_authors
    add constraint FKojslxvsg6nel09vd9xsbn8953 foreign key (publisher_id) references publisher;