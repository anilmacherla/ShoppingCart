1. Add Multiple Items to a Cart for a User
   POST http://localhost:8080/api/cart/{userId}/addItems
   Content-Type: application/json
  [
    {
      "name": "Item 1",
      "price": 10.99,
      "quantity": 2
    },
    {
      "name": "Item 2",
      "price": 5.49,
      "quantity": 3
    }
  ]

2. Get Cart by User ID and Cart ID
GET http://localhost:8080/api/cart/{userId}/cart/{cartId}

3.Get Cart by User ID
GET http://localhost:8080/api/cart/{userId}
