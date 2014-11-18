
/****************************************************************************************/
/*
/* Groups.sql
/* 
/* SQL tables for groups in WishWheel. 
/* Using functions such as adding users, finding users/creator.
/*
/* REFERENCE:
/* 
/* Change Ref 	Date		  By 				      DESCRIPTION
/* ==========   ======= 	============== 	=============
/* 074e5e9	 	  11/16/14  John Anny	      update documentation
/* dfaefc1		  10/28/14	Taylor Lapeyre	document sql
/* a71f789		  10/28/14	Taylor Lapeyre	typos
/* 54423b3		  10/28/14 	Taylor Lapeyre	api token authentication
/* c31fac1		  10/28/14	Taylor Lapeyre	code documentation
/* 6f4fba5		  10/27/14	Taylor Lapeyre	groups controller
/* 75b67c1		  10/27/14	John Anny		    initial commit
/*
/*
/*
/****************************************************************************************/


-- name: find-by-creator
-- Selects all groups that were created by the user with the given id.
SELECT name, user_id
FROM groups
WHERE user_id = :user_id;

-- name: find-by-id
-- Selects all groups that match a given id.
SELECT name, user_id
FROM groups
WHERE id = :id;

-- name: insert!
-- Inserts a new group into the database.
INSERT INTO groups (name, user_id)
VALUES (:name, :user_id);

-- name: add-user!
-- Relates a given group id and a user id in the table users_groups.
INSERT INTO users_groups (user_id, group_id)
VALUES (:user_id, :id);

-- name: find-by-user
-- Given a user id, selects all groups that the user is a member of.
SELECT g.name, g.user_id
FROM groups g
JOIN users_groups membership ON (
  membership.group_id  = g.id AND
  membership.user_id = :user_id
);
