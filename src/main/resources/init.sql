GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.users TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.personal_data TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.google_auth TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.administrators TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.tenants TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.owners TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.access_levels TO ssbd02mok;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.old_passwords TO ssbd02mok;
GRANT SELECT, INSERT, DELETE ON TABLE public.tokens TO ssbd02mok;
GRANT SELECT ON TABLE public.timezones TO ssbd02mok;
GRANT SELECT ON TABLE public.themes TO ssbd02mok;
GRANT SELECT, INSERT, UPDATE ON TABLE public.user_filters TO ssbd02mok;

GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.locals TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.addresses TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.applications TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.fixed_fees TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.payments TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.rents TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.role_requests TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.variable_fees TO ssbd02mol;

GRANT SELECT, INSERT, UPDATE ON TABLE public.users TO ssbd02auth;
GRANT SELECT ON TABLE public.personal_data TO ssbd02auth;
GRANT SELECT, INSERT, UPDATE ON TABLE public.google_auth TO ssbd02auth;
GRANT SELECT ON TABLE public.timezones TO ssbd02auth;
GRANT SELECT ON TABLE public.themes TO ssbd02auth;
GRANT SELECT ON TABLE public.administrators TO ssbd02auth;
GRANT SELECT ON TABLE public.tenants TO ssbd02auth;
GRANT SELECT ON TABLE public.owners TO ssbd02auth;
GRANT SELECT ON TABLE public.access_levels TO ssbd02auth;
GRANT SELECT, INSERT, DELETE ON TABLE public.tokens TO ssbd02auth;

GRANT SELECT ON TABLE public.tenants TO ssbd02mol;
GRANT SELECT ON TABLE public.personal_data TO ssbd02mol;
GRANT SELECT, INSERT ON TABLE public.owners TO ssbd02mol;
GRANT SELECT, INSERT, UPDATE ON TABLE public.access_levels TO ssbd02mol;

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'admin', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.',
        false, true, 0, 1, 'EN', NOW(), NOW(), null, null, true);
INSERT INTO public.personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'kacperkafara18@gmail.com', null, 'Admin', 'Admin');
INSERT INTO public.access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('22f34716-3b77-4e63-809d-35f9a4758011', 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ADMINISTRATOR', true, 0, NOW(),
        NOW(), null, null);
INSERT INTO public.administrators (id)
VALUES ('22f34716-3b77-4e63-809d-35f9a4758011');

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('05854132-8b7c-440e-9ef2-8fe46a7962dc', 'tenant',
        '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.', false, true, 0, 1, 'PL', NOW(), NOW(), null,
        null, true);
INSERT INTO public.personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('05854132-8b7c-440e-9ef2-8fe46a7962dc', 'tenant@test.com', null, 'Tenant', 'Tenant');
INSERT INTO public.access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('4b329d71-2a92-4e90-8f0b-f673e4f79529', '05854132-8b7c-440e-9ef2-8fe46a7962dc', 'TENANT', true, 0, NOW(), NOW(),
        null, null);
INSERT INTO public.tenants (id)
VALUES ('4b329d71-2a92-4e90-8f0b-f673e4f79529');

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'test', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.',
        false, true, 0, 1, 'EN', NOW(), NOW(), null, null, true);
INSERT INTO public.personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'test@test.com', null, 'test', 'test');
INSERT INTO public.access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('6bcef94d-16f5-401b-a0d0-9461257572f6', '2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'TENANT', true, 0, NOW(), NOW(),
        null, null);
INSERT INTO public.tenants (id)
VALUES ('6bcef94d-16f5-401b-a0d0-9461257572f6');
INSERT INTO public.access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('397cf3c5-f369-4783-9892-6fc781fd2b0d', '2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'OWNER', true, 0, NOW(), NOW(),
        null, null);
INSERT INTO public.owners (id)
VALUES ('397cf3c5-f369-4783-9892-6fc781fd2b0d');

INSERT INTO public.themes
    (id, type)
VALUES (gen_random_uuid(), 'light'),
       (gen_random_uuid(), 'dark');

INSERT INTO public.timezones
    (id, name)
