#sql("findUserByAcctAndPwd")
  SELECT id,aid,nickname,phone,email,region,signature,gender,is_login FROM t_user WHERE aid = ? AND pwd = ?
#end

#sql("findUserByPhone")
  SELECT * FROM t_user WHERE phone = ?
#end

#sql ("findAllUserCount")
  SELECT COUNT(*) AS count FROM t_user;
#end