create table plats_facture
(
    id_facture integer
        constraint facture_fk
            references factures,
    id_plat    integer
        constraint plat_fk
            references plats,
    quantite   smallint default 1 not null
);

alter table plats_facture
    owner to postgres;

INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (1, 1, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (1, 5, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (2, 4, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (2, 1, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (3, 3, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (3, 2, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (3, 4, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (1, 2, 2);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (1, 4, 2);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (10, 2, 2);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (11, 1, 2);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (11, 3, 2);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (11, 4, 2);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (11, 2, 1);
INSERT INTO public.plats_facture (id_facture, id_plat, quantite) VALUES (11, 5, 3);