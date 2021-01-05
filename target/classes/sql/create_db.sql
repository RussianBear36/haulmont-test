create table bank (
    id binary(255) not null,
    name varchar(255),
    primary key (id)
);

create table credit (
    id binary(255) not null,
    deleted boolean not null,
    interest_rate double not null,
    limit bigint not null,
    name varchar(255),
    bank_id binary(255),
    primary key (id)
);

create table credit_offer (
    id binary(255) not null,
    credit_condition_on_registration_moment varchar(255),
    credit_sum bigint not null,
    credit_term integer not null,
    total_payments double not null,
    credit_id binary(255),
    user_id binary(255),
    primary key (id)
);

create table payment_schedule (
    id binary(255) not null,
    amount_of_payment double not null,
    date varchar(255),
    interest_amount double not null,
    loan_body_amount double not null,
    credit_offer_id binary(255),
    primary key (id)
);

create table user (
    id binary(255) not null,
    email varchar(255),
    family varchar(255),
    firstname varchar(255),
    lastname varchar(255),
    passport varchar(255),
    phonenumber varchar(255),
    primary key (id)
);

create table client_banks_list (
    user_id binary(255) not null,
    bank_id binary(255) not null,
    primary key (bank_id, user_id)
);

alter table client_banks_list add constraint bank_id_fk foreign key (bank_id) references bank
alter table client_banks_list add constraint user_id_fk foreign key (user_id) references user
alter table credit add constraint credit_bank_fk foreign key (bank_id) references bank
alter table credit_offer add constraint creditOffer_credit_fk foreign key (credit_id) references credit
alter table credit_offer add constraint creditOffer_user_fk foreign key (user_id) references user
alter table payment_schedule add constraint payment_schedule_credit_offer_fk foreign key (credit_offer_id) references credit_offer