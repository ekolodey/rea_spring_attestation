INSERT INTO person(id, login, "password", "role")
VALUES
    (1, 'admin', '$2a$10$VgUFcAGs03NyB1wk3EDt5O43kaQ6fHPVyl3J2PlNQd8.RDlsoKnlG', 'ROLE_ADMIN'),
    (2, 'user1', '$2a$10$J9w1mKOWL7au4QynJFqTYuA6iu8v4V45TzDR2UdHX8h2zI5OXXZHa', 'ROLE_USER'),
    (3, 'user2', '$2a$10$RbPMdI7WiKSPwxru.MpELOJmEnsmaoAFEn4Bzb22Ghzd5OVUisXUe', 'ROLE_USER')
on conflict do nothing;


INSERT INTO order_status(id, name)
VALUES
    (1,'Принят'),
    (2,'Оформлен'),
    (3,'Ожидает'),
    (4,'Получен')
on conflict do nothing;