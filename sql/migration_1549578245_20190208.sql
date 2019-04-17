-- Table: public.file

SET CLIENT_MIN_MESSAGES = WARNING;

BEGIN;
	-- Create public.roles table
	
	DROP TABLE IF EXISTS public.role;
	
	CREATE TABLE public.role
	(
	  id serial,
	  code text NOT NULL,
	  created_at timestamp without time zone DEFAULT now(),
	  updated_at timestamp without time zone DEFAULT now(),
	  CONSTRAINT role_pkey PRIMARY KEY (id)
	);
	
	-- Create public.users table
	
	DROP TABLE IF EXISTS public.user;
	
	CREATE TABLE public.user
	(
	  id serial,
	  password text NOT NULL,
	  created_at timestamp without time zone DEFAULT now(),
	  updated_at timestamp without time zone DEFAULT now(),
	  role_id integer NOT NULL DEFAULT 1,
	  telephone text NOT NULL,
	  address1 character varying(50) NOT NULL,
	  address2 character varying(50) NOT NULL,
	  firstname character varying(50) NOT NULL,
	  lastname character varying(50) NOT NULL,
	  gender character varying(2) NOT NULL,
	  email character varying(50) NOT NULL,
	  username character varying(50) NOT NULL,
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
	  id serial,
	  name text NOT NULL,
	  path text NOT NULL,
	  user_id integer NOT NULL,
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
	  id serial,
	  body text NOT NULL,
	  user_id integer NOT NULL,
	  CONSTRAINT note_pkey PRIMARY KEY (id),
	  CONSTRAINT note_user_id_fkey FOREIGN KEY (user_id)
	      REFERENCES public.user (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
	);
	
	DROP TABLE IF EXISTS public.shipper;
	
	CREATE TABLE public.shipper
	(
	  id serial,
	  name text NOT NULL,
	  contact character varying(20) NOT NULL,
	  created_at timestamp without time zone DEFAULT now(),
	  updated_at timestamp without time zone DEFAULT now(),
	  CONSTRAINT shipper_pkey PRIMARY KEY (id)
	);
	
	DROP TABLE IF EXISTS public.product_category;
	
	CREATE TABLE public.product_category
	(
	  id serial,
	  name text NOT NULL,
	  created_at timestamp without time zone DEFAULT now(),
	  updated_at timestamp without time zone DEFAULT now(),
	  CONSTRAINT product_category_pkey PRIMARY KEY (id)
	);
	
	DROP TABLE IF EXISTS public.product;
	
	CREATE TABLE public.product
	(
	  id serial,
	  name text NOT NULL,
	  vendor_id integer,
	  price numeric NOT NULL,
	  units_in_stock integer NOT NULL DEFAULT 0,
	  created_at timestamp without time zone DEFAULT now(),
	  updated_at timestamp without time zone DEFAULT now(),
	  category_id integer,
	  CONSTRAINT product_pkey PRIMARY KEY (id),
	  CONSTRAINT product_category_id_fkey FOREIGN KEY (category_id)
	      REFERENCES public.product_category (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT product_vendor_id_fkey FOREIGN KEY (vendor_id)
	      REFERENCES public.user (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
	);
	
	
	DROP TABLE IF EXISTS public.order;
	
	CREATE TABLE public.order
	(
	  id serial,
	  user_id integer NOT NULL,
	  created_at timestamp without time zone DEFAULT now(),
	  updated_at timestamp without time zone DEFAULT now(),
	  shipper_id integer NOT NULL,
	  CONSTRAINT order_pkey PRIMARY KEY (id),
	  CONSTRAINT order_shipper_id_fkey FOREIGN KEY (shipper_id)
	      REFERENCES public.shipper (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT order_user_id_fkey FOREIGN KEY (user_id)
	      REFERENCES public.user (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE CASCADE
	);
	
	
	DROP TABLE IF EXISTS public.order_item;
	
	CREATE TABLE public.order_item
	(
	  id serial,
	  order_id integer NOT NULL,
	  unit_price numeric NOT NULL DEFAULT 0,
	  quantity integer NOT NULL DEFAULT 0,
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
	  id serial,
	  order_id integer NOT NULL,
	  created_at timestamp without time zone DEFAULT now(),
	  updated_at timestamp without time zone DEFAULT now(),
	  invoice_id text NOT NULL,
	  CONSTRAINT invoice_pkey PRIMARY KEY (id),
	  CONSTRAINT invoice_order_id_fkey FOREIGN KEY (order_id)
	      REFERENCES public.order (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT invoice_invoice_id_key UNIQUE (invoice_id)
	);

COMMIT;