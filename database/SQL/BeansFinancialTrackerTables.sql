CREATE TABLE "user" (
  "id" integer PRIMARY KEY,
  "name" varchar(255),
  "email" varchar(255)
);

CREATE TABLE "bean" (
  "id" integer PRIMARY KEY,
  "name" varchar(255),
  "country_id" integer
);

CREATE TABLE "bean_account" (
  "id" integer PRIMARY KEY,
  "user_id" integer,
  "account_name" varchar(255),
  "current_balance" money
);

CREATE TABLE "transaction" (
  "id" integer PRIMARY KEY,
  "user_id" integer,
  "bean_account_id" integer,
  "batch_id" integer,
  "category_id" integer,
  "is_outgoing" bool,
  "amount" money,
  "transaction_time" TIMESTAMP WITH TIME ZONE
);

CREATE TABLE "category" (
  "id" integer PRIMARY KEY,
  "name" varchar(255),
  "monthly_budget" money
);

CREATE TABLE "country" (
  "id" integer PRIMARY KEY,
  "name" varchar(255)
);

CREATE TABLE "batch" (
  "id" integer PRIMARY KEY,
  "quantity" integer,
  "weight" decimal,
  "bean_id" integer
);

COMMENT ON TABLE "batch" IS 'Optional in transaction';

ALTER TABLE "bean_account" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "transaction" ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "transaction" ADD FOREIGN KEY ("bean_account_id") REFERENCES "bean_account" ("id");

ALTER TABLE "bean" ADD FOREIGN KEY ("country_id") REFERENCES "country" ("id");

ALTER TABLE "batch" ADD FOREIGN KEY ("bean_id") REFERENCES "bean" ("id");

ALTER TABLE "transaction" ADD FOREIGN KEY ("batch_id") REFERENCES "batch" ("id");
