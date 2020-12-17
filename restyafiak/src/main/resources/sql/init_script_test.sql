INSERT INTO public.t_fire_fir(
fir_id, fir_intensity, fir_locationx, fir_locationy)
VALUES (1, 4, 5, 6),
(2, 6, 18, 5),
(3, 6, 12, 2),
(4, 8, 10, 9);



INSERT INTO public.t_firestation_fis(
fis_id, fis_locationx, fis_locationy, fis_name)
VALUES (1, 5, 5, 'SDIS Villeurbanne 1'),
(2, 11, 10, 'SDIS Villeurbanne 2'),
(3, 15, 5, 'SDIS Lyon 1');



INSERT INTO public.j_fire_firestation(
fire_id, firestation_id)
VALUES (1, 1),
(2, 3),
(3, 3),
(4, 2);



INSERT INTO public.t_firetruck_fit(
fit_id, fit_capacity, fit_speed, fit_rate, fit_firestation_id)
VALUES (1, 20, 8, 5, 1),
(2, 30, 6, 6, 1),
(3, 10, 10, 4, 1),
(4, 20, 8, 5, 2),
(5, 20, 8, 5, 2),
(6, 30, 6, 6, 3),
(7, 10, 10, 4, 3),
(8, 50, 4, 10, 3),
(9, 20, 8, 5, 3);