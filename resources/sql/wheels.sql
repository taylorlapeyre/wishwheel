/****************************************************************************************/
/*
/* Wheels.sql
/* 
/* SQL tables for wheels in WishWheel. 
/* Using fucntions such as finding by email/id/token/group.
/*
/* REFERENCE:
/* 
/* Change Ref   Date        By              DESCRIPTION
/* ==========   =========   ==============  =============
/* dfaefc1		10/28/14	Taylor Lapeyre	document sql
/* d1bc906		10/27/14	Taylor Lapeyre	get some routing figured out
/* 7ccba48		10/27/14	Taylor Lapeyre	initial commit
/* 75b67c1		10/27/14	John Anny		initial commit
/*
/*
/*
/****************************************************************************************/


-- name: find-by-user
-- Selects all wheels that were created by the given user_id.
SELECT id, name, user_id, group_id
FROM wheels
WHERE .user_id = :user_id;

-- name: find-by-id
-- Selects all wheels that have the given id.
SELECT id, name, user_id, group_id
FROM wheels
WHERE id = :id;

-- name: find-by-group
-- Selects all wheels that belong to the given group.
SELECT id, name, user_id, group_id
FROM wheels
WHERE group_id = :group_id;

-- name: insert!
-- Inserts a new wish wheel into the database.
INSERT INTO wheels (name, user_id, group_id)
VALUES (:name, :user_id, :group_id);
