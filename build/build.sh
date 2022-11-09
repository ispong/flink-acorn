# 进入项目目录
CMD_PATH=`dirname $0`
BASE_PATH=$(`cd "$CMD_PATH"/../ || exit ; pwd`)
cd "${BASE_PATH}" || exit
echo "Flink-Acorn ==> Go to ${BASE_PATH}"

# 打包
echo "Flink-Acorn ==> Start package..."
mvn clean package -Dmaven.test.skip -pl acorn-common,acorn-server,acorn-plugins/acorn-sql-plugin || exit
echo "Flink-Acorn ==> Package success!"

# 创建acorn文件夹
ACORN_BUILD_DIR="${BASE_PATH}"/build/acorn
if [ -d "${ACORN_BUILD_DIR}" ]; then
    rm -rf "${ACORN_BUILD_DIR}"
    echo "Flink-Acorn ==> Delete acorn success!"
fi
mkdir -p "${ACORN_BUILD_DIR}"
echo "Flink-Acorn ==> Init acorn success!"

# 创建bin文件夹
mkdir -p "${ACORN_BUILD_DIR}"/bin
cp "${BASE_PATH}"/build/bin/* "$ACORN_BUILD_DIR"/bin
chmod a+x "$ACORN_BUILD_DIR"/bin/*
echo "Flink-Acorn ==> Init bin dir success!"

# 创建conf文件夹
mkdir -p "${ACORN_BUILD_DIR}"/conf
cp "${BASE_PATH}"/build/conf/* "$ACORN_BUILD_DIR"/conf
echo "Flink-Acorn ==> Init conf dir success!"

# 创建log文件夹
mkdir -p "${ACORN_BUILD_DIR}"/log
echo "Flink-Acorn ==> Init log dir success!"

# 创建lib文件夹
mkdir -p "${ACORN_BUILD_DIR}"/lib
cp "${BASE_PATH}"/acorn-server/target/acorn-server.jar "${ACORN_BUILD_DIR}"/lib/acorn-server.jar
echo "Flink-Acorn ==> Init lib dir success!"

# 创建plugins文件夹
mkdir -p "${ACORN_BUILD_DIR}"/plugins
cp "${BASE_PATH}"/acorn-plugins/acorn-sql-plugin/target/acorn-sql-plugin.jar "${ACORN_BUILD_DIR}"/plugins/acorn-sql-plugin.jar
echo "Flink-Acorn ==> Init plugins dir success!"

# 构建成功
echo "Welcome to use Flink-Acorn!"
