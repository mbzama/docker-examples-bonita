-- Table: public.registration

-- DROP TABLE public.registration;

CREATE TABLE public.registration
(
    persistenceid bigint NOT NULL,
    approved boolean,
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    persistenceversion bigint,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT registration_pkey PRIMARY KEY (persistenceid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.registration
    OWNER to businessuser;