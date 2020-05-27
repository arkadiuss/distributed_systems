INSERT OR REPLACE INTO queries(query, occurences)
VALUES ('test', coalesce((SELECT occurences FROM queries WHERE query='test'), 0)+1);

SELECT * FROM queries