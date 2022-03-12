package com.ilpanda.plugin


import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ilpanda.plugin.extesion.PluginConfigExtension

class CommonTransform(private val pluginConfigExtension: PluginConfigExtension) : Transform() {

    override fun getName(): String {
        return "TeemoPlugin"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return true
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        beforeTransform()
        internalTransform(transformInvocation)
        afterTransform()
    }

    private fun beforeTransform() {
        TransformHelper.plugConfigExtension = pluginConfigExtension
    }

    private fun internalTransform(transformInvocation: TransformInvocation?) {
        if (transformInvocation != null) {
            if (!transformInvocation.isIncremental) {
                transformInvocation.outputProvider.deleteAll()
            }
            for (transformInput in transformInvocation.inputs) {
                for (jarInput in transformInput.jarInputs) {
                    TransformHelper.transformJars(jarInput, transformInvocation.outputProvider, transformInvocation.isIncremental)
                }
                for (directoryInput in transformInput.directoryInputs) {
                    TransformHelper.transformDirectory(directoryInput, transformInvocation.outputProvider, transformInvocation.isIncremental)
                }
            }
        }
    }

    private fun afterTransform() {

    }
}
