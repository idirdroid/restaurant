create table menu
(
    id_menu serial not null
        constraint menu_pk
            primary key,
    title   varchar
);

alter table menu
    owner to postgres;

INSERT INTO public.menu (id_menu, title) VALUES (1, 'Enregister une facture');
INSERT INTO public.menu (id_menu, title) VALUES (2, 'lister les meilleures tables par chiffre d''affaire');
INSERT INTO public.menu (id_menu, title) VALUES (3, 'lister les meilleurs plats par chiffre d''affaire');
INSERT INTO public.menu (id_menu, title) VALUES (4, 'Quitter le programme');