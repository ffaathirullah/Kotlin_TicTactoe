package org.d3ifcool.tic_ta_toe.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.tic_ta_toe.databinding.AdapterMychatBinding
import org.d3ifcool.tic_ta_toe.databinding.AdapterOtherChatBinding

class MainAdapter(
    private val username : String,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val _chat by lazy { MutableLiveData<Chat>() }

    private val MY_CHAT = 0
    private val OTHER_CHAT = 1

    private var chatList: List<Chat> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val  inflater = LayoutInflater.from(parent.context)
        return if (viewType == MY_CHAT){
            val view = AdapterMychatBinding.inflate(inflater, parent,false)
            MyChatHolder(view.root, view)
        }else{
            val view = AdapterOtherChatBinding.inflate(inflater,parent, false)
            OtherChatHolder(view.root, view)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chatList[position]
        if (holder is MyChatHolder){
            holder.bindView(chat)
        }else if(holder is OtherChatHolder){
            holder.bindView(chat)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chat = chatList[position]
        return if(chat.sender.orEmpty() == username){
            MY_CHAT
        }else OTHER_CHAT
    }

    fun setChatDataList(dataChatList: List<Chat>) {
        this.chatList = dataChatList
        notifyDataSetChanged()
    }

    inner class MyChatHolder(
        view : View,
        val binding: AdapterMychatBinding
    ) : RecyclerView.ViewHolder(view){
        fun bindView(chat : Chat){
            binding.sender.text = chat.sender.orEmpty()
            binding.message.text = chat.message.orEmpty()

            binding.bubble.setOnClickListener {
               onChatLongPress(chat)
                true
            }
        }
    }

    fun onChatLongPress(chat: Chat){
        _chat.value = chat
    }

    inner class OtherChatHolder(
        view : View,
        val binding: AdapterOtherChatBinding
    ) : RecyclerView.ViewHolder(view){
        fun bindView(chat : Chat){
            binding.sender.text = chat.sender.orEmpty()
            binding.message.text = chat.message.orEmpty()
        }
    }
}