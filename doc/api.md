## Introduction to the Wish Wheel API

### Users

**POST /users**

Creates a new user.Requires a `user` object in the request.

**GET /users/:email**

Responds with the user whos email is given. Responds with a 404 if the user does not exist.

### Wheels

**POST /wheels**

Creates a new wish wheel. Requires a `wheel` object in the request with a valid `user_id` and `group_id`.

**GET /wheels/:id**

Responds with the wheel whos id is given. Responds with a 404 if the wheel does not exist.

### Items

**POST /wheel/:id/items**

Creates a new item for the given wish wheel. Requires an `item` object in the request.

**GET /items/:id**

Responds with the item whos id is given. Responds with a 404 if the item does not exist.

**GET /wheel/:id/items**

Responds with all items that belong to the given wish wheel. Returns a 404 if the wheel does not exist.

**PATCH /items/:id**

Assigns a user to the given item. Requires an `item` object in the request with a valid `user_id`.

### Groups

**POST /groups**

Creates a new group. Requires a `group` object in the request.

**GET /groups/:id**

Responds with the group whos id is given. Responds with a 404 if the group does not exist.

**GET /users/:email/groups**

Responds with all groups that a user belongs to. Returns a 404 if the user does not exist.

**POST /groups/:id/adduser**

Adds a user to the given group. Requires a valid `user_id` value in the request.
