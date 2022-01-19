# DY Lint Rules

自定义[Lint工具](https://developer.android.com/studio/write/lint)规则仓库。

## 使用

拉代码
```bash
git clone ssh://git@git.nonolive.co:1122/lulu.mll/dylintrules.git
cd dylintrules
```

在示例项目执行lint
```bash
./gradlew :app:lint
```

## Modules介绍

1. checks

一个Java library，需要新增Lint规则在这里新建`Detector`编写，并在`DYLintRuleRegistry`注册`ISSUE`。

2. lib_dylint

一个Android library，`:checks`生成的jar直接复制到AS指定的lint规则目录下，即可将编写的规则应用到全部的项目，若只希望在特定项目生效，则有了此lib，`lintPublish`将jar打包进aar。修改了`checks`中的规则后，重新编译此lib生成aar并发布。

3. sample_app

示例app，依赖`:lib_dylint`，在这里检查lint效果。

## 自定义Lint规则

* [Lint API 文档](https://googlesamples.github.io/android-custom-lint-rules/api-guide.html)

* AS Settings -> Editor -> Inspections -> Android -> Lint查看谷歌编写的Lint规则，可以在`com.android.tools.lint:lint-checks@aar`找到这些`Detector`的具体实现。