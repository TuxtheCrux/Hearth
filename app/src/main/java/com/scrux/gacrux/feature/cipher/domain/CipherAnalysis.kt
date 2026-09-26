package com.scrux.gacrux.feature.cipher.domain

data class CipherAnalysis(
    val avalanchePercentage: Float,
    val bruteForceEstimate: String,
    val frequencyDistribution: Map<Char, Float>
)