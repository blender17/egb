UPDATE users_roles
SET roles_id = 1
WHERE
user_user_id IN (SELECT * FROM users WHERE username LIKE 'admin%' AND user_user_id = users.user_id)