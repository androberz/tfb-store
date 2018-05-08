drop table if exists CONTACTS;
create table CONTACTS (CONTACT_ID int(10) unsigned not null auto_increment,
                       CONTACT_NAME varchar(255) not null unique,
                       primary key (CONTACT_ID));

drop table if exists PRODUCTS;
create table PRODUCTS (APPLICATION_ID int(10) unsigned not null auto_increment,
                       PRODUCT_NAME varchar(255) not null unique,
                       DT_CREATED datetime,
                       primary key (APPLICATION_ID));

drop table if exists CONTACT_PRODUCT;
create table CONTACT_PRODUCT (CONTACT_ID int(10) unsigned not null,
                              APPLICATION_ID int(10) unsigned not null,
                              primary key (CONTACT_ID, APPLICATION_ID),
                              constraint fk_contactproduct_contact foreign key (CONTACT_ID) references CONTACTS(CONTACT_ID) on delete cascade on update cascade,
                              constraint fk_contactproduct_product foreign key (APPLICATION_ID) references PRODUCTS(APPLICATION_ID) on delete cascade on update cascade);



