package com.pikipin.pkpn_mobile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.pikipin.pkpn_mobile.base.BaseFragment
import com.pikipin.pkpn_mobile.databinding.FragmentLoginBinding
import com.pikipin.pkpn_mobile.network.registration.ILoginRepository
import com.pikipin.pkpn_mobile.util.createViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject



/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    /*
    private val loginButtonSend: Button
        get() = binding.loginButtonSend

    private val loginPhoneNumberEdit: EditText
        get() = binding.loginPhoneNumberEdit
*/
    @Inject
    lateinit var loginRepository: ILoginRepository

    private lateinit var viewModel: LoginViewModel
    override fun onAttach(context: Context) {
        context.theme?.applyStyle(R.style.Theme_Pkpn_mobile, true)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.theme?.applyStyle(R.style.Theme_Pkpn_mobile, true)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel { LoginViewModel(loginRepository) }
            .apply {
                observeLifeCycle(lifecycle)
            }

        /*
        loginButtonSend.setOnClickListener {
           /* viewModel.sendVerificationCode(loginPhoneNumberEdit.text.toString()).observe {
                Timber.d(it.toString())
            }*/
            viewModel.login("zakimstatefarm@gmail.com", "Chang3m3!").observe{
                Timber.d(it.toString())
            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}