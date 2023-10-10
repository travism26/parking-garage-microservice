CREATE TABLE ticket (
    pk serial NOT NULL,
    id uuid NOT NULL,
    "parking_spot" bigint NOT NULL,
    "license_number" varchar NOT NULL,
    "entry_time" timestamp NOT NULL,
    "exit_time" timestamp,
    CONSTRAINT pk_pk PRIMARY KEY (pk),
    CONSTRAINT un_id UNIQUE (id)
);
