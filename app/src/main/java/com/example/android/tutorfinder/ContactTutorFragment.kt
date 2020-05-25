package com.example.android.tutorfinder

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.contact_tutor_fragment.*


class ContactTutorFragment : Fragment() {

    companion object {
        fun newInstance() = ContactTutorFragment()
    }

    private lateinit var viewModel: ContactTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_tutor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactTutorViewModel::class.java)


        // TODO: Use the ViewModel
    }

}
