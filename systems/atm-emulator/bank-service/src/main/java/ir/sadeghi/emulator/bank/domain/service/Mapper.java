package ir.sadeghi.emulator.bank.domain.service;


public interface Mapper<T, E> {

    E mapEntityToDTO(T t);

    T mapDTOToEntity(E e);
}
