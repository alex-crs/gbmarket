create table products
(
    id         bigserial primary key,
    title      varchar(255),
    price      int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into products(title, price)

values ('Milk', 80),
       ('Bread', 25),
       ('Cheese', 300);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price int          not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    title             varchar(255),
    amount            int,
    price_per_product int,
    total_price       int,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);
