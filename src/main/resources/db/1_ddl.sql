--
-- PostgreSQL database dump
-- 
-- Cria o schema padrão de dados
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.0
-- Started on 2015-10-11 17:10:43 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 181 (class 3079 OID 12123)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2306 (class 0 OID 0)
-- Dependencies: 181
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 194 (class 1255 OID 16574)
-- Name: fn_update_synset_with_votes(character, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION fn_update_synset_with_votes(_lang_id character, _synset_id integer DEFAULT NULL::integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	totalRowsUpdated int := 0;
begin
	-- sum values from all words of a synset
	with sum as (
		select
			s.id
			,s.gloss
			,sum(stv.positive_score) pos_sum
			,sum(stv.negative_score) neg_sum
			,count(1) total
		from synset_term_votes stv
		inner join synset_term st on st.id=stv.synset_term_id
		inner join synset s on s.id=st.synset_id
		where 
			s.language_id=_lang_id
			and s.id=coalesce(_synset_id, s.id) -- updates either one synset or all of them
		group by s.id, s.gloss
	)
	
	-- applies simple mean to all words of a synset
	, mean as (
		select
			id
			,gloss
			,(pos_sum/total) pos_mean
			,(neg_sum/total) neg_mean
		from sum
	)
	
	-- updates the synsets and returns the number of affected rows
	, update as (
		update synset
		set
			positive_score=pos_mean
			,negative_score=neg_mean
		from mean
		where synset.id=mean.id
		returning 1
	)
	
	-- saves the number of affected rows
	select count(1) into totalRowsUpdated from update;
	
	return totalRowsUpdated;
end;
$$;


SET default_with_oids = false;

--
-- TOC entry 172 (class 1259 OID 16388)
-- Name: language; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE language (
    id character(2) NOT NULL,
    name character varying(20) NOT NULL,
    active boolean NOT NULL
);


--
-- TOC entry 173 (class 1259 OID 16402)
-- Name: sq_synset_id; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sq_synset_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 176 (class 1259 OID 16433)
-- Name: sq_synset_term_id; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sq_synset_term_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 179 (class 1259 OID 16496)
-- Name: sq_synset_term_votes_id; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sq_synset_term_votes_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 177 (class 1259 OID 16462)
-- Name: sq_user_id; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sq_user_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 174 (class 1259 OID 16404)
-- Name: synset; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE synset (
    id integer DEFAULT nextval('sq_synset_id'::regclass) NOT NULL,
    pos_speech character varying(1) NOT NULL,
    original_id character varying(10),
    positive_score double precision NOT NULL,
    negative_score double precision NOT NULL,
    gloss text,
    language_id character(2) NOT NULL
);


--
-- TOC entry 175 (class 1259 OID 16423)
-- Name: synset_term; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE synset_term (
    id integer DEFAULT nextval('sq_synset_term_id'::regclass) NOT NULL,
    synset_id integer NOT NULL,
    term character varying(150) NOT NULL,
    sense_number smallint NOT NULL
);


--
-- TOC entry 180 (class 1259 OID 16498)
-- Name: synset_term_votes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE synset_term_votes (
    id integer DEFAULT nextval('sq_synset_term_votes_id'::regclass) NOT NULL,
    synset_term_id integer NOT NULL,
    positive_score double precision NOT NULL,
    negative_score double precision NOT NULL,
    date timestamp without time zone DEFAULT now()
);


--
-- TOC entry 178 (class 1259 OID 16464)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    id integer DEFAULT nextval('sq_user_id'::regclass) NOT NULL,
    username character varying(20) NOT NULL,
    password character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    active boolean DEFAULT false NOT NULL
);


--
-- TOC entry 2178 (class 2606 OID 16392)
-- Name: language_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);


--
-- TOC entry 2180 (class 2606 OID 16412)
-- Name: synset_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY synset
    ADD CONSTRAINT synset_pkey PRIMARY KEY (id);


--
-- TOC entry 2183 (class 2606 OID 16427)
-- Name: synset_term_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY synset_term
    ADD CONSTRAINT synset_term_pkey PRIMARY KEY (id);


--
-- TOC entry 2187 (class 2606 OID 16504)
-- Name: synset_term_votes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY synset_term_votes
    ADD CONSTRAINT synset_term_votes_pkey PRIMARY KEY (id);


--
-- TOC entry 2185 (class 2606 OID 16470)
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2181 (class 1259 OID 16491)
-- Name: ix_synset_term_term; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_synset_term_term ON synset_term USING btree (term);


--
-- TOC entry 2188 (class 2606 OID 16418)
-- Name: synset_language_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY synset
    ADD CONSTRAINT synset_language_id_fkey FOREIGN KEY (language_id) REFERENCES language(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2189 (class 2606 OID 16428)
-- Name: synset_term_synset_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY synset_term
    ADD CONSTRAINT synset_term_synset_id_fkey FOREIGN KEY (synset_id) REFERENCES synset(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2190 (class 2606 OID 16505)
-- Name: synset_term_votes_synset_term_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY synset_term_votes
    ADD CONSTRAINT synset_term_votes_synset_term_id_fkey FOREIGN KEY (synset_term_id) REFERENCES synset_term(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


-- Completed on 2015-10-11 17:10:44 BRT

--
-- PostgreSQL database dump complete
--

