DROP TABLE IF EXISTS public.t_sensor_sen;

CREATE TABLE public.t_sensor_sen(
    sen_id bigint UNIQUE NOT NULL,
	sen_y integer NOT NULL,
	sen_x integer NOT NULL,
	sen_intensity integer NOT NULL,
	CONSTRAINT t_sensor_sen_pkey PRIMARY KEY (sen_id)
);

INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (1, 0, 0, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (2, 1, 0, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (3, 2, 0, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (4, 3, 0, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (5, 4, 0, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (6, 5, 0, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (7, 0, 1, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (8, 1, 1, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (9, 2, 1, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (10, 3, 1, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (11, 4, 1, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (12, 5, 1, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (13, 0, 2, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (14, 1, 2, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (15, 2, 2, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (16, 3, 2, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (17, 4, 2, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (18, 5, 2, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (19, 0, 3, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (20, 1, 3, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (21, 2, 3, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (22, 3, 3, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (23, 4, 3, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (24, 5, 3, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (25, 0, 4, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (26, 1, 4, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (27, 2, 4, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (28, 3, 4, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (29, 4, 4, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (30, 5, 4, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (31, 0, 5, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (32, 1, 5, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (33, 2, 5, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (34, 3, 5, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (35, 4, 5, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (36, 5, 5, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (37, 0, 6, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (38, 1, 6, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (39, 2, 6, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (40, 3, 6, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (41, 4, 6, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (42, 5, 6, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (43, 0, 7, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (44, 1, 7, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (45, 2, 7, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (46, 3, 7, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (47, 4, 7, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (48, 5, 7, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (49, 0, 8, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (50, 1, 8, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (51, 2, 8, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (52, 3, 8, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (53, 4, 8, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (54, 5, 8, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (55, 0, 9, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (56, 1, 9, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (57, 2, 9, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (58, 3, 9, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (59, 4, 9, 0);
INSERT INTO public.t_sensor_sen(sen_id, sen_y, sen_x, sen_intensity) VALUES (60, 5, 9, 0);