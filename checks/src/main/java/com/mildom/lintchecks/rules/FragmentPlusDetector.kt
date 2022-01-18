package com.mildom.lintchecks.rules

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UAnonymousClass
import org.jetbrains.uast.UClass

/**
 * 使用androidx包Fragment没有保留public的空构造器时，提醒需要注册自定义FragmentFactory
 */
@Suppress("UnstableApiUsage")
class FragmentPlusDetector : Detector(), Detector.UastScanner {

    override fun applicableSuperClasses(): List<String> {
        return listOf("androidx.fragment.app.Fragment")
    }

    override fun visitClass(context: JavaContext, declaration: UClass) {
        if (declaration is UAnonymousClass) {
            //覆盖匿名类的场景
            context.report(ISSUE, declaration, context.getNameLocation(declaration),
                ISSUE.getBriefDescription(TextFormat.TEXT))
        }

        val evaluator = context.evaluator
        if (evaluator.isAbstract(declaration)) {
            return
        }

        var hasDefaultConstructor = false
        var hasConstructor = false

        for (constructor in declaration.constructors) {
            hasConstructor = true

            if (constructor.parameterList.parametersCount == 0 && evaluator.isPublic(constructor)) {
                hasDefaultConstructor = true
                break
            }
        }

        if (!hasDefaultConstructor && hasConstructor) {
            val message = "[`${declaration.qualifiedName}`] 缺少无参构造函数，请保证注册了自定义的FragmentFactory"
            context.report(ISSUE, declaration, context.getNameLocation(declaration), message)
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "FragmentUsage",
            briefDescription = "Fragment缺少无参构造函数，请保证注册了自定义的FragmentFactory",
            explanation = """
                Fragment缺少无参构造函数，请保证注册了自定义的FragmentFactory。
                
                如果没有使用FragmentFactory，请保留public的空构造器，并使用argument传参。
            """,
            //issue的类别
            category = Category.CORRECTNESS,
            //优先级，1最低10最高
            priority = 8,
            //级别
            severity = Severity.WARNING,
            //指定此issue由哪个Detector实现，以及覆盖的范围
            implementation = Implementation(
                FragmentPlusDetector::class.java, Scope.JAVA_FILE_SCOPE
            )
        )
    }
}