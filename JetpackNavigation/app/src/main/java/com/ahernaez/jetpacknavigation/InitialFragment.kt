package com.ahernaez.jetpacknavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.ahernaez.jetpacknavigation.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {

    private lateinit var binding: FragmentInitialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInitialBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gotoDestinationFragmentBtn.setOnClickListener {
//            NavHostFragment.findNavController(this).navigate(R.id.action_initialFragment_to_destinationFragment)

            val action = InitialFragmentDirections.actionInitialFragmentToDestinationFragment(binding.editText.text.toString())
            NavHostFragment.findNavController(this).navigate(action)

        }

    }
}