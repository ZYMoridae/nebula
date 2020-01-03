--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: api_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.api_category (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.api_category OWNER TO postgres;

--
-- Name: api_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.api_category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.api_category_id_seq OWNER TO postgres;

--
-- Name: api_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.api_category_id_seq OWNED BY public.api_category.id;


--
-- Name: api_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.api_info (
    id integer NOT NULL,
    action_type character varying(50) NOT NULL,
    api_category_id integer NOT NULL,
    request character varying,
    response character varying,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    end_point character varying(200)
);


ALTER TABLE public.api_info OWNER TO postgres;

--
-- Name: api_info_header; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.api_info_header (
    id integer NOT NULL,
    api_info_id integer NOT NULL,
    name character varying(50) NOT NULL,
    value text[] NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.api_info_header OWNER TO postgres;

--
-- Name: api_info_header_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.api_info_header_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.api_info_header_id_seq OWNER TO postgres;

--
-- Name: api_info_header_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.api_info_header_id_seq OWNED BY public.api_info_header.id;


--
-- Name: api_info_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.api_info_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.api_info_id_seq OWNER TO postgres;

--
-- Name: api_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.api_info_id_seq OWNED BY public.api_info.id;


--
-- Name: cart; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.cart (
    id integer NOT NULL,
    user_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.cart OWNER TO yuezhou;

--
-- Name: cart_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.cart_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cart_id_seq OWNER TO yuezhou;

--
-- Name: cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.cart_id_seq OWNED BY public.cart.id;


--
-- Name: cart_item; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.cart_item (
    id integer NOT NULL,
    cart_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    sku_code character varying(200)
);


ALTER TABLE public.cart_item OWNER TO yuezhou;

--
-- Name: cart_item_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.cart_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cart_item_id_seq OWNER TO yuezhou;

--
-- Name: cart_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.cart_item_id_seq OWNED BY public.cart_item.id;


--
-- Name: class; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.class (
    id integer NOT NULL,
    name text NOT NULL,
    description text NOT NULL,
    unit text NOT NULL,
    unit_price double precision NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    teacher_id integer NOT NULL
);


ALTER TABLE public.class OWNER TO postgres;

--
-- Name: class_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.class_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.class_id_seq OWNER TO postgres;

--
-- Name: class_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.class_id_seq OWNED BY public.class.id;


--
-- Name: class_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.class_order (
    id integer NOT NULL,
    user_id integer NOT NULL,
    class_id integer NOT NULL,
    unit_count integer NOT NULL,
    total_price double precision NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    status_id integer NOT NULL
);


ALTER TABLE public.class_order OWNER TO postgres;

--
-- Name: class_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.class_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.class_order_id_seq OWNER TO postgres;

--
-- Name: class_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.class_order_id_seq OWNED BY public.class_order.id;


--
-- Name: currency_rates; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.currency_rates (
    id integer NOT NULL,
    currency_code character varying(5) NOT NULL,
    base_currency character varying(5) NOT NULL,
    rate double precision NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.currency_rates OWNER TO postgres;

--
-- Name: currency_rates_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.currency_rates_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.currency_rates_id_seq OWNER TO postgres;

--
-- Name: currency_rates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.currency_rates_id_seq OWNED BY public.currency_rates.id;


--
-- Name: file; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.file (
    id integer NOT NULL,
    name text NOT NULL,
    path text NOT NULL,
    user_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.file OWNER TO yuezhou;

--
-- Name: file_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.file_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.file_id_seq OWNER TO yuezhou;

--
-- Name: file_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.file_id_seq OWNED BY public.file.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: home_banner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.home_banner (
    id integer NOT NULL,
    image_url character varying NOT NULL,
    title character varying(250) NOT NULL,
    description text NOT NULL,
    link character varying NOT NULL,
    active boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.home_banner OWNER TO postgres;

--
-- Name: home_banner_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.home_banner_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.home_banner_id_seq OWNER TO postgres;

--
-- Name: home_banner_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.home_banner_id_seq OWNED BY public.home_banner.id;


--
-- Name: invoice; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.invoice (
    id integer NOT NULL,
    order_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    invoice_id text NOT NULL
);


ALTER TABLE public.invoice OWNER TO yuezhou;

--
-- Name: invoice_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.invoice_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invoice_id_seq OWNER TO yuezhou;

--
-- Name: invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.invoice_id_seq OWNED BY public.invoice.id;


--
-- Name: location; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.location (
    id integer NOT NULL,
    country_code character varying(10) NOT NULL,
    state_code character varying(10) NOT NULL,
    name character varying NOT NULL,
    postcode character varying(20),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.location OWNER TO postgres;

--
-- Name: location_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.location_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.location_id_seq OWNER TO postgres;

--
-- Name: location_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.location_id_seq OWNED BY public.location.id;


--
-- Name: member_level; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.member_level (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    growth_point integer DEFAULT 0,
    privilege_birthday boolean DEFAULT false,
    note character varying(200) DEFAULT NULL::character varying,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.member_level OWNER TO postgres;

--
-- Name: member_level_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.member_level_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.member_level_id_seq OWNER TO postgres;

--
-- Name: member_level_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.member_level_id_seq OWNED BY public.member_level.id;


--
-- Name: member_price; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.member_price (
    id integer NOT NULL,
    product_id integer NOT NULL,
    member_level_id integer NOT NULL,
    member_price numeric NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.member_price OWNER TO postgres;

--
-- Name: member_price_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.member_price_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.member_price_id_seq OWNER TO postgres;

--
-- Name: member_price_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.member_price_id_seq OWNED BY public.member_price.id;


--
-- Name: note; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.note (
    id integer NOT NULL,
    body text NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.note OWNER TO yuezhou;

--
-- Name: note_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.note_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.note_id_seq OWNER TO yuezhou;

--
-- Name: note_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.note_id_seq OWNED BY public.note.id;


--
-- Name: notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notification (
    id integer NOT NULL,
    user_id integer NOT NULL,
    body text NOT NULL,
    status_id integer DEFAULT 1 NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.notification OWNER TO postgres;

--
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notification_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notification_id_seq OWNER TO postgres;

--
-- Name: notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notification_id_seq OWNED BY public.notification.id;


--
-- Name: notification_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notification_status (
    id integer NOT NULL,
    code character varying(30) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.notification_status OWNER TO postgres;

--
-- Name: notification_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notification_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notification_status_id_seq OWNER TO postgres;

--
-- Name: notification_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notification_status_id_seq OWNED BY public.notification_status.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.orders (
    id integer NOT NULL,
    user_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    shipper_id integer NOT NULL,
    order_status_id integer DEFAULT 1
);


ALTER TABLE public.orders OWNER TO yuezhou;

--
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_id_seq OWNER TO yuezhou;

--
-- Name: order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.order_id_seq OWNED BY public.orders.id;


--
-- Name: order_item; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.order_item (
    id integer NOT NULL,
    order_id integer NOT NULL,
    unit_price numeric DEFAULT 0 NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    product_id integer NOT NULL,
    sku_code character varying(200)
);


ALTER TABLE public.order_item OWNER TO yuezhou;

--
-- Name: order_item_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.order_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_item_id_seq OWNER TO yuezhou;

--
-- Name: order_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.order_item_id_seq OWNED BY public.order_item.id;


--
-- Name: order_operate_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_operate_history (
    id integer NOT NULL,
    order_id integer NOT NULL,
    operate_user integer NOT NULL,
    order_status_id integer NOT NULL,
    note text,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.order_operate_history OWNER TO postgres;

--
-- Name: order_operate_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_operate_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_operate_history_id_seq OWNER TO postgres;

--
-- Name: order_operate_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_operate_history_id_seq OWNED BY public.order_operate_history.id;


--
-- Name: order_return_apply; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_return_apply (
    id integer NOT NULL,
    order_id integer NOT NULL,
    user_id integer NOT NULL,
    handler_user_id integer NOT NULL,
    handle_note character varying(500) DEFAULT NULL::character varying,
    reason text NOT NULL,
    status_id integer NOT NULL,
    description text,
    proof_pics character varying(1000) DEFAULT NULL::character varying,
    receive_man character varying(100) NOT NULL,
    receive_time timestamp with time zone,
    receive_note character varying(500) DEFAULT NULL::character varying,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.order_return_apply OWNER TO postgres;

--
-- Name: order_return_apply_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_return_apply_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_return_apply_id_seq OWNER TO postgres;

--
-- Name: order_return_apply_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_return_apply_id_seq OWNED BY public.order_return_apply.id;


--
-- Name: order_return_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_return_status (
    id integer NOT NULL,
    name character(50) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.order_return_status OWNER TO postgres;

--
-- Name: order_return_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_return_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_return_status_id_seq OWNER TO postgres;

--
-- Name: order_return_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_return_status_id_seq OWNED BY public.order_return_status.id;


--
-- Name: order_shipping_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_shipping_info (
    id integer NOT NULL,
    orders_id integer NOT NULL,
    address1 character varying(50) NOT NULL,
    address2 character varying(50),
    firstname character varying(50) NOT NULL,
    lastname character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    post_code character varying(50) NOT NULL,
    telephone text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.order_shipping_info OWNER TO postgres;

--
-- Name: order_shipping_info_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_shipping_info_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_shipping_info_id_seq OWNER TO postgres;

--
-- Name: order_shipping_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_shipping_info_id_seq OWNED BY public.order_shipping_info.id;


--
-- Name: order_status; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.order_status (
    id integer NOT NULL,
    name text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.order_status OWNER TO yuezhou;

--
-- Name: order_status_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.order_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_status_id_seq OWNER TO yuezhou;

--
-- Name: order_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.order_status_id_seq OWNED BY public.order_status.id;


--
-- Name: product; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.product (
    id integer NOT NULL,
    name text NOT NULL,
    vendor_id integer,
    price numeric NOT NULL,
    units_in_stock integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    category_id integer,
    description character varying(255)
);


ALTER TABLE public.product OWNER TO yuezhou;

--
-- Name: product_category; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.product_category (
    id integer NOT NULL,
    name text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.product_category OWNER TO yuezhou;

--
-- Name: product_category_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.product_category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_category_id_seq OWNER TO yuezhou;

--
-- Name: product_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.product_category_id_seq OWNED BY public.product_category.id;


--
-- Name: product_comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_comment (
    id integer NOT NULL,
    product_id integer NOT NULL,
    parent_comment_id integer DEFAULT 0,
    body text,
    user_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.product_comment OWNER TO postgres;

--
-- Name: product_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_comment_id_seq OWNER TO postgres;

--
-- Name: product_comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_comment_id_seq OWNED BY public.product_comment.id;


--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO yuezhou;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- Name: product_meta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_meta (
    id bigint NOT NULL,
    created_at date,
    key character varying(255),
    updated_at date,
    value character varying(255),
    product_id bigint NOT NULL
);


ALTER TABLE public.product_meta OWNER TO postgres;

--
-- Name: product_meta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_meta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_meta_id_seq OWNER TO postgres;

--
-- Name: product_meta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_meta_id_seq OWNED BY public.product_meta.id;


--
-- Name: product_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_rating (
    id integer NOT NULL,
    product_id integer NOT NULL,
    user_id integer NOT NULL,
    rating integer DEFAULT 0,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT product_rating_rating_check CHECK (((rating >= 0) AND (rating <= 5)))
);


ALTER TABLE public.product_rating OWNER TO postgres;

--
-- Name: product_rating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_rating_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_rating_id_seq OWNER TO postgres;

--
-- Name: product_rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_rating_id_seq OWNED BY public.product_rating.id;


--
-- Name: role; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.role (
    id integer NOT NULL,
    code text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.role OWNER TO yuezhou;

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO yuezhou;

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: shipper; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public.shipper (
    id integer NOT NULL,
    name text NOT NULL,
    contact character varying(20) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.shipper OWNER TO yuezhou;

--
-- Name: shipper_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.shipper_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.shipper_id_seq OWNER TO yuezhou;

--
-- Name: shipper_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.shipper_id_seq OWNED BY public.shipper.id;


--
-- Name: sku; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sku (
    id integer NOT NULL,
    product_id integer NOT NULL,
    sku_code character varying(50) NOT NULL,
    price numeric NOT NULL,
    stock integer NOT NULL,
    created_user_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.sku OWNER TO postgres;

--
-- Name: sku_attribute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sku_attribute (
    id integer NOT NULL,
    sku_code character varying(50) NOT NULL,
    sku_attribute_category_id integer NOT NULL,
    value text,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.sku_attribute OWNER TO postgres;

--
-- Name: sku_attribute_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sku_attribute_category (
    id integer NOT NULL,
    name text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.sku_attribute_category OWNER TO postgres;

--
-- Name: sku_attribute_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sku_attribute_category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sku_attribute_category_id_seq OWNER TO postgres;

--
-- Name: sku_attribute_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sku_attribute_category_id_seq OWNED BY public.sku_attribute_category.id;


--
-- Name: sku_attribute_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sku_attribute_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sku_attribute_id_seq OWNER TO postgres;

--
-- Name: sku_attribute_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sku_attribute_id_seq OWNED BY public.sku_attribute.id;


--
-- Name: sku_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sku_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sku_id_seq OWNER TO postgres;

--
-- Name: sku_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sku_id_seq OWNED BY public.sku.id;


--
-- Name: swagger_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.swagger_config (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    value text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.swagger_config OWNER TO postgres;

--
-- Name: swagger_config_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.swagger_config_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.swagger_config_id_seq OWNER TO postgres;

--
-- Name: swagger_config_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.swagger_config_id_seq OWNED BY public.swagger_config.id;


--
-- Name: teacher_available_time; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teacher_available_time (
    id integer NOT NULL,
    user_id integer NOT NULL,
    start_time timestamp without time zone DEFAULT now(),
    end_time timestamp without time zone DEFAULT now(),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.teacher_available_time OWNER TO postgres;

--
-- Name: teacher_available_time_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.teacher_available_time_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teacher_available_time_id_seq OWNER TO postgres;

--
-- Name: teacher_available_time_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.teacher_available_time_id_seq OWNED BY public.teacher_available_time.id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: yuezhou
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    password text NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    role_id integer DEFAULT 1 NOT NULL,
    telephone text NOT NULL,
    address1 character varying(50) NOT NULL,
    address2 character varying(50) NOT NULL,
    firstname character varying(50) NOT NULL,
    lastname character varying(50) NOT NULL,
    gender character varying(2) NOT NULL,
    email character varying(50) NOT NULL,
    username character varying(50) NOT NULL
);


ALTER TABLE public."user" OWNER TO yuezhou;

--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    id integer NOT NULL,
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- Name: teacher_info; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.teacher_info AS
 SELECT "user".id,
    "user".password,
    "user".created_at,
    "user".updated_at,
    "user".role_id,
    "user".telephone,
    "user".address1,
    "user".address2,
    "user".firstname,
    "user".lastname,
    "user".gender,
    "user".email,
    "user".username
   FROM public."user"
  WHERE ("user".id IN ( SELECT user_roles.user_id
           FROM public.user_roles
          WHERE (user_roles.role_id IN ( SELECT role.id
                   FROM public.role
                  WHERE (role.code = 'TEACHER'::text)))));


ALTER TABLE public.teacher_info OWNER TO postgres;

--
-- Name: teacher_meta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teacher_meta (
    id integer NOT NULL,
    user_id integer NOT NULL,
    intro text,
    country_code character varying(2) NOT NULL,
    avatar text,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.teacher_meta OWNER TO postgres;

--
-- Name: teacher_meta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.teacher_meta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teacher_meta_id_seq OWNER TO postgres;

--
-- Name: teacher_meta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.teacher_meta_id_seq OWNED BY public.teacher_meta.id;


--
-- Name: teacher_subscription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teacher_subscription (
    id integer NOT NULL,
    teacher_id integer NOT NULL,
    user_id integer NOT NULL,
    started_at timestamp without time zone DEFAULT now(),
    expired_at timestamp without time zone DEFAULT now(),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.teacher_subscription OWNER TO postgres;

--
-- Name: teacher_subscription_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.teacher_subscription_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teacher_subscription_id_seq OWNER TO postgres;

--
-- Name: teacher_subscription_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.teacher_subscription_id_seq OWNED BY public.teacher_subscription.id;


--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: yuezhou
--

CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO yuezhou;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yuezhou
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;


--
-- Name: user_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_log (
    id integer NOT NULL,
    action text NOT NULL,
    user_id integer NOT NULL,
    ip_addr character varying(50) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.user_log OWNER TO postgres;

--
-- Name: user_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_log_id_seq OWNER TO postgres;

--
-- Name: user_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_log_id_seq OWNED BY public.user_log.id;


--
-- Name: user_roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_roles_id_seq OWNER TO postgres;

--
-- Name: user_roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_roles_id_seq OWNED BY public.user_roles.id;


--
-- Name: user_shipping_preference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_shipping_preference (
    id integer NOT NULL,
    user_id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(200) NOT NULL,
    phone character varying(50) NOT NULL,
    address1 character varying(50) NOT NULL,
    address2 character varying(50),
    post_code character varying(50) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.user_shipping_preference OWNER TO postgres;

--
-- Name: user_shipping_preference_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_shipping_preference_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_shipping_preference_id_seq OWNER TO postgres;

--
-- Name: user_shipping_preference_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_shipping_preference_id_seq OWNED BY public.user_shipping_preference.id;


--
-- Name: vendor_info; Type: VIEW; Schema: public; Owner: yuezhou
--

CREATE VIEW public.vendor_info AS
 SELECT "user".username,
    "user".telephone,
    "user".address1,
    "user".address2,
    "user".firstname,
    "user".lastname,
    "user".gender,
    "user".email
   FROM public."user";


ALTER TABLE public.vendor_info OWNER TO yuezhou;

--
-- Name: wish_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wish_list (
    id integer NOT NULL,
    user_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.wish_list OWNER TO postgres;

--
-- Name: wish_list_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wish_list_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.wish_list_id_seq OWNER TO postgres;

--
-- Name: wish_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.wish_list_id_seq OWNED BY public.wish_list.id;


--
-- Name: wish_list_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wish_list_item (
    id integer NOT NULL,
    wish_list_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.wish_list_item OWNER TO postgres;

--
-- Name: wish_list_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wish_list_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.wish_list_item_id_seq OWNER TO postgres;

--
-- Name: wish_list_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.wish_list_item_id_seq OWNED BY public.wish_list_item.id;


--
-- Name: api_category id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_category ALTER COLUMN id SET DEFAULT nextval('public.api_category_id_seq'::regclass);


--
-- Name: api_info id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_info ALTER COLUMN id SET DEFAULT nextval('public.api_info_id_seq'::regclass);


--
-- Name: api_info_header id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_info_header ALTER COLUMN id SET DEFAULT nextval('public.api_info_header_id_seq'::regclass);


--
-- Name: cart id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart ALTER COLUMN id SET DEFAULT nextval('public.cart_id_seq'::regclass);


--
-- Name: cart_item id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart_item ALTER COLUMN id SET DEFAULT nextval('public.cart_item_id_seq'::regclass);


--
-- Name: class id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class ALTER COLUMN id SET DEFAULT nextval('public.class_id_seq'::regclass);


--
-- Name: class_order id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class_order ALTER COLUMN id SET DEFAULT nextval('public.class_order_id_seq'::regclass);


--
-- Name: currency_rates id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.currency_rates ALTER COLUMN id SET DEFAULT nextval('public.currency_rates_id_seq'::regclass);


--
-- Name: file id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.file ALTER COLUMN id SET DEFAULT nextval('public.file_id_seq'::regclass);


--
-- Name: home_banner id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.home_banner ALTER COLUMN id SET DEFAULT nextval('public.home_banner_id_seq'::regclass);


--
-- Name: invoice id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.invoice ALTER COLUMN id SET DEFAULT nextval('public.invoice_id_seq'::regclass);


--
-- Name: location id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.location ALTER COLUMN id SET DEFAULT nextval('public.location_id_seq'::regclass);


--
-- Name: member_level id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_level ALTER COLUMN id SET DEFAULT nextval('public.member_level_id_seq'::regclass);


--
-- Name: member_price id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_price ALTER COLUMN id SET DEFAULT nextval('public.member_price_id_seq'::regclass);


--
-- Name: note id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.note ALTER COLUMN id SET DEFAULT nextval('public.note_id_seq'::regclass);


--
-- Name: notification id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification ALTER COLUMN id SET DEFAULT nextval('public.notification_id_seq'::regclass);


--
-- Name: notification_status id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification_status ALTER COLUMN id SET DEFAULT nextval('public.notification_status_id_seq'::regclass);


--
-- Name: order_item id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.order_item ALTER COLUMN id SET DEFAULT nextval('public.order_item_id_seq'::regclass);


--
-- Name: order_operate_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_operate_history ALTER COLUMN id SET DEFAULT nextval('public.order_operate_history_id_seq'::regclass);


--
-- Name: order_return_apply id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_apply ALTER COLUMN id SET DEFAULT nextval('public.order_return_apply_id_seq'::regclass);


--
-- Name: order_return_status id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_status ALTER COLUMN id SET DEFAULT nextval('public.order_return_status_id_seq'::regclass);


--
-- Name: order_shipping_info id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_shipping_info ALTER COLUMN id SET DEFAULT nextval('public.order_shipping_info_id_seq'::regclass);


--
-- Name: order_status id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.order_status ALTER COLUMN id SET DEFAULT nextval('public.order_status_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.order_id_seq'::regclass);


--
-- Name: product id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- Name: product_category id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.product_category ALTER COLUMN id SET DEFAULT nextval('public.product_category_id_seq'::regclass);


--
-- Name: product_comment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_comment ALTER COLUMN id SET DEFAULT nextval('public.product_comment_id_seq'::regclass);


--
-- Name: product_meta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_meta ALTER COLUMN id SET DEFAULT nextval('public.product_meta_id_seq'::regclass);


--
-- Name: product_rating id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rating ALTER COLUMN id SET DEFAULT nextval('public.product_rating_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: shipper id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.shipper ALTER COLUMN id SET DEFAULT nextval('public.shipper_id_seq'::regclass);


--
-- Name: sku id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku ALTER COLUMN id SET DEFAULT nextval('public.sku_id_seq'::regclass);


--
-- Name: sku_attribute id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku_attribute ALTER COLUMN id SET DEFAULT nextval('public.sku_attribute_id_seq'::regclass);


--
-- Name: sku_attribute_category id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku_attribute_category ALTER COLUMN id SET DEFAULT nextval('public.sku_attribute_category_id_seq'::regclass);


--
-- Name: swagger_config id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.swagger_config ALTER COLUMN id SET DEFAULT nextval('public.swagger_config_id_seq'::regclass);


--
-- Name: teacher_available_time id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_available_time ALTER COLUMN id SET DEFAULT nextval('public.teacher_available_time_id_seq'::regclass);


--
-- Name: teacher_meta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_meta ALTER COLUMN id SET DEFAULT nextval('public.teacher_meta_id_seq'::regclass);


--
-- Name: teacher_subscription id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_subscription ALTER COLUMN id SET DEFAULT nextval('public.teacher_subscription_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- Name: user_log id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_log ALTER COLUMN id SET DEFAULT nextval('public.user_log_id_seq'::regclass);


--
-- Name: user_roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles ALTER COLUMN id SET DEFAULT nextval('public.user_roles_id_seq'::regclass);


--
-- Name: user_shipping_preference id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_shipping_preference ALTER COLUMN id SET DEFAULT nextval('public.user_shipping_preference_id_seq'::regclass);


--
-- Name: wish_list id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list ALTER COLUMN id SET DEFAULT nextval('public.wish_list_id_seq'::regclass);


--
-- Name: wish_list_item id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list_item ALTER COLUMN id SET DEFAULT nextval('public.wish_list_item_id_seq'::regclass);


--
-- Data for Name: api_category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.api_category (id, name, created_at, updated_at) FROM stdin;
1	Shipper	2019-05-02 20:32:01.338896	2019-05-02 20:32:01.338896
3	Test put	2019-05-04 12:28:10.106349	2019-05-04 12:28:10.106349
5	testc	2019-05-05 22:22:26.689609	2019-05-05 22:22:26.689609
16	test	2019-05-05 22:41:09.249229	2019-05-05 22:41:09.249229
28	test234	2019-05-05 22:49:54.646388	2019-05-05 22:49:54.646388
29	test123	2019-05-07 21:39:05.221432	2019-05-07 21:39:05.221432
\.


--
-- Data for Name: api_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.api_info (id, action_type, api_category_id, request, response, created_at, updated_at, end_point) FROM stdin;
7	GET	1	\N	\N	2019-05-02 20:58:04.088338	2019-05-02 20:58:04.088338	\N
8	GET	1	\N	\N	2019-05-02 21:33:04.853049	2019-05-02 21:33:04.853049	\N
9	GET	1	\N	\N	2019-05-02 21:33:31.896123	2019-05-02 21:33:31.896123	\N
10	GET	1	\N	\N	2019-05-02 21:34:54.943198	2019-05-02 21:34:54.943198	\N
11	GET	1	\N	\N	2019-05-02 21:36:42.994566	2019-05-02 21:36:42.994566	\N
12	GET	1	\N	\N	2019-05-02 21:36:51.218413	2019-05-02 21:36:51.218413	\N
3	GET	1	\N	\N	2019-05-02 20:52:56.732362	2019-05-02 20:52:56.732362	\N
14	PUT	1	      {\n        "id": 1,\n        "name": "Test Shipper",\n        "contact": "8976556456"\n      }	      {\n        "id": 1,\n        "name": "Test Shipper",\n        "contact": "8976556456"\n      }	2019-05-04 12:31:42.255013	2019-05-04 12:31:42.255013	\N
15	DELETE	1	123	345	2019-05-04 12:32:58.985941	2019-05-04 12:32:58.985941	\N
16	PUT	1	ert	ert	2019-05-04 12:38:21.280496	2019-05-04 12:38:21.280496	\N
17	GET	1	ert	ert	2019-05-04 19:40:09.240773	2019-05-04 19:40:09.240773	\N
18	POST	1	dfg	dfg	2019-05-04 19:42:02.614228	2019-05-04 19:42:02.614228	\N
20	POST	1	\N	\N	2019-05-04 21:30:52.848795	2019-05-04 21:30:52.848795	\N
21	POST	5	{\n\t"apiInfoHeaders": [{\n\t\t"id": 1,\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}]\n}	{\n\t"apiInfoHeaders": [{\n\t\t"id": 1,\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}]\n}	2019-05-06 23:17:03.694267	2019-05-06 23:17:03.694267	/test/234
22	DELETE	5	{\n\t"apiInfoHeaders": [{\n\t\t"id": 1,\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}]\n}	{\n\t"apiInfoHeaders": [{\n\t\t"id": 1,\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}]\n}	2019-05-06 23:17:19.107988	2019-05-06 23:17:19.107988	/test/234/456
23	POST	5	{\n\t"actionType": "GET",\n\t"apiCategoryId": 1,\n\t"apiInfoHeaders": [{\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}],\n\t"request": "",\n\t"response": ""\n}	{\n\t"actionType": "GET",\n\t"apiCategoryId": 1,\n\t"apiInfoHeaders": [{\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}],\n\t"request": "",\n\t"response": ""\n}	2019-05-06 23:24:14.128319	2019-05-06 23:24:14.128319	ert/ert/er/t
24	POST	5	dfg	dfg	2019-05-06 23:28:01.326467	2019-05-06 23:28:01.326467	sdf
25	POST	28	{\n\t"actionType": "GET",\n\t"apiCategoryId": 1,\n\t"apiInfoHeaders": [{\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}],\n\t"request": "",\n\t"response": ""\n}	{\n\t"actionType": "GET",\n\t"apiCategoryId": 1,\n\t"apiInfoHeaders": [{\n\t\t"name": "option",\n\t\t"value": ["test3", "test4", "test5"]\n\t}],\n\t"request": "",\n\t"response": ""\n}	2019-05-06 23:31:32.482448	2019-05-06 23:31:32.482448	asdasd
26	POST	1	asd	asd	2019-05-07 21:41:21.988191	2019-05-07 21:41:21.988191	asd
27	POST	5	d	\N	2019-05-07 21:42:24.694926	2019-05-07 21:42:24.694926	asd
28	PUT	5	asd	asd	2019-05-07 21:43:20.794944	2019-05-07 21:43:20.794944	asd
\.


--
-- Data for Name: api_info_header; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.api_info_header (id, api_info_id, name, value, created_at, updated_at) FROM stdin;
1	3	option	{test3,test4,test5}	2019-05-02 20:52:56.732362	2019-05-02 20:52:56.732362
2	14		{""}	2019-05-04 12:31:42.255013	2019-05-04 12:31:42.255013
3	15		{""}	2019-05-04 12:32:58.985941	2019-05-04 12:32:58.985941
4	16	ert	{ert}	2019-05-04 12:38:21.280496	2019-05-04 12:38:21.280496
5	25	asd	{asd}	2019-05-06 23:31:32.482448	2019-05-06 23:31:32.482448
6	26	asdasd	{asd}	2019-05-07 21:41:21.988191	2019-05-07 21:41:21.988191
7	27	asd	{as}	2019-05-07 21:42:24.694926	2019-05-07 21:42:24.694926
8	28	asd	{asd}	2019-05-07 21:43:20.794944	2019-05-07 21:43:20.794944
\.


--
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.cart (id, user_id, created_at, updated_at) FROM stdin;
1	1	2019-04-13 08:43:27.435392	2019-04-13 08:43:27.435392
\.


--
-- Data for Name: cart_item; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.cart_item (id, cart_id, product_id, quantity, created_at, updated_at, sku_code) FROM stdin;
\.


--
-- Data for Name: class; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.class (id, name, description, unit, unit_price, created_at, updated_at, teacher_id) FROM stdin;
\.


--
-- Data for Name: class_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.class_order (id, user_id, class_id, unit_count, total_price, created_at, updated_at, status_id) FROM stdin;
\.


--
-- Data for Name: currency_rates; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.currency_rates (id, currency_code, base_currency, rate, created_at, updated_at) FROM stdin;
1	HRK	CAD	5.1059525442	2019-12-27 11:57:59.189	2019-12-27 11:57:59.189
2	CHF	CAD	0.745988204600000038	2019-12-27 11:57:59.205	2019-12-27 11:57:59.205
3	MXN	CAD	14.4085859279000008	2019-12-27 11:57:59.214	2019-12-27 11:57:59.214
4	ZAR	CAD	10.7848031819999992	2019-12-27 11:57:59.218	2019-12-27 11:57:59.218
5	INR	CAD	54.1438074337999993	2019-12-27 11:57:59.221	2019-12-27 11:57:59.221
6	THB	CAD	22.9131806337000015	2019-12-27 11:57:59.225	2019-12-27 11:57:59.225
7	CNY	CAD	5.32457824719999984	2019-12-27 11:57:59.228	2019-12-27 11:57:59.228
8	AUD	CAD	1.09854615280000001	2019-12-27 11:57:59.232	2019-12-27 11:57:59.232
9	ILS	CAD	2.63777259640000006	2019-12-27 11:57:59.235	2019-12-27 11:57:59.235
10	KRW	CAD	883.081881771999974	2019-12-27 11:57:59.238	2019-12-27 11:57:59.238
11	JPY	CAD	83.1093128514999933	2019-12-27 11:57:59.241	2019-12-27 11:57:59.241
12	PLN	CAD	2.92127280209999984	2019-12-27 11:57:59.25	2019-12-27 11:57:59.25
13	GBP	CAD	0.586565628899999969	2019-12-27 11:57:59.259	2019-12-27 11:57:59.259
14	IDR	CAD	10626.3886983952998	2019-12-27 11:57:59.268	2019-12-27 11:57:59.268
15	HUF	CAD	227.513372651200001	2019-12-27 11:57:59.278	2019-12-27 11:57:59.278
16	PHP	CAD	38.630503360299997	2019-12-27 11:57:59.288	2019-12-27 11:57:59.288
17	TRY	CAD	4.52571663690000037	2019-12-27 11:57:59.296	2019-12-27 11:57:59.296
18	RUB	CAD	47.1767933068000005	2019-12-27 11:57:59.3	2019-12-27 11:57:59.3
19	HKD	CAD	5.91756960639999985	2019-12-27 11:57:59.303	2019-12-27 11:57:59.303
20	ISK	CAD	92.9913592099999988	2019-12-27 11:57:59.306	2019-12-27 11:57:59.306
21	EUR	CAD	0.685776985299999975	2019-12-27 11:57:59.31	2019-12-27 11:57:59.31
22	DKK	CAD	5.12357701280000022	2019-12-27 11:57:59.314	2019-12-27 11:57:59.314
23	CAD	CAD	1	2019-12-27 11:57:59.318	2019-12-27 11:57:59.318
24	MYR	CAD	3.14305307909999998	2019-12-27 11:57:59.321	2019-12-27 11:57:59.321
25	USD	CAD	0.759840899700000016	2019-12-27 11:57:59.324	2019-12-27 11:57:59.324
26	BGN	CAD	1.34124262790000004	2019-12-27 11:57:59.327	2019-12-27 11:57:59.327
27	NOK	CAD	6.79728432310000041	2019-12-27 11:57:59.331	2019-12-27 11:57:59.331
28	RON	CAD	3.27732821290000009	2019-12-27 11:57:59.334	2019-12-27 11:57:59.334
29	SGD	CAD	1.02983129890000003	2019-12-27 11:57:59.337	2019-12-27 11:57:59.337
30	CZK	CAD	17.4770264709999985	2019-12-27 11:57:59.34	2019-12-27 11:57:59.34
31	SEK	CAD	7.17000411470000021	2019-12-27 11:57:59.343	2019-12-27 11:57:59.343
32	NZD	CAD	1.14634480870000011	2019-12-27 11:57:59.347	2019-12-27 11:57:59.347
33	BRL	CAD	3.10286654780000015	2019-12-27 11:57:59.35	2019-12-27 11:57:59.35
34	HRK	HKD	0.862846216200000016	2019-12-27 11:58:00.771	2019-12-27 11:58:00.771
35	CHF	HKD	0.126063275000000002	2019-12-27 11:58:00.799	2019-12-27 11:58:00.799
36	MXN	HKD	2.4348823733999998	2019-12-27 11:58:00.809	2019-12-27 11:58:00.809
37	ZAR	HKD	1.82250550470000006	2019-12-27 11:58:00.817	2019-12-27 11:58:00.818
38	INR	HKD	9.14966971840000021	2019-12-27 11:58:00.821	2019-12-27 11:58:00.821
39	THB	HKD	3.87205933479999986	2019-12-27 11:58:00.825	2019-12-27 11:58:00.825
40	CNY	HKD	0.899791401099999999	2019-12-27 11:58:00.831	2019-12-27 11:58:00.831
41	AUD	HKD	0.185641441699999993	2019-12-27 11:58:00.834	2019-12-27 11:58:00.834
42	ILS	HKD	0.445752694399999982	2019-12-27 11:58:00.839	2019-12-27 11:58:00.839
43	KRW	HKD	149.23050179629999	2019-12-27 11:58:00.842	2019-12-27 11:58:00.842
44	JPY	HKD	14.0445011008999998	2019-12-27 11:58:00.846	2019-12-27 11:58:00.846
45	PLN	HKD	0.493660910900000027	2019-12-27 11:58:00.849	2019-12-27 11:58:00.849
46	GBP	HKD	0.0991227256999999973	2019-12-27 11:58:00.853	2019-12-27 11:58:00.853
47	IDR	HKD	1795.73531116000004	2019-12-27 11:58:00.856	2019-12-27 11:58:00.856
48	HUF	HKD	38.4470969985000011	2019-12-27 11:58:00.859	2019-12-27 11:58:00.859
49	PHP	HKD	6.52810290880000021	2019-12-27 11:58:00.863	2019-12-27 11:58:00.863
50	TRY	HKD	0.764793139400000044	2019-12-27 11:58:00.866	2019-12-27 11:58:00.867
51	RUB	HKD	7.97232587790000036	2019-12-27 11:58:00.87	2019-12-27 11:58:00.87
52	HKD	HKD	1	2019-12-27 11:58:00.872	2019-12-27 11:58:00.872
53	ISK	HKD	15.7144512689999996	2019-12-27 11:58:00.875	2019-12-27 11:58:00.875
54	EUR	HKD	0.115888283699999997	2019-12-27 11:58:00.88	2019-12-27 11:58:00.88
55	DKK	HKD	0.865824545099999954	2019-12-27 11:58:00.885	2019-12-27 11:58:00.885
56	CAD	HKD	0.168988295300000008	2019-12-27 11:58:00.888	2019-12-27 11:58:00.888
57	MYR	HKD	0.531139181799999971	2019-12-27 11:58:00.89	2019-12-27 11:58:00.89
58	USD	HKD	0.128404218300000011	2019-12-27 11:58:00.893	2019-12-27 11:58:00.893
59	BGN	HKD	0.226654305199999989	2019-12-27 11:58:00.896	2019-12-27 11:58:00.896
60	NOK	HKD	1.14866149030000009	2019-12-27 11:58:00.899	2019-12-27 11:58:00.899
61	RON	HKD	0.553830107799999971	2019-12-27 11:58:00.902	2019-12-27 11:58:00.902
62	SGD	HKD	0.174029435600000004	2019-12-27 11:58:00.905	2019-12-27 11:58:00.905
63	CZK	HKD	2.95341290999999995	2019-12-27 11:58:00.908	2019-12-27 11:58:00.908
64	SEK	HKD	1.21164677249999997	2019-12-27 11:58:00.911	2019-12-27 11:58:00.911
65	NZD	HKD	0.193718854999999995	2019-12-27 11:58:00.915	2019-12-27 11:58:00.915
66	BRL	HKD	0.524348128400000002	2019-12-27 11:58:00.917	2019-12-27 11:58:00.917
67	HRK	ISK	0.054907817099999999	2019-12-27 11:58:01.173	2019-12-27 11:58:01.173
68	CHF	ISK	0.00802212390000000067	2019-12-27 11:58:01.181	2019-12-27 11:58:01.181
69	MXN	ISK	0.154945427699999999	2019-12-27 11:58:01.184	2019-12-27 11:58:01.184
70	ZAR	ISK	0.115976401199999996	2019-12-27 11:58:01.186	2019-12-27 11:58:01.187
71	INR	ISK	0.582245575199999976	2019-12-27 11:58:01.189	2019-12-27 11:58:01.189
72	THB	ISK	0.246401179899999989	2019-12-27 11:58:01.191	2019-12-27 11:58:01.191
73	CNY	ISK	0.0572588496000000016	2019-12-27 11:58:01.194	2019-12-27 11:58:01.194
74	AUD	ISK	0.0118134217999999996	2019-12-27 11:58:01.197	2019-12-27 11:58:01.197
75	ILS	ISK	0.0283657816999999986	2019-12-27 11:58:01.2	2019-12-27 11:58:01.2
76	KRW	ISK	9.49638643069999944	2019-12-27 11:58:01.203	2019-12-27 11:58:01.203
77	JPY	ISK	0.89373156340000004	2019-12-27 11:58:01.205	2019-12-27 11:58:01.205
78	PLN	ISK	0.0314144542999999984	2019-12-27 11:58:01.208	2019-12-27 11:58:01.208
79	GBP	ISK	0.00630774340000000034	2019-12-27 11:58:01.211	2019-12-27 11:58:01.211
80	IDR	ISK	114.272861356899995	2019-12-27 11:58:01.213	2019-12-27 11:58:01.213
81	HUF	ISK	2.44660766960000009	2019-12-27 11:58:01.215	2019-12-27 11:58:01.215
82	PHP	ISK	0.415420353999999992	2019-12-27 11:58:01.217	2019-12-27 11:58:01.218
83	TRY	ISK	0.0486681415999999992	2019-12-27 11:58:01.22	2019-12-27 11:58:01.22
84	RUB	ISK	0.507324483799999948	2019-12-27 11:58:01.222	2019-12-27 11:58:01.222
85	HKD	ISK	0.0636356931999999959	2019-12-27 11:58:01.225	2019-12-27 11:58:01.225
86	ISK	ISK	1	2019-12-27 11:58:01.228	2019-12-27 11:58:01.228
87	EUR	ISK	0.00737463130000000008	2019-12-27 11:58:01.23	2019-12-27 11:58:01.23
88	DKK	ISK	0.0550973451000000003	2019-12-27 11:58:01.232	2019-12-27 11:58:01.232
89	CAD	ISK	0.0107536873000000002	2019-12-27 11:58:01.234	2019-12-27 11:58:01.234
90	MYR	ISK	0.033799410000000002	2019-12-27 11:58:01.236	2019-12-27 11:58:01.236
91	USD	ISK	0.00817109140000000025	2019-12-27 11:58:01.238	2019-12-27 11:58:01.238
92	BGN	ISK	0.0144233038000000004	2019-12-27 11:58:01.24	2019-12-27 11:58:01.24
93	NOK	ISK	0.073095870199999996	2019-12-27 11:58:01.242	2019-12-27 11:58:01.242
94	RON	ISK	0.0352433627999999971	2019-12-27 11:58:01.244	2019-12-27 11:58:01.244
95	SGD	ISK	0.0110744837999999998	2019-12-27 11:58:01.247	2019-12-27 11:58:01.247
96	CZK	ISK	0.187942477899999988	2019-12-27 11:58:01.249	2019-12-27 11:58:01.249
97	SEK	ISK	0.0771039822999999985	2019-12-27 11:58:01.252	2019-12-27 11:58:01.252
98	NZD	ISK	0.0123274335999999998	2019-12-27 11:58:01.254	2019-12-27 11:58:01.254
99	BRL	ISK	0.033367256599999999	2019-12-27 11:58:01.257	2019-12-27 11:58:01.257
100	HRK	PHP	0.132174113700000012	2019-12-27 11:58:01.586	2019-12-27 11:58:01.586
101	CHF	PHP	0.0193108589999999997	2019-12-27 11:58:01.594	2019-12-27 11:58:01.594
102	MXN	PHP	0.372984679799999996	2019-12-27 11:58:01.597	2019-12-27 11:58:01.597
103	ZAR	PHP	0.279178427499999993	2019-12-27 11:58:01.6	2019-12-27 11:58:01.6
104	INR	PHP	1.4015817223	2019-12-27 11:58:01.603	2019-12-27 11:58:01.603
105	THB	PHP	0.593136993799999956	2019-12-27 11:58:01.605	2019-12-27 11:58:01.605
106	CNY	PHP	0.13783351969999999	2019-12-27 11:58:01.608	2019-12-27 11:58:01.608
107	AUD	PHP	0.0284372724999999993	2019-12-27 11:58:01.611	2019-12-27 11:58:01.611
108	ILS	PHP	0.0682821182000000054	2019-12-27 11:58:01.613	2019-12-27 11:58:01.613
109	KRW	PHP	22.8597042481000017	2019-12-27 11:58:01.616	2019-12-27 11:58:01.616
110	JPY	PHP	2.15139088600000017	2019-12-27 11:58:01.618	2019-12-27 11:58:01.618
111	PLN	PHP	0.0756208837000000023	2019-12-27 11:58:01.621	2019-12-27 11:58:01.621
112	GBP	PHP	0.0151840016999999997	2019-12-27 11:58:01.623	2019-12-27 11:58:01.623
113	IDR	PHP	275.07766593880001	2019-12-27 11:58:01.625	2019-12-27 11:58:01.625
114	HUF	PHP	5.88947471200000017	2019-12-27 11:58:01.628	2019-12-27 11:58:01.628
115	PHP	PHP	1	2019-12-27 11:58:01.63	2019-12-27 11:58:01.63
116	TRY	PHP	0.117153964999999999	2019-12-27 11:58:01.632	2019-12-27 11:58:01.632
117	RUB	PHP	1.22123164860000011	2019-12-27 11:58:01.635	2019-12-27 11:58:01.635
118	HKD	PHP	0.153183859700000008	2019-12-27 11:58:01.637	2019-12-27 11:58:01.637
119	ISK	PHP	2.40720029819999981	2019-12-27 11:58:01.639	2019-12-27 11:58:01.639
120	EUR	PHP	0.0177522145999999999	2019-12-27 11:58:01.641	2019-12-27 11:58:01.641
121	DKK	PHP	0.132630345600000005	2019-12-27 11:58:01.643	2019-12-27 11:58:01.643
122	CAD	PHP	0.0258862792999999987	2019-12-27 11:58:01.646	2019-12-27 11:58:01.646
123	MYR	PHP	0.0813619498999999941	2019-12-27 11:58:01.648	2019-12-27 11:58:01.648
124	USD	PHP	0.0196694538000000002	2019-12-27 11:58:01.651	2019-12-27 11:58:01.651
125	BGN	PHP	0.0347197813000000022	2019-12-27 11:58:01.654	2019-12-27 11:58:01.654
126	NOK	PHP	0.175956400600000007	2019-12-27 11:58:01.657	2019-12-27 11:58:01.657
127	RON	PHP	0.0848378335000000011	2019-12-27 11:58:01.659	2019-12-27 11:58:01.659
128	SGD	PHP	0.0266585005999999992	2019-12-27 11:58:01.662	2019-12-27 11:58:01.662
129	CZK	PHP	0.452415188799999979	2019-12-27 11:58:01.664	2019-12-27 11:58:01.664
130	SEK	PHP	0.185604729200000013	2019-12-27 11:58:01.666	2019-12-27 11:58:01.666
131	NZD	PHP	0.0296746019	2019-12-27 11:58:01.669	2019-12-27 11:58:01.669
132	BRL	PHP	0.0803216701000000061	2019-12-27 11:58:01.671	2019-12-27 11:58:01.671
133	HRK	DKK	0.996560124199999953	2019-12-27 11:58:01.931	2019-12-27 11:58:01.931
134	CHF	DKK	0.145599100499999995	2019-12-27 11:58:01.938	2019-12-27 11:58:01.938
135	MXN	DKK	2.81221222829999995	2019-12-27 11:58:01.94	2019-12-27 11:58:01.94
136	ZAR	DKK	2.10493628869999982	2019-12-27 11:58:01.943	2019-12-27 11:58:01.943
137	INR	DKK	10.5675795052999995	2019-12-27 11:58:01.945	2019-12-27 11:58:01.945
138	THB	DKK	4.47210622119999979	2019-12-27 11:58:01.947	2019-12-27 11:58:01.947
139	CNY	DKK	1.03923064570000001	2019-12-27 11:58:01.949	2019-12-27 11:58:01.949
140	AUD	DKK	0.214410001099999997	2019-12-27 11:58:01.951	2019-12-27 11:58:01.951
141	ILS	DKK	0.514830281599999995	2019-12-27 11:58:01.953	2019-12-27 11:58:01.953
142	KRW	DKK	172.356515686899996	2019-12-27 11:58:01.955	2019-12-27 11:58:01.955
143	JPY	DKK	16.2209551344000005	2019-12-27 11:58:01.957	2019-12-27 11:58:01.957
144	PLN	DKK	0.570162758299999961	2019-12-27 11:58:01.959	2019-12-27 11:58:01.959
145	GBP	DKK	0.114483617100000004	2019-12-27 11:58:01.961	2019-12-27 11:58:01.961
146	IDR	DKK	2074.01756076670017	2019-12-27 11:58:01.962	2019-12-27 11:58:01.962
147	HUF	DKK	44.4051825677000025	2019-12-27 11:58:01.964	2019-12-27 11:58:01.964
148	PHP	DKK	7.53975265019999963	2019-12-27 11:58:01.966	2019-12-27 11:58:01.966
149	TRY	DKK	0.883311917799999957	2019-12-27 11:58:01.968	2019-12-27 11:58:01.968
150	RUB	DKK	9.20778455940000029	2019-12-27 11:58:01.97	2019-12-27 11:58:01.97
151	HKD	DKK	1.15496841200000011	2019-12-27 11:58:01.972	2019-12-27 11:58:01.972
152	ISK	DKK	18.1496948280999995	2019-12-27 11:58:01.973	2019-12-27 11:58:01.973
153	EUR	DKK	0.133847306999999999	2019-12-27 11:58:01.975	2019-12-27 11:58:01.975
154	DKK	DKK	1	2019-12-27 11:58:01.977	2019-12-27 11:58:01.977
155	CAD	DKK	0.195176143100000005	2019-12-27 11:58:01.979	2019-12-27 11:58:01.979
156	MYR	DKK	0.613448977400000039	2019-12-27 11:58:01.981	2019-12-27 11:58:01.981
157	USD	DKK	0.148302816100000012	2019-12-27 11:58:01.983	2019-12-27 11:58:01.983
158	BGN	DKK	0.26177856300000002	2019-12-27 11:58:01.985	2019-12-27 11:58:01.985
159	NOK	DKK	1.32666773739999999	2019-12-27 11:58:01.987	2019-12-27 11:58:01.987
160	RON	DKK	0.639656280099999974	2019-12-27 11:58:01.989	2019-12-27 11:58:01.989
161	SGD	DKK	0.200998500899999988	2019-12-27 11:58:01.991	2019-12-27 11:58:01.992
162	CZK	DKK	3.41109861870000008	2019-12-27 11:58:01.994	2019-12-27 11:58:01.994
163	SEK	DKK	1.39941374880000002	2019-12-27 11:58:01.996	2019-12-27 11:58:01.996
164	NZD	DKK	0.223739158399999999	2019-12-27 11:58:01.998	2019-12-27 11:58:01.998
165	BRL	DKK	0.605605525199999994	2019-12-27 11:58:02	2019-12-27 11:58:02
166	HRK	HUF	0.0224424283000000009	2019-12-27 11:58:02.304	2019-12-27 11:58:02.304
167	CHF	HUF	0.00327887630000000012	2019-12-27 11:58:02.314	2019-12-27 11:58:02.314
168	MXN	HUF	0.0633307210000000065	2019-12-27 11:58:02.323	2019-12-27 11:58:02.323
169	ZAR	HUF	0.0474029419000000021	2019-12-27 11:58:02.332	2019-12-27 11:58:02.332
170	INR	HUF	0.237980769199999997	2019-12-27 11:58:02.34	2019-12-27 11:58:02.34
171	THB	HUF	0.100711357599999995	2019-12-27 11:58:02.342	2019-12-27 11:58:02.342
172	CNY	HUF	0.0234033639000000016	2019-12-27 11:58:02.344	2019-12-27 11:58:02.344
173	AUD	HUF	0.00482849050000000012	2019-12-27 11:58:02.346	2019-12-27 11:58:02.346
174	ILS	HUF	0.0115939232999999996	2019-12-27 11:58:02.349	2019-12-27 11:58:02.349
175	KRW	HUF	3.88145044610000012	2019-12-27 11:58:02.35	2019-12-27 11:58:02.35
176	JPY	HUF	0.365294188600000014	2019-12-27 11:58:02.352	2019-12-27 11:58:02.352
177	PLN	HUF	0.0128400048000000006	2019-12-27 11:58:02.355	2019-12-27 11:58:02.355
178	GBP	HUF	0.00257815889999999997	2019-12-27 11:58:02.357	2019-12-27 11:58:02.357
179	IDR	HUF	46.7066554135999965	2019-12-27 11:58:02.359	2019-12-27 11:58:02.359
180	HUF	HUF	1	2019-12-27 11:58:02.36	2019-12-27 11:58:02.36
181	PHP	HUF	0.169794429699999999	2019-12-27 11:58:02.362	2019-12-27 11:58:02.362
182	TRY	HUF	0.0198920907000000001	2019-12-27 11:58:02.364	2019-12-27 11:58:02.364
183	RUB	HUF	0.207358331300000004	2019-12-27 11:58:02.366	2019-12-27 11:58:02.366
184	HKD	HUF	0.0260097661000000015	2019-12-27 11:58:02.367	2019-12-27 11:58:02.367
185	ISK	HUF	0.408729201799999997	2019-12-27 11:58:02.369	2019-12-27 11:58:02.369
186	EUR	HUF	0.00301422720000000003	2019-12-27 11:58:02.371	2019-12-27 11:58:02.371
187	DKK	HUF	0.0225198939000000008	2019-12-27 11:58:02.373	2019-12-27 11:58:02.373
188	CAD	HUF	0.00439534599999999963	2019-12-27 11:58:02.375	2019-12-27 11:58:02.375
189	MYR	HUF	0.0138148058999999993	2019-12-27 11:58:02.376	2019-12-27 11:58:02.376
190	USD	HUF	0.00333976370000000017	2019-12-27 11:58:02.378	2019-12-27 11:58:02.378
191	BGN	HUF	0.0058952255000000002	2019-12-27 11:58:02.38	2019-12-27 11:58:02.38
192	NOK	HUF	0.0298764166999999983	2019-12-27 11:58:02.382	2019-12-27 11:58:02.382
193	RON	HUF	0.0144049916	2019-12-27 11:58:02.384	2019-12-27 11:58:02.384
194	SGD	HUF	0.00452646489999999988	2019-12-27 11:58:02.386	2019-12-27 11:58:02.386
195	CZK	HUF	0.0768175789999999969	2019-12-27 11:58:02.387	2019-12-27 11:58:02.387
196	SEK	HUF	0.0315146491000000001	2019-12-27 11:58:02.389	2019-12-27 11:58:02.389
197	NZD	HUF	0.00503858210000000025	2019-12-27 11:58:02.39	2019-12-27 11:58:02.39
198	BRL	HUF	0.0136381721999999997	2019-12-27 11:58:02.392	2019-12-27 11:58:02.392
199	HRK	CZK	0.292152246399999982	2019-12-27 11:58:02.661	2019-12-27 11:58:02.661
200	CHF	CZK	0.0426839316999999968	2019-12-27 11:58:02.67	2019-12-27 11:58:02.67
201	MXN	CZK	0.824430056900000041	2019-12-27 11:58:02.672	2019-12-27 11:58:02.672
202	ZAR	CZK	0.617084559499999963	2019-12-27 11:58:02.674	2019-12-27 11:58:02.674
203	INR	CZK	3.09799882280000016	2019-12-27 11:58:02.676	2019-12-27 11:58:02.676
204	THB	CZK	1.31104571319999996	2019-12-27 11:58:02.679	2019-12-27 11:58:02.679
205	CNY	CZK	0.304661565600000017	2019-12-27 11:58:02.681	2019-12-27 11:58:02.681
206	AUD	CZK	0.0628565823000000051	2019-12-27 11:58:02.683	2019-12-27 11:58:02.683
207	ILS	CZK	0.1509279969	2019-12-27 11:58:02.684	2019-12-27 11:58:02.684
208	KRW	CZK	50.5281538159999997	2019-12-27 11:58:02.686	2019-12-27 11:58:02.686
209	JPY	CZK	4.75534628209999966	2019-12-27 11:58:02.688	2019-12-27 11:58:02.688
210	PLN	CZK	0.167149303500000013	2019-12-27 11:58:02.69	2019-12-27 11:58:02.69
211	GBP	CZK	0.0335620953999999985	2019-12-27 11:58:02.693	2019-12-27 11:58:02.693
212	IDR	CZK	608.020404159300028	2019-12-27 11:58:02.695	2019-12-27 11:58:02.695
213	HUF	CZK	13.0178536394000002	2019-12-27 11:58:02.697	2019-12-27 11:58:02.697
214	PHP	CZK	2.21035903470000017	2019-12-27 11:58:02.699	2019-12-27 11:58:02.699
215	TRY	CZK	0.258952324899999975	2019-12-27 11:58:02.7	2019-12-27 11:58:02.7
216	RUB	CZK	2.6993604081	2019-12-27 11:58:02.702	2019-12-27 11:58:02.702
217	HKD	CZK	0.338591328199999986	2019-12-27 11:58:02.704	2019-12-27 11:58:02.704
218	ISK	CZK	5.32077692759999987	2019-12-27 11:58:02.705	2019-12-27 11:58:02.705
219	EUR	CZK	0.0392387678999999989	2019-12-27 11:58:02.707	2019-12-27 11:58:02.707
220	DKK	CZK	0.293160682800000016	2019-12-27 11:58:02.709	2019-12-27 11:58:02.709
221	CAD	CZK	0.0572179713999999978	2019-12-27 11:58:02.71	2019-12-27 11:58:02.71
222	MYR	CZK	0.179839121099999999	2019-12-27 11:58:02.712	2019-12-27 11:58:02.712
223	USD	CZK	0.0434765547999999974	2019-12-27 11:58:02.714	2019-12-27 11:58:02.714
224	BGN	CZK	0.0767431823000000041	2019-12-27 11:58:02.716	2019-12-27 11:58:02.716
225	NOK	CZK	0.388926819699999982	2019-12-27 11:58:02.718	2019-12-27 11:58:02.718
226	RON	CZK	0.187522071799999995	2019-12-27 11:58:02.72	2019-12-27 11:58:02.72
227	SGD	CZK	0.0589248578000000009	2019-12-27 11:58:02.722	2019-12-27 11:58:02.722
228	CZK	CZK	1	2019-12-27 11:58:02.724	2019-12-27 11:58:02.724
229	SEK	CZK	0.410253090099999995	2019-12-27 11:58:02.726	2019-12-27 11:58:02.726
230	NZD	CZK	0.0655915244000000036	2019-12-27 11:58:02.728	2019-12-27 11:58:02.728
231	BRL	CZK	0.177539729300000004	2019-12-27 11:58:02.73	2019-12-27 11:58:02.73
232	HRK	GBP	8.70482737660000083	2019-12-27 11:58:02.991	2019-12-27 11:58:02.991
233	CHF	GBP	1.27178983549999991	2019-12-27 11:58:02.998	2019-12-27 11:58:02.998
234	MXN	GBP	24.5643202038999995	2019-12-27 11:58:03	2019-12-27 11:58:03
235	ZAR	GBP	18.3863538049999988	2019-12-27 11:58:03.002	2019-12-27 11:58:03.002
236	INR	GBP	92.3064782013999974	2019-12-27 11:58:03.004	2019-12-27 11:58:03.004
237	THB	GBP	39.0632855154999987	2019-12-27 11:58:03.006	2019-12-27 11:58:03.006
238	CNY	GBP	9.07754901620000076	2019-12-27 11:58:03.008	2019-12-27 11:58:03.008
239	AUD	GBP	1.8728443991999999	2019-12-27 11:58:03.01	2019-12-27 11:58:03.01
240	ILS	GBP	4.49697777470000037	2019-12-27 11:58:03.012	2019-12-27 11:58:03.012
241	KRW	GBP	1505.5124922545001	2019-12-27 11:58:03.014	2019-12-27 11:58:03.014
242	JPY	GBP	141.688003460700003	2019-12-27 11:58:03.017	2019-12-27 11:58:03.017
243	PLN	GBP	4.98030000119999983	2019-12-27 11:58:03.019	2019-12-27 11:58:03.019
244	GBP	GBP	1	2019-12-27 11:58:03.021	2019-12-27 11:58:03.021
245	IDR	GBP	18116.282604374901	2019-12-27 11:58:03.023	2019-12-27 11:58:03.023
246	HUF	GBP	387.873686179599986	2019-12-27 11:58:03.025	2019-12-27 11:58:03.025
247	PHP	GBP	65.8587913437000054	2019-12-27 11:58:03.027	2019-12-27 11:58:03.027
248	TRY	GBP	7.7156185331999998	2019-12-27 11:58:03.029	2019-12-27 11:58:03.029
249	RUB	GBP	80.4288403305999964	2019-12-27 11:58:03.031	2019-12-27 11:58:03.031
250	HKD	GBP	10.0885038523000006	2019-12-27 11:58:03.033	2019-12-27 11:58:03.033
251	ISK	GBP	158.535302164099988	2019-12-27 11:58:03.035	2019-12-27 11:58:03.035
252	EUR	GBP	1.16913939650000009	2019-12-27 11:58:03.037	2019-12-27 11:58:03.037
253	DKK	GBP	8.7348742590999997	2019-12-27 11:58:03.04	2019-12-27 11:58:03.04
254	CAD	GBP	1.70483906800000007	2019-12-27 11:58:03.042	2019-12-27 11:58:03.042
255	MYR	GBP	5.35839968199999994	2019-12-27 11:58:03.045	2019-12-27 11:58:03.045
256	USD	GBP	1.29540645130000009	2019-12-27 11:58:03.048	2019-12-27 11:58:03.048
257	BGN	GBP	2.28660283169999978	2019-12-27 11:58:03.05	2019-12-27 11:58:03.05
258	NOK	GBP	11.5882758701000004	2019-12-27 11:58:03.053	2019-12-27 11:58:03.053
259	RON	GBP	5.58731717579999998	2019-12-27 11:58:03.055	2019-12-27 11:58:03.055
260	SGD	GBP	1.75569663170000001	2019-12-27 11:58:03.057	2019-12-27 11:58:03.057
261	CZK	GBP	29.7955175196000006	2019-12-27 11:58:03.059	2019-12-27 11:58:03.059
262	SEK	GBP	12.2237031321000007	2019-12-27 11:58:03.061	2019-12-27 11:58:03.061
263	NZD	GBP	1.95433341520000003	2019-12-27 11:58:03.063	2019-12-27 11:58:03.063
264	BRL	GBP	5.28988811339999998	2019-12-27 11:58:03.065	2019-12-27 11:58:03.065
265	HRK	RON	1.55796191670000006	2019-12-27 11:58:03.326	2019-12-27 11:58:03.326
266	CHF	RON	0.227620841200000007	2019-12-27 11:58:03.329	2019-12-27 11:58:03.329
267	MXN	RON	4.39644277050000021	2019-12-27 11:58:03.331	2019-12-27 11:58:03.331
268	ZAR	RON	3.29073027829999987	2019-12-27 11:58:03.333	2019-12-27 11:58:03.333
269	INR	RON	16.5207156308999998	2019-12-27 11:58:03.335	2019-12-27 11:58:03.335
270	THB	RON	6.99142079930000016	2019-12-27 11:58:03.336	2019-12-27 11:58:03.336
271	CNY	RON	1.62467043309999992	2019-12-27 11:58:03.338	2019-12-27 11:58:03.338
272	AUD	RON	0.33519564759999998	2019-12-27 11:58:03.34	2019-12-27 11:58:03.34
273	ILS	RON	0.804854572099999999	2019-12-27 11:58:03.342	2019-12-27 11:58:03.342
274	KRW	RON	269.451768152300019	2019-12-27 11:58:03.344	2019-12-27 11:58:03.344
275	JPY	RON	25.3588616864999992	2019-12-27 11:58:03.345	2019-12-27 11:58:03.345
276	PLN	RON	0.891358024700000029	2019-12-27 11:58:03.347	2019-12-27 11:58:03.347
277	GBP	RON	0.178976773400000011	2019-12-27 11:58:03.348	2019-12-27 11:58:03.348
278	IDR	RON	3242.3938062356001	2019-12-27 11:58:03.349	2019-12-27 11:58:03.349
279	HUF	RON	69.4203808328000065	2019-12-27 11:58:03.351	2019-12-27 11:58:03.351
280	PHP	RON	11.7871939736000009	2019-12-27 11:58:03.352	2019-12-27 11:58:03.352
281	TRY	RON	1.38091650970000002	2019-12-27 11:58:03.354	2019-12-27 11:58:03.354
282	RUB	RON	14.3948943293999996	2019-12-27 11:58:03.355	2019-12-27 11:58:03.355
283	HKD	RON	1.80560786780000004	2019-12-27 11:58:03.357	2019-12-27 11:58:03.357
284	ISK	RON	28.374136848700001	2019-12-27 11:58:03.358	2019-12-27 11:58:03.358
285	EUR	RON	0.209248796799999998	2019-12-27 11:58:03.36	2019-12-27 11:58:03.36
286	DKK	RON	1.56333961079999995	2019-12-27 11:58:03.361	2019-12-27 11:58:03.361
287	CAD	RON	0.305126595499999986	2019-12-27 11:58:03.362	2019-12-27 11:58:03.362
288	MYR	RON	0.959029085599999997	2019-12-27 11:58:03.364	2019-12-27 11:58:03.364
289	USD	RON	0.231847666899999999	2019-12-27 11:58:03.365	2019-12-27 11:58:03.365
290	BGN	RON	0.409248796799999981	2019-12-27 11:58:03.367	2019-12-27 11:58:03.367
291	NOK	RON	2.07403222429999978	2019-12-27 11:58:03.368	2019-12-27 11:58:03.368
292	RON	RON	1	2019-12-27 11:58:03.369	2019-12-27 11:58:03.369
293	SGD	RON	0.314228918199999985	2019-12-27 11:58:03.371	2019-12-27 11:58:03.371
294	CZK	RON	5.33270558690000041	2019-12-27 11:58:03.373	2019-12-27 11:58:03.373
295	SEK	RON	2.18775894540000015	2019-12-27 11:58:03.376	2019-12-27 11:58:03.376
296	NZD	RON	0.349780288800000005	2019-12-27 11:58:03.379	2019-12-27 11:58:03.379
297	BRL	RON	0.946767106100000033	2019-12-27 11:58:03.381	2019-12-27 11:58:03.381
298	HRK	SEK	0.712126863900000018	2019-12-27 11:58:03.637	2019-12-27 11:58:03.637
299	CHF	SEK	0.104042925600000002	2019-12-27 11:58:03.645	2019-12-27 11:58:03.645
300	MXN	SEK	2.00956452710000022	2019-12-27 11:58:03.647	2019-12-27 11:58:03.647
301	ZAR	SEK	1.50415578699999997	2019-12-27 11:58:03.648	2019-12-27 11:58:03.648
302	INR	SEK	7.55143324440000008	2019-12-27 11:58:03.65	2019-12-27 11:58:03.65
303	THB	SEK	3.19569978859999981	2019-12-27 11:58:03.652	2019-12-27 11:58:03.652
304	CNY	SEK	0.742618576200000047	2019-12-27 11:58:03.653	2019-12-27 11:58:03.653
305	AUD	SEK	0.153214159300000013	2019-12-27 11:58:03.655	2019-12-27 11:58:03.655
306	ILS	SEK	0.367889969699999986	2019-12-27 11:58:03.657	2019-12-27 11:58:03.657
307	KRW	SEK	123.163371687099996	2019-12-27 11:58:03.659	2019-12-27 11:58:03.659
308	JPY	SEK	11.5912503705999992	2019-12-27 11:58:03.66	2019-12-27 11:58:03.66
309	PLN	SEK	0.407429724599999987	2019-12-27 11:58:03.662	2019-12-27 11:58:03.662
310	GBP	SEK	0.0818082695000000026	2019-12-27 11:58:03.664	2019-12-27 11:58:03.664
311	IDR	SEK	1482.06172945780008	2019-12-27 11:58:03.665	2019-12-27 11:58:03.665
312	HUF	SEK	31.7312750470999987	2019-12-27 11:58:03.667	2019-12-27 11:58:03.667
313	PHP	SEK	5.3877937505000002	2019-12-27 11:58:03.668	2019-12-27 11:58:03.668
314	TRY	SEK	0.63120140020000004	2019-12-27 11:58:03.67	2019-12-27 11:58:03.67
315	RUB	SEK	6.57974424449999962	2019-12-27 11:58:03.672	2019-12-27 11:58:03.672
316	HKD	SEK	0.825323041900000054	2019-12-27 11:58:03.674	2019-12-27 11:58:03.674
317	ISK	SEK	12.9694987230999992	2019-12-27 11:58:03.676	2019-12-27 11:58:03.676
318	EUR	SEK	0.0956452708000000013	2019-12-27 11:58:03.678	2019-12-27 11:58:03.678
319	DKK	SEK	0.714584947299999995	2019-12-27 11:58:03.68	2019-12-27 11:58:03.68
320	CAD	SEK	0.139469933900000009	2019-12-27 11:58:03.682	2019-12-27 11:58:03.682
321	MYR	SEK	0.438361405199999998	2019-12-27 11:58:03.684	2019-12-27 11:58:03.684
322	USD	SEK	0.105974960100000001	2019-12-27 11:58:03.686	2019-12-27 11:58:03.686
323	BGN	SEK	0.187063020699999999	2019-12-27 11:58:03.688	2019-12-27 11:58:03.688
324	NOK	SEK	0.948016795300000048	2019-12-27 11:58:03.689	2019-12-27 11:58:03.689
325	RON	SEK	0.457088749199999977	2019-12-27 11:58:03.691	2019-12-27 11:58:03.691
326	SGD	SEK	0.143630503200000009	2019-12-27 11:58:03.692	2019-12-27 11:58:03.692
327	CZK	SEK	2.43751972680000017	2019-12-27 11:58:03.694	2019-12-27 11:58:03.694
328	SEK	SEK	1	2019-12-27 11:58:03.695	2019-12-27 11:58:03.695
329	NZD	SEK	0.159880634699999996	2019-12-27 11:58:03.697	2019-12-27 11:58:03.697
330	BRL	SEK	0.432756592399999973	2019-12-27 11:58:03.699	2019-12-27 11:58:03.699
331	HRK	IDR	0.000480497400000000005	2019-12-27 11:58:03.955	2019-12-27 11:58:03.955
332	CHF	IDR	7.02014999999999952e-05	2019-12-27 11:58:03.958	2019-12-27 11:58:03.958
333	MXN	IDR	0.00135592500000000001	2019-12-27 11:58:03.96	2019-12-27 11:58:03.96
334	ZAR	IDR	0.00101490770000000003	2019-12-27 11:58:03.962	2019-12-27 11:58:03.962
335	INR	IDR	0.00509522179999999988	2019-12-27 11:58:03.964	2019-12-27 11:58:03.964
336	THB	IDR	0.00215625279999999983	2019-12-27 11:58:03.966	2019-12-27 11:58:03.966
337	CNY	IDR	0.000501071299999999954	2019-12-27 11:58:03.968	2019-12-27 11:58:03.968
338	AUD	IDR	0.000103379099999999994	2019-12-27 11:58:03.97	2019-12-27 11:58:03.97
339	ILS	IDR	0.000248228499999999977	2019-12-27 11:58:03.972	2019-12-27 11:58:03.972
340	KRW	IDR	0.0831027272999999983	2019-12-27 11:58:03.974	2019-12-27 11:58:03.974
341	JPY	IDR	0.00782103079999999992	2019-12-27 11:58:03.975	2019-12-27 11:58:03.975
342	PLN	IDR	0.00027490739999999999	2019-12-27 11:58:03.978	2019-12-27 11:58:03.978
343	GBP	IDR	5.51989999999999979e-05	2019-12-27 11:58:03.98	2019-12-27 11:58:03.98
344	IDR	IDR	1	2019-12-27 11:58:03.982	2019-12-27 11:58:03.982
345	HUF	IDR	0.0214102250000000015	2019-12-27 11:58:03.983	2019-12-27 11:58:03.984
346	PHP	IDR	0.00363533690000000017	2019-12-27 11:58:03.985	2019-12-27 11:58:03.985
347	TRY	IDR	0.000425894099999999992	2019-12-27 11:58:03.987	2019-12-27 11:58:03.987
348	RUB	IDR	0.00443958850000000012	2019-12-27 11:58:03.989	2019-12-27 11:58:03.989
349	HKD	IDR	0.000556874900000000044	2019-12-27 11:58:03.991	2019-12-27 11:58:03.991
350	ISK	IDR	0.00875098420000000078	2019-12-27 11:58:03.993	2019-12-27 11:58:03.993
351	EUR	IDR	6.45352999999999986e-05	2019-12-27 11:58:03.995	2019-12-27 11:58:03.995
352	DKK	IDR	0.000482156000000000023	2019-12-27 11:58:03.996	2019-12-27 11:58:03.996
353	CAD	IDR	9.41053000000000018e-05	2019-12-27 11:58:03.998	2019-12-27 11:58:03.998
354	MYR	IDR	0.000295778099999999993	2019-12-27 11:58:04	2019-12-27 11:58:04
355	USD	IDR	7.15050999999999941e-05	2019-12-27 11:58:04.002	2019-12-27 11:58:04.002
356	BGN	IDR	0.000126218099999999997	2019-12-27 11:58:04.003	2019-12-27 11:58:04.003
357	NOK	IDR	0.000639660799999999959	2019-12-27 11:58:04.005	2019-12-27 11:58:04.005
358	RON	IDR	0.000308414099999999999	2019-12-27 11:58:04.007	2019-12-27 11:58:04.007
359	SGD	IDR	9.69125999999999977e-05	2019-12-27 11:58:04.009	2019-12-27 11:58:04.009
360	CZK	IDR	0.00164468160000000004	2019-12-27 11:58:04.011	2019-12-27 11:58:04.011
361	SEK	IDR	0.000674735700000000005	2019-12-27 11:58:04.013	2019-12-27 11:58:04.013
362	NZD	IDR	0.000107877199999999998	2019-12-27 11:58:04.015	2019-12-27 11:58:04.015
363	BRL	IDR	0.000291996299999999981	2019-12-27 11:58:04.017	2019-12-27 11:58:04.017
364	HRK	INR	0.0943035368999999984	2019-12-27 11:58:04.277	2019-12-27 11:58:04.277
365	CHF	INR	0.0137779044000000007	2019-12-27 11:58:04.28	2019-12-27 11:58:04.28
366	MXN	INR	0.266116969099999989	2019-12-27 11:58:04.282	2019-12-27 11:58:04.282
367	ZAR	INR	0.199188119400000002	2019-12-27 11:58:04.284	2019-12-27 11:58:04.284
368	INR	INR	1	2019-12-27 11:58:04.285	2019-12-27 11:58:04.285
369	THB	INR	0.4231911592	2019-12-27 11:58:04.287	2019-12-27 11:58:04.287
370	CNY	INR	0.0983414078000000025	2019-12-27 11:58:04.289	2019-12-27 11:58:04.289
371	AUD	INR	0.0202894144999999984	2019-12-27 11:58:04.29	2019-12-27 11:58:04.29
372	ILS	INR	0.0487179000000000015	2019-12-27 11:58:04.292	2019-12-27 11:58:04.292
373	KRW	INR	16.3099331877000004	2019-12-27 11:58:04.294	2019-12-27 11:58:04.294
374	JPY	INR	1.53497356010000008	2019-12-27 11:58:04.296	2019-12-27 11:58:04.296
375	PLN	INR	0.0539539596999999982	2019-12-27 11:58:04.297	2019-12-27 11:58:04.297
376	GBP	INR	0.0108334758000000007	2019-12-27 11:58:04.299	2019-12-27 11:58:04.299
377	IDR	INR	196.262309616500005	2019-12-27 11:58:04.3	2019-12-27 11:58:04.3
378	HUF	INR	4.20202020199999993	2019-12-27 11:58:04.302	2019-12-27 11:58:04.302
379	PHP	INR	0.713479623799999962	2019-12-27 11:58:04.303	2019-12-27 11:58:04.303
380	TRY	INR	0.0835869667999999955	2019-12-27 11:58:04.305	2019-12-27 11:58:04.305
381	RUB	INR	0.871323897300000039	2019-12-27 11:58:04.306	2019-12-27 11:58:04.306
382	HKD	INR	0.109293562600000005	2019-12-27 11:58:04.309	2019-12-27 11:58:04.309
383	ISK	INR	1.71748836329999999	2019-12-27 11:58:04.31	2019-12-27 11:58:04.31
384	EUR	INR	0.0126658433999999997	2019-12-27 11:58:04.312	2019-12-27 11:58:04.312
385	DKK	INR	0.0946290491000000011	2019-12-27 11:58:04.314	2019-12-27 11:58:04.314
386	CAD	INR	0.0184693327999999983	2019-12-27 11:58:04.316	2019-12-27 11:58:04.316
387	MYR	INR	0.0580500934000000024	2019-12-27 11:58:04.318	2019-12-27 11:58:04.318
388	USD	INR	0.0140337545000000005	2019-12-27 11:58:04.319	2019-12-27 11:58:04.319
389	BGN	INR	0.0247718565000000016	2019-12-27 11:58:04.321	2019-12-27 11:58:04.321
390	NOK	INR	0.125541306500000005	2019-12-27 11:58:04.322	2019-12-27 11:58:04.322
391	RON	INR	0.0605300655000000007	2019-12-27 11:58:04.323	2019-12-27 11:58:04.324
392	SGD	INR	0.0190202969999999985	2019-12-27 11:58:04.325	2019-12-27 11:58:04.325
393	CZK	INR	0.322789018699999986	2019-12-27 11:58:04.326	2019-12-27 11:58:04.326
394	SEK	INR	0.132425192400000002	2019-12-27 11:58:04.327	2019-12-27 11:58:04.327
395	NZD	INR	0.0211722238000000007	2019-12-27 11:58:04.329	2019-12-27 11:58:04.329
396	BRL	INR	0.0573078750000000012	2019-12-27 11:58:04.33	2019-12-27 11:58:04.33
397	HRK	BRL	1.64555982849999993	2019-12-27 11:58:06.605	2019-12-27 11:58:06.605
398	CHF	BRL	0.240419042599999994	2019-12-27 11:58:06.612	2019-12-27 11:58:06.612
399	MXN	BRL	4.64363700659999967	2019-12-27 11:58:06.615	2019-12-27 11:58:06.615
400	ZAR	BRL	3.47575476289999985	2019-12-27 11:58:06.617	2019-12-27 11:58:06.617
401	INR	BRL	17.4496088052000005	2019-12-27 11:58:06.619	2019-12-27 11:58:06.619
402	THB	BRL	7.38452017859999987	2019-12-27 11:58:06.621	2019-12-27 11:58:06.621
403	CNY	BRL	1.7160190956000001	2019-12-27 11:58:06.623	2019-12-27 11:58:06.623
404	AUD	BRL	0.354042346299999977	2019-12-27 11:58:06.625	2019-12-27 11:58:06.625
405	ILS	BRL	0.850108296900000004	2019-12-27 11:58:06.628	2019-12-27 11:58:06.628
406	KRW	BRL	284.601953763899985	2019-12-27 11:58:06.63	2019-12-27 11:58:06.63
407	JPY	BRL	26.7846881492000009	2019-12-27 11:58:06.632	2019-12-27 11:58:06.632
408	PLN	BRL	0.94147548950000004	2019-12-27 11:58:06.635	2019-12-27 11:58:06.635
409	GBP	BRL	0.189039915100000011	2019-12-27 11:58:06.637	2019-12-27 11:58:06.637
410	IDR	BRL	3424.70052601330008	2019-12-27 11:58:06.639	2019-12-27 11:58:06.639
411	HUF	BRL	73.3236087167999955	2019-12-27 11:58:06.641	2019-12-27 11:58:06.641
412	PHP	BRL	12.4499403262000001	2019-12-27 11:58:06.643	2019-12-27 11:58:06.643
413	TRY	BRL	1.45855987269999998	2019-12-27 11:58:06.646	2019-12-27 11:58:06.646
414	RUB	BRL	15.2042611502000007	2019-12-27 11:58:06.648	2019-12-27 11:58:06.648
415	HKD	BRL	1.90712991200000004	2019-12-27 11:58:06.65	2019-12-27 11:58:06.65
416	ISK	BRL	29.9695000663000002	2019-12-27 11:58:06.652	2019-12-27 11:58:06.652
417	EUR	BRL	0.221014012300000007	2019-12-27 11:58:06.654	2019-12-27 11:58:06.654
418	DKK	BRL	1.65123988859999993	2019-12-27 11:58:06.656	2019-12-27 11:58:06.656
419	CAD	BRL	0.322282632700000016	2019-12-27 11:58:06.658	2019-12-27 11:58:06.658
420	MYR	BRL	1.01295142109999992	2019-12-27 11:58:06.661	2019-12-27 11:58:06.661
421	USD	BRL	0.244883525600000013	2019-12-27 11:58:06.663	2019-12-27 11:58:06.663
422	BGN	BRL	0.432259205199999996	2019-12-27 11:58:06.664	2019-12-27 11:58:06.664
423	NOK	BRL	2.19064668700000009	2019-12-27 11:58:06.666	2019-12-27 11:58:06.666
424	RON	BRL	1.05622596470000008	2019-12-27 11:58:06.668	2019-12-27 11:58:06.668
425	SGD	BRL	0.331896742299999992	2019-12-27 11:58:06.67	2019-12-27 11:58:06.67
426	CZK	BRL	5.63254210319999959	2019-12-27 11:58:06.671	2019-12-27 11:58:06.671
427	SEK	BRL	2.31076780270000004	2019-12-27 11:58:06.673	2019-12-27 11:58:06.673
428	NZD	BRL	0.369447022899999977	2019-12-27 11:58:06.675	2019-12-27 11:58:06.675
429	BRL	BRL	1	2019-12-27 11:58:06.676	2019-12-27 11:58:06.676
430	HRK	RUB	0.108230173900000004	2019-12-27 11:58:07.013	2019-12-27 11:58:07.013
431	CHF	RUB	0.0158126094000000002	2019-12-27 11:58:07.02	2019-12-27 11:58:07.02
432	MXN	RUB	0.305416814500000022	2019-12-27 11:58:07.022	2019-12-27 11:58:07.022
433	ZAR	RUB	0.228603989899999999	2019-12-27 11:58:07.024	2019-12-27 11:58:07.024
434	INR	RUB	1.14767884040000001	2019-12-27 11:58:07.025	2019-12-27 11:58:07.025
435	THB	RUB	0.485687538899999993	2019-12-27 11:58:07.027	2019-12-27 11:58:07.027
436	CNY	RUB	0.112864352900000006	2019-12-27 11:58:07.028	2019-12-27 11:58:07.028
437	AUD	RUB	0.0232857316999999994	2019-12-27 11:58:07.03	2019-12-27 11:58:07.03
438	ILS	RUB	0.0559125030000000023	2019-12-27 11:58:07.031	2019-12-27 11:58:07.031
439	KRW	RUB	18.718565207000001	2019-12-27 11:58:07.032	2019-12-27 11:58:07.032
440	JPY	RUB	1.76165667540000004	2019-12-27 11:58:07.034	2019-12-27 11:58:07.034
441	PLN	RUB	0.0619218179000000021	2019-12-27 11:58:07.035	2019-12-27 11:58:07.035
442	GBP	RUB	0.0124333510000000005	2019-12-27 11:58:07.036	2019-12-27 11:58:07.036
443	IDR	RUB	225.246099905199998	2019-12-27 11:58:07.038	2019-12-27 11:58:07.038
444	HUF	RUB	4.82256967260000025	2019-12-27 11:58:07.039	2019-12-27 11:58:07.039
445	PHP	RUB	0.818845467299999963	2019-12-27 11:58:07.04	2019-12-27 11:58:07.04
446	TRY	RUB	0.0959309931999999949	2019-12-27 11:58:07.042	2019-12-27 11:58:07.042
447	RUB	RUB	1	2019-12-27 11:58:07.044	2019-12-27 11:58:07.044
448	HKD	RUB	0.125433909199999999	2019-12-27 11:58:07.045	2019-12-27 11:58:07.045
449	ISK	RUB	1.97112505309999997	2019-12-27 11:58:07.047	2019-12-27 11:58:07.047
450	EUR	RUB	0.0145363204999999999	2019-12-27 11:58:07.049	2019-12-27 11:58:07.049
451	DKK	RUB	0.108603757300000006	2019-12-27 11:58:07.051	2019-12-27 11:58:07.051
452	CAD	RUB	0.0211968625000000001	2019-12-27 11:58:07.052	2019-12-27 11:58:07.052
453	MYR	RUB	0.0666228638999999956	2019-12-27 11:58:07.054	2019-12-27 11:58:07.054
454	USD	RUB	0.0161062431000000006	2019-12-27 11:58:07.056	2019-12-27 11:58:07.056
455	BGN	RUB	0.0284301354999999985	2019-12-27 11:58:07.057	2019-12-27 11:58:07.057
456	NOK	RUB	0.144081100999999989	2019-12-27 11:58:07.059	2019-12-27 11:58:07.059
457	RON	RUB	0.0694690753999999966	2019-12-27 11:58:07.06	2019-12-27 11:58:07.06
458	SGD	RUB	0.0218291923999999991	2019-12-27 11:58:07.062	2019-12-27 11:58:07.062
459	CZK	RUB	0.370458126700000001	2019-12-27 11:58:07.064	2019-12-27 11:58:07.064
460	SEK	RUB	0.151981591199999988	2019-12-27 11:58:07.065	2019-12-27 11:58:07.065
461	NZD	RUB	0.0242989132999999989	2019-12-27 11:58:07.066	2019-12-27 11:58:07.066
462	BRL	RUB	0.0657710355000000052	2019-12-27 11:58:07.067	2019-12-27 11:58:07.067
463	HRK	HRK	1	2019-12-27 11:58:07.339	2019-12-27 11:58:07.339
464	CHF	HRK	0.146101672200000005	2019-12-27 11:58:07.346	2019-12-27 11:58:07.346
465	MXN	HRK	2.82191928009999993	2019-12-27 11:58:07.348	2019-12-27 11:58:07.348
466	ZAR	HRK	2.11220200120000001	2019-12-27 11:58:07.349	2019-12-27 11:58:07.349
467	INR	HRK	10.6040561412999992	2019-12-27 11:58:07.351	2019-12-27 11:58:07.351
468	THB	HRK	4.48754281109999997	2019-12-27 11:58:07.352	2019-12-27 11:58:07.352
469	CNY	HRK	1.04281780940000002	2019-12-27 11:58:07.354	2019-12-27 11:58:07.354
470	AUD	HRK	0.215150090699999991	2019-12-27 11:58:07.355	2019-12-27 11:58:07.355
471	ILS	HRK	0.51660734669999997	2019-12-27 11:58:07.357	2019-12-27 11:58:07.357
472	KRW	HRK	172.951447182900012	2019-12-27 11:58:07.358	2019-12-27 11:58:07.358
473	JPY	HRK	16.2769458062000005	2019-12-27 11:58:07.359	2019-12-27 11:58:07.359
474	PLN	HRK	0.572130817300000039	2019-12-27 11:58:07.361	2019-12-27 11:58:07.361
475	GBP	HRK	0.114878785799999994	2019-12-27 11:58:07.362	2019-12-27 11:58:07.362
476	IDR	HRK	2081.1765495937002	2019-12-27 11:58:07.364	2019-12-27 11:58:07.364
477	HUF	HRK	44.5584581291000035	2019-12-27 11:58:07.365	2019-12-27 11:58:07.365
478	PHP	HRK	7.56577798669999968	2019-12-27 11:58:07.367	2019-12-27 11:58:07.367
479	TRY	HRK	0.886360889099999993	2019-12-27 11:58:07.369	2019-12-27 11:58:07.369
480	RUB	HRK	9.23956752399999992	2019-12-27 11:58:07.37	2019-12-27 11:58:07.37
481	HKD	HRK	1.15895507350000004	2019-12-27 11:58:07.372	2019-12-27 11:58:07.372
482	ISK	HRK	18.2123430259999992	2019-12-27 11:58:07.373	2019-12-27 11:58:07.373
483	EUR	HRK	0.134309314399999991	2019-12-27 11:58:07.375	2019-12-27 11:58:07.375
484	DKK	HRK	1.00345174939999993	2019-12-27 11:58:07.376	2019-12-27 11:58:07.376
485	CAD	HRK	0.195849842200000013	2019-12-27 11:58:07.378	2019-12-27 11:58:07.378
486	MYR	HRK	0.615566449499999946	2019-12-27 11:58:07.379	2019-12-27 11:58:07.379
487	USD	HRK	0.148814720300000009	2019-12-27 11:58:07.381	2019-12-27 11:58:07.381
488	BGN	HRK	0.262682157000000027	2019-12-27 11:58:07.382	2019-12-27 11:58:07.382
489	NOK	HRK	1.33124706200000009	2019-12-27 11:58:07.384	2019-12-27 11:58:07.384
490	RON	HRK	0.641864213299999986	2019-12-27 11:58:07.386	2019-12-27 11:58:07.386
491	SGD	HRK	0.201692297399999998	2019-12-27 11:58:07.388	2019-12-27 11:58:07.388
492	CZK	HRK	3.42287287620000003	2019-12-27 11:58:07.39	2019-12-27 11:58:07.39
493	SEK	HRK	1.40424417430000004	2019-12-27 11:58:07.392	2019-12-27 11:58:07.392
494	NZD	HRK	0.224511449899999993	2019-12-27 11:58:07.394	2019-12-27 11:58:07.394
495	BRL	HRK	0.607695923700000029	2019-12-27 11:58:07.396	2019-12-27 11:58:07.396
496	HRK	JPY	0.0614365871999999966	2019-12-27 11:58:07.656	2019-12-27 11:58:07.656
497	CHF	JPY	0.00897598810000000032	2019-12-27 11:58:07.664	2019-12-27 11:58:07.664
498	MXN	JPY	0.173369089899999995	2019-12-27 11:58:07.67	2019-12-27 11:58:07.67
499	ZAR	JPY	0.129766482399999994	2019-12-27 11:58:07.672	2019-12-27 11:58:07.672
500	INR	JPY	0.65147701960000004	2019-12-27 11:58:07.674	2019-12-27 11:58:07.674
501	THB	JPY	0.275699315100000009	2019-12-27 11:58:07.675	2019-12-27 11:58:07.675
502	CNY	JPY	0.0640671673000000053	2019-12-27 11:58:07.677	2019-12-27 11:58:07.677
503	AUD	JPY	0.0132180873000000004	2019-12-27 11:58:07.679	2019-12-27 11:58:07.679
504	ILS	JPY	0.0317385923	2019-12-27 11:58:07.68	2019-12-27 11:58:07.68
505	KRW	JPY	10.6255466622999997	2019-12-27 11:58:07.682	2019-12-27 11:58:07.682
506	JPY	JPY	1	2019-12-27 11:58:07.683	2019-12-27 11:58:07.683
507	PLN	JPY	0.0351497647999999968	2019-12-27 11:58:07.685	2019-12-27 11:58:07.685
508	GBP	JPY	0.00705776050000000033	2019-12-27 11:58:07.687	2019-12-27 11:58:07.687
509	IDR	JPY	127.8603845202	2019-12-27 11:58:07.689	2019-12-27 11:58:07.689
510	HUF	JPY	2.73751959729999994	2019-12-27 11:58:07.691	2019-12-27 11:58:07.691
511	PHP	JPY	0.464815578800000018	2019-12-27 11:58:07.693	2019-12-27 11:58:07.693
512	TRY	JPY	0.0544549880000000028	2019-12-27 11:58:07.696	2019-12-27 11:58:07.696
513	RUB	JPY	0.567647495699999949	2019-12-27 11:58:07.698	2019-12-27 11:58:07.698
514	HKD	JPY	0.0712022444000000032	2019-12-27 11:58:07.7	2019-12-27 11:58:07.7
515	ISK	JPY	1.11890420000000002	2019-12-27 11:58:07.702	2019-12-27 11:58:07.702
516	EUR	JPY	0.00825150590000000086	2019-12-27 11:58:07.704	2019-12-27 11:58:07.704
517	DKK	JPY	0.0616486508999999974	2019-12-27 11:58:07.706	2019-12-27 11:58:07.706
518	CAD	JPY	0.0120323458999999997	2019-12-27 11:58:07.707	2019-12-27 11:58:07.707
519	MYR	JPY	0.0378183018000000024	2019-12-27 11:58:07.709	2019-12-27 11:58:07.709
520	USD	JPY	0.00914266849999999937	2019-12-27 11:58:07.711	2019-12-27 11:58:07.711
521	BGN	JPY	0.0161382951999999996	2019-12-27 11:58:07.712	2019-12-27 11:58:07.712
522	NOK	JPY	0.0817872761999999948	2019-12-27 11:58:07.714	2019-12-27 11:58:07.714
523	RON	JPY	0.0394339466999999999	2019-12-27 11:58:07.715	2019-12-27 11:58:07.715
524	SGD	JPY	0.0123912863999999998	2019-12-27 11:58:07.717	2019-12-27 11:58:07.717
525	CZK	JPY	0.210289627899999998	2019-12-27 11:58:07.718	2019-12-27 11:58:07.718
526	SEK	JPY	0.086271969599999998	2019-12-27 11:58:07.72	2019-12-27 11:58:07.72
527	NZD	JPY	0.0137932173000000005	2019-12-27 11:58:07.722	2019-12-27 11:58:07.722
528	BRL	JPY	0.037334763600000001	2019-12-27 11:58:07.724	2019-12-27 11:58:07.724
529	HRK	THB	0.222839099699999987	2019-12-27 11:58:07.976	2019-12-27 11:58:07.976
530	CHF	THB	0.0325571651000000004	2019-12-27 11:58:07.983	2019-12-27 11:58:07.983
531	MXN	THB	0.628833951900000021	2019-12-27 11:58:07.985	2019-12-27 11:58:07.985
532	ZAR	THB	0.470681192400000004	2019-12-27 11:58:07.986	2019-12-27 11:58:07.986
533	INR	THB	2.36299832399999987	2019-12-27 11:58:07.988	2019-12-27 11:58:07.988
534	THB	THB	1	2019-12-27 11:58:07.99	2019-12-27 11:58:07.99
535	CNY	THB	0.232380581800000013	2019-12-27 11:58:07.991	2019-12-27 11:58:07.991
536	AUD	THB	0.0479438525000000018	2019-12-27 11:58:07.993	2019-12-27 11:58:07.993
537	ILS	THB	0.115120316099999995	2019-12-27 11:58:07.994	2019-12-27 11:58:07.994
538	KRW	THB	38.5403447862999968	2019-12-27 11:58:07.996	2019-12-27 11:58:07.996
539	JPY	THB	3.62713994970000009	2019-12-27 11:58:07.997	2019-12-27 11:58:07.997
540	PLN	THB	0.127493116200000006	2019-12-27 11:58:07.999	2019-12-27 11:58:07.999
541	GBP	THB	0.0255994852000000009	2019-12-27 11:58:08	2019-12-27 11:58:08
542	IDR	THB	463.767508679499997	2019-12-27 11:58:08.004	2019-12-27 11:58:08.004
543	HUF	THB	9.92936669460000054	2019-12-27 11:58:08.006	2019-12-27 11:58:08.006
544	PHP	THB	1.68595115529999995	2019-12-27 11:58:08.007	2019-12-27 11:58:08.007
545	TRY	THB	0.197515862600000008	2019-12-27 11:58:08.009	2019-12-27 11:58:08.009
546	RUB	THB	2.05893690889999981	2019-12-27 11:58:08.011	2019-12-27 11:58:08.011
547	HKD	THB	0.25826050519999999	2019-12-27 11:58:08.012	2019-12-27 11:58:08.012
548	ISK	THB	4.05842212379999978	2019-12-27 11:58:08.014	2019-12-27 11:58:08.014
549	EUR	THB	0.0299293667000000013	2019-12-27 11:58:08.015	2019-12-27 11:58:08.015
550	DKK	THB	0.223608284399999996	2019-12-27 11:58:08.016	2019-12-27 11:58:08.016
551	CAD	THB	0.0436430024999999999	2019-12-27 11:58:08.018	2019-12-27 11:58:08.018
552	MYR	THB	0.137172273399999989	2019-12-27 11:58:08.019	2019-12-27 11:58:08.019
553	USD	THB	0.0331617383000000002	2019-12-27 11:58:08.02	2019-12-27 11:58:08.02
554	BGN	THB	0.0585358554000000031	2019-12-27 11:58:08.022	2019-12-27 11:58:08.022
555	NOK	THB	0.296653896799999983	2019-12-27 11:58:08.023	2019-12-27 11:58:08.023
556	RON	THB	0.143032443399999987	2019-12-27 11:58:08.025	2019-12-27 11:58:08.025
557	SGD	THB	0.044944930000000001	2019-12-27 11:58:08.026	2019-12-27 11:58:08.026
558	CZK	THB	0.762749910200000047	2019-12-27 11:58:08.028	2019-12-27 11:58:08.028
559	SEK	THB	0.312920507600000009	2019-12-27 11:58:08.029	2019-12-27 11:58:08.029
560	NZD	THB	0.0500299293999999992	2019-12-27 11:58:08.031	2019-12-27 11:58:08.031
561	BRL	THB	0.135418412500000002	2019-12-27 11:58:08.033	2019-12-27 11:58:08.033
562	HRK	CHF	6.84454863030000027	2019-12-27 11:58:08.345	2019-12-27 11:58:08.345
563	CHF	CHF	1	2019-12-27 11:58:08.352	2019-12-27 11:58:08.352
564	MXN	CHF	19.3147637432999986	2019-12-27 11:58:08.354	2019-12-27 11:58:08.354
565	ZAR	CHF	14.4570693142	2019-12-27 11:58:08.356	2019-12-27 11:58:08.356
566	INR	CHF	72.5799779370999971	2019-12-27 11:58:08.357	2019-12-27 11:58:08.357
567	THB	CHF	30.7152050008999993	2019-12-27 11:58:08.359	2019-12-27 11:58:08.359
568	CNY	CHF	7.13761720900000007	2019-12-27 11:58:08.36	2019-12-27 11:58:08.36
569	AUD	CHF	1.4726052583	2019-12-27 11:58:08.361	2019-12-27 11:58:08.361
570	ILS	CHF	3.5359441074000002	2019-12-27 11:58:08.363	2019-12-27 11:58:08.363
571	KRW	CHF	1183.77459091740002	2019-12-27 11:58:08.364	2019-12-27 11:58:08.364
572	JPY	CHF	111.408347122600006	2019-12-27 11:58:08.366	2019-12-27 11:58:08.366
573	PLN	CHF	3.91597720170000008	2019-12-27 11:58:08.367	2019-12-27 11:58:08.367
574	GBP	CHF	0.786293436300000037	2019-12-27 11:58:08.368	2019-12-27 11:58:08.368
575	IDR	CHF	14244.7141018570001	2019-12-27 11:58:08.37	2019-12-27 11:58:08.37
576	HUF	CHF	304.982533553999986	2019-12-27 11:58:08.371	2019-12-27 11:58:08.371
577	PHP	CHF	51.7843353558000032	2019-12-27 11:58:08.373	2019-12-27 11:58:08.373
578	TRY	CHF	6.06674020959999982	2019-12-27 11:58:08.374	2019-12-27 11:58:08.374
579	RUB	CHF	63.2406692407000008	2019-12-27 11:58:08.376	2019-12-27 11:58:08.376
580	HKD	CHF	7.93252436109999959	2019-12-27 11:58:08.377	2019-12-27 11:58:08.377
581	ISK	CHF	124.655267512400002	2019-12-27 11:58:08.379	2019-12-27 11:58:08.379
582	EUR	CHF	0.919286633599999958	2019-12-27 11:58:08.38	2019-12-27 11:58:08.38
583	DKK	CHF	6.86817429670000035	2019-12-27 11:58:08.382	2019-12-27 11:58:08.382
584	CAD	CHF	1.34050376909999991	2019-12-27 11:58:08.383	2019-12-27 11:58:08.383
585	MYR	CHF	4.21327449899999973	2019-12-27 11:58:08.385	2019-12-27 11:58:08.385
586	USD	CHF	1.01856959000000002	2019-12-27 11:58:08.386	2019-12-27 11:58:08.386
587	BGN	CHF	1.79794079789999994	2019-12-27 11:58:08.387	2019-12-27 11:58:08.387
588	NOK	CHF	9.11178525460000088	2019-12-27 11:58:08.388	2019-12-27 11:58:08.388
589	RON	CHF	4.39327082179999984	2019-12-27 11:58:08.39	2019-12-27 11:58:08.39
590	SGD	CHF	1.38049273760000002	2019-12-27 11:58:08.391	2019-12-27 11:58:08.391
591	CZK	CHF	23.4280198565999989	2019-12-27 11:58:08.392	2019-12-27 11:58:08.392
592	SEK	CHF	9.6114175399999997	2019-12-27 11:58:08.393	2019-12-27 11:58:08.393
593	NZD	CHF	1.5366795366999999	2019-12-27 11:58:08.395	2019-12-27 11:58:08.395
594	BRL	CHF	4.15940430229999958	2019-12-27 11:58:08.396	2019-12-27 11:58:08.396
595	HRK	EUR	7.44550000000000001	2019-12-27 11:58:08.651	2019-12-27 11:58:08.651
596	CHF	EUR	1.0878000000000001	2019-12-27 11:58:08.659	2019-12-27 11:58:08.659
597	MXN	EUR	21.0106000000000002	2019-12-27 11:58:08.661	2019-12-27 11:58:08.661
598	ZAR	EUR	15.7263999999999999	2019-12-27 11:58:08.663	2019-12-27 11:58:08.663
599	INR	EUR	78.9525000000000006	2019-12-27 11:58:08.665	2019-12-27 11:58:08.665
600	THB	EUR	33.411999999999999	2019-12-27 11:58:08.667	2019-12-27 11:58:08.667
601	CNY	EUR	7.76430000000000042	2019-12-27 11:58:08.669	2019-12-27 11:58:08.669
602	AUD	EUR	1.6019000000000001	2019-12-27 11:58:08.67	2019-12-27 11:58:08.67
603	ILS	EUR	3.84640000000000004	2019-12-27 11:58:08.672	2019-12-27 11:58:08.672
604	KRW	EUR	1287.71000000000004	2019-12-27 11:58:08.674	2019-12-27 11:58:08.674
605	JPY	EUR	121.189999999999998	2019-12-27 11:58:08.675	2019-12-27 11:58:08.675
606	PLN	EUR	4.25980000000000025	2019-12-27 11:58:08.677	2019-12-27 11:58:08.677
607	GBP	EUR	0.855330000000000035	2019-12-27 11:58:08.678	2019-12-27 11:58:08.678
608	IDR	EUR	15495.3999999999996	2019-12-27 11:58:08.68	2019-12-27 11:58:08.68
609	HUF	EUR	331.759999999999991	2019-12-27 11:58:08.681	2019-12-27 11:58:08.681
610	PHP	EUR	56.3310000000000031	2019-12-27 11:58:08.684	2019-12-27 11:58:08.684
611	TRY	EUR	6.59940000000000015	2019-12-27 11:58:08.686	2019-12-27 11:58:08.686
612	RUB	EUR	68.7931999999999988	2019-12-27 11:58:08.687	2019-12-27 11:58:08.687
613	HKD	EUR	8.62899999999999956	2019-12-27 11:58:08.689	2019-12-27 11:58:08.689
614	ISK	EUR	135.599999999999994	2019-12-27 11:58:08.691	2019-12-27 11:58:08.691
615	DKK	EUR	7.47119999999999962	2019-12-27 11:58:08.692	2019-12-27 11:58:08.692
616	CAD	EUR	1.45819999999999994	2019-12-27 11:58:08.693	2019-12-27 11:58:08.693
617	USD	EUR	1.1080000000000001	2019-12-27 11:58:08.695	2019-12-27 11:58:08.695
618	MYR	EUR	4.58319999999999972	2019-12-27 11:58:08.698	2019-12-27 11:58:08.698
619	BGN	EUR	1.95579999999999998	2019-12-27 11:58:08.7	2019-12-27 11:58:08.7
620	NOK	EUR	9.9117999999999995	2019-12-27 11:58:08.701	2019-12-27 11:58:08.701
621	RON	EUR	4.77899999999999991	2019-12-27 11:58:08.703	2019-12-27 11:58:08.703
622	SGD	EUR	1.50170000000000003	2019-12-27 11:58:08.705	2019-12-27 11:58:08.705
623	CZK	EUR	25.4849999999999994	2019-12-27 11:58:08.706	2019-12-27 11:58:08.706
624	SEK	EUR	10.4552999999999994	2019-12-27 11:58:08.708	2019-12-27 11:58:08.708
625	NZD	EUR	1.67159999999999997	2019-12-27 11:58:08.71	2019-12-27 11:58:08.71
626	BRL	EUR	4.5246000000000004	2019-12-27 11:58:08.711	2019-12-27 11:58:08.711
627	HRK	MYR	1.62451998599999992	2019-12-27 11:58:08.967	2019-12-27 11:58:08.968
628	CHF	MYR	0.237345086399999988	2019-12-27 11:58:08.97	2019-12-27 11:58:08.97
629	MXN	MYR	4.58426426950000021	2019-12-27 11:58:08.971	2019-12-27 11:58:08.971
630	ZAR	MYR	3.43131436550000002	2019-12-27 11:58:08.972	2019-12-27 11:58:08.972
631	INR	MYR	17.2265011345999994	2019-12-27 11:58:08.974	2019-12-27 11:58:08.974
632	THB	MYR	7.29010298479999985	2019-12-27 11:58:08.975	2019-12-27 11:58:08.975
633	CNY	MYR	1.69407837319999999	2019-12-27 11:58:08.976	2019-12-27 11:58:08.976
634	AUD	MYR	0.349515622299999995	2019-12-27 11:58:08.978	2019-12-27 11:58:08.978
635	ILS	MYR	0.839238959699999953	2019-12-27 11:58:08.979	2019-12-27 11:58:08.979
636	KRW	MYR	280.963082562400018	2019-12-27 11:58:08.98	2019-12-27 11:58:08.98
637	JPY	MYR	26.4422237737999986	2019-12-27 11:58:08.982	2019-12-27 11:58:08.982
638	PLN	MYR	0.929437947299999956	2019-12-27 11:58:08.983	2019-12-27 11:58:08.983
639	GBP	MYR	0.186622883600000011	2019-12-27 11:58:08.985	2019-12-27 11:58:08.985
640	IDR	MYR	3380.91289928429978	2019-12-27 11:58:08.987	2019-12-27 11:58:08.987
641	HUF	MYR	72.3861057775999939	2019-12-27 11:58:08.988	2019-12-27 11:58:08.988
642	PHP	MYR	12.2907575493000003	2019-12-27 11:58:08.989	2019-12-27 11:58:08.989
643	TRY	MYR	1.4399109792	2019-12-27 11:58:08.991	2019-12-27 11:58:08.991
644	RUB	MYR	15.0098621050999999	2019-12-27 11:58:08.994	2019-12-27 11:58:08.994
645	HKD	MYR	1.88274567989999997	2019-12-27 11:58:08.996	2019-12-27 11:58:08.996
646	ISK	MYR	29.5863152382999992	2019-12-27 11:58:08.998	2019-12-27 11:58:08.998
647	EUR	MYR	0.218188165500000003	2019-12-27 11:58:08.999	2019-12-27 11:58:08.999
648	DKK	MYR	1.63012742189999993	2019-12-27 11:58:09.001	2019-12-27 11:58:09.001
649	CAD	MYR	0.318161982899999973	2019-12-27 11:58:09.002	2019-12-27 11:58:09.002
650	MYR	MYR	1	2019-12-27 11:58:09.003	2019-12-27 11:58:09.003
651	USD	MYR	0.241752487300000013	2019-12-27 11:58:09.005	2019-12-27 11:58:09.005
652	BGN	MYR	0.426732414000000004	2019-12-27 11:58:09.006	2019-12-27 11:58:09.006
653	NOK	MYR	2.16263745849999989	2019-12-27 11:58:09.007	2019-12-27 11:58:09.007
654	RON	MYR	1.04272124279999989	2019-12-27 11:58:09.009	2019-12-27 11:58:09.009
655	SGD	MYR	0.327653168100000003	2019-12-27 11:58:09.01	2019-12-27 11:58:09.01
656	CZK	MYR	5.56052539710000016	2019-12-27 11:58:09.011	2019-12-27 11:58:09.011
657	SEK	MYR	2.2812227264999998	2019-12-27 11:58:09.013	2019-12-27 11:58:09.013
658	NZD	MYR	0.364723337400000014	2019-12-27 11:58:09.014	2019-12-27 11:58:09.014
659	BRL	MYR	0.987214173499999958	2019-12-27 11:58:09.015	2019-12-27 11:58:09.015
660	HRK	BGN	3.80688209430000013	2019-12-27 11:58:09.274	2019-12-27 11:58:09.274
661	CHF	BGN	0.556191839699999968	2019-12-27 11:58:09.282	2019-12-27 11:58:09.282
662	MXN	BGN	10.7427139788999995	2019-12-27 11:58:09.284	2019-12-27 11:58:09.284
663	ZAR	BGN	8.04090397789999933	2019-12-27 11:58:09.286	2019-12-27 11:58:09.286
664	INR	BGN	40.3683914511000026	2019-12-27 11:58:09.287	2019-12-27 11:58:09.287
665	THB	BGN	17.0835463748999992	2019-12-27 11:58:09.289	2019-12-27 11:58:09.289
666	CNY	BGN	3.96988444630000004	2019-12-27 11:58:09.29	2019-12-27 11:58:09.29
667	AUD	BGN	0.819051027700000045	2019-12-27 11:58:09.292	2019-12-27 11:58:09.292
668	ILS	BGN	1.96666325800000008	2019-12-27 11:58:09.293	2019-12-27 11:58:09.293
669	KRW	BGN	658.405767460900051	2019-12-27 11:58:09.295	2019-12-27 11:58:09.295
670	JPY	BGN	61.9644135392000024	2019-12-27 11:58:09.296	2019-12-27 11:58:09.296
671	PLN	BGN	2.17803456389999983	2019-12-27 11:58:09.297	2019-12-27 11:58:09.297
672	GBP	BGN	0.437329992800000011	2019-12-27 11:58:09.299	2019-12-27 11:58:09.299
673	IDR	BGN	7922.7937416914001	2019-12-27 11:58:09.3	2019-12-27 11:58:09.3
674	HUF	BGN	169.628796400400006	2019-12-27 11:58:09.302	2019-12-27 11:58:09.302
675	PHP	BGN	28.802024746899999	2019-12-27 11:58:09.303	2019-12-27 11:58:09.303
676	TRY	BGN	3.37427139789999986	2019-12-27 11:58:09.304	2019-12-27 11:58:09.304
677	RUB	BGN	35.1739441661000001	2019-12-27 11:58:09.306	2019-12-27 11:58:09.306
678	HKD	BGN	4.41200531750000025	2019-12-27 11:58:09.307	2019-12-27 11:58:09.307
679	ISK	BGN	69.3322425605999939	2019-12-27 11:58:09.309	2019-12-27 11:58:09.309
680	EUR	BGN	0.511299723900000003	2019-12-27 11:58:09.31	2019-12-27 11:58:09.31
681	DKK	BGN	3.82002249720000009	2019-12-27 11:58:09.312	2019-12-27 11:58:09.312
682	CAD	BGN	0.745577257400000026	2019-12-27 11:58:09.313	2019-12-27 11:58:09.313
683	MYR	BGN	2.34338889459999988	2019-12-27 11:58:09.315	2019-12-27 11:58:09.315
684	USD	BGN	0.566520094100000038	2019-12-27 11:58:09.316	2019-12-27 11:58:09.316
685	BGN	BGN	1	2019-12-27 11:58:09.318	2019-12-27 11:58:09.318
686	NOK	BGN	5.06790060330000003	2019-12-27 11:58:09.319	2019-12-27 11:58:09.319
687	RON	BGN	2.44350138049999988	2019-12-27 11:58:09.321	2019-12-27 11:58:09.321
688	SGD	BGN	0.767818795399999976	2019-12-27 11:58:09.322	2019-12-27 11:58:09.322
689	CZK	BGN	13.0304734634999999	2019-12-27 11:58:09.324	2019-12-27 11:58:09.324
690	SEK	BGN	5.34579200329999971	2019-12-27 11:58:09.325	2019-12-27 11:58:09.325
691	NZD	BGN	0.854688618499999997	2019-12-27 11:58:09.327	2019-12-27 11:58:09.327
692	BRL	BGN	2.31342673069999982	2019-12-27 11:58:09.328	2019-12-27 11:58:09.328
693	HRK	TRY	1.12820862500000008	2019-12-27 11:58:09.584	2019-12-27 11:58:09.584
694	CHF	TRY	0.164833166700000006	2019-12-27 11:58:09.591	2019-12-27 11:58:09.591
695	MXN	TRY	3.18371367090000001	2019-12-27 11:58:09.594	2019-12-27 11:58:09.594
696	ZAR	TRY	2.38300451560000015	2019-12-27 11:58:09.595	2019-12-27 11:58:09.595
697	INR	TRY	11.9635875989000002	2019-12-27 11:58:09.597	2019-12-27 11:58:09.597
698	THB	TRY	5.0628845047000004	2019-12-27 11:58:09.598	2019-12-27 11:58:09.598
699	CNY	TRY	1.1765160469	2019-12-27 11:58:09.6	2019-12-27 11:58:09.6
700	AUD	TRY	0.24273418799999999	2019-12-27 11:58:09.601	2019-12-27 11:58:09.601
701	ILS	TRY	0.582840864300000039	2019-12-27 11:58:09.602	2019-12-27 11:58:09.602
702	KRW	TRY	195.125314422499997	2019-12-27 11:58:09.604	2019-12-27 11:58:09.604
703	JPY	TRY	18.3637906475999984	2019-12-27 11:58:09.605	2019-12-27 11:58:09.605
704	PLN	TRY	0.645482922699999961	2019-12-27 11:58:09.607	2019-12-27 11:58:09.607
705	GBP	TRY	0.129607236999999986	2019-12-27 11:58:09.608	2019-12-27 11:58:09.608
706	IDR	TRY	2348.00133345460017	2019-12-27 11:58:09.61	2019-12-27 11:58:09.61
707	HUF	TRY	50.2712367791000005	2019-12-27 11:58:09.611	2019-12-27 11:58:09.611
708	PHP	TRY	8.53577597960000034	2019-12-27 11:58:09.613	2019-12-27 11:58:09.613
709	TRY	TRY	1	2019-12-27 11:58:09.614	2019-12-27 11:58:09.614
710	RUB	TRY	10.4241597720999994	2019-12-27 11:58:09.615	2019-12-27 11:58:09.615
711	HKD	TRY	1.3075431099999999	2019-12-27 11:58:09.617	2019-12-27 11:58:09.617
712	ISK	TRY	20.5473224839000004	2019-12-27 11:58:09.618	2019-12-27 11:58:09.618
713	EUR	TRY	0.151528926899999999	2019-12-27 11:58:09.62	2019-12-27 11:58:09.62
714	DKK	TRY	1.13210291839999999	2019-12-27 11:58:09.621	2019-12-27 11:58:09.621
715	CAD	TRY	0.220959481200000002	2019-12-27 11:58:09.622	2019-12-27 11:58:09.622
716	MYR	TRY	0.694487377600000011	2019-12-27 11:58:09.623	2019-12-27 11:58:09.623
717	USD	TRY	0.167894050999999989	2019-12-27 11:58:09.625	2019-12-27 11:58:09.625
718	BGN	TRY	0.296360275200000023	2019-12-27 11:58:09.626	2019-12-27 11:58:09.626
719	NOK	TRY	1.50192441739999993	2019-12-27 11:58:09.628	2019-12-27 11:58:09.628
720	RON	TRY	0.724156741499999979	2019-12-27 11:58:09.63	2019-12-27 11:58:09.63
721	SGD	TRY	0.227550989499999995	2019-12-27 11:58:09.631	2019-12-27 11:58:09.631
722	CZK	TRY	3.86171470129999994	2019-12-27 11:58:09.633	2019-12-27 11:58:09.633
723	SEK	TRY	1.58428038909999991	2019-12-27 11:58:09.634	2019-12-27 11:58:09.634
724	NZD	TRY	0.253295754200000001	2019-12-27 11:58:09.636	2019-12-27 11:58:09.636
725	BRL	TRY	0.685607782499999985	2019-12-27 11:58:09.637	2019-12-27 11:58:09.637
726	HRK	CNY	0.958940277900000027	2019-12-27 11:58:09.889	2019-12-27 11:58:09.889
727	CHF	CNY	0.140102778099999992	2019-12-27 11:58:09.894	2019-12-27 11:58:09.894
728	MXN	CNY	2.7060520588000001	2019-12-27 11:58:09.895	2019-12-27 11:58:09.895
729	ZAR	CNY	2.02547557410000012	2019-12-27 11:58:09.897	2019-12-27 11:58:09.897
730	INR	CNY	10.1686565433999991	2019-12-27 11:58:09.898	2019-12-27 11:58:09.898
731	THB	CNY	4.30328555050000006	2019-12-27 11:58:09.9	2019-12-27 11:58:09.9
732	CNY	CNY	1	2019-12-27 11:58:09.902	2019-12-27 11:58:09.902
733	AUD	CNY	0.206316087700000012	2019-12-27 11:58:09.903	2019-12-27 11:58:09.903
734	ILS	CNY	0.49539559259999999	2019-12-27 11:58:09.905	2019-12-27 11:58:09.905
735	KRW	CNY	165.850108831400007	2019-12-27 11:58:09.907	2019-12-27 11:58:09.907
736	JPY	CNY	15.6086189353999991	2019-12-27 11:58:09.908	2019-12-27 11:58:09.908
737	PLN	CNY	0.548639284900000024	2019-12-27 11:58:09.909	2019-12-27 11:58:09.909
738	GBP	CNY	0.110161894799999993	2019-12-27 11:58:09.91	2019-12-27 11:58:09.91
739	IDR	CNY	1995.72401890700007	2019-12-27 11:58:09.911	2019-12-27 11:58:09.911
740	HUF	CNY	42.7289002228000001	2019-12-27 11:58:09.913	2019-12-27 11:58:09.913
741	PHP	CNY	7.25512924540000004	2019-12-27 11:58:09.914	2019-12-27 11:58:09.914
742	TRY	CNY	0.849967157399999951	2019-12-27 11:58:09.916	2019-12-27 11:58:09.916
743	RUB	CNY	8.86019344950000054	2019-12-27 11:58:09.917	2019-12-27 11:58:09.917
744	HKD	CNY	1.1113687002999999	2019-12-27 11:58:09.918	2019-12-27 11:58:09.918
745	ISK	CNY	17.4645492832999985	2019-12-27 11:58:09.92	2019-12-27 11:58:09.92
746	EUR	CNY	0.128794611199999992	2019-12-27 11:58:09.921	2019-12-27 11:58:09.921
747	DKK	CNY	0.962250299399999953	2019-12-27 11:58:09.922	2019-12-27 11:58:09.922
748	CAD	CNY	0.187808302099999991	2019-12-27 11:58:09.923	2019-12-27 11:58:09.923
749	MYR	CNY	0.59029146219999995	2019-12-27 11:58:09.925	2019-12-27 11:58:09.925
750	USD	CNY	0.14270442920000001	2019-12-27 11:58:09.926	2019-12-27 11:58:09.926
751	BGN	CNY	0.251896500700000026	2019-12-27 11:58:09.927	2019-12-27 11:58:09.927
752	NOK	CNY	1.27658642760000007	2019-12-27 11:58:09.928	2019-12-27 11:58:09.928
753	RON	CNY	0.615509447099999996	2019-12-27 11:58:09.929	2019-12-27 11:58:09.929
754	SGD	CNY	0.193410867699999989	2019-12-27 11:58:09.931	2019-12-27 11:58:09.931
755	CZK	CNY	3.28233066730000012	2019-12-27 11:58:09.932	2019-12-27 11:58:09.932
756	SEK	CNY	1.34658629879999991	2019-12-27 11:58:09.933	2019-12-27 11:58:09.933
757	NZD	CNY	0.21529307210000001	2019-12-27 11:58:09.934	2019-12-27 11:58:09.934
758	BRL	CNY	0.582744098000000044	2019-12-27 11:58:09.936	2019-12-27 11:58:09.936
759	HRK	NOK	0.751175366699999958	2019-12-27 11:58:10.187	2019-12-27 11:58:10.187
760	CHF	NOK	0.109747977199999999	2019-12-27 11:58:10.194	2019-12-27 11:58:10.194
761	MXN	NOK	2.11975625010000002	2019-12-27 11:58:10.196	2019-12-27 11:58:10.197
762	ZAR	NOK	1.58663411289999989	2019-12-27 11:58:10.199	2019-12-27 11:58:10.199
763	INR	NOK	7.96550576080000017	2019-12-27 11:58:10.2	2019-12-27 11:58:10.2
764	THB	NOK	3.37093161690000009	2019-12-27 11:58:10.201	2019-12-27 11:58:10.201
765	CNY	NOK	0.783339050399999959	2019-12-27 11:58:10.203	2019-12-27 11:58:10.203
766	AUD	NOK	0.161615448299999992	2019-12-27 11:58:10.204	2019-12-27 11:58:10.204
767	ILS	NOK	0.388062713100000012	2019-12-27 11:58:10.207	2019-12-27 11:58:10.207
768	KRW	NOK	129.916866764899993	2019-12-27 11:58:10.209	2019-12-27 11:58:10.209
769	JPY	NOK	12.2268407352999997	2019-12-27 11:58:10.211	2019-12-27 11:58:10.211
770	PLN	NOK	0.429770576500000001	2019-12-27 11:58:10.213	2019-12-27 11:58:10.213
771	GBP	NOK	0.0862941140999999995	2019-12-27 11:58:10.215	2019-12-27 11:58:10.215
772	IDR	NOK	1563.32855788050006	2019-12-27 11:58:10.217	2019-12-27 11:58:10.217
773	HUF	NOK	33.4712161261999981	2019-12-27 11:58:10.218	2019-12-27 11:58:10.218
774	PHP	NOK	5.68322605380000034	2019-12-27 11:58:10.22	2019-12-27 11:58:10.22
775	TRY	NOK	0.665812465899999983	2019-12-27 11:58:10.221	2019-12-27 11:58:10.221
776	RUB	NOK	6.94053552330000034	2019-12-27 11:58:10.222	2019-12-27 11:58:10.222
777	HKD	NOK	0.870578502400000009	2019-12-27 11:58:10.223	2019-12-27 11:58:10.223
778	ISK	NOK	13.6806634515999992	2019-12-27 11:58:10.224	2019-12-27 11:58:10.224
779	EUR	NOK	0.100889848500000004	2019-12-27 11:58:10.225	2019-12-27 11:58:10.225
780	DKK	NOK	0.753768235799999964	2019-12-27 11:58:10.226	2019-12-27 11:58:10.226
781	CAD	NOK	0.147117576999999999	2019-12-27 11:58:10.228	2019-12-27 11:58:10.228
782	MYR	NOK	0.462398353500000026	2019-12-27 11:58:10.229	2019-12-27 11:58:10.229
783	USD	NOK	0.111785952100000002	2019-12-27 11:58:10.23	2019-12-27 11:58:10.23
784	BGN	NOK	0.197320365599999992	2019-12-27 11:58:10.231	2019-12-27 11:58:10.231
785	NOK	NOK	1	2019-12-27 11:58:10.232	2019-12-27 11:58:10.232
786	RON	NOK	0.482152585799999978	2019-12-27 11:58:10.234	2019-12-27 11:58:10.234
787	SGD	NOK	0.151506285399999996	2019-12-27 11:58:10.235	2019-12-27 11:58:10.235
788	CZK	NOK	2.57117778809999997	2019-12-27 11:58:10.236	2019-12-27 11:58:10.236
789	SEK	NOK	1.05483363260000007	2019-12-27 11:58:10.238	2019-12-27 11:58:10.238
790	NZD	NOK	0.168647470699999996	2019-12-27 11:58:10.239	2019-12-27 11:58:10.239
791	BRL	NOK	0.456486208399999982	2019-12-27 11:58:10.24	2019-12-27 11:58:10.24
792	HRK	NZD	4.45411581719999994	2019-12-27 11:58:10.491	2019-12-27 11:58:10.491
793	CHF	NZD	0.650753768800000021	2019-12-27 11:58:10.498	2019-12-27 11:58:10.498
794	MXN	NZD	12.5691553003000003	2019-12-27 11:58:10.501	2019-12-27 11:58:10.501
795	ZAR	NZD	9.40799234270000007	2019-12-27 11:58:10.503	2019-12-27 11:58:10.503
796	INR	NZD	47.2316941851999985	2019-12-27 11:58:10.504	2019-12-27 11:58:10.504
797	THB	NZD	19.9880354151999988	2019-12-27 11:58:10.506	2019-12-27 11:58:10.506
798	CNY	NZD	4.64483129939999984	2019-12-27 11:58:10.508	2019-12-27 11:58:10.508
799	AUD	NZD	0.958303421899999952	2019-12-27 11:58:10.51	2019-12-27 11:58:10.51
800	ILS	NZD	2.30102895429999998	2019-12-27 11:58:10.512	2019-12-27 11:58:10.512
801	KRW	NZD	770.345776501599971	2019-12-27 11:58:10.514	2019-12-27 11:58:10.514
802	JPY	NZD	72.4994017708000058	2019-12-27 11:58:10.517	2019-12-27 11:58:10.517
803	PLN	NZD	2.5483369226999999	2019-12-27 11:58:10.518	2019-12-27 11:58:10.518
804	GBP	NZD	0.511683417099999982	2019-12-27 11:58:10.52	2019-12-27 11:58:10.52
805	IDR	NZD	9269.8013878917991	2019-12-27 11:58:10.522	2019-12-27 11:58:10.522
806	HUF	NZD	198.468533141899997	2019-12-27 11:58:10.524	2019-12-27 11:58:10.524
807	PHP	NZD	33.6988513999000006	2019-12-27 11:58:10.526	2019-12-27 11:58:10.526
808	TRY	NZD	3.94795405599999993	2019-12-27 11:58:10.527	2019-12-27 11:58:10.527
809	RUB	NZD	41.1541038526000023	2019-12-27 11:58:10.529	2019-12-27 11:58:10.529
810	HKD	NZD	5.16212012440000034	2019-12-27 11:58:10.531	2019-12-27 11:58:10.531
811	ISK	NZD	81.1198851399999938	2019-12-27 11:58:10.533	2019-12-27 11:58:10.533
812	EUR	NZD	0.5982292414	2019-12-27 11:58:10.534	2019-12-27 11:58:10.534
813	DKK	NZD	4.46949030870000019	2019-12-27 11:58:10.536	2019-12-27 11:58:10.536
814	CAD	NZD	0.872337879900000002	2019-12-27 11:58:10.537	2019-12-27 11:58:10.537
815	MYR	NZD	2.74180425939999983	2019-12-27 11:58:10.539	2019-12-27 11:58:10.539
816	USD	NZD	0.662837999499999997	2019-12-27 11:58:10.54	2019-12-27 11:58:10.54
817	BGN	NZD	1.17001675040000008	2019-12-27 11:58:10.542	2019-12-27 11:58:10.542
818	NOK	NZD	5.92952859539999988	2019-12-27 11:58:10.544	2019-12-27 11:58:10.544
819	RON	NZD	2.85893754489999985	2019-12-27 11:58:10.545	2019-12-27 11:58:10.545
820	SGD	NZD	0.898360851900000035	2019-12-27 11:58:10.547	2019-12-27 11:58:10.547
821	CZK	NZD	15.2458722182000006	2019-12-27 11:58:10.549	2019-12-27 11:58:10.549
822	SEK	NZD	6.25466618809999986	2019-12-27 11:58:10.55	2019-12-27 11:58:10.55
823	NZD	NZD	1	2019-12-27 11:58:10.552	2019-12-27 11:58:10.552
824	BRL	NZD	2.70674802580000007	2019-12-27 11:58:10.553	2019-12-27 11:58:10.553
825	HRK	ZAR	0.473439566599999972	2019-12-27 11:58:10.83	2019-12-27 11:58:10.83
826	CHF	ZAR	0.0691703122999999948	2019-12-27 11:58:10.834	2019-12-27 11:58:10.834
827	MXN	ZAR	1.33600824090000003	2019-12-27 11:58:10.836	2019-12-27 11:58:10.836
828	ZAR	ZAR	1	2019-12-27 11:58:10.837	2019-12-27 11:58:10.837
829	INR	ZAR	5.02037974360000039	2019-12-27 11:58:10.839	2019-12-27 11:58:10.839
830	THB	ZAR	2.12458032350000003	2019-12-27 11:58:10.84	2019-12-27 11:58:10.84
831	CNY	ZAR	0.493711211699999986	2019-12-27 11:58:10.841	2019-12-27 11:58:10.841
832	AUD	ZAR	0.101860565700000003	2019-12-27 11:58:10.843	2019-12-27 11:58:10.843
833	ILS	ZAR	0.244582358299999997	2019-12-27 11:58:10.845	2019-12-27 11:58:10.845
834	KRW	ZAR	81.8820581951000008	2019-12-27 11:58:10.846	2019-12-27 11:58:10.846
835	JPY	ZAR	7.70615016789999974	2019-12-27 11:58:10.848	2019-12-27 11:58:10.848
836	PLN	ZAR	0.270869366200000017	2019-12-27 11:58:10.85	2019-12-27 11:58:10.85
837	GBP	ZAR	0.0543881625999999979	2019-12-27 11:58:10.852	2019-12-27 11:58:10.852
838	IDR	ZAR	985.311323634099949	2019-12-27 11:58:10.853	2019-12-27 11:58:10.853
839	HUF	ZAR	21.0957371044999995	2019-12-27 11:58:10.855	2019-12-27 11:58:10.855
840	PHP	ZAR	3.58193865090000019	2019-12-27 11:58:10.856	2019-12-27 11:58:10.856
841	TRY	ZAR	0.419638315200000001	2019-12-27 11:58:10.857	2019-12-27 11:58:10.857
842	RUB	ZAR	4.37437684400000037	2019-12-27 11:58:10.859	2019-12-27 11:58:10.859
843	HKD	ZAR	0.548695187700000031	2019-12-27 11:58:10.86	2019-12-27 11:58:10.86
844	ISK	ZAR	8.62244378880000006	2019-12-27 11:58:10.862	2019-12-27 11:58:10.862
845	EUR	ZAR	0.0635873435999999986	2019-12-27 11:58:10.863	2019-12-27 11:58:10.863
846	DKK	ZAR	0.475073761300000008	2019-12-27 11:58:10.865	2019-12-27 11:58:10.865
847	CAD	ZAR	0.0927230643999999993	2019-12-27 11:58:10.866	2019-12-27 11:58:10.866
848	MYR	ZAR	0.291433513099999986	2019-12-27 11:58:10.868	2019-12-27 11:58:10.868
849	USD	ZAR	0.0704547766999999991	2019-12-27 11:58:10.869	2019-12-27 11:58:10.869
850	BGN	ZAR	0.1243641266	2019-12-27 11:58:10.871	2019-12-27 11:58:10.871
851	NOK	ZAR	0.630265031999999947	2019-12-27 11:58:10.872	2019-12-27 11:58:10.872
852	RON	ZAR	0.303883914899999996	2019-12-27 11:58:10.874	2019-12-27 11:58:10.874
853	SGD	ZAR	0.0954891137999999973	2019-12-27 11:58:10.875	2019-12-27 11:58:10.875
854	CZK	ZAR	1.62052345099999995	2019-12-27 11:58:10.876	2019-12-27 11:58:10.876
855	SEK	ZAR	0.664824753300000015	2019-12-27 11:58:10.878	2019-12-27 11:58:10.878
856	NZD	ZAR	0.106292603499999999	2019-12-27 11:58:10.879	2019-12-27 11:58:10.879
857	BRL	ZAR	0.287707294700000005	2019-12-27 11:58:10.881	2019-12-27 11:58:10.881
858	HRK	USD	6.71976534299999972	2019-12-27 11:58:11.144	2019-12-27 11:58:11.144
859	CHF	USD	0.981768953100000008	2019-12-27 11:58:11.152	2019-12-27 11:58:11.152
860	MXN	USD	18.9626353791	2019-12-27 11:58:11.153	2019-12-27 11:58:11.153
861	ZAR	USD	14.1935018051000004	2019-12-27 11:58:11.155	2019-12-27 11:58:11.155
862	INR	USD	71.256768953100007	2019-12-27 11:58:11.156	2019-12-27 11:58:11.156
863	THB	USD	30.1552346570000012	2019-12-27 11:58:11.157	2019-12-27 11:58:11.157
864	CNY	USD	7.0074909746999996	2019-12-27 11:58:11.159	2019-12-27 11:58:11.159
865	AUD	USD	1.44575812270000004	2019-12-27 11:58:11.16	2019-12-27 11:58:11.16
866	ILS	USD	3.47148014440000008	2019-12-27 11:58:11.161	2019-12-27 11:58:11.161
867	KRW	USD	1162.19314079419996	2019-12-27 11:58:11.163	2019-12-27 11:58:11.163
868	JPY	USD	109.377256317700002	2019-12-27 11:58:11.165	2019-12-27 11:58:11.165
869	PLN	USD	3.8445848374999998	2019-12-27 11:58:11.166	2019-12-27 11:58:11.166
870	GBP	USD	0.771958483799999984	2019-12-27 11:58:11.168	2019-12-27 11:58:11.168
871	IDR	USD	13985.0180505414992	2019-12-27 11:58:11.169	2019-12-27 11:58:11.169
872	HUF	USD	299.422382671499975	2019-12-27 11:58:11.17	2019-12-27 11:58:11.17
873	PHP	USD	50.8402527076000013	2019-12-27 11:58:11.172	2019-12-27 11:58:11.172
874	TRY	USD	5.95613718410000015	2019-12-27 11:58:11.173	2019-12-27 11:58:11.173
875	RUB	USD	62.0877256317999979	2019-12-27 11:58:11.175	2019-12-27 11:58:11.175
876	HKD	USD	7.78790613720000025	2019-12-27 11:58:11.176	2019-12-27 11:58:11.176
877	ISK	USD	122.382671480100001	2019-12-27 11:58:11.178	2019-12-27 11:58:11.178
878	EUR	USD	0.902527075800000023	2019-12-27 11:58:11.179	2019-12-27 11:58:11.179
879	DKK	USD	6.74296028879999998	2019-12-27 11:58:11.18	2019-12-27 11:58:11.18
880	CAD	USD	1.3160649819000001	2019-12-27 11:58:11.182	2019-12-27 11:58:11.182
881	MYR	USD	4.13646209389999964	2019-12-27 11:58:11.183	2019-12-27 11:58:11.183
882	USD	USD	1	2019-12-27 11:58:11.185	2019-12-27 11:58:11.185
883	BGN	USD	1.76516245490000001	2019-12-27 11:58:11.186	2019-12-27 11:58:11.186
884	NOK	USD	8.94566786999999941	2019-12-27 11:58:11.187	2019-12-27 11:58:11.187
885	RON	USD	4.31317689529999981	2019-12-27 11:58:11.189	2019-12-27 11:58:11.189
886	SGD	USD	1.35532490969999997	2019-12-27 11:58:11.19	2019-12-27 11:58:11.19
887	CZK	USD	23.0009025270999992	2019-12-27 11:58:11.192	2019-12-27 11:58:11.192
888	SEK	USD	9.43619133570000024	2019-12-27 11:58:11.193	2019-12-27 11:58:11.193
889	NZD	USD	1.50866425989999997	2019-12-27 11:58:11.194	2019-12-27 11:58:11.194
890	BRL	USD	4.08357400720000019	2019-12-27 11:58:11.196	2019-12-27 11:58:11.196
891	HRK	MXN	0.3543687472	2019-12-27 11:58:11.445	2019-12-27 11:58:11.445
892	CHF	MXN	0.0517738665000000015	2019-12-27 11:58:11.453	2019-12-27 11:58:11.453
893	MXN	MXN	1	2019-12-27 11:58:11.455	2019-12-27 11:58:11.455
894	ZAR	MXN	0.748498376999999993	2019-12-27 11:58:11.457	2019-12-27 11:58:11.457
895	INR	MXN	3.75774609009999994	2019-12-27 11:58:11.459	2019-12-27 11:58:11.459
896	THB	MXN	1.59024492400000006	2019-12-27 11:58:11.462	2019-12-27 11:58:11.462
897	CNY	MXN	0.369542040700000018	2019-12-27 11:58:11.464	2019-12-27 11:58:11.464
898	AUD	MXN	0.0762424681000000021	2019-12-27 11:58:11.466	2019-12-27 11:58:11.466
899	ILS	MXN	0.183069498299999994	2019-12-27 11:58:11.468	2019-12-27 11:58:11.468
900	KRW	MXN	61.2885876652999997	2019-12-27 11:58:11.469	2019-12-27 11:58:11.469
901	JPY	MXN	5.76804089360000027	2019-12-27 11:58:11.471	2019-12-27 11:58:11.471
902	PLN	MXN	0.202745280999999999	2019-12-27 11:58:11.473	2019-12-27 11:58:11.473
903	GBP	MXN	0.0407094513999999991	2019-12-27 11:58:11.474	2019-12-27 11:58:11.474
904	IDR	MXN	737.503926589399953	2019-12-27 11:58:11.475	2019-12-27 11:58:11.475
905	HUF	MXN	15.7901249845000002	2019-12-27 11:58:11.477	2019-12-27 11:58:11.477
906	PHP	MXN	2.68107526680000019	2019-12-27 11:58:11.478	2019-12-27 11:58:11.478
907	TRY	MXN	0.314098597899999998	2019-12-27 11:58:11.479	2019-12-27 11:58:11.479
908	RUB	MXN	3.27421396820000021	2019-12-27 11:58:11.481	2019-12-27 11:58:11.481
909	HKD	MXN	0.410697457499999974	2019-12-27 11:58:11.482	2019-12-27 11:58:11.482
910	ISK	MXN	6.45388518179999959	2019-12-27 11:58:11.484	2019-12-27 11:58:11.484
911	EUR	MXN	0.0475950234999999999	2019-12-27 11:58:11.485	2019-12-27 11:58:11.485
912	DKK	MXN	0.355591939299999993	2019-12-27 11:58:11.486	2019-12-27 11:58:11.486
913	CAD	MXN	0.0694030632000000036	2019-12-27 11:58:11.488	2019-12-27 11:58:11.488
914	MYR	MXN	0.218137511499999992	2019-12-27 11:58:11.489	2019-12-27 11:58:11.489
915	USD	MXN	0.0527352859999999993	2019-12-27 11:58:11.49	2019-12-27 11:58:11.49
916	BGN	MXN	0.0930863469000000054	2019-12-27 11:58:11.491	2019-12-27 11:58:11.491
917	NOK	MXN	0.471752353600000007	2019-12-27 11:58:11.493	2019-12-27 11:58:11.493
918	RON	MXN	0.227456617100000008	2019-12-27 11:58:11.494	2019-12-27 11:58:11.494
919	SGD	MXN	0.0714734466999999984	2019-12-27 11:58:11.495	2019-12-27 11:58:11.495
920	CZK	MXN	1.212959173	2019-12-27 11:58:11.497	2019-12-27 11:58:11.497
921	SEK	MXN	0.497620248799999998	2019-12-27 11:58:11.498	2019-12-27 11:58:11.498
922	NZD	MXN	0.0795598411999999949	2019-12-27 11:58:11.499	2019-12-27 11:58:11.499
923	BRL	MXN	0.215348443199999989	2019-12-27 11:58:11.5	2019-12-27 11:58:11.5
924	HRK	SGD	4.95804754610000042	2019-12-27 11:58:11.766	2019-12-27 11:58:11.766
925	CHF	SGD	0.724379037099999956	2019-12-27 11:58:11.773	2019-12-27 11:58:11.773
926	MXN	SGD	13.9912099619999992	2019-12-27 11:58:11.775	2019-12-27 11:58:11.775
927	ZAR	SGD	10.4723979489999994	2019-12-27 11:58:11.777	2019-12-27 11:58:11.777
928	INR	SGD	52.5754145301999998	2019-12-27 11:58:11.778	2019-12-27 11:58:11.778
929	THB	SGD	22.2494506226000013	2019-12-27 11:58:11.78	2019-12-27 11:58:11.78
930	CNY	SGD	5.17034028099999965	2019-12-27 11:58:11.781	2019-12-27 11:58:11.781
931	AUD	SGD	1.06672437900000006	2019-12-27 11:58:11.782	2019-12-27 11:58:11.782
932	ILS	SGD	2.56136378769999995	2019-12-27 11:58:11.784	2019-12-27 11:58:11.784
933	KRW	SGD	857.501498301900028	2019-12-27 11:58:11.785	2019-12-27 11:58:11.785
934	JPY	SGD	80.7018712125999969	2019-12-27 11:58:11.786	2019-12-27 11:58:11.786
935	PLN	SGD	2.83665179459999983	2019-12-27 11:58:11.788	2019-12-27 11:58:11.788
936	GBP	SGD	0.569574482299999962	2019-12-27 11:58:11.789	2019-12-27 11:58:11.789
937	IDR	SGD	10318.5722847439993	2019-12-27 11:58:11.79	2019-12-27 11:58:11.79
938	HUF	SGD	220.922953985499987	2019-12-27 11:58:11.792	2019-12-27 11:58:11.792
939	PHP	SGD	37.5114869813999974	2019-12-27 11:58:11.793	2019-12-27 11:58:11.793
940	TRY	SGD	4.39461943129999977	2019-12-27 11:58:11.794	2019-12-27 11:58:11.794
941	RUB	SGD	45.8102150895999998	2019-12-27 11:58:11.795	2019-12-27 11:58:11.795
942	HKD	SGD	5.74615435840000011	2019-12-27 11:58:11.796	2019-12-27 11:58:11.796
943	ISK	SGD	90.2976626490000029	2019-12-27 11:58:11.798	2019-12-27 11:58:11.798
944	EUR	SGD	0.665911966400000055	2019-12-27 11:58:11.799	2019-12-27 11:58:11.799
945	DKK	SGD	4.9751614837	2019-12-27 11:58:11.8	2019-12-27 11:58:11.8
946	CAD	SGD	0.971032829500000028	2019-12-27 11:58:11.801	2019-12-27 11:58:11.801
947	MYR	SGD	3.05200772460000014	2019-12-27 11:58:11.802	2019-12-27 11:58:11.802
948	USD	SGD	0.737830458799999978	2019-12-27 11:58:11.803	2019-12-27 11:58:11.803
949	BGN	SGD	1.30239062400000005	2019-12-27 11:58:11.804	2019-12-27 11:58:11.804
950	NOK	SGD	6.60038622889999971	2019-12-27 11:58:11.806	2019-12-27 11:58:11.806
951	RON	SGD	3.18239328760000006	2019-12-27 11:58:11.807	2019-12-27 11:58:11.807
952	SGD	SGD	1	2019-12-27 11:58:11.808	2019-12-27 11:58:11.808
953	CZK	SGD	16.9707664646999987	2019-12-27 11:58:11.809	2019-12-27 11:58:11.809
954	SEK	SGD	6.96230938269999999	2019-12-27 11:58:11.81	2019-12-27 11:58:11.81
955	NZD	SGD	1.11313844309999999	2019-12-27 11:58:11.811	2019-12-27 11:58:11.811
956	BRL	SGD	3.01298528329999993	2019-12-27 11:58:11.812	2019-12-27 11:58:11.812
957	HRK	AUD	4.64791809729999983	2019-12-27 11:58:11.841	2019-12-27 11:58:11.841
958	CHF	AUD	0.679068605999999964	2019-12-27 11:58:11.842	2019-12-27 11:58:11.842
959	MXN	AUD	13.1160496910000006	2019-12-27 11:58:11.844	2019-12-27 11:58:11.844
960	ZAR	AUD	9.81734190649999938	2019-12-27 11:58:11.845	2019-12-27 11:58:11.845
961	INR	AUD	49.2867844434999967	2019-12-27 11:58:11.847	2019-12-27 11:58:11.847
962	THB	AUD	20.8577314439000006	2019-12-27 11:58:11.848	2019-12-27 11:58:11.848
963	CNY	AUD	4.84693176850000018	2019-12-27 11:58:11.85	2019-12-27 11:58:11.85
964	AUD	AUD	1	2019-12-27 11:58:11.851	2019-12-27 11:58:11.851
965	ILS	AUD	2.40114863599999984	2019-12-27 11:58:11.852	2019-12-27 11:58:11.852
966	KRW	AUD	803.864161308399957	2019-12-27 11:58:11.853	2019-12-27 11:58:11.853
967	JPY	AUD	75.6539109807000045	2019-12-27 11:58:11.854	2019-12-27 11:58:11.854
968	PLN	AUD	2.65921717960000015	2019-12-27 11:58:11.856	2019-12-27 11:58:11.856
969	GBP	AUD	0.533947187700000048	2019-12-27 11:58:11.857	2019-12-27 11:58:11.857
970	IDR	AUD	9673.13814844869921	2019-12-27 11:58:11.858	2019-12-27 11:58:11.858
971	HUF	AUD	207.104063924099989	2019-12-27 11:58:11.86	2019-12-27 11:58:11.86
972	PHP	AUD	35.1651164242000007	2019-12-27 11:58:11.861	2019-12-27 11:58:11.861
973	TRY	AUD	4.11973281730000007	2019-12-27 11:58:11.862	2019-12-27 11:58:11.862
974	RUB	AUD	42.944753105700002	2019-12-27 11:58:11.863	2019-12-27 11:58:11.863
975	HKD	AUD	5.38672826019999995	2019-12-27 11:58:11.865	2019-12-27 11:58:11.865
976	ISK	AUD	84.6494787440000067	2019-12-27 11:58:11.866	2019-12-27 11:58:11.866
977	EUR	AUD	0.624258692800000015	2019-12-27 11:58:11.867	2019-12-27 11:58:11.867
978	DKK	AUD	4.66396154570000032	2019-12-27 11:58:11.869	2019-12-27 11:58:11.869
979	CAD	AUD	0.910294025799999962	2019-12-27 11:58:11.87	2019-12-27 11:58:11.87
980	MYR	AUD	2.86110244089999988	2019-12-27 11:58:11.872	2019-12-27 11:58:11.872
981	USD	AUD	0.691678631599999982	2019-12-27 11:58:11.873	2019-12-27 11:58:11.873
982	BGN	AUD	1.22092515139999991	2019-12-27 11:58:11.874	2019-12-27 11:58:11.874
983	NOK	AUD	6.18752731130000022	2019-12-27 11:58:11.876	2019-12-27 11:58:11.876
984	RON	AUD	2.98333229290000013	2019-12-27 11:58:11.877	2019-12-27 11:58:11.877
985	SGD	AUD	0.937449278999999969	2019-12-27 11:58:11.878	2019-12-27 11:58:11.878
986	CZK	AUD	15.9092327861000005	2019-12-27 11:58:11.879	2019-12-27 11:58:11.879
987	SEK	AUD	6.52681191090000024	2019-12-27 11:58:11.88	2019-12-27 11:58:11.88
988	NZD	AUD	1.04351083090000007	2019-12-27 11:58:11.881	2019-12-27 11:58:11.881
989	BRL	AUD	2.8245208814999998	2019-12-27 11:58:11.882	2019-12-27 11:58:11.882
990	HRK	ILS	1.9357061148000001	2019-12-27 11:58:12.164	2019-12-27 11:58:12.164
991	CHF	ILS	0.282809900200000019	2019-12-27 11:58:12.172	2019-12-27 11:58:12.172
992	MXN	ILS	5.46240640600000038	2019-12-27 11:58:12.174	2019-12-27 11:58:12.174
993	ZAR	ILS	4.08860232949999958	2019-12-27 11:58:12.175	2019-12-27 11:58:12.175
994	INR	ILS	20.5263363145	2019-12-27 11:58:12.177	2019-12-27 11:58:12.177
995	THB	ILS	8.68656405990000025	2019-12-27 11:58:12.178	2019-12-27 11:58:12.178
996	CNY	ILS	2.01858881029999981	2019-12-27 11:58:12.179	2019-12-27 11:58:12.179
997	AUD	ILS	0.416467346100000024	2019-12-27 11:58:12.18	2019-12-27 11:58:12.18
998	ILS	ILS	1	2019-12-27 11:58:12.182	2019-12-27 11:58:12.182
999	KRW	ILS	334.78317387689998	2019-12-27 11:58:12.183	2019-12-27 11:58:12.183
1000	JPY	ILS	31.5073835275	2019-12-27 11:58:12.184	2019-12-27 11:58:12.184
1001	PLN	ILS	1.10747712150000011	2019-12-27 11:58:12.185	2019-12-27 11:58:12.185
1002	GBP	ILS	0.222371568200000008	2019-12-27 11:58:12.186	2019-12-27 11:58:12.186
1003	IDR	ILS	4028.54617304490012	2019-12-27 11:58:12.188	2019-12-27 11:58:12.188
1004	HUF	ILS	86.2520798669000044	2019-12-27 11:58:12.189	2019-12-27 11:58:12.189
1005	PHP	ILS	14.6451227120999992	2019-12-27 11:58:12.19	2019-12-27 11:58:12.19
1006	TRY	ILS	1.71573419300000007	2019-12-27 11:58:12.191	2019-12-27 11:58:12.191
1007	RUB	ILS	17.8850873543999995	2019-12-27 11:58:12.192	2019-12-27 11:58:12.192
1008	HKD	ILS	2.24339642260000005	2019-12-27 11:58:12.193	2019-12-27 11:58:12.193
1009	ISK	ILS	35.253743760399999	2019-12-27 11:58:12.195	2019-12-27 11:58:12.195
1010	EUR	ILS	0.259983361099999977	2019-12-27 11:58:12.196	2019-12-27 11:58:12.196
1011	DKK	ILS	1.9423876872000001	2019-12-27 11:58:12.198	2019-12-27 11:58:12.198
1012	CAD	ILS	0.379107737100000008	2019-12-27 11:58:12.199	2019-12-27 11:58:12.199
1013	MYR	ILS	1.1915557403999999	2019-12-27 11:58:12.201	2019-12-27 11:58:12.201
1014	USD	ILS	0.2880615641	2019-12-27 11:58:12.202	2019-12-27 11:58:12.202
1015	BGN	ILS	0.508475457600000014	2019-12-27 11:58:12.204	2019-12-27 11:58:12.204
1016	NOK	ILS	2.57690307819999997	2019-12-27 11:58:12.205	2019-12-27 11:58:12.205
1017	RON	ILS	1.24246048250000007	2019-12-27 11:58:12.207	2019-12-27 11:58:12.207
1018	SGD	ILS	0.390417013300000004	2019-12-27 11:58:12.208	2019-12-27 11:58:12.208
1019	CZK	ILS	6.62567595670000031	2019-12-27 11:58:12.209	2019-12-27 11:58:12.209
1020	SEK	ILS	2.71820403489999984	2019-12-27 11:58:12.211	2019-12-27 11:58:12.211
1021	NZD	ILS	0.434588186400000021	2019-12-27 11:58:12.213	2019-12-27 11:58:12.213
1022	BRL	ILS	1.17632071549999995	2019-12-27 11:58:12.215	2019-12-27 11:58:12.215
1023	HRK	KRW	0.00578196950000000025	2019-12-27 11:58:12.544	2019-12-27 11:58:12.544
1024	CHF	KRW	0.000844755400000000013	2019-12-27 11:58:12.552	2019-12-27 11:58:12.552
1025	MXN	KRW	0.0163162513000000009	2019-12-27 11:58:12.554	2019-12-27 11:58:12.554
1026	ZAR	KRW	0.0122126875999999992	2019-12-27 11:58:12.555	2019-12-27 11:58:12.555
1027	INR	KRW	0.0613123295999999998	2019-12-27 11:58:12.557	2019-12-27 11:58:12.557
1028	THB	KRW	0.0259468358999999996	2019-12-27 11:58:12.558	2019-12-27 11:58:12.558
1029	CNY	KRW	0.00602954080000000017	2019-12-27 11:58:12.56	2019-12-27 11:58:12.56
1030	AUD	KRW	0.00124399129999999995	2019-12-27 11:58:12.561	2019-12-27 11:58:12.561
1031	ILS	KRW	0.00298700790000000016	2019-12-27 11:58:12.562	2019-12-27 11:58:12.562
1032	KRW	KRW	1	2019-12-27 11:58:12.564	2019-12-27 11:58:12.564
1033	JPY	KRW	0.0941128048999999994	2019-12-27 11:58:12.565	2019-12-27 11:58:12.565
1034	PLN	KRW	0.00330804300000000009	2019-12-27 11:58:12.566	2019-12-27 11:58:12.566
1035	GBP	KRW	0.000664225600000000046	2019-12-27 11:58:12.567	2019-12-27 11:58:12.567
1036	IDR	KRW	12.0332994230000008	2019-12-27 11:58:12.569	2019-12-27 11:58:12.569
1037	HUF	KRW	0.257635647799999978	2019-12-27 11:58:12.57	2019-12-27 11:58:12.57
1038	PHP	KRW	0.0437450979000000018	2019-12-27 11:58:12.571	2019-12-27 11:58:12.571
1039	TRY	KRW	0.00512491170000000003	2019-12-27 11:58:12.572	2019-12-27 11:58:12.572
1040	RUB	KRW	0.0534228980000000034	2019-12-27 11:58:12.573	2019-12-27 11:58:12.573
1041	HKD	KRW	0.00670104289999999981	2019-12-27 11:58:12.574	2019-12-27 11:58:12.574
1042	ISK	KRW	0.105303212699999996	2019-12-27 11:58:12.575	2019-12-27 11:58:12.575
1043	EUR	KRW	0.000776572400000000032	2019-12-27 11:58:12.576	2019-12-27 11:58:12.576
1044	DKK	KRW	0.00580192749999999986	2019-12-27 11:58:12.578	2019-12-27 11:58:12.578
1045	CAD	KRW	0.0011323978000000001	2019-12-27 11:58:12.579	2019-12-27 11:58:12.579
1046	MYR	KRW	0.00355918649999999994	2019-12-27 11:58:12.58	2019-12-27 11:58:12.58
1047	USD	KRW	0.000860442199999999959	2019-12-27 11:58:12.581	2019-12-27 11:58:12.582
1048	BGN	KRW	0.0015188202	2019-12-27 11:58:12.583	2019-12-27 11:58:12.583
1049	NOK	KRW	0.00769723000000000022	2019-12-27 11:58:12.584	2019-12-27 11:58:12.584
1050	RON	KRW	0.00371123929999999996	2019-12-27 11:58:12.585	2019-12-27 11:58:12.585
1051	SGD	KRW	0.00116617869999999994	2019-12-27 11:58:12.586	2019-12-27 11:58:12.586
1052	CZK	KRW	0.0197909466999999992	2019-12-27 11:58:12.587	2019-12-27 11:58:12.587
1053	SEK	KRW	0.00811929699999999928	2019-12-27 11:58:12.588	2019-12-27 11:58:12.588
1054	NZD	KRW	0.00129811839999999993	2019-12-27 11:58:12.589	2019-12-27 11:58:12.589
1055	BRL	KRW	0.00351367930000000012	2019-12-27 11:58:12.59	2019-12-27 11:58:12.59
1056	HRK	PLN	1.74785201180000005	2019-12-27 11:58:12.847	2019-12-27 11:58:12.847
1057	CHF	PLN	0.255364101600000004	2019-12-27 11:58:12.85	2019-12-27 11:58:12.85
1058	MXN	PLN	4.93229729100000025	2019-12-27 11:58:12.852	2019-12-27 11:58:12.852
1059	ZAR	PLN	3.69181651719999993	2019-12-27 11:58:12.853	2019-12-27 11:58:12.853
1060	INR	PLN	18.5343208600999994	2019-12-27 11:58:12.855	2019-12-27 11:58:12.855
1061	THB	PLN	7.84356073060000014	2019-12-27 11:58:12.857	2019-12-27 11:58:12.857
1062	CNY	PLN	1.82269120620000002	2019-12-27 11:58:12.858	2019-12-27 11:58:12.858
1063	AUD	PLN	0.376050518800000011	2019-12-27 11:58:12.859	2019-12-27 11:58:12.859
1064	ILS	PLN	0.902953190299999986	2019-12-27 11:58:12.86	2019-12-27 11:58:12.86
1065	KRW	PLN	302.293534907700007	2019-12-27 11:58:12.862	2019-12-27 11:58:12.862
1066	JPY	PLN	28.449692473799999	2019-12-27 11:58:12.864	2019-12-27 11:58:12.864
1067	PLN	PLN	1	2019-12-27 11:58:12.865	2019-12-27 11:58:12.865
1068	GBP	PLN	0.200791116999999991	2019-12-27 11:58:12.867	2019-12-27 11:58:12.867
1069	IDR	PLN	3637.58861918399998	2019-12-27 11:58:12.868	2019-12-27 11:58:12.868
1070	HUF	PLN	77.8815906850000061	2019-12-27 11:58:12.87	2019-12-27 11:58:12.87
1071	PHP	PLN	13.2238602750999998	2019-12-27 11:58:12.871	2019-12-27 11:58:12.871
1072	TRY	PLN	1.54922766329999995	2019-12-27 11:58:12.873	2019-12-27 11:58:12.873
1073	RUB	PLN	16.1493966853000011	2019-12-27 11:58:12.874	2019-12-27 11:58:12.874
1074	HKD	PLN	2.02568195690000019	2019-12-27 11:58:12.876	2019-12-27 11:58:12.876
1075	ISK	PLN	31.8324803980999995	2019-12-27 11:58:12.877	2019-12-27 11:58:12.877
1076	EUR	PLN	0.234752805300000006	2019-12-27 11:58:12.879	2019-12-27 11:58:12.879
1077	DKK	PLN	1.75388515889999996	2019-12-27 11:58:12.88	2019-12-27 11:58:12.88
1078	CAD	PLN	0.342316540700000005	2019-12-27 11:58:12.882	2019-12-27 11:58:12.882
1079	MYR	PLN	1.07591905719999992	2019-12-27 11:58:12.884	2019-12-27 11:58:12.884
1080	USD	PLN	0.260106108300000027	2019-12-27 11:58:12.885	2019-12-27 11:58:12.885
1081	BGN	PLN	0.459129536599999999	2019-12-27 11:58:12.887	2019-12-27 11:58:12.887
1082	NOK	PLN	2.32682285550000012	2019-12-27 11:58:12.888	2019-12-27 11:58:12.888
1083	RON	PLN	1.1218836565000001	2019-12-27 11:58:12.889	2019-12-27 11:58:12.889
1084	SGD	PLN	0.352528287699999998	2019-12-27 11:58:12.891	2019-12-27 11:58:12.891
1085	CZK	PLN	5.98267524300000009	2019-12-27 11:58:12.892	2019-12-27 11:58:12.892
1086	SEK	PLN	2.45441100519999988	2019-12-27 11:58:12.894	2019-12-27 11:58:12.894
1087	NZD	PLN	0.392412789300000009	2019-12-27 11:58:12.895	2019-12-27 11:58:12.895
1088	BRL	PLN	1.06216254280000011	2019-12-27 11:58:12.896	2019-12-27 11:58:12.896
\.


--
-- Data for Name: file; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.file (id, name, path, user_id, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: home_banner; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.home_banner (id, image_url, title, description, link, active, created_at, updated_at) FROM stdin;
2	https://i.imgur.com/ZXBtVw7.jpg	Promotion2	this is promotion2	/	f	2019-05-18 22:14:21.241	2019-05-18 22:14:21.241
4	http://www.sclance.com/backgrounds/geometric-background-hd/geometric-background-hd_1052802.png	Promotion3	this is promotion3	/	t	2019-05-18 22:41:38.613	2019-05-18 22:41:38.613
1	https://d2v9y0dukr6mq2.cloudfront.net/video/thumbnail/4RUieTItxik2c5fvx/simple-animated-geometric-background-with-squares-4k-ultra-high-definition-video-loop_hzhlwak8x_thumbnail-full01.png	Winter Promotion 10% OFF	HEY, YOU’RE NEW HERE! TAKE 10% OFF YOUR NEXT SHOP! USE CODE: MEOWNEW19	/	t	2019-05-18 22:04:06.115	2019-05-18 22:04:06.115
3	https://images.pond5.com/light-yellow-pink-polygonal-geometric-footage-086246878_prevstill.jpeg	Promotion2	this is promotion2	/	t	2019-05-18 22:41:26.38	2019-05-18 22:41:26.38
\.


--
-- Data for Name: invoice; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.invoice (id, order_id, created_at, updated_at, invoice_id) FROM stdin;
\.


--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.location (id, country_code, state_code, name, postcode, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: member_level; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.member_level (id, name, growth_point, privilege_birthday, note, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: member_price; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.member_price (id, product_id, member_level_id, member_price, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: note; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.note (id, body, user_id) FROM stdin;
\.


--
-- Data for Name: notification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notification (id, user_id, body, status_id, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: notification_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notification_status (id, code, created_at, updated_at) FROM stdin;
1	UNREAD	2019-06-25 19:57:33.678386	2019-06-25 19:57:33.678386
2	READ	2019-06-25 19:57:33.678386	2019-06-25 19:57:33.678386
\.


--
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.order_item (id, order_id, unit_price, quantity, created_at, updated_at, product_id, sku_code) FROM stdin;
30	19	0	1	2019-06-05 20:37:07.97479	2019-06-05 20:37:07.97479	9	\N
31	20	0	1	2019-06-05 21:12:33.519709	2019-06-05 21:12:33.519709	9	\N
32	21	0	1	2019-06-05 21:14:27.541612	2019-06-05 21:14:27.541612	9	\N
33	22	0	1	2019-06-06 21:18:56.348695	2019-06-06 21:18:56.348695	3	\N
35	24	0	1	2019-06-06 22:32:56.758182	2019-06-06 22:32:56.758182	6	\N
36	24	0	3	2019-06-06 22:32:56.758182	2019-06-06 22:32:56.758182	8	\N
37	25	0	10	2019-06-08 19:04:52.247951	2019-06-08 19:04:52.247951	3	\N
38	58	0	3	2019-06-28 20:32:25.651825	2019-06-28 20:32:25.651825	8	\N
39	63	0	1	2019-12-14 10:13:06.967866	2019-12-14 10:13:06.967866	12	\N
40	64	0	1	2019-12-14 10:13:12.248334	2019-12-14 10:13:12.248334	12	\N
41	65	0	1	2019-12-14 10:13:26.732041	2019-12-14 10:13:26.732041	12	\N
42	66	0	1	2019-12-14 10:16:17.064483	2019-12-14 10:16:17.064483	12	\N
43	66	0	1	2019-12-14 10:16:17.064483	2019-12-14 10:16:17.064483	11	\N
44	67	0	1	2019-12-14 10:18:21.389286	2019-12-14 10:18:21.389286	11	\N
45	67	0	1	2019-12-14 10:18:21.389286	2019-12-14 10:18:21.389286	12	\N
46	68	0	1	2019-12-14 10:19:28.905342	2019-12-14 10:19:28.905342	12	\N
47	68	0	1	2019-12-14 10:19:28.905342	2019-12-14 10:19:28.905342	11	\N
48	69	0	1	2019-12-14 10:22:42.539701	2019-12-14 10:22:42.539701	11	\N
49	69	0	1	2019-12-14 10:22:42.539701	2019-12-14 10:22:42.539701	12	\N
50	70	0	1	2019-12-14 10:23:29.353766	2019-12-14 10:23:29.353766	11	\N
51	70	0	1	2019-12-14 10:23:29.353766	2019-12-14 10:23:29.353766	12	\N
52	71	0	1	2019-12-14 10:25:24.227771	2019-12-14 10:25:24.227771	12	\N
53	71	0	1	2019-12-14 10:25:24.227771	2019-12-14 10:25:24.227771	11	\N
25	16	0	1	2019-05-31 21:49:45.725521	2019-05-31 21:49:45.725521	13	\N
26	16	0	1	2019-05-31 21:49:45.725521	2019-05-31 21:49:45.725521	6	\N
27	17	0	1	2019-06-01 19:25:01.256545	2019-06-01 19:25:01.256545	6	\N
28	17	0	1	2019-06-01 19:25:01.256545	2019-06-01 19:25:01.256545	13	\N
29	18	0	2	2019-06-04 22:38:19.964081	2019-06-04 22:38:19.964081	3	\N
54	72	0	1	2019-12-14 10:34:11.125242	2019-12-14 10:34:11.125242	11	\N
55	72	0	1	2019-12-14 10:34:11.125242	2019-12-14 10:34:11.125242	12	\N
56	73	0	1	2019-12-14 10:34:11.194571	2019-12-14 10:34:11.194571	12	\N
57	73	0	1	2019-12-14 10:34:11.194571	2019-12-14 10:34:11.194571	11	\N
58	74	3.7	1	2019-12-14 10:38:22.925708	2019-12-14 10:38:22.925708	12	\N
59	74	3.7	1	2019-12-14 10:38:22.925708	2019-12-14 10:38:22.925708	11	\N
60	75	3.7	1	2019-12-14 10:39:36.92124	2019-12-14 10:39:36.92124	12	\N
61	75	3.7	1	2019-12-14 10:39:36.92124	2019-12-14 10:39:36.92124	11	\N
62	76	3.7	1	2019-12-14 10:40:35.849267	2019-12-14 10:40:35.849267	12	\N
63	76	3.7	1	2019-12-14 10:40:35.849267	2019-12-14 10:40:35.849267	11	\N
64	77	2.5	1	2019-12-14 15:23:47.163263	2019-12-14 15:23:47.163263	32	\N
65	78	2.5	1	2019-12-14 15:24:04.465072	2019-12-14 15:24:04.465072	32	\N
66	80	2.5	1	2019-12-14 15:28:36.483173	2019-12-14 15:28:36.483173	32	\N
67	79	2.5	1	2019-12-14 15:28:36.482411	2019-12-14 15:28:36.482411	32	\N
68	81	2.5	1	2019-12-14 15:30:30.364786	2019-12-14 15:30:30.364786	32	SKU954681e03d82af88e5d16e0680a93607
69	82	2.5	1	2019-12-14 15:31:27.55158	2019-12-14 15:31:27.55158	32	SKU954681e03d82af88e5d16e0680a93607
73	86	2.5	1	2019-12-14 15:53:19.748871	2019-12-14 15:53:19.748871	32	SKU954681e03d82af88e5d16e0680a93607
\.


--
-- Data for Name: order_operate_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_operate_history (id, order_id, operate_user, order_status_id, note, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: order_return_apply; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_return_apply (id, order_id, user_id, handler_user_id, handle_note, reason, status_id, description, proof_pics, receive_man, receive_time, receive_note, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: order_return_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_return_status (id, name, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: order_shipping_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_shipping_info (id, orders_id, address1, address2, firstname, lastname, email, post_code, telephone, created_at, updated_at) FROM stdin;
1	58	test	test	test	test	tes	test	test	2019-06-28 20:33:17.782236	2019-06-28 20:33:17.782236
2	86	202/33 Coventry St	Southbank	YUE 	ZHOU	ashzhouyue@gmail.com	3006	0416886681	2019-12-14 15:56:22.740873	2019-12-14 15:56:22.740873
\.


--
-- Data for Name: order_status; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.order_status (id, name, created_at, updated_at) FROM stdin;
1	pending	2019-04-03 19:00:03.088024	2019-04-03 19:00:03.088024
2	paid	2019-04-03 19:00:03.088024	2019-04-03 19:00:03.088024
3	refund	2019-04-03 19:00:03.088024	2019-04-03 19:00:03.088024
4	token_expired	2019-04-03 19:00:03.088	2019-04-03 19:00:03.088
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.orders (id, user_id, created_at, updated_at, shipper_id, order_status_id) FROM stdin;
16	1	2019-05-31 21:49:45.725521	2019-05-31 21:49:45.725521	6	2
17	1	2019-06-01 19:25:01.256545	2019-06-01 19:25:01.256545	6	2
18	1	2019-06-04 22:38:19.964081	2019-06-04 22:38:19.964081	6	2
19	1	2019-06-05 20:37:07.97479	2019-06-05 20:37:07.97479	6	2
20	1	2019-06-05 21:12:33.519709	2019-06-05 21:12:33.519709	6	2
21	1	2019-06-05 21:14:27.541612	2019-06-05 21:14:27.541612	6	2
22	1	2019-06-06 21:18:56.348695	2019-06-06 21:18:56.348695	6	2
24	1	2019-06-06 22:32:56.758182	2019-06-06 22:32:56.758182	6	2
25	1	2019-06-08 19:04:52.247951	2019-06-08 19:04:52.247951	6	2
26	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
27	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
28	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
29	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
30	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
31	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
32	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
33	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
34	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
35	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
36	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
37	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
38	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
39	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
40	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
41	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
42	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
43	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
44	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
45	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
46	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
47	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
48	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
49	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
50	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
51	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
52	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
53	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
54	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
55	1	2019-06-08 19:04:52.247	2019-06-08 19:04:52.247	6	2
58	1	2019-06-28 20:32:25.651825	2019-06-28 20:32:25.651825	6	2
63	1	2019-12-14 10:13:06.967866	2019-12-14 10:13:06.967866	6	1
64	1	2019-12-14 10:13:12.248334	2019-12-14 10:13:12.248334	6	1
65	1	2019-12-14 10:13:26.732041	2019-12-14 10:13:26.732041	6	1
66	1	2019-12-14 10:16:17.064483	2019-12-14 10:16:17.064483	6	1
67	1	2019-12-14 10:18:21.389286	2019-12-14 10:18:21.389286	6	1
68	1	2019-12-14 10:19:28.905342	2019-12-14 10:19:28.905342	6	1
69	1	2019-12-14 10:22:42.539701	2019-12-14 10:22:42.539701	6	1
70	1	2019-12-14 10:23:29.353766	2019-12-14 10:23:29.353766	6	1
71	1	2019-12-14 10:25:24.227771	2019-12-14 10:25:24.227771	6	1
72	1	2019-12-14 10:34:11.125242	2019-12-14 10:34:11.125242	6	1
73	1	2019-12-14 10:34:11.194571	2019-12-14 10:34:11.194571	6	1
74	1	2019-12-14 10:38:22.925708	2019-12-14 10:38:22.925708	6	1
75	1	2019-12-14 10:39:36.92124	2019-12-14 10:39:36.92124	6	1
76	1	2019-12-14 10:40:35.849267	2019-12-14 10:40:35.849267	6	1
77	1	2019-12-14 15:23:47.163263	2019-12-14 15:23:47.163263	6	1
78	1	2019-12-14 15:24:04.465072	2019-12-14 15:24:04.465072	6	1
79	1	2019-12-14 15:28:36.482411	2019-12-14 15:28:36.482411	6	1
80	1	2019-12-14 15:28:36.483173	2019-12-14 15:28:36.483173	6	1
81	1	2019-12-14 15:30:30.364786	2019-12-14 15:30:30.364786	6	1
82	1	2019-12-14 15:31:27.55158	2019-12-14 15:31:27.55158	6	1
86	1	2019-12-14 15:53:19.748871	2019-12-14 15:53:19.748871	6	2
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.product (id, name, vendor_id, price, units_in_stock, created_at, updated_at, category_id, description) FROM stdin;
11	Test fruit1	5	3.7	3	2019-04-13 08:41:26.934282	2019-04-13 08:41:26.934282	1	test
12	Test fruit1	5	3.7	3	2019-04-13 08:42:43.672836	2019-04-13 08:42:43.672836	1	test
33	sku product	1	0	0	2019-11-13 22:46:37.810766	2019-11-13 22:46:37.810766	1	tttt
34	sku product	1	0	0	2019-11-13 22:48:27.120159	2019-11-13 22:48:27.120159	1	tttt
35	sku product	1	0	0	2019-11-13 22:54:08.258734	2019-11-13 22:54:08.258734	1	tttt
36	sku product	1	0	0	2019-11-13 22:54:18.177452	2019-11-13 22:54:18.177452	1	tttt
37	sku product	1	0	0	2019-11-13 22:58:03.431562	2019-11-13 22:58:03.431562	1	tttt
13	Test fruit5	5	3.7	1	2019-04-13 08:43:09.792422	2019-04-13 08:43:09.792422	1	test
22	Sku Product 8899	1	0	0	2019-11-10 22:09:13.426694	2019-11-10 22:09:13.426694	1	Sku Product 8899 dec
23	Sku Product 8955	1	0	0	2019-11-10 22:11:01.854427	2019-11-10 22:11:01.854427	1	Sku Product 8955 dec
24	Sku Product 8996	1	0	0	2019-11-10 22:12:32.753692	2019-11-10 22:12:32.753692	1	Sku Product 8996 desc
25	Sku Product 996	1	0	0	2019-11-10 22:18:04.042061	2019-11-10 22:18:04.042061	3	Sku Product 996 desc
39	Typescript	\N	0	0	2019-11-16 10:22:28.796045	2019-11-16 10:22:28.796045	2	Typescript dec
6	Tomato	4	3.7	100	2019-04-13 08:36:26.063788	2019-04-13 08:36:26.063788	1	test
9	Test fruit1	4	3.7	100	2019-04-13 08:37:53.876993	2019-04-13 08:37:53.876993	1	test
8	Potato	4	3.7	97	2019-04-13 08:37:47.458368	2019-04-13 08:37:47.458368	1	test123123
10	Test fruit1	5	3.7	3	2019-04-13 08:41:13.906516	2019-04-13 08:41:13.906516	1	test123123123123
16	sku product	1	0	0	2019-11-10 16:56:55.483156	2019-11-10 16:56:55.483156	1	tttt
18	sku product	1	0	0	2019-11-10 17:09:31.533591	2019-11-10 17:09:31.533591	1	tttt
19	sku product	1	0	0	2019-11-10 17:11:23.356239	2019-11-10 17:11:23.356239	1	tttt
20	sku product	1	0	0	2019-11-10 17:21:17.843437	2019-11-10 17:21:17.843437	1	tttt
3	Strawberry test	1	0	0	2019-04-13 08:08:55.417501	2019-04-13 08:08:55.417501	1	test
44	Test 20191208 12345678	1	0	0	2019-12-08 17:42:15.385981	2019-12-08 17:42:15.385981	17	Test 20191208
32	sku product	1	2.5	100	2019-11-13 22:45:35.365297	2019-11-13 22:45:35.365297	1	tttt
21	sku product	1	0	0	2019-11-10 17:23:46.841836	2019-11-10 17:23:46.841836	1	tttt
26	sku product	1	0	0	2019-11-13 22:30:33.341592	2019-11-13 22:30:33.341592	1	tttt
27	sku product	1	0	0	2019-11-13 22:33:48.035668	2019-11-13 22:33:48.035668	1	tttt
28	sku product	1	0	0	2019-11-13 22:35:50.056323	2019-11-13 22:35:50.056323	1	tttt
29	sku product	1	0	0	2019-11-13 22:39:52.415391	2019-11-13 22:39:52.415391	1	tttt
30	sku product	1	0	0	2019-11-13 22:40:57.530408	2019-11-13 22:40:57.530408	1	tttt
31	sku product	1	0	0	2019-11-13 22:44:43.537464	2019-11-13 22:44:43.537464	1	tttt
\.


--
-- Data for Name: product_category; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.product_category (id, name, created_at, updated_at) FROM stdin;
1	Food	\N	\N
2	electronics	2019-04-14 11:41:38.585394	2019-04-14 11:41:38.585394
3	sports & outdoors	2019-04-14 11:42:24.159437	2019-04-14 11:42:24.159437
4	test	2019-06-25 19:57:16.524064	2019-06-25 19:57:16.524064
5	test2	2019-06-25 19:57:34.351836	2019-06-25 19:57:34.351836
6	test2	2019-06-25 19:57:34.408159	2019-06-25 19:57:34.408159
7	test2	2019-06-25 20:03:44.39663	2019-06-25 20:03:44.39663
8	Food	2019-10-27 15:17:48.865367	2019-10-27 15:17:48.865367
9	1	2019-10-27 15:18:46.282674	2019-10-27 15:18:46.282674
10	123	2019-10-27 15:23:30.470987	2019-10-27 15:23:30.470987
11	1	2019-10-27 15:26:13.017565	2019-10-27 15:26:13.017565
12	1	2019-10-27 15:33:02.977127	2019-10-27 15:33:02.977127
14	123	2019-10-27 15:57:55.003755	2019-10-27 15:57:55.003755
15	123	2019-10-27 15:58:12.284822	2019-10-27 15:58:12.284822
17	asd	2019-10-27 16:00:33.383743	2019-10-27 16:00:33.383743
19	345	2019-10-27 16:08:35.638374	2019-10-27 16:08:35.638374
20	234	2019-10-27 16:12:55.758155	2019-10-27 16:12:55.758155
21	345	2019-10-27 16:13:44.541307	2019-10-27 16:13:44.541307
31	345	2019-10-27 16:34:10.697476	2019-10-27 16:34:10.697476
\.


--
-- Data for Name: product_comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_comment (id, product_id, parent_comment_id, body, user_id, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: product_meta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_meta (id, created_at, key, updated_at, value, product_id) FROM stdin;
1	\N	ASIN	\N	B07BFR7PBS	3
2	\N	Item weight	\N	300g	3
3	\N	Item weight	\N	300g	6
4	\N	ASIN	\N	B07BFR7PBS	6
5	\N	Item weight	\N	300g	8
6	\N	ASIN	\N	B07BFR7PBS	8
7	\N	ASIN	\N	B07BFR7PBS	9
8	\N	Item weight	\N	300g	9
9	\N	Item weight	\N	300g	10
10	\N	ASIN	\N	B07BFR7PBS	10
11	\N	Item weight	\N	300g	11
12	\N	ASIN	\N	B07BFR7PBS	11
13	\N	ASIN	\N	B07BFR7PBS	12
14	\N	Item weight	\N	300g	12
15	\N	Item weight	\N	300g	13
16	\N	ASIN	\N	B07BFR7PBS	13
\.


--
-- Data for Name: product_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_rating (id, product_id, user_id, rating, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.role (id, code, created_at, updated_at) FROM stdin;
1	USER	2019-04-03 21:03:17.714879	2019-04-03 21:03:17.714879
2	ADMIN	2019-04-03 21:03:22.271768	2019-04-03 21:03:22.271768
3	VENDOR	2019-04-03 21:03:28.057656	2019-04-03 21:03:28.057656
4	TEACHER	2019-06-30 16:47:34.368076	2019-06-30 16:47:34.368076
\.


--
-- Data for Name: shipper; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public.shipper (id, name, contact, created_at, updated_at) FROM stdin;
6	Cainiao3	555	2019-04-29 21:03:32.405638	2019-04-29 21:03:32.405638
40	test2	test222	2019-12-14 09:35:30.350623	2019-12-14 09:35:30.350623
39	Shunfeng	123456789	2019-12-14 09:34:46.253891	2019-12-14 09:34:46.253891
\.


--
-- Data for Name: sku; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sku (id, product_id, sku_code, price, stock, created_user_id, created_at, updated_at) FROM stdin;
17	20	SKUb1389e450c316c79e7d887ad28d24967	34	345345	1	2019-11-10 17:21:17.865779	2019-11-10 17:21:17.865779
18	21	SKUbb774725ba5d49484d4008cd7c13e13f	555555	345345	1	2019-11-10 17:23:46.863341	2019-11-10 17:23:46.863341
22	21	SKUde8705b5c008b3304054d62926bf8ce3	546	456456	1	2019-11-10 18:44:33.831286	2019-11-10 18:44:33.831286
23	21	SKU4898e683d7d05df09c9f02d8dedd5f1d	546	456456	1	2019-11-10 18:44:39.229462	2019-11-10 18:44:39.229462
24	21	SKU580205caeb82e3a70f596db29107888a	546	456456	1	2019-11-10 18:47:13.142165	2019-11-10 18:47:13.142165
25	21	SKUe5f2fa59c9ffae6f041accd8719a9a79	546	456456	1	2019-11-10 18:48:25.233639	2019-11-10 18:48:25.233639
26	21	SKU61e31a853047a0aa38c866ffc7eb92f4	45645	456456456	1	2019-11-10 19:01:35.672978	2019-11-10 19:01:35.672978
27	21	SKUcb2cd0618f29f1daba649daa17c2ddbe	456	4890	1	2019-11-10 19:08:01.216295	2019-11-10 19:08:01.216295
28	21	SKUb921ee9d79a26d702c0587b9ecbfee76	7.89	1200	1	2019-11-10 19:48:29.941665	2019-11-10 19:48:29.941665
32	22	SKU2821988dada03ee8444701e63c2ab830	35	897576	1	2019-11-10 22:09:13.47591	2019-11-10 22:09:13.47591
33	23	SKU59ec3dbab7c384840e4773563ebc75ba	345	3453	1	2019-11-10 22:11:01.862286	2019-11-10 22:11:01.862286
34	24	SKUfc1b666ec924571c009eadf58aec00f1	456.77	456	1	2019-11-10 22:12:32.761319	2019-11-10 22:12:32.761319
35	25	SKU0c06cb426504202357fb413c92594964	345	345345	1	2019-11-10 22:18:04.046147	2019-11-10 22:18:04.046147
37	27	SKU94880a2f55a2dc332c064fe447e78b30	34	345345	1	2019-11-13 22:33:48.057874	2019-11-13 22:33:48.057874
38	28	SKUa24ac4ef4be90ba89829af81b8a1db96	34	345345	1	2019-11-13 22:35:50.064413	2019-11-13 22:35:50.064413
39	29	SKU2042742bc6916f42237c67842ae4972a	34	345345	1	2019-11-13 22:39:52.415391	2019-11-13 22:39:52.415391
40	30	SKU7abf337d357674f969f6a403e8d1eca8	34	345345	1	2019-11-13 22:40:57.530408	2019-11-13 22:40:57.530408
41	31	SKU6b1465e1b0146f527f7592bf4d1a7350	34	345345	1	2019-11-13 22:44:43.537464	2019-11-13 22:44:43.537464
43	33	SKU459ab646675256649dafb19b1d5583b1	34	345345	1	2019-11-13 22:46:37.83607	2019-11-13 22:46:37.83607
44	34	SKUa009ae01a1aa6d673f57a25df9d38b30	34	345345	1	2019-11-13 22:48:27.120159	2019-11-13 22:48:27.120159
45	35	SKUc89a9aa05cc2d49823119f4741902c8d	34	345345	1	2019-11-13 22:54:08.258734	2019-11-13 22:54:08.258734
46	36	SKU57ec7eff07e5f8fe4efbfe41ec207a42	34	345345	1	2019-11-13 22:54:18.177452	2019-11-13 22:54:18.177452
47	37	SKU13b0a01a5fbfe0fc0e820a298054e7e3	34	345345	1	2019-11-13 22:58:03.431562	2019-11-13 22:58:03.431562
49	39	SKU9c607df961cec217bc6bbe729594f089	45	54645	1	2019-11-16 10:22:28.796045	2019-11-16 10:22:28.796045
1	3	SKU406d91a7810cdab64b98047ef2a73b42	34.5	1005	1	2019-10-14 20:47:03.337562	2019-10-14 20:47:03.337562
63	44	SKU3dbee577917a9d902697699f1eb48a04	123	5656	1	2019-12-08 17:42:15.385981	2019-12-08 17:42:15.385981
64	11	SKUb1389e450c316c79e7d887ad28d24967123	34	345345	1	2019-11-10 17:21:17.865	2019-11-10 17:21:17.865
65	12	SKUb1389e450c316c79e7d887ad28d24967888	34	345345	1	2019-11-10 17:21:17.865	2019-11-10 17:21:17.865
42	32	SKU954681e03d82af88e5d16e0680a93607	34	345344	1	2019-11-13 22:45:35.365297	2019-11-13 22:45:35.365297
\.


--
-- Data for Name: sku_attribute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sku_attribute (id, sku_code, sku_attribute_category_id, value, created_at, updated_at) FROM stdin;
1	SKU406d91a7810cdab64b98047ef2a73b42	1	S	2019-10-27 10:10:38.61415	2019-10-27 10:10:38.61415
2	SKU406d91a7810cdab64b98047ef2a73b42	1	XS	2019-10-27 10:10:45.484857	2019-10-27 10:10:45.484857
3	SKU406d91a7810cdab64b98047ef2a73b42	1	M	2019-10-27 10:10:48.960893	2019-10-27 10:10:48.960893
4	SKU406d91a7810cdab64b98047ef2a73b42	1	L	2019-10-27 10:10:52.918918	2019-10-27 10:10:52.918918
5	SKU406d91a7810cdab64b98047ef2a73b42	1	XXL	2019-10-27 10:10:56.113932	2019-10-27 10:10:56.113932
10	SKUbb774725ba5d49484d4008cd7c13e13f	2	White	2019-11-10 17:23:46.874139	2019-11-10 17:23:46.874139
11	SKUbb774725ba5d49484d4008cd7c13e13f	2	Black	2019-11-10 17:23:46.881012	2019-11-10 17:23:46.881012
12	SKUe5f2fa59c9ffae6f041accd8719a9a79	2	Purple	2019-11-10 18:48:25.272305	2019-11-10 18:48:25.272305
13	SKUe5f2fa59c9ffae6f041accd8719a9a79	2	Orange	2019-11-10 18:48:25.289792	2019-11-10 18:48:25.289792
15	SKU61e31a853047a0aa38c866ffc7eb92f4	2	Orange	2019-11-10 19:47:45.220549	2019-11-10 19:47:45.220549
16	SKUb921ee9d79a26d702c0587b9ecbfee76	1	M	2019-11-10 19:48:29.953029	2019-11-10 19:48:29.953029
18	SKUcb2cd0618f29f1daba649daa17c2ddbe	2	Violet	2019-11-10 19:53:10.40309	2019-11-10 19:53:10.40309
19	SKU59ec3dbab7c384840e4773563ebc75ba	2	Pink	2019-11-10 22:11:01.865713	2019-11-10 22:11:01.865713
20	SKUfc1b666ec924571c009eadf58aec00f1	2	Pink	2019-11-10 22:12:32.763166	2019-11-10 22:12:32.763166
21	SKU0c06cb426504202357fb413c92594964	2	Green	2019-11-10 22:18:04.047953	2019-11-10 22:18:04.047953
22	SKU0c06cb426504202357fb413c92594964	2	Pink	2019-11-10 22:18:04.049878	2019-11-10 22:18:04.049878
23	SKUa24ac4ef4be90ba89829af81b8a1db96	2	White	2019-11-13 22:35:50.072989	2019-11-13 22:35:50.072989
24	SKUa24ac4ef4be90ba89829af81b8a1db96	2	Red	2019-11-13 22:35:50.077384	2019-11-13 22:35:50.077384
25	SKU2042742bc6916f42237c67842ae4972a	2	Red	2019-11-13 22:39:52.415391	2019-11-13 22:39:52.415391
26	SKU2042742bc6916f42237c67842ae4972a	2	White	2019-11-13 22:39:52.415391	2019-11-13 22:39:52.415391
27	SKU7abf337d357674f969f6a403e8d1eca8	2	Red	2019-11-13 22:40:57.530408	2019-11-13 22:40:57.530408
28	SKU7abf337d357674f969f6a403e8d1eca8	2	White	2019-11-13 22:40:57.530408	2019-11-13 22:40:57.530408
29	SKU6b1465e1b0146f527f7592bf4d1a7350	2	White	2019-11-13 22:44:43.537464	2019-11-13 22:44:43.537464
30	SKU6b1465e1b0146f527f7592bf4d1a7350	2	Red	2019-11-13 22:44:43.537464	2019-11-13 22:44:43.537464
31	SKU954681e03d82af88e5d16e0680a93607	2	Red	2019-11-13 22:45:35.365297	2019-11-13 22:45:35.365297
32	SKU954681e03d82af88e5d16e0680a93607	2	White	2019-11-13 22:45:35.365297	2019-11-13 22:45:35.365297
33	SKU459ab646675256649dafb19b1d5583b1	2	White	2019-11-13 22:46:37.848335	2019-11-13 22:46:37.848335
34	SKU459ab646675256649dafb19b1d5583b1	2	Red	2019-11-13 22:46:37.851393	2019-11-13 22:46:37.851393
35	SKUa009ae01a1aa6d673f57a25df9d38b30	2	White	2019-11-13 22:48:27.120159	2019-11-13 22:48:27.120159
36	SKUa009ae01a1aa6d673f57a25df9d38b30	2	Red	2019-11-13 22:48:27.120159	2019-11-13 22:48:27.120159
37	SKUc89a9aa05cc2d49823119f4741902c8d	2	White	2019-11-13 22:54:08.258734	2019-11-13 22:54:08.258734
38	SKUc89a9aa05cc2d49823119f4741902c8d	2	Red	2019-11-13 22:54:08.258734	2019-11-13 22:54:08.258734
39	SKU57ec7eff07e5f8fe4efbfe41ec207a42	2	Red	2019-11-13 22:54:18.177452	2019-11-13 22:54:18.177452
40	SKU57ec7eff07e5f8fe4efbfe41ec207a42	2	White	2019-11-13 22:54:18.177452	2019-11-13 22:54:18.177452
41	SKU13b0a01a5fbfe0fc0e820a298054e7e3	2	White	2019-11-13 22:58:03.431562	2019-11-13 22:58:03.431562
42	SKU13b0a01a5fbfe0fc0e820a298054e7e3	2	Red	2019-11-13 22:58:03.431562	2019-11-13 22:58:03.431562
43	SKU9c607df961cec217bc6bbe729594f089	1	M	2019-11-16 10:22:28.796045	2019-11-16 10:22:28.796045
44	SKU9c607df961cec217bc6bbe729594f089	1	S	2019-11-16 10:22:28.796045	2019-11-16 10:22:28.796045
45	SKU406d91a7810cdab64b98047ef2a73b42	1	XXXXL	2019-12-08 16:05:58.443606	2019-12-08 16:05:58.443606
7	SKU406d91a7810cdab64b98047ef2a73b42	1	XXXL	2019-11-07 21:38:10.641969	2019-11-07 21:38:10.641969
50	SKU3dbee577917a9d902697699f1eb48a04	1	S	2019-12-08 17:42:15.385981	2019-12-08 17:42:15.385981
51	SKU3dbee577917a9d902697699f1eb48a04	1	L	2019-12-08 17:42:15.385981	2019-12-08 17:42:15.385981
\.


--
-- Data for Name: sku_attribute_category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sku_attribute_category (id, name, created_at, updated_at) FROM stdin;
1	Size	2019-10-27 10:09:57.931978	2019-10-27 10:09:57.931978
2	Color	2019-11-05 12:12:52.228811	2019-11-05 12:12:52.228811
\.


--
-- Data for Name: swagger_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.swagger_config (id, name, value, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: teacher_available_time; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.teacher_available_time (id, user_id, start_time, end_time, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: teacher_meta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.teacher_meta (id, user_id, intro, country_code, avatar, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: teacher_subscription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.teacher_subscription (id, teacher_id, user_id, started_at, expired_at, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: yuezhou
--

COPY public."user" (id, password, created_at, updated_at, role_id, telephone, address1, address2, firstname, lastname, gender, email, username) FROM stdin;
4	$2a$10$Dyaompt3BaHGHWdTT6Szq.cs4/TCd52blPqiXGD.owK7ndzl5qLIi	2019-04-13 08:26:42.37925	2019-04-13 08:26:42.37925	2	123	addr1	addr2	joe	zhou	m	joeadmin@gmail.com	joeadmin
5	$2a$10$Kvu.OEsMICEt3aCX.G2HG.eWN7QzzYbtEUmzyocUTmEZY99icuS5G	2019-04-13 08:40:45.36358	2019-04-13 08:40:45.36358	3	123	addr1	addr2	joe	zhou	m	joevendor@gmail.com	joevendor
21	$2a$10$BwSs50UllejUeGdqQO/ucOFoHz7.cdYGkZQ595tym5V80jiXNJvMq	2019-06-20 01:02:13.057322	2019-06-20 01:02:13.057322	1	1234	1234	1234	test123	test123	m	joe915@gmail.com	joe915
23	$2a$10$jRoFrzQzQR3Rfql4KoVQqOPgkxoI0odp1vcVztnb94Ci9B5dLZGae	2019-06-20 01:04:31.595124	2019-06-20 01:04:31.595124	1	1234	1234	1234	test123	test123	m	joe917@gmail.com	joe917
25	$2a$10$USVHkMMpm5R7aQIWJNui5Ovjre17km6qrmeZiQ9MUHGKirn2UXxQm	2019-06-20 01:04:56.958804	2019-06-20 01:04:56.958804	1	1234	1234	1234	test123	test123	m	joe919@gmail.com	joe919
27	$2a$10$/4w0ofiEC3AwdV09eoNypOtY7BUE3m1Hg/J1t.x4IWvrHnbgZxsM6	2019-06-20 01:08:15.781007	2019-06-20 01:08:15.781007	1	1234	1234	1234	test123	test123	m	joe925@gmail.com	joe925
29	$2a$10$qxw76nrXnvUzGwL/djzDPO.lp.ZCbFlD8cnNOM/1dT1E3O13gSRQS	2019-06-20 01:08:41.690508	2019-06-20 01:08:41.690508	1	1234	1234	1234	test123	test123	m	joe930@gmail.com	joe930
14	$2a$10$6RvnSoYaAmJ5Q0bf.Zvf5O4i466/4x/JUzZ6Ahg3TYAlmwr7C4rWO	2019-06-20 00:36:52.055608	2019-06-20 00:36:52.055608	1	14	address1	address2	joe	zhou	m	joe997@gmail.com	joe997
16	$2a$10$IdnoXoe37z0ES8KN04v5.Oniy.3bqG3H3yKHh1S8KvONTdDdgzpwm	2019-06-20 00:57:08.558103	2019-06-20 00:57:08.558103	1	16	6666666	1234	test123	test123	m	joe910@gmail.com	joe910
12	$2a$10$baD/vGp8MY0yXXdLk/F0HuG0eogGdlpKVztm.XsemUVlxOe0G76S.	2019-06-20 00:32:10.402128	2019-06-20 00:32:10.402128	1	12	address1	address2	joe	zhou	m	joe996@gmail.com	joe996
8	$2a$10$tgrGtkkyoCbb4jgtY4RB4eWUWYwOHp8OSW2PyvToTLSNHHzPRmNnC	2019-04-30 20:51:34.714523	2019-04-30 20:51:34.714523	1	8	addr1	addr2	joe	zhou	m	shepi888@gmail.com	shepi888
10	$2a$10$NnXfYG1eNQmpS509HDvDJ.AEF6Jdd35o8nyGxGHgLcQwiGxmhQj6S	2019-06-16 22:42:59.017726	2019-06-16 22:42:59.017726	1	10	10	address255555577777	test123	test123	m	testdddddddddd@gmail.com	test
19	$2a$10$cM/fi7RpnXsAlOIc7p5z7eii5Xti448TFuIK9vf.TAE2kixo.pxWC	2019-06-20 00:58:42.32075	2019-06-20 00:58:42.32075	1	13915987230	1234555555	1234	test123	test123	m	joe912@gmail.com	joe912
34	$2a$10$9/nOLAw9/2.WfrrNaaFig.tjn/Z1b3isReBqCl4MqCwdvRmdbfneK	2019-10-21 21:32:47.976777	2019-10-21 21:32:47.976777	1	546456456	456456	456456	fdgdfg	dfg	n	test89757@gmail.com	test89757
1	$2a$10$ZtoESuUGNJfe0ABvVMv/kuMIRC1ElpQR7693TIGhq7szNH/RlfD3.	2019-04-03 21:05:54.945184	2019-04-03 21:05:54.945184	1	041688668678	addr123667	addr2	yue	zhou	M	ashzhouyue@gmail.com	joe999
2	$2a$10$F3BkasN6NdHYsMB1JOn98u3SUpwWq737EqLFxcWsRoAucGNlsUMNW	2019-11-23 21:17:57.043738	2019-11-23 21:17:57.043738	1	12123123	joe445a	joe445a	joe445a	joe445a	M	joe445a@gmail.com	joe445a
3	$2a$10$Tsyq8.JCEQYcVC/I7APXAecoKtj6IYj770aSJKMKhocWpifB0I2OK	2019-11-23 21:33:10.432894	2019-11-23 21:33:10.432894	1	123123123	joe885	joe885	joe885	joe885	M	joe885@gmail.com	joe885
\.


--
-- Data for Name: user_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_log (id, action, user_id, ip_addr, created_at, updated_at) FROM stdin;
1	/	-1	127.0.0.1	2019-12-27 11:57:59.170791	2019-12-27 11:57:59.170791
2	/	-1	127.0.0.1	2019-12-27 11:57:59.614024	2019-12-27 11:57:59.614024
3	/	-1	0:0:0:0:0:0:0:1	2019-12-27 11:57:59.680681	2019-12-27 11:57:59.680681
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (id, user_id, role_id, created_at, updated_at) FROM stdin;
56	4	1	2019-06-25 20:00:58.135	2019-06-25 20:00:58.135
57	4	2	2019-06-25 20:01:43.734	2019-06-25 20:01:43.734
59	1	1	2019-06-25 21:27:14.812	2019-06-25 21:27:14.812
60	1	2	2019-06-25 21:27:17.283	2019-06-25 21:27:17.283
61	1	3	2019-06-25 21:27:18.931	2019-06-25 21:27:18.931
63	4	4	2019-07-01 22:15:33.064	2019-07-01 22:15:33.064
64	5	4	2019-07-01 22:15:46.097	2019-07-01 22:15:46.097
12	1	4	2019-11-23 16:36:52.101156	2019-11-23 16:36:52.101156
13	2	1	2019-11-23 21:17:57.05947	2019-11-23 21:17:57.05947
14	3	1	2019-11-23 21:33:10.441546	2019-11-23 21:33:10.441546
\.


--
-- Data for Name: user_shipping_preference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_shipping_preference (id, user_id, first_name, last_name, email, phone, address1, address2, post_code, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: wish_list; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.wish_list (id, user_id, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: wish_list_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.wish_list_item (id, wish_list_id, product_id, quantity, created_at, updated_at) FROM stdin;
\.


--
-- Name: api_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.api_category_id_seq', 30, true);


--
-- Name: api_info_header_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.api_info_header_id_seq', 8, true);


--
-- Name: api_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.api_info_id_seq', 28, true);


--
-- Name: cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.cart_id_seq', 1, true);


--
-- Name: cart_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.cart_item_id_seq', 29, true);


--
-- Name: class_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.class_id_seq', 1, false);


--
-- Name: class_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.class_order_id_seq', 1, false);


--
-- Name: currency_rates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.currency_rates_id_seq', 1088, true);


--
-- Name: file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.file_id_seq', 1, false);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 40, true);


--
-- Name: home_banner_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.home_banner_id_seq', 1, false);


--
-- Name: invoice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.invoice_id_seq', 1, false);


--
-- Name: location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.location_id_seq', 1, false);


--
-- Name: member_level_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.member_level_id_seq', 1, false);


--
-- Name: member_price_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.member_price_id_seq', 1, false);


--
-- Name: note_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.note_id_seq', 1, false);


--
-- Name: notification_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notification_id_seq', 1, false);


--
-- Name: notification_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notification_status_id_seq', 2, true);


--
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.order_id_seq', 86, true);


--
-- Name: order_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.order_item_id_seq', 73, true);


--
-- Name: order_operate_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_operate_history_id_seq', 1, false);


--
-- Name: order_return_apply_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_return_apply_id_seq', 1, false);


--
-- Name: order_return_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_return_status_id_seq', 1, false);


--
-- Name: order_shipping_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_shipping_info_id_seq', 2, true);


--
-- Name: order_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.order_status_id_seq', 3, true);


--
-- Name: product_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.product_category_id_seq', 31, true);


--
-- Name: product_comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_comment_id_seq', 1, false);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.product_id_seq', 44, true);


--
-- Name: product_meta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_meta_id_seq', 16, true);


--
-- Name: product_rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_rating_id_seq', 1, false);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.role_id_seq', 5, true);


--
-- Name: shipper_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.shipper_id_seq', 1, false);


--
-- Name: sku_attribute_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sku_attribute_category_id_seq', 3, true);


--
-- Name: sku_attribute_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sku_attribute_id_seq', 51, true);


--
-- Name: sku_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sku_id_seq', 65, true);


--
-- Name: swagger_config_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.swagger_config_id_seq', 1, false);


--
-- Name: teacher_available_time_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.teacher_available_time_id_seq', 1, false);


--
-- Name: teacher_meta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.teacher_meta_id_seq', 3, true);


--
-- Name: teacher_subscription_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.teacher_subscription_id_seq', 1, false);


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: yuezhou
--

SELECT pg_catalog.setval('public.user_id_seq', 3, true);


--
-- Name: user_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_log_id_seq', 3, true);


--
-- Name: user_roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_roles_id_seq', 14, true);


--
-- Name: user_shipping_preference_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_shipping_preference_id_seq', 1, false);


--
-- Name: wish_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wish_list_id_seq', 1, false);


--
-- Name: wish_list_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wish_list_item_id_seq', 1, false);


--
-- Name: api_category api_category_name_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_category
    ADD CONSTRAINT api_category_name_ukey UNIQUE (name);


--
-- Name: api_category api_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_category
    ADD CONSTRAINT api_category_pkey PRIMARY KEY (id);


--
-- Name: api_info_header api_info_header_api_info_id_name_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_info_header
    ADD CONSTRAINT api_info_header_api_info_id_name_ukey UNIQUE (api_info_id, name);


--
-- Name: api_info_header api_info_header_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_info_header
    ADD CONSTRAINT api_info_header_pkey PRIMARY KEY (id);


--
-- Name: api_info api_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_info
    ADD CONSTRAINT api_info_pkey PRIMARY KEY (id);


--
-- Name: cart_item cart_item_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_pkey PRIMARY KEY (id);


--
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);


--
-- Name: cart_item cart_product_ukey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_product_ukey UNIQUE (cart_id, product_id);


--
-- Name: class_order class_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class_order
    ADD CONSTRAINT class_order_pkey PRIMARY KEY (id);


--
-- Name: class class_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class
    ADD CONSTRAINT class_pkey PRIMARY KEY (id);


--
-- Name: currency_rates currency_code_base_currency_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.currency_rates
    ADD CONSTRAINT currency_code_base_currency_ukey UNIQUE (currency_code, base_currency);


--
-- Name: currency_rates currency_rates_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.currency_rates
    ADD CONSTRAINT currency_rates_pkey PRIMARY KEY (id);


--
-- Name: file file_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (id);


--
-- Name: invoice invoice_invoice_id_key; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_invoice_id_key UNIQUE (invoice_id);


--
-- Name: invoice invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_pkey PRIMARY KEY (id);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- Name: member_level member_level_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_level
    ADD CONSTRAINT member_level_pkey PRIMARY KEY (id);


--
-- Name: member_price member_price_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_price
    ADD CONSTRAINT member_price_pkey PRIMARY KEY (id);


--
-- Name: note note_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.note
    ADD CONSTRAINT note_pkey PRIMARY KEY (id);


--
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);


--
-- Name: notification_status notification_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification_status
    ADD CONSTRAINT notification_status_pkey PRIMARY KEY (id);


--
-- Name: order_item order_item_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_pkey PRIMARY KEY (id);


--
-- Name: order_operate_history order_operate_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_operate_history
    ADD CONSTRAINT order_operate_history_pkey PRIMARY KEY (id);


--
-- Name: orders order_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- Name: order_return_apply order_return_apply_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_apply
    ADD CONSTRAINT order_return_apply_pkey PRIMARY KEY (id);


--
-- Name: order_return_status order_return_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_status
    ADD CONSTRAINT order_return_status_pkey PRIMARY KEY (id);


--
-- Name: order_shipping_info order_shipping_info_orders_id_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_shipping_info
    ADD CONSTRAINT order_shipping_info_orders_id_ukey UNIQUE (orders_id);


--
-- Name: order_shipping_info order_shipping_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_shipping_info
    ADD CONSTRAINT order_shipping_info_pkey PRIMARY KEY (id);


--
-- Name: order_status order_status_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.order_status
    ADD CONSTRAINT order_status_pkey PRIMARY KEY (id);


--
-- Name: product_category product_category_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (id);


--
-- Name: product_comment product_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_comment
    ADD CONSTRAINT product_comment_pkey PRIMARY KEY (id);


--
-- Name: product_rating product_id_user_id_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rating
    ADD CONSTRAINT product_id_user_id_ukey UNIQUE (product_id, user_id);


--
-- Name: product_meta product_meta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_meta
    ADD CONSTRAINT product_meta_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: product_rating product_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rating
    ADD CONSTRAINT product_rating_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: shipper shipper_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.shipper
    ADD CONSTRAINT shipper_pkey PRIMARY KEY (id);


--
-- Name: sku_attribute_category sku_attribute_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku_attribute_category
    ADD CONSTRAINT sku_attribute_category_pkey PRIMARY KEY (id);


--
-- Name: sku_attribute sku_attribute_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku_attribute
    ADD CONSTRAINT sku_attribute_pkey PRIMARY KEY (id);


--
-- Name: sku sku_code_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku
    ADD CONSTRAINT sku_code_ukey UNIQUE (sku_code);


--
-- Name: sku sku_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku
    ADD CONSTRAINT sku_pkey PRIMARY KEY (id);


--
-- Name: teacher_available_time teacher_available_time_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_available_time
    ADD CONSTRAINT teacher_available_time_pkey PRIMARY KEY (id);


--
-- Name: teacher_meta teacher_meta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_meta
    ADD CONSTRAINT teacher_meta_pkey PRIMARY KEY (id);


--
-- Name: teacher_meta teacher_meta_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_meta
    ADD CONSTRAINT teacher_meta_ukey UNIQUE (user_id);


--
-- Name: teacher_subscription teacher_subscription_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_subscription
    ADD CONSTRAINT teacher_subscription_pkey PRIMARY KEY (id);


--
-- Name: teacher_subscription teacher_subscription_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_subscription
    ADD CONSTRAINT teacher_subscription_ukey UNIQUE (teacher_id);


--
-- Name: user user_email_key; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_email_key UNIQUE (email);


--
-- Name: user_log user_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_log
    ADD CONSTRAINT user_log_pkey PRIMARY KEY (id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (id);


--
-- Name: user_roles user_roles_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_ukey UNIQUE (user_id, role_id);


--
-- Name: user_shipping_preference user_shipping_preference_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_shipping_preference
    ADD CONSTRAINT user_shipping_preference_pkey PRIMARY KEY (id);


--
-- Name: user_shipping_preference user_shipping_preference_user_id_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_shipping_preference
    ADD CONSTRAINT user_shipping_preference_user_id_ukey UNIQUE (user_id);


--
-- Name: user user_username_key; Type: CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_username_key UNIQUE (username);


--
-- Name: wish_list_item wish_list_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list_item
    ADD CONSTRAINT wish_list_item_pkey PRIMARY KEY (id);


--
-- Name: wish_list wish_list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list
    ADD CONSTRAINT wish_list_pkey PRIMARY KEY (id);


--
-- Name: wish_list_item wish_list_product_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list_item
    ADD CONSTRAINT wish_list_product_ukey UNIQUE (wish_list_id, product_id);


--
-- Name: sku_attribute_sku_code_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX sku_attribute_sku_code_idx ON public.sku_attribute USING btree (sku_code);


--
-- Name: sku_created_user_id_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX sku_created_user_id_idx ON public.sku USING btree (created_user_id);


--
-- Name: sku_sku_code_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX sku_sku_code_idx ON public.sku USING btree (sku_code);


--
-- Name: api_info api_info_api_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_info
    ADD CONSTRAINT api_info_api_category_id_fkey FOREIGN KEY (api_category_id) REFERENCES public.api_category(id);


--
-- Name: api_info_header api_info_header_api_info_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.api_info_header
    ADD CONSTRAINT api_info_header_api_info_id_fkey FOREIGN KEY (api_info_id) REFERENCES public.api_info(id);


--
-- Name: cart_item cart_item_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.cart(id);


--
-- Name: cart_item cart_item_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: cart cart_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: class_order class_order_class_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class_order
    ADD CONSTRAINT class_order_class_id_fkey FOREIGN KEY (class_id) REFERENCES public.class(id);


--
-- Name: class_order class_order_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class_order
    ADD CONSTRAINT class_order_status_id_fkey FOREIGN KEY (status_id) REFERENCES public.order_status(id);


--
-- Name: class_order class_order_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class_order
    ADD CONSTRAINT class_order_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: class class_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.class
    ADD CONSTRAINT class_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public."user"(id);


--
-- Name: file file_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: orders fk_order_status_id; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_order_status_id FOREIGN KEY (order_status_id) REFERENCES public.order_status(id);


--
-- Name: product_meta fknrj529uycuyprik999f192i33; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_meta
    ADD CONSTRAINT fknrj529uycuyprik999f192i33 FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: invoice invoice_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: member_price member_price_member_level_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_price
    ADD CONSTRAINT member_price_member_level_id_fkey FOREIGN KEY (member_level_id) REFERENCES public.member_level(id);


--
-- Name: member_price member_price_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_price
    ADD CONSTRAINT member_price_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: note note_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.note
    ADD CONSTRAINT note_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: notification notification_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_status_id_fkey FOREIGN KEY (status_id) REFERENCES public.notification_status(id);


--
-- Name: notification notification_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: order_item order_item_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_item order_item_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: order_operate_history order_operate_history_operate_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_operate_history
    ADD CONSTRAINT order_operate_history_operate_user_fkey FOREIGN KEY (operate_user) REFERENCES public."user"(id);


--
-- Name: order_operate_history order_operate_history_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_operate_history
    ADD CONSTRAINT order_operate_history_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: order_operate_history order_operate_history_order_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_operate_history
    ADD CONSTRAINT order_operate_history_order_status_id_fkey FOREIGN KEY (order_status_id) REFERENCES public.order_status(id);


--
-- Name: order_return_apply order_return_apply_handler_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_apply
    ADD CONSTRAINT order_return_apply_handler_user_id_fkey FOREIGN KEY (handler_user_id) REFERENCES public."user"(id);


--
-- Name: order_return_apply order_return_apply_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_apply
    ADD CONSTRAINT order_return_apply_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: order_return_apply order_return_apply_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_apply
    ADD CONSTRAINT order_return_apply_status_id_fkey FOREIGN KEY (status_id) REFERENCES public.order_return_status(id);


--
-- Name: order_return_apply order_return_apply_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_return_apply
    ADD CONSTRAINT order_return_apply_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: orders order_shipper_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_shipper_id_fkey FOREIGN KEY (shipper_id) REFERENCES public.shipper(id);


--
-- Name: order_shipping_info order_shipping_info_orders_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_shipping_info
    ADD CONSTRAINT order_shipping_info_orders_id_fkey FOREIGN KEY (orders_id) REFERENCES public.orders(id);


--
-- Name: orders order_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON DELETE CASCADE;


--
-- Name: product product_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.product_category(id);


--
-- Name: product_comment product_comment_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_comment
    ADD CONSTRAINT product_comment_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_comment product_comment_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_comment
    ADD CONSTRAINT product_comment_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: product_rating product_rating_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rating
    ADD CONSTRAINT product_rating_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_rating product_rating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_rating
    ADD CONSTRAINT product_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: product product_vendor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_vendor_id_fkey FOREIGN KEY (vendor_id) REFERENCES public."user"(id);


--
-- Name: sku_attribute sku_attribute_sku_attribute_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku_attribute
    ADD CONSTRAINT sku_attribute_sku_attribute_category_id_fkey FOREIGN KEY (sku_attribute_category_id) REFERENCES public.sku_attribute_category(id);


--
-- Name: sku_attribute sku_attribute_sku_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku_attribute
    ADD CONSTRAINT sku_attribute_sku_code_fkey FOREIGN KEY (sku_code) REFERENCES public.sku(sku_code);


--
-- Name: sku sku_created_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku
    ADD CONSTRAINT sku_created_user_id_fkey FOREIGN KEY (created_user_id) REFERENCES public."user"(id);


--
-- Name: sku sku_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sku
    ADD CONSTRAINT sku_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: teacher_available_time teacher_available_time_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_available_time
    ADD CONSTRAINT teacher_available_time_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: teacher_meta teacher_meta_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_meta
    ADD CONSTRAINT teacher_meta_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: teacher_subscription teacher_subscription_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_subscription
    ADD CONSTRAINT teacher_subscription_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public."user"(id);


--
-- Name: teacher_subscription teacher_subscription_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teacher_subscription
    ADD CONSTRAINT teacher_subscription_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: user user_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yuezhou
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- Name: user_roles user_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- Name: user_roles user_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: user_shipping_preference user_shipping_preference_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_shipping_preference
    ADD CONSTRAINT user_shipping_preference_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: wish_list_item wish_list_item_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list_item
    ADD CONSTRAINT wish_list_item_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: wish_list_item wish_list_item_wish_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list_item
    ADD CONSTRAINT wish_list_item_wish_list_id_fkey FOREIGN KEY (wish_list_id) REFERENCES public.wish_list(id);


--
-- Name: wish_list wish_list_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wish_list
    ADD CONSTRAINT wish_list_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- PostgreSQL database dump complete
--

