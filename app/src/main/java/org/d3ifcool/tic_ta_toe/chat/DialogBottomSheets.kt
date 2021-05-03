package org.d3ifcool.tic_ta_toe.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.tic_ta_toe.databinding.BottomSheetsBinding

class UpdateBottomDialog(
    private val chat: Chat,
): BottomSheetDialogFragment() {
    private  val databaseReference by lazy { FirebaseDatabase.getInstance().reference}
    private lateinit var binding: BottomSheetsBinding
    private val MESSAGE ="message"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etEdit.setText(chat.message.orEmpty())
        viewClicked()
    }

    private fun viewClicked() {
        binding.tvUpdate.setOnClickListener {
            chat.message = binding.etEdit.text.toString()
            updateChat(chat)
            dismiss()
        }

        binding.tvDelete.setOnClickListener {
//            viewModel.deleteChat(chat)
            dismiss()
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    fun updateChat(chat:Chat){
        val childUpdater = hashMapOf<String, Any>(
            "/$MESSAGE/${chat.fireBaseKey}" to chat.toMap()
        )
        databaseReference.updateChildren(childUpdater)
    }
}
