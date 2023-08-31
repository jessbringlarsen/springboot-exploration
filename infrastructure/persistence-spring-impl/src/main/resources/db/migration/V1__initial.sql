create table customer (
    id uuid not null,
    name varchar(255) not null,
    primary key (id)
);

create table account (
    id uuid not null,
    customer_id uuid not null,
    name varchar(255) not null,
    primary key (id),
    foreign key (customer_id) references customer (id)
);

create table transaction_table (
    id uuid not null,
    account_id uuid not null,
    text varchar(255) not null,
    amount decimal,
    transaction_date timestamp,
    primary key (id),
    foreign key (account_id) references account (id)
);
