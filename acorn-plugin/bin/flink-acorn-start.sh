#!/bin/bash

## 检测是否配置环境变量
current_path=`pwd`
case "`uname`" in
    Linux)
                bin_abs_path=$(readlink -f $(dirname $0))
                ;;
        *)
                bin_abs_path=`cd $(dirname $0); pwd`
                ;;
esac
base=${bin_abs_path}/..
echo ${base}


## mvn打包
#POM_PATH=../pom.xml
#mvn clean package -f "${POM_PATH}"
#
## 关闭原有进程
#for metaResult in $(ps -e -o pid,command | grep acorn-plugin.jar)
#do
#    if [ "$metaResult" == java ]; then
#      ACORN_PID=${PRE_VAL}
#    	break
#	fi
#	PRE_VAL=${metaResult}
#done
#if [ _"${ACORN_PID}" != _ ];then
#  kill -9 "${ACORN_PID}"
#fi
#
## 如果日志文件不存在则创建
#ACORN_LOG_DIR=~/acorn-plugin/
#if [ ! -d "$ACORN_LOG_DIR" ]; then
#    mkdir -p "$ACORN_LOG_DIR"
#fi
#ACORN_LOG=~/acorn-plugin/acorn-plugin.log
#if [ ! -f "$ACORN_LOG" ]; then
#    touch "$ACORN_LOG"
#fi
#
## 启动项目
#ACORN_APP=../target/acorn-plugin.jar
#nohup java -jar -Xmx2048m "${ACORN_APP}" --spring.profiles.active="${ENV}" >> ${ACORN_LOG} 2>&1 &
#echo "部署成功"
