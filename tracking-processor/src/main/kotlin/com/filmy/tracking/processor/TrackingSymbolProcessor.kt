package com.filmy.tracking.processor

import com.filmy.core.tracking.annotations.NoTracking
import com.filmy.tracking.annotation.Trackable
import com.filmy.tracking.TrackingParam
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import java.lang.Exception

class TrackingSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>,
) : SymbolProcessor {
    val processedFunctions = mutableSetOf<String>()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val trackableSymbols = resolver
            .getSymbolsWithAnnotation(Trackable::class.qualifiedName!!)
            .filterIsInstance<KSFunctionDeclaration>()

        val (validSymbols, invalidSymbols) = trackableSymbols.partition { it.validate() }

        validSymbols.forEach { function ->
            processTrackableFunction(function, resolver)
        }
        return invalidSymbols
    }

    @OptIn(KspExperimental::class)
    private fun processTrackableFunction(
        function: KSFunctionDeclaration,
        resolver: Resolver,
    ) {
        val qualifiedName = function.qualifiedName?.asString() ?: return
        if (qualifiedName in processedFunctions) return

        if (!function.isComposable()) {
            logger.error(
                "@Trackable can only be applied to @Composable functions",
                function
            )
            return
        }

        if (function.hasAnnotation<NoTracking>()) {
            logger.warn("@Trackable ignored due to @NoTracking annotation", function)
            return
        }

        val trackableAnnotation = function.getAnnotationsByType(Trackable::class)
        val trackingData = extractTrackingData(function, trackableAnnotation.firstOrNull())

        try {
            val wrapperCodeGen = TrackingCodeGenerator(
                function = function,
                trackingData = trackingData,
                logger = logger,
            )

            val fileSpec = wrapperCodeGen.generate()

            val dependencies = Dependencies(
                aggregating = false,
                function.containingFile!!
            )

            codeGenerator.createNewFile(
                dependencies = dependencies,
                packageName = fileSpec.packageName,
                fileName = fileSpec.name,
                extensionName = "kt"
            ).use { outputStream ->
                outputStream.writer().use { writer ->
                    fileSpec.writeTo(writer)
                }
            }
            processedFunctions.add(qualifiedName)
            logger.info("Generated tracking wrapper for: $qualifiedName")
        } catch (e: Exception) {
            logger.error("Failed to generate wrapper for $qualifiedName: ${e.message}", function)
        }
    }

    private fun extractTrackingData(
        function: KSFunctionDeclaration,
        trackableAnnotation: Trackable?,
    ): TrackingParam {
        val functionName = function.simpleName.asString()

        return TrackingParam(
            id = trackableAnnotation?.id?.takeIf { it.isNotEmpty() } ?: "",
            name = trackableAnnotation?.name?.takeIf { it.isNotEmpty() } ?: "",
            role = trackableAnnotation?.role?.takeIf { it.isNotEmpty() } ?: "",
            metadata = emptyMap(),
        )
    }
}

private fun KSFunctionDeclaration.isComposable(): Boolean {
    return annotations.any {
        it.shortName.asString() == "Composable"
    }
}

private inline fun <reified T : Annotation> KSFunctionDeclaration.hasAnnotation(): Boolean {
    return annotations.any {
        it.shortName.asString() == T::class.simpleName
    }
}

private inline fun <reified T : Annotation> KSFunctionDeclaration.getAnnotationByType(): T? {
    // This is simplified; actual implementation would parse annotation arguments
    return null
}

private fun String.toSnakeCase(): String {
    return replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase()
}

private fun String.splitCamelCase(): String {
    return replace(Regex("([a-z])([A-Z])"), "$1 $2")
}