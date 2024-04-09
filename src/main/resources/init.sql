GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.users TO ssbd02mok;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.administrators TO ssbd02mok;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.tenants TO ssbd02mok;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.owners TO ssbd02mok;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.locals TO ssbd02mol;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.addresses TO ssbd02mol;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.applications TO ssbd02mol;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.fixed_fees TO ssbd02mol;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.payments TO ssbd02mol;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.rents TO ssbd02mol;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.role_requests TO ssbd02mol;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.variable_fees TO ssbd02mol;

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version) VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'login', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.', false, true, 0, 1);