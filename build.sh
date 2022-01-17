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
BASE_PATH=${bin_abs_path}/..

## 创建项目文件夹
ACORN_BUILD_DIR="${BASE_PATH}"/../flink-acorn
if [ -d "${ACORN_BUILD_DIR}" ]; then
    rm -rf "${ACORN_BUILD_DIR}"
fi
mkdir -p "${ACORN_BUILD_DIR}"

# 复制bin文件夹
mkdir -p "${ACORN_BUILD_DIR}"/bin
cp "${BASE_PATH}"/../acorn-plugin/bin/* "$ACORN_BUILD_DIR"/bin

# 复制conf文件夹
mkdir -p "${ACORN_BUILD_DIR}"/conf
cp "${BASE_PATH}"/../acorn-plugin/conf/* "$ACORN_BUILD_DIR"/conf

# 创建log文件夹
mkdir -p "${ACORN_BUILD_DIR}"/log

# 打包
mvn clean package -Dmaven.test.skip -pl acorn-common,acorn-plugin

# 创建lib文件夹
mkdir -p "${ACORN_BUILD_DIR}"/lib
cp "${BASE_PATH}"/../star-plugin/target/acorn-plugin.jar "${STAR_BUILD_DIR}"/lib/acorn-plugin.jar

# 创建plugins文件夹
mkdir -p "${STAR_BUILD_DIR}"/plugins
