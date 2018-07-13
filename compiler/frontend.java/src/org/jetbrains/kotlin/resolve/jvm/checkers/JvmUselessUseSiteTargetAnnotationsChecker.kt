/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.jvm.checkers

import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.MemberDescriptor
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.*
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.diagnostics.reportDiagnosticOnce
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext

class JvmUselessUseSiteTargetAnnotationsChecker : DeclarationChecker {
    companion object {
        private val possibleUselessTargets = setOf(PROPERTY_GETTER, PROPERTY_SETTER, SETTER_PARAMETER)
    }

    override fun check(declaration: KtDeclaration, descriptor: DeclarationDescriptor, context: DeclarationCheckerContext) {
        if (descriptor !is MemberDescriptor) return
        if (declaration !is KtParameter && declaration !is KtProperty) return

        if (!Visibilities.isPrivate(descriptor.visibility)) return

        for (annotation in declaration.annotationEntries) {
            val psiTarget = annotation.useSiteTarget ?: continue
            val useSiteTarget = psiTarget.getAnnotationUseSiteTarget()
            if (useSiteTarget in possibleUselessTargets) {
                context.trace.reportDiagnosticOnce(Errors.ANNOTATION_WITH_TARGET_ON_NON_EXISTENT_DECLARATION.on(psiTarget, psiTarget.text))
            }
        }
    }
}
