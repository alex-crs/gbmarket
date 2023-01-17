create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       int,
    image       varchar(255),
    description varchar(1000),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into products(title, price, image, description)

values ('Молоко', 80, '', 'Замечательное молоко'),
       ('Хлеб', 25, '', 'Свежий хлеб'),
       ('Яйца', 78, '', 'Яйца от добрых кур'),
       ('Паштет', 99, '', 'Паштет из славных гусей'),
       ('Сливки', 76, '', 'Замечательные сливки от михалыча'),
       ('Докторская колбаса', 240, '', 'Прекрасная колбаса из натуральных ингредиентов из чистого мяса'),
       ('Йогурт', 46, '', 'Термостатный йогурт Danone'),
       ('Ряженка', 53, '', 'Замечательная Ряженка "Простоквашино"- вкус традиций!'),
       ('Отруби пшеничные', 59, '', 'Полезные отруби из пшеницы'),
       ('Конфеты "Халва в шоколаде" (РотФронт)', 320, '',
        'Великолепная халва облитая шоколадом не оставит никого равнодушным'),
       ('Бекон', 190, '', 'Вкуснейший бекон. Подойдет для завтрака Гурманам и ценителям вкуса и качества'),
       ('Пиво "Аян"', 44, '', 'Сварено по старинному традиционному рецепту'),
       ('Сыр косичка', 90, '', 'Качественный копченый сыр, подойдет в качестве закуски или отдельного блюда'),
       ('Мёд саянский', 560, '', 'Мед высокого качества, свежей качки, разнотравье.'),
       ('Соль', 78, '', 'Качественная морская соль, подойдет как для лечения так и в качестве дополнения к блюдам'),
       ('Курица свежая', 420, '', 'Великолепная курица, выращенная на натуральных кормах без применения химии'),
       ('Корм кошачий', 1100, '',
        'Если хотите что бы ваш питомец был здоровым, берите этот корм. Корм содержит весь необходимый набор витаминов и микроэлементов для здоровья вашего любимца'),
       ('Сосиски морковные', 169, '',
        'Если вы следите за своей фигурой или поститесь - эти сосиски Ваше спасение. Мало того что они имеют великолепный вкус, они еще и крайне полезные'),
       ('Лапша удон', 76, '', 'Лапша ручного изготовления, обладает потрясающим вкусом.'),
       ('Сыр', 300, '', 'Вкусный Ужурский сыр');

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
