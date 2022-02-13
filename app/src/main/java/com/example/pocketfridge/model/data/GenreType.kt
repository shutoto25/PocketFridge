package com.example.pocketfridge.model.data

/**
 * ジャンル一覧.
 */
enum class GenreType(val id: Int) {
    ALL(0),
    MEAT(1),
    VEGETABLE(2),
    FISH(3),
    REFRIGERATE(4),
    FROZEN(5),
    OTHER(6)
}