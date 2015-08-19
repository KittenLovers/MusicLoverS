DROP TABLE IF EXISTS MSL_ORDER;
DROP TABLE IF EXISTS MSL_PROFESSIONAL;
DROP TABLE IF EXISTS MSL_CUSTOMER;
DROP TABLE IF EXISTS MSL_EMPLOYER;
DROP TABLE IF EXISTS MSL_ACCOUNT;
DROP TABLE IF EXISTS MSL_PRODUCT_IMAGES;
DROP TABLE IF EXISTS MSL_PRODUCT;
DROP TABLE IF EXISTS MSL_BRAND;

CREATE TABLE IF NOT EXISTS MSL_BRAND (
  id SERIAL PRIMARY KEY,
  name VARCHAR(64) not null,
  description TEXT not null,
  link VARCHAR(256) not null,
  logo VARCHAR(256) not null
);

CREATE TABLE IF NOT EXISTS MSL_PRODUCT (
  id SERIAL PRIMARY KEY,
  status BOOLEAN not null,
  online BOOLEAN not null,
  weight FLOAT not null,
  price FLOAT not null,
  name VARCHAR(64) not null,
  description TEXT not null,
  inexpensive BOOLEAN,
  professional BOOLEAN,
  for_child BOOLEAN,
  min_age INT,
  used BOOLEAN,
  brand_id INT references MSL_BRAND(id)
);

CREATE TABLE IF NOT EXISTS MSL_PRODUCT_IMAGES (
  id SERIAL PRIMARY KEY,
  image VARCHAR(64),
  product_id INT not null references MSL_PRODUCT(id)
);

CREATE TABLE IF NOT EXISTS MSL_ACCOUNT (
  id SERIAL PRIMARY KEY,
  username VARCHAR(64) not null,
  password CHAR(256) not null
);

CREATE TABLE IF NOT EXISTS MSL_EMPLOYER (
  id SERIAL PRIMARY KEY,
  code CHAR(16) not null,
  birthdate DATE not null,
  name VARCHAR(64) not null,
  surname VARCHAR(64) not null,
  account_id INT not null references MSL_ACCOUNT(id)
);

CREATE TABLE IF NOT EXISTS MSL_CUSTOMER (
  id SERIAL PRIMARY KEY,
  code CHAR(16) not null,
  name VARCHAR(64) not null,
  surname VARCHAR(64) not null,
  city VARCHAR(64) not null,
  telephone VARCHAR(64),
  mobile VARCHAR(64),
  email VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS MSL_PROFESSIONAL (
  customer_id INT not null UNIQUE references MSL_CUSTOMER(id),
  account_id INT not null UNIQUE references MSL_ACCOUNT(id),
  role VARCHAR(64) not null,
  reduction INT not null
);

CREATE TABLE IF NOT EXISTS MSL_ORDER (
  id SERIAL PRIMARY KEY,
  price INT not null,
  sold_date TIMESTAMP,
  payment_type VARCHAR(45),
  product_id INT not null references MSL_PRODUCT(id),
  empoyer_id INT not null references MSL_EMPLOYER(id),
  buyer_id INT not null references MSL_CUSTOMER(id),
  owner_id INT references MSL_PROFESSIONAL(customer_id)
);


