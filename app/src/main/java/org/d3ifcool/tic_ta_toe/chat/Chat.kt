package org.d3ifcool.tic_ta_toe.chat

data class Chat(
    val sender: String? = null,
    var message: String? = null,
    var fireBaseKey: String? = null
){
    fun toMap(): Map<String, String>{
        return hashMapOf(
            "sender" to sender.orEmpty(),
            "message" to message.orEmpty()
        )
    }
}