create table products
(
    id         bigserial primary key,
    title      varchar(255),
    price      int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table cart_order
(
    id           bigserial primary key,
    order_number double
);

create table cart_item
(
    id       bigserial primary key,
    item_id  double,
    title    varchar(255),
    price    int,
    order_id bigint references cart_order (id) ON DELETE CASCADE

);



insert into products(title, price)

values ('Milk', 80),
       ('Bread', 25),
       ('Cheese', 300);

create table users
(
    id       bigserial primary key,
    name     varchar(36) not null,
    password varchar(80) not null
);

create table roles
(
    id   bigserial primary key,
    name varchar(50) not null
);

create table user_roles
(
    user_id    bigint not null references users (id),
    role_id    bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (name, password)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');

insert into user_roles (user_id, role_id)
values (1, 1),
       (2, 2);