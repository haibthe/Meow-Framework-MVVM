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

package sample.ui.api.catbreed.index

import meow.core.api.MeowEvent
import meow.core.arch.MeowViewModel
import meow.core.arch.SingleLiveData
import sample.App
import sample.data.catbreed.CatBreed

/**
 * [CatBreed]/Index View Model class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-04-11
 */

class CatBreedIndexViewModel(
    override val app: App,
    private val repository: CatBreed.Repository
) : MeowViewModel(app) {

    var eventLiveData = SingleLiveData<MeowEvent<*>>()
    var listLiveData = SingleLiveData<List<CatBreed>>()

    fun callApi() {
        safeCallApi(
            liveData = eventLiveData,
            apiAction = { repository.getCatBreedsFromApi() }
        ) { _, it ->
            listLiveData.postValue(it)
        }
    }
}