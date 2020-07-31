1.Настройка базы данных PostgreSQL:

Создаем базу данных test
DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

Подключаемся к test, создаем схему currency
\c test;
DROP SCHEMA IF EXISTS currency;
CREATE SCHEMA currency;

Создаем пользователя и настраиваем ему права доступа к базе данных test
CREATE ROLE financier WITH PASSWORD 'financier' LOGIN;
GRANT ALL ON SCHEMA currency TO financier;

2.Сборка проекта:

откройте командную строку, перейдите в директорию с проектом, 
далее выполните последовательно команды:
mvn package, 
mvn install.
В директории target открываем файл CurrencyConverter-0.0.1-SNAPSHOT.jar,
приложение запускается на порту 8189, url = "http://localhost:8189/currency/".
Логин пользователя admin, пароль admin.

3.Приятного пользования!