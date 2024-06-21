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
GRANT SELECT, DELETE ON TABLE public.role_requests TO ssbd02mok;

GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.locals TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.addresses TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.applications TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.fixed_fees TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.payments TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.rents TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.role_requests TO ssbd02mol;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE public.variable_fees TO ssbd02mol;
GRANT SELECT ON TABLE public.users TO ssbd02mol;
GRANT SELECT ON TABLE public.personal_data TO ssbd02mol;
GRANT SELECT ON TABLE public.google_auth TO ssbd02mol;
GRANT SELECT ON TABLE public.timezones TO ssbd02mol;
GRANT SELECT ON TABLE public.themes TO ssbd02mol;

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
GRANT SELECT ON TABLE public.users TO ssbd02mol;
GRANT SELECT ON TABLE public.themes TO ssbd02mol;
GRANT SELECT ON TABLE public.timezones TO ssbd02mol;
GRANT SELECT ON TABLE public.google_auth TO ssbd02mol;
GRANT SELECT, INSERT ON TABLE public.owners TO ssbd02mol;
GRANT SELECT, INSERT, UPDATE ON TABLE public.access_levels TO ssbd02mol;
GRANT SELECT, INSERT, DELETE ON TABLE public.images TO ssbd02mol;

INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'admin', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.',
        false, true, 0, 1, 'EN', NOW(), NOW(), null, null, true);
INSERT INTO public.personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ssbdkontotestowe@int.pl', null, 'Admin', 'Admin');
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
VALUES ('05854132-8b7c-440e-9ef2-8fe46a7962dc', '242352@edu.p.lodz.pl', null, 'Tenant', 'Tenant');
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
VALUES ('2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', '242374@edu.p.lodz.pl', null, 'test', 'test');
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
    ('2023-01-01 10:00:00', '2023-01-01 10:00:00', 1, '123', '12-345', NULL, '550e8400-e29b-41d4-a716-446655440000', NULL, 'City A', 'Country A', 'Street A'),
    ('2023-01-02 11:00:00', '2023-01-02 11:00:00', 1, '456', '67-890', NULL, '550e8400-e29b-41d4-a716-446655440005', NULL, 'City B', 'Country B', 'Street B'),
    ('2023-01-03 12:00:00', '2023-01-03 12:00:00', 1, '789', '10-111', NULL, '550e8400-e29b-41d4-a716-446655440010', NULL, 'City C', 'Country C', 'Street C'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '101', '12-131', NULL, '550e8400-e29b-41d4-a716-446655440015', NULL, 'City D', 'Country D', 'Street D'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '103', '12-139', NULL, '550e8400-e29b-41d4-a716-446655440025', NULL, 'City F', 'Country F', 'Street F'),
    ('2023-01-05 14:00:00', '2023-01-05 14:00:00', 1, '112', '14-151', NULL, '550e8400-e29b-41d4-a716-446655440020', NULL, 'City E', 'Country E', 'Street E'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '193', '19-139', NULL, '550e8400-e29b-41d4-a716-446655440030', NULL, 'City G', 'Country G', 'Street G'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '323', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440035', NULL, 'City I', 'Country I', 'Street I'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '323', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440040', NULL, 'City H', 'Country H', 'Street H'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '323', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440045', NULL, 'City J', 'Country J', 'Street J'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '323', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440050', NULL, 'City K', 'Country K', 'Street K'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '323', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440055', NULL, 'City L', 'Country L', 'Street L'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '323', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440060', NULL, 'City M', 'Country M', 'Street M');


