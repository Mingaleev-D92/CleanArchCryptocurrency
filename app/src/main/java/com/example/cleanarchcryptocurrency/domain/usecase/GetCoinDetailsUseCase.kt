package com.example.cleanarchcryptocurrency.domain.usecase

import coil.network.HttpException
import com.example.cleanarchcryptocurrency.data.common.Resource
import com.example.cleanarchcryptocurrency.domain.model.CoinDetailModel
import com.example.cleanarchcryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

/**
 * @author : Mingaleev D
 * @data : 17.10.2023
 */

class GetCoinDetailsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {

   suspend operator fun invoke(coinId: String): Flow<Resource<CoinDetailModel>> = flow {
      try {
         emit(Resource.Loading)
         val coins = coinRepository.fetchByIdCoin(coinId = coinId)
         emit(Resource.Success(coins))
      } catch (ex: HttpException) {
         emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
      } catch (ex: IOException) {
         emit(Resource.Error(ex.message ?: "Couldn't reach server. Check your internet connection."))
      }
   }
}
