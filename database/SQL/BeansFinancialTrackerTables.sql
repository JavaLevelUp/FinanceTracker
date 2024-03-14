--changeset rfarisani:ftdb:createTable:user
--comment: Create user table
CREATE TABLE "user" (
  "id" integer PRIMARY KEY,
  "name" varchar(255),
  "email" varchar(255)
);
--rollback DROP TABLE "user";

--changeset rfarisani:ftdb:createTable:bean
--comment: Create bean table
CREATE TABLE "bean" (
  "id" integer PRIMARY KEY,
  "name" varchar(255),
  "country_id" integer
);
--rollback DROP TABLE "bean";

--changeset rfarisani:ftdb:createTable:bean_account
--comment: Create bean_account table
CREATE TABLE "bean_account" (
  "id" integer PRIMARY KEY,
  "user_id" integer,
  "account_name" varchar(255),
  "current_balance" money
);
--rollback DROP TABLE "bean_account";

--changeset rfarisani:ftdb:createTable:transaction
--comment: Create transaction table
CREATE TABLE "transaction" (
  "id" integer PRIMARY KEY,
  "user_id" integer,
  "bean_account_id" integer,
  "batch_id" integer,
  "category_id" integer,
  "is_outgoing" bool,
  "amount" money,
  "transaction_time" TIMESTAMP WITHOUT TIME ZONE
);
--rollback DROP TABLE "transaction";

--changeset rfarisani:ftdb:createTable:category
--comment: Create category table
CREATE TABLE "category" (
  "id" integer PRIMARY KEY,
  "name" varchar(255),
  "monthly_budget" money
);
--rollback DROP TABLE "category";

--changeset rfarisani:ftdb:createTable:country
--comment: Create country table
CREATE TABLE "country" (
  "id" integer PRIMARY KEY,
  "name" varchar(255)
);
--rollback DROP TABLE "country";

--changeset rfarisani:ftdb:createTable:batch
--comment: Create batch table
CREATE TABLE "batch" (
  "id" integer PRIMARY KEY,
  "batch_date" TIMESTAMP WITHOUT TIME ZONE,
  "weight" decimal,
  "bean_id" integer
);
--rollback DROP TABLE "batch";


COMMENT ON TABLE "batch" IS 'Optional in transaction';


--changeset rfarisani:ftdb:alterTable:bean_account_FK_user
--comment: Add user.id as FK to bean_account
ALTER TABLE "bean_account"
ADD CONSTRAINT bean_account_FK_user FOREIGN KEY ("user_id") REFERENCES "user" ("id")
--rollback ALTER TABLE "bean_account" DROP CONSTRAINT bean_account_FK_user

--changeset rfarisani:ftdb:alterTable:transaction_FK_category
--comment: Add category.id as FK to transaction
ALTER TABLE "transaction"
ADD CONSTRAINT transaction_FK_category FOREIGN KEY ("category_id") REFERENCES "category" ("id")
--rollback ALTER TABLE "transaction" DROP CONSTRAINT transaction_FK_category

--changeset rfarisani:ftdb:alterTable:transaction_FK_bean_account
--comment: Add bean_account.id as FK to transaction
ALTER TABLE "transaction"
ADD CONSTRAINT transaction_FK_bean_account FOREIGN KEY ("bean_account_id") REFERENCES "bean_account" ("id")
--rollback ALTER TABLE "transaction" DROP CONSTRAINT transaction_FK_bean_account

--changeset rfarisani:ftdb:alterTable:bean_FK_country
--comment: Add country.id as FK to bean
ALTER TABLE "bean"
ADD CONSTRAINT bean_FK_country FOREIGN KEY ("country_id") REFERENCES "country" ("id")
--rollback ALTER TABLE "bean" DROP CONSTRAINT bean_FK_country

--changeset rfarisani:ftdb:alterTable:batch_FK_bean
--comment: Add bean.id as FK to batch
ALTER TABLE "batch"
ADD CONSTRAINT batch_FK_bean FOREIGN KEY ("bean_id") REFERENCES "bean" ("id")
--rollback ALTER TABLE "batch" DROP CONSTRAINT batch_FK_bean

--changeset rfarisani:ftdb:alterTable:transaction_FK_batch
--comment: Add batch.id as FK to transaction
ALTER TABLE "transaction"
ADD CONSTRAINT transaction_FK_batch FOREIGN KEY ("batch_id") REFERENCES "batch" ("id")
--rollback ALTER TABLE "transaction" DROP CONSTRAINT transaction_FK_batch
