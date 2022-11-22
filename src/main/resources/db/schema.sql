ALTER USER postgres PASSWORD 'postgres';
CREATE SCHEMA IF NOT EXISTS recipes_db;
grant all privileges on database recipes_db to postgres;