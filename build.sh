# 进入项目目录
BASE_PATH=$(cd "$(dirname "$0")" || exit ; pwd)
cd "${BASE_PATH}" || exit
echo "Flink-Acorn ==> Go to ${BASE_PATH}"

# 打包
echo "Flink-Acorn ==> Start build"
mvn clean package -Dmaven.test.skip -pl acorn-common,acorn-plugin || exit
echo "Flink-Acorn ==> Build success"

# 创建acorn文件夹
ACORN_BUILD_DIR="${BASE_PATH}"/flink-acorn
if [ -d "${ACORN_BUILD_DIR}" ]; then
    rm -rf "${ACORN_BUILD_DIR}"
    echo "Flink-Acorn ==> Delete acorn success"
fi
mkdir -p "${ACORN_BUILD_DIR}"
echo "Flink-Acorn ==> Create acorn success"

# 创建bin文件夹
mkdir -p "${ACORN_BUILD_DIR}"/bin
cp "${BASE_PATH}"/acorn-plugin/bin/* "$ACORN_BUILD_DIR"/bin
chmod a+x "$ACORN_BUILD_DIR"/bin/*
echo "Flink-Acorn ==> Create bin dir success"

# 创建conf文件夹
mkdir -p "${ACORN_BUILD_DIR}"/conf
cp "${BASE_PATH}"/acorn-plugin/conf/* "$ACORN_BUILD_DIR"/conf
echo "Flink-Acorn ==> Create conf dir success"

# 创建log文件夹
mkdir -p "${ACORN_BUILD_DIR}"/log
echo "Flink-Acorn ==> Create log dir success"

# 创建lib文件夹
mkdir -p "${ACORN_BUILD_DIR}"/lib
cp "${BASE_PATH}"/acorn-plugin/target/acorn-plugin.jar "${ACORN_BUILD_DIR}"/lib/acorn-plugin.jar
echo "Flink-Acorn ==> Create lib dir success"

# 创建plugins文件夹
mkdir -p "${ACORN_BUILD_DIR}"/plugins
echo "Flink-Acorn ==> Create plugins dir success"

# 创建tmp文件夹
mkdir -p "${ACORN_BUILD_DIR}"/tmp
echo "Flink-Acorn ==> Create tmp dir success"
