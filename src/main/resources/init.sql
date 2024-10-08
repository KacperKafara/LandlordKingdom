GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.users TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.personal_data TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.google_auth TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.administrators TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.tenants TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.owners TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.access_levels TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.old_passwords TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, DELETE ON ssbd02.tokens TO 'ssbd02mok'@'%';
GRANT SELECT ON ssbd02.timezones TO 'ssbd02mok'@'%';
GRANT SELECT ON ssbd02.themes TO 'ssbd02mok'@'%';
GRANT SELECT, INSERT, UPDATE ON ssbd02.user_filters TO 'ssbd02mok'@'%';
GRANT SELECT, DELETE ON ssbd02.role_requests TO 'ssbd02mok'@'%';

GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.locals TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.addresses TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.applications TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.fixed_fees TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.payments TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.rents TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.role_requests TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON ssbd02.variable_fees TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.users TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.personal_data TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.google_auth TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.timezones TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.themes TO 'ssbd02mol'@'%';

GRANT SELECT, INSERT, UPDATE ON ssbd02.users TO 'ssbd02auth'@'%';
GRANT SELECT ON ssbd02.personal_data TO 'ssbd02auth'@'%';
GRANT SELECT, INSERT, UPDATE ON ssbd02.google_auth TO 'ssbd02auth'@'%';
GRANT SELECT ON ssbd02.timezones TO 'ssbd02auth'@'%';
GRANT SELECT ON ssbd02.themes TO 'ssbd02auth'@'%';
GRANT SELECT ON ssbd02.administrators TO 'ssbd02auth'@'%';
GRANT SELECT ON ssbd02.tenants TO 'ssbd02auth'@'%';
GRANT SELECT ON ssbd02.owners TO 'ssbd02auth'@'%';
GRANT SELECT ON ssbd02.access_levels TO 'ssbd02auth'@'%';
GRANT SELECT, INSERT, DELETE ON ssbd02.tokens TO 'ssbd02auth'@'%';

GRANT SELECT ON ssbd02.tenants TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.personal_data TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.users TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.themes TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.timezones TO 'ssbd02mol'@'%';
GRANT SELECT ON ssbd02.google_auth TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT ON ssbd02.owners TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, UPDATE ON ssbd02.access_levels TO 'ssbd02mol'@'%';
GRANT SELECT, INSERT, DELETE ON ssbd02.images TO 'ssbd02mol'@'%';

FLUSH PRIVILEGES;

INSERT INTO users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'admin', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.',
        false, true, 0, 1, 'EN', NOW(), NOW(), null, null, true);
INSERT INTO personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('d42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'kacperkafara18@gmail.com', null, 'Admin', 'Admin');
INSERT INTO access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('22f34716-3b77-4e63-809d-35f9a4758011', 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ADMINISTRATOR', true, 0, NOW(),
        NOW(), null, null);
INSERT INTO administrators (id)
VALUES ('22f34716-3b77-4e63-809d-35f9a4758011');

INSERT INTO users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('05854132-8b7c-440e-9ef2-8fe46a7962dc', 'tenant',
        '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.', false, true, 0, 1, 'PL', NOW(), NOW(), null,
        null, true);
INSERT INTO personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('05854132-8b7c-440e-9ef2-8fe46a7962dc', 'tenant@mail.com', null, 'Tenant', 'Tenant');
INSERT INTO access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('4b329d71-2a92-4e90-8f0b-f673e4f79529', '05854132-8b7c-440e-9ef2-8fe46a7962dc', 'TENANT', true, 0, NOW(), NOW(),
        null, null);
INSERT INTO tenants (id)
VALUES ('4b329d71-2a92-4e90-8f0b-f673e4f79529');

INSERT INTO users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'test', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.',
        false, true, 0, 1, 'EN', NOW(), NOW(), null, null, true);
INSERT INTO personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', '242374@edu.p.lodz.pl', null, 'test', 'test');
INSERT INTO access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('6bcef94d-16f5-401b-a0d0-9461257572f6', '2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'TENANT', true, 0, NOW(), NOW(),
        null, null);
