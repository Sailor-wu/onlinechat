#sql("findUserByAcctAndPwd")
  SELECT * FROM t_user WHERE aid = ? AND pwd = ?
#end

#sql("findUserByPhone")
  SELECT * FROM t_user WHERE phone = ?
#end

#sql ("findAllUserCount")
  SELECT COUNT(*) AS count FROM t_user;
#end