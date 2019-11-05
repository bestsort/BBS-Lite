#!/bin/bash
echo "begin restart..."
PID=$(ps -ef | grep spring-boot:run | grep -v "grep" | awk '{print $2}')
BBS_PATH=$(pwd)
echo "BBS_PATH is ${BBS_PATH}"
kill -9 ${PID}
echo "begin update BBS-Lite from GitHub..."
cd ${BBS_PATH}

git pull
if [ $? -eq 0 ];then
    echo "kill java process success"
    echo "restart bbs-lite"
    mvn flyway:migrate -f run.xml
    echo "database merge compelete,begin to restart BBS-Lite"
    nohup mvn -f run.xml spring-boot:run > /dev/null 2>&1 &
    echo -e "[\033[1;32;48mSUCCESS\033[0m] BBS-Lite restart success"

else
    echo "kill java fail"
fi

