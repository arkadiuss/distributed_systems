rm queries.db
sqlite3 queries.db "CREATE TABLE queries(id INTEGER PRIMARY KEY AUTOINCREMENT, query VARCHAR UNIQUE, occurences INTEGER)"