INSERT INTO locals (margin_fee, rental_fee, size, state, created_at, modified_at, version, address_id, created_by, id, modified_by, owner_id, name, description)
VALUES
    (10.50, 1000.00, 50, 3, '2023-01-01 12:00:00', '2023-01-01 12:00:00', 1, '550e8400-e29b-41d4-a716-446655440000', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440002', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local A', 'Description of Local A'),
    (12.00, 1200.00, 60, 2, '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, '550e8400-e29b-41d4-a716-446655440005', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440007', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local B', 'Description of Local B'),
    (15.00, 1500.00, 70, 4, '2023-01-03 14:00:00', '2023-01-03 14:00:00', 1, '550e8400-e29b-41d4-a716-446655440010', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440012', null, null, 'Local C', 'Description of Local C'),
    (20.00, 2000.00, 80, 1, '2023-01-04 15:00:00', '2023-01-04 15:00:00', 1, '550e8400-e29b-41d4-a716-446655440015', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440017', null, null, 'Local D', 'Description of Local D'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440020', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440022', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local F', 'Description of Local F'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440025', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440025', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local G', 'Description of Local G'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440030', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440028', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local H', 'Description of Local H'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440035', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440031', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local I', 'Description of Local I'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440040', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440032', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local J', 'Description of Local J'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440045', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440033', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local K', 'Description of Local K'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440050', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440034', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local L', 'Description of Local L'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440055', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440035', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local M', 'Description of Local M'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440060', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440036', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Local N', 'Description of Local N');

-- Rent 1
INSERT INTO public.rents (balance, end_date, start_date, created_at, modified_at, version, created_by, id, local_id, modified_by, owner_id, tenant_id)
VALUES (1000.00, '2024-06-23', '2024-01-01', '2023-01-01 12:00:00', '2023-01-01 12:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a1111111-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440025', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');
VALUES (1000.00, '2024-06-30', '2024-01-01', '2023-01-01 12:00:00', '2023-01-01 12:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a1111111-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440022', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');

-- Rent 2
INSERT INTO public.rents (balance, end_date, start_date, created_at, modified_at, version, created_by, id, local_id, modified_by, owner_id, tenant_id)
VALUES (1200.00, '2024-06-16', '2024-01-02', '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a2222222-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440028', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');

-- Rent 3
INSERT INTO public.rents (balance, end_date, start_date, created_at, modified_at, version, created_by, id, local_id, modified_by, owner_id, tenant_id)
VALUES (1500.00, '2024-04-03', '2024-01-03', '2023-01-03 14:00:00', '2023-01-03 14:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a3333333-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440031', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');
-- Additional Fixed Fees for Rent 1
INSERT INTO public.fixed_fees (date, margin_fee, rental_fee, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    ('2024-01-01', 15.00, 95.00, '2023-01-01 12:40:00', '2023-01-01 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff1a1114-e29b-41d4-a716-446655440001', null, 'a1111111-e29b-41d4-a716-446655440000'),
    ('2024-02-01', 7.50, 47.50, '2023-01-01 12:50:00', '2023-01-01 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff1a1115-e29b-41d4-a716-446655440001', null, 'a1111111-e29b-41d4-a716-446655440000'),
    ('2024-03-01', 10.00, 67.00, '2023-01-01 13:00:00', '2023-01-01 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff1a1116-e29b-41d4-a716-446655440001', null, 'a1111111-e29b-41d4-a716-446655440000');

-- Additional Fixed Fees for Rent 2
INSERT INTO public.fixed_fees (date, margin_fee, rental_fee, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    ('2024-01-02', 18.00, 112.00, '2023-01-02 12:40:00', '2023-01-02 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff2a1114-e29b-41d4-a716-446655440002', null, 'a2222222-e29b-41d4-a716-446655440000'),
    ('2024-02-02', 9.00, 56.00, '2023-01-02 12:50:00', '2023-01-02 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff2a1115-e29b-41d4-a716-446655440002', null, 'a2222222-e29b-41d4-a716-446655440000'),
    ('2024-03-02', 12.00, 70.00, '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff2a1116-e29b-41d4-a716-446655440002', null, 'a2222222-e29b-41d4-a716-446655440000');

-- Additional Fixed Fees for Rent 3
INSERT INTO public.fixed_fees (date, margin_fee, rental_fee, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    ('2024-04-03', 21.00, 119.00, '2023-01-03 12:40:00', '2023-01-03 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff3a1114-e29b-41d4-a716-446655440003', null, 'a3333333-e29b-41d4-a716-446655440000'),
    ('2024-05-03', 10.50, 64.50, '2023-01-03 12:50:00', '2023-01-03 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff3a1115-e29b-41d4-a716-446655440003', null, 'a3333333-e29b-41d4-a716-446655440000'),
    ('2024-06-03', 13.50, 78.50, '2023-01-03 13:00:00', '2023-01-03 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff3a1116-e29b-41d4-a716-446655440003', null, 'a3333333-e29b-41d4-a716-446655440000');

-- Additional fixed fees follow the same pattern for remaining rents...


-- Payments for Rent 1
INSERT INTO public.payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (300.00, '2023-01-10 12:10:00', '2023-01-10 12:10:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '659451d2-283b-4f45-b0d3-6b3d01ce1610', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-08'),
    (400.00, '2023-01-15 12:20:00', '2023-01-15 12:20:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '1fb46985-bff4-492f-a403-6a7fada1c192', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-16'),
    (300.00, '2023-01-20 12:30:00', '2023-01-20 12:30:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a64e6501-68ed-4be0-a49b-37f05d5538ef', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-23');

-- Payments for Rent 2
INSERT INTO public.payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (400.00, '2023-01-11 12:10:00', '2023-01-11 12:10:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '56aad039-f835-43b1-b99b-5d6cb54b0e7b', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-11'),
    (400.00, '2023-01-16 12:20:00', '2023-01-16 12:20:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '43dd70c4-c85d-4b1c-b877-c1df995bef84', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-16'),
    (400.00, '2023-01-21 12:30:00', '2023-01-21 12:30:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'f41fce24-0662-4d2f-8a15-3c3aa21b34f5', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-21');

-- Payments for Rent 3
INSERT INTO public.payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (500.00, '2023-01-12 12:10:00', '2023-01-12 12:10:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '9dc038a5-bf01-4869-8e15-c9b1f0c0e884', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-12'),
    (500.00, '2023-01-17 12:20:00', '2023-01-17 12:20:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '7d78be21-120b-47fa-bab3-e257039a8149', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-17'),
    (500.00, '2023-01-22 12:30:00', '2023-01-22 12:30:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '8a38fdd6-a596-461e-a85e-ad022a73e76e', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-22');

-- Additional payments follow the same pattern for remaining rents...
-- Additional Payments for Rent 1
INSERT INTO public.payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (310.00, '2023-01-25 12:40:00', '2023-01-25 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '25569df6-6cc8-4350-ac3e-5a04616d5d12', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-25'),
    (410.00, '2023-01-30 12:50:00', '2023-01-30 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'c099836b-d620-4144-9ad4-1040023ba4f8', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-30'),
    (310.00, '2023-02-05 13:00:00', '2023-02-05 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '994ab228-102c-4def-a696-9ab0aebf757b', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-02-05');

-- Additional Payments for Rent 2
INSERT INTO public.payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (420.00, '2023-01-26 12:40:00', '2023-01-26 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '071b5f5b-3eb5-47b0-82c6-85b5ec49c63b', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-26'),
    (420.00, '2023-01-31 12:50:00', '2023-01-31 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'f3781f64-1970-467d-828d-6f724be6cce5', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-31'),
    (420.00, '2023-02-06 13:00:00', '2023-02-06 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '59f20709-cf92-476f-a4b3-0d9362aec916', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-02-06');
-- Additional Payments for Rent 3
INSERT INTO public.payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (530.00, '2023-01-27 12:40:00', '2023-01-27 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'b78f92ad-3ec9-4270-86b2-ede94d2e7aa5', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-27'),
    (530.00, '2023-02-01 12:50:00', '2023-02-01 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '679f1b4f-64f2-4f2f-b08e-0879516615fb', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-02-01'),
    (530.00, '2023-02-07 13:00:00', '2023-02-07 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '84081b9e-41cb-4b33-ab38-2a3af6b5a780', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-02-07');

-- Additional payments follow the same pattern for remaining rents...

-- Additional Variable Fees for Rent 1
INSERT INTO public.variable_fees (amount, date, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    (15.00, '2024-01-01', '2023-01-01 12:40:00', '2023-01-01 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'eca37199-fe8b-409e-9346-c3d798b9f3ad', null, 'a1111111-e29b-41d4-a716-446655440000'),
    (7.50, '2024-02-01', '2023-01-01 12:50:00', '2023-01-01 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '4796fb20-dcf8-4ad8-bd36-9db10b7fbc66', null, 'a1111111-e29b-41d4-a716-446655440000'),
    (10.00, '2024-03-01', '2023-01-01 13:00:00', '2023-01-01 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '1faf1d81-4716-40df-95fb-e2327493559b', null, 'a1111111-e29b-41d4-a716-446655440000');

-- Additional Variable Fees for Rent 2
INSERT INTO public.variable_fees (amount, date, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    (18.00, '2024-02-02', '2023-01-02 12:40:00', '2023-01-02 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '48ef2f1f-f1a1-462d-9537-d14ad551de4b', null, 'a2222222-e29b-41d4-a716-446655440000'),
    (9.00, '2024-03-02', '2023-01-02 12:50:00', '2023-01-02 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '10ea5949-b7be-4018-b41d-f21e7fdd55c1', null, 'a2222222-e29b-41d4-a716-446655440000'),
    (12.00, '2024-04-02', '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '1e391d79-afb8-48d2-82cf-b55f12b3296b', null, 'a2222222-e29b-41d4-a716-446655440000');

-- Additional Variable Fees for Rent 3
INSERT INTO public.variable_fees (amount, date, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    (21.00, '2024-04-03', '2023-01-03 12:40:00', '2023-01-03 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '10076d55-7cff-4648-9458-035347737ca1', null, 'a3333333-e29b-41d4-a716-446655440000'),
    (10.50, '2024-05-03', '2023-01-03 12:50:00', '2023-01-03 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'd6f388ff-1381-4aae-b73f-b624ecd73791', null, 'a3333333-e29b-41d4-a716-446655440000'),
    (13.50, '2024-06-03', '2023-01-03 13:00:00', '2023-01-03 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'c6e1ecaf-5545-4510-8387-65af92a0e716', null, 'a3333333-e29b-41d4-a716-446655440000');

-- Additional variable fees follow the same pattern for remaining rents...
-- Applications
INSERT INTO public.applications
(id, tenant_id, local_id, created_at, modified_at, version, created_by, modified_by)
VALUES
    ('a1d11111-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440032', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a2d22222-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440033', '2024-01-02 11:00:00', '2024-01-02 11:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a3d33333-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440034', '2024-01-03 12:00:00', '2024-01-03 12:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a4d44444-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440035', '2024-01-04 13:00:00', '2024-01-04 13:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a4d44446-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440036', '2024-01-04 13:00:00', '2024-01-04 13:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d');


INSERT INTO public.users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('e872ce90-734b-49a9-8949-dde4753b8864', 'admin2', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.',
        false, true, 0, 1, 'PL', NOW(), NOW(), null, null, true);
INSERT INTO public.personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('e872ce90-734b-49a9-8949-dde4753b8864', 'kozakonrad72@gmail.com', null, 'Admin', 'Admin');
INSERT INTO public.access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('2dbe6d57-54ca-4662-96b4-149d72a8ab44', 'e872ce90-734b-49a9-8949-dde4753b8864', 'ADMINISTRATOR', true, 0, NOW(),
        NOW(), null, null);
INSERT INTO public.administrators (id)
VALUES ('2dbe6d57-54ca-4662-96b4-149d72a8ab44');

