-- Write queries to return the following:
-- The following queries utilize the "world" database.

-- 1. The city name, country name, and city population of all cities in Europe with population greater than 1 million
-- (36 rows)

SELECT city.name AS "city name", country.name AS "country name", city.population
FROM city
JOIN country ON city.countrycode = country.code
WHERE city.population > 1000000 AND country.continent = 'Europe';

-- 2. The city name, country name, and city population of all cities in countries where French is an official language and the city population is greater than 1 million
-- (2 rows)
SELECT city.name, country.name, city.population
FROM city
JOIN countrylanguage ON city.countrycode = countrylanguage.countrycode
JOIN country ON countrylanguage.countrycode = country.code
WHERE city.population > 1000000 AND countrylanguage.isofficial = 'true' AND countrylanguage.language = 'French';

-- 3. The name of the countries and continents where the language Javanese is spoken
-- (1 row)
SELECT country.name, country.continent
FROM country
JOIN countrylanguage cl ON country.code = cl.countrycode
WHERE cl.language = 'Japanese'AND cl.isofficial = 'true';

-- 4. The names of all of the countries in Africa that speak French as an official language
-- (5 row)
SELECT country.name
FROM country
JOIN countrylanguage ON country.code = countrylanguage.countrycode
WHERE countrylanguage.language = 'French' AND countrylanguage.isofficial = 'true' AND country.continent = 'Africa';


-- 5. The average city population of cities in Europe
-- (average city population in Europe: 287,684)
SELECT AVG(city.population) AS "Avg city population"
FROM city
JOIN country ON city.countrycode = country.code
WHERE country.continent = 'Europe';

-- 6. The average city population of cities in Asia
-- (average city population in Asia: 395,019)
SELECT AVG(city.population) AS "Avg city population"
FROM city
JOIN country ON city.countrycode = country.code
WHERE country.continent = 'Asia';

-- 7. The number of cities in countries where English is an official language
-- (number of cities where English is official language: 523)
SELECT COUNT(city.name)
FROM city
JOIN countrylanguage ON countrylanguage.countrycode = city.countrycode
WHERE countrylanguage.language = 'English' AND countrylanguage.isofficial = 'true';

-- 8. The average population of cities in countries where the official language is English
-- (average population of cities where English is official language: 285,809)
SELECT AVG(city.population)
FROM city
JOIN countrylanguage ON city.countrycode = countrylanguage.countrycode
WHERE countrylanguage.language = 'English' AND countrylanguage.isofficial = 'true';

-- 9. The names of all of the continents and the population of the continent’s largest city
-- (6 rows, largest population for North America: 8,591,309)
SELECT country.continent, MAX(city.population)
FROM country
JOIN city ON country.code = city.countrycode
GROUP BY country.continent
ORDER BY country.continent ASC;


-- 10. The names of all of the cities in South America that have a population of more than 1 million people and the official language of each city’s country
-- (29 rows)
SELECT city.name, countrylanguage.language
FROM city
JOIN countrylanguage ON city.countrycode = countrylanguage.countrycode
JOIN country ON countrylanguage.countrycode = country.code
WHERE city.population > 1000000 AND countrylanguage.isofficial = 'true' AND country.continent = 'South America';  


