package com.mildom.lintchecks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.mildom.lintchecks.rules.SampleCodeDetector
import com.mildom.lintchecks.rules.ToastUtilDetector

/**
 * Lint Issue注册入口
 */
@Suppress("UnstableApiUsage")
class DYLintRuleRegistry : IssueRegistry() {

    override val issues: List<Issue>
        get() = listOf(
            SampleCodeDetector.ISSUE,
            ToastUtilDetector.ISSUE
        )

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 1

}