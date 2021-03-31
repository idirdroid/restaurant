create table tables
(
    id_table    serial not null
        constraint tables_pk
            primary key,
    nom         varchar,
    nb_convives smallint
);

alter table tables
    owner to postgres;

INSERT INTO public.tables (id_table, nom, nb_convives) VALUES (1, 'Ocean', 6);
INSERT INTO public.tables (id_table, nom, nb_convives) VALUES (2, 'Terre', 4);
INSERT INTO public.tables (id_table, nom, nb_convives) VALUES (3, 'trucmuch', 4);
INSERT INTO public.tables (id_table, nom, nb_convives) VALUES (4, 'Montagne', 2);
INSERT INTO public.tables (id_table, nom, nb_convives) VALUES (5, 'Fleuve', 2);
INSERT INTO public.tables (id_table, nom, nb_convives) VALUES (6, 'Rivière', 10);
INSERT INTO public.tables (id_table, nom, nb_convives) VALUES (7, 'Route', 2);