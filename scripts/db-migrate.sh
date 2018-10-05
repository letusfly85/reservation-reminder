#!/usr/bin/env bash

export ROOT_PATH="`pwd`"

export NETWORK=reser
if [ "${CI}" = "true" ]; then
    export NETWORK=host
fi

docker run --rm  \
   --network ${NETWORK} \
   -v ${ROOT_PATH}/src/main/resources/db/migrations:/flyway/sql \
   -v ${ROOT_PATH}/src/main/resources/conf:/flyway/conf \
   boxfuse/flyway:5.1.4-alpine info migrate
