create table factures
(
    id_facture serial not null
        constraint factures_pk
            primary key,
    id_table   integer
        constraint table_fk
            references tables,
    id_serveur integer
        constraint serveur_fk
            references serveurs
);

alter table factures
    owner to postgres;

INSERT INTO public.factures (id_facture, id_table, id_serveur) VALUES (1, 1, 1);
INSERT INTO public.factures (id_facture, id_table, id_serveur) VALUES (2, 2, 2);
INSERT INTO public.factures (id_facture, id_table, id_serveur) VALUES (3, 3, 1);
INSERT INTO public.factures (id_facture, id_table, id_serveur) VALUES (10, 5, 2);
INSERT INTO public.factures (id_facture, id_table, id_serveur) VALUES (11, 5, 2);