package com.mildom.lintchecks.rules

import com.android.tools.lint.detector.api.*

@Suppress("UnstableApiUsage")
class SampleCodeDetector : Detector(), Detector.UastScanner {

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            //此issue的全局唯一id
            id = "test_id",
            //issue的标题，会显示在IDE的preference对话框，以及生成lint报告的类别标题
            briefDescription = "测试标题",
            //issue的完整解释，可以使用`等宽`, *斜体*, **粗体**等标记编写
            explanation = """
                你说啥？？
            """,
            //issue的类别
            category = Category.CORRECTNESS,
            //优先级，1最低10最高
            priority = 6,
            //级别
            severity = Severity.WARNING,
            //指定此issue由哪个Detector实现，以及覆盖的范围
            implementation = Implementation(
                SampleCodeDetector::class.java, Scope.JAVA_FILE_SCOPE
            )
        )
    }
}