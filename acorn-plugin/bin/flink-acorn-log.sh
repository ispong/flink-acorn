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

tail -f "${BASE_PATH}"/log/
