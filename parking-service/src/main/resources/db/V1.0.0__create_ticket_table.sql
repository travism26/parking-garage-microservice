CREATE TABLE ticket (
    pk serial4 NOT NULL,
    id uuid NOT NULL,
    "parking_spot" bigint NOT NULL,
    "license_number" varchar NOT NULL,
    "entry_time" timestamptz NOT NULL,
    "exit_time" timestamptz,
    CONSTRAINT pk_pk PRIMARY KEY (pk),
    CONSTRAINT un_id UNIQUE (id)
);
