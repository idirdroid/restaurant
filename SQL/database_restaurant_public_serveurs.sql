create table serveurs
(
    id_serveur serial not null
        constraint serveurs_pk
            primary key,
    nom        varchar,
    prenom     varchar
);

alter table serveurs
    owner to postgres;

INSERT INTO public.serveurs (id_serveur, nom, prenom) VALUES (1, 'Simon', 'Fourcade');
INSERT INTO public.serveurs (id_serveur, nom, prenom) VALUES (2, 'Julia', 'Simon');
INSERT INTO public.serveurs (id_serveur, nom, prenom) VALUES (3, 'Idir', 'Abed');