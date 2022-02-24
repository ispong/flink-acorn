#### 如何加入我们

1. fork一下项目。
2. 创建一个合并请求（Pull Request），修改`.mailmap`文件，类似下面的格式。

```text
ispong <ispong@outlook.com>
```

#### 开启本地文档

```bash
# 先安装nodejs
npm i docsify-cli -g
docsify serve docs
```

#### 构建开发环境

参考小栗子博客，安装flink，kafka，mysql等测试项目。

#### 发布代码

- [ ] 修改acorn-common版本号(1.1.0)
- [ ] 修改SECURITY.md文件的版本号
- [ ] 修改CHANGELOG.md文件中版本说明(从github的project中获取)
- [ ] 将代码提交pr推到codeql中做代码检测
- [ ] 从main分支上创建当前版本分支
- [ ] 创建github的分支和版本(1.1.0)
- [ ] 发布到中央仓库
- [ ] 创建下一个 github 的 project
 
