#!/usr/bin/env bash

export ROOT_PATH="`pwd`"
echo $ROOT_PATH

docker run --rm  \
   --net==host \
   -v ${ROOT_PATH}/src/main/resources/db/migrations:/flyway/sql \
   -v ${ROOT_PATH}/src/main/resources/conf:/flyway/conf \
   boxfuse/flyway:5.1.4-alpine info migrate
