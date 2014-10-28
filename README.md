# Wish Wheel

Wish Wheel is a website designed to help you find gifts for other people, and to make it easy for others to find the perfect gift for you.

## Code Organization

Front end code is located in `resources/public`.

SQL queries are located in `resources/sql`.

Back end code is located in `src`.

Documentation is located in `doc`.

Tests are located in `test`.


## Getting Up & Running

Make sure that you have **Java** installed.

Make sure that you have **MySQL** running and have created a database
called `wishwheel3`.

1. Download [Leiningen](http://leiningen.org)
  ``` bash
  # OSX
  $ brew install leiningen

  # Linux
  $ curl -O https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
  $ mv lein ~/bin/lein && chmod a+x ~/bin/lein
  $ lein
  ```
2. Clone this repository
3. Reset your database tables: `$ mysql -uroot wishwheel3 < resources/sql/reset.sql`
4. `lein run`

The website will be available on localhost:3000
