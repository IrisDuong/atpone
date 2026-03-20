package com.atpone.utils.service;

public interface DataConverter<E,D> {

	D convertToDto(E e);
	E convertToEntity(D d);
}
