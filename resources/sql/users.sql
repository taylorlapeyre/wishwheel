/****************************************************************************************/
/*
/* Users.sql
/* 
/* SQL tables for users in WishWheel. 
/* Using functions such as finding by email/id/token/group.
/*
/* REFERENCE:
/* 
/* Change Ref   Date        By              DESCRIPTION
/* ==========   =========   ==============  =============
/* dfaefc1		10/28/14	Taylor Lapeyre	document sql
/* 1e10633		10/27/14	Taylor Lapeyre	controllers
/* ad7ba78		10/27/14	Taylor Lapeyre	add front end, secure passwords
/* 7ccba48		10/27/14	Taylor Lapeyre	initial commit
/* 75b67c1		10/27/14	John Anny		initial commit
/*
/*
/*
/****************************************************************************************/

-- name: find-by-email
-- Selects all users that have the given email address.
SELECT id, email, first_name, last_name, password, token
FROM users
WHERE email = :email;

-- name: find-by-token
-- Selects all users that have the given api token.
SELECT id, email, first_name, last_name, password, token
FROM users
WHERE token = :token;

-- name: find-by-id
-- Selects all users that have the given id.
SELECT id, email, first_name, last_name, password, token
FROM users
WHERE id = :id;

-- name: find-by-group
-- Given a group id, selects all users that are members of the group.
SELECT id, email, first_name, last_name, password, token
FROM users u
JOIN users_groups membership ON (
  membership.user_id  = u.id AND
  membership.group_id = :group_id
);

-- name: insert!
-- Inserts a new user into the database.
INSERT INTO users (email, password, first_name, last_name)
VALUES (:email, :password, :first_name, :last_name);
