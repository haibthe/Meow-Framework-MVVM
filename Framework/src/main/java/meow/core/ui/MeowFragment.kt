/*
 * Copyright (C) 2020 Hamidreza Etebarian & Ali Modares.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package meow.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import meow.ktx.PermissionUtils
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.x.closestKodein
import org.kodein.di.erased.kcontext

/**
 * Meow Fragment class inherits from [Fragment] , [FragmentActivityInterface] , [KodeinAware].
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-02-28
 */

abstract class MeowFragment<B : ViewDataBinding> : Fragment(),
    FragmentActivityInterface<B>,
    KodeinAware {

    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)
    private val _parentKodein by closestKodein()
    override val kodein by Kodein.lazy { extend(_parentKodein) }

    override var permissionUtils: PermissionUtils? = null
    override fun context() = requireContext()
    override fun activity() = requireActivity() as MeowActivity<*>
    override fun contentView() = view!!

    override lateinit var binding: B

//    private var cachedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        if (cachedView == null) {
//            cachedView = binding.root
//        } else {
//            (binding.root.parent as? ViewGroup)?.removeView(binding.root)
//        }
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return binding.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initViewModel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        onRequestPermission(requestCode, grantResults)
    }

}