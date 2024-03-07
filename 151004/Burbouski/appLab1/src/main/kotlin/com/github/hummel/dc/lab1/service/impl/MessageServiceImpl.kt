package com.github.hummel.dc.lab1.service.impl

import com.github.hummel.dc.lab1.dto.request.MessageRequestTo
import com.github.hummel.dc.lab1.dto.request.MessageRequestToId
import com.github.hummel.dc.lab1.dto.response.MessageResponseTo
import com.github.hummel.dc.lab1.repository.MessagesRepository
import com.github.hummel.dc.lab1.service.MessageService

class MessageServiceImpl(
	private val messageRepository: MessagesRepository
) : MessageService {
	override suspend fun getAll(): List<MessageResponseTo> {
		val result = messageRepository.data.map { it.second }

		return result.map { it.toResponse() }
	}

	override suspend fun create(messageRequestTo: MessageRequestTo?): MessageResponseTo? {
		val id = if (messageRepository.data.isEmpty()) {
			-1
		} else {
			messageRepository.getLastItem()?.id ?: return null
		} + 1

		val bean = messageRequestTo?.toBean(id) ?: return null
		val result = messageRepository.addItem(bean.id, bean) ?: return null

		return result.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = messageRepository.deleteById(id)

	override suspend fun getById(id: Long): MessageResponseTo? {
		val result = messageRepository.getById(id) ?: return null

		return result.toResponse()
	}

	override suspend fun update(messageRequestToId: MessageRequestToId?): MessageResponseTo? {
		val bean = messageRequestToId?.toBean() ?: return null
		val result = messageRepository.addItem(bean.id, bean) ?: return null

		return result.toResponse()
	}
}