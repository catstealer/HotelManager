INSERT  INTO public.user_role(user_role_id,name,description) VALUES
(1,'USER','Zwykły użytkownik'),
(2,'ADMIN','Admin ma zawsze racje');

INSERT INTO public.app_user(
	 email, password, person_person_id)
	SELECT 'admin@wp.pl', '{noop}adminadmin', null
	WHERE NOT EXISTS (SELECT  * FROM public.app_user WHERE email='admin@wp.pl');


INSERT INTO public .APP_USER_ROLE(USER_ID , USER_ROLE_ID) VALUES
(1,2),
(1,1);

INSERT INTO public.person VALUES (1,'Admin', 'Admin');

UPDATE public.app_user SET app_user.person_person_id = 1  where app_user.user_id = 1 ;
-- INSERT INTO public.bill(23, 323, 124, null);

