package com.hsharz.redline.feature.passwords.domain

import com.hsharz.redline.feature.passwords.data.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPasswordListUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    fun invoke(): Flow<List<PasswordListItem>> {
        return passwordRepository.getAllPasswords().map { list ->
            list.map { entity ->
                PasswordListItem(entity.id, entity.websiteOrApp)
            }
        }
    }
}