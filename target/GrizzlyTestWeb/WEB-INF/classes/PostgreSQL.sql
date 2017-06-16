CREATE DATABASE postgres
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Belarusian_Belarus.1251'
    LC_CTYPE = 'Belarusian_Belarus.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE postgres
    IS 'default administrative connection database';


  CREATE TABLE public.reports
(
    id serial ,
    performer_id integer,
    activity_id integer,
    date timestamp without time zone,
    CONSTRAINT  reports_pkey PRIMARY KEY (id),
    CONSTRAINT  performer_id FOREIGN KEY ( performer_id)
        REFERENCES public.performers (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT  activity_id FOREIGN KEY ( activity_id)
        REFERENCES public.activities (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.reports
    OWNER to postgres;

ALTER TABLE public.reports
    OWNER to postgres;
    
    
     CREATE TABLE public.activities
(
     id serial ,
     name name,
    CONSTRAINT activities_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.activities
    OWNER to postgres;
    
    
    
    
    CREATE TABLE public.performers
(
     id serial ,
     name name,
    CONSTRAINT performers_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.performers
    OWNER to postgres;
    
    
    
    
    
    INSERT INTO public.activities(
     name)
    VALUES ( 'Accounting');
    INSERT INTO public.activities(
     name)
    VALUES ( 'Marketing');
    INSERT INTO public.activities(
     name)
    VALUES ( 'Sales');
    INSERT INTO public.activities(
     name)
    VALUES ( 'Hiring Employees');
    INSERT INTO public.activities(
     name)
    VALUES ( 'Customer Service');
    INSERT INTO public.activities(
     name)
    VALUES ( 'Budgeting');
    
    
    
    INSERT INTO public.performers(
     name)
    VALUES ( 'All Performers');
    INSERT INTO public.performers(
    name)
    VALUES ('James Bay');
    INSERT INTO public.performers(
    name)
    VALUES ('Richie Havens');
    INSERT INTO public.performers(
    name)
    VALUES ('Tim Hardin');
    INSERT INTO public.performers(
    name)
    VALUES ('Joe Hill');
    INSERT INTO public.performers(
    name)
    VALUES ('Arlo Guthrie');
    INSERT INTO public.performers(
    name)
    VALUES ('Sweetwater');
    
    
   
    INSERT INTO public.reports(
     performer_id, activity_id, date)
    VALUES ( 2, 1, '2010-09-02');
    INSERT INTO public.reports(
     performer_id, activity_id, date)
    VALUES ( 3, 2, '2010-09-30 ');
    INSERT INTO public.reports(
     performer_id, activity_id, date)
    VALUES ( 4, 3, '2010-10-02');
    INSERT INTO public.reports(
     performer_id, activity_id, date)
    VALUES ( 5, 4, '2017-06-13');
    INSERT INTO public.reports(
     performer_id, activity_id, date)
    VALUES ( 6, 5, '2017-05-13 ');
    INSERT INTO public.reports(
     performer_id, activity_id, date)
    VALUES ( 7, 6, '2016-05-13');
    