VALUES (gen_random_uuid(), 'Pacific/Midway'),
       (gen_random_uuid(), 'Pacific/Honolulu'),
       (gen_random_uuid(), 'America/Juneau'),
       (gen_random_uuid(), 'America/Dawson'),
       (gen_random_uuid(), 'America/Phoenix'),
       (gen_random_uuid(), 'America/Tijuana'),
       (gen_random_uuid(), 'America/Los_Angeles'),
       (gen_random_uuid(), 'America/Boise'),
       (gen_random_uuid(), 'America/Chihuahua'),
       (gen_random_uuid(), 'America/Regina'),
       (gen_random_uuid(), 'America/Mexico_City'),
       (gen_random_uuid(), 'America/Belize'),
       (gen_random_uuid(), 'America/Chicago'),
       (gen_random_uuid(), 'America/Bogota'),
       (gen_random_uuid(), 'America/Detroit'),
       (gen_random_uuid(), 'America/Caracas'),
       (gen_random_uuid(), 'America/Santiago'),
       (gen_random_uuid(), 'America/Sao_Paulo'),
       (gen_random_uuid(), 'America/Montevideo'),
       (gen_random_uuid(), 'America/Argentina/Buenos_Aires'),
       (gen_random_uuid(), 'America/St_Johns'),
       (gen_random_uuid(), 'America/Godthab'),
       (gen_random_uuid(), 'Atlantic/Cape_Verde'),
       (gen_random_uuid(), 'Atlantic/Azores'),
       (gen_random_uuid(), 'Etc/GMT'),
       (gen_random_uuid(), 'Europe/London'),
       (gen_random_uuid(), 'Europe/Dublin'),
       (gen_random_uuid(), 'Europe/Lisbon'),
       (gen_random_uuid(), 'Africa/Casablanca'),
       (gen_random_uuid(), 'Atlantic/Canary'),
       (gen_random_uuid(), 'Africa/Algiers'),
       (gen_random_uuid(), 'Europe/Belgrade'),
       (gen_random_uuid(), 'Europe/Sarajevo'),
       (gen_random_uuid(), 'Europe/Brussels'),
       (gen_random_uuid(), 'Europe/Amsterdam'),
       (gen_random_uuid(), 'Africa/Harare'),
       (gen_random_uuid(), 'Europe/Bucharest'),
       (gen_random_uuid(), 'Africa/Cairo'),
       (gen_random_uuid(), 'Europe/Helsinki'),
       (gen_random_uuid(), 'Europe/Athens'),
       (gen_random_uuid(), 'Asia/Jerusalem'),
       (gen_random_uuid(), 'Europe/Moscow'),
       (gen_random_uuid(), 'Asia/Kuwait'),
       (gen_random_uuid(), 'Africa/Nairobi'),
       (gen_random_uuid(), 'Asia/Baghdad'),
       (gen_random_uuid(), 'Asia/Tehran'),
       (gen_random_uuid(), 'Asia/Dubai'),
       (gen_random_uuid(), 'Asia/Baku'),
       (gen_random_uuid(), 'Asia/Kabul'),
       (gen_random_uuid(), 'Asia/Yekaterinburg'),
       (gen_random_uuid(), 'Asia/Karachi'),
       (gen_random_uuid(), 'Asia/Kolkata'),
       (gen_random_uuid(), 'Asia/Colombo'),
       (gen_random_uuid(), 'Asia/Kathmandu'),
       (gen_random_uuid(), 'Asia/Dhaka'),
       (gen_random_uuid(), 'Asia/Almaty'),
       (gen_random_uuid(), 'Asia/Rangoon'),
       (gen_random_uuid(), 'Asia/Bangkok'),
       (gen_random_uuid(), 'Asia/Krasnoyarsk'),
       (gen_random_uuid(), 'Asia/Shanghai'),
       (gen_random_uuid(), 'Asia/Kuala_Lumpur'),
       (gen_random_uuid(), 'Asia/Taipei'),
       (gen_random_uuid(), 'Australia/Perth'),
       (gen_random_uuid(), 'Asia/Irkutsk'),
       (gen_random_uuid(), 'Asia/Seoul'),
       (gen_random_uuid(), 'Asia/Tokyo'),
       (gen_random_uuid(), 'Asia/Yakutsk'),
       (gen_random_uuid(), 'Australia/Darwin'),
       (gen_random_uuid(), 'Australia/Adelaide'),
       (gen_random_uuid(), 'Australia/Sydney'),
       (gen_random_uuid(), 'Australia/Brisbane'),
       (gen_random_uuid(), 'Australia/Hobart'),
       (gen_random_uuid(), 'Asia/Vladivostok'),
       (gen_random_uuid(), 'Pacific/Guam'),
       (gen_random_uuid(), 'Asia/Magadan'),
       (gen_random_uuid(), 'Asia/Kamchatka'),
       (gen_random_uuid(), 'Pacific/Fiji'),
       (gen_random_uuid(), 'Pacific/Auckland'),
       (gen_random_uuid(), 'Pacific/Tongatapu');

INSERT INTO public.addresses (created_at, modified_at, version, number, zip, created_by, id, modified_by, city, country, street)
VALUES
    ('2023-01-01 10:00:00', '2023-01-01 10:00:00', 1, '123', '12345', NULL, '550e8400-e29b-41d4-a716-446655440000', NULL, 'City A', 'Country A', 'Street A'),
    ('2023-01-02 11:00:00', '2023-01-02 11:00:00', 1, '456', '67890', NULL, '550e8400-e29b-41d4-a716-446655440005', NULL, 'City B', 'Country B', 'Street B'),
    ('2023-01-03 12:00:00', '2023-01-03 12:00:00', 1, '789', '10111', NULL, '550e8400-e29b-41d4-a716-446655440010', NULL, 'City C', 'Country C', 'Street C'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '101', '12131', NULL, '550e8400-e29b-41d4-a716-446655440015', NULL, 'City D', 'Country D', 'Street D'),
    ('2023-01-05 14:00:00', '2023-01-05 14:00:00', 1, '112', '14151', NULL, '550e8400-e29b-41d4-a716-446655440020', NULL, 'City E', 'Country E', 'Street E');


INSERT INTO locals (margin_fee, rental_fee, size, state, created_at, modified_at, version, address_id, created_by, id, modified_by, owner_id, name, description)
VALUES
    (10.50, 1000.00, 50, 3, '2023-01-01 12:00:00', '2023-01-01 12:00:00', 1, '550e8400-e29b-41d4-a716-446655440000', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440002', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local A', 'Description of Local A'),
    (12.00, 1200.00, 60, 2, '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, '550e8400-e29b-41d4-a716-446655440005', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440007', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local B', 'Description of Local B'),
    (15.00, 1500.00, 70, 4, '2023-01-03 14:00:00', '2023-01-03 14:00:00', 1, '550e8400-e29b-41d4-a716-446655440010', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440012', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local C', 'Description of Local C'),
    (20.00, 2000.00, 80, 1, '2023-01-04 15:00:00', '2023-01-04 15:00:00', 1, '550e8400-e29b-41d4-a716-446655440015', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440017', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local D', 'Description of Local D'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440020', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440022', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local E', 'Description of Local E');
