package com.hsharz.redline.feature.cipher.domain

data class CipherAnalysis(
    val avalanchePercentage: Float,
    val bruteForceEstimate: String,
    val frequencyDistribution: Map<Char, Float>
)