INSERT INTO tenants (id)
VALUES ('6bcef94d-16f5-401b-a0d0-9461257572f6');
INSERT INTO access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('397cf3c5-f369-4783-9892-6fc781fd2b0d', '2d56f6d5-2dfd-4003-89d9-9e9ac6c145c9', 'OWNER', true, 0, NOW(), NOW(),
        null, null);
INSERT INTO owners (id)
VALUES ('397cf3c5-f369-4783-9892-6fc781fd2b0d');

INSERT INTO themes
(id, type)
VALUES (UUID(), 'light'),
       (UUID(), 'dark');

INSERT INTO timezones
(id, name)
VALUES (UUID(), 'Pacific/Midway'),
       (UUID(), 'Pacific/Honolulu'),
       (UUID(), 'America/Juneau'),
       (UUID(), 'America/Dawson'),
       (UUID(), 'America/Phoenix'),
       (UUID(), 'America/Tijuana'),
       (UUID(), 'America/Los_Angeles'),
       (UUID(), 'America/Boise'),
       (UUID(), 'America/Chihuahua'),
       (UUID(), 'America/Regina'),
       (UUID(), 'America/Mexico_City'),
       (UUID(), 'America/Belize'),
       (UUID(), 'America/Chicago'),
       (UUID(), 'America/Bogota'),
       (UUID(), 'America/Detroit'),
       (UUID(), 'America/Caracas'),
       (UUID(), 'America/Santiago'),
       (UUID(), 'America/Sao_Paulo'),
       (UUID(), 'America/Montevideo'),
       (UUID(), 'America/Argentina/Buenos_Aires'),
       (UUID(), 'America/St_Johns'),
       (UUID(), 'America/Godthab'),
       (UUID(), 'Atlantic/Cape_Verde'),
       (UUID(), 'Atlantic/Azores'),
       (UUID(), 'Etc/GMT'),
       (UUID(), 'Europe/London'),
       (UUID(), 'Europe/Dublin'),
       (UUID(), 'Europe/Lisbon'),
       (UUID(), 'Africa/Casablanca'),
       (UUID(), 'Atlantic/Canary'),
       (UUID(), 'Africa/Algiers'),
       (UUID(), 'Europe/Belgrade'),
       (UUID(), 'Europe/Sarajevo'),
       (UUID(), 'Europe/Brussels'),
       (UUID(), 'Europe/Amsterdam'),
       (UUID(), 'Africa/Harare'),
       (UUID(), 'Europe/Bucharest'),
       (UUID(), 'Africa/Cairo'),
       (UUID(), 'Europe/Helsinki'),
       (UUID(), 'Europe/Athens'),
       (UUID(), 'Asia/Jerusalem'),
       (UUID(), 'Europe/Moscow'),
       (UUID(), 'Asia/Kuwait'),
       (UUID(), 'Africa/Nairobi'),
       (UUID(), 'Asia/Baghdad'),
       (UUID(), 'Asia/Tehran'),
       (UUID(), 'Asia/Dubai'),
       (UUID(), 'Asia/Baku'),
       (UUID(), 'Asia/Kabul'),
       (UUID(), 'Asia/Yekaterinburg'),
       (UUID(), 'Asia/Karachi'),
       (UUID(), 'Asia/Kolkata'),
       (UUID(), 'Asia/Colombo'),
       (UUID(), 'Asia/Kathmandu'),
       (UUID(), 'Asia/Dhaka'),
       (UUID(), 'Asia/Almaty'),
       (UUID(), 'Asia/Rangoon'),
       (UUID(), 'Asia/Bangkok'),
       (UUID(), 'Asia/Krasnoyarsk'),
       (UUID(), 'Asia/Shanghai'),
       (UUID(), 'Asia/Kuala_Lumpur'),
       (UUID(), 'Asia/Taipei'),
       (UUID(), 'Australia/Perth'),
       (UUID(), 'Asia/Irkutsk'),
       (UUID(), 'Asia/Seoul'),
       (UUID(), 'Asia/Tokyo'),
       (UUID(), 'Asia/Yakutsk'),
       (UUID(), 'Australia/Darwin'),
       (UUID(), 'Australia/Adelaide'),
       (UUID(), 'Australia/Sydney'),
       (UUID(), 'Australia/Brisbane'),
       (UUID(), 'Australia/Hobart'),
       (UUID(), 'Asia/Vladivostok'),
       (UUID(), 'Pacific/Guam'),
       (UUID(), 'Asia/Magadan'),
       (UUID(), 'Asia/Kamchatka'),
       (UUID(), 'Pacific/Fiji'),
       (UUID(), 'Pacific/Auckland'),
       (UUID(), 'Pacific/Tongatapu');

