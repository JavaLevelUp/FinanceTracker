--changeset nshaelin:example:createTable:example
--comment: Create example table
CREATE TABLE "example" (
  "example_primary_key_column_id" serial PRIMARY KEY NOT NULL
);
--rollback DROP TABLE "example";