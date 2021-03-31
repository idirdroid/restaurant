create table plats
(
    id_plat       serial not null
        constraint plats_pk
            primary key,
    nom           varchar,
    prix_unitaire double precision
);

alter table plats
    owner to postgres;

INSERT INTO public.plats (id_plat, nom, prix_unitaire) VALUES (1, 'Burger Classique', 12);
INSERT INTO public.plats (id_plat, nom, prix_unitaire) VALUES (2, 'Entrecôte', 16);
INSERT INTO public.plats (id_plat, nom, prix_unitaire) VALUES (3, 'Pizza', 13);
INSERT INTO public.plats (id_plat, nom, prix_unitaire) VALUES (4, 'café', 2.5);
INSERT INTO public.plats (id_plat, nom, prix_unitaire) VALUES (5, 'Tarte aux pommes', 4.5);
INSERT INTO public.plats (id_plat, nom, prix_unitaire) VALUES (6, 'Coca Zéro', 2.9);
INSERT INTO public.plats (id_plat, nom, prix_unitaire) VALUES (7, 'Café Gourmand', 7.2);