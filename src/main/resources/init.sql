GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.users TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.personal_data TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.administrators TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.tenants TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.owners TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.access_levels TO ssbd02mok;

GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.locals TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.addresses TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.applications TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.fixed_fees TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.payments TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.rents TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.role_requests TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.variable_fees TO ssbd02mol;

GRANT SELECT ON TABLE public.users TO ssbd02auth;

GRANT SELECT ON TABLE public.tenants TO ssbd02mol;
GRANT SELECT ON TABLE public.owners TO ssbd02mol;
GRANT SELECT ON TABLE public.access_levels TO ssbd02mol;

GRANT SELECT, INSERT, DELETE ON TABLE public.tokens TO ssbd02mok;

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at) VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'admin', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.', false, true, 0, 1, 'en', '2024-05-04 14:33:01.413581');
INSERT INTO public.personal_data (user_id, email, first_name, last_name) VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'admin@test.com', 'Admin', 'Admin');
INSERT INTO public.access_levels (id, user_id, level, active, version) VALUES ('22f34716-3b77-4e63-809d-35f9a4758011', 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ADMINISTRATOR', true, 0);
INSERT INTO public.administrators (id) VALUES ('22f34716-3b77-4e63-809d-35f9a4758011');

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at) VALUES ('05854132-8b7c-440e-9ef2-8fe46a7962dc', 'tenant', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.', false, true, 0, 1, 'pl', '2024-05-04 14:33:01.413581');
INSERT INTO public.personal_data (user_id, email, first_name, last_name) VALUES ('05854132-8b7c-440e-9ef2-8fe46a7962dc', 'tenant@test.com', 'Tenant', 'Tenant');
INSERT INTO public.access_levels (id, user_id, level, active, version) VALUES ('4b329d71-2a92-4e90-8f0b-f673e4f79529', '05854132-8b7c-440e-9ef2-8fe46a7962dc', 'TENANT', true, 0);
INSERT INTO public.tenants (id) VALUES ('4b329d71-2a92-4e90-8f0b-f673e4f79529');

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at) VALUES ('2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'test', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.', false, true, 0, 1, 'en', '2024-05-04 14:33:01.413581');
INSERT INTO public.personal_data (user_id, email, first_name, last_name) VALUES ('2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'test@test.com', 'test', 'test');
INSERT INTO public.access_levels (id, user_id, level, active, version) VALUES ('6bcef94d-16f5-401b-a0d0-9461257572f6', '2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'TENANT', true, 0);
INSERT INTO public.tenants (id) VALUES ('6bcef94d-16f5-401b-a0d0-9461257572f6');
INSERT INTO public.access_levels (id, user_id, level, active, version) VALUES ('397cf3c5-f369-4783-9892-6fc781fd2b0d', '2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'OWNER', true, 0);
INSERT INTO public.owners (id) VALUES ('397cf3c5-f369-4783-9892-6fc781fd2b0d');