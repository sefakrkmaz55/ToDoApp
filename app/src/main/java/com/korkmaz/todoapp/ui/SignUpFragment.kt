package com.korkmaz.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.korkmaz.todoapp.R
import com.korkmaz.todoapp.databinding.FragmentSignupBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String) {
        // ... (FirebaseAuth ile kullanıcı kaydı işlemleri) ...

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                // Kullanıcı başarıyla kaydolduğunda yapılacak işlemler
                Toast.makeText(requireContext(), "Kullanıcı oluşturuldu", Toast.LENGTH_SHORT).show()

                // Navigation Component ile HomeFragment'e geçiş yap
                findNavController().navigate(R.id.action_signUpFragment_to_home)
            } else {
                // Kullanıcı oluşturulamadığında yapılacak işlemler
                Toast.makeText(requireContext(), "Kullanıcı oluşturulamadı: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}