INSERT INTO addresses (created_at, modified_at, version, number, zip, created_by, id, modified_by, city, country, street)
VALUES
    ('2023-01-01 10:00:00', '2023-01-01 10:00:00', 1, '123', '12-345', NULL, '550e8400-e29b-41d4-a716-446655440000', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-02 11:00:00', '2023-01-02 11:00:00', 1, '456', '67-890', NULL, '550e8400-e29b-41d4-a716-446655440005', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-03 12:00:00', '2023-01-03 12:00:00', 1, '789', '10-111', NULL, '550e8400-e29b-41d4-a716-446655440010', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '101', '12-131', NULL, '550e8400-e29b-41d4-a716-446655440015', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '103', '12-139', NULL, '550e8400-e29b-41d4-a716-446655440025', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-05 14:00:00', '2023-01-05 14:00:00', 1, '104', '14-151', NULL, '550e8400-e29b-41d4-a716-446655440020', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '105', '19-139', NULL, '550e8400-e29b-41d4-a716-446655440030', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '106', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440035', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '107', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440040', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '108', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440045', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '109', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440050', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '110', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440055', NULL, 'Warszawa', 'Polska', 'Różana'),
    ('2023-01-04 13:00:00', '2023-01-04 13:00:00', 1, '111', '19-142', NULL, '550e8400-e29b-41d4-a716-446655440060', NULL, 'Warszawa', 'Polska', 'Różana');

