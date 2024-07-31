package com.example.noteapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.databinding.BottomSheetBinding
import com.example.noteapp.databinding.FragmentSaveOrUpdateBinding
import com.example.noteapp.model.NotesData
import com.example.noteapp.utils.hideKeyboard
import com.example.noteapp.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.Date


class SaveOrUpdateFragment : Fragment(R.layout.fragment_save_or_update)  {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentSaveOrUpdateBinding
    private var notesData:NotesData?=null
    private lateinit var result:String
    private var color=-1
    private val noteViewModel:NoteViewModel by activityViewModels()
    private val currentData=SimpleDateFormat.getInstance().format(Date())
    private val job=CoroutineScope(Dispatchers.Main)
    private val args:SaveOrUpdateFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation=MaterialContainerTransform().apply {
            drawingViewId=R.id.fragment
            scrimColor=Color.TRANSPARENT
            duration=300L
        }
        sharedElementEnterTransition=animation
        sharedElementReturnTransition=animation

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSaveOrUpdateBinding.bind(view)
        navController = Navigation.findNavController(view)
        val activity = activity as MainActivity

        binding.arrowback.setOnClickListener {
            requireView().hideKeyboard()
            navController.popBackStack()
        }

        binding.lastEdited.text=getString(R.string.edited_on,SimpleDateFormat.getDateInstance().format(Date()))
        binding.saveNote.setOnClickListener {
            saveNote()
        }


            try {
                binding.etNoteContent.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        binding.bottomBar.visibility = View.VISIBLE
                        binding.etNoteContent.setStylesBar(binding.StylesBar)
                    } else binding.bottomBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.d("TAG", "errorthis")
            }


            binding.fabColorPick.setOnClickListener {
                val bottomSheetDialog = BottomSheetDialog(
                    requireContext(),
                    R.style.BottomSheetDialog_Theme
                )
                val bottomSheetView: View = layoutInflater.inflate(
                    R.layout.bottom_sheet,
                    null
                )
                with(bottomSheetDialog) {
                    setContentView(bottomSheetView)
                    show()
                }


                val bottomSheetBinding = BottomSheetBinding.bind(bottomSheetView)
                bottomSheetBinding.apply {
                    colorPicker.apply {
                        setSelectedColor(color)
                        setOnColorSelectedListener { value ->
                            color = value
                            binding.apply {
                                notecontentfragmentparent.setBackgroundColor(color)
                                toolbarfragmentcontent.setBackgroundColor(color)
                                bottomBar.setBackgroundColor(color)
                                activity.window.statusBarColor = color
                            }
                            bottomSheetBinding.bottomSheetParent.setCardBackgroundColor(color)
                        }
                    }
                    bottomSheetParent.setCardBackgroundColor(color)
                }
                bottomSheetView.post {
                    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

    }

    private fun saveNote() {
       if (binding.etNoteContent.text.toString().isEmpty() || binding.etTitle.text.toString().isEmpty()){
           Toast.makeText(activity, "Something is Empty", Toast.LENGTH_SHORT).show()
       }
        else{
           notesData=args.notesData

           when(notesData) {
               null -> {
                   noteViewModel.saveNote(
                       NotesData(
                           0,
                           binding.etTitle.text.toString(),
                           binding.etNoteContent.getMD(),
                           currentData,
                           color
                       )

                   )
                   result="Note Saved"
                   setFragmentResult(
                       "key",
                       bundleOf("bundle key" to result)
                   )

                   navController.navigate(SaveOrUpdateFragmentDirections.actionSaveOrUpdateFragmentToNoteFragment())

               }

               else -> {

               }

           }
        }

    }


}