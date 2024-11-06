truncate table wallet_entity cascade;
truncate table user_entity cascade;
truncate table transaction_entity cascade;

insert into wallet_entity (wallet_id, balance, pin)values
(500, 400.00, '2222'),
(501, 600.0, '1111'),
(502, 500.0, '5555');

insert into user_entity (id, email, first_name, last_name, password, wallet_entity_id)values
(200, 'msonter@gmail.com', 'mson', 'ter', 'password', 501),
(201, 'johndoe@gmail.com','john', 'doe', 'password', 502);