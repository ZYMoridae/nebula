-- Table: public.file

SET CLIENT_MIN_MESSAGES = WARNING;

BEGIN;
-- Create public.roles table

DROP TABLE IF EXISTS public.role;

CREATE TABLE public.role
(
    id         serial,
    code       text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

-- Create public.users table

DROP TABLE IF EXISTS public.user;

CREATE TABLE public.user
(
    id         serial,
    password   text                  NOT NULL,
    created_at timestamp without time zone    DEFAULT now(),
    updated_at timestamp without time zone    DEFAULT now(),
    role_id    integer               NOT NULL DEFAULT 1,
    telephone  text                  NOT NULL,
    address1   character varying(50) NOT NULL,
    address2   character varying(50) NOT NULL,
    firstname  character varying(50) NOT NULL,
    lastname   character varying(50) NOT NULL,
    gender     character varying(2)  NOT NULL,
    email      character varying(50) NOT NULL,
    username   character varying(50) NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT user_email_key UNIQUE (email),
    CONSTRAINT user_username_key UNIQUE (username)
);

-- Create public.file table

DROP TABLE IF EXISTS public.file;

CREATE TABLE public.file
(
    id         serial,
    name       text    NOT NULL,
    path       text    NOT NULL,
    user_id    integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT file_pkey PRIMARY KEY (id),
    CONSTRAINT file_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE IF EXISTS public.note;

CREATE TABLE public.note
(
    id      serial,
    body    text    NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT note_pkey PRIMARY KEY (id),
    CONSTRAINT note_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE IF EXISTS public.logisticsProvider;

CREATE TABLE public.logisticsProvider
(
    id         serial,
    name       text                  NOT NULL,
    contact    character varying(20) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT shipper_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS public.product_category;

CREATE TABLE public.product_category
(
    id         serial,
    name       text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT product_category_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS public.product;

CREATE TABLE public.product
(
    id             serial,
    name           text    NOT NULL,
    vendor_id      integer,
    price          numeric NOT NULL,
    units_in_stock integer NOT NULL            DEFAULT 0,
    created_at     timestamp without time zone DEFAULT now(),
    updated_at     timestamp without time zone DEFAULT now(),
    category_id    integer,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_category_id_fkey FOREIGN KEY (category_id)
        REFERENCES public.product_category (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT product_vendor_id_fkey FOREIGN KEY (vendor_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);


DROP TABLE IF EXISTS public.orders;

CREATE TABLE public.orders
(
    id         serial,
    user_id    integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    shipper_id integer NOT NULL,
    CONSTRAINT order_pkey PRIMARY KEY (id),
    CONSTRAINT order_shipper_id_fkey FOREIGN KEY (shipper_id)
        REFERENCES public.logisticsProvider (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT order_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE CASCADE
);


DROP TABLE IF EXISTS public.order_item;

CREATE TABLE public.order_item
(
    id         serial,
    order_id   integer NOT NULL,
    unit_price numeric NOT NULL            DEFAULT 0,
    quantity   integer NOT NULL            DEFAULT 0,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    product_id integer NOT NULL,
    CONSTRAINT order_item_pkey PRIMARY KEY (id),
    CONSTRAINT order_item_order_id_fkey FOREIGN KEY (order_id)
        REFERENCES public.order (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT order_item_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE IF EXISTS public.invoice;

CREATE TABLE public.invoice
(
    id         serial,
    order_id   integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    invoice_id text    NOT NULL,
    CONSTRAINT invoice_pkey PRIMARY KEY (id),
    CONSTRAINT invoice_order_id_fkey FOREIGN KEY (order_id)
        REFERENCES public.order (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT invoice_invoice_id_key UNIQUE (invoice_id)
);


-- Drop table

-- DROP TABLE public.order_status;

CREATE TABLE public.order_status
(
    id         serial        NOT NULL,
    code       character(20) NOT NULL,
    "name"     text          NOT NULL,
    created_at timestamp     NULL DEFAULT now(),
    updated_at timestamp     NULL DEFAULT now(),
    CONSTRAINT order_status_pkey PRIMARY KEY (id)
);


INSERT INTO public.order_status
    (code, "name", created_at, updated_at)
VALUES ('PAD', 'paid', '2019-04-03 19:00:03.088', '2019-04-03 19:00:03.088');

INSERT INTO public.order_status
    (code, "name", created_at, updated_at)
VALUES ('UPD', 'unpaid', '2019-04-03 19:00:03.088', '2019-04-03 19:00:03.088');

INSERT INTO public.order_status
    (code, "name", created_at, updated_at)
VALUES ('SPD', 'shipped', '2019-04-03 19:00:03.088', '2019-04-03 19:00:03.088');

INSERT INTO public.order_status
    (code, "name", created_at, updated_at)
VALUES ('USD', 'unshipped', '2019-04-03 19:00:03.088', '2019-04-03 19:00:03.088');


create view vendor_info as (select username,
                                   telephone,
                                   address1,
                                   address2,
                                   firstname,
                                   lastname,
                                   gender,
                                   email
                            from "user");

DROP TABLE IF EXISTS public.cart;
CREATE TABLE public.cart
(
    id         serial,
    user_id    int NOT NULL REFERENCES "user" (id),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT cart_pkey PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for order_status
-- ----------------------------
DROP TABLE IF EXISTS public.cart_item;
CREATE TABLE public.cart_item
(
    id         serial,
    cart_id    int NOT NULL REFERENCES "cart" (id),
    product_id int NOT NULL REFERENCES "product" (id),
    quantity   int NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT cart_item_pkey PRIMARY KEY (id),
    CONSTRAINT cart_product_ukey UNIQUE (cart_id, product_id)
);

-- ----------------------------
-- Table structure for order_status
-- ----------------------------
DROP TABLE IF EXISTS public.order_status;
CREATE TABLE public.order_status
(
    id         serial,
    name       text not null,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT order_status_pkey PRIMARY KEY (id)
);

-- ----------------------------
-- Records of order_status
-- ----------------------------
INSERT INTO order_status(name)
VALUES ('pending');
INSERT INTO order_status(name)
VALUES ('paid');
INSERT INTO order_status(name)
VALUES ('refund');

ALTER TABLE public."orders"
    ADD COLUMN order_status_id int default 1;
ALTER TABLE public."orders"
    ADD CONSTRAINT fk_order_status_id foreign key (order_status_id) references order_status (id);


-- ----------------------------
-- Table structure for notification_status
-- ----------------------------
DROP TABLE IF EXISTS public.notification_status;

CREATE TABLE public.notification_status
(
    id         serial,
    code       varchar(30) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT notification_status_pkey PRIMARY KEY (id)
);

-- ----------------------------
-- Records of notification_status
-- ----------------------------
INSERT INTO public.notification_status(code)
VALUES ('UNREAD');
INSERT INTO public.notification_status(code)
VALUES ('READ');

DROP TABLE IF EXISTS public.notification;

CREATE TABLE public.notification
(
    id         serial,
    user_id    int  NOT NULL REFERENCES "user" (id),
    body       text NOT NULL,
    status_id  int  NOT NULL REFERENCES "notification_status" (id) DEFAULT 1,
    created_at timestamp without time zone                         DEFAULT now(),
    updated_at timestamp without time zone                         DEFAULT now(),
    CONSTRAINT notification_pkey PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS public.location;

CREATE TABLE public.location
(
    id           serial,
    country_code varchar(10) NOT NULL,
    state_code   varchar(10) NOT NULL,
    name         varchar     NOT NULL,
    postcode     varchar(20),
    created_at   timestamp without time zone DEFAULT now(),
    updated_at   timestamp without time zone DEFAULT now(),
    CONSTRAINT location_pkey PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for product_comment
-- ----------------------------
DROP TABLE IF EXISTS public.product_comment;

CREATE TABLE public.product_comment
(
    id                serial,
    product_id        int NOT NULL REFERENCES "product" (id),
    parent_comment_id int                         default 0,
    body              text,
    user_id           int NOT NULL REFERENCES "user" (id),
    created_at        timestamp without time zone DEFAULT now(),
    updated_at        timestamp without time zone DEFAULT now(),
    CONSTRAINT product_comment_pkey PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for product_rating
-- ----------------------------
DROP TABLE IF EXISTS public.product_rating;

CREATE TABLE public.product_rating
(
    id         serial,
    product_id int NOT NULL REFERENCES "product" (id),
    user_id    int NOT NULL REFERENCES "user" (id),
    rating     int                         default 0,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    check (rating >= 0 AND rating <= 5),
    CONSTRAINT product_rating_pkey PRIMARY KEY (id),
    CONSTRAINT product_id_user_id_ukey UNIQUE (product_id, user_id)
);

-- ----------------------------
-- Table structure for wish_list
-- ----------------------------
DROP TABLE IF EXISTS public.wish_list;
CREATE TABLE public.wish_list
(
    id         serial,
    user_id    int NOT NULL REFERENCES "user" (id),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT wish_list_pkey PRIMARY KEY (id)
);


-- ----------------------------
-- Table structure for wish_list_item
-- ----------------------------
DROP TABLE IF EXISTS public.wish_list_item;
CREATE TABLE public.wish_list_item
(
    id           serial,
    wish_list_id int NOT NULL REFERENCES "wish_list" (id),
    product_id   int NOT NULL REFERENCES "product" (id),
    quantity     int NOT NULL,
    created_at   timestamp without time zone DEFAULT now(),
    updated_at   timestamp without time zone DEFAULT now(),
    CONSTRAINT wish_list_item_pkey PRIMARY KEY (id),
    CONSTRAINT wish_list_product_ukey UNIQUE (wish_list_id, product_id)
);

-- ----------------------------
-- Table structure for order_shipping_info
-- ----------------------------
DROP TABLE IF EXISTS public.order_shipping_info;
CREATE TABLE public.order_shipping_info
(
    id         serial,
    orders_id  int                   NOT NULL REFERENCES "orders" (id),
    address1   character varying(50) NOT NULL,
    address2   character varying(50),
    firstname  character varying(50) NOT NULL,
    lastname   character varying(50) NOT NULL,
    email      character varying(50) NOT NULL,
    post_code  character varying(50) NOT NULL,
    telephone  text                  NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT order_shipping_info_pkey PRIMARY KEY (id),
    CONSTRAINT order_shipping_info_orders_id_ukey UNIQUE (orders_id)
);

-- ----------------------------
-- Table structure for user_shipping_preference
-- ----------------------------
DROP TABLE IF EXISTS public.user_shipping_preference;
CREATE TABLE public.user_shipping_preference
(
    id         serial,
    user_id    int                    NOT NULL REFERENCES "user" (id),
    first_name character varying(50)  NOT NULL,
    last_name  character varying(50)  NOT NULL,
    email      character varying(200) NOT NULL,
    phone      character varying(50)  NOT NULL,
    address1   character varying(50)  NOT NULL,
    address2   character varying(50),
    post_code  character varying(50)  NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT user_shipping_preference_pkey PRIMARY KEY (id),
    CONSTRAINT user_shipping_preference_user_id_ukey UNIQUE (user_id)
);

-- ----------------------------
-- Table structure for product_meta
-- ----------------------------
CREATE TABLE public.product_meta
(
    id         serial       NOT NULL,
    "key"      varchar(500) NOT NULL,
    value      varchar(500) NOT NULL,
    product_id int4         NOT NULL,
    created_at timestamp    NULL DEFAULT now(),
    updated_at timestamp    NULL DEFAULT now(),
    CONSTRAINT product_meta_pkey PRIMARY KEY (id),
    CONSTRAINT product_meta_product_id_fkey FOREIGN KEY (product_id) REFERENCES product (id)
);

ALTER TABLE product
    ADD COLUMN description text;

INSERT INTO "role"(code)
VALUES ('USER');
INSERT INTO "role"(code)
VALUES ('ADMIN');
INSERT INTO "role"(code)
VALUES ('VENDOR');
insert into "role"(code)
values ('TEACHER');

CREATE SEQUENCE hibernate_sequence START 1;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
-- CREATE TABLE public.user_roles
-- (
--     user_id int8         NOT NULL,
--     roles   varchar(255) NULL,
--     CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES "user" (id)
-- );

-- ----------------------------
-- Table structure for home_banner
-- ----------------------------
DROP TABLE IF EXISTS public.home_banner;
CREATE TABLE public.home_banner
(
    id          serial       NOT NULL,
    image_url   varchar      NOT NULL,
    title       varchar(250) NOT NULL,
    description text         NOT NULL,
    link        varchar      NOT NULL,
    active      boolean      not null default false,
    created_at  timestamp    NULL     DEFAULT now(),
    updated_at  timestamp    NULL     DEFAULT now()
);

DROP TABLE IF EXISTS public.user_roles;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
CREATE TABLE public.user_roles
(
    id         serial,
    user_id    int NOT NULL REFERENCES "user" (id),
    role_id    int NOT NULL REFERENCES "role" (id),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT user_roles_pkey PRIMARY KEY (id),
    CONSTRAINT user_roles_ukey UNIQUE (user_id, role_id)
);

-- ----------------------------
-- Table structure for teacher_info
-- ----------------------------
drop view if exists teacher_info;
create view teacher_info as (select *
                             from "user"
                             where id in (select user_id
                                          from user_roles
                                          where role_id in (select id from "role" where code = 'TEACHER')));

drop schema if exists teacher;
create schema teacher;
set search_path = teacher, public;

-- ----------------------------
-- Table structure for teacher_meta
-- ----------------------------
DROP TABLE IF EXISTS public.teacher_meta;
CREATE TABLE public.teacher_meta
(
    id           serial,
    user_id      int                  NOT NULL REFERENCES "user" (id),
    intro        text,
    country_code character varying(2) NOT NULL,
    avatar       text,
    created_at   timestamp without time zone DEFAULT now(),
    updated_at   timestamp without time zone DEFAULT now(),
    CONSTRAINT teacher_meta_pkey PRIMARY KEY (id),
    CONSTRAINT teacher_meta_ukey UNIQUE (user_id)
);

-- ----------------------------
-- Table structure for teacher_subscription
-- ----------------------------
DROP TABLE IF EXISTS public.teacher_subscription;
CREATE TABLE public.teacher_subscription
(
    id         serial,
    teacher_id int NOT NULL REFERENCES "user" (id),
    user_id    int NOT NULL REFERENCES "user" (id),
    started_at timestamp without time zone DEFAULT now(),
    expired_at timestamp without time zone DEFAULT now(),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT teacher_subscription_pkey PRIMARY KEY (id),
    CONSTRAINT teacher_subscription_ukey UNIQUE (teacher_id)
);

-- ----------------------------
-- Table structure for sku
-- ----------------------------
DROP TABLE IF EXISTS public.sku;
CREATE TABLE public.sku
(
    id              serial,
    product_id      int                   NOT NULL REFERENCES "product" (id),
    sku_code        character varying(50) NOT NULL,
    price           numeric               NOT NULL,
    stock           int                   NOT NULL,
    created_user_id int                   not null REFERENCES "user" (id),
    created_at      timestamp without time zone DEFAULT now(),
    updated_at      timestamp without time zone DEFAULT now(),
    CONSTRAINT sku_pkey PRIMARY KEY (id),
    CONSTRAINT sku_code_ukey UNIQUE (sku_code)
);
CREATE INDEX ON sku (sku_code);
CREATE INDEX ON sku (created_user_id);

-- ----------------------------
-- Table structure for sku_attribute_category
-- ----------------------------
DROP TABLE IF EXISTS public.sku_attribute_category;
CREATE TABLE public.sku_attribute_category
(
    id         serial,
    name       text not null,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT sku_attribute_category_pkey PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for sku_attribute
-- ----------------------------
DROP TABLE IF EXISTS public.sku_attribute;
CREATE TABLE public.sku_attribute
(
    id                        serial,
    sku_code                  character varying(50) NOT NULL REFERENCES "sku" (sku_code),
    sku_attribute_category_id int                   NOT NULL REFERENCES "sku_attribute_category" (id),
    value                     text,
    created_at                timestamp without time zone DEFAULT now(),
    updated_at                timestamp without time zone DEFAULT now(),
    CONSTRAINT sku_attribute_pkey PRIMARY KEY (id)
);
CREATE INDEX ON sku_attribute (sku_code);

-- ----------------------------
-- Table structure for order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS public.order_operate_history;
CREATE TABLE public.order_operate_history
(
    id              serial,
    order_id        int not null references "orders" (id),
    operate_user    int not null references "user" (id),
    order_status_id int not null references "order_status" (id),
    note            text                        default null,
    created_at      timestamp without time zone DEFAULT now(),
    updated_at      timestamp without time zone DEFAULT now()
);

ALTER TABLE order_item ADD COLUMN sku_code character varying(50) NOT NULL REFERENCES "sku" (sku_code);


COMMIT;