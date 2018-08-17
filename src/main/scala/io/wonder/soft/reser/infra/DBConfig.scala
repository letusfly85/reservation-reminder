package io.wonder.soft.reser.infra

import io.getquill._

// todo change name
class DBConfig extends MysqlAsyncContext(Literal, "reser")

