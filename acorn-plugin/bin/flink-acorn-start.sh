#!/bin/bash

## 获取当前路径
current_path=`pwd`
case "`uname`" in
    Linux)
        bin_abs_path=$(readlink -f $(dirname $0))
        ;;
    *)
        bin_abs_path=`cd $(dirname $0); pwd`
        ;;
esac
BASE_PATH=${bin_abs_path}
echo "获取当前路径:"+ "${BASE_PATH}"

## 关闭原有进程
for metaResult in $(ps -e -o pid,command | grep acorn-plugin.jar)
do
    if [ "$metaResult" == java ]; then
      ACORN_PID=${PRE_VAL}
    	break
	fi
	PRE_VAL=${metaResult}
done
if [ _"${ACORN_PID}" != _ ];then
  kill -9 "${ACORN_PID}"
fi

## 日志
ACORN_LOG="${BASE_PATH}"/log/acorn-plugin.log
if [ ! -f "$ACORN_LOG" ]; then
    touch "$ACORN_LOG"
fi

## 启动项目
ACORN_APP="${BASE_PATH}"/lib/acorn-plugin.jar
nohup java -jar -Xmx2048m "${ACORN_APP}" --spring.config.location="${BASE_PATH}"/conf/application-star.yml --spring.profiles.active=star >> "${ACORN_LOG}" 2>&1 &
echo "部署成功"
