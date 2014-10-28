# Wish Wheel

Wish Wheel is a website designed to help you find gifts for other people, and to make it easy for others to find the perfect gift for you.

## Code Organization

- Front-end code is located in `resources/public`.
- SQL is located in `resources/sql`.
- Back-end code is located in `src`.
- Documentation is located in `doc`.
- Tests are located in `test`.


## Getting Up & Running

First, make sure that you have **Java** installed.

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
3. `$ lein ring server-headless`

The website will be available on localhost:3000
