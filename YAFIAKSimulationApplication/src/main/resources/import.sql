DROP TABLE IF EXISTS public.t_sensor_sen;

CREATE TABLE public.t_sensor_sen(
    sen_id bigint UNIQUE NOT NULL,
	sen_y integer NOT NULL,
	sen_x integer NOT NULL,
	sen_intensity integer NOT NULL,
	sen_lat real NOT NULL,
	sen_long real NOT NULL,
	sen_name varchar(100) not null,
	CONSTRAINT t_sensor_sen_pkey PRIMARY KEY (sen_id)
);

INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (1, 0, 0, 0, 45.763159536648914, 4.822648316418376, 'Basilique de Fourvière');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (2, 1, 0, 0, 45.76884762930256, 4.8356945814534065, 'Hôtel de ville - Louis Pradel');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (3, 2, 0, 0, 45.78285582587307, 4.83080223206527, 'Hôpital de la Croix-Rousse');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (4, 3, 0, 0, 45.76435707803825, 4.850285798926798, 'Halles de Lyon - Paul Bocuse');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (5, 4, 0, 0, 45.77261941300358, 4.856894762135334, 'Lycée du Parc');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (6, 5, 0, 0, 45.75106292764906, 4.8356945814534065, 'Centre Hôspitalier Saint Joseph Saint Luc');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (7, 0, 1, 0, 45.760644616057526, 4.819730072923698, 'Théâtre Gallo-romain');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (8, 1, 1, 0, 45.75597374848109, 4.871485979608724, 'Consulat Général du Royaume du Maroc');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (9, 2, 1, 0, 45.784195662019805, 4.8698686420602195, 'CPE Lyon');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (10, 3, 1, 0, 45.74189763013645, 4.817839007763024, 'Centre Commercial de la Confluence');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (11, 4, 1, 0, 45.73183343449823, 4.951219885152688, 'Eurexpo Lyon');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (12, 5, 1, 0, 45.76070322771638, 4.908218714256792, 'Médipôle Lyon-Villeurbanne');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (13, 0, 2, 0, 45.73956146066998, 4.856891967359097, 'Cimetière de la Guillotière ;ouveau');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (14, 1, 2, 0, 45.78213627418592, 4.930963844570989, 'METRO - Vaux-en-Velin');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (15, 2, 2, 0, 45.728658044016726, 4.942550986429165, 'Lyon Aéroport - Bron');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (16, 3, 2, 0, 45.760553526644614, 4.861162043710536, 'Lyon Part Dieu - Gare');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (17, 4, 2, 0, 45.761960700861685, 4.856226779585757, 'La Part Dieu - Centre Commercial');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (18, 5, 2, 0, 45.756810869310485, 4.845712521232968, 'Commissariat de Police - Lyon 3ème et 6ème');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (19, 0, 3, 0, 45.75071809911183, 4.861924655904504, 'Mémorial National de la prison de Montluc');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (20, 1, 3, 0, 45.759522059158, 4.8481724267406845, 'Bourse du Travail Lyon - Théâtre');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (21, 2, 3, 0, 45.763923184423696, 4.845179081290994, 'COPY-TOP Part-Dieu-Lafayette');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (22, 3, 3, 0, 45.76470906310249, 4.848687410353608, 'Consulat de Chine');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (23, 4, 3, 0, 45.76630324012914, 4.851305246106752, 'Consulat Général du Sénégal');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (24, 5, 3, 0, 45.76634814586453, 4.8417136675685954, 'Consulat Général du Mali');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (25, 0, 4, 0, 45.77120523626423, 4.852227926334726, 'Consulat de la République de Pologne');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (26, 1, 4, 0, 45.78454696082833, 4.852217197708858, 'Cité Internationale');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (27, 2, 4, 0, 45.769614111651215, 4.823073625886927, 'LGT La Martinière Diderot');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (28, 3, 4, 0, 45.76599929581348, 4.8269145490765775, 'Lyon Saint-Paul');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (29, 4, 4, 0, 45.76400842761092, 4.8359857795782455, 'CCI - Lyon Métropole');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (30, 5, 4, 0, 45.757395642298164, 4.827598827562714, 'Grande Synagogue de Lyon');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (31, 0, 5, 0, 45.75958764178199, 4.836671481715149, 'Grand Hôtel-Dieu');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (32, 1, 5, 0, 45.76158152990234, 4.832805563209842, 'OXYBUL Lyon 2ème Mercière');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (33, 2, 5, 0, 45.76092729323796, 4.831567958932611, 'Hospices Civils de Lyon - HCL');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (34, 3, 5, 0, 45.76201768342818, 4.828021010362056, 'Cour d''Appel de Lyon');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (35, 4, 5, 0, 45.76194202438956, 4.827357552399004, 'Musée du Cinéma et Miniature');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (36, 5, 5, 0, 45.764007034320066, 4.828174116036864, 'Le Petit Musée de Guignol');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (37, 0, 6, 0, 45.76794102272871, 4.817010158949513, 'Ecole Nationale Supérieure des Beaux Arts');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (38, 1, 6, 0, 45.76817242518922, 4.840837232884172, 'Skatepark Foch');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (39, 2, 6, 0, 45.766201023619246, 4.845959894085283, 'Eglise Saint-Pothin de Lyon');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (40, 3, 6, 0, 45.76188416742864, 4.868198496894557, 'Patinoire Baraban');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (41, 4, 6, 0, 45.7450796182093, 4.870711692656721, 'Musée Lumière');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (42, 5, 6, 0, 45.74007548117003, 4.8685682130849814, 'Lycée la Martinière Montplaisir');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (43, 0, 7, 0, 45.73306160496341, 4.877338261255731, 'I.T.I.I - Institut des Techniques d''Ingénieurs de l''Industrie');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (44, 1, 7, 0, 45.74388928918146, 4.902411551144052, 'CRNL - Neurocampus');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (45, 2, 7, 0, 45.72375231161559, 4.8715876062723, 'Paris Store');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (46, 3, 7, 0, 45.72722756310889, 4.825754022922183, 'I.S.F.A - Institut des Sciences Financières Appliquées');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (47, 4, 7, 0, 45.77561144470424, 4.804811334418986, 'Médiathèque de Vaise');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (48, 5, 7, 0, 45.78043031642379, 4.805068826485422, 'Gare de Vaise');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (49, 0, 8, 0, 45.787672806084494, 4.811999654413959, 'Pathé Vaise');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (50, 1, 8, 0, 45.776703958313504, 4.821025957374761, 'ESSO Express - Croix-Rousse');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (51, 2, 8, 0, 45.770956797490626, 4.8217984340216065, 'Eglise de Saint-Bruno des Chartreux');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (52, 3, 8, 0, 45.74853122153405, 4.825682272482078, 'Lyon-Perrache - Gare');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (53, 4, 8, 0, 45.747423182411396, 4.8196955823671415, 'Gendarmerie Nationale');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (54, 5, 8, 0, 45.74478775776883, 4.819116224922237, 'Banque de France');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (55, 0, 9, 0, 45.733436041255636, 4.833385575847596, 'Ecole Normale Supérieure de Lyon');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (56, 1, 9, 0, 45.73698708128294, 4.849208807219348, 'Technicentre TGV SNCF - Lyon Gerland');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (57, 2, 9, 0, 45.736791879485615, 4.8359557258214165, 'Service urbanisme appliqué');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (58, 3, 9, 0, 45.740859492046184, 4.844156577609335, 'Fourrière municipale');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (59, 4, 9, 0, 45.746123021483086, 4.843028847938458, 'CPAM du Rhône');
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity, sen_lat, sen_long, sen_name) VALUES (60, 5, 9, 0, 45.75885200001829, 4.830589214003582, 'Pharmacie Bellecour');