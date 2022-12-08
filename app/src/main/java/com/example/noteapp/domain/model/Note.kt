package com.example.noteapp.domain.model

data class Note(
    val id: Int = DEFAULT_ID,
    val title: String,
    val description: String,
    val createdAt: Long
): java.io.Serializable
{
    companion object {
        const val DEFAULT_ID = 0
    }
}