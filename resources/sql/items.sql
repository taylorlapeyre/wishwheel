-- name: find-by-wheel
-- Selects all items that belong to a certain wish wheel.
SELECT id, name, price, wheel_id, user_id, image
FROM items
WHERE wheel_id = :wheel_id;

-- name: find-by-id
-- Selects all items that match a given id.
SELECT id, name, price, wheel_id, user_id, image
FROM items
WHERE id = :id;

-- name: insert!
-- Inserts a new item into the database.
INSERT INTO items (name, price, wheel_id)
VALUES (:name, :price, :wheel_id);

-- name: insert-with-image!
-- Inserts a new item into the database.
INSERT INTO items (name, price, wheel_id, image)
VALUES (:name, :price, :wheel_id, :image);

-- name: assign-user!
-- Updates the item that matches the given id by assigning its user_id to
-- the user id given.
UPDATE items
SET user_id = :user_id
WHERE id = :id;

-- name: assign-image!
-- Updates the item that matches the given id by assigning its image to
-- the image url given.
UPDATE items
SET image = :image
WHERE id = :id;
