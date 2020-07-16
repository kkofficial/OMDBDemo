package com.kishor.omdbdemo



data class MovieDetails(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)

data class Search(
    val Poster: String,
    var Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String

)


