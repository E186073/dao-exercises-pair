-- Write queries to return the following:
-- Make the following changes in the "world" database.

-- 1. Add Superman's hometown, Smallville, Kansas to the city table. The 
-- countrycode is 'USA', and population of 45001. (Yes, I looked it up on 
-- Wikipedia.)
INSERT INTO city
(id, name, countrycode, district, population)
VALUES
(4080, 'Smallville', 'USA', 'Kansas', 45001);
 
SELECT * FROM city WHERE id = 4080;

-- 2. Add Kryptonese to the countrylanguage table. Kryptonese is spoken by 0.0001
-- percentage of the 'USA' population.
INSERT INTO countrylanguage
(countrycode, language, isofficial, percentage)
VALUES
('USA', 'Kryptonese', true, 0.0001);

SELECT * FROM countrylanguage WHERE countrycode = 'USA';
-- 3. After heated debate, "Kryptonese" was renamed to "Krypto-babble", change 
-- the appropriate record accordingly.
UPDATE countrylanguage SET language = 'Krypto-babble' WHERE language = 'Kryptonese';
SELECT * FROM countrylanguage WHERE language = 'Krypto-babble';

-- 4. Set the US captial to Smallville, Kansas in the country table.
UPDATE country SET capital = 4080 WHERE code = 'USA';


SELECT capital, "name" FROM country WHERE code = 'USA';

-- 5. Delete Smallville, Kansas from the city table. (Did it succeed? Why?)
-- DELETE from city WHERE name = 'Smallville';
-- not successful due to foreign key constraints

-- 6. Return the US captial to Washington.
UPDATE country SET capital = 3813 WHERE code = 'USA';
SELECT id, "name" FROM city WHERE countrycode = 'USA' AND district = 'District of Columbia';
SELECT capital FROM country WHERE code = 'USA';


-- 7. Delete Smallville, Kansas from the city table. (Did it succeed? Why?)
DELETE from city WHERE name = 'Smallville';
SELECT * FROM city WHERE name = 'Smallville';

-- 8. Reverse the "is the official language" setting for all languages where the
-- country's year of independence is within the range of 1800 and 1972 
-- (exclusive). 
-- (590 rows affected)
START TRANSACTION;
UPDATE countrylanguage
SET isofficial = NOT isofficial
FROM country
WHERE countrylanguage.countrycode = country.code AND country.indepyear > 1800 AND country.indepyear < 1972;


SELECT COUNT(code) --,  isofficial 
FROM countrylanguage cl
JOIN country ON cl.countrycode = country.code
WHERE country.indepyear > 1800 AND country.indepyear < 1972;

-- 9. Convert population so it is expressed in 1,000s for all cities. (Round to
-- the nearest integer value greater than 0.)
-- (4079 rows affected)
START TRANSACTION;
UPDATE city
SET population = ROUND(population/1000)
WHERE population > 0 AND population IS NOT NULL;


SELECT population, name FROM city; --1780000 kabul _> 1780

SELECT COUNT(population)
FROM city;
-- 10. Assuming a country's surfacearea is expressed in miles, convert it to 
-- meters for all countries where French is spoken by more than 20% of the 
-- population.
-- (7 rows affected)
START TRANSACTION;
UPDATE country
SET surfacearea = surfacearea *1609.344
FROM countrylanguage
WHERE country.code = countrylanguage.countrycode AND countrylanguage.language = 'French' AND countrylanguage.percentage > 20.0;

SELECT surfacearea FROM country where name = 'France';

