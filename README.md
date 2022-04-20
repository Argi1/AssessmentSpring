# Spring Assessment for Kuehne+Nagel
This is an assessment  project made using Spring Boot and Spring Data.

## How to run
1. Clone the repository
2. Add the project to your selected IDE
3. Configure the datasource
    * Currently, it is configured to use H2 in memory database
    * If you want to use a mysql connection you have to do the following
        1. In the resources/application.properties file uncomment these lines
           ```
           #spring.datasource.url=jdbc:mysql://localhost:3306/springassessment?useSSL=false&allowPublicKeyRetrieval=true
           #spring.datasource.username=root
           #spring.datasource.password=password
  
           #spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
           #spring.jpa.properties.hibernate.dialect.storage_engine=innodb
           ```
        2. Change the url and login information according to your mysql setup
        3. Comment out these lines
           ```
           spring.datasource.url=jdbc:h2:mem:springassessment
           spring.datasource.driverClassName=org.h2.Driver
           spring.datasource.username=sa
           spring.datasource.password=
           spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
           ```
4. Run the project from your IDE
5. The API should now work and be accessible

## API Endpoints
* #### GET Endpoints
    1. [http://localhost:8081/api/orders](http://localhost:8081/api/orders) Get all orders
        * Optional parameter ```orderBy=asc``` or ```orderBy=desc``` to order the orders by their submission date.
        * Optional parameter ```date``` to only get orders that have submission date on the given date. Must be in dd-MM-yyyy format, like so ```date=17-04-2022```
    2. [http://localhost:8081/api/orders/products/{id}](http://localhost:8081/api/orders/products/1) Get all orders that have an orderLine where the product has the same Id as given in the URL
    3. [http://localhost:8081/api/orders/products/{id}/JPQL](http://localhost:8081/api/orders/products/1/JPQL) Same endpoint as the above one but uses JPQL to get the data instead.
    4. [http://localhost:8081/api/orders/customers/{id}](http://localhost:8081/api/orders/customers/1) Get all the orders belonging to given customer by Id.
    5. [http://localhost:8081/api/orders/customers/{id}/JPQL](http://localhost:8081/api/orders/customers/1/JPQL) Same endpoint as the above one but uses JPQL to get the data instead.

* #### POST Endpoints
    1. [http://localhost:8081/api/customers/{id}/addOrder](http://localhost:8081/api/customers/1/addOrder) Create a new order for the given customer by id given in the URL. Post body should look like this in JSON:
       ```
       {
           "orderLines": [
               {
                   "quantity": 4,
                   "product": {
                      "id": 1
                    }
               },
               {
                   "quantity":2,
                   "product" : {
                      "id": 2
                   }
               }
           ]
       }
       ```
       You can add any amount of orderLines to it, just change the quantity and the products Id to whatever is needed.
    2. [http://localhost:8081/api/customers](http://localhost:8081/api/customers) Create a new customer. Post body should look like this in JSON:
       ```
       {
           "fullName": "Jass",
           "email": "jass@gmail.com",
           "telephone": "55442300",
           "registrationCode": "570"
       }
       ```
    3. [http://localhost:8081/api/products](http://localhost:8081/api/products) Create a new product. Post body should look like this in JSON:
       ```
       {
           "name": "ThinkPad Laptop",
           "unitPrice": 1000,
           "skuCode": "0746"
       }
       ```
* #### PUT Endpoints
    1. [http://localhost:8081/api/orderlines/{id}](http://localhost:8081/api/orderlines/{1}) Update an order lines product quantity. Id in the url is for finding the correct orderline and the Put body should look like this in JSON:
       ```
       {
           "quantity": 5 
       }
       ```

## Test Coverage
All the functions required in the assessment have full unit test coverage.# Spring Assessment for Kuehne+Nagel
This is an assessment  project made using Spring Boot and Spring Data.

## How to run
1. Clone the repository
2. Add the project to your selected IDE
3. Configure the datasource
    * Currently, it is configured to use H2 in memory database
    * If you want to use a mysql connection you have to do the following
        1. In the resources/application.properties file uncomment these lines
           ```
           #spring.datasource.url=jdbc:mysql://localhost:3306/springassessment?useSSL=false&allowPublicKeyRetrieval=true
           #spring.datasource.username=root
           #spring.datasource.password=password
  
           #spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
           #spring.jpa.properties.hibernate.dialect.storage_engine=innodb
           ```
        2. Change the url and login information according to your mysql setup
        3. Comment out these lines
           ```
           spring.datasource.url=jdbc:h2:mem:springassessment
           spring.datasource.driverClassName=org.h2.Driver
           spring.datasource.username=sa
           spring.datasource.password=
           spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
           ```
4. Run the project from your IDE
5. The API should now work and be accessible

## API Endpoints
* #### GET Endpoints
    1. [http://localhost:8081/api/orders](http://localhost:8081/api/orders) Get all orders
        * Optional parameter ```orderBy=asc``` or ```orderBy=desc``` to order the orders by their submission date.
        * Optional parameter ```date``` to only get orders that have submission date on the given date. Must be in dd-MM-yyyy format, like so ```date=17-04-2022```
    2. [http://localhost:8081/api/orders/products/{id}](http://localhost:8081/api/orders/products/1) Get all orders that have an orderLine where the product has the same Id as given in the URL
    3. [http://localhost:8081/api/orders/products/{id}/JPQL](http://localhost:8081/api/orders/products/1/JPQL) Same endpoint as the above one but uses JPQL to get the data instead.
    4. [http://localhost:8081/api/orders/customers/{id}](http://localhost:8081/api/orders/customers/1) Get all the orders belonging to given customer by Id.
    5. [http://localhost:8081/api/orders/customers/{id}/JPQL](http://localhost:8081/api/orders/customers/1/JPQL) Same endpoint as the above one but uses JPQL to get the data instead.

* #### POST Endpoints
    1. [http://localhost:8081/api/customers/{id}/addOrder](http://localhost:8081/api/customers/1/addOrder) Create a new order for the given customer by id given in the URL. Post body should look like this in JSON:
       ```
       {
           "orderLines": [
               {
                   "quantity": 4,
                   "product": {
                      "id": 1
                    }
               },
               {
                   "quantity":2,
                   "product" : {
                      "id": 2
                   }
               }
           ]
       }
       ```
       You can add any amount of orderLines to it, just change the quantity and the products Id to whatever is needed.
    2. [http://localhost:8081/api/customers](http://localhost:8081/api/customers) Create a new customer. Post body should look like this in JSON:
       ```
       {
           "fullName": "Jass",
           "email": "jass@gmail.com",
           "telephone": "55442300",
           "registrationCode": "570"
       }
       ```
    3. [http://localhost:8081/api/products](http://localhost:8081/api/products) Create a new product. Post body should look like this in JSON:
       ```
       {
           "name": "ThinkPad Laptop",
           "unitPrice": 1000,
           "skuCode": "0746"
       }
       ```
* #### PUT Endpoints
    1. [http://localhost:8081/api/orderlines/{id}](http://localhost:8081/api/orderlines/{1}) Update an order lines product quantity. Id in the url is for finding the correct orderline and the Put body should look like this in JSON:
       ```
       {
           "quantity": 5 
       }
       ```

## Test Coverage
All the functions required in the assessment have full unit test coverage.# Spring Assessment for Kuehne+Nagel
This is an assessment  project made using Spring Boot and Spring Data.

## How to run
1. Clone the repository
2. Add the project to your selected IDE
3. Configure the datasource
    * Currently, it is configured to use H2 in memory database
    * If you want to use a mysql connection you have to do the following
        1. In the resources/application.properties file uncomment these lines
           ```
           #spring.datasource.url=jdbc:mysql://localhost:3306/springassessment?useSSL=false&allowPublicKeyRetrieval=true
           #spring.datasource.username=root
           #spring.datasource.password=password
  
           #spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
           #spring.jpa.properties.hibernate.dialect.storage_engine=innodb
           ```
        2. Change the url and login information according to your mysql setup
        3. Comment out these lines
           ```
           spring.datasource.url=jdbc:h2:mem:springassessment
           spring.datasource.driverClassName=org.h2.Driver
           spring.datasource.username=sa
           spring.datasource.password=
           spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
           ```
4. Run the project from your IDE
5. The API should now work and be accessible

## API Endpoints
* #### GET Endpoints
    1. [http://localhost:8081/api/orders](http://localhost:8081/api/orders) Get all orders
        * Optional parameter ```orderBy=asc``` or ```orderBy=desc``` to order the orders by their submission date.
        * Optional parameter ```date``` to only get orders that have submission date on the given date. Must be in dd-MM-yyyy format, like so ```date=17-04-2022```
    2. [http://localhost:8081/api/orders/products/{id}](http://localhost:8081/api/orders/products/1) Get all orders that have an orderLine where the product has the same Id as given in the URL
    3. [http://localhost:8081/api/orders/products/{id}/JPQL](http://localhost:8081/api/orders/products/1/JPQL) Same endpoint as the above one but uses JPQL to get the data instead.
    4. [http://localhost:8081/api/orders/customers/{id}](http://localhost:8081/api/orders/customers/1) Get all the orders belonging to given customer by Id.
    5. [http://localhost:8081/api/orders/customers/{id}/JPQL](http://localhost:8081/api/orders/customers/1/JPQL) Same endpoint as the above one but uses JPQL to get the data instead.

* #### POST Endpoints
    1. [http://localhost:8081/api/customers/{id}/addOrder](http://localhost:8081/api/customers/1/addOrder) Create a new order for the given customer by id given in the URL. Post body should look like this in JSON:
       ```
       {
           "orderLines": [
               {
                   "quantity": 4,
                   "product": {
                      "id": 1
                    }
               },
               {
                   "quantity":2,
                   "product" : {
                      "id": 2
                   }
               }
           ]
       }
       ```
       You can add any amount of orderLines to it, just change the quantity and the products Id to whatever is needed.
    2. [http://localhost:8081/api/customers](http://localhost:8081/api/customers) Create a new customer. Post body should look like this in JSON:
       ```
       {
           "fullName": "Jass",
           "email": "jass@gmail.com",
           "telephone": "55442300",
           "registrationCode": "570"
       }
       ```
    3. [http://localhost:8081/api/products](http://localhost:8081/api/products) Create a new product. Post body should look like this in JSON:
       ```
       {
           "name": "ThinkPad Laptop",
           "unitPrice": 1000,
           "skuCode": "0746"
       }
       ```
* #### PUT Endpoints
    1. [http://localhost:8081/api/orderlines/{id}](http://localhost:8081/api/orderlines/{1}) Update an order lines product quantity. Id in the url is for finding the correct orderline and the Put body should look like this in JSON:
       ```
       {
           "quantity": 5 
       }
       ```

## Test Coverage
All the functions required in the assessment have full unit test coverage.