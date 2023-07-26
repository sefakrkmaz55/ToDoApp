package com.korkmaz.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.korkmaz.todoapp.R
import com.korkmaz.todoapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPass.text.toString()

            performLogin(email, password)
        }

        binding.tvSignin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_to_signUpFragment)
        }
    }

    private fun performLogin(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Giriş Başarılı", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_login_to_home)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "E-posta veya şifre alanları boş bırakılamaz",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}