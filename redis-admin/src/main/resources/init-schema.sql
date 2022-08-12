DROP TABLE IF EXISTS agenda_votes CASCADE;
DROP TABLE IF EXISTS associates_agendas CASCADE;
DROP TABLE IF EXISTS agendas CASCADE;
DROP TABLE IF EXISTS associates CASCADE;


CREATE TABLE associates (
    id BIGINT(255) NOT NULL,
    name varchar(40) NOT NULL,
    cpf varchar(20) NOT NULL UNIQUE,
    CONSTRAINT associates_pkey PRIMARY KEY (id)
);

CREATE UNIQUE INDEX associates_indexes ON associates (cpf);

CREATE TABLE agendas (
    id BIGINT(255) NOT NULL,
    subject varchar(200) NOT NULL,
    title varchar(50) NOT NULL,
    start_votation DATE NOT NULL,
    end_votation DATE,
    is_closed boolean,
    is_open boolean,
    CONSTRAINT agendas_pkey PRIMARY KEY (id)
);

CREATE TABLE associates_agendas (
    id BIGINT(255) NOT NULL,
    associate_id BIGINT(255) NOT NULL,
    agenda_id BIGINT(255) NOT NULL,
    CONSTRAINT associates_agendas_pkey primary key (id),
    CONSTRAINT associate_fkey foreign key (associate_id) references associates(id),
    CONSTRAINT agenda_fkey foreign key (agenda_id) references agendas(id)
);

CREATE TABLE agenda_votes (
    agenda_id BIGINT(255) NOT NULL,
    associate_id BIGINT(255) NOT NULL,
    vote varchar(4) NOT NULL,
    CONSTRAINT agenda_votes_associate_fkey
        FOREIGN KEY (associate_id) references associates(id),
    CONSTRAINT agenda_votes_agenda_fkey
            FOREIGN KEY (agenda_id) references agendas(id)
);