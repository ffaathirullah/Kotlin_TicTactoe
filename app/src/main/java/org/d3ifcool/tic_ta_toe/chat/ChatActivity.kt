package org.d3ifcool.tic_ta_toe.chat

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.*
import org.d3ifcool.tic_ta_toe.R

class ChatActivity : AppCompatActivity() {

    private  val firebaseAuth by lazy { FirebaseAuth.getInstance()}

    private  val databaseReference by lazy { FirebaseDatabase.getInstance().reference}

    private lateinit var mainAdapter: MainAdapter

    private val _chatList by lazy { MutableLiveData<List<Chat>>() }
    val chatList : LiveData<List<Chat>>
        get() = _chatList

    private val Tag ="Main View Model"

    private val MESSAGE ="message"

    private val _chat by lazy { MutableLiveData<Chat>() }
    val chat : LiveData<Chat>
        get() = _chat

    private lateinit var valueEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        viewClicked()
        setupRecyclerView()
        subscribeVM()
        readMessageFromFirebase()
    }

    private fun viewClicked(){
        btn_Send.setOnClickListener {
            if (text_chat.text.toString().isNotEmpty()){
                postMessage(text_chat.text.toString())
                text_chat.setText("")
            }else{
                Toast.makeText(this,"Message can't be apply", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getUsername(): String{
        val email = firebaseAuth.currentUser?.email.orEmpty()
        return email.split("@").first()
    }



    fun postMessage(mesaage :String){
        val chat = Chat(getUsername(), mesaage)
        Log.e(Tag, "$chat")
        databaseReference.child(MESSAGE).push().setValue(chat)
    }

    fun readMessageFromFirebase(){
        valueEventListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, error.message)
            }


            override fun onDataChange(snapshot: DataSnapshot) {
                val dataChat = snapshot.children.toMutableList().map {child ->
                    val chat = child.getValue(Chat::class.java)
                    chat?.fireBaseKey = child.key
                    chat ?: Chat()
                }
                _chatList.value = dataChat
            }

        }

        databaseReference.child(MESSAGE).addValueEventListener(valueEventListener)
    }

    fun updateChat(chat:Chat){
        val childUpdater = hashMapOf<String, Any>(
            "/$MESSAGE/${chat.fireBaseKey}" to chat.toMap()
        )
        databaseReference.updateChildren(childUpdater)
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        mainAdapter = MainAdapter( getUsername())
        rv_chat.layoutManager = layoutManager
        rv_chat.adapter = mainAdapter
    }

    private fun subscribeVM(){
        chatList.observe(this, Observer { chats ->
            chats?.let { mainAdapter.setChatDataList(it) }
        })

        chat.observe(this, Observer { chat ->
            chat.let {
                val bottomSheet = UpdateBottomDialog(it)
                bottomSheet.show(supportFragmentManager, "")
            }
        })
    }


}