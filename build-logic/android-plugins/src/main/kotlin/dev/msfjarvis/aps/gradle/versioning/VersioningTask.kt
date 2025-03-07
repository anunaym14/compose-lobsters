/*
 * Copyright © 2022 Harsh Shandilya.
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package dev.msfjarvis.aps.gradle.versioning

import com.vdurmont.semver4j.Semver
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class VersioningTask : DefaultTask() {
  @get:Input abstract val semverString: Property<String>

  @get:OutputFile abstract val propertyFile: RegularFileProperty

  /** Generate the Android 'versionCode' property */
  private fun Semver.androidCode(): Int {
    return major * 1_00_00 + minor * 1_00 + patch
  }

  private fun Semver.toPropFileText(): String {
    val newVersionCode = androidCode()
    val newVersionName = toString()
    return buildString {
      appendLine(VERSIONING_PROP_COMMENT)
      append(VERSIONING_PROP_VERSION_CODE)
      append('=')
      appendLine(newVersionCode)
      append(VERSIONING_PROP_VERSION_NAME)
      append('=')
      appendLine(newVersionName)
    }
  }

  override fun getGroup(): String {
    return "versioning"
  }

  @TaskAction
  fun execute() {
    propertyFile.get().asFile.writeText(Semver(semverString.get()).toPropFileText())
  }
}
