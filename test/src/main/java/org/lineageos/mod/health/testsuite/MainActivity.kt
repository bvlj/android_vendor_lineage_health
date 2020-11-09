/*
 * Copyright (C) 2020 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.mod.health.testsuite

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.lineageos.mod.health.testsuite.tests.TestInfo
import org.lineageos.mod.health.testsuite.tests.TestRunner
import org.lineageos.mod.health.testsuite.tests.TestStatus

class MainActivity : Activity() {
    private lateinit var testRunner: TestRunner
    private lateinit var adapter: ArrayAdapter<TestInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.testResultsList)
        val runBtn = findViewById<Button>(R.id.runTestsBtn)

        runBtn.setOnClickListener { v ->
            v.isEnabled = false
            runTests()
            v.isEnabled = true
        }

        testRunner = TestRunner(this)
        adapter = TestResultsAdapter(this, mutableListOf())
        listView.adapter = adapter
    }

    private fun runTests() {
        if (!assertHasPermissions()) {
            return
        }

        runBlocking {
            testRunner.runTests().collect { results ->
                adapter.clear()
                adapter.addAll(*results)
            }
        }
    }

    private fun assertHasPermissions(): Boolean {
        val permissions = arrayOf(
            "lineageos.permission.HEALTH_ACTIVITY",
            "lineageos.permission.HEALTH_BODY",
            "lineageos.permission.HEALTH_BREATHING",
            "lineageos.permission.HEALTH_HEART_BLOOD",
            "lineageos.permission.HEALTH_MINDFULNESS",
            "lineageos.permission.HEALTH_MEDICAL_PROFILE",
        )

        if (permissions.any { checkSelfPermission(it) == PackageManager.PERMISSION_DENIED }) {
            requestPermissions(permissions, 100)
            return false
        }
        return true
    }

    private class TestResultsAdapter(
        context: Context,
        data: List<TestInfo>
    ) : ArrayAdapter<TestInfo>(context, R.layout.item_result, data) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView
                ?: LayoutInflater.from(context).inflate(R.layout.item_result, parent, false)

            val item = getItem(position) ?: return view
            val icon = view.findViewById<ImageView>(android.R.id.icon)
            val title = view.findViewById<TextView>(android.R.id.title)

            when (item.status) {
                is TestStatus.Pass -> icon.setImageResource(R.drawable.ic_pass)
                is TestStatus.Running -> icon.setImageResource(R.drawable.ic_running)
                is TestStatus.Fail -> icon.setImageResource(R.drawable.ic_fail)
                else -> icon.setImageDrawable(null)
            }

            title.text = StringBuilder().apply {
                append(item.name)
                if (item.status is TestStatus.Fail) {
                    append("\n\t${item.status.error}")
                }
            }
            return view
        }
    }
}
