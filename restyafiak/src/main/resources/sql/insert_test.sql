INSERT INTO public.t_sensor_sen(
sen_id, sen_y, sen_intensity, sen_x, sen_lat, sen_long)
VALUES (1, 0, 15, 0, 45.78388219697, 4.8692691122),
(2, 1, 40, 0, 45.781107138236074, 4.880348662284839),
(3, 2, 60, 0, 45.7728731454003, 4.860355964542107),
(4, 3, 20, 0, 45.77142060746705, 4.851500005762919),
(5, 4, 10, 0, 45.76896451842911, 4.84447458132192),
(6, 5, 25, 0, 45.757982461304394, 4.849661450959597),
(7, 6, 30, 0, 45.761363804352776, 4.8558672228109145),
(8, 7, 55, 0, 45.76758831164702, 4.867761346384607),
(9, 8, 50, 0, 45.77449124185002, 4.892095881068599),
(10, 9, 65, 0, 45.767448612103465, 4.901242863619788),
(11, 10, 80, 0, 45.752995036237785, 4.877405749870198),
(12, 11, 100, 0, 45.756752, 4.888761);



INSERT INTO public.t_firestation_fst(
fst_id, fst_lat, fst_long, fst_name)
VALUES (1, 45.76966, 4.894794, 'SDIS Villeurbanne Est'),
(2, 45.74545, 4.887126, 'SDIS Villeurbanne Sud'),
(3, 45.76975191263477, 4.872284768919388, 'SDIS Villeurbanne Ouest'),
(4, 45.77796416688027, 4.871853637232038, 'SDIS Villeurbanne Nord'),
(5, 45.75706627886744, 4.853665476829301, 'SDIS Lyon 3'),
(6, 45.76920359587581, 4.8480525068687985, 'SDIS Lyon 6');



INSERT INTO public.j_firestation_sensor(
firestation_id, sensor_id)
VALUES (4, 1),
(4, 2),
(4, 3),
(6, 4),
(6, 5),
(5, 6),
(5, 7),
(3, 8),
(1, 9),
(1, 10),
(2, 11),
(2, 12);



INSERT INTO public.t_firetruck_ftr(
ftr_id, ftr_capacity, ftr_lat, ftr_long, ftr_name, ftr_rate, ftr_firestation_id, ftr_sensor_id)
VALUES (1, 20, 45.76966, 4.894794, 'Extincteur 3000', 8, 1, 9),
(2, 30, 45.76966, 4.894794, 'Jeanne d''Arc', 6, 1, 10),
(3, 10, 45.74545, 4.887126, 'Notre-Dame', 4, 2, 11),
(4, 20, 45.74545, 4.887126, 'Master Fiakos', 8, 2, 12),
(5, 20, 45.76975191263477, 4.872284768919388, 'KillFire', 8, 3, 8),
(6, 30, 45.77796416688027, 4.871853637232038, 'Johnny Halliday', 6, 4, 1),
(7, 10, 45.77796416688027, 4.871853637232038, 'John Terry', 4, 4, 2),
(8, 10, 45.77796416688027, 4.871853637232038, 'Franck Lampard', 4, 4, 3),
(9, 50, 45.75706627886744, 4.853665476829301, 'Steven Gerrard', 10, 5, 6),
(10, 50, 45.75706627886744, 4.853665476829301, 'Ryan Giggs', 10, 5, 7),
(11, 20, 45.76920359587581, 4.8480525068687985, 'Demba Ba', 5, 6, 4),
(12, 20, 45.76920359587581, 4.8480525068687985, 'Michael Ballack', 5, 6, 5),
(13, 20, 45.76920359587581, 4.8480525068687985, 'Camion Inactif', 5, 6, 5);