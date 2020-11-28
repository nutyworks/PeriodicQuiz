package com.github.nutyworks.periodicquiz

class Elements {
    var elements = mutableListOf<Element>()
}

class Element(
    val name: String,
    val appearance: String,
    val atomic_mass: Float,
    val boil: Float,
    val category: String,
    val color: String,
    val density: Float,
    val discovered_by: String,
    val melt: Float,
    val molarHeat: Float,
    val namedBy: String,
    val number: Int,
    val period: Int,
    val phase: String,
    val source: String,
    val spectral_img: String,
    val summary: String,
    val symbol: String,
    val xpos: Int,
    val ypos: Int,
    val shells: List<Int>,
    val electron_configuration: String,
    val electron_configuration_semantic: String,
    val electron_affinity: Float,
    val electronegativity_pauling: Float,
    val ionization_energies: List<Float>
)

