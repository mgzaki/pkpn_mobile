package com.pikipin.pkpn_mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.pikipin.pkpn_mobile.databinding.FragmentLoginBinding
import com.pikipin.pkpn_mobile.network.login.ILoginRepository
import com.pikipin.pkpn_mobile.util.createViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private val loginButtonSend: Button
        get() = binding.loginButtonSend

    private val loginPhoneNumberEdit: EditText
        get() = binding.loginPhoneNumberEdit

    @Inject
    lateinit var loginRepository: ILoginRepository

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel { LoginViewModel(loginRepository) }
            .apply {
                observeLifeCycle(lifecycle)
            }
        loginButtonSend.setOnClickListener {
            viewModel.verificationCode(loginPhoneNumberEdit.text.toString()).observe(viewLifecycleOwner) {
                Timber.d(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}