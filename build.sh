# 创建star文件
STAR_BUILD_DIR=./star
if [ -d "$STAR_BUILD_DIR" ]; then
    rm -rf "$STAR_BUILD_DIR"
fi
mkdir -p "$STAR_BUILD_DIR"

# 复制bin文件夹
mkdir -p "$STAR_BUILD_DIR"/bin
cp ./star-plugin/bin/* "$STAR_BUILD_DIR"/bin

# 复制conf文件夹
mkdir -p "$STAR_BUILD_DIR"/conf
cp ./star-plugin/conf/* "$STAR_BUILD_DIR"/conf

# 创建log文件夹
mkdir -p "$STAR_BUILD_DIR"/log

# 打包
mvn clean package -Dmaven.test.skip -pl star-common,star-plugin,star-executor

# 创建lib文件夹
mkdir -p "$STAR_BUILD_DIR"/lib
cp ./star-plugin/target/star-plugin.jar "$STAR_BUILD_DIR"/lib/star-plugin.jar

# 创建plugins文件夹
mkdir -p "$STAR_BUILD_DIR"/plugins
cp ./star-executor/target/flink-executor.jar "$STAR_BUILD_DIR"/plugins/flink-executor.jar
