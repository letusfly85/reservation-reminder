package io.wonder.soft.reser.infra

import io.getquill._

class DBConfig extends MysqlAsyncContext(Literal, "reser")

