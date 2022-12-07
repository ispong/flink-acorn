#!/bin/bash

# 关闭原有进程
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

echo "关闭成功"
