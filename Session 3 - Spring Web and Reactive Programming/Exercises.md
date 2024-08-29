# Exercises

## Spring Web:
* Exercise 3.1: Develop RESTful endpoints for the Product Service to handle CRUD operations:

```json

POST /products

//payload
{
    "name": "6 pcs. Pandesal",
    "description": "Filipino Monay 6 pieces per pack"
}

//response
{
    "productId": "43432432"
}

PUT /products/{productId}

//payload
{
    "name": "6 pcs. Pandesal",
    "description": "Filipino Monay"
}

GET /products/{productId}

//response
{
    "name": "6 pcs. Pandesal",
    "description": "Filipino Monay"
}

GET /products

//response
[
    {
        "productId": "434343",
        "name": "6 pcs. Pandesal"
    }
]

DELETE /products/{productId}
```

* Exercise 3.2: Implement global exception handling in the Product Service.

```json
POST /products

//Handled Exception
RuntimeException
//status code
500
//header
Request-Tracker: UUID (e.g. JHFDJHS267217HH)
//response
{
    "errorCode": "500-01",
    "description": "Internal Server Code"
}

GET /products/{productId}

//Exception Handled
ProductNotFoundException
//status code
404
//header
Request-Tracker: UUID (e.g. JHFDJHS267217HH)
//response
{
    "errorCode": "404-01",
    "description": "Product Not Found"
}

```

## Spring Reactive:
* Exercise 3.3: Create a reactive Order Service using Spring Web to handle order
placements.

```json

POST /orders

//payload
[
    {
        "productId": "HJDGSHJDGSJDGS322DS",
        "quantity": 2
    },
    {
        "productId": "AMDGSHJDDDGSJDGS322DS",
        "quantity": 12
    }
]
```

* Exercise 3.4: Implement reactive streams to process real-time order data using
Project Reactor.

```json

GET /order-events

//response
[
    {
        "productId": "HJDGSHJDGSJDGS322DS",
        "quantity": 2
    },
    {
        "productId": "AMDGSHJDDDGSJDGS322DS",
        "quantity": 12
    }
]
```