INSERT INTO locals (margin_fee, rental_fee, size, state, created_at, modified_at, version, address_id, created_by, id, modified_by, owner_id, name, description)
VALUES
    (10.50, 1000.00, 50, 3, '2023-01-01 12:00:00', '2023-01-01 12:00:00', 1, '550e8400-e29b-41d4-a716-446655440000', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440002', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Uczta filozofów', 'Pięciu filozofów bije się o widelce?'),
    (12.00, 1200.00, 60, 2, '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, '550e8400-e29b-41d4-a716-446655440005', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440007', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Ciepły zakątek', 'Przytulne miejsce na nocleg'),
    (15.00, 1500.00, 70, 4, '2023-01-03 14:00:00', '2023-01-03 14:00:00', 1, '550e8400-e29b-41d4-a716-446655440010', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440012', null, null, 'Na Różanej', 'Przytulne miejsce na nocleg'),
    (20.00, 2000.00, 80, 1, '2023-01-04 15:00:00', '2023-01-04 15:00:00', 1, '550e8400-e29b-41d4-a716-446655440015', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440017', null, null, 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440020', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440022', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440025', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440025', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla emerytów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440030', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440028', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 5, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440035', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440031', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440040', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440032', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440045', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440033', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440050', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440034', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440055', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440035', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg'),
    (25.00, 2500.00, 90, 2, '2023-01-05 16:00:00', '2023-01-05 16:00:00', 1, '550e8400-e29b-41d4-a716-446655440060', '397cf3c5-f369-4783-9892-6fc781fd2b0d', '550e8400-e29b-41d4-a716-446655440036', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', 'Lokal idealny dla studentów', 'Przytulne miejsce na nocleg');

-- Rent 1
INSERT INTO rents (balance, end_date, start_date, created_at, modified_at, version, created_by, id, local_id, modified_by, owner_id, tenant_id)
VALUES (1000.00, '2024-06-23', '2024-01-01', '2023-01-01 12:00:00', '2023-01-01 12:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a1111111-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440025', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');
VALUES (1000.00, '2024-06-30', '2024-01-01', '2023-01-01 12:00:00', '2023-01-01 12:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a1111111-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440022', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');

-- Rent 2
INSERT INTO rents (balance, end_date, start_date, created_at, modified_at, version, created_by, id, local_id, modified_by, owner_id, tenant_id)
VALUES (1200.00, '2024-06-16', '2024-01-02', '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a2222222-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440028', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');

-- Rent 3
INSERT INTO rents (balance, end_date, start_date, created_at, modified_at, version, created_by, id, local_id, modified_by, owner_id, tenant_id)
VALUES (1500.00, '2024-04-03', '2024-01-03', '2023-01-03 14:00:00', '2023-01-03 14:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a3333333-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440031', null, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '4b329d71-2a92-4e90-8f0b-f673e4f79529');
-- Additional Fixed Fees for Rent 1
INSERT INTO fixed_fees (date, margin_fee, rental_fee, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    ('2024-01-01', 15.00, 95.00, '2023-01-01 12:40:00', '2023-01-01 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff1a1114-e29b-41d4-a716-446655440001', null, 'a1111111-e29b-41d4-a716-446655440000'),
    ('2024-02-01', 7.50, 47.50, '2023-01-01 12:50:00', '2023-01-01 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff1a1115-e29b-41d4-a716-446655440001', null, 'a1111111-e29b-41d4-a716-446655440000'),
    ('2024-03-01', 10.00, 67.00, '2023-01-01 13:00:00', '2023-01-01 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff1a1116-e29b-41d4-a716-446655440001', null, 'a1111111-e29b-41d4-a716-446655440000');

-- Additional Fixed Fees for Rent 2
INSERT INTO fixed_fees (date, margin_fee, rental_fee, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    ('2024-01-02', 18.00, 112.00, '2023-01-02 12:40:00', '2023-01-02 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff2a1114-e29b-41d4-a716-446655440002', null, 'a2222222-e29b-41d4-a716-446655440000'),
    ('2024-02-02', 9.00, 56.00, '2023-01-02 12:50:00', '2023-01-02 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff2a1115-e29b-41d4-a716-446655440002', null, 'a2222222-e29b-41d4-a716-446655440000'),
    ('2024-03-02', 12.00, 70.00, '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff2a1116-e29b-41d4-a716-446655440002', null, 'a2222222-e29b-41d4-a716-446655440000');

-- Additional Fixed Fees for Rent 3
INSERT INTO fixed_fees (date, margin_fee, rental_fee, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    ('2024-04-03', 21.00, 119.00, '2023-01-03 12:40:00', '2023-01-03 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff3a1114-e29b-41d4-a716-446655440003', null, 'a3333333-e29b-41d4-a716-446655440000'),
    ('2024-05-03', 10.50, 64.50, '2023-01-03 12:50:00', '2023-01-03 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff3a1115-e29b-41d4-a716-446655440003', null, 'a3333333-e29b-41d4-a716-446655440000'),
    ('2024-06-03', 13.50, 78.50, '2023-01-03 13:00:00', '2023-01-03 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'ff3a1116-e29b-41d4-a716-446655440003', null, 'a3333333-e29b-41d4-a716-446655440000');

-- Additional fixed fees follow the same pattern for remaining rents...


-- Payments for Rent 1
INSERT INTO payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (300.00, '2023-01-10 12:10:00', '2023-01-10 12:10:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '659451d2-283b-4f45-b0d3-6b3d01ce1610', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-08'),
    (400.00, '2023-01-15 12:20:00', '2023-01-15 12:20:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '1fb46985-bff4-492f-a403-6a7fada1c192', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-16'),
    (300.00, '2023-01-20 12:30:00', '2023-01-20 12:30:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'a64e6501-68ed-4be0-a49b-37f05d5538ef', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-23');

-- Payments for Rent 2
INSERT INTO payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (400.00, '2023-01-11 12:10:00', '2023-01-11 12:10:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '56aad039-f835-43b1-b99b-5d6cb54b0e7b', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-11'),
    (400.00, '2023-01-16 12:20:00', '2023-01-16 12:20:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '43dd70c4-c85d-4b1c-b877-c1df995bef84', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-16'),
    (400.00, '2023-01-21 12:30:00', '2023-01-21 12:30:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'f41fce24-0662-4d2f-8a15-3c3aa21b34f5', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-21');

-- Payments for Rent 3
INSERT INTO payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (500.00, '2023-01-12 12:10:00', '2023-01-12 12:10:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '9dc038a5-bf01-4869-8e15-c9b1f0c0e884', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-12'),
    (500.00, '2023-01-17 12:20:00', '2023-01-17 12:20:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '7d78be21-120b-47fa-bab3-e257039a8149', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-17'),
    (500.00, '2023-01-22 12:30:00', '2023-01-22 12:30:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '8a38fdd6-a596-461e-a85e-ad022a73e76e', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-22');

-- Additional payments follow the same pattern for remaining rents...
-- Additional Payments for Rent 1
INSERT INTO payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (310.00, '2023-01-25 12:40:00', '2023-01-25 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '25569df6-6cc8-4350-ac3e-5a04616d5d12', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-25'),
    (410.00, '2023-01-30 12:50:00', '2023-01-30 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'c099836b-d620-4144-9ad4-1040023ba4f8', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-01-30'),
    (310.00, '2023-02-05 13:00:00', '2023-02-05 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '994ab228-102c-4def-a696-9ab0aebf757b', null, 'a1111111-e29b-41d4-a716-446655440000', '2024-02-05');

-- Additional Payments for Rent 2
INSERT INTO payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (420.00, '2023-01-26 12:40:00', '2023-01-26 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '071b5f5b-3eb5-47b0-82c6-85b5ec49c63b', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-26'),
    (420.00, '2023-01-31 12:50:00', '2023-01-31 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'f3781f64-1970-467d-828d-6f724be6cce5', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-01-31'),
    (420.00, '2023-02-06 13:00:00', '2023-02-06 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '59f20709-cf92-476f-a4b3-0d9362aec916', null, 'a2222222-e29b-41d4-a716-446655440000', '2024-02-06');
-- Additional Payments for Rent 3
INSERT INTO payments (amount, created_at, modified_at, version, created_by, id, modified_by, rent_id, date)
VALUES
    (530.00, '2023-01-27 12:40:00', '2023-01-27 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'b78f92ad-3ec9-4270-86b2-ede94d2e7aa5', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-01-27'),
    (530.00, '2023-02-01 12:50:00', '2023-02-01 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '679f1b4f-64f2-4f2f-b08e-0879516615fb', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-02-01'),
    (530.00, '2023-02-07 13:00:00', '2023-02-07 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '84081b9e-41cb-4b33-ab38-2a3af6b5a780', null, 'a3333333-e29b-41d4-a716-446655440000', '2024-02-07');

-- Additional payments follow the same pattern for remaining rents...

-- Additional Variable Fees for Rent 1
INSERT INTO variable_fees (amount, date, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    (15.00, '2024-01-01', '2023-01-01 12:40:00', '2023-01-01 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'eca37199-fe8b-409e-9346-c3d798b9f3ad', null, 'a1111111-e29b-41d4-a716-446655440000'),
    (7.50, '2024-02-01', '2023-01-01 12:50:00', '2023-01-01 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '4796fb20-dcf8-4ad8-bd36-9db10b7fbc66', null, 'a1111111-e29b-41d4-a716-446655440000'),
    (10.00, '2024-03-01', '2023-01-01 13:00:00', '2023-01-01 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '1faf1d81-4716-40df-95fb-e2327493559b', null, 'a1111111-e29b-41d4-a716-446655440000');

-- Additional Variable Fees for Rent 2
INSERT INTO variable_fees (amount, date, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    (18.00, '2024-02-02', '2023-01-02 12:40:00', '2023-01-02 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '48ef2f1f-f1a1-462d-9537-d14ad551de4b', null, 'a2222222-e29b-41d4-a716-446655440000'),
    (9.00, '2024-03-02', '2023-01-02 12:50:00', '2023-01-02 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '10ea5949-b7be-4018-b41d-f21e7fdd55c1', null, 'a2222222-e29b-41d4-a716-446655440000'),
    (12.00, '2024-04-02', '2023-01-02 13:00:00', '2023-01-02 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '1e391d79-afb8-48d2-82cf-b55f12b3296b', null, 'a2222222-e29b-41d4-a716-446655440000');

-- Additional Variable Fees for Rent 3
INSERT INTO variable_fees (amount, date, created_at, modified_at, version, created_by, id, modified_by, rent_id)
VALUES
    (21.00, '2024-04-03', '2023-01-03 12:40:00', '2023-01-03 12:40:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', '10076d55-7cff-4648-9458-035347737ca1', null, 'a3333333-e29b-41d4-a716-446655440000'),
    (10.50, '2024-05-03', '2023-01-03 12:50:00', '2023-01-03 12:50:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'd6f388ff-1381-4aae-b73f-b624ecd73791', null, 'a3333333-e29b-41d4-a716-446655440000'),
    (13.50, '2024-06-03', '2023-01-03 13:00:00', '2023-01-03 13:00:00', 1, 'd42d399a-59cd-4895-a48c-4a3b2a9e46d1', 'c6e1ecaf-5545-4510-8387-65af92a0e716', null, 'a3333333-e29b-41d4-a716-446655440000');

-- Additional variable fees follow the same pattern for remaining rents...
-- Applications
INSERT INTO applications
(id, tenant_id, local_id, created_at, modified_at, version, created_by, modified_by)
VALUES
    ('a1d11111-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440032', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a2d22222-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440033', '2024-01-02 11:00:00', '2024-01-02 11:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a3d33333-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440034', '2024-01-03 12:00:00', '2024-01-03 12:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a4d44444-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440035', '2024-01-04 13:00:00', '2024-01-04 13:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d'),
    ('a4d44446-e29b-41d4-a716-446655440000', '4b329d71-2a92-4e90-8f0b-f673e4f79529', '550e8400-e29b-41d4-a716-446655440036', '2024-01-04 13:00:00', '2024-01-04 13:00:00', 1, '397cf3c5-f369-4783-9892-6fc781fd2b0d', '397cf3c5-f369-4783-9892-6fc781fd2b0d');


INSERT INTO users (id, login, password, blocked, verified, login_attempts, version, language, created_at,
                          modified_at, created_by, modified_by, active)
VALUES ('e872ce90-734b-49a9-8949-dde4753b8864', 'admin2', '$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.',
        false, true, 0, 1, 'PL', NOW(), NOW(), null, null, true);
INSERT INTO personal_data (user_id, email, temp_email, first_name, last_name)
VALUES ('e872ce90-734b-49a9-8949-dde4753b8864', 'admin2@mail.com', null, 'Admin', 'Admin');
INSERT INTO access_levels (id, user_id, level, active, version, created_at, modified_at, created_by, modified_by)
VALUES ('2dbe6d57-54ca-4662-96b4-149d72a8ab44', 'e872ce90-734b-49a9-8949-dde4753b8864', 'ADMINISTRATOR', true, 0, NOW(),
        NOW(), null, null);
INSERT INTO administrators (id)
VALUES ('2dbe6d57-54ca-4662-96b4-149d72a8ab44');