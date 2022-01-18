package com.mildom.lintchecks.rules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.mildom.lintchecks.safeLet
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement

/**
 * 检查toast是否使用了封装的ToastUtil类
 */
@Suppress("UnstableApiUsage")
class ToastUtilDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        //返回需要检查的AST结点的类型
        return listOf(UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                safeLet(node.receiver, node.methodName) { _, methodName ->
                    if (methodName == "makeText") {
                        val resolve = node.resolve()
                        if (context.evaluator.isMemberInClass(resolve, "android.widget.Toast")) {
                            context.report(ISSUE, node, context.getLocation(node),
                                ISSUE.getBriefDescription(TextFormat.TEXT))
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            //此issue的全局唯一id
            id = "ShowToast",
            //issue的标题，会显示在IDE的preference对话框，以及生成lint报告的类别标题
            briefDescription = "使用ToastUtil替代系统Toast",
            //issue的完整解释，可以使用`等宽`, *斜体*, **粗体**等标记编写
            explanation = """
                统一使用`ToastUtil`展示toast.
            """,
            //issue的类别
            category = Category.CORRECTNESS,
            //优先级，1最低10最高
            priority = 6,
            //级别
            severity = Severity.WARNING,
            //指定此issue由哪个Detector实现，以及覆盖的范围
            implementation = Implementation(
                ToastUtilDetector::class.java, Scope.JAVA_FILE_SCOPE
            )
        )
    }
}