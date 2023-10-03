CREATE TABLE parking_spot (
    pk serial4 NOT NULL,
    id uuid NOT NULL,
    "spot_number" bigint NOT NULL,
    "spot_type" varchar NOT NULL,
    "occupied" boolean,
    CONSTRAINT pk_pk PRIMARY KEY (pk),
    CONSTRAINT un_id UNIQUE (id)
);
