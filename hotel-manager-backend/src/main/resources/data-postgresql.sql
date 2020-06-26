INSERT INTO public.app_user(
	 user_id, email, password, person_person_id)
	SELECT 1,'admin@wp.pl', '{noop}adminadmin', null
	WHERE NOT EXISTS (SELECT  * FROM public.app_user WHERE email='admin@wp.pl');

INSERT  INTO public.user_role(user_role_id,name,description) SELECT
1,'USER','Zwykły użytkownik' WHERE NOT EXISTS(SELECT  * FROM  public.user_role WHERE name='USER');

INSERT  INTO public.user_role(user_role_id,name,description) SELECT
2,'ADMIN','Admin ma zawsze racje' WHERE NOT EXISTS(SELECT  * FROM  public.user_role WHERE name='ADMIN');

INSERT INTO public.APP_USER_ROLE(USER_ID , USER_ROLE_ID)
SELECT  1,2 WHERE NOT EXISTS(SELECT * FROM public.APP_USER_ROLE WHERE USER_ID = 1 AND USER_ROLE_ID = 2);

INSERT INTO public.APP_USER_ROLE(USER_ID , USER_ROLE_ID)
SELECT  1,1 WHERE NOT EXISTS(SELECT * FROM public.APP_USER_ROLE WHERE USER_ID = 1 AND USER_ROLE_ID = 1);

INSERT INTO public.person(person_id, last_name, first_name)
SELECT 1,'Admin', 'Admin' WHERE NOT EXISTS(SELECT * FROM  public.person WHERE person_id = 1);

-- UPDATE public.app_user SET person_id = 1  where user_id = 1